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
package jalview.datamodel.xdb.embl;

public class EmblFeatureLocElement
{
  String type;

  String accession;

  String version;

  boolean complement;

  BasePosition basePositions[];

  /**
   * @return the accession
   */
  public String getAccession()
  {
    return accession;
  }

  /**
   * @param accession
   *          the accession to set
   */
  public void setAccession(String accession)
  {
    this.accession = accession;
  }

  /**
   * @return the basePositions
   */
  public BasePosition[] getBasePositions()
  {
    return basePositions;
  }

  /**
   * @param basePositions
   *          the basePositions to set
   */
  public void setBasePositions(BasePosition[] basePositions)
  {
    this.basePositions = basePositions;
  }

  /**
   * @return the complement
   */
  public boolean isComplement()
  {
    return complement;
  }

  /**
   * @param complement
   *          the complement to set
   */
  public void setComplement(boolean complement)
  {
    this.complement = complement;
  }

  /**
   * @return the type
   */
  public String getType()
  {
    return type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(String type)
  {
    this.type = type;
  }

  /**
   * @return the version
   */
  public String getVersion()
  {
    return version;
  }

  /**
   * @param version
   *          the version to set
   */
  public void setVersion(String version)
  {
    this.version = version;
  }
}
