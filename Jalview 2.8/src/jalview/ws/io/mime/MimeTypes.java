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
package jalview.ws.io.mime;

import jalview.io.packed.DataProvider.JvDataType;

/**
 * static functions for resolving Jalview datatypes from mime types
 * 
 * @author JimP TODO: consider making get(Mime)TypeOf functions throw exceptions
 *         rather than returning null
 */
public class MimeTypes
{
  /**
   * pair list {String,JvDataType} giving a mime-type followed by its associated
   * JvDataType enumeration.
   */
  final public static Object[] typemap = new Object[]
  { "application/x-align", JvDataType.ALIGNMENT,
      "application/x-jalview-annotation", JvDataType.ANNOTATION,
      "application/x-newick", JvDataType.TREE,
      "application/x-new-hampshire", JvDataType.TREE,
      "application/x-new-hampshire-extended", JvDataType.TREE,
      "application/x-nh", JvDataType.TREE, "application/x-nhx",
      JvDataType.TREE, "application/x-gff", JvDataType.FEATURES,
      "application/x-gff3", JvDataType.FEATURES,
      "application/x-jalview-feature-file", JvDataType.FEATURES,
      "application/x-pdb", JvDataType.SEQASSOCATED };

  /**
   * 
   * @param mimeType
   * @return the associated jalview datatype or null if no mapping is available
   */
  public static JvDataType getTypeOf(String mimeType)
  {
    String mt = mimeType.toLowerCase();
    for (int i = 0; i < typemap.length; i += 2)
    {
      if (typemap[i].equals(mt))
      {
        return (JvDataType) typemap[i + 1];
      }
    }
    return null;
  }

  /**
   * 
   * @param type
   * @return the primary mimetype associated with this type.
   */
  public static String getMimeTypeOf(JvDataType type)
  {
    for (int i = 1; i < typemap.length; i += 2)
    {
      if (typemap[i].equals(type))
      {
        return (String) typemap[i - 1];
      }
    }
    return null;
  }
}
