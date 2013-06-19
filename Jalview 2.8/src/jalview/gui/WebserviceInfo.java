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

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import jalview.jbgui.*;
import jalview.ws.WSClientI;

/**
 * Base class for web service client thread and gui TODO: create StAX parser to
 * extract html body content reliably when preparing html formatted job statuses
 * 
 * @author $author$
 * @version $Revision$
 */
public class WebserviceInfo extends GWebserviceInfo implements
        HyperlinkListener, IProgressIndicator
{

  /** Job is Queued */
  public static final int STATE_QUEUING = 0;

  /** Job is Running */
  public static final int STATE_RUNNING = 1;

  /** Job has finished with no errors */
  public static final int STATE_STOPPED_OK = 2;

  /** Job has been cancelled with no errors */
  public static final int STATE_CANCELLED_OK = 3;

  /** job has stopped because of some error */
  public static final int STATE_STOPPED_ERROR = 4;

  /** job has failed because of some unavoidable service interruption */
  public static final int STATE_STOPPED_SERVERERROR = 5;

  int currentStatus = STATE_QUEUING;

  Image image;

  int angle = 0;

  String title = "";

  jalview.ws.WSClientI thisService;

  boolean serviceIsCancellable;

  JInternalFrame frame;

  JTabbedPane subjobs = null;

  java.util.Vector jobPanes = null;

  private boolean serviceCanMergeResults = false;

  private boolean viewResultsImmediatly = true;

  /**
   * Get
   * 
   * @param flag
   *          to indicate if results will be shown in a new window as soon as
   *          they are available.
   */
  public boolean isViewResultsImmediatly()
  {
    return viewResultsImmediatly;
  }

  /**
   * Set
   * 
   * @param flag
   *          to indicate if results will be shown in a new window as soon as
   *          they are available.
   */
  public void setViewResultsImmediatly(boolean viewResultsImmediatly)
  {
    this.viewResultsImmediatly = viewResultsImmediatly;
  }

  private StyleSheet getStyleSheet(HTMLEditorKit editorKit)
  {

    // Copied blatantly from
    // http://www.velocityreviews.com/forums/t132265-string-into-htmldocument.html
    StyleSheet myStyleSheet = new StyleSheet();

    myStyleSheet.addStyleSheet(editorKit.getStyleSheet());

    editorKit.setStyleSheet(myStyleSheet);

    /*
     * Set the style sheet rules here by reading them from the constants
     * interface.
     */
    /*
     * for (int ix=0; ix<CSS_RULES.length; ix++) {
     * 
     * myStyleSheet.addRule(CSS_RULES[ix]);
     * 
     * }
     */
    return myStyleSheet;

  }

  // tabbed or not
  public synchronized int addJobPane()
  {
    JScrollPane jobpane = new JScrollPane();
    JComponent _progressText;
    if (renderAsHtml)
    {
      JEditorPane progressText = new JEditorPane("text/html", "");
      progressText.addHyperlinkListener(this);
      _progressText = progressText;
      // progressText.setFont(new java.awt.Font("Verdana", 0, 10));
      // progressText.setBorder(null);
      progressText.setEditable(false);
      /*
       * HTMLEditorKit myEditorKit = new HTMLEditorKit();
       * 
       * StyleSheet myStyleSheet = getStyleSheet(myEditorKit);
       * 
       * HTMLDocument tipDocument = (HTMLDocument)
       * (myEditorKit.createDefaultDocument());
       * 
       * progressText.setDocument(tipDocument);
       */progressText.setText("<html><h1>WS Job</h1></html>");
    }
    else
    {
      JTextArea progressText = new JTextArea();
      _progressText = progressText;

      progressText.setFont(new java.awt.Font("Verdana", 0, 10));
      progressText.setBorder(null);
      progressText.setEditable(false);
      progressText.setText("WS Job");
      progressText.setLineWrap(true);
      progressText.setWrapStyleWord(true);
    }
    jobpane.setName("JobPane");
    jobpane.getViewport().add(_progressText, null);
    jobpane.setBorder(null);
    if (jobPanes == null)
    {
      jobPanes = new Vector();
    }
    int newpane = jobPanes.size();
    jobPanes.add(jobpane);

    if (newpane == 0)
    {
      this.add(jobpane, BorderLayout.CENTER);
    }
    else
    {
      if (newpane == 1)
      {
        // revert to a tabbed pane.
        JScrollPane firstpane;
        this.remove(firstpane = (JScrollPane) jobPanes.get(0));
        subjobs = new JTabbedPane();
        this.add(subjobs, BorderLayout.CENTER);
        subjobs.add(firstpane);
        subjobs.setTitleAt(0, firstpane.getName());
      }
      subjobs.add(jobpane);
    }
    return newpane; // index for accessor methods below
  }

  /**
   * Creates a new WebserviceInfo object.
   * 
   * @param title
   *          short name and job type
   * @param info
   *          reference or other human readable description
   */
  public WebserviceInfo(String title, String info)
  {
    init(title, info, 520, 500);
  }

  /**
   * Creates a new WebserviceInfo object.
   * 
   * @param title
   *          DOCUMENT ME!
   * @param info
   *          DOCUMENT ME!
   * @param width
   *          DOCUMENT ME!
   * @param height
   *          DOCUMENT ME!
   */
  public WebserviceInfo(String title, String info, int width, int height)
  {
    init(title, info, width, height);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public jalview.ws.WSClientI getthisService()
  {
    return thisService;
  }

  /**
   * Update state of GUI based on client capabilities (like whether the job is
   * cancellable, whether the 'merge results' button is shown.
   * 
   * @param newservice
   *          service client to query for capabilities
   */
  public void setthisService(jalview.ws.WSClientI newservice)
  {
    thisService = newservice;
    serviceIsCancellable = newservice.isCancellable();
    frame.setClosable(!serviceIsCancellable);
    serviceCanMergeResults = newservice.canMergeResults();
    rebuildButtonPanel();
  }

  private void rebuildButtonPanel()
  {
    if (buttonPanel != null)
    {
      buttonPanel.removeAll();
      if (serviceIsCancellable)
      {
        buttonPanel.add(cancel);
        frame.setClosable(false);
      }
      else
      {
        frame.setClosable(true);
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param title
   *          DOCUMENT ME!
   * @param info
   *          DOCUMENT ME!
   * @param width
   *          DOCUMENT ME!
   * @param height
   *          DOCUMENT ME!
   */
  void init(String title, String info, int width, int height)
  {
    frame = new JInternalFrame();
    frame.setContentPane(this);
    Desktop.addInternalFrame(frame, title, width, height);
    frame.setClosable(false);

    this.title = title;
    setInfoText(info);

    java.net.URL url = getClass().getResource(
            "/images/Jalview_Logo_small.png");
    image = java.awt.Toolkit.getDefaultToolkit().createImage(url);

    MediaTracker mt = new MediaTracker(this);
    mt.addImage(image, 0);

    try
    {
      mt.waitForID(0);
    } catch (Exception ex)
    {
    }

    AnimatedPanel ap = new AnimatedPanel();
    titlePanel.add(ap, BorderLayout.CENTER);

    Thread thread = new Thread(ap);
    thread.start();
    final WebserviceInfo thisinfo = this;
    frame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter()
    {
      public void internalFrameClosed(
              javax.swing.event.InternalFrameEvent evt)
      {
        // System.out.println("Shutting down webservice client");
        WSClientI service = thisinfo.getthisService();
        if (service != null && service.isCancellable())
        {
          service.cancelJob();
        }
      };
    });
    frame.validate();

  }

  /**
   * DOCUMENT ME!
   * 
   * @param status
   *          integer status from state constants
   */
  public void setStatus(int status)
  {
    currentStatus = status;
  }

  /**
   * subjob status indicator
   * 
   * @param jobpane
   * @param status
   */
  public void setStatus(int jobpane, int status)
  {
    if (jobpane < 0 || jobpane >= jobPanes.size())
    {
      throw new Error("setStatus called for non-existent job pane."
              + jobpane);
    }
    switch (status)
    {
    case STATE_QUEUING:
      setProgressName(jobpane + " - QUEUED", jobpane);
      break;
    case STATE_RUNNING:
      setProgressName(jobpane + " - RUNNING", jobpane);
      break;
    case STATE_STOPPED_OK:
      setProgressName(jobpane + " - FINISHED", jobpane);
      break;
    case STATE_CANCELLED_OK:
      setProgressName(jobpane + " - CANCELLED", jobpane);
      break;
    case STATE_STOPPED_ERROR:
      setProgressName(jobpane + " - BROKEN", jobpane);
      break;
    case STATE_STOPPED_SERVERERROR:
      setProgressName(jobpane + " - ALERT", jobpane);
      break;
    default:
      setProgressName(jobpane + " - UNKNOWN STATE", jobpane);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getInfoText()
  {
    return infoText.getText();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param text
   *          DOCUMENT ME!
   */
  public void setInfoText(String text)
  {
    infoText.setText(text);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param text
   *          DOCUMENT ME!
   */
  public void appendInfoText(String text)
  {
    infoText.append(text);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getProgressText(int which)
  {
    if (jobPanes == null)
    {
      addJobPane();
    }
    if (renderAsHtml)
    {
      return ((JEditorPane) ((JScrollPane) jobPanes.get(which))
              .getViewport().getComponent(0)).getText();
    }
    else
    {
      return ((JTextArea) ((JScrollPane) jobPanes.get(which)).getViewport()
              .getComponent(0)).getText();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param text
   *          DOCUMENT ME!
   */
  public void setProgressText(int which, String text)
  {
    if (jobPanes == null)
    {
      addJobPane();
    }
    if (renderAsHtml)
    {
      ((JEditorPane) ((JScrollPane) jobPanes.get(which)).getViewport()
              .getComponent(0)).setText(ensureHtmlTagged(text));
    }
    else
    {
      ((JTextArea) ((JScrollPane) jobPanes.get(which)).getViewport()
              .getComponent(0)).setText(text);
    }
  }

  /**
   * extract content from &lt;body&gt; content &lt;/body&gt;
   * 
   * @param text
   * @param leaveFirst
   *          - set to leave the initial html tag intact
   * @param leaveLast
   *          - set to leave the final html tag intact
   * @return
   */
  private String getHtmlFragment(String text, boolean leaveFirst,
          boolean leaveLast)
  {
    if (text == null)
    {
      return null;
    }
    String lowertxt = text.toLowerCase();
    int htmlpos = leaveFirst ? -1 : lowertxt.indexOf("<body");

    int htmlend = leaveLast ? -1 : lowertxt.indexOf("</body");
    int htmlpose = lowertxt.indexOf(">", htmlpos), htmlende = lowertxt
            .indexOf(">", htmlend);
    if (htmlend == -1 && htmlpos == -1)
    {
      return text;
    }
    if (htmlend > -1)
    {
      return text.substring((htmlpos == -1 ? 0 : htmlpose + 1), htmlend);
    }
    return text.substring(htmlpos == -1 ? 0 : htmlpose + 1);
  }

  /**
   * very simple routine for adding/ensuring html tags are present in text.
   * 
   * @param text
   * @return properly html tag enclosed text
   */
  private String ensureHtmlTagged(String text)
  {
    if (text == null)
    {
      return "";
    }
    String lowertxt = text.toLowerCase();
    int htmlpos = lowertxt.indexOf("<body");
    int htmlend = lowertxt.indexOf("</body");
    int doctype = lowertxt.indexOf("<!doctype");
    int xmltype = lowertxt.indexOf("<?xml");
    if (htmlend == -1)
    {
      text = text + "</body></html>";
    }
    if (htmlpos > -1)
    {
      if ((doctype > -1 && htmlpos > doctype)
              || (xmltype > -1 && htmlpos > xmltype))
      {
        text = "<html><head></head><body>\n" + text.substring(htmlpos - 1);
      }
    }
    else
    {
      text = "<html><head></head><body>\n" + text;
    }
    if (text.indexOf("<meta") > -1)
    {
      System.err.println("HTML COntent: \n" + text
              + "<< END HTML CONTENT\n");

    }
    return text;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param text
   *          DOCUMENT ME!
   */
  public void appendProgressText(int which, String text)
  {
    if (jobPanes == null)
    {
      addJobPane();
    }
    if (renderAsHtml)
    {
      String txt = getHtmlFragment(
              ((JEditorPane) ((JScrollPane) jobPanes.get(which))
                      .getViewport().getComponent(0)).getText(), true,
              false);
      ((JEditorPane) ((JScrollPane) jobPanes.get(which)).getViewport()
              .getComponent(0)).setText(ensureHtmlTagged(txt
              + getHtmlFragment(text, false, true)));
    }
    else
    {
      ((JTextArea) ((JScrollPane) jobPanes.get(which)).getViewport()
              .getComponent(0)).append(text);
    }
  }

  /**
   * setProgressText(0, text)
   */
  public void setProgressText(String text)
  {
    setProgressText(0, text);
  }

  /**
   * appendProgressText(0, text)
   */
  public void appendProgressText(String text)
  {
    appendProgressText(0, text);
  }

  /**
   * getProgressText(0)
   */
  public String getProgressText()
  {
    return getProgressText(0);
  }

  /**
   * get the tab title for a subjob
   * 
   * @param which
   *          int
   * @return String
   */
  public String getProgressName(int which)
  {
    if (jobPanes == null)
    {
      addJobPane();
    }
    if (subjobs != null)
    {
      return subjobs.getTitleAt(which);
    }
    else
    {
      return ((JScrollPane) jobPanes.get(which)).getViewport()
              .getComponent(0).getName();
    }
  }

  /**
   * set the tab title for a subjob
   * 
   * @param name
   *          String
   * @param which
   *          int
   */
  public void setProgressName(String name, int which)
  {
    if (subjobs != null)
    {
      subjobs.setTitleAt(which, name);
      subjobs.revalidate();
      subjobs.repaint();
    }
    JScrollPane c = (JScrollPane) jobPanes.get(which);
    c.getViewport().getComponent(0).setName(name);
    c.repaint();
  }

  /**
   * Gui action for cancelling the current job, if possible.
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void cancel_actionPerformed(ActionEvent e)
  {
    if (!serviceIsCancellable)
    {
      // JBPNote : TODO: We should REALLY just tell the WSClientI to cancel
      // anyhow - it has to stop threads and clean up
      // JBPNote : TODO: Instead of a warning, we should have an optional 'Are
      // you sure?' prompt
      warnUser("This job cannot be cancelled.\nJust close the window.",
              "Cancel job");
    }
    else
    {
      thisService.cancelJob();
    }
    frame.setClosable(true);
  }

  /**
   * Spawns a thread that pops up a warning dialog box with the given message
   * and title.
   * 
   * @param message
   * @param title
   */
  public void warnUser(final String message, final String title)
  {
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        JOptionPane.showInternalMessageDialog(Desktop.desktop, message,
                title, JOptionPane.WARNING_MESSAGE);

      }
    });
  }

  /**
   * Set up GUI for user to get at results - and possibly automatically display
   * them if viewResultsImmediatly is set.
   */
  public void setResultsReady()
  {
    frame.setClosable(true);
    buttonPanel.remove(cancel);
    buttonPanel.add(showResultsNewFrame);
    if (serviceCanMergeResults)
    {
      buttonPanel.add(mergeResults);
      buttonPanel.setLayout(new GridLayout(2, 1, 5, 5));
    }
    buttonPanel.validate();
    validate();
    if (viewResultsImmediatly)
    {
      showResultsNewFrame.doClick();
    }
  }

  /**
   * called when job has finished but no result objects can be passed back to
   * user
   */
  public void setFinishedNoResults()
  {
    frame.setClosable(true);
    buttonPanel.remove(cancel);
    buttonPanel.validate();
    validate();
  }

  class AnimatedPanel extends JPanel implements Runnable
  {
    long startTime = 0;

    BufferedImage offscreen;

    public void run()
    {
      startTime = System.currentTimeMillis();

      while (currentStatus < STATE_STOPPED_OK)
      {
        try
        {
          Thread.sleep(50);

          int units = (int) ((System.currentTimeMillis() - startTime) / 10f);
          angle += units;
          angle %= 360;
          startTime = System.currentTimeMillis();

          if (currentStatus >= STATE_STOPPED_OK)
          {
            angle = 0;
          }

          repaint();
        } catch (Exception ex)
        {
        }
      }

      cancel.setEnabled(false);
    }

    void drawPanel()
    {
      if (offscreen == null || offscreen.getWidth(this) != getWidth()
              || offscreen.getHeight(this) != getHeight())
      {
        offscreen = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_ARGB);
      }

      Graphics2D g = (Graphics2D) offscreen.getGraphics();

      g.setColor(Color.white);
      g.fillRect(0, 0, getWidth(), getHeight());

      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.setColor(Color.black);

      switch (currentStatus)
      {
      case STATE_QUEUING:
        g.drawString(title.concat(" - queuing"), 60, 30);

        break;

      case STATE_RUNNING:
        g.drawString(title.concat(" - running"), 60, 30);

        break;

      case STATE_STOPPED_OK:
        g.drawString(title.concat(" - complete"), 60, 30);

        break;

      case STATE_CANCELLED_OK:
        g.drawString(title.concat(" - job cancelled!"), 60, 30);

        break;

      case STATE_STOPPED_ERROR:
        g.drawString(title.concat(" - job error!"), 60, 30);

        break;

      case STATE_STOPPED_SERVERERROR:
        g.drawString(title.concat(" - Server Error! (try later)"), 60, 30);

        break;
      }

      if (image != null)
      {
        int x = image.getWidth(this) / 2, y = image.getHeight(this) / 2;
        g.rotate(Math.toRadians(angle), 10 + x, 10 + y);
        g.drawImage(image, 10, 10, this);
        g.rotate(-Math.toRadians(angle), 10 + x, 10 + y);
      }
    }

    public void paintComponent(Graphics g1)
    {
      drawPanel();

      g1.drawImage(offscreen, 0, 0, this);
    }
  }

  boolean renderAsHtml = false;

  public void setRenderAsHtml(boolean b)
  {
    renderAsHtml = b;
  }

  public void hyperlinkUpdate(HyperlinkEvent e)
  {
    Desktop.hyperlinkUpdate(e);
  }

  // methods for implementing IProgressIndicator
  // need to refactor to a reusable stub class
  Hashtable progressBars, progressBarHandlers;

  /*
   * (non-Javadoc)
   * 
   * @see jalview.gui.IProgressIndicator#setProgressBar(java.lang.String, long)
   */
  @Override
  public void setProgressBar(String message, long id)
  {
    if (progressBars == null)
    {
      progressBars = new Hashtable();
      progressBarHandlers = new Hashtable();
    }

    JPanel progressPanel;
    Long lId = new Long(id);
    GridLayout layout = (GridLayout) statusPanel.getLayout();
    if (progressBars.get(lId) != null)
    {
      progressPanel = (JPanel) progressBars.get(new Long(id));
      statusPanel.remove(progressPanel);
      progressBars.remove(lId);
      progressPanel = null;
      if (message != null)
      {
        statusBar.setText(message);
      }
      if (progressBarHandlers.contains(lId))
      {
        progressBarHandlers.remove(lId);
      }
      layout.setRows(layout.getRows() - 1);
    }
    else
    {
      progressPanel = new JPanel(new BorderLayout(10, 5));

      JProgressBar progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);

      progressPanel.add(new JLabel(message), BorderLayout.WEST);
      progressPanel.add(progressBar, BorderLayout.CENTER);

      layout.setRows(layout.getRows() + 1);
      statusPanel.add(progressPanel);

      progressBars.put(lId, progressPanel);
    }
    // update GUI
    // setMenusForViewport();
    validate();
  }

  @Override
  public void registerHandler(final long id,
          final IProgressIndicatorHandler handler)
  {
    if (progressBarHandlers == null || !progressBars.contains(new Long(id)))
    {
      throw new Error(
              "call setProgressBar before registering the progress bar's handler.");
    }
    progressBarHandlers.put(new Long(id), handler);
    final JPanel progressPanel = (JPanel) progressBars.get(new Long(id));
    if (handler.canCancel())
    {
      JButton cancel = new JButton("Cancel");
      final IProgressIndicator us = this;
      cancel.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent e)
        {
          handler.cancelActivity(id);
          us.setProgressBar(
                  "Cancelled "
                          + ((JLabel) progressPanel.getComponent(0))
                                  .getText(), id);
        }
      });
      progressPanel.add(cancel, BorderLayout.EAST);
    }
  }

  /**
   * 
   * @return true if any progress bars are still active
   */
  @Override
  public boolean operationInProgress()
  {
    if (progressBars != null && progressBars.size() > 0)
    {
      return true;
    }
    return false;
  }
}
