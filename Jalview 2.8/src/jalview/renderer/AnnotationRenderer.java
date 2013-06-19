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
package jalview.renderer;

import jalview.analysis.AAFrequency;
import jalview.analysis.StructureFrequency;
import jalview.api.AlignViewportI;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.Annotation;
import jalview.datamodel.ColumnSelection;
import jalview.schemes.ColourSchemeI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.BitSet;
import java.util.Hashtable;

import com.stevesoft.pat.Regex;

public class AnnotationRenderer
{
  /**
   * flag indicating if timing and redraw parameter info should be output
   */
  private final boolean debugRedraw;

  public AnnotationRenderer()
  {
    this(false);
  }
  /**
   * Create a new annotation Renderer
   * @param debugRedraw flag indicating if timing and redraw parameter info should be output
   */
  public AnnotationRenderer(boolean debugRedraw)
  {
    this.debugRedraw=debugRedraw;
  }

  public void drawStemAnnot(Graphics g, Annotation[] row_annotations,
          int lastSSX, int x, int y, int iconOffset, int startRes,
          int column, boolean validRes, boolean validEnd)
  {
    g.setColor(STEM_COLOUR);
    int sCol = (lastSSX / charWidth) + startRes;
    int x1 = lastSSX;
    int x2 = (x * charWidth);
    Regex closeparen = new Regex("(\\))");

    String dc = (column == 0 || row_annotations[column - 1] == null) ? ""
            : row_annotations[column - 1].displayCharacter;

    boolean diffupstream = sCol == 0 || row_annotations[sCol - 1] == null
            || !dc.equals(row_annotations[sCol - 1].displayCharacter);
    boolean diffdownstream = !validRes || !validEnd
            || row_annotations[column] == null
            || !dc.equals(row_annotations[column].displayCharacter);
    // System.out.println("Column "+column+" diff up: "+diffupstream+" down:"+diffdownstream);
    // If a closing base pair half of the stem, display a backward arrow
    if (column > 0 && closeparen.search(dc))
    {
      if (diffupstream)
      // if (validRes && column>1 && row_annotations[column-2]!=null &&
      // dc.equals(row_annotations[column-2].displayCharacter))
      {
        g.fillPolygon(new int[]
        { lastSSX + 5, lastSSX + 5, lastSSX }, new int[]
        { y + iconOffset, y + 14 + iconOffset, y + 8 + iconOffset }, 3);
        x1 += 5;
      }
      if (diffdownstream)
      {
        x2 -= 1;
      }
    }
    else
    {
      // display a forward arrow
      if (diffdownstream)
      {
        g.fillPolygon(new int[]
        { x2 - 5, x2 - 5, x2 }, new int[]
        { y + iconOffset, y + 14 + iconOffset, y + 8 + iconOffset }, 3);
        x2 -= 5;
      }
      if (diffupstream)
      {
        x1 += 1;
      }
    }
    // draw arrow body
    g.fillRect(x1, y + 4 + iconOffset, x2 - x1, 7);
  }

  private int charWidth, endRes, charHeight;

  private boolean validCharWidth, hasHiddenColumns;

  private FontMetrics fm;

  private final boolean MAC = new jalview.util.Platform().isAMac();

  boolean av_renderHistogram = true, av_renderProfile = true,
          av_normaliseProfile = false;

  ColourSchemeI profcolour = null;

  private ColumnSelection columnSelection;

  private Hashtable[] hconsensus;

  private Hashtable[] hStrucConsensus;

  private boolean av_ignoreGapsConsensus;

  /**
   * attributes set from AwtRenderPanelI
   */
  /**
   * old image used when data is currently being calculated and cannot be
   * rendered
   */
  private Image fadedImage;

  /**
   * panel being rendered into
   */
  private ImageObserver annotationPanel;

  /**
   * width of image to render in panel
   */
  private int imgWidth;
  /**
   * offset to beginning of visible area
   */
  private int sOffset;
  /**
   * offset to end of visible area
   */
  private int visHeight;
  /**
   * indicate if the renderer should only render the visible portion of the annotation given the current view settings
   */
  private boolean useClip=true;
  /**
   * master flag indicating if renderer should ever try to clip. not enabled for jalview 2.8.1 
   */
  private boolean canClip=false;
  
  // public void updateFromAnnotationPanel(FontMetrics annotFM, AlignViewportI
  // av)
  public void updateFromAwtRenderPanel(AwtRenderPanelI annotPanel,
          AlignViewportI av)
  {
    fm = annotPanel.getFontMetrics();
    annotationPanel = annotPanel;
    fadedImage = annotPanel.getFadedImage();
    imgWidth = annotPanel.getFadedImageWidth();
    // visible area for rendering
    int[] bounds=annotPanel.getVisibleVRange();
    if (bounds!=null)
    {
      sOffset = bounds[0];
      visHeight = bounds[1];
      if (visHeight==0)
      {
        useClip=false;
      } else {
        useClip=canClip;
      }
    } else {
      useClip=false;
    }

    updateFromAlignViewport(av);
  }

  public void updateFromAlignViewport(AlignViewportI av)
  {
    charWidth = av.getCharWidth();
    endRes = av.getEndRes();
    charHeight = av.getCharHeight();
    hasHiddenColumns = av.hasHiddenColumns();
    validCharWidth = av.isValidCharWidth();
    av_renderHistogram = av.isShowConsensusHistogram();
    av_renderProfile = av.isShowSequenceLogo();
    av_normaliseProfile = av.isNormaliseSequenceLogo();
    profcolour = av.getGlobalColourScheme();
    if (profcolour == null)
    {
      // Set the default colour for sequence logo if the alignnent has no
      // colourscheme set
      profcolour = av.getAlignment().isNucleotide() ? new jalview.schemes.NucleotideColourScheme()
              : new jalview.schemes.ZappoColourScheme();
    }
    columnSelection = av.getColumnSelection();
    hconsensus = av.getSequenceConsensusHash();// hconsensus;
    hStrucConsensus = av.getRnaStructureConsensusHash(); // hStrucConsensus;
    av_ignoreGapsConsensus = av.getIgnoreGapsConsensus();
  }

  public int[] getProfileFor(AlignmentAnnotation aa, int column)
  {
    // TODO : consider refactoring the global alignment calculation
    // properties/rendering attributes as a global 'alignment group' which holds
    // all vis settings for the alignment as a whole rather than a subset
    //
    if (aa.autoCalculated && aa.label.startsWith("Consensus"))
    {
      if (aa.groupRef != null && aa.groupRef.consensusData != null
              && aa.groupRef.isShowSequenceLogo())
      {
        return AAFrequency.extractProfile(
                aa.groupRef.consensusData[column],
                aa.groupRef.getIgnoreGapsConsensus());
      }
      // TODO extend annotation row to enable dynamic and static profile data to
      // be stored
      if (aa.groupRef == null && aa.sequenceRef == null && av_renderProfile)
      {
        return AAFrequency.extractProfile(hconsensus[column],
                av_ignoreGapsConsensus);
      }
    }
    else
    {
      if (aa.autoCalculated && aa.label.startsWith("StrucConsensus"))
      {
        // TODO implement group structure consensus
        /*
         * if (aa.groupRef != null && aa.groupRef.consensusData != null &&
         * aa.groupRef.isShowSequenceLogo()) { //TODO check what happens for
         * group selections return StructureFrequency.extractProfile(
         * aa.groupRef.consensusData[column], aa.groupRef
         * .getIgnoreGapsConsensus()); }
         */
        // TODO extend annotation row to enable dynamic and static profile data
        // to
        // be stored
        if (aa.groupRef == null && aa.sequenceRef == null
                && av_renderProfile && hStrucConsensus != null
                && hStrucConsensus.length > column)
        {
          return StructureFrequency.extractProfile(hStrucConsensus[column],
                  av_ignoreGapsConsensus);
        }
      }
    }
    return null;
  }

  /**
   * Render the annotation rows associated with an alignment.
   * 
   * @param annotPanel
   *          container frame
   * @param av
   *          data and view settings to render
   * @param g
   *          destination for graphics
   * @param activeRow
   *          row where a mouse event occured (or -1)
   * @param startRes
   *          first column that will be drawn
   * @param endRes
   *          last column that will be drawn
   * @return true if the fadedImage was used for any alignment annotation rows
   *         currently being calculated
   */
  public boolean drawComponent(AwtRenderPanelI annotPanel,
          AlignViewportI av, Graphics g, int activeRow, int startRes,
          int endRes)
  {
    long stime=System.currentTimeMillis();
    boolean usedFaded = false;
    // NOTES:
    // AnnotationPanel needs to implement: ImageObserver, access to
    // AlignViewport
    updateFromAwtRenderPanel(annotPanel, av);
    fm = g.getFontMetrics();
    AlignmentAnnotation[] aa = av.getAlignment().getAlignmentAnnotation();
    if (aa==null)
    {
      return false;
    }
    int x = 0, y = 0;
    int column = 0;
    char lastSS;
    int lastSSX;
    int iconOffset = 0;
    boolean validRes = false;
    boolean validEnd = false;
    boolean labelAllCols = false;
    boolean centreColLabels, centreColLabelsDef = av
            .getCentreColumnLabels();
    boolean scaleColLabel = false;

    BitSet graphGroupDrawn = new BitSet();
    int charOffset = 0; // offset for a label
    float fmWidth, fmScaling = 1f; // scaling for a label to fit it into a
    // column.
    Font ofont = g.getFont();
    // \u03B2 \u03B1
    // debug ints
    int yfrom=0,f_i=0,yto=0,f_to=0;
    boolean clipst=false,clipend=false;
    for (int i = 0; i < aa.length; i++)
    {
      AlignmentAnnotation row = aa[i];
      Annotation[] row_annotations = row.annotations;
      if (!row.visible)
      {
        continue;
      }
      centreColLabels = row.centreColLabels || centreColLabelsDef;
      labelAllCols = row.showAllColLabels;
      scaleColLabel = row.scaleColLabel;
      lastSS = ' ';
      lastSSX = 0;
      
      if (!useClip || ((y-charHeight)<visHeight && (y+row.height+charHeight*2)>=sOffset)) 
      {// if_in_visible_region
        if (!clipst)
        {
          clipst=true;
          yfrom=y;
          f_i=i;
        }
        yto = y;
        f_to=i;
      if (row.graph > 0)
      {
        if (row.graphGroup > -1 && graphGroupDrawn.get(row.graphGroup)) {
          continue;
        }

        // this is so that we draw the characters below the graph
        y += row.height;

        if (row.hasText)
        {
          iconOffset = charHeight - fm.getDescent();
          y -= charHeight;
        }
      }
      else if (row.hasText)
      {
        iconOffset = charHeight - fm.getDescent();

      }
      else
      {
        iconOffset = 0;
      }

      if (row.autoCalculated && av.isCalculationInProgress(row))
      {
        y += charHeight;
        usedFaded = true;
        g.drawImage(fadedImage, 0, y - row.height, imgWidth, y, 0, y
                - row.height, imgWidth, y, annotationPanel);
        g.setColor(Color.black);
        // g.drawString("Calculating "+aa[i].label+"....",20, y-row.height/2);

        continue;
      }

      /*
       * else if (annotationPanel.av.updatingConservation &&
       * aa[i].label.equals("Conservation")) {
       * 
       * y += charHeight; g.drawImage(annotationPanel.fadedImage, 0, y -
       * row.height, annotationPanel.imgWidth, y, 0, y - row.height,
       * annotationPanel.imgWidth, y, annotationPanel);
       * 
       * g.setColor(Color.black); //
       * g.drawString("Calculating Conservation.....",20, y-row.height/2);
       * 
       * continue; } else if (annotationPanel.av.updatingConservation &&
       * aa[i].label.equals("Quality")) {
       * 
       * y += charHeight; g.drawImage(annotationPanel.fadedImage, 0, y -
       * row.height, annotationPanel.imgWidth, y, 0, y - row.height,
       * annotationPanel.imgWidth, y, annotationPanel); g.setColor(Color.black);
       * // / g.drawString("Calculating Quality....",20, y-row.height/2);
       * 
       * continue; }
       */
      // first pass sets up state for drawing continuation from left-hand column
      // of startRes
      x = (startRes == 0) ? 0 : -1;
      while (x < endRes - startRes)
      {
        if (hasHiddenColumns)
        {
          column = columnSelection.adjustForHiddenColumns(startRes + x);
          if (column > row_annotations.length - 1)
          {
            break;
          }
        }
        else
        {
          column = startRes + x;
        }

        if ((row_annotations == null) || (row_annotations.length <= column)
                || (row_annotations[column] == null))
        {
          validRes = false;
        }
        else
        {
          validRes = true;
        }
        if (x > -1)
        {
          if (activeRow == i)
          {
            g.setColor(Color.red);

            if (columnSelection != null)
            {
              for (int n = 0; n < columnSelection.size(); n++)
              {
                int v = columnSelection.columnAt(n);

                if (v == column)
                {
                  g.fillRect(x * charWidth, y, charWidth, charHeight);
                }
              }
            }
          }
          if (!row.isValidStruc())
          {
            g.setColor(Color.orange);
            g.fillRect((int) row.getInvalidStrucPos() * charWidth, y,
                    charWidth, charHeight);
          }
          if (validCharWidth
                  && validRes
                  && row_annotations[column].displayCharacter != null
                  && (row_annotations[column].displayCharacter.length() > 0))
          {

            if (centreColLabels || scaleColLabel)
            {
              fmWidth = fm.charsWidth(
                      row_annotations[column].displayCharacter
                              .toCharArray(), 0,
                      row_annotations[column].displayCharacter.length());

              if (scaleColLabel)
              {
                // justify the label and scale to fit in column
                if (fmWidth > charWidth)
                {
                  // scale only if the current font isn't already small enough
                  fmScaling = charWidth;
                  fmScaling /= fmWidth;
                  g.setFont(ofont.deriveFont(AffineTransform
                          .getScaleInstance(fmScaling, 1.0)));
                  // and update the label's width to reflect the scaling.
                  fmWidth = charWidth;
                }
              }
            }
            else
            {
              fmWidth = fm
                      .charWidth(row_annotations[column].displayCharacter
                              .charAt(0));
            }
            charOffset = (int) ((charWidth - fmWidth) / 2f);

            if (row_annotations[column].colour == null)
              g.setColor(Color.black);
            else
              g.setColor(row_annotations[column].colour);

            if (column == 0 || row.graph > 0)
            {
              g.drawString(row_annotations[column].displayCharacter,
                      (x * charWidth) + charOffset, y + iconOffset);
            }
            else if (row_annotations[column - 1] == null
                    || (labelAllCols
                            || !row_annotations[column].displayCharacter
                                    .equals(row_annotations[column - 1].displayCharacter) || (row_annotations[column].displayCharacter
                            .length() < 2 && row_annotations[column].secondaryStructure == ' ')))
            {
              g.drawString(row_annotations[column].displayCharacter, x
                      * charWidth + charOffset, y + iconOffset);
            }
            g.setFont(ofont);
          }
        }
        if (row.hasIcons)
        {
          char ss = validRes ? row_annotations[column].secondaryStructure
                  : ' ';
          if (ss == 'S')
          {
            // distinguish between forward/backward base-pairing
            if (row_annotations[column].displayCharacter.indexOf(')') > -1)
            {
              ss = 's';
            }
          }
          if (!validRes || (ss != lastSS))
          {
            if (x > -1)
            {
              switch (lastSS)
              {
              case 'H':
                drawHelixAnnot(g, row_annotations, lastSSX, x, y,
                        iconOffset, startRes, column, validRes, validEnd);
                break;

              case 'E':
                drawSheetAnnot(g, row_annotations, lastSSX, x, y,
                        iconOffset, startRes, column, validRes, validEnd);
                break;

              case 'S': // Stem case for RNA secondary structure
              case 's': // and opposite direction
                drawStemAnnot(g, row_annotations, lastSSX, x, y,
                        iconOffset, startRes, column, validRes, validEnd);
                break;

              default:
                g.setColor(Color.gray);
                g.fillRect(lastSSX, y + 6 + iconOffset, (x * charWidth)
                        - lastSSX, 2);

                break;
              }
            }
            if (validRes)
            {
              lastSS = ss;
            }
            else
            {
              lastSS = ' ';
            }
            if (x > -1)
            {
              lastSSX = (x * charWidth);
            }
          }
        }
        column++;
        x++;
      }
      if (column >= row_annotations.length)
      {
        column = row_annotations.length - 1;
        validEnd = false;
      }
      else
      {
        validEnd = true;
      }
      if ((row_annotations == null) || (row_annotations.length <= column)
              || (row_annotations[column] == null))
      {
        validRes = false;
      }
      else
      {
        validRes = true;
      }

      // x ++;

      if (row.hasIcons)
      {
        switch (lastSS)
        {
        case 'H':
          drawHelixAnnot(g, row_annotations, lastSSX, x, y, iconOffset,
                  startRes, column, validRes, validEnd);
          break;

        case 'E':
          drawSheetAnnot(g, row_annotations, lastSSX, x, y, iconOffset,
                  startRes, column, validRes, validEnd);
          break;
        case 's':
        case 'S': // Stem case for RNA secondary structure
          drawStemAnnot(g, row_annotations, lastSSX, x, y, iconOffset,
                  startRes, column, validRes, validEnd);
          break;
        default:
          drawGlyphLine(g, row_annotations, lastSSX, x, y, iconOffset,
                  startRes, column, validRes, validEnd);
          break;
        }
      }

      if (row.graph > 0 && row.graphHeight > 0)
      {
        if (row.graph == AlignmentAnnotation.LINE_GRAPH)
        {
          if (row.graphGroup > -1 && !graphGroupDrawn.get(row.graphGroup))
          {
            // TODO: JAL-1291 revise rendering model so the graphGroup map is computed efficiently for all visible labels
            float groupmax = -999999, groupmin = 9999999;
            for (int gg = 0; gg < aa.length; gg++)
            {
              if (aa[gg].graphGroup != row.graphGroup)
              {
                continue;
              }

              if (aa[gg] != row)
              {
                aa[gg].visible = false;
              }
              if (aa[gg].graphMax > groupmax)
              {
                groupmax = aa[gg].graphMax;
              }
              if (aa[gg].graphMin < groupmin)
              {
                groupmin = aa[gg].graphMin;
              }
            }

            for (int gg = 0; gg < aa.length; gg++)
            {
              if (aa[gg].graphGroup == row.graphGroup)
              {
                drawLineGraph(g, aa[gg], aa[gg].annotations, startRes,
                        endRes, y, groupmin, groupmax, row.graphHeight);
              }
            }

            graphGroupDrawn.set(row.graphGroup);
          }
          else
          {
            drawLineGraph(g, row, row_annotations, startRes, endRes, y,
                    row.graphMin, row.graphMax, row.graphHeight);
          }
        }
        else if (row.graph == AlignmentAnnotation.BAR_GRAPH)
        {
          drawBarGraph(g, row, row_annotations, startRes, endRes,
                  row.graphMin, row.graphMax, y);
        }
      }
    } else {
      if (clipst && !clipend)
      {
        clipend = true;
      }
    }// end if_in_visible_region
      if (row.graph > 0 && row.hasText)
      {
        y += charHeight;
      }

      if (row.graph == 0)
      {
        y += aa[i].height;
      }
    }
    if (debugRedraw)
    {
      if (canClip)
      {
        if (clipst)
        {
          System.err.println("Start clip at : " + yfrom + " (index " + f_i
                  + ")");
        }
        if (clipend)
        {
          System.err.println("End clip at : " + yto + " (index " + f_to
                  + ")");
        }
      }
      ;
      System.err.println("Annotation Rendering time:"
              + (System.currentTimeMillis() - stime));
    }
    ;

    return !usedFaded;
  }

  private final Color GLYPHLINE_COLOR = Color.gray;

  private final Color SHEET_COLOUR = Color.green;

  private final Color HELIX_COLOUR = Color.red;

  private final Color STEM_COLOUR = Color.blue;

  public void drawGlyphLine(Graphics g, Annotation[] row, int lastSSX,
          int x, int y, int iconOffset, int startRes, int column,
          boolean validRes, boolean validEnd)
  {
    g.setColor(GLYPHLINE_COLOR);
    g.fillRect(lastSSX, y + 6 + iconOffset, (x * charWidth) - lastSSX, 2);
  }

  public void drawSheetAnnot(Graphics g, Annotation[] row, int lastSSX,
          int x, int y, int iconOffset, int startRes, int column,
          boolean validRes, boolean validEnd)
  {
    g.setColor(SHEET_COLOUR);

    if (!validEnd || !validRes || row == null || row[column] == null
            || row[column].secondaryStructure != 'E')
    {
      g.fillRect(lastSSX, y + 4 + iconOffset,
              (x * charWidth) - lastSSX - 4, 7);
      g.fillPolygon(new int[]
      { (x * charWidth) - 4, (x * charWidth) - 4, (x * charWidth) },
              new int[]
              { y + iconOffset, y + 14 + iconOffset, y + 7 + iconOffset },
              3);
    }
    else
    {
      g.fillRect(lastSSX, y + 4 + iconOffset,
              (x + 1) * charWidth - lastSSX, 7);
    }

  }

  public void drawHelixAnnot(Graphics g, Annotation[] row, int lastSSX,
          int x, int y, int iconOffset, int startRes, int column,
          boolean validRes, boolean validEnd)
  {
    g.setColor(HELIX_COLOUR);

    int sCol = (lastSSX / charWidth) + startRes;
    int x1 = lastSSX;
    int x2 = (x * charWidth);

    if (MAC)
    {
      int ofs = charWidth / 2;
      // Off by 1 offset when drawing rects and ovals
      // to offscreen image on the MAC
      g.fillRoundRect(lastSSX, y + 4 + iconOffset, x2 - x1, 8, 8, 8);
      if (sCol == 0 || row[sCol - 1] == null
              || row[sCol - 1].secondaryStructure != 'H')
      {
      }
      else
      {
        // g.setColor(Color.orange);
        g.fillRoundRect(lastSSX, y + 4 + iconOffset, x2 - x1 - ofs + 1, 8,
                0, 0);
      }
      if (!validRes || row[column] == null
              || row[column].secondaryStructure != 'H')
      {

      }
      else
      {
        // g.setColor(Color.magenta);
        g.fillRoundRect(lastSSX + ofs, y + 4 + iconOffset, x2 - x1 - ofs
                + 1, 8, 0, 0);

      }

      return;
    }

    if (sCol == 0 || row[sCol - 1] == null
            || row[sCol - 1].secondaryStructure != 'H')
    {
      g.fillArc(lastSSX, y + 4 + iconOffset, charWidth, 8, 90, 180);
      x1 += charWidth / 2;
    }

    if (!validRes || row[column] == null
            || row[column].secondaryStructure != 'H')
    {
      g.fillArc((x * charWidth) - charWidth, y + 4 + iconOffset, charWidth,
              8, 270, 180);
      x2 -= charWidth / 2;
    }

    g.fillRect(x1, y + 4 + iconOffset, x2 - x1, 8);
  }

  public void drawLineGraph(Graphics g, AlignmentAnnotation _aa,
          Annotation[] aa_annotations, int sRes, int eRes, int y,
          float min, float max, int graphHeight)
  {
    if (sRes > aa_annotations.length)
    {
      return;
    }

    int x = 0;

    // Adjustment for fastpaint to left
    if (eRes < endRes)
    {
      eRes++;
    }

    eRes = Math.min(eRes, aa_annotations.length);

    if (sRes == 0)
    {
      x++;
    }

    int y1 = y, y2 = y;
    float range = max - min;

    // //Draw origin
    if (min < 0)
    {
      y2 = y - (int) ((0 - min / range) * graphHeight);
    }

    g.setColor(Color.gray);
    g.drawLine(x - charWidth, y2, (eRes - sRes + 1) * charWidth, y2);

    eRes = Math.min(eRes, aa_annotations.length);

    int column;
    int aaMax = aa_annotations.length - 1;

    while (x < eRes - sRes)
    {
      column = sRes + x;
      if (hasHiddenColumns)
      {
        column = columnSelection.adjustForHiddenColumns(column);
      }

      if (column > aaMax)
      {
        break;
      }

      if (aa_annotations[column] == null
              || aa_annotations[column - 1] == null)
      {
        x++;
        continue;
      }

      if (aa_annotations[column].colour == null)
        g.setColor(Color.black);
      else
        g.setColor(aa_annotations[column].colour);

      y1 = y
              - (int) (((aa_annotations[column - 1].value - min) / range) * graphHeight);
      y2 = y
              - (int) (((aa_annotations[column].value - min) / range) * graphHeight);

      g.drawLine(x * charWidth - charWidth / 2, y1, x * charWidth
              + charWidth / 2, y2);
      x++;
    }

    if (_aa.threshold != null)
    {
      g.setColor(_aa.threshold.colour);
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE,
              BasicStroke.JOIN_ROUND, 3f, new float[]
              { 5f, 3f }, 0f));

      y2 = (int) (y - ((_aa.threshold.value - min) / range) * graphHeight);
      g.drawLine(0, y2, (eRes - sRes) * charWidth, y2);
      g2.setStroke(new BasicStroke());
    }
  }

  public void drawBarGraph(Graphics g, AlignmentAnnotation _aa,
          Annotation[] aa_annotations, int sRes, int eRes, float min,
          float max, int y)
  {
    if (sRes > aa_annotations.length)
    {
      return;
    }
    Font ofont = g.getFont();
    eRes = Math.min(eRes, aa_annotations.length);

    int x = 0, y1 = y, y2 = y;

    float range = max - min;

    if (min < 0)
    {
      y2 = y - (int) ((0 - min / (range)) * _aa.graphHeight);
    }

    g.setColor(Color.gray);

    g.drawLine(x, y2, (eRes - sRes) * charWidth, y2);

    int column;
    int aaMax = aa_annotations.length - 1;
    boolean renderHistogram = true, renderProfile = true, normaliseProfile = false;
    // if (aa.autoCalculated && aa.label.startsWith("Consensus"))
    {
      // TODO: generalise this to have render styles for consensus/profile data
      if (_aa.groupRef != null)
      {
        renderHistogram = _aa.groupRef.isShowConsensusHistogram();
        renderProfile = _aa.groupRef.isShowSequenceLogo();
        normaliseProfile = _aa.groupRef.isNormaliseSequenceLogo();
      }
      else
      {
        renderHistogram = av_renderHistogram;
        renderProfile = av_renderProfile;
        normaliseProfile = av_normaliseProfile;
      }
    }
    while (x < eRes - sRes)
    {
      column = sRes + x;
      if (hasHiddenColumns)
      {
        column = columnSelection.adjustForHiddenColumns(column);
      }

      if (column > aaMax)
      {
        break;
      }

      if (aa_annotations[column] == null)
      {
        x++;
        continue;
      }
      if (aa_annotations[column].colour == null)
        g.setColor(Color.black);
      else
        g.setColor(aa_annotations[column].colour);

      y1 = y
              - (int) (((aa_annotations[column].value - min) / (range)) * _aa.graphHeight);

      if (renderHistogram)
      {
        if (y1 - y2 > 0)
        {
          g.fillRect(x * charWidth, y2, charWidth, y1 - y2);
        }
        else
        {
          g.fillRect(x * charWidth, y1, charWidth, y2 - y1);
        }
      }
      // draw profile if available
      if (renderProfile)
      {

        int profl[] = getProfileFor(_aa, column);
        // just try to draw the logo if profl is not null
        if (profl != null && profl[1] != 0)
        {
          float ht = normaliseProfile ? y - _aa.graphHeight : y1;
          double htn = normaliseProfile ? _aa.graphHeight : (y2 - y1);// aa.graphHeight;
          double hght;
          float wdth;
          double ht2 = 0;
          char[] dc;

          /**
           * profl.length == 74 indicates that the profile of a secondary
           * structure conservation row was accesed. Therefore dc gets length 2,
           * to have space for a basepair instead of just a single nucleotide
           */
          if (profl.length == 74)
          {
            dc = new char[2];
          }
          else
          {
            dc = new char[1];
          }
          LineMetrics lm = g.getFontMetrics(ofont).getLineMetrics("Q", g);
          double scale = 1f / (normaliseProfile ? profl[1] : 100f);
          float ofontHeight = 1f / lm.getAscent();// magnify to fill box
          double scl = 0.0;
          for (int c = 2; c < profl[0];)
          {
            dc[0] = (char) profl[c++];

            if (_aa.label.startsWith("StrucConsensus"))
            {
              dc[1] = (char) profl[c++];
            }

            wdth = charWidth;
            wdth /= fm.charsWidth(dc, 0, dc.length);

            ht += scl;
            {
              scl = htn * scale * profl[c++];
              lm = ofont.getLineMetrics(dc, 0, 1, g.getFontMetrics()
                      .getFontRenderContext());
              g.setFont(ofont.deriveFont(AffineTransform.getScaleInstance(
                      wdth, scl / lm.getAscent())));
              lm = g.getFontMetrics().getLineMetrics(dc, 0, 1, g);

              // Debug - render boxes around characters
              // g.setColor(Color.red);
              // g.drawRect(x*av.charWidth, (int)ht, av.charWidth,
              // (int)(scl));
              // g.setColor(profcolour.findColour(dc[0]).darker());
              g.setColor(profcolour.findColour(dc[0], column, null));

              hght = (ht + (scl - lm.getDescent() - lm.getBaselineOffsets()[lm
                      .getBaselineIndex()]));

              g.drawChars(dc, 0, dc.length, x * charWidth, (int) hght);
            }
          }
          g.setFont(ofont);
        }
      }
      x++;
    }
    if (_aa.threshold != null)
    {
      g.setColor(_aa.threshold.colour);
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE,
              BasicStroke.JOIN_ROUND, 3f, new float[]
              { 5f, 3f }, 0f));

      y2 = (int) (y - ((_aa.threshold.value - min) / range)
              * _aa.graphHeight);
      g.drawLine(0, y2, (eRes - sRes) * charWidth, y2);
      g2.setStroke(new BasicStroke());
    }
  }

  // used by overview window
  public void drawGraph(Graphics g, AlignmentAnnotation _aa,
          Annotation[] aa_annotations, int width, int y, int sRes, int eRes)
  {
    eRes = Math.min(eRes, aa_annotations.length);
    g.setColor(Color.white);
    g.fillRect(0, 0, width, y);
    g.setColor(new Color(0, 0, 180));

    int x = 0, height;

    for (int j = sRes; j < eRes; j++)
    {
      if (aa_annotations[j] != null)
      {
        if (aa_annotations[j].colour == null)
          g.setColor(Color.black);
        else
          g.setColor(aa_annotations[j].colour);

        height = (int) ((aa_annotations[j].value / _aa.graphMax) * y);
        if (height > y)
        {
          height = y;
        }

        g.fillRect(x, y - height, charWidth, height);
      }
      x += charWidth;
    }
  }
}
