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

import jalview.ws.AWsJob;

abstract class WSJob extends AWsJob
{
  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.AWsJob#clearResponse()
   */
  @Override
  public void clearResponse()
  {
    result = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.AWsJob#hasResponse()
   */
  @Override
  public boolean hasResponse()
  {
    return result != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.AWsJob#hasStatus()
   */
  @Override
  public boolean hasStatus()
  {
    return result != null && result.getStatus() != null;
  }

  /**
   * The last result object returned by the service.
   */
  vamsas.objects.simple.Result result;

  /**
   * @return
   * @see vamsas.objects.simple.Result#getStatus()
   */
  public String getStatus()
  {
    return result == null ? null : result.getStatus();
  }

  public String getState()
  {
    return result == null ? "NULL result" : "" + result.getState();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isBroken()
   */
  public boolean isBroken()
  {
    return result != null && result.isBroken();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isFailed()
   */
  public boolean isFailed()
  {
    return result != null && result.isFailed();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isFinished()
   */
  public boolean isFinished()
  {
    return result != null && result.isFinished();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isInvalid()
   */
  public boolean isInvalid()
  {
    return result != null && result.isInvalid();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isJobFailed()
   */
  public boolean isJobFailed()
  {
    return result != null && result.isJobFailed();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isQueued()
   */
  public boolean isQueued()
  {
    return result != null && result.isQueued();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isRunning()
   */
  public boolean isRunning()
  {
    return result != null && result.isRunning();
  }

  /**
   * @return
   * @see vamsas.objects.simple.Result#isServerError()
   */
  public boolean isServerError()
  {
    return result != null && result.isServerError();
  }
}
