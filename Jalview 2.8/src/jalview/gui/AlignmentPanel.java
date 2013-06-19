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

import java.beans.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;

import jalview.api.AlignmentViewPanel;
import jalview.bin.Cache;
import jalview.datamodel.*;
import jalview.jbgui.*;
import jalview.schemes.*;
import jalview.structure.StructureSelectionManager;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision: 1.161 $
 */
public class AlignmentPanel extends GAlignmentPanel implements
        AdjustmentListener, Printable, AlignmentViewPanel
{
  public AlignViewport av;

  OverviewPanel overviewPanel;

  SeqPanel seqPanel;

  IdPanel idPanel;

  IdwidthAdjuster idwidthAdjuster;

  /** DOCUMENT ME!! */
  public AlignFrame alignFrame;

  ScalePanel scalePanel;

  AnnotationPanel annotationPanel;

  AnnotationLabels alabels;

  // this value is set false when selection area being dragged
  boolean fastPaint = true;

  int hextent = 0;

  int vextent = 0;

  /**
   * Creates a new AlignmentPanel object.
   * 
   * @param af
   *          DOCUMENT ME!
   * @param av
   *          DOCUMENT ME!
   */
  public AlignmentPanel(AlignFrame af, final AlignViewport av)
  {
    alignFrame = af;
    this.av = av;
    seqPanel = new SeqPanel(av, this);
    idPanel = new IdPanel(av, this);

    scalePanel = new ScalePanel(av, this);

    idPanelHolder.add(idPanel, BorderLayout.CENTER);
    idwidthAdjuster = new IdwidthAdjuster(this);
    idSpaceFillerPanel1.add(idwidthAdjuster, BorderLayout.CENTER);

    annotationPanel = new AnnotationPanel(this);
    alabels = new AnnotationLabels(this);

    annotationScroller.setViewportView(annotationPanel);
    annotationSpaceFillerHolder.add(alabels, BorderLayout.CENTER);

    scalePanelHolder.add(scalePanel, BorderLayout.CENTER);
    seqPanelHolder.add(seqPanel, BorderLayout.CENTER);

    setScrollValues(0, 0);

    setAnnotationVisible(av.getShowAnnotation());

    hscroll.addAdjustmentListener(this);
    vscroll.addAdjustmentListener(this);

    final AlignmentPanel ap = this;
    av.addPropertyChangeListener(new PropertyChangeListener()
    {
      public void propertyChange(PropertyChangeEvent evt)
      {
        if (evt.getPropertyName().equals("alignment"))
        {
          PaintRefresher.Refresh(ap, av.getSequenceSetId(), true, true);
          alignmentChanged();
        }
      }
    });
    fontChanged();
    adjustAnnotationHeight();

  }

  public void alignmentChanged()
  {
    av.alignmentChanged(this);

    alignFrame.updateEditMenuBar();

    paintAlignment(true);

  }

  /**
   * DOCUMENT ME!
   */
  public void fontChanged()
  {
    // set idCanvas bufferedImage to null
    // to prevent drawing old image
    FontMetrics fm = getFontMetrics(av.getFont());

    scalePanelHolder.setPreferredSize(new Dimension(10, av.charHeight
            + fm.getDescent()));
    idSpaceFillerPanel1.setPreferredSize(new Dimension(10, av.charHeight
            + fm.getDescent()));

    idPanel.idCanvas.gg = null;
    seqPanel.seqCanvas.img = null;
    annotationPanel.adjustPanelHeight();

    Dimension d = calculateIdWidth();
    d.setSize(d.width + 4, d.height);
    idPanel.idCanvas.setPreferredSize(d);
    hscrollFillerPanel.setPreferredSize(d);

    if (overviewPanel != null)
    {
      overviewPanel.setBoxPosition();
    }

    repaint();
  }

  /**
   * Calculate the width of the alignment labels based on the displayed names
   * and any bounds on label width set in preferences.
   * 
   * @return Dimension giving the maximum width of the alignment label panel
   *         that should be used.
   */
  public Dimension calculateIdWidth()
  {
    // calculate sensible default width when no preference is available

    int afwidth = (alignFrame != null ? alignFrame.getWidth() : 300);
    int maxwidth = Math.max(20,
            Math.min(afwidth - 200, (int) 2 * afwidth / 3));
    return calculateIdWidth(maxwidth);
  }

  /**
   * Calculate the width of the alignment labels based on the displayed names
   * and any bounds on label width set in preferences.
   * 
   * @param maxwidth
   *          -1 or maximum width allowed for IdWidth
   * @return Dimension giving the maximum width of the alignment label panel
   *         that should be used.
   */
  public Dimension calculateIdWidth(int maxwidth)
  {
    Container c = new Container();

    FontMetrics fm = c.getFontMetrics(new Font(av.font.getName(),
            Font.ITALIC, av.font.getSize()));

    AlignmentI al = av.getAlignment();
    int i = 0;
    int idWidth = 0;
    String id;

    while ((i < al.getHeight()) && (al.getSequenceAt(i) != null))
    {
      SequenceI s = al.getSequenceAt(i);

      id = s.getDisplayId(av.getShowJVSuffix());

      if (fm.stringWidth(id) > idWidth)
      {
        idWidth = fm.stringWidth(id);
      }

      i++;
    }

    // Also check annotation label widths
    i = 0;

    if (al.getAlignmentAnnotation() != null)
    {
      fm = c.getFontMetrics(alabels.getFont());

      while (i < al.getAlignmentAnnotation().length)
      {
        String label = al.getAlignmentAnnotation()[i].label;

        if (fm.stringWidth(label) > idWidth)
        {
          idWidth = fm.stringWidth(label);
        }

        i++;
      }
    }

    return new Dimension(maxwidth < 0 ? idWidth : Math.min(maxwidth,
            idWidth), 12);
  }

  /**
   * Highlight the given results on the alignment.
   * 
   */
  public void highlightSearchResults(SearchResults results)
  {
    scrollToPosition(results);
    seqPanel.seqCanvas.highlightSearchResults(results);
  }

  /**
   * scroll the view to show the position of the highlighted region in results
   * (if any) and redraw the overview
   * 
   * @param results
   */
  public boolean scrollToPosition(SearchResults results)
  {
    return scrollToPosition(results, true);
  }

  /**
   * scroll the view to show the position of the highlighted region in results
   * (if any)
   * 
   * @param results
   * @param redrawOverview
   *          - when set, the overview will be recalculated (takes longer)
   * @return false if results were not found
   */
  public boolean scrollToPosition(SearchResults results,
          boolean redrawOverview)
  {
    int startv, endv, starts, ends, width;
    // TODO: properly locate search results in view when large numbers of hidden
    // columns exist before highlighted region
    // do we need to scroll the panel?
    // TODO: tons of nullpointereexceptions raised here.
    if (results != null && results.getSize() > 0 && av != null
            && av.getAlignment() != null)
    {
      int seqIndex = av.getAlignment().findIndex(results);
      if (seqIndex == -1)
      {
        return false;
      }
      SequenceI seq = av.getAlignment().getSequenceAt(seqIndex);

      int[] r = results.getResults(seq, 0, av.getAlignment().getWidth());
      if (r == null)
      {
        return false;
      }
      int start = r[0];
      int end = r[1];
      // System.err.println("Seq : "+seqIndex+" Scroll to "+start+","+end); //
      // DEBUG
      if (start < 0)
      {
        return false;
      }
      if (end == seq.getEnd())
      {
        return false;
      }
      if (av.hasHiddenColumns())
      {
        start = av.getColumnSelection().findColumnPosition(start);
        end = av.getColumnSelection().findColumnPosition(end);
        if (start == end)
        {
          if (!av.getColumnSelection().isVisible(r[0]))
          {
            // don't scroll - position isn't visible
            return false;
          }
        }
      }
      if (!av.wrapAlignment)
      {
        if ((startv = av.getStartRes()) >= start)
        {
          setScrollValues(start - 1, seqIndex);
        }
        else if ((endv = av.getEndRes()) <= end)
        {
          setScrollValues(startv + 1 + end - endv, seqIndex);
        }
        else if ((starts = av.getStartSeq()) > seqIndex)
        {
          setScrollValues(av.getStartRes(), seqIndex);
        }
        else if ((ends = av.getEndSeq()) <= seqIndex)
        {
          setScrollValues(av.getStartRes(), starts + seqIndex - ends + 1);
        }
      }
      else
      {
        scrollToWrappedVisible(start);
      }
    }
    if (redrawOverview && overviewPanel != null)
    {
      overviewPanel.setBoxPosition();
    }
    paintAlignment(redrawOverview);
    return true;
  }

  void scrollToWrappedVisible(int res)
  {
    int cwidth = seqPanel.seqCanvas
            .getWrappedCanvasWidth(seqPanel.seqCanvas.getWidth());
    if (res < av.getStartRes() || res >= (av.getStartRes() + cwidth))
    {
      vscroll.setValue((res / cwidth));
      av.startRes = vscroll.getValue() * cwidth;
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public OverviewPanel getOverviewPanel()
  {
    return overviewPanel;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param op
   *          DOCUMENT ME!
   */
  public void setOverviewPanel(OverviewPanel op)
  {
    overviewPanel = op;
  }

  /**
   * 
   * @param b
   *          Hide or show annotation panel
   * 
   */
  public void setAnnotationVisible(boolean b)
  {
    if (!av.wrapAlignment)
    {
      annotationSpaceFillerHolder.setVisible(b);
      annotationScroller.setVisible(b);
    }
    repaint();
  }

  /**
   * automatically adjust annotation panel height for new annotation whilst
   * ensuring the alignment is still visible.
   */
  public void adjustAnnotationHeight()
  {
    // TODO: display vertical annotation scrollbar if necessary
    // this is called after loading new annotation onto alignment
    if (alignFrame.getHeight() == 0)
    {
      System.out.println("NEEDS FIXING");
    }
    validateAnnotationDimensions(true);
    addNotify();
    paintAlignment(true);
  }

  /**
   * calculate the annotation dimensions and refresh slider values accordingly.
   * need to do repaints/notifys afterwards.
   */
  protected void validateAnnotationDimensions(boolean adjustPanelHeight)
  {
    int height = annotationPanel.adjustPanelHeight();

    if (hscroll.isVisible())
    {
      height += hscroll.getPreferredSize().height;
    }
    if (height > alignFrame.getHeight() / 2)
    {
      height = alignFrame.getHeight() / 2;
    }
    if (!adjustPanelHeight)
    {
      // maintain same window layout whilst updating sliders
      height = annotationScroller.getSize().height;
    }
    hscroll.addNotify();

    annotationScroller.setPreferredSize(new Dimension(annotationScroller
            .getWidth(), height));

    annotationSpaceFillerHolder.setPreferredSize(new Dimension(
            annotationSpaceFillerHolder.getWidth(), height));
    annotationScroller.validate();// repaint();
    annotationScroller.addNotify();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param wrap
   *          DOCUMENT ME!
   */
  public void setWrapAlignment(boolean wrap)
  {
    av.startSeq = 0;
    scalePanelHolder.setVisible(!wrap);
    hscroll.setVisible(!wrap);
    idwidthAdjuster.setVisible(!wrap);

    if (wrap)
    {
      annotationScroller.setVisible(false);
      annotationSpaceFillerHolder.setVisible(false);
    }
    else if (av.showAnnotation)
    {
      annotationScroller.setVisible(true);
      annotationSpaceFillerHolder.setVisible(true);
    }

    idSpaceFillerPanel1.setVisible(!wrap);

    repaint();
  }

  // return value is true if the scroll is valid
  public boolean scrollUp(boolean up)
  {
    if (up)
    {
      if (vscroll.getValue() < 1)
      {
        return false;
      }

      fastPaint = false;
      vscroll.setValue(vscroll.getValue() - 1);
    }
    else
    {
      if ((vextent + vscroll.getValue()) >= av.getAlignment().getHeight())
      {
        return false;
      }

      fastPaint = false;
      vscroll.setValue(vscroll.getValue() + 1);
    }

    fastPaint = true;

    return true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param right
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean scrollRight(boolean right)
  {
    if (!right)
    {
      if (hscroll.getValue() < 1)
      {
        return false;
      }

      fastPaint = false;
      hscroll.setValue(hscroll.getValue() - 1);
    }
    else
    {
      if ((hextent + hscroll.getValue()) >= av.getAlignment().getWidth())
      {
        return false;
      }

      fastPaint = false;
      hscroll.setValue(hscroll.getValue() + 1);
    }

    fastPaint = true;

    return true;
  }

  /**
   * Adjust row/column scrollers to show a visible position in the alignment.
   * 
   * @param x
   *          visible column to scroll to DOCUMENT ME!
   * @param y
   *          visible row to scroll to
   * 
   */
  public void setScrollValues(int x, int y)
  {
    // System.err.println("Scroll to "+x+","+y);
    if (av == null || av.getAlignment() == null)
    {
      return;
    }
    int width = av.getAlignment().getWidth();
    int height = av.getAlignment().getHeight();

    if (av.hasHiddenColumns())
    {
      width = av.getColumnSelection().findColumnPosition(width);
    }

    av.setEndRes((x + (seqPanel.seqCanvas.getWidth() / av.charWidth)) - 1);

    hextent = seqPanel.seqCanvas.getWidth() / av.charWidth;
    vextent = seqPanel.seqCanvas.getHeight() / av.charHeight;

    if (hextent > width)
    {
      hextent = width;
    }

    if (vextent > height)
    {
      vextent = height;
    }

    if ((hextent + x) > width)
    {
      x = width - hextent;
    }

    if ((vextent + y) > height)
    {
      y = height - vextent;
    }

    if (y < 0)
    {
      y = 0;
    }

    if (x < 0)
    {
      x = 0;
    }

    hscroll.setValues(x, hextent, 0, width);
    vscroll.setValues(y, vextent, 0, height);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void adjustmentValueChanged(AdjustmentEvent evt)
  {

    int oldX = av.getStartRes();
    int oldY = av.getStartSeq();

    if (evt.getSource() == hscroll)
    {
      int x = hscroll.getValue();
      av.setStartRes(x);
      av.setEndRes((x + (seqPanel.seqCanvas.getWidth() / av.getCharWidth())) - 1);
    }

    if (evt.getSource() == vscroll)
    {
      int offy = vscroll.getValue();

      if (av.getWrapAlignment())
      {
        if (offy > -1)
        {
          int rowSize = seqPanel.seqCanvas
                  .getWrappedCanvasWidth(seqPanel.seqCanvas.getWidth());
          av.setStartRes(offy * rowSize);
          av.setEndRes((offy + 1) * rowSize);
        }
        else
        {
          // This is only called if file loaded is a jar file that
          // was wrapped when saved and user has wrap alignment true
          // as preference setting
          SwingUtilities.invokeLater(new Runnable()
          {
            public void run()
            {
              setScrollValues(av.getStartRes(), av.getStartSeq());
            }
          });
        }
      }
      else
      {
        av.setStartSeq(offy);
        av.setEndSeq(offy
                + (seqPanel.seqCanvas.getHeight() / av.getCharHeight()));
      }
    }

    if (overviewPanel != null)
    {
      overviewPanel.setBoxPosition();
    }

    int scrollX = av.startRes - oldX;
    int scrollY = av.startSeq - oldY;

    if (av.getWrapAlignment() || !fastPaint)
    {
      repaint();
    }
    else
    {
      // Make sure we're not trying to draw a panel
      // larger than the visible window
      if (scrollX > av.endRes - av.startRes)
      {
        scrollX = av.endRes - av.startRes;
      }
      else if (scrollX < av.startRes - av.endRes)
      {
        scrollX = av.startRes - av.endRes;
      }

      if (scrollX != 0 || scrollY != 0)
      {
        idPanel.idCanvas.fastPaint(scrollY);
        seqPanel.seqCanvas.fastPaint(scrollX, scrollY);
        scalePanel.repaint();

        if (av.getShowAnnotation() && scrollX!=0)
        {
          annotationPanel.fastPaint(scrollX);
        }
      }
    }
  }

  public void paintAlignment(boolean updateOverview)
  {
    repaint();

    if (updateOverview)
    {
      av.getStructureSelectionManager().sequenceColoursChanged(this);

      if (overviewPanel != null)
      {
        overviewPanel.updateOverviewImage();
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param g
   *          DOCUMENT ME!
   */
  public void paintComponent(Graphics g)
  {
    invalidate();

    Dimension d = idPanel.idCanvas.getPreferredSize();
    idPanelHolder.setPreferredSize(d);
    hscrollFillerPanel.setPreferredSize(new Dimension(d.width, 12));
    validate();

    if (av.getWrapAlignment())
    {
      int maxwidth = av.getAlignment().getWidth();

      if (av.hasHiddenColumns())
      {
        maxwidth = av.getColumnSelection().findColumnPosition(maxwidth) - 1;
      }

      int canvasWidth = seqPanel.seqCanvas
              .getWrappedCanvasWidth(seqPanel.seqCanvas.getWidth());
      if (canvasWidth > 0)
      {
        int max = maxwidth
                / seqPanel.seqCanvas
                        .getWrappedCanvasWidth(seqPanel.seqCanvas
                                .getWidth()) + 1;
        vscroll.setMaximum(max);
        vscroll.setUnitIncrement(1);
        vscroll.setVisibleAmount(1);
      }
    }
    else
    {
      setScrollValues(av.getStartRes(), av.getStartSeq());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param pg
   *          DOCUMENT ME!
   * @param pf
   *          DOCUMENT ME!
   * @param pi
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   * 
   * @throws PrinterException
   *           DOCUMENT ME!
   */
  public int print(Graphics pg, PageFormat pf, int pi)
          throws PrinterException
  {
    pg.translate((int) pf.getImageableX(), (int) pf.getImageableY());

    int pwidth = (int) pf.getImageableWidth();
    int pheight = (int) pf.getImageableHeight();

    if (av.getWrapAlignment())
    {
      return printWrappedAlignment(pg, pwidth, pheight, pi);
    }
    else
    {
      return printUnwrapped(pg, pwidth, pheight, pi);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param pg
   *          DOCUMENT ME!
   * @param pwidth
   *          DOCUMENT ME!
   * @param pheight
   *          DOCUMENT ME!
   * @param pi
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   * 
   * @throws PrinterException
   *           DOCUMENT ME!
   */
  public int printUnwrapped(Graphics pg, int pwidth, int pheight, int pi)
          throws PrinterException
  {
    int idWidth = getVisibleIdWidth(false);
    FontMetrics fm = getFontMetrics(av.getFont());
    int scaleHeight = av.charHeight + fm.getDescent();

    pg.setColor(Color.white);
    pg.fillRect(0, 0, pwidth, pheight);
    pg.setFont(av.getFont());

    // //////////////////////////////////
    // / How many sequences and residues can we fit on a printable page?
    int totalRes = (pwidth - idWidth) / av.getCharWidth();

    int totalSeq = (int) ((pheight - scaleHeight) / av.getCharHeight()) - 1;

    int pagesWide = (av.getAlignment().getWidth() / totalRes) + 1;

    // ///////////////////////////
    // / Only print these sequences and residues on this page
    int startRes;

    // ///////////////////////////
    // / Only print these sequences and residues on this page
    int endRes;

    // ///////////////////////////
    // / Only print these sequences and residues on this page
    int startSeq;

    // ///////////////////////////
    // / Only print these sequences and residues on this page
    int endSeq;
    startRes = (pi % pagesWide) * totalRes;
    endRes = (startRes + totalRes) - 1;

    if (endRes > (av.getAlignment().getWidth() - 1))
    {
      endRes = av.getAlignment().getWidth() - 1;
    }

    startSeq = (pi / pagesWide) * totalSeq;
    endSeq = startSeq + totalSeq;

    if (endSeq > av.getAlignment().getHeight())
    {
      endSeq = av.getAlignment().getHeight();
    }

    int pagesHigh = ((av.getAlignment().getHeight() / totalSeq) + 1)
            * pheight;

    if (av.showAnnotation)
    {
      pagesHigh += annotationPanel.adjustPanelHeight() + 3;
    }

    pagesHigh /= pheight;

    if (pi >= (pagesWide * pagesHigh))
    {
      return Printable.NO_SUCH_PAGE;
    }

    // draw Scale
    pg.translate(idWidth, 0);
    scalePanel.drawScale(pg, startRes, endRes, pwidth - idWidth,
            scaleHeight);
    pg.translate(-idWidth, scaleHeight);

    // //////////////
    // Draw the ids
    Color currentColor = null;
    Color currentTextColor = null;

    pg.setFont(idPanel.idCanvas.idfont);

    SequenceI seq;
    for (int i = startSeq; i < endSeq; i++)
    {
      seq = av.getAlignment().getSequenceAt(i);
      if ((av.getSelectionGroup() != null)
              && av.getSelectionGroup().getSequences(null).contains(seq))
      {
        currentColor = Color.gray;
        currentTextColor = Color.black;
      }
      else
      {
        currentColor = av.getSequenceColour(seq);
        currentTextColor = Color.black;
      }

      pg.setColor(currentColor);
      pg.fillRect(0, (i - startSeq) * av.charHeight, idWidth,
              av.getCharHeight());

      pg.setColor(currentTextColor);

      int xPos = 0;
      if (av.rightAlignIds)
      {
        fm = pg.getFontMetrics();
        xPos = idWidth
                - fm.stringWidth(seq.getDisplayId(av.getShowJVSuffix()))
                - 4;
      }

      pg.drawString(
              seq.getDisplayId(av.getShowJVSuffix()),
              xPos,
              (((i - startSeq) * av.charHeight) + av.getCharHeight())
                      - (av.getCharHeight() / 5));
    }

    pg.setFont(av.getFont());

    // draw main sequence panel
    pg.translate(idWidth, 0);
    seqPanel.seqCanvas.drawPanel(pg, startRes, endRes, startSeq, endSeq, 0);

    if (av.showAnnotation && (endSeq == av.getAlignment().getHeight()))
    {
      // draw annotation - need to offset for current scroll position
      int offset = -alabels.scrollOffset;
      pg.translate(0, offset);
      pg.translate(-idWidth - 3, (endSeq - startSeq) * av.charHeight + 3);
      alabels.drawComponent((Graphics2D) pg, idWidth);
      pg.translate(idWidth + 3, 0);
      annotationPanel.renderer.drawComponent(annotationPanel, av,
              (Graphics2D) pg, -1, startRes, endRes + 1);
      pg.translate(0, -offset);
    }

    return Printable.PAGE_EXISTS;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param pg
   *          DOCUMENT ME!
   * @param pwidth
   *          DOCUMENT ME!
   * @param pheight
   *          DOCUMENT ME!
   * @param pi
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   * 
   * @throws PrinterException
   *           DOCUMENT ME!
   */
  public int printWrappedAlignment(Graphics pg, int pwidth, int pheight,
          int pi) throws PrinterException
  {

    int annotationHeight = 0;
    AnnotationLabels labels = null;
    if (av.showAnnotation)
    {
      annotationHeight = annotationPanel.adjustPanelHeight();
      labels = new AnnotationLabels(av);
    }

    int hgap = av.charHeight;
    if (av.scaleAboveWrapped)
    {
      hgap += av.charHeight;
    }

    int cHeight = av.getAlignment().getHeight() * av.charHeight + hgap
            + annotationHeight;

    int idWidth = getVisibleIdWidth(false);

    int maxwidth = av.getAlignment().getWidth();
    if (av.hasHiddenColumns())
    {
      maxwidth = av.getColumnSelection().findColumnPosition(maxwidth) - 1;
    }

    int resWidth = seqPanel.seqCanvas.getWrappedCanvasWidth(pwidth
            - idWidth);

    int totalHeight = cHeight * (maxwidth / resWidth + 1);

    pg.setColor(Color.white);
    pg.fillRect(0, 0, pwidth, pheight);
    pg.setFont(av.getFont());

    // //////////////
    // Draw the ids
    pg.setColor(Color.black);

    pg.translate(0, -pi * pheight);

    pg.setClip(0, pi * pheight, pwidth, pheight);

    int ypos = hgap;

    do
    {
      for (int i = 0; i < av.getAlignment().getHeight(); i++)
      {
        pg.setFont(idPanel.idCanvas.idfont);
        SequenceI s = av.getAlignment().getSequenceAt(i);
        String string = s.getDisplayId(av.getShowJVSuffix());
        int xPos = 0;
        if (av.rightAlignIds)
        {
          FontMetrics fm = pg.getFontMetrics();
          xPos = idWidth - fm.stringWidth(string) - 4;
        }
        pg.drawString(string, xPos,
                ((i * av.charHeight) + ypos + av.charHeight)
                        - (av.charHeight / 5));
      }
      if (labels != null)
      {
        pg.translate(-3, ypos
                + (av.getAlignment().getHeight() * av.charHeight));

        pg.setFont(av.getFont());
        labels.drawComponent(pg, idWidth);
        pg.translate(+3, -ypos
                - (av.getAlignment().getHeight() * av.charHeight));
      }

      ypos += cHeight;
    } while (ypos < totalHeight);

    pg.translate(idWidth, 0);

    seqPanel.seqCanvas.drawWrappedPanel(pg, pwidth - idWidth, totalHeight,
            0);

    if ((pi * pheight) < totalHeight)
    {
      return Printable.PAGE_EXISTS;

    }
    else
    {
      return Printable.NO_SUCH_PAGE;
    }
  }

  /**
   * get current sequence ID panel width, or nominal value if panel were to be
   * displayed using default settings
   * 
   * @return
   */
  int getVisibleIdWidth()
  {
    return getVisibleIdWidth(true);
  }

  /**
   * get current sequence ID panel width, or nominal value if panel were to be
   * displayed using default settings
   * 
   * @param onscreen
   *          indicate if the Id width for onscreen or offscreen display should
   *          be returned
   * @return
   */
  int getVisibleIdWidth(boolean onscreen)
  {
    // see if rendering offscreen - check preferences and calc width accordingly
    if (!onscreen && Cache.getDefault("FIGURE_AUTOIDWIDTH", false))
    {
      return calculateIdWidth(-1).width + 4;
    }
    Integer idwidth = null;
    if (onscreen
            || (idwidth = Cache.getIntegerProperty("FIGURE_FIXEDIDWIDTH")) == null)
    {
      return (idPanel.getWidth() > 0 ? idPanel.getWidth()
              : calculateIdWidth().width + 4);
    }
    return idwidth.intValue() + 4;
  }

  void makeAlignmentImage(int type, File file)
  {
    long progress = System.currentTimeMillis();
    if (alignFrame != null)
    {
      alignFrame.setProgressBar("Saving "
              + (type == jalview.util.ImageMaker.PNG ? "PNG image"
                      : "EPS file"), progress);
    }
    try
    {
      int maxwidth = av.getAlignment().getWidth();
      if (av.hasHiddenColumns())
      {
        maxwidth = av.getColumnSelection().findColumnPosition(maxwidth);
      }

      int height = ((av.getAlignment().getHeight() + 1) * av.charHeight)
              + scalePanel.getHeight();
      int width = getVisibleIdWidth(false) + (maxwidth * av.charWidth);

      if (av.getWrapAlignment())
      {
        height = getWrappedHeight();
        if (System.getProperty("java.awt.headless") != null
                && System.getProperty("java.awt.headless").equals("true"))
        {
          // need to obtain default alignment width and then add in any
          // additional allowance for id margin
          // this duplicates the calculation in getWrappedHeight but adjusts for
          // offscreen idWith
          width = alignFrame.getWidth() - vscroll.getPreferredSize().width
                  - alignFrame.getInsets().left
                  - alignFrame.getInsets().right - getVisibleIdWidth()
                  + getVisibleIdWidth(false);
        }
        else
        {
          width = seqPanel.getWidth() + getVisibleIdWidth(false);
        }

      }
      else if (av.getShowAnnotation())
      {
        height += annotationPanel.adjustPanelHeight() + 3;
      }

      try
      {

        jalview.util.ImageMaker im;
        final String imageAction, imageTitle;
        if (type == jalview.util.ImageMaker.PNG)
        {
          imageAction = "Create PNG image from alignment";
          imageTitle = null;
        }
        else
        {
          imageAction = "Create EPS file from alignment";
          imageTitle = alignFrame.getTitle();
        }
        im = new jalview.util.ImageMaker(this, type, imageAction, width,
                height, file, imageTitle);
        if (av.getWrapAlignment())
        {
          if (im.getGraphics() != null)
          {
            printWrappedAlignment(im.getGraphics(), width, height, 0);
            im.writeImage();
          }
        }
        else
        {
          if (im.getGraphics() != null)
          {
            printUnwrapped(im.getGraphics(), width, height, 0);
            im.writeImage();
          }
        }
      } catch (OutOfMemoryError err)
      {
        // Be noisy here.
        System.out.println("########################\n" + "OUT OF MEMORY "
                + file + "\n" + "########################");
        new OOMWarning("Creating Image for " + file, err);
        // System.out.println("Create IMAGE: " + err);
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    } finally
    {
      if (alignFrame != null)
      {
        alignFrame.setProgressBar("Export complete.", progress);
      }
    }
  }

  /**
   * DOCUMENT ME!
   */
  public void makeEPS(File epsFile)
  {
    makeAlignmentImage(jalview.util.ImageMaker.EPS, epsFile);
  }

  /**
   * DOCUMENT ME!
   */
  public void makePNG(File pngFile)
  {
    makeAlignmentImage(jalview.util.ImageMaker.PNG, pngFile);
  }

  public void makePNGImageMap(File imgMapFile, String imageName)
  {
    // /////ONLY WORKS WITH NONE WRAPPED ALIGNMENTS
    // ////////////////////////////////////////////
    int idWidth = getVisibleIdWidth(false);
    FontMetrics fm = getFontMetrics(av.getFont());
    int scaleHeight = av.charHeight + fm.getDescent();

    // Gen image map
    // ////////////////////////////////
    if (imgMapFile != null)
    {
      try
      {
        int s, sSize = av.getAlignment().getHeight(), res, alwidth = av
                .getAlignment().getWidth(), g, gSize, f, fSize, sy;
        StringBuffer text = new StringBuffer();
        PrintWriter out = new PrintWriter(new FileWriter(imgMapFile));
        out.println(jalview.io.HTMLOutput.getImageMapHTML());
        out.println("<img src=\"" + imageName
                + "\" border=\"0\" usemap=\"#Map\" >"
                + "<map name=\"Map\">");

        for (s = 0; s < sSize; s++)
        {
          sy = s * av.charHeight + scaleHeight;

          SequenceI seq = av.getAlignment().getSequenceAt(s);
          SequenceFeature[] features = seq.getDatasetSequence()
                  .getSequenceFeatures();
          SequenceGroup[] groups = av.getAlignment().findAllGroups(seq);
          for (res = 0; res < alwidth; res++)
          {
            text = new StringBuffer();
            Object obj = null;
            if (av.getAlignment().isNucleotide())
            {
              obj = ResidueProperties.nucleotideName.get(seq.getCharAt(res)
                      + "");
            }
            else
            {
              obj = ResidueProperties.aa2Triplet.get(seq.getCharAt(res)
                      + "");
            }

            if (obj == null)
            {
              continue;
            }

            String triplet = obj.toString();
            int alIndex = seq.findPosition(res);
            gSize = groups.length;
            for (g = 0; g < gSize; g++)
            {
              if (text.length() < 1)
              {
                text.append("<area shape=\"rect\" coords=\""
                        + (idWidth + res * av.charWidth) + "," + sy + ","
                        + (idWidth + (res + 1) * av.charWidth) + ","
                        + (av.charHeight + sy) + "\""
                        + " onMouseOver=\"toolTip('" + alIndex + " "
                        + triplet);
              }

              if (groups[g].getStartRes() < res
                      && groups[g].getEndRes() > res)
              {
                text.append("<br><em>" + groups[g].getName() + "</em>");
              }
            }

            if (features != null)
            {
              if (text.length() < 1)
              {
                text.append("<area shape=\"rect\" coords=\""
                        + (idWidth + res * av.charWidth) + "," + sy + ","
                        + (idWidth + (res + 1) * av.charWidth) + ","
                        + (av.charHeight + sy) + "\""
                        + " onMouseOver=\"toolTip('" + alIndex + " "
                        + triplet);
              }
              fSize = features.length;
              for (f = 0; f < fSize; f++)
              {

                if ((features[f].getBegin() <= seq.findPosition(res))
                        && (features[f].getEnd() >= seq.findPosition(res)))
                {
                  if (features[f].getType().equals("disulfide bond"))
                  {
                    if (features[f].getBegin() == seq.findPosition(res)
                            || features[f].getEnd() == seq
                                    .findPosition(res))
                    {
                      text.append("<br>disulfide bond "
                              + features[f].getBegin() + ":"
                              + features[f].getEnd());
                    }
                  }
                  else
                  {
                    text.append("<br>");
                    text.append(features[f].getType());
                    if (features[f].getDescription() != null
                            && !features[f].getType().equals(
                                    features[f].getDescription()))
                    {
                      text.append(" " + features[f].getDescription());
                    }

                    if (features[f].getValue("status") != null)
                    {
                      text.append(" (" + features[f].getValue("status")
                              + ")");
                    }
                  }
                }

              }
            }
            if (text.length() > 1)
            {
              text.append("')\"; onMouseOut=\"toolTip()\";  href=\"#\">");
              out.println(text.toString());
            }
          }
        }
        out.println("</map></body></html>");
        out.close();

      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    } // /////////END OF IMAGE MAP

  }

  int getWrappedHeight()
  {
    int seqPanelWidth = seqPanel.seqCanvas.getWidth();

    if (System.getProperty("java.awt.headless") != null
            && System.getProperty("java.awt.headless").equals("true"))
    {
      seqPanelWidth = alignFrame.getWidth() - getVisibleIdWidth()
              - vscroll.getPreferredSize().width
              - alignFrame.getInsets().left - alignFrame.getInsets().right;
    }

    int chunkWidth = seqPanel.seqCanvas
            .getWrappedCanvasWidth(seqPanelWidth);

    int hgap = av.charHeight;
    if (av.scaleAboveWrapped)
    {
      hgap += av.charHeight;
    }

    int annotationHeight = 0;
    if (av.showAnnotation)
    {
      annotationHeight = annotationPanel.adjustPanelHeight();
    }

    int cHeight = av.getAlignment().getHeight() * av.charHeight + hgap
            + annotationHeight;

    int maxwidth = av.getAlignment().getWidth();
    if (av.hasHiddenColumns())
    {
      maxwidth = av.getColumnSelection().findColumnPosition(maxwidth) - 1;
    }

    int height = ((maxwidth / chunkWidth) + 1) * cHeight;

    return height;
  }

  /**
   * close the panel - deregisters all listeners and nulls any references to
   * alignment data.
   */
  public void closePanel()
  {
    PaintRefresher.RemoveComponent(seqPanel.seqCanvas);
    PaintRefresher.RemoveComponent(idPanel.idCanvas);
    PaintRefresher.RemoveComponent(this);
    if (av != null)
    {
      jalview.structure.StructureSelectionManager ssm = av
              .getStructureSelectionManager();
      ssm.removeStructureViewerListener(seqPanel, null);
      ssm.removeSelectionListener(seqPanel);
      av.setAlignment(null);
      av = null;
    }
    else
    {
      if (Cache.log.isDebugEnabled())
      {
        Cache.log.warn("Closing alignment panel which is already closed.");
      }
    }
  }

  /**
   * hides or shows dynamic annotation rows based on groups and av state flags
   */
  public void updateAnnotation()
  {
    updateAnnotation(false, false);
  }

  public void updateAnnotation(boolean applyGlobalSettings)
  {
    updateAnnotation(applyGlobalSettings, false);
  }

  public void updateAnnotation(boolean applyGlobalSettings,
          boolean preserveNewGroupSettings)
  {
    av.updateGroupAnnotationSettings(applyGlobalSettings,
            preserveNewGroupSettings);
    adjustAnnotationHeight();
  }

  @Override
  public AlignmentI getAlignment()
  {
    return av.getAlignment();
  }

  /**
   * get the name for this view
   * 
   * @return
   */
  public String getViewName()
  {
    return av.viewName;
  }

  /**
   * Make/Unmake this alignment panel the current input focus
   * 
   * @param b
   */
  public void setSelected(boolean b)
  {
    try
    {
      alignFrame.setSelected(b);
    } catch (Exception ex)
    {
    }
    ;

    if (b)
    {
      alignFrame.setDisplayedView(this);
    }
  }

  @Override
  public StructureSelectionManager getStructureSelectionManager()
  {
    return av.getStructureSelectionManager();
  }

  @Override
  public void raiseOOMWarning(String string, OutOfMemoryError error)
  {
    new OOMWarning(string, error, this);
  }

  public FeatureRenderer cloneFeatureRenderer()
  {

    return new FeatureRenderer(this);
  }

  public void updateFeatureRenderer(FeatureRenderer fr)
  {
    fr.transferSettings(seqPanel.seqCanvas.getFeatureRenderer());
  }

  public void updateFeatureRendererFrom(FeatureRenderer fr)
  {
    if (seqPanel.seqCanvas.getFeatureRenderer() != null)
    {
      seqPanel.seqCanvas.getFeatureRenderer().transferSettings(fr);
    }
  }
}
