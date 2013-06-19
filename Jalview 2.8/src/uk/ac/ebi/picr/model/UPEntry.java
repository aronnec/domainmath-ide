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
package uk.ac.ebi.picr.model;

public class UPEntry implements java.io.Serializable
{
  private java.lang.String CRC64;

  private java.lang.String UPI;

  private uk.ac.ebi.picr.model.CrossReference[] identicalCrossReferences;

  private uk.ac.ebi.picr.model.CrossReference[] logicalCrossReferences;

  private java.lang.String sequence;

  private java.util.Calendar timestamp;

  public UPEntry()
  {
  }

  public UPEntry(java.lang.String CRC64, java.lang.String UPI,
          uk.ac.ebi.picr.model.CrossReference[] identicalCrossReferences,
          uk.ac.ebi.picr.model.CrossReference[] logicalCrossReferences,
          java.lang.String sequence, java.util.Calendar timestamp)
  {
    this.CRC64 = CRC64;
    this.UPI = UPI;
    this.identicalCrossReferences = identicalCrossReferences;
    this.logicalCrossReferences = logicalCrossReferences;
    this.sequence = sequence;
    this.timestamp = timestamp;
  }

  /**
   * Gets the CRC64 value for this UPEntry.
   * 
   * @return CRC64
   */
  public java.lang.String getCRC64()
  {
    return CRC64;
  }

  /**
   * Sets the CRC64 value for this UPEntry.
   * 
   * @param CRC64
   */
  public void setCRC64(java.lang.String CRC64)
  {
    this.CRC64 = CRC64;
  }

  /**
   * Gets the UPI value for this UPEntry.
   * 
   * @return UPI
   */
  public java.lang.String getUPI()
  {
    return UPI;
  }

  /**
   * Sets the UPI value for this UPEntry.
   * 
   * @param UPI
   */
  public void setUPI(java.lang.String UPI)
  {
    this.UPI = UPI;
  }

  /**
   * Gets the identicalCrossReferences value for this UPEntry.
   * 
   * @return identicalCrossReferences
   */
  public uk.ac.ebi.picr.model.CrossReference[] getIdenticalCrossReferences()
  {
    return identicalCrossReferences;
  }

  /**
   * Sets the identicalCrossReferences value for this UPEntry.
   * 
   * @param identicalCrossReferences
   */
  public void setIdenticalCrossReferences(
          uk.ac.ebi.picr.model.CrossReference[] identicalCrossReferences)
  {
    this.identicalCrossReferences = identicalCrossReferences;
  }

  public uk.ac.ebi.picr.model.CrossReference getIdenticalCrossReferences(
          int i)
  {
    return this.identicalCrossReferences[i];
  }

  public void setIdenticalCrossReferences(int i,
          uk.ac.ebi.picr.model.CrossReference _value)
  {
    this.identicalCrossReferences[i] = _value;
  }

  /**
   * Gets the logicalCrossReferences value for this UPEntry.
   * 
   * @return logicalCrossReferences
   */
  public uk.ac.ebi.picr.model.CrossReference[] getLogicalCrossReferences()
  {
    return logicalCrossReferences;
  }

  /**
   * Sets the logicalCrossReferences value for this UPEntry.
   * 
   * @param logicalCrossReferences
   */
  public void setLogicalCrossReferences(
          uk.ac.ebi.picr.model.CrossReference[] logicalCrossReferences)
  {
    this.logicalCrossReferences = logicalCrossReferences;
  }

  public uk.ac.ebi.picr.model.CrossReference getLogicalCrossReferences(int i)
  {
    return this.logicalCrossReferences[i];
  }

  public void setLogicalCrossReferences(int i,
          uk.ac.ebi.picr.model.CrossReference _value)
  {
    this.logicalCrossReferences[i] = _value;
  }

  /**
   * Gets the sequence value for this UPEntry.
   * 
   * @return sequence
   */
  public java.lang.String getSequence()
  {
    return sequence;
  }

  /**
   * Sets the sequence value for this UPEntry.
   * 
   * @param sequence
   */
  public void setSequence(java.lang.String sequence)
  {
    this.sequence = sequence;
  }

  /**
   * Gets the timestamp value for this UPEntry.
   * 
   * @return timestamp
   */
  public java.util.Calendar getTimestamp()
  {
    return timestamp;
  }

  /**
   * Sets the timestamp value for this UPEntry.
   * 
   * @param timestamp
   */
  public void setTimestamp(java.util.Calendar timestamp)
  {
    this.timestamp = timestamp;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj)
  {
    if (!(obj instanceof UPEntry))
      return false;
    UPEntry other = (UPEntry) obj;
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
            && ((this.CRC64 == null && other.getCRC64() == null) || (this.CRC64 != null && this.CRC64
                    .equals(other.getCRC64())))
            && ((this.UPI == null && other.getUPI() == null) || (this.UPI != null && this.UPI
                    .equals(other.getUPI())))
            && ((this.identicalCrossReferences == null && other
                    .getIdenticalCrossReferences() == null) || (this.identicalCrossReferences != null && java.util.Arrays
                    .equals(this.identicalCrossReferences,
                            other.getIdenticalCrossReferences())))
            && ((this.logicalCrossReferences == null && other
                    .getLogicalCrossReferences() == null) || (this.logicalCrossReferences != null && java.util.Arrays
                    .equals(this.logicalCrossReferences,
                            other.getLogicalCrossReferences())))
            && ((this.sequence == null && other.getSequence() == null) || (this.sequence != null && this.sequence
                    .equals(other.getSequence())))
            && ((this.timestamp == null && other.getTimestamp() == null) || (this.timestamp != null && this.timestamp
                    .equals(other.getTimestamp())));
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
    if (getCRC64() != null)
    {
      _hashCode += getCRC64().hashCode();
    }
    if (getUPI() != null)
    {
      _hashCode += getUPI().hashCode();
    }
    if (getIdenticalCrossReferences() != null)
    {
      for (int i = 0; i < java.lang.reflect.Array
              .getLength(getIdenticalCrossReferences()); i++)
      {
        java.lang.Object obj = java.lang.reflect.Array.get(
                getIdenticalCrossReferences(), i);
        if (obj != null && !obj.getClass().isArray())
        {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getLogicalCrossReferences() != null)
    {
      for (int i = 0; i < java.lang.reflect.Array
              .getLength(getLogicalCrossReferences()); i++)
      {
        java.lang.Object obj = java.lang.reflect.Array.get(
                getLogicalCrossReferences(), i);
        if (obj != null && !obj.getClass().isArray())
        {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getSequence() != null)
    {
      _hashCode += getSequence().hashCode();
    }
    if (getTimestamp() != null)
    {
      _hashCode += getTimestamp().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

}
