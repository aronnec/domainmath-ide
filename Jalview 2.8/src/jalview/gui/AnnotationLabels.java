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
import java.util.regex.Pattern;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.swing.*;

import jalview.datamodel.*;
import jalview.io.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class AnnotationLabels extends JPanel implements MouseListener,
        MouseMotionListener, ActionListener
{
  static String TOGGLE_LABELSCALE = "Scale Label to Column";

  static String ADDNEW = "Add New Row";

  static String EDITNAME = "Edit Label/Description";

  static String HIDE = "Hide This Row";

  static String DELETE = "Delete This Row";

  static String SHOWALL = "Show All Hidden Rows";

  static String OUTPUT_TEXT = "Export Annotation";

  static String COPYCONS_SEQ = "Copy Consensus Sequence";

  boolean resizePanel = false;

  Image image;

  AlignmentPanel ap;

  AlignViewport av;

  boolean resizing = false;

  MouseEvent dragEvent;

  int oldY;

  int selectedRow;

  int scrollOffset = 0;

  Font font = new Font("Arial", Font.PLAIN, 11);

  private boolean hasHiddenRows;

  /**
   * Creates a new AnnotationLabels object.
   * 
   * @param ap
   *          DOCUMENT ME!
   */
  public AnnotationLabels(AlignmentPanel ap)
  {
    this.ap = ap;
    av = ap.av;
    ToolTipManager.sharedInstance().registerComponent(this);

    java.net.URL url = getClass().getResource("/images/idwidth.gif");
    Image temp = null;

    if (url != null)
    {
      temp = java.awt.Toolkit.getDefaultToolkit().createImage(url);
    }

    try
    {
      MediaTracker mt = new MediaTracker(this);
      mt.addImage(temp, 0);
      mt.waitForID(0);
    } catch (Exception ex)
    {
    }

    BufferedImage bi = new BufferedImage(temp.getHeight(this),
            temp.getWidth(this), BufferedImage.TYPE_INT_RGB);
    Graphics2D g = (Graphics2D) bi.getGraphics();
    g.rotate(Math.toRadians(90));
    g.drawImage(temp, 0, -bi.getWidth(this), this);
    image = (Image) bi;

    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(ap.annotationPanel);
  }

  public AnnotationLabels(AlignViewport av)
  {
    this.av = av;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param y
   *          DOCUMENT ME!
   */
  public void setScrollOffset(int y)
  {
    scrollOffset = y;
    repaint();
  }

  /**
   * sets selectedRow to -2 if no annotation preset, -1 if no visible row is at
   * y
   * 
   * @param y
   *          coordinate position to search for a row
   */
  void getSelectedRow(int y)
  {
    int height = 0;
    AlignmentAnnotation[] aa = ap.av.getAlignment()
            .getAlignmentAnnotation();
    selectedRow = -2;
    if (aa != null)
    {
      for (int i = 0; i < aa.length; i++)
      {
        selectedRow = -1;
        if (!aa[i].visible)
        {
          continue;
        }

        height += aa[i].height;

        if (y < height)
        {
          selectedRow = i;

          break;
        }
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void actionPerformed(ActionEvent evt)
  {
    AlignmentAnnotation[] aa = ap.av.getAlignment()
            .getAlignmentAnnotation();

    if (evt.getActionCommand().equals(ADDNEW))
    {
      AlignmentAnnotation newAnnotation = new AlignmentAnnotation(null,
              null, new Annotation[ap.av.getAlignment().getWidth()]);

      if (!editLabelDescription(newAnnotation))
      {
        return;
      }

      ap.av.getAlignment().addAnnotation(newAnnotation);
      ap.av.getAlignment().setAnnotationIndex(newAnnotation, 0);
    }
    else if (evt.getActionCommand().equals(EDITNAME))
    {
      editLabelDescription(aa[selectedRow]);
      repaint();
    }
    else if (evt.getActionCommand().equals(HIDE))
    {
      aa[selectedRow].visible = false;
    }
    else if (evt.getActionCommand().equals(DELETE))
    {
      ap.av.getAlignment().deleteAnnotation(aa[selectedRow]);
    }
    else if (evt.getActionCommand().equals(SHOWALL))
    {
      for (int i = 0; i < aa.length; i++)
      {
        if (!aa[i].visible && aa[i].annotations != null)
        {
          aa[i].visible = true;
        }
      }
    }
    else if (evt.getActionCommand().equals(OUTPUT_TEXT))
    {
      new AnnotationExporter().exportAnnotations(ap,
              new AlignmentAnnotation[]
              { aa[selectedRow] }, null, null);
    }
    else if (evt.getActionCommand().equals(COPYCONS_SEQ))
    {
      SequenceI cons = null;
      if (aa[selectedRow].groupRef != null)
      {
        cons = aa[selectedRow].groupRef.getConsensusSeq();
      }
      else
      {
        cons = av.getConsensusSeq();
      }
      if (cons != null)
      {
        copy_annotseqtoclipboard(cons);
      }

    }
    else if (evt.getActionCommand().equals(TOGGLE_LABELSCALE))
    {
      aa[selectedRow].scaleColLabel = !aa[selectedRow].scaleColLabel;
    }

    ap.validateAnnotationDimensions(false);
    ap.addNotify();
    ap.repaint();
    // validate();
    // ap.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  boolean editLabelDescription(AlignmentAnnotation annotation)
  {
    EditNameDialog dialog = new EditNameDialog(annotation.label,
            annotation.description, "       Annotation Name ",
            "Annotation Description ", "Edit Annotation Name/Description",
            ap.alignFrame);

    if (!dialog.accept)
    {
      return false;
    }

    annotation.label = dialog.getName();

    String text = dialog.getDescription();
    if (text != null && text.length() == 0)
    {
      text = null;
    }
    annotation.description = text;

    return true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void mousePressed(MouseEvent evt)
  {
    getSelectedRow(evt.getY() - scrollOffset);
    oldY = evt.getY();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void mouseReleased(MouseEvent evt)
  {
    int start = selectedRow;
    getSelectedRow(evt.getY() - scrollOffset);
    int end = selectedRow;

    if (start != end)
    {
      // Swap these annotations
      AlignmentAnnotation startAA = ap.av.getAlignment()
              .getAlignmentAnnotation()[start];
      if (end == -1)
      {
        end = ap.av.getAlignment().getAlignmentAnnotation().length - 1;
      }
      AlignmentAnnotation endAA = ap.av.getAlignment()
              .getAlignmentAnnotation()[end];

      ap.av.getAlignment().getAlignmentAnnotation()[end] = startAA;
      ap.av.getAlignment().getAlignmentAnnotation()[start] = endAA;
    }

    resizePanel = false;
    dragEvent = null;
    repaint();
    ap.annotationPanel.repaint();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void mouseEntered(MouseEvent evt)
  {
    if (evt.getY() < 10)
    {
      resizePanel = true;
      repaint();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void mouseExited(MouseEvent evt)
  {
    if (dragEvent == null)
    {
      resizePanel = false;
      repaint();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void mouseDragged(MouseEvent evt)
  {
    dragEvent = evt;

    if (resizePanel)
    {
      Dimension d = ap.annotationScroller.getPreferredSize();
      int dif = evt.getY() - oldY;

      dif /= ap.av.charHeight;
      dif *= ap.av.charHeight;

      if ((d.height - dif) > 20)
      {
        ap.annotationScroller.setPreferredSize(new Dimension(d.width,
                d.height - dif));
        d = ap.annotationSpaceFillerHolder.getPreferredSize();
        ap.annotationSpaceFillerHolder.setPreferredSize(new Dimension(
                d.width, d.height - dif));
        ap.paintAlignment(true);
      }

      ap.addNotify();
    }
    else
    {
      repaint();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void mouseMoved(MouseEvent evt)
  {
    resizePanel = evt.getY() < 10;

    getSelectedRow(evt.getY() - scrollOffset);

    if (selectedRow > -1
            && ap.av.getAlignment().getAlignmentAnnotation().length > selectedRow)
    {
      AlignmentAnnotation aa = ap.av.getAlignment()
              .getAlignmentAnnotation()[selectedRow];

      StringBuffer desc = new StringBuffer();
      if (aa.description != null
              && !aa.description.equals("New description"))
      {
        // TODO: we could refactor and merge this code with the code in
        // jalview.gui.SeqPanel.mouseMoved(..) that formats sequence feature
        // tooltips
        desc.append(aa.getDescription(true).trim());
        // check to see if the description is an html fragment.
        if (desc.length() < 6
                || (desc.substring(0, 6).toLowerCase().indexOf("<html>") < 0))
        {
          // clean the description ready for embedding in html
          desc = new StringBuffer(Pattern.compile("<").matcher(desc)
                  .replaceAll("&lt;"));
          desc.insert(0, "<html>");
        }
        else
        {
          // remove terminating html if any
          int i = desc.substring(desc.length() - 7).toLowerCase()
                  .lastIndexOf("</html>");
          if (i > -1)
          {
            desc.setLength(desc.length() - 7 + i);
          }
        }
        if (aa.hasScore())
        {
          desc.append("<br/>");
        }

      }
      else
      {
        // begin the tooltip's html fragment
        desc.append("<html>");
      }
      if (aa.hasScore())
      {
        // TODO: limit precision of score to avoid noise from imprecise doubles
        // (64.7 becomes 64.7+/some tiny value).
        desc.append(" Score: " + aa.score);
      }

      if (desc.length() > 6)
      {
        desc.append("</html>");
        this.setToolTipText(desc.toString());
      }
      else
        this.setToolTipText(null);
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void mouseClicked(MouseEvent evt)
  {
    AlignmentAnnotation[] aa = ap.av.getAlignment()
            .getAlignmentAnnotation();
    if (SwingUtilities.isLeftMouseButton(evt))
    {
      if (selectedRow > -1 && selectedRow < aa.length)
      {
        if (aa[selectedRow].groupRef != null)
        {
          if (evt.getClickCount() >= 2)
          {
            // todo: make the ap scroll to the selection - not necessary, first
            // click highlights/scrolls, second selects
            ap.seqPanel.ap.idPanel.highlightSearchResults(null);
            ap.av.setSelectionGroup(// new SequenceGroup(
            aa[selectedRow].groupRef); // );
            ap.paintAlignment(false);
            PaintRefresher.Refresh(ap, ap.av.getSequenceSetId());
            ap.av.sendSelection();
          }
          else
          {
            ap.seqPanel.ap.idPanel
                    .highlightSearchResults(aa[selectedRow].groupRef
                            .getSequences(null));
          }
          return;
        }
        else if (aa[selectedRow].sequenceRef != null)
        {
          Vector sr = new Vector();
          sr.addElement(aa[selectedRow].sequenceRef);
          if (evt.getClickCount() == 1)
          {
            ap.seqPanel.ap.idPanel.highlightSearchResults(sr);
          }
          else if (evt.getClickCount() >= 2)
          {
            ap.seqPanel.ap.idPanel.highlightSearchResults(null);
            SequenceGroup sg = new SequenceGroup();
            sg.addSequence(aa[selectedRow].sequenceRef, false);
            ap.av.setSelectionGroup(sg);
            ap.av.sendSelection();
            ap.paintAlignment(false);
            PaintRefresher.Refresh(ap, ap.av.getSequenceSetId());
          }

        }
      }
    }
    if (!SwingUtilities.isRightMouseButton(evt))
    {
      return;
    }

    JPopupMenu pop = new JPopupMenu("Annotations");
    JMenuItem item = new JMenuItem(ADDNEW);
    item.addActionListener(this);
    pop.add(item);
    if (selectedRow < 0)
    {
      if (hasHiddenRows)
      { // let the user make everything visible again
        item = new JMenuItem(SHOWALL);
        item.addActionListener(this);
        pop.add(item);
      }
      pop.show(this, evt.getX(), evt.getY());
      return;
    }
    item = new JMenuItem(EDITNAME);
    item.addActionListener(this);
    pop.add(item);
    item = new JMenuItem(HIDE);
    item.addActionListener(this);
    pop.add(item);
    item = new JMenuItem(DELETE);
    item.addActionListener(this);
    pop.add(item);
    if (hasHiddenRows)
    {
      item = new JMenuItem(SHOWALL);
      item.addActionListener(this);
      pop.add(item);
    }
    item = new JMenuItem(OUTPUT_TEXT);
    item.addActionListener(this);
    pop.add(item);
    // TODO: annotation object should be typed for autocalculated/derived
    // property methods
    if (selectedRow < aa.length)
    {
      if (!aa[selectedRow].autoCalculated)
      {
        if (aa[selectedRow].graph == AlignmentAnnotation.NO_GRAPH)
        {
          // display formatting settings for this row.
          pop.addSeparator();
          // av and sequencegroup need to implement same interface for
          item = new JCheckBoxMenuItem(TOGGLE_LABELSCALE,
                  aa[selectedRow].scaleColLabel);
          item.addActionListener(this);
          pop.add(item);
        }
      }
      else if (aa[selectedRow].label.indexOf("Consensus") > -1)
      {
        pop.addSeparator();
        // av and sequencegroup need to implement same interface for
        final JCheckBoxMenuItem cbmi = new JCheckBoxMenuItem(
                "Ignore Gaps In Consensus",
                (aa[selectedRow].groupRef != null) ? aa[selectedRow].groupRef
                        .getIgnoreGapsConsensus() : ap.av
                        .getIgnoreGapsConsensus());
        final AlignmentAnnotation aaa = aa[selectedRow];
        cbmi.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            if (aaa.groupRef != null)
            {
              // TODO: pass on reference to ap so the view can be updated.
              aaa.groupRef.setIgnoreGapsConsensus(cbmi.getState());
              ap.annotationPanel.paint(ap.annotationPanel.getGraphics());
            }
            else
            {
              ap.av.setIgnoreGapsConsensus(cbmi.getState(), ap);
            }
          }
        });
        pop.add(cbmi);
        // av and sequencegroup need to implement same interface for
        if (aaa.groupRef != null)
        {
          final JCheckBoxMenuItem chist = new JCheckBoxMenuItem(
                  "Show Group Histogram",
                  aa[selectedRow].groupRef.isShowConsensusHistogram());
          chist.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              // TODO: pass on reference
              // to ap
              // so the
              // view
              // can be
              // updated.
              aaa.groupRef.setShowConsensusHistogram(chist.getState());
              ap.repaint();
              // ap.annotationPanel.paint(ap.annotationPanel.getGraphics());
            }
          });
          pop.add(chist);
          final JCheckBoxMenuItem cprofl = new JCheckBoxMenuItem(
                  "Show Group Logo",
                  aa[selectedRow].groupRef.isShowSequenceLogo());
          cprofl.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              // TODO: pass on reference
              // to ap
              // so the
              // view
              // can be
              // updated.
              aaa.groupRef.setshowSequenceLogo(cprofl.getState());
              ap.repaint();
              // ap.annotationPanel.paint(ap.annotationPanel.getGraphics());
            }
          });
          pop.add(cprofl);
          final JCheckBoxMenuItem cproflnorm = new JCheckBoxMenuItem(
                  "Normalise Group Logo",
                  aa[selectedRow].groupRef.isNormaliseSequenceLogo());
          cproflnorm.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {

              // TODO: pass on reference
              // to ap
              // so the
              // view
              // can be
              // updated.
              aaa.groupRef.setNormaliseSequenceLogo(cproflnorm.getState());
              // automatically enable logo display if we're clicked
              aaa.groupRef.setshowSequenceLogo(true);
              ap.repaint();
              // ap.annotationPanel.paint(ap.annotationPanel.getGraphics());
            }
          });
          pop.add(cproflnorm);
        }
        else
        {
          final JCheckBoxMenuItem chist = new JCheckBoxMenuItem(
                  "Show Histogram", av.isShowConsensusHistogram());
          chist.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              // TODO: pass on reference
              // to ap
              // so the
              // view
              // can be
              // updated.
              av.setShowConsensusHistogram(chist.getState());
              ap.alignFrame.setMenusForViewport();
              ap.repaint();
              // ap.annotationPanel.paint(ap.annotationPanel.getGraphics());
            }
          });
          pop.add(chist);
          final JCheckBoxMenuItem cprof = new JCheckBoxMenuItem(
                  "Show Logo", av.isShowSequenceLogo());
          cprof.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              // TODO: pass on reference
              // to ap
              // so the
              // view
              // can be
              // updated.
              av.setShowSequenceLogo(cprof.getState());
              ap.alignFrame.setMenusForViewport();
              ap.repaint();
              // ap.annotationPanel.paint(ap.annotationPanel.getGraphics());
            }
          });
          pop.add(cprof);
          final JCheckBoxMenuItem cprofnorm = new JCheckBoxMenuItem(
                  "Normalise Logo", av.isNormaliseSequenceLogo());
          cprofnorm.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              // TODO: pass on reference
              // to ap
              // so the
              // view
              // can be
              // updated.
              av.setShowSequenceLogo(true);
              av.setNormaliseSequenceLogo(cprofnorm.getState());
              ap.alignFrame.setMenusForViewport();
              ap.repaint();
              // ap.annotationPanel.paint(ap.annotationPanel.getGraphics());
            }
          });
          pop.add(cprofnorm);
        }
        final JMenuItem consclipbrd = new JMenuItem(COPYCONS_SEQ);
        consclipbrd.addActionListener(this);
        pop.add(consclipbrd);
      }
    }
    pop.show(this, evt.getX(), evt.getY());
  }

  /**
   * do a single sequence copy to jalview and the system clipboard
   * 
   * @param sq
   *          sequence to be copied to clipboard
   */
  protected void copy_annotseqtoclipboard(SequenceI sq)
  {
    SequenceI[] seqs = new SequenceI[]
    { sq };
    String[] omitHidden = null;
    SequenceI[] dseqs = new SequenceI[]
    { sq.getDatasetSequence() };
    if (dseqs[0] == null)
    {
      dseqs[0] = new Sequence(sq);
      dseqs[0].setSequence(jalview.analysis.AlignSeq.extractGaps(
              jalview.util.Comparison.GapChars, sq.getSequenceAsString()));

      sq.setDatasetSequence(dseqs[0]);
    }
    Alignment ds = new Alignment(dseqs);
    if (av.hasHiddenColumns())
    {
      omitHidden = av.getColumnSelection().getVisibleSequenceStrings(0,
              sq.getLength(), seqs);
    }

    String output = new FormatAdapter().formatSequences("Fasta", seqs,
            omitHidden);

    Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new StringSelection(output), Desktop.instance);

    Vector hiddenColumns = null;
    if (av.hasHiddenColumns())
    {
      hiddenColumns = new Vector();
      for (int i = 0; i < av.getColumnSelection().getHiddenColumns().size(); i++)
      {
        int[] region = (int[]) av.getColumnSelection().getHiddenColumns()
                .elementAt(i);

        hiddenColumns.addElement(new int[]
        { region[0], region[1] });
      }
    }

    Desktop.jalviewClipboard = new Object[]
    { seqs, ds, // what is the dataset of a consensus sequence ? need to flag
        // sequence as special.
        hiddenColumns };
  }

  /**
   * DOCUMENT ME!
   * 
   * @param g1
   *          DOCUMENT ME!
   */
  public void paintComponent(Graphics g)
  {

    int width = getWidth();
    if (width == 0)
    {
      width = ap.calculateIdWidth().width + 4;
    }

    Graphics2D g2 = (Graphics2D) g;
    if (av.antiAlias)
    {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
              RenderingHints.VALUE_ANTIALIAS_ON);
    }

    drawComponent(g2, true, width);

  }

  /**
   * Draw the full set of annotation Labels for the alignment at the given cursor
   * 
   * @param g Graphics2D instance (needed for font scaling)
   * @param width Width for scaling labels
   *          
   */
  public void drawComponent(Graphics g, int width)
  {
    drawComponent(g, false, width);
  }

  private final boolean debugRedraw = false;
  /**
   * Draw the full set of annotation Labels for the alignment at the given cursor
   * 
   * @param g Graphics2D instance (needed for font scaling)
   * @param clip - true indicates that only current visible area needs to be rendered
   * @param width Width for scaling labels         
   */
  public void drawComponent(Graphics g, boolean clip, int width)
  {
    if (av.getFont().getSize() < 10)
    {
      g.setFont(font);
    }
    else
    {
      g.setFont(av.getFont());
    }

    FontMetrics fm = g.getFontMetrics(g.getFont());
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    g.translate(0, scrollOffset);
    g.setColor(Color.black);
    
    AlignmentAnnotation[] aa = av.getAlignment().getAlignmentAnnotation();
    int fontHeight = g.getFont().getSize();
    int y = 0;
    int x = 0;
    int graphExtras = 0;
    int offset = 0;
    Font baseFont = g.getFont();
    FontMetrics baseMetrics = fm;
    int ofontH = fontHeight;
    int sOffset=0;
    int visHeight = 0;
    int[] visr = (ap!=null && ap.annotationPanel!=null) ? ap.annotationPanel.getVisibleVRange() : null;
    if (clip && visr!=null){ 
      sOffset = visr[0]; 
      visHeight = visr[1];
    }
    boolean visible = true,before=false,after=false;
    if (aa != null)
    {
      hasHiddenRows = false;
      int olY=0;
      for (int i = 0; i < aa.length; i++)
      {
        visible = true;
        if (!aa[i].visible)
        {
          hasHiddenRows = true;
          continue;
        }
        olY=y;
        y += aa[i].height;
        if (clip) {if (y<sOffset)
        {
          if (!before)
          {
            if (debugRedraw) {
              System.out.println("before vis: "+i);
            }
          before=true;
          }
          // don't draw what isn't visible
          continue;
        }
        if (olY>visHeight)
        {

          if (!after)
          {
            if (debugRedraw) {
              System.out.println("Scroll offset: "+sOffset+" after vis: "+i);
            }
          after=true;
          }
          // don't draw what isn't visible
          continue;
        }}
        g.setColor(Color.black);

        offset = -aa[i].height / 2;

        if (aa[i].hasText)
        {
          offset += fm.getHeight() / 2;
          offset -= fm.getDescent();
        }
        else
          offset += fm.getDescent();

        x = width - fm.stringWidth(aa[i].label) - 3;

        if (aa[i].graphGroup > -1)
        {
          int groupSize = 0;
          // TODO: JAL-1291 revise rendering model so the graphGroup map is computed efficiently for all visible labels
          for (int gg = 0; gg < aa.length; gg++)
          {
            if (aa[gg].graphGroup == aa[i].graphGroup)
            {
              groupSize++;
            }
          }
          if (groupSize * (fontHeight + 8) < aa[i].height)
          {
            graphExtras = (aa[i].height - (groupSize * (fontHeight + 8))) / 2;
          }
          else
          {
            // scale font to fit
            float h = aa[i].height / (float) groupSize, s;
            if (h < 9)
            {
              visible = false;
            }
            else
            {
              fontHeight = -8 + (int) h;
              s = ((float) fontHeight) / (float) ofontH;
              Font f = baseFont.deriveFont(AffineTransform
                      .getScaleInstance(s, s));
              g.setFont(f);
              fm = g.getFontMetrics();
              graphExtras = (aa[i].height - (groupSize * (fontHeight + 8))) / 2;
            }
          }
          if (visible)
          {
            for (int gg = 0; gg < aa.length; gg++)
            {
              if (aa[gg].graphGroup == aa[i].graphGroup)
              {
                x = width - fm.stringWidth(aa[gg].label) - 3;
                g.drawString(aa[gg].label, x, y - graphExtras);

                if (aa[gg]._linecolour != null)
                {

                  g.setColor(aa[gg]._linecolour);
                  g.drawLine(x, y - graphExtras + 3,
                          x + fm.stringWidth(aa[gg].label), y - graphExtras
                                  + 3);
                }

                g.setColor(Color.black);
                graphExtras += fontHeight + 8;
              }
            }
          }
          g.setFont(baseFont);
          fm = baseMetrics;
          fontHeight = ofontH;
        }
        else
        {
          g.drawString(aa[i].label, x, y + offset);
        }
      }
    }

    if (resizePanel)
    {
      g.drawImage(image, 2, 0 - scrollOffset, this);
    }
    else if (dragEvent != null && aa != null)
    {
      g.setColor(Color.lightGray);
      g.drawString(aa[selectedRow].label, dragEvent.getX(),
              dragEvent.getY() - scrollOffset);
    }

    if (!av.wrapAlignment && ((aa == null) || (aa.length < 1)))
    {
      g.drawString("Right click", 2, 8);
      g.drawString("to add annotation", 2, 18);
    }
  }
}
