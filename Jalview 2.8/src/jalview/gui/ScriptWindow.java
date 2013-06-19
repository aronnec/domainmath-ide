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

import org.jmol.api.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.Vector;

import org.jmol.i18n.GT;
import org.jmol.util.Logger;
import org.jmol.util.CommandHistory;

// TODO: this class is copied in from jmol 11.0.2 - upgrade to 12.0.2 ?
public final class ScriptWindow extends JPanel implements ActionListener,
        EnterListener
{

  private ConsoleTextPane console;

  private JButton closeButton;

  private JButton runButton;

  private JButton haltButton;

  private JButton clearButton;

  private JButton historyButton;

  private JButton stateButton;

  JmolViewer viewer;

  AppJmol appJmol;

  public ScriptWindow(AppJmol appJmol)
  {
    this.viewer = appJmol.jmb.viewer;
    this.appJmol = appJmol;

    setLayout(new BorderLayout());

    console = new ConsoleTextPane(this);

    console.setPrompt();
    add(new JScrollPane(console), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    add(buttonPanel, BorderLayout.SOUTH);

    runButton = new JButton(GT._("Run"));
    haltButton = new JButton(GT._("Halt"));
    runButton.addActionListener(this);
    // buttonPanel.add(runButton);
    haltButton.addActionListener(this);
    // buttonPanel.add(haltButton);
    haltButton.setEnabled(false);

    clearButton = new JButton(GT._("Clear"));
    clearButton.addActionListener(this);
    buttonPanel.add(clearButton);

    historyButton = new JButton(GT._("History"));
    historyButton.addActionListener(this);
    buttonPanel.add(historyButton);

    stateButton = new JButton(GT._("State"));
    stateButton.addActionListener(this);
    buttonPanel.add(stateButton);

    closeButton = new JButton(GT._("Close"));
    closeButton.addActionListener(this);
    buttonPanel.add(closeButton);

    for (int i = 0; i < buttonPanel.getComponentCount(); i++)
    {
      // ((JButton)buttonPanel.getComponent(i))
      // .setMargin(new Insets(0, 0, 0, 0));
    }

  }

  public void sendConsoleEcho(String strEcho)
  {
    if (strEcho != null && !isError)
    {

      console.outputEcho(strEcho);

    }
    setError(false);
  }

  boolean isError = false;

  void setError(boolean TF)
  {
    isError = TF;
    // if (isError)
    // console.recallCommand(true);
  }

  public void sendConsoleMessage(String strStatus)
  {
    if (strStatus == null)
    {
      console.clearContent();
      console.outputStatus("");
    }
    else if (strStatus.indexOf("ERROR:") >= 0)
    {
      console.outputError(strStatus);
      isError = true;
    }
    else if (!isError)
    {
      console.outputStatus(strStatus);
    }
  }

  public void notifyScriptTermination(String strMsg, int msWalltime)
  {
    if (strMsg != null && strMsg.indexOf("ERROR") >= 0)
    {
      console.outputError(strMsg);
    }
    runButton.setEnabled(true);
    haltButton.setEnabled(false);
  }

  public void enterPressed()
  {
    runButton.doClick(100);
    // executeCommand();
  }

  class ExecuteCommandThread extends Thread
  {

    String strCommand;

    ExecuteCommandThread(String command)
    {
      strCommand = command;
    }

    public void run()
    {
      try
      {
        executeCommand(strCommand);
      } catch (Exception ie)
      {
        Logger.debug("execution command interrupted!" + ie);
      }
    }
  }

  ExecuteCommandThread execThread;

  void executeCommandAsThread()
  {
    String strCommand = console.getCommandString().trim();
    if (strCommand.length() > 0)
    {
      execThread = new ExecuteCommandThread(strCommand);
      execThread.start();
    }
  }

  void executeCommand(String strCommand)
  {
    boolean doWait;
    setError(false);
    console.appendNewline();
    console.setPrompt();
    if (strCommand.length() > 0)
    {
      String strErrorMessage = null;
      doWait = (strCommand.indexOf("WAIT ") == 0);
      if (doWait)
      { // for testing, mainly
        // demonstrates using the statusManager system.
        runButton.setEnabled(false);
        haltButton.setEnabled(true);

        Vector info = (Vector) viewer
                .scriptWaitStatus(strCommand.substring(5),
                        "+fileLoaded,+scriptStarted,+scriptStatus,+scriptEcho,+scriptTerminated");
        runButton.setEnabled(true);
        haltButton.setEnabled(false);
        /*
         * info = [ statusRecortSet0, statusRecortSet1, statusRecortSet2, ...]
         * statusRecordSet = [ statusRecord0, statusRecord1, statusRecord2, ...]
         * statusRecord = [int msgPtr, String statusName, int intInfo, String
         * msg]
         */
        for (int i = 0; i < info.size(); i++)
        {
          Vector statusRecordSet = (Vector) info.get(i);
          for (int j = 0; j < statusRecordSet.size(); j++)
          {
            Vector statusRecord = (Vector) statusRecordSet.get(j);
            Logger.info("msg#=" + statusRecord.get(0) + " "
                    + statusRecord.get(1) + " intInfo="
                    + statusRecord.get(2) + " stringInfo="
                    + statusRecord.get(3));
          }
        }
        console.appendNewline();
      }
      else
      {
        boolean isScriptExecuting = viewer.isScriptExecuting();
        if (viewer.checkHalt(strCommand, true))
          strErrorMessage = (isScriptExecuting ? "string execution halted with "
                  + strCommand
                  : "no script was executing");
        else
          strErrorMessage = "";// viewer.scriptCheck(strCommand);
        // the problem is that scriptCheck is synchronized, so these might get
        // backed up.
        if (strErrorMessage != null && strErrorMessage.length() > 0)
        {
          console.outputError(strErrorMessage);
        }
        else
        {
          // runButton.setEnabled(false);
          haltButton.setEnabled(true);
          viewer.script(strCommand);
        }
      }
    }
    console.grabFocus();
  }

  public void actionPerformed(ActionEvent e)
  {
    Object source = e.getSource();
    if (source == closeButton)
    {
      // appJmol.showConsole(false);
    }
    else if (source == runButton)
    {
      executeCommandAsThread();
    }
    else if (source == clearButton)
    {
      console.clearContent();
    }
    else if (source == historyButton)
    {
      console.clearContent(viewer.getSetHistory(Integer.MAX_VALUE));
    }
    else if (source == stateButton)
    {
      console.clearContent(viewer.getStateInfo());
    }
    else if (source == haltButton)
    {
      viewer.haltScriptExecution();
    }
    console.grabFocus(); // always grab the focus (e.g., after clear)
  }
}

class ConsoleTextPane extends JTextPane
{

  ConsoleDocument consoleDoc;

  EnterListener enterListener;

  JmolViewer viewer;

  ConsoleTextPane(ScriptWindow scriptWindow)
  {
    super(new ConsoleDocument());
    consoleDoc = (ConsoleDocument) getDocument();
    consoleDoc.setConsoleTextPane(this);
    this.enterListener = (EnterListener) scriptWindow;
    this.viewer = scriptWindow.viewer;
  }

  public String getCommandString()
  {
    String cmd = consoleDoc.getCommandString();
    return cmd;
  }

  public void setPrompt()
  {
    consoleDoc.setPrompt();
  }

  public void appendNewline()
  {
    consoleDoc.appendNewline();
  }

  public void outputError(String strError)
  {
    consoleDoc.outputError(strError);
  }

  public void outputErrorForeground(String strError)
  {
    consoleDoc.outputErrorForeground(strError);
  }

  public void outputEcho(String strEcho)
  {
    consoleDoc.outputEcho(strEcho);
  }

  public void outputStatus(String strStatus)
  {
    consoleDoc.outputStatus(strStatus);
  }

  public void enterPressed()
  {
    if (enterListener != null)
      enterListener.enterPressed();
  }

  public void clearContent()
  {
    clearContent(null);
  }

  public void clearContent(String text)
  {
    consoleDoc.clearContent();
    if (text != null)
      consoleDoc.outputEcho(text);
    setPrompt();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.Component#processKeyEvent(java.awt.event.KeyEvent)
   */

  /**
   * Custom key event processing for command 0 implementation.
   * 
   * Captures key up and key down strokes to call command history and redefines
   * the same events with control down to allow caret vertical shift.
   * 
   * @see java.awt.Component#processKeyEvent(java.awt.event.KeyEvent)
   */
  protected void processKeyEvent(KeyEvent ke)
  {
    // Id Control key is down, captures events does command
    // history recall and inhibits caret vertical shift.
    if (ke.getKeyCode() == KeyEvent.VK_UP
            && ke.getID() == KeyEvent.KEY_PRESSED && !ke.isControlDown())
    {
      recallCommand(true);
    }
    else if (ke.getKeyCode() == KeyEvent.VK_DOWN
            && ke.getID() == KeyEvent.KEY_PRESSED && !ke.isControlDown())
    {
      recallCommand(false);
    }
    // If Control key is down, redefines the event as if it
    // where a key up or key down stroke without modifiers.
    // This allows to move the caret up and down
    // with no command history recall.
    else if ((ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_UP)
            && ke.getID() == KeyEvent.KEY_PRESSED && ke.isControlDown())
    {
      super.processKeyEvent(new KeyEvent((Component) ke.getSource(), ke
              .getID(), ke.getWhen(), 0, // No modifiers
              ke.getKeyCode(), ke.getKeyChar(), ke.getKeyLocation()));
    }
    // Standard processing for other events.
    else
    {
      super.processKeyEvent(ke);
      // check command for compiler-identifyable syntax issues
      // this may have to be taken out if people start complaining
      // that only some of the commands are being checked
      // that is -- that the script itself is not being fully checked

      // not perfect -- help here?
      if (ke.getID() == KeyEvent.KEY_RELEASED
              && (ke.getKeyCode() > KeyEvent.VK_DOWN)
              || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        checkCommand();
    }
  }

  /**
   * Recall command history.
   * 
   * @param up
   *          - history up or down
   */
  void recallCommand(boolean up)
  {
    String cmd = viewer.getSetHistory(up ? -1 : 1);
    if (cmd == null)
    {
      return;
    }
    try
    {
      if (cmd.endsWith(CommandHistory.ERROR_FLAG))
      {
        cmd = cmd.substring(0, cmd.indexOf(CommandHistory.ERROR_FLAG));
        consoleDoc.replaceCommand(cmd, true);
      }
      else
      {
        consoleDoc.replaceCommand(cmd, false);
      }
    } catch (BadLocationException e)
    {
      e.printStackTrace();
    }
  }

  void checkCommand()
  {
    String strCommand = consoleDoc.getCommandString();
    if (strCommand.length() == 0)
      return;
    consoleDoc
            .colorCommand(viewer.scriptCheck(strCommand) == null ? consoleDoc.attUserInput
                    : consoleDoc.attError);
  }

}

class ConsoleDocument extends DefaultStyledDocument
{

  ConsoleTextPane consoleTextPane;

  SimpleAttributeSet attError;

  SimpleAttributeSet attEcho;

  SimpleAttributeSet attPrompt;

  SimpleAttributeSet attUserInput;

  SimpleAttributeSet attStatus;

  ConsoleDocument()
  {
    super();

    attError = new SimpleAttributeSet();
    StyleConstants.setForeground(attError, Color.red);

    attPrompt = new SimpleAttributeSet();
    StyleConstants.setForeground(attPrompt, Color.magenta);

    attUserInput = new SimpleAttributeSet();
    StyleConstants.setForeground(attUserInput, Color.black);

    attEcho = new SimpleAttributeSet();
    StyleConstants.setForeground(attEcho, Color.blue);
    StyleConstants.setBold(attEcho, true);

    attStatus = new SimpleAttributeSet();
    StyleConstants.setForeground(attStatus, Color.black);
    StyleConstants.setItalic(attStatus, true);
  }

  void setConsoleTextPane(ConsoleTextPane consoleTextPane)
  {
    this.consoleTextPane = consoleTextPane;
  }

  Position positionBeforePrompt; // starts at 0, so first time isn't tracked

  // (at least on Mac OS X)

  Position positionAfterPrompt; // immediately after $, so this will track

  int offsetAfterPrompt; // only still needed for the insertString override and

  // replaceCommand

  /**
   * Removes all content of the script window, and add a new prompt.
   */
  void clearContent()
  {
    try
    {
      super.remove(0, getLength());
    } catch (BadLocationException exception)
    {
      System.out.println("Could not clear script window content: "
              + exception.getMessage());
    }
  }

  void setPrompt()
  {
    try
    {
      super.insertString(getLength(), "$ ", attPrompt);
      setOffsetPositions();
      consoleTextPane.setCaretPosition(offsetAfterPrompt);
    } catch (BadLocationException e)
    {
      e.printStackTrace();
    }
  }

  void setOffsetPositions()
  {
    try
    {
      offsetAfterPrompt = getLength();
      positionBeforePrompt = createPosition(offsetAfterPrompt - 2);
      // after prompt should be immediately after $ otherwise tracks the end
      // of the line (and no command will be found) at least on Mac OS X it did.
      positionAfterPrompt = createPosition(offsetAfterPrompt - 1);
    } catch (BadLocationException e)
    {
      e.printStackTrace();
    }
  }

  void setNoPrompt()
  {
    try
    {
      offsetAfterPrompt = getLength();
      positionAfterPrompt = positionBeforePrompt = createPosition(offsetAfterPrompt);
      consoleTextPane.setCaretPosition(offsetAfterPrompt);
    } catch (BadLocationException e)
    {
      e.printStackTrace();
    }
  }

  // it looks like the positionBeforePrompt does not track when it started out
  // as 0
  // and a insertString at location 0 occurs. It may be better to track the
  // position after the prompt in stead
  void outputBeforePrompt(String str, SimpleAttributeSet attribute)
  {
    try
    {
      int pt = consoleTextPane.getCaretPosition();
      Position caretPosition = createPosition(pt);
      pt = positionBeforePrompt.getOffset();
      super.insertString(pt, str + "\n", attribute);
      setOffsetPositions();
      pt = caretPosition.getOffset();
      consoleTextPane.setCaretPosition(pt);
    } catch (BadLocationException e)
    {
      e.printStackTrace();
    }
  }

  void outputError(String strError)
  {
    outputBeforePrompt(strError, attError);
  }

  void outputErrorForeground(String strError)
  {
    try
    {
      super.insertString(getLength(), strError + "\n", attError);
      consoleTextPane.setCaretPosition(getLength());
    } catch (BadLocationException e)
    {
      e.printStackTrace();

    }
  }

  void outputEcho(String strEcho)
  {
    outputBeforePrompt(strEcho, attEcho);
  }

  void outputStatus(String strStatus)
  {
    outputBeforePrompt(strStatus, attStatus);
  }

  void appendNewline()
  {
    try
    {
      super.insertString(getLength(), "\n", attUserInput);
      consoleTextPane.setCaretPosition(getLength());
    } catch (BadLocationException e)
    {
      e.printStackTrace();
    }
  }

  // override the insertString to make sure everything typed ends up at the end
  // or in the 'command line' using the proper font, and the newline is
  // processed.
  public void insertString(int offs, String str, AttributeSet a)
          throws BadLocationException
  {
    int ichNewline = str.indexOf('\n');
    if (ichNewline > 0)
      str = str.substring(0, ichNewline);
    if (ichNewline != 0)
    {
      if (offs < offsetAfterPrompt)
      {
        offs = getLength();
      }
      super.insertString(offs, str, a == attError ? a : attUserInput);
      consoleTextPane.setCaretPosition(offs + str.length());
    }
    if (ichNewline >= 0)
    {
      consoleTextPane.enterPressed();
    }
  }

  String getCommandString()
  {
    String strCommand = "";
    try
    {
      int cmdStart = positionAfterPrompt.getOffset();
      strCommand = getText(cmdStart, getLength() - cmdStart);
      while (strCommand.length() > 0 && strCommand.charAt(0) == ' ')
        strCommand = strCommand.substring(1);
    } catch (BadLocationException e)
    {
      e.printStackTrace();
    }
    return strCommand;
  }

  public void remove(int offs, int len) throws BadLocationException
  {
    if (offs < offsetAfterPrompt)
    {
      len -= offsetAfterPrompt - offs;
      if (len <= 0)
        return;
      offs = offsetAfterPrompt;
    }
    super.remove(offs, len);
    // consoleTextPane.setCaretPosition(offs);
  }

  public void replace(int offs, int length, String str, AttributeSet attrs)
          throws BadLocationException
  {
    if (offs < offsetAfterPrompt)
    {
      if (offs + length < offsetAfterPrompt)
      {
        offs = getLength();
        length = 0;
      }
      else
      {
        length -= offsetAfterPrompt - offs;
        offs = offsetAfterPrompt;
      }
    }
    super.replace(offs, length, str, attrs);
    // consoleTextPane.setCaretPosition(offs + str.length());
  }

  /**
   * Replaces current command on script.
   * 
   * @param newCommand
   *          new command value
   * @param isError
   *          true to set error color ends with #??
   * 
   * @throws BadLocationException
   */
  void replaceCommand(String newCommand, boolean isError)
          throws BadLocationException
  {
    if (positionAfterPrompt == positionBeforePrompt)
      return;
    replace(offsetAfterPrompt, getLength() - offsetAfterPrompt, newCommand,
            isError ? attError : attUserInput);
  }

  void colorCommand(SimpleAttributeSet att)
  {
    if (positionAfterPrompt == positionBeforePrompt)
      return;
    setCharacterAttributes(offsetAfterPrompt, getLength()
            - offsetAfterPrompt, att, true);
  }
}

interface EnterListener
{
  public void enterPressed();
}
