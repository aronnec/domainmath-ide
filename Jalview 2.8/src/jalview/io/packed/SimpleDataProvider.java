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

import jalview.io.FileParse;

/**
 * minimal implementation of the DataProvider interface. Allows a FileParse
 * datasource to be specified as one of the DataProvider.JvDataType content
 * types, with or without some other associated object as external reference.
 */
public class SimpleDataProvider implements DataProvider
{
  DataProvider.JvDataType jvtype;

  FileParse source;

  Object assocseq;

  /**
   * create a SimpleDataProvider
   * 
   * @param type
   *          - contents of resource accessible via fp
   * @param fp
   *          - datasource
   * @param assoc
   *          - external object that fp's content should be associated with (may
   *          be null)
   */
  public SimpleDataProvider(DataProvider.JvDataType type, FileParse fp,
          Object assoc)
  {
    jvtype = type;
    source = fp;
    assocseq = assoc;
  }

  @Override
  public FileParse getDataSource()
  {
    return source;
  }

  @Override
  public Object getSequenceTarget()
  {
    return assocseq;
  }

  @Override
  public DataProvider.JvDataType getType()
  {
    return jvtype;
  }

}
