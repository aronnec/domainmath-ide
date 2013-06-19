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

package jalview.io.packed;

/**
 * API for a data provider that can be used with
 * jalview.io.packed.ParsePackedSet
 * 
 * @author JimP
 * 
 */
public interface DataProvider
{
  /**
   * class of data expected to be provided by datasource
   * 
   * @author JimP
   * 
   */
  public enum JvDataType
  {
    /**
     * any alignment flatfile recognisable by jalview.io.IdentifyFile
     */
    ALIGNMENT,
    /**
     * a jalview annotation file
     */
    ANNOTATION,
    /**
     * a GFF or Jalview features file
     */
    FEATURES,
    /**
     * a tree representation understood by the NewickFile parser
     */
    TREE,
    /**
     * any file that provides data that should be associated with a specified
     * sequence.
     */
    SEQASSOCATED;
  }

  /**
   * data to be parsed according to its type. Each call to getDataSource should
   * return a new instance of the same data stream initialised to the beginning
   * of the chunk of data that is to be parsed.
   * 
   * @return
   */
  jalview.io.FileParse getDataSource();

  /**
   * association context for data. Either null or a specific sequence.
   * 
   * @return
   */
  Object getSequenceTarget();

  /**
   * type of data
   * 
   * @return
   */
  DataProvider.JvDataType getType();
}
