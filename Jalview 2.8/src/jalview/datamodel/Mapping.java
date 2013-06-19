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

import java.util.Vector;

import jalview.util.MapList;

public class Mapping
{
  /**
   * Contains the start-end pairs mapping from the associated sequence to the
   * sequence in the database coordinate system it also takes care of step
   * difference between coordinate systems
   */
  MapList map = null;

  /**
   * The seuqence that map maps the associated seuqence to (if any).
   */
  SequenceI to = null;

  public Mapping(MapList map)
  {
    super();
    this.map = map;
  }

  public Mapping(SequenceI to, MapList map)
  {
    this(map);
    this.to = to;
  }

  /**
   * create a new mapping from
   * 
   * @param to
   *          the sequence being mapped
   * @param exon
   *          int[] {start,end,start,end} series on associated sequence
   * @param is
   *          int[] {start,end,...} ranges on the reference frame being mapped
   *          to
   * @param i
   *          step size on associated sequence
   * @param j
   *          step size on mapped frame
   */
  public Mapping(SequenceI to, int[] exon, int[] is, int i, int j)
  {
    this(to, new MapList(exon, is, i, j));
  }

  /**
   * create a duplicate (and independent) mapping object with the same reference
   * to any SequenceI being mapped to.
   * 
   * @param map2
   */
  public Mapping(Mapping map2)
  {
    if (map2 != this && map2 != null)
    {
      if (map2.map != null)
      {
        map = new MapList(map2.map);
      }
      to = map2.to;
    }
  }

  /**
   * @return the map
   */
  public MapList getMap()
  {
    return map;
  }

  /**
   * @param map
   *          the map to set
   */
  public void setMap(MapList map)
  {
    this.map = map;
  }

  /**
   * Equals that compares both the to references and MapList mappings.
   * 
   * @param other
   * @return
   */
  public boolean equals(Mapping other)
  {
    if (other == null)
      return false;
    if (other == this)
      return true;
    if (other.to != to)
      return false;
    if ((map != null && other.map == null)
            || (map == null && other.map != null))
      return false;
    if (map.equals(other.map))
      return true;
    return false;
  }

  /**
   * get the 'initial' position in the associated sequence for a position in the
   * mapped reference frame
   * 
   * @param mpos
   * @return
   */
  public int getPosition(int mpos)
  {
    if (map != null)
    {
      int[] mp = map.shiftTo(mpos);
      if (mp != null)
      {
        return mp[0];
      }
    }
    return mpos;
  }

  /**
   * gets boundary in direction of mapping
   * 
   * @param position
   *          in mapped reference frame
   * @return int{start, end} positions in associated sequence (in direction of
   *         mapped word)
   */
  public int[] getWord(int mpos)
  {
    if (map != null)
    {
      return map.getToWord(mpos);
    }
    return null;
  }

  /**
   * width of mapped unit in associated sequence
   * 
   */
  public int getWidth()
  {
    if (map != null)
    {
      return map.getFromRatio();
    }
    return 1;
  }

  /**
   * width of unit in mapped reference frame
   * 
   * @return
   */
  public int getMappedWidth()
  {
    if (map != null)
    {
      return map.getToRatio();
    }
    return 1;
  }

  /**
   * get mapped position in the associated reference frame for position pos in
   * the associated sequence.
   * 
   * @param pos
   * @return
   */
  public int getMappedPosition(int pos)
  {
    if (map != null)
    {
      int[] mp = map.shiftFrom(pos);
      if (mp != null)
      {
        return mp[0];
      }
    }
    return pos;
  }

  public int[] getMappedWord(int pos)
  {
    if (map != null)
    {
      int[] mp = map.shiftFrom(pos);
      if (mp != null)
      {
        return new int[]
        { mp[0], mp[0] + mp[2] * (map.getToRatio() - 1) };
      }
    }
    return null;
  }

  /**
   * locates the region of feature f in the associated sequence's reference
   * frame
   * 
   * @param f
   * @return one or more features corresponding to f
   */
  public SequenceFeature[] locateFeature(SequenceFeature f)
  {
    if (true)
    { // f.getBegin()!=f.getEnd()) {
      if (map != null)
      {
        int[] frange = map.locateInFrom(f.getBegin(), f.getEnd());
        if (frange == null)
        {
          // JBPNote - this isprobably not the right thing to doJBPHack
          return null;
        }
        SequenceFeature[] vf = new SequenceFeature[frange.length / 2];
        for (int i = 0, v = 0; i < frange.length; i += 2, v++)
        {
          vf[v] = new SequenceFeature(f);
          vf[v].setBegin(frange[i]);
          vf[v].setEnd(frange[i + 1]);
          if (frange.length > 2)
            vf[v].setDescription(f.getDescription() + "\nPart " + (v + 1));
        }
        return vf;
      }
    }
    if (false) // else
    {
      int[] word = getWord(f.getBegin());
      if (word[0] < word[1])
      {
        f.setBegin(word[0]);
      }
      else
      {
        f.setBegin(word[1]);
      }
      word = getWord(f.getEnd());
      if (word[0] > word[1])
      {
        f.setEnd(word[0]);
      }
      else
      {
        f.setEnd(word[1]);
      }
    }
    // give up and just return the feature.
    return new SequenceFeature[]
    { f };
  }

  /**
   * return a series of contigs on the associated sequence corresponding to the
   * from,to interval on the mapped reference frame
   * 
   * @param from
   * @param to
   * @return int[] { from_i, to_i for i=1 to n contiguous regions in the
   *         associated sequence}
   */
  public int[] locateRange(int from, int to)
  {
    if (map != null)
    {
      if (from <= to)
      {
        from = (map.getToLowest() < from) ? from : map.getToLowest();
        to = (map.getToHighest() > to) ? to : map.getToHighest();
        if (from > to)
          return null;
      }
      else
      {
        from = (map.getToHighest() > from) ? from : map.getToHighest();
        to = (map.getToLowest() < to) ? to : map.getToLowest();
        if (from < to)
          return null;
      }
      return map.locateInFrom(from, to);
    }
    return new int[]
    { from, to };
  }

  /**
   * return a series of mapped contigs mapped from a range on the associated
   * sequence
   * 
   * @param from
   * @param to
   * @return
   */
  public int[] locateMappedRange(int from, int to)
  {
    if (map != null)
    {

      if (from <= to)
      {
        from = (map.getFromLowest() < from) ? from : map.getFromLowest();
        to = (map.getFromHighest() > to) ? to : map.getFromHighest();
        if (from > to)
          return null;
      }
      else
      {
        from = (map.getFromHighest() > from) ? from : map.getFromHighest();
        to = (map.getFromLowest() < to) ? to : map.getFromLowest();
        if (from < to)
          return null;
      }
      return map.locateInTo(from, to);
    }
    return new int[]
    { from, to };
  }

  /**
   * return a new mapping object with a maplist modifed to only map the visible
   * regions defined by viscontigs.
   * 
   * @param viscontigs
   * @return
   */
  public Mapping intersectVisContigs(int[] viscontigs)
  {
    Mapping copy = new Mapping(this);
    if (map != null)
    {
      int vpos = 0;
      int apos = 0;
      Vector toRange = new Vector();
      Vector fromRange = new Vector();
      for (int vc = 0; vc < viscontigs.length; vc += 2)
      {
        // find a mapped range in this visible region
        int[] mpr = locateMappedRange(1 + viscontigs[vc],
                viscontigs[vc + 1] - 1);
        if (mpr != null)
        {
          for (int m = 0; m < mpr.length; m += 2)
          {
            toRange.addElement(new int[]
            { mpr[m], mpr[m + 1] });
            int[] xpos = locateRange(mpr[m], mpr[m + 1]);
            for (int x = 0; x < xpos.length; x += 2)
            {
              fromRange.addElement(new int[]
              { xpos[x], xpos[x + 1] });
            }
          }
        }
      }
      int[] from = new int[fromRange.size() * 2];
      int[] to = new int[toRange.size() * 2];
      int[] r;
      for (int f = 0, fSize = fromRange.size(); f < fSize; f++)
      {
        r = (int[]) fromRange.elementAt(f);
        from[f * 2] = r[0];
        from[f * 2 + 1] = r[1];
      }
      for (int f = 0, fSize = toRange.size(); f < fSize; f++)
      {
        r = (int[]) toRange.elementAt(f);
        to[f * 2] = r[0];
        to[f * 2 + 1] = r[1];
      }
      copy.setMap(new MapList(from, to, map.getFromRatio(), map
              .getToRatio()));
    }
    return copy;
  }

  public static void main(String[] args)
  {
    /**
     * trite test of the intersectVisContigs method for a simple DNA -> Protein
     * exon map and a range of visContigs
     */
    MapList fk = new MapList(new int[]
    { 1, 6, 8, 13, 15, 23 }, new int[]
    { 1, 7 }, 3, 1);
    Mapping m = new Mapping(fk);
    Mapping m_1 = m.intersectVisContigs(new int[]
    { fk.getFromLowest(), fk.getFromHighest() });
    Mapping m_2 = m.intersectVisContigs(new int[]
    { 1, 7, 11, 20 });
    System.out.println("" + m_1.map.getFromRanges());

  }

  /**
   * get the sequence being mapped to - if any
   * 
   * @return null or a dataset sequence
   */
  public SequenceI getTo()
  {
    return to;
  }

  /**
   * set the dataset sequence being mapped to if any
   * 
   * @param tto
   */
  public void setTo(SequenceI tto)
  {
    to = tto;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#finalize()
   */
  protected void finalize() throws Throwable
  {
    map = null;
    to = null;
    super.finalize();
  }

}
