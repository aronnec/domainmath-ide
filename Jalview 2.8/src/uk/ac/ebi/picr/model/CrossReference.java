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

public class CrossReference implements java.io.Serializable
{
  private java.lang.String accession;

  private java.lang.String accessionVersion;

  private java.lang.String databaseDescription;

  private java.lang.String databaseName;

  private java.util.Calendar dateAdded;

  private java.util.Calendar dateDeleted;

  private boolean deleted;

  private java.lang.String gi;

  private java.lang.String taxonId;

  public CrossReference()
  {
  }

  public CrossReference(java.lang.String accession,
          java.lang.String accessionVersion,
          java.lang.String databaseDescription,
          java.lang.String databaseName, java.util.Calendar dateAdded,
          java.util.Calendar dateDeleted, boolean deleted,
          java.lang.String gi, java.lang.String taxonId)
  {
    this.accession = accession;
    this.accessionVersion = accessionVersion;
    this.databaseDescription = databaseDescription;
    this.databaseName = databaseName;
    this.dateAdded = dateAdded;
    this.dateDeleted = dateDeleted;
    this.deleted = deleted;
    this.gi = gi;
    this.taxonId = taxonId;
  }

  /**
   * Gets the accession value for this CrossReference.
   * 
   * @return accession
   */
  public java.lang.String getAccession()
  {
    return accession;
  }

  /**
   * Sets the accession value for this CrossReference.
   * 
   * @param accession
   */
  public void setAccession(java.lang.String accession)
  {
    this.accession = accession;
  }

  /**
   * Gets the accessionVersion value for this CrossReference.
   * 
   * @return accessionVersion
   */
  public java.lang.String getAccessionVersion()
  {
    return accessionVersion;
  }

  /**
   * Sets the accessionVersion value for this CrossReference.
   * 
   * @param accessionVersion
   */
  public void setAccessionVersion(java.lang.String accessionVersion)
  {
    this.accessionVersion = accessionVersion;
  }

  /**
   * Gets the databaseDescription value for this CrossReference.
   * 
   * @return databaseDescription
   */
  public java.lang.String getDatabaseDescription()
  {
    return databaseDescription;
  }

  /**
   * Sets the databaseDescription value for this CrossReference.
   * 
   * @param databaseDescription
   */
  public void setDatabaseDescription(java.lang.String databaseDescription)
  {
    this.databaseDescription = databaseDescription;
  }

  /**
   * Gets the databaseName value for this CrossReference.
   * 
   * @return databaseName
   */
  public java.lang.String getDatabaseName()
  {
    return databaseName;
  }

  /**
   * Sets the databaseName value for this CrossReference.
   * 
   * @param databaseName
   */
  public void setDatabaseName(java.lang.String databaseName)
  {
    this.databaseName = databaseName;
  }

  /**
   * Gets the dateAdded value for this CrossReference.
   * 
   * @return dateAdded
   */
  public java.util.Calendar getDateAdded()
  {
    return dateAdded;
  }

  /**
   * Sets the dateAdded value for this CrossReference.
   * 
   * @param dateAdded
   */
  public void setDateAdded(java.util.Calendar dateAdded)
  {
    this.dateAdded = dateAdded;
  }

  /**
   * Gets the dateDeleted value for this CrossReference.
   * 
   * @return dateDeleted
   */
  public java.util.Calendar getDateDeleted()
  {
    return dateDeleted;
  }

  /**
   * Sets the dateDeleted value for this CrossReference.
   * 
   * @param dateDeleted
   */
  public void setDateDeleted(java.util.Calendar dateDeleted)
  {
    this.dateDeleted = dateDeleted;
  }

  /**
   * Gets the deleted value for this CrossReference.
   * 
   * @return deleted
   */
  public boolean isDeleted()
  {
    return deleted;
  }

  /**
   * Sets the deleted value for this CrossReference.
   * 
   * @param deleted
   */
  public void setDeleted(boolean deleted)
  {
    this.deleted = deleted;
  }

  /**
   * Gets the gi value for this CrossReference.
   * 
   * @return gi
   */
  public java.lang.String getGi()
  {
    return gi;
  }

  /**
   * Sets the gi value for this CrossReference.
   * 
   * @param gi
   */
  public void setGi(java.lang.String gi)
  {
    this.gi = gi;
  }

  /**
   * Gets the taxonId value for this CrossReference.
   * 
   * @return taxonId
   */
  public java.lang.String getTaxonId()
  {
    return taxonId;
  }

  /**
   * Sets the taxonId value for this CrossReference.
   * 
   * @param taxonId
   */
  public void setTaxonId(java.lang.String taxonId)
  {
    this.taxonId = taxonId;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj)
  {
    if (!(obj instanceof CrossReference))
      return false;
    CrossReference other = (CrossReference) obj;
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
            && ((this.accession == null && other.getAccession() == null) || (this.accession != null && this.accession
                    .equals(other.getAccession())))
            && ((this.accessionVersion == null && other
                    .getAccessionVersion() == null) || (this.accessionVersion != null && this.accessionVersion
                    .equals(other.getAccessionVersion())))
            && ((this.databaseDescription == null && other
                    .getDatabaseDescription() == null) || (this.databaseDescription != null && this.databaseDescription
                    .equals(other.getDatabaseDescription())))
            && ((this.databaseName == null && other.getDatabaseName() == null) || (this.databaseName != null && this.databaseName
                    .equals(other.getDatabaseName())))
            && ((this.dateAdded == null && other.getDateAdded() == null) || (this.dateAdded != null && this.dateAdded
                    .equals(other.getDateAdded())))
            && ((this.dateDeleted == null && other.getDateDeleted() == null) || (this.dateDeleted != null && this.dateDeleted
                    .equals(other.getDateDeleted())))
            && this.deleted == other.isDeleted()
            && ((this.gi == null && other.getGi() == null) || (this.gi != null && this.gi
                    .equals(other.getGi())))
            && ((this.taxonId == null && other.getTaxonId() == null) || (this.taxonId != null && this.taxonId
                    .equals(other.getTaxonId())));
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
    if (getAccession() != null)
    {
      _hashCode += getAccession().hashCode();
    }
    if (getAccessionVersion() != null)
    {
      _hashCode += getAccessionVersion().hashCode();
    }
    if (getDatabaseDescription() != null)
    {
      _hashCode += getDatabaseDescription().hashCode();
    }
    if (getDatabaseName() != null)
    {
      _hashCode += getDatabaseName().hashCode();
    }
    if (getDateAdded() != null)
    {
      _hashCode += getDateAdded().hashCode();
    }
    if (getDateDeleted() != null)
    {
      _hashCode += getDateDeleted().hashCode();
    }
    _hashCode += (isDeleted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
    if (getGi() != null)
    {
      _hashCode += getGi().hashCode();
    }
    if (getTaxonId() != null)
    {
      _hashCode += getTaxonId().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

}
