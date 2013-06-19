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
package jalview.io;

import java.io.*;
import java.net.*;
import java.util.zip.GZIPInputStream;

/**
 * implements a random access wrapper around a particular datasource, for
 * passing to identifyFile and AlignFile objects.
 */
public class FileParse
{
  /**
   * text specifying source of data. usually filename or url.
   */
  private String dataName = "unknown source";

  public File inFile = null;

  public int index = 1; // sequence counter for FileParse object created from

  // same data source

  protected char suffixSeparator = '#';

  /**
   * character used to write newlines
   */
  protected String newline = System.getProperty("line.separator");

  public void setNewlineString(String nl)
  {
    newline = nl;
  }

  public String getNewlineString()
  {
    return newline;
  }

  /**
   * '#' separated string tagged on to end of filename or url that was clipped
   * off to resolve to valid filename
   */
  protected String suffix = null;

  protected String type = null;

  protected BufferedReader dataIn = null;

  protected String errormessage = "UNITIALISED SOURCE";

  protected boolean error = true;

  protected String warningMessage = null;

  /**
   * size of readahead buffer used for when initial stream position is marked.
   */
  final int READAHEAD_LIMIT = 2048;

  public FileParse()
  {
  }

  /**
   * Create a new FileParse instance reading from the same datasource starting
   * at the current position. WARNING! Subsequent reads from either object will
   * affect the read position of the other, but not the error state.
   * 
   * @param from
   */
  public FileParse(FileParse from) throws IOException
  {
    if (from == null)
    {
      throw new Error(
              "Implementation error. Null FileParse in copy constructor");
    }
    if (from == this)
      return;
    index = ++from.index;
    inFile = from.inFile;
    suffixSeparator = from.suffixSeparator;
    suffix = from.suffix;
    errormessage = from.errormessage; // inherit potential error messages
    error = false; // reset any error condition.
    type = from.type;
    dataIn = from.dataIn;
    if (dataIn != null)
    {
      mark();
    }
    dataName = from.dataName;
  }

  /**
   * Attempt to open a file as a datasource. Sets error and errormessage if
   * fileStr was invalid.
   * 
   * @param fileStr
   * @return this.error (true if the source was invalid)
   */
  private boolean checkFileSource(String fileStr) throws IOException
  {
    error = false;
    this.inFile = new File(fileStr);
    // check to see if it's a Jar file in disguise.
    if (!inFile.exists())
    {
      errormessage = "FILE NOT FOUND";
      error = true;
    }
    if (!inFile.canRead())
    {
      errormessage = "FILE CANNOT BE OPENED FOR READING";
      error = true;
    }
    if (inFile.isDirectory())
    {
      // this is really a 'complex' filetype - but we don't handle directory
      // reads yet.
      errormessage = "FILE IS A DIRECTORY";
      error = true;
    }
    if (!error)
    {
      dataIn = new BufferedReader(new FileReader(fileStr));
      dataName = fileStr;
    }
    return error;
  }

  private boolean checkURLSource(String fileStr) throws IOException,
          MalformedURLException
  {
    errormessage = "URL NOT FOUND";
    URL url = new URL(fileStr);
    //
    // GZIPInputStream code borrowed from Aquaria (soon to be open sourced) via Kenny Sabir
    Exception e=null;
    if (fileStr.endsWith(".gz")) {
      try {
          InputStream inputStream = url.openStream();
          dataIn = new BufferedReader(new InputStreamReader(new GZIPInputStream(inputStream)));
          dataIn.mark(2048);
          dataIn.read();
          dataIn.reset();
          
          dataName = fileStr;
          return false;
      } catch (Exception ex) {
        e=ex;
      }
    }

    try {
      dataIn = new BufferedReader(new InputStreamReader(url.openStream()));
    } catch (IOException q) {
      if (e!=null)
      {
        throw new IOException("Failed to resolve GZIP stream", e);
      }
      throw q;
    }
    // record URL as name of datasource.
    dataName = fileStr;
    return false;
  }

  /**
   * sets the suffix string (if any) and returns remainder (if suffix was
   * detected)
   * 
   * @param fileStr
   * @return truncated fileStr or null
   */
  private String extractSuffix(String fileStr)
  {
    // first check that there wasn't a suffix string tagged on.
    int sfpos = fileStr.lastIndexOf(suffixSeparator);
    if (sfpos > -1 && sfpos < fileStr.length() - 1)
    {
      suffix = fileStr.substring(sfpos + 1);
      // System.err.println("DEBUG: Found Suffix:"+suffix);
      return fileStr.substring(0, sfpos);
    }
    return null;
  }

  /**
   * Create a datasource for input to Jalview. See AppletFormatAdapter for the
   * types of sources that are handled.
   * 
   * @param fileStr
   *          - datasource locator/content
   * @param type
   *          - protocol of source
   * @throws MalformedURLException
   * @throws IOException
   */
  public FileParse(String fileStr, String type)
          throws MalformedURLException, IOException
  {
    this.type = type;
    error = false;

    if (type.equals(AppletFormatAdapter.FILE))
    {
      if (checkFileSource(fileStr))
      {
        String suffixLess = extractSuffix(fileStr);
        if (suffixLess != null)
        {
          if (checkFileSource(suffixLess))
          {
            throw new IOException("Problem opening " + inFile
                    + " (also tried " + suffixLess + ") : " + errormessage);
          }
        }
        else
        {
          throw new IOException("Problem opening " + inFile + " : "
                  + errormessage);
        }
      }
    }
    else if (type.equals(AppletFormatAdapter.URL))
    {
      try
      {
        try
        {
          checkURLSource(fileStr);
          if (suffixSeparator == '#')
            extractSuffix(fileStr); // URL lref is stored for later reference.
        } catch (IOException e)
        {
          String suffixLess = extractSuffix(fileStr);
          if (suffixLess == null)
          {
            throw (e);
          }
          else
          {
            try
            {
              checkURLSource(suffixLess);
            } catch (IOException e2)
            {
              errormessage = "BAD URL WITH OR WITHOUT SUFFIX";
              throw (e); // just pass back original - everything was wrong.
            }
          }
        }
      } catch (Exception e)
      {
        errormessage = "CANNOT ACCESS DATA AT URL '" + fileStr + "' ("
                + e.getMessage() + ")";
        error = true;
      }
    }
    else if (type.equals(AppletFormatAdapter.PASTE))
    {
      errormessage = "PASTE INACCESSIBLE!";
      dataIn = new BufferedReader(new StringReader(fileStr));
      dataName = "Paste";
    }
    else if (type.equals(AppletFormatAdapter.CLASSLOADER))
    {
      errormessage = "RESOURCE CANNOT BE LOCATED";
      java.io.InputStream is = getClass()
              .getResourceAsStream("/" + fileStr);
      if (is == null)
      {
        String suffixLess = extractSuffix(fileStr);
        if (suffixLess != null)
          is = getClass().getResourceAsStream("/" + suffixLess);
      }
      if (is != null)
      {
        dataIn = new BufferedReader(new java.io.InputStreamReader(is));
        dataName = fileStr;
      }
      else
      {
        error = true;
      }
    }
    else
    {
      errormessage = "PROBABLE IMPLEMENTATION ERROR : Datasource Type given as '"
              + (type != null ? type : "null") + "'";
      error = true;
    }
    if (dataIn == null || error)
    {
      // pass up the reason why we have no source to read from
      throw new IOException("Failed to read data from source:\n"
              + errormessage);
    }
    error = false;
    dataIn.mark(READAHEAD_LIMIT);
  }

  /**
   * mark the current position in the source as start for the purposes of it
   * being analysed by IdentifyFile().identify
   * 
   * @throws IOException
   */
  public void mark() throws IOException
  {
    if (dataIn != null)
    {
      dataIn.mark(READAHEAD_LIMIT);
    }
    else
    {
      throw new IOException("Unitialised Source Stream");
    }
  }

  public String nextLine() throws IOException
  {
    if (!error)
      return dataIn.readLine();
    throw new IOException("Invalid Source Stream:" + errormessage);
  }

  public boolean isValid()
  {
    return !error;
  }

  /**
   * closes the datasource and tidies up. source will be left in an error state
   */
  public void close() throws IOException
  {
    errormessage = "EXCEPTION ON CLOSE";
    error = true;
    dataIn.close();
    dataIn = null;
    errormessage = "SOURCE IS CLOSED";
  }

  /**
   * rewinds the datasource the beginning.
   * 
   */
  public void reset() throws IOException
  {
    if (dataIn != null && !error)
    {
      dataIn.reset();
    }
    else
    {
      throw new IOException(
              "Implementation Error: Reset called for invalid source.");
    }
  }

  /**
   * 
   * @return true if there is a warning for the user
   */
  public boolean hasWarningMessage()
  {
    return (warningMessage != null && warningMessage.length() > 0);
  }

  /**
   * 
   * @return empty string or warning message about file that was just parsed.
   */
  public String getWarningMessage()
  {
    return warningMessage;
  }

  public String getInFile()
  {
    if (inFile != null)
    {
      return inFile.getAbsolutePath() + " (" + index + ")";
    }
    else
    {
      return "From Paste + (" + index + ")";
    }
  }

  /**
   * @return the dataName
   */
  public String getDataName()
  {
    return dataName;
  }

  /**
   * set the (human readable) name or URI for this datasource
   * 
   * @param dataname
   */
  protected void setDataName(String dataname)
  {
    dataName = dataname;
  }

  /**
   * get the underlying bufferedReader for this data source.
   * 
   * @return null if no reader available
   * @throws IOException
   */
  public Reader getReader()
  {
    if (dataIn != null) // Probably don't need to test for readiness &&
                        // dataIn.ready())
    {
      return dataIn;
    }
    return null;
  }
}
