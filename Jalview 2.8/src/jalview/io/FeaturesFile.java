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
import java.util.*;

import jalview.analysis.SequenceIdMatcher;
import jalview.datamodel.*;
import jalview.schemes.*;
import jalview.util.Format;

/**
 * Parse and create Jalview Features files Detects GFF format features files and
 * parses. Does not implement standard print() - call specific printFeatures or
 * printGFF. Uses AlignmentI.findSequence(String id) to find the sequence object
 * for the features annotation - this normally works on an exact match.
 * 
 * @author AMW
 * @version $Revision$
 */
public class FeaturesFile extends AlignFile
{
  /**
   * work around for GFF interpretation bug where source string becomes
   * description rather than a group
   */
  private boolean doGffSource = true;

  /**
   * Creates a new FeaturesFile object.
   */
  public FeaturesFile()
  {
  }

  /**
   * Creates a new FeaturesFile object.
   * 
   * @param inFile
   *          DOCUMENT ME!
   * @param type
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public FeaturesFile(String inFile, String type) throws IOException
  {
    super(inFile, type);
  }

  public FeaturesFile(FileParse source) throws IOException
  {
    super(source);
  }

  /**
   * Parse GFF or sequence features file using case-independent matching,
   * discarding URLs
   * 
   * @param align
   *          - alignment/dataset containing sequences that are to be annotated
   * @param colours
   *          - hashtable to store feature colour definitions
   * @param removeHTML
   *          - process html strings into plain text
   * @return true if features were added
   */
  public boolean parse(AlignmentI align, Hashtable colours,
          boolean removeHTML)
  {
    return parse(align, colours, null, removeHTML, false);
  }

  /**
   * Parse GFF or sequence features file optionally using case-independent
   * matching, discarding URLs
   * 
   * @param align
   *          - alignment/dataset containing sequences that are to be annotated
   * @param colours
   *          - hashtable to store feature colour definitions
   * @param removeHTML
   *          - process html strings into plain text
   * @param relaxedIdmatching
   *          - when true, ID matches to compound sequence IDs are allowed
   * @return true if features were added
   */
  public boolean parse(AlignmentI align, Map colours, boolean removeHTML,
          boolean relaxedIdMatching)
  {
    return parse(align, colours, null, removeHTML, relaxedIdMatching);
  }

  /**
   * Parse GFF or sequence features file optionally using case-independent
   * matching
   * 
   * @param align
   *          - alignment/dataset containing sequences that are to be annotated
   * @param colours
   *          - hashtable to store feature colour definitions
   * @param featureLink
   *          - hashtable to store associated URLs
   * @param removeHTML
   *          - process html strings into plain text
   * @return true if features were added
   */
  public boolean parse(AlignmentI align, Map colours, Map featureLink,
          boolean removeHTML)
  {
    return parse(align, colours, featureLink, removeHTML, false);
  }

  /**
   * /** Parse GFF or sequence features file
   * 
   * @param align
   *          - alignment/dataset containing sequences that are to be annotated
   * @param colours
   *          - hashtable to store feature colour definitions
   * @param featureLink
   *          - hashtable to store associated URLs
   * @param removeHTML
   *          - process html strings into plain text
   * @param relaxedIdmatching
   *          - when true, ID matches to compound sequence IDs are allowed
   * @return true if features were added
   */
  public boolean parse(AlignmentI align, Map colours, Map featureLink,
          boolean removeHTML, boolean relaxedIdmatching)
  {

    String line = null;
    try
    {
      SequenceI seq = null;
      String type, desc, token = null;

      int index, start, end;
      float score;
      StringTokenizer st;
      SequenceFeature sf;
      String featureGroup = null, groupLink = null;
      Map typeLink = new Hashtable();
      /**
       * when true, assume GFF style features rather than Jalview style.
       */
      boolean GFFFile = true;
      while ((line = nextLine()) != null)
      {
        if (line.startsWith("#"))
        {
          continue;
        }

        st = new StringTokenizer(line, "\t");
        if (st.countTokens() == 1)
        {
          if (line.trim().equalsIgnoreCase("GFF"))
          {
            // Start parsing file as if it might be GFF again.
            GFFFile = true;
            continue;
          }
        }
        if (st.countTokens() > 1 && st.countTokens() < 4)
        {
          GFFFile = false;
          type = st.nextToken();
          if (type.equalsIgnoreCase("startgroup"))
          {
            featureGroup = st.nextToken();
            if (st.hasMoreElements())
            {
              groupLink = st.nextToken();
              featureLink.put(featureGroup, groupLink);
            }
          }
          else if (type.equalsIgnoreCase("endgroup"))
          {
            // We should check whether this is the current group,
            // but at present theres no way of showing more than 1 group
            st.nextToken();
            featureGroup = null;
            groupLink = null;
          }
          else
          {
            Object colour = null;
            String colscheme = st.nextToken();
            if (colscheme.indexOf("|") > -1
                    || colscheme.trim().equalsIgnoreCase("label"))
            {
              // Parse '|' separated graduated colourscheme fields:
              // [label|][mincolour|maxcolour|[absolute|]minvalue|maxvalue|thresholdtype|thresholdvalue]
              // can either provide 'label' only, first is optional, next two
              // colors are required (but may be
              // left blank), next is optional, nxt two min/max are required.
              // first is either 'label'
              // first/second and third are both hexadecimal or word equivalent
              // colour.
              // next two are values parsed as floats.
              // fifth is either 'above','below', or 'none'.
              // sixth is a float value and only required when fifth is either
              // 'above' or 'below'.
              StringTokenizer gcol = new StringTokenizer(colscheme, "|",
                      true);
              // set defaults
              int threshtype = AnnotationColourGradient.NO_THRESHOLD;
              float min = Float.MIN_VALUE, max = Float.MAX_VALUE, threshval = Float.NaN;
              boolean labelCol = false;
              // Parse spec line
              String mincol = gcol.nextToken();
              if (mincol == "|")
              {
                System.err
                        .println("Expected either 'label' or a colour specification in the line: "
                                + line);
                continue;
              }
              String maxcol = null;
              if (mincol.toLowerCase().indexOf("label") == 0)
              {
                labelCol = true;
                mincol = (gcol.hasMoreTokens() ? gcol.nextToken() : null); // skip
                                                                           // '|'
                mincol = (gcol.hasMoreTokens() ? gcol.nextToken() : null);
              }
              String abso = null, minval, maxval;
              if (mincol != null)
              {
                // at least four more tokens
                if (mincol.equals("|"))
                {
                  mincol = "";
                }
                else
                {
                  gcol.nextToken(); // skip next '|'
                }
                // continue parsing rest of line
                maxcol = gcol.nextToken();
                if (maxcol.equals("|"))
                {
                  maxcol = "";
                }
                else
                {
                  gcol.nextToken(); // skip next '|'
                }
                abso = gcol.nextToken();
                gcol.nextToken(); // skip next '|'
                if (abso.toLowerCase().indexOf("abso") != 0)
                {
                  minval = abso;
                  abso = null;
                }
                else
                {
                  minval = gcol.nextToken();
                  gcol.nextToken(); // skip next '|'
                }
                maxval = gcol.nextToken();
                if (gcol.hasMoreTokens())
                {
                  gcol.nextToken(); // skip next '|'
                }
                try
                {
                  if (minval.length() > 0)
                  {
                    min = new Float(minval).floatValue();
                  }
                } catch (Exception e)
                {
                  System.err
                          .println("Couldn't parse the minimum value for graduated colour for type ("
                                  + colscheme
                                  + ") - did you misspell 'auto' for the optional automatic colour switch ?");
                  e.printStackTrace();
                }
                try
                {
                  if (maxval.length() > 0)
                  {
                    max = new Float(maxval).floatValue();
                  }
                } catch (Exception e)
                {
                  System.err
                          .println("Couldn't parse the maximum value for graduated colour for type ("
                                  + colscheme + ")");
                  e.printStackTrace();
                }
              }
              else
              {
                // add in some dummy min/max colours for the label-only
                // colourscheme.
                mincol = "FFFFFF";
                maxcol = "000000";
              }
              try
              {
                colour = new jalview.schemes.GraduatedColor(
                        new UserColourScheme(mincol).findColour('A'),
                        new UserColourScheme(maxcol).findColour('A'), min,
                        max);
              } catch (Exception e)
              {
                System.err
                        .println("Couldn't parse the graduated colour scheme ("
                                + colscheme + ")");
                e.printStackTrace();
              }
              if (colour != null)
              {
                ((jalview.schemes.GraduatedColor) colour)
                        .setColourByLabel(labelCol);
                ((jalview.schemes.GraduatedColor) colour)
                        .setAutoScaled(abso == null);
                // add in any additional parameters
                String ttype = null, tval = null;
                if (gcol.hasMoreTokens())
                {
                  // threshold type and possibly a threshold value
                  ttype = gcol.nextToken();
                  if (ttype.toLowerCase().startsWith("below"))
                  {
                    ((jalview.schemes.GraduatedColor) colour)
                            .setThreshType(AnnotationColourGradient.BELOW_THRESHOLD);
                  }
                  else if (ttype.toLowerCase().startsWith("above"))
                  {
                    ((jalview.schemes.GraduatedColor) colour)
                            .setThreshType(AnnotationColourGradient.ABOVE_THRESHOLD);
                  }
                  else
                  {
                    ((jalview.schemes.GraduatedColor) colour)
                            .setThreshType(AnnotationColourGradient.NO_THRESHOLD);
                    if (!ttype.toLowerCase().startsWith("no"))
                    {
                      System.err
                              .println("Ignoring unrecognised threshold type : "
                                      + ttype);
                    }
                  }
                }
                if (((GraduatedColor) colour).getThreshType() != AnnotationColourGradient.NO_THRESHOLD)
                {
                  try
                  {
                    gcol.nextToken();
                    tval = gcol.nextToken();
                    ((jalview.schemes.GraduatedColor) colour)
                            .setThresh(new Float(tval).floatValue());
                  } catch (Exception e)
                  {
                    System.err
                            .println("Couldn't parse threshold value as a float: ("
                                    + tval + ")");
                    e.printStackTrace();
                  }
                }
                // parse the thresh-is-min token ?
                if (gcol.hasMoreTokens())
                {
                  System.err
                          .println("Ignoring additional tokens in parameters in graduated colour specification\n");
                  while (gcol.hasMoreTokens())
                  {
                    System.err.println("|" + gcol.nextToken());
                  }
                  System.err.println("\n");
                }
              }
            }
            else
            {
              UserColourScheme ucs = new UserColourScheme(colscheme);
              colour = ucs.findColour('A');
            }
            if (colour != null)
            {
              colours.put(type, colour);
            }
            if (st.hasMoreElements())
            {
              String link = st.nextToken();
              typeLink.put(type, link);
              if (featureLink == null)
              {
                featureLink = new Hashtable();
              }
              featureLink.put(type, link);
            }
          }
          continue;
        }
        String seqId = "";
        while (st.hasMoreElements())
        {

          if (GFFFile)
          {
            // Still possible this is an old Jalview file,
            // which does not have type colours at the beginning
            seqId = token = st.nextToken();
            seq = findName(align, seqId, relaxedIdmatching);
            if (seq != null)
            {
              desc = st.nextToken();
              String group = null;
              if (doGffSource && desc.indexOf(' ') == -1)
              {
                // could also be a source term rather than description line
                group = new String(desc);
              }
              type = st.nextToken();
              try
              {
                String stt = st.nextToken();
                if (stt.length() == 0 || stt.equals("-"))
                {
                  start = 0;
                }
                else
                {
                  start = Integer.parseInt(stt);
                }
              } catch (NumberFormatException ex)
              {
                start = 0;
              }
              try
              {
                String stt = st.nextToken();
                if (stt.length() == 0 || stt.equals("-"))
                {
                  end = 0;
                }
                else
                {
                  end = Integer.parseInt(stt);
                }
              } catch (NumberFormatException ex)
              {
                end = 0;
              }
              // TODO: decide if non positional feature assertion for input data
              // where end==0 is generally valid
              if (end == 0)
              {
                // treat as non-positional feature, regardless.
                start = 0;
              }
              try
              {
                score = new Float(st.nextToken()).floatValue();
              } catch (NumberFormatException ex)
              {
                score = 0;
              }

              sf = new SequenceFeature(type, desc, start, end, score, group);

              try
              {
                sf.setValue("STRAND", st.nextToken());
                sf.setValue("FRAME", st.nextToken());
              } catch (Exception ex)
              {
              }

              if (st.hasMoreTokens())
              {
                StringBuffer attributes = new StringBuffer();
                while (st.hasMoreTokens())
                {
                  attributes.append("\t" + st.nextElement());
                }
                // TODO validate and split GFF2 attributes field ? parse out
                // ([A-Za-z][A-Za-z0-9_]*) <value> ; and add as
                // sf.setValue(attrib, val);
                sf.setValue("ATTRIBUTES", attributes.toString());
              }

              seq.addSequenceFeature(sf);
              while ((seq = align.findName(seq, seqId, true)) != null)
              {
                seq.addSequenceFeature(new SequenceFeature(sf));
              }
              break;
            }
          }

          if (GFFFile && seq == null)
          {
            desc = token;
          }
          else
          {
            desc = st.nextToken();
          }
          if (!st.hasMoreTokens())
          {
            System.err
                    .println("DEBUG: Run out of tokens when trying to identify the destination for the feature.. giving up.");
            // in all probability, this isn't a file we understand, so bail
            // quietly.
            return false;
          }

          token = st.nextToken();

          if (!token.equals("ID_NOT_SPECIFIED"))
          {
            seq = findName(align, seqId = token, relaxedIdmatching);
            st.nextToken();
          }
          else
          {
            seqId = null;
            try
            {
              index = Integer.parseInt(st.nextToken());
              seq = align.getSequenceAt(index);
            } catch (NumberFormatException ex)
            {
              seq = null;
            }
          }

          if (seq == null)
          {
            System.out.println("Sequence not found: " + line);
            break;
          }

          start = Integer.parseInt(st.nextToken());
          end = Integer.parseInt(st.nextToken());

          type = st.nextToken();

          if (!colours.containsKey(type))
          {
            // Probably the old style groups file
            UserColourScheme ucs = new UserColourScheme(type);
            colours.put(type, ucs.findColour('A'));
          }
          sf = new SequenceFeature(type, desc, "", start, end, featureGroup);
          if (st.hasMoreTokens())
          {
            try
            {
              score = new Float(st.nextToken()).floatValue();
              // update colourgradient bounds if allowed to
            } catch (NumberFormatException ex)
            {
              score = 0;
            }
            sf.setScore(score);
          }
          if (groupLink != null && removeHTML)
          {
            sf.addLink(groupLink);
            sf.description += "%LINK%";
          }
          if (typeLink.containsKey(type) && removeHTML)
          {
            sf.addLink(typeLink.get(type).toString());
            sf.description += "%LINK%";
          }

          parseDescriptionHTML(sf, removeHTML);

          seq.addSequenceFeature(sf);

          while (seqId != null
                  && (seq = align.findName(seq, seqId, false)) != null)
          {
            seq.addSequenceFeature(new SequenceFeature(sf));
          }
          // If we got here, its not a GFFFile
          GFFFile = false;
        }
      }
      resetMatcher();
    } catch (Exception ex)
    {
      System.out.println(line);
      System.out.println("Error parsing feature file: " + ex + "\n" + line);
      ex.printStackTrace(System.err);
      resetMatcher();
      return false;
    }

    return true;
  }

  private AlignmentI lastmatchedAl = null;

  private SequenceIdMatcher matcher = null;

  /**
   * clear any temporary handles used to speed up ID matching
   */
  private void resetMatcher()
  {
    lastmatchedAl = null;
    matcher = null;
  }

  private SequenceI findName(AlignmentI align, String seqId,
          boolean relaxedIdMatching)
  {
    SequenceI match = null;
    if (relaxedIdMatching)
    {
      if (lastmatchedAl != align)
      {
        matcher = new SequenceIdMatcher(
                (lastmatchedAl = align).getSequencesArray());
      }
      match = matcher.findIdMatch(seqId);
    }
    else
    {
      match = align.findName(seqId, true);
    }
    return match;
  }

  public void parseDescriptionHTML(SequenceFeature sf, boolean removeHTML)
  {
    if (sf.getDescription() == null)
    {
      return;
    }
    jalview.util.ParseHtmlBodyAndLinks parsed = new jalview.util.ParseHtmlBodyAndLinks(
            sf.getDescription(), removeHTML, newline);

    sf.description = (removeHTML) ? parsed.getNonHtmlContent()
            : sf.description;
    for (String link : parsed.getLinks())
    {
      sf.addLink(link);
    }

  }

  /**
   * generate a features file for seqs includes non-pos features by default.
   * 
   * @param seqs
   *          source of sequence features
   * @param visible
   *          hash of feature types and colours
   * @return features file contents
   */
  public String printJalviewFormat(SequenceI[] seqs, Hashtable visible)
  {
    return printJalviewFormat(seqs, visible, true, true);
  }

  /**
   * generate a features file for seqs with colours from visible (if any)
   * 
   * @param seqs
   *          source of features
   * @param visible
   *          hash of Colours for each feature type
   * @param visOnly
   *          when true only feature types in 'visible' will be output
   * @param nonpos
   *          indicates if non-positional features should be output (regardless
   *          of group or type)
   * @return features file contents
   */
  public String printJalviewFormat(SequenceI[] seqs, Hashtable visible,
          boolean visOnly, boolean nonpos)
  {
    StringBuffer out = new StringBuffer();
    SequenceFeature[] next;
    boolean featuresGen = false;
    if (visOnly && !nonpos && (visible == null || visible.size() < 1))
    {
      // no point continuing.
      return "No Features Visible";
    }

    if (visible != null && visOnly)
    {
      // write feature colours only if we're given them and we are generating
      // viewed features
      // TODO: decide if feature links should also be written here ?
      Enumeration en = visible.keys();
      String type, color;
      while (en.hasMoreElements())
      {
        type = en.nextElement().toString();

        if (visible.get(type) instanceof GraduatedColor)
        {
          GraduatedColor gc = (GraduatedColor) visible.get(type);
          color = (gc.isColourByLabel() ? "label|" : "")
                  + Format.getHexString(gc.getMinColor()) + "|"
                  + Format.getHexString(gc.getMaxColor())
                  + (gc.isAutoScale() ? "|" : "|abso|") + gc.getMin() + "|"
                  + gc.getMax() + "|";
          if (gc.getThreshType() != AnnotationColourGradient.NO_THRESHOLD)
          {
            if (gc.getThreshType() == AnnotationColourGradient.BELOW_THRESHOLD)
            {
              color += "below";
            }
            else
            {
              if (gc.getThreshType() != AnnotationColourGradient.ABOVE_THRESHOLD)
              {
                System.err.println("WARNING: Unsupported threshold type ("
                        + gc.getThreshType() + ") : Assuming 'above'");
              }
              color += "above";
            }
            // add the value
            color += "|" + gc.getThresh();
          }
          else
          {
            color += "none";
          }
        }
        else if (visible.get(type) instanceof java.awt.Color)
        {
          color = Format.getHexString((java.awt.Color) visible.get(type));
        }
        else
        {
          // legacy support for integer objects containing colour triplet values
          color = Format.getHexString(new java.awt.Color(Integer
                  .parseInt(visible.get(type).toString())));
        }
        out.append(type);
        out.append("\t");
        out.append(color);
        out.append(newline);
      }
    }
    // Work out which groups are both present and visible
    Vector groups = new Vector();
    int groupIndex = 0;
    boolean isnonpos = false;

    for (int i = 0; i < seqs.length; i++)
    {
      next = seqs[i].getSequenceFeatures();
      if (next != null)
      {
        for (int j = 0; j < next.length; j++)
        {
          isnonpos = next[j].begin == 0 && next[j].end == 0;
          if ((!nonpos && isnonpos)
                  || (!isnonpos && visOnly && !visible
                          .containsKey(next[j].type)))
          {
            continue;
          }

          if (next[j].featureGroup != null
                  && !groups.contains(next[j].featureGroup))
          {
            groups.addElement(next[j].featureGroup);
          }
        }
      }
    }

    String group = null;
    do
    {

      if (groups.size() > 0 && groupIndex < groups.size())
      {
        group = groups.elementAt(groupIndex).toString();
        out.append(newline);
        out.append("STARTGROUP\t");
        out.append(group);
        out.append(newline);
      }
      else
      {
        group = null;
      }

      for (int i = 0; i < seqs.length; i++)
      {
        next = seqs[i].getSequenceFeatures();
        if (next != null)
        {
          for (int j = 0; j < next.length; j++)
          {
            isnonpos = next[j].begin == 0 && next[j].end == 0;
            if ((!nonpos && isnonpos)
                    || (!isnonpos && visOnly && !visible
                            .containsKey(next[j].type)))
            {
              // skip if feature is nonpos and we ignore them or if we only
              // output visible and it isn't non-pos and it's not visible
              continue;
            }

            if (group != null
                    && (next[j].featureGroup == null || !next[j].featureGroup
                            .equals(group)))
            {
              continue;
            }

            if (group == null && next[j].featureGroup != null)
            {
              continue;
            }
            // we have features to output
            featuresGen = true;
            if (next[j].description == null
                    || next[j].description.equals(""))
            {
              out.append(next[j].type + "\t");
            }
            else
            {
              if (next[j].links != null
                      && next[j].getDescription().indexOf("<html>") == -1)
              {
                out.append("<html>");
              }

              out.append(next[j].description + " ");
              if (next[j].links != null)
              {
                for (int l = 0; l < next[j].links.size(); l++)
                {
                  String label = next[j].links.elementAt(l).toString();
                  String href = label.substring(label.indexOf("|") + 1);
                  label = label.substring(0, label.indexOf("|"));

                  if (next[j].description.indexOf(href) == -1)
                  {
                    out.append("<a href=\"" + href + "\">" + label + "</a>");
                  }
                }

                if (next[j].getDescription().indexOf("</html>") == -1)
                {
                  out.append("</html>");
                }
              }

              out.append("\t");
            }
            out.append(seqs[i].getName());
            out.append("\t-1\t");
            out.append(next[j].begin);
            out.append("\t");
            out.append(next[j].end);
            out.append("\t");
            out.append(next[j].type);
            if (next[j].score != Float.NaN)
            {
              out.append("\t");
              out.append(next[j].score);
            }
            out.append(newline);
          }
        }
      }

      if (group != null)
      {
        out.append("ENDGROUP\t");
        out.append(group);
        out.append(newline);
        groupIndex++;
      }
      else
      {
        break;
      }

    } while (groupIndex < groups.size() + 1);

    if (!featuresGen)
    {
      return "No Features Visible";
    }

    return out.toString();
  }

  /**
   * generate a gff file for sequence features includes non-pos features by
   * default.
   * 
   * @param seqs
   * @param visible
   * @return
   */
  public String printGFFFormat(SequenceI[] seqs, Hashtable visible)
  {
    return printGFFFormat(seqs, visible, true, true);
  }

  public String printGFFFormat(SequenceI[] seqs, Hashtable visible,
          boolean visOnly, boolean nonpos)
  {
    StringBuffer out = new StringBuffer();
    SequenceFeature[] next;
    String source;
    boolean isnonpos;
    for (int i = 0; i < seqs.length; i++)
    {
      if (seqs[i].getSequenceFeatures() != null)
      {
        next = seqs[i].getSequenceFeatures();
        for (int j = 0; j < next.length; j++)
        {
          isnonpos = next[j].begin == 0 && next[j].end == 0;
          if ((!nonpos && isnonpos)
                  || (!isnonpos && visOnly && !visible
                          .containsKey(next[j].type)))
          {
            continue;
          }

          source = next[j].featureGroup;
          if (source == null)
          {
            source = next[j].getDescription();
          }

          out.append(seqs[i].getName());
          out.append("\t");
          out.append(source);
          out.append("\t");
          out.append(next[j].type);
          out.append("\t");
          out.append(next[j].begin);
          out.append("\t");
          out.append(next[j].end);
          out.append("\t");
          out.append(next[j].score);
          out.append("\t");

          if (next[j].getValue("STRAND") != null)
          {
            out.append(next[j].getValue("STRAND"));
            out.append("\t");
          }
          else
          {
            out.append(".\t");
          }

          if (next[j].getValue("FRAME") != null)
          {
            out.append(next[j].getValue("FRAME"));
          }
          else
          {
            out.append(".");
          }
          // TODO: verify/check GFF - should there be a /t here before attribute
          // output ?

          if (next[j].getValue("ATTRIBUTES") != null)
          {
            out.append(next[j].getValue("ATTRIBUTES"));
          }

          out.append(newline);

        }
      }
    }

    return out.toString();
  }

  /**
   * this is only for the benefit of object polymorphism - method does nothing.
   */
  public void parse()
  {
    // IGNORED
  }

  /**
   * this is only for the benefit of object polymorphism - method does nothing.
   * 
   * @return error message
   */
  public String print()
  {
    return "USE printGFFFormat() or printJalviewFormat()";
  }

}
