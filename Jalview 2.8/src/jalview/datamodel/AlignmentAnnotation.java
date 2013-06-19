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
package jalview.datamodel;

import jalview.analysis.Rna;
import jalview.analysis.WUSSParseException;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class AlignmentAnnotation
{
  /**
   * If true, this annotations is calculated every edit, eg consensus, quality
   * or conservation graphs
   */
  public boolean autoCalculated = false;

  public String annotationId;

  public SequenceI sequenceRef;

  /** DOCUMENT ME!! */
  public String label;

  /** DOCUMENT ME!! */
  public String description;

  /** DOCUMENT ME!! */
  public Annotation[] annotations;

  /**
   * RNA secondary structure contact positions
   */
  public SequenceFeature[] _rnasecstr = null;

  /**
   * position of annotation resulting in invalid WUSS parsing or -1
   */
  private long invalidrnastruc = -1;

  /**
   * Updates the _rnasecstr field Determines the positions that base pair and
   * the positions of helices based on secondary structure from a Stockholm file
   * 
   * @param RNAannot
   */
  private void _updateRnaSecStr(CharSequence RNAannot)
  {
    try
    {
      _rnasecstr = Rna.GetBasePairs(RNAannot);
      invalidrnastruc = -1;
    } catch (WUSSParseException px)
    {
      invalidrnastruc = px.getProblemPos();
    }
    if (invalidrnastruc > -1)
    {
      return;
    }
    Rna.HelixMap(_rnasecstr);
    // setRNAStruc(RNAannot);

    if (_rnasecstr != null && _rnasecstr.length > 0)
    {
      // show all the RNA secondary structure annotation symbols.
      isrna = true;
      showAllColLabels = true;
      scaleColLabel = true;
    }
    // System.out.println("featuregroup " + _rnasecstr[0].getFeatureGroup());
  }

  public java.util.Hashtable sequenceMapping;

  /** DOCUMENT ME!! */
  public float graphMin;

  /** DOCUMENT ME!! */
  public float graphMax;

  /**
   * Score associated with label and description.
   */
  public double score = Double.NaN;

  /**
   * flag indicating if annotation has a score.
   */
  public boolean hasScore = false;

  public GraphLine threshold;

  // Graphical hints and tips

  /** Can this row be edited by the user ? */
  public boolean editable = false;

  /** Indicates if annotation has a graphical symbol track */
  public boolean hasIcons; //

  /** Indicates if annotation has a text character label */
  public boolean hasText;

  /** is the row visible */
  public boolean visible = true;

  public int graphGroup = -1;

  /** Displayed height of row in pixels */
  public int height = 0;

  public int graph = 0;

  public int graphHeight = 40;

  public boolean padGaps = false;

  public static final int NO_GRAPH = 0;

  public static final int BAR_GRAPH = 1;

  public static final int LINE_GRAPH = 2;

  public boolean belowAlignment = true;

  public SequenceGroup groupRef = null;

  /**
   * display every column label, even if there is a row of identical labels
   */
  public boolean showAllColLabels = false;

  /**
   * scale the column label to fit within the alignment column.
   */
  public boolean scaleColLabel = false;

  /**
   * centre the column labels relative to the alignment column
   */
  public boolean centreColLabels = false;

  private boolean isrna;

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#finalize()
   */
  protected void finalize() throws Throwable
  {
    sequenceRef = null;
    groupRef = null;
    super.finalize();
  }

  public static int getGraphValueFromString(String string)
  {
    if (string.equalsIgnoreCase("BAR_GRAPH"))
    {
      return BAR_GRAPH;
    }
    else if (string.equalsIgnoreCase("LINE_GRAPH"))
    {
      return LINE_GRAPH;
    }
    else
    {
      return NO_GRAPH;
    }
  }

  /**
   * Creates a new AlignmentAnnotation object.
   * 
   * @param label
   *          short label shown under sequence labels
   * @param description
   *          text displayed on mouseover
   * @param annotations
   *          set of positional annotation elements
   */
  public AlignmentAnnotation(String label, String description,
          Annotation[] annotations)
  {
    // always editable?
    editable = true;
    this.label = label;
    this.description = description;
    this.annotations = annotations;

    validateRangeAndDisplay();
  }

  /**
   * Checks if annotation labels represent secondary structures
   * 
   */
  void areLabelsSecondaryStructure()
  {
    boolean nonSSLabel = false;
    isrna = false;
    StringBuffer rnastring = new StringBuffer();

    char firstChar = 0;
    for (int i = 0; i < annotations.length; i++)
    {
      if (annotations[i] == null)
      {
        continue;
      }
      if (annotations[i].secondaryStructure == 'H'
              || annotations[i].secondaryStructure == 'E')
      {
        hasIcons |= true;
      }
      else
      // Check for RNA secondary structure
      {
        if (annotations[i].secondaryStructure == 'S')
        {
          hasIcons |= true;
          isrna |= true;
        }
      }

      // System.out.println("displaychar " + annotations[i].displayCharacter);

      if (annotations[i].displayCharacter == null
              || annotations[i].displayCharacter.length() == 0)
      {
        rnastring.append('.');
        continue;
      }
      if (annotations[i].displayCharacter.length() == 1)
      {
        firstChar = annotations[i].displayCharacter.charAt(0);
        // check to see if it looks like a sequence or is secondary structure
        // labelling.
        if (annotations[i].secondaryStructure != ' '
                && !hasIcons
                &&
                // Uncomment to only catch case where
                // displayCharacter==secondary
                // Structure
                // to correctly redisplay SS annotation imported from Stockholm,
                // exported to JalviewXML and read back in again.
                // &&
                // annotations[i].displayCharacter.charAt(0)==annotations[i].secondaryStructure
                firstChar != ' '
                && firstChar != 'H'
                && firstChar != 'E'
                && firstChar != 'S'
                && firstChar != '-'
                && firstChar < jalview.schemes.ResidueProperties.aaIndex.length)
        {
          if (jalview.schemes.ResidueProperties.aaIndex[firstChar] < 23) // TODO:
                                                                         // parameterise
                                                                         // to
                                                                         // gap
                                                                         // symbol
                                                                         // number
          {
            nonSSLabel = true;
          }
        }
      }
      else
      {
        rnastring.append(annotations[i].displayCharacter.charAt(1));
      }

      if (annotations[i].displayCharacter.length() > 0)
      {
        hasText = true;
      }
    }

    if (nonSSLabel)
    {
      hasIcons = false;
      for (int j = 0; j < annotations.length; j++)
      {
        if (annotations[j] != null
                && annotations[j].secondaryStructure != ' ')
        {
          annotations[j].displayCharacter = String
                  .valueOf(annotations[j].secondaryStructure);
          annotations[j].secondaryStructure = ' ';
        }

      }
    }
    else
    {
      if (isrna)
      {
        _updateRnaSecStr(new AnnotCharSequence());
      }
    }

    annotationId = this.hashCode() + "";
  }

  /**
   * flyweight access to positions in the alignment annotation row for RNA
   * processing
   * 
   * @author jimp
   * 
   */
  private class AnnotCharSequence implements CharSequence
  {
    int offset = 0;

    int max = 0;

    public AnnotCharSequence()
    {
      this(0, annotations.length);
    }

    public AnnotCharSequence(int start, int end)
    {
      offset = start;
      max = end;
    }

    @Override
    public CharSequence subSequence(int start, int end)
    {
      return new AnnotCharSequence(offset + start, offset + end);
    }

    @Override
    public int length()
    {
      return max - offset;
    }

    @Override
    public char charAt(int index)
    {
      String dc;
      return ((index + offset < 0) || (index + offset) >= max
              || annotations[index + offset] == null || (dc = annotations[index
              + offset].displayCharacter.trim()).length() < 1) ? '.' : dc
              .charAt(0);
    }

    public String toString()
    {
      char[] string = new char[max - offset];
      int mx = annotations.length;

      for (int i = offset; i < mx; i++)
      {
        String dc;
        string[i] = (annotations[i] == null || (dc = annotations[i].displayCharacter
                .trim()).length() < 1) ? '.' : dc.charAt(0);
      }
      return new String(string);
    }
  };

  private long _lastrnaannot = -1;

  public String getRNAStruc()
  {
    if (isrna)
    {
      String rnastruc = new AnnotCharSequence().toString();
      if (_lastrnaannot != rnastruc.hashCode())
      {
        // ensure rna structure contacts are up to date
        _lastrnaannot = rnastruc.hashCode();
        _updateRnaSecStr(rnastruc);
      }
      return rnastruc;
    }
    return null;
  }

  /**
   * Creates a new AlignmentAnnotation object.
   * 
   * @param label
   *          DOCUMENT ME!
   * @param description
   *          DOCUMENT ME!
   * @param annotations
   *          DOCUMENT ME!
   * @param min
   *          DOCUMENT ME!
   * @param max
   *          DOCUMENT ME!
   * @param winLength
   *          DOCUMENT ME!
   */
  public AlignmentAnnotation(String label, String description,
          Annotation[] annotations, float min, float max, int graphType)
  {
    // graphs are not editable
    editable = graphType == 0;

    this.label = label;
    this.description = description;
    this.annotations = annotations;
    graph = graphType;
    graphMin = min;
    graphMax = max;
    validateRangeAndDisplay();
  }

  /**
   * checks graphMin and graphMax, secondary structure symbols, sets graphType
   * appropriately, sets null labels to the empty string if appropriate.
   */
  public void validateRangeAndDisplay()
  {

    if (annotations == null)
    {
      visible = false; // try to prevent renderer from displaying.
      return; // this is a non-annotation row annotation - ie a sequence score.
    }

    int graphType = graph;
    float min = graphMin;
    float max = graphMax;
    boolean drawValues = true;
    _linecolour = null;
    if (min == max)
    {
      min = 999999999;
      for (int i = 0; i < annotations.length; i++)
      {
        if (annotations[i] == null)
        {
          continue;
        }

        if (drawValues && annotations[i].displayCharacter != null
                && annotations[i].displayCharacter.length() > 1)
        {
          drawValues = false;
        }

        if (annotations[i].value > max)
        {
          max = annotations[i].value;
        }

        if (annotations[i].value < min)
        {
          min = annotations[i].value;
        }
        if (_linecolour == null && annotations[i].colour != null)
        {
          _linecolour = annotations[i].colour;
        }
      }
      // ensure zero is origin for min/max ranges on only one side of zero
      if (min > 0)
      {
        min = 0;
      }
      else
      {
        if (max < 0)
        {
          max = 0;
        }
      }
    }

    graphMin = min;
    graphMax = max;

    areLabelsSecondaryStructure();

    if (!drawValues && graphType != NO_GRAPH)
    {
      for (int i = 0; i < annotations.length; i++)
      {
        if (annotations[i] != null)
        {
          annotations[i].displayCharacter = "";
        }
      }
    }
  }

  /**
   * Copy constructor creates a new independent annotation row with the same
   * associated sequenceRef
   * 
   * @param annotation
   */
  public AlignmentAnnotation(AlignmentAnnotation annotation)
  {
    this.label = new String(annotation.label);
    if (annotation.description != null)
      this.description = new String(annotation.description);
    this.graphMin = annotation.graphMin;
    this.graphMax = annotation.graphMax;
    this.graph = annotation.graph;
    this.graphHeight = annotation.graphHeight;
    this.graphGroup = annotation.graphGroup;
    this.groupRef = annotation.groupRef;
    this.editable = annotation.editable;
    this.autoCalculated = annotation.autoCalculated;
    this.hasIcons = annotation.hasIcons;
    this.hasText = annotation.hasText;
    this.height = annotation.height;
    this.label = annotation.label;
    this.padGaps = annotation.padGaps;
    this.visible = annotation.visible;
    this.centreColLabels = annotation.centreColLabels;
    this.scaleColLabel = annotation.scaleColLabel;
    this.showAllColLabels = annotation.showAllColLabels;
    this.calcId = annotation.calcId;
    if (this.hasScore = annotation.hasScore)
    {
      this.score = annotation.score;
    }
    if (annotation.threshold != null)
    {
      threshold = new GraphLine(annotation.threshold);
    }
    if (annotation.annotations != null)
    {
      Annotation[] ann = annotation.annotations;
      this.annotations = new Annotation[ann.length];
      for (int i = 0; i < ann.length; i++)
      {
        if (ann[i] != null)
        {
          annotations[i] = new Annotation(ann[i]);
          if (_linecolour != null)
          {
            _linecolour = annotations[i].colour;
          }
        }
      }
      ;
      if (annotation.sequenceRef != null)
      {
        this.sequenceRef = annotation.sequenceRef;
        if (annotation.sequenceMapping != null)
        {
          Integer p = null;
          sequenceMapping = new Hashtable();
          Enumeration pos = annotation.sequenceMapping.keys();
          while (pos.hasMoreElements())
          {
            // could optimise this!
            p = (Integer) pos.nextElement();
            Annotation a = (Annotation) annotation.sequenceMapping.get(p);
            if (a == null)
            {
              continue;
            }
            for (int i = 0; i < ann.length; i++)
            {
              if (ann[i] == a)
              {
                sequenceMapping.put(p, annotations[i]);
              }
            }
          }
        }
        else
        {
          this.sequenceMapping = null;
        }
      }
    }
    // TODO: check if we need to do this: JAL-952
    // if (this.isrna=annotation.isrna)
    {
      // _rnasecstr=new SequenceFeature[annotation._rnasecstr];
    }
    validateRangeAndDisplay(); // construct hashcodes, etc.
  }

  /**
   * clip the annotation to the columns given by startRes and endRes (inclusive)
   * and prune any existing sequenceMapping to just those columns.
   * 
   * @param startRes
   * @param endRes
   */
  public void restrict(int startRes, int endRes)
  {
    if (annotations == null)
    {
      // non-positional
      return;
    }
    if (startRes < 0)
      startRes = 0;
    if (startRes >= annotations.length)
      startRes = annotations.length - 1;
    if (endRes >= annotations.length)
      endRes = annotations.length - 1;
    if (annotations == null)
      return;
    Annotation[] temp = new Annotation[endRes - startRes + 1];
    if (startRes < annotations.length)
    {
      System.arraycopy(annotations, startRes, temp, 0, endRes - startRes
              + 1);
    }
    if (sequenceRef != null)
    {
      // Clip the mapping, if it exists.
      int spos = sequenceRef.findPosition(startRes);
      int epos = sequenceRef.findPosition(endRes);
      if (sequenceMapping != null)
      {
        Hashtable newmapping = new Hashtable();
        Enumeration e = sequenceMapping.keys();
        while (e.hasMoreElements())
        {
          Integer pos = (Integer) e.nextElement();
          if (pos.intValue() >= spos && pos.intValue() <= epos)
          {
            newmapping.put(pos, sequenceMapping.get(pos));
          }
        }
        sequenceMapping.clear();
        sequenceMapping = newmapping;
      }
    }
    annotations = temp;
  }

  /**
   * set the annotation row to be at least length Annotations
   * 
   * @param length
   *          minimum number of columns required in the annotation row
   * @return false if the annotation row is greater than length
   */
  public boolean padAnnotation(int length)
  {
    if (annotations == null)
    {
      return true; // annotation row is correct - null == not visible and
      // undefined length
    }
    if (annotations.length < length)
    {
      Annotation[] na = new Annotation[length];
      System.arraycopy(annotations, 0, na, 0, annotations.length);
      annotations = na;
      return true;
    }
    return annotations.length > length;

  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < annotations.length; i++)
    {
      if (annotations[i] != null)
      {
        if (graph != 0)
        {
          buffer.append(annotations[i].value);
        }
        else if (hasIcons)
        {
          buffer.append(annotations[i].secondaryStructure);
        }
        else
        {
          buffer.append(annotations[i].displayCharacter);
        }
      }

      buffer.append(", ");
    }
    // TODO: remove disgusting hack for 'special' treatment of consensus line.
    if (label.indexOf("Consensus") == 0)
    {
      buffer.append("\n");

      for (int i = 0; i < annotations.length; i++)
      {
        if (annotations[i] != null)
        {
          buffer.append(annotations[i].description);
        }

        buffer.append(", ");
      }
    }

    return buffer.toString();
  }

  public void setThreshold(GraphLine line)
  {
    threshold = line;
  }

  public GraphLine getThreshold()
  {
    return threshold;
  }

  /**
   * Attach the annotation to seqRef, starting from startRes position. If
   * alreadyMapped is true then the indices of the annotation[] array are
   * sequence positions rather than alignment column positions.
   * 
   * @param seqRef
   * @param startRes
   * @param alreadyMapped
   */
  public void createSequenceMapping(SequenceI seqRef, int startRes,
          boolean alreadyMapped)
  {

    if (seqRef == null)
    {
      return;
    }
    sequenceRef = seqRef;
    if (annotations == null)
    {
      return;
    }
    sequenceMapping = new java.util.Hashtable();

    int seqPos;

    for (int i = 0; i < annotations.length; i++)
    {
      if (annotations[i] != null)
      {
        if (alreadyMapped)
        {
          seqPos = seqRef.findPosition(i);
        }
        else
        {
          seqPos = i + startRes;
        }

        sequenceMapping.put(new Integer(seqPos), annotations[i]);
      }
    }

  }

  public void adjustForAlignment()
  {
    if (sequenceRef == null)
      return;

    if (annotations == null)
    {
      return;
    }

    int a = 0, aSize = sequenceRef.getLength();

    if (aSize == 0)
    {
      // Its been deleted
      return;
    }

    int position;
    Annotation[] temp = new Annotation[aSize];
    Integer index;

    for (a = sequenceRef.getStart(); a <= sequenceRef.getEnd(); a++)
    {
      index = new Integer(a);
      if (sequenceMapping.containsKey(index))
      {
        position = sequenceRef.findIndex(a) - 1;

        temp[position] = (Annotation) sequenceMapping.get(index);
      }
    }

    annotations = temp;
  }

  /**
   * remove any null entries in annotation row and return the number of non-null
   * annotation elements.
   * 
   * @return
   */
  public int compactAnnotationArray()
  {
    int i = 0, iSize = annotations.length;
    while (i < iSize)
    {
      if (annotations[i] == null)
      {
        if (i + 1 < iSize)
          System.arraycopy(annotations, i + 1, annotations, i, iSize - i
                  - 1);
        iSize--;
      }
      else
      {
        i++;
      }
    }
    Annotation[] ann = annotations;
    annotations = new Annotation[i];
    System.arraycopy(ann, 0, annotations, 0, i);
    ann = null;
    return iSize;
  }

  /**
   * Associate this annotion with the aligned residues of a particular sequence.
   * sequenceMapping will be updated in the following way: null sequenceI -
   * existing mapping will be discarded but annotations left in mapped
   * positions. valid sequenceI not equal to current sequenceRef: mapping is
   * discarded and rebuilt assuming 1:1 correspondence TODO: overload with
   * parameter to specify correspondence between current and new sequenceRef
   * 
   * @param sequenceI
   */
  public void setSequenceRef(SequenceI sequenceI)
  {
    if (sequenceI != null)
    {
      if (sequenceRef != null)
      {
        if (sequenceRef != sequenceI
                && !sequenceRef.equals(sequenceI)
                && sequenceRef.getDatasetSequence() != sequenceI
                        .getDatasetSequence())
        {
          // if sequenceRef isn't intersecting with sequenceI
          // throw away old mapping and reconstruct.
          sequenceRef = null;
          if (sequenceMapping != null)
          {
            sequenceMapping = null;
            // compactAnnotationArray();
          }
          createSequenceMapping(sequenceI, 1, true);
          adjustForAlignment();
        }
        else
        {
          // Mapping carried over
          sequenceRef = sequenceI;
        }
      }
      else
      {
        // No mapping exists
        createSequenceMapping(sequenceI, 1, true);
        adjustForAlignment();
      }
    }
    else
    {
      // throw away the mapping without compacting.
      sequenceMapping = null;
      sequenceRef = null;
    }
  }

  /**
   * @return the score
   */
  public double getScore()
  {
    return score;
  }

  /**
   * @param score
   *          the score to set
   */
  public void setScore(double score)
  {
    hasScore = true;
    this.score = score;
  }

  /**
   * 
   * @return true if annotation has an associated score
   */
  public boolean hasScore()
  {
    return hasScore || !Double.isNaN(score);
  }

  /**
   * Score only annotation
   * 
   * @param label
   * @param description
   * @param score
   */
  public AlignmentAnnotation(String label, String description, double score)
  {
    this(label, description, null);
    setScore(score);
  }

  /**
   * copy constructor with edit based on the hidden columns marked in colSel
   * 
   * @param alignmentAnnotation
   * @param colSel
   */
  public AlignmentAnnotation(AlignmentAnnotation alignmentAnnotation,
          ColumnSelection colSel)
  {
    this(alignmentAnnotation);
    if (annotations == null)
    {
      return;
    }
    colSel.makeVisibleAnnotation(this);
  }

  public void setPadGaps(boolean padgaps, char gapchar)
  {
    this.padGaps = padgaps;
    if (padgaps)
    {
      hasText = true;
      for (int i = 0; i < annotations.length; i++)
      {
        if (annotations[i] == null)
          annotations[i] = new Annotation(String.valueOf(gapchar), null,
                  ' ', 0f);
        else if (annotations[i].displayCharacter == null
                || annotations[i].displayCharacter.equals(" "))
          annotations[i].displayCharacter = String.valueOf(gapchar);
      }
    }
  }

  /**
   * format description string for display
   * 
   * @param seqname
   * @return Get the annotation description string optionally prefixed by
   *         associated sequence name (if any)
   */
  public String getDescription(boolean seqname)
  {
    if (seqname && this.sequenceRef != null)
    {
      int i = description.toLowerCase().indexOf("<html>");
      if (i > -1)
      {
        // move the html tag to before the sequence reference.
        return "<html>" + sequenceRef.getName() + " : "
                + description.substring(i + 6);
      }
      return sequenceRef.getName() + " : " + description;
    }
    return description;
  }

  public boolean isValidStruc()
  {
    return invalidrnastruc == -1;
  }

  public long getInvalidStrucPos()
  {
    return invalidrnastruc;
  }

  /**
   * machine readable ID string indicating what generated this annotation
   */
  protected String calcId = "";

  /**
   * base colour for line graphs. If null, will be set automatically by
   * searching the alignment annotation
   */
  public java.awt.Color _linecolour;

  public String getCalcId()
  {
    return calcId;
  }

  public void setCalcId(String calcId)
  {
    this.calcId = calcId;
  }

}
