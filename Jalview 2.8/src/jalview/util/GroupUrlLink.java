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
package jalview.util;

import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceI;

import java.util.Hashtable;
import java.util.Vector;

public class GroupUrlLink
{
  public class UrlStringTooLongException extends Exception
  {
    public UrlStringTooLongException(int lng)
    {
      urlLength = lng;
    }

    public int urlLength;

    public String toString()
    {
      return "Generated url is estimated to be too long (" + urlLength
              + ")";
    }
  }

  /**
   * Helper class based on the UrlLink class which enables URLs to be
   * constructed from sequences or IDs associated with a group of sequences. URL
   * definitions consist of a pipe separated string containing a <label>|<url
   * construct>|<separator character>[|<sequence separator character>]. The url
   * construct includes regex qualified tokens which are replaced with seuqence
   * IDs ($SEQUENCE_IDS$) and/or seuqence regions ($SEQUENCES$) that are
   * extracted from the group. See <code>UrlLink</code> for more information
   * about the approach, and the original implementation. Documentation to come.
   * Note - groupUrls can be very big!
   */
  private String url_prefix, target, label;

  /**
   * these are all filled in order of the occurence of each token in the url
   * string template
   */
  private String url_suffix[], separators[], regexReplace[];

  private String invalidMessage = null;

  /**
   * tokens that can be replaced in the URL.
   */
  private static String[] tokens;

  /**
   * position of each token (which can appear once only) in the url
   */
  private int[] segs;

  /**
   * contains tokens in the order they appear in the URL template.
   */
  private String[] mtch;
  static
  {
    if (tokens == null)
    {
      tokens = new String[]
      { "SEQUENCEIDS", "SEQUENCES", "DATASETID" };
    }
  }

  /**
   * test for GroupURLType bitfield (with default tokens)
   */
  public static final int SEQUENCEIDS = 1;

  /**
   * test for GroupURLType bitfield (with default tokens)
   */
  public static final int SEQUENCES = 2;

  /**
   * test for GroupURLType bitfield (with default tokens)
   */
  public static final int DATASETID = 4;

  // private int idseg = -1, seqseg = -1;

  /**
   * parse the given linkString of the form '<label>|<url>|separator
   * char[|optional sequence separator char]' into parts. url may contain a
   * string $SEQUENCEIDS<=optional regex=>$ where <=optional regex=> must be of
   * the form =/<perl style regex>/=$ or $SEQUENCES<=optional regex=>$ or
   * $SEQUENCES<=optional regex=>$.
   * 
   * @param link
   */
  public GroupUrlLink(String link)
  {
    int sep = link.indexOf("|");
    segs = new int[tokens.length];
    int ntoks = 0;
    for (int i = 0; i < segs.length; i++)
    {
      if ((segs[i] = link.indexOf("$" + tokens[i])) > -1)
      {
        ntoks++;
      }
    }
    // expect at least one token
    if (ntoks == 0)
    {
      invalidMessage = "Group URL string must contain at least one of ";
      for (int i = 0; i < segs.length; i++)
      {
        invalidMessage += " '$" + tokens[i] + "[=/regex=/]$'";
      }
      return;
    }

    int[] ptok = new int[ntoks + 1];
    String[] tmtch = new String[ntoks + 1];
    mtch = new String[ntoks];
    for (int i = 0, t = 0; i < segs.length; i++)
    {
      if (segs[i] > -1)
      {
        ptok[t] = segs[i];
        tmtch[t++] = tokens[i];
      }
    }
    ptok[ntoks] = link.length();
    tmtch[ntoks] = "$$$$$$$$$";
    jalview.util.QuickSort.sort(ptok, tmtch);
    for (int i = 0; i < ntoks; i++)
    {
      mtch[i] = tmtch[i]; // TODO: check order is ascending
    }
    /*
     * replaces the specific code below {}; if (psqids > -1 && pseqs > -1) { if
     * (psqids > pseqs) { idseg = 1; seqseg = 0;
     * 
     * ptok = new int[] { pseqs, psqids, link.length() }; mtch = new String[] {
     * "$SEQUENCES", "$SEQUENCEIDS" }; } else { idseg = 0; seqseg = 1; ptok =
     * new int[] { psqids, pseqs, link.length() }; mtch = new String[] {
     * "$SEQUENCEIDS", "$SEQUENCES" }; } } else { if (psqids != -1) { idseg = 0;
     * ptok = new int[] { psqids, link.length() }; mtch = new String[] {
     * "$SEQUENCEIDS" }; } else { seqseg = 0; ptok = new int[] { pseqs,
     * link.length() }; mtch = new String[] { "$SEQUENCES" }; } }
     */

    int p = sep;
    // first get the label and target part before the first |
    do
    {
      sep = p;
      p = link.indexOf("|", sep + 1);
    } while (p > sep && p < ptok[0]);
    // Assuming that the URL itself does not contain any '|' symbols
    // sep now contains last pipe symbol position prior to any regex symbols
    label = link.substring(0, sep);
    if (label.indexOf("|") > -1)
    {
      // | terminated database name / www target at start of Label
      target = label.substring(0, label.indexOf("|"));
    }
    else if (label.indexOf(" ") > 2)
    {
      // space separated Label - matches database name
      target = label.substring(0, label.indexOf(" "));
    }
    else
    {
      target = label;
    }
    // Now Parse URL : Whole URL string first
    url_prefix = link.substring(sep + 1, ptok[0]);
    url_suffix = new String[mtch.length];
    regexReplace = new String[mtch.length];
    // and loop through tokens
    for (int pass = 0; pass < mtch.length; pass++)
    {
      int mlength = 3 + mtch[pass].length();
      if (link.indexOf("$" + mtch[pass] + "=/") == ptok[pass]
              && (p = link.indexOf("/=$", ptok[pass] + mlength)) > ptok[pass]
                      + mlength)
      {
        // Extract Regex and suffix
        if (ptok[pass + 1] < p + 3)
        {
          // tokens are not allowed inside other tokens - e.g. inserting a
          // $sequences$ into the regex match for the sequenceid
          invalidMessage = "Token regexes cannot contain other regexes (did you terminate the $"
                  + mtch[pass] + " regex with a '/=$' ?";
          return;
        }
        url_suffix[pass] = link.substring(p + 3, ptok[pass + 1]);
        regexReplace[pass] = link.substring(ptok[pass] + mlength, p);
        try
        {
          com.stevesoft.pat.Regex rg = com.stevesoft.pat.Regex.perlCode("/"
                  + regexReplace[pass] + "/");
          if (rg == null)
          {
            invalidMessage = "Invalid Regular Expression : '"
                    + regexReplace[pass] + "'\n";
          }
        } catch (Exception e)
        {
          invalidMessage = "Invalid Regular Expression : '"
                  + regexReplace[pass] + "'\n";
        }
      }
      else
      {
        regexReplace[pass] = null;
        // verify format is really correct.
        if ((p = link.indexOf("$" + mtch[pass] + "$")) == ptok[pass])
        {
          url_suffix[pass] = link.substring(p + mtch[pass].length() + 2,
                  ptok[pass + 1]);
        }
        else
        {
          invalidMessage = "Warning: invalid regex structure (after '"
                  + mtch[0] + "') for URL link : " + link;
        }
      }
    }
    int pass = 0;
    separators = new String[url_suffix.length];
    String suffices = url_suffix[url_suffix.length - 1], lastsep = ",";
    // have a look in the last suffix for any more separators.
    while ((p = suffices.indexOf('|')) > -1)
    {
      separators[pass] = suffices.substring(p + 1);
      if (pass == 0)
      {
        // trim the original suffix string
        url_suffix[url_suffix.length - 1] = suffices.substring(0, p);
      }
      else
      {
        lastsep = (separators[pass - 1] = separators[pass - 1].substring(0,
                p));
      }
      suffices = separators[pass];
      pass++;
    }
    if (pass > 0)
    {
      lastsep = separators[pass - 1];
    }
    // last separator is always used for all the remaining separators
    while (pass < separators.length)
    {
      separators[pass++] = lastsep;
    }
  }

  /**
   * @return the url_suffix
   */
  public String getUrl_suffix()
  {
    return url_suffix[url_suffix.length - 1];
  }

  /**
   * @return the url_prefix
   */
  public String getUrl_prefix()
  {
    return url_prefix;
  }

  /**
   * @return the target
   */
  public String getTarget()
  {
    return target;
  }

  /**
   * @return the label
   */
  public String getLabel()
  {
    return label;
  }

  /**
   * @return the sequence ID regexReplace
   */
  public String getIDRegexReplace()
  {
    return _replaceFor(tokens[0]);
  }

  private String _replaceFor(String token)
  {
    for (int i = 0; i < mtch.length; i++)
      if (segs[i] > -1 && mtch[i].equals(token))
      {
        return regexReplace[i];
      }
    return null;
  }

  /**
   * @return the sequence ID regexReplace
   */
  public String getSeqRegexReplace()
  {
    return _replaceFor(tokens[1]);
  }

  /**
   * @return the invalidMessage
   */
  public String getInvalidMessage()
  {
    return invalidMessage;
  }

  /**
   * Check if URL string was parsed properly.
   * 
   * @return boolean - if false then <code>getInvalidMessage</code> returns an
   *         error message
   */
  public boolean isValid()
  {
    return invalidMessage == null;
  }

  /**
   * return one or more URL strings by applying regex to the given idstring
   * 
   * @param idstrings
   *          array of id strings to pass to service
   * @param seqstrings
   *          array of seq strings to pass to service
   * @param onlyIfMatches
   *          - when true url strings are only made if regex is defined and
   *          matches for all qualified tokens in groupURL - TODO: consider if
   *          onlyIfMatches is really a useful parameter!
   * @return null or Object[] { int[] { number of seqs substituted},boolean[] {
   *         which seqs were substituted }, StringBuffer[] { substituted lists
   *         for each token }, String[] { url } }
   * @throws UrlStringTooLongException
   */
  public Object[] makeUrls(String[] idstrings, String[] seqstrings,
          String dsstring, boolean onlyIfMatches)
          throws UrlStringTooLongException
  {
    Hashtable rstrings = replacementArgs(idstrings, seqstrings, dsstring);
    return makeUrls(rstrings, onlyIfMatches);
  }

  /**
   * gathers input into a hashtable
   * 
   * @param idstrings
   * @param seqstrings
   * @param dsstring
   * @return
   */
  private Hashtable replacementArgs(String[] idstrings,
          String[] seqstrings, String dsstring)
  {
    Hashtable rstrings = new Hashtable();
    rstrings.put(tokens[0], idstrings);
    rstrings.put(tokens[1], seqstrings);
    rstrings.put(tokens[2], new String[]
    { dsstring });
    if (idstrings.length != seqstrings.length)
    {
      throw new Error(
              "idstrings and seqstrings contain one string each per sequence.");
    }
    return rstrings;
  }

  public Object[] makeUrls(Hashtable repstrings, boolean onlyIfMatches)
          throws UrlStringTooLongException
  {
    return makeUrlsIf(true, repstrings, onlyIfMatches);
  }

  /**
   * 
   * @param ids
   * @param seqstr
   * @param string
   * @param b
   * @return URL stub objects ready to pass to constructFrom
   * @throws UrlStringTooLongException
   */
  public Object[] makeUrlStubs(String[] ids, String[] seqstr,
          String string, boolean b) throws UrlStringTooLongException
  {
    Hashtable rstrings = replacementArgs(ids, seqstr, string);
    Object[] stubs = makeUrlsIf(false, rstrings, b);
    if (stubs != null)
    {
      return new Object[]
      { stubs[0], stubs[1], rstrings, new boolean[]
      { b } };
    }
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * generate the URL for the given URL stub object array returned from
   * makeUrlStubs
   * 
   * @param stubs
   * @return URL string.
   * @throws UrlStringTooLongException
   */
  public String constructFrom(Object[] stubs)
          throws UrlStringTooLongException
  {
    Object[] results = makeUrlsIf(true, (Hashtable) stubs[2],
            ((boolean[]) stubs[3])[0]);
    return ((String[]) results[3])[0];
  }

  /**
   * conditionally generate urls or stubs for a given input.
   * 
   * @param createFullUrl
   *          set to false if you only want to test if URLs would be generated.
   * @param repstrings
   * @param onlyIfMatches
   * @return null if no url is generated. Object[] { int[] { number of matches
   *         seqs }, boolean[] { which matched }, (if createFullUrl also has
   *         StringBuffer[] { segment generated from inputs that is used in URL
   *         }, String[] { url })}
   * @throws UrlStringTooLongException
   */
  protected Object[] makeUrlsIf(boolean createFullUrl,
          Hashtable repstrings, boolean onlyIfMatches)
          throws UrlStringTooLongException
  {
    int pass = 0;

    // prepare string arrays in correct order to be assembled into URL input
    String[][] idseq = new String[mtch.length][]; // indexed by pass
    int mins = 0, maxs = 0; // allowed two values, 1 or n-sequences.
    for (int i = 0; i < mtch.length; i++)
    {
      idseq[i] = (String[]) repstrings.get(mtch[i]);
      if (idseq[i].length >= 1)
      {
        if (mins == 0 && idseq[i].length == 1)
        {
          mins = 1;
        }
        if (maxs < 2)
        {
          maxs = idseq[i].length;
        }
        else
        {
          if (maxs != idseq[i].length)
          {
            throw new Error(
                    "Cannot have mixed length replacement vectors. Replacement vector for "
                            + (mtch[i]) + " is " + idseq[i].length
                            + " strings long, and have already seen a "
                            + maxs + " length vector.");
          }
        }
      }
      else
      {
        throw new Error(
                "Cannot have zero length vector of replacement strings - either 1 value or n values.");
      }
    }
    // iterate through input, collating segments to be inserted into url
    StringBuffer matched[] = new StringBuffer[idseq.length];
    // and precompile regexes
    com.stevesoft.pat.Regex[] rgxs = new com.stevesoft.pat.Regex[matched.length];
    for (pass = 0; pass < matched.length; pass++)
    {
      matched[pass] = new StringBuffer();
      if (regexReplace[pass] != null)
      {
        rgxs[pass] = com.stevesoft.pat.Regex.perlCode("/"
                + regexReplace[pass] + "/");
      }
      else
      {
        rgxs[pass] = null;
      }
    }
    // tot up the invariant lengths for this url
    int urllength = url_prefix.length();
    for (pass = 0; pass < matched.length; pass++)
    {
      urllength += url_suffix[pass].length();
    }

    // flags to record which of the input sequences were actually used to
    // generate the
    // url
    boolean[] thismatched = new boolean[maxs];
    int seqsmatched = 0;
    for (int sq = 0; sq < maxs; sq++)
    {
      // initialise flag for match
      thismatched[sq] = false;
      StringBuffer[] thematches = new StringBuffer[rgxs.length];
      for (pass = 0; pass < rgxs.length; pass++)
      {
        thematches[pass] = new StringBuffer(); // initialise - in case there are
                                               // no more
        // matches.
        // if a regex is provided, then it must match for all sequences in all
        // tokens for it to be considered.
        if (idseq[pass].length <= sq)
        {
          // no more replacement strings to try for this token
          continue;
        }
        if (rgxs[pass] != null)
        {
          com.stevesoft.pat.Regex rg = rgxs[pass];
          int rematchat = 0;
          // concatenate all matches of re in the given string!
          while (rg.searchFrom(idseq[pass][sq], rematchat))
          {
            rematchat = rg.matchedTo();
            thismatched[sq] |= true;
            urllength += rg.charsMatched(); // count length
            if ((urllength + 32) > Platform.getMaxCommandLineLength())
            {
              throw new UrlStringTooLongException(urllength);
            }

            if (!createFullUrl)
            {
              continue; // don't bother making the URL replacement text.
            }
            // do we take the cartesian products of the substituents ?
            int ns = rg.numSubs();
            if (ns == 0)
            {
              thematches[pass].append(rg.stringMatched());// take whole regex
            }
            /*
             * else if (ns==1) { // take only subgroup match return new String[]
             * { rg.stringMatched(1), url_prefix+rg.stringMatched(1)+url_suffix
             * }; }
             */
            // deal with multiple submatch case - for moment we do the simplest
            // - concatenate the matched regions, instead of creating a complete
            // list for each alternate match over all sequences.
            // TODO: specify a 'replace pattern' - next refinement
            else
            {
              // debug
              /*
               * for (int s = 0; s <= rg.numSubs(); s++) {
               * System.err.println("Sub " + s + " : " + rg.matchedFrom(s) +
               * " : " + rg.matchedTo(s) + " : '" + rg.stringMatched(s) + "'");
               * }
               */
              // try to collate subgroup matches
              StringBuffer subs = new StringBuffer();
              // have to loop through submatches, collating them at top level
              // match
              int s = 0; // 1;
              while (s <= ns)
              {
                if (s + 1 <= ns && rg.matchedTo(s) > -1
                        && rg.matchedTo(s + 1) > -1
                        && rg.matchedTo(s + 1) < rg.matchedTo(s))
                {
                  // s is top level submatch. search for submatches enclosed by
                  // this one
                  int r = s + 1;
                  StringBuffer rmtch = new StringBuffer();
                  while (r <= ns && rg.matchedTo(r) <= rg.matchedTo(s))
                  {
                    if (rg.matchedFrom(r) > -1)
                    {
                      rmtch.append(rg.stringMatched(r));
                    }
                    r++;
                  }
                  if (rmtch.length() > 0)
                  {
                    subs.append(rmtch); // simply concatenate
                  }
                  s = r;
                }
                else
                {
                  if (rg.matchedFrom(s) > -1)
                  {
                    subs.append(rg.stringMatched(s)); // concatenate
                  }
                  s++;
                }
              }
              thematches[pass].append(subs);
            }
          }
        }
        else
        {
          // are we only supposed to take regex matches ?
          if (!onlyIfMatches)
          {
            thismatched[sq] |= true;
            urllength += idseq[pass][sq].length(); // tot up length
            if (createFullUrl)
            {
              thematches[pass] = new StringBuffer(idseq[pass][sq]); // take
                                                                    // whole
                                                                    // string -
              // regardless - probably not a
              // good idea!
              /*
               * TODO: do some boilerplate trimming of the fields to make them
               * sensible e.g. trim off any 'prefix' in the id string (see
               * UrlLink for the below) - pre 2.4 Jalview behaviour if
               * (idstring.indexOf("|") > -1) { idstring =
               * idstring.substring(idstring.lastIndexOf("|") + 1); }
               */
            }

          }
        }
      }

      // check if we are going to add this sequence's results ? all token
      // replacements must be valid for this to happen!
      // (including single value replacements - eg. dataset name)
      if (thismatched[sq])
      {
        if (createFullUrl)
        {
          for (pass = 0; pass < matched.length; pass++)
          {
            if (idseq[pass].length > 1 && matched[pass].length() > 0)
            {
              matched[pass].append(separators[pass]);
            }
            matched[pass].append(thematches[pass]);
          }
        }
        seqsmatched++;
      }
    }
    // finally, if any sequences matched, then form the URL and return
    if (seqsmatched == 0 || (createFullUrl && matched[0].length() == 0))
    {
      // no matches - no url generated
      return null;
    }
    // check if we are beyond the feasible command line string limit for this
    // platform
    if ((urllength + 32) > Platform.getMaxCommandLineLength())
    {
      throw new UrlStringTooLongException(urllength);
    }
    if (!createFullUrl)
    {
      // just return the essential info about what the URL would be generated
      // from
      return new Object[]
      { new int[]
      { seqsmatched }, thismatched };
    }
    // otherwise, create the URL completely.

    StringBuffer submiturl = new StringBuffer();
    submiturl.append(url_prefix);
    for (pass = 0; pass < matched.length; pass++)
    {
      submiturl.append(matched[pass]);
      if (url_suffix[pass] != null)
      {
        submiturl.append(url_suffix[pass]);
      }
    }

    return new Object[]
    { new int[]
    { seqsmatched }, thismatched, matched, new String[]
    { submiturl.toString() } };
  }

  /**
   * 
   * @param urlstub
   * @return number of distinct sequence (id or seuqence) replacements predicted
   *         for this stub
   */
  public int getNumberInvolved(Object[] urlstub)
  {
    return ((int[]) urlstub[0])[0]; // returns seqsmatched from
                                    // makeUrlsIf(false,...)
  }

  /**
   * get token types present in this url as a bitfield indicating presence of
   * each token from tokens (LSB->MSB).
   * 
   * @return groupURL class as integer
   */
  public int getGroupURLType()
  {
    int r = 0;
    for (int pass = 0; pass < tokens.length; pass++)
    {
      for (int i = 0; i < mtch.length; i++)
      {
        if (mtch[i].equals(tokens[pass]))
        {
          r += 1 << pass;
        }
      }
    }
    return r;
  }

  public String toString()
  {
    StringBuffer result = new StringBuffer();
    result.append(label + "|" + url_prefix);
    int r;
    for (r = 0; r < url_suffix.length; r++)
    {
      result.append("$");
      result.append(mtch[r]);
      if (regexReplace[r] != null)
      {
        result.append("=/");
        result.append(regexReplace[r]);
        result.append("/=");
      }
      result.append("$");
      result.append(url_suffix[r]);
    }
    for (r = 0; r < separators.length; r++)
    {
      result.append("|");
      result.append(separators[r]);
    }
    return result.toString();
  }

  /**
   * report stats about the generated url string given an input set
   * 
   * @param ul
   * @param idstring
   * @param url
   */
  private static void testUrls(GroupUrlLink ul, String[][] idstring,
          Object[] url)
  {

    if (url == null)
    {
      System.out.println("Created NO urls.");
    }
    else
    {
      System.out.println("Created a url from " + ((int[]) url[0])[0]
              + "out of " + idstring[0].length + " sequences.");
      System.out.println("Sequences that did not match:");
      for (int sq = 0; sq < idstring[0].length; sq++)
      {
        if (!((boolean[]) url[1])[sq])
        {
          System.out.println("Seq " + sq + ": " + idstring[0][sq] + "\t: "
                  + idstring[1][sq]);
        }
      }
      System.out.println("Sequences that DID match:");
      for (int sq = 0; sq < idstring[0].length; sq++)
      {
        if (((boolean[]) url[1])[sq])
        {
          System.out.println("Seq " + sq + ": " + idstring[0][sq] + "\t: "
                  + idstring[1][sq]);
        }
      }
      System.out.println("The generated URL:");
      System.out.println(((String[]) url[3])[0]);
    }
  }

  public static void main(String argv[])
  {
    String[] links = new String[]
    {
        "EnVision2|IDS|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=Enfin%20Default%20Workflow&datasetName=linkInDatasetFromJalview&input=$SEQUENCEIDS$&inputType=0|,",
        "EnVision2|Seqs|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=Enfin%20Default%20Workflow&datasetName=linkInDatasetFromJalview&input=$SEQUENCES$&inputType=1|,",
        "EnVision2|IDS|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=Enfin%20Default%20Workflow&datasetName=$DATASETID$&input=$SEQUENCEIDS$&inputType=0|,",
        "EnVision2|Seqs|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=Enfin%20Default%20Workflow&datasetName=$DATASETID$&input=$SEQUENCES$&inputType=1|,",
        "EnVision2|IDS|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=$SEQUENCEIDS$&datasetName=linkInDatasetFromJalview&input=$SEQUENCEIDS$&inputType=0|,",
        "EnVision2|Seqs|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=$SEQUENCEIDS$&datasetName=$DATASETID$&input=$SEQUENCES$&inputType=1|,",
        "EnVision2 Seqs|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=Default&datasetName=JalviewSeqs$DATASETID$&input=$SEQUENCES=/([a-zA-Z]+)/=$&inputType=1|,",
        "EnVision2 Seqs|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?workflow=Default&datasetName=JalviewSeqs$DATASETID$&input=$SEQUENCES=/[A-Za-z]+/=$&inputType=1|,"
    /*
     * http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?input=P38389,P38398
     * &inputType=0&workflow=Enfin%20Default%20Workflow&datasetName=
     * linkInDatasetFromPRIDE
     */
    };

    SequenceI[] seqs = new SequenceI[]
    { new Sequence("StupidLabel:gi|9234|pdb|102L|A",
            "asdiasdpasdpadpwpadasdpaspdw"), };
    String[][] seqsandids = formStrings(seqs);
    for (int i = 0; i < links.length; i++)
    {
      GroupUrlLink ul = new GroupUrlLink(links[i]);
      if (ul.isValid())
      {
        System.out.println("\n\n\n");
        System.out.println("Link " + i + " " + links[i] + " : "
                + ul.toString());
        System.out.println(" pref : " + ul.getUrl_prefix());
        System.out.println(" IdReplace : " + ul.getIDRegexReplace());
        System.out.println(" SeqReplace : " + ul.getSeqRegexReplace());
        System.out.println(" Suffixes : " + ul.getUrl_suffix());

        System.out
                .println("<insert input id and sequence strings here> Without onlyIfMatches:");
        Object[] urls;
        try
        {
          urls = ul.makeUrls(seqsandids[0], seqsandids[1], "mydataset",
                  false);
          testUrls(ul, seqsandids, urls);
        } catch (UrlStringTooLongException ex)
        {
          System.out.println("too long exception " + ex);
        }
        System.out
                .println("<insert input id and sequence strings here> With onlyIfMatches set:");
        try
        {
          urls = ul.makeUrls(seqsandids[0], seqsandids[1], "mydataset",
                  true);
          testUrls(ul, seqsandids, urls);
        } catch (UrlStringTooLongException ex)
        {
          System.out.println("too long exception " + ex);
        }
      }
      else
      {
        System.err.println("Invalid URLLink : " + links[i] + " : "
                + ul.getInvalidMessage());
      }
    }
  }

  /**
   * covenience method to generate the id and sequence string vector from a set
   * of seuqences using each sequence's getName() and getSequenceAsString()
   * method
   * 
   * @param seqs
   * @return String[][] {{sequence ids},{sequence strings}}
   */
  public static String[][] formStrings(SequenceI[] seqs)
  {
    String[][] idset = new String[2][seqs.length];
    for (int i = 0; i < seqs.length; i++)
    {
      idset[0][i] = seqs[i].getName();
      idset[1][i] = seqs[i].getSequenceAsString();
    }
    return idset;
  }

  public void setLabel(String newlabel)
  {
    this.label = newlabel;
  }

}
