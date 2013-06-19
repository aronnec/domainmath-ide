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

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * From http://textareaappender.zcage.com/ the means to capture the logs, too.
 * Simple example of creating a Log4j appender that will write to a JTextArea.
 */
public class JalviewAppender extends WriterAppender
{

  static private JTextArea jTextArea = null;

  /** Set the target JTextArea for the logging information to appear. */
  static public void setTextArea(JTextArea jTextArea)
  {
    JalviewAppender.jTextArea = jTextArea;
  }

  /**
   * Format and then append the loggingEvent to the stored JTextArea.
   */
  public void append(LoggingEvent loggingEvent)
  {
    final String message = this.layout.format(loggingEvent);

    // Append formatted message to textarea using the Swing Thread.
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        if (jTextArea != null)
        {
          jTextArea.append(message);
        }
      }
    });
  }
}
