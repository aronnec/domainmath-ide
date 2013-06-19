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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jalview.bin.*;
import jalview.jbgui.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class FontChooser extends GFontChooser
{
  AlignmentPanel ap;

  TreePanel tp;

  Font oldFont;

  boolean init = true;

  JInternalFrame frame;

  /**
   * Creates a new FontChooser object.
   * 
   * @param ap
   *          DOCUMENT ME!
   */
  public FontChooser(TreePanel tp)
  {
    this.tp = tp;
    ap = tp.treeCanvas.ap;
    oldFont = tp.getTreeFont();
    defaultButton.setVisible(false);
    smoothFont.setEnabled(false);
    init();
  }

  /**
   * Creates a new FontChooser object.
   * 
   * @param ap
   *          DOCUMENT ME!
   */
  public FontChooser(AlignmentPanel ap)
  {
    oldFont = ap.av.getFont();
    this.ap = ap;
    init();
  }

  void init()
  {
    frame = new JInternalFrame();
    frame.setContentPane(this);

    smoothFont.setSelected(ap.av.antiAlias);

    if (tp != null)
    {
      Desktop.addInternalFrame(frame, "Change Font (Tree Panel)", 340, 170,
              false);
    }
    else
    {
      Desktop.addInternalFrame(frame, "Change Font", 340, 170, false);
    }

    frame.setLayer(JLayeredPane.PALETTE_LAYER);

    String[] fonts = java.awt.GraphicsEnvironment
            .getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    for (int i = 0; i < fonts.length; i++)
    {
      fontName.addItem(fonts[i]);
    }

    for (int i = 1; i < 51; i++)
    {
      fontSize.addItem(i + "");
    }

    fontStyle.addItem("plain");
    fontStyle.addItem("bold");
    fontStyle.addItem("italic");

    fontName.setSelectedItem(oldFont.getName());
    fontSize.setSelectedItem(oldFont.getSize() + "");
    fontStyle.setSelectedIndex(oldFont.getStyle());

    FontMetrics fm = getGraphics().getFontMetrics(oldFont);
    monospaced.setSelected(fm.getStringBounds("M", getGraphics())
            .getWidth() == fm.getStringBounds("|", getGraphics())
            .getWidth());

    init = false;
  }

  public void smoothFont_actionPerformed(ActionEvent e)
  {
    ap.av.antiAlias = smoothFont.isSelected();
    ap.annotationPanel.image = null;
    ap.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void ok_actionPerformed(ActionEvent e)
  {
    try
    {
      frame.setClosed(true);
    } catch (Exception ex)
    {
    }

    if (ap != null)
    {
      if (ap.getOverviewPanel() != null)
      {
        ap.getOverviewPanel().updateOverviewImage();
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void cancel_actionPerformed(ActionEvent e)
  {
    if (ap != null)
    {
      ap.av.setFont(oldFont);
      ap.paintAlignment(true);
    }
    else if (tp != null)
    {
      tp.setTreeFont(oldFont);
    }
    fontName.setSelectedItem(oldFont.getName());
    fontSize.setSelectedItem(oldFont.getSize() + "");
    fontStyle.setSelectedIndex(oldFont.getStyle());

    try
    {
      frame.setClosed(true);
    } catch (Exception ex)
    {
    }
  }

  private Font lastSelected = null;

  private int lastSelStyle = 0;

  private int lastSelSize = 0;

  private boolean lastSelMono = false;

  /**
   * DOCUMENT ME!
   */
  void changeFont()
  {
    if (lastSelected == null)
    {
      // initialise with original font
      lastSelected = oldFont;
      lastSelSize = oldFont.getSize();
      lastSelStyle = oldFont.getStyle();
      FontMetrics fm = getGraphics().getFontMetrics(oldFont);
      double mw = fm.getStringBounds("M", getGraphics()).getWidth(), iw = fm
              .getStringBounds("I", getGraphics()).getWidth();
      lastSelMono = mw == iw;
    }

    Font newFont = new Font(fontName.getSelectedItem().toString(),
            fontStyle.getSelectedIndex(), Integer.parseInt(fontSize
                    .getSelectedItem().toString()));
    FontMetrics fm = getGraphics().getFontMetrics(newFont);
    double mw = fm.getStringBounds("M", getGraphics()).getWidth(), iw = fm
            .getStringBounds("I", getGraphics()).getWidth();
    if (mw < 1 || iw < 1)
    {
      fontName.setSelectedItem(lastSelected.getName());
      fontStyle.setSelectedIndex(lastSelStyle);
      fontSize.setSelectedItem("" + lastSelSize);
      monospaced.setSelected(lastSelMono);
      JOptionPane
              .showInternalMessageDialog(
                      this,
                      "Font doesn't have letters defined\nso cannot be used\nwith alignment data.",
                      "Invalid Font", JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (tp != null)
    {
      tp.setTreeFont(newFont);
    }
    else if (ap != null)
    {
      ap.av.setFont(newFont);
      ap.fontChanged();
    }

    monospaced.setSelected(mw == iw);
    // remember last selected
    lastSelected = newFont;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void fontName_actionPerformed(ActionEvent e)
  {
    if (init)
    {
      return;
    }

    changeFont();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void fontSize_actionPerformed(ActionEvent e)
  {
    if (init)
    {
      return;
    }

    changeFont();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void fontStyle_actionPerformed(ActionEvent e)
  {
    if (init)
    {
      return;
    }

    changeFont();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void defaultButton_actionPerformed(ActionEvent e)
  {
    Cache.setProperty("FONT_NAME", fontName.getSelectedItem().toString());
    Cache.setProperty("FONT_STYLE", fontStyle.getSelectedIndex() + "");
    Cache.setProperty("FONT_SIZE", fontSize.getSelectedItem().toString());
    Cache.setProperty("ANTI_ALIAS",
            Boolean.toString(smoothFont.isSelected()));
  }
}
