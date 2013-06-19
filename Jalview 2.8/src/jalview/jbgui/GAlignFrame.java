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

import jalview.schemes.ColourSchemeProperty;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class GAlignFrame extends JInternalFrame
{
  protected JMenuBar alignFrameMenuBar = new JMenuBar();

  protected JMenu fileMenu = new JMenu();

  protected JMenuItem closeMenuItem = new JMenuItem();

  protected JMenu editMenu = new JMenu();

  protected JMenu viewMenu = new JMenu();

  protected JMenu colourMenu = new JMenu();

  protected JMenu calculateMenu = new JMenu();

  protected JMenu webService = new JMenu();

  protected JMenuItem webServiceNoServices;

  protected JMenuItem selectAllSequenceMenuItem = new JMenuItem();

  protected JMenuItem deselectAllSequenceMenuItem = new JMenuItem();

  protected JMenuItem invertSequenceMenuItem = new JMenuItem();

  protected JMenuItem remove2LeftMenuItem = new JMenuItem();

  protected JMenuItem remove2RightMenuItem = new JMenuItem();

  protected JMenuItem removeGappedColumnMenuItem = new JMenuItem();

  protected JMenuItem removeAllGapsMenuItem = new JMenuItem();

  public JCheckBoxMenuItem viewBoxesMenuItem = new JCheckBoxMenuItem();

  public JCheckBoxMenuItem viewTextMenuItem = new JCheckBoxMenuItem();

  protected JMenuItem sortPairwiseMenuItem = new JMenuItem();

  protected JMenuItem sortIDMenuItem = new JMenuItem();

  protected JMenuItem sortLengthMenuItem = new JMenuItem();

  protected JMenuItem sortGroupMenuItem = new JMenuItem();

  protected JMenu sortByAnnotScore = new JMenu();

  protected JMenuItem removeRedundancyMenuItem = new JMenuItem();

  protected JMenuItem pairwiseAlignmentMenuItem = new JMenuItem();

  protected JMenuItem PCAMenuItem = new JMenuItem();

  protected JMenuItem averageDistanceTreeMenuItem = new JMenuItem();

  protected JMenuItem neighbourTreeMenuItem = new JMenuItem();

  BorderLayout borderLayout1 = new BorderLayout();

  public JLabel statusBar = new JLabel();

  protected JMenuItem saveAs = new JMenuItem();

  protected JMenu outputTextboxMenu = new JMenu();

  protected JRadioButtonMenuItem clustalColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem zappoColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem taylorColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem hydrophobicityColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem helixColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem strandColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem turnColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem buriedColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem userDefinedColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem PIDColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem BLOSUM62Colour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem nucleotideColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem purinePyrimidineColour = new JRadioButtonMenuItem();

  // protected JRadioButtonMenuItem covariationColour = new
  // JRadioButtonMenuItem();

  protected JRadioButtonMenuItem tcoffeeColour = new JRadioButtonMenuItem();

  JMenuItem njTreeBlosumMenuItem = new JMenuItem();

  JMenuItem avDistanceTreeBlosumMenuItem = new JMenuItem();

  public JCheckBoxMenuItem annotationPanelMenuItem = new JCheckBoxMenuItem();

  public JCheckBoxMenuItem colourTextMenuItem = new JCheckBoxMenuItem();

  public JCheckBoxMenuItem showNonconservedMenuItem = new JCheckBoxMenuItem();

  JMenuItem htmlMenuItem = new JMenuItem();

  JMenuItem overviewMenuItem = new JMenuItem();

  protected JMenuItem undoMenuItem = new JMenuItem();

  protected JMenuItem redoMenuItem = new JMenuItem();

  public JCheckBoxMenuItem conservationMenuItem = new JCheckBoxMenuItem();

  JRadioButtonMenuItem noColourmenuItem = new JRadioButtonMenuItem();

  public JCheckBoxMenuItem wrapMenuItem = new JCheckBoxMenuItem();

  JMenuItem printMenuItem = new JMenuItem();

  public JCheckBoxMenuItem renderGapsMenuItem = new JCheckBoxMenuItem();

  JMenuItem findMenuItem = new JMenuItem();

  public JCheckBoxMenuItem abovePIDThreshold = new JCheckBoxMenuItem();

  public JCheckBoxMenuItem showSeqFeatures = new JCheckBoxMenuItem();

  public JCheckBoxMenuItem showSeqFeaturesHeight = new JCheckBoxMenuItem();

  JMenuItem deleteGroups = new JMenuItem();

  JMenuItem delete = new JMenuItem();

  JMenuItem copy = new JMenuItem();

  JMenuItem cut = new JMenuItem();

  JMenu pasteMenu = new JMenu();

  JMenuItem pasteNew = new JMenuItem();

  JMenuItem pasteThis = new JMenuItem();

  public JCheckBoxMenuItem applyToAllGroups = new JCheckBoxMenuItem();

  JMenuItem createPNG = new JMenuItem();

  protected JMenuItem font = new JMenuItem();

  public JCheckBoxMenuItem seqLimits = new JCheckBoxMenuItem();

  JMenuItem epsFile = new JMenuItem();

  JMenuItem LoadtreeMenuItem = new JMenuItem();

  public JCheckBoxMenuItem scaleAbove = new JCheckBoxMenuItem();

  public JCheckBoxMenuItem scaleLeft = new JCheckBoxMenuItem();

  public JCheckBoxMenuItem scaleRight = new JCheckBoxMenuItem();

  protected JMenuItem modifyPID = new JMenuItem();

  protected JMenuItem modifyConservation = new JMenuItem();

  protected JMenu sortByTreeMenu = new JMenu();

  protected JMenu sort = new JMenu();

  protected JMenu calculateTree = new JMenu();

  JMenu jMenu2 = new JMenu();
  JMenuItem exportToWorkspace = new JMenuItem();

  protected JCheckBoxMenuItem padGapsMenuitem = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem showNpFeatsMenuitem = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem showDbRefsMenuitem = new JCheckBoxMenuItem();

  protected ButtonGroup colours = new ButtonGroup();

  JMenuItem vamsasStore = new JMenuItem();

  protected JMenuItem showTranslation = new JMenuItem();

  protected JMenuItem extractScores = new JMenuItem();

  protected JMenu showProducts = new JMenu();

  public JMenuItem openFeatureSettings = new JMenuItem();

  JMenuItem fetchSequence = new JMenuItem();

  JMenuItem annotationColour = new JMenuItem();

  protected JMenuItem rnahelicesColour = new JMenuItem();

  JMenuItem associatedData = new JMenuItem();

  protected JCheckBoxMenuItem autoCalculate = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem sortByTree = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem listenToViewSelections = new JCheckBoxMenuItem();

  JMenu addSequenceMenu = new JMenu();

  JMenuItem addFromFile = new JMenuItem();

  JMenuItem addFromText = new JMenuItem();

  JMenuItem addFromURL = new JMenuItem();

  JMenuItem exportAnnotations = new JMenuItem();

  JMenuItem exportFeatures = new JMenuItem();

  protected JPanel statusPanel = new JPanel();

  GridLayout gridLayout1 = new GridLayout();

  JMenu jMenu3 = new JMenu();

  JMenuItem showAllSeqs = new JMenuItem();

  JMenuItem showAllColumns = new JMenuItem();

  JMenu hideMenu = new JMenu();

  JMenuItem hideSelSequences = new JMenuItem();

  JMenuItem hideSelColumns = new JMenuItem();

  JMenuItem hideAllButSelection = new JMenuItem();

  JMenuItem hideAllSelection = new JMenuItem();

  JMenuItem showAllhidden = new JMenuItem();

  protected JCheckBoxMenuItem hiddenMarkers = new JCheckBoxMenuItem();

  JMenuItem invertColSel = new JMenuItem();

  protected JTabbedPane tabbedPane = new JTabbedPane();

  JMenuItem save = new JMenuItem();

  protected JMenuItem reload = new JMenuItem();

  JMenuItem newView = new JMenuItem();

  JMenuItem textColour = new JMenuItem();

  JMenu formatMenu = new JMenu();

  JMenu selectMenu = new JMenu();

  protected JCheckBoxMenuItem idRightAlign = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem centreColumnLabelsMenuItem = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem followHighlightMenuItem = new JCheckBoxMenuItem();

  protected JMenuItem gatherViews = new JMenuItem();

  protected JMenuItem expandViews = new JMenuItem();

  JMenuItem pageSetup = new JMenuItem();

  JMenuItem alignmentProperties = new JMenuItem();

  JMenu tooltipSettingsMenu = new JMenu();

  private JMenuItem justifyLeftMenuItem = new JMenuItem();

  private JMenuItem justifyRightMenuItem = new JMenuItem();

  JMenu autoAnnMenu = new JMenu();

  protected JCheckBoxMenuItem showGroupConsensus = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem showGroupConservation = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem showConsensusHistogram = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem showSequenceLogo = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem normaliseSequenceLogo = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem applyAutoAnnotationSettings = new JCheckBoxMenuItem();

  private JMenuItem grpsFromSelection = new JMenuItem();

  public GAlignFrame()
  {
    try
    {
      jbInit();
      setJMenuBar(alignFrameMenuBar);

      // dynamically fill save as menu with available formats
      for (int i = 0; i < jalview.io.FormatAdapter.WRITEABLE_FORMATS.length; i++)
      {
        JMenuItem item = new JMenuItem(
                jalview.io.FormatAdapter.WRITEABLE_FORMATS[i]);

        item.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
          public void actionPerformed(ActionEvent e)
          {
            outputText_actionPerformed(e);
          }
        });

        outputTextboxMenu.add(item);
      }
    } catch (Exception e)
    {
    }

    if (!new jalview.util.Platform().isAMac())
    {
      closeMenuItem.setMnemonic('C');
      outputTextboxMenu.setMnemonic('T');
      undoMenuItem.setMnemonic('Z');
      redoMenuItem.setMnemonic('0');
      copy.setMnemonic('C');
      cut.setMnemonic('U');
      pasteMenu.setMnemonic('P');
      reload.setMnemonic('R');
    }

    if (jalview.gui.UserDefinedColours.getUserColourSchemes() != null)
    {
      java.util.Enumeration userColours = jalview.gui.UserDefinedColours
              .getUserColourSchemes().keys();

      while (userColours.hasMoreElements())
      {
        final JRadioButtonMenuItem radioItem = new JRadioButtonMenuItem(
                userColours.nextElement().toString());
        radioItem.setName("USER_DEFINED");
        radioItem.addMouseListener(new MouseAdapter()
        {
            @Override
          public void mousePressed(MouseEvent evt)
          {
            if (evt.isControlDown()
                    || SwingUtilities.isRightMouseButton(evt))
            {
              radioItem.removeActionListener(radioItem.getActionListeners()[0]);

              int option = JOptionPane.showInternalConfirmDialog(
                      jalview.gui.Desktop.desktop,
                      "Remove from default list?",
                      "Remove user defined colour",
                      JOptionPane.YES_NO_OPTION);
              if (option == JOptionPane.YES_OPTION)
              {
                jalview.gui.UserDefinedColours
                        .removeColourFromDefaults(radioItem.getText());
                colourMenu.remove(radioItem);
              }
              else
              {
                radioItem.addActionListener(new ActionListener()
                {
                    @Override
                  public void actionPerformed(ActionEvent evt)
                  {
                    userDefinedColour_actionPerformed(evt);
                  }
                });
              }
            }
          }
        });
        radioItem.addActionListener(new ActionListener()
        {
            @Override
          public void actionPerformed(ActionEvent evt)
          {
            userDefinedColour_actionPerformed(evt);
          }
        });
        colourMenu.insert(radioItem, 15);
        colours.add(radioItem);
      }
    }
    colours.add(noColourmenuItem);
    colours.add(clustalColour);
    colours.add(zappoColour);
    colours.add(taylorColour);
    colours.add(hydrophobicityColour);
    colours.add(helixColour);
    colours.add(strandColour);
    colours.add(turnColour);
    colours.add(buriedColour);
    colours.add(userDefinedColour);
    colours.add(PIDColour);
    colours.add(BLOSUM62Colour);
    colours.add(nucleotideColour);
    colours.add(purinePyrimidineColour);
    // colours.add(covariationColour);
    colours.add(tcoffeeColour);

    setColourSelected(jalview.bin.Cache
            .getDefault("DEFAULT_COLOUR", "None"));

  }

  public void setColourSelected(String defaultColour)
  {

    if (defaultColour != null)
    {
      int index = ColourSchemeProperty
              .getColourIndexFromName(defaultColour);

      switch (index)
      {
      case ColourSchemeProperty.CLUSTAL:
        clustalColour.setSelected(true);

        break;

      case ColourSchemeProperty.BLOSUM:
        BLOSUM62Colour.setSelected(true);

        break;

      case ColourSchemeProperty.PID:
        PIDColour.setSelected(true);

        break;

      case ColourSchemeProperty.ZAPPO:
        zappoColour.setSelected(true);

        break;

      case ColourSchemeProperty.TAYLOR:
        taylorColour.setSelected(true);
        break;

      case ColourSchemeProperty.HYDROPHOBIC:
        hydrophobicityColour.setSelected(true);

        break;

      case ColourSchemeProperty.HELIX:
        helixColour.setSelected(true);

        break;

      case ColourSchemeProperty.STRAND:
        strandColour.setSelected(true);

        break;

      case ColourSchemeProperty.TURN:
        turnColour.setSelected(true);

        break;

      case ColourSchemeProperty.BURIED:
        buriedColour.setSelected(true);

        break;

      case ColourSchemeProperty.NUCLEOTIDE:
        nucleotideColour.setSelected(true);

        break;

      case ColourSchemeProperty.TCOFFEE:
        tcoffeeColour.setSelected(true);
        break;

      case ColourSchemeProperty.PURINEPYRIMIDINE:
        purinePyrimidineColour.setSelected(true);

        break;
      /*
       * case ColourSchemeProperty.COVARIATION:
       * covariationColour.setSelected(true);
       * 
       * break;
       */
      case ColourSchemeProperty.USER_DEFINED:
        userDefinedColour.setSelected(true);

        break;
      case ColourSchemeProperty.NONE:
      default:
        noColourmenuItem.setSelected(true);
        break;

      }
    }

  }

  private void jbInit() throws Exception
  {
    fileMenu.setText("File");
    saveAs.setText("Save As...");
    saveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_S, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask()
                    | java.awt.event.KeyEvent.SHIFT_MASK, false));
    saveAs.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        saveAs_actionPerformed(e);
      }
    });
    closeMenuItem.setText("Close");
    closeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_W, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    closeMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        closeMenuItem_actionPerformed(false);
      }
    });
    editMenu.setText("Edit");
    viewMenu.setText("View");
    colourMenu.setText("Colour");
    calculateMenu.setText("Calculate");
    webService.setText("Web Service");
    selectAllSequenceMenuItem.setText("Select All");
    selectAllSequenceMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_A, Toolkit
                    .getDefaultToolkit().getMenuShortcutKeyMask(), false));
    selectAllSequenceMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                selectAllSequenceMenuItem_actionPerformed(e);
              }
            });
    deselectAllSequenceMenuItem.setText("Deselect All");
    deselectAllSequenceMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false));
    deselectAllSequenceMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                deselectAllSequenceMenuItem_actionPerformed(e);
              }
            });
    invertSequenceMenuItem.setText("Invert Sequence Selection");
    invertSequenceMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_I, Toolkit
                    .getDefaultToolkit().getMenuShortcutKeyMask(), false));
    invertSequenceMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                invertSequenceMenuItem_actionPerformed(e);
              }
            });
    grpsFromSelection.setText("Make Groups for selection");
    grpsFromSelection.addActionListener(new java.awt.event.ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        makeGrpsFromSelection_actionPerformed(e);
      }
    });

    remove2LeftMenuItem.setText("Remove Left");
    remove2LeftMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_L, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    remove2LeftMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                remove2LeftMenuItem_actionPerformed(e);
              }
            });
    remove2RightMenuItem.setText("Remove Right");
    remove2RightMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_R, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    remove2RightMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                remove2RightMenuItem_actionPerformed(e);
              }
            });
    removeGappedColumnMenuItem.setText("Remove Empty Columns");
    removeGappedColumnMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_E, Toolkit
                    .getDefaultToolkit().getMenuShortcutKeyMask(), false));
    removeGappedColumnMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                removeGappedColumnMenuItem_actionPerformed(e);
              }
            });
    removeAllGapsMenuItem.setText("Remove All Gaps");
    removeAllGapsMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_E, Toolkit
                    .getDefaultToolkit().getMenuShortcutKeyMask()
                    | java.awt.event.KeyEvent.SHIFT_MASK, false));
    removeAllGapsMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                removeAllGapsMenuItem_actionPerformed(e);
              }
            });
    justifyLeftMenuItem.setText("Left Justify Alignment");
    justifyLeftMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                justifyLeftMenuItem_actionPerformed(e);
              }
            });
    justifyRightMenuItem.setText("Right Justify Alignment");
    justifyRightMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                justifyRightMenuItem_actionPerformed(e);
              }
            });
    viewBoxesMenuItem.setText("Boxes");
    viewBoxesMenuItem.setState(true);
    viewBoxesMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        viewBoxesMenuItem_actionPerformed(e);
      }
    });
    viewTextMenuItem.setText("Text");
    viewTextMenuItem.setState(true);
    viewTextMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        viewTextMenuItem_actionPerformed(e);
      }
    });
    showNonconservedMenuItem.setText("Show nonconserved");
    showNonconservedMenuItem.setState(false);
    showNonconservedMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                showUnconservedMenuItem_actionPerformed(e);
              }
            });
    sortPairwiseMenuItem.setText("by Pairwise Identity");
    sortPairwiseMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                sortPairwiseMenuItem_actionPerformed(e);
              }
            });
    sortIDMenuItem.setText("by ID");
    sortIDMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        sortIDMenuItem_actionPerformed(e);
      }
    });
    sortLengthMenuItem.setText("By Length");
    sortLengthMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                sortLengthMenuItem_actionPerformed(e);
              }
            });
    sortGroupMenuItem.setText("by Group");
    sortGroupMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        sortGroupMenuItem_actionPerformed(e);
      }
    });
    removeRedundancyMenuItem.setText("Remove Redundancy...");
    removeRedundancyMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_D, Toolkit
                    .getDefaultToolkit().getMenuShortcutKeyMask(), false));
    removeRedundancyMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                removeRedundancyMenuItem_actionPerformed(e);
              }
            });
    pairwiseAlignmentMenuItem.setText("Pairwise Alignments...");
    pairwiseAlignmentMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                pairwiseAlignmentMenuItem_actionPerformed(e);
              }
            });
    PCAMenuItem.setText("Principal Component Analysis");
    PCAMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        PCAMenuItem_actionPerformed(e);
      }
    });
    averageDistanceTreeMenuItem
            .setText("Average Distance Using % Identity");
    averageDistanceTreeMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                averageDistanceTreeMenuItem_actionPerformed(e);
              }
            });
    neighbourTreeMenuItem.setText("Neighbour Joining Using % Identity");
    neighbourTreeMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                neighbourTreeMenuItem_actionPerformed(e);
              }
            });
    this.getContentPane().setLayout(borderLayout1);
    alignFrameMenuBar.setFont(new java.awt.Font("Verdana", 0, 11));
    statusBar.setBackground(Color.white);
    statusBar.setFont(new java.awt.Font("Verdana", 0, 11));
    statusBar.setBorder(BorderFactory.createLineBorder(Color.black));
    statusBar.setText("Status bar");
    outputTextboxMenu.setText("Output to Textbox");
    clustalColour.setText("Clustalx");

    clustalColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        clustalColour_actionPerformed(e);
      }
    });
    zappoColour.setText("Zappo");
    zappoColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        zappoColour_actionPerformed(e);
      }
    });
    taylorColour.setText("Taylor");
    taylorColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        taylorColour_actionPerformed(e);
      }
    });
    hydrophobicityColour.setText("Hydrophobicity");
    hydrophobicityColour
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                hydrophobicityColour_actionPerformed(e);
              }
            });
    helixColour.setText("Helix Propensity");
    helixColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        helixColour_actionPerformed(e);
      }
    });
    strandColour.setText("Strand Propensity");
    strandColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        strandColour_actionPerformed(e);
      }
    });
    turnColour.setText("Turn Propensity");
    turnColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        turnColour_actionPerformed(e);
      }
    });
    buriedColour.setText("Buried Index");
    buriedColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        buriedColour_actionPerformed(e);
      }
    });
    userDefinedColour.setText("User Defined...");
    userDefinedColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        userDefinedColour_actionPerformed(e);
      }
    });
    PIDColour.setText("Percentage Identity");
    PIDColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        PIDColour_actionPerformed(e);
      }
    });
    BLOSUM62Colour.setText("BLOSUM62 Score");
    BLOSUM62Colour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        BLOSUM62Colour_actionPerformed(e);
      }
    });
    nucleotideColour.setText("Nucleotide");
    nucleotideColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nucleotideColour_actionPerformed(e);
      }
    });

    purinePyrimidineColour.setText("Purine/Pyrimidine");
    purinePyrimidineColour
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                purinePyrimidineColour_actionPerformed(e);
              }
            });
    /*
     * covariationColour.setText("Covariation");
     * covariationColour.addActionListener(new java.awt.event.ActionListener() {
     * public void actionPerformed(ActionEvent e) {
     * covariationColour_actionPerformed(e); } });
     */

    avDistanceTreeBlosumMenuItem.setText("Average Distance Using BLOSUM62");
    avDistanceTreeBlosumMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                avTreeBlosumMenuItem_actionPerformed(e);
              }
            });
    njTreeBlosumMenuItem.setText("Neighbour Joining using BLOSUM62");
    njTreeBlosumMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                njTreeBlosumMenuItem_actionPerformed(e);
              }
            });
    annotationPanelMenuItem.setActionCommand("");
    annotationPanelMenuItem.setText("Show Annotations");
    annotationPanelMenuItem.setState(jalview.bin.Cache.getDefault(
            "SHOW_ANNOTATIONS", true));
    annotationPanelMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                annotationPanelMenuItem_actionPerformed(e);
              }
            });
    colourTextMenuItem.setText("Colour Text");
    colourTextMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                colourTextMenuItem_actionPerformed(e);
              }
            });
    htmlMenuItem.setText("HTML");
    htmlMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        htmlMenuItem_actionPerformed(e);
      }
    });
    overviewMenuItem.setText("Overview Window");
    overviewMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        overviewMenuItem_actionPerformed(e);
      }
    });
    undoMenuItem.setEnabled(false);
    undoMenuItem.setText("Undo");
    undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_Z, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    undoMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        undoMenuItem_actionPerformed(e);
      }
    });
    redoMenuItem.setEnabled(false);
    redoMenuItem.setText("Redo");
    redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_Y, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    redoMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        redoMenuItem_actionPerformed(e);
      }
    });
    conservationMenuItem.setText("By Conservation");
    conservationMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                conservationMenuItem_actionPerformed(e);
              }
            });
    noColourmenuItem.setText("None");
    noColourmenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        noColourmenuItem_actionPerformed(e);
      }
    });
    wrapMenuItem.setText("Wrap");
    wrapMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        wrapMenuItem_actionPerformed(e);
      }
    });
    printMenuItem.setText("Print ...");
    printMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_P, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    printMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        printMenuItem_actionPerformed(e);
      }
    });
    renderGapsMenuItem.setText("Show Gaps");
    renderGapsMenuItem.setState(true);
    renderGapsMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                renderGapsMenuItem_actionPerformed(e);
              }
            });
    findMenuItem.setText("Find...");
    findMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_F, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    findMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        findMenuItem_actionPerformed(e);
      }
    });
    abovePIDThreshold.setText("Above Identity Threshold");
    abovePIDThreshold.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        abovePIDThreshold_actionPerformed(e);
      }
    });
    showSeqFeatures.setText("Show Sequence Features");
    showSeqFeatures.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        showSeqFeatures_actionPerformed(actionEvent);
      }
    });
    /*
     * showSeqFeaturesHeight.setText("Vary Sequence Feature Height");
     * showSeqFeaturesHeight.addActionListener(new ActionListener() { public
     * void actionPerformed(ActionEvent actionEvent) {
     * showSeqFeaturesHeight_actionPerformed(actionEvent); } });
     */
    showDbRefsMenuitem.setText("Show Database Refs");
    showDbRefsMenuitem.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        showDbRefs_actionPerformed(e);
      }

    });
    showNpFeatsMenuitem.setText("Show Non-Positional Features");
    showNpFeatsMenuitem.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        showNpFeats_actionPerformed(e);
      }

    });
    showGroupConservation.setText("Group Conservation");
    showGroupConservation.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        showGroupConservation_actionPerformed(e);
      }

    });

    showGroupConsensus.setText("Group Consensus");
    showGroupConsensus.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        showGroupConsensus_actionPerformed(e);
      }

    });
    showConsensusHistogram.setText("Show Consensus Histogram");
    showConsensusHistogram.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        showConsensusHistogram_actionPerformed(e);
      }

    });
    showSequenceLogo.setText("Show Consensus Logo");
    showSequenceLogo.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        showSequenceLogo_actionPerformed(e);
      }

    });
    normaliseSequenceLogo.setText("Normalise Consensus Logo");
    normaliseSequenceLogo.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        normaliseSequenceLogo_actionPerformed(e);
      }

    });
    applyAutoAnnotationSettings.setText("Apply to all groups");
    applyAutoAnnotationSettings.setState(false);
    applyAutoAnnotationSettings.setVisible(true);
    applyAutoAnnotationSettings.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        applyAutoAnnotationSettings_actionPerformed(e);
      }

    });

    nucleotideColour.setText("Nucleotide");
    nucleotideColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nucleotideColour_actionPerformed(e);
      }
    });

    tcoffeeColour.setText("T-Coffee scores");
    tcoffeeColour.setEnabled(false);
    tcoffeeColour.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        tcoffeeColorScheme_actionPerformed(e);
      }
    });

    deleteGroups.setText("Undefine groups");
    deleteGroups.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_U, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    deleteGroups.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        deleteGroups_actionPerformed(e);
      }
    });
    copy.setText("Copy");
    copy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_C, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));

    copy.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        copy_actionPerformed(e);
      }
    });
    cut.setText("Cut");
    cut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_X, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    cut.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cut_actionPerformed(e);
      }
    });
    delete.setText("Delete");
    delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_BACK_SPACE, 0, false));
    delete.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        delete_actionPerformed(e);
      }
    });
    pasteMenu.setText("Paste");
    pasteNew.setText("To New Alignment");
    pasteNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_V, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask()
                    | java.awt.event.KeyEvent.SHIFT_MASK, false));
    pasteNew.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        pasteNew_actionPerformed(e);
      }
    });
    pasteThis.setText("Add To This Alignment");
    pasteThis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_V, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    pasteThis.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        pasteThis_actionPerformed(e);
      }
    });
    applyToAllGroups.setText("Apply Colour To All Groups");
    applyToAllGroups.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        applyToAllGroups_actionPerformed(e);
      }
    });
    createPNG.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        createPNG(null);
      }
    });
    createPNG.setActionCommand("Save As PNG Image");
    createPNG.setText("PNG");
    font.setText("Font...");
    font.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        font_actionPerformed(e);
      }
    });

    seqLimits.setText("Show Sequence Limits");
    seqLimits.setState(jalview.bin.Cache.getDefault("SHOW_JVSUFFIX", true));
    seqLimits.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        seqLimit_actionPerformed(e);
      }
    });
    epsFile.setText("EPS");
    epsFile.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        createEPS(null);
      }
    });
    LoadtreeMenuItem.setActionCommand("Load a tree for this sequence set");
    LoadtreeMenuItem.setText("Load Associated Tree");
    LoadtreeMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        LoadtreeMenuItem_actionPerformed(e);
      }
    });

    scaleAbove.setVisible(false);
    scaleAbove.setText("Scale Above");
    scaleAbove.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        scaleAbove_actionPerformed(e);
      }
    });
    scaleLeft.setVisible(false);
    scaleLeft.setSelected(true);
    scaleLeft.setText("Scale Left");
    scaleLeft.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        scaleLeft_actionPerformed(e);
      }
    });
    scaleRight.setVisible(false);
    scaleRight.setSelected(true);
    scaleRight.setText("Scale Right");
    scaleRight.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        scaleRight_actionPerformed(e);
      }
    });
    centreColumnLabelsMenuItem.setVisible(true);
    centreColumnLabelsMenuItem.setState(false);
    centreColumnLabelsMenuItem.setText("Centre Column Labels");
    centreColumnLabelsMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                centreColumnLabels_actionPerformed(e);
              }
            });
    followHighlightMenuItem.setVisible(true);
    followHighlightMenuItem.setState(true);
    followHighlightMenuItem.setText("Automatic Scrolling");
    followHighlightMenuItem.addActionListener(new ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        followHighlight_actionPerformed();
      }

    });

    modifyPID.setText("Modify Identity Threshold...");
    modifyPID.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        modifyPID_actionPerformed(e);
      }
    });
    modifyConservation.setText("Modify Conservation Threshold...");
    modifyConservation
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                modifyConservation_actionPerformed(e);
              }
            });
    sortByTreeMenu.setText("By Tree Order");
    sort.setText("Sort");
    sort.addMenuListener(new MenuListener()
    {
      public void menuSelected(MenuEvent e)
      {
        buildTreeMenu();
      }

      public void menuDeselected(MenuEvent e)
      {
      }

      public void menuCanceled(MenuEvent e)
      {
      }
    });
    sortByAnnotScore.setText("by Score");
    sort.add(sortByAnnotScore);
    sortByAnnotScore.addMenuListener(new javax.swing.event.MenuListener()
    {

      public void menuCanceled(MenuEvent e)
      {
      }

      public void menuDeselected(MenuEvent e)
      {
      }

      public void menuSelected(MenuEvent e)
      {
        buildSortByAnnotationScoresMenu();
      }
    });
    sortByAnnotScore.setVisible(false);

    calculateTree.setText("Calculate Tree");

    jMenu2.setText("Export Image");
    this.exportToWorkspace.setText("Export to Workspace");
    padGapsMenuitem.setText("Pad Gaps");
    padGapsMenuitem.setState(jalview.bin.Cache
            .getDefault("PAD_GAPS", false));
    
    this.exportToWorkspace.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
              exportToWorkspaceMenuitem_actionPerformed(e);
          }

         
        
    });
    padGapsMenuitem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        padGapsMenuitem_actionPerformed(e);
      }
    });
    vamsasStore.setVisible(false);
    vamsasStore.setText("VAMSAS store");
    vamsasStore.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        vamsasStore_actionPerformed(e);
      }
    });
    showTranslation.setText("Translate cDNA");
    showTranslation.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showTranslation_actionPerformed(e);
      }
    });
    extractScores.setText("Extract Scores...");
    extractScores.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        extractScores_actionPerformed(e);
      }
    });
    extractScores.setVisible(true); // JBPNote: TODO: make gui for regex based
    // score extraction
    showProducts.setText("Get Cross References");
    /*
     * showProducts.addActionListener(new ActionListener() {
     * 
     * public void actionPerformed(ActionEvent e) {
     * showProducts_actionPerformed(e); } });
     */
    openFeatureSettings.setText("Feature Settings...");
    openFeatureSettings.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        featureSettings_actionPerformed(e);
      }
    });
    fetchSequence.setText("Fetch Sequence(s)...");
    fetchSequence.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        fetchSequence_actionPerformed(e);
      }
    });

    annotationColour.setText("By Annotation...");
    annotationColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        annotationColour_actionPerformed(e);
      }
    });

    rnahelicesColour.setText("By RNA helices");
    rnahelicesColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        rnahelicesColour_actionPerformed(e);
      }
    });

    associatedData.setText("Load Features / Annotations");
    associatedData.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        associatedData_actionPerformed(e);
      }
    });
    autoCalculate.setText("Autocalculate Consensus");
    autoCalculate.setState(jalview.bin.Cache.getDefault(
            "AUTO_CALC_CONSENSUS", true));
    autoCalculate.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        autoCalculate_actionPerformed(e);
      }
    });
    sortByTree.setText("Sort Alignment With New Tree");
    sortByTree
            .setToolTipText("<html>Enable this to automatically sort<br>the alignment when you open<br> a new tree.");
    sortByTree
            .setState(jalview.bin.Cache.getDefault("SORT_BY_TREE", false));
    sortByTree.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        sortByTreeOption_actionPerformed(e);
      }
    });

    listenToViewSelections.setText("Listen for selections");
    listenToViewSelections
            .setToolTipText("<html>When selected, selections in this view will mirror<br>selections made on the same sequences in other views.");
    listenToViewSelections.setState(false);
    listenToViewSelections.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        listenToViewSelections_actionPerformed(e);
      }
    });

    addSequenceMenu.setText("Add Sequences");
    addFromFile.setText("From File");
    addFromFile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        addFromFile_actionPerformed(e);
      }
    });
    addFromText.setText("From Textbox");
    addFromText.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        addFromText_actionPerformed(e);
      }
    });
    addFromURL.setText("From URL");
    addFromURL.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        addFromURL_actionPerformed(e);
      }
    });
    exportFeatures.setText("Export Features...");
    exportFeatures.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        exportFeatures_actionPerformed(e);
      }
    });
    exportAnnotations.setText("Export Annotations...");
    exportAnnotations.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        exportAnnotations_actionPerformed(e);
      }
    });
    statusPanel.setLayout(gridLayout1);
    jMenu3.setText("Show");
    showAllSeqs.setText("All Sequences");
    showAllSeqs.setToolTipText("Shift+H toggles sequence visiblity.");
    showAllSeqs.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showAllSeqs_actionPerformed(e);
      }
    });
    showAllColumns.setText("All Columns");
    showAllColumns.setToolTipText("Ctrl+H toggles column visiblity.");
    showAllColumns.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showAllColumns_actionPerformed(e);
      }
    });
    hideMenu.setText("Hide");
    hideSelSequences.setText("Selected Sequences");
    hideSelSequences.setToolTipText("Shift+H toggles sequence visiblity.");
    hideSelSequences.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        hideSelSequences_actionPerformed(e);
      }
    });
    hideSelColumns.setText("Selected Columns");
    hideSelColumns.setToolTipText("Ctrl+H toggles column visiblity.");
    hideSelColumns.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        hideSelColumns_actionPerformed(e);
      }
    });
    hideAllSelection.setText("Selected Region");
    hideAllSelection.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        hideAllSelection_actionPerformed(e);
      }
    });
    // TODO: should be hidden if no selection exists.
    hideAllButSelection.setText("All but Selected Region (Shift+Ctrl+H)");
    hideAllButSelection.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        hideAllButSelection_actionPerformed(e);
      }
    });
    showAllhidden.setText("All Sequences and Columns");
    showAllhidden
            .setToolTipText("H toggles visibility of hidden or selected regions.");
    showAllhidden.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showAllhidden_actionPerformed(e);
      }
    });

    hiddenMarkers.setText("Show Hidden Markers");
    hiddenMarkers.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        hiddenMarkers_actionPerformed(e);
      }
    });
    invertColSel.setText("Invert Column Selection");
    invertColSel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_I, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask()
                    | java.awt.event.KeyEvent.ALT_MASK, false));
    invertColSel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        invertColSel_actionPerformed(e);
      }
    });
    tabbedPane.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(ChangeEvent evt)
      {
        JTabbedPane pane = (JTabbedPane) evt.getSource();
        int sel = pane.getSelectedIndex();
        tabSelectionChanged(sel);
      }
    });
    tabbedPane.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        tabbedPane_mousePressed(e);
      }
    });
    tabbedPane.addFocusListener(new FocusAdapter()
    {
      public void focusGained(FocusEvent e)
      {
        tabbedPane_focusGained(e);
      }
    });
    save.setText("Save");
    save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_S, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    save.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        save_actionPerformed(e);
      }
    });
    reload.setEnabled(false);
    reload.setText("Reload");
    reload.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        reload_actionPerformed(e);
      }
    });
    newView.setText("New View");
    newView.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_T, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    newView.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        newView_actionPerformed(e);
      }
    });
    tabbedPane.setToolTipText("<html><i> Right-click to rename tab"
            + "<br> Press X to eXpand tabs, G to reGroup.</i></html>");
    textColour.setText("Colour Text ...");
    textColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        textColour_actionPerformed(e);
      }
    });
    formatMenu.setText("Format");
    selectMenu.setText("Select");
    idRightAlign.setText("Right Align Sequence Id");
    idRightAlign.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        idRightAlign_actionPerformed(e);
      }
    });
    gatherViews.setEnabled(false);
    gatherViews.setText("Gather Views");
    gatherViews.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_G, 0, false));
    gatherViews.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        gatherViews_actionPerformed(e);
      }
    });
    expandViews.setEnabled(false);
    expandViews.setText("Expand Views");
    expandViews.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_X, 0, false));
    expandViews.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        expandViews_actionPerformed(e);
      }
    });
    pageSetup.setText("Page Setup ...");
    pageSetup.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        pageSetup_actionPerformed(e);
      }
    });
    alignmentProperties.setText("Alignment Properties...");
    alignmentProperties.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        alignmentProperties();
      }
    });
    tooltipSettingsMenu.setText("Sequence ID Tooltip");
    autoAnnMenu.setText("Autocalculated Annotation");
    alignFrameMenuBar.add(fileMenu);
    alignFrameMenuBar.add(editMenu);
    alignFrameMenuBar.add(selectMenu);
    alignFrameMenuBar.add(viewMenu);
    alignFrameMenuBar.add(formatMenu);
    alignFrameMenuBar.add(colourMenu);
    alignFrameMenuBar.add(calculateMenu);
    alignFrameMenuBar.add(webService);
    fileMenu.add(fetchSequence);
    fileMenu.add(addSequenceMenu);
    fileMenu.add(reload);
    fileMenu.addSeparator();
    fileMenu.add(vamsasStore);
    fileMenu.add(save);
    fileMenu.add(saveAs);
    fileMenu.add(outputTextboxMenu);
    fileMenu.add(pageSetup);
    fileMenu.add(printMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(jMenu2);
    fileMenu.add(this.exportToWorkspace);
    fileMenu.add(exportFeatures);
    fileMenu.add(exportAnnotations);
    fileMenu.add(LoadtreeMenuItem);
    fileMenu.add(associatedData);
    fileMenu.addSeparator();
    fileMenu.add(closeMenuItem);
    editMenu.add(undoMenuItem);
    editMenu.add(redoMenuItem);
    editMenu.add(cut);
    editMenu.add(copy);
    editMenu.add(pasteMenu);
    editMenu.add(delete);
    editMenu.addSeparator();
    editMenu.add(remove2LeftMenuItem);
    editMenu.add(remove2RightMenuItem);
    editMenu.add(removeGappedColumnMenuItem);
    editMenu.add(removeAllGapsMenuItem);
    editMenu.add(removeRedundancyMenuItem);
    editMenu.addSeparator();
    // dont add these yet in the CVS build - they cannot be undone!
    // Excluded from Jalview 2.5 release - undo needs to be implemented.
    // editMenu.add(justifyLeftMenuItem);
    // editMenu.add(justifyRightMenuItem);
    // editMenu.addSeparator();
    editMenu.add(padGapsMenuitem);
    viewMenu.add(newView);
    viewMenu.add(expandViews);
    viewMenu.add(gatherViews);
    viewMenu.addSeparator();
    viewMenu.add(jMenu3);
    viewMenu.add(hideMenu);
    viewMenu.addSeparator();
    viewMenu.add(followHighlightMenuItem);
    viewMenu.add(annotationPanelMenuItem);
    autoAnnMenu.add(applyAutoAnnotationSettings);
    autoAnnMenu.add(showConsensusHistogram);
    autoAnnMenu.add(showSequenceLogo);
    autoAnnMenu.add(normaliseSequenceLogo);
    autoAnnMenu.addSeparator();
    autoAnnMenu.add(showGroupConservation);
    autoAnnMenu.add(showGroupConsensus);
    viewMenu.add(autoAnnMenu);
    viewMenu.addSeparator();
    viewMenu.add(showSeqFeatures);
    // viewMenu.add(showSeqFeaturesHeight);

    viewMenu.add(openFeatureSettings);
    tooltipSettingsMenu.add(showDbRefsMenuitem);
    tooltipSettingsMenu.add(showNpFeatsMenuitem);
    viewMenu.add(tooltipSettingsMenu);
    viewMenu.addSeparator();
    viewMenu.add(alignmentProperties);
    viewMenu.addSeparator();
    viewMenu.add(overviewMenuItem);
    colourMenu.add(applyToAllGroups);
    colourMenu.add(textColour);
    colourMenu.addSeparator();
    colourMenu.add(noColourmenuItem);
    colourMenu.add(clustalColour);
    colourMenu.add(BLOSUM62Colour);
    colourMenu.add(PIDColour);
    colourMenu.add(zappoColour);
    colourMenu.add(taylorColour);
    colourMenu.add(hydrophobicityColour);
    colourMenu.add(helixColour);
    colourMenu.add(strandColour);
    colourMenu.add(turnColour);
    colourMenu.add(buriedColour);
    colourMenu.add(nucleotideColour);
    colourMenu.add(purinePyrimidineColour);
    // colourMenu.add(covariationColour);
    colourMenu.add(tcoffeeColour);
    colourMenu.add(userDefinedColour);
    colourMenu.addSeparator();
    colourMenu.add(conservationMenuItem);
    colourMenu.add(modifyConservation);
    colourMenu.add(abovePIDThreshold);
    colourMenu.add(modifyPID);
    colourMenu.add(annotationColour);
    colourMenu.add(rnahelicesColour);
    calculateMenu.add(sort);
    calculateMenu.add(calculateTree);
    calculateMenu.addSeparator();
    calculateMenu.add(pairwiseAlignmentMenuItem);
    calculateMenu.add(PCAMenuItem);
    calculateMenu.addSeparator();
    calculateMenu.add(showTranslation);
    calculateMenu.add(showProducts);
    calculateMenu.add(autoCalculate);
    calculateMenu.add(sortByTree);
    calculateMenu.addSeparator();
    calculateMenu.add(extractScores);
    webServiceNoServices = new JMenuItem("<No Services>");
    webService.add(webServiceNoServices);
    pasteMenu.add(pasteNew);
    pasteMenu.add(pasteThis);
    sort.add(sortIDMenuItem);
    sort.add(sortLengthMenuItem);
    sort.add(sortGroupMenuItem);
    sort.add(sortPairwiseMenuItem);
    sort.add(sortByTreeMenu);
    calculateTree.add(averageDistanceTreeMenuItem);
    calculateTree.add(neighbourTreeMenuItem);
    calculateTree.add(avDistanceTreeBlosumMenuItem);
    calculateTree.add(njTreeBlosumMenuItem);
    jMenu2.add(htmlMenuItem);
    jMenu2.add(epsFile);
    jMenu2.add(createPNG);
    addSequenceMenu.add(addFromFile);
    addSequenceMenu.add(addFromText);
    addSequenceMenu.add(addFromURL);
    this.getContentPane().add(statusPanel, java.awt.BorderLayout.SOUTH);
    statusPanel.add(statusBar, null);
    this.getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);
    jMenu3.add(showAllColumns);
    jMenu3.add(showAllSeqs);
    jMenu3.add(showAllhidden);
    hideMenu.add(hideSelColumns);
    hideMenu.add(hideSelSequences);
    hideMenu.add(hideAllSelection);
    hideMenu.add(hideAllButSelection);

    formatMenu.add(font);
    formatMenu.addSeparator();
    formatMenu.add(wrapMenuItem);
    formatMenu.add(scaleAbove);
    formatMenu.add(scaleLeft);
    formatMenu.add(scaleRight);
    formatMenu.add(seqLimits);
    formatMenu.add(idRightAlign);
    formatMenu.add(hiddenMarkers);
    formatMenu.add(viewBoxesMenuItem);
    formatMenu.add(viewTextMenuItem);
    formatMenu.add(colourTextMenuItem);
    formatMenu.add(renderGapsMenuItem);
    formatMenu.add(centreColumnLabelsMenuItem);
    formatMenu.add(showNonconservedMenuItem);
    selectMenu.add(findMenuItem);
    selectMenu.addSeparator();
    selectMenu.add(selectAllSequenceMenuItem);
    selectMenu.add(deselectAllSequenceMenuItem);
    selectMenu.add(invertSequenceMenuItem);
    selectMenu.add(invertColSel);
    selectMenu.add(deleteGroups);
    selectMenu.add(grpsFromSelection);
    // TODO - determine if the listenToViewSelections button is needed : see bug
    // JAL-574
    // selectMenu.addSeparator();
    // selectMenu.add(listenToViewSelections);
  }

  protected void normaliseSequenceLogo_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void listenToViewSelections_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showAllhidden_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void hideAllButSelection_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void hideAllSelection_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void applyAutoAnnotationSettings_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showConsensusHistogram_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showSequenceLogo_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void makeGrpsFromSelection_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showGroupConsensus_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showGroupConservation_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showUnconservedMenuItem_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showSeqFeaturesHeight_actionPerformed(
          ActionEvent actionEvent)
  {
    // TODO Auto-generated method stub

  }

  protected void justifyRightMenuItem_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void justifyLeftMenuItem_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void followHighlight_actionPerformed()
  {
    // TODO Auto-generated method stub

  }

  protected void showNpFeats_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showDbRefs_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void centreColumnLabels_actionPerformed(ActionEvent e)
  {
  }

  protected void showProducts_actionPerformed(ActionEvent e)
  {
  }

  protected void buildSortByAnnotationScoresMenu()
  {
  }

  protected void extractScores_actionPerformed(ActionEvent e)
  {
  }

  protected void outputText_actionPerformed(ActionEvent e)
  {
  }

  public void addFromFile_actionPerformed(ActionEvent e)
  {

  }

  public void addFromText_actionPerformed(ActionEvent e)
  {

  }

  public void addFromURL_actionPerformed(ActionEvent e)
  {

  }

  public void exportFeatures_actionPerformed(ActionEvent e)
  {

  }

  public void exportAnnotations_actionPerformed(ActionEvent e)
  {

  }

  protected void htmlMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void closeMenuItem_actionPerformed(boolean b)
  {
  }

  protected void redoMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void undoMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void selectAllSequenceMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void deselectAllSequenceMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void invertSequenceMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void remove2LeftMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void remove2RightMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void removeGappedColumnMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void removeAllGapsMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void wrapMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void viewBoxesMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void viewTextMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void colourTextMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void annotationPanelMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void overviewMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void sortPairwiseMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void sortIDMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void sortLengthMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void sortGroupMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void removeRedundancyMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void pairwiseAlignmentMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void PCAMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void averageDistanceTreeMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void neighbourTreeMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void njTreeBlosumMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void avTreeBlosumMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void clustalColour_actionPerformed(ActionEvent e)
  {
  }

  protected void zappoColour_actionPerformed(ActionEvent e)
  {
  }

  protected void taylorColour_actionPerformed(ActionEvent e)
  {
  }

  protected void hydrophobicityColour_actionPerformed(ActionEvent e)
  {
  }

  protected void helixColour_actionPerformed(ActionEvent e)
  {
  }

  protected void strandColour_actionPerformed(ActionEvent e)
  {
  }

  protected void turnColour_actionPerformed(ActionEvent e)
  {
  }

  protected void buriedColour_actionPerformed(ActionEvent e)
  {
  }

  protected void userDefinedColour_actionPerformed(ActionEvent e)
  {
  }

  protected void PIDColour_actionPerformed(ActionEvent e)
  {
  }

  protected void BLOSUM62Colour_actionPerformed(ActionEvent e)
  {
  }

  protected void purinePyrimidineColour_actionPerformed(ActionEvent e)
  {
  }

  /*
   * protected void covariationColour_actionPerformed(ActionEvent e) { }
   */

  protected void noColourmenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void conservationMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void printMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void renderGapsMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void findMenuItem_actionPerformed(ActionEvent e)
  {
  }

  protected void abovePIDThreshold_actionPerformed(ActionEvent e)
  {
  }

  public void showSeqFeatures_actionPerformed(ActionEvent actionEvent)
  {
  }

  protected void nucleotideColour_actionPerformed(ActionEvent e)
  {
  }

  protected void deleteGroups_actionPerformed(ActionEvent e)
  {
  }

  protected void copy_actionPerformed(ActionEvent e)
  {
  }

  protected void cut_actionPerformed(ActionEvent e)
  {
  }

  protected void delete_actionPerformed(ActionEvent e)
  {
  }

  protected void pasteNew_actionPerformed(ActionEvent e)
  {
  }

  protected void pasteThis_actionPerformed(ActionEvent e)
  {
  }

  protected void applyToAllGroups_actionPerformed(ActionEvent e)
  {
  }

  public void createPNG(java.io.File f)
  {
  }

  protected void font_actionPerformed(ActionEvent e)
  {
  }

  protected void seqLimit_actionPerformed(ActionEvent e)
  {
  }

  public void seqDBRef_actionPerformed(ActionEvent e)
  {

  }

  public void createEPS(java.io.File f)
  {
  }

  protected void LoadtreeMenuItem_actionPerformed(ActionEvent e)
  {

  }

  /**
   * Template method to handle the 'load T-Coffee scores' menu event.
   * <p>
   * Subclasses override this method to provide a custom action.
   * 
   * @param event
   *          The raised event
   */
  protected void loadScores_actionPerformed(ActionEvent event)
  {

  }

  /**
   * Template method to handle the 'Color T-Coffee scores' menu event.
   * <p>
   * Subclasses override this method to provide a custom action.
   * 
   * @param event
   *          The raised event
   */
  protected void tcoffeeColorScheme_actionPerformed(ActionEvent event)
  {

  }

  protected void jpred_actionPerformed(ActionEvent e)
  {
  }

  protected void scaleAbove_actionPerformed(ActionEvent e)
  {
  }

  protected void scaleLeft_actionPerformed(ActionEvent e)
  {
  }

  protected void scaleRight_actionPerformed(ActionEvent e)
  {
  }

  protected void modifyPID_actionPerformed(ActionEvent e)
  {
  }

  protected void modifyConservation_actionPerformed(ActionEvent e)
  {
  }

  protected void saveAs_actionPerformed(ActionEvent e)
  {
  }

  protected void padGapsMenuitem_actionPerformed(ActionEvent e)
  {
  }

   protected void exportToWorkspaceMenuitem_actionPerformed(ActionEvent e) {
             
   }
  public void vamsasStore_actionPerformed(ActionEvent e)
  {

  }

  public void vamsasLoad_actionPerformed(ActionEvent e)
  {

  }

  public void showTranslation_actionPerformed(ActionEvent e)
  {

  }

  public void featureSettings_actionPerformed(ActionEvent e)
  {

  }

  public void fetchSequence_actionPerformed(ActionEvent e)
  {

  }

  public void smoothFont_actionPerformed(ActionEvent e)
  {

  }

  public void annotationColour_actionPerformed(ActionEvent e)
  {

  }

  public void rnahelicesColour_actionPerformed(ActionEvent e)
  {

  }

  public void associatedData_actionPerformed(ActionEvent e)
  {

  }

  public void autoCalculate_actionPerformed(ActionEvent e)
  {

  }

  public void sortByTreeOption_actionPerformed(ActionEvent e)
  {

  }

  public void showAllSeqs_actionPerformed(ActionEvent e)
  {

  }

  public void showAllColumns_actionPerformed(ActionEvent e)
  {

  }

  public void hideSelSequences_actionPerformed(ActionEvent e)
  {

  }

  public void hideSelColumns_actionPerformed(ActionEvent e)
  {

  }

  public void hiddenMarkers_actionPerformed(ActionEvent e)
  {

  }

  public void findPdbId_actionPerformed(ActionEvent e)
  {

  }

  public void enterPdbId_actionPerformed(ActionEvent e)
  {

  }

  public void pdbFile_actionPerformed(ActionEvent e)
  {

  }

  public void invertColSel_actionPerformed(ActionEvent e)
  {

  }

  public void tabSelectionChanged(int sel)
  {

  }

  public void tabbedPane_mousePressed(MouseEvent e)
  {

  }

  public void tabbedPane_focusGained(FocusEvent e)
  {
    requestFocus();
  }

  public void save_actionPerformed(ActionEvent e)
  {

  }

  public void reload_actionPerformed(ActionEvent e)
  {

  }

  public void newView_actionPerformed(ActionEvent e)
  {

  }

  public void textColour_actionPerformed(ActionEvent e)
  {

  }

  public void idRightAlign_actionPerformed(ActionEvent e)
  {

  }

  public void expandViews_actionPerformed(ActionEvent e)
  {

  }

  public void gatherViews_actionPerformed(ActionEvent e)
  {

  }

  public void buildTreeMenu()
  {

  }

  public void pageSetup_actionPerformed(ActionEvent e)
  {

  }

  public void alignmentProperties()
  {

  }
}
