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

import jalview.datamodel.DBRefEntry;
import jalview.datamodel.SequenceI;
import uk.ac.vamsas.objects.core.DataSet;
import uk.ac.vamsas.objects.core.DbRef;
import uk.ac.vamsas.objects.core.Map;
import uk.ac.vamsas.objects.core.Sequence;
import jalview.io.VamsasAppDatastore;

public class Dbref extends Rangetype
{
  jalview.datamodel.SequenceI sq = null;

  uk.ac.vamsas.objects.core.Sequence sequence = null;

  DataSet ds;

  public Dbref(VamsasAppDatastore datastore, DBRefEntry dbentry,
          jalview.datamodel.SequenceI sq2,
          uk.ac.vamsas.objects.core.Sequence sequence2, DataSet dataset)
  {
    super(datastore, dbentry, DbRef.class);
    // initialise object specific attributes
    sq = sq2;
    sequence = sequence2;
    this.jvobj = dbentry;
    ds = dataset;
    // call the accessors
    doSync();
  }

  public Dbref(VamsasAppDatastore datastore, DbRef ref, Sequence vdseq,
          SequenceI dsseq)
  {
    super(datastore, ref, jalview.datamodel.DBRefEntry.class);
    sequence = vdseq;
    sq = dsseq;
    ds = (DataSet) vdseq.getV_parent();
    doJvUpdate();
  }

  public void updateToDoc()
  {
    DbRef dbref = (DbRef) this.vobj;
    DBRefEntry jvobj = (DBRefEntry) this.jvobj;
    dbref.setAccessionId(jvobj.getAccessionId());
    dbref.setSource(jvobj.getSource());
    dbref.setVersion(jvobj.getVersion());
    if (jvobj.getMap() != null)
    {
      // Record mapping to external database coordinate system.
      jalview.datamodel.Mapping mp = jvobj.getMap();
      if (mp.getMap() != null)
      {
        Map vMap = null;
        if (dbref.getMapCount() == 0)
        {
          vMap = new Map();
          initMapType(vMap, mp.getMap(), true);
          dbref.addMap(vMap);
        }
        else
        {
          // we just update the data anyway.
          vMap = dbref.getMap(0);
          initMapType(vMap, mp.getMap(), true);
        }
        updateMapTo(mp);
      }
    }
    else
    {
      jalview.bin.Cache.log.debug("Ignoring mapless DbRef.Map "
              + jvobj.getSrcAccString());
    }

  }

  /**
   * ugly hack to try to get the embedded sequences within a database reference
   * to be stored in the document's dataset.
   * 
   * @param mp
   */
  private void updateMapTo(jalview.datamodel.Mapping mp)
  {
    log.info("Performing updateMapTo remove this message when we know what we're doing.");
    // TODO determine how sequences associated with database mappings are stored
    // in the document
    if (mp != null && mp.getTo() != null)
    {
      if (mp.getTo().getDatasetSequence() == null)
      {
        // TODO: fix this hinky sh!t
        DatastoreItem dssync = dsReg.getDatastoreItemFor(mp.getTo());
        if (dssync == null)
        {
          // sync the dataset sequence, if it hasn't been done already.
          // TODO: ensure real dataset sequence corresponding to getTo is
          // recovered
          dssync = new Datasetsequence(
                  datastore,
                  mp.getTo(),
                  (mp.getMappedWidth() == mp.getWidth()) ? sequence
                          .getDictionary()
                          : ((mp.getMappedWidth() == 3) ? uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_NA
                                  : uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_AA),
                  ds);
        }
        //
        // TODO: NOW add a mapping between new dataset sequence and sequence
        // associated with the database reference

        // dna mappings only...
        // new jalview.io.vamsas.Sequencemapping(datastore, mp, sequence, ds);

      }

    }
    else
    {
      log.debug("Ignoring non-dataset sequence mapping.");
    }
  }

  public void updateFromDoc()
  {
    DbRef vobj = (DbRef) this.vobj;
    DBRefEntry jvobj = (DBRefEntry) this.jvobj;
    jvobj.setAccessionId(vobj.getAccessionId());
    jvobj.setSource(vobj.getSource());
    jvobj.setVersion(vobj.getVersion());
    // add new dbref
    if (vobj.getMapCount() > 0)
    {
      // TODO: Jalview ignores all the other maps
      if (vobj.getMapCount() > 1)
      {
        jalview.bin.Cache.log
                .debug("Ignoring additional mappings on DbRef: "
                        + jvobj.getSource() + ":" + jvobj.getAccessionId());
      }
      jalview.datamodel.Mapping mp = new jalview.datamodel.Mapping(
              parsemapType(vobj.getMap(0)));
      if (jvobj.getMap() == null || !mp.equals(jvobj.getMap()))
      {
        jvobj.setMap(mp);
      }
    }
  }

  public void conflict()
  {
    DbRef vobj = (DbRef) this.vobj;
    DBRefEntry jvobj = (DBRefEntry) this.jvobj;
    jalview.bin.Cache.log.debug("Conflict in dbentry update for "
            + vobj.getAccessionId() + vobj.getSource() + " "
            + vobj.getVorbaId());
    // TODO Auto-generated method stub

  }

  public void addFromDocument()
  {
    DbRef vobj = (DbRef) this.vobj;
    DBRefEntry jvobj = (DBRefEntry) this.jvobj;
    // add new dbref
    sq.addDBRef(jvobj = new jalview.datamodel.DBRefEntry(vobj.getSource()
            .toString(), vobj.getVersion().toString(), vobj
            .getAccessionId().toString()));
    if (vobj.getMapCount() > 0)
    {
      // TODO: Jalview ignores all the other maps
      if (vobj.getMapCount() > 1)
      {
        jalview.bin.Cache.log
                .debug("Ignoring additional mappings on DbRef: "
                        + jvobj.getSource() + ":" + jvobj.getAccessionId());
      }
      jalview.datamodel.Mapping mp = new jalview.datamodel.Mapping(
              parsemapType(vobj.getMap(0)));
      jvobj.setMap(mp);
    }
    // TODO: jalview ignores links and properties because it doesn't know what
    // to do with them.

    bindjvvobj(jvobj, vobj);
  }

  public void addToDocument()
  {
    DBRefEntry jvobj = (DBRefEntry) this.jvobj;
    DbRef dbref = new DbRef();
    bindjvvobj(jvobj, dbref);
    dbref.setAccessionId(jvobj.getAccessionId());
    dbref.setSource(jvobj.getSource());
    dbref.setVersion(jvobj.getVersion());
    sequence.addDbRef(dbref);
    if (jvobj.getMap() != null)
    {
      jalview.datamodel.Mapping mp = jvobj.getMap();
      if (mp.getMap() != null)
      {
        Map vMap = new Map();
        initMapType(vMap, mp.getMap(), true);
        dbref.addMap(vMap);
        updateMapTo(mp);
      }
      else
      {
        jalview.bin.Cache.log.debug("Ignoring mapless DbRef.Map "
                + jvobj.getSrcAccString());
      }
    }
  }

}
