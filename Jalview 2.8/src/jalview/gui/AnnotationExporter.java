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
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jalview.datamodel.*;
import jalview.io.*;

/**
 * 
 * GUI dialog for exporting features or alignment annotations depending upon
 * which method is called.
 * 
 * @author AMW
 * 
 */
public class AnnotationExporter extends JPanel
{
  JInternalFrame frame;

  AlignmentPanel ap;

  boolean features = true;

  AlignmentAnnotation[] annotations;

  List<SequenceGroup> sequenceGroups;

  Hashtable alignmentProperties;

  public AnnotationExporter()
  {
    try
    {
      jbInit();
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }

    frame = new JInternalFrame();
    frame.setContentPane(this);
    frame.setLayer(JLayeredPane.PALETTE_LAYER);
    Desktop.addInternalFrame(frame, "", frame.getPreferredSize().width,
            frame.getPreferredSize().height);
  }

  public void exportFeatures(AlignmentPanel ap)
  {
    this.ap = ap;
    features = true;
    CSVFormat.setVisible(false);
    frame.setTitle("Export Features");
  }

  public void exportAnnotations(AlignmentPanel ap,
          AlignmentAnnotation[] annotations, List<SequenceGroup> list,
          Hashtable alProperties)
  {
    this.ap = ap;
    features = false;
    GFFFormat.setVisible(false);
    CSVFormat.setVisible(true);
    this.annotations = annotations;
    this.sequenceGroups = list;
    this.alignmentProperties = alProperties;
    frame.setTitle("Export Annotations");
  }

  public void toFile_actionPerformed(ActionEvent e)
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"));

    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle(features ? "Save Features to File"
            : "Save Annotation to File");
    chooser.setToolTipText("Save");

    int value = chooser.showSaveDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      String text = "No features found on alignment";
      if (features)
      {
        if (GFFFormat.isSelected())
        {
          text = new FeaturesFile().printGFFFormat(ap.av.getAlignment()
                  .getDataset().getSequencesArray(),
                  getDisplayedFeatureCols(), true, ap.av.isShowNpFeats());// ap.av.featuresDisplayed//);
        }
        else
        {
          text = new FeaturesFile().printJalviewFormat(ap.av.getAlignment()
                  .getDataset().getSequencesArray(),
                  getDisplayedFeatureCols(), true, ap.av.isShowNpFeats()); // ap.av.featuresDisplayed);
        }
      }
      else
      {
        if (CSVFormat.isSelected())
        {
          text = new AnnotationFile().printCSVAnnotations(annotations);
        }
        else
        {
          text = new AnnotationFile().printAnnotations(annotations,
                  sequenceGroups, alignmentProperties);
        }
      }

      try
      {
        java.io.PrintWriter out = new java.io.PrintWriter(
                new java.io.FileWriter(chooser.getSelectedFile()));

        out.print(text);
        out.close();
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }

    close_actionPerformed(null);
  }

  public void toTextbox_actionPerformed(ActionEvent e)
  {
    String text = "No features found on alignment";
    if (features)
    {
      if (GFFFormat.isSelected())
      {
        text = new FeaturesFile().printGFFFormat(ap.av.getAlignment()
                .getDataset().getSequencesArray(),
                getDisplayedFeatureCols(), true, ap.av.isShowNpFeats());
      }
      else
      {
        text = new FeaturesFile().printJalviewFormat(ap.av.getAlignment()
                .getDataset().getSequencesArray(),
                getDisplayedFeatureCols(), true, ap.av.isShowNpFeats());
      }
    }
    else if (!features)
    {
      if (CSVFormat.isSelected())
      {
        text = new AnnotationFile().printCSVAnnotations(annotations);
      }
      else
      {
        text = new AnnotationFile().printAnnotations(annotations,
                sequenceGroups, alignmentProperties);
      }
    }

    CutAndPasteTransfer cap = new CutAndPasteTransfer();
    try
    {
      cap.setText(text);
      Desktop.addInternalFrame(cap, (features ? "Features for - "
              : "Annotations for - ") + ap.alignFrame.getTitle(), 600, 500);
    } catch (OutOfMemoryError oom)
    {
      new OOMWarning("generating "
              + (features ? "Features for - " : "Annotations for - ")
              + ap.alignFrame.getTitle(), oom);
      cap.dispose();
    }

    close_actionPerformed(null);
  }

  private Hashtable getDisplayedFeatureCols()
  {
    Hashtable fcols = new Hashtable();
    if (ap.av.featuresDisplayed == null)
    {
      return fcols;
    }
    Enumeration en = ap.av.featuresDisplayed.keys();
    FeatureRenderer fr = ap.seqPanel.seqCanvas.getFeatureRenderer(); // consider
                                                                     // higher
                                                                     // level
                                                                     // method ?
    while (en.hasMoreElements())
    {
      Object col = en.nextElement();
      fcols.put(col, fr.featureColours.get(col));
    }
    return fcols;
  }

  public void close_actionPerformed(ActionEvent e)
  {
    try
    {
      frame.setClosed(true);
    } catch (java.beans.PropertyVetoException ex)
    {
    }
  }

  private void jbInit() throws Exception
  {
    this.setLayout(new BorderLayout());

    toFile.setText("to File");
    toFile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        toFile_actionPerformed(e);
      }
    });
    toTextbox.setText("to Textbox");
    toTextbox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        toTextbox_actionPerformed(e);
      }
    });
    close.setText("Close");
    close.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        close_actionPerformed(e);
      }
    });
    jalviewFormat.setOpaque(false);
    jalviewFormat.setSelected(true);
    jalviewFormat.setText("Jalview");
    GFFFormat.setOpaque(false);
    GFFFormat.setText("GFF");
    CSVFormat.setOpaque(false);
    CSVFormat.setText("CSV(Spreadsheet)");
    jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
    jLabel1.setText("Format: ");
    this.setBackground(Color.white);
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    jPanel3.setOpaque(false);
    jPanel1.setOpaque(false);
    jPanel1.add(toFile);
    jPanel1.add(toTextbox);
    jPanel1.add(close);
    jPanel3.add(jLabel1);
    jPanel3.add(jalviewFormat);
    jPanel3.add(GFFFormat);
    jPanel3.add(CSVFormat);
    buttonGroup.add(jalviewFormat);
    buttonGroup.add(GFFFormat);
    buttonGroup.add(CSVFormat);
    this.add(jPanel3, BorderLayout.CENTER);
    this.add(jPanel1, BorderLayout.SOUTH);
  }

  JPanel jPanel1 = new JPanel();

  JButton toFile = new JButton();

  JButton toTextbox = new JButton();

  JButton close = new JButton();

  ButtonGroup buttonGroup = new ButtonGroup();

  JRadioButton jalviewFormat = new JRadioButton();

  JRadioButton GFFFormat = new JRadioButton();

  JRadioButton CSVFormat = new JRadioButton();

  JLabel jLabel1 = new JLabel();

  JPanel jPanel3 = new JPanel();

  FlowLayout flowLayout1 = new FlowLayout();

}
