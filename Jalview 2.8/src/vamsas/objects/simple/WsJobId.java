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
package vamsas.objects.simple;

public class WsJobId implements java.io.Serializable
{
  private java.lang.String jobId;

  private int status;

  public WsJobId()
  {
  }

  public WsJobId(java.lang.String jobId, int status)
  {
    this.jobId = jobId;
    this.status = status;
  }

  /**
   * Gets the jobId value for this WsJobId.
   * 
   * @return jobId
   */
  public java.lang.String getJobId()
  {
    return jobId;
  }

  /**
   * Sets the jobId value for this WsJobId.
   * 
   * @param jobId
   */
  public void setJobId(java.lang.String jobId)
  {
    this.jobId = jobId;
  }

  /**
   * Gets the status value for this WsJobId.
   * 
   * @return status
   */
  public int getStatus()
  {
    return status;
  }

  /**
   * Sets the status value for this WsJobId.
   * 
   * @param status
   */
  public void setStatus(int status)
  {
    this.status = status;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj)
  {
    if (!(obj instanceof WsJobId))
      return false;
    WsJobId other = (WsJobId) obj;
    if (obj == null)
      return false;
    if (this == obj)
      return true;
    if (__equalsCalc != null)
    {
      return (__equalsCalc == obj);
    }
    __equalsCalc = obj;
    boolean _equals;
    _equals = true
            && ((this.jobId == null && other.getJobId() == null) || (this.jobId != null && this.jobId
                    .equals(other.getJobId())))
            && this.status == other.getStatus();
    __equalsCalc = null;
    return _equals;
  }

  private boolean __hashCodeCalc = false;

  public synchronized int hashCode()
  {
    if (__hashCodeCalc)
    {
      return 0;
    }
    __hashCodeCalc = true;
    int _hashCode = 1;
    if (getJobId() != null)
    {
      _hashCode += getJobId().hashCode();
    }
    _hashCode += getStatus();
    __hashCodeCalc = false;
    return _hashCode;
  }

}
