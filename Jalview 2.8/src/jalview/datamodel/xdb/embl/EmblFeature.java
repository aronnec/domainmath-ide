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

import java.util.Vector;

public class EmblFeature
{
  String name;

  Vector dbRefs;

  Vector qualifiers;

  Vector locations;

  /**
   * @return the dbRefs
   */
  public Vector getDbRefs()
  {
    return dbRefs;
  }

  /**
   * @param dbRefs
   *          the dbRefs to set
   */
  public void setDbRefs(Vector dbRefs)
  {
    this.dbRefs = dbRefs;
  }

  /**
   * @return the locations
   */
  public Vector getLocations()
  {
    return locations;
  }

  /**
   * @param locations
   *          the locations to set
   */
  public void setLocations(Vector locations)
  {
    this.locations = locations;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the qualifiers
   */
  public Vector getQualifiers()
  {
    return qualifiers;
  }

  /**
   * @param qualifiers
   *          the qualifiers to set
   */
  public void setQualifiers(Vector qualifiers)
  {
    this.qualifiers = qualifiers;
  }
}
