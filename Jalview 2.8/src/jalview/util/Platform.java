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

/**
 * System platform information used by Applet and Application
 * 
 * @author Jim Procter
 */
public class Platform
{
  /**
   * sorry folks - Macs really are different
   * 
   * @return true if we do things in a special way.
   */
  public boolean isAMac()
  {
    return java.lang.System.getProperty("os.name").indexOf("Mac") > -1;

  }

  public boolean isHeadless()
  {
    String hdls = java.lang.System.getProperty("java.awt.headless");

    return hdls != null && hdls.equals("true");
  }

  /**
   * 
   * @return nominal maximum command line length for this platform
   */
  public static int getMaxCommandLineLength()
  {
    // TODO: determine nominal limits for most platforms.
    return 2046; // this is the max length for a windows NT system.
  }

  /**
   * escape a string according to the local platform's escape character
   * 
   * @param file
   * @return escaped file
   */
  public static String escapeString(String file)
  {
    StringBuffer f = new StringBuffer();
    int p = 0, lastp = 0;
    while ((p = file.indexOf('\\', lastp)) > -1)
    {
      f.append(file.subSequence(lastp, p));
      f.append("\\\\");
      lastp = p + 1;
    }
    f.append(file.substring(lastp));
    return f.toString();
  }
}
