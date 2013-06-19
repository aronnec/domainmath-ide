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
import jalview.io.VamsasAppDatastore;
import uk.ac.vamsas.objects.core.DataSet;
import uk.ac.vamsas.objects.core.DbRef;
import uk.ac.vamsas.objects.core.Sequence;

/**
 * synchronize a vamsas dataset sequence with a jalview dataset sequence. This
 * class deals with all sequence features and database references associated
 * with the jalview sequence.
 * 
 * @author JimP
 * 
 */
public class Datasetsequence extends DatastoreItem
{
  String dict;

  private DataSet dataset;

  // private AlignmentI jvdset;

  public Datasetsequence(VamsasAppDatastore vamsasAppDatastore,
          SequenceI sq, String dict, DataSet dataset)
  {
    super(vamsasAppDatastore, sq, uk.ac.vamsas.objects.core.Sequence.class);
    this.dataset = dataset;
    // this.jvdset = jvdset;
    this.dict = dict;
    doSync();
  }

  public Datasetsequence(VamsasAppDatastore vamsasAppDatastore,
          Sequence vdseq)
  {
    super(vamsasAppDatastore, vdseq, SequenceI.class);
    doJvUpdate();
  }

  public void addFromDocument()
  {
    Sequence vseq = (Sequence) vobj;
    SequenceI dsseq = new jalview.datamodel.Sequence(vseq.getName(),
            vseq.getSequence(), (int) vseq.getStart(), (int) vseq.getEnd());
    dsseq.setDescription(vseq.getDescription());
    bindjvvobj(dsseq, vseq);
    dsseq.setVamsasId(vseq.getVorbaId().getId());
    jvobj = dsseq;
    modified = true;
  }

  public void updateFromDoc()
  {
    Sequence sq = (Sequence) vobj;
    SequenceI sequence = (SequenceI) jvobj;
    if (!sequence.getSequenceAsString().equals(sq.getSequence()))
    {
      log.warn("Potential Client Error ! - mismatch of dataset sequence: and jalview internal dataset sequence.");
    }
    else
    {
      // verify and update principal attributes.
      if (sequence.getDescription() != null
              && (sequence.getDescription() == null || !sequence
                      .getDescription().equals(sq.getDescription())))
      {
        sequence.setDescription(sq.getDescription());
        modified = true;
      }
      if (sequence.getSequence() == null
              || !sequence.getSequenceAsString().equals(sq.getSequence()))
      {
        if (sequence.getStart() != sq.getStart()
                || sequence.getEnd() != sq.getEnd())
        {
          // update modified sequence.
          sequence.setSequence(sq.getSequence());
          sequence.setStart((int) sq.getStart());
          sequence.setEnd((int) sq.getEnd());
          modified = true;
        }
      }
      if (!sequence.getName().equals(sq.getName()))
      {
        sequence.setName(sq.getName());
        modified = true;
      }
      modified |= updateJvDbRefs();
      // updateJvFeatures();
    }
  }

  /*
   * private void updateJvFeatures() { Sequence vsq = (Sequence) vobj;
   * 
   * // add or update any new features/references on dataset sequence if
   * (vsq.getgetSequenceFeatures() != null) { int sfSize =
   * sq.getSequenceFeatures().length;
   * 
   * for (int sf = 0; sf < sfSize; sf++) { new
   * jalview.io.vamsas.Sequencefeature(datastore,
   * (jalview.datamodel.SequenceFeature) sq .getSequenceFeatures()[sf], dataset,
   * (Sequence) vobj); } } }
   */
  private boolean updateSqFeatures()
  {
    boolean modified = false;
    SequenceI sq = (SequenceI) jvobj;

    // add or update any new features/references on dataset sequence
    if (sq.getSequenceFeatures() != null)
    {
      int sfSize = sq.getSequenceFeatures().length;

      for (int sf = 0; sf < sfSize; sf++)
      {
        modified |= new jalview.io.vamsas.Sequencefeature(datastore,
                (jalview.datamodel.SequenceFeature) sq
                        .getSequenceFeatures()[sf], dataset,
                (Sequence) vobj).docWasUpdated();
      }
    }
    return modified;
  }

  public void addToDocument()
  {
    SequenceI sq = (SequenceI) jvobj;
    Sequence sequence = new Sequence();
    bindjvvobj(sq, sequence);
    sq.setVamsasId(sequence.getVorbaId().getId());
    sequence.setSequence(sq.getSequenceAsString());
    sequence.setDictionary(dict);
    sequence.setName(sq.getName());
    sequence.setStart(sq.getStart());
    sequence.setEnd(sq.getEnd());
    sequence.setDescription(sq.getDescription());
    dataset.addSequence(sequence);
    vobj = sequence;
    updateSqFeatures();
    updateDbRefs();// sq,(Sequence) vobj, dataset);
  }

  /**
   * sync database references from jv to document
   * 
   * @return true if document was modified
   */
  private boolean updateDbRefs()
  {
    boolean modifiedthedoc = false;
    SequenceI sq = (SequenceI) jvobj;

    if (sq.getDatasetSequence() == null && sq.getDBRef() != null)
    {
      // only sync database references for dataset sequences
      DBRefEntry[] entries = sq.getDBRef();
      // jalview.datamodel.DBRefEntry dbentry;
      for (int db = 0; db < entries.length; db++)
      {
        modifiedthedoc |= new jalview.io.vamsas.Dbref(datastore,
        // dbentry =
                entries[db], sq, (Sequence) vobj, dataset).docWasUpdated();

      }

    }
    return modifiedthedoc;
  }

  /**
   * sync database references from document to jv sequence
   * 
   * @return true if local sequence refs were modified
   */
  private boolean updateJvDbRefs()
  {
    boolean modifiedtheseq = false;
    SequenceI sq = (SequenceI) jvobj;
    Sequence vsq = (Sequence) vobj;
    if (vsq.getDbRefCount() > 0)
    {
      // only sync database references for dataset sequences
      DbRef[] entries = vsq.getDbRef();
      // DbRef dbentry;
      for (int db = 0; db < entries.length; db++)
      {
        modifiedtheseq |= new jalview.io.vamsas.Dbref(datastore,
        // dbentry =
                entries[db], vsq, sq).jvWasUpdated();
      }
    }
    return modifiedtheseq;
  }

  public void conflict()
  {
    log.warn("Conflict in dataset sequence update to document. Overwriting document");
    // TODO: could try to import from document data to jalview first. and then
    updateToDoc();
  }

  boolean modified = false;

  public void updateToDoc()
  {
    SequenceI sq = (SequenceI) jvobj;
    Sequence sequence = (Sequence) vobj;
    // verify and update principal attributes.
    if (sequence.getDescription() != null
            && (sequence.getDescription() == null || !sequence
                    .getDescription().equals(sq.getDescription())))
    {
      sequence.setDescription(sq.getDescription());
      modified = true;
    }
    if (sequence.getSequence() == null
            || !sequence.getSequence().equals(sq.getSequenceAsString()))
    {
      if (sequence.getStart() != sq.getStart()
              || sequence.getEnd() != sq.getEnd())
      {
        // update modified sequence.
        sequence.setSequence(sq.getSequenceAsString());
        sequence.setStart(sq.getStart());
        sequence.setEnd(sq.getEnd());
        modified = true;
      }
    }
    if (!dict.equals(sequence.getDictionary()))
    {
      sequence.setDictionary(dict);
      modified = true;
    }
    if (!sequence.getName().equals(sq.getName()))
    {
      sequence.setName(sq.getName());
      modified = true;
    }
    modified |= updateDbRefs();
    modified |= updateSqFeatures();
  }

  /**
   * (probably could just do vobj.isModified(), but..)
   * 
   * @return true if document's dataset sequence was modified
   */
  public boolean getModified()
  {
    return modified;
  }

}
