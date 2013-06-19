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

import java.util.*;

import javax.swing.*;

import jalview.datamodel.*;
import jalview.gui.*;

public class FileLoader implements Runnable
{
  String file;

  String protocol;

  String format;

  FileParse source = null; // alternative specification of where data comes

  // from

  AlignViewport viewport;

  AlignFrame alignFrame;

  long loadtime;

  long memused;

  boolean raiseGUI = true;

  /**
   * default constructor always raised errors in GUI dialog boxes
   */
  public FileLoader()
  {
    this(true);
  }

  /**
   * construct a Fileloader that may raise errors non-interactively
   * 
   * @param raiseGUI
   *          true if errors are to be raised as GUI dialog boxes
   */
  public FileLoader(boolean raiseGUI)
  {
    this.raiseGUI = raiseGUI;
  }

  public void LoadFile(AlignViewport viewport, String file,
          String protocol, String format)
  {
    this.viewport = viewport;
    LoadFile(file, protocol, format);
  }

  public void LoadFile(String file, String protocol, String format)
  {
    this.file = file;
    this.protocol = protocol;
    this.format = format;

    final Thread loader = new Thread(this);

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        loader.start();
      }
    });
  }

  /**
   * Load a (file, protocol) source of unknown type
   * 
   * @param file
   * @param protocol
   */
  public void LoadFile(String file, String protocol)
  {
    LoadFile(file, protocol, null);
  }

  /**
   * Load alignment from (file, protocol) and wait till loaded
   * 
   * @param file
   * @param protocol
   * @return alignFrame constructed from file contents
   */
  public AlignFrame LoadFileWaitTillLoaded(String file, String protocol)
  {
    return LoadFileWaitTillLoaded(file, protocol, null);
  }

  /**
   * Load alignment from (file, protocol) of type format and wait till loaded
   * 
   * @param file
   * @param protocol
   * @param format
   * @return alignFrame constructed from file contents
   */
  public AlignFrame LoadFileWaitTillLoaded(String file, String protocol,
          String format)
  {
    this.file = file;
    this.protocol = protocol;
    this.format = format;
    return _LoadFileWaitTillLoaded();
  }

  /**
   * Load alignment from FileParse source of type format and wait till loaded
   * 
   * @param source
   * @param format
   * @return alignFrame constructed from file contents
   */
  public AlignFrame LoadFileWaitTillLoaded(FileParse source, String format)
  {
    this.source = source;
    file = source.getInFile();
    protocol = source.type;
    this.format = format;
    return _LoadFileWaitTillLoaded();
  }

  /**
   * start thread and wait until finished, then return the alignFrame that's
   * (hopefully) been read.
   * 
   * @return
   */
  protected AlignFrame _LoadFileWaitTillLoaded()
  {
    Thread loader = new Thread(this);
    loader.start();

    while (loader.isAlive())
    {
      try
      {
        Thread.sleep(500);
      } catch (Exception ex)
      {
      }
    }

    return alignFrame;
  }

  public void updateRecentlyOpened()
  {
    Vector recent = new Vector();
    if (protocol.equals(FormatAdapter.PASTE))
    {
      // do nothing if the file was pasted in as text... there is no filename to
      // refer to it as.
      return;
    }
    String type = protocol.equals(FormatAdapter.FILE) ? "RECENT_FILE"
            : "RECENT_URL";

    String historyItems = jalview.bin.Cache.getProperty(type);

    StringTokenizer st;

    if (historyItems != null)
    {
      st = new StringTokenizer(historyItems, "\t");

      while (st.hasMoreTokens())
      {
        recent.addElement(st.nextElement().toString().trim());
      }
    }

    if (recent.contains(file))
    {
      recent.remove(file);
    }

    StringBuffer newHistory = new StringBuffer(file);
    for (int i = 0; i < recent.size() && i < 10; i++)
    {
      newHistory.append("\t");
      newHistory.append(recent.elementAt(i));
    }

    jalview.bin.Cache.setProperty(type, newHistory.toString());

    if (protocol.equals(FormatAdapter.FILE))
    {
      jalview.bin.Cache.setProperty("DEFAULT_FILE_FORMAT", format);
    }
  }

  public void run()
  {
    String title = protocol.equals(AppletFormatAdapter.PASTE) ? "Copied From Clipboard"
            : file;
    Runtime rt = Runtime.getRuntime();
    try
    {
      if (Desktop.instance != null)
      {
        Desktop.instance.startLoading(file);
      }
      if (format == null)
      {
        // just in case the caller didn't identify the file for us
        if (source != null)
        {
          format = new IdentifyFile().Identify(source, false); // identify
          // stream and
          // rewind rather
          // than close
        }
        else
        {
          format = new IdentifyFile().Identify(file, protocol);
        }
      }
      // TODO: cache any stream datasources as a temporary file (eg. PDBs
      // retrieved via URL)
      if (Desktop.desktop != null && Desktop.desktop.isShowMemoryUsage())
      {
        System.gc();
        memused = (rt.maxMemory() - rt.totalMemory() + rt.freeMemory()); // free
        // memory
        // before
        // load
      }
      loadtime = -System.currentTimeMillis();
      Alignment al = null;

      if (format.equalsIgnoreCase("Jalview"))
      {
        if (source != null)
        {
          // Tell the user (developer?) that this is going to cause a problem
          System.err
                  .println("IMPLEMENTATION ERROR: Cannot read consecutive Jalview XML projects from a stream.");
          // We read the data anyway - it might make sense.
        }
        alignFrame = new Jalview2XML(raiseGUI).LoadJalviewAlign(file);
      }
      else
      {
        String error = AppletFormatAdapter.SUPPORTED_FORMATS;
        if (FormatAdapter.isValidFormat(format))
        {
          try
          {
            if (source != null)
            {
              // read from the provided source
              al = new FormatAdapter().readFromFile(source, format);
            }
            else
            {

              // open a new source and read from it
              FormatAdapter fa = new FormatAdapter();
              al = fa.readFile(file, protocol, format);
              source = fa.afile; // keep reference for later if necessary.
            }
          } catch (java.io.IOException ex)
          {
            error = ex.getMessage();
          }
        }
        else
        {
          if (format != null && format.length() > 7)
          {
            // ad hoc message in format.
            error = format + "\n" + error;
          }
        }

        if ((al != null) && (al.getHeight() > 0))
        {
          if (viewport != null)
          {
            // TODO: create undo object for this JAL-1101
            for (int i = 0; i < al.getHeight(); i++)
            {
              viewport.getAlignment().addSequence(al.getSequenceAt(i));
            }
            viewport.firePropertyChange("alignment", null, viewport
                    .getAlignment().getSequences());

          }
          else
          {
            alignFrame = new AlignFrame(al, AlignFrame.DEFAULT_WIDTH,
                    AlignFrame.DEFAULT_HEIGHT);

            alignFrame.statusBar.setText("Successfully loaded file "
                    + title);

            if (!protocol.equals(AppletFormatAdapter.PASTE))
              alignFrame.setFileName(file, format);
            if (raiseGUI)
            {
              // add the window to the GUI
              // note - this actually should happen regardless of raiseGUI status in Jalview 3
              // TODO: define 'virtual desktop' for benefit of headless scripts that perform queries to find the 'current working alignment'
              Desktop.addInternalFrame(alignFrame, title,
                      AlignFrame.DEFAULT_WIDTH, AlignFrame.DEFAULT_HEIGHT);
            }

            try
            {
              alignFrame.setMaximum(jalview.bin.Cache.getDefault(
                      "SHOW_FULLSCREEN", false));
            } catch (java.beans.PropertyVetoException ex)
            {
            }
          }
        }
        else
        {
          if (Desktop.instance != null)
          {
            Desktop.instance.stopLoading();
          }

          final String errorMessage = "Couldn't load file " + title + "\n"
                  + error;
          if (raiseGUI)
          {
            javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
              public void run()
              {
                JOptionPane.showInternalMessageDialog(Desktop.desktop,
                        errorMessage, "Error loading file",
                        JOptionPane.WARNING_MESSAGE);
              }
            });
          }
          else
          {
            System.err.println(errorMessage);
          }
        }
      }

      updateRecentlyOpened();

    } catch (Exception er)
    {
      System.err.println("Exception whilst opening file '" + file);
      er.printStackTrace();
      if (raiseGUI)
      {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            javax.swing.JOptionPane.showInternalMessageDialog(
                    Desktop.desktop, "Encountered problems opening " + file
                            + "!!", "File open error",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
          }
        });
      }
      alignFrame = null;
    } catch (OutOfMemoryError er)
    {

      er.printStackTrace();
      alignFrame = null;
      if (raiseGUI)
      {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            javax.swing.JOptionPane
                    .showInternalMessageDialog(
                            Desktop.desktop,
                            "Out of memory loading file "
                                    + file
                                    + "!!"
                                    + "\nSee help files for increasing Java Virtual Machine memory.",
                            "Out of memory",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
          }
        });
      }
      System.err.println("Out of memory loading file " + file + "!!");

    }
    loadtime += System.currentTimeMillis();
    // TODO: Estimate percentage of memory used by a newly loaded alignment -
    // warn if more memory will be needed to work with it
    // System.gc();
    memused = memused
            - (rt.maxMemory() - rt.totalMemory() + rt.freeMemory()); // difference
    // in free
    // memory
    // after
    // load
    if (Desktop.desktop != null && Desktop.desktop.isShowMemoryUsage())
    {
      if (alignFrame != null)
      {
        AlignmentI al = alignFrame.getViewport().getAlignment();

        System.out.println("Loaded '" + title + "' in "
                + (loadtime / 1000.0) + "s, took an additional "
                + (1.0 * memused / (1024.0 * 1024.0)) + " MB ("
                + al.getHeight() + " seqs by " + al.getWidth() + " cols)");
      }
      else
      {
        // report that we didn't load anything probably due to an out of memory
        // error
        System.out.println("Failed to load '" + title + "' in "
                + (loadtime / 1000.0) + "s, took an additional "
                + (1.0 * memused / (1024.0 * 1024.0))
                + " MB (alignment is null)");
      }
    }
    // remove the visual delay indicator
    if (Desktop.instance != null)
    {
      Desktop.instance.stopLoading();
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#finalize()
   */
  protected void finalize() throws Throwable
  {
    source = null;
    alignFrame = null;
    viewport = null;
    super.finalize();
  }

}
