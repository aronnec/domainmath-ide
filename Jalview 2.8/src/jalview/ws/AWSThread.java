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
package jalview.ws;

import jalview.bin.Cache;
import jalview.datamodel.AlignedCodonFrame;
import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.AlignmentView;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.gui.WebserviceInfo;
import jalview.gui.FeatureRenderer.FeatureRendererSettings;

public abstract class AWSThread extends Thread
{

  /**
   * view that this job was associated with
   */
  protected AlignmentI currentView = null;

  /**
   * feature settings from view that job was associated with
   */
  protected FeatureRendererSettings featureSettings = null;

  /**
   * metadata about this web service
   */
  protected WebserviceInfo wsInfo = null;

  /**
   * original input data for this job
   */
  protected AlignmentView input = null;

  /**
   * dataset sequence relationships to be propagated onto new results
   */
  protected AlignedCodonFrame[] codonframe = null;

  /**
   * are there jobs still running in this thread.
   */
  protected boolean jobComplete = false;

  /**
   * one or more jobs being managed by this thread.
   */
  protected AWsJob jobs[] = null;

  /**
   * full name of service
   */
  protected String WebServiceName = null;

  protected char defGapChar = '-';

  /**
   * header prepended to all output from job
   */
  protected String OutputHeader;

  /**
   * only used when reporting a web service out of memory error - the job ID
   * will be concatenated to the URL
   */
  protected String WsUrl = null;

  /**
   * generic web service job/subjob poll loop
   */
  public void run()
  {
    JobStateSummary jstate = null;
    if (jobs == null)
    {
      jobComplete = true;
    }
    while (!jobComplete)
    {
      jstate = new JobStateSummary();
      for (int j = 0; j < jobs.length; j++)
      {

        if (!jobs[j].submitted && jobs[j].hasValidInput())
        {
          StartJob(jobs[j]);
        }

        if (jobs[j].submitted && !jobs[j].subjobComplete)
        {
          try
          {
            pollJob(jobs[j]);
            if (!jobs[j].hasResponse())
            {
              throw (new Exception(
                      "Timed out when communicating with server\nTry again later.\n"));
            }
            jalview.bin.Cache.log.debug("Job " + j + " Result state "
                    + jobs[j].getState() + "(ServerError="
                    + jobs[j].isServerError() + ")");
          } catch (Exception ex)
          {
            // Deal with Transaction exceptions
            wsInfo.appendProgressText(jobs[j].jobnum, "\n" + WebServiceName
                    + " Server exception!\n" + ex.getMessage());
            // always output the exception's stack trace to the log
            Cache.log.warn(WebServiceName + " job(" + jobs[j].jobnum
                    + ") Server exception.");
            // todo: could limit trace to cause if this is a SOAPFaultException.
            ex.printStackTrace();

            if (jobs[j].allowedServerExceptions > 0)
            {
              jobs[j].allowedServerExceptions--;
              Cache.log.debug("Sleeping after a server exception.");
              try
              {
                Thread.sleep(5000);
              } catch (InterruptedException ex1)
              {
              }
            }
            else
            {
              Cache.log.warn("Dropping job " + j + " " + jobs[j].jobId);
              jobs[j].subjobComplete = true;
              wsInfo.setStatus(jobs[j].jobnum,
                      WebserviceInfo.STATE_STOPPED_SERVERERROR);
            }
          } catch (OutOfMemoryError er)
          {
            jobComplete = true;
            jobs[j].subjobComplete = true;
            jobs[j].clearResponse(); // may contain out of date result data
            wsInfo.setStatus(jobs[j].jobnum,
                    WebserviceInfo.STATE_STOPPED_ERROR);
            Cache.log.error("Out of memory when retrieving Job " + j
                    + " id:" + WsUrl + "/" + jobs[j].jobId, er);
            new jalview.gui.OOMWarning("retrieving result for "
                    + WebServiceName, er);
            System.gc();
          }
        }
        jstate.updateJobPanelState(wsInfo, OutputHeader, jobs[j]);
      }
      // Decide on overall state based on collected jobs[] states
      updateGlobalStatus(jstate);
      if (!jobComplete)
      {
        try
        {
          Thread.sleep(5000);
        } catch (InterruptedException e)
        {
          Cache.log
                  .debug("Interrupted sleep waiting for next job poll.", e);
        }
        // System.out.println("I'm alive "+alTitle);
      }
    }
    if (jobComplete && jobs != null)
    {
      parseResult(); // tidy up and make results available to user
    }
    else
    {
      Cache.log
              .debug("WebServiceJob poll loop finished with no jobs created.");
      wsInfo.setStatus(wsInfo.STATE_STOPPED_ERROR);
      wsInfo.appendProgressText("No jobs ran.");
      wsInfo.setFinishedNoResults();
    }
  }

  protected void updateGlobalStatus(JobStateSummary jstate)
  {
    if (jstate.running > 0)
    {
      wsInfo.setStatus(WebserviceInfo.STATE_RUNNING);
    }
    else if (jstate.queuing > 0)
    {
      wsInfo.setStatus(WebserviceInfo.STATE_QUEUING);
    }
    else
    {
      jobComplete = true;
      if (jstate.finished > 0)
      {
        wsInfo.setStatus(WebserviceInfo.STATE_STOPPED_OK);
      }
      else if (jstate.error > 0)
      {
        wsInfo.setStatus(WebserviceInfo.STATE_STOPPED_ERROR);
      }
      else if (jstate.serror > 0)
      {
        wsInfo.setStatus(WebserviceInfo.STATE_STOPPED_SERVERERROR);
      }
    }
  }

  public AWSThread()
  {
    super();
  }

  public AWSThread(Runnable target)
  {
    super(target);
  }

  public AWSThread(String name)
  {
    super(name);
  }

  public AWSThread(ThreadGroup group, Runnable target)
  {
    super(group, target);
  }

  public AWSThread(ThreadGroup group, String name)
  {
    super(group, name);
  }

  public AWSThread(Runnable target, String name)
  {
    super(target, name);
  }

  public AWSThread(ThreadGroup group, Runnable target, String name)
  {
    super(group, target, name);
  }

  /**
   * query web service for status of job. on return, job.result must not be null
   * - if it is then it will be assumed that the job status query timed out and
   * a server exception will be logged.
   * 
   * @param job
   * @throws Exception
   *           will be logged as a server exception for this job
   */
  public abstract void pollJob(AWsJob job) throws Exception;

  /**
   * submit job to web service
   * 
   * @param job
   */
  public abstract void StartJob(AWsJob job);

  /**
   * process the set of AWsJob objects into a set of results, and tidy up.
   */
  public abstract void parseResult();

  /**
   * helper function to conserve dataset references to sequence objects returned
   * from web services 1. Propagates AlCodonFrame data from
   * <code>codonframe</code> to <code>al</code> TODO: refactor to datamodel
   * 
   * @param al
   */
  public void propagateDatasetMappings(Alignment al)
  {
    if (codonframe != null)
    {
      SequenceI[] alignment = al.getSequencesArray();
      for (int sq = 0; sq < alignment.length; sq++)
      {
        for (int i = 0; i < codonframe.length; i++)
        {
          if (codonframe[i] != null
                  && codonframe[i].involvesSequence(alignment[sq]))
          {
            al.addCodonFrame(codonframe[i]);
            codonframe[i] = null;
            break;
          }
        }
      }
    }
  }

  public AWSThread(ThreadGroup group, Runnable target, String name,
          long stackSize)
  {
    super(group, target, name, stackSize);
  }

  /**
   * 
   * @return gap character to use for any alignment generation
   */
  public char getGapChar()
  {
    return defGapChar;
  }

  /**
   * 
   * @param alignFrame
   *          reference for copying mappings across
   * @param wsInfo
   *          gui attachment point
   * @param input
   *          input data for the calculation
   * @param webServiceName
   *          name of service
   * @param wsUrl
   *          url of the service being invoked
   */
  public AWSThread(AlignFrame alignFrame, WebserviceInfo wsinfo,
          AlignmentView input, String webServiceName, String wsUrl)
  {
    this(alignFrame, wsinfo, input, wsUrl);
    WebServiceName = webServiceName;
  }

  /**
   * Extracts additional info from alignment view's context.
   * 
   * @param alframe
   *          - reference for copying mappings and display styles across
   * @param wsinfo2
   *          - gui attachment point - may be null
   * @param alview
   *          - input data for the calculation
   * @param wsurl2
   *          - url of the service being invoked
   */
  public AWSThread(AlignFrame alframe, WebserviceInfo wsinfo2,
          AlignmentView alview, String wsurl2)
  {
    super();
    // this.alignFrame = alframe;
    currentView = alframe.getCurrentView().getAlignment();
    featureSettings = alframe.getFeatureRenderer().getSettings();
    defGapChar = alframe.getViewport().getGapCharacter();
    this.wsInfo = wsinfo2;
    this.input = alview;
    WsUrl = wsurl2;
    if (alframe != null)
    {
      AlignedCodonFrame[] cf = alframe.getViewport().getAlignment()
              .getCodonFrames();
      if (cf != null)
      {
        codonframe = new AlignedCodonFrame[cf.length];
        System.arraycopy(cf, 0, codonframe, 0, cf.length);
      }
    }
  }
}
