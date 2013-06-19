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

import java.util.*;

public class PDBEntry
{
  String file;

  String type;

  String id;

  Hashtable properties;

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof PDBEntry))
    {
      return false;
    }
    if (obj == this)
      return true;
    PDBEntry o = (PDBEntry) obj;
    return (file == o.file || (file != null && o.file != null && o.file
            .equals(file)))
            && (type == o.type || (type != null && o.type != null && o.type
                    .equals(type)))
            && (id == o.id || (id != null && o.id != null && o.id
                    .equalsIgnoreCase(id)))
            && (properties == o.properties || (properties != null
                    && o.properties != null && properties
                      .equals(o.properties)));
  }

  public PDBEntry()
  {
  }

  public PDBEntry(PDBEntry entry)
  {
    file = entry.file;
    type = entry.type;
    id = entry.id;
    if (entry.properties != null)
    {
      properties = (Hashtable) entry.properties.clone();
    }
  }

  public void setFile(String file)
  {
    this.file = file;
  }

  public String getFile()
  {
    return file;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getType()
  {
    return type;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setProperty(Hashtable property)
  {
    this.properties = property;
  }

  public Hashtable getProperty()
  {
    return properties;
  }

}
