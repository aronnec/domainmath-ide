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
package jalview.io;

import java.io.*;
import java.net.*;
import java.util.*;

import jalview.analysis.*;
import jalview.datamodel.*;
import jalview.schemes.*;

public class AnnotationFile
{
  public AnnotationFile()
  {
    init();
  }

  /**
   * character used to write newlines
   */
  protected String newline = System.getProperty("line.separator");

  /**
   * set new line string and reset the output buffer
   * 
   * @param nl
   */
  public void setNewlineString(String nl)
  {
    newline = nl;
    init();
  }

  public String getNewlineString()
  {
    return newline;
  }

  StringBuffer text;

  private void init()
  {
    text = new StringBuffer("JALVIEW_ANNOTATION" + newline + "# Created: "
            + new java.util.Date() + newline + newline);
    refSeq = null;
    refSeqId = null;
  }

  /**
   * convenience method for pre-2.4 feature files which have no view, hidden
   * columns or hidden row keywords.
   * 
   * @param annotations
   * @param list
   * @param properties
   * @return feature file as a string.
   */
  public String printAnnotations(AlignmentAnnotation[] annotations,
          List<SequenceGroup> list, Hashtable properties)
  {
    return printAnnotations(annotations, list, properties, null);

  }

  /**
   * hold all the information about a particular view definition read from or
   * written out in an annotations file.
   */
  public class ViewDef
  {
    public String viewname;

    public HiddenSequences hidseqs;

    public ColumnSelection hiddencols;

    public Vector visibleGroups;

    public Hashtable hiddenRepSeqs;

    public ViewDef(String viewname, HiddenSequences hidseqs,
            ColumnSelection hiddencols, Hashtable hiddenRepSeqs)
    {
      this.viewname = viewname;
      this.hidseqs = hidseqs;
      this.hiddencols = hiddencols;
      this.hiddenRepSeqs = hiddenRepSeqs;
    }
  }

  /**
   * Prepare an annotation file given a set of annotations, groups, alignment
   * properties and views.
   * 
   * @param annotations
   * @param list
   * @param properties
   * @param views
   * @return annotation file
   */
  public String printAnnotations(AlignmentAnnotation[] annotations,
          List<SequenceGroup> list, Hashtable properties, ViewDef[] views)
  {
    // TODO: resolve views issue : annotationFile could contain visible region,
    // or full data + hidden region specifications for a view.
    if (annotations != null)
    {
      boolean oneColour = true;
      AlignmentAnnotation row;
      String comma;
      SequenceI refSeq = null;
      SequenceGroup refGroup = null;

      StringBuffer colours = new StringBuffer();
      StringBuffer graphLine = new StringBuffer();
      StringBuffer rowprops = new StringBuffer();
      Hashtable<Integer,String> graphGroup = new Hashtable<Integer,String>();
      Hashtable<Integer, Object[]> graphGroup_refs = new Hashtable<Integer,Object[]>();
      BitSet graphGroupSeen = new BitSet();

      java.awt.Color color;

      for (int i = 0; i < annotations.length; i++)
      {
        row = annotations[i];

        if (!row.visible && !row.hasScore() && !(row.graphGroup>-1 && graphGroupSeen.get(row.graphGroup)))
        {
          continue;
        }

        color = null;
        oneColour = true;
        
        // mark any sequence references for the row
        writeSequence_Ref(refSeq ,row.sequenceRef);
        refSeq = row.sequenceRef;
        // mark any group references for the row
        writeGroup_Ref(refGroup, row.groupRef);
        refGroup = row.groupRef;

        boolean hasGlyphs = row.hasIcons, hasLabels = row.hasText, hasValues = row.hasScore, hasText = false;
        // lookahead to check what the annotation row object actually contains.
        for (int j = 0; row.annotations != null
                && j < row.annotations.length
                && (!hasGlyphs || !hasLabels || !hasValues); j++)
        {
          if (row.annotations[j] != null)
          {
            hasLabels |= (row.annotations[j].displayCharacter != null
                    && row.annotations[j].displayCharacter.length() > 0 && !row.annotations[j].displayCharacter
                    .equals(" "));
            hasGlyphs |= (row.annotations[j].secondaryStructure != 0 && row.annotations[j].secondaryStructure != ' ');
            hasValues |= (row.annotations[j].value != Float.NaN); // NaNs can't
            // be
            // rendered..
            hasText |= (row.annotations[j].description != null && row.annotations[j].description
                    .length() > 0);
          }
        }

        if (row.graph == AlignmentAnnotation.NO_GRAPH)
        {
          text.append("NO_GRAPH\t");
          hasValues = false; // only secondary structure
          // hasLabels = false; // and annotation description string.
        }
        else
        {
          if (row.graph == AlignmentAnnotation.BAR_GRAPH)
          {
            text.append("BAR_GRAPH\t");
            hasGlyphs = false; // no secondary structure

          }
          else if (row.graph == AlignmentAnnotation.LINE_GRAPH)
          {
            hasGlyphs = false; // no secondary structure
            text.append("LINE_GRAPH\t");
          }

          if (row.getThreshold() != null)
          {
            graphLine.append("GRAPHLINE\t");
            graphLine.append(row.label);
            graphLine.append("\t");
            graphLine.append(row.getThreshold().value);
            graphLine.append("\t");
            graphLine.append(row.getThreshold().label);
            graphLine.append("\t");
            graphLine.append(jalview.util.Format.getHexString(row
                    .getThreshold().colour));
            graphLine.append(newline);
          }

          if (row.graphGroup > -1)
          {
            graphGroupSeen.set(row.graphGroup);
            Integer key = new Integer(row.graphGroup);
            if (graphGroup.containsKey(key))
            {
              graphGroup.put(key, graphGroup.get(key) + "\t" + row.label);
              
            }
            else
            {
              graphGroup_refs.put(key, new Object[] { refSeq, refGroup});
              graphGroup.put(key, row.label);
            }
          }
        }

        text.append(row.label + "\t");
        if (row.description != null)
        {
          text.append(row.description + "\t");
        }
        for (int j = 0; row.annotations != null
                && j < row.annotations.length; j++)
        {
          if (refSeq != null
                  && jalview.util.Comparison.isGap(refSeq.getCharAt(j)))
          {
            continue;
          }

          if (row.annotations[j] != null)
          {
            comma = "";
            if (hasGlyphs) // could be also hasGlyphs || ...
            {

              text.append(comma);
              if (row.annotations[j].secondaryStructure != ' ')
              {
                // only write out the field if its not whitespace.
                text.append(row.annotations[j].secondaryStructure);
              }
              comma = ",";
            }
            if (hasValues)
            {
              if (row.annotations[j].value != Float.NaN)
              {
                text.append(comma + row.annotations[j].value);
              }
              else
              {
                System.err.println("Skipping NaN - not valid value.");
                text.append(comma + 0f);// row.annotations[j].value);
              }
              comma = ",";
            }
            if (hasLabels)
            {
              // TODO: labels are emitted after values for bar graphs.
              if // empty labels are allowed, so
              (row.annotations[j].displayCharacter != null
                      && row.annotations[j].displayCharacter.length() > 0
                      && !row.annotations[j].displayCharacter.equals(" "))
              {
                text.append(comma + row.annotations[j].displayCharacter);
                comma = ",";
              }
            }
            if (hasText)
            {
              if (row.annotations[j].description != null
                      && row.annotations[j].description.length() > 0
                      && !row.annotations[j].description
                              .equals(row.annotations[j].displayCharacter))
              {
                text.append(comma + row.annotations[j].description);
                comma = ",";
              }
            }
            if (color != null && !color.equals(row.annotations[j].colour))
            {
              oneColour = false;
            }

            color = row.annotations[j].colour;

            if (row.annotations[j].colour != null
                    && row.annotations[j].colour != java.awt.Color.black)
            {
              text.append(comma
                      + "["
                      + jalview.util.Format
                              .getHexString(row.annotations[j].colour)
                      + "]");
              comma = ",";
            }
          }
          text.append("|");
        }

        if (row.hasScore())
          text.append("\t" + row.score);

        text.append(newline);

        if (color != null && color != java.awt.Color.black && oneColour)
        {
          colours.append("COLOUR\t");
          colours.append(row.label);
          colours.append("\t");
          colours.append(jalview.util.Format.getHexString(color));
          colours.append(newline);
        }
        if (row.scaleColLabel || row.showAllColLabels
                || row.centreColLabels)
        {
          rowprops.append("ROWPROPERTIES\t");
          rowprops.append(row.label);
          rowprops.append("\tscaletofit=");
          rowprops.append(row.scaleColLabel);
          rowprops.append("\tshowalllabs=");
          rowprops.append(row.showAllColLabels);
          rowprops.append("\tcentrelabs=");
          rowprops.append(row.centreColLabels);
          rowprops.append(newline);
        }
      }

      text.append(newline);

      text.append(colours.toString());
      text.append(graphLine.toString());
      if (graphGroup.size() > 0)
      {
        SequenceI oldRefSeq = refSeq;
        SequenceGroup oldRefGroup = refGroup;
        for (Map.Entry<Integer, String> combine_statement:graphGroup.entrySet())
        {
          Object[] seqRefAndGroup=graphGroup_refs.get(combine_statement.getKey());
          
          writeSequence_Ref(refSeq, (SequenceI)seqRefAndGroup[0]);
          refSeq = (SequenceI)seqRefAndGroup[0];
          
          writeGroup_Ref(refGroup, (SequenceGroup)seqRefAndGroup[1]);
          refGroup = (SequenceGroup)seqRefAndGroup[1];
          text.append("COMBINE\t");
          text.append(combine_statement.getValue());
          text.append(newline);
        }
        writeSequence_Ref(refSeq, oldRefSeq);
        refSeq = oldRefSeq;
        
        writeGroup_Ref(refGroup, oldRefGroup);
        refGroup = oldRefGroup;
      }
      text.append(rowprops.toString());
    }

    if (list != null)
    {
      printGroups(list);
    }

    if (properties != null)
    {
      text.append(newline);
      text.append(newline);
      text.append("ALIGNMENT");
      Enumeration en = properties.keys();
      while (en.hasMoreElements())
      {
        String key = en.nextElement().toString();
        text.append("\t");
        text.append(key);
        text.append("=");
        text.append(properties.get(key));
      }
      // TODO: output alignment visualization settings here if required

    }

    return text.toString();
  }

  private Object writeGroup_Ref(SequenceGroup refGroup, SequenceGroup next_refGroup)
  {
    if (next_refGroup == null)
    {

      if (refGroup != null)
      {
        text.append(newline);
        text.append("GROUP_REF\t");
        text.append("ALIGNMENT");
        text.append(newline);
      }
      return true;
    }
    else
    {
      if (refGroup == null || refGroup != next_refGroup)
      {
        text.append(newline);
        text.append("GROUP_REF\t");
        text.append(next_refGroup.getName());
        text.append(newline);
        return true;
      }
    }
    return false;  
  }
  
  private boolean writeSequence_Ref(SequenceI refSeq, SequenceI next_refSeq)
  {

    if (next_refSeq==null)
    {
      if (refSeq != null)
      {
        text.append(newline);
        text.append("SEQUENCE_REF\t");
        text.append("ALIGNMENT");
        text.append(newline);
        return true;
      }
    }
    else
    {
      if (refSeq == null || refSeq != next_refSeq)
      {
        text.append(newline);
        text.append("SEQUENCE_REF\t");
        text.append(next_refSeq.getName());
        text.append(newline);
        return true;
      }
    }
    return false;
  }

  public void printGroups(List<SequenceGroup> list)
  {
    SequenceI seqrep = null;
    for (SequenceGroup sg : list)
    {
      if (!sg.hasSeqrep())
      {
        text.append("SEQUENCE_GROUP\t" + sg.getName() + "\t"
                + (sg.getStartRes() + 1) + "\t" + (sg.getEndRes() + 1)
                + "\t" + "-1\t");
        seqrep = null;
      }
      else
      {
        seqrep = sg.getSeqrep();
        text.append("SEQUENCE_REF\t");
        text.append(seqrep.getName());
        text.append(newline);
        text.append("SEQUENCE_GROUP\t");
        text.append(sg.getName());
        text.append("\t");
        text.append((seqrep.findPosition(sg.getStartRes())));
        text.append("\t");
        text.append((seqrep.findPosition(sg.getEndRes())));
        text.append("\t");
        text.append("-1\t");
      }
      for (int s = 0; s < sg.getSize(); s++)
      {
        text.append(sg.getSequenceAt(s).getName());
        text.append("\t");
      }
      text.append(newline);
      text.append("PROPERTIES\t");
      text.append(sg.getName());
      text.append("\t");

      if (sg.getDescription() != null)
      {
        text.append("description=");
        text.append(sg.getDescription());
        text.append("\t");
      }
      if (sg.cs != null)
      {
        text.append("colour=");
        text.append(ColourSchemeProperty.getColourName(sg.cs));
        text.append("\t");
        if (sg.cs.getThreshold() != 0)
        {
          text.append("pidThreshold=");
          text.append(sg.cs.getThreshold());
        }
        if (sg.cs.conservationApplied())
        {
          text.append("consThreshold=");
          text.append(sg.cs.getConservationInc());
          text.append("\t");
        }
      }
      text.append("outlineColour=");
      text.append(jalview.util.Format.getHexString(sg.getOutlineColour()));
      text.append("\t");

      text.append("displayBoxes=");
      text.append(sg.getDisplayBoxes());
      text.append("\t");
      text.append("displayText=");
      text.append(sg.getDisplayText());
      text.append("\t");
      text.append("colourText=");
      text.append(sg.getColourText());
      text.append("\t");
      text.append("showUnconserved=");
      text.append(sg.getShowNonconserved());
      text.append("\t");
      if (sg.textColour != java.awt.Color.black)
      {
        text.append("textCol1=");
        text.append(jalview.util.Format.getHexString(sg.textColour));
        text.append("\t");
      }
      if (sg.textColour2 != java.awt.Color.white)
      {
        text.append("textCol2=");
        text.append(jalview.util.Format.getHexString(sg.textColour2));
        text.append("\t");
      }
      if (sg.thresholdTextColour != 0)
      {
        text.append("textColThreshold=");
        text.append(sg.thresholdTextColour);
        text.append("\t");
      }
      if (sg.idColour != null)
      {
        text.append("idColour=");
        text.append(jalview.util.Format.getHexString(sg.idColour));
        text.append("\t");
      }
      if (sg.isHidereps())
      {
        text.append("hide=true\t");
      }
      if (sg.isHideCols())
      {
        text.append("hidecols=true\t");
      }
      if (seqrep != null)
      {
        // terminate the last line and clear the sequence ref for the group
        text.append(newline);
        text.append("SEQUENCE_REF");
      }
      text.append(newline);
      text.append(newline);

    }
  }

  SequenceI refSeq = null;

  String refSeqId = null;

  public boolean readAnnotationFile(AlignmentI al, String file,
          String protocol)
  {
    BufferedReader in = null;
    try
    {
      if (protocol.equals(AppletFormatAdapter.FILE))
      {
        in = new BufferedReader(new FileReader(file));
      }
      else if (protocol.equals(AppletFormatAdapter.URL))
      {
        URL url = new URL(file);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
      }
      else if (protocol.equals(AppletFormatAdapter.PASTE))
      {
        in = new BufferedReader(new StringReader(file));
      }
      else if (protocol.equals(AppletFormatAdapter.CLASSLOADER))
      {
        java.io.InputStream is = getClass().getResourceAsStream("/" + file);
        if (is != null)
        {
          in = new BufferedReader(new java.io.InputStreamReader(is));
        }
      }
      if (in != null)
      {
        return parseAnnotationFrom(al, in);
      }

    } catch (Exception ex)
    {
      ex.printStackTrace();
      System.out.println("Problem reading annotation file: " + ex);
      if (nlinesread>0) {
        System.out.println("Last read line "+nlinesread+": '"+lastread+"' (first 80 chars) ...");
      }
      return false;
    }
    return false;
  }
  long nlinesread=0;
  String lastread="";
  public boolean parseAnnotationFrom(AlignmentI al, BufferedReader in)
          throws Exception
  {
    nlinesread = 0;
    ArrayList<Object[]> combineAnnotation_calls = new ArrayList<Object[]>();
    boolean modified = false;
    String groupRef = null;
    Hashtable groupRefRows = new Hashtable();

    Hashtable autoAnnots = new Hashtable();
    {
      String line, label, description, token;
      int graphStyle, index;
      int refSeqIndex = 1;
      int existingAnnotations = 0;
      // when true - will add new rows regardless of whether they are duplicate
      // auto-annotation like consensus or conservation graphs
      boolean overrideAutoAnnot = false;
      if (al.getAlignmentAnnotation() != null)
      {
        existingAnnotations = al.getAlignmentAnnotation().length;
        if (existingAnnotations > 0)
        {
          AlignmentAnnotation[] aa = al.getAlignmentAnnotation();
          for (int aai = 0; aai < aa.length; aai++)
          {
            if (aa[aai].autoCalculated)
            {
              // make a note of the name and description
              autoAnnots.put(
                      autoAnnotsKey(aa[aai], aa[aai].sequenceRef,
                              (aa[aai].groupRef == null ? null
                                      : aa[aai].groupRef.getName())),
                      new Integer(1));
            }
          }
        }
      }

      int alWidth = al.getWidth();

      StringTokenizer st;
      Annotation[] annotations;
      AlignmentAnnotation annotation = null;

      // First confirm this is an Annotation file
      boolean jvAnnotationFile = false;
      while ((line = in.readLine()) != null)
      {
        nlinesread++;lastread = new String(line);
        if (line.indexOf("#") == 0)
        {
          continue;
        }

        if (line.indexOf("JALVIEW_ANNOTATION") > -1)
        {
          jvAnnotationFile = true;
          break;
        }
      }

      if (!jvAnnotationFile)
      {
        in.close();
        return false;
      }

      while ((line = in.readLine()) != null)
      {
        nlinesread++;lastread = new String(line);
        if (line.indexOf("#") == 0
                || line.indexOf("JALVIEW_ANNOTATION") > -1
                || line.length() == 0)
        {
          continue;
        }

        st = new StringTokenizer(line, "\t");
        token = st.nextToken();
        if (token.equalsIgnoreCase("COLOUR"))
        {
          // TODO: use graduated colour def'n here too
          colourAnnotations(al, st.nextToken(), st.nextToken());
          modified = true;
          continue;
        }

        else if (token.equalsIgnoreCase("COMBINE"))
        {
          // keep a record of current state and resolve groupRef at end
          combineAnnotation_calls.add(new Object[] { st, refSeq, groupRef});
          modified = true;
          continue;
        }
        else if (token.equalsIgnoreCase("ROWPROPERTIES"))
        {
          addRowProperties(al, st);
          modified = true;
          continue;
        }
        else if (token.equalsIgnoreCase("GRAPHLINE"))
        {
          addLine(al, st);
          modified = true;
          continue;
        }

        else if (token.equalsIgnoreCase("SEQUENCE_REF"))
        {
          if (st.hasMoreTokens())
          {
            refSeq = al.findName(refSeqId = st.nextToken());
            if (refSeq == null)
            {
              refSeqId = null;
            }
            try
            {
              refSeqIndex = Integer.parseInt(st.nextToken());
              if (refSeqIndex < 1)
              {
                refSeqIndex = 1;
                System.out
                        .println("WARNING: SEQUENCE_REF index must be > 0 in AnnotationFile");
              }
            } catch (Exception ex)
            {
              refSeqIndex = 1;
            }
          }
          else
          {
            refSeq = null;
            refSeqId = null;
          }
          continue;
        }
        else if (token.equalsIgnoreCase("GROUP_REF"))
        {
          // Group references could be forward or backwards, so they are
          // resolved after the whole file is read in
          groupRef = null;
          if (st.hasMoreTokens())
          {
            groupRef = st.nextToken();
            if (groupRef.length() < 1)
            {
              groupRef = null; // empty string
            }
            else
            {
              if (groupRefRows.get(groupRef) == null)
              {
                groupRefRows.put(groupRef, new Vector());
              }
            }
          }
          continue;
        }
        else if (token.equalsIgnoreCase("SEQUENCE_GROUP"))
        {
          addGroup(al, st);
          continue;
        }

        else if (token.equalsIgnoreCase("PROPERTIES"))
        {
          addProperties(al, st);
          modified = true;
          continue;
        }

        else if (token.equalsIgnoreCase("BELOW_ALIGNMENT"))
        {
          setBelowAlignment(al, st);
          modified = true;
          continue;
        }
        else if (token.equalsIgnoreCase("ALIGNMENT"))
        {
          addAlignmentDetails(al, st);
          modified = true;
          continue;
        }

        // Parse out the annotation row
        graphStyle = AlignmentAnnotation.getGraphValueFromString(token);
        label = st.nextToken();

        index = 0;
        annotations = new Annotation[alWidth];
        description = null;
        float score = Float.NaN;

        if (st.hasMoreTokens())
        {
          line = st.nextToken();

          if (line.indexOf("|") == -1)
          {
            description = line;
            if (st.hasMoreTokens())
              line = st.nextToken();
          }

          if (st.hasMoreTokens())
          {
            // This must be the score
            score = Float.valueOf(st.nextToken()).floatValue();
          }

          st = new StringTokenizer(line, "|", true);

          boolean emptyColumn = true;
          boolean onlyOneElement = (st.countTokens() == 1);

          while (st.hasMoreElements() && index < alWidth)
          {
            token = st.nextToken().trim();

            if (onlyOneElement)
            {
              try
              {
                score = Float.valueOf(token).floatValue();
                break;
              } catch (NumberFormatException ex)
              {
              }
            }

            if (token.equals("|"))
            {
              if (emptyColumn)
              {
                index++;
              }

              emptyColumn = true;
            }
            else
            {
              annotations[index++] = parseAnnotation(token, graphStyle);
              emptyColumn = false;
            }
          }

        }

        annotation = new AlignmentAnnotation(label, description,
                (index == 0) ? null : annotations, 0, 0, graphStyle);

        annotation.score = score;
        if (!overrideAutoAnnot
                && autoAnnots.containsKey(autoAnnotsKey(annotation, refSeq,
                        groupRef)))
        {
          // skip - we've already got an automatic annotation of this type.
          continue;
        }
        // otherwise add it!
        if (refSeq != null)
        {

          annotation.belowAlignment = false;
          // make a copy of refSeq so we can find other matches in the alignment
          SequenceI referedSeq = refSeq;
          do
          {
            // copy before we do any mapping business.
            // TODO: verify that undo/redo with 1:many sequence associated
            // annotations can be undone correctly
            AlignmentAnnotation ann = new AlignmentAnnotation(annotation);
            annotation
                    .createSequenceMapping(referedSeq, refSeqIndex, false);
            annotation.adjustForAlignment();
            referedSeq.addAlignmentAnnotation(annotation);
            al.addAnnotation(annotation);
            al.setAnnotationIndex(annotation,
                    al.getAlignmentAnnotation().length
                            - existingAnnotations - 1);
            if (groupRef != null)
            {
              ((Vector) groupRefRows.get(groupRef)).addElement(annotation);
            }
            // and recover our virgin copy to use again if necessary.
            annotation = ann;

          } while (refSeqId != null
                  && (referedSeq = al.findName(referedSeq, refSeqId, true)) != null);
        }
        else
        {
          al.addAnnotation(annotation);
          al.setAnnotationIndex(annotation,
                  al.getAlignmentAnnotation().length - existingAnnotations
                          - 1);
          if (groupRef != null)
          {
            ((Vector) groupRefRows.get(groupRef)).addElement(annotation);
          }
        }
        // and set modification flag
        modified = true;
      }
      // Resolve the groupRefs
      Hashtable <String,SequenceGroup> groupRefLookup=new Hashtable<String,SequenceGroup>();
      Enumeration en = groupRefRows.keys();

      while (en.hasMoreElements())
      {
        groupRef = (String) en.nextElement();
        boolean matched = false;
        // Resolve group: TODO: add a getGroupByName method to alignments
        for (SequenceGroup theGroup : al.getGroups())
        {
          if (theGroup.getName().equals(groupRef))
          {
            if (matched)
            {
              // TODO: specify and implement duplication of alignment annotation
              // for multiple group references.
              System.err
                      .println("Ignoring 1:many group reference mappings for group name '"
                              + groupRef + "'");
            }
            else
            {
              matched = true;
              Vector rowset = (Vector) groupRefRows.get(groupRef);
              groupRefLookup.put(groupRef,  theGroup);
              if (rowset != null && rowset.size() > 0)
              {
                AlignmentAnnotation alan = null;
                for (int elm = 0, elmSize = rowset.size(); elm < elmSize; elm++)
                {
                  alan = (AlignmentAnnotation) rowset.elementAt(elm);
                  alan.groupRef = theGroup;
                }
              }
            }
          }
        }
        ((Vector) groupRefRows.get(groupRef)).removeAllElements();
      }
      // finally, combine all the annotation rows within each context.
      /**
       * number of combine statements in this annotation file. Used to create new groups for combined annotation graphs without disturbing existing ones
       */
      int combinecount = 0;
      for (Object[] _combine_args:combineAnnotation_calls) {
        combineAnnotations(al, 
                ++combinecount,
                (StringTokenizer) _combine_args[0], // st
                (SequenceI) _combine_args[1], // refSeq
                (_combine_args[2]==null) ? null : groupRefLookup.get((String)_combine_args[2]) // the reference group, or null
                );
      }

    }
    return modified;
  }

  private Object autoAnnotsKey(AlignmentAnnotation annotation,
          SequenceI refSeq, String groupRef)
  {
    return annotation.graph + "\t" + annotation.label + "\t"
            + annotation.description + "\t"
            + (refSeq != null ? refSeq.getDisplayId(true) : "");
  }

  Annotation parseAnnotation(String string, int graphStyle)
  {
    boolean hasSymbols = (graphStyle == AlignmentAnnotation.NO_GRAPH); // don't
    // do the
    // glyph
    // test
    // if we
    // don't
    // want
    // secondary
    // structure
    String desc = null, displayChar = null;
    char ss = ' '; // secondaryStructure
    float value = 0;
    boolean parsedValue = false, dcset = false;

    // find colour here
    java.awt.Color colour = null;
    int i = string.indexOf("[");
    int j = string.indexOf("]");
    if (i > -1 && j > -1)
    {
      UserColourScheme ucs = new UserColourScheme();

      colour = ucs.getColourFromString(string.substring(i + 1, j));
      if (i > 0 && string.charAt(i - 1) == ',')
      {
        // clip the preceding comma as well
        i--;
      }
      string = string.substring(0, i) + string.substring(j + 1);
    }

    StringTokenizer st = new StringTokenizer(string, ",", true);
    String token;
    boolean seenContent = false;
    int pass = 0;
    while (st.hasMoreTokens())
    {
      pass++;
      token = st.nextToken().trim();
      if (token.equals(","))
      {
        if (!seenContent && parsedValue && !dcset)
        {
          // allow the value below the bar/line to be empty
          dcset = true;
          displayChar = " ";
        }
        seenContent = false;
        continue;
      }
      else
      {
        seenContent = true;
      }

      if (!parsedValue)
      {
        try
        {
          displayChar = token;
          // foo
          value = new Float(token).floatValue();
          parsedValue = true;
          continue;
        } catch (NumberFormatException ex)
        {
        }
      }
      else
      {
        if (token.length() == 1)
        {
          displayChar = token;
        }
      }
      if (hasSymbols
              && (token.equals("H") || token.equals("E")
                      || token.equals("S") || token.equals(" ")))
      {
        // Either this character represents a helix or sheet
        // or an integer which can be displayed
        ss = token.charAt(0);
        if (displayChar.equals(token.substring(0, 1)))
        {
          displayChar = "";
        }
      }
      else if (desc == null || (parsedValue && pass > 2))
      {
        desc = token;
      }

    }
    // if (!dcset && string.charAt(string.length() - 1) == ',')
    // {
    // displayChar = " "; // empty display char symbol.
    // }
    if (displayChar != null && desc != null && desc.length() == 1)
    {
      if (displayChar.length() > 1)
      {
        // switch desc and displayChar - legacy support
        String tmp = displayChar;
        displayChar = desc;
        desc = tmp;
      }
      else
      {
        if (displayChar.equals(desc))
        {
          // duplicate label - hangover from the 'robust parser' above
          desc = null;
        }
      }
    }
    Annotation anot = new Annotation(displayChar, desc, ss, value);

    anot.colour = colour;

    return anot;
  }

  void colourAnnotations(AlignmentI al, String label, String colour)
  {
    UserColourScheme ucs = new UserColourScheme(colour);
    Annotation[] annotations;
    for (int i = 0; i < al.getAlignmentAnnotation().length; i++)
    {
      if (al.getAlignmentAnnotation()[i].label.equalsIgnoreCase(label))
      {
        annotations = al.getAlignmentAnnotation()[i].annotations;
        for (int j = 0; j < annotations.length; j++)
        {
          if (annotations[j] != null)
          {
            annotations[j].colour = ucs.findColour('A');
          }
        }
      }
    }
  }

  void combineAnnotations(AlignmentI al, int combineCount, StringTokenizer st, SequenceI seqRef, SequenceGroup groupRef)
  {
    String group = st.nextToken();
    // First make sure we are not overwriting the graphIndex
    int graphGroup=0;
    if (al.getAlignmentAnnotation() != null)
    {
      for (int i = 0; i < al.getAlignmentAnnotation().length; i++)
      {
        AlignmentAnnotation aa = al.getAlignmentAnnotation()[i];
        
        if (aa.graphGroup>graphGroup)
        {
          // try to number graphGroups in order of occurence.
          graphGroup=aa.graphGroup+1;
        }
        if (aa.sequenceRef==seqRef && aa.groupRef==groupRef && aa.label.equalsIgnoreCase(group))
        {
          if (aa.graphGroup>-1)
          {
            graphGroup = aa.graphGroup;
          } else {
            if (graphGroup <= combineCount)
            {
              graphGroup=combineCount+1;
            }
            aa.graphGroup = graphGroup;
          }
          break;
        }
      }

      // Now update groups
      while (st.hasMoreTokens())
      {
        group = st.nextToken();
        for (int i = 0; i < al.getAlignmentAnnotation().length; i++)
        {
          AlignmentAnnotation aa = al.getAlignmentAnnotation()[i];
          if (aa.sequenceRef==seqRef && aa.groupRef==groupRef && aa.label.equalsIgnoreCase(group))
          {
            aa.graphGroup = graphGroup;
            break;
          }
        }
      }
    }
    else
    {
      System.err
              .println("Couldn't combine annotations. None are added to alignment yet!");
    }
  }

  void addLine(AlignmentI al, StringTokenizer st)
  {
    String group = st.nextToken();
    AlignmentAnnotation annotation = null, alannot[] = al
            .getAlignmentAnnotation();
    if (alannot != null)
    {
      for (int i = 0; i < alannot.length; i++)
      {
        if (alannot[i].label.equalsIgnoreCase(group))
        {
          annotation = alannot[i];
          break;
        }
      }
    }
    if (annotation == null)
    {
      return;
    }
    float value = new Float(st.nextToken()).floatValue();
    String label = st.hasMoreTokens() ? st.nextToken() : null;
    java.awt.Color colour = null;
    if (st.hasMoreTokens())
    {
      UserColourScheme ucs = new UserColourScheme(st.nextToken());
      colour = ucs.findColour('A');
    }

    annotation.setThreshold(new GraphLine(value, label, colour));
  }

  void addGroup(AlignmentI al, StringTokenizer st)
  {
    SequenceGroup sg = new SequenceGroup();
    sg.setName(st.nextToken());
    String rng = "";
    try
    {
      rng = st.nextToken();
      if (rng.length() > 0 && !rng.startsWith("*"))
      {
        sg.setStartRes(Integer.parseInt(rng) - 1);
      }
      else
      {
        sg.setStartRes(0);
      }
      rng = st.nextToken();
      if (rng.length() > 0 && !rng.startsWith("*"))
      {
        sg.setEndRes(Integer.parseInt(rng) - 1);
      }
      else
      {
        sg.setEndRes(al.getWidth() - 1);
      }
    } catch (Exception e)
    {
      System.err
              .println("Couldn't parse Group Start or End Field as '*' or a valid column or sequence index: '"
                      + rng + "' - assuming alignment width for group.");
      // assume group is full width
      sg.setStartRes(0);
      sg.setEndRes(al.getWidth() - 1);
    }

    String index = st.nextToken();
    if (index.equals("-1"))
    {
      while (st.hasMoreElements())
      {
        sg.addSequence(al.findName(st.nextToken()), false);
      }
    }
    else
    {
      StringTokenizer st2 = new StringTokenizer(index, ",");

      while (st2.hasMoreTokens())
      {
        String tmp = st2.nextToken();
        if (tmp.equals("*"))
        {
          for (int i = 0; i < al.getHeight(); i++)
          {
            sg.addSequence(al.getSequenceAt(i), false);
          }
        }
        else if (tmp.indexOf("-") >= 0)
        {
          StringTokenizer st3 = new StringTokenizer(tmp, "-");

          int start = (Integer.parseInt(st3.nextToken()));
          int end = (Integer.parseInt(st3.nextToken()));

          if (end > start)
          {
            for (int i = start; i <= end; i++)
            {
              sg.addSequence(al.getSequenceAt(i - 1), false);
            }
          }
        }
        else
        {
          sg.addSequence(al.getSequenceAt(Integer.parseInt(tmp) - 1), false);
        }
      }
    }

    if (refSeq != null)
    {
      sg.setStartRes(refSeq.findIndex(sg.getStartRes() + 1) - 1);
      sg.setEndRes(refSeq.findIndex(sg.getEndRes() + 1) - 1);
      sg.setSeqrep(refSeq);
    }

    if (sg.getSize() > 0)
    {
      al.addGroup(sg);
    }
  }

  void addRowProperties(AlignmentI al, StringTokenizer st)
  {
    String label = st.nextToken(), keyValue, key, value;
    boolean scaletofit = false, centerlab = false, showalllabs = false;
    while (st.hasMoreTokens())
    {
      keyValue = st.nextToken();
      key = keyValue.substring(0, keyValue.indexOf("="));
      value = keyValue.substring(keyValue.indexOf("=") + 1);
      if (key.equalsIgnoreCase("scaletofit"))
      {
        scaletofit = Boolean.valueOf(value).booleanValue();
      }
      if (key.equalsIgnoreCase("showalllabs"))
      {
        showalllabs = Boolean.valueOf(value).booleanValue();
      }
      if (key.equalsIgnoreCase("centrelabs"))
      {
        centerlab = Boolean.valueOf(value).booleanValue();
      }
      AlignmentAnnotation[] alr = al.getAlignmentAnnotation();
      if (alr != null)
      {
        for (int i = 0; i < alr.length; i++)
        {
          if (alr[i].label.equalsIgnoreCase(label))
          {
            alr[i].centreColLabels = centerlab;
            alr[i].scaleColLabel = scaletofit;
            alr[i].showAllColLabels = showalllabs;
          }
        }
      }
    }
  }

  void addProperties(AlignmentI al, StringTokenizer st)
  {

    // So far we have only added groups to the annotationHash,
    // the idea is in the future properties can be added to
    // alignments, other annotations etc
    if (al.getGroups() == null)
    {
      return;
    }

    String name = st.nextToken();
    SequenceGroup sg = null;
    for (SequenceGroup _sg : al.getGroups())
    {
      if ((sg = _sg).getName().equals(name))
      {
        break;
      }
      else
      {
        sg = null;
      }
    }

    if (sg != null)
    {
      String keyValue, key, value;
      ColourSchemeI def = sg.cs;
      sg.cs = null;
      while (st.hasMoreTokens())
      {
        keyValue = st.nextToken();
        key = keyValue.substring(0, keyValue.indexOf("="));
        value = keyValue.substring(keyValue.indexOf("=") + 1);

        if (key.equalsIgnoreCase("description"))
        {
          sg.setDescription(value);
        }
        else if (key.equalsIgnoreCase("colour"))
        {
          sg.cs = ColourSchemeProperty.getColour(al, value);
        }
        else if (key.equalsIgnoreCase("pidThreshold"))
        {
          sg.cs.setThreshold(Integer.parseInt(value), true);

        }
        else if (key.equalsIgnoreCase("consThreshold"))
        {
          sg.cs.setConservationInc(Integer.parseInt(value));
          Conservation c = new Conservation("Group",
                  ResidueProperties.propHash, 3, sg.getSequences(null),
                  sg.getStartRes(), sg.getEndRes() + 1);

          c.calculate();
          c.verdict(false, 25); // TODO: refer to conservation percent threshold

          sg.cs.setConservation(c);

        }
        else if (key.equalsIgnoreCase("outlineColour"))
        {
          sg.setOutlineColour(new UserColourScheme(value).findColour('A'));
        }
        else if (key.equalsIgnoreCase("displayBoxes"))
        {
          sg.setDisplayBoxes(Boolean.valueOf(value).booleanValue());
        }
        else if (key.equalsIgnoreCase("showUnconserved"))
        {
          sg.setShowNonconserved(Boolean.valueOf(value).booleanValue());
        }
        else if (key.equalsIgnoreCase("displayText"))
        {
          sg.setDisplayText(Boolean.valueOf(value).booleanValue());
        }
        else if (key.equalsIgnoreCase("colourText"))
        {
          sg.setColourText(Boolean.valueOf(value).booleanValue());
        }
        else if (key.equalsIgnoreCase("textCol1"))
        {
          sg.textColour = new UserColourScheme(value).findColour('A');
        }
        else if (key.equalsIgnoreCase("textCol2"))
        {
          sg.textColour2 = new UserColourScheme(value).findColour('A');
        }
        else if (key.equalsIgnoreCase("textColThreshold"))
        {
          sg.thresholdTextColour = Integer.parseInt(value);
        }
        else if (key.equalsIgnoreCase("idColour"))
        {
          // consider warning if colour doesn't resolve to a real colour
          sg.setIdColour((def = new UserColourScheme(value))
                  .findColour('A'));
        }
        else if (key.equalsIgnoreCase("hide"))
        {
          // see bug https://mantis.lifesci.dundee.ac.uk/view.php?id=25847
          sg.setHidereps(true);
        }
        else if (key.equalsIgnoreCase("hidecols"))
        {
          // see bug https://mantis.lifesci.dundee.ac.uk/view.php?id=25847
          sg.setHideCols(true);
        }
        sg.recalcConservation();
      }
      if (sg.cs == null)
      {
        sg.cs = def;
      }
    }
  }

  void setBelowAlignment(AlignmentI al, StringTokenizer st)
  {
    String token;
    AlignmentAnnotation aa, ala[] = al.getAlignmentAnnotation();
    if (ala == null)
    {
      System.err
              .print("Warning - no annotation to set below for sequence associated annotation:");
    }
    while (st.hasMoreTokens())
    {
      token = st.nextToken();
      if (ala == null)
      {
        System.err.print(" " + token);
      }
      else
      {
        for (int i = 0; i < al.getAlignmentAnnotation().length; i++)
        {
          aa = al.getAlignmentAnnotation()[i];
          if (aa.sequenceRef == refSeq && aa.label.equals(token))
          {
            aa.belowAlignment = true;
          }
        }
      }
    }
    if (ala == null)
    {
      System.err.print("\n");
    }
  }

  void addAlignmentDetails(AlignmentI al, StringTokenizer st)
  {
    String keyValue, key, value;
    while (st.hasMoreTokens())
    {
      keyValue = st.nextToken();
      key = keyValue.substring(0, keyValue.indexOf("="));
      value = keyValue.substring(keyValue.indexOf("=") + 1);
      al.setProperty(key, value);
    }
  }

  /**
   * Write annotations as a CSV file of the form 'label, value, value, ...' for
   * each row.
   * 
   * @param annotations
   * @return CSV file as a string.
   */
  public String printCSVAnnotations(AlignmentAnnotation[] annotations)
  {
    StringBuffer sp = new StringBuffer();
    for (int i = 0; i < annotations.length; i++)
    {
      String atos = annotations[i].toString();
      int p = 0;
      do
      {
        int cp = atos.indexOf("\n", p);
        sp.append(annotations[i].label);
        sp.append(",");
        if (cp > p)
        {
          sp.append(atos.substring(p, cp + 1));
        }
        else
        {
          sp.append(atos.substring(p));
          sp.append(newline);
        }
        p = cp + 1;
      } while (p > 0);
    }
    return sp.toString();
  }
}
