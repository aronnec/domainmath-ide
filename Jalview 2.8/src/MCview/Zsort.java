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
package MCview;

import java.util.*;

public class Zsort
{
  public void Zsort(Vector bonds)
  {
    sort(bonds, 0, bonds.size() - 1);
  }

  public void sort(Vector bonds, int p, int r)
  {
    int q;

    if (p < r)
    {
      q = partition(bonds, p, r);
      sort(bonds, p, q);
      sort(bonds, q + 1, r);
    }
  }

  private int partition(Vector bonds, int p, int r)
  {
    float x = ((Bond) bonds.elementAt(p)).start[2];
    int i = p - 1;
    int j = r + 1;
    Bond tmp;
    while (true)
    {
      do
      {
        j--;
      } while ((j >= 0) && (((Bond) bonds.elementAt(j)).start[2] > x));

      do
      {
        i++;
      } while ((i < bonds.size())
              && (((Bond) bonds.elementAt(i)).start[2] < x));

      if (i < j)
      {
        tmp = (Bond) bonds.elementAt(i);
        bonds.setElementAt(bonds.elementAt(j), i);
        bonds.setElementAt(tmp, j);
      }
      else
      {
        return j;
      }
    }
  }
}
