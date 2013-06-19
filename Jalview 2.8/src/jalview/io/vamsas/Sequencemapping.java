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

import jalview.datamodel.AlignedCodonFrame;
import jalview.datamodel.Mapping;
import jalview.datamodel.SequenceI;
import jalview.gui.Desktop;
import jalview.io.VamsasAppDatastore;
import uk.ac.vamsas.client.Vobject;
import uk.ac.vamsas.objects.core.AlignmentSequence;
import uk.ac.vamsas.objects.core.DataSet;
import uk.ac.vamsas.objects.core.Sequence;
import uk.ac.vamsas.objects.core.SequenceMapping;
import uk.ac.vamsas.objects.core.SequenceType;

/**
 * binds a vamsas sequence mapping object from the vamsas document to a maplist
 * object associated with a mapping in the Jalview model. We use the maplist
 * object because these are referred to both in the Mapping object associated
 * with a jalview.datamodel.DBRefEntry and in the array of
 * jalview.datamodel.AlCodonFrame objects that Jalview uses to propagate
 * sequence mapping position highlighting across the views.
 * 
 * @author JimP
 * 
 */
public class Sequencemapping extends Rangetype
{
  public Sequencemapping(VamsasAppDatastore datastore,
          SequenceMapping sequenceMapping)
  {
    super(datastore, sequenceMapping, jalview.util.MapList.class);
    doJvUpdate();
  }

  private SequenceType from;

  private DataSet ds;

  private Mapping mjvmapping;

  /**
   * create or update a vamsas sequence mapping corresponding to a jalview
   * Mapping between two dataset sequences
   * 
   * @param datastore
   * @param mjvmapping
   * @param from
   * @param ds
   */
  public Sequencemapping(VamsasAppDatastore datastore,
          jalview.datamodel.Mapping mjvmapping,
          uk.ac.vamsas.objects.core.SequenceType from,
          uk.ac.vamsas.objects.core.DataSet ds)
  {
    super(datastore, mjvmapping.getMap(), SequenceMapping.class);
    this.from = from;
    this.ds = ds;
    this.mjvmapping = mjvmapping;
    validate();
    doSync();
  }

  /**
   * local check that extant mapping context is valid
   */
  public void validate()
  {

    SequenceMapping sequenceMapping = (SequenceMapping) vobj;
    if (sequenceMapping == null)
    {
      return;
    }
    if (from != null && sequenceMapping.getLoc() != from)
    {
      jalview.bin.Cache.log.warn("Probable IMPLEMENTATION ERROR: " + from
              + " doesn't match the local mapping sequence.");
    }
    if (ds != null && sequenceMapping.is__stored_in_document()
            && sequenceMapping.getV_parent() != ds)
    {
      jalview.bin.Cache.log
              .warn("Probable IMPLEMENTATION ERROR: "
                      + ds
                      + " doesn't match the parent of the bound sequence mapping object.");
    }
  }

  public void addToDocument()
  {
    add(mjvmapping, from, ds);
  }

  public void addFromDocument()
  {
    add((SequenceMapping) vobj);
  }

  public void conflict()
  {
    conflict(mjvmapping, (SequenceMapping) vobj);

  }

  public void updateToDoc()
  {
    update(mjvmapping, (SequenceMapping) vobj);
  }

  public void updateFromDoc()
  {
    update((SequenceMapping) vobj, (jalview.datamodel.Mapping) jvobj);
  }

  private void conflict(Mapping mjvmapping, SequenceMapping sequenceMapping)
  {
    System.err.println("Conflict in update of sequenceMapping "
            + sequenceMapping.getVorbaId());
  }

  private void add(Mapping mjvmapping,
          uk.ac.vamsas.objects.core.SequenceType from, DataSet ds)
  {
    SequenceI jvto = mjvmapping.getTo();
    while (jvto.getDatasetSequence() != null)
    {
      jvto = jvto.getDatasetSequence();
    }
    SequenceType to = (SequenceType) getjv2vObj(jvto);
    if (to == null)
    {
      jalview.bin.Cache.log
              .warn("FIXME NONFATAL - do a second update: Ignoring Forward Reference to seuqence not yet bound to vamsas seuqence object");
      return;
    }
    SequenceMapping sequenceMapping = new SequenceMapping();
    sequenceMapping.setLoc(from);
    sequenceMapping.setMap(to);
    boolean dnaToProt = false, sense = false;
    // ensure that we create a mapping with the correct sense
    if (((Sequence) sequenceMapping.getLoc()).getDictionary().equals(
            uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_NA))
    {
      if (((Sequence) sequenceMapping.getMap()).getDictionary().equals(
              uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_AA))
      {
        dnaToProt = true;
        sense = true;
      }
    }
    else
    {
      if (((Sequence) sequenceMapping.getMap()).getDictionary().equals(
              uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_NA))
      {
        dnaToProt = true;
        sense = false;
      }
    }

    if (!dnaToProt)
    {
      jalview.bin.Cache.log
              .warn("Ignoring Mapping - don't support protein to protein mapping in vamsas document yet.");
      return;
    }
    if (ds == null)
    {
      // locate dataset for storage of SequenceMapping
      if (sense)
      {
        ds = (DataSet) ((uk.ac.vamsas.client.Vobject) sequenceMapping
                .getLoc()).getV_parent();
      }
      else
      {
        ds = (DataSet) ((uk.ac.vamsas.client.Vobject) sequenceMapping
                .getMap()).getV_parent();
      }
    }
    if (sense)
    {
      this.initMapType(sequenceMapping, mjvmapping.getMap(), true);
    }
    else
    {
      this.initMapType(sequenceMapping, mjvmapping.getMap().getInverse(),
              true);
    }
    ds.addSequenceMapping(sequenceMapping);
    sequenceMapping.setProvenance(this
            .dummyProvenance("user defined coding region translation")); // TODO:
    // correctly
    // construct
    // provenance
    // based
    // on
    // source
    // of
    // mapping
    bindjvvobj(mjvmapping.getMap(), sequenceMapping);

    jalview.bin.Cache.log.debug("Successfully created mapping "
            + sequenceMapping.getVorbaId());
  }

  // private void update(jalview.util.MapList mjvmapping,
  // SequenceMapping sequenceMapping)
  {
    jalview.bin.Cache.log
            .error("Not implemented: Jalview Update Alcodon Mapping:TODO!");
  }

  private void update(SequenceMapping sequenceMapping,
          jalview.datamodel.Mapping mjvmapping)
  {
    jalview.bin.Cache.log
            .error("Not implemented: Update DBRef Mapping from Jalview");
  }

  private void update(jalview.datamodel.Mapping mjvmapping,
          SequenceMapping sequenceMapping)
  {
    jalview.bin.Cache.log
            .error("Not implemented: Jalview Update Sequence DBRef Mapping");
  }

  /**
   * bind a SequenceMapping to a live AlCodonFrame element limitations:
   * Currently, jalview only deals with mappings between dataset sequences, and
   * even then, only between those that map from DNA to Protein.
   * 
   * @param sequenceMapping
   */
  private void add(SequenceMapping sequenceMapping)
  {
    Object mobj;
    SequenceI from = null, to = null;
    boolean dnaToProt = false, sense = false;
    Sequence sdloc = null, sdmap = null;
    if (sequenceMapping.getLoc() instanceof AlignmentSequence)
    {
      sdloc = (Sequence) ((AlignmentSequence) sequenceMapping.getLoc())
              .getRefid();
    }
    else
    {
      sdloc = ((Sequence) sequenceMapping.getLoc());
    }
    if (sequenceMapping.getMap() instanceof AlignmentSequence)
    {
      sdmap = (Sequence) ((AlignmentSequence) sequenceMapping.getMap())
              .getRefid();
    }
    else
    {
      sdmap = ((Sequence) sequenceMapping.getMap());
    }
    if (sdloc == null || sdmap == null)
    {
      jalview.bin.Cache.log.info("Ignoring non sequence-sequence mapping");
      return;
    }
    mobj = this.getvObj2jv((Vobject) sdloc);
    if (mobj instanceof SequenceI)
    {
      from = (SequenceI) mobj;
    }
    mobj = this.getvObj2jv((Vobject) sdmap);
    if (mobj instanceof SequenceI)
    {
      to = (SequenceI) mobj;
    }
    if (from == null || to == null)
    {

      jalview.bin.Cache.log
              .error("Probable Vamsas implementation error : unbound dataset sequences involved in a mapping are being parsed!");
      return;
    }

    if (sdloc.getDictionary().equals(
            uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_NA))
    {
      if (sdmap.getDictionary().equals(
              uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_AA))
      {
        dnaToProt = true;
        sense = true;
      }
      // else {

      // }
    }
    else
    {
      if (sdmap.getDictionary().equals(
              uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_NA))
      {
        dnaToProt = true;
        sense = false;
      }
    }
    // create mapping storage object and make each dataset alignment reference
    // it.
    jalview.datamodel.AlignmentI dsLoc = (jalview.datamodel.AlignmentI) getvObj2jv(sdloc
            .getV_parent());
    jalview.datamodel.AlignmentI dsMap = (jalview.datamodel.AlignmentI) getvObj2jv(sdmap
            .getV_parent());
    AlignedCodonFrame afc = new AlignedCodonFrame(0);

    if (dsLoc != null && dsLoc != dsMap)
    {
      dsLoc.addCodonFrame(afc);
    }
    if (dsMap != null)
    {
      dsMap.addCodonFrame(afc);
    }
    // create and add the new mapping to (each) dataset's codonFrame

    jalview.util.MapList mapping = null;
    if (dnaToProt)
    {
      if (!sense)
      {
        mapping = this.parsemapType(sequenceMapping, 1, 3); // invert sense
        mapping = new jalview.util.MapList(mapping.getToRanges(),
                mapping.getFromRanges(), mapping.getToRatio(),
                mapping.getFromRatio());
        afc.addMap(to, from, mapping);
      }
      else
      {
        mapping = this.parsemapType(sequenceMapping, 3, 1); // correct sense
        afc.addMap(from, to, mapping);
      }
    }
    else
    {
      mapping = this.parsemapType(sequenceMapping, 1, 1); // correct sense
      afc.addMap(from, to, mapping);
    }
    bindjvvobj(mapping, sequenceMapping);
    jalview.structure.StructureSelectionManager
            .getStructureSelectionManager(Desktop.instance).addMappings(
                    new AlignedCodonFrame[]
                    { afc });
    // Try to link up any conjugate database references in the two sequences
    // matchConjugateDBRefs(from, to, mapping);
    // Try to propagate any dbrefs across this mapping.

  }

  /**
   * Complete any 'to' references in jalview.datamodel.Mapping objects
   * associated with conjugate DBRefEntry under given mapping
   * 
   * @param from
   *          sequence corresponding to from reference for sequence mapping
   * @param to
   *          sequence correspondeing to to reference for sequence mapping
   * @param smap
   *          maplist parsed in same sense as from and to
   */
  private void matchConjugateDBRefs(SequenceI from, SequenceI to,
          jalview.util.MapList smap)
  {
    if (from.getDBRef() == null && to.getDBRef() == null)
    {
      if (jalview.bin.Cache.log.isDebugEnabled())
      {
        jalview.bin.Cache.log.debug("Not matching conjugate refs for "
                + from.getName() + " and " + to.getName());
      }
      return;
    }
    if (jalview.bin.Cache.log.isDebugEnabled())
    {
      jalview.bin.Cache.log.debug("Matching conjugate refs for "
              + from.getName() + " and " + to.getName());
    }
    jalview.datamodel.DBRefEntry[] fdb = from.getDBRef();
    jalview.datamodel.DBRefEntry[] tdb = new jalview.datamodel.DBRefEntry[to
            .getDBRef().length];
    int tdblen = to.getDBRef().length;
    System.arraycopy(to.getDBRef(), 0, tdb, 0, tdblen);
    Vector matched = new Vector();
    jalview.util.MapList smapI = smap.getInverse();
    for (int f = 0; f < fdb.length; f++)
    {
      jalview.datamodel.DBRefEntry fe = fdb[f];
      jalview.datamodel.Mapping fmp = fe.getMap();
      boolean fmpnnl = fmp != null;
      // if (fmpnnl && fmp.getTo()!=null)
      // {
      // jalview.bin.Cache.log.debug("Not overwriting existing To reference in
      // "+fe);
      // continue;
      // }
      // smap from maps from fe.local to fe.map
      boolean smapfromlocal2fe = (fmpnnl) ? smap.equals(fmp.getMap())
              : false;
      // smap from maps from fe.map to fe.local.
      boolean smapfromfemap2local = (fmpnnl) ? smapI.equals(fmp.getMap())
              : false;
      for (int t = 0; t < tdblen; t++)
      {
        jalview.datamodel.DBRefEntry te = tdb[t];
        if (te != null)
        {
          if (fe.getSource().equals(te.getSource())
                  && fe.getAccessionId().equals(te.getAccessionId()))
          {
            jalview.datamodel.Mapping tmp = te.getMap();
            boolean tmpnnl = tmp != null;
            if (tmpnnl && tmp.getTo() != null)
            {

            }
            // smap to maps from te.local to te.map
            boolean smaptolocal2tm = (tmpnnl) ? smap.equals(tmp.getMap())
                    : false;
            // smap to maps from te.map to te.local
            boolean smaptotemap2local = (tmpnnl) ? smapI.equals(fmp
                    .getMap()) : false;
            if (smapfromlocal2fe && smaptotemap2local)
            {
              // smap implies mapping from to to from
              fmp.setTo(to);
              tmp.setTo(from);
            }
            else if (smapfromfemap2local && smaptolocal2tm)
            {
              fmp.setTo(to);
            }
          }

        }
      }
    }
  }
}
