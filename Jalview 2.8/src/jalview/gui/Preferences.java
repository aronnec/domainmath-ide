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

import javax.swing.*;

import jalview.bin.*;
import jalview.io.*;
import jalview.jbgui.*;
import jalview.schemes.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class Preferences extends GPreferences
{

  /**
   * Holds name and link separated with | character. Sequence ID must be
   * $SEQUENCE_ID$ or $SEQUENCE_ID=/.possible | chars ./=$
   */
  public static Vector sequenceURLLinks;

  /**
   * Holds name and link separated with | character. Sequence IDS and Sequences
   * must be $SEQUENCEIDS$ or $SEQUENCEIDS=/.possible | chars ./=$ and
   * $SEQUENCES$ or $SEQUENCES=/.possible | chars ./=$ and separation character
   * for first and second token specified after a pipe character at end |,|.
   * (TODO: proper escape for using | to separate ids or sequences
   */

  public static Vector groupURLLinks;
  static
  {
    String string = Cache
            .getDefault(
                    "SEQUENCE_LINKS",
                    "SRS|http://srs.ebi.ac.uk/srsbin/cgi-bin/wgetz?-newId+(([uniprot-all:$SEQUENCE_ID$]))+-view+SwissEntry");
    sequenceURLLinks = new Vector();

    try
    {
      StringTokenizer st = new StringTokenizer(string, "|");
      while (st.hasMoreElements())
      {
        String name = st.nextToken();
        String url = st.nextToken();
        // check for '|' within a regex
        int rxstart = url.indexOf("$SEQUENCE_ID$");
        while (rxstart == -1 && url.indexOf("/=$") == -1)
        {
          url = url + "|" + st.nextToken();
        }
        sequenceURLLinks.addElement(name + "|" + url);
      }
    } catch (Exception ex)
    {
      System.out.println(ex + "\nError parsing sequence links");
    }
    /**
     * TODO: reformulate groupURL encoding so two or more can be stored in the
     * .properties file as '|' separated strings
     */

    groupURLLinks = new Vector();
    // groupURLLinks.addElement("UNIPROT|EnVision2|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?tool=Jalview&workflow=Default&datasetName=JalviewIDs$DATASETID$&input=$SEQUENCEIDS$&inputType=0|,");
    // groupURLLinks.addElement("Seqs|EnVision2|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?tool=Jalview&workflow=Default&datasetName=JalviewSeqs$DATASETID$&input=$SEQUENCES=/([A-Za-z]+)+/=$&inputType=1|,");

  }

  Vector nameLinks, urlLinks;

  JInternalFrame frame;

  DasSourceBrowser dasSource;

  private WsPreferences wsPrefs;

  /**
   * Creates a new Preferences object.
   */
  public Preferences()
  {

    frame = new JInternalFrame();
    frame.setContentPane(this);
    dasSource = new DasSourceBrowser();
    dasPanel.add(dasSource, BorderLayout.CENTER);
    wsPrefs = new WsPreferences();
    wsPanel.add(wsPrefs, BorderLayout.CENTER);
    int width = 500, height = 420;
    if (new jalview.util.Platform().isAMac())
    {
      width = 570;
      height = 460;
    }

    Desktop.addInternalFrame(frame, "Preferences", width, height);
    frame.setMinimumSize(new Dimension(width, height));

    seqLimit.setSelected(Cache.getDefault("SHOW_JVSUFFIX", true));
    rightAlign.setSelected(Cache.getDefault("RIGHT_ALIGN_IDS", false));
    fullScreen.setSelected(Cache.getDefault("SHOW_FULLSCREEN", false));
    annotations.setSelected(Cache.getDefault("SHOW_ANNOTATIONS", true));

    conservation.setSelected(Cache.getDefault("SHOW_CONSERVATION", true));
    quality.setSelected(Cache.getDefault("SHOW_QUALITY", true));
    identity.setSelected(Cache.getDefault("SHOW_IDENTITY", true));
    openoverv.setSelected(Cache.getDefault("SHOW_OVERVIEW", false));
    showUnconserved
            .setSelected(Cache.getDefault("SHOW_UNCONSERVED", false));
    showNpTooltip.setSelected(Cache
            .getDefault("SHOW_NPFEATS_TOOLTIP", true));
    showDbRefTooltip.setSelected(Cache.getDefault("SHOW_DBREFS_TOOLTIP",
            true));
    sortByTree.setSelected(Cache.getDefault("SORT_BY_TREE", false));
    for (int i = ColourSchemeProperty.FIRST_COLOUR; i <= ColourSchemeProperty.LAST_COLOUR; i++)
    {
      colour.addItem(ColourSchemeProperty.getColourName(i));
    }

    String string = Cache.getDefault("DEFAULT_COLOUR", "None");

    colour.setSelectedItem(string);

    /**
     * default min-max colours for annotation shading
     */
    minColour.setBackground(Cache.getDefaultColour("ANNOTATIONCOLOUR_MIN",
            Color.orange));
    maxColour.setBackground(Cache.getDefaultColour("ANNOTATIONCOLOUR_MAX",
            Color.red));

    String[] fonts = java.awt.GraphicsEnvironment
            .getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    for (int i = 0; i < fonts.length; i++)
    {
      fontNameCB.addItem(fonts[i]);
    }

    for (int i = 1; i < 31; i++)
    {
      fontSizeCB.addItem(i + "");
    }

    fontStyleCB.addItem("plain");
    fontStyleCB.addItem("bold");
    fontStyleCB.addItem("italic");

    fontNameCB.setSelectedItem(Cache.getDefault("FONT_NAME", "SansSerif"));
    fontSizeCB.setSelectedItem(Cache.getDefault("FONT_SIZE", "10"));
    fontStyleCB.setSelectedItem(Cache.getDefault("FONT_STYLE", Font.PLAIN
            + ""));

    smoothFont.setSelected(Cache.getDefault("ANTI_ALIAS", false));

    idItalics.setSelected(Cache.getDefault("ID_ITALICS", true));

    wrap.setSelected(Cache.getDefault("WRAP_ALIGNMENT", false));

    gapSymbolCB.addItem("-");
    gapSymbolCB.addItem(".");

    gapSymbolCB.setSelectedItem(Cache.getDefault("GAP_SYMBOL", "-"));

    startupCheckbox
            .setSelected(Cache.getDefault("SHOW_STARTUP_FILE", true));
    startupFileTextfield.setText(Cache.getDefault("STARTUP_FILE",
            Cache.getDefault("www.jalview.org", "http://www.jalview.org")
                    + "/examples/exampleFile_2_3.jar"));

    sortby.addItem("No sort");
    sortby.addItem("Id");
    sortby.addItem("Pairwise Identity");
    sortby.setSelectedItem(Cache.getDefault("SORT_ALIGNMENT", "No sort"));

    epsRendering.addItem("Prompt each time");
    epsRendering.addItem("Lineart");
    epsRendering.addItem("Text");
    epsRendering.setSelectedItem(Cache.getDefault("EPS_RENDERING",
            "Prompt each time"));
    autoIdWidth.setSelected(Cache.getDefault("FIGURE_AUTOIDWIDTH", false));
    userIdWidth.setEnabled(autoIdWidth.isSelected());
    userIdWidthlabel.setEnabled(autoIdWidth.isSelected());
    Integer wi = Cache.getIntegerProperty("FIGURE_USERIDWIDTH");
    userIdWidth.setText(wi == null ? "" : wi.toString());
    blcjv.setSelected(Cache.getDefault("BLC_JVSUFFIX", true));
    clustaljv.setSelected(Cache.getDefault("CLUSTAL_JVSUFFIX", true));
    fastajv.setSelected(Cache.getDefault("FASTA_JVSUFFIX", true));
    msfjv.setSelected(Cache.getDefault("MSF_JVSUFFIX", true));
    pfamjv.setSelected(Cache.getDefault("PFAM_JVSUFFIX", true));
    pileupjv.setSelected(Cache.getDefault("PILEUP_JVSUFFIX", true));
    pirjv.setSelected(Cache.getDefault("PIR_JVSUFFIX", true));

    modellerOutput.setSelected(Cache.getDefault("PIR_MODELLER", false));

    autoCalculateConsCheck.setSelected(Cache.getDefault(
            "AUTO_CALC_CONSENSUS", true));
    showGroupConsensus.setSelected(Cache.getDefault("SHOW_GROUP_CONSENSUS",
            false));
    showGroupConservation.setSelected(Cache.getDefault(
            "SHOW_GROUP_CONSERVATION", false));
    showConsensHistogram.setSelected(Cache.getDefault(
            "SHOW_CONSENSUS_HISTOGRAM", true));
    showConsensLogo.setSelected(Cache.getDefault("SHOW_CONSENSUS_LOGO",
            false));

    padGaps.setSelected(Cache.getDefault("PAD_GAPS", false));

    /***************************************************************************
     * Set up Connections
     */
    nameLinks = new Vector();
    urlLinks = new Vector();
    for (int i = 0; i < sequenceURLLinks.size(); i++)
    {
      String link = sequenceURLLinks.elementAt(i).toString();
      nameLinks.addElement(link.substring(0, link.indexOf("|")));
      urlLinks.addElement(link.substring(link.indexOf("|") + 1));
    }

    updateLinkData();

    useProxy.setSelected(Cache.getDefault("USE_PROXY", false));
    proxyServerTB.setEnabled(useProxy.isSelected());
    proxyPortTB.setEnabled(useProxy.isSelected());
    proxyServerTB.setText(Cache.getDefault("PROXY_SERVER", ""));
    proxyPortTB.setText(Cache.getDefault("PROXY_PORT", ""));

    defaultBrowser.setText(Cache.getDefault("DEFAULT_BROWSER", ""));

    usagestats.setSelected(Cache.getDefault("USAGESTATS", false));
    questionnaire
            .setSelected(Cache.getProperty("NOQUESTIONNAIRES") == null); // note
                                                                         // antisense
                                                                         // here
    versioncheck.setSelected(Cache.getDefault("VERSION_CHECK", true)); // default
                                                                       // is
                                                                       // true
    annotations_actionPerformed(null); // update the display of the annotation
                                       // settings
    try
    {
      jbInit();
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void ok_actionPerformed(ActionEvent e)
  {

    Cache.applicationProperties.setProperty("SHOW_JVSUFFIX",
            Boolean.toString(seqLimit.isSelected()));
    Cache.applicationProperties.setProperty("RIGHT_ALIGN_IDS",
            Boolean.toString(rightAlign.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_FULLSCREEN",
            Boolean.toString(fullScreen.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_OVERVIEW",
            Boolean.toString(openoverv.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_ANNOTATIONS",
            Boolean.toString(annotations.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_CONSERVATION",
            Boolean.toString(conservation.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_QUALITY",
            Boolean.toString(quality.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_IDENTITY",
            Boolean.toString(identity.isSelected()));

    Cache.applicationProperties.setProperty("DEFAULT_COLOUR", colour
            .getSelectedItem().toString());
    Cache.applicationProperties.setProperty("GAP_SYMBOL", gapSymbolCB
            .getSelectedItem().toString());

    Cache.applicationProperties.setProperty("FONT_NAME", fontNameCB
            .getSelectedItem().toString());
    Cache.applicationProperties.setProperty("FONT_STYLE", fontStyleCB
            .getSelectedItem().toString());
    Cache.applicationProperties.setProperty("FONT_SIZE", fontSizeCB
            .getSelectedItem().toString());

    Cache.applicationProperties.setProperty("ID_ITALICS",
            Boolean.toString(idItalics.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_UNCONSERVED",
            Boolean.toString(showUnconserved.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_GROUP_CONSENSUS",
            Boolean.toString(showGroupConsensus.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_GROUP_CONSERVATION",
            Boolean.toString(showGroupConservation.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_CONSENSUS_HISTOGRAM",
            Boolean.toString(showConsensHistogram.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_CONSENSUS_LOGO",
            Boolean.toString(showConsensLogo.isSelected()));
    Cache.applicationProperties.setProperty("ANTI_ALIAS",
            Boolean.toString(smoothFont.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_NPFEATS_TOOLTIP",
            Boolean.toString(showNpTooltip.isSelected()));
    Cache.applicationProperties.setProperty("SHOW_DBREFS_TOOLTIP",
            Boolean.toString(showDbRefTooltip.isSelected()));

    Cache.applicationProperties.setProperty("WRAP_ALIGNMENT",
            Boolean.toString(wrap.isSelected()));

    Cache.applicationProperties.setProperty("STARTUP_FILE",
            startupFileTextfield.getText());
    Cache.applicationProperties.setProperty("SHOW_STARTUP_FILE",
            Boolean.toString(startupCheckbox.isSelected()));

    Cache.applicationProperties.setProperty("SORT_ALIGNMENT", sortby
            .getSelectedItem().toString());

    Cache.setColourProperty("ANNOTATIONCOLOUR_MIN",
            minColour.getBackground());
    Cache.setColourProperty("ANNOTATIONCOLOUR_MAX",
            maxColour.getBackground());

    if (epsRendering.getSelectedItem().equals("Prompt each time"))
    {
      Cache.applicationProperties.remove("EPS_RENDERING");
    }
    else
    {
      Cache.applicationProperties.setProperty("EPS_RENDERING", epsRendering
              .getSelectedItem().toString());
    }

    if (defaultBrowser.getText().trim().length() < 1)
    {
      Cache.applicationProperties.remove("DEFAULT_BROWSER");
    }
    else
    {
      Cache.applicationProperties.setProperty("DEFAULT_BROWSER",
              defaultBrowser.getText());
    }

    jalview.util.BrowserLauncher.resetBrowser();

    if (nameLinks.size() > 0)
    {
      StringBuffer links = new StringBuffer();
      sequenceURLLinks = new Vector();
      for (int i = 0; i < nameLinks.size(); i++)
      {
        sequenceURLLinks.addElement(nameLinks.elementAt(i) + "|"
                + urlLinks.elementAt(i));
        links.append(sequenceURLLinks.elementAt(i).toString());
        links.append("|");
      }
      // remove last "|"
      links.setLength(links.length() - 1);
      Cache.applicationProperties.setProperty("SEQUENCE_LINKS",
              links.toString());
    }
    else
    {
      Cache.applicationProperties.remove("SEQUENCE_LINKS");
    }

    Cache.applicationProperties.setProperty("USE_PROXY",
            Boolean.toString(useProxy.isSelected()));

    if (proxyServerTB.getText().trim().length() < 1)
    {
      Cache.applicationProperties.remove("PROXY_SERVER");
    }
    else
    {
      Cache.applicationProperties.setProperty("PROXY_SERVER",
              proxyServerTB.getText());
    }

    if (proxyPortTB.getText().trim().length() < 1)
    {
      Cache.applicationProperties.remove("PROXY_PORT");
    }
    else
    {
      Cache.applicationProperties.setProperty("PROXY_PORT",
              proxyPortTB.getText());
    }

    if (useProxy.isSelected())
    {
      System.setProperty("http.proxyHost", proxyServerTB.getText());
      System.setProperty("http.proxyPort", proxyPortTB.getText());
    }
    else
    {
      System.setProperty("http.proxyHost", "");
      System.setProperty("http.proxyPort", "");
    }
    Cache.setProperty("VERSION_CHECK",
            Boolean.toString(versioncheck.isSelected()));
    if (Cache.getProperty("USAGESTATS") != null || usagestats.isSelected())
    {
      // default is false - we only set this if the user has actively agreed
      Cache.setProperty("USAGESTATS",
              Boolean.toString(usagestats.isSelected()));
    }
    if (!questionnaire.isSelected())
    {
      Cache.setProperty("NOQUESTIONNAIRES", "true");
    }
    else
    {
      // special - made easy to edit a property file to disable questionnaires
      // by just adding the given line
      Cache.removeProperty("NOQUESTIONNAIRES");
    }
    Cache.applicationProperties.setProperty("BLC_JVSUFFIX",
            Boolean.toString(blcjv.isSelected()));
    Cache.applicationProperties.setProperty("CLUSTAL_JVSUFFIX",
            Boolean.toString(clustaljv.isSelected()));
    Cache.applicationProperties.setProperty("FASTA_JVSUFFIX",
            Boolean.toString(fastajv.isSelected()));
    Cache.applicationProperties.setProperty("MSF_JVSUFFIX",
            Boolean.toString(msfjv.isSelected()));
    Cache.applicationProperties.setProperty("PFAM_JVSUFFIX",
            Boolean.toString(pfamjv.isSelected()));
    Cache.applicationProperties.setProperty("PILEUP_JVSUFFIX",
            Boolean.toString(pileupjv.isSelected()));
    Cache.applicationProperties.setProperty("PIR_JVSUFFIX",
            Boolean.toString(pirjv.isSelected()));
    Cache.applicationProperties.setProperty("PIR_MODELLER",
            Boolean.toString(modellerOutput.isSelected()));
    jalview.io.PIRFile.useModellerOutput = modellerOutput.isSelected();

    Cache.applicationProperties.setProperty("FIGURE_AUTOIDWIDTH",
            Boolean.toString(autoIdWidth.isSelected()));
    userIdWidth_actionPerformed();
    Cache.applicationProperties.setProperty("FIGURE_USERIDWIDTH",
            userIdWidth.getText());

    Cache.applicationProperties.setProperty("AUTO_CALC_CONSENSUS",
            Boolean.toString(autoCalculateConsCheck.isSelected()));
    Cache.applicationProperties.setProperty("SORT_BY_TREE",
            Boolean.toString(sortByTree.isSelected()));
    Cache.applicationProperties.setProperty("PAD_GAPS",
            Boolean.toString(padGaps.isSelected()));

    dasSource.saveProperties(Cache.applicationProperties);
    wsPrefs.updateAndRefreshWsMenuConfig(false);
    Cache.saveProperties();
    try
    {
      frame.setClosed(true);
    } catch (Exception ex)
    {
    }
  }

  /**
   * DOCUMENT ME!
   */
  public void startupFileTextfield_mouseClicked()
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"),
            new String[]
            { "fa, fasta, fastq", "aln", "pfam", "msf", "pir", "blc", "jar" },
            new String[]
            { "Fasta", "Clustal", "PFAM", "MSF", "PIR", "BLC", "Jalview" },
            jalview.bin.Cache.getProperty("DEFAULT_FILE_FORMAT"));
    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Select startup file");

    int value = chooser.showOpenDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      jalview.bin.Cache.applicationProperties.setProperty(
              "DEFAULT_FILE_FORMAT", chooser.getSelectedFormat());
      startupFileTextfield.setText(chooser.getSelectedFile()
              .getAbsolutePath());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void cancel_actionPerformed(ActionEvent e)
  {
    try
    {
      wsPrefs.updateWsMenuConfig(true);
      wsPrefs.refreshWs_actionPerformed(e);
      frame.setClosed(true);
    } catch (Exception ex)
    {
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void annotations_actionPerformed(ActionEvent e)
  {
    conservation.setEnabled(annotations.isSelected());
    quality.setEnabled(annotations.isSelected());
    identity.setEnabled(annotations.isSelected());
    showGroupConsensus.setEnabled(annotations.isSelected());
    showGroupConservation.setEnabled(annotations.isSelected());
    showConsensHistogram.setEnabled(annotations.isSelected()
            && (identity.isSelected() || showGroupConsensus.isSelected()));
    showConsensLogo.setEnabled(annotations.isSelected()
            && (identity.isSelected() || showGroupConsensus.isSelected()));
  }

  public void newLink_actionPerformed(ActionEvent e)
  {

    GSequenceLink link = new GSequenceLink();
    boolean valid = false;
    while (!valid)
    {
      if (JOptionPane.showInternalConfirmDialog(Desktop.desktop, link,
              "New sequence URL link", JOptionPane.OK_CANCEL_OPTION, -1,
              null) == JOptionPane.OK_OPTION)
      {
        if (link.checkValid())
        {
          nameLinks.addElement(link.getName());
          urlLinks.addElement(link.getURL());
          updateLinkData();
          valid = true;
        }
      }
      else
      {
        break;
      }
    }
  }

  public void editLink_actionPerformed(ActionEvent e)
  {
    GSequenceLink link = new GSequenceLink();

    int index = linkNameList.getSelectedIndex();
    if (index == -1)
    {
      JOptionPane.showInternalMessageDialog(Desktop.desktop,
              "No link selected!", "No link selected",
              JOptionPane.WARNING_MESSAGE);
      return;
    }

    link.setName(nameLinks.elementAt(index).toString());
    link.setURL(urlLinks.elementAt(index).toString());

    boolean valid = false;
    while (!valid)
    {

      if (JOptionPane.showInternalConfirmDialog(Desktop.desktop, link,
              "New sequence URL link", JOptionPane.OK_CANCEL_OPTION, -1,
              null) == JOptionPane.OK_OPTION)
      {
        if (link.checkValid())
        {
          nameLinks.setElementAt(link.getName(), index);
          urlLinks.setElementAt(link.getURL(), index);
          updateLinkData();
          valid = true;
        }
      }

      else
      {
        break;
      }
    }
  }

  public void deleteLink_actionPerformed(ActionEvent e)
  {
    int index = linkNameList.getSelectedIndex();
    if (index == -1)
    {
      JOptionPane.showInternalMessageDialog(Desktop.desktop,
              "No link selected!", "No link selected",
              JOptionPane.WARNING_MESSAGE);
      return;
    }
    nameLinks.removeElementAt(index);
    urlLinks.removeElementAt(index);
    updateLinkData();
  }

  void updateLinkData()
  {
    linkNameList.setListData(nameLinks);
    linkURLList.setListData(urlLinks);
  }

  public void defaultBrowser_mouseClicked(MouseEvent e)
  {
    JFileChooser chooser = new JFileChooser(".");
    chooser.setDialogTitle("Select default web browser");

    int value = chooser.showOpenDialog(this);

    if (value == JFileChooser.APPROVE_OPTION)
    {
      defaultBrowser.setText(chooser.getSelectedFile().getAbsolutePath());
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GPreferences#showunconserved_actionPerformed(java.awt.event
   * .ActionEvent)
   */
  protected void showunconserved_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    super.showunconserved_actionPerformed(e);
  }

  private void jbInit() throws Exception
  {
  }

  public static Collection getGroupURLLinks()
  {
    return groupURLLinks;
  }

  public void minColour_actionPerformed()
  {
    Color col = JColorChooser.showDialog(this,
            "Select Colour for Minimum Value", minColour.getBackground());
    if (col != null)
    {
      minColour.setBackground(col);
    }
    minColour.repaint();
  }

  public void maxColour_actionPerformed()
  {
    Color col = JColorChooser.showDialog(this,
            "Select Colour for Maximum Value", maxColour.getBackground());
    if (col != null)
    {
      maxColour.setBackground(col);
    }
    maxColour.repaint();
  }

  @Override
  protected void userIdWidth_actionPerformed()
  {
    try
    {
      String val = userIdWidth.getText().trim();
      if (val.length() > 0)
      {
        Integer iw = Integer.parseInt(val);
        if (iw.intValue() < 12)
        {
          throw new NumberFormatException();
        }
        userIdWidth.setText(iw.toString());
      }
    } catch (NumberFormatException x)
    {
      JOptionPane
              .showInternalMessageDialog(
                      Desktop.desktop,
                      "The user defined width for the\nannotation and sequence ID columns\nin exported figures must be\nat least 12 pixels wide.",
                      "Invalid ID Column width",
                      JOptionPane.WARNING_MESSAGE);
      userIdWidth.setText("");
    }
  }

  @Override
  protected void autoIdWidth_actionPerformed()
  {
    userIdWidth.setEnabled(!autoIdWidth.isSelected());
    userIdWidthlabel.setEnabled(!autoIdWidth.isSelected());
  }

}
