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

import java.util.Enumeration;
import java.util.Vector;

public class EmblFeatureLocations
{
  Vector locElements;

  String locationType;

  boolean locationComplement;

  /**
   * @return the locationComplement
   */
  public boolean isLocationComplement()
  {
    return locationComplement;
  }

  /**
   * @param locationComplement
   *          the locationComplement to set
   */
  public void setLocationComplement(boolean locationComplement)
  {
    this.locationComplement = locationComplement;
  }

  /**
   * @return the locationType
   */
  public String getLocationType()
  {
    return locationType;
  }

  /**
   * @param locationType
   *          the locationType to set
   */
  public void setLocationType(String locationType)
  {
    this.locationType = locationType;
  }

  /**
   * @return the locElements
   */
  public Vector getLocElements()
  {
    return locElements;
  }

  /**
   * @param locElements
   *          the locElements to set
   */
  public void setLocElements(Vector locElements)
  {
    this.locElements = locElements;
  }

  /**
   * Return all location elements as start-end pairs (without accessions) TODO:
   * pass back complement and 'less than or more than' range information Note:
   * do not use this since it throws away any accessionIds associated with each
   * location!
   * 
   * @return int[] { start1, end1, ... }
   */
  public int[] getElementRanges()
  {
    return getElementRanges(null);
  }

  /**
   * Return all location elements concerning given accession as start-end pairs
   * TODO: pass back complement and 'less than or more than' range information
   * TODO: deal with multiple accessions
   * 
   * @param accession
   *          the accession string for which locations are requested, or null
   *          for all locations
   * @return null or int[] { start1, end1, ... }
   */

  public int[] getElementRanges(String accession)
  {
    int sepos = 0;
    int[] se = new int[locElements.size() * 2];
    if (locationType.equalsIgnoreCase("single"))
    {
      for (Enumeration le = locElements.elements(); le.hasMoreElements();)
      {
        EmblFeatureLocElement loce = (EmblFeatureLocElement) le
                .nextElement();
        if (accession == null || loce.accession != null
                && accession.equals(loce.accession))
        {
          BasePosition bp[] = loce.getBasePositions();
          if (bp.length == 2)
          {
            se[sepos++] = Integer.parseInt(bp[0].getPos());
            se[sepos++] = Integer.parseInt(bp[1].getPos());
          }
        }
      }
    }
    else if (locationType.equalsIgnoreCase("join"))
    {
      for (Enumeration le = locElements.elements(); le.hasMoreElements();)
      {
        EmblFeatureLocElement loce = (EmblFeatureLocElement) le
                .nextElement();
        if (accession == null || loce.accession != null
                && accession.equals(loce.accession))
        {
          BasePosition bp[] = loce.getBasePositions();
          if (bp.length == 2)
          {
            se[sepos++] = Integer.parseInt(bp[0].getPos());
            se[sepos++] = Integer.parseInt(bp[1].getPos());
          }
        }
      }
      return se;
    }
    else if (locationType != null)
    {
      if (jalview.bin.Cache.log != null)
        jalview.bin.Cache.log
                .error("EmbleFeatureLocations.getElementRanges cannot deal with locationType=='"
                        + locationType + "'");
      else
        System.err
                .println("EmbleFeatureLocations.getElementRanges cannot deal with locationType=='"
                        + locationType + "'");
    }
    // trim range if necessary.
    if (se != null && sepos != se.length)
    {
      int[] trimmed = new int[sepos];
      System.arraycopy(se, 0, trimmed, 0, sepos);
      se = trimmed;
    }
    return se;
  }
}
