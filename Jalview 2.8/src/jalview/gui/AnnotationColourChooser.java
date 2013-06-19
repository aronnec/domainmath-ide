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
import javax.swing.event.*;

import net.miginfocom.swing.MigLayout;

import jalview.bin.Cache;
import jalview.datamodel.*;
import jalview.schemes.*;
import java.awt.Dimension;

public class AnnotationColourChooser extends JPanel
{
  JInternalFrame frame;

  AlignViewport av;

  AlignmentPanel ap;

  ColourSchemeI oldcs;

  Hashtable oldgroupColours;

  jalview.datamodel.AlignmentAnnotation currentAnnotation;

  boolean adjusting = false;

  public AnnotationColourChooser(AlignViewport av, final AlignmentPanel ap)
  {
    oldcs = av.getGlobalColourScheme();
    if (av.getAlignment().getGroups() != null)
    {
      oldgroupColours = new Hashtable();
      for (SequenceGroup sg : ap.av.getAlignment().getGroups())
      {
        if (sg.cs != null)
        {
          oldgroupColours.put(sg, sg.cs);
        }
      }
    }
    this.av = av;
    this.ap = ap;
    frame = new JInternalFrame();
    frame.setContentPane(this);
    frame.setLayer(JLayeredPane.PALETTE_LAYER);
    Desktop.addInternalFrame(frame, "Colour by Annotation", 520, 215);

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
        ap.paintAlignment(true);
      }
    });

    if (av.getAlignment().getAlignmentAnnotation() == null)
    {
      return;
    }

    // Always get default shading from preferences.
    setDefaultMinMax();

    if (oldcs instanceof AnnotationColourGradient)
    {
      AnnotationColourGradient acg = (AnnotationColourGradient) oldcs;
      currentColours.setSelected(acg.predefinedColours);
      if (!acg.predefinedColours)
      {
        minColour.setBackground(acg.getMinColour());
        maxColour.setBackground(acg.getMaxColour());
      }
      seqAssociated.setSelected(acg.isSeqAssociated());
    }
    adjusting = true;
    annotations = new JComboBox(
            getAnnotationItems(seqAssociated.isSelected()));

    threshold.addItem("No Threshold");
    threshold.addItem("Above Threshold");
    threshold.addItem("Below Threshold");

    if (oldcs instanceof AnnotationColourGradient)
    {
      AnnotationColourGradient acg = (AnnotationColourGradient) oldcs;
      annotations.setSelectedItem(acg.getAnnotation());
      switch (acg.getAboveThreshold())
      {
      case AnnotationColourGradient.NO_THRESHOLD:
        threshold.setSelectedItem("No Threshold");
        break;
      case AnnotationColourGradient.ABOVE_THRESHOLD:
        threshold.setSelectedItem("Above Threshold");
        break;
      case AnnotationColourGradient.BELOW_THRESHOLD:
        threshold.setSelectedItem("Below Threshold");
        break;
      default:
        throw new Error(
                "Implementation error: don't know about threshold setting for current AnnotationColourGradient.");
      }
      thresholdIsMin.setSelected(acg.thresholdIsMinMax);
      thresholdValue.setText("" + acg.getAnnotationThreshold());
    }

    try
    {
      jbInit();
    } catch (Exception ex)
    {
    }

    adjusting = false;

    changeColour();
    frame.invalidate();
    frame.pack();

  }

  private Vector<String> getAnnotationItems(boolean isSeqAssociated)
  {
    Vector<String> list = new Vector<String>();
    int index = 1;
    int[] anmap = new int[av.getAlignment().getAlignmentAnnotation().length];
    boolean enableSeqAss = false;
    for (int i = 0; i < av.getAlignment().getAlignmentAnnotation().length; i++)
    {
      if (av.getAlignment().getAlignmentAnnotation()[i].sequenceRef == null)
      {
        if (isSeqAssociated)
        {
          continue;
        }
      }
      else
      {
        enableSeqAss = true;
      }
      String label = av.getAlignment().getAlignmentAnnotation()[i].label;
      if (!list.contains(label))
      {
        anmap[list.size()] = i;
        list.addElement(label);

      }
      else
      {
        if (!isSeqAssociated)
        {
          anmap[list.size()] = i;
          list.addElement(label + "_" + (index++));
        }
      }
    }
    seqAssociated.setEnabled(enableSeqAss);
    annmap = new int[list.size()];
    System.arraycopy(anmap, 0, annmap, 0, annmap.length);
    return list;
  }

  private void setDefaultMinMax()
  {
    minColour.setBackground(Cache.getDefaultColour("ANNOTATIONCOLOUR_MIN",
            Color.orange));
    maxColour.setBackground(Cache.getDefaultColour("ANNOTATIONCOLOUR_MAX",
            Color.red));
  }

  public AnnotationColourChooser()
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
    minColour.setBorder(BorderFactory.createEtchedBorder());
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
    maxColour.setBorder(BorderFactory.createEtchedBorder());
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
    ok.setOpaque(false);
    ok.setText("OK");
    ok.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ok_actionPerformed(e);
      }
    });
    cancel.setOpaque(false);
    cancel.setText("Cancel");
    cancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cancel_actionPerformed(e);
      }
    });
    defColours.setOpaque(false);
    defColours.setText("Defaults");
    defColours
            .setToolTipText("Reset min and max colours to defaults from user preferences.");
    defColours.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        resetColours_actionPerformed(arg0);
      }
    });

    annotations.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        annotations_actionPerformed(e);
      }
    });
    threshold.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        threshold_actionPerformed(e);
      }
    });
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
    thresholdValue.setEnabled(false);
    thresholdValue.setColumns(7);
    currentColours.setFont(JvSwingUtils.getLabelFont());
    currentColours.setOpaque(false);
    currentColours.setText("Use Original Colours");
    currentColours.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        currentColours_actionPerformed(e);
      }
    });
    thresholdIsMin.setBackground(Color.white);
    thresholdIsMin.setFont(JvSwingUtils.getLabelFont());
    thresholdIsMin.setText("Threshold is Min/Max");
    thresholdIsMin.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        thresholdIsMin_actionPerformed(actionEvent);
      }
    });
    seqAssociated.setBackground(Color.white);
    seqAssociated.setFont(JvSwingUtils.getLabelFont());
    seqAssociated.setText("Per-sequence only");
    seqAssociated.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        seqAssociated_actionPerformed(arg0);
      }
    });

    this.setLayout(borderLayout1);
    jPanel2.setLayout(new MigLayout("", "[left][center][right]", "[][][]"));
    jPanel1.setBackground(Color.white);
    jPanel2.setBackground(Color.white);

    jPanel1.add(ok);
    jPanel1.add(cancel);
    jPanel2.add(annotations, "grow, wrap");
    jPanel2.add(seqAssociated);
    jPanel2.add(currentColours);
    JPanel colpanel = new JPanel(new FlowLayout());
    colpanel.setBackground(Color.white);
    colpanel.add(minColour);
    colpanel.add(maxColour);
    jPanel2.add(colpanel, "wrap");
    jPanel2.add(threshold);
    jPanel2.add(defColours, "skip 1, wrap");
    jPanel2.add(thresholdIsMin);
    jPanel2.add(slider, "grow");
    jPanel2.add(thresholdValue, "grow");
    this.add(jPanel1, java.awt.BorderLayout.SOUTH);
    this.add(jPanel2, java.awt.BorderLayout.CENTER);
    this.validate();
  }

  protected void seqAssociated_actionPerformed(ActionEvent arg0)
  {
    adjusting = true;
    String cursel = (String) annotations.getSelectedItem();
    boolean isvalid = false, isseqs = seqAssociated.isSelected();
    this.annotations.removeAllItems();
    for (String anitem : getAnnotationItems(seqAssociated.isSelected()))
    {
      if (anitem.equals(cursel) || (isseqs && cursel.startsWith(anitem)))
      {
        isvalid = true;
        cursel = anitem;
      }
      this.annotations.addItem(anitem);
    }
    adjusting = false;
    if (isvalid)
    {
      this.annotations.setSelectedItem(cursel);
    }
    else
    {
      if (annotations.getItemCount() > 0)
      {
        annotations.setSelectedIndex(0);
      }
    }
  }

  protected void resetColours_actionPerformed(ActionEvent arg0)
  {
    setDefaultMinMax();
    changeColour();
  }

  JComboBox annotations;

  int[] annmap;

  JPanel minColour = new JPanel();

  JPanel maxColour = new JPanel();

  JButton defColours = new JButton();

  JButton ok = new JButton();

  JButton cancel = new JButton();

  JPanel jPanel1 = new JPanel();

  JPanel jPanel2 = new JPanel();

  BorderLayout borderLayout1 = new BorderLayout();

  JComboBox threshold = new JComboBox();

  JSlider slider = new JSlider();

  JTextField thresholdValue = new JTextField(20);

  JCheckBox currentColours = new JCheckBox();

  JCheckBox thresholdIsMin = new JCheckBox();

  JCheckBox seqAssociated = new JCheckBox();

  public void minColour_actionPerformed()
  {
    Color col = JColorChooser.showDialog(this,
            "Select Colour for Minimum Value", minColour.getBackground());
    if (col != null)
    {
      minColour.setBackground(col);
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

    currentAnnotation = av.getAlignment().getAlignmentAnnotation()[annmap[annotations
            .getSelectedIndex()]];

    int aboveThreshold = -1;
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
    thresholdIsMin.setEnabled(true);

    if (aboveThreshold == AnnotationColourGradient.NO_THRESHOLD)
    {
      slider.setEnabled(false);
      thresholdValue.setEnabled(false);
      thresholdValue.setText("");
      thresholdIsMin.setEnabled(false);
    }
    else if (aboveThreshold != AnnotationColourGradient.NO_THRESHOLD
            && currentAnnotation.threshold == null)
    {
      currentAnnotation
              .setThreshold(new jalview.datamodel.GraphLine(
                      (currentAnnotation.graphMax - currentAnnotation.graphMin) / 2f,
                      "Threshold", Color.black));
    }

    if (aboveThreshold != AnnotationColourGradient.NO_THRESHOLD)
    {
      adjusting = true;
      float range = currentAnnotation.graphMax * 1000
              - currentAnnotation.graphMin * 1000;

      slider.setMinimum((int) (currentAnnotation.graphMin * 1000));
      slider.setMaximum((int) (currentAnnotation.graphMax * 1000));
      slider.setValue((int) (currentAnnotation.threshold.value * 1000));
      thresholdValue.setText(currentAnnotation.threshold.value + "");
      slider.setMajorTickSpacing((int) (range / 10f));
      slider.setEnabled(true);
      thresholdValue.setEnabled(true);
      adjusting = false;
    }

    AnnotationColourGradient acg = null;
    if (currentColours.isSelected())
    {
      acg = new AnnotationColourGradient(currentAnnotation,
              av.getGlobalColourScheme(), aboveThreshold);
    }
    else
    {
      acg = new AnnotationColourGradient(currentAnnotation,
              minColour.getBackground(), maxColour.getBackground(),
              aboveThreshold);
    }
    acg.setSeqAssociated(seqAssociated.isSelected());

    if (currentAnnotation.graphMin == 0f
            && currentAnnotation.graphMax == 0f)
    {
      acg.predefinedColours = true;
    }

    acg.thresholdIsMinMax = thresholdIsMin.isSelected();

    av.setGlobalColourScheme(acg);

    if (av.getAlignment().getGroups() != null)
    {

      for (SequenceGroup sg : ap.av.getAlignment().getGroups())
      {
        if (sg.cs == null)
        {
          continue;
        }

        if (currentColours.isSelected())
        {
          sg.cs = new AnnotationColourGradient(currentAnnotation, sg.cs,
                  aboveThreshold);
          ((AnnotationColourGradient) sg.cs).setSeqAssociated(seqAssociated
                  .isSelected());

        }
        else
        {
          sg.cs = new AnnotationColourGradient(currentAnnotation,
                  minColour.getBackground(), maxColour.getBackground(),
                  aboveThreshold);
          ((AnnotationColourGradient) sg.cs).setSeqAssociated(seqAssociated
                  .isSelected());
        }

      }
    }
    ap.alignmentChanged();
    // ensure all associated views (overviews, structures, etc) are notified of
    // updated colours.
    ap.paintAlignment(true);
  }

  public void ok_actionPerformed(ActionEvent e)
  {
    changeColour();
    try
    {
      frame.setClosed(true);
    } catch (Exception ex)
    {
    }
  }

  public void cancel_actionPerformed(ActionEvent e)
  {
    reset();
    // ensure all original colouring is propagated to listeners.
    ap.paintAlignment(true);
    try
    {
      frame.setClosed(true);
    } catch (Exception ex)
    {
    }
  }

  void reset()
  {
    av.setGlobalColourScheme(oldcs);
    if (av.getAlignment().getGroups() != null)
    {

      for (SequenceGroup sg : ap.av.getAlignment().getGroups())
      {
        sg.cs = (ColourSchemeI) oldgroupColours.get(sg);
      }
    }
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
    } catch (NumberFormatException ex)
    {
    }
  }

  public void valueChanged()
  {
    if (currentColours.isSelected()
            && !(av.getGlobalColourScheme() instanceof AnnotationColourGradient))
    {
      changeColour();
    }

    currentAnnotation.threshold.value = (float) slider.getValue() / 1000f;
    ap.paintAlignment(false);
  }

  public void currentColours_actionPerformed(ActionEvent e)
  {
    if (currentColours.isSelected())
    {
      reset();
    }

    maxColour.setEnabled(!currentColours.isSelected());
    minColour.setEnabled(!currentColours.isSelected());

    changeColour();
  }

  public void thresholdIsMin_actionPerformed(ActionEvent actionEvent)
  {
    changeColour();
  }

}
