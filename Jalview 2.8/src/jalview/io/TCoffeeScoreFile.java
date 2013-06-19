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

import jalview.analysis.SequenceIdMatcher;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.Annotation;
import jalview.datamodel.SequenceI;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A file parse for T-Coffee score ascii format. This file contains the
 * alignment consensus for each resude in any sequence.
 * <p>
 * This file is procuded by <code>t_coffee</code> providing the option
 * <code>-output=score_ascii </code> to the program command line
 * 
 * An example file is the following
 * 
 * <pre>
 * T-COFFEE, Version_9.02.r1228 (2012-02-16 18:15:12 - Revision 1228 - Build 336)
 * Cedric Notredame 
 * CPU TIME:0 sec.
 * SCORE=90
 * *
 *  BAD AVG GOOD
 * *
 * 1PHT   :  89
 * 1BB9   :  90
 * 1UHC   :  94
 * 1YCS   :  94
 * 1OOT   :  93
 * 1ABO   :  94
 * 1FYN   :  94
 * 1QCF   :  94
 * cons   :  90
 * 
 * 1PHT   999999999999999999999999998762112222543211112134
 * 1BB9   99999999999999999999999999987-------4322----2234
 * 1UHC   99999999999999999999999999987-------5321----2246
 * 1YCS   99999999999999999999999999986-------4321----1-35
 * 1OOT   999999999999999999999999999861-------3------1135
 * 1ABO   99999999999999999999999999986-------422-------34
 * 1FYN   99999999999999999999999999985-------32--------35
 * 1QCF   99999999999999999999999999974-------2---------24
 * cons   999999999999999999999999999851000110321100001134
 * 
 * 
 * 1PHT   ----------5666642367889999999999889
 * 1BB9   1111111111676653-355679999999999889
 * 1UHC   ----------788774--66789999999999889
 * 1YCS   ----------78777--356789999999999889
 * 1OOT   ----------78877--356789999999997-67
 * 1ABO   ----------687774--56779999999999889
 * 1FYN   ----------6888842356789999999999889
 * 1QCF   ----------6878742356789999999999889
 * cons   00100000006877641356789999999999889
 * </pre>
 * 
 * 
 * @author Paolo Di Tommaso
 * 
 */
public class TCoffeeScoreFile extends AlignFile
{

  public TCoffeeScoreFile(String inFile, String type) throws IOException
  {
    super(inFile, type);

  }

  public TCoffeeScoreFile(FileParse source) throws IOException
  {
    super(source);
  }

  /** The {@link Header} structure holder */
  Header header;

  /**
   * Holds the consensues values for each sequences. It uses a LinkedHashMap to
   * maintaint the insertion order.
   */
  LinkedHashMap<String, StringBuilder> scores;

  Integer fWidth;

  /**
   * Parse the provided reader for the T-Coffee scores file format
   * 
   * @param reader
   *          public static TCoffeeScoreFile load(Reader reader) {
   * 
   *          try { BufferedReader in = (BufferedReader) (reader instanceof
   *          BufferedReader ? reader : new BufferedReader(reader));
   *          TCoffeeScoreFile result = new TCoffeeScoreFile();
   *          result.doParsing(in); return result.header != null &&
   *          result.scores != null ? result : null; } catch( Exception e) {
   *          throw new RuntimeException(e); } }
   */

  /**
   * @return The 'height' of the score matrix i.e. the numbers of score rows
   *         that should matches the number of sequences in the alignment
   */
  public int getHeight()
  {
    // the last entry will always be the 'global' alingment consensus scores, so
    // it is removed
    // from the 'height' count to make this value compatible with the number of
    // sequences in the MSA
    return scores != null && scores.size() > 0 ? scores.size() - 1 : 0;
  }

  /**
   * @return The 'width' of the score matrix i.e. the number of columns. Since
   *         the score value are supposed to be calculated for an 'aligned' MSA,
   *         all the entries have to have the same width.
   */
  public int getWidth()
  {
    return fWidth != null ? fWidth : 0;
  }

  /**
   * Get the string of score values for the specified seqeunce ID.
   * 
   * @param id
   *          The sequence ID
   * @return The scores as a string of values e.g. {@code 99999987-------432}.
   *         It return an empty string when the specified ID is missing.
   */
  public String getScoresFor(String id)
  {
    return scores != null && scores.containsKey(id) ? scores.get(id)
            .toString() : "";
  }

  /**
   * @return The list of score string as a {@link List} object, in the same
   *         ordeer of the insertion i.e. in the MSA
   */
  public List<String> getScoresList()
  {
    if (scores == null)
    {
      return null;
    }
    List<String> result = new ArrayList<String>(scores.size());
    for (Map.Entry<String, StringBuilder> it : scores.entrySet())
    {
      result.add(it.getValue().toString());
    }

    return result;
  }

  /**
   * @return The parsed score values a matrix of bytes
   */
  public byte[][] getScoresArray()
  {
    if (scores == null)
    {
      return null;
    }
    byte[][] result = new byte[scores.size()][];

    int rowCount = 0;
    for (Map.Entry<String, StringBuilder> it : scores.entrySet())
    {
      String line = it.getValue().toString();
      byte[] seqValues = new byte[line.length()];
      for (int j = 0, c = line.length(); j < c; j++)
      {

        byte val = (byte) (line.charAt(j) - '0');

        seqValues[j] = (val >= 0 && val <= 9) ? val : -1;
      }

      result[rowCount++] = seqValues;
    }

    return result;
  }

  public void parse() throws IOException
  {
    /*
     * read the header
     */
    header = readHeader(this);

    if (header == null)
    {
      error = true;
      return;
    }
    scores = new LinkedHashMap<String, StringBuilder>();

    /*
     * initilize the structure
     */
    for (Map.Entry<String, Integer> entry : header.scores.entrySet())
    {
      scores.put(entry.getKey(), new StringBuilder());
    }

    /*
     * go with the reading
     */
    Block block;
    while ((block = readBlock(this, header.scores.size())) != null)
    {

      /*
       * append sequences read in the block
       */
      for (Map.Entry<String, String> entry : block.items.entrySet())
      {
        StringBuilder scoreStringBuilder = scores.get(entry.getKey());
        if (scoreStringBuilder == null)
        {
          error = true;
          errormessage = String
                  .format("Invalid T-Coffee score file: Sequence ID '%s' is not declared in header section",
                          entry.getKey());
          return;
        }

        scoreStringBuilder.append(entry.getValue());
      }
    }

    /*
     * verify that all rows have the same width
     */
    for (StringBuilder str : scores.values())
    {
      if (fWidth == null)
      {
        fWidth = str.length();
      }
      else if (fWidth != str.length())
      {
        error = true;
        errormessage = "Invalid T-Coffee score file: All the score sequences must have the same length";
        return;
      }
    }

    return;
  }

  static int parseInt(String str)
  {
    try
    {
      return Integer.parseInt(str);
    } catch (NumberFormatException e)
    {
      // TODO report a warning ?
      return 0;
    }
  }

  /**
   * Reaad the header section in the T-Coffee score file format
   * 
   * @param reader
   *          The scores reader
   * @return The parser {@link Header} instance
   * @throws RuntimeException
   *           when the header is not in the expected format
   */
  static Header readHeader(FileParse reader) throws IOException
  {

    Header result = null;
    try
    {
      result = new Header();
      result.head = reader.nextLine();

      String line;

      while ((line = reader.nextLine()) != null)
      {
        if (line.startsWith("SCORE="))
        {
          result.score = parseInt(line.substring(6).trim());
          break;
        }
      }

      if ((line = reader.nextLine()) == null || !"*".equals(line.trim()))
      {
        error(reader,
                "Invalid T-COFFEE score format (NO BAD/AVG/GOOD header)");
        return null;
      }
      if ((line = reader.nextLine()) == null
              || !"BAD AVG GOOD".equals(line.trim()))
      {
        error(reader,
                "Invalid T-COFFEE score format (NO BAD/AVG/GOOD header)");
        return null;
      }
      if ((line = reader.nextLine()) == null || !"*".equals(line.trim()))
      {
        error(reader,
                "Invalid T-COFFEE score format (NO BAD/AVG/GOOD header)");
        return null;
      }

      /*
       * now are expected a list if sequences ID up to the first blank line
       */
      while ((line = reader.nextLine()) != null)
      {
        if ("".equals(line))
        {
          break;
        }

        int p = line.indexOf(":");
        if (p == -1)
        {
          // TODO report a warning
          continue;
        }

        String id = line.substring(0, p).trim();
        int val = parseInt(line.substring(p + 1).trim());
        if ("".equals(id))
        {
          // TODO report warning
          continue;
        }

        result.scores.put(id, val);
      }

      if (result == null)
      {
        error(reader, "T-COFFEE score file had no per-sequence scores");
      }

    } catch (IOException e)
    {
      error(reader, "Unexpected problem parsing T-Coffee score ascii file");
      throw e;
    }

    return result;
  }

  private static void error(FileParse reader, String errm)
  {
    reader.error = true;
    if (reader.errormessage == null)
    {
      reader.errormessage = errm;
    }
    else
    {
      reader.errormessage += "\n" + errm;
    }
  }

  static Pattern SCORES_WITH_RESIDUE_NUMS = Pattern.compile("^\\d+\\s([^\\s]+)\\s+\\d+$");
  
  /**
   * Read a scores block ihe provided stream.
   * 
   * @param reader
   *          The stream to parse
   * @param size
   *          The expected number of the sequence to be read
   * @return The {@link Block} instance read or {link null} null if the end of
   *         file has reached.
   * @throws IOException
   *           Something went wrong on the 'wire'
   */
  static Block readBlock(FileParse reader, int size) throws IOException
  {
    Block result = new Block(size);
    String line;

    /*
     * read blank lines (eventually)
     */
    while ((line = reader.nextLine()) != null && "".equals(line.trim()))
    {
      // consume blank lines
    }

    if (line == null)
    {
      return null;
    }

    /*
     * read the scores block
     */
    do
    {
      if ("".equals(line.trim()))
      {
        // terminated
        break;
      }

      // split the line on the first blank
      // the first part have to contain the sequence id
      // the remaining part are the scores values
      int p = line.indexOf(" ");
      if (p == -1)
      {
        if (reader.warningMessage == null)
        {
          reader.warningMessage = "";
        }
        reader.warningMessage += "Possible parsing error - expected to find a space in line: '"
                + line + "'\n";
        continue;
      }

      String id = line.substring(0, p).trim();
      String val = line.substring(p + 1).trim();

      Matcher m = SCORES_WITH_RESIDUE_NUMS.matcher(val);
      if( m.matches() ) {
    	  val = m.group(1);
      }
      
      result.items.put(id, val);

    } while ((line = reader.nextLine()) != null);

    return result;
  }

  /*
   * The score file header
   */
  static class Header
  {
    String head;

    int score;

    LinkedHashMap<String, Integer> scores = new LinkedHashMap<String, Integer>();

    public int getScoreAvg()
    {
      return score;
    }

    public int getScoreFor(String ID)
    {

      return scores.containsKey(ID) ? scores.get(ID) : -1;

    }
  }

  /*
   * Hold a single block values block in the score file
   */
  static class Block
  {
    int size;

    Map<String, String> items;

    public Block(int size)
    {
      this.size = size;
      this.items = new HashMap<String, String>(size);
    }

    String getScoresFor(String id)
    {
      return items.get(id);
    }

    String getConsensus()
    {
      return items.get("cons");
    }
  }

  /**
   * TCOFFEE score colourscheme
   */
  static final Color[] colors =
  { new Color(102, 102, 255), // #6666FF
      new Color(0, 255, 0), // #00FF00
      new Color(102, 255, 0), // #66FF00
      new Color(204, 255, 0), // #CCFF00
      new Color(255, 255, 0), // #FFFF00
      new Color(255, 204, 0), // #FFCC00
      new Color(255, 153, 0), // #FF9900
      new Color(255, 102, 0), // #FF6600
      new Color(255, 51, 0), // #FF3300
      new Color(255, 34, 0) // #FF2000
  };

  public final static String TCOFFEE_SCORE = "TCoffeeScore";

  /**
   * generate annotation for this TCoffee score set on the given alignment
   * 
   * @param al
   *          alignment to annotate
   * @param matchids
   *          if true, annotate sequences based on matching sequence names
   * @return true if alignment annotation was modified, false otherwise.
   */
  public boolean annotateAlignment(AlignmentI al, boolean matchids)
  {
    if (al.getHeight() != getHeight() || al.getWidth() != getWidth())
    {
      String info = String.format("align w: %s, h: %s; score: w: %s; h: %s ", al.getWidth(), al.getHeight(), getWidth(), getHeight() );
      warningMessage = "Alignment shape does not match T-Coffee score file shape -- " + info;
      return false;
    }
    boolean added = false;
    int i = 0;
    SequenceIdMatcher sidmatcher = new SequenceIdMatcher(
            al.getSequencesArray());
    byte[][] scoreMatrix = getScoresArray();
    // for 2.8 - we locate any existing TCoffee annotation and remove it first
    // before adding this.
    for (Map.Entry<String, StringBuilder> id : scores.entrySet())
    {
      byte[] srow = scoreMatrix[i];
      SequenceI s;
      if (matchids)
      {
        s = sidmatcher.findIdMatch(id.getKey());
      }
      else
      {
        s = al.getSequenceAt(i);
      }
      i++;
      if (s == null && i != scores.size() && !id.getKey().equals("cons"))
      {
        System.err.println("No "
                + (matchids ? "match " : " sequences left ")
                + " for TCoffee score set : " + id.getKey());
        continue;
      }
      int jSize = al.getWidth() < srow.length ? al.getWidth() : srow.length;
      Annotation[] annotations = new Annotation[al.getWidth()];
      for (int j = 0; j < jSize; j++)
      {
        byte val = srow[j];
        if (s != null && jalview.util.Comparison.isGap(s.getCharAt(j)))
        {
          annotations[j] = null;
          if (val > 0)
          {
            System.err
                    .println("Warning: non-zero value for positional T-COFFEE score for gap at "
                            + j + " in sequence " + s.getName());
          }
        }
        else
        {
          annotations[j] = new Annotation(s == null ? "" + val : null,
                  s == null ? "" + val : null, '\0', val * 1f, val >= 0
                          && val < colors.length ? colors[val]
                          : Color.white);
        }
      }
      // this will overwrite any existing t-coffee scores for the alignment
      AlignmentAnnotation aa = al.findOrCreateAnnotation(TCOFFEE_SCORE,
              TCOFFEE_SCORE, false, s, null);
      if (s != null)
      {
        aa.label = "T-COFFEE";
        aa.description = "" + id.getKey();
        aa.annotations = annotations;
        aa.visible = false;
        aa.belowAlignment = false;
        aa.setScore(header.getScoreFor(id.getKey()));
        aa.createSequenceMapping(s, s.getStart(), true);
        s.addAlignmentAnnotation(aa);
        aa.adjustForAlignment();
      }
      else
      {
        aa.graph = AlignmentAnnotation.NO_GRAPH;
        aa.label = "T-COFFEE";
        aa.description = "TCoffee column reliability score";
        aa.annotations = annotations;
        aa.belowAlignment = true;
        aa.visible = true;
        aa.setScore(header.getScoreAvg());
      }
      aa.showAllColLabels = true;
      aa.validateRangeAndDisplay();
      added = true;
    }

    return added;
  }

  @Override
  public String print()
  {
    // TODO Auto-generated method stub
    return "Not valid.";
  }
}
