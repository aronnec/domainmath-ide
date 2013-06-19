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
/* Author: Lauren Michelle Lui 
 * Methods are based on RALEE methods http://personalpages.manchester.ac.uk/staff/sam.griffiths-jones/software/ralee/
 * */

package jalview.analysis;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import jalview.datamodel.SequenceFeature;

public class Rna
{
  static Hashtable<Integer, Integer> pairHash = new Hashtable();

  /**
   * Based off of RALEE code ralee-get-base-pairs. Keeps track of open bracket
   * positions in "stack" vector. When a close bracket is reached, pair this
   * with the last element in the "stack" vector and store in "pairs" vector.
   * Remove last element in the "stack" vector. Continue in this manner until
   * the whole string is processed.
   * 
   * @param line
   *          Secondary structure line of an RNA Stockholm file
   * @return Array of SequenceFeature; type = RNA helix, begin is open base
   *         pair, end is close base pair
   */
  public static SequenceFeature[] GetBasePairs(CharSequence line)
          throws WUSSParseException
  {
    Stack stack = new Stack();
    Vector pairs = new Vector();

    int i = 0;
    while (i < line.length())
    {
      char base = line.charAt(i);

      if ((base == '<') || (base == '(') || (base == '{') || (base == '['))
      {
        stack.push(i);
      }
      else if ((base == '>') || (base == ')') || (base == '}')
              || (base == ']'))
      {

        if (stack.isEmpty())
        {
          // error whilst parsing i'th position. pass back
          throw new WUSSParseException("Mismatched closing bracket", i);
        }
        Object temp = stack.pop();
        pairs.addElement(temp);
        pairs.addElement(i);
      }

      i++;
    }

    int numpairs = pairs.size() / 2;
    SequenceFeature[] outPairs = new SequenceFeature[numpairs];

    // Convert pairs to array
    for (int p = 0; p < pairs.size(); p += 2)
    {
      int begin = Integer.parseInt(pairs.elementAt(p).toString());
      int end = Integer.parseInt(pairs.elementAt(p + 1).toString());

      outPairs[p / 2] = new SequenceFeature("RNA helix", "", "", begin,
              end, "");
      // pairHash.put(begin, end);

    }

    return outPairs;
  }

  /**
   * Function to get the end position corresponding to a given start position
   * 
   * @param indice
   *          - start position of a base pair
   * @return - end position of a base pair
   */
  /*
   * makes no sense at the moment :( public int findEnd(int indice){ //TODO:
   * Probably extend this to find the start to a given end? //could be done by
   * putting everything twice to the hash ArrayList<Integer> pair = new
   * ArrayList<Integer>(); return pairHash.get(indice); }
   */

  /**
   * Figures out which helix each position belongs to and stores the helix
   * number in the 'featureGroup' member of a SequenceFeature Based off of RALEE
   * code ralee-helix-map.
   * 
   * @param pairs
   *          Array of SequenceFeature (output from Rna.GetBasePairs)
   */
  public static void HelixMap(SequenceFeature[] pairs)
  {

    int helix = 0; // Number of helices/current helix
    int lastopen = 0; // Position of last open bracket reviewed
    int lastclose = 9999999; // Position of last close bracket reviewed
    int i = pairs.length; // Number of pairs

    int open; // Position of an open bracket under review
    int close; // Position of a close bracket under review
    int j; // Counter

    Hashtable helices = new Hashtable(); // Keep track of helix number for each
                                         // position

    // Go through each base pair and assign positions a helix
    for (i = 0; i < pairs.length; i++)
    {

      open = pairs[i].getBegin();
      close = pairs[i].getEnd();

      // System.out.println("open " + open + " close " + close);
      // System.out.println("lastclose " + lastclose + " lastopen " + lastopen);

      // we're moving from right to left based on closing pair
      /*
       * catch things like <<..>>..<<..>> |
       */
      if (open > lastclose)
      {
        helix++;
      }

      /*
       * catch things like <<..<<..>>..<<..>>>> |
       */
      j = pairs.length - 1;
      while (j >= 0)
      {
        int popen = pairs[j].getBegin();

        // System.out.println("j " + j + " popen " + popen + " lastopen "
        // +lastopen + " open " + open);
        if ((popen < lastopen) && (popen > open))
        {
          if (helices.containsValue(popen)
                  && (((Integer) helices.get(popen)) == helix))
          {
            continue;
          }
          else
          {
            helix++;
            break;
          }
        }

        j -= 1;
      }

      // Put positions and helix information into the hashtable
      helices.put(open, helix);
      helices.put(close, helix);

      // Record helix as featuregroup
      pairs[i].setFeatureGroup(Integer.toString(helix));

      lastopen = open;
      lastclose = close;

    }
  }
}
