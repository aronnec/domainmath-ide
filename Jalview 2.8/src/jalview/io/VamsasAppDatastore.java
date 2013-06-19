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
package jalview.io;

import jalview.bin.Cache;
import jalview.datamodel.AlignedCodonFrame;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.GraphLine;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.gui.AlignViewport;
import jalview.gui.Desktop;
import jalview.gui.TreePanel;
import jalview.io.vamsas.Datasetsequence;
import jalview.io.vamsas.DatastoreItem;
import jalview.io.vamsas.DatastoreRegistry;
import jalview.io.vamsas.Rangetype;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import uk.ac.vamsas.client.*;
import uk.ac.vamsas.objects.core.*;
import uk.ac.vamsas.objects.utils.Properties;

/*
 *
 * static {
 * org.exolab.castor.util.LocalConfiguration.getInstance().getProperties().setProperty(
 * "org.exolab.castor.serializer", "org.apache.xml.serialize.XMLSerilazizer"); }
 *
 */
/*
 * TODO: check/verify consistency for vamsas sync with group associated alignment annotation
 */
public class VamsasAppDatastore
{
  /**
   * Type used for general jalview generated annotation added to vamsas document
   */
  public static final String JALVIEW_ANNOTATION_ROW = "JalviewAnnotation";

  /**
   * AlignmentAnnotation property to indicate that values should not be
   * interpolated
   */
  public static final String DISCRETE_ANNOTATION = "discrete";

  /**
   * continuous property - optional to specify that annotation should be
   * represented as a continous graph line
   */
  private static final String CONTINUOUS_ANNOTATION = "continuous";

  private static final String THRESHOLD = "threshold";

  /**
   * template for provenance entries written to vamsas session document
   */
  Entry provEntry = null;

  /**
   * Instance of the session document being synchronized with
   */
  IClientDocument cdoc;

  /**
   * map Vorba (vamsas object xml ref) IDs to live jalview object references
   */
  Hashtable vobj2jv;

  /**
   * map live jalview object references to Vorba IDs
   */
  IdentityHashMap jv2vobj;

  /**
   * map jalview sequence set ID (which is vorba ID for alignment) to last
   * recorded hash value for the alignment viewport (the undo/redo hash value)
   */
  Hashtable alignRDHash;

  public VamsasAppDatastore(IClientDocument cdoc, Hashtable vobj2jv,
          IdentityHashMap jv2vobj, Entry provEntry, Hashtable alignRDHash)
  {
    this.cdoc = cdoc;
    this.vobj2jv = vobj2jv;
    this.jv2vobj = jv2vobj;
    this.provEntry = provEntry;
    this.alignRDHash = alignRDHash;
    buildSkipList();
  }

  /**
   * the skipList used to skip over views from Jalview Appdata's that we've
   * already syncrhonized
   */
  Hashtable skipList;

  private void buildSkipList()
  {
    skipList = new Hashtable();
    AlignFrame[] al = Desktop.getAlignframes();
    for (int f = 0; al != null && f < al.length; f++)
    {
      skipList.put(al[f].getViewport().getSequenceSetId(), al[f]);
    }
  }

  /**
   * @return the Vobject bound to Jalview datamodel object
   */
  protected Vobject getjv2vObj(Object jvobj)
  {
    if (jv2vobj.containsKey(jvobj))
    {
      return cdoc.getObject((VorbaId) jv2vobj.get(jvobj));
    }
    // check if we're working with a string - then workaround
    // the use of IdentityHashTable because different strings
    // have different object IDs.
    if (jvobj instanceof String)
    {
      Object seqsetidobj = null;
      seqsetidobj = getVamsasObjectBinding().get(jvobj);
      if (seqsetidobj != null)
      {
        if (seqsetidobj instanceof String)
        {
          // what is expected. object returned by av.getSequenceSetId() -
          // reverse lookup to get the 'registered' instance of this string
          Vobject obj = getjv2vObj(seqsetidobj);
          if (obj != null && !(obj instanceof Alignment))
          {
            Cache.log
                    .warn("IMPLEMENTATION ERROR?: Unexpected mapping for unmapped jalview string object content:"
                            + seqsetidobj + " to object " + obj);
          }
          return obj;
        }
        else
        {
          Cache.log.warn("Unexpected mapping for Jalview String Object ID "
                  + seqsetidobj + " to another jalview dataset object "
                  + seqsetidobj);
        }
      }
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
            && !((VorbaId) vobj2jv.get(vobj.getVorbaId())).equals(jvobj))
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
  }

  /**
   * put the alignment viewed by AlignViewport into cdoc.
   * 
   * @param av
   *          alignViewport to be stored
   * @param aFtitle
   *          title for alignment
   * @return true if alignment associated with viewport was stored/synchronized
   *         to document
   */
  public boolean storeVAMSAS(AlignViewport av, String aFtitle)
  {
    try
    {
      jalview.datamodel.AlignmentI jal = av.getAlignment();
      jalview.datamodel.AlignmentI jds = jal.getDataset();
      boolean nw = false;
      VAMSAS root = null; // will be resolved based on Dataset Parent.
      // /////////////////////////////////////////
      // SAVE THE DATASET
      DataSet dataset = null;
      if (jds == null)
      {
        Cache.log.warn("Creating new dataset for an alignment.");
        jal.setDataset(null);
        jds = jal.getDataset();
      }

      // try and get alignment and association for sequence set id

      Alignment alignment = (Alignment) getjv2vObj(av.getSequenceSetId());
      if (alignment != null)
      {
        dataset = (DataSet) alignment.getV_parent();
      }
      else
      {
        // is the dataset already registered
        dataset = (DataSet) getjv2vObj(jds);
      }

      if (dataset == null)
      {
        // it might be that one of the dataset sequences does actually have a
        // binding, so search for it indirectly. If it does, then the local
        // jalview dataset
        // must be merged with the existing vamsas dataset.
        jalview.datamodel.SequenceI[] jdatset = jds.getSequencesArray();
        for (int i = 0; i < jdatset.length; i++)
        {
          Vobject vbound = getjv2vObj(jdatset[i]);
          if (vbound != null)
          {
            if (vbound instanceof uk.ac.vamsas.objects.core.Sequence)
            {
              if (dataset == null)
              {
                dataset = (DataSet) vbound.getV_parent();
              }
              else
              {
                if (vbound.getV_parent() != null
                        && dataset != vbound.getV_parent())
                {
                  throw new Error(
                          "IMPLEMENTATION ERROR: Cannot map an alignment of sequences from different datasets into a single alignment in the vamsas document.");
                  // This occurs because the dataset for the alignment we are
                  // trying to
                }
              }
            }
          }
        }
      }

      if (dataset == null)
      {
        Cache.log.warn("Creating new vamsas dataset for alignment view "
                + av.getSequenceSetId());
        // we create a new dataset on the default vamsas root.
        root = cdoc.getVamsasRoots()[0]; // default vamsas root for modifying.
        dataset = new DataSet();
        root.addDataSet(dataset);
        bindjvvobj(jds, dataset);
        dataset.setProvenance(dummyProvenance());
        // dataset.getProvenance().addEntry(provEntry);
        nw = true;
      }
      else
      {
        root = (VAMSAS) dataset.getV_parent();
      }
      // update dataset
      Sequence sequence;
      // set new dataset and alignment sequences based on alignment Nucleotide
      // flag.
      // this *will* break when alignment contains both nucleotide and amino
      // acid sequences.
      String dict = jal.isNucleotide() ? uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_NA
              : uk.ac.vamsas.objects.utils.SymbolDictionary.STANDARD_AA;
      Vector dssmods = new Vector();
      for (int i = 0; i < jal.getHeight(); i++)
      {
        SequenceI sq = jal.getSequenceAt(i).getDatasetSequence(); // only insert
        // referenced
        // sequences
        // to dataset.
        Datasetsequence dssync = new jalview.io.vamsas.Datasetsequence(
                this, sq, dict, dataset);
        sequence = (Sequence) dssync.getVobj();
        if (dssync.getModified())
        {
          dssmods.addElement(sequence);
        }
        ;
      }
      if (dssmods.size() > 0)
      {
        if (!nw)
        {
          Entry pentry = this.addProvenance(dataset.getProvenance(),
                  "updated sequences");
          // pentry.addInput(vInput); could write in which sequences were
          // modified.
          dssmods.removeAllElements();
        }
      }
      // dataset.setProvenance(getVamsasProvenance(jal.getDataset().getProvenance()));
      // ////////////////////////////////////////////
      if (alignmentWillBeSkipped(av))
      {
        // TODO: trees could be written - but for the moment we just
        addToSkipList(av);
        // add to the JalviewXML skipList and ..
        return false;
      }

      if (alignment == null)
      {
        alignment = new Alignment();
        bindjvvobj(av.getSequenceSetId(), alignment);
        if (alignment.getProvenance() == null)
        {
          alignment.setProvenance(new Provenance());
        }
        addProvenance(alignment.getProvenance(), "added"); // TODO: insert some
        // sensible source
        // here
        dataset.addAlignment(alignment);
        {
          Property title = new Property();
          title.setName("title");
          title.setType("string");
          title.setContent(aFtitle);
          alignment.addProperty(title);
        }
        alignment.setGapChar(String.valueOf(av.getGapCharacter()));
        for (int i = 0; i < jal.getHeight(); i++)
        {
          syncToAlignmentSequence(jal.getSequenceAt(i), alignment, null);
        }
        alignRDHash.put(av.getSequenceSetId(), av.getUndoRedoHash());
      }
      else
      {
        // always prepare to clone the alignment
        boolean alismod = av.isUndoRedoHashModified((long[]) alignRDHash
                .get(av.getSequenceSetId()));
        // todo: verify and update mutable alignment props.
        // TODO: Use isLocked methods
        if (alignment.getModifiable() == null
                || alignment.getModifiable().length() == 0)
        // && !alignment.isDependedOn())
        {
          boolean modified = false;
          // check existing sequences in local and in document.
          Vector docseqs = new Vector(
                  alignment.getAlignmentSequenceAsReference());
          for (int i = 0; i < jal.getHeight(); i++)
          {
            modified |= syncToAlignmentSequence(jal.getSequenceAt(i),
                    alignment, docseqs);
          }
          if (docseqs.size() > 0)
          {
            // removeValignmentSequences(alignment, docseqs);
            docseqs.removeAllElements();
            System.out
                    .println("Sequence deletion from alignment is not implemented.");

          }
          if (modified)
          {
            if (alismod)
            {
              // info in the undo
              addProvenance(alignment.getProvenance(), "Edited"); // TODO:
              // insert
              // something
              // sensible
              // here again
            }
            else
            {
              // info in the undo
              addProvenance(alignment.getProvenance(), "Attributes Edited"); // TODO:
              // insert
              // something
              // sensible
              // here
              // again
            }
          }
          if (alismod)
          {
            System.out.println("update alignment in document.");
          }
          else
          {
            System.out.println("alignment in document left unchanged.");
          }
        }
        else
        {
          // unbind alignment from view.
          // create new binding and new alignment.
          // mark trail on new alignment as being derived from old ?
          System.out
                  .println("update edited alignment to new alignment in document.");
        }
      }
      // ////////////////////////////////////////////
      // SAVE Alignment Sequence Features
      for (int i = 0, iSize = alignment.getAlignmentSequenceCount(); i < iSize; i++)
      {
        AlignmentSequence valseq;
        SequenceI alseq = (SequenceI) getvObj2jv(valseq = alignment
                .getAlignmentSequence(i));
        if (alseq != null && alseq.getSequenceFeatures() != null)
        {
          /*
           * We do not put local Alignment Sequence Features into the vamsas
           * document yet.
           * 
           * 
           * jalview.datamodel.SequenceFeature[] features = alseq
           * .getSequenceFeatures(); for (int f = 0; f < features.length; f++) {
           * if (features[f] != null) { AlignmentSequenceAnnotation valseqf = (
           * AlignmentSequenceAnnotation) getjv2vObj(features[i]); if (valseqf
           * == null) {
           * 
           * valseqf = (AlignmentSequenceAnnotation) getDSAnnotationFromJalview(
           * new AlignmentSequenceAnnotation(), features[i]);
           * valseqf.setGraph(false);
           * valseqf.addProperty(newProperty("jalview:feature"
           * ,"boolean","true")); if (valseqf.getProvenance() == null) {
           * valseqf.setProvenance(new Provenance()); }
           * addProvenance(valseqf.getProvenance(), "created"); // JBPNote - //
           * need to // update bindjvvobj(features[i], valseqf);
           * valseq.addAlignmentSequenceAnnotation(valseqf); } } }
           */
        }
      }

      // ////////////////////////////////////////////
      // SAVE ANNOTATIONS
      if (jal.getAlignmentAnnotation() != null)
      {
        jalview.datamodel.AlignmentAnnotation[] aa = jal
                .getAlignmentAnnotation();
        java.util.HashMap AlSeqMaps = new HashMap(); // stores int maps from
        // alignment columns to
        // sequence positions.
        for (int i = 0; i < aa.length; i++)
        {
          if (aa[i] == null || isJalviewOnly(aa[i]))
          {
            continue;
          }
          if (aa[i].groupRef != null)
          {
            // TODO: store any group associated annotation references
            Cache.log
                    .warn("Group associated sequence annotation is not stored in VAMSAS document.");
            continue;
          }
          if (aa[i].sequenceRef != null)
          {
            // Deal with sequence associated annotation
            Vobject sref = getjv2vObj(aa[i].sequenceRef);
            if (sref instanceof uk.ac.vamsas.objects.core.AlignmentSequence)
            {
              saveAlignmentSequenceAnnotation(AlSeqMaps,
                      (AlignmentSequence) sref, aa[i]);
            }
            else
            {
              // first find the alignment sequence to associate this with.
              for (SequenceI jvalsq : av.getAlignment().getSequences())
              {
                // saveDatasetSequenceAnnotation(AlSeqMaps,(uk.ac.vamsas.objects.core.Sequence)
                // sref, aa[i]);
                if (jvalsq.getDatasetSequence() == aa[i].sequenceRef)
                {
                  Vobject alsref = getjv2vObj(jvalsq);
                  saveAlignmentSequenceAnnotation(AlSeqMaps,
                          (AlignmentSequence) alsref, aa[i]);
                  break;
                }
                ;
              }
            }
          }
          else
          {
            // add Alignment Annotation
            uk.ac.vamsas.objects.core.AlignmentAnnotation an = (uk.ac.vamsas.objects.core.AlignmentAnnotation) getjv2vObj(aa[i]);
            if (an == null)
            {
              an = new uk.ac.vamsas.objects.core.AlignmentAnnotation();
              an.setType(JALVIEW_ANNOTATION_ROW);
              an.setDescription(aa[i].description);
              alignment.addAlignmentAnnotation(an);
              Seg vSeg = new Seg(); // TODO: refactor to have a default
              // rangeAnnotationType initer/updater that
              // takes a set of int ranges.
              vSeg.setStart(1);
              vSeg.setInclusive(true);
              vSeg.setEnd(jal.getWidth());
              an.addSeg(vSeg);
              if (aa[i].graph > 0)
              {
                an.setGraph(true); // aa[i].graph);
              }
              an.setLabel(aa[i].label);
              an.setProvenance(dummyProvenance());
              if (aa[i].graph != AlignmentAnnotation.NO_GRAPH)
              {
                an.setGroup(Integer.toString(aa[i].graphGroup)); // // JBPNote
                // -
                // originally we
                // were going to
                // store
                // graphGroup in
                // the Jalview
                // specific
                // bits.
                an.setGraph(true);
              }
              else
              {
                an.setGraph(false);
              }
              AnnotationElement ae;

              for (int a = 0; a < aa[i].annotations.length; a++)
              {
                if ((aa[i] == null) || (aa[i].annotations[a] == null))
                {
                  continue;
                }

                ae = new AnnotationElement();
                ae.setDescription(aa[i].annotations[a].description);
                ae.addGlyph(new Glyph());
                ae.getGlyph(0).setContent(
                        aa[i].annotations[a].displayCharacter); // assume
                // jax-b
                // takes
                // care
                // of
                // utf8
                // translation
                if (an.isGraph())
                {
                  ae.addValue(aa[i].annotations[a].value);
                }
                ae.setPosition(a + 1);
                if (aa[i].annotations[a].secondaryStructure != ' ')
                {
                  Glyph ss = new Glyph();
                  ss.setDict(uk.ac.vamsas.objects.utils.GlyphDictionary.PROTEIN_SS_3STATE);
                  ss.setContent(String
                          .valueOf(aa[i].annotations[a].secondaryStructure));
                  ae.addGlyph(ss);
                }
                an.addAnnotationElement(ae);
              }
              if (aa[i].editable)
              {
                // an.addProperty(newProperty("jalview:editable", null,
                // "true"));
                // an.setModifiable(""); // TODO: This is not the way the
                // modifiable flag is supposed to be used.
              }
              setAnnotationType(an, aa[i]);

              if (aa[i].graph != jalview.datamodel.AlignmentAnnotation.NO_GRAPH)
              {
                an.setGraph(true);
                an.setGroup(Integer.toString(aa[i].graphGroup));
                if (aa[i].threshold != null && aa[i].threshold.displayed)
                {
                  an.addProperty(Properties.newProperty(THRESHOLD,
                          Properties.FLOATTYPE, "" + aa[i].threshold.value));
                  if (aa[i].threshold.label != null)
                  {
                    an.addProperty(Properties.newProperty(THRESHOLD
                            + "Name", Properties.STRINGTYPE, ""
                            + aa[i].threshold.label));
                  }
                }
              }

            }

            else
            {
              if (an.getModifiable() == null) // TODO: USE VAMSAS LIBRARY OBJECT
              // LOCK METHODS)
              {
                // verify annotation - update (perhaps)
                Cache.log
                        .info("update alignment sequence annotation. not yet implemented.");
              }
              else
              {
                // verify annotation - update (perhaps)
                Cache.log
                        .info("updated alignment sequence annotation added.");
              }
            }
          }
        }
      }
      // /////////////////////////////////////////////////////

      // //////////////////////////////////////////////
      // /SAVE THE TREES
      // /////////////////////////////////
      // FIND ANY ASSOCIATED TREES
      if (Desktop.desktop != null)
      {
        javax.swing.JInternalFrame[] frames = Desktop.instance
                .getAllFrames();

        for (int t = 0; t < frames.length; t++)
        {
          if (frames[t] instanceof TreePanel)
          {
            TreePanel tp = (TreePanel) frames[t];

            if (tp.getViewPort().getSequenceSetId()
                    .equals(av.getSequenceSetId()))
            {
              DatastoreItem vtree = new jalview.io.vamsas.Tree(this, tp,
                      jal, alignment);
            }
          }
        }
      }
      // Store Jalview specific stuff in the Jalview appData
      // not implemented in the SimpleDoc interface.
    }

    catch (Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * very quick test to see if the viewport would be stored in the vamsas
   * document. Reasons for not storing include the unaligned flag being false
   * (for all sequences, including the hidden ones!)
   * 
   * @param av
   * @return true if alignment associated with this view will be stored in
   *         document.
   */
  public boolean alignmentWillBeSkipped(AlignViewport av)
  {
    return (!av.getAlignment().isAligned());
  }

  private void addToSkipList(AlignViewport av)
  {
    if (skipList == null)
    {
      skipList = new Hashtable();
    }
    skipList.put(av.getSequenceSetId(), av);
  }

  /**
   * remove docseqs from the given alignment marking provenance appropriately
   * and removing any references to the sequences.
   * 
   * @param alignment
   * @param docseqs
   */
  private void removeValignmentSequences(Alignment alignment, Vector docseqs)
  {
    // delete these from document. This really needs to be a generic document
    // API function derived by CASTOR.
    Enumeration en = docseqs.elements();
    while (en.hasMoreElements())
    {
      alignment.removeAlignmentSequence((AlignmentSequence) en
              .nextElement());
    }
    Entry pe = addProvenance(alignment.getProvenance(), "Removed "
            + docseqs.size() + " sequences");
    en = alignment.enumerateAlignmentAnnotation();
    Vector toremove = new Vector();
    while (en.hasMoreElements())
    {
      uk.ac.vamsas.objects.core.AlignmentAnnotation alan = (uk.ac.vamsas.objects.core.AlignmentAnnotation) en
              .nextElement();
      if (alan.getSeqrefsCount() > 0)
      {
        int p = 0;
        Vector storem = new Vector();
        Enumeration sr = alan.enumerateSeqrefs();
        while (sr.hasMoreElements())
        {
          Object alsr = sr.nextElement();
          if (docseqs.contains(alsr))
          {
            storem.addElement(alsr);
          }
        }
        // remove references to the deleted sequences
        sr = storem.elements();
        while (sr.hasMoreElements())
        {
          alan.removeSeqrefs(sr.nextElement());
        }

        if (alan.getSeqrefsCount() == 0)
        {
          // should then delete alan from dataset
          toremove.addElement(alan);
        }
      }
    }
    // remove any annotation that used to be associated to a specific bunch of
    // sequences
    en = toremove.elements();
    while (en.hasMoreElements())
    {
      alignment
              .removeAlignmentAnnotation((uk.ac.vamsas.objects.core.AlignmentAnnotation) en
                      .nextElement());
    }
    // TODO: search through alignment annotations to remove any references to
    // this alignment sequence
  }

  /**
   * sync a jalview alignment seuqence into a vamsas alignment assumes all lock
   * transformation/bindings have been sorted out before hand. creates/syncs the
   * vamsas alignment sequence for jvalsq and adds it to the alignment if
   * necessary. unbounddocseq is a duplicate of the vamsas alignment sequences
   * and these are removed after being processed w.r.t a bound jvalsq
   * 
   */
  private boolean syncToAlignmentSequence(SequenceI jvalsq,
          Alignment alignment, Vector unbounddocseq)
  {
    boolean modal = false;
    // todo: islocked method here
    boolean up2doc = false;
    AlignmentSequence alseq = (AlignmentSequence) getjv2vObj(jvalsq);
    if (alseq == null)
    {
      alseq = new AlignmentSequence();
      up2doc = true;
    }
    else
    {
      if (unbounddocseq != null)
      {
        unbounddocseq.removeElement(alseq);
      }
    }
    // boolean locked = (alignment.getModifiable()==null ||
    // alignment.getModifiable().length()>0);
    // TODO: VAMSAS: translate lowercase symbols to annotation ?
    if (up2doc || !alseq.getSequence().equals(jvalsq.getSequenceAsString()))
    {
      alseq.setSequence(jvalsq.getSequenceAsString());
      alseq.setStart(jvalsq.getStart());
      alseq.setEnd(jvalsq.getEnd());
      modal = true;
    }
    if (up2doc || !alseq.getName().equals(jvalsq.getName()))
    {
      modal = true;
      alseq.setName(jvalsq.getName());
    }
    if (jvalsq.getDescription() != null
            && (alseq.getDescription() == null || !jvalsq.getDescription()
                    .equals(alseq.getDescription())))
    {
      modal = true;
      alseq.setDescription(jvalsq.getDescription());
    }
    if (getjv2vObj(jvalsq.getDatasetSequence()) == null)
    {
      Cache.log
              .warn("Serious Implementation error - Unbound dataset sequence in alignment: "
                      + jvalsq.getDatasetSequence());
    }
    alseq.setRefid(getjv2vObj(jvalsq.getDatasetSequence()));
    if (up2doc)
    {

      alignment.addAlignmentSequence(alseq);
      bindjvvobj(jvalsq, alseq);
    }
    return up2doc || modal;
  }

  /**
   * locally sync a jalview alignment seuqence from a vamsas alignment assumes
   * all lock transformation/bindings have been sorted out before hand.
   * creates/syncs the jvalsq from the alignment sequence
   */
  private boolean syncFromAlignmentSequence(AlignmentSequence valseq,
          char valGapchar, char gapChar, List<SequenceI> dsseqs)

  {
    boolean modal = false;
    // todo: islocked method here
    boolean upFromdoc = false;
    jalview.datamodel.SequenceI alseq = (SequenceI) getvObj2jv(valseq);
    if (alseq == null)
    {
      upFromdoc = true;
    }
    if (alseq != null)
    {

      // boolean locked = (alignment.getModifiable()==null ||
      // alignment.getModifiable().length()>0);
      // TODO: VAMSAS: translate lowercase symbols to annotation ?
      if (upFromdoc
              || !valseq.getSequence().equals(alseq.getSequenceAsString()))
      {
        // this might go *horribly* wrong
        alseq.setSequence(new String(valseq.getSequence()).replace(
                valGapchar, gapChar));
        alseq.setStart((int) valseq.getStart());
        alseq.setEnd((int) valseq.getEnd());
        modal = true;
      }
      if (!valseq.getName().equals(alseq.getName()))
      {
        modal = true;
        alseq.setName(valseq.getName());
      }
      if (alseq.getDescription() == null
              || (valseq.getDescription() != null && !alseq
                      .getDescription().equals(valseq.getDescription())))
      {
        alseq.setDescription(valseq.getDescription());
        modal = true;
      }
      if (modal && Cache.log.isDebugEnabled())
      {
        Cache.log.debug("Updating apparently edited sequence "
                + alseq.getName());
      }
    }
    else
    {
      alseq = new jalview.datamodel.Sequence(valseq.getName(), valseq
              .getSequence().replace(valGapchar, gapChar),
              (int) valseq.getStart(), (int) valseq.getEnd());

      Vobject datsetseq = (Vobject) valseq.getRefid();
      if (datsetseq != null)
      {
        alseq.setDatasetSequence((SequenceI) getvObj2jv(datsetseq)); // exceptions
        if (valseq.getDescription() != null)
        {
          alseq.setDescription(valseq.getDescription());
        }
        else
        {
          // inherit description line from dataset.
          if (alseq.getDatasetSequence().getDescription() != null)
          {
            alseq.setDescription(alseq.getDatasetSequence()
                    .getDescription());
          }
        }
        // if
        // AlignemntSequence
        // reference
        // isn't
        // a
        // simple
        // SequenceI
      }
      else
      {
        Cache.log
                .error("Invalid dataset sequence id (null) for alignment sequence "
                        + valseq.getVorbaId());
      }
      bindjvvobj(alseq, valseq);
      alseq.setVamsasId(valseq.getVorbaId().getId());
      dsseqs.add(alseq);
    }
    Vobject datsetseq = (Vobject) valseq.getRefid();
    if (datsetseq != null)
    {
      if (datsetseq != alseq.getDatasetSequence())
      {
        modal = true;
      }
      alseq.setDatasetSequence((SequenceI) getvObj2jv(datsetseq)); // exceptions
    }
    return upFromdoc || modal;
  }

  private void initRangeAnnotationType(RangeAnnotation an,
          AlignmentAnnotation alan, int[] gapMap)
  {
    Seg vSeg = new Seg();
    vSeg.setStart(1);
    vSeg.setInclusive(true);
    vSeg.setEnd(gapMap.length);
    an.addSeg(vSeg);

    // LATER: much of this is verbatim from the alignmentAnnotation
    // method below. suggests refactoring to make rangeAnnotation the
    // base class
    an.setDescription(alan.description);
    an.setLabel(alan.label);
    an.setGroup(Integer.toString(alan.graphGroup));
    // // JBPNote -
    // originally we
    // were going to
    // store
    // graphGroup in
    // the Jalview
    // specific
    // bits.
    AnnotationElement ae;
    for (int a = 0; a < alan.annotations.length; a++)
    {
      if (alan.annotations[a] == null)
      {
        continue;
      }

      ae = new AnnotationElement();
      ae.setDescription(alan.annotations[a].description);
      ae.addGlyph(new Glyph());
      ae.getGlyph(0).setContent(alan.annotations[a].displayCharacter); // assume
      // jax-b
      // takes
      // care
      // of
      // utf8
      // translation
      if (alan.graph != jalview.datamodel.AlignmentAnnotation.NO_GRAPH)
      {
        ae.addValue(alan.annotations[a].value);
      }
      ae.setPosition(gapMap[a] + 1); // position w.r.t. AlignmentSequence
      // symbols
      if (alan.annotations[a].secondaryStructure != ' ')
      {
        // we only write an annotation where it really exists.
        Glyph ss = new Glyph();
        ss.setDict(uk.ac.vamsas.objects.utils.GlyphDictionary.PROTEIN_SS_3STATE);
        ss.setContent(String
                .valueOf(alan.annotations[a].secondaryStructure));
        ae.addGlyph(ss);
      }
      an.addAnnotationElement(ae);
    }

  }

  private void saveDatasetSequenceAnnotation(HashMap AlSeqMaps,
          uk.ac.vamsas.objects.core.Sequence sref, AlignmentAnnotation alan)
  {
    // {
    // uk.ac.vamsas.
    // objects.core.AlignmentSequence alsref = (uk.ac.vamsas.
    // objects.core.AlignmentSequence) sref;
    uk.ac.vamsas.objects.core.DataSetAnnotations an = (uk.ac.vamsas.objects.core.DataSetAnnotations) getjv2vObj(alan);
    int[] gapMap = getGapMap(AlSeqMaps, alan);
    if (an == null)
    {
      an = new uk.ac.vamsas.objects.core.DataSetAnnotations();
      initRangeAnnotationType(an, alan, gapMap);

      an.setProvenance(dummyProvenance()); // get provenance as user
      // created, or jnet, or
      // something else.
      setAnnotationType(an, alan);
      an.setGroup(Integer.toString(alan.graphGroup)); // // JBPNote -
      // originally we
      // were going to
      // store
      // graphGroup in
      // the Jalview
      // specific
      // bits.
      if (alan.getThreshold() != null && alan.getThreshold().displayed)
      {
        an.addProperty(Properties.newProperty(THRESHOLD,
                Properties.FLOATTYPE, "" + alan.getThreshold().value));
        if (alan.getThreshold().label != null)
          an.addProperty(Properties.newProperty(THRESHOLD + "Name",
                  Properties.STRINGTYPE, "" + alan.getThreshold().label));
      }
      ((DataSet) sref.getV_parent()).addDataSetAnnotations(an);
      bindjvvobj(alan, an);
    }
    else
    {
      // update reference sequence Annotation
      if (an.getModifiable() == null) // TODO: USE VAMSAS LIBRARY OBJECT LOCK
      // METHODS)
      {
        // verify existing alignment sequence annotation is up to date
        System.out.println("update dataset sequence annotation.");
      }
      else
      {
        // verify existing alignment sequence annotation is up to date
        System.out
                .println("make new alignment dataset sequence annotation if modification has happened.");
      }
    }

  }

  private int[] getGapMap(HashMap AlSeqMaps, AlignmentAnnotation alan)
  {
    int[] gapMap;
    if (AlSeqMaps.containsKey(alan.sequenceRef))
    {
      gapMap = (int[]) AlSeqMaps.get(alan.sequenceRef);
    }
    else
    {
      gapMap = new int[alan.sequenceRef.getLength()];
      // map from alignment position to sequence position.
      int[] sgapMap = alan.sequenceRef.gapMap();
      for (int a = 0; a < sgapMap.length; a++)
      {
        gapMap[sgapMap[a]] = a;
      }
    }
    return gapMap;
  }

  private void saveAlignmentSequenceAnnotation(HashMap AlSeqMaps,
          AlignmentSequence alsref, AlignmentAnnotation alan)
  {
    // {
    // uk.ac.vamsas.
    // objects.core.AlignmentSequence alsref = (uk.ac.vamsas.
    // objects.core.AlignmentSequence) sref;
    uk.ac.vamsas.objects.core.AlignmentSequenceAnnotation an = (uk.ac.vamsas.objects.core.AlignmentSequenceAnnotation) getjv2vObj(alan);
    int[] gapMap = getGapMap(AlSeqMaps, alan);
    if (an == null)
    {
      an = new uk.ac.vamsas.objects.core.AlignmentSequenceAnnotation();
      initRangeAnnotationType(an, alan, gapMap);
      /**
       * I mean here that we don't actually have a semantic 'type' for the
       * annotation (this might be - score, intrinsic property, measurement,
       * something extracted from another program, etc)
       */
      an.setType(JALVIEW_ANNOTATION_ROW); // TODO: better fix
      // this rough guess ;)
      alsref.addAlignmentSequenceAnnotation(an);
      bindjvvobj(alan, an);
      // These properties are directly supported by the
      // AlignmentSequenceAnnotation type.
      setAnnotationType(an, alan);
      an.setProvenance(dummyProvenance()); // get provenance as user
      // created, or jnet, or
      // something else.
    }
    else
    {
      // update reference sequence Annotation
      if (an.getModifiable() == null) // TODO: USE VAMSAS LIBRARY OBJECT LOCK
      // METHODS)
      {
        // verify existing alignment sequence annotation is up to date
        System.out.println("update alignment sequence annotation.");
      }
      else
      {
        // verify existing alignment sequence annotation is up to date
        System.out
                .println("make new alignment sequence annotation if modification has happened.");
      }
    }
  }

  /**
   * set vamsas annotation object type from jalview annotation
   * 
   * @param an
   * @param alan
   */
  private void setAnnotationType(RangeAnnotation an,
          AlignmentAnnotation alan)
  {
    if (an instanceof AlignmentSequenceAnnotation)
    {
      if (alan.graph != AlignmentAnnotation.NO_GRAPH)
      {
        ((AlignmentSequenceAnnotation) an).setGraph(true);
      }
      else
      {
        ((AlignmentSequenceAnnotation) an).setGraph(false);
      }
    }
    if (an instanceof uk.ac.vamsas.objects.core.AlignmentAnnotation)
    {
      if (alan.graph != AlignmentAnnotation.NO_GRAPH)
      {
        ((uk.ac.vamsas.objects.core.AlignmentAnnotation) an).setGraph(true);
      }
      else
      {
        ((uk.ac.vamsas.objects.core.AlignmentAnnotation) an)
                .setGraph(false);
      }
    }
    switch (alan.graph)
    {
    case AlignmentAnnotation.BAR_GRAPH:
      an.addProperty(Properties.newProperty(DISCRETE_ANNOTATION,
              Properties.BOOLEANTYPE, "true"));
      break;
    case AlignmentAnnotation.LINE_GRAPH:
      an.addProperty(Properties.newProperty(CONTINUOUS_ANNOTATION,
              Properties.BOOLEANTYPE, "true"));
      break;
    default:
      // don't add any kind of discrete or continous property info.
    }
  }

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
  private int[] getSegRange(Seg visSeg, boolean ensureDirection)
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
   * 
   * @param annotation
   * @return true if annotation is not to be stored in document
   */
  private boolean isJalviewOnly(AlignmentAnnotation annotation)
  {
    return annotation.autoCalculated || annotation.label.equals("Quality")
            || annotation.label.equals("Conservation")
            || annotation.label.equals("Consensus");
  }

  boolean dojvsync = true;

  // boolean dojvsync = false; // disables Jalview AppData IO
  /**
   * list of alignment views created when updating Jalview from document.
   */
  private final Vector newAlignmentViews = new Vector();

  /**
   * update local jalview view settings from the stored appdata (if any)
   */
  public void updateJalviewFromAppdata()
  {
    // recover any existing Jalview data from appdata
    // TODO: recover any PDB files stored as attachments in the vamsas session
    // and initialise the Jalview2XML.alreadyLoadedPDB hashtable with mappings
    // to temp files.
    {
      final IClientAppdata cappdata = cdoc.getClientAppdata();
      if (cappdata != null)
      {
        if (cappdata.hasClientAppdata())
        {
          // TODO: how to check version of Jalview client app data and whether
          // it has been modified
          // client data is shared over all app clients
          try
          {
            jalview.gui.Jalview2XML fromxml = new jalview.gui.Jalview2XML();
            fromxml.attemptversion1parse = false;
            fromxml.setUniqueSetSuffix("");
            fromxml.setObjectMappingTables(vobj2jv, jv2vobj); // mapKeysToString
            // and
            // mapValuesToString
            fromxml.setSkipList(skipList);
            jalview.util.jarInputStreamProvider jprovider = new jalview.util.jarInputStreamProvider()
            {

              @Override
              public String getFilename()
              {

                // TODO Get the vamsas session ID here
                return "Jalview Vamsas Document Client Data";
              }

              @Override
              public JarInputStream getJarInputStream() throws IOException
              {
                jalview.bin.Cache.log
                        .debug("Returning client input stream for Jalview from Vamsas Document.");
                return new JarInputStream(cappdata.getClientInputStream());
              }
            };
            if (dojvsync)
            {
              fromxml.LoadJalviewAlign(jprovider);
            }
          } catch (Exception e)
          {

          } catch (OutOfMemoryError e)
          {

          } catch (Error e)
          {

          }
        }
      }
      if (cappdata.hasUserAppdata())
      {
        // TODO: how to check version of Jalview user app data and whether it
        // has been modified
        // user data overrides data shared over all app clients ?
        try
        {
          jalview.gui.Jalview2XML fromxml = new jalview.gui.Jalview2XML();
          fromxml.attemptversion1parse = false;
          fromxml.setUniqueSetSuffix("");
          fromxml.setSkipList(skipList);
          fromxml.setObjectMappingTables(mapKeysToString(vobj2jv),
                  mapValuesToString(jv2vobj));
          jalview.util.jarInputStreamProvider jarstream = new jalview.util.jarInputStreamProvider()
          {

            @Override
            public String getFilename()
            {

              // TODO Get the vamsas session ID here
              return "Jalview Vamsas Document User Data";
            }

            @Override
            public JarInputStream getJarInputStream() throws IOException
            {
              jalview.bin.Cache.log
                      .debug("Returning user input stream for Jalview from Vamsas Document.");
              return new JarInputStream(cappdata.getUserInputStream());
            }
          };
          if (dojvsync)
          {
            fromxml.LoadJalviewAlign(jarstream);
          }
        } catch (Exception e)
        {

        } catch (OutOfMemoryError e)
        {

        } catch (Error e)
        {

        }
      }

    }
    flushAlignViewports();
  }

  /**
   * remove any spurious views generated by document synchronization
   */
  private void flushAlignViewports()
  {
    // remove any additional viewports originally recovered from the vamsas
    // document.
    // search for all alignframes containing viewports generated from document
    // sync,
    // and if any contain more than one view, then remove the one generated by
    // document update.
    AlignViewport views[], av = null;
    AlignFrame af = null;
    Iterator newviews = newAlignmentViews.iterator();
    while (newviews.hasNext())
    {
      av = (AlignViewport) newviews.next();
      af = Desktop.getAlignFrameFor(av);
      // TODO implement this : af.getNumberOfViews
      String seqsetidobj = av.getSequenceSetId();
      views = Desktop.getViewports(seqsetidobj);
      Cache.log.debug("Found "
              + (views == null ? " no " : "" + views.length)
              + " views for '" + av.getSequenceSetId() + "'");
      if (views.length > 1)
      {
        // we need to close the original document view.

        // work out how to do this by seeing if the views are gathered.
        // pretty clunky but the only way to do this without adding more flags
        // to the align frames.
        boolean gathered = false;
        String newviewid = null;
        AlignedCodonFrame[] mappings = av.getAlignment().getCodonFrames();
        for (int i = 0; i < views.length; i++)
        {
          if (views[i] != av)
          {
            AlignFrame viewframe = Desktop.getAlignFrameFor(views[i]);
            if (viewframe == af)
            {
              gathered = true;
            }
            newviewid = views[i].getSequenceSetId();
          }
          else
          {
            // lose the reference to the vamsas document created view
            views[i] = null;
          }
        }
        // close the view generated by the vamsas document synchronization
        if (gathered)
        {
          af.closeView(av);
        }
        else
        {
          af.closeMenuItem_actionPerformed(false);
        }
        replaceJvObjMapping(seqsetidobj, newviewid);
        seqsetidobj = newviewid;
        // not sure if we need to do this:

        if (false) // mappings != null)
        {
          // ensure sequence mappings from vamsas document view still
          // active
          if (mappings != null && mappings.length > 0)
          {
            jalview.structure.StructureSelectionManager
                    .getStructureSelectionManager(Desktop.instance)
                    .addMappings(mappings);
          }
        }
      }
      // ensure vamsas object binds to the stored views retrieved from
      // Jalview appdata
      // jalview.structure.StructureSelectionManager
      // .getStructureSelectionManager()
      // .addStructureViewerListener(viewframe.alignPanel);

    }

    newviews = null;
    newAlignmentViews.clear();
  }

  /**
   * replaces oldjvobject with newjvobject in the Jalview Object <> VorbaID
   * binding tables
   * 
   * @param oldjvobject
   * @param newjvobject
   *          (may be null)
   */
  private void replaceJvObjMapping(Object oldjvobject, Object newjvobject)
  {
    Object vobject = jv2vobj.remove(oldjvobject);
    if (vobject == null)
    {
      // NOTE: this happens if user deletes object in one session then updates
      // from another client
      throw new Error(
              "IMPLEMENTATION ERROR: old jalview object is not bound ! ("
                      + oldjvobject + ")");
    }
    if (newjvobject != null)
    {
      jv2vobj.put(newjvobject, vobject);
      vobj2jv.put(vobject, newjvobject);
    }
  }

  /**
   * Update the jalview client and user appdata from the local jalview settings
   */
  public void updateJalviewClientAppdata()
  {
    final IClientAppdata cappdata = cdoc.getClientAppdata();
    if (cappdata != null)
    {
      try
      {
        jalview.gui.Jalview2XML jxml = new jalview.gui.Jalview2XML();
        jxml.setObjectMappingTables(mapKeysToString(vobj2jv),
                mapValuesToString(jv2vobj));
        jxml.setSkipList(skipList);
        if (dojvsync)
        {
          jxml.SaveState(new JarOutputStream(cappdata
                  .getClientOutputStream()));
        }

      } catch (Exception e)
      {
        // TODO raise GUI warning if user requests it.
        jalview.bin.Cache.log
                .error("Couldn't update jalview client application data. Giving up - local settings probably lost.",
                        e);
      }
    }
    else
    {
      jalview.bin.Cache.log
              .error("Couldn't access client application data for vamsas session. This is probably a vamsas client bug.");
    }
  }

  /**
   * translate the Vobject keys to strings for use in Jalview2XML
   * 
   * @param jv2vobj2
   * @return
   */
  private IdentityHashMap mapValuesToString(IdentityHashMap jv2vobj2)
  {
    IdentityHashMap mapped = new IdentityHashMap();
    Iterator keys = jv2vobj2.keySet().iterator();
    while (keys.hasNext())
    {
      Object key = keys.next();
      mapped.put(key, jv2vobj2.get(key).toString());
    }
    return mapped;
  }

  /**
   * translate the Vobject values to strings for use in Jalview2XML
   * 
   * @param vobj2jv2
   * @return hashtable with string values
   */
  private Hashtable mapKeysToString(Hashtable vobj2jv2)
  {
    Hashtable mapped = new Hashtable();
    Iterator keys = vobj2jv2.keySet().iterator();
    while (keys.hasNext())
    {
      Object key = keys.next();
      mapped.put(key.toString(), vobj2jv2.get(key));
    }
    return mapped;
  }

  /**
   * synchronize Jalview from the vamsas document
   * 
   * @return number of new views from document
   */
  public int updateToJalview()
  {
    VAMSAS _roots[] = cdoc.getVamsasRoots();

    for (int _root = 0; _root < _roots.length; _root++)
    {
      VAMSAS root = _roots[_root];
      boolean newds = false;
      for (int _ds = 0, _nds = root.getDataSetCount(); _ds < _nds; _ds++)
      {
        // ///////////////////////////////////
        // ///LOAD DATASET
        DataSet dataset = root.getDataSet(_ds);
        int i, iSize = dataset.getSequenceCount();
        List<SequenceI> dsseqs;
        jalview.datamodel.Alignment jdataset = (jalview.datamodel.Alignment) getvObj2jv(dataset);
        int jremain = 0;
        if (jdataset == null)
        {
          Cache.log.debug("Initialising new jalview dataset fields");
          newds = true;
          dsseqs = new Vector();
        }
        else
        {
          Cache.log.debug("Update jalview dataset from vamsas.");
          jremain = jdataset.getHeight();
          dsseqs = jdataset.getSequences();
        }

        // TODO: test sequence merging - we preserve existing non vamsas
        // sequences but add in any new vamsas ones, and don't yet update any
        // sequence attributes
        for (i = 0; i < iSize; i++)
        {
          Sequence vdseq = dataset.getSequence(i);
          jalview.io.vamsas.Datasetsequence dssync = new Datasetsequence(
                  this, vdseq);

          jalview.datamodel.SequenceI dsseq = (SequenceI) dssync.getJvobj();
          if (dssync.isAddfromdoc())
          {
            dsseqs.add(dsseq);
          }
          if (vdseq.getDbRefCount() > 0)
          {
            DbRef[] dbref = vdseq.getDbRef();
            for (int db = 0; db < dbref.length; db++)
            {
              new jalview.io.vamsas.Dbref(this, dbref[db], vdseq, dsseq);

            }
            dsseq.updatePDBIds();
          }
        }

        if (newds)
        {
          SequenceI[] seqs = new SequenceI[dsseqs.size()];
          for (i = 0, iSize = dsseqs.size(); i < iSize; i++)
          {
            seqs[i] = dsseqs.get(i);
            dsseqs.set(i, null);
          }
          jdataset = new jalview.datamodel.Alignment(seqs);
          Cache.log.debug("New vamsas dataset imported into jalview.");
          bindjvvobj(jdataset, dataset);
        }
        // ////////
        // add any new dataset sequence feature annotations
        if (dataset.getDataSetAnnotations() != null)
        {
          for (int dsa = 0; dsa < dataset.getDataSetAnnotationsCount(); dsa++)
          {
            DataSetAnnotations dseta = dataset.getDataSetAnnotations(dsa);
            // TODO: deal with group annotation on datset sequences.
            if (dseta.getSeqRefCount() == 1)
            {
              SequenceI dsSeq = (SequenceI) getvObj2jv((Vobject) dseta
                      .getSeqRef(0)); // TODO: deal with group dataset
              // annotations
              if (dsSeq == null)
              {
                jalview.bin.Cache.log
                        .warn("Couldn't resolve jalview sequenceI for dataset object reference "
                                + ((Vobject) dataset.getDataSetAnnotations(
                                        dsa).getSeqRef(0)).getVorbaId()
                                        .getId());
              }
              else
              {
                if (dseta.getAnnotationElementCount() == 0)
                {
                  new jalview.io.vamsas.Sequencefeature(this, dseta, dsSeq);

                }
                else
                {
                  // TODO: deal with alignmentAnnotation style annotation
                  // appearing on dataset sequences.
                  // JBPNote: we could just add them to all alignments but
                  // that may complicate cross references in the jalview
                  // datamodel
                  Cache.log
                          .warn("Ignoring dataset annotation with annotationElements. Not yet supported in jalview.");
                }
              }
            }
            else
            {
              Cache.log
                      .warn("Ignoring multiply referenced dataset sequence annotation for binding to datsaet sequence features.");
            }
          }
        }
        if (dataset.getAlignmentCount() > 0)
        {
          // LOAD ALIGNMENTS from DATASET

          for (int al = 0, nal = dataset.getAlignmentCount(); al < nal; al++)
          {
            uk.ac.vamsas.objects.core.Alignment alignment = dataset
                    .getAlignment(al);
            // TODO check this handles multiple views properly
            AlignViewport av = findViewport(alignment);

            jalview.datamodel.AlignmentI jal = null;
            if (av != null)
            {
              // TODO check that correct alignment object is retrieved when
              // hidden seqs exist.
              jal = (av.hasHiddenRows()) ? av.getAlignment()
                      .getHiddenSequences().getFullAlignment() : av
                      .getAlignment();
            }
            iSize = alignment.getAlignmentSequenceCount();
            boolean refreshal = false;
            Vector newasAnnots = new Vector();
            char gapChar = ' '; // default for new alignments read in from the
            // document
            if (jal != null)
            {
              dsseqs = jal.getSequences(); // for merge/update
              gapChar = jal.getGapCharacter();
            }
            else
            {
              dsseqs = new Vector();
            }
            char valGapchar = alignment.getGapChar().charAt(0);
            for (i = 0; i < iSize; i++)
            {
              AlignmentSequence valseq = alignment.getAlignmentSequence(i);
              jalview.datamodel.Sequence alseq = (jalview.datamodel.Sequence) getvObj2jv(valseq);
              if (syncFromAlignmentSequence(valseq, valGapchar, gapChar,
                      dsseqs) && alseq != null)
              {

                // updated to sequence from the document
                jremain--;
                refreshal = true;
              }
              if (valseq.getAlignmentSequenceAnnotationCount() > 0)
              {
                AlignmentSequenceAnnotation[] vasannot = valseq
                        .getAlignmentSequenceAnnotation();
                for (int a = 0; a < vasannot.length; a++)
                {
                  jalview.datamodel.AlignmentAnnotation asa = (jalview.datamodel.AlignmentAnnotation) getvObj2jv(vasannot[a]); // TODO:
                  // 1:many
                  // jalview
                  // alignment
                  // sequence
                  // annotations
                  if (asa == null)
                  {
                    int se[] = getBounds(vasannot[a]);
                    asa = getjAlignmentAnnotation(jal, vasannot[a]);
                    asa.setSequenceRef(alseq);
                    asa.createSequenceMapping(alseq, se[0], false); // TODO:
                    // verify
                    // that
                    // positions
                    // in
                    // alseqAnnotation
                    // correspond
                    // to
                    // ungapped
                    // residue
                    // positions.
                    alseq.addAlignmentAnnotation(asa);
                    bindjvvobj(asa, vasannot[a]);
                    refreshal = true;
                    newasAnnots.add(asa);
                  }
                  else
                  {
                    // update existing annotation - can do this in place
                    if (vasannot[a].getModifiable() == null) // TODO: USE
                    // VAMSAS LIBRARY
                    // OBJECT LOCK
                    // METHODS)
                    {
                      Cache.log
                              .info("UNIMPLEMENTED: not recovering user modifiable sequence alignment annotation");
                      // TODO: should at least replace with new one - otherwise
                      // things will break
                      // basically do this:
                      // int se[] = getBounds(vasannot[a]);
                      // asa.update(getjAlignmentAnnotation(jal, vasannot[a]));
                      // // update from another annotation object in place.
                      // asa.createSequenceMapping(alseq, se[0], false);

                    }
                  }
                }
              }
            }
            if (jal == null)
            {
              SequenceI[] seqs = new SequenceI[dsseqs.size()];
              for (i = 0, iSize = dsseqs.size(); i < iSize; i++)
              {
                seqs[i] = dsseqs.get(i);
                dsseqs.set(i, null);
              }
              jal = new jalview.datamodel.Alignment(seqs);
              Cache.log.debug("New vamsas alignment imported into jalview "
                      + alignment.getVorbaId().getId());
              jal.setDataset(jdataset);
            }
            if (newasAnnots != null && newasAnnots.size() > 0)
            {
              // Add the new sequence annotations in to the alignment.
              for (int an = 0, anSize = newasAnnots.size(); an < anSize; an++)
              {
                jal.addAnnotation((AlignmentAnnotation) newasAnnots
                        .elementAt(an));
                // TODO: check if anything has to be done - like calling
                // adjustForAlignment or something.
                newasAnnots.setElementAt(null, an);
              }
              newasAnnots = null;
            }
            // //////////////////////////////////////////
            // //LOAD ANNOTATIONS FOR THE ALIGNMENT
            // ////////////////////////////////////
            if (alignment.getAlignmentAnnotationCount() > 0)
            {
              uk.ac.vamsas.objects.core.AlignmentAnnotation[] an = alignment
                      .getAlignmentAnnotation();

              for (int j = 0; j < an.length; j++)
              {
                jalview.datamodel.AlignmentAnnotation jan = (jalview.datamodel.AlignmentAnnotation) getvObj2jv(an[j]);
                if (jan != null)
                {
                  // update or stay the same.
                  // TODO: should at least replace with a new one - otherwise
                  // things will break
                  // basically do this:
                  // jan.update(getjAlignmentAnnotation(jal, an[a])); // update
                  // from another annotation object in place.

                  Cache.log
                          .debug("update from vamsas alignment annotation to existing jalview alignment annotation.");
                  if (an[j].getModifiable() == null) // TODO: USE VAMSAS
                  // LIBRARY OBJECT LOCK
                  // METHODS)
                  {
                    // TODO: user defined annotation is totally mutable... - so
                    // load it up or throw away if locally edited.
                    Cache.log
                            .info("NOT IMPLEMENTED - Recovering user-modifiable annotation - yet...");
                  }
                  // TODO: compare annotation element rows
                  // TODO: compare props.
                }
                else
                {
                  jan = getjAlignmentAnnotation(jal, an[j]);
                  // TODO: ensure we add the alignment annotation before the
                  // automatic annotation rows
                  jal.addAnnotation(jan);
                  bindjvvobj(jan, an[j]);
                  refreshal = true;
                }
              }
            }
            AlignFrame alignFrame;
            if (av == null)
            {
              Cache.log.debug("New alignframe for alignment "
                      + alignment.getVorbaId());
              // ///////////////////////////////
              // construct alignment view
              alignFrame = new AlignFrame(jal, AlignFrame.DEFAULT_WIDTH,
                      AlignFrame.DEFAULT_HEIGHT, alignment.getVorbaId()
                              .toString());
              av = alignFrame.getViewport();
              newAlignmentViews.addElement(av);
              String title = alignment
                      .getProvenance()
                      .getEntry(
                              alignment.getProvenance().getEntryCount() - 1)
                      .getAction();
              if (alignment.getPropertyCount() > 0)
              {
                for (int p = 0, pe = alignment.getPropertyCount(); p < pe; p++)
                {
                  if (alignment.getProperty(p).getName().equals("title"))
                  {
                    title = alignment.getProperty(p).getContent();
                  }
                }
              }
              // TODO: automatically create meaningful title for a vamsas
              // alignment using its provenance.
              if (Cache.log.isDebugEnabled())
              {
                title = title + "(" + alignment.getVorbaId() + ")";

              }
              jalview.gui.Desktop.addInternalFrame(alignFrame, title,
                      AlignFrame.DEFAULT_WIDTH, AlignFrame.DEFAULT_HEIGHT);
              bindjvvobj(av.getSequenceSetId(), alignment);
            }
            else
            {
              // find the alignFrame for jal.
              // TODO: fix this so we retrieve the alignFrame handing av
              // *directly* (JBPNote - don't understand this now)
              // TODO: make sure all associated views are refreshed
              alignFrame = Desktop.getAlignFrameFor(av);
              if (refreshal)
              {
                av.alignmentChanged(alignFrame.alignPanel);
                alignFrame.alignPanel.adjustAnnotationHeight();
              }
            }
            // LOAD TREES
            // /////////////////////////////////////
            if (alignment.getTreeCount() > 0)
            {

              for (int t = 0; t < alignment.getTreeCount(); t++)
              {
                jalview.io.vamsas.Tree vstree = new jalview.io.vamsas.Tree(
                        this, alignFrame, alignment.getTree(t));
                TreePanel tp = null;
                if (vstree.isValidTree())
                {
                  tp = alignFrame.ShowNewickTree(vstree.getNewickTree(),
                          vstree.getTitle(), vstree.getInputData(), 600,
                          500, t * 20 + 50, t * 20 + 50);

                }
                if (tp != null)
                {
                  bindjvvobj(tp, alignment.getTree(t));
                  try
                  {
                    vstree.UpdateSequenceTreeMap(tp);
                  } catch (RuntimeException e)
                  {
                    Cache.log.warn("update of labels failed.", e);
                  }
                }
                else
                {
                  Cache.log.warn("Cannot create tree for tree " + t
                          + " in document ("
                          + alignment.getTree(t).getVorbaId());
                }

              }
            }
          }
        }
      }
      // we do sequenceMappings last because they span all datasets in a vamsas
      // root
      for (int _ds = 0, _nds = root.getDataSetCount(); _ds < _nds; _ds++)
      {
        DataSet dataset = root.getDataSet(_ds);
        if (dataset.getSequenceMappingCount() > 0)
        {
          for (int sm = 0, smCount = dataset.getSequenceMappingCount(); sm < smCount; sm++)
          {
            Rangetype seqmap = new jalview.io.vamsas.Sequencemapping(this,
                    dataset.getSequenceMapping(sm));
          }
        }
      }
    }
    return newAlignmentViews.size();
  }

  public AlignViewport findViewport(Alignment alignment)
  {
    AlignViewport av = null;
    AlignViewport[] avs = Desktop
            .getViewports((String) getvObj2jv(alignment));
    if (avs != null)
    {
      av = avs[0];
    }
    return av;
  }

  // bitfields - should be a template in j1.5
  private static int HASSECSTR = 0;

  private static int HASVALS = 1;

  private static int HASHPHOB = 2;

  private static int HASDC = 3;

  private static int HASDESCSTR = 4;

  private static int HASTWOSTATE = 5; // not used yet.

  /**
   * parses the AnnotationElements - if they exist - into
   * jalview.datamodel.Annotation[] rows Two annotation rows are made if there
   * are distinct annotation for both at 'pos' and 'after pos' at any particular
   * site.
   * 
   * @param annotation
   * @return { boolean[static int constants ], int[ae.length] - map to annotated
   *         object frame, jalview.datamodel.Annotation[],
   *         jalview.datamodel.Annotation[] (after)}
   */
  private Object[] parseRangeAnnotation(
          uk.ac.vamsas.objects.core.RangeAnnotation annotation)
  {
    // set these attributes by looking in the annotation to decide what kind of
    // alignment annotation rows will be made
    // TODO: potentially we might make several annotation rows from one vamsas
    // alignment annotation. the jv2Vobj binding mechanism
    // may not quite cope with this (without binding an array of annotations to
    // a vamsas alignment annotation)
    // summary flags saying what we found over the set of annotation rows.
    boolean[] AeContent = new boolean[]
    { false, false, false, false, false };
    int[] rangeMap = getMapping(annotation);
    jalview.datamodel.Annotation[][] anot = new jalview.datamodel.Annotation[][]
    { new jalview.datamodel.Annotation[rangeMap.length],
        new jalview.datamodel.Annotation[rangeMap.length] };
    boolean mergeable = true; // false if 'after positions cant be placed on
    // same annotation row as positions.

    if (annotation.getAnnotationElementCount() > 0)
    {
      AnnotationElement ae[] = annotation.getAnnotationElement();
      for (int aa = 0; aa < ae.length; aa++)
      {
        int pos = (int) ae[aa].getPosition() - 1; // pos counts from 1 to
        // (|seg.start-seg.end|+1)
        if (pos >= 0 && pos < rangeMap.length)
        {
          int row = ae[aa].getAfter() ? 1 : 0;
          if (anot[row][pos] != null)
          {
            // only time this should happen is if the After flag is set.
            Cache.log.debug("Ignoring duplicate annotation site at " + pos);
            continue;
          }
          if (anot[1 - row][pos] != null)
          {
            mergeable = false;
          }
          String desc = "";
          if (ae[aa].getDescription() != null)
          {
            desc = ae[aa].getDescription();
            if (desc.length() > 0)
            {
              // have imported valid description string
              AeContent[HASDESCSTR] = true;
            }
          }
          String dc = null; // ae[aa].getDisplayCharacter()==null ? "dc" :
          // ae[aa].getDisplayCharacter();
          String ss = null; // ae[aa].getSecondaryStructure()==null ? "ss" :
          // ae[aa].getSecondaryStructure();
          java.awt.Color colour = null;
          if (ae[aa].getGlyphCount() > 0)
          {
            Glyph[] glyphs = ae[aa].getGlyph();
            for (int g = 0; g < glyphs.length; g++)
            {
              if (glyphs[g]
                      .getDict()
                      .equals(uk.ac.vamsas.objects.utils.GlyphDictionary.PROTEIN_SS_3STATE))
              {
                ss = glyphs[g].getContent();
                AeContent[HASSECSTR] = true;
              }
              else if (glyphs[g]
                      .getDict()
                      .equals(uk.ac.vamsas.objects.utils.GlyphDictionary.PROTEIN_HD_HYDRO))
              {
                Cache.log.debug("ignoring hydrophobicity glyph marker.");
                AeContent[HASHPHOB] = true;
                char c = (dc = glyphs[g].getContent()).charAt(0);
                // dc may get overwritten - but we still set the colour.
                colour = new java.awt.Color(c == '+' ? 255 : 0,
                        c == '.' ? 255 : 0, c == '-' ? 255 : 0);

              }
              else if (glyphs[g].getDict().equals(
                      uk.ac.vamsas.objects.utils.GlyphDictionary.DEFAULT))
              {
                dc = glyphs[g].getContent();
                AeContent[HASDC] = true;
              }
              else
              {
                Cache.log
                        .debug("IMPLEMENTATION TODO: Ignoring unknown glyph type "
                                + glyphs[g].getDict());
              }
            }
          }
          float val = 0;
          if (ae[aa].getValueCount() > 0)
          {
            AeContent[HASVALS] = true;
            if (ae[aa].getValueCount() > 1)
            {
              Cache.log.warn("ignoring additional "
                      + (ae[aa].getValueCount() - 1)
                      + " values in annotation element.");
            }
            val = ae[aa].getValue(0);
          }
          if (colour == null)
          {
            anot[row][pos] = new jalview.datamodel.Annotation(
                    (dc != null) ? dc : "", desc,
                    (ss != null) ? ss.charAt(0) : ' ', val);
          }
          else
          {
            anot[row][pos] = new jalview.datamodel.Annotation(
                    (dc != null) ? dc : "", desc,
                    (ss != null) ? ss.charAt(0) : ' ', val, colour);
          }
        }
        else
        {
          Cache.log.warn("Ignoring out of bound annotation element " + aa
                  + " in " + annotation.getVorbaId().getId());
        }
      }
      // decide on how many annotation rows are needed.
      if (mergeable)
      {
        for (int i = 0; i < anot[0].length; i++)
        {
          if (anot[1][i] != null)
          {
            anot[0][i] = anot[1][i];
            anot[0][i].description = anot[0][i].description + " (after)";
            AeContent[HASDESCSTR] = true; // we have valid description string
            // data
            anot[1][i] = null;
          }
        }
        anot[1] = null;
      }
      else
      {
        for (int i = 0; i < anot[0].length; i++)
        {
          anot[1][i].description = anot[1][i].description + " (after)";
        }
      }
      return new Object[]
      { AeContent, rangeMap, anot[0], anot[1] };
    }
    else
    {
      // no annotations to parse. Just return an empty annotationElement[]
      // array.
      return new Object[]
      { AeContent, rangeMap, anot[0], anot[1] };
    }
    // return null;
  }

  /**
   * @param jal
   *          the jalview alignment to which the annotation will be attached
   *          (ideally - freshly updated from corresponding vamsas alignment)
   * @param annotation
   * @return unbound jalview alignment annotation object.
   */
  private jalview.datamodel.AlignmentAnnotation getjAlignmentAnnotation(
          jalview.datamodel.AlignmentI jal,
          uk.ac.vamsas.objects.core.RangeAnnotation annotation)
  {
    if (annotation == null)
    {
      return null;
    }
    // boolean
    // hasSequenceRef=annotation.getClass().equals(uk.ac.vamsas.objects.core.AlignmentSequenceAnnotation.class);
    // boolean hasProvenance=hasSequenceRef ||
    // (annotation.getClass().equals(uk.ac.vamsas.objects.core.AlignmentAnnotation.class));
    /*
     * int se[] = getBounds(annotation); if (se==null) se=new int[]
     * {0,jal.getWidth()-1};
     */
    Object[] parsedRangeAnnotation = parseRangeAnnotation(annotation);
    String a_label = annotation.getLabel();
    String a_descr = annotation.getDescription();
    GraphLine gl = null;
    int type = 0;
    boolean interp = true; // cleared if annotation is DISCRETE
    // set type and other attributes from properties
    if (annotation.getPropertyCount() > 0)
    {
      // look for special jalview properties
      uk.ac.vamsas.objects.core.Property[] props = annotation.getProperty();
      for (int p = 0; p < props.length; p++)
      {
        if (props[p].getName().equalsIgnoreCase(DISCRETE_ANNOTATION))
        {
          type = AlignmentAnnotation.BAR_GRAPH;
          interp = false;
        }
        else if (props[p].getName().equalsIgnoreCase(CONTINUOUS_ANNOTATION))
        {
          type = AlignmentAnnotation.LINE_GRAPH;
        }
        else if (props[p].getName().equalsIgnoreCase(THRESHOLD))
        {
          Float val = null;
          try
          {
            val = new Float(props[p].getContent());
          } catch (Exception e)
          {
            Cache.log.warn("Failed to parse threshold property");
          }
          if (val != null)
            if (gl == null)
            {
              gl = new GraphLine(val.floatValue(), "", java.awt.Color.black);
            }
            else
            {
              gl.value = val.floatValue();
            }
        }
        else if (props[p].getName().equalsIgnoreCase(THRESHOLD + "Name"))
        {
          if (gl == null)
            gl = new GraphLine(0, "", java.awt.Color.black);
          gl.label = props[p].getContent();
        }
      }
    }
    jalview.datamodel.AlignmentAnnotation jan = null;
    if (a_label == null || a_label.length() == 0)
    {
      a_label = annotation.getType();
      if (a_label.length() == 0)
      {
        a_label = "Unamed annotation";
      }
    }
    if (a_descr == null || a_descr.length() == 0)
    {
      a_descr = "Annotation of type '" + annotation.getType() + "'";
    }
    if (parsedRangeAnnotation == null)
    {
      Cache.log
              .debug("Inserting empty annotation row elements for a whole-alignment annotation.");
    }
    else
    {
      if (parsedRangeAnnotation[3] != null)
      {
        Cache.log.warn("Ignoring 'After' annotation row in "
                + annotation.getVorbaId());
      }
      jalview.datamodel.Annotation[] arow = (jalview.datamodel.Annotation[]) parsedRangeAnnotation[2];
      boolean[] has = (boolean[]) parsedRangeAnnotation[0];
      // VAMSAS: getGraph is only on derived annotation for alignments - in this
      // way its 'odd' - there is already an existing TODO about removing this
      // flag as being redundant
      /*
       * if((annotation.getClass().equals(uk.ac.vamsas.objects.core.
       * AlignmentAnnotation.class) &&
       * ((uk.ac.vamsas.objects.core.AlignmentAnnotation)annotation).getGraph())
       * || (hasSequenceRef=true &&
       * ((uk.ac.vamsas.objects.core.AlignmentSequenceAnnotation
       * )annotation).getGraph())) {
       */
      if (has[HASVALS])
      {
        if (type == 0)
        {
          type = jalview.datamodel.AlignmentAnnotation.BAR_GRAPH; // default
          // type of
          // value
          // annotation
          if (has[HASHPHOB])
          {
            // no hints - so we ensure HPHOB display is like this.
            type = jalview.datamodel.AlignmentAnnotation.BAR_GRAPH;
          }
        }
        // make bounds and automatic description strings for jalview user's
        // benefit (these shouldn't be written back to vamsas document)
        boolean first = true;
        float min = 0, max = 1;
        int lastval = 0;
        for (int i = 0; i < arow.length; i++)
        {
          if (arow[i] != null)
          {
            if (i - lastval > 1 && interp)
            {
              // do some interpolation *between* points
              if (arow[lastval] != null)
              {
                float interval = arow[i].value - arow[lastval].value;
                interval /= i - lastval;
                float base = arow[lastval].value;
                for (int ip = lastval + 1, np = 0; ip < i; np++, ip++)
                {
                  arow[ip] = new jalview.datamodel.Annotation("", "", ' ',
                          interval * np + base);
                  // NB - Interpolated points don't get a tooltip and
                  // description.
                }
              }
            }
            lastval = i;
            // check range - shouldn't we have a min and max property in the
            // annotation object ?
            if (first)
            {
              min = max = arow[i].value;
              first = false;
            }
            else
            {
              if (arow[i].value < min)
              {
                min = arow[i].value;
              }
              else if (arow[i].value > max)
              {
                max = arow[i].value;
              }
            }
            // make tooltip and display char value
            if (!has[HASDESCSTR])
            {
              arow[i].description = arow[i].value + "";
            }
            if (!has[HASDC])
            {
              if (!interp)
              {
                if (arow[i].description != null
                        && arow[i].description.length() < 3)
                {
                  // copy over the description as the display char.
                  arow[i].displayCharacter = new String(arow[i].description);
                }
              }
              else
              {
                // mark the position as a point used for the interpolation.
                arow[i].displayCharacter = arow[i].value + "";
              }
            }
          }
        }
        jan = new jalview.datamodel.AlignmentAnnotation(a_label, a_descr,
                arow, min, max, type);
      }
      else
      {
        if (annotation.getAnnotationElementCount() == 0)
        {
          // empty annotation array
          // TODO: alignment 'features' compare rangeType spec to alignment
          // width - if it is not complete, then mark regions on the annotation
          // row.
        }
        jan = new jalview.datamodel.AlignmentAnnotation(a_label, a_descr,
                arow);
        jan.setThreshold(null);
        jan.annotationId = annotation.getVorbaId().toString(); // keep all the
        // ids together.
      }
      if (annotation.getLinkCount() > 0)
      {
        Cache.log.warn("Ignoring " + annotation.getLinkCount()
                + "links added to AlignmentAnnotation.");
      }
      if (annotation.getModifiable() == null
              || annotation.getModifiable().length() == 0) // TODO: USE VAMSAS
      // LIBRARY OBJECT
      // LOCK METHODS)
      {
        jan.editable = true;
      }
      try
      {
        if (annotation.getGroup() != null
                && annotation.getGroup().length() > 0)
        {
          jan.graphGroup = Integer.parseInt(annotation.getGroup()); // TODO:
          // group
          // similarly
          // named
          // annotation
          // together
          // ?
        }
      } catch (Exception e)
      {
        Cache.log
                .info("UNIMPLEMENTED : Couldn't parse non-integer group value for setting graphGroup correctly.");
      }
      return jan;

    }

    return null;
  }

  /**
   * get real bounds of a RangeType's specification. start and end are an
   * inclusive range within which all segments and positions lie. TODO: refactor
   * to vamsas utils
   * 
   * @param dseta
   * @return int[] { start, end}
   */
  private int[] getBounds(RangeType dseta)
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
  private int[] getMapping(RangeType dseta)
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

  /**
   * 
   * @param maprange
   *          where the from range is the local mapped range, and the to range
   *          is the 'mapped' range in the MapRangeType
   * @param default unit for local
   * @param default unit for mapped
   * @return MapList
   */
  private jalview.util.MapList parsemapType(MapType maprange, int localu,
          int mappedu)
  {
    jalview.util.MapList ml = null;
    int[] localRange = getMapping(maprange.getLocal());
    int[] mappedRange = getMapping(maprange.getMapped());
    long lu = maprange.getLocal().hasUnit() ? maprange.getLocal().getUnit()
            : localu;
    long mu = maprange.getMapped().hasUnit() ? maprange.getMapped()
            .getUnit() : mappedu;
    ml = new jalview.util.MapList(localRange, mappedRange, (int) lu,
            (int) mu);
    return ml;
  }

  /**
   * initialise a range type object from a set of start/end inclusive intervals
   * 
   * @param mrt
   * @param range
   */
  private void initRangeType(RangeType mrt, int[] range)
  {
    for (int i = 0; i < range.length; i += 2)
    {
      Seg vSeg = new Seg();
      vSeg.setStart(range[i]);
      vSeg.setEnd(range[i + 1]);
      mrt.addSeg(vSeg);
    }
  }

  /**
   * initialise a MapType object from a MapList object.
   * 
   * @param maprange
   * @param ml
   * @param setUnits
   */
  private void initMapType(MapType maprange, jalview.util.MapList ml,
          boolean setUnits)
  {
    maprange.setLocal(new Local());
    maprange.setMapped(new Mapped());
    initRangeType(maprange.getLocal(), ml.getFromRanges());
    initRangeType(maprange.getMapped(), ml.getToRanges());
    if (setUnits)
    {
      maprange.getLocal().setUnit(ml.getFromRatio());
      maprange.getLocal().setUnit(ml.getToRatio());
    }
  }

  /*
   * not needed now. Provenance getVamsasProvenance(jalview.datamodel.Provenance
   * jprov) { jalview.datamodel.ProvenanceEntry[] entries = null; // TODO: fix
   * App and Action here. Provenance prov = new Provenance();
   * org.exolab.castor.types.Date date = new org.exolab.castor.types.Date( new
   * java.util.Date()); Entry provEntry;
   * 
   * if (jprov != null) { entries = jprov.getEntries(); for (int i = 0; i <
   * entries.length; i++) { provEntry = new Entry(); try { date = new
   * org.exolab.castor.types.Date(entries[i].getDate()); } catch (Exception ex)
   * { ex.printStackTrace();
   * 
   * date = new org.exolab.castor.types.Date(entries[i].getDate()); }
   * provEntry.setDate(date); provEntry.setUser(entries[i].getUser());
   * provEntry.setAction(entries[i].getAction()); prov.addEntry(provEntry); } }
   * else { provEntry = new Entry(); provEntry.setDate(date);
   * provEntry.setUser(System.getProperty("user.name")); // TODO: ext string
   * provEntry.setApp("JVAPP"); // TODO: ext string provEntry.setAction(action);
   * prov.addEntry(provEntry); }
   * 
   * return prov; }
   */
  jalview.datamodel.Provenance getJalviewProvenance(Provenance prov)
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

  Entry dummyPEntry(String action)
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

  Provenance dummyProvenance(String action)
  {
    Provenance prov = new Provenance();
    prov.addEntry(dummyPEntry(action));
    return prov;
  }

  Entry addProvenance(Provenance p, String action)
  {
    Entry dentry = dummyPEntry(action);
    p.addEntry(dentry);
    return dentry;
  }

  public Entry getProvEntry()
  {
    return provEntry;
  }

  public IClientDocument getClientDocument()
  {
    return cdoc;
  }

  public IdentityHashMap getJvObjectBinding()
  {
    return jv2vobj;
  }

  public Hashtable getVamsasObjectBinding()
  {
    return vobj2jv;
  }

  public void storeSequenceMappings(AlignViewport viewport, String title)
          throws Exception
  {
    AlignViewport av = viewport;
    try
    {
      jalview.datamodel.AlignmentI jal = av.getAlignment();
      // /////////////////////////////////////////
      // SAVE THE DATASET
      DataSet dataset = null;
      if (jal.getDataset() == null)
      {
        Cache.log.warn("Creating new dataset for an alignment.");
        jal.setDataset(null);
      }
      dataset = (DataSet) ((Alignment) getjv2vObj(viewport
              .getSequenceSetId())).getV_parent(); // jal.getDataset());
      if (dataset == null)
      {
        dataset = (DataSet) getjv2vObj(jal.getDataset());
        Cache.log
                .error("Can't find the correct dataset for the alignment in this view. Creating new one.");

      }
      // Store any sequence mappings.
      if (av.getAlignment().getCodonFrames() != null
              && av.getAlignment().getCodonFrames().length > 0)
      {
        jalview.datamodel.AlignedCodonFrame[] cframes = av.getAlignment()
                .getCodonFrames();
        for (int cf = 0; cf < cframes.length; cf++)
        {
          if (cframes[cf].getdnaSeqs() != null
                  && cframes[cf].getdnaSeqs().length > 0)
          {
            jalview.datamodel.SequenceI[] dmps = cframes[cf].getdnaSeqs();
            jalview.datamodel.Mapping[] mps = cframes[cf].getProtMappings();
            for (int smp = 0; smp < mps.length; smp++)
            {
              uk.ac.vamsas.objects.core.SequenceType mfrom = (SequenceType) getjv2vObj(dmps[smp]);
              if (mfrom != null)
              {
                new jalview.io.vamsas.Sequencemapping(this, mps[smp],
                        mfrom, dataset);
              }
              else
              {
                Cache.log
                        .warn("NO Vamsas Binding for local sequence! NOT CREATING MAPPING FOR "
                                + dmps[smp].getDisplayId(true)
                                + " to "
                                + mps[smp].getTo().getName());
              }
            }
          }
        }
      }
    } catch (Exception e)
    {
      throw new Exception("Couldn't store sequence mappings for " + title,
              e);
    }
  }

  public void clearSkipList()
  {
    if (skipList != null)
    {
      skipList.clear();
    }
  }

  /**
   * @return the skipList
   */
  public Hashtable getSkipList()
  {
    return skipList;
  }

  /**
   * @param skipList
   *          the skipList to set
   */
  public void setSkipList(Hashtable skipList)
  {
    this.skipList = skipList;
  }

  /**
   * registry for datastoreItems
   */
  DatastoreRegistry dsReg = new DatastoreRegistry();

  public DatastoreRegistry getDatastoreRegisty()
  {
    if (dsReg == null)
    {
      dsReg = new DatastoreRegistry();
    }
    return dsReg;
  }
}
