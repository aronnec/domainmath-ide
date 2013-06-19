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
package jalview.datamodel;

public class DBRefEntry
{
  String source = "", version = "", accessionId = "";

  /**
   * maps from associated sequence to the database sequence's coordinate system
   */
  Mapping map = null;

  public DBRefEntry()
  {

  }

  public DBRefEntry(String source, String version, String accessionId)
  {
    this(source, version, accessionId, null);
  }

  /**
   * 
   * @param source
   *          canonical source (uppercase only)
   * @param version
   *          (source dependent version string)
   * @param accessionId
   *          (source dependent accession number string)
   * @param map
   *          (mapping from local sequence numbering to source accession
   *          numbering)
   */
  public DBRefEntry(String source, String version, String accessionId,
          Mapping map)
  {
    this.source = source.toUpperCase();
    this.version = version;
    this.accessionId = accessionId;
    this.map = map;
  }

  public DBRefEntry(DBRefEntry entry)
  {
    this(
            (entry.source == null ? "" : new String(entry.source)),
            (entry.version == null ? "" : new String(entry.version)),
            (entry.accessionId == null ? "" : new String(entry.accessionId)),
            (entry.map == null ? null : new Mapping(entry.map)));
  }

  public boolean equals(DBRefEntry entry)
  {
    if (entry == this)
      return true;
    if (entry == null)
      return false;
    if (equalRef(entry)
            && ((map == null && entry.map == null) || (map != null
                    && entry.map != null && map.equals(entry.map))))
    {
      return true;
    }
    return false;
  }

  /**
   * test for similar DBRef attributes, except for the map object.
   * 
   * @param entry
   * @return true if source, accession and version are equal with those of entry
   */
  public boolean equalRef(DBRefEntry entry)
  {
    if (entry == null)
    {
      return false;
    }
    if (entry == this)
      return true;
    if ((source != null && entry.source != null && source
            .equalsIgnoreCase(entry.source))
            && (accessionId != null && entry.accessionId != null && accessionId
                    .equalsIgnoreCase(entry.accessionId))
            && (version != null && entry.version != null && version
                    .equalsIgnoreCase(entry.version)))
    {
      return true;
    }
    return false;
  }

  public String getSource()
  {
    return source;
  }

  public String getVersion()
  {
    return version;
  }

  public String getAccessionId()
  {
    return accessionId;
  }

  /**
   * @param accessionId
   *          the accessionId to set
   */
  public void setAccessionId(String accessionId)
  {
    this.accessionId = accessionId;
  }

  /**
   * @param source
   *          the source to set
   */
  public void setSource(String source)
  {
    this.source = source;
  }

  /**
   * @param version
   *          the version to set
   */
  public void setVersion(String version)
  {
    this.version = version;
  }

  /**
   * @return the map
   */
  public Mapping getMap()
  {
    return map;
  }

  /**
   * @param map
   *          the map to set
   */
  public void setMap(Mapping map)
  {
    this.map = map;
  }

  public boolean hasMap()
  {
    return map != null;
  }

  /**
   * 
   * @return source+":"+accessionId
   */
  public String getSrcAccString()
  {
    return ((source != null) ? source : "") + ":"
            + ((accessionId != null) ? accessionId : "");
  }
}
