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

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * utility class for dealing with HTML link extraction
 * 
 * @author jprocter
 * 
 */
public class ParseHtmlBodyAndLinks
{
  String orig = null;

  public String getOrig()
  {
    return orig;
  }

  boolean htmlContent = true;

  /**
   * @return true if the content looked like HTML
   */
  public boolean isHtmlContent()
  {
    return htmlContent;
  }

  ArrayList<String> links = new ArrayList<String>();

  StringBuffer sb = new StringBuffer();

  /**
   * result of parsing description - with or without HTML tags
   * 
   * @return
   */
  public String getContent()
  {

    return sb.toString();
  }

  /**
   * list of Label|Link encoded URL links extracted from HTML
   * 
   * @return
   */
  public ArrayList<String> getLinks()
  {
    return links;
  }

  /**
   * 
   * @param description
   *          - html or text content to be parsed
   * @param removeHTML
   *          flag to indicate if HTML tags should be removed if they are
   *          present.
   * @param newline
   */
  public ParseHtmlBodyAndLinks(String description, boolean removeHTML,
          String newline)
  {
    if (description == null || description.length() == 0)
    {
      htmlContent = false;
      return;
    }
    if (description.toUpperCase().indexOf("<HTML>") == -1)
    {
      htmlContent = false;
    }
    orig = description;
    StringTokenizer st = new StringTokenizer(description, "<");
    String token, link;
    int startTag;
    String tag = null;
    while (st.hasMoreElements())
    {
      token = st.nextToken("&>");
      if (token.equalsIgnoreCase("html") || token.startsWith("/"))
      {
        continue;
      }

      tag = null;
      startTag = token.indexOf("<");

      if (startTag > -1)
      {
        tag = token.substring(startTag + 1);
        token = token.substring(0, startTag);
      }

      if (tag != null && tag.toUpperCase().startsWith("A HREF="))
      {
        if (token.length() > 0)
        {
          sb.append(token);
        }
        link = tag.substring(tag.indexOf("\"") + 1, tag.length() - 1);
        String label = st.nextToken("<>");
        links.add(label + "|" + link);
        sb.append(label + "%LINK%");
      }
      else if (tag != null && tag.equalsIgnoreCase("br"))
      {
        sb.append(newline);
      }
      else if (token.startsWith("lt;"))
      {
        sb.append("<" + token.substring(3));
      }
      else if (token.startsWith("gt;"))
      {
        sb.append(">" + token.substring(3));
      }
      else if (token.startsWith("amp;"))
      {
        sb.append("&" + token.substring(4));
      }
      else
      {
        sb.append(token);
      }
    }
    if (removeHTML && !htmlContent)
    {
      // instead of parsing the html into plaintext
      // clean the description ready for embedding in html
      sb = new StringBuffer(Pattern.compile("<").matcher(description)
              .replaceAll("&lt;"));

    }

  }

  /**
   * get either the parsed content or the original, depending on whether the
   * original looked like html content or not.
   * 
   * @return
   */
  public String getNonHtmlContent()
  {
    return isHtmlContent() ? sb.toString() : orig;
  }

}
