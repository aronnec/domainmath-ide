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
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import jalview.datamodel.*;
import jalview.schemes.*;
import java.awt.Dimension;

public class FeatureColourChooser extends JalviewDialog
{
  // FeatureSettings fs;
  FeatureRenderer fr;

  private GraduatedColor cs;

  private Object oldcs;

  /**
   * 
   * @return the last colour setting selected by user - either oldcs (which may
   *         be a java.awt.Color) or the new GraduatedColor
   */
  public Object getLastColour()
  {
    if (cs == null)
    {
      return oldcs;
    }
    return cs;
  }

  Hashtable oldgroupColours;

  AlignmentPanel ap;

  boolean adjusting = false;

  private float min;

  private float max;

  String type = null;

  public FeatureColourChooser(FeatureRenderer frender, String type)
  {
    this(frender, false, type);
  }

  public FeatureColourChooser(FeatureRenderer frender, boolean block,
          String type)
  {
    this.fr = frender;
    this.type = type;
    ap = fr.ap;
    initDialogFrame(this, true, block, "Graduated Feature Colour for "
            + type, 480, 185);
    // frame.setLayer(JLayeredPane.PALETTE_LAYER);
    // Desktop.addInternalFrame(frame, "Graduated Feature Colour for "+type,
    // 480, 145);

    slider.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent evt)
      {
        if (!adjusting)
        {
          thresholdValue.setText(((float) slider.getValue() / 1000f) + "");
          valueChanged();
        }
      }
    });
    slider.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent evt)
      {
        if (ap != null)
        {
          ap.paintAlignment(true);
        }
        ;
      }
    });

    float mm[] = ((float[][]) fr.minmax.get(type))[0];
    min = mm[0];
    max = mm[1];
    oldcs = fr.featureColours.get(type);
    if (oldcs instanceof GraduatedColor)
    {
      if (((GraduatedColor) oldcs).isAutoScale())
      {
        // update the scale
        cs = new GraduatedColor((GraduatedColor) oldcs, min, max);
      }
      else
      {
        cs = new GraduatedColor((GraduatedColor) oldcs);
      }
    }
    else
    {
      // promote original color to a graduated color
      Color bl = Color.black;
      if (oldcs instanceof Color)
      {
        bl = (Color) oldcs;
      }
      // original colour becomes the maximum colour
      cs = new GraduatedColor(Color.white, bl, mm[0], mm[1]);
      cs.setColourByLabel(false);
    }
    minColour.setBackground(oldminColour = cs.getMinColor());
    maxColour.setBackground(oldmaxColour = cs.getMaxColor());
    adjusting = true;

    try
    {
      jbInit();
    } catch (Exception ex)
    {
    }
    // update the gui from threshold state
    thresholdIsMin.setSelected(!cs.isAutoScale());
    colourByLabel.setSelected(cs.isColourByLabel());
    if (cs.getThreshType() != AnnotationColourGradient.NO_THRESHOLD)
    {
      // initialise threshold slider and selector
      threshold
              .setSelectedIndex(cs.getThreshType() == AnnotationColourGradient.ABOVE_THRESHOLD ? 1
                      : 2);
      slider.setEnabled(true);
      thresholdValue.setEnabled(true);
      threshline = new jalview.datamodel.GraphLine((max - min) / 2f,
              "Threshold", Color.black);

    }

    adjusting = false;

    changeColour();
    waitForInput();
  }

  public FeatureColourChooser()
  {
    try
    {
      jbInit();
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {

    minColour.setFont(JvSwingUtils.getLabelFont());
    minColour.setBorder(BorderFactory.createLineBorder(Color.black));
    minColour.setPreferredSize(new Dimension(40, 20));
    minColour.setToolTipText("Minimum Colour");
    minColour.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        if (minColour.isEnabled())
        {
          minColour_actionPerformed();
        }
      }
    });
    maxColour.setFont(JvSwingUtils.getLabelFont());
    maxColour.setBorder(BorderFactory.createLineBorder(Color.black));
    maxColour.setPreferredSize(new Dimension(40, 20));
    maxColour.setToolTipText("Maximum Colour");
    maxColour.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        if (maxColour.isEnabled())
        {
          maxColour_actionPerformed();
        }
      }
    });
    maxColour.setBorder(new LineBorder(Color.black));
    minText.setText("Min:");
    minText.setFont(JvSwingUtils.getLabelFont());
    maxText.setText("Max:");
    maxText.setFont(JvSwingUtils.getLabelFont());
    this.setLayout(borderLayout1);
    jPanel2.setLayout(flowLayout1);
    jPanel1.setBackground(Color.white);
    jPanel2.setBackground(Color.white);
    threshold.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        threshold_actionPerformed(e);
      }
    });
    threshold.setToolTipText("Threshold the feature display by score.");
    threshold.addItem("No Threshold"); // index 0
    threshold.addItem("Above Threshold"); // index 1
    threshold.addItem("Below Threshold"); // index 2
    jPanel3.setLayout(flowLayout2);
    thresholdValue.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        thresholdValue_actionPerformed(e);
      }
    });
    slider.setPaintLabels(false);
    slider.setPaintTicks(true);
    slider.setBackground(Color.white);
    slider.setEnabled(false);
    slider.setOpaque(false);
    slider.setPreferredSize(new Dimension(100, 32));
    slider.setToolTipText("Adjust threshold");
    thresholdValue.setEnabled(false);
    thresholdValue.setColumns(7);
    jPanel3.setBackground(Color.white);
    thresholdIsMin.setBackground(Color.white);
    thresholdIsMin.setText("Threshold is Min/Max");
    thresholdIsMin
            .setToolTipText("Toggle between absolute and relative display threshold.");
    thresholdIsMin.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        thresholdIsMin_actionPerformed(actionEvent);
      }
    });
    colourByLabel.setBackground(Color.white);
    colourByLabel.setText("Colour by Label");
    colourByLabel
            .setToolTipText("Display features of the same type with a different label using a different colour. (e.g. domain features)");
    colourByLabel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        colourByLabel_actionPerformed(actionEvent);
      }
    });
    colourPanel.setBackground(Color.white);
    jPanel1.add(ok);
    jPanel1.add(cancel);
    jPanel2.add(colourByLabel, java.awt.BorderLayout.WEST);
    jPanel2.add(colourPanel, java.awt.BorderLayout.EAST);
    colourPanel.add(minText);
    colourPanel.add(minColour);
    colourPanel.add(maxText);
    colourPanel.add(maxColour);
    this.add(jPanel3, java.awt.BorderLayout.CENTER);
    jPanel3.add(threshold);
    jPanel3.add(slider);
    jPanel3.add(thresholdValue);
    jPanel3.add(thresholdIsMin);
    this.add(jPanel1, java.awt.BorderLayout.SOUTH);
    this.add(jPanel2, java.awt.BorderLayout.NORTH);
  }

  JLabel minText = new JLabel();

  JLabel maxText = new JLabel();

  JPanel minColour = new JPanel();

  JPanel maxColour = new JPanel();

  JPanel colourPanel = new JPanel();

  JPanel jPanel1 = new JPanel();

  JPanel jPanel2 = new JPanel();

  BorderLayout borderLayout1 = new BorderLayout();

  JComboBox threshold = new JComboBox();

  FlowLayout flowLayout1 = new FlowLayout();

  JPanel jPanel3 = new JPanel();

  FlowLayout flowLayout2 = new FlowLayout();

  JSlider slider = new JSlider();

  JTextField thresholdValue = new JTextField(20);

  // TODO implement GUI for tolower flag
  // JCheckBox toLower = new JCheckBox();

  JCheckBox thresholdIsMin = new JCheckBox();

  JCheckBox colourByLabel = new JCheckBox();

  private GraphLine threshline;

  private Color oldmaxColour;

  private Color oldminColour;

  public void minColour_actionPerformed()
  {
    Color col = JColorChooser.showDialog(this,
            "Select Colour for Minimum Value", minColour.getBackground());
    if (col != null)
    {
      minColour.setBackground(col);
      minColour.setForeground(col);
    }
    minColour.repaint();
    changeColour();
  }

  public void maxColour_actionPerformed()
  {
    Color col = JColorChooser.showDialog(this,
            "Select Colour for Maximum Value", maxColour.getBackground());
    if (col != null)
    {
      maxColour.setBackground(col);
      maxColour.setForeground(col);
    }
    maxColour.repaint();
    changeColour();
  }

  void changeColour()
  {
    // Check if combobox is still adjusting
    if (adjusting)
    {
      return;
    }

    int aboveThreshold = AnnotationColourGradient.NO_THRESHOLD;
    if (threshold.getSelectedItem().equals("Above Threshold"))
    {
      aboveThreshold = AnnotationColourGradient.ABOVE_THRESHOLD;
    }
    else if (threshold.getSelectedItem().equals("Below Threshold"))
    {
      aboveThreshold = AnnotationColourGradient.BELOW_THRESHOLD;
    }

    slider.setEnabled(true);
    thresholdValue.setEnabled(true);

    GraduatedColor acg;
    if (cs.isColourByLabel())
    {
      acg = new GraduatedColor(oldminColour, oldmaxColour, min, max);
    }
    else
    {
      acg = new GraduatedColor(oldminColour = minColour.getBackground(),
              oldmaxColour = maxColour.getBackground(), min, max);

    }

    if (aboveThreshold == AnnotationColourGradient.NO_THRESHOLD)
    {
      slider.setEnabled(false);
      thresholdValue.setEnabled(false);
      thresholdValue.setText("");
      thresholdIsMin.setEnabled(false);
    }
    else if (aboveThreshold != AnnotationColourGradient.NO_THRESHOLD
            && threshline == null)
    {
      // todo visual indication of feature threshold
      threshline = new jalview.datamodel.GraphLine((max - min) / 2f,
              "Threshold", Color.black);
    }

    if (aboveThreshold != AnnotationColourGradient.NO_THRESHOLD)
    {
      adjusting = true;
      acg.setThresh(threshline.value);

      float range = max * 1000f - min * 1000f;

      slider.setMinimum((int) (min * 1000));
      slider.setMaximum((int) (max * 1000));
      slider.setValue((int) (threshline.value * 1000));
      thresholdValue.setText(threshline.value + "");
      slider.setMajorTickSpacing((int) (range / 10f));
      slider.setEnabled(true);
      thresholdValue.setEnabled(true);
      thresholdIsMin.setEnabled(!colourByLabel.isSelected());
      adjusting = false;
    }

    acg.setThreshType(aboveThreshold);
    if (thresholdIsMin.isSelected()
            && aboveThreshold != AnnotationColourGradient.NO_THRESHOLD)
    {
      acg.setAutoScaled(false);
      if (aboveThreshold == AnnotationColourGradient.ABOVE_THRESHOLD)
      {
        acg = new GraduatedColor(acg, threshline.value, max);
      }
      else
      {
        acg = new GraduatedColor(acg, min, threshline.value);
      }
    }
    else
    {
      acg.setAutoScaled(true);
    }
    acg.setColourByLabel(colourByLabel.isSelected());
    if (acg.isColourByLabel())
    {
      maxColour.setEnabled(false);
      minColour.setEnabled(false);
      maxColour.setBackground(this.getBackground());
      maxColour.setForeground(this.getBackground());
      minColour.setBackground(this.getBackground());
      minColour.setForeground(this.getBackground());

    }
    else
    {
      maxColour.setEnabled(true);
      minColour.setEnabled(true);
      maxColour.setBackground(oldmaxColour);
      minColour.setBackground(oldminColour);
      maxColour.setForeground(oldmaxColour);
      minColour.setForeground(oldminColour);
    }
    fr.featureColours.put(type, acg);
    cs = acg;
    ap.paintAlignment(false);
  }

  protected void raiseClosed()
  {
    if (this.colourEditor != null)
    {
      colourEditor.actionPerformed(new ActionEvent(this, 0, "CLOSED"));
    }
  }

  public void okPressed()
  {
    changeColour();
  }

  public void cancelPressed()
  {
    reset();
  }

  void reset()
  {
    fr.featureColours.put(type, oldcs);
    ap.paintAlignment(false);
    cs = null;
  }

  public void thresholdCheck_actionPerformed(ActionEvent e)
  {
    changeColour();
  }

  public void annotations_actionPerformed(ActionEvent e)
  {
    changeColour();
  }

  public void threshold_actionPerformed(ActionEvent e)
  {
    changeColour();
  }

  public void thresholdValue_actionPerformed(ActionEvent e)
  {
    try
    {
      float f = Float.parseFloat(thresholdValue.getText());
      slider.setValue((int) (f * 1000));
      threshline.value = f;
    } catch (NumberFormatException ex)
    {
    }
  }

  public void valueChanged()
  {
    threshline.value = (float) slider.getValue() / 1000f;
    cs.setThresh(threshline.value);
    changeColour();
    ap.paintAlignment(false);
  }

  public void thresholdIsMin_actionPerformed(ActionEvent actionEvent)
  {
    changeColour();
  }

  public void colourByLabel_actionPerformed(ActionEvent actionEvent)
  {
    changeColour();
  }

  ActionListener colourEditor = null;

  public void addActionListener(ActionListener graduatedColorEditor)
  {
    if (colourEditor != null)
    {
      System.err
              .println("IMPLEMENTATION ISSUE: overwriting action listener for FeatureColourChooser");
    }
    colourEditor = graduatedColorEditor;
  }

}
