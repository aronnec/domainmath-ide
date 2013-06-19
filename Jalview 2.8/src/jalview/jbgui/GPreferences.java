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
package jalview.jbgui;

import jalview.gui.JvSwingUtils;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.Rectangle;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class GPreferences extends JPanel
{
  JTabbedPane tabbedPane = new JTabbedPane();

  JButton ok = new JButton();

  JButton cancel = new JButton();

  JPanel okCancelPanel = new JPanel();

  BorderLayout borderLayout1 = new BorderLayout();

  protected JCheckBox quality = new JCheckBox();

  JPanel visualTab = new JPanel();

  JPanel visual2Tab = new JPanel();

  protected JCheckBox fullScreen = new JCheckBox();

  protected JCheckBox conservation = new JCheckBox();

  protected JCheckBox identity = new JCheckBox();

  protected JCheckBox annotations = new JCheckBox();

  protected JPanel minColour = new JPanel();

  JLabel mincolourLabel = new JLabel();

  protected JPanel maxColour = new JPanel();

  JLabel maxcolourLabel = new JLabel();

  JLabel gapLabel = new JLabel();

  protected JComboBox colour = new JComboBox();

  JLabel colourLabel = new JLabel();

  JLabel fontLabel = new JLabel();

  protected JComboBox fontSizeCB = new JComboBox();

  protected JComboBox fontStyleCB = new JComboBox();

  protected JComboBox fontNameCB = new JComboBox();

  protected JComboBox gapSymbolCB = new JComboBox();

  protected JCheckBox startupCheckbox = new JCheckBox();

  protected JTextField startupFileTextfield = new JTextField();

  JPanel connectTab = new JPanel();

  JLabel serverLabel = new JLabel();

  protected JList linkURLList = new JList();

  protected JTextField proxyServerTB = new JTextField();

  protected JTextField proxyPortTB = new JTextField();

  JLabel portLabel = new JLabel();

  JLabel browserLabel = new JLabel();

  protected JTextField defaultBrowser = new JTextField();

  JButton newLink = new JButton();

  JButton editLink = new JButton();

  JButton deleteLink = new JButton();

  JScrollPane linkScrollPane = new JScrollPane();

  JPanel linkPanel = new JPanel();

  BorderLayout borderLayout2 = new BorderLayout();

  JPanel editLinkButtons = new JPanel();

  GridLayout gridLayout1 = new GridLayout();

  protected JList linkNameList = new JList();

  JPanel linkPanel2 = new JPanel();

  BorderLayout borderLayout3 = new BorderLayout();

  protected JCheckBox useProxy = new JCheckBox();

  JPanel jPanel1 = new JPanel();

  TitledBorder titledBorder1 = new TitledBorder("Proxy Server");

  TitledBorder titledBorder2 = new TitledBorder("File Output");

  GridBagLayout gridBagLayout2 = new GridBagLayout();

  GridBagLayout gridBagLayout1 = new GridBagLayout();

  GridBagLayout gridBagLayout3 = new GridBagLayout();

  protected JComboBox sortby = new JComboBox();

  JLabel sortLabel = new JLabel();

  JPanel jPanel2 = new JPanel();

  JPanel visual2panel = new JPanel();

  GridLayout gridLayout2 = new GridLayout();

  GridLayout gridLayout4 = new GridLayout();

  JPanel annsettingsPanel = new JPanel();

  JPanel autoAnnotSettings1 = new JPanel();

  JPanel autoAnnotSettings2 = new JPanel();

  JPanel autoAnnotSettings3 = new JPanel();

  JPanel exportTab = new JPanel();

  JLabel epsLabel = new JLabel();

  protected JComboBox epsRendering = new JComboBox();

  protected JLabel userIdWidthlabel = new JLabel();

  protected JCheckBox autoIdWidth = new JCheckBox();

  protected JTextField userIdWidth = new JTextField();

  JLabel jLabel1 = new JLabel();

  protected JCheckBox blcjv = new JCheckBox();

  protected JCheckBox pileupjv = new JCheckBox();

  protected JCheckBox clustaljv = new JCheckBox();

  protected JCheckBox msfjv = new JCheckBox();

  protected JCheckBox fastajv = new JCheckBox();

  protected JCheckBox pfamjv = new JCheckBox();

  FlowLayout flowLayout1 = new FlowLayout();

  protected JCheckBox pirjv = new JCheckBox();

  JPanel jPanel11 = new JPanel();

  Font verdana11 = JvSwingUtils.getLabelFont();

  protected JCheckBox seqLimit = new JCheckBox();

  GridLayout gridLayout3 = new GridLayout();

  protected JCheckBox smoothFont = new JCheckBox();

  JPanel calcTab = new JPanel();

  protected JCheckBox autoCalculateConsCheck = new JCheckBox();

  protected JCheckBox padGaps = new JCheckBox();

  protected JCheckBox modellerOutput = new JCheckBox();

  protected JPanel dasPanel = new JPanel();

  BorderLayout borderLayout4 = new BorderLayout();

  protected JPanel wsPanel = new JPanel();

  BorderLayout borderLayout5 = new BorderLayout();

  protected JCheckBox wrap = new JCheckBox();

  protected JCheckBox rightAlign = new JCheckBox();

  protected JCheckBox showUnconserved = new JCheckBox();

  protected JCheckBox showDbRefTooltip = new JCheckBox();

  protected JCheckBox showNpTooltip = new JCheckBox();

  protected JCheckBox idItalics = new JCheckBox();

  protected JCheckBox openoverv = new JCheckBox();

  protected JCheckBox usagestats = new JCheckBox();

  protected JCheckBox questionnaire = new JCheckBox();

  protected JCheckBox versioncheck = new JCheckBox();

  protected JLabel showGroupbits = new JLabel();

  protected JLabel showConsensbits = new JLabel();

  protected JCheckBox showConsensLogo = new JCheckBox();

  protected JCheckBox showConsensHistogram = new JCheckBox();

  protected JCheckBox showGroupConsensus = new JCheckBox();

  protected JCheckBox showGroupConservation = new JCheckBox();

  protected JCheckBox shareSelections = new JCheckBox();

  protected JCheckBox followHighlight = new JCheckBox();

  protected JCheckBox sortByTree = new JCheckBox();

  /**
   * Creates a new GPreferences object.
   */
  public GPreferences()
  {
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
   * @throws Exception
   *           DOCUMENT ME!
   */
  private void jbInit() throws Exception
  {
    this.setLayout(borderLayout1);
    ok.setText("OK");
    ok.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ok_actionPerformed(e);
      }
    });
    cancel.setText("Cancel");
    cancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cancel_actionPerformed(e);
      }
    });
    quality.setEnabled(false);
    quality.setFont(verdana11);
    quality.setHorizontalAlignment(SwingConstants.RIGHT);
    quality.setHorizontalTextPosition(SwingConstants.LEFT);
    quality.setSelected(true);
    quality.setText("Quality");
    visualTab.setBorder(new TitledBorder("Open new alignment"));
    visualTab.setLayout(null);
    visual2Tab.setBorder(new TitledBorder("Open new alignment"));
    visual2Tab.setLayout(new FlowLayout());
    fullScreen.setFont(verdana11);
    fullScreen.setHorizontalAlignment(SwingConstants.RIGHT);
    fullScreen.setHorizontalTextPosition(SwingConstants.LEFT);
    fullScreen.setText("Maximise Window");
    conservation.setEnabled(false);
    conservation.setFont(verdana11);
    conservation.setHorizontalAlignment(SwingConstants.RIGHT);
    conservation.setHorizontalTextPosition(SwingConstants.LEFT);
    conservation.setSelected(true);
    conservation.setText("Conservation");
    identity.setEnabled(false);
    identity.setFont(verdana11);
    identity.setHorizontalAlignment(SwingConstants.RIGHT);
    identity.setHorizontalTextPosition(SwingConstants.LEFT);
    identity.setSelected(true);
    identity.setText("Consensus");
    showGroupbits.setFont(verdana11);
    showGroupbits.setHorizontalAlignment(SwingConstants.RIGHT);
    showGroupbits.setHorizontalTextPosition(SwingConstants.LEFT);
    showGroupbits.setText("Show group:");
    showConsensbits.setFont(verdana11);
    showConsensbits.setHorizontalAlignment(SwingConstants.RIGHT);
    showConsensbits.setHorizontalTextPosition(SwingConstants.LEFT);
    showConsensbits.setText("Consensus:");
    showConsensHistogram.setEnabled(false);
    showConsensHistogram.setFont(verdana11);
    showConsensHistogram.setHorizontalAlignment(SwingConstants.RIGHT);
    showConsensHistogram.setHorizontalTextPosition(SwingConstants.LEFT);
    showConsensHistogram.setSelected(true);
    showConsensHistogram.setText("Histogram");
    showConsensLogo.setEnabled(false);
    showConsensLogo.setFont(verdana11);
    showConsensLogo.setHorizontalAlignment(SwingConstants.RIGHT);
    showConsensLogo.setHorizontalTextPosition(SwingConstants.LEFT);
    showConsensLogo.setSelected(true);
    showConsensLogo.setText("Logo");
    showGroupConsensus.setEnabled(false);
    showGroupConsensus.setFont(verdana11);
    showGroupConsensus.setHorizontalAlignment(SwingConstants.RIGHT);
    showGroupConsensus.setHorizontalTextPosition(SwingConstants.LEFT);
    showGroupConsensus.setSelected(true);
    showGroupConsensus.setText("Consensus");
    showGroupConservation.setEnabled(false);
    showGroupConservation.setFont(verdana11);
    showGroupConservation.setHorizontalAlignment(SwingConstants.RIGHT);
    showGroupConservation.setHorizontalTextPosition(SwingConstants.LEFT);
    showGroupConservation.setSelected(true);
    showGroupConservation.setText("Conservation");
    showNpTooltip.setEnabled(true);
    showNpTooltip.setFont(verdana11);
    showNpTooltip.setHorizontalAlignment(SwingConstants.RIGHT);
    showNpTooltip.setHorizontalTextPosition(SwingConstants.LEFT);
    showNpTooltip.setSelected(true);
    showNpTooltip.setText("Non-positional Features");
    showDbRefTooltip.setEnabled(true);
    showDbRefTooltip.setFont(verdana11);
    showDbRefTooltip.setHorizontalAlignment(SwingConstants.RIGHT);
    showDbRefTooltip.setHorizontalTextPosition(SwingConstants.LEFT);
    showDbRefTooltip.setSelected(true);
    showDbRefTooltip.setText("Database References");
    annotations.setFont(verdana11);
    annotations.setHorizontalAlignment(SwingConstants.RIGHT);
    annotations.setHorizontalTextPosition(SwingConstants.LEADING);
    annotations.setSelected(true);
    annotations.setText("Show Annotations");
    annotations.setBounds(new Rectangle(169, 12, 200, 23));
    annotations.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        annotations_actionPerformed(e);
      }
    });
    identity.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        annotations_actionPerformed(e);
      }
    });
    showGroupConsensus.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        annotations_actionPerformed(e);
      }
    });
    showUnconserved.setFont(verdana11);
    showUnconserved.setHorizontalAlignment(SwingConstants.RIGHT);
    showUnconserved.setHorizontalTextPosition(SwingConstants.LEFT);
    showUnconserved.setSelected(true);
    showUnconserved.setText("Show Unconserved");
    showUnconserved.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showunconserved_actionPerformed(e);
      }
    });
    // / TODO: fit these in to preferences panel!!!!!
    shareSelections.setFont(verdana11);
    shareSelections.setHorizontalAlignment(SwingConstants.RIGHT);
    shareSelections.setHorizontalTextPosition(SwingConstants.LEFT);
    shareSelections.setSelected(true);
    shareSelections.setText("Share selection across views");
    followHighlight.setFont(verdana11);
    followHighlight.setHorizontalAlignment(SwingConstants.RIGHT);
    followHighlight.setHorizontalTextPosition(SwingConstants.LEFT);
    // showUnconserved.setBounds(new Rectangle(169, 40, 200, 23));
    followHighlight.setSelected(true);
    followHighlight.setText("Scroll to highlighted regions");

    gapLabel.setFont(verdana11);
    gapLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    gapLabel.setText("Gap Symbol ");
    colour.setFont(verdana11);
    colour.setBounds(new Rectangle(172, 225, 155, 21));
    colourLabel.setFont(verdana11);
    colourLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    colourLabel.setText("Alignment Colour ");
    fontLabel.setFont(verdana11);
    fontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    fontLabel.setText("Font ");
    fontSizeCB.setFont(verdana11);
    fontSizeCB.setBounds(new Rectangle(319, 104, 49, 23));
    fontStyleCB.setFont(verdana11);
    fontStyleCB.setBounds(new Rectangle(367, 104, 70, 23));
    fontNameCB.setFont(verdana11);
    fontNameCB.setBounds(new Rectangle(172, 104, 147, 23));
    gapSymbolCB.setFont(verdana11);
    gapSymbolCB.setBounds(new Rectangle(172, 204, 69, 23));
    mincolourLabel.setFont(verdana11);
    mincolourLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    mincolourLabel.setText("Minimum Colour");
    minColour.setFont(verdana11);
    minColour.setBorder(BorderFactory.createEtchedBorder());
    minColour.setPreferredSize(new Dimension(40, 20));
    minColour.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        minColour_actionPerformed();
      }
    });
    maxcolourLabel.setFont(verdana11);
    maxcolourLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    maxcolourLabel.setText("Maximum Colour ");
    maxColour.setFont(verdana11);
    maxColour.setBorder(BorderFactory.createEtchedBorder());
    maxColour.setPreferredSize(new Dimension(40, 20));
    maxColour.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        maxColour_actionPerformed();
      }
    });

    startupCheckbox.setText("Open file");
    startupCheckbox.setFont(verdana11);
    startupCheckbox.setHorizontalAlignment(SwingConstants.RIGHT);
    startupCheckbox.setHorizontalTextPosition(SwingConstants.LEFT);
    startupCheckbox.setSelected(true);
    startupFileTextfield.setFont(verdana11);
    startupFileTextfield.setBounds(new Rectangle(172, 273, 270, 20));
    startupFileTextfield.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if (e.getClickCount() > 1)
        {
          startupFileTextfield_mouseClicked();
        }
      }
    });

    connectTab.setLayout(gridBagLayout3);
    serverLabel.setText("Address");
    serverLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    serverLabel.setFont(verdana11);
    proxyServerTB.setFont(verdana11);
    proxyPortTB.setFont(verdana11);
    portLabel.setFont(verdana11);
    portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    portLabel.setText("Port");
    browserLabel.setFont(new java.awt.Font("SansSerif", 0, 11));
    browserLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    browserLabel.setText("Default Browser (Unix)");
    defaultBrowser.setFont(verdana11);
    defaultBrowser.setText("");
    usagestats.setText("Send usage statistics");
    usagestats.setFont(verdana11);
    usagestats.setHorizontalAlignment(SwingConstants.RIGHT);
    usagestats.setHorizontalTextPosition(SwingConstants.LEADING);
    questionnaire.setText("Check for questionnaires");
    questionnaire.setFont(verdana11);
    questionnaire.setHorizontalAlignment(SwingConstants.RIGHT);
    questionnaire.setHorizontalTextPosition(SwingConstants.LEADING);
    versioncheck.setText("Check for latest version");
    versioncheck.setFont(verdana11);
    versioncheck.setHorizontalAlignment(SwingConstants.RIGHT);
    versioncheck.setHorizontalTextPosition(SwingConstants.LEADING);
    newLink.setText("New");
    newLink.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        newLink_actionPerformed(e);
      }
    });
    editLink.setText("Edit");
    editLink.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        editLink_actionPerformed(e);
      }
    });
    deleteLink.setText("Delete");
    deleteLink.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        deleteLink_actionPerformed(e);
      }
    });

    linkURLList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        int index = linkURLList.getSelectedIndex();
        linkNameList.setSelectedIndex(index);
      }
    });

    linkNameList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        int index = linkNameList.getSelectedIndex();
        linkURLList.setSelectedIndex(index);
      }
    });

    linkScrollPane.setBorder(null);
    linkPanel.setBorder(new TitledBorder("URL link from Sequence ID"));
    linkPanel.setLayout(borderLayout2);
    editLinkButtons.setLayout(gridLayout1);
    gridLayout1.setRows(3);
    linkNameList.setFont(verdana11);
    linkNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    linkPanel2.setLayout(borderLayout3);
    linkURLList.setFont(verdana11);
    linkURLList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    defaultBrowser.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if (e.getClickCount() > 1)
        {
          defaultBrowser_mouseClicked(e);
        }
      }
    });
    useProxy.setFont(verdana11);
    useProxy.setHorizontalAlignment(SwingConstants.RIGHT);
    useProxy.setHorizontalTextPosition(SwingConstants.LEADING);
    useProxy.setText("Use a proxy server");
    useProxy.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        useProxy_actionPerformed();
      }
    });
    jPanel1.setBorder(titledBorder1);
    jPanel1.setLayout(gridBagLayout1);
    sortby.setFont(verdana11);
    sortby.setBounds(new Rectangle(172, 249, 155, 21));
    sortLabel.setFont(verdana11);
    sortLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    sortLabel.setText("Sort by ");
    jPanel2.setBounds(new Rectangle(7, 17, 158, 278));
    jPanel2.setLayout(gridLayout2);
    gridLayout2.setRows(12);
    exportTab.setLayout(null);
    epsLabel.setFont(verdana11);
    epsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    epsLabel.setText("EPS Rendering Style");
    epsLabel.setBounds(new Rectangle(9, 31, 140, 24));
    epsRendering.setFont(verdana11);
    epsRendering.setBounds(new Rectangle(154, 34, 187, 21));
    jLabel1.setFont(verdana11);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Append /start-end (/15-380)");
    jLabel1.setFont(verdana11);
    fastajv.setFont(verdana11);
    fastajv.setHorizontalAlignment(SwingConstants.LEFT);
    clustaljv.setText("Clustal     ");
    blcjv.setText("BLC     ");
    fastajv.setText("Fasta     ");
    msfjv.setText("MSF     ");
    pfamjv.setText("PFAM     ");
    pileupjv.setText("Pileup     ");
    msfjv.setFont(verdana11);
    msfjv.setHorizontalAlignment(SwingConstants.LEFT);
    pirjv.setText("PIR     ");
    jPanel11.setFont(verdana11);
    jPanel11.setBorder(titledBorder2);
    jPanel11.setBounds(new Rectangle(30, 72, 196, 182));
    jPanel11.setLayout(gridLayout3);
    blcjv.setFont(verdana11);
    blcjv.setHorizontalAlignment(SwingConstants.LEFT);
    clustaljv.setFont(verdana11);
    clustaljv.setHorizontalAlignment(SwingConstants.LEFT);
    pfamjv.setFont(verdana11);
    pfamjv.setHorizontalAlignment(SwingConstants.LEFT);
    pileupjv.setFont(verdana11);
    pileupjv.setHorizontalAlignment(SwingConstants.LEFT);
    pirjv.setFont(verdana11);
    pirjv.setHorizontalAlignment(SwingConstants.LEFT);
    seqLimit.setFont(verdana11);
    seqLimit.setHorizontalAlignment(SwingConstants.RIGHT);
    seqLimit.setHorizontalTextPosition(SwingConstants.LEFT);
    seqLimit.setText("Full Sequence Id");
    gridLayout3.setRows(8);
    smoothFont.setFont(verdana11);
    smoothFont.setHorizontalAlignment(SwingConstants.RIGHT);
    smoothFont.setHorizontalTextPosition(SwingConstants.LEADING);
    smoothFont.setText("Smooth Font");
    calcTab.setLayout(null);
    autoCalculateConsCheck.setFont(JvSwingUtils.getLabelFont());
    autoCalculateConsCheck.setText("AutoCalculate Consensus");
    autoCalculateConsCheck.setBounds(new Rectangle(21, 52, 209, 23));
    padGaps.setFont(JvSwingUtils.getLabelFont());
    padGaps.setText("Pad Gaps When Editing");
    padGaps.setBounds(new Rectangle(22, 94, 168, 23));
    sortByTree.setFont(JvSwingUtils.getLabelFont());
    sortByTree.setText("Sort With New Tree");
    sortByTree
            .setToolTipText("When selected, any trees calculated or loaded onto the alignment will automatically sort the alignment.");
    sortByTree.setBounds(new Rectangle(22, 136, 168, 23));

    autoIdWidth.setFont(JvSwingUtils.getLabelFont());
    autoIdWidth.setText("Automatically set ID width");
    autoIdWidth
            .setToolTipText("<html>"
                    + JvSwingUtils
                            .wrapTooltip("Adjusts the width of the generated EPS or PNG file to ensure even the longest sequence ID or annotation label is displayed")
                    + "</html>");
    autoIdWidth.setBounds(new Rectangle(228, 96, 188, 23));
    autoIdWidth.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        autoIdWidth_actionPerformed();
      }
    });
    userIdWidthlabel.setFont(JvSwingUtils.getLabelFont());
    userIdWidthlabel.setText("Figure ID column width");
    userIdWidth
            .setToolTipText("<html>"
                    + JvSwingUtils
                            .wrapTooltip("Manually specify the width of the left hand column where sequence IDs and annotation labels will be rendered in exported alignment figures. This setting will be ignored if 'Automatically set ID width' is set")
                    + "</html>");
    userIdWidthlabel
            .setToolTipText("<html>"
                    + JvSwingUtils
                            .wrapTooltip("Manually specify the width of the left hand column where sequence IDs and annotation labels will be rendered in exported alignment figures. This setting will be ignored if 'Automatically set ID width' is set")
                    + "</html>");
    userIdWidthlabel.setBounds(new Rectangle(236, 120, 168, 23));
    userIdWidth.setFont(JvSwingUtils.getTextAreaFont());
    userIdWidth.setText("");
    userIdWidth.setBounds(new Rectangle(232, 144, 84, 23));
    userIdWidth.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        userIdWidth_actionPerformed();
      }
    });
    modellerOutput.setFont(JvSwingUtils.getLabelFont());
    modellerOutput.setText("Use Modeller Output");
    modellerOutput.setBounds(new Rectangle(228, 226, 168, 23));

    dasPanel.setLayout(borderLayout4);
    wsPanel.setLayout(borderLayout5);
    wrap.setFont(JvSwingUtils.getLabelFont());
    wrap.setHorizontalAlignment(SwingConstants.TRAILING);
    wrap.setHorizontalTextPosition(SwingConstants.LEADING);
    wrap.setText("Wrap Alignment");
    rightAlign.setFont(JvSwingUtils.getLabelFont());
    rightAlign.setForeground(Color.black);
    rightAlign.setHorizontalAlignment(SwingConstants.RIGHT);
    rightAlign.setHorizontalTextPosition(SwingConstants.LEFT);
    rightAlign.setText("Right Align Ids");
    idItalics.setFont(JvSwingUtils.getLabelFont());
    idItalics.setHorizontalAlignment(SwingConstants.RIGHT);
    idItalics.setHorizontalTextPosition(SwingConstants.LEADING);
    idItalics.setText("Sequence Name Italics");
    openoverv.setFont(JvSwingUtils.getLabelFont());
    openoverv.setActionCommand("Open Overview");
    openoverv.setHorizontalAlignment(SwingConstants.RIGHT);
    openoverv.setHorizontalTextPosition(SwingConstants.LEFT);
    openoverv.setText("Open Overview");
    jPanel2.add(fullScreen);
    jPanel2.add(openoverv);
    jPanel2.add(seqLimit);
    jPanel2.add(rightAlign);
    jPanel2.add(fontLabel);
    jPanel2.add(showUnconserved);
    jPanel2.add(idItalics);
    jPanel2.add(smoothFont);
    jPanel2.add(gapLabel);
    jPanel2.add(wrap);
    jPanel2.add(sortLabel);
    jPanel2.add(startupCheckbox);
    visualTab.add(annotations);
    visualTab.add(startupFileTextfield);
    visualTab.add(sortby);
    visualTab.add(gapSymbolCB);
    visualTab.add(fontNameCB);
    visualTab.add(fontSizeCB);
    visualTab.add(fontStyleCB);
    annsettingsPanel.setBounds(new Rectangle(173, 34, 300, 61));
    annsettingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    annsettingsPanel.add(autoAnnotSettings1);
    annsettingsPanel.add(autoAnnotSettings2);
    annsettingsPanel.add(autoAnnotSettings3);
    autoAnnotSettings1.setLayout(new GridLayout(3, 1, 0, 0));
    autoAnnotSettings2.setLayout(new GridLayout(3, 1, 0, 0));
    autoAnnotSettings3.setLayout(new GridLayout(3, 1, 0, 0));
    visualTab.add(annsettingsPanel);
    Border jb = new EmptyBorder(1, 1, 4, 5);
    quality.setBorder(jb);
    conservation.setBorder(jb);
    identity.setBorder(jb);
    showConsensbits.setBorder(jb);
    showGroupbits.setBorder(jb);
    showGroupConsensus.setBorder(jb);
    showGroupConservation.setBorder(jb);
    showConsensHistogram.setBorder(jb);
    showConsensLogo.setBorder(jb);

    autoAnnotSettings2.add(conservation);
    autoAnnotSettings1.add(quality);
    autoAnnotSettings3.add(identity);
    autoAnnotSettings1.add(showGroupbits);
    autoAnnotSettings3.add(showGroupConsensus);
    autoAnnotSettings2.add(showGroupConservation);
    autoAnnotSettings1.add(showConsensbits);
    autoAnnotSettings2.add(showConsensHistogram);
    autoAnnotSettings3.add(showConsensLogo);

    JPanel tooltipSettings = new JPanel();
    tooltipSettings.setBorder(new TitledBorder("Sequence ID Tooltip"));
    tooltipSettings.setBounds(173, 130, 200, 62);
    tooltipSettings.setLayout(new GridLayout(2, 1));
    tooltipSettings.add(showDbRefTooltip);
    tooltipSettings.add(showNpTooltip);
    visualTab.add(tooltipSettings);
    visualTab.add(jPanel2);
    JvSwingUtils.addtoLayout(visual2Tab,
            "Default Colourscheme for alignment", colourLabel, colour);
    JPanel annotationShding = new JPanel();
    annotationShding.setBorder(new TitledBorder(
            "Annotation Shading Default"));
    annotationShding.setLayout(new GridLayout(1, 2));
    JvSwingUtils.addtoLayout(annotationShding,
            "Default Minimum Colour for annotation shading",
            mincolourLabel, minColour);
    JvSwingUtils.addtoLayout(annotationShding,
            "Default Maximum Colour for annotation shading",
            maxcolourLabel, maxColour);
    visual2Tab.add(annotationShding); // , FlowLayout.LEFT);

    // visual2panel.add(minColour);
    // visual2panel.add(maxColour);
    // visual2Tab.add(visual2panel);

    linkPanel.add(editLinkButtons, BorderLayout.EAST);
    editLinkButtons.add(newLink, null);
    editLinkButtons.add(editLink, null);
    editLinkButtons.add(deleteLink, null);
    linkPanel.add(linkScrollPane, BorderLayout.CENTER);
    linkScrollPane.getViewport().add(linkPanel2, null);
    linkPanel2.add(linkURLList, BorderLayout.CENTER);
    linkPanel2.add(linkNameList, BorderLayout.WEST);
    okCancelPanel.add(ok);
    okCancelPanel.add(cancel);
    this.add(tabbedPane, java.awt.BorderLayout.CENTER);

    this.add(okCancelPanel, java.awt.BorderLayout.SOUTH);
    jPanel1.add(serverLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                    2, 4, 0), 5, 0));
    jPanel1.add(portLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                    0, 4, 0), 11, 6));
    connectTab.add(linkPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                    16, 0, 0, 12), 359, -17));
    connectTab.add(jPanel1, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                    21, 0, 35, 12), 4, 6));
    connectTab.add(browserLabel, new GridBagConstraints(0, 1, 1, 1, 0.0,
            0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(16, 0, 0, 0), 5, 1));
    jPanel1.add(proxyPortTB, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 2, 4, 2), 54, 1));
    jPanel1.add(proxyServerTB, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 2, 4, 0), 263, 1));
    connectTab.add(defaultBrowser, new GridBagConstraints(1, 1, 1, 1, 1.0,
            0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(15, 0, 0, 15), 307, 1));
    connectTab.add(usagestats, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 2, 4, 2), 70, 1));
    connectTab.add(questionnaire, new GridBagConstraints(1, 4, 1, 1, 1.0,
            0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 2, 4, 2), 70, 1));
    connectTab.add(versioncheck, new GridBagConstraints(0, 5, 1, 1, 1.0,
            0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 2, 4, 2), 70, 1));

    jPanel1.add(useProxy, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
                    2, 5, 185), 2, -4));
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    gapSymbolCB.setRenderer(dlcr);

    tabbedPane.add(visualTab, "Visual");
    tabbedPane.add(visual2Tab, "Colours");
    tabbedPane.add(connectTab, "Connections");
    tabbedPane.add(exportTab, "Output");
    jPanel11.add(jLabel1);
    jPanel11.add(blcjv);
    jPanel11.add(clustaljv);
    jPanel11.add(fastajv);
    jPanel11.add(msfjv);
    jPanel11.add(pfamjv);
    jPanel11.add(pileupjv);
    jPanel11.add(pirjv);
    exportTab.add(autoIdWidth);
    exportTab.add(userIdWidth);
    exportTab.add(userIdWidthlabel);
    exportTab.add(modellerOutput);
    tabbedPane.add(calcTab, "Editing");
    calcTab.add(autoCalculateConsCheck);
    calcTab.add(padGaps);
    calcTab.add(sortByTree);

    tabbedPane.add(dasPanel, "DAS Settings");
    tabbedPane.add(wsPanel, "Web Services");

    exportTab.add(epsLabel);
    exportTab.add(epsRendering);
    exportTab.add(jPanel11);
  }

  protected void autoIdWidth_actionPerformed()
  {
    // TODO Auto-generated method stub

  }

  protected void userIdWidth_actionPerformed()
  {
    // TODO Auto-generated method stub

  }

  protected void maxColour_actionPerformed()
  {
    // TODO Auto-generated method stub

  }

  protected void minColour_actionPerformed()
  {
    // TODO Auto-generated method stub

  }

  protected void showunconserved_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void ok_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void cancel_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void annotations_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   */
  public void startupFileTextfield_mouseClicked()
  {
  }

  public void newLink_actionPerformed(ActionEvent e)
  {

  }

  public void editLink_actionPerformed(ActionEvent e)
  {

  }

  public void deleteLink_actionPerformed(ActionEvent e)
  {

  }

  public void defaultBrowser_mouseClicked(MouseEvent e)
  {

  }

  public void linkURLList_keyTyped(KeyEvent e)
  {

  }

  public void useProxy_actionPerformed()
  {
    proxyServerTB.setEnabled(useProxy.isSelected());
    proxyPortTB.setEnabled(useProxy.isSelected());
  }

}
