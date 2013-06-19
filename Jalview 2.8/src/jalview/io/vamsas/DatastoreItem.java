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

import jalview.bin.Cache;
import jalview.io.VamsasAppDatastore;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Vector;

import uk.ac.vamsas.client.IClientDocument;
import uk.ac.vamsas.client.Vobject;
import uk.ac.vamsas.client.VorbaId;
import uk.ac.vamsas.objects.core.Entry;
import uk.ac.vamsas.objects.core.Provenance;
import uk.ac.vamsas.objects.core.Seg;

/**
 * Holds all the common machinery for binding objects to vamsas objects
 * 
 * @author JimP
 * 
 */
public abstract class DatastoreItem
{
  /**
   * 
   */
  Entry provEntry = null;

  IClientDocument cdoc;

  Hashtable vobj2jv;

  IdentityHashMap jv2vobj;

  boolean tojalview = false;

  /**
   * shared log instance
   */
  protected static org.apache.log4j.Logger log = org.apache.log4j.Logger
          .getLogger(DatastoreItem.class);

  /**
   * note: this is taken verbatim from jalview.io.VamsasAppDatastore
   * 
   * @return the Vobject bound to Jalview datamodel object
   */
  protected Vobject getjv2vObj(Object jvobj)
  {
    if (jv2vobj.containsKey(jvobj))
    {
      return cdoc.getObject((VorbaId) jv2vobj.get(jvobj));
    }
    if (Cache.log.isDebugEnabled())
    {
      Cache.log.debug("Returning null VorbaID binding for jalview object "
              + jvobj);
    }
    return null;
  }

  /**
   * 
   * @param vobj
   * @return Jalview datamodel object bound to the vamsas document object
   */
  protected Object getvObj2jv(uk.ac.vamsas.client.Vobject vobj)
  {
    if (vobj2jv == null)
      return null;
    VorbaId id = vobj.getVorbaId();
    if (id == null)
    {
      id = cdoc.registerObject(vobj);
      Cache.log
              .debug("Registering new object and returning null for getvObj2jv");
      return null;
    }
    if (vobj2jv.containsKey(vobj.getVorbaId()))
    {
      return vobj2jv.get(vobj.getVorbaId());
    }
    return null;
  }

  /**
   * note: this is taken verbatim from jalview.io.VamsasAppDatastore with added
   * call to updateRegistryEntry
   * 
   * @param jvobj
   * @param vobj
   */
  protected void bindjvvobj(Object jvobj, uk.ac.vamsas.client.Vobject vobj)
  {
    VorbaId id = vobj.getVorbaId();
    if (id == null)
    {
      id = cdoc.registerObject(vobj);
      if (id == null || vobj.getVorbaId() == null
              || cdoc.getObject(id) != vobj)
      {
        Cache.log.error("Failed to get id for "
                + (vobj.isRegisterable() ? "registerable"
                        : "unregisterable") + " object " + vobj);
      }
    }
    if (vobj2jv.containsKey(vobj.getVorbaId())
            && !(vobj2jv.get(vobj.getVorbaId())).equals(jvobj))
    {
      Cache.log.debug(
              "Warning? Overwriting existing vamsas id binding for "
                      + vobj.getVorbaId(), new Exception(
                      "Overwriting vamsas id binding."));
    }
    else if (jv2vobj.containsKey(jvobj)
            && !((VorbaId) jv2vobj.get(jvobj)).equals(vobj.getVorbaId()))
    {
      Cache.log.debug(
              "Warning? Overwriting existing jalview object binding for "
                      + jvobj, new Exception(
                      "Overwriting jalview object binding."));
    }
    /*
     * Cache.log.error("Attempt to make conflicting object binding! "+vobj+" id "
     * +vobj.getVorbaId()+" already bound to "+getvObj2jv(vobj)+" and "+jvobj+"
     * already bound to "+getjv2vObj(jvobj),new Exception("Excessive call to
     * bindjvvobj")); }
     */
    // we just update the hash's regardless!
    Cache.log.debug("Binding " + vobj.getVorbaId() + " to " + jvobj);
    vobj2jv.put(vobj.getVorbaId(), jvobj);
    // JBPNote - better implementing a hybrid invertible hash.
    jv2vobj.put(jvobj, vobj.getVorbaId());
    if (jvobj == this.jvobj || vobj == this.vobj)
    {
      updateRegistryEntry(jvobj, vobj);
    }
  }

  /**
   * update the vobj and jvobj references and the registry entry for this
   * datastore object called by bindjvvobj and replacejvobjmapping
   */
  private void updateRegistryEntry(Object jvobj, Vobject vobj)
  {
    if (this.jvobj != null && this.vobj != null)
    {
      Cache.log.debug("updating dsobj registry. ("
              + this.getClass().getName() + ")");
    }
    this.jvobj = jvobj;
    this.vobj = vobj;
    dsReg.registerDsObj(this);
  }

  /**
   * replaces oldjvobject with newjvobject in the Jalview Object <> VorbaID
   * binding tables note: originally taken verbatim from
   * jalview.io.VamsasAppDatastore with added call to updateRegistryEntry
   * 
   * @param oldjvobject
   * @param newjvobject
   *          (may be null to forget the oldjvobject's document mapping)
   * 
   */
  protected void replaceJvObjMapping(Object oldjvobject, Object newjvobject)
  {
    Object vobject = jv2vobj.remove(oldjvobject);
    if (vobject == null)
    {
      throw new Error(
              "IMPLEMENTATION ERROR: old jalview object is not bound ! ("
                      + oldjvobject + ")");
    }
    if (newjvobject != null)
    {
      jv2vobj.put(newjvobject, vobject);
      vobj2jv.put(vobject, newjvobject);
      updateRegistryEntry(newjvobject, vobj);
    }
  }

  public DatastoreItem()
  {
    super();
  }

  public DatastoreItem(VamsasAppDatastore datastore)
  {
    this();
    initDatastoreItem(datastore);
    // TODO Auto-generated constructor stub
  }

  /**
   * construct and initialise datastore object and retrieve object bound to
   * vobj2 and validate it against boundType
   * 
   * @param datastore2
   * @param vobj2
   * @param boundType
   */
  public DatastoreItem(VamsasAppDatastore datastore2, Vobject vobj2,
          Class boundType)
  {
    this(datastore2);
    vobj = vobj2;
    jvobj = getvObj2jv(vobj2);
    tojalview = true;
    if (jvobj != null && !(boundType.isAssignableFrom(jvobj.getClass())))
    {
      throw new Error("Implementation Error: Vamsas Document Class "
              + vobj.getClass() + " should bind to a " + boundType
              + " (found a " + jvobj.getClass() + ")");
    }
    dsReg.registerDsObj(this);
  }

  /**
   * construct and initialise datastore object and retrieve document object
   * bound to Jalview object jvobj2 and validate it against boundType
   * 
   * @param datastore2
   *          the datastore
   * @param jvobj2
   *          the jalview object
   * @param boundToType
   *          - the document object class that the bound object should be
   *          assignable from
   */
  public DatastoreItem(VamsasAppDatastore datastore2, Object jvobj2,
          Class boundToType)
  {
    this(datastore2);
    jvobj = jvobj2;
    tojalview = false;
    vobj = getjv2vObj(jvobj);
    if (vobj != null && !(boundToType.isAssignableFrom(vobj.getClass())))
    {
      throw new Error("Implementation Error: Jalview Class "
              + jvobj2.getClass() + " should bind to a " + boundToType
              + " (found a " + vobj.getClass() + ")");
    }
    dsReg.registerDsObj(this);
  }

  /**
   * create a new vobj to be added to the document for the jalview object jvobj
   * (jvobj!=null, vobj==null)
   */
  public abstract void addToDocument();

  /**
   * handle a conflict where both an existing vobj has been updated and a local
   * jalview object has been updated. This method is only called from doSync,
   * when an incoming update from the vamsas session conflicts with local
   * modifications made by the Jalview user. (jvobj!=null, vobj!=null)
   */
  public abstract void conflict();

  /**
   * update an existing vobj in the document with the data and settings from
   * jvobj (jvobj!=null, vobj!=null)
   */
  public abstract void updateToDoc();

  /**
   * update the local jalview object with the data from an existing vobj in the
   * document (jvobj!=null, vobj!=null)
   */
  public abstract void updateFromDoc();

  /**
   * create a new local jvobj bound to the vobj in the document. (jvobj==null,
   * vobj!=null)
   */
  public abstract void addFromDocument();

  boolean addtodoc = false, conflicted = false, updated = false,
          addfromdoc = false, success = false;

  private boolean updatedtodoc;

  private boolean updatedfromdoc;

  /**
   * Sync jalview to document. Enact addToDocument, conflict or update dependent
   * on existence of a vobj bound to the local jvobj.
   */
  protected void doSync()
  {
    dsReg.registerDsObj(this);
    if (vobj == null)
    {
      log.debug("adding new vobject to document.");
      addtodoc = true;
      addToDocument();
    }
    else
    {
      if (vobj.isUpdated())
      {
        log.debug("Handling update conflict for existing bound vobject.");
        conflicted = true;
        conflict();
      }
      else
      {
        log.debug("updating existing vobject in document.");
        updatedtodoc = true;
        updateToDoc();
      }
    }
    // no exceptions were encountered...
    success = true;
  }

  /**
   * Update jalview from document. enact addFromDocument if no local jvobj
   * exists, or update iff jvobj exists and the vobj.isUpdated() flag is set.
   */
  protected void doJvUpdate()
  {
    dsReg.registerDsObj(this);
    if (jvobj == null)
    {
      log.debug("adding new vobject to Jalview from Document");
      addfromdoc = true;
      addFromDocument();
    }
    else
    {
      if (vobj.isUpdated())
      {
        log.debug("updating Jalview from existing bound vObject");
        updatedfromdoc = true;
        updateFromDoc();
      }
    }
  }

  VamsasAppDatastore datastore = null;

  /**
   * object in vamsas document
   */
  protected Vobject vobj = null;

  /**
   * local jalview object
   */
  protected Object jvobj = null;

  protected DatastoreRegistry dsReg;

  public void initDatastoreItem(VamsasAppDatastore ds)
  {
    datastore = ds;
    dsReg = ds.getDatastoreRegisty();
    initDatastoreItem(ds.getProvEntry(), ds.getClientDocument(),
            ds.getVamsasObjectBinding(), ds.getJvObjectBinding());
  }

  private void initDatastoreItem(Entry provEntry, IClientDocument cdoc,
          Hashtable vobj2jv, IdentityHashMap jv2vobj)
  {
    this.provEntry = provEntry;
    this.cdoc = cdoc;
    this.vobj2jv = vobj2jv;
    this.jv2vobj = jv2vobj;
  }

  protected boolean isModifiable(String modifiable)
  {
    return modifiable == null; // TODO: USE VAMSAS LIBRARY OBJECT LOCK METHODS)
  }

  protected Vector getjv2vObjs(Vector alsq)
  {
    Vector vObjs = new Vector();
    Enumeration elm = alsq.elements();
    while (elm.hasMoreElements())
    {
      vObjs.addElement(getjv2vObj(elm.nextElement()));
    }
    return vObjs;
  }

  // utility functions
  /**
   * get start<end range of segment, adjusting for inclusivity flag and
   * polarity.
   * 
   * @param visSeg
   * @param ensureDirection
   *          when true - always ensure start is less than end.
   * @return int[] { start, end, direction} where direction==1 for range running
   *         from end to start.
   */
  public int[] getSegRange(Seg visSeg, boolean ensureDirection)
  {
    boolean incl = visSeg.getInclusive();
    // adjust for inclusive flag.
    int pol = (visSeg.getStart() <= visSeg.getEnd()) ? 1 : -1; // polarity of
    // region.
    int start = visSeg.getStart() + (incl ? 0 : pol);
    int end = visSeg.getEnd() + (incl ? 0 : -pol);
    if (ensureDirection && pol == -1)
    {
      // jalview doesn't deal with inverted ranges, yet.
      int t = end;
      end = start;
      start = t;
    }
    return new int[]
    { start, end, pol < 0 ? 1 : 0 };
  }

  /**
   * provenance bits
   */
  protected jalview.datamodel.Provenance getJalviewProvenance(
          Provenance prov)
  {
    // TODO: fix App and Action entries and check use of provenance in jalview.
    jalview.datamodel.Provenance jprov = new jalview.datamodel.Provenance();
    for (int i = 0; i < prov.getEntryCount(); i++)
    {
      jprov.addEntry(prov.getEntry(i).getUser(), prov.getEntry(i)
              .getAction(), prov.getEntry(i).getDate(), prov.getEntry(i)
              .getId());
    }

    return jprov;
  }

  /**
   * 
   * @return default initial provenance list for a Jalview created vamsas
   *         object.
   */
  Provenance dummyProvenance()
  {
    return dummyProvenance(null);
  }

  protected Entry dummyPEntry(String action)
  {
    Entry entry = new Entry();
    entry.setApp(this.provEntry.getApp());
    if (action != null)
    {
      entry.setAction(action);
    }
    else
    {
      entry.setAction("created.");
    }
    entry.setDate(new java.util.Date());
    entry.setUser(this.provEntry.getUser());
    return entry;
  }

  protected Provenance dummyProvenance(String action)
  {
    Provenance prov = new Provenance();
    prov.addEntry(dummyPEntry(action));
    return prov;
  }

  protected void addProvenance(Provenance p, String action)
  {
    p.addEntry(dummyPEntry(action));
  }

  /**
   * @return true if jalview was being updated from the vamsas document
   */
  public boolean isTojalview()
  {
    return tojalview;
  }

  /**
   * @return true if addToDocument() was called.
   */
  public boolean isAddtodoc()
  {
    return addtodoc;
  }

  /**
   * @return true if conflict() was called
   */
  public boolean isConflicted()
  {
    return conflicted;
  }

  /**
   * @return true if updateFromDoc() was called
   */
  public boolean isUpdatedFromDoc()
  {
    return updatedfromdoc;
  }

  /**
   * @return true if updateToDoc() was called
   */
  public boolean isUpdatedToDoc()
  {
    return updatedtodoc;
  }

  /**
   * @return true if addFromDocument() was called.
   */
  public boolean isAddfromdoc()
  {
    return addfromdoc;
  }

  /**
   * @return true if object sync logic completed normally.
   */
  public boolean isSuccess()
  {
    return success;
  }

  /**
   * @return the vobj
   */
  public Vobject getVobj()
  {
    return vobj;
  }

  /**
   * @return the jvobj
   */
  public Object getJvobj()
  {
    return jvobj;
  }

  public boolean docWasUpdated()
  {
    return (this.addtodoc || this.updated) && this.success;
  }

  public boolean jvWasUpdated()
  {
    return (success); // TODO : Implement this properly!
  }

}
