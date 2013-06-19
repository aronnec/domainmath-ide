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
package jalview.io.vamsas;

import java.util.Vector;

import uk.ac.vamsas.client.Vobject;
import uk.ac.vamsas.objects.core.Local;
import uk.ac.vamsas.objects.core.MapType;
import uk.ac.vamsas.objects.core.Mapped;
import uk.ac.vamsas.objects.core.RangeType;
import uk.ac.vamsas.objects.core.Seg;
import jalview.io.VamsasAppDatastore;

/**
 * Enhances DatastoreItem objects with additional functions to do with RangeType
 * objects
 * 
 * @author JimP
 * 
 */
public abstract class Rangetype extends DatastoreItem
{

  public Rangetype()
  {
    super();
  }

  public Rangetype(VamsasAppDatastore datastore)
  {
    super(datastore);
  }

  public Rangetype(VamsasAppDatastore datastore, Vobject vobj, Class jvClass)
  {
    super(datastore, vobj, jvClass);
  }

  public Rangetype(VamsasAppDatastore datastore, Object jvobj, Class vClass)
  {
    super(datastore, jvobj, vClass);
  }

  /**
   * get real bounds of a RangeType's specification. start and end are an
   * inclusive range within which all segments and positions lie. TODO: refactor
   * to vamsas utils
   * 
   * @param dseta
   * @return int[] { start, end}
   */
  protected int[] getBounds(RangeType dseta)
  {
    if (dseta != null)
    {
      int[] se = null;
      if (dseta.getSegCount() > 0 && dseta.getPosCount() > 0)
      {
        throw new Error(
                "Invalid vamsas RangeType - cannot resolve both lists of Pos and Seg from choice!");
      }
      if (dseta.getSegCount() > 0)
      {
        se = getSegRange(dseta.getSeg(0), true);
        for (int s = 1, sSize = dseta.getSegCount(); s < sSize; s++)
        {
          int nse[] = getSegRange(dseta.getSeg(s), true);
          if (se[0] > nse[0])
          {
            se[0] = nse[0];
          }
          if (se[1] < nse[1])
          {
            se[1] = nse[1];
          }
        }
      }
      if (dseta.getPosCount() > 0)
      {
        // could do a polarity for pos range too. and pass back indication of
        // discontinuities.
        int pos = dseta.getPos(0).getI();
        se = new int[]
        { pos, pos };
        for (int p = 0, pSize = dseta.getPosCount(); p < pSize; p++)
        {
          pos = dseta.getPos(p).getI();
          if (se[0] > pos)
          {
            se[0] = pos;
          }
          if (se[1] < pos)
          {
            se[1] = pos;
          }
        }
      }
      return se;
    }
    return null;
  }

  /**
   * map from a rangeType's internal frame to the referenced object's coordinate
   * frame.
   * 
   * @param dseta
   * @return int [] { ref(pos)...} for all pos in rangeType's frame.
   */
  protected int[] getMapping(RangeType dseta)
  {
    Vector posList = new Vector();
    if (dseta != null)
    {
      int[] se = null;
      if (dseta.getSegCount() > 0 && dseta.getPosCount() > 0)
      {
        throw new Error(
                "Invalid vamsas RangeType - cannot resolve both lists of Pos and Seg from choice!");
      }
      if (dseta.getSegCount() > 0)
      {
        for (int s = 0, sSize = dseta.getSegCount(); s < sSize; s++)
        {
          se = getSegRange(dseta.getSeg(s), false);
          int se_end = se[1 - se[2]] + (se[2] == 0 ? 1 : -1);
          for (int p = se[se[2]]; p != se_end; p += se[2] == 0 ? 1 : -1)
          {
            posList.add(new Integer(p));
          }
        }
      }
      else if (dseta.getPosCount() > 0)
      {
        int pos = dseta.getPos(0).getI();

        for (int p = 0, pSize = dseta.getPosCount(); p < pSize; p++)
        {
          pos = dseta.getPos(p).getI();
          posList.add(new Integer(pos));
        }
      }
    }
    if (posList != null && posList.size() > 0)
    {
      int[] range = new int[posList.size()];
      for (int i = 0; i < range.length; i++)
      {
        range[i] = ((Integer) posList.elementAt(i)).intValue();
      }
      posList.clear();
      return range;
    }
    return null;
  }

  protected int[] getIntervals(RangeType range)
  {
    int[] intervals = null;
    Vector posList = new Vector();
    if (range != null)
    {
      int[] se = null;
      if (range.getSegCount() > 0 && range.getPosCount() > 0)
      {
        throw new Error(
                "Invalid vamsas RangeType - cannot resolve both lists of Pos and Seg from choice!");
      }
      if (range.getSegCount() > 0)
      {
        for (int s = 0, sSize = range.getSegCount(); s < sSize; s++)
        {
          se = getSegRange(range.getSeg(s), false);
          posList.addElement(new Integer(se[0]));
          posList.addElement(new Integer(se[1]));
        }
      }
      else if (range.getPosCount() > 0)
      {
        int pos = range.getPos(0).getI();

        for (int p = 0, pSize = range.getPosCount(); p < pSize; p++)
        {
          pos = range.getPos(p).getI();
          posList.add(new Integer(pos));
          posList.add(new Integer(pos));
        }
      }
    }
    if (posList != null && posList.size() > 0)
    {
      intervals = new int[posList.size()];
      java.util.Enumeration e = posList.elements();
      int i = 0;
      while (e.hasMoreElements())
      {
        intervals[i++] = ((Integer) e.nextElement()).intValue();
      }
    }
    return intervals;
  }

  /**
   * initialise a range type object from a set of start/end inclusive intervals
   * 
   * @param mrt
   * @param range
   */
  protected void initRangeType(RangeType mrt, int[] range)
  {
    for (int i = 0; i < range.length; i += 2)
    {
      Seg vSeg = new Seg();
      vSeg.setStart(range[i]);
      vSeg.setEnd(range[i + 1]);
      vSeg.setInclusive(true);
      mrt.addSeg(vSeg);
    }
  }

  /**
   * 
   * @param maprange
   *          where the from range is the local mapped range, and the to range
   *          is the 'mapped' range in the MapRangeType
   * @param default unit for local
   * @param default unit for mapped
   * @return MapList
   */
  protected jalview.util.MapList parsemapType(MapType maprange, int localu,
          int mappedu)
  {
    jalview.util.MapList ml = null;
    int[] localRange = getIntervals(maprange.getLocal());
    int[] mappedRange = getIntervals(maprange.getMapped());
    long lu = maprange.getLocal().hasUnit() ? maprange.getLocal().getUnit()
            : localu;
    long mu = maprange.getMapped().hasUnit() ? maprange.getMapped()
            .getUnit() : mappedu;
    ml = new jalview.util.MapList(localRange, mappedRange, (int) lu,
            (int) mu);
    return ml;
  }

  protected jalview.util.MapList parsemapType(MapType map)
  {
    if (!map.getLocal().hasUnit() || map.getMapped().hasUnit())
    {
      jalview.bin.Cache.log
              .warn("using default mapping length of 1:1 for map "
                      + (map.isRegistered() ? map.getVorbaId().toString()
                              : ("<no Id registered> " + map.toString())));
    }
    return parsemapType(map, 1, 1);
  }

  /**
   * initialise a MapType object from a MapList object.
   * 
   * @param maprange
   * @param ml
   * @param setUnits
   */
  protected void initMapType(MapType maprange, jalview.util.MapList ml,
          boolean setUnits)
  {
    initMapType(maprange, ml, setUnits, false);
  }

  /**
   * 
   * @param maprange
   * @param ml
   * @param setUnits
   * @param reverse
   *          - reverse MapList mapping for Local and Mapped ranges and units
   */
  protected void initMapType(MapType maprange, jalview.util.MapList ml,
          boolean setUnits, boolean reverse)
  {
    if (ml == null)
    {
      throw new Error(
              "Implementation error. MapList is null for initMapType.");
    }
    maprange.setLocal(new Local());
    maprange.setMapped(new Mapped());
    if (!reverse)
    {
      initRangeType(maprange.getLocal(), ml.getFromRanges());
      initRangeType(maprange.getMapped(), ml.getToRanges());
    }
    else
    {
      initRangeType(maprange.getLocal(), ml.getToRanges());
      initRangeType(maprange.getMapped(), ml.getFromRanges());
    }
    if (setUnits)
    {
      if (!reverse)
      {
        maprange.getLocal().setUnit(ml.getFromRatio());
        maprange.getMapped().setUnit(ml.getToRatio());
      }
      else
      {
        maprange.getLocal().setUnit(ml.getToRatio());
        maprange.getMapped().setUnit(ml.getFromRatio());
      }
      // TODO: and verify - raise an implementation fault notice if local/mapped
      // range % Local/Mapped Ratio != 0
      // if (uk.ac.vamsas.objects.utils.Range.getIntervals(range))

    }
  }

}
