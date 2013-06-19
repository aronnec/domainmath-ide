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
import java.util.concurrent.ConcurrentHashMap;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import jalview.datamodel.*;
import jalview.schemes.GraduatedColor;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class FeatureRenderer implements jalview.api.FeatureRenderer
{
  AlignmentPanel ap;

  AlignViewport av;

  Color resBoxColour;

  /**
   * global transparency for feature
   */
  float transparency = 1.0f;

  FontMetrics fm;

  int charOffset;

  Map featureColours = new ConcurrentHashMap();

  // A higher level for grouping features of a
  // particular type
  Map featureGroups = new ConcurrentHashMap();

  // This is actually an Integer held in the hashtable,
  // Retrieved using the key feature type
  Object currentColour;

  String[] renderOrder;

  PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

  Vector allfeatures;

  /**
   * Creates a new FeatureRenderer object.
   * 
   * @param av
   *          DOCUMENT ME!
   */
  public FeatureRenderer(AlignmentPanel ap)
  {
    this.ap = ap;
    this.av = ap.av;
    if (ap != null && ap.seqPanel != null && ap.seqPanel.seqCanvas != null
            && ap.seqPanel.seqCanvas.fr != null)
    {
      transferSettings(ap.seqPanel.seqCanvas.fr);
    }
  }

  public class FeatureRendererSettings implements Cloneable
  {
    String[] renderOrder;

    Map featureGroups;

    Map featureColours;

    float transparency;

    Map featureOrder;

    public FeatureRendererSettings(String[] renderOrder,
            Hashtable featureGroups, Hashtable featureColours,
            float transparency, Hashtable featureOrder)
    {
      super();
      this.renderOrder = renderOrder;
      this.featureGroups = featureGroups;
      this.featureColours = featureColours;
      this.transparency = transparency;
      this.featureOrder = featureOrder;
    }

    /**
     * create an independent instance of the feature renderer settings
     * 
     * @param fr
     */
    public FeatureRendererSettings(FeatureRenderer fr)
    {
      renderOrder = null;
      featureGroups = new ConcurrentHashMap();
      featureColours = new ConcurrentHashMap();
      featureOrder = new ConcurrentHashMap();
      if (fr.renderOrder != null)
      {
        this.renderOrder = new String[fr.renderOrder.length];
        System.arraycopy(fr.renderOrder, 0, renderOrder, 0,
                fr.renderOrder.length);
      }
      if (fr.featureGroups != null)
      {
        this.featureGroups = new ConcurrentHashMap(fr.featureGroups);
      }
      if (fr.featureColours != null)
      {
        this.featureColours = new ConcurrentHashMap(fr.featureColours);
      }
      Iterator en = fr.featureColours.keySet().iterator();
      while (en.hasNext())
      {
        Object next = en.next();
        Object val = featureColours.get(next);
        if (val instanceof GraduatedColor)
        {
          featureColours
                  .put(next, new GraduatedColor((GraduatedColor) val));
        }
      }
      this.transparency = fr.transparency;
      if (fr.featureOrder != null)
      {
        this.featureOrder = new ConcurrentHashMap(fr.featureOrder);
      }
    }
  }

  public FeatureRendererSettings getSettings()
  {
    return new FeatureRendererSettings(this);
  }

  public void transferSettings(FeatureRendererSettings fr)
  {
    this.renderOrder = fr.renderOrder;
    this.featureGroups = fr.featureGroups;
    this.featureColours = fr.featureColours;
    this.transparency = fr.transparency;
    this.featureOrder = fr.featureOrder;
  }
  /**
   * update from another feature renderer
   * @param fr settings to copy
   */
  public void transferSettings(FeatureRenderer fr)
  {
    FeatureRendererSettings frs = new FeatureRendererSettings(fr);
    this.renderOrder = frs.renderOrder;
    this.featureGroups = frs.featureGroups;
    this.featureColours = frs.featureColours;
    this.transparency = frs.transparency;
    this.featureOrder = frs.featureOrder;
    if (av != null && av != fr.av)
    {
      // copy over the displayed feature settings
      if (fr.av != null)
      {
        if (fr.av.featuresDisplayed != null)
        {
          // update display settings
          if (av.featuresDisplayed == null)
          {
            av.featuresDisplayed = new Hashtable(fr.av.featuresDisplayed);
          }
          else
          {
            av.featuresDisplayed.clear();
            Enumeration en = fr.av.featuresDisplayed.keys();
            while (en.hasMoreElements())
            {
              av.featuresDisplayed.put(en.nextElement(), Boolean.TRUE);
            }

          }
        }
      }
    }
  }

  BufferedImage offscreenImage;

  boolean offscreenRender = false;

  public Color findFeatureColour(Color initialCol, SequenceI seq, int res)
  {
    return new Color(findFeatureColour(initialCol.getRGB(), seq, res));
  }

  /**
   * This is used by the Molecule Viewer and Overview to get the accurate
   * colourof the rendered sequence
   */
  public synchronized int findFeatureColour(int initialCol, SequenceI seq,
          int column)
  {
    if (!av.showSequenceFeatures)
    {
      return initialCol;
    }

    if (seq != lastSeq)
    {
      lastSeq = seq;
      sequenceFeatures = lastSeq.getDatasetSequence().getSequenceFeatures();
      if (sequenceFeatures != null)
      {
        sfSize = sequenceFeatures.length;
      }
    }

    if (sequenceFeatures != lastSeq.getDatasetSequence()
            .getSequenceFeatures())
    {
      sequenceFeatures = lastSeq.getDatasetSequence().getSequenceFeatures();
      if (sequenceFeatures != null)
      {
        sfSize = sequenceFeatures.length;
      }
    }

    if (sequenceFeatures == null || sfSize == 0)
    {
      return initialCol;
    }

    if (jalview.util.Comparison.isGap(lastSeq.getCharAt(column)))
    {
      return Color.white.getRGB();
    }

    // Only bother making an offscreen image if transparency is applied
    if (transparency != 1.0f && offscreenImage == null)
    {
      offscreenImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }

    currentColour = null;
    // TODO: non-threadsafe - each rendering thread needs its own instance of
    // the feature renderer - or this should be synchronized.
    offscreenRender = true;

    if (offscreenImage != null)
    {
      offscreenImage.setRGB(0, 0, initialCol);
      drawSequence(offscreenImage.getGraphics(), lastSeq, column, column, 0);

      return offscreenImage.getRGB(0, 0);
    }
    else
    {
      drawSequence(null, lastSeq, lastSeq.findPosition(column), -1, -1);

      if (currentColour == null)
      {
        return initialCol;
      }
      else
      {
        return ((Integer) currentColour).intValue();
      }
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
  // String type;
  // SequenceFeature sf;
  SequenceI lastSeq;

  SequenceFeature[] sequenceFeatures;

  int sfSize, sfindex, spos, epos;

  /**
   * show scores as heights
   */
  protected boolean varyHeight = false;

  synchronized public void drawSequence(Graphics g, SequenceI seq,
          int start, int end, int y1)
  {

    if (seq.getDatasetSequence().getSequenceFeatures() == null
            || seq.getDatasetSequence().getSequenceFeatures().length == 0)
    {
      return;
    }

    if (g != null)
    {
      fm = g.getFontMetrics();
    }

    if (av.featuresDisplayed == null || renderOrder == null
            || newFeatureAdded)
    {
      findAllFeatures();
      if (av.featuresDisplayed.size() < 1)
      {
        return;
      }

      sequenceFeatures = seq.getDatasetSequence().getSequenceFeatures();
    }

    if (lastSeq == null
            || seq != lastSeq
            || seq.getDatasetSequence().getSequenceFeatures() != sequenceFeatures)
    {
      lastSeq = seq;
      sequenceFeatures = seq.getDatasetSequence().getSequenceFeatures();
    }

    if (transparency != 1 && g != null)
    {
      Graphics2D g2 = (Graphics2D) g;
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
              transparency));
    }

    if (!offscreenRender)
    {
      spos = lastSeq.findPosition(start);
      epos = lastSeq.findPosition(end);
    }

    sfSize = sequenceFeatures.length;
    String type;
    for (int renderIndex = 0; renderIndex < renderOrder.length; renderIndex++)
    {
      type = renderOrder[renderIndex];

      if (type == null || !av.featuresDisplayed.containsKey(type))
      {
        continue;
      }

      // loop through all features in sequence to find
      // current feature to render
      for (sfindex = 0; sfindex < sfSize; sfindex++)
      {
        if (!sequenceFeatures[sfindex].type.equals(type))
        {
          continue;
        }

        if (featureGroups != null
                && sequenceFeatures[sfindex].featureGroup != null
                && sequenceFeatures[sfindex].featureGroup.length() != 0
                && featureGroups
                        .containsKey(sequenceFeatures[sfindex].featureGroup)
                && !((Boolean) featureGroups
                        .get(sequenceFeatures[sfindex].featureGroup))
                        .booleanValue())
        {
          continue;
        }

        if (!offscreenRender
                && (sequenceFeatures[sfindex].getBegin() > epos || sequenceFeatures[sfindex]
                        .getEnd() < spos))
        {
          continue;
        }

        if (offscreenRender && offscreenImage == null)
        {
          if (sequenceFeatures[sfindex].begin <= start
                  && sequenceFeatures[sfindex].end >= start)
          {
            // this is passed out to the overview and other sequence renderers
            // (e.g. molecule viewer) to get displayed colour for rendered
            // sequence
            currentColour = new Integer(
                    getColour(sequenceFeatures[sfindex]).getRGB());
            // used to be retreived from av.featuresDisplayed
            // currentColour = av.featuresDisplayed
            // .get(sequenceFeatures[sfindex].type);

          }
        }
        else if (sequenceFeatures[sfindex].type.equals("disulfide bond"))
        {

          renderFeature(g, seq,
                  seq.findIndex(sequenceFeatures[sfindex].begin) - 1,
                  seq.findIndex(sequenceFeatures[sfindex].begin) - 1,
                  getColour(sequenceFeatures[sfindex])
                  // new Color(((Integer) av.featuresDisplayed
                  // .get(sequenceFeatures[sfindex].type)).intValue())
                  , start, end, y1);
          renderFeature(g, seq,
                  seq.findIndex(sequenceFeatures[sfindex].end) - 1,
                  seq.findIndex(sequenceFeatures[sfindex].end) - 1,
                  getColour(sequenceFeatures[sfindex])
                  // new Color(((Integer) av.featuresDisplayed
                  // .get(sequenceFeatures[sfindex].type)).intValue())
                  , start, end, y1);

        }
        else if (showFeature(sequenceFeatures[sfindex]))
        {
          if (av.showSeqFeaturesHeight
                  && sequenceFeatures[sfindex].score != Float.NaN)
          {
            renderScoreFeature(g, seq,
                    seq.findIndex(sequenceFeatures[sfindex].begin) - 1,
                    seq.findIndex(sequenceFeatures[sfindex].end) - 1,
                    getColour(sequenceFeatures[sfindex]), start, end, y1,
                    normaliseScore(sequenceFeatures[sfindex]));
          }
          else
          {
            renderFeature(g, seq,
                    seq.findIndex(sequenceFeatures[sfindex].begin) - 1,
                    seq.findIndex(sequenceFeatures[sfindex].end) - 1,
                    getColour(sequenceFeatures[sfindex]), start, end, y1);
          }
        }

      }

    }

    if (transparency != 1.0f && g != null)
    {
      Graphics2D g2 = (Graphics2D) g;
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
              1.0f));
    }
  }

  Hashtable minmax = new Hashtable();

  /**
   * normalise a score against the max/min bounds for the feature type.
   * 
   * @param sequenceFeature
   * @return byte[] { signed, normalised signed (-127 to 127) or unsigned
   *         (0-255) value.
   */
  private final byte[] normaliseScore(SequenceFeature sequenceFeature)
  {
    float[] mm = ((float[][]) minmax.get(sequenceFeature.type))[0];
    final byte[] r = new byte[]
    { 0, (byte) 255 };
    if (mm != null)
    {
      if (r[0] != 0 || mm[0] < 0.0)
      {
        r[0] = 1;
        r[1] = (byte) ((int) 128.0 + 127.0 * (sequenceFeature.score / mm[1]));
      }
      else
      {
        r[1] = (byte) ((int) 255.0 * (sequenceFeature.score / mm[1]));
      }
    }
    return r;
  }

  char s;

  int i;

  void renderFeature(Graphics g, SequenceI seq, int fstart, int fend,
          Color featureColour, int start, int end, int y1)
  {

    if (((fstart <= end) && (fend >= start)))
    {
      if (fstart < start)
      { // fix for if the feature we have starts before the sequence start,
        fstart = start; // but the feature end is still valid!!
      }

      if (fend >= end)
      {
        fend = end;
      }
      int pady = (y1 + av.charHeight) - av.charHeight / 5;
      for (i = fstart; i <= fend; i++)
      {
        s = seq.getCharAt(i);

        if (jalview.util.Comparison.isGap(s))
        {
          continue;
        }

        g.setColor(featureColour);

        g.fillRect((i - start) * av.charWidth, y1, av.charWidth,
                av.charHeight);

        if (offscreenRender || !av.validCharWidth)
        {
          continue;
        }

        g.setColor(Color.white);
        charOffset = (av.charWidth - fm.charWidth(s)) / 2;
        g.drawString(String.valueOf(s), charOffset
                + (av.charWidth * (i - start)), pady);

      }
    }
  }

  void renderScoreFeature(Graphics g, SequenceI seq, int fstart, int fend,
          Color featureColour, int start, int end, int y1, byte[] bs)
  {

    if (((fstart <= end) && (fend >= start)))
    {
      if (fstart < start)
      { // fix for if the feature we have starts before the sequence start,
        fstart = start; // but the feature end is still valid!!
      }

      if (fend >= end)
      {
        fend = end;
      }
      int pady = (y1 + av.charHeight) - av.charHeight / 5;
      int ystrt = 0, yend = av.charHeight;
      if (bs[0] != 0)
      {
        // signed - zero is always middle of residue line.
        if (bs[1] < 128)
        {
          yend = av.charHeight * (128 - bs[1]) / 512;
          ystrt = av.charHeight - yend / 2;
        }
        else
        {
          ystrt = av.charHeight / 2;
          yend = av.charHeight * (bs[1] - 128) / 512;
        }
      }
      else
      {
        yend = av.charHeight * bs[1] / 255;
        ystrt = av.charHeight - yend;

      }
      for (i = fstart; i <= fend; i++)
      {
        s = seq.getCharAt(i);

        if (jalview.util.Comparison.isGap(s))
        {
          continue;
        }

        g.setColor(featureColour);
        int x = (i - start) * av.charWidth;
        g.drawRect(x, y1, av.charWidth, av.charHeight);
        g.fillRect(x, y1 + ystrt, av.charWidth, yend);

        if (offscreenRender || !av.validCharWidth)
        {
          continue;
        }

        g.setColor(Color.black);
        charOffset = (av.charWidth - fm.charWidth(s)) / 2;
        g.drawString(String.valueOf(s), charOffset
                + (av.charWidth * (i - start)), pady);

      }
    }
  }

  boolean newFeatureAdded = false;

  /**
   * Called when alignment in associated view has new/modified features to
   * discover and display.
   * 
   */
  public void featuresAdded()
  {
    lastSeq = null;
    findAllFeatures();
  }

  boolean findingFeatures = false;

  /**
   * search the alignment for all new features, give them a colour and display
   * them. Then fires a PropertyChangeEvent on the changeSupport object.
   * 
   */
  void findAllFeatures()
  {
    synchronized (firing)
    {
      if (firing.equals(Boolean.FALSE))
      {
        firing = Boolean.TRUE;
        findAllFeatures(true); // add all new features as visible
        changeSupport.firePropertyChange("changeSupport", null, null);
        firing = Boolean.FALSE;
      }
    }
  }

  /**
   * Searches alignment for all features and updates colours
   * 
   * @param newMadeVisible
   *          if true newly added feature types will be rendered immediatly
   */
  synchronized void findAllFeatures(boolean newMadeVisible)
  {
    newFeatureAdded = false;

    if (findingFeatures)
    {
      newFeatureAdded = true;
      return;
    }

    findingFeatures = true;

    if (av.featuresDisplayed == null)
    {
      av.featuresDisplayed = new Hashtable();
    }

    allfeatures = new Vector();
    Vector oldfeatures = new Vector();
    if (renderOrder != null)
    {
      for (int i = 0; i < renderOrder.length; i++)
      {
        if (renderOrder[i] != null)
        {
          oldfeatures.addElement(renderOrder[i]);
        }
      }
    }
    if (minmax == null)
    {
      minmax = new Hashtable();
    }
    AlignmentI alignment = av.getAlignment();
    for (int i = 0; i < alignment.getHeight(); i++)
    {
      SequenceFeature[] features = alignment.getSequenceAt(i)
              .getDatasetSequence().getSequenceFeatures();

      if (features == null)
      {
        continue;
      }

      int index = 0;
      while (index < features.length)
      {
        if (!av.featuresDisplayed.containsKey(features[index].getType()))
        {

          if (featureGroups.containsKey(features[index].getType()))
          {
            boolean visible = ((Boolean) featureGroups
                    .get(features[index].featureGroup)).booleanValue();

            if (!visible)
            {
              index++;
              continue;
            }
          }

          if (!(features[index].begin == 0 && features[index].end == 0))
          {
            // If beginning and end are 0, the feature is for the whole sequence
            // and we don't want to render the feature in the normal way

            if (newMadeVisible
                    && !oldfeatures.contains(features[index].getType()))
            {
              // this is a new feature type on the alignment. Mark it for
              // display.
              av.featuresDisplayed.put(features[index].getType(),
                      new Integer(getColour(features[index].getType())
                              .getRGB()));
              setOrder(features[index].getType(), 0);
            }
          }
        }
        if (!allfeatures.contains(features[index].getType()))
        {
          allfeatures.addElement(features[index].getType());
        }
        if (features[index].score != Float.NaN)
        {
          int nonpos = features[index].getBegin() >= 1 ? 0 : 1;
          float[][] mm = (float[][]) minmax.get(features[index].getType());
          if (mm == null)
          {
            mm = new float[][]
            { null, null };
            minmax.put(features[index].getType(), mm);
          }
          if (mm[nonpos] == null)
          {
            mm[nonpos] = new float[]
            { features[index].score, features[index].score };

          }
          else
          {
            if (mm[nonpos][0] > features[index].score)
            {
              mm[nonpos][0] = features[index].score;
            }
            if (mm[nonpos][1] < features[index].score)
            {
              mm[nonpos][1] = features[index].score;
            }
          }
        }
        index++;
      }
    }
    updateRenderOrder(allfeatures);
    findingFeatures = false;
  }

  protected Boolean firing = Boolean.FALSE;

  /**
   * replaces the current renderOrder with the unordered features in
   * allfeatures. The ordering of any types in both renderOrder and allfeatures
   * is preserved, and all new feature types are rendered on top of the existing
   * types, in the order given by getOrder or the order given in allFeatures.
   * Note. this operates directly on the featureOrder hash for efficiency. TODO:
   * eliminate the float storage for computing/recalling the persistent ordering
   * New Cability: updates min/max for colourscheme range if its dynamic
   * 
   * @param allFeatures
   */
  private void updateRenderOrder(Vector allFeatures)
  {
    Vector allfeatures = new Vector(allFeatures);
    String[] oldRender = renderOrder;
    renderOrder = new String[allfeatures.size()];
    Object mmrange, fc = null;
    boolean initOrders = (featureOrder == null);
    int opos = 0;
    if (oldRender != null && oldRender.length > 0)
    {
      for (int j = 0; j < oldRender.length; j++)
      {
        if (oldRender[j] != null)
        {
          if (initOrders)
          {
            setOrder(oldRender[j], (1 - (1 + (float) j)
                    / (float) oldRender.length));
          }
          if (allfeatures.contains(oldRender[j]))
          {
            renderOrder[opos++] = oldRender[j]; // existing features always
            // appear below new features
            allfeatures.removeElement(oldRender[j]);
            if (minmax != null)
            {
              mmrange = minmax.get(oldRender[j]);
              if (mmrange != null)
              {
                fc = featureColours.get(oldRender[j]);
                if (fc != null && fc instanceof GraduatedColor
                        && ((GraduatedColor) fc).isAutoScale())
                {
                  ((GraduatedColor) fc).updateBounds(
                          ((float[][]) mmrange)[0][0],
                          ((float[][]) mmrange)[0][1]);
                }
              }
            }
          }
        }
      }
    }
    if (allfeatures.size() == 0)
    {
      // no new features - leave order unchanged.
      return;
    }
    int i = allfeatures.size() - 1;
    int iSize = i;
    boolean sort = false;
    String[] newf = new String[allfeatures.size()];
    float[] sortOrder = new float[allfeatures.size()];
    Enumeration en = allfeatures.elements();
    // sort remaining elements
    while (en.hasMoreElements())
    {
      newf[i] = en.nextElement().toString();
      if (minmax != null)
      {
        // update from new features minmax if necessary
        mmrange = minmax.get(newf[i]);
        if (mmrange != null)
        {
          fc = featureColours.get(newf[i]);
          if (fc != null && fc instanceof GraduatedColor
                  && ((GraduatedColor) fc).isAutoScale())
          {
            ((GraduatedColor) fc).updateBounds(((float[][]) mmrange)[0][0],
                    ((float[][]) mmrange)[0][1]);
          }
        }
      }
      if (initOrders || !featureOrder.containsKey(newf[i]))
      {
        int denom = initOrders ? allfeatures.size() : featureOrder.size();
        // new unordered feature - compute persistent ordering at head of
        // existing features.
        setOrder(newf[i], i / (float) denom);
      }
      // set order from newly found feature from persisted ordering.
      sortOrder[i] = 2 - ((Float) featureOrder.get(newf[i])).floatValue();
      if (i < iSize)
      {
        // only sort if we need to
        sort = sort || sortOrder[i] > sortOrder[i + 1];
      }
      i--;
    }
    if (iSize > 1 && sort)
    {
      jalview.util.QuickSort.sort(sortOrder, newf);
    }
    sortOrder = null;
    System.arraycopy(newf, 0, renderOrder, opos, newf.length);
  }

  /**
   * get a feature style object for the given type string. Creates a
   * java.awt.Color for a featureType with no existing colourscheme. TODO:
   * replace return type with object implementing standard abstract colour/style
   * interface
   * 
   * @param featureType
   * @return java.awt.Color or GraduatedColor
   */
  public Object getFeatureStyle(String featureType)
  {
    Object fc = featureColours.get(featureType);
    if (fc == null)
    {
      jalview.schemes.UserColourScheme ucs = new jalview.schemes.UserColourScheme();
      Color col = ucs.createColourFromName(featureType);
      featureColours.put(featureType, fc = col);
    }
    return fc;
  }

  /**
   * return a nominal colour for this feature
   * 
   * @param featureType
   * @return standard color, or maximum colour for graduated colourscheme
   */
  public Color getColour(String featureType)
  {
    Object fc = getFeatureStyle(featureType);

    if (fc instanceof Color)
    {
      return (Color) fc;
    }
    else
    {
      if (fc instanceof GraduatedColor)
      {
        return ((GraduatedColor) fc).getMaxColor();
      }
    }
    throw new Error("Implementation Error: Unrecognised render object "
            + fc.getClass() + " for features of type " + featureType);
  }

  /**
   * calculate the render colour for a specific feature using current feature
   * settings.
   * 
   * @param feature
   * @return render colour for the given feature
   */
  public Color getColour(SequenceFeature feature)
  {
    Object fc = getFeatureStyle(feature.getType());
    if (fc instanceof Color)
    {
      return (Color) fc;
    }
    else
    {
      if (fc instanceof GraduatedColor)
      {
        return ((GraduatedColor) fc).findColor(feature);
      }
    }
    throw new Error("Implementation Error: Unrecognised render object "
            + fc.getClass() + " for features of type " + feature.getType());
  }

  private boolean showFeature(SequenceFeature sequenceFeature)
  {
    Object fc = getFeatureStyle(sequenceFeature.type);
    if (fc instanceof GraduatedColor)
    {
      return ((GraduatedColor) fc).isColored(sequenceFeature);
    }
    else
    {
      return true;
    }
  }

  // // /////////////
  // // Feature Editing Dialog
  // // Will be refactored in next release.

  static String lastFeatureAdded;

  static String lastFeatureGroupAdded;

  static String lastDescriptionAdded;

  Object oldcol, fcol;

  int featureIndex = 0;

  boolean amendFeatures(final SequenceI[] sequences,
          final SequenceFeature[] features, boolean newFeatures,
          final AlignmentPanel ap)
  {

    featureIndex = 0;

    final JPanel bigPanel = new JPanel(new BorderLayout());
    final JComboBox overlaps;
    final JTextField name = new JTextField(25);
    final JTextField source = new JTextField(25);
    final JTextArea description = new JTextArea(3, 25);
    final JSpinner start = new JSpinner();
    final JSpinner end = new JSpinner();
    start.setPreferredSize(new Dimension(80, 20));
    end.setPreferredSize(new Dimension(80, 20));
    final FeatureRenderer me = this;
    final JLabel colour = new JLabel();
    colour.setOpaque(true);
    // colour.setBorder(BorderFactory.createEtchedBorder());
    colour.setMaximumSize(new Dimension(30, 16));
    colour.addMouseListener(new MouseAdapter()
    {
      FeatureColourChooser fcc = null;

      public void mousePressed(MouseEvent evt)
      {
        if (fcol instanceof Color)
        {
          Color col = JColorChooser.showDialog(Desktop.desktop,
                  "Select Feature Colour", ((Color) fcol));
          if (col != null)
          {
            fcol = col;
            updateColourButton(bigPanel, colour, col);
          }
        }
        else
        {

          if (fcc == null)
          {
            final String type = features[featureIndex].getType();
            fcc = new FeatureColourChooser(me, type);
            fcc.setRequestFocusEnabled(true);
            fcc.requestFocus();

            fcc.addActionListener(new ActionListener()
            {

              public void actionPerformed(ActionEvent e)
              {
                fcol = fcc.getLastColour();
                fcc = null;
                setColour(type, fcol);
                updateColourButton(bigPanel, colour, fcol);
              }
            });

          }
        }
      }
    });
    JPanel tmp = new JPanel();
    JPanel panel = new JPanel(new GridLayout(3, 1));

    // /////////////////////////////////////
    // /MULTIPLE FEATURES AT SELECTED RESIDUE
    if (!newFeatures && features.length > 1)
    {
      panel = new JPanel(new GridLayout(4, 1));
      tmp = new JPanel();
      tmp.add(new JLabel("Select Feature: "));
      overlaps = new JComboBox();
      for (int i = 0; i < features.length; i++)
      {
        overlaps.addItem(features[i].getType() + "/"
                + features[i].getBegin() + "-" + features[i].getEnd()
                + " (" + features[i].getFeatureGroup() + ")");
      }

      tmp.add(overlaps);

      overlaps.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          int index = overlaps.getSelectedIndex();
          if (index != -1)
          {
            featureIndex = index;
            name.setText(features[index].getType());
            description.setText(features[index].getDescription());
            source.setText(features[index].getFeatureGroup());
            start.setValue(new Integer(features[index].getBegin()));
            end.setValue(new Integer(features[index].getEnd()));

            SearchResults highlight = new SearchResults();
            highlight.addResult(sequences[0], features[index].getBegin(),
                    features[index].getEnd());

            ap.seqPanel.seqCanvas.highlightSearchResults(highlight);

          }
          Object col = getFeatureStyle(name.getText());
          if (col == null)
          {
            col = new jalview.schemes.UserColourScheme()
                    .createColourFromName(name.getText());
          }
          oldcol = fcol = col;
          updateColourButton(bigPanel, colour, col);
        }
      });

      panel.add(tmp);
    }
    // ////////
    // ////////////////////////////////////

    tmp = new JPanel();
    panel.add(tmp);
    tmp.add(new JLabel("Name: ", JLabel.RIGHT));
    tmp.add(name);

    tmp = new JPanel();
    panel.add(tmp);
    tmp.add(new JLabel("Group: ", JLabel.RIGHT));
    tmp.add(source);

    tmp = new JPanel();
    panel.add(tmp);
    tmp.add(new JLabel("Colour: ", JLabel.RIGHT));
    tmp.add(colour);
    colour.setPreferredSize(new Dimension(150, 15));
    colour.setFont(new java.awt.Font("Verdana", Font.PLAIN, 9));
    colour.setForeground(Color.black);
    colour.setHorizontalAlignment(SwingConstants.CENTER);
    colour.setVerticalAlignment(SwingConstants.CENTER);
    colour.setHorizontalTextPosition(SwingConstants.CENTER);
    colour.setVerticalTextPosition(SwingConstants.CENTER);
    bigPanel.add(panel, BorderLayout.NORTH);

    panel = new JPanel();
    panel.add(new JLabel("Description: ", JLabel.RIGHT));
    description.setFont(JvSwingUtils.getTextAreaFont());
    description.setLineWrap(true);
    panel.add(new JScrollPane(description));

    if (!newFeatures)
    {
      bigPanel.add(panel, BorderLayout.SOUTH);

      panel = new JPanel();
      panel.add(new JLabel(" Start:", JLabel.RIGHT));
      panel.add(start);
      panel.add(new JLabel("  End:", JLabel.RIGHT));
      panel.add(end);
      bigPanel.add(panel, BorderLayout.CENTER);
    }
    else
    {
      bigPanel.add(panel, BorderLayout.CENTER);
    }

    if (lastFeatureAdded == null)
    {
      if (features[0].type != null)
      {
        lastFeatureAdded = features[0].type;
      }
      else
      {
        lastFeatureAdded = "feature_1";
      }
    }

    if (lastFeatureGroupAdded == null)
    {
      if (features[0].featureGroup != null)
      {
        lastFeatureGroupAdded = features[0].featureGroup;
      }
      else
      {
        lastFeatureGroupAdded = "Jalview";
      }
    }

    if (newFeatures)
    {
      name.setText(lastFeatureAdded);
      source.setText(lastFeatureGroupAdded);
    }
    else
    {
      name.setText(features[0].getType());
      source.setText(features[0].getFeatureGroup());
    }

    start.setValue(new Integer(features[0].getBegin()));
    end.setValue(new Integer(features[0].getEnd()));
    description.setText(features[0].getDescription());
    updateColourButton(bigPanel, colour,
            (oldcol = fcol = getFeatureStyle(name.getText())));
    Object[] options;
    if (!newFeatures)
    {
      options = new Object[]
      { "Amend", "Delete", "Cancel" };
    }
    else
    {
      options = new Object[]
      { "OK", "Cancel" };
    }

    String title = newFeatures ? "Create New Sequence Feature(s)"
            : "Amend/Delete Features for " + sequences[0].getName();

    int reply = JOptionPane.showInternalOptionDialog(Desktop.desktop,
            bigPanel, title, JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, "OK");

    jalview.io.FeaturesFile ffile = new jalview.io.FeaturesFile();

    if (reply == JOptionPane.OK_OPTION && name.getText().length() > 0)
    {
      // This ensures that the last sequence
      // is refreshed and new features are rendered
      lastSeq = null;
      lastFeatureAdded = name.getText().trim();
      lastFeatureGroupAdded = source.getText().trim();
      lastDescriptionAdded = description.getText().replaceAll("\n", " ");
      // TODO: determine if the null feature group is valid
      if (lastFeatureGroupAdded.length() < 1)
        lastFeatureGroupAdded = null;
    }

    if (!newFeatures)
    {
      SequenceFeature sf = features[featureIndex];

      if (reply == JOptionPane.NO_OPTION)
      {
        sequences[0].getDatasetSequence().deleteFeature(sf);
      }
      else if (reply == JOptionPane.YES_OPTION)
      {
        sf.type = lastFeatureAdded;
        sf.featureGroup = lastFeatureGroupAdded;
        sf.description = lastDescriptionAdded;

        setColour(sf.type, fcol);
        av.featuresDisplayed.put(sf.type, getColour(sf.type));

        try
        {
          sf.begin = ((Integer) start.getValue()).intValue();
          sf.end = ((Integer) end.getValue()).intValue();
        } catch (NumberFormatException ex)
        {
        }

        ffile.parseDescriptionHTML(sf, false);
      }
    }
    else
    // NEW FEATURES ADDED
    {
      if (reply == JOptionPane.OK_OPTION && lastFeatureAdded.length() > 0)
      {
        for (int i = 0; i < sequences.length; i++)
        {
          features[i].type = lastFeatureAdded;
          if (lastFeatureGroupAdded != null)
            features[i].featureGroup = lastFeatureGroupAdded;
          features[i].description = lastDescriptionAdded;
          sequences[i].addSequenceFeature(features[i]);
          ffile.parseDescriptionHTML(features[i], false);
        }

        if (av.featuresDisplayed == null)
        {
          av.featuresDisplayed = new Hashtable();
        }

        if (lastFeatureGroupAdded != null)
        {
          if (featureGroups == null)
            featureGroups = new Hashtable();
          featureGroups.put(lastFeatureGroupAdded, new Boolean(true));
        }
        setColour(lastFeatureAdded, fcol);
        av.featuresDisplayed.put(lastFeatureAdded,
                getColour(lastFeatureAdded));

        findAllFeatures(false);

        ap.paintAlignment(true);

        return true;
      }
      else
      {
        return false;
      }
    }

    ap.paintAlignment(true);

    return true;
  }

  /**
   * update the amend feature button dependent on the given style
   * 
   * @param bigPanel
   * @param col
   * @param col2
   */
  protected void updateColourButton(JPanel bigPanel, JLabel colour,
          Object col2)
  {
    colour.removeAll();
    colour.setIcon(null);
    colour.setToolTipText(null);
    colour.setText("");

    if (col2 instanceof Color)
    {
      colour.setBackground((Color) col2);
    }
    else
    {
      colour.setBackground(bigPanel.getBackground());
      colour.setForeground(Color.black);
      FeatureSettings.renderGraduatedColor(colour, (GraduatedColor) col2);
      // colour.setForeground(colour.getBackground());
    }
  }

  public void setColour(String featureType, Object col)
  {
    // overwrite
    // Color _col = (col instanceof Color) ? ((Color) col) : (col instanceof
    // GraduatedColor) ? ((GraduatedColor) col).getMaxColor() : null;
    // Object c = featureColours.get(featureType);
    // if (c == null || c instanceof Color || (c instanceof GraduatedColor &&
    // !((GraduatedColor)c).getMaxColor().equals(_col)))
    {
      featureColours.put(featureType, col);
    }
  }

  public void setTransparency(float value)
  {
    transparency = value;
  }

  public float getTransparency()
  {
    return transparency;
  }

  /**
   * Replace current ordering with new ordering
   * 
   * @param data
   *          { String(Type), Colour(Type), Boolean(Displayed) }
   */
  public void setFeaturePriority(Object[][] data)
  {
    setFeaturePriority(data, true);
  }

  /**
   * 
   * @param data
   *          { String(Type), Colour(Type), Boolean(Displayed) }
   * @param visibleNew
   *          when true current featureDisplay list will be cleared
   */
  public void setFeaturePriority(Object[][] data, boolean visibleNew)
  {
    if (visibleNew)
    {
      if (av.featuresDisplayed != null)
      {
        av.featuresDisplayed.clear();
      }
      else
      {
        av.featuresDisplayed = new Hashtable();
      }
    }
    if (data == null)
    {
      return;
    }

    // The feature table will display high priority
    // features at the top, but theses are the ones
    // we need to render last, so invert the data
    renderOrder = new String[data.length];

    if (data.length > 0)
    {
      for (int i = 0; i < data.length; i++)
      {
        String type = data[i][0].toString();
        setColour(type, data[i][1]); // todo : typesafety - feature color
        // interface object
        if (((Boolean) data[i][2]).booleanValue())
        {
          av.featuresDisplayed.put(type, new Integer(getColour(type)
                  .getRGB()));
        }

        renderOrder[data.length - i - 1] = type;
      }
    }

  }

  Map featureOrder = null;

  /**
   * analogous to colour - store a normalized ordering for all feature types in
   * this rendering context.
   * 
   * @param type
   *          Feature type string
   * @param position
   *          normalized priority - 0 means always appears on top, 1 means
   *          always last.
   */
  public float setOrder(String type, float position)
  {
    if (featureOrder == null)
    {
      featureOrder = new Hashtable();
    }
    featureOrder.put(type, new Float(position));
    return position;
  }

  /**
   * get the global priority (0 (top) to 1 (bottom))
   * 
   * @param type
   * @return [0,1] or -1 for a type without a priority
   */
  public float getOrder(String type)
  {
    if (featureOrder != null)
    {
      if (featureOrder.containsKey(type))
      {
        return ((Float) featureOrder.get(type)).floatValue();
      }
    }
    return -1;
  }

  /**
   * @param listener
   * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
   */
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    changeSupport.addPropertyChangeListener(listener);
  }

  /**
   * @param listener
   * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
   */
  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    changeSupport.removePropertyChangeListener(listener);
  }
}
