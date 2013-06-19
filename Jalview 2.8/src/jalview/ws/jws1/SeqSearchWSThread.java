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
package jalview.ws.jws1;

import java.util.*;

import jalview.analysis.*;
import jalview.bin.*;
import jalview.datamodel.*;
import jalview.gui.*;
import jalview.io.NewickFile;
import jalview.ws.AWsJob;
import jalview.ws.JobStateSummary;
import jalview.ws.WSClientI;
import vamsas.objects.simple.MsaResult;
import vamsas.objects.simple.SeqSearchResult;

class SeqSearchWSThread extends JWS1Thread implements WSClientI
{
  String dbs = null;

  boolean profile = false;

  class SeqSearchWSJob extends WSJob
  {
    // hold special input for this
    vamsas.objects.simple.SequenceSet seqs = new vamsas.objects.simple.SequenceSet();

    /**
     * MsaWSJob
     * 
     * @param jobNum
     *          int
     * @param jobId
     *          String
     */
    public SeqSearchWSJob(int jobNum, SequenceI[] inSeqs)
    {
      this.jobnum = jobNum;
      if (!prepareInput(inSeqs, 2))
      {
        submitted = true;
        subjobComplete = true;
        result = new MsaResult();
        result.setFinished(true);
        result.setStatus("Job never ran - input returned to user.");
      }

    }

    Hashtable SeqNames = new Hashtable();

    Vector emptySeqs = new Vector();

    /**
     * prepare input sequences for service
     * 
     * @param seqs
     *          jalview sequences to be prepared
     * @param minlen
     *          minimum number of residues required for this MsaWS service
     * @return true if seqs contains sequences to be submitted to service.
     */
    private boolean prepareInput(SequenceI[] seqs, int minlen)
    {
      int nseqs = 0;
      if (minlen < 0)
      {
        throw new Error(
                "Implementation error: minlen must be zero or more.");
      }
      for (int i = 0; i < seqs.length; i++)
      {
        if (seqs[i].getEnd() - seqs[i].getStart() > minlen - 1)
        {
          nseqs++;
        }
      }
      boolean valid = nseqs >= 1; // need at least one sequence for valid input
      // TODO: generalise
      vamsas.objects.simple.Sequence[] seqarray = (valid) ? new vamsas.objects.simple.Sequence[nseqs]
              : null;
      boolean submitGaps = (nseqs == 1) ? false : true; // profile is submitted
      // with gaps
      for (int i = 0, n = 0; i < seqs.length; i++)
      {

        String newname = jalview.analysis.SeqsetUtils.unique_name(i); // same
        // for
        // any
        // subjob
        SeqNames.put(newname,
                jalview.analysis.SeqsetUtils.SeqCharacterHash(seqs[i]));
        if (valid && seqs[i].getEnd() - seqs[i].getStart() > minlen - 1)
        {
          seqarray[n] = new vamsas.objects.simple.Sequence();
          seqarray[n].setId(newname);
          seqarray[n++].setSeq((submitGaps) ? seqs[i].getSequenceAsString()
                  : AlignSeq.extractGaps(jalview.util.Comparison.GapChars,
                          seqs[i].getSequenceAsString()));
        }
        else
        {
          String empty = null;
          if (seqs[i].getEnd() >= seqs[i].getStart())
          {
            empty = (submitGaps) ? seqs[i].getSequenceAsString() : AlignSeq
                    .extractGaps(jalview.util.Comparison.GapChars,
                            seqs[i].getSequenceAsString());
          }
          emptySeqs.add(new String[]
          { newname, empty });
        }
      }
      if (submitGaps)
      {
        // almost certainly have to remove gapped columns here
      }
      this.seqs = new vamsas.objects.simple.SequenceSet();
      this.seqs.setSeqs(seqarray);
      return valid;
    }

    /**
     * 
     * @return true if getAlignment will return a valid alignment result.
     */
    public boolean hasResults()
    {
      if (subjobComplete
              && result != null
              && result.isFinished()
              && ((SeqSearchResult) result).getAlignment() != null
              && ((SeqSearchResult) result).getAlignment().getSeqs() != null)
      {
        return true;
      }
      return false;
    }

    /**
     * return sequence search results for display
     * 
     * @return null or { Alignment(+features and annotation), NewickFile)}
     */
    public Object[] getAlignment(Alignment dataset, Hashtable featureColours)
    {

      if (result != null && result.isFinished())
      {
        SequenceI[] alseqs = null;
        // char alseq_gapchar = '-';
        // int alseq_l = 0;
        if (((SeqSearchResult) result).getAlignment() != null)
        {
          alseqs = getVamsasAlignment(((SeqSearchResult) result)
                  .getAlignment());
          // alseq_gapchar = ( (SeqSearchResult)
          // result).getAlignment().getGapchar().charAt(0);
          // alseq_l = alseqs.length;
        }
        /**
         * what has to be done. 1 - annotate returned alignment with annotation
         * file and sequence features file, and associate any tree-nodes. 2.
         * connect alignment back to any associated dataset: 2.a. deuniquify
         * recovers sequence information - but additionally, relocations must be
         * made from the returned aligned sequence back to the dataset.
         */
        // construct annotated alignment as it would be done by the jalview
        // applet
        jalview.datamodel.Alignment al = new Alignment(alseqs);
        // al.setDataset(dataset);
        // make dataset
        String inFile = null;
        try
        {
          inFile = ((SeqSearchResult) result).getAnnotation();
          if (inFile != null && inFile.length() > 0)
          {
            new jalview.io.AnnotationFile().readAnnotationFile(al, inFile,
                    jalview.io.AppletFormatAdapter.PASTE);
          }
        } catch (Exception e)
        {
          System.err
                  .println("Failed to parse the annotation file associated with the alignment.");
          System.err.println(">>>EOF" + inFile + "\n<<<EOF\n");
          e.printStackTrace(System.err);
        }

        try
        {
          inFile = ((SeqSearchResult) result).getFeatures();
          if (inFile != null && inFile.length() > 0)
          {
            jalview.io.FeaturesFile ff = new jalview.io.FeaturesFile(
                    inFile, jalview.io.AppletFormatAdapter.PASTE);
            ff.parse(al, featureColours, false);
          }
        } catch (Exception e)
        {
          System.err
                  .println("Failed to parse the Features file associated with the alignment.");
          System.err.println(">>>EOF" + inFile + "\n<<<EOF\n");
          e.printStackTrace(System.err);
        }
        jalview.io.NewickFile nf = null;
        try
        {
          inFile = ((SeqSearchResult) result).getNewickTree();
          if (inFile != null && inFile.length() > 0)
          {
            nf = new jalview.io.NewickFile(inFile,
                    jalview.io.AppletFormatAdapter.PASTE);
            if (!nf.isValid())
            {
              nf.close();
              nf = null;
            }
          }
        } catch (Exception e)
        {
          System.err
                  .println("Failed to parse the treeFile associated with the alignment.");
          System.err.println(">>>EOF" + inFile + "\n<<<EOF\n");
          e.printStackTrace(System.err);
        }

        /*
         * TODO: housekeeping w.r.t. recovery of dataset and annotation
         * references for input sequences, and then dataset sequence creation
         * for new sequences retrieved from service // finally, attempt to
         * de-uniquify to recover input sequence identity, and try to map back
         * onto dataset Note: this
         * jalview.analysis.SeqsetUtils.deuniquify(SeqNames, alseqs, true); will
         * NOT WORK - the returned alignment may contain multiple versions of
         * the input sequence, each being a subsequence of the original.
         * deuniquify also removes existing annotation and features added in the
         * previous step... al.setDataset(dataset); // add in new sequences
         * retrieved from sequence search which are not already in dataset. //
         * trigger a 'fetchDBids' to annotate sequences with database ids...
         */

        return new Object[]
        { al, nf };
      }
      return null;
    }

    /**
     * mark subjob as cancelled and set result object appropriatly
     */
    void cancel()
    {
      cancelled = true;
      subjobComplete = true;
      result = null;
    }

    /**
     * 
     * @return boolean true if job can be submitted.
     */
    public boolean hasValidInput()
    {
      if (seqs.getSeqs() != null)
      {
        return true;
      }
      return false;
    }
  }

  String alTitle; // name which will be used to form new alignment window.

  Alignment dataset; // dataset to which the new alignment will be

  // associated.

  ext.vamsas.SeqSearchI server = null;

  private String dbArg;

  /**
   * set basic options for this (group) of Msa jobs
   * 
   * @param subgaps
   *          boolean
   * @param presorder
   *          boolean
   */
  SeqSearchWSThread(ext.vamsas.SeqSearchI server, String wsUrl,
          WebserviceInfo wsinfo, jalview.gui.AlignFrame alFrame,
          AlignmentView alview, String wsname, String db)
  {
    super(alFrame, wsinfo, alview, wsname, wsUrl);
    this.server = server;
    this.dbArg = db;
  }

  /**
   * create one or more Msa jobs to align visible seuqences in _msa
   * 
   * @param title
   *          String
   * @param _msa
   *          AlignmentView
   * @param subgaps
   *          boolean
   * @param presorder
   *          boolean
   * @param seqset
   *          Alignment
   */
  SeqSearchWSThread(ext.vamsas.SeqSearchI server, String wsUrl,
          WebserviceInfo wsinfo, jalview.gui.AlignFrame alFrame,
          String wsname, String title, AlignmentView _msa, String db,
          Alignment seqset)
  {
    this(server, wsUrl, wsinfo, alFrame, _msa, wsname, db);
    OutputHeader = wsInfo.getProgressText();
    alTitle = title;
    dataset = seqset;

    SequenceI[][] conmsa = _msa.getVisibleContigs('-');
    if (conmsa != null)
    {
      int njobs = conmsa.length;
      jobs = new SeqSearchWSJob[njobs];
      for (int j = 0; j < njobs; j++)
      {
        if (j != 0)
        {
          jobs[j] = new SeqSearchWSJob(wsinfo.addJobPane(), conmsa[j]);
        }
        else
        {
          jobs[j] = new SeqSearchWSJob(0, conmsa[j]);
        }
        if (njobs > 0)
        {
          wsinfo.setProgressName("region " + jobs[j].getJobnum(),
                  jobs[j].getJobnum());
        }
        wsinfo.setProgressText(jobs[j].getJobnum(), OutputHeader);
      }
    }
  }

  public boolean isCancellable()
  {
    return true;
  }

  public void cancelJob()
  {
    if (!jobComplete && jobs != null)
    {
      boolean cancelled = true;
      for (int job = 0; job < jobs.length; job++)
      {
        if (jobs[job].isSubmitted() && !jobs[job].isSubjobComplete())
        {
          String cancelledMessage = "";
          try
          {
            vamsas.objects.simple.WsJobId cancelledJob = server
                    .cancel(jobs[job].getJobId());
            if (cancelledJob.getStatus() == 2)
            {
              // CANCELLED_JOB
              cancelledMessage = "Job cancelled.";
              ((SeqSearchWSJob) jobs[job]).cancel();
              wsInfo.setStatus(jobs[job].getJobnum(),
                      WebserviceInfo.STATE_CANCELLED_OK);
            }
            else if (cancelledJob.getStatus() == 3)
            {
              // VALID UNSTOPPABLE JOB
              cancelledMessage += "Server cannot cancel this job. just close the window.\n";
              cancelled = false;
              // wsInfo.setStatus(jobs[job].jobnum,
              // WebserviceInfo.STATE_RUNNING);
            }

            if (cancelledJob.getJobId() != null)
            {
              cancelledMessage += ("[" + cancelledJob.getJobId() + "]");
            }

            cancelledMessage += "\n";
          } catch (Exception exc)
          {
            cancelledMessage += ("\nProblems cancelling the job : Exception received...\n"
                    + exc + "\n");
            Cache.log.warn(
                    "Exception whilst cancelling " + jobs[job].getJobId(),
                    exc);
          }
          wsInfo.setProgressText(jobs[job].getJobnum(), OutputHeader
                  + cancelledMessage + "\n");
        }
      }
      if (cancelled)
      {
        wsInfo.setStatus(WebserviceInfo.STATE_CANCELLED_OK);
        jobComplete = true;
      }
      this.interrupt(); // kick thread to update job states.
    }
    else
    {
      if (!jobComplete)
      {
        wsInfo.setProgressText(OutputHeader
                + "Server cannot cancel this job because it has not been submitted properly. just close the window.\n");
      }
    }
  }

  public void pollJob(AWsJob job) throws Exception
  {
    ((SeqSearchWSJob) job).result = server.getResult(((SeqSearchWSJob) job)
            .getJobId());
  }

  public void StartJob(AWsJob job)
  {
    if (!(job instanceof SeqSearchWSJob))
    {
      throw new Error("StartJob(MsaWSJob) called on a WSJobInstance "
              + job.getClass());
    }
    SeqSearchWSJob j = (SeqSearchWSJob) job;
    if (j.isSubmitted())
    {
      if (Cache.log.isDebugEnabled())
      {
        Cache.log.debug("Tried to submit an already submitted job "
                + j.getJobId());
      }
      return;
    }
    if (j.seqs.getSeqs() == null)
    {
      // special case - selection consisted entirely of empty sequences...
      j.setSubmitted(true);
      j.result = new MsaResult();
      j.result.setFinished(true);
      j.result.setStatus("Empty Alignment Job");
      ((MsaResult) j.result).setMsa(null);
    }
    try
    {
      vamsas.objects.simple.WsJobId jobsubmit = server.search(
              j.seqs.getSeqs()[0], dbArg);

      if ((jobsubmit != null) && (jobsubmit.getStatus() == 1))
      {
        j.setJobId(jobsubmit.getJobId());
        j.setSubmitted(true);
        j.setSubjobComplete(false);
        // System.out.println(WsURL + " Job Id '" + jobId + "'");
      }
      else
      {
        if (jobsubmit == null)
        {
          throw new Exception(
                  "Server at "
                          + WsUrl
                          + " returned null object, it probably cannot be contacted. Try again later ?");
        }

        throw new Exception(jobsubmit.getJobId());
      }
    } catch (Exception e)
    {
      // TODO: JBPNote catch timeout or other fault types explicitly
      // For unexpected errors
      System.err
              .println(WebServiceName
                      + "Client: Failed to submit the sequences for alignment (probably a server side problem)\n"
                      + "When contacting Server:" + WsUrl + "\n"
                      + e.toString() + "\n");
      j.setAllowedServerExceptions(0);
      wsInfo.setStatus(WebserviceInfo.STATE_STOPPED_SERVERERROR);
      wsInfo.setStatus(j.getJobnum(),
              WebserviceInfo.STATE_STOPPED_SERVERERROR);
      wsInfo.appendProgressText(
              j.getJobnum(),
              "Failed to submit sequences for alignment.\n"
                      + "It is most likely that there is a problem with the server.\n"
                      + "Just close the window\n");

      // e.printStackTrace(); // TODO: JBPNote DEBUG
    }
  }

  private jalview.datamodel.Sequence[] getVamsasAlignment(
          vamsas.objects.simple.Alignment valign)
  {
    vamsas.objects.simple.Sequence[] seqs = valign.getSeqs().getSeqs();
    jalview.datamodel.Sequence[] msa = new jalview.datamodel.Sequence[seqs.length];

    for (int i = 0, j = seqs.length; i < j; i++)
    {
      msa[i] = new jalview.datamodel.Sequence(seqs[i].getId(),
              seqs[i].getSeq());
    }

    return msa;
  }

  public void parseResult()
  {
    int results = 0; // number of result sets received
    JobStateSummary finalState = new JobStateSummary();
    try
    {
      for (int j = 0; j < jobs.length; j++)
      {
        finalState.updateJobPanelState(wsInfo, OutputHeader, jobs[j]);
        if (jobs[j].isSubmitted() && jobs[j].isSubjobComplete()
                && jobs[j].hasResults())
        {
          results++;
          vamsas.objects.simple.Alignment valign = ((SeqSearchResult) ((SeqSearchWSJob) jobs[j]).result)
                  .getAlignment();
          if (valign != null)
          {
            wsInfo.appendProgressText(jobs[j].getJobnum(),
                    "\nAlignment Object Method Notes\n");
            String[] lines = valign.getMethod();
            for (int line = 0; line < lines.length; line++)
            {
              wsInfo.appendProgressText(jobs[j].getJobnum(), lines[line]
                      + "\n");
            }
            // JBPNote The returned files from a webservice could be
            // hidden behind icons in the monitor window that,
            // when clicked, pop up their corresponding data
          }
        }
      }
    } catch (Exception ex)
    {

      Cache.log.error("Unexpected exception when processing results for "
              + alTitle, ex);
      wsInfo.setStatus(WebserviceInfo.STATE_STOPPED_ERROR);
    }
    if (results > 0)
    {
      wsInfo.showResultsNewFrame
              .addActionListener(new java.awt.event.ActionListener()
              {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                  displayResults(true);
                }
              });
      wsInfo.mergeResults
              .addActionListener(new java.awt.event.ActionListener()
              {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                  displayResults(false);
                }
              });
      wsInfo.setResultsReady();
    }
    else
    {
      wsInfo.setFinishedNoResults();
    }
  }

  void displayResults(boolean newFrame)
  {
    if (!newFrame)
    {
      System.err.println("MERGE WITH OLD FRAME NOT IMPLEMENTED");
      return;
    }
    // each subjob is an independent alignment for the moment
    // Alignment al[] = new Alignment[jobs.length];
    // NewickFile nf[] = new NewickFile[jobs.length];
    for (int j = 0; j < jobs.length; j++)
    {
      Hashtable featureColours = new Hashtable();
      Alignment al = null;
      NewickFile nf = null;
      if (jobs[j].hasResults())
      {
        Object[] res = ((SeqSearchWSJob) jobs[j]).getAlignment(dataset,
                featureColours);
        if (res == null)
        {
          continue;
        }
        ;
        al = (Alignment) res[0];
        nf = (NewickFile) res[1];
      }
      else
      {
        al = null;
        nf = null;
        continue;
      }
      /*
       * We can't map new alignment back with insertions from input's hidden
       * regions until dataset mapping is sorted out... but basically it goes
       * like this: 1. Merge each domain hit back onto the visible segments in
       * the same way as a Jnet prediction is mapped back
       * 
       * Object[] newview = input.getUpdatedView(results, orders, getGapChar());
       * // trash references to original result data for (int j = 0; j <
       * jobs.length; j++) { results[j] = null; orders[j] = null; } SequenceI[]
       * alignment = (SequenceI[]) newview[0]; ColumnSelection columnselection =
       * (ColumnSelection) newview[1]; Alignment al = new Alignment(alignment);
       * 
       * if (dataset != null) { al.setDataset(dataset); }
       * 
       * propagateDatasetMappings(al); }
       */

      AlignFrame af = new AlignFrame(al,// columnselection,
              AlignFrame.DEFAULT_WIDTH, AlignFrame.DEFAULT_HEIGHT);
      if (nf != null)
      {
        af.ShowNewickTree(nf, "Tree from " + this.alTitle);
      }
      // initialise with same renderer settings as in parent alignframe.
      af.getFeatureRenderer().transferSettings(this.featureSettings);
      Desktop.addInternalFrame(af, alTitle, AlignFrame.DEFAULT_WIDTH,
              AlignFrame.DEFAULT_HEIGHT);
    }
  }

  public boolean canMergeResults()
  {
    return false;
  }
}
