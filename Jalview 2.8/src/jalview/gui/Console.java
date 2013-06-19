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
package jalview.gui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.apache.log4j.SimpleLayout;

/**
 * Simple Jalview Java Console. Version 1 - allows viewing of console output
 * after desktop is created. Acquired with thanks from RJHM's site
 * http://www.comweb.nl/java/Console/Console.html A simple Java Console for your
 * application (Swing version) Requires Java 1.1.5 or higher Disclaimer the use
 * of this source is at your own risk. Permision to use and distribute into your
 * own applications RJHM van den Bergh , rvdb@comweb.nl
 */

public class Console extends WindowAdapter implements WindowListener,
        ActionListener, Runnable
{
  private JFrame frame;

  private JTextArea textArea;

  /*
   * unused - tally and limit for lines in console window int lines = 0;
   * 
   * int lim = 1000;
   */
  int byteslim = 102400, bytescut = 76800; // 100k and 75k cut point.

  private Thread reader, reader2, textAppender;

  private boolean quit;

  private final PrintStream stdout = System.out, stderr = System.err;

  private PipedInputStream pin = new PipedInputStream();

  private PipedInputStream pin2 = new PipedInputStream();

  private StringBuffer displayPipe = new StringBuffer();

  Thread errorThrower; // just for testing (Throws an Exception at this Console

  // are we attached to some parent Desktop
  Desktop parent = null;

  public Console()
  {
    // create all components and add them
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame = initFrame("Java Console", screenSize.width / 2,
            screenSize.height / 2, -1, -1);
    initConsole(true);
  }

  private void initConsole(boolean visible)
  {
    initConsole(visible, true);
  }

  /**
   * 
   * @param visible
   *          - open the window
   * @param redirect
   *          - redirect std*
   */
  private void initConsole(boolean visible, boolean redirect)
  {
    // CutAndPasteTransfer cpt = new CutAndPasteTransfer();
    // textArea = cpt.getTextArea();
    textArea = new JTextArea();
    textArea.setEditable(false);
    JButton button = new JButton("clear");

    // frame = cpt;
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(new JScrollPane(textArea),
            BorderLayout.CENTER);
    frame.getContentPane().add(button, BorderLayout.SOUTH);
    frame.setVisible(visible);
    updateConsole = visible;
    frame.addWindowListener(this);
    button.addActionListener(this);
    if (redirect)
    {
      redirectStreams();
    }
    else
    {
      unredirectStreams();
    }
    quit = false; // signals the Threads that they should exit

    // Starting two seperate threads to read from the PipedInputStreams
    //
    reader = new Thread(this);
    reader.setDaemon(true);
    reader.start();
    //
    reader2 = new Thread(this);
    reader2.setDaemon(true);
    reader2.start();
    // and a thread to append text to the textarea
    textAppender = new Thread(this);
    textAppender.setDaemon(true);
    textAppender.start();
  }

  PipedOutputStream pout = null, perr = null;

  public void redirectStreams()
  {
    if (pout == null)
    {
      try
      {
        pout = new PipedOutputStream(this.pin);
        System.setOut(new PrintStream(pout, true));
      } catch (java.io.IOException io)
      {
        textArea.append("Couldn't redirect STDOUT to this console\n"
                + io.getMessage());
        io.printStackTrace(stderr);
      } catch (SecurityException se)
      {
        textArea.append("Couldn't redirect STDOUT to this console\n"
                + se.getMessage());
        se.printStackTrace(stderr);
      }

      try
      {
        perr = new PipedOutputStream(this.pin2);
        System.setErr(new PrintStream(perr, true));
      } catch (java.io.IOException io)
      {
        textArea.append("Couldn't redirect STDERR to this console\n"
                + io.getMessage());
        io.printStackTrace(stderr);
      } catch (SecurityException se)
      {
        textArea.append("Couldn't redirect STDERR to this console\n"
                + se.getMessage());
        se.printStackTrace(stderr);
      }
    }
  }

  public void unredirectStreams()
  {
    if (pout != null)
    {
      try
      {
        System.setOut(stdout);
        pout.flush();
        pout.close();
        pin = new PipedInputStream();
        pout = null;
      } catch (java.io.IOException io)
      {
        textArea.append("Couldn't unredirect STDOUT to this console\n"
                + io.getMessage());
        io.printStackTrace(stderr);
      } catch (SecurityException se)
      {
        textArea.append("Couldn't unredirect STDOUT to this console\n"
                + se.getMessage());
        se.printStackTrace(stderr);
      }

      try
      {
        System.setErr(stderr);
        perr.flush();
        perr.close();
        pin2 = new PipedInputStream();
        perr = null;
      } catch (java.io.IOException io)
      {
        textArea.append("Couldn't unredirect STDERR to this console\n"
                + io.getMessage());
        io.printStackTrace(stderr);
      } catch (SecurityException se)
      {
        textArea.append("Couldn't unredirect STDERR to this console\n"
                + se.getMessage());
        se.printStackTrace(stderr);
      }
    }
  }

  public void test()
  {
    // testing part
    // you may omit this part for your application
    //

    System.out.println("Hello World 2");
    System.out.println("All fonts available to Graphic2D:\n");
    GraphicsEnvironment ge = GraphicsEnvironment
            .getLocalGraphicsEnvironment();
    String[] fontNames = ge.getAvailableFontFamilyNames();
    for (int n = 0; n < fontNames.length; n++)
      System.out.println(fontNames[n]);
    // Testing part: simple an error thrown anywhere in this JVM will be printed
    // on the Console
    // We do it with a seperate Thread becasue we don't wan't to break a Thread
    // used by the Console.
    System.out.println("\nLets throw an error on this console");
    errorThrower = new Thread(this);
    errorThrower.setDaemon(true);
    errorThrower.start();
  }

  private JFrame initFrame(String string, int i, int j, int x, int y)
  {
    JFrame frame = new JFrame(string);
    frame.setName(string);
    if (x == -1)
      x = (int) (i / 2);
    if (y == -1)
      y = (int) (j / 2);
    frame.setBounds(x, y, i, j);
    return frame;
  }

  /**
   * attach a console to the desktop - the desktop will open it if requested.
   * 
   * @param desktop
   */
  public Console(Desktop desktop)
  {
    this(desktop, true);
  }

  /**
   * attach a console to the desktop - the desktop will open it if requested.
   * 
   * @param desktop
   * @param showjconsole
   *          - if true, then redirect stdout immediately
   */
  public Console(Desktop desktop, boolean showjconsole)
  {
    parent = desktop;
    // window name - get x,y,width, height possibly scaled
    Rectangle bounds = desktop.getLastKnownDimensions("JAVA_CONSOLE_");
    if (bounds == null)
    {
      frame = initFrame("Jalview Java Console", desktop.getWidth() / 2,
              desktop.getHeight() / 4, desktop.getX(), desktop.getY());
    }
    else
    {
      frame = initFrame("Jalview Java Console", bounds.width,
              bounds.height, bounds.x, bounds.y);
    }
    // desktop.add(frame);
    initConsole(false);
    JalviewAppender jappender = new JalviewAppender();
    jappender.setLayout(new SimpleLayout());
    JalviewAppender.setTextArea(textArea);
    org.apache.log4j.Logger.getRootLogger().addAppender(jappender);
  }

  public synchronized void stopConsole()
  {
    quit = true;
    this.notifyAll();
    /*
     * reader.notify(); reader2.notify(); if (errorThrower!=null)
     * errorThrower.notify(); // stop all threads if (textAppender!=null)
     * textAppender.notify();
     */
    if (pout != null)
    {
      try
      {
        reader.join(10);
        pin.close();
      } catch (Exception e)
      {
      }
      try
      {
        reader2.join(10);
        pin2.close();
      } catch (Exception e)
      {
      }
      try
      {
        textAppender.join(10);
      } catch (Exception e)
      {
      }
    }
    if (!frame.isVisible())
    {
      frame.dispose();
    }
    // System.exit(0);
  }

  public synchronized void windowClosed(WindowEvent evt)
  {
    frame.setVisible(false);
    closeConsoleGui();
  }

  private void closeConsoleGui()
  {
    updateConsole = false;
    if (parent == null)
    {

      stopConsole();
    }
    else
    {
      parent.showConsole(false);
    }
  }

  public synchronized void windowClosing(WindowEvent evt)
  {
    frame.setVisible(false); // default behaviour of JFrame
    closeConsoleGui();

    // frame.dispose();
  }

  public synchronized void actionPerformed(ActionEvent evt)
  {
    trimBuffer(true);
    // textArea.setText("");
  }

  public synchronized void run()
  {
    try
    {
      while (Thread.currentThread() == reader)
      {
        if (pin == null || pin.available() == 0)
        {
          try
          {
            this.wait(100);
            if (pin.available() == 0)
            {
              trimBuffer(false);
            }
          } catch (InterruptedException ie)
          {
          }
        }

        while (pin.available() != 0)
        {
          String input = this.readLine(pin);
          stdout.print(input);
          long time = System.nanoTime();
          appendToTextArea(input);
          // stderr.println("Time taken to stdout append:\t"
          // + (System.nanoTime() - time) + " ns");
          // lines++;
        }
        if (quit)
          return;
      }

      while (Thread.currentThread() == reader2)
      {
        if (pin2.available() == 0)
        {
          try
          {
            this.wait(100);
            if (pin2.available() == 0)
            {
              trimBuffer(false);
            }
          } catch (InterruptedException ie)
          {
          }
        }
        while (pin2.available() != 0)
        {
          String input = this.readLine(pin2);
          stderr.print(input);
          long time = System.nanoTime();
          appendToTextArea(input);
          // stderr.println("Time taken to stderr append:\t"
          // + (System.nanoTime() - time) + " ns");
          // lines++;
        }
        if (quit)
          return;
      }
      while (Thread.currentThread() == textAppender)
      {
        if (updateConsole)
        {
          // check string buffer - if greater than console, clear console and
          // replace with last segment of content, otherwise, append all to
          // content.
          long count;
          while (displayPipe.length() > 0)
          {
            count = 0;
            StringBuffer tmp = new StringBuffer(), replace;
            synchronized (displayPipe)
            {
              replace = displayPipe;
              displayPipe = tmp;
            }
            // simply append whole buffer
            textArea.append(replace.toString());
            count += replace.length();
            if (count > byteslim)
            {
              trimBuffer(false);
            }
          }
          if (displayPipe.length() == 0)
          {
            try
            {
              this.wait(100);
              if (displayPipe.length() == 0)
              {
                trimBuffer(false);
              }
            } catch (InterruptedException e)
            {
            }
            ;
          }
        }
        else
        {
          try
          {
            this.wait(100);
          } catch (InterruptedException e)
          {

          }
        }
        if (quit)
        {
          return;
        }

      }
    } catch (Exception e)
    {
      textArea.append("\nConsole reports an Internal error.");
      textArea.append("The error is: " + e.getMessage());
      // Need to uncomment this to ensure that line tally is synched.
      // lines += 2;
      stderr.println("Console reports an Internal error.\nThe error is: "
              + e);
    }

    // just for testing (Throw a Nullpointer after 1 second)
    if (Thread.currentThread() == errorThrower)
    {
      try
      {
        this.wait(1000);
      } catch (InterruptedException ie)
      {
      }
      throw new NullPointerException(
              "Application test: throwing an NullPointerException It should arrive at the console");
    }
  }

  private void appendToTextArea(final String input)
  {
    if (updateConsole == false)
    {
      // do nothing;
      return;
    }
    long time = System.nanoTime();
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        displayPipe.append(input); // change to stringBuffer
        // displayPipe.flush();

      }
    });
    // stderr.println("Time taken to Spawnappend:\t" + (System.nanoTime() -
    // time)
    // + " ns");
  }

  private String header = null;

  private boolean updateConsole = false;

  private synchronized void trimBuffer(boolean clear)
  {
    if (header == null && textArea.getLineCount() > 5)
    {
      try
      {
        header = textArea.getText(0, textArea.getLineStartOffset(5))
                + "\nTruncated...\n";
      } catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    // trim the buffer
    int tlength = textArea.getDocument().getLength();
    if (header != null)
    {
      if (clear || (tlength > byteslim))
      {
        try
        {
          if (!clear)
          {
            long time = System.nanoTime();
            textArea.replaceRange(header, 0, tlength - bytescut);
            // stderr.println("Time taken to cut:\t"
            // + (System.nanoTime() - time) + " ns");
          }
          else
          {
            textArea.setText(header);
          }
        } catch (Exception e)
        {
          e.printStackTrace();
        }
        // lines = textArea.getLineCount();
      }
    }

  }

  public synchronized String readLine(PipedInputStream in)
          throws IOException
  {
    String input = "";
    int lp = -1;
    do
    {
      int available = in.available();
      if (available == 0)
        break;
      byte b[] = new byte[available];
      in.read(b);
      input = input + new String(b, 0, b.length);
      // counts lines - we don't do this for speed.
      // while ((lp = input.indexOf("\n", lp + 1)) > -1)
      // {
      // lines++;
      // }
    } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
    return input;
  }

  public static void main(String[] arg)
  {
    new Console().test(); // create console with not reference

  }

  public void setVisible(boolean selected)
  {
    frame.setVisible(selected);
    if (selected == true)
    {
      redirectStreams();
      updateConsole = true;
      frame.toFront();
    }
    else
    {
      unredirectStreams();
      updateConsole = false;
    }
  }

  public Rectangle getBounds()
  {
    if (frame != null)
    {
      return frame.getBounds();
    }
    return null;
  }

  /**
   * set the banner that appears at the top of the console output
   * 
   * @param string
   */
  public void setHeader(String string)
  {
    header = string;
    if (header.charAt(header.length() - 1) != '\n')
    {
      header += "\n";
    }
    textArea.insert(header, 0);
  }

  /**
   * get the banner
   * 
   * @return
   */
  public String getHeader()
  {
    return header;
  }
}
