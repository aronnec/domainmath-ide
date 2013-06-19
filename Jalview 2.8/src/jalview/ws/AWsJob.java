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

/**
 * Generic properties for an individual job within a Web Service Client thread.
 * Derived from jalview web services version 1 statuses, and revised for Jws2.
 */

public abstract class AWsJob
{
  protected int jobnum = 0;

  protected String jobId;

  /**
   * @param jobId
   *          the jobId to set
   */
  public void setJobId(String jobId)
  {
    this.jobId = jobId;
  }

  /**
   * has job been cancelled
   */
  protected boolean cancelled = false;

  /**
   * number of exceptions left before job dies
   */
  int allowedServerExceptions = 3;

  /**
   * @param allowedServerExceptions
   *          the allowedServerExceptions to set
   */
  public void setAllowedServerExceptions(int allowedServerExceptions)
  {
    this.allowedServerExceptions = allowedServerExceptions;
  }

  /**
   * has job been submitted to server ? if false, then no state info is
   * available.
   */
  protected boolean submitted = false;

  /**
   * @param jobnum
   *          the jobnum to set
   */
  public void setJobnum(int jobnum)
  {
    this.jobnum = jobnum;
  }

  /**
   * @param submitted
   *          the submitted to set
   */
  public void setSubmitted(boolean submitted)
  {
    this.submitted = submitted;
  }

  /**
   * @param subjobComplete
   *          the subjobComplete to set
   */
  public void setSubjobComplete(boolean subjobComplete)
  {
    this.subjobComplete = subjobComplete;
  }

  /**
   * @return the jobnum
   */
  public int getJobnum()
  {
    return jobnum;
  }

  /**
   * @return the jobId
   */
  public String getJobId()
  {
    return jobId;
  }

  /**
   * @return the cancelled
   */
  public boolean isCancelled()
  {
    return cancelled;
  }

  /**
   * @return the allowedServerExceptions
   */
  public int getAllowedServerExceptions()
  {
    return allowedServerExceptions;
  }

  /**
   * @return the submitted
   */
  public boolean isSubmitted()
  {
    return submitted;
  }

  /**
   * @return the subjobComplete
   */
  public boolean isSubjobComplete()
  {
    return subjobComplete;
  }

  /**
   * are all sub-jobs complete
   */
  protected boolean subjobComplete = false;

  public AWsJob()
  {
  }

  /**
   * 
   * @return true if job has completed and valid results are available
   */
  abstract public boolean hasResults();

  /**
   * 
   * @return boolean true if job can be submitted.
   */
  public abstract boolean hasValidInput();

  /**
   * 
   * @return true if job is running
   */
  abstract public boolean isRunning();

  /**
   * 
   * @return true if job is queued
   */
  abstract public boolean isQueued();

  /**
   * 
   * @return true if job has finished
   */
  abstract public boolean isFinished();

  /**
   * 
   * @return true if the job failed due to some problem with the input data or
   *         parameters.
   */
  abstract public boolean isFailed();

  /**
   * 
   * @return true if job failed due to an unhandled technical issue
   */
  abstract public boolean isBroken();

  /**
   * 
   * @return true if there was a problem contacting the server.
   */
  abstract public boolean isServerError();

  /**
   * 
   * @return true if the job has status text.
   */
  abstract public boolean hasStatus();

  /**
   * 
   * @return status text for job to be displayed to user.
   */
  abstract public String getStatus();

  abstract public boolean hasResponse();

  abstract public void clearResponse();

  abstract public String getState();

  /**
   * generates response using the abstract service flags.
   * 
   * @return a standard state response
   */
  protected String _defaultState()
  {

    String state = "";
    return state;
  }
}
