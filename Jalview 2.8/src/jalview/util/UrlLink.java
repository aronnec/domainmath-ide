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

import java.util.Vector;

public class UrlLink
{
  /**
   * helper class to parse URL Link strings taken from applet parameters or
   * jalview properties file using the com.stevesoft.pat.Regex implementation.
   * Jalview 2.4 extension allows regular expressions to be used to parse ID
   * strings and replace the result in the URL. Regex's operate on the whole ID
   * string given to the matchURL method, if no regex is supplied, then only
   * text following the first pipe symbol will be susbstituted. Usage
   * documentation todo.
   */
  private String url_suffix, url_prefix, target, label, regexReplace;

  private boolean dynamic = false;

  private String invalidMessage = null;

  /**
   * parse the given linkString of the form '<label>|<url>' into parts url may
   * contain a string $SEQUENCE_ID<=optional regex=>$ where <=optional regex=>
   * must be of the form =/<perl style regex>/=$
   * 
   * @param link
   */
  public UrlLink(String link)
  {
    int sep = link.indexOf("|"), psqid = link.indexOf("$SEQUENCE_ID");
    if (psqid > -1)
    {
      dynamic = true;
      int p = sep;
      do
      {
        sep = p;
        p = link.indexOf("|", sep + 1);
      } while (p > sep && p < psqid);
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
      // Parse URL : Whole URL string first
      url_prefix = link.substring(sep + 1, psqid);
      if (link.indexOf("$SEQUENCE_ID=/") == psqid
              && (p = link.indexOf("/=$", psqid + 14)) > psqid + 14)
      {
        // Extract Regex and suffix
        url_suffix = link.substring(p + 3);
        regexReplace = link.substring(psqid + 14, p);
        try
        {
          com.stevesoft.pat.Regex rg = com.stevesoft.pat.Regex.perlCode("/"
                  + regexReplace + "/");
          if (rg == null)
          {
            invalidMessage = "Invalid Regular Expression : '"
                    + regexReplace + "'\n";
          }
        } catch (Exception e)
        {
          invalidMessage = "Invalid Regular Expression : '" + regexReplace
                  + "'\n";
        }
      }
      else
      {
        regexReplace = null;
        // verify format is really correct.
        if (link.indexOf("$SEQUENCE_ID$") == psqid)
        {
          url_suffix = link.substring(psqid + 13);
          regexReplace = null;
        }
        else
        {
          invalidMessage = "Warning: invalid regex structure for URL link : "
                  + link;
        }
      }
    }
    else
    {
      target = link.substring(0, sep);
      label = link.substring(0, sep = link.lastIndexOf("|"));
      url_prefix = link.substring(sep + 1);
      regexReplace = null; // implies we trim any prefix if necessary //
      // regexReplace=".*\\|?(.*)";
      url_suffix = null;
    }
  }

  /**
   * @return the url_suffix
   */
  public String getUrl_suffix()
  {
    return url_suffix;
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
   * @return the regexReplace
   */
  public String getRegexReplace()
  {
    return regexReplace;
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
   * @param idstring
   * @param onlyIfMatches
   *          - when true url strings are only made if regex is defined and
   *          matches
   * @return String[] { part of idstring substituted, full substituted url , ..
   *         next part, next url..}
   */
  public String[] makeUrls(String idstring, boolean onlyIfMatches)
  {
    if (dynamic)
    {
      if (regexReplace != null)
      {
        com.stevesoft.pat.Regex rg = com.stevesoft.pat.Regex.perlCode("/"
                + regexReplace + "/");
        if (rg.search(idstring))
        {
          int ns = rg.numSubs();
          if (ns == 0)
          {
            // take whole regex
            return new String[]
            { rg.stringMatched(),
                url_prefix + rg.stringMatched() + url_suffix };
          } /*
             * else if (ns==1) { // take only subgroup match return new String[]
             * { rg.stringMatched(1), url_prefix+rg.stringMatched(1)+url_suffix
             * }; }
             */
          else
          {
            // debug
            for (int s = 0; s <= rg.numSubs(); s++)
            {
              System.err.println("Sub " + s + " : " + rg.matchedFrom(s)
                      + " : " + rg.matchedTo(s) + " : '"
                      + rg.stringMatched(s) + "'");
            }
            // try to collate subgroup matches
            Vector subs = new Vector();
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
                String mtch = "";
                while (r <= ns && rg.matchedTo(r) <= rg.matchedTo(s))
                {
                  if (rg.matchedFrom(r) > -1)
                  {
                    mtch += rg.stringMatched(r);
                  }
                  r++;
                }
                if (mtch.length() > 0)
                {
                  subs.addElement(mtch);
                  subs.addElement(url_prefix + mtch + url_suffix);
                }
                s = r;
              }
              else
              {
                if (rg.matchedFrom(s) > -1)
                {
                  subs.addElement(rg.stringMatched(s));
                  subs.addElement(url_prefix + rg.stringMatched(s)
                          + url_suffix);
                }
                s++;
              }
            }

            String[] res = new String[subs.size()];
            for (int r = 0, rs = subs.size(); r < rs; r++)
            {
              res[r] = (String) subs.elementAt(r);
            }
            subs.removeAllElements();
            return res;
          }
        }
        if (onlyIfMatches)
        {
          return null;
        }
      }
      /* Otherwise - trim off any 'prefix' - pre 2.4 Jalview behaviour */
      if (idstring.indexOf("|") > -1)
      {
        idstring = idstring.substring(idstring.lastIndexOf("|") + 1);
      }

      // just return simple url substitution.
      return new String[]
      { idstring, url_prefix + idstring + url_suffix };
    }
    else
    {
      return new String[]
      { "", url_prefix };
    }
  }

  public String toString()
  {
    return label
            + "|"
            + url_prefix
            + (dynamic ? ("$SEQUENCE_ID" + ((regexReplace != null) ? "="
                    + regexReplace + "=$" : "$")) : "")
            + ((url_suffix == null) ? "" : url_suffix);

  }

  private static void testUrls(UrlLink ul, String idstring, String[] urls)
  {

    if (urls == null)
    {
      System.out.println("Created NO urls.");
    }
    else
    {
      System.out.println("Created " + (urls.length / 2) + " Urls.");
      for (int uls = 0; uls < urls.length; uls += 2)
      {
        System.out.println("URL Replacement text : " + urls[uls]
                + " : URL : " + urls[uls + 1]);
      }
    }
  }

  public static void main(String argv[])
  {
    String[] links = new String[]
    {
    /*
     * "AlinkT|Target|http://foo.foo.soo/",
     * "myUrl1|http://$SEQUENCE_ID=/[0-9]+/=$.someserver.org/foo",
     * "myUrl2|http://$SEQUENCE_ID=/(([0-9]+).+([A-Za-z]+))/=$.someserver.org/foo"
     * ,
     * "myUrl3|http://$SEQUENCE_ID=/([0-9]+).+([A-Za-z]+)/=$.someserver.org/foo"
     * , "myUrl4|target|http://$SEQUENCE_ID$.someserver.org/foo|too",
     * "PF1|http://us.expasy.org/cgi-bin/niceprot.pl?$SEQUENCE_ID=/(?:PFAM:)?(.+)/=$"
     * ,
     * "PF2|http://us.expasy.org/cgi-bin/niceprot.pl?$SEQUENCE_ID=/(PFAM:)?(.+)/=$"
     * ,
     * "PF3|http://us.expasy.org/cgi-bin/niceprot.pl?$SEQUENCE_ID=/PFAM:(.+)/=$"
     * , "NOTFER|http://notfer.org/$SEQUENCE_ID=/(?<!\\s)(.+)/=$",
     */
    "NESTED|http://nested/$SEQUENCE_ID=/^(?:Label:)?(?:(?:gi\\|(\\d+))|([^:]+))/=$/nested" };
    String[] idstrings = new String[]
    {
    /*
     * //"LGUL_human", //"QWIQW_123123", "uniprot|why_do+_12313_foo",
     * //"123123312", "123123 ABCDE foo", "PFAM:PF23943",
     */
    "Label:gi|9234|pdb|102L|A" };
    // TODO: test the setLabel method.
    for (int i = 0; i < links.length; i++)
    {
      UrlLink ul = new UrlLink(links[i]);
      if (ul.isValid())
      {
        System.out.println("\n\n\n");
        System.out.println("Link " + i + " " + links[i] + " : "
                + ul.toString());
        System.out.println(" pref : "
                + ul.getUrl_prefix()
                + "\n suf : "
                + ul.getUrl_suffix()
                + "\n : "
                + ((ul.getRegexReplace() != null) ? ul.getRegexReplace()
                        : ""));
        for (int ids = 0; ids < idstrings.length; ids++)
        {
          System.out.println("ID String : " + idstrings[ids]
                  + "\nWithout onlyIfMatches:");
          String[] urls = ul.makeUrls(idstrings[ids], false);
          testUrls(ul, idstrings[ids], urls);
          System.out.println("With onlyIfMatches set.");
          urls = ul.makeUrls(idstrings[ids], true);
          testUrls(ul, idstrings[ids], urls);
        }
      }
      else
      {
        System.err.println("Invalid URLLink : " + links[i] + " : "
                + ul.getInvalidMessage());
      }
    }
  }

  public boolean isDynamic()
  {
    // TODO Auto-generated method stub
    return dynamic;
  }

  public void setLabel(String newlabel)
  {
    this.label = newlabel;
  }
}
