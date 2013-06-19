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

import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.schemes.ColourSchemeI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class SequenceRenderer implements jalview.api.SequenceRenderer
{
  AlignViewport av;

  FontMetrics fm;

  boolean renderGaps = true;

  SequenceGroup currentSequenceGroup = null;

  SequenceGroup[] allGroups = null;

  Color resBoxColour;

  Graphics graphics;

  boolean monospacedFont;

  boolean forOverview = false;

  /**
   * Creates a new SequenceRenderer object.
   * 
   * @param av
   *          DOCUMENT ME!
   */
  public SequenceRenderer(AlignViewport av)
  {
    this.av = av;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param b
   *          DOCUMENT ME!
   */
  public void prepare(Graphics g, boolean renderGaps)
  {
    graphics = g;
    fm = g.getFontMetrics();

    // If EPS graphics, stringWidth will be a double, not an int
    double dwidth = fm.getStringBounds("M", g).getWidth();

    monospacedFont = (dwidth == fm.getStringBounds("|", g).getWidth() && (float) av.charWidth == dwidth);

    this.renderGaps = renderGaps;
  }

  public Color getResidueBoxColour(SequenceI seq, int i)
  {
    allGroups = av.getAlignment().findAllGroups(seq);

    if (inCurrentSequenceGroup(i))
    {
      if (currentSequenceGroup.getDisplayBoxes())
      {
        getBoxColour(currentSequenceGroup.cs, seq, i);
      }
    }
    else if (av.getShowBoxes())
    {
      getBoxColour(av.getGlobalColourScheme(), seq, i);
    }

    return resBoxColour;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param cs
   *          DOCUMENT ME!
   * @param seq
   *          DOCUMENT ME!
   * @param i
   *          DOCUMENT ME!
   */
  void getBoxColour(ColourSchemeI cs, SequenceI seq, int i)
  {
    if (cs != null)
    {
      resBoxColour = cs.findColour(seq.getCharAt(i), i, seq);
    }
    else if (forOverview
            && !jalview.util.Comparison.isGap(seq.getCharAt(i)))
    {
      resBoxColour = Color.lightGray;
    }
    else
    {
      resBoxColour = Color.white;
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param g
   *          DOCUMENT ME!
   * @param seq
   *          DOCUMENT ME!
   * @param sg
   *          DOCUMENT ME!
   * @param start
   *          DOCUMENT ME!
   * @param end
   *          DOCUMENT ME!
   * @param x1
   *          DOCUMENT ME!
   * @param y1
   *          DOCUMENT ME!
   * @param width
   *          DOCUMENT ME!
   * @param height
   *          DOCUMENT ME!
   */
  public void drawSequence(SequenceI seq, SequenceGroup[] sg, int start,
          int end, int y1)
  {
    allGroups = sg;

    drawBoxes(seq, start, end, y1);

    if (av.validCharWidth)
    {
      drawText(seq, start, end, y1);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param seq
   *          DOCUMENT ME!
   * @param start
   *          DOCUMENT ME!
   * @param end
   *          DOCUMENT ME!
   * @param x1
   *          DOCUMENT ME!
   * @param y1
   *          DOCUMENT ME!
   * @param width
   *          DOCUMENT ME!
   * @param height
   *          DOCUMENT ME!
   */
  public synchronized void drawBoxes(SequenceI seq, int start, int end,
          int y1)
  {
    if (seq == null)
      return; // fix for racecondition
    int i = start;
    int length = seq.getLength();

    int curStart = -1;
    int curWidth = av.charWidth;

    Color tempColour = null;

    while (i <= end)
    {
      resBoxColour = Color.white;

      if (i < length)
      {
        if (inCurrentSequenceGroup(i))
        {
          if (currentSequenceGroup.getDisplayBoxes())
          {
            getBoxColour(currentSequenceGroup.cs, seq, i);
          }
        }
        else if (av.getShowBoxes())
        {
          getBoxColour(av.getGlobalColourScheme(), seq, i);
        }

      }

      if (resBoxColour != tempColour)
      {
        if (tempColour != null)
        {
          graphics.fillRect(av.charWidth * (curStart - start), y1,
                  curWidth, av.charHeight);
        }

        graphics.setColor(resBoxColour);

        curStart = i;
        curWidth = av.charWidth;
        tempColour = resBoxColour;
      }
      else
      {
        curWidth += av.charWidth;
      }

      i++;
    }

    graphics.fillRect(av.charWidth * (curStart - start), y1, curWidth,
            av.charHeight);

  }

  /**
   * DOCUMENT ME!
   * 
   * @param seq
   *          DOCUMENT ME!
   * @param start
   *          DOCUMENT ME!
   * @param end
   *          DOCUMENT ME!
   * @param x1
   *          DOCUMENT ME!
   * @param y1
   *          DOCUMENT ME!
   * @param width
   *          DOCUMENT ME!
   * @param height
   *          DOCUMENT ME!
   */
  public void drawText(SequenceI seq, int start, int end, int y1)
  {
    y1 += av.charHeight - av.charHeight / 5; // height/5 replaces pady
    int charOffset = 0;
    char s;

    if (end + 1 >= seq.getLength())
    {
      end = seq.getLength() - 1;
    }
    graphics.setColor(av.textColour);

    if (monospacedFont && av.showText && allGroups.length == 0
            && !av.getColourText() && av.thresholdTextColour == 0)
    {
      if (av.renderGaps)
      {
        graphics.drawString(seq.getSequenceAsString(start, end + 1), 0, y1);
      }
      else
      {
        char gap = av.getGapCharacter();
        graphics.drawString(seq.getSequenceAsString(start, end + 1)
                .replace(gap, ' '), 0, y1);
      }
    }
    else
    {
      boolean getboxColour = false;
      for (int i = start; i <= end; i++)
      {
        graphics.setColor(av.textColour);
        getboxColour = false;
        s = seq.getCharAt(i);
        if (!renderGaps && jalview.util.Comparison.isGap(s))
        {
          continue;
        }

        if (inCurrentSequenceGroup(i))
        {
          if (!currentSequenceGroup.getDisplayText())
          {
            continue;
          }

          if (currentSequenceGroup.thresholdTextColour > 0
                  || currentSequenceGroup.getColourText())
          {
            getboxColour = true;
            getBoxColour(currentSequenceGroup.cs, seq, i);

            if (currentSequenceGroup.getColourText())
            {
              graphics.setColor(resBoxColour.darker());
            }

            if (currentSequenceGroup.thresholdTextColour > 0)
            {
              if (resBoxColour.getRed() + resBoxColour.getBlue()
                      + resBoxColour.getGreen() < currentSequenceGroup.thresholdTextColour)
              {
                graphics.setColor(currentSequenceGroup.textColour2);
              }
            }
          }
          else
          {
            graphics.setColor(currentSequenceGroup.textColour);
          }
          if (currentSequenceGroup.getShowNonconserved()) // todo optimize
          {
            // todo - use sequence group consensus
            s = getDisplayChar(av.getAlignmentConsensusAnnotation(), i, s,
                    '.');

          }

        }
        else
        {
          if (!av.getShowText())
          {
            continue;
          }

          if (av.getColourText())
          {
            getboxColour = true;
            getBoxColour(av.getGlobalColourScheme(), seq, i);

            if (av.getShowBoxes())
            {
              graphics.setColor(resBoxColour.darker());
            }
            else
            {
              graphics.setColor(resBoxColour);
            }
          }

          if (av.thresholdTextColour > 0)
          {
            if (!getboxColour)
            {
              getBoxColour(av.getGlobalColourScheme(), seq, i);
            }

            if (resBoxColour.getRed() + resBoxColour.getBlue()
                    + resBoxColour.getGreen() < av.thresholdTextColour)
            {
              graphics.setColor(av.textColour2);
            }
          }
          if (av.getShowUnconserved())
          {
            s = getDisplayChar(av.getAlignmentConsensusAnnotation(), i, s,
                    '.');

          }

        }

        charOffset = (av.charWidth - fm.charWidth(s)) / 2;
        graphics.drawString(String.valueOf(s), charOffset + av.charWidth
                * (i - start), y1);

      }
    }
  }

  private char getDisplayChar(AlignmentAnnotation consensus, int position,
          char s, char c)
  {
    char conschar = consensus.annotations[position].displayCharacter
            .charAt(0);
    if (conschar != '-' && s == conschar)
    {
      s = c;
    }
    return s;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param res
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  boolean inCurrentSequenceGroup(int res)
  {
    if (allGroups == null)
    {
      return false;
    }

    for (int i = 0; i < allGroups.length; i++)
    {
      if ((allGroups[i].getStartRes() <= res)
              && (allGroups[i].getEndRes() >= res))
      {
        currentSequenceGroup = allGroups[i];

        return true;
      }
    }

    return false;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param seq
   *          DOCUMENT ME!
   * @param start
   *          DOCUMENT ME!
   * @param end
   *          DOCUMENT ME!
   * @param x1
   *          DOCUMENT ME!
   * @param y1
   *          DOCUMENT ME!
   * @param width
   *          DOCUMENT ME!
   * @param height
   *          DOCUMENT ME!
   */
  public void drawHighlightedText(SequenceI seq, int start, int end,
          int x1, int y1)
  {
    int pady = av.charHeight / 5;
    int charOffset = 0;
    graphics.setColor(Color.BLACK);
    graphics.fillRect(x1, y1, av.charWidth * (end - start + 1),
            av.charHeight);
    graphics.setColor(Color.white);

    char s = '~';

    // Need to find the sequence position here.
    if (av.validCharWidth)
    {
      for (int i = start; i <= end; i++)
      {
        if (i < seq.getLength())
        {
          s = seq.getCharAt(i);
        }

        charOffset = (av.charWidth - fm.charWidth(s)) / 2;
        graphics.drawString(String.valueOf(s), charOffset + x1
                + (av.charWidth * (i - start)), (y1 + av.charHeight) - pady);
      }
    }
  }

  public void drawCursor(SequenceI seq, int res, int x1, int y1)
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
}
