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
import java.awt.image.*;
import javax.swing.*;

import jalview.datamodel.*;
import jalview.renderer.AnnotationRenderer;
import jalview.renderer.AwtRenderPanelI;

/**
 * AnnotationPanel displays visible portion of annotation rows below unwrapped
 * alignment
 * 
 * @author $author$
 * @version $Revision$
 */
public class AnnotationPanel extends JPanel implements AwtRenderPanelI,
        MouseListener, MouseWheelListener, MouseMotionListener,
        ActionListener, AdjustmentListener, Scrollable
{
  final String HELIX = "Helix";

  final String SHEET = "Sheet";

  /**
   * For RNA secondary structure "stems" aka helices
   */
  final String STEM = "RNA Helix";

  final String LABEL = "Label";

  final String REMOVE = "Remove Annotation";

  final String COLOUR = "Colour";

  public final Color HELIX_COLOUR = Color.red.darker();

  public final Color SHEET_COLOUR = Color.green.darker().darker();

  public final Color STEM_COLOUR = Color.blue.darker();

  /** DOCUMENT ME!! */
  public AlignViewport av;

  AlignmentPanel ap;

  public int activeRow = -1;

  public BufferedImage image;

  public volatile BufferedImage fadedImage;

  Graphics2D gg;

  public FontMetrics fm;

  public int imgWidth = 0;

  boolean fastPaint = false;

  // Used For mouse Dragging and resizing graphs
  int graphStretch = -1;

  int graphStretchY = -1;

  int min; // used by mouseDragged to see if user

  int max; // used by mouseDragged to see if user

  boolean mouseDragging = false;

  boolean MAC = false;

  // for editing cursor
  int cursorX = 0;

  int cursorY = 0;

  public final AnnotationRenderer renderer;

  private MouseWheelListener[] _mwl;

  /**
   * Creates a new AnnotationPanel object.
   * 
   * @param ap
   *          DOCUMENT ME!
   */
  public AnnotationPanel(AlignmentPanel ap)
  {

    MAC = new jalview.util.Platform().isAMac();

    ToolTipManager.sharedInstance().registerComponent(this);
    ToolTipManager.sharedInstance().setInitialDelay(0);
    ToolTipManager.sharedInstance().setDismissDelay(10000);
    this.ap = ap;
    av = ap.av;
    this.setLayout(null);
    addMouseListener(this);
    addMouseMotionListener(this);
    ap.annotationScroller.getVerticalScrollBar()
            .addAdjustmentListener(this);
    // save any wheel listeners on the scroller, so we can propagate scroll
    // events to them.
    _mwl = ap.annotationScroller.getMouseWheelListeners();
    // and then set our own listener to consume all mousewheel events
    ap.annotationScroller.addMouseWheelListener(this);
    renderer = new AnnotationRenderer();
  }

  public AnnotationPanel(AlignViewport av)
  {
    this.av = av;
    renderer = new AnnotationRenderer();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e)
  {
    if (e.isShiftDown())
    {
      e.consume();
      if (e.getWheelRotation() > 0)
      {
        ap.scrollRight(true);
      }
      else
      {
        ap.scrollRight(false);
      }
    }
    else
    {
      // TODO: find the correct way to let the event bubble up to
      // ap.annotationScroller
      for (MouseWheelListener mwl : _mwl)
      {
        if (mwl != null)
        {
          mwl.mouseWheelMoved(e);
        }
        if (e.isConsumed())
        {
          break;
        }
      }
    }
  }

  @Override
  public Dimension getPreferredScrollableViewportSize()
  {
    return getPreferredSize();
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect,
          int orientation, int direction)
  {
    return 30;
  }

  @Override
  public boolean getScrollableTracksViewportHeight()
  {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportWidth()
  {
    return true;
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect,
          int orientation, int direction)
  {
    return 30;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event
   * .AdjustmentEvent)
   */
  @Override
  public void adjustmentValueChanged(AdjustmentEvent evt)
  {
    // update annotation label display
    ap.alabels.setScrollOffset(-evt.getValue());
  }

  /**
   * Calculates the height of the annotation displayed in the annotation panel.
   * Callers should normally call the ap.adjustAnnotationHeight method to ensure
   * all annotation associated components are updated correctly.
   * 
   */
  public int adjustPanelHeight()
  {
    int height = av.calcPanelHeight();
    this.setPreferredSize(new Dimension(1, height));
    if (ap != null)
    {
      // revalidate only when the alignment panel is fully constructed
      ap.validate();
    }

    return height;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void actionPerformed(ActionEvent evt)
  {
    AlignmentAnnotation[] aa = av.getAlignment().getAlignmentAnnotation();
    if (aa == null)
    {
      return;
    }
    Annotation[] anot = aa[activeRow].annotations;

    if (anot.length < av.getColumnSelection().getMax())
    {
      Annotation[] temp = new Annotation[av.getColumnSelection().getMax() + 2];
      System.arraycopy(anot, 0, temp, 0, anot.length);
      anot = temp;
      aa[activeRow].annotations = anot;
    }

    if (evt.getActionCommand().equals(REMOVE))
    {
      for (int i = 0; i < av.getColumnSelection().size(); i++)
      {
        anot[av.getColumnSelection().columnAt(i)] = null;
      }
    }
    else if (evt.getActionCommand().equals(LABEL))
    {
      String exMesg = collectAnnotVals(anot, av.getColumnSelection(), LABEL);
      String label = JOptionPane.showInputDialog(this, "Enter label",
              exMesg);

      if (label == null)
      {
        return;
      }

      if ((label.length() > 0) && !aa[activeRow].hasText)
      {
        aa[activeRow].hasText = true;
      }

      for (int i = 0; i < av.getColumnSelection().size(); i++)
      {
        int index = av.getColumnSelection().columnAt(i);

        if (!av.getColumnSelection().isVisible(index))
          continue;

        if (anot[index] == null)
        {
          anot[index] = new Annotation(label, "", ' ', 0); // TODO: verify that
          // null exceptions
          // aren't raised
          // elsewhere.
        }
        else
        {
          anot[index].displayCharacter = label;
        }
      }
    }
    else if (evt.getActionCommand().equals(COLOUR))
    {
      Color col = JColorChooser.showDialog(this,
              "Choose foreground colour", Color.black);

      for (int i = 0; i < av.getColumnSelection().size(); i++)
      {
        int index = av.getColumnSelection().columnAt(i);

        if (!av.getColumnSelection().isVisible(index))
          continue;

        if (anot[index] == null)
        {
          anot[index] = new Annotation("", "", ' ', 0);
        }

        anot[index].colour = col;
      }
    }
    else
    // HELIX OR SHEET
    {
      char type = 0;
      String symbol = "\u03B1";

      if (evt.getActionCommand().equals(HELIX))
      {
        type = 'H';
      }
      else if (evt.getActionCommand().equals(SHEET))
      {
        type = 'E';
        symbol = "\u03B2";
      }

      // Added by LML to color stems
      else if (evt.getActionCommand().equals(STEM))
      {
        type = 'S';
        symbol = "\u03C3";
      }

      if (!aa[activeRow].hasIcons)
      {
        aa[activeRow].hasIcons = true;
      }

      String label = JOptionPane.showInputDialog(
              "Enter a label for the structure?", symbol);

      if (label == null)
      {
        return;
      }

      if ((label.length() > 0) && !aa[activeRow].hasText)
      {
        aa[activeRow].hasText = true;
      }

      for (int i = 0; i < av.getColumnSelection().size(); i++)
      {
        int index = av.getColumnSelection().columnAt(i);

        if (!av.getColumnSelection().isVisible(index))
          continue;

        if (anot[index] == null)
        {
          anot[index] = new Annotation(label, "", type, 0);
        }

        anot[index].secondaryStructure = type;
        anot[index].displayCharacter = label;
      }
    }
    aa[activeRow].validateRangeAndDisplay();

    adjustPanelHeight();
    ap.alignmentChanged();
    repaint();

    return;
  }

  private String collectAnnotVals(Annotation[] anot,
          ColumnSelection columnSelection, String label2)
  {
    String collatedInput = "";
    String last = "";
    ColumnSelection viscols = av.getColumnSelection();
    // TODO: refactor and save av.getColumnSelection for efficiency
    for (int i = 0; i < columnSelection.size(); i++)
    {
      int index = columnSelection.columnAt(i);
      // always check for current display state - just in case
      if (!viscols.isVisible(index))
        continue;
      String tlabel = null;
      if (anot[index] != null)
      { // LML added stem code
        if (label2.equals(HELIX) || label2.equals(SHEET)
                || label2.equals(STEM) || label2.equals(LABEL))
        {
          tlabel = anot[index].description;
          if (tlabel == null || tlabel.length() < 1)
          {
            if (label2.equals(HELIX) || label2.equals(SHEET)
                    || label2.equals(STEM))
            {
              tlabel = "" + anot[index].secondaryStructure;
            }
            else
            {
              tlabel = "" + anot[index].displayCharacter;
            }
          }
        }
        if (tlabel != null && !tlabel.equals(last))
        {
          if (last.length() > 0)
          {
            collatedInput += " ";
          }
          collatedInput += tlabel;
        }
      }
    }
    return collatedInput;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void mousePressed(MouseEvent evt)
  {

    AlignmentAnnotation[] aa = av.getAlignment().getAlignmentAnnotation();
    if (aa == null)
    {
      return;
    }

    int height = 0;
    activeRow = -1;

    for (int i = 0; i < aa.length; i++)
    {
      if (aa[i].visible)
      {
        height += aa[i].height;
      }

      if (evt.getY() < height)
      {
        if (aa[i].editable)
        {
          activeRow = i;
        }
        else if (aa[i].graph > 0)
        {
          // Stretch Graph
          graphStretch = i;
          graphStretchY = evt.getY();
        }

        break;
      }
    }

    if (SwingUtilities.isRightMouseButton(evt) && activeRow != -1)
    {
      if (av.getColumnSelection() == null)
      {
        return;
      }

      JPopupMenu pop = new JPopupMenu("Structure type");
      JMenuItem item;
      /*
       * Just display the needed structure options
       */
      if (av.getAlignment().isNucleotide() == true)
      {
        item = new JMenuItem(STEM);
        item.addActionListener(this);
        pop.add(item);
      }
      else
      {
        item = new JMenuItem(HELIX);
        item.addActionListener(this);
        pop.add(item);
        item = new JMenuItem(SHEET);
        item.addActionListener(this);
        pop.add(item);
      }
      item = new JMenuItem(LABEL);
      item.addActionListener(this);
      pop.add(item);
      item = new JMenuItem(COLOUR);
      item.addActionListener(this);
      pop.add(item);
      item = new JMenuItem(REMOVE);
      item.addActionListener(this);
      pop.add(item);
      pop.show(this, evt.getX(), evt.getY());

      return;
    }

    if (aa == null)
    {
      return;
    }

    ap.scalePanel.mousePressed(evt);

  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void mouseReleased(MouseEvent evt)
  {
    graphStretch = -1;
    graphStretchY = -1;
    mouseDragging = false;
    ap.scalePanel.mouseReleased(evt);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void mouseEntered(MouseEvent evt)
  {
    ap.scalePanel.mouseEntered(evt);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void mouseExited(MouseEvent evt)
  {
    ap.scalePanel.mouseExited(evt);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void mouseDragged(MouseEvent evt)
  {
    if (graphStretch > -1)
    {
      av.getAlignment().getAlignmentAnnotation()[graphStretch].graphHeight += graphStretchY
              - evt.getY();
      if (av.getAlignment().getAlignmentAnnotation()[graphStretch].graphHeight < 0)
      {
        av.getAlignment().getAlignmentAnnotation()[graphStretch].graphHeight = 0;
      }
      graphStretchY = evt.getY();
      adjustPanelHeight();
      ap.paintAlignment(true);
    }
    else
    {
      ap.scalePanel.mouseDragged(evt);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void mouseMoved(MouseEvent evt)
  {
    AlignmentAnnotation[] aa = av.getAlignment().getAlignmentAnnotation();

    if (aa == null)
    {
      this.setToolTipText(null);
      return;
    }

    int row = -1;
    int height = 0;

    for (int i = 0; i < aa.length; i++)
    {
      if (aa[i].visible)
      {
        height += aa[i].height;
      }

      if (evt.getY() < height)
      {
        row = i;

        break;
      }
    }

    if (row == -1)
    {
      this.setToolTipText(null);
      return;
    }

    int res = (evt.getX() / av.getCharWidth()) + av.getStartRes();

    if (av.hasHiddenColumns())
    {
      res = av.getColumnSelection().adjustForHiddenColumns(res);
    }

    if (row > -1 && aa[row].annotations != null
            && res < aa[row].annotations.length)
    {
      if (aa[row].graphGroup > -1)
      {
        StringBuffer tip = new StringBuffer("<html>");
        for (int gg = 0; gg < aa.length; gg++)
        {
          if (aa[gg].graphGroup == aa[row].graphGroup
                  && aa[gg].annotations[res] != null)
          {
            tip.append(aa[gg].label + " "
                    + aa[gg].annotations[res].description + "<br>");
          }
        }
        if (tip.length() != 6)
        {
          tip.setLength(tip.length() - 4);
          this.setToolTipText(tip.toString() + "</html>");
        }
      }
      else if (aa[row].annotations[res] != null
              && aa[row].annotations[res].description != null
              && aa[row].annotations[res].description.length() > 0)
      {
        this.setToolTipText(aa[row].annotations[res].description);
      }
      else
      {
        // clear the tooltip.
        this.setToolTipText(null);
      }

      if (aa[row].annotations[res] != null)
      {
        StringBuffer text = new StringBuffer("Sequence position "
                + (res + 1));

        if (aa[row].annotations[res].description != null)
        {
          text.append("  " + aa[row].annotations[res].description);
        }

        ap.alignFrame.statusBar.setText(text.toString());
      }
    }
    else
    {
      this.setToolTipText(null);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void mouseClicked(MouseEvent evt)
  {
    if (activeRow != -1)
    {
      AlignmentAnnotation[] aa = av.getAlignment().getAlignmentAnnotation();
      AlignmentAnnotation anot = aa[activeRow];

      if (anot.description.equals("secondary structure"))
      {
        // System.out.println(anot.description+" "+anot.getRNAStruc());
      }
    }
  }

  // TODO mouseClicked-content and drawCursor are quite experimental!
  public void drawCursor(Graphics graphics, SequenceI seq, int res, int x1,
          int y1)
  {
    int pady = av.charHeight / 5;
    int charOffset = 0;
    graphics.setColor(Color.black);
    graphics.fillRect(x1, y1, av.charWidth, av.charHeight);

    if (av.validCharWidth)
    {
      graphics.setColor(Color.white);

      char s = seq.getCharAt(res);

      charOffset = (av.charWidth - fm.charWidth(s)) / 2;
      graphics.drawString(String.valueOf(s), charOffset + x1,
              (y1 + av.charHeight) - pady);
    }

  }

  private volatile boolean imageFresh = false;

  /**
   * DOCUMENT ME!
   * 
   * @param g
   *          DOCUMENT ME!
   */
  @Override
  public void paintComponent(Graphics g)
  {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    if (image != null)
    {
      if (fastPaint || (getVisibleRect().width != g.getClipBounds().width)
              || (getVisibleRect().height != g.getClipBounds().height))
      {
        g.drawImage(image, 0, 0, this);
        fastPaint = false;
        return;
      }
    }
    imgWidth = (av.endRes - av.startRes + 1) * av.charWidth;
    if (imgWidth < 1)
      return;
    if (image == null || imgWidth != image.getWidth(this)
            || image.getHeight(this) != getHeight())
    {
      try
      {
        image = new BufferedImage(imgWidth, ap.annotationPanel.getHeight(),
                BufferedImage.TYPE_INT_RGB);
      } catch (OutOfMemoryError oom)
      {
        try
        {
          System.gc();
        } catch (Exception x)
        {
        }
        ;
        new OOMWarning(
                "Couldn't allocate memory to redraw screen. Please restart Jalview",
                oom);
        return;
      }
      gg = (Graphics2D) image.getGraphics();

      if (av.antiAlias)
      {
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
      }

      gg.setFont(av.getFont());
      fm = gg.getFontMetrics();
      gg.setColor(Color.white);
      gg.fillRect(0, 0, imgWidth, image.getHeight());
      imageFresh = true;
    }

    drawComponent(gg, av.startRes, av.endRes + 1);
    imageFresh = false;
    g.drawImage(image, 0, 0, this);
  }
  /**
   * set true to enable redraw timing debug output on stderr
   */
  private final boolean debugRedraw = false;
  /**
   * non-Thread safe repaint
   * 
   * @param horizontal
   *          repaint with horizontal shift in alignment
   */
  public void fastPaint(int horizontal)
  {
    if ((horizontal == 0) || gg == null
            || av.getAlignment().getAlignmentAnnotation() == null
            || av.getAlignment().getAlignmentAnnotation().length < 1
            || av.isCalcInProgress())
    {
      repaint();
      return;
    }
    long stime=System.currentTimeMillis();
    gg.copyArea(0, 0, imgWidth, getHeight(), -horizontal * av.charWidth, 0);
    long mtime=System.currentTimeMillis();
    int sr = av.startRes;
    int er = av.endRes + 1;
    int transX = 0;

    if (horizontal > 0) // scrollbar pulled right, image to the left
    {
      transX = (er - sr - horizontal) * av.charWidth;
      sr = er - horizontal;
    }
    else if (horizontal < 0)
    {
      er = sr - horizontal;
    }

    gg.translate(transX, 0);

    drawComponent(gg, sr, er);

    gg.translate(-transX, 0);
    long dtime=System.currentTimeMillis();
    fastPaint = true;
    repaint();
    long rtime=System.currentTimeMillis();
    if (debugRedraw) {
      System.err.println("Scroll:\t"+horizontal+"\tCopyArea:\t"+(mtime-stime)+"\tDraw component:\t"+(dtime-mtime)+"\tRepaint call:\t"+(rtime-dtime));
    }

  }

  private volatile boolean lastImageGood = false;

  /**
   * DOCUMENT ME!
   * 
   * @param g
   *          DOCUMENT ME!
   * @param startRes
   *          DOCUMENT ME!
   * @param endRes
   *          DOCUMENT ME!
   */
  public void drawComponent(Graphics g, int startRes, int endRes)
  {
    BufferedImage oldFaded = fadedImage;
    if (av.isCalcInProgress())
    {
      if (image == null)
      {
        lastImageGood = false;
        return;
      }
      // We'll keep a record of the old image,
      // and draw a faded image until the calculation
      // has completed
      if (lastImageGood
              && (fadedImage == null || fadedImage.getWidth() != imgWidth || fadedImage
                      .getHeight() != image.getHeight()))
      {
        // System.err.println("redraw faded image ("+(fadedImage==null ?
        // "null image" : "") + " lastGood="+lastImageGood+")");
        fadedImage = new BufferedImage(imgWidth, image.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D fadedG = (Graphics2D) fadedImage.getGraphics();

        fadedG.setColor(Color.white);
        fadedG.fillRect(0, 0, imgWidth, image.getHeight());

        fadedG.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, .3f));
        fadedG.drawImage(image, 0, 0, this);

      }
      // make sure we don't overwrite the last good faded image until all
      // calculations have finished
      lastImageGood = false;

    }
    else
    {
      if (fadedImage != null)
      {
        oldFaded = fadedImage;
      }
      fadedImage = null;
    }
    
    g.setColor(Color.white);
    g.fillRect(0, 0, (endRes - startRes) * av.charWidth, getHeight());

    g.setFont(av.getFont());
    if (fm == null)
    {
      fm = g.getFontMetrics();
    }

    if ((av.getAlignment().getAlignmentAnnotation() == null)
            || (av.getAlignment().getAlignmentAnnotation().length < 1))
    {
      g.setColor(Color.white);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(Color.black);
      if (av.validCharWidth)
      {
        g.drawString("Alignment has no annotations", 20, 15);
      }

      return;
    }
    lastImageGood = renderer.drawComponent(this, av, g, activeRow,
            startRes, endRes);
    if (!lastImageGood && fadedImage == null)
    {
      fadedImage = oldFaded;
    }
  }

  @Override
  public FontMetrics getFontMetrics()
  {
    return fm;
  }

  @Override
  public Image getFadedImage()
  {
    return fadedImage;
  }

  @Override
  public int getFadedImageWidth()
  {
    return imgWidth;
  }
  private int[] bounds = new int[2];
  @Override
  public int[] getVisibleVRange()
  {
    if (ap!=null && ap.alabels!=null)
    {
    int sOffset=-ap.alabels.scrollOffset;
    int visHeight = sOffset+ap.annotationSpaceFillerHolder.getHeight();
    bounds[0] = sOffset; bounds[1]=visHeight;
    return bounds;
    } else return null;
  }
}
