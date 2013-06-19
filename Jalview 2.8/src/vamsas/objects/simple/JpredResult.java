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

public class JpredResult extends vamsas.objects.simple.Result implements
        java.io.Serializable
{
  private java.lang.String aligfile;

  private java.lang.String predfile;

  public JpredResult()
  {
  }

  public JpredResult(java.lang.String aligfile, java.lang.String predfile)
  {
    this.aligfile = aligfile;
    this.predfile = predfile;
  }

  /**
   * Gets the aligfile value for this JpredResult.
   * 
   * @return aligfile
   */
  public java.lang.String getAligfile()
  {
    return aligfile;
  }

  /**
   * Sets the aligfile value for this JpredResult.
   * 
   * @param aligfile
   */
  public void setAligfile(java.lang.String aligfile)
  {
    this.aligfile = aligfile;
  }

  /**
   * Gets the predfile value for this JpredResult.
   * 
   * @return predfile
   */
  public java.lang.String getPredfile()
  {
    return predfile;
  }

  /**
   * Sets the predfile value for this JpredResult.
   * 
   * @param predfile
   */
  public void setPredfile(java.lang.String predfile)
  {
    this.predfile = predfile;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj)
  {
    if (!(obj instanceof JpredResult))
    {
      return false;
    }
    JpredResult other = (JpredResult) obj;
    if (obj == null)
    {
      return false;
    }
    if (this == obj)
    {
      return true;
    }
    if (__equalsCalc != null)
    {
      return (__equalsCalc == obj);
    }
    __equalsCalc = obj;
    boolean _equals;
    _equals = super.equals(obj)
            && ((this.aligfile == null && other.getAligfile() == null) || (this.aligfile != null && this.aligfile
                    .equals(other.getAligfile())))
            && ((this.predfile == null && other.getPredfile() == null) || (this.predfile != null && this.predfile
                    .equals(other.getPredfile())));
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
    int _hashCode = super.hashCode();
    if (getAligfile() != null)
    {
      _hashCode += getAligfile().hashCode();
    }
    if (getPredfile() != null)
    {
      _hashCode += getPredfile().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

}
