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

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class DatastoreRegistry
{
  protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
          .getLogger(DatastoreRegistry.class);

  /**
   * map between Datastore objects and the objects they are handling- used to
   * prevent cycles in the synchronization pattern. Keys are both vamsas objects
   * and jalview objects, values are datastore objects.
   */
  IdentityHashMap dsObjReg;

  /**
   * all datastore items - value is [jvObject,vObject]
   */
  IdentityHashMap dsItemReg;

  public DatastoreRegistry()
  {
    dsObjReg = new IdentityHashMap();
    dsItemReg = new IdentityHashMap();
  }

  /**
   * 
   * @param obj
   * @return true if obj is in the registry
   */
  public boolean isInvolvedInDsitem(Object obj)
  {
    return (obj instanceof DatastoreItem) ? dsItemReg.containsKey(obj)
            : dsObjReg.containsKey(obj);
  }

  /**
   * 
   * @param obj
   * @return DatastoreItem associated with obj - or null
   */
  public DatastoreItem getDatastoreItemFor(Object obj)
  {
    if (obj instanceof DatastoreItem)
    {
      log.debug("Returning DatastoreItem self reference.");// TODO: we could
      // store the update
      // hierarchy - so
      // retrieve parent
      // for obj.
      return (DatastoreItem) obj;
    }
    return (DatastoreItem) dsObjReg.get(obj);
  }

  synchronized void registerDsObj(DatastoreItem dsitem)
  {
    Object[] dsregitem = (Object[]) dsItemReg.get(dsitem);
    if (dsregitem == null)
    {
      // create a new item entry
      dsregitem = new Object[]
      { dsitem.jvobj, dsitem.vobj };
      dsItemReg.put(dsitem, dsregitem);
      dsObjReg.put(dsitem.jvobj, dsitem);
      dsObjReg.put(dsitem.vobj, dsitem);
    }
    else
    {
      // update registry for any changed references
      // for the jvobject
      if (dsitem.jvobj != dsregitem[0])
      {
        // overwrite existing involved entries.
        if (dsregitem[0] != null)
        {
          dsObjReg.remove(dsregitem[0]);
        }
        if ((dsregitem[0] = dsitem.jvobj) != null)
        {
          dsObjReg.put(dsregitem[0], dsitem);
        }
      }
      // and for the vobject
      if (dsitem.vobj != dsregitem[1])
      {
        // overwrite existing involved entries.
        if (dsregitem[1] != null)
        {
          dsObjReg.remove(dsregitem[1]);
        }
        if ((dsregitem[1] = dsitem.vobj) != null)
        {
          dsObjReg.put(dsregitem[1], dsitem);
        }
      }
    }
  }

  /**
   * Remove dsitem from the registry
   * 
   * @param dsitem
   * @return null or last known Object[] { jvobject, vobject } references for
   *         this dsitem
   */
  public synchronized Object[] removeDsObj(DatastoreItem dsitem)
  {
    Object[] dsregitem = null;
    // synchronized (dsItemReg)
    {
      // synchronized (dsObjReg)
      {
        dsregitem = (Object[]) dsItemReg.remove(dsitem);
        if (dsregitem != null)
        {
          // eliminate object refs too
          if (dsregitem[0] != null)
          {
            dsObjReg.remove(dsregitem[0]);
          }
          if (dsregitem[1] != null)
          {
            dsObjReg.remove(dsregitem[1]);
          }
        }
      }
    }
    return dsregitem;
  }

  protected void finalize()
  {
    if (dsObjReg != null)
    {
      Iterator items = dsObjReg.entrySet().iterator();
      // avoid the nested reference memory leak.
      while (items.hasNext())
      {
        Object[] vals = (Object[]) ((Map.Entry) items.next()).getValue();
        vals[0] = null;
        vals[1] = null;
      }
      items = null;
      dsObjReg.clear();
    }
    if (dsItemReg != null)
    {
      dsItemReg.clear();
    }
  }
}
