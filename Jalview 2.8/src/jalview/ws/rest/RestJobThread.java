/*
 * Jalview - A Sequence Alignment Editor and Viewer (Version 2.8)
 * Copyright (C) 2012 J Procter, AM Waterhouse, LM Lui, J Engelhardt, G Barton, M Clamp, S Searle
 * 
 * This file is part of Jalview.
 * 
 * Jalview is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * Jalview is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Jalview.  If not, see <http://www.gnu.org/licenses/>.
 */
package jalview.ws.rest;

import jalview.bin.Cache;
import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.AlignmentOrder;
import jalview.datamodel.Annotation;
import jalview.datamodel.ColumnSelection;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.gui.Desktop;
import jalview.gui.PaintRefresher;
import jalview.gui.WebserviceInfo;
import jalview.io.NewickFile;
import jalview.io.packed.JalviewDataset;
import jalview.io.packed.JalviewDataset.AlignmentSet;
import jalview.ws.AWSThread;
import jalview.ws.AWsJob;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.apache.axis.transport.http.HTTPConstants;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class RestJobThread extends AWSThread
{
  enum Stage
  {
    SUBMIT, POLL
  }

  protected RestClient restClient;

  public RestJobThread(RestClient restClient)
  {
    super(restClient.af, null, restClient._input,
            restClient.service.postUrl);
    this.restClient = restClient; // may not be needed
    // Test Code
    // minimal job - submit given input and parse result onto alignment as
    // annotation/whatever

    // look for tree data, etc.

    // for moment do following job type only
    // input=visiblealignment,groupsindex
    // ie one subjob using groups defined on alignment.
    if (!restClient.service.isHseparable())
    {
      jobs = new RestJob[1];
      jobs[0] = new RestJob(0, this,
              restClient._input.getVisibleAlignment(restClient.service
                      .getGapCharacter()),
              restClient._input.getVisibleContigs());
      // need a function to get a range on a view/alignment and return both
      // annotation, groups and selection subsetted to just that region.

    }
    else
    {
      int[] viscontig = restClient._input.getVisibleContigs();
      AlignmentI[] viscontigals = restClient._input
              .getVisibleContigAlignments(restClient.service
                      .getGapCharacter());
      if (viscontigals != null && viscontigals.length > 0)
      {
        jobs = new RestJob[viscontigals.length];
        for (int j = 0; j < jobs.length; j++)
        {
          int[] visc = new int[]
          { viscontig[j * 2], viscontig[j * 2 + 1] };
          if (j != 0)
          {
            jobs[j] = new RestJob(j, this, viscontigals[j], visc);
          }
          else
          {
            jobs[j] = new RestJob(0, this, viscontigals[j], visc);
          }
        }
      }
    }
    // end Test Code
    /**
     * subjob types row based: per sequence in alignment/selected region { input
     * is one sequence or sequence ID } per alignment/selected region { input is
     * set of sequences, alignment, one or more sets of sequence IDs,
     */

    if (!restClient.service.isHseparable())
    {

      // create a bunch of subjobs per visible contig to ensure result honours
      // hidden boundaries
      // TODO: determine if a 'soft' hSeperable property is needed - e.g. if
      // user does SS-pred on sequence with big hidden regions, its going to be
      // less reliable.
    }
    else
    {
      // create a single subjob for the visible/selected input

    }
    // TODO: decide if vSeperable exists: eg. column wide analysis where hidden
    // rows do not affect output - generally no analysis that generates
    // alignment annotation is vSeparable -
  }

  /**
   * create gui components for monitoring jobs
   * 
   * @param webserviceInfo
   */
  public void setWebServiceInfo(WebserviceInfo webserviceInfo)
  {
    wsInfo = webserviceInfo;
    for (int j = 0; j < jobs.length; j++)
    {
      wsInfo.addJobPane();
      // Copy over any per job params
      if (jobs.length > 1)
      {
        wsInfo.setProgressName("region " + jobs[j].getJobnum(),
                jobs[j].getJobnum());
      }
      else
      {
        wsInfo.setProgressText(jobs[j].getJobnum(), OutputHeader);
      }
    }
  }

  private String getStage(Stage stg)
  {
    if (stg == Stage.SUBMIT)
      return "submitting ";
    if (stg == Stage.POLL)
      return "checking status of ";

    return (" being confused about ");
  }

  private void doPoll(RestJob rj) throws Exception
  {
    String postUrl = rj.getPollUrl();
    doHttpReq(Stage.POLL, rj, postUrl);
  }

  /**
   * construct the post and handle the response.
   * 
   * @throws Exception
   */
  public void doPost(RestJob rj) throws Exception
  {
    String postUrl = rj.getPostUrl();
    doHttpReq(Stage.SUBMIT, rj, postUrl);
    wsInfo.invalidate();
  }

  /**
   * do the HTTP request - and respond/set statuses appropriate to the current
   * stage.
   * 
   * @param stg
   * @param rj
   *          - provides any data needed for posting and used to record state
   * @param postUrl
   *          - actual URL to post/get from
   * @throws Exception
   */
  protected void doHttpReq(Stage stg, RestJob rj, String postUrl)
          throws Exception
  {
    StringBuffer respText = new StringBuffer();
    // con.setContentHandlerFactory(new
    // jalview.ws.io.mime.HttpContentHandler());
    HttpRequestBase request = null;
    String messages = "";
    if (stg == Stage.SUBMIT)
    {
      // Got this from
      // http://evgenyg.wordpress.com/2010/05/01/uploading-files-multipart-post-apache/

      HttpPost htpost = new HttpPost(postUrl);
      MultipartEntity postentity = new MultipartEntity(
              HttpMultipartMode.STRICT);
      for (Entry<String, InputType> input : rj.getInputParams())
      {
        if (input.getValue().validFor(rj))
        {
          postentity.addPart(input.getKey(), input.getValue()
                  .formatForInput(rj));
        }
        else
        {
          messages += "Skipped an input (" + input.getKey()
                  + ") - Couldn't generate it from available data.";
        }
      }
      htpost.setEntity(postentity);
      request = htpost;
    }
    else
    {
      request = new HttpGet(postUrl);
    }
    if (request != null)
    {
      DefaultHttpClient httpclient = new DefaultHttpClient();

      HttpContext localContext = new BasicHttpContext();
      HttpResponse response = null;
      try
      {
        response = httpclient.execute(request);
      } catch (ClientProtocolException he)
      {
        rj.statMessage = "Web Protocol Exception when " + getStage(stg)
                + "Job. <br>Problematic url was <a href=\""
                + request.getURI() + "\">" + request.getURI()
                + "</a><br>See Console output for details.";
        rj.setAllowedServerExceptions(0);// unrecoverable;
        rj.error = true;
        Cache.log.fatal("Unexpected REST Job " + getStage(stg)
                + "exception for URL " + rj.rsd.postUrl);
        throw (he);
      } catch (IOException e)
      {
        rj.statMessage = "IO Exception when " + getStage(stg)
                + "Job. <br>Problematic url was <a href=\""
                + request.getURI() + "\">" + request.getURI()
                + "</a><br>See Console output for details.";
        Cache.log.warn("IO Exception for REST Job " + getStage(stg)
                + "exception for URL " + rj.rsd.postUrl);

        throw (e);
      }
      switch (response.getStatusLine().getStatusCode())
      {
      case 200:
        rj.running = false;
        Cache.log.debug("Processing result set.");
        processResultSet(rj, response, request);
        break;
      case 202:
        rj.statMessage = "<br>Job submitted successfully. Results available at this URL:\n"
                + "<a href="
                + rj.getJobId()
                + "\">"
                + rj.getJobId()
                + "</a><br>";
        rj.running = true;
        break;
      case 302:
        Header[] loc;
        if (!rj.isSubmitted()
                && (loc = response
                        .getHeaders(HTTPConstants.HEADER_LOCATION)) != null
                && loc.length > 0)
        {
          if (loc.length > 1)
          {
            Cache.log
                    .warn("Ignoring additional "
                            + (loc.length - 1)
                            + " location(s) provided in response header ( next one is '"
                            + loc[1].getValue() + "' )");
          }
          rj.setJobId(loc[0].getValue());
          rj.setSubmitted(true);
        }
        completeStatus(rj, response);
        break;
      case 500:
        // Failed.
        rj.setSubmitted(true);
        rj.setAllowedServerExceptions(0);
        rj.setSubjobComplete(true);
        rj.error = true;
        rj.running = false;
        completeStatus(rj, response, "" + getStage(stg)
                + "failed. Reason below:\n");
        break;
      default:
        // Some other response. Probably need to pop up the content in a window.
        // TODO: deal with all other HTTP response codes from server.
        Cache.log.warn("Unhandled response status when " + getStage(stg)
                + "for " + postUrl + ": " + response.getStatusLine());
        rj.error = true;
        rj.setAllowedServerExceptions(0);
        rj.setSubjobComplete(true);
        rj.setSubmitted(true);
        try
        {
          completeStatus(
                  rj,
                  response,
                  ""
                          + getStage(stg)
                          + " resulted in an unexpected server response.<br/>Url concerned was <a href=\""
                          + request.getURI()
                          + "\">"
                          + request.getURI()
                          + "</a><br/>Filtered response content below:<br/>");
        } catch (IOException e)
        {
          Cache.log.debug("IOException when consuming unhandled response",
                  e);
        }
        ;
      }
    }
  }

  /**
   * job has completed. Something valid should be available from con
   * 
   * @param rj
   * @param con
   * @param req
   *          is a stateless request - expected to return the same data
   *          regardless of how many times its called.
   */
  private void processResultSet(RestJob rj, HttpResponse con,
          HttpRequestBase req)
  {
    if (rj.statMessage == null)
    {
      rj.statMessage = "";
    }
    rj.statMessage += "Job Complete.\n";
    try
    {
      rj.resSet = new HttpResultSet(rj, con, req);
      rj.gotresult = true;
    } catch (IOException e)
    {
      rj.statMessage += "Couldn't parse results. Failed.";
      rj.error = true;
      rj.gotresult = false;
    }
  }

  private void completeStatus(RestJob rj, HttpResponse con)
          throws IOException
  {
    completeStatus(rj, con, null);

  }

  private void completeStatus(RestJob rj, HttpResponse con, String prefix)
          throws IOException
  {
    StringBuffer sb = new StringBuffer();
    if (prefix != null)
    {
      sb.append(prefix);
    }
    ;
    if (rj.statMessage != null && rj.statMessage.length() > 0)
    {
      sb.append(rj.statMessage);
    }
    HttpEntity en = con.getEntity();
    /*
     * Just append the content as a string.
     */
    String f;
    StringBuffer content = new StringBuffer(f = EntityUtils.toString(en));
    f = f.toLowerCase();
    int body = f.indexOf("<body");
    if (body > -1)
    {
      content.delete(0, f.indexOf(">", body) + 1);
    }
    if (body > -1 && sb.length() > 0)
    {
      sb.append("\n");
      content.insert(0, sb);
      sb = null;
    }
    f = null;
    rj.statMessage = content.toString();
  }

  @Override
  public void pollJob(AWsJob job) throws Exception
  {
    assert (job instanceof RestJob);
    System.err.println("Debug RestJob: Polling Job");
    doPoll((RestJob) job);
  }

  @Override
  public void StartJob(AWsJob job)
  {
    assert (job instanceof RestJob);
    try
    {
      System.err.println("Debug RestJob: Posting Job");
      doPost((RestJob) job);
    } catch (NoValidInputDataException erex)
    {
      job.setSubjobComplete(true);
      job.setSubmitted(true);
      ((RestJob) job).statMessage = "<br>It looks like there was a problem with the data sent to the service :<br>"
              + erex.getMessage() + "\n";
      ((RestJob) job).error = true;

    } catch (Exception ex)
    {
      job.setSubjobComplete(true);
      job.setAllowedServerExceptions(-1);
      Cache.log.error("Exception when trying to start Rest Job.", ex);
    }
  }

  @Override
  public void parseResult()
  {
    // crazy users will see this message
    // TODO: finish this! and remove the message below!
    Cache.log.warn("Rest job result parser is currently INCOMPLETE!");
    int validres = 0;
    for (RestJob rj : (RestJob[]) jobs)
    {
      if (rj.hasResponse() && rj.resSet != null && rj.resSet.isValid())
      {
        String ln = null;
        try
        {
          Cache.log.debug("Parsing data for job " + rj.getJobId());
          rj.parseResultSet();
          if (rj.hasResults())
          {
            validres++;
          }
          Cache.log.debug("Finished parsing data for job " + rj.getJobId());

        } catch (Error ex)
        {
          Cache.log.warn("Failed to finish parsing data for job "
                  + rj.getJobId());
          ex.printStackTrace();
        } catch (Exception ex)
        {
          Cache.log.warn("Failed to finish parsing data for job "
                  + rj.getJobId());
          ex.printStackTrace();
        } finally
        {
          rj.error = true;
          rj.statMessage = "Error whilst parsing data for this job.<br>URL for job response is :<a href=\""
                  + rj.resSet.getUrl()
                  + "\">"
                  + rj.resSet.getUrl()
                  + "</a><br>";
        }
      }
    }
    if (validres > 0)
    {
      // add listeners and activate result display gui elements
      /**
       * decisions based on job result content + state of alignFrame that
       * originated the job:
       */
      /*
       * 1. Can/Should this job automatically open a new window for results
       */
      if (true)
      {
        // preserver current jalview behaviour
        wsInfo.setViewResultsImmediatly(true);
      }
      else
      {
        // realiseResults(true, true);
      }
      // otherwise, should automatically view results

      // TODO: check if at least one or more contexts are valid - if so, enable
      // gui
      wsInfo.showResultsNewFrame.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent e)
        {
          realiseResults(false);
        }

      });
      wsInfo.mergeResults.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent e)
        {
          realiseResults(true);
        }

      });

      wsInfo.setResultsReady();
    }
    else
    {
      // tell the user nothing was returned.
      wsInfo.setStatus(wsInfo.STATE_STOPPED_ERROR);
      wsInfo.setFinishedNoResults();
    }
  }

  /**
   * instructions for whether to create new alignment view on current alignment
   * set, add to current set, or create new alignFrame
   */
  private enum AddDataTo
  {
    /**
     * add annotation, trees etc to current view
     */
    currentView,
    /**
     * create a new view derived from current view and add data to that
     */
    newView,
    /**
     * create a new alignment frame for the result set and add annotation to
     * that.
     */
    newAlignment
  };

  public void realiseResults(boolean merge)
  {
    /*
     * 2. Should the job modify the parent alignment frame/view(s) (if they
     * still exist and the alignment hasn't been edited) in order to display new
     * annotation/features.
     */
    /**
     * alignment panels derived from each alignment set returned by service.
     */
    ArrayList<jalview.gui.AlignmentPanel> destPanels = new ArrayList<jalview.gui.AlignmentPanel>();
    /**
     * list of instructions for how to process each distinct alignment set
     * returned by the job set
     */
    ArrayList<AddDataTo> resultDest = new ArrayList<AddDataTo>();
    /**
     * when false, zeroth pane is panel derived from input deta.
     */
    boolean newAlignment = false;
    /**
     * gap character to be used for alignment reconstruction
     */
    char gapCharacter = restClient.av.getGapCharacter();
    // Now, iterate over all alignment sets returned from all jobs:
    // first inspect jobs and collate results data in order to count alignments
    // and other results
    // then assemble results from decomposed (v followed by h-separated) jobs
    // finally, new views and alignments will be created and displayed as
    // necessary.
    boolean hsepjobs = restClient.service.isHseparable();
    boolean vsepjobs = restClient.service.isVseparable();
    // total number of distinct alignment sets generated by job set.
    int numAlSets = 0, als = 0;
    List<AlignmentI> destAls = new ArrayList<AlignmentI>();
    List<jalview.datamodel.ColumnSelection> destColsel = new ArrayList<jalview.datamodel.ColumnSelection>();
    List<List<NewickFile>> trees = new ArrayList<List<NewickFile>>();

    do
    {
      // Step 1.
      // iterate over each alignment set returned from each subjob. Treating
      // each one returned in parallel with others.
      // Result collation arrays

      /**
       * mapping between index of sequence in alignment that was submitted to
       * server and index of sequence in the input alignment
       */
      int[][] ordermap = new int[jobs.length][];
      SequenceI[][] rseqs = new SequenceI[jobs.length][];
      AlignmentOrder[] orders = new AlignmentOrder[jobs.length];
      AlignmentAnnotation[][] alan = new AlignmentAnnotation[jobs.length][];
      SequenceGroup[][] sgrp = new SequenceGroup[jobs.length][];
      // Now collect all data for alignment Set als from job array
      for (int j = 0; j < jobs.length; j++)
      {
        RestJob rj = (RestJob) jobs[j];
        if (rj.hasResults())
        {
          JalviewDataset rset = rj.context;
          if (rset.hasAlignments())
          {
            if (numAlSets < rset.getAl().size())
            {
              numAlSets = rset.getAl().size();
            }
            if (als < rset.getAl().size()
                    && rset.getAl().get(als).isModified())
            {
              // Collate result data
              // TODO: decide if all alignmentI should be collected rather than
              // specific alignment data containers
              // for moment, we just extract content, but this means any
              // alignment properties may be lost.
              AlignmentSet alset = rset.getAl().get(als);
              alan[j] = alset.al.getAlignmentAnnotation();
              if (alset.al.getGroups() != null)
              {
                sgrp[j] = new SequenceGroup[alset.al.getGroups().size()];
                alset.al.getGroups().toArray(sgrp[j]);
              }
              else
              {
                sgrp[j] = null;
              }
              orders[j] = new AlignmentOrder(alset.al);
              rseqs[j] = alset.al.getSequencesArray();
              ordermap[j] = rj.getOrderMap();
              // if (rj.isInputUniquified()) {
              // jalview.analysis.AlignmentSorter.recoverOrder(rseqs[als]);
              // }

              if (alset.trees != null)
              {
                trees.add(new ArrayList<NewickFile>(alset.trees));
              }
              else
              {
                trees.add(new ArrayList<NewickFile>());
              }
            }
          }
        }
      }
      // Now aggregate and present results from this frame of alignment data.
      int nvertsep = 0, nvertseps = 1;
      if (vsepjobs)
      {
        // Jobs relate to different rows of input alignment.
        // Jobs are subdivided by rows before columns,
        // so there will now be a number of subjobs according tohsep for each
        // vertsep
        // TODO: get vertical separation intervals for each job and set
        // nvertseps
        // TODO: merge data from each group/sequence onto whole
        // alignment
      }
      /**
       * index into rest jobs subdivided vertically
       */
      int vrestjob = 0;
      // Destination alignments for all result data.
      ArrayList<SequenceGroup> visgrps = new ArrayList<SequenceGroup>();
      Hashtable<String, SequenceGroup> groupNames = new Hashtable<String, SequenceGroup>();
      ArrayList<AlignmentAnnotation> visAlAn = null;
      for (nvertsep = 0; nvertsep < nvertseps; nvertsep++)
      {
        // TODO: set scope w.r.t. original alignment view for vertical
        // separation.
        {
          // results for a job exclude hidden columns of input data, so map
          // back on to all visible regions
          /**
           * rest job result we are working with
           */
          int nrj = vrestjob;

          RestJob rj = (RestJob) jobs[nrj];
          int contigs[] = input.getVisibleContigs();
          AlignmentI destAl = null;
          jalview.datamodel.ColumnSelection destCs = null;
          // Resolve destAl for this data.
          if (als == 0 && rj.isInputContextModified())
          {
            // special case: transfer features, annotation, groups, etc,
            // from input
            // context to align panel derived from input data
            if (destAls.size() > als)
            {
              destAl = destAls.get(als);
            }
            else
            {
              if (!restClient.isAlignmentModified() && merge)
              {
                destAl = restClient.av.getAlignment();
                destCs = restClient.av.getColumnSelection();
                resultDest
                        .add(restClient.isShowResultsInNewView() ? AddDataTo.newView
                                : AddDataTo.currentView);
                destPanels.add(restClient.recoverAlignPanelForView());
              }
              else
              {
                newAlignment = true;
                // recreate the input alignment data
                Object[] idat = input
                        .getAlignmentAndColumnSelection(gapCharacter);
                destAl = new Alignment((SequenceI[]) idat[0]);
                destCs = (ColumnSelection) idat[1];
                resultDest.add(AddDataTo.newAlignment);
                // but do not add to the alignment panel list - since we need to
                // create a whole new alignFrame set.
              }
              destAls.add(destAl);
              destColsel.add(destCs);
            }
          }
          else
          {
            // alignment(s) returned by service is to be re-integrated and
            // displayed
            if (destAls.size() > als)
            {
              if (!vsepjobs)
              {
                // TODO: decide if multiple multiple alignments returned by
                // non-vseparable services are allowed.
                Cache.log
                        .warn("dealing with multiple alignment products returned by non-vertically separable service.");
              }
              // recover reference to last alignment created for this rest frame
              // ready for extension
              destAl = destAls.get(als);
              destCs = destColsel.get(als);
            }
            else
            {
              Object[] newview;

              if (!hsepjobs)
              {
                // single alignment for any job that gets mapped back on to
                // input data. Reconstruct by interleaving parts of returned
                // alignment with hidden parts of input data.
                SequenceI[][] nsq = splitSeqsOnVisibleContigs(rseqs[nrj],
                        contigs, gapCharacter);
                AlignmentOrder alo[] = new AlignmentOrder[nsq.length];
                for (int no = 0; no < alo.length; no++)
                {
                  alo[no] = new AlignmentOrder(orders[nrj].getOrder());
                }
                newview = input.getUpdatedView(nsq, orders, gapCharacter);
              }
              else
              {
                // each job maps to a single visible contig, and all need to be
                // stitched back together.
                // reconstruct using sub-region based MSA alignment construction
                // mechanism
                newview = input.getUpdatedView(rseqs, orders, gapCharacter);
              }
              destAl = new Alignment((SequenceI[]) newview[0]);
              destCs = (ColumnSelection) newview[1];
              newAlignment = true;
              // TODO create alignment from result data with propagated
              // references.
              destAls.add(destAl);
              destColsel.add(destCs);
              resultDest.add(AddDataTo.newAlignment);
              throw new Error("Impl. Error! TODO: ");
            }
          }
          /**
           * save initial job in this set in case alignment is h-separable
           */
          int initnrj = nrj;
          // Now add in groups
          for (int ncnt = 0; ncnt < contigs.length; ncnt += 2)
          {
            if (!hsepjobs)
            {
              // single alignment for any job that gets mapped back on to input
              // data.
            }
            else
            {
              // each job maps to a single visible contig, and all need to be
              // stitched back together.
              if (ncnt > 0)
              {
                nrj++;
              }
              // TODO: apply options for group merging and annotation merging.
              // If merging not supported, then either clear hashtables now or
              // use them to rename the new annotation/groups for each contig if
              // a conflict occurs.
            }
            if (sgrp[nrj] != null)
            {
              for (SequenceGroup sg : sgrp[nrj])
              {
                boolean recovered = false;
                SequenceGroup exsg = null;
                if (sg.getName() != null)
                {
                  exsg = groupNames.get(sg.getName());
                }
                if (exsg == null)
                {
                  exsg = new SequenceGroup(sg);
                  groupNames.put(exsg.getName(), exsg);
                  visgrps.add(exsg);
                  exsg.setStartRes(sg.getStartRes() + contigs[ncnt]);
                  exsg.setEndRes(sg.getEndRes() + contigs[ncnt]);
                }
                else
                {
                  recovered = true;
                }
                // now replace any references from the result set with
                // corresponding refs from alignment input set.

                // TODO: cope with recovering hidden sequences from
                // resultContext
                {
                  for (SequenceI oseq : sg.getSequences(null))
                  {
                    SequenceI nseq = getNewSeq(oseq, rseqs[nrj],
                            ordermap[nrj], destAl);
                    if (nseq != null)
                    {
                      if (!recovered)
                      {
                        exsg.deleteSequence(oseq, false);
                      }
                      exsg.addSequence(nseq, false);
                    }
                    else
                    {
                      Cache.log
                              .warn("Couldn't resolve original sequence for new sequence.");
                    }
                  }
                  if (sg.hasSeqrep())
                  {
                    if (exsg.getSeqrep() == sg.getSeqrep())
                    {
                      // lift over sequence rep reference too
                      SequenceI oseq = sg.getSeqrep();
                      SequenceI nseq = getNewSeq(oseq, rseqs[nrj],
                              ordermap[nrj], destAl);
                      if (nseq != null)
                      {
                        exsg.setSeqrep(nseq);
                      }
                    }
                  }
                }
                if (recovered)
                {
                  // adjust boundaries of recovered group w.r.t. new group being
                  // merged on to original alignment.
                  int start = sg.getStartRes() + contigs[ncnt], end = sg
                          .getEndRes() + contigs[ncnt];
                  if (start < exsg.getStartRes())
                  {
                    exsg.setStartRes(start);
                  }
                  if (end > exsg.getEndRes())
                  {
                    exsg.setEndRes(end);
                  }
                }
              }
            }
          }
          // reset job counter
          nrj = initnrj;
          // and finally add in annotation and any trees for each job
          for (int ncnt = 0; ncnt < contigs.length; ncnt += 2)
          {
            if (!hsepjobs)
            {
              // single alignment for any job that gets mapped back on to input
              // data.
            }
            else
            {
              // each job maps to a single visible contig, and all need to be
              // stitched back together.
              if (ncnt > 0)
              {
                nrj++;
              }
            }

            // merge alignmentAnnotation into one row
            if (alan[nrj] != null)
            {
              for (int an = 0; an < alan[nrj].length; an++)
              {
                SequenceI sqass = null;
                SequenceGroup grass = null;
                if (alan[nrj][an].sequenceRef != null)
                {
                  // TODO: ensure this relocates sequence reference to local
                  // context.
                  sqass = getNewSeq(alan[nrj][an].sequenceRef, rseqs[nrj],
                          ordermap[nrj], destAl);
                }
                if (alan[nrj][an].groupRef != null)
                {
                  // TODO: verify relocate group reference to local context
                  // works correctly
                  grass = groupNames.get(alan[nrj][an].groupRef.getName());
                  if (grass == null)
                  {
                    Cache.log
                            .error("Couldn't relocate group referemce for group "
                                    + alan[nrj][an].groupRef.getName());
                  }
                }
                if (visAlAn == null)
                {
                  visAlAn = new ArrayList<AlignmentAnnotation>();
                }
                AlignmentAnnotation visan = null;
                for (AlignmentAnnotation v : visAlAn)
                {
                  if (v.label != null
                          && v.label.equals(alan[nrj][an].label))
                  {
                    visan = v;
                  }
                }
                if (visan == null)
                {
                  visan = new AlignmentAnnotation(alan[nrj][an]);
                  // copy annotations, and wipe out/update refs.
                  visan.annotations = new Annotation[input.getWidth()];
                  visan.groupRef = grass;
                  visan.sequenceRef = sqass;
                  visAlAn.add(visan);
                }
                if (contigs[ncnt] + alan[nrj][an].annotations.length > visan.annotations.length)
                {
                  // increase width of annotation row
                  Annotation[] newannv = new Annotation[contigs[ncnt]
                          + alan[nrj][an].annotations.length];
                  System.arraycopy(visan.annotations, 0, newannv, 0,
                          visan.annotations.length);
                  visan.annotations = newannv;
                }
                // now copy local annotation data into correct position
                System.arraycopy(alan[nrj][an].annotations, 0,
                        visan.annotations, contigs[ncnt],
                        alan[nrj][an].annotations.length);

              }
            }
            // Trees
            if (trees.get(nrj).size() > 0)
            {
              for (NewickFile nf : trees.get(nrj))
              {
                // TODO: process each newick file, lifting over sequence refs to
                // current alignment, if necessary.
                Cache.log
                        .error("Tree recovery from restjob not yet implemented.");
              }
            }
          }
        }
      } // end of vseps loops.
      if (visAlAn != null)
      {
        for (AlignmentAnnotation v : visAlAn)
        {
          destAls.get(als).addAnnotation(v);
        }
      }
      if (visgrps != null)
      {
        for (SequenceGroup sg : visgrps)
        {
          destAls.get(als).addGroup(sg);
        }
      }
    } while (++als < numAlSets);
    // Finally, assemble each new alignment, and create new gui components to
    // present it.
    /**
     * current AlignFrame where results will go.
     */
    AlignFrame destaf = restClient.recoverAlignFrameForView();
    /**
     * current pane being worked with
     */
    jalview.gui.AlignmentPanel destPanel = restClient
            .recoverAlignPanelForView();
    als = 0;
    for (AddDataTo action : resultDest)
    {
      AlignmentI destal;
      ColumnSelection destcs;
      String alTitle = restClient.service.details.Action + " using "
              + restClient.service.details.Name + " on "
              + restClient.viewTitle;
      switch (action)
      {
      case newAlignment:
        destal = destAls.get(als);
        destcs = destColsel.get(als);
        destaf = new AlignFrame(destal, destcs, AlignFrame.DEFAULT_WIDTH,
                AlignFrame.DEFAULT_HEIGHT);
        PaintRefresher.Refresh(destaf, destaf.getViewport()
                .getSequenceSetId());
        // todo transfer any feature settings and colouring
        /*
         * destaf.getFeatureRenderer().transferSettings(this.featureSettings);
         * // update orders if (alorders.size() > 0) { if (alorders.size() == 1)
         * { af.addSortByOrderMenuItem(WebServiceName + " Ordering",
         * (AlignmentOrder) alorders.get(0)); } else { // construct a
         * non-redundant ordering set Vector names = new Vector(); for (int i =
         * 0, l = alorders.size(); i < l; i++) { String orderName = new
         * String(" Region " + i); int j = i + 1;
         * 
         * while (j < l) { if (((AlignmentOrder) alorders.get(i))
         * .equals(((AlignmentOrder) alorders.get(j)))) { alorders.remove(j);
         * l--; orderName += "," + j; } else { j++; } }
         * 
         * if (i == 0 && j == 1) { names.add(new String("")); } else {
         * names.add(orderName); } } for (int i = 0, l = alorders.size(); i < l;
         * i++) { af.addSortByOrderMenuItem( WebServiceName + ((String)
         * names.get(i)) + " Ordering", (AlignmentOrder) alorders.get(i)); } } }
         */
        // TODO: modify this and previous alignment's title if many alignments
        // have been returned.
        Desktop.addInternalFrame(destaf, alTitle, AlignFrame.DEFAULT_WIDTH,
                AlignFrame.DEFAULT_HEIGHT);

        break;
      case newView:
        // TODO: determine title for view
        break;
      case currentView:
        break;
      }
    }
    if (!newAlignment)
    {
      if (restClient.isShowResultsInNewView())
      {
        // destPanel = destPanel.alignFrame.newView(false);
      }
    }
    else
    {

    }
    /*
     * if (als) // add the destination panel to frame zero of result panel set }
     * } if (destPanels.size()==0) { AlignFrame af = new AlignFrame((AlignmentI)
     * idat[0], (ColumnSelection) idat[1], AlignFrame.DEFAULT_WIDTH,
     * AlignFrame.DEFAULT_HEIGHT);
     * 
     * jalview.gui.Desktop.addInternalFrame(af, "Results for " +
     * restClient.service.details.Name + " " + restClient.service.details.Action
     * + " on " + restClient.af.getTitle(), AlignFrame.DEFAULT_WIDTH,
     * AlignFrame.DEFAULT_HEIGHT); destPanel = af.alignPanel; // create totally
     * new alignment from stashed data/results
     */

    /*
     */

    /**
     * alignments. New alignments are added to dataset, and subsequently
     * annotated/visualised accordingly. 1. New alignment frame created for new
     * alignment. Decide if any vis settings should be inherited from old
     * alignment frame (e.g. sequence ordering ?). 2. Subsequent data added to
     * alignment as below:
     */
    /**
     * annotation update to original/newly created context alignment: 1.
     * identify alignment where annotation is to be loaded onto. 2. Add
     * annotation, excluding any duplicates. 3. Ensure annotation is visible on
     * alignment - honouring ordering given by file.
     */
    /**
     * features updated to original or newly created context alignment: 1.
     * Features are(or were already) added to dataset. 2. Feature settings
     * modified to ensure all features are displayed - honouring any ordering
     * given by result file. Consider merging action with the code used by the
     * DAS fetcher to update alignment views with new info.
     */
    /**
     * Seq associated data files (PDB files). 1. locate seq association in
     * current dataset/alignment context and add file as normal - keep handle of
     * any created ref objects. 2. decide if new data should be displayed : PDB
     * display: if alignment has PDB display already, should new pdb files be
     * aligned to it ?
     * 
     */
    // destPanel.adjustAnnotationHeight();

  }

  /**
   * split the given array of sequences into blocks of subsequences
   * corresponding to each visible contig
   * 
   * @param sequenceIs
   * @param contigs
   * @param gapChar
   *          padding character for ragged ends of visible contig region.
   * @return
   */
  private SequenceI[][] splitSeqsOnVisibleContigs(SequenceI[] sequenceIs,
          int[] contigs, char gapChar)
  {
    int nvc = contigs == null ? 1 : contigs.length / 2;
    SequenceI[][] blocks = new SequenceI[nvc][];
    if (contigs == null)
    {
      blocks[0] = new SequenceI[sequenceIs.length];
      System.arraycopy(sequenceIs, 0, blocks[0], 0, sequenceIs.length);
    }
    else
    {
      // deja vu - have I written this before ?? propagateGaps does this in a
      // way
      char[] gapset = null;
      int start = 0, width = 0;
      for (int c = 0; c < nvc; c++)
      {
        width = contigs[c * 2 + 1] - contigs[c * 2] + 1;
        for (int s = 0; s < sequenceIs.length; s++)
        {
          int end = sequenceIs[s].getLength();
          if (start < end)
          {
            if (start + width < end)
            {
              blocks[c][s] = sequenceIs[s].getSubSequence(start, start
                      + width);
            }
            else
            {
              blocks[c][s] = sequenceIs[s].getSubSequence(start, end);
              String sq = blocks[c][s].getSequenceAsString();
              for (int n = start + width; n > end; n--)
              {
                sq += gapChar;
              }
            }
          }
          else
          {
            if (gapset == null || gapset.length < width)
            {
              char ng[] = new char[width];
              int gs = 0;
              if (gapset != null)
              {
                System.arraycopy(gapset, 0, ng, 0, gs = gapset.length);
              }
              while (gs < ng.length)
              {
                ng[gs++] = gapChar;
              }
            }
            blocks[c][s] = sequenceIs[s].getSubSequence(end, end);
            blocks[c][s].setSequence(gapset.toString().substring(0, width));
          }
        }
        if (c > 0)
        {
          // adjust window for next visible segnment
          start += contigs[c * 2 + 1] - contigs[c * 2];
        }
      }
    }
    return blocks;
  }

  /**
   * recover corresponding sequence from original input data corresponding to
   * sequence in a specific job's input data.
   * 
   * @param oseq
   * @param sequenceIs
   * @param is
   * @param destAl
   * @return
   */
  private SequenceI getNewSeq(SequenceI oseq, SequenceI[] sequenceIs,
          int[] is, AlignmentI destAl)
  {
    int p = 0;
    while (p < sequenceIs.length && sequenceIs[p] != oseq)
    {
      p++;
    }
    if (p < sequenceIs.length && p < destAl.getHeight())
    {
      return destAl.getSequenceAt(is[p]);
    }
    return null;
  }

  /**
   * 
   * @return true if the run method is safe to call
   */
  public boolean isValid()
  {
    ArrayList<String> _warnings = new ArrayList<String>();
    boolean validt = true;
    if (jobs != null)
    {
      for (RestJob rj : (RestJob[]) jobs)
      {
        if (!rj.hasValidInput())
        {
          // invalid input for this job
          System.err.println("Job " + rj.getJobnum()
                  + " has invalid input. ( " + rj.getStatus() + ")");
          if (rj.hasStatus() && !_warnings.contains(rj.getStatus()))
          {
            _warnings.add(rj.getStatus());
          }
          validt = false;
        }
      }
    }
    if (!validt)
    {
      warnings = "";
      for (String st : _warnings)
      {
        if (warnings.length() > 0)
        {
          warnings += "\n";
        }
        warnings += st;

      }
    }
    return validt;
  }

  protected String warnings;

  public boolean hasWarnings()
  {
    // TODO Auto-generated method stub
    return warnings != null && warnings.length() > 0;
  }

  /**
   * get any informative messages about why the job thread couldn't start.
   * 
   * @return
   */
  public String getWarnings()
  {
    return isValid() ? "Job can be started. No warnings."
            : hasWarnings() ? warnings : "";
  }

}
