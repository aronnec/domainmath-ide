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

import jalview.gui.WebserviceInfo;

/**
 * bookkeeper class for the WebServiceInfo GUI, maintaining records of web
 * service jobs handled by the window and reflecting any status updates.
 * 
 * @author JimP
 * 
 */
public class JobStateSummary
{
  /**
   * number of jobs running
   */
  int running = 0;

  /**
   * number of jobs queued
   */
  int queuing = 0;

  /**
   * number of jobs finished
   */
  int finished = 0;

  /**
   * number of jobs failed
   */
  int error = 0;

  /**
   * number of jobs stopped due to server error
   */
  int serror = 0;

  /**
   * number of jobs cancelled
   */
  int cancelled = 0;

  /**
   * number of jobs finished with results
   */
  int results = 0;

  /**
   * processes an AWSJob's status and updates job status counters and WebService
   * status displays
   * 
   * @param wsInfo
   * @param OutputHeader
   * @param j
   */
  public void updateJobPanelState(WebserviceInfo wsInfo,
          String OutputHeader, AWsJob j)
  {
    if (j.submitted)
    {
      String progheader = "";
      // Parse state of job[j]
      if (j.isRunning())
      {
        running++;
        wsInfo.setStatus(j.jobnum, WebserviceInfo.STATE_RUNNING);
      }
      else if (j.isQueued())
      {
        queuing++;
        wsInfo.setStatus(j.jobnum, WebserviceInfo.STATE_QUEUING);
      }
      else if (j.isFinished())
      {
        finished++;
        j.subjobComplete = true;
        if (j.hasResults())
        {
          results++;
        }
        wsInfo.setStatus(j.jobnum, WebserviceInfo.STATE_STOPPED_OK);
      }
      else if (j.isFailed())
      {
        progheader += "Job failed.\n";
        j.subjobComplete = true;
        wsInfo.setStatus(j.jobnum, WebserviceInfo.STATE_STOPPED_ERROR);
        error++;
      }
      else if (j.isServerError())
      {
        serror++;
        j.subjobComplete = true;
        wsInfo.setStatus(j.jobnum, WebserviceInfo.STATE_STOPPED_SERVERERROR);
      }
      else if (j.isBroken())
      {
        progheader += "Job was broken.\n";
        error++;
        j.subjobComplete = true;
        wsInfo.setStatus(j.jobnum, WebserviceInfo.STATE_STOPPED_ERROR);
      }
      // and pass on any sub-job messages to the user
      StringBuffer output = new StringBuffer();
      if (OutputHeader != null)
      {

        output.append(OutputHeader);
      }
      if (progheader != null)
      {
        output.append(progheader);
      }
      if (j.hasStatus())
      {
        // Could try to squash OOMs here, but it usually doesn't help - there
        // probably won't be
        // enough memory to handle the results later on anyway.
        // try {
        String stat = j.getStatus();
        if (stat != null)
        {
          output.append(stat);
        }
        // } catch (OutOfMemoryError e)
        // {
        // System.err.println("Out of memory when displaying status. Squashing error.");
        // wsInfo.appendProgressText(j.jobnum,
        // "..\n(Out of memory when displaying status)\n");
        // }
      }
      wsInfo.setProgressText(j.jobnum, output.toString());
    }
    else
    {
      if (j.submitted && j.subjobComplete)
      {
        if (j.allowedServerExceptions == 0)
        {
          serror++;
        }
        else if (!j.hasResults())
        {
          error++;
        }
      }
    }
  }
}
