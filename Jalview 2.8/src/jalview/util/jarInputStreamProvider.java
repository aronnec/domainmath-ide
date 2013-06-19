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
package jalview.util;

import java.io.IOException;
import java.util.jar.JarInputStream;

/**
 * input stream provider interface to be implemented by any non-file or URL
 * datasources so that all Jar entries can be read from the datasource by
 * repeatedly re-opening the JarInputStream.
 * 
 * This is a workaround necessary because castor's unmarshaller will close the
 * input stream after an unmarshalling session, which normally closes the whole
 * Jar input stream, not just the current JarEntry's stream.
 */
public interface jarInputStreamProvider
{
  /**
   * @return properly initialized jar input stream
   */
  JarInputStream getJarInputStream() throws IOException;

  /**
   * 
   * @return human readable name for datasource used when reporting any problems
   *         with it
   */
  String getFilename();
}
