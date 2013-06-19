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
package jalview.gui;

import java.awt.Rectangle;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.jar.*;

import javax.swing.*;

import org.exolab.castor.xml.*;

import jalview.bin.Cache;
import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.SequenceI;
import jalview.schemabinding.version2.*;
import jalview.schemes.*;
import jalview.util.Platform;
import jalview.util.jarInputStreamProvider;
import jalview.ws.jws2.Jws2Discoverer;
import jalview.ws.jws2.dm.AAConSettings;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.ArgumentI;
import jalview.ws.params.AutoCalcSetting;
import jalview.ws.params.WsParamSetI;

/**
 * Write out the current jalview desktop state as a Jalview XML stream.
 * 
 * Note: the vamsas objects referred to here are primitive versions of the
 * VAMSAS project schema elements - they are not the same and most likely never
 * will be :)
 * 
 * @author $author$
 * @version $Revision: 1.134 $
 */
public class Jalview2XML
{
  /**
   * create/return unique hash string for sq
   * 
   * @param sq
   * @return new or existing unique string for sq
   */
  String seqHash(SequenceI sq)
  {
    if (seqsToIds == null)
    {
      initSeqRefs();
    }
    if (seqsToIds.containsKey(sq))
    {
      return (String) seqsToIds.get(sq);
    }
    else
    {
      // create sequential key
      String key = "sq" + (seqsToIds.size() + 1);
      key = makeHashCode(sq, key); // check we don't have an external reference
      // for it already.
      seqsToIds.put(sq, key);
      return key;
    }
  }

  void clearSeqRefs()
  {
    if (_cleartables)
    {
      if (seqRefIds != null)
      {
        seqRefIds.clear();
      }
      if (seqsToIds != null)
      {
        seqsToIds.clear();
      }
      // seqRefIds = null;
      // seqsToIds = null;
    }
    else
    {
      // do nothing
      warn("clearSeqRefs called when _cleartables was not set. Doing nothing.");
      // seqRefIds = new Hashtable();
      // seqsToIds = new IdentityHashMap();
    }
  }

  void initSeqRefs()
  {
    if (seqsToIds == null)
    {
      seqsToIds = new IdentityHashMap();
    }
    if (seqRefIds == null)
    {
      seqRefIds = new Hashtable();
    }
  }

  /**
   * SequenceI reference -> XML ID string in jalview XML. Populated as XML reps
   * of sequence objects are created.
   */
  java.util.IdentityHashMap seqsToIds = null;

  /**
   * jalview XML Sequence ID to jalview sequence object reference (both dataset
   * and alignment sequences. Populated as XML reps of sequence objects are
   * created.)
   */
  java.util.Hashtable seqRefIds = null; // key->SequenceI resolution

  Vector frefedSequence = null;

  boolean raiseGUI = true; // whether errors are raised in dialog boxes or not

  public Jalview2XML()
  {
  }

  public Jalview2XML(boolean raiseGUI)
  {
    this.raiseGUI = raiseGUI;
  }

  public void resolveFrefedSequences()
  {
    if (frefedSequence.size() > 0)
    {
      int r = 0, rSize = frefedSequence.size();
      while (r < rSize)
      {
        Object[] ref = (Object[]) frefedSequence.elementAt(r);
        if (ref != null)
        {
          String sref = (String) ref[0];
          if (seqRefIds.containsKey(sref))
          {
            if (ref[1] instanceof jalview.datamodel.Mapping)
            {
              SequenceI seq = (SequenceI) seqRefIds.get(sref);
              while (seq.getDatasetSequence() != null)
              {
                seq = seq.getDatasetSequence();
              }
              ((jalview.datamodel.Mapping) ref[1]).setTo(seq);
            }
            else
            {
              if (ref[1] instanceof jalview.datamodel.AlignedCodonFrame)
              {
                SequenceI seq = (SequenceI) seqRefIds.get(sref);
                while (seq.getDatasetSequence() != null)
                {
                  seq = seq.getDatasetSequence();
                }
                if (ref[2] != null
                        && ref[2] instanceof jalview.datamodel.Mapping)
                {
                  jalview.datamodel.Mapping mp = (jalview.datamodel.Mapping) ref[2];
                  ((jalview.datamodel.AlignedCodonFrame) ref[1]).addMap(
                          seq, mp.getTo(), mp.getMap());
                }
                else
                {
                  System.err
                          .println("IMPLEMENTATION ERROR: Unimplemented forward sequence references for AlcodonFrames involving "
                                  + ref[2].getClass() + " type objects.");
                }
              }
              else
              {
                System.err
                        .println("IMPLEMENTATION ERROR: Unimplemented forward sequence references for "
                                + ref[1].getClass() + " type objects.");
              }
            }
            frefedSequence.remove(r);
            rSize--;
          }
          else
          {
            System.err
                    .println("IMPLEMENTATION WARNING: Unresolved forward reference for hash string "
                            + ref[0]
                            + " with objecttype "
                            + ref[1].getClass());
            r++;
          }
        }
        else
        {
          // empty reference
          frefedSequence.remove(r);
          rSize--;
        }
      }
    }
  }

  /**
   * This maintains a list of viewports, the key being the seqSetId. Important
   * to set historyItem and redoList for multiple views
   */
  Hashtable viewportsAdded;

  Hashtable annotationIds = new Hashtable();

  String uniqueSetSuffix = "";

  /**
   * List of pdbfiles added to Jar
   */
  Vector pdbfiles = null;

  // SAVES SEVERAL ALIGNMENT WINDOWS TO SAME JARFILE
  public void SaveState(File statefile)
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(statefile);
      JarOutputStream jout = new JarOutputStream(fos);
      SaveState(jout);

    } catch (Exception e)
    {
      // TODO: inform user of the problem - they need to know if their data was
      // not saved !
      if (errorMessage == null)
      {
        errorMessage = "Couldn't write Jalview Archive to output file '"
                + statefile + "' - See console error log for details";
      }
      else
      {
        errorMessage += "(output file was '" + statefile + "')";
      }
      e.printStackTrace();
    }
    reportErrors();
  }

  /**
   * Writes a jalview project archive to the given Jar output stream.
   * 
   * @param jout
   */
  public void SaveState(JarOutputStream jout)
  {
    JInternalFrame[] frames = Desktop.desktop.getAllFrames();

    if (frames == null)
    {
      return;
    }

    try
    {

      // NOTE UTF-8 MUST BE USED FOR WRITING UNICODE CHARS
      // //////////////////////////////////////////////////
      // NOTE ALSO new PrintWriter must be used for each new JarEntry
      PrintWriter out = null;

      Vector shortNames = new Vector();

      // REVERSE ORDER
      for (int i = frames.length - 1; i > -1; i--)
      {
        if (frames[i] instanceof AlignFrame)
        {
          AlignFrame af = (AlignFrame) frames[i];
          // skip ?
          if (skipList != null
                  && skipList.containsKey(af.getViewport()
                          .getSequenceSetId()))
          {
            continue;
          }

          String shortName = af.getTitle();

          if (shortName.indexOf(File.separatorChar) > -1)
          {
            shortName = shortName.substring(shortName
                    .lastIndexOf(File.separatorChar) + 1);
          }

          int count = 1;

          while (shortNames.contains(shortName))
          {
            if (shortName.endsWith("_" + (count - 1)))
            {
              shortName = shortName
                      .substring(0, shortName.lastIndexOf("_"));
            }

            shortName = shortName.concat("_" + count);
            count++;
          }

          shortNames.addElement(shortName);

          if (!shortName.endsWith(".xml"))
          {
            shortName = shortName + ".xml";
          }

          int ap, apSize = af.alignPanels.size();
          for (ap = 0; ap < apSize; ap++)
          {
            AlignmentPanel apanel = (AlignmentPanel) af.alignPanels
                    .elementAt(ap);
            String fileName = apSize == 1 ? shortName : ap + shortName;
            if (!fileName.endsWith(".xml"))
            {
              fileName = fileName + ".xml";
            }

            SaveState(apanel, fileName, jout);
          }
        }
      }
      try
      {
        jout.flush();
      } catch (Exception foo)
      {
      }
      ;
      jout.close();
    } catch (Exception ex)
    {
      // TODO: inform user of the problem - they need to know if their data was
      // not saved !
      if (errorMessage == null)
      {
        errorMessage = "Couldn't write Jalview Archive - see error output for details";
      }
      ex.printStackTrace();
    }
  }

  // USE THIS METHOD TO SAVE A SINGLE ALIGNMENT WINDOW
  public boolean SaveAlignment(AlignFrame af, String jarFile,
          String fileName)
  {
    try
    {
      int ap, apSize = af.alignPanels.size();
      FileOutputStream fos = new FileOutputStream(jarFile);
      JarOutputStream jout = new JarOutputStream(fos);
      for (ap = 0; ap < apSize; ap++)
      {
        AlignmentPanel apanel = (AlignmentPanel) af.alignPanels
                .elementAt(ap);
        String jfileName = apSize == 1 ? fileName : fileName + ap;
        if (!jfileName.endsWith(".xml"))
        {
          jfileName = jfileName + ".xml";
        }
        SaveState(apanel, jfileName, jout);
      }

      try
      {
        jout.flush();
      } catch (Exception foo)
      {
      }
      ;
      jout.close();
      return true;
    } catch (Exception ex)
    {
      errorMessage = "Couldn't Write alignment view to Jalview Archive - see error output for details";
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * create a JalviewModel from an algnment view and marshall it to a
   * JarOutputStream
   * 
   * @param ap
   *          panel to create jalview model for
   * @param fileName
   *          name of alignment panel written to output stream
   * @param jout
   *          jar output stream
   * @param out
   *          jar entry name
   */
  public JalviewModel SaveState(AlignmentPanel ap, String fileName,
          JarOutputStream jout)
  {
    initSeqRefs();
    Vector jmolViewIds = new Vector(); //
    Vector userColours = new Vector();

    AlignViewport av = ap.av;

    JalviewModel object = new JalviewModel();
    object.setVamsasModel(new jalview.schemabinding.version2.VamsasModel());

    object.setCreationDate(new java.util.Date(System.currentTimeMillis()));
    object.setVersion(jalview.bin.Cache.getProperty("VERSION"));

    jalview.datamodel.AlignmentI jal = av.getAlignment();

    if (av.hasHiddenRows())
    {
      jal = jal.getHiddenSequences().getFullAlignment();
    }

    SequenceSet vamsasSet = new SequenceSet();
    Sequence vamsasSeq;
    JalviewModelSequence jms = new JalviewModelSequence();

    vamsasSet.setGapChar(jal.getGapCharacter() + "");

    if (jal.getDataset() != null)
    {
      // dataset id is the dataset's hashcode
      vamsasSet.setDatasetId(getDatasetIdRef(jal.getDataset()));
    }
    if (jal.getProperties() != null)
    {
      Enumeration en = jal.getProperties().keys();
      while (en.hasMoreElements())
      {
        String key = en.nextElement().toString();
        SequenceSetProperties ssp = new SequenceSetProperties();
        ssp.setKey(key);
        ssp.setValue(jal.getProperties().get(key).toString());
        vamsasSet.addSequenceSetProperties(ssp);
      }
    }

    JSeq jseq;
    Set<String> calcIdSet = new HashSet<String>();

    // SAVE SEQUENCES
    String id = "";
    jalview.datamodel.SequenceI jds;
    for (int i = 0; i < jal.getHeight(); i++)
    {
      jds = jal.getSequenceAt(i);
      id = seqHash(jds);

      if (seqRefIds.get(id) != null)
      {
        // This happens for two reasons: 1. multiple views are being serialised.
        // 2. the hashCode has collided with another sequence's code. This DOES
        // HAPPEN! (PF00072.15.stk does this)
        // JBPNote: Uncomment to debug writing out of files that do not read
        // back in due to ArrayOutOfBoundExceptions.
        // System.err.println("vamsasSeq backref: "+id+"");
        // System.err.println(jds.getName()+"
        // "+jds.getStart()+"-"+jds.getEnd()+" "+jds.getSequenceAsString());
        // System.err.println("Hashcode: "+seqHash(jds));
        // SequenceI rsq = (SequenceI) seqRefIds.get(id + "");
        // System.err.println(rsq.getName()+"
        // "+rsq.getStart()+"-"+rsq.getEnd()+" "+rsq.getSequenceAsString());
        // System.err.println("Hashcode: "+seqHash(rsq));
      }
      else
      {
        vamsasSeq = createVamsasSequence(id, jds);
        vamsasSet.addSequence(vamsasSeq);
        seqRefIds.put(id, jds);
      }

      jseq = new JSeq();
      jseq.setStart(jds.getStart());
      jseq.setEnd(jds.getEnd());
      jseq.setColour(av.getSequenceColour(jds).getRGB());

      jseq.setId(id); // jseq id should be a string not a number

      if (av.hasHiddenRows())
      {
        jseq.setHidden(av.getAlignment().getHiddenSequences().isHidden(jds));

        if (av.isHiddenRepSequence(jal.getSequenceAt(i)))
        {
          jalview.datamodel.SequenceI[] reps = av.getRepresentedSequences(
                  jal.getSequenceAt(i)).getSequencesInOrder(jal);

          for (int h = 0; h < reps.length; h++)
          {
            if (reps[h] != jal.getSequenceAt(i))
            {
              jseq.addHiddenSequences(jal.findIndex(reps[h]));
            }
          }
        }
      }

      if (jds.getDatasetSequence().getSequenceFeatures() != null)
      {
        jalview.datamodel.SequenceFeature[] sf = jds.getDatasetSequence()
                .getSequenceFeatures();
        int index = 0;
        while (index < sf.length)
        {
          Features features = new Features();

          features.setBegin(sf[index].getBegin());
          features.setEnd(sf[index].getEnd());
          features.setDescription(sf[index].getDescription());
          features.setType(sf[index].getType());
          features.setFeatureGroup(sf[index].getFeatureGroup());
          features.setScore(sf[index].getScore());
          if (sf[index].links != null)
          {
            for (int l = 0; l < sf[index].links.size(); l++)
            {
              OtherData keyValue = new OtherData();
              keyValue.setKey("LINK_" + l);
              keyValue.setValue(sf[index].links.elementAt(l).toString());
              features.addOtherData(keyValue);
            }
          }
          if (sf[index].otherDetails != null)
          {
            String key;
            Enumeration keys = sf[index].otherDetails.keys();
            while (keys.hasMoreElements())
            {
              key = keys.nextElement().toString();
              OtherData keyValue = new OtherData();
              keyValue.setKey(key);
              keyValue.setValue(sf[index].otherDetails.get(key).toString());
              features.addOtherData(keyValue);
            }
          }

          jseq.addFeatures(features);
          index++;
        }
      }

      if (jds.getDatasetSequence().getPDBId() != null)
      {
        Enumeration en = jds.getDatasetSequence().getPDBId().elements();
        while (en.hasMoreElements())
        {
          Pdbids pdb = new Pdbids();
          jalview.datamodel.PDBEntry entry = (jalview.datamodel.PDBEntry) en
                  .nextElement();

          pdb.setId(entry.getId());
          pdb.setType(entry.getType());

          AppJmol jmol;
          // This must have been loaded, is it still visible?
          JInternalFrame[] frames = Desktop.desktop.getAllFrames();
          String matchedFile = null;
          for (int f = frames.length - 1; f > -1; f--)
          {
            if (frames[f] instanceof AppJmol)
            {
              jmol = (AppJmol) frames[f];
              for (int peid = 0; peid < jmol.jmb.pdbentry.length; peid++)
              {
                if (!jmol.jmb.pdbentry[peid].getId().equals(entry.getId())
                        && !(entry.getId().length() > 4 && entry
                                .getId()
                                .toLowerCase()
                                .startsWith(
                                        jmol.jmb.pdbentry[peid].getId()
                                                .toLowerCase())))
                  continue;
                if (matchedFile == null)
                {
                  matchedFile = jmol.jmb.pdbentry[peid].getFile();
                }
                else if (!matchedFile.equals(jmol.jmb.pdbentry[peid]
                        .getFile()))
                {
                  Cache.log
                          .warn("Probably lost some PDB-Sequence mappings for this structure file (which apparently has same PDB Entry code): "
                                  + jmol.jmb.pdbentry[peid].getFile());
                  ; // record the
                }
                // file so we
                // can get at it if the ID
                // match is ambiguous (e.g.
                // 1QIP==1qipA)
                String statestring = jmol.jmb.viewer.getStateInfo();

                for (int smap = 0; smap < jmol.jmb.sequence[peid].length; smap++)
                {
                  // if (jal.findIndex(jmol.jmb.sequence[peid][smap]) > -1)
                  if (jds == jmol.jmb.sequence[peid][smap])
                  {
                    StructureState state = new StructureState();
                    state.setVisible(true);
                    state.setXpos(jmol.getX());
                    state.setYpos(jmol.getY());
                    state.setWidth(jmol.getWidth());
                    state.setHeight(jmol.getHeight());
                    state.setViewId(jmol.getViewId());
                    state.setAlignwithAlignPanel(jmol.isUsedforaligment(ap));
                    state.setColourwithAlignPanel(jmol
                            .isUsedforcolourby(ap));
                    state.setColourByJmol(jmol.isColouredByJmol());
                    if (!jmolViewIds.contains(state.getViewId()))
                    {
                      // Make sure we only store a Jmol state once in each XML
                      // document.
                      jmolViewIds.addElement(state.getViewId());
                      state.setContent(statestring.replaceAll("\n", ""));
                    }
                    else
                    {
                      state.setContent("# duplicate state");
                    }
                    pdb.addStructureState(state);
                  }
                }
              }
            }
          }

          if (matchedFile != null || entry.getFile() != null)
          {
            if (entry.getFile() != null)
            {
              // use entry's file
              matchedFile = entry.getFile();
            }
            pdb.setFile(matchedFile); // entry.getFile());
            if (pdbfiles == null)
            {
              pdbfiles = new Vector();
            }

            if (!pdbfiles.contains(entry.getId()))
            {
              pdbfiles.addElement(entry.getId());
              try
              {
                File file = new File(matchedFile);
                if (file.exists() && jout != null)
                {
                  byte[] data = new byte[(int) file.length()];
                  jout.putNextEntry(new JarEntry(entry.getId()));
                  DataInputStream dis = new DataInputStream(
                          new FileInputStream(file));
                  dis.readFully(data);

                  DataOutputStream dout = new DataOutputStream(jout);
                  dout.write(data, 0, data.length);
                  dout.flush();
                  jout.closeEntry();
                }
              } catch (Exception ex)
              {
                ex.printStackTrace();
              }

            }
          }

          if (entry.getProperty() != null)
          {
            PdbentryItem item = new PdbentryItem();
            Hashtable properties = entry.getProperty();
            Enumeration en2 = properties.keys();
            while (en2.hasMoreElements())
            {
              Property prop = new Property();
              String key = en2.nextElement().toString();
              prop.setName(key);
              prop.setValue(properties.get(key).toString());
              item.addProperty(prop);
            }
            pdb.addPdbentryItem(item);
          }

          jseq.addPdbids(pdb);
        }
      }

      jms.addJSeq(jseq);
    }

    if (av.hasHiddenRows())
    {
      jal = av.getAlignment();
    }
    // SAVE MAPPINGS
    if (jal.getCodonFrames() != null && jal.getCodonFrames().length > 0)
    {
      jalview.datamodel.AlignedCodonFrame[] jac = jal.getCodonFrames();
      for (int i = 0; i < jac.length; i++)
      {
        AlcodonFrame alc = new AlcodonFrame();
        vamsasSet.addAlcodonFrame(alc);
        for (int p = 0; p < jac[i].aaWidth; p++)
        {
          Alcodon cmap = new Alcodon();
          if (jac[i].codons[p] != null)
          {
            // Null codons indicate a gapped column in the translated peptide
            // alignment.
            cmap.setPos1(jac[i].codons[p][0]);
            cmap.setPos2(jac[i].codons[p][1]);
            cmap.setPos3(jac[i].codons[p][2]);
          }
          alc.addAlcodon(cmap);
        }
        if (jac[i].getProtMappings() != null
                && jac[i].getProtMappings().length > 0)
        {
          SequenceI[] dnas = jac[i].getdnaSeqs();
          jalview.datamodel.Mapping[] pmaps = jac[i].getProtMappings();
          for (int m = 0; m < pmaps.length; m++)
          {
            AlcodMap alcmap = new AlcodMap();
            alcmap.setDnasq(seqHash(dnas[m]));
            alcmap.setMapping(createVamsasMapping(pmaps[m], dnas[m], null,
                    false));
            alc.addAlcodMap(alcmap);
          }
        }
      }
    }

    // SAVE TREES
    // /////////////////////////////////
    if (av.currentTree != null)
    {
      // FIND ANY ASSOCIATED TREES
      // NOT IMPLEMENTED FOR HEADLESS STATE AT PRESENT
      if (Desktop.desktop != null)
      {
        JInternalFrame[] frames = Desktop.desktop.getAllFrames();

        for (int t = 0; t < frames.length; t++)
        {
          if (frames[t] instanceof TreePanel)
          {
            TreePanel tp = (TreePanel) frames[t];

            if (tp.treeCanvas.av.getAlignment() == jal)
            {
              Tree tree = new Tree();
              tree.setTitle(tp.getTitle());
              tree.setCurrentTree((av.currentTree == tp.getTree()));
              tree.setNewick(tp.getTree().toString());
              tree.setThreshold(tp.treeCanvas.threshold);

              tree.setFitToWindow(tp.fitToWindow.getState());
              tree.setFontName(tp.getTreeFont().getName());
              tree.setFontSize(tp.getTreeFont().getSize());
              tree.setFontStyle(tp.getTreeFont().getStyle());
              tree.setMarkUnlinked(tp.placeholdersMenu.getState());

              tree.setShowBootstrap(tp.bootstrapMenu.getState());
              tree.setShowDistances(tp.distanceMenu.getState());

              tree.setHeight(tp.getHeight());
              tree.setWidth(tp.getWidth());
              tree.setXpos(tp.getX());
              tree.setYpos(tp.getY());
              tree.setId(makeHashCode(tp, null));
              jms.addTree(tree);
            }
          }
        }
      }
    }
    // SAVE ANNOTATIONS
    /**
     * store forward refs from an annotationRow to any groups
     */
    IdentityHashMap groupRefs = new IdentityHashMap();
    if (jal.getAlignmentAnnotation() != null)
    {
      jalview.datamodel.AlignmentAnnotation[] aa = jal
              .getAlignmentAnnotation();

      for (int i = 0; i < aa.length; i++)
      {
        Annotation an = new Annotation();

        if (aa[i].annotationId != null)
        {
          annotationIds.put(aa[i].annotationId, aa[i]);
        }

        an.setId(aa[i].annotationId);

        an.setVisible(aa[i].visible);

        an.setDescription(aa[i].description);

        if (aa[i].sequenceRef != null)
        {
          // TODO later annotation sequenceRef should be the XML ID of the
          // sequence rather than its display name
          an.setSequenceRef(aa[i].sequenceRef.getName());
        }
        if (aa[i].groupRef != null)
        {
          Object groupIdr = groupRefs.get(aa[i].groupRef);
          if (groupIdr == null)
          {
            // make a locally unique String
            groupRefs.put(aa[i].groupRef,
                    groupIdr = ("" + System.currentTimeMillis()
                            + aa[i].groupRef.getName() + groupRefs.size()));
          }
          an.setGroupRef(groupIdr.toString());
        }

        // store all visualization attributes for annotation
        an.setGraphHeight(aa[i].graphHeight);
        an.setCentreColLabels(aa[i].centreColLabels);
        an.setScaleColLabels(aa[i].scaleColLabel);
        an.setShowAllColLabels(aa[i].showAllColLabels);
        an.setBelowAlignment(aa[i].belowAlignment);

        if (aa[i].graph > 0)
        {
          an.setGraph(true);
          an.setGraphType(aa[i].graph);
          an.setGraphGroup(aa[i].graphGroup);
          if (aa[i].getThreshold() != null)
          {
            ThresholdLine line = new ThresholdLine();
            line.setLabel(aa[i].getThreshold().label);
            line.setValue(aa[i].getThreshold().value);
            line.setColour(aa[i].getThreshold().colour.getRGB());
            an.setThresholdLine(line);
          }
        }
        else
        {
          an.setGraph(false);
        }

        an.setLabel(aa[i].label);

        if (aa[i] == av.getAlignmentQualityAnnot()
                || aa[i] == av.getAlignmentConservationAnnotation()
                || aa[i] == av.getAlignmentConsensusAnnotation()
                || aa[i].autoCalculated)
        {
          // new way of indicating autocalculated annotation -
          an.setAutoCalculated(aa[i].autoCalculated);
        }
        if (aa[i].hasScore())
        {
          an.setScore(aa[i].getScore());
        }

        if (aa[i].getCalcId() != null)
        {
          calcIdSet.add(aa[i].getCalcId());
          an.setCalcId(aa[i].getCalcId());
        }

        AnnotationElement ae;
        if (aa[i].annotations != null)
        {
          an.setScoreOnly(false);
          for (int a = 0; a < aa[i].annotations.length; a++)
          {
            if ((aa[i] == null) || (aa[i].annotations[a] == null))
            {
              continue;
            }

            ae = new AnnotationElement();
            if (aa[i].annotations[a].description != null)
              ae.setDescription(aa[i].annotations[a].description);
            if (aa[i].annotations[a].displayCharacter != null)
              ae.setDisplayCharacter(aa[i].annotations[a].displayCharacter);

            if (!Float.isNaN(aa[i].annotations[a].value))
              ae.setValue(aa[i].annotations[a].value);

            ae.setPosition(a);
            if (aa[i].annotations[a].secondaryStructure != ' '
                    && aa[i].annotations[a].secondaryStructure != '\0')
              ae.setSecondaryStructure(aa[i].annotations[a].secondaryStructure
                      + "");

            if (aa[i].annotations[a].colour != null
                    && aa[i].annotations[a].colour != java.awt.Color.black)
            {
              ae.setColour(aa[i].annotations[a].colour.getRGB());
            }

            an.addAnnotationElement(ae);
            if (aa[i].autoCalculated)
            {
              // only write one non-null entry into the annotation row -
              // sufficient to get the visualization attributes necessary to
              // display data
              continue;
            }
          }
        }
        else
        {
          an.setScoreOnly(true);
        }
        vamsasSet.addAnnotation(an);
      }
    }
    // SAVE GROUPS
    if (jal.getGroups() != null)
    {
      JGroup[] groups = new JGroup[jal.getGroups().size()];
      int i = -1;
      for (jalview.datamodel.SequenceGroup sg : jal.getGroups())
      {
        groups[++i] = new JGroup();

        groups[i].setStart(sg.getStartRes());
        groups[i].setEnd(sg.getEndRes());
        groups[i].setName(sg.getName());
        if (groupRefs.containsKey(sg))
        {
          // group has references so set it's ID field
          groups[i].setId(groupRefs.get(sg).toString());
        }
        if (sg.cs != null)
        {
          if (sg.cs.conservationApplied())
          {
            groups[i].setConsThreshold(sg.cs.getConservationInc());

            if (sg.cs instanceof jalview.schemes.UserColourScheme)
            {
              groups[i].setColour(SetUserColourScheme(sg.cs, userColours,
                      jms));
            }
            else
            {
              groups[i]
                      .setColour(ColourSchemeProperty.getColourName(sg.cs));
            }
          }
          else if (sg.cs instanceof jalview.schemes.AnnotationColourGradient)
          {
            groups[i]
                    .setColour(ColourSchemeProperty
                            .getColourName(((jalview.schemes.AnnotationColourGradient) sg.cs)
                                    .getBaseColour()));
          }
          else if (sg.cs instanceof jalview.schemes.UserColourScheme)
          {
            groups[i]
                    .setColour(SetUserColourScheme(sg.cs, userColours, jms));
          }
          else
          {
            groups[i].setColour(ColourSchemeProperty.getColourName(sg.cs));
          }

          groups[i].setPidThreshold(sg.cs.getThreshold());
        }

        groups[i].setOutlineColour(sg.getOutlineColour().getRGB());
        groups[i].setDisplayBoxes(sg.getDisplayBoxes());
        groups[i].setDisplayText(sg.getDisplayText());
        groups[i].setColourText(sg.getColourText());
        groups[i].setTextCol1(sg.textColour.getRGB());
        groups[i].setTextCol2(sg.textColour2.getRGB());
        groups[i].setTextColThreshold(sg.thresholdTextColour);
        groups[i].setShowUnconserved(sg.getShowNonconserved());
        groups[i].setIgnoreGapsinConsensus(sg.getIgnoreGapsConsensus());
        groups[i].setShowConsensusHistogram(sg.isShowConsensusHistogram());
        groups[i].setShowSequenceLogo(sg.isShowSequenceLogo());
        groups[i].setNormaliseSequenceLogo(sg.isNormaliseSequenceLogo());
        for (int s = 0; s < sg.getSize(); s++)
        {
          jalview.datamodel.Sequence seq = (jalview.datamodel.Sequence) sg
                  .getSequenceAt(s);
          groups[i].addSeq(seqHash(seq));
        }
      }

      jms.setJGroup(groups);
    }

    // /////////SAVE VIEWPORT
    Viewport view = new Viewport();
    view.setTitle(ap.alignFrame.getTitle());
    view.setSequenceSetId(makeHashCode(av.getSequenceSetId(),
            av.getSequenceSetId()));
    view.setId(av.getViewId());
    view.setViewName(av.viewName);
    view.setGatheredViews(av.gatherViewsHere);

    if (ap.av.explodedPosition != null)
    {
      view.setXpos(av.explodedPosition.x);
      view.setYpos(av.explodedPosition.y);
      view.setWidth(av.explodedPosition.width);
      view.setHeight(av.explodedPosition.height);
    }
    else
    {
      view.setXpos(ap.alignFrame.getBounds().x);
      view.setYpos(ap.alignFrame.getBounds().y);
      view.setWidth(ap.alignFrame.getBounds().width);
      view.setHeight(ap.alignFrame.getBounds().height);
    }

    view.setStartRes(av.startRes);
    view.setStartSeq(av.startSeq);

    if (av.getGlobalColourScheme() instanceof jalview.schemes.UserColourScheme)
    {
      view.setBgColour(SetUserColourScheme(av.getGlobalColourScheme(),
              userColours, jms));
    }
    else if (av.getGlobalColourScheme() instanceof jalview.schemes.AnnotationColourGradient)
    {
      jalview.schemes.AnnotationColourGradient acg = (jalview.schemes.AnnotationColourGradient) av
              .getGlobalColourScheme();

      AnnotationColours ac = new AnnotationColours();
      ac.setAboveThreshold(acg.getAboveThreshold());
      ac.setThreshold(acg.getAnnotationThreshold());
      ac.setAnnotation(acg.getAnnotation());
      if (acg.getBaseColour() instanceof jalview.schemes.UserColourScheme)
      {
        ac.setColourScheme(SetUserColourScheme(acg.getBaseColour(),
                userColours, jms));
      }
      else
      {
        ac.setColourScheme(ColourSchemeProperty.getColourName(acg
                .getBaseColour()));
      }

      ac.setMaxColour(acg.getMaxColour().getRGB());
      ac.setMinColour(acg.getMinColour().getRGB());
      view.setAnnotationColours(ac);
      view.setBgColour("AnnotationColourGradient");
    }
    else
    {
      view.setBgColour(ColourSchemeProperty.getColourName(av
              .getGlobalColourScheme()));
    }

    ColourSchemeI cs = av.getGlobalColourScheme();

    if (cs != null)
    {
      if (cs.conservationApplied())
      {
        view.setConsThreshold(cs.getConservationInc());
        if (cs instanceof jalview.schemes.UserColourScheme)
        {
          view.setBgColour(SetUserColourScheme(cs, userColours, jms));
        }
      }

      if (cs instanceof ResidueColourScheme)
      {
        view.setPidThreshold(cs.getThreshold());
      }
    }

    view.setConservationSelected(av.getConservationSelected());
    view.setPidSelected(av.getAbovePIDThreshold());
    view.setFontName(av.font.getName());
    view.setFontSize(av.font.getSize());
    view.setFontStyle(av.font.getStyle());
    view.setRenderGaps(av.renderGaps);
    view.setShowAnnotation(av.getShowAnnotation());
    view.setShowBoxes(av.getShowBoxes());
    view.setShowColourText(av.getColourText());
    view.setShowFullId(av.getShowJVSuffix());
    view.setRightAlignIds(av.rightAlignIds);
    view.setShowSequenceFeatures(av.showSequenceFeatures);
    view.setShowText(av.getShowText());
    view.setShowUnconserved(av.getShowUnconserved());
    view.setWrapAlignment(av.getWrapAlignment());
    view.setTextCol1(av.textColour.getRGB());
    view.setTextCol2(av.textColour2.getRGB());
    view.setTextColThreshold(av.thresholdTextColour);
    view.setShowConsensusHistogram(av.isShowConsensusHistogram());
    view.setShowSequenceLogo(av.isShowSequenceLogo());
    view.setNormaliseSequenceLogo(av.isNormaliseSequenceLogo());
    view.setShowGroupConsensus(av.isShowGroupConsensus());
    view.setShowGroupConservation(av.isShowGroupConservation());
    view.setShowNPfeatureTooltip(av.isShowNpFeats());
    view.setShowDbRefTooltip(av.isShowDbRefs());
    view.setFollowHighlight(av.followHighlight);
    view.setFollowSelection(av.followSelection);
    view.setIgnoreGapsinConsensus(av.getIgnoreGapsConsensus());
    if (av.featuresDisplayed != null)
    {
      jalview.schemabinding.version2.FeatureSettings fs = new jalview.schemabinding.version2.FeatureSettings();

      String[] renderOrder = ap.seqPanel.seqCanvas.getFeatureRenderer().renderOrder;

      Vector settingsAdded = new Vector();
      Object gstyle = null;
      GraduatedColor gcol = null;
      if (renderOrder != null)
      {
        for (int ro = 0; ro < renderOrder.length; ro++)
        {
          gstyle = ap.seqPanel.seqCanvas.getFeatureRenderer()
                  .getFeatureStyle(renderOrder[ro]);
          Setting setting = new Setting();
          setting.setType(renderOrder[ro]);
          if (gstyle instanceof GraduatedColor)
          {
            gcol = (GraduatedColor) gstyle;
            setting.setColour(gcol.getMaxColor().getRGB());
            setting.setMincolour(gcol.getMinColor().getRGB());
            setting.setMin(gcol.getMin());
            setting.setMax(gcol.getMax());
            setting.setColourByLabel(gcol.isColourByLabel());
            setting.setAutoScale(gcol.isAutoScale());
            setting.setThreshold(gcol.getThresh());
            setting.setThreshstate(gcol.getThreshType());
          }
          else
          {
            setting.setColour(ap.seqPanel.seqCanvas.getFeatureRenderer()
                    .getColour(renderOrder[ro]).getRGB());
          }

          setting.setDisplay(av.featuresDisplayed
                  .containsKey(renderOrder[ro]));
          float rorder = ap.seqPanel.seqCanvas.getFeatureRenderer()
                  .getOrder(renderOrder[ro]);
          if (rorder > -1)
          {
            setting.setOrder(rorder);
          }
          fs.addSetting(setting);
          settingsAdded.addElement(renderOrder[ro]);
        }
      }

      // Make sure we save none displayed feature settings
      Iterator en = ap.seqPanel.seqCanvas.getFeatureRenderer().featureColours
              .keySet().iterator();
      while (en.hasNext())
      {
        String key = en.next().toString();
        if (settingsAdded.contains(key))
        {
          continue;
        }

        Setting setting = new Setting();
        setting.setType(key);
        setting.setColour(ap.seqPanel.seqCanvas.getFeatureRenderer()
                .getColour(key).getRGB());

        setting.setDisplay(false);
        float rorder = ap.seqPanel.seqCanvas.getFeatureRenderer().getOrder(
                key);
        if (rorder > -1)
        {
          setting.setOrder(rorder);
        }
        fs.addSetting(setting);
        settingsAdded.addElement(key);
      }
      en = ap.seqPanel.seqCanvas.getFeatureRenderer().featureGroups
              .keySet().iterator();
      Vector groupsAdded = new Vector();
      while (en.hasNext())
      {
        String grp = en.next().toString();
        if (groupsAdded.contains(grp))
        {
          continue;
        }
        Group g = new Group();
        g.setName(grp);
        g.setDisplay(((Boolean) ap.seqPanel.seqCanvas.getFeatureRenderer().featureGroups
                .get(grp)).booleanValue());
        fs.addGroup(g);
        groupsAdded.addElement(grp);
      }
      jms.setFeatureSettings(fs);

    }

    if (av.hasHiddenColumns())
    {
      if (av.getColumnSelection() == null
              || av.getColumnSelection().getHiddenColumns() == null)
      {
        warn("REPORT BUG: avoided null columnselection bug (DMAM reported). Please contact Jim about this.");
      }
      else
      {
        for (int c = 0; c < av.getColumnSelection().getHiddenColumns()
                .size(); c++)
        {
          int[] region = (int[]) av.getColumnSelection().getHiddenColumns()
                  .elementAt(c);
          HiddenColumns hc = new HiddenColumns();
          hc.setStart(region[0]);
          hc.setEnd(region[1]);
          view.addHiddenColumns(hc);
        }
      }
    }
    if (calcIdSet.size() > 0)
    {
      for (String calcId : calcIdSet)
      {
        if (calcId.trim().length() > 0)
        {
          CalcIdParam cidp = createCalcIdParam(calcId, av);
          // Some calcIds have no parameters.
          if (cidp != null)
          {
            view.addCalcIdParam(cidp);
          }
        }
      }
    }

    jms.addViewport(view);

    object.setJalviewModelSequence(jms);
    object.getVamsasModel().addSequenceSet(vamsasSet);

    if (jout != null && fileName != null)
    {
      // We may not want to write the object to disk,
      // eg we can copy the alignViewport to a new view object
      // using save and then load
      try
      {
        JarEntry entry = new JarEntry(fileName);
        jout.putNextEntry(entry);
        PrintWriter pout = new PrintWriter(new OutputStreamWriter(jout,
                "UTF-8"));
        org.exolab.castor.xml.Marshaller marshaller = new org.exolab.castor.xml.Marshaller(
                pout);
        marshaller.marshal(object);
        pout.flush();
        jout.closeEntry();
      } catch (Exception ex)
      {
        // TODO: raise error in GUI if marshalling failed.
        ex.printStackTrace();
      }
    }
    return object;
  }

  private CalcIdParam createCalcIdParam(String calcId, AlignViewport av)
  {
    AutoCalcSetting settings = av.getCalcIdSettingsFor(calcId);
    if (settings != null)
    {
      CalcIdParam vCalcIdParam = new CalcIdParam();
      vCalcIdParam.setCalcId(calcId);
      vCalcIdParam.addServiceURL(settings.getServiceURI());
      // generic URI allowing a third party to resolve another instance of the
      // service used for this calculation
      for (String urls : settings.getServiceURLs())
      {
        vCalcIdParam.addServiceURL(urls);
      }
      vCalcIdParam.setVersion("1.0");
      if (settings.getPreset() != null)
      {
        WsParamSetI setting = settings.getPreset();
        vCalcIdParam.setName(setting.getName());
        vCalcIdParam.setDescription(setting.getDescription());
      }
      else
      {
        vCalcIdParam.setName("");
        vCalcIdParam.setDescription("Last used parameters");
      }
      // need to be able to recover 1) settings 2) user-defined presets or
      // recreate settings from preset 3) predefined settings provided by
      // service - or settings that can be transferred (or discarded)
      vCalcIdParam.setParameters(settings.getWsParamFile().replace("\n",
              "|\\n|"));
      vCalcIdParam.setAutoUpdate(settings.isAutoUpdate());
      // todo - decide if updateImmediately is needed for any projects.

      return vCalcIdParam;
    }
    return null;
  }

  private boolean recoverCalcIdParam(CalcIdParam calcIdParam,
          AlignViewport av)
  {
    if (calcIdParam.getVersion().equals("1.0"))
    {
      Jws2Instance service = Jws2Discoverer.getDiscoverer()
              .getPreferredServiceFor(calcIdParam.getServiceURL());
      if (service != null)
      {
        WsParamSetI parmSet = null;
        try
        {
          parmSet = service.getParamStore().parseServiceParameterFile(
                  calcIdParam.getName(), calcIdParam.getDescription(),
                  calcIdParam.getServiceURL(),
                  calcIdParam.getParameters().replace("|\\n|", "\n"));
        } catch (IOException x)
        {
          warn("Couldn't parse parameter data for "
                  + calcIdParam.getCalcId(), x);
          return false;
        }
        List<ArgumentI> argList = null;
        if (calcIdParam.getName().length() > 0)
        {
          parmSet = service.getParamStore()
                  .getPreset(calcIdParam.getName());
          if (parmSet != null)
          {
            // TODO : check we have a good match with settings in AACon -
            // otherwise we'll need to create a new preset
          }
        }
        else
        {
          argList = parmSet.getArguments();
          parmSet = null;
        }
        AAConSettings settings = new AAConSettings(
                calcIdParam.isAutoUpdate(), service, parmSet, argList);
        av.setCalcIdSettingsFor(calcIdParam.getCalcId(), settings,
                calcIdParam.isNeedsUpdate());
        return true;
      }
      else
      {
        warn("Cannot resolve a service for the parameters used in this project. Try configuring a JABAWS server.");
        return false;
      }
    }
    throw new Error("Unsupported Version for calcIdparam "
            + calcIdParam.toString());
  }

  /**
   * External mapping between jalview objects and objects yielding a valid and
   * unique object ID string. This is null for normal Jalview project IO, but
   * non-null when a jalview project is being read or written as part of a
   * vamsas session.
   */
  IdentityHashMap jv2vobj = null;

  /**
   * Construct a unique ID for jvobj using either existing bindings or if none
   * exist, the result of the hashcode call for the object.
   * 
   * @param jvobj
   *          jalview data object
   * @return unique ID for referring to jvobj
   */
  private String makeHashCode(Object jvobj, String altCode)
  {
    if (jv2vobj != null)
    {
      Object id = jv2vobj.get(jvobj);
      if (id != null)
      {
        return id.toString();
      }
      // check string ID mappings
      if (jvids2vobj != null && jvobj instanceof String)
      {
        id = jvids2vobj.get(jvobj);
      }
      if (id != null)
      {
        return id.toString();
      }
      // give up and warn that something has gone wrong
      warn("Cannot find ID for object in external mapping : " + jvobj);
    }
    return altCode;
  }

  /**
   * return local jalview object mapped to ID, if it exists
   * 
   * @param idcode
   *          (may be null)
   * @return null or object bound to idcode
   */
  private Object retrieveExistingObj(String idcode)
  {
    if (idcode != null && vobj2jv != null)
    {
      return vobj2jv.get(idcode);
    }
    return null;
  }

  /**
   * binding from ID strings from external mapping table to jalview data model
   * objects.
   */
  private Hashtable vobj2jv;

  private Sequence createVamsasSequence(String id, SequenceI jds)
  {
    return createVamsasSequence(true, id, jds, null);
  }

  private Sequence createVamsasSequence(boolean recurse, String id,
          SequenceI jds, SequenceI parentseq)
  {
    Sequence vamsasSeq = new Sequence();
    vamsasSeq.setId(id);
    vamsasSeq.setName(jds.getName());
    vamsasSeq.setSequence(jds.getSequenceAsString());
    vamsasSeq.setDescription(jds.getDescription());
    jalview.datamodel.DBRefEntry[] dbrefs = null;
    if (jds.getDatasetSequence() != null)
    {
      vamsasSeq.setDsseqid(seqHash(jds.getDatasetSequence()));
      if (jds.getDatasetSequence().getDBRef() != null)
      {
        dbrefs = jds.getDatasetSequence().getDBRef();
      }
    }
    else
    {
      vamsasSeq.setDsseqid(id); // so we can tell which sequences really are
      // dataset sequences only
      dbrefs = jds.getDBRef();
    }
    if (dbrefs != null)
    {
      for (int d = 0; d < dbrefs.length; d++)
      {
        DBRef dbref = new DBRef();
        dbref.setSource(dbrefs[d].getSource());
        dbref.setVersion(dbrefs[d].getVersion());
        dbref.setAccessionId(dbrefs[d].getAccessionId());
        if (dbrefs[d].hasMap())
        {
          Mapping mp = createVamsasMapping(dbrefs[d].getMap(), parentseq,
                  jds, recurse);
          dbref.setMapping(mp);
        }
        vamsasSeq.addDBRef(dbref);
      }
    }
    return vamsasSeq;
  }

  private Mapping createVamsasMapping(jalview.datamodel.Mapping jmp,
          SequenceI parentseq, SequenceI jds, boolean recurse)
  {
    Mapping mp = null;
    if (jmp.getMap() != null)
    {
      mp = new Mapping();

      jalview.util.MapList mlst = jmp.getMap();
      int r[] = mlst.getFromRanges();
      for (int s = 0; s < r.length; s += 2)
      {
        MapListFrom mfrom = new MapListFrom();
        mfrom.setStart(r[s]);
        mfrom.setEnd(r[s + 1]);
        mp.addMapListFrom(mfrom);
      }
      r = mlst.getToRanges();
      for (int s = 0; s < r.length; s += 2)
      {
        MapListTo mto = new MapListTo();
        mto.setStart(r[s]);
        mto.setEnd(r[s + 1]);
        mp.addMapListTo(mto);
      }
      mp.setMapFromUnit(mlst.getFromRatio());
      mp.setMapToUnit(mlst.getToRatio());
      if (jmp.getTo() != null)
      {
        MappingChoice mpc = new MappingChoice();
        if (recurse
                && (parentseq != jmp.getTo() || parentseq
                        .getDatasetSequence() != jmp.getTo()))
        {
          mpc.setSequence(createVamsasSequence(false, seqHash(jmp.getTo()),
                  jmp.getTo(), jds));
        }
        else
        {
          String jmpid = "";
          SequenceI ps = null;
          if (parentseq != jmp.getTo()
                  && parentseq.getDatasetSequence() != jmp.getTo())
          {
            // chaining dbref rather than a handshaking one
            jmpid = seqHash(ps = jmp.getTo());
          }
          else
          {
            jmpid = seqHash(ps = parentseq);
          }
          mpc.setDseqFor(jmpid);
          if (!seqRefIds.containsKey(mpc.getDseqFor()))
          {
            jalview.bin.Cache.log.debug("creatign new DseqFor ID");
            seqRefIds.put(mpc.getDseqFor(), ps);
          }
          else
          {
            jalview.bin.Cache.log.debug("reusing DseqFor ID");
          }
        }
        mp.setMappingChoice(mpc);
      }
    }
    return mp;
  }

  String SetUserColourScheme(jalview.schemes.ColourSchemeI cs,
          Vector userColours, JalviewModelSequence jms)
  {
    String id = null;
    jalview.schemes.UserColourScheme ucs = (jalview.schemes.UserColourScheme) cs;
    boolean newucs = false;
    if (!userColours.contains(ucs))
    {
      userColours.add(ucs);
      newucs = true;
    }
    id = "ucs" + userColours.indexOf(ucs);
    if (newucs)
    {
      // actually create the scheme's entry in the XML model
      java.awt.Color[] colours = ucs.getColours();
      jalview.schemabinding.version2.UserColours uc = new jalview.schemabinding.version2.UserColours();
      jalview.schemabinding.version2.UserColourScheme jbucs = new jalview.schemabinding.version2.UserColourScheme();

      for (int i = 0; i < colours.length; i++)
      {
        jalview.schemabinding.version2.Colour col = new jalview.schemabinding.version2.Colour();
        col.setName(ResidueProperties.aa[i]);
        col.setRGB(jalview.util.Format.getHexString(colours[i]));
        jbucs.addColour(col);
      }
      if (ucs.getLowerCaseColours() != null)
      {
        colours = ucs.getLowerCaseColours();
        for (int i = 0; i < colours.length; i++)
        {
          jalview.schemabinding.version2.Colour col = new jalview.schemabinding.version2.Colour();
          col.setName(ResidueProperties.aa[i].toLowerCase());
          col.setRGB(jalview.util.Format.getHexString(colours[i]));
          jbucs.addColour(col);
        }
      }

      uc.setId(id);
      uc.setUserColourScheme(jbucs);
      jms.addUserColours(uc);
    }

    return id;
  }

  jalview.schemes.UserColourScheme GetUserColourScheme(
          JalviewModelSequence jms, String id)
  {
    UserColours[] uc = jms.getUserColours();
    UserColours colours = null;

    for (int i = 0; i < uc.length; i++)
    {
      if (uc[i].getId().equals(id))
      {
        colours = uc[i];

        break;
      }
    }

    java.awt.Color[] newColours = new java.awt.Color[24];

    for (int i = 0; i < 24; i++)
    {
      newColours[i] = new java.awt.Color(Integer.parseInt(colours
              .getUserColourScheme().getColour(i).getRGB(), 16));
    }

    jalview.schemes.UserColourScheme ucs = new jalview.schemes.UserColourScheme(
            newColours);

    if (colours.getUserColourScheme().getColourCount() > 24)
    {
      newColours = new java.awt.Color[23];
      for (int i = 0; i < 23; i++)
      {
        newColours[i] = new java.awt.Color(Integer.parseInt(colours
                .getUserColourScheme().getColour(i + 24).getRGB(), 16));
      }
      ucs.setLowerCaseColours(newColours);
    }

    return ucs;
  }

  /**
   * contains last error message (if any) encountered by XML loader.
   */
  String errorMessage = null;

  /**
   * flag to control whether the Jalview2XML_V1 parser should be deferred to if
   * exceptions are raised during project XML parsing
   */
  public boolean attemptversion1parse = true;

  /**
   * Load a jalview project archive from a jar file
   * 
   * @param file
   *          - HTTP URL or filename
   */
  public AlignFrame LoadJalviewAlign(final String file)
  {

    jalview.gui.AlignFrame af = null;

    try
    {
      // create list to store references for any new Jmol viewers created
      newStructureViewers=new Vector<AppJmol>();
      // UNMARSHALLER SEEMS TO CLOSE JARINPUTSTREAM, MOST ANNOYING
      // Workaround is to make sure caller implements the JarInputStreamProvider
      // interface
      // so we can re-open the jar input stream for each entry.

      jarInputStreamProvider jprovider = createjarInputStreamProvider(file);
      af = LoadJalviewAlign(jprovider);
      
    } catch (MalformedURLException e)
    {
      errorMessage = "Invalid URL format for '" + file + "'";
      reportErrors();
    }
    finally {
      try
      {
        SwingUtilities.invokeAndWait(new Runnable()
        {
          public void run()
          {
            setLoadingFinishedForNewStructureViewers();
          };
        });
      } catch (Exception x)
      {

      }
    }
    return af;
  }

  private jarInputStreamProvider createjarInputStreamProvider(
          final String file) throws MalformedURLException
  {
    URL url = null;
    errorMessage = null;
    uniqueSetSuffix = null;
    seqRefIds = null;
    viewportsAdded = null;
    frefedSequence = null;

    if (file.startsWith("http://"))
    {
      url = new URL(file);
    }
    final URL _url = url;
    return new jarInputStreamProvider()
    {

      @Override
      public JarInputStream getJarInputStream() throws IOException
      {
        if (_url != null)
        {
          return new JarInputStream(_url.openStream());
        }
        else
        {
          return new JarInputStream(new FileInputStream(file));
        }
      }

      @Override
      public String getFilename()
      {
        return file;
      }
    };
  }

  /**
   * Recover jalview session from a jalview project archive. Caller may
   * initialise uniqueSetSuffix, seqRefIds, viewportsAdded and frefedSequence
   * themselves. Any null fields will be initialised with default values,
   * non-null fields are left alone.
   * 
   * @param jprovider
   * @return
   */
  public AlignFrame LoadJalviewAlign(final jarInputStreamProvider jprovider)
  {
    errorMessage = null;
    if (uniqueSetSuffix == null)
    {
      uniqueSetSuffix = System.currentTimeMillis() % 100000 + "";
    }
    if (seqRefIds == null)
    {
      seqRefIds = new Hashtable();
    }
    if (viewportsAdded == null)
    {
      viewportsAdded = new Hashtable();
    }
    if (frefedSequence == null)
    {
      frefedSequence = new Vector();
    }

    jalview.gui.AlignFrame af = null;
    Hashtable gatherToThisFrame = new Hashtable();
    final String file = jprovider.getFilename();
    try
    {
      JarInputStream jin = null;
      JarEntry jarentry = null;
      int entryCount = 1;

      do
      {
        jin = jprovider.getJarInputStream();
        for (int i = 0; i < entryCount; i++)
        {
          jarentry = jin.getNextJarEntry();
        }

        if (jarentry != null && jarentry.getName().endsWith(".xml"))
        {
          InputStreamReader in = new InputStreamReader(jin, "UTF-8");
          JalviewModel object = new JalviewModel();

          Unmarshaller unmar = new Unmarshaller(object);
          unmar.setValidation(false);
          object = (JalviewModel) unmar.unmarshal(in);
          if (true) // !skipViewport(object))
          {
            af = LoadFromObject(object, file, true, jprovider);
            if (af.viewport.gatherViewsHere)
            {
              gatherToThisFrame.put(af.viewport.getSequenceSetId(), af);
            }
          }
          entryCount++;
        }
        else if (jarentry != null)
        {
          // Some other file here.
          entryCount++;
        }
      } while (jarentry != null);
      resolveFrefedSequences();
    } catch (java.io.FileNotFoundException ex)
    {
      ex.printStackTrace();
      errorMessage = "Couldn't locate Jalview XML file : " + file;
      System.err.println("Exception whilst loading jalview XML file : "
              + ex + "\n");
    } catch (java.net.UnknownHostException ex)
    {
      ex.printStackTrace();
      errorMessage = "Couldn't locate Jalview XML file : " + file;
      System.err.println("Exception whilst loading jalview XML file : "
              + ex + "\n");
    } catch (Exception ex)
    {
      System.err.println("Parsing as Jalview Version 2 file failed.");
      ex.printStackTrace(System.err);
      if (attemptversion1parse)
      {
        // Is Version 1 Jar file?
        try
        {
          af = new Jalview2XML_V1(raiseGUI).LoadJalviewAlign(jprovider);
        } catch (Exception ex2)
        {
          System.err.println("Exception whilst loading as jalviewXMLV1:");
          ex2.printStackTrace();
          af = null;
        }
      }
      if (Desktop.instance != null)
      {
        Desktop.instance.stopLoading();
      }
      if (af != null)
      {
        System.out.println("Successfully loaded archive file");
        return af;
      }
      ex.printStackTrace();

      System.err.println("Exception whilst loading jalview XML file : "
              + ex + "\n");
    } catch (OutOfMemoryError e)
    {
      // Don't use the OOM Window here
      errorMessage = "Out of memory loading jalview XML file";
      System.err.println("Out of memory whilst loading jalview XML file");
      e.printStackTrace();
    }

    if (Desktop.instance != null)
    {
      Desktop.instance.stopLoading();
    }

    Enumeration en = gatherToThisFrame.elements();
    while (en.hasMoreElements())
    {
      Desktop.instance.gatherViews((AlignFrame) en.nextElement());
    }
    if (errorMessage != null)
    {
      reportErrors();
    }
    return af;
  }

  /**
   * check errorMessage for a valid error message and raise an error box in the
   * GUI or write the current errorMessage to stderr and then clear the error
   * state.
   */
  protected void reportErrors()
  {
    reportErrors(false);
  }

  protected void reportErrors(final boolean saving)
  {
    if (errorMessage != null)
    {
      final String finalErrorMessage = errorMessage;
      if (raiseGUI)
      {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
          @Override
          public void run()
          {
            JOptionPane.showInternalMessageDialog(Desktop.desktop,
                    finalErrorMessage, "Error "
                            + (saving ? "saving" : "loading")
                            + " Jalview file", JOptionPane.WARNING_MESSAGE);
          }
        });
      }
      else
      {
        System.err.println("Problem loading Jalview file: " + errorMessage);
      }
    }
    errorMessage = null;
  }

  Hashtable alreadyLoadedPDB;

  /**
   * when set, local views will be updated from view stored in JalviewXML
   * Currently (28th Sep 2008) things will go horribly wrong in vamsas document
   * sync if this is set to true.
   */
  private final boolean updateLocalViews = false;

  String loadPDBFile(jarInputStreamProvider jprovider, String pdbId)
  {
    if (alreadyLoadedPDB == null)
      alreadyLoadedPDB = new Hashtable();

    if (alreadyLoadedPDB.containsKey(pdbId))
      return alreadyLoadedPDB.get(pdbId).toString();

    try
    {
      JarInputStream jin = jprovider.getJarInputStream();
      /*
       * if (jprovider.startsWith("http://")) { jin = new JarInputStream(new
       * URL(jprovider).openStream()); } else { jin = new JarInputStream(new
       * FileInputStream(jprovider)); }
       */

      JarEntry entry = null;
      do
      {
        entry = jin.getNextJarEntry();
      } while (entry != null && !entry.getName().equals(pdbId));
      if (entry != null)
      {
        BufferedReader in = new BufferedReader(new InputStreamReader(jin));
        File outFile = File.createTempFile("jalview_pdb", ".txt");
        outFile.deleteOnExit();
        PrintWriter out = new PrintWriter(new FileOutputStream(outFile));
        String data;

        while ((data = in.readLine()) != null)
        {
          out.println(data);
        }
        try
        {
          out.flush();
        } catch (Exception foo)
        {
        }
        ;
        out.close();
        String t = outFile.getAbsolutePath();
        alreadyLoadedPDB.put(pdbId, t);
        return t;
      }
      else
      {
        warn("Couldn't find PDB file entry in Jalview Jar for " + pdbId);
      }
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  private class JvAnnotRow
  {
    public JvAnnotRow(int i, AlignmentAnnotation jaa)
    {
      order = i;
      template = jaa;
    }

    /**
     * persisted version of annotation row from which to take vis properties
     */
    public jalview.datamodel.AlignmentAnnotation template;

    /**
     * original position of the annotation row in the alignment
     */
    public int order;
  }

  /**
   * Load alignment frame from jalview XML DOM object
   * 
   * @param object
   *          DOM
   * @param file
   *          filename source string
   * @param loadTreesAndStructures
   *          when false only create Viewport
   * @param jprovider
   *          data source provider
   * @return alignment frame created from view stored in DOM
   */
  AlignFrame LoadFromObject(JalviewModel object, String file,
          boolean loadTreesAndStructures, jarInputStreamProvider jprovider)
  {
    SequenceSet vamsasSet = object.getVamsasModel().getSequenceSet(0);
    Sequence[] vamsasSeq = vamsasSet.getSequence();

    JalviewModelSequence jms = object.getJalviewModelSequence();

    Viewport view = jms.getViewport(0);
    // ////////////////////////////////
    // LOAD SEQUENCES

    Vector hiddenSeqs = null;
    jalview.datamodel.Sequence jseq;

    ArrayList tmpseqs = new ArrayList();

    boolean multipleView = false;

    JSeq[] JSEQ = object.getJalviewModelSequence().getJSeq();
    int vi = 0; // counter in vamsasSeq array
    for (int i = 0; i < JSEQ.length; i++)
    {
      String seqId = JSEQ[i].getId();

      if (seqRefIds.get(seqId) != null)
      {
        tmpseqs.add(seqRefIds.get(seqId));
        multipleView = true;
      }
      else
      {
        jseq = new jalview.datamodel.Sequence(vamsasSeq[vi].getName(),
                vamsasSeq[vi].getSequence());
        jseq.setDescription(vamsasSeq[vi].getDescription());
        jseq.setStart(JSEQ[i].getStart());
        jseq.setEnd(JSEQ[i].getEnd());
        jseq.setVamsasId(uniqueSetSuffix + seqId);
        seqRefIds.put(vamsasSeq[vi].getId(), jseq);
        tmpseqs.add(jseq);
        vi++;
      }

      if (JSEQ[i].getHidden())
      {
        if (hiddenSeqs == null)
        {
          hiddenSeqs = new Vector();
        }

        hiddenSeqs.addElement(seqRefIds.get(seqId));
      }

    }

    // /
    // Create the alignment object from the sequence set
    // ///////////////////////////////
    jalview.datamodel.Sequence[] orderedSeqs = new jalview.datamodel.Sequence[tmpseqs
            .size()];

    tmpseqs.toArray(orderedSeqs);

    jalview.datamodel.Alignment al = new jalview.datamodel.Alignment(
            orderedSeqs);

    // / Add the alignment properties
    for (int i = 0; i < vamsasSet.getSequenceSetPropertiesCount(); i++)
    {
      SequenceSetProperties ssp = vamsasSet.getSequenceSetProperties(i);
      al.setProperty(ssp.getKey(), ssp.getValue());
    }

    // /
    // SequenceFeatures are added to the DatasetSequence,
    // so we must create or recover the dataset before loading features
    // ///////////////////////////////
    if (vamsasSet.getDatasetId() == null || vamsasSet.getDatasetId() == "")
    {
      // older jalview projects do not have a dataset id.
      al.setDataset(null);
    }
    else
    {
      recoverDatasetFor(vamsasSet, al);
    }
    // ///////////////////////////////

    Hashtable pdbloaded = new Hashtable();
    if (!multipleView)
    {
      // load sequence features, database references and any associated PDB
      // structures for the alignment
      for (int i = 0; i < vamsasSeq.length; i++)
      {
        if (JSEQ[i].getFeaturesCount() > 0)
        {
          Features[] features = JSEQ[i].getFeatures();
          for (int f = 0; f < features.length; f++)
          {
            jalview.datamodel.SequenceFeature sf = new jalview.datamodel.SequenceFeature(
                    features[f].getType(), features[f].getDescription(),
                    features[f].getStatus(), features[f].getBegin(),
                    features[f].getEnd(), features[f].getFeatureGroup());

            sf.setScore(features[f].getScore());
            for (int od = 0; od < features[f].getOtherDataCount(); od++)
            {
              OtherData keyValue = features[f].getOtherData(od);
              if (keyValue.getKey().startsWith("LINK"))
              {
                sf.addLink(keyValue.getValue());
              }
              else
              {
                sf.setValue(keyValue.getKey(), keyValue.getValue());
              }

            }

            al.getSequenceAt(i).getDatasetSequence().addSequenceFeature(sf);
          }
        }
        if (vamsasSeq[i].getDBRefCount() > 0)
        {
          addDBRefs(al.getSequenceAt(i).getDatasetSequence(), vamsasSeq[i]);
        }
        if (JSEQ[i].getPdbidsCount() > 0)
        {
          Pdbids[] ids = JSEQ[i].getPdbids();
          for (int p = 0; p < ids.length; p++)
          {
            jalview.datamodel.PDBEntry entry = new jalview.datamodel.PDBEntry();
            entry.setId(ids[p].getId());
            entry.setType(ids[p].getType());
            if (ids[p].getFile() != null)
            {
              if (!pdbloaded.containsKey(ids[p].getFile()))
              {
                entry.setFile(loadPDBFile(jprovider, ids[p].getId()));
              }
              else
              {
                entry.setFile(pdbloaded.get(ids[p].getId()).toString());
              }
            }

            al.getSequenceAt(i).getDatasetSequence().addPDBId(entry);
          }
        }
      }
    } // end !multipleview

    // ///////////////////////////////
    // LOAD SEQUENCE MAPPINGS

    if (vamsasSet.getAlcodonFrameCount() > 0)
    {
      // TODO Potentially this should only be done once for all views of an
      // alignment
      AlcodonFrame[] alc = vamsasSet.getAlcodonFrame();
      for (int i = 0; i < alc.length; i++)
      {
        jalview.datamodel.AlignedCodonFrame cf = new jalview.datamodel.AlignedCodonFrame(
                alc[i].getAlcodonCount());
        if (alc[i].getAlcodonCount() > 0)
        {
          Alcodon[] alcods = alc[i].getAlcodon();
          for (int p = 0; p < cf.codons.length; p++)
          {
            if (alcods[p].hasPos1() && alcods[p].hasPos2()
                    && alcods[p].hasPos3())
            {
              // translated codons require three valid positions
              cf.codons[p] = new int[3];
              cf.codons[p][0] = (int) alcods[p].getPos1();
              cf.codons[p][1] = (int) alcods[p].getPos2();
              cf.codons[p][2] = (int) alcods[p].getPos3();
            }
            else
            {
              cf.codons[p] = null;
            }
          }
        }
        if (alc[i].getAlcodMapCount() > 0)
        {
          AlcodMap[] maps = alc[i].getAlcodMap();
          for (int m = 0; m < maps.length; m++)
          {
            SequenceI dnaseq = (SequenceI) seqRefIds
                    .get(maps[m].getDnasq());
            // Load Mapping
            jalview.datamodel.Mapping mapping = null;
            // attach to dna sequence reference.
            if (maps[m].getMapping() != null)
            {
              mapping = addMapping(maps[m].getMapping());
            }
            if (dnaseq != null)
            {
              cf.addMap(dnaseq, mapping.getTo(), mapping.getMap());
            }
            else
            {
              // defer to later
              frefedSequence.add(new Object[]
              { maps[m].getDnasq(), cf, mapping });
            }
          }
        }
        al.addCodonFrame(cf);
      }

    }

    // ////////////////////////////////
    // LOAD ANNOTATIONS
    ArrayList<JvAnnotRow> autoAlan = new ArrayList<JvAnnotRow>();
    /**
     * store any annotations which forward reference a group's ID
     */
    Hashtable<String, ArrayList<jalview.datamodel.AlignmentAnnotation>> groupAnnotRefs = new Hashtable<String, ArrayList<jalview.datamodel.AlignmentAnnotation>>();

    if (vamsasSet.getAnnotationCount() > 0)
    {
      Annotation[] an = vamsasSet.getAnnotation();

      for (int i = 0; i < an.length; i++)
      {
        /**
         * test if annotation is automatically calculated for this view only
         */
        boolean autoForView = false;
        if (an[i].getLabel().equals("Quality")
                || an[i].getLabel().equals("Conservation")
                || an[i].getLabel().equals("Consensus"))
        {
          // Kludge for pre 2.5 projects which lacked the autocalculated flag
          autoForView = true;
          if (!an[i].hasAutoCalculated())
          {
            an[i].setAutoCalculated(true);
          }
        }
        if (autoForView
                || (an[i].hasAutoCalculated() && an[i].isAutoCalculated()))
        {
          // remove ID - we don't recover annotation from other views for
          // view-specific annotation
          an[i].setId(null);
        }

        // set visiblity for other annotation in this view
        if (an[i].getId() != null
                && annotationIds.containsKey(an[i].getId()))
        {
          jalview.datamodel.AlignmentAnnotation jda = (jalview.datamodel.AlignmentAnnotation) annotationIds
                  .get(an[i].getId());
          // in principle Visible should always be true for annotation displayed
          // in multiple views
          if (an[i].hasVisible())
            jda.visible = an[i].getVisible();

          al.addAnnotation(jda);

          continue;
        }
        // Construct new annotation from model.
        AnnotationElement[] ae = an[i].getAnnotationElement();
        jalview.datamodel.Annotation[] anot = null;
        java.awt.Color firstColour = null;
        int anpos;
        if (!an[i].getScoreOnly())
        {
          anot = new jalview.datamodel.Annotation[al.getWidth()];
          for (int aa = 0; aa < ae.length && aa < anot.length; aa++)
          {
            anpos = ae[aa].getPosition();

            if (anpos >= anot.length)
              continue;

            anot[anpos] = new jalview.datamodel.Annotation(

            ae[aa].getDisplayCharacter(), ae[aa].getDescription(),
                    (ae[aa].getSecondaryStructure() == null || ae[aa]
                            .getSecondaryStructure().length() == 0) ? ' '
                            : ae[aa].getSecondaryStructure().charAt(0),
                    ae[aa].getValue()

            );
            // JBPNote: Consider verifying dataflow for IO of secondary
            // structure annotation read from Stockholm files
            // this was added to try to ensure that
            // if (anot[ae[aa].getPosition()].secondaryStructure>' ')
            // {
            // anot[ae[aa].getPosition()].displayCharacter = "";
            // }
            anot[anpos].colour = new java.awt.Color(ae[aa].getColour());
            if (firstColour == null)
            {
              firstColour = anot[anpos].colour;
            }
          }
        }
        jalview.datamodel.AlignmentAnnotation jaa = null;

        if (an[i].getGraph())
        {
          float llim = 0, hlim = 0;
          // if (autoForView || an[i].isAutoCalculated()) {
          // hlim=11f;
          // }
          jaa = new jalview.datamodel.AlignmentAnnotation(an[i].getLabel(),
                  an[i].getDescription(), anot, llim, hlim,
                  an[i].getGraphType());

          jaa.graphGroup = an[i].getGraphGroup();
          jaa._linecolour = firstColour;
          if (an[i].getThresholdLine() != null)
          {
            jaa.setThreshold(new jalview.datamodel.GraphLine(an[i]
                    .getThresholdLine().getValue(), an[i]
                    .getThresholdLine().getLabel(), new java.awt.Color(
                    an[i].getThresholdLine().getColour())));

          }
          if (autoForView || an[i].isAutoCalculated())
          {
            // Hardwire the symbol display line to ensure that labels for
            // histograms are displayed
            jaa.hasText = true;
          }
        }
        else
        {
          jaa = new jalview.datamodel.AlignmentAnnotation(an[i].getLabel(),
                  an[i].getDescription(), anot);
          jaa._linecolour = firstColour;
        }
        // register new annotation
        if (an[i].getId() != null)
        {
          annotationIds.put(an[i].getId(), jaa);
          jaa.annotationId = an[i].getId();
        }
        // recover sequence association
        if (an[i].getSequenceRef() != null)
        {
          if (al.findName(an[i].getSequenceRef()) != null)
          {
            jaa.createSequenceMapping(al.findName(an[i].getSequenceRef()),
                    1, true);
            al.findName(an[i].getSequenceRef()).addAlignmentAnnotation(jaa);
          }
        }
        // and make a note of any group association
        if (an[i].getGroupRef() != null && an[i].getGroupRef().length() > 0)
        {
          ArrayList<jalview.datamodel.AlignmentAnnotation> aal = groupAnnotRefs
                  .get(an[i].getGroupRef());
          if (aal == null)
          {
            aal = new ArrayList<jalview.datamodel.AlignmentAnnotation>();
            groupAnnotRefs.put(an[i].getGroupRef(), aal);
          }
          aal.add(jaa);
        }

        if (an[i].hasScore())
        {
          jaa.setScore(an[i].getScore());
        }
        if (an[i].hasVisible())
          jaa.visible = an[i].getVisible();

        if (an[i].hasCentreColLabels())
          jaa.centreColLabels = an[i].getCentreColLabels();

        if (an[i].hasScaleColLabels())
        {
          jaa.scaleColLabel = an[i].getScaleColLabels();
        }
        if (an[i].hasAutoCalculated() && an[i].isAutoCalculated())
        {
          // newer files have an 'autoCalculated' flag and store calculation
          // state in viewport properties
          jaa.autoCalculated = true; // means annotation will be marked for
          // update at end of load.
        }
        if (an[i].hasGraphHeight())
        {
          jaa.graphHeight = an[i].getGraphHeight();
        }
        if (an[i].hasBelowAlignment())
        {
          jaa.belowAlignment = an[i].isBelowAlignment();
        }
        jaa.setCalcId(an[i].getCalcId());

        if (jaa.autoCalculated)
        {
          autoAlan.add(new JvAnnotRow(i, jaa));
        }
        else
        // if (!autoForView)
        {
          // add autocalculated group annotation and any user created annotation
          // for the view
          al.addAnnotation(jaa);
        }
      }
    }

    // ///////////////////////
    // LOAD GROUPS
    // Create alignment markup and styles for this view
    if (jms.getJGroupCount() > 0)
    {
      JGroup[] groups = jms.getJGroup();

      for (int i = 0; i < groups.length; i++)
      {
        ColourSchemeI cs = null;

        if (groups[i].getColour() != null)
        {
          if (groups[i].getColour().startsWith("ucs"))
          {
            cs = GetUserColourScheme(jms, groups[i].getColour());
          }
          else
          {
            cs = ColourSchemeProperty.getColour(al, groups[i].getColour());
          }

          if (cs != null)
          {
            cs.setThreshold(groups[i].getPidThreshold(), true);
          }
        }

        Vector seqs = new Vector();

        for (int s = 0; s < groups[i].getSeqCount(); s++)
        {
          String seqId = groups[i].getSeq(s) + "";
          jalview.datamodel.SequenceI ts = (jalview.datamodel.SequenceI) seqRefIds
                  .get(seqId);

          if (ts != null)
          {
            seqs.addElement(ts);
          }
        }

        if (seqs.size() < 1)
        {
          continue;
        }

        jalview.datamodel.SequenceGroup sg = new jalview.datamodel.SequenceGroup(
                seqs, groups[i].getName(), cs, groups[i].getDisplayBoxes(),
                groups[i].getDisplayText(), groups[i].getColourText(),
                groups[i].getStart(), groups[i].getEnd());

        sg.setOutlineColour(new java.awt.Color(groups[i].getOutlineColour()));

        sg.textColour = new java.awt.Color(groups[i].getTextCol1());
        sg.textColour2 = new java.awt.Color(groups[i].getTextCol2());
        sg.setShowNonconserved(groups[i].hasShowUnconserved() ? groups[i]
                .isShowUnconserved() : false);
        sg.thresholdTextColour = groups[i].getTextColThreshold();
        if (groups[i].hasShowConsensusHistogram())
        {
          sg.setShowConsensusHistogram(groups[i].isShowConsensusHistogram());
        }
        ;
        if (groups[i].hasShowSequenceLogo())
        {
          sg.setshowSequenceLogo(groups[i].isShowSequenceLogo());
        }
        if (groups[i].hasNormaliseSequenceLogo())
        {
          sg.setNormaliseSequenceLogo(groups[i].isNormaliseSequenceLogo());
        }
        if (groups[i].hasIgnoreGapsinConsensus())
        {
          sg.setIgnoreGapsConsensus(groups[i].getIgnoreGapsinConsensus());
        }
        if (groups[i].getConsThreshold() != 0)
        {
          jalview.analysis.Conservation c = new jalview.analysis.Conservation(
                  "All", ResidueProperties.propHash, 3,
                  sg.getSequences(null), 0, sg.getWidth() - 1);
          c.calculate();
          c.verdict(false, 25);
          sg.cs.setConservation(c);
        }

        if (groups[i].getId() != null && groupAnnotRefs.size() > 0)
        {
          // re-instate unique group/annotation row reference
          ArrayList<jalview.datamodel.AlignmentAnnotation> jaal = groupAnnotRefs
                  .get(groups[i].getId());
          if (jaal != null)
          {
            for (jalview.datamodel.AlignmentAnnotation jaa : jaal)
            {
              jaa.groupRef = sg;
              if (jaa.autoCalculated)
              {
                // match up and try to set group autocalc alignment row for this
                // annotation
                if (jaa.label.startsWith("Consensus for "))
                {
                  sg.setConsensus(jaa);
                }
                // match up and try to set group autocalc alignment row for this
                // annotation
                if (jaa.label.startsWith("Conservation for "))
                {
                  sg.setConservationRow(jaa);
                }
              }
            }
          }
        }
        al.addGroup(sg);

      }
    }

    // ///////////////////////////////
    // LOAD VIEWPORT

    // If we just load in the same jar file again, the sequenceSetId
    // will be the same, and we end up with multiple references
    // to the same sequenceSet. We must modify this id on load
    // so that each load of the file gives a unique id
    String uniqueSeqSetId = view.getSequenceSetId() + uniqueSetSuffix;
    String viewId = (view.getId() == null ? null : view.getId()
            + uniqueSetSuffix);
    AlignFrame af = null;
    AlignViewport av = null;
    // now check to see if we really need to create a new viewport.
    if (multipleView && viewportsAdded.size() == 0)
    {
      // We recovered an alignment for which a viewport already exists.
      // TODO: fix up any settings necessary for overlaying stored state onto
      // state recovered from another document. (may not be necessary).
      // we may need a binding from a viewport in memory to one recovered from
      // XML.
      // and then recover its containing af to allow the settings to be applied.
      // TODO: fix for vamsas demo
      System.err
              .println("About to recover a viewport for existing alignment: Sequence set ID is "
                      + uniqueSeqSetId);
      Object seqsetobj = retrieveExistingObj(uniqueSeqSetId);
      if (seqsetobj != null)
      {
        if (seqsetobj instanceof String)
        {
          uniqueSeqSetId = (String) seqsetobj;
          System.err
                  .println("Recovered extant sequence set ID mapping for ID : New Sequence set ID is "
                          + uniqueSeqSetId);
        }
        else
        {
          System.err
                  .println("Warning : Collision between sequence set ID string and existing jalview object mapping.");
        }

      }
    }
    AlignmentPanel ap = null;
    boolean isnewview = true;
    if (viewId != null)
    {
      // Check to see if this alignment already has a view id == viewId
      jalview.gui.AlignmentPanel views[] = Desktop
              .getAlignmentPanels(uniqueSeqSetId);
      if (views != null && views.length > 0)
      {
        for (int v = 0; v < views.length; v++)
        {
          if (views[v].av.getViewId().equalsIgnoreCase(viewId))
          {
            // recover the existing alignpanel, alignframe, viewport
            af = views[v].alignFrame;
            av = views[v].av;
            ap = views[v];
            // TODO: could even skip resetting view settings if we don't want to
            // change the local settings from other jalview processes
            isnewview = false;
          }
        }
      }
    }

    if (isnewview)
    {
      af = loadViewport(file, JSEQ, hiddenSeqs, al, jms, view,
              uniqueSeqSetId, viewId, autoAlan);
      av = af.viewport;
      ap = af.alignPanel;
    }
    // LOAD TREES
    // /////////////////////////////////////
    if (loadTreesAndStructures && jms.getTreeCount() > 0)
    {
      try
      {
        for (int t = 0; t < jms.getTreeCount(); t++)
        {

          Tree tree = jms.getTree(t);

          TreePanel tp = (TreePanel) retrieveExistingObj(tree.getId());
          if (tp == null)
          {
            tp = af.ShowNewickTree(
                    new jalview.io.NewickFile(tree.getNewick()),
                    tree.getTitle(), tree.getWidth(), tree.getHeight(),
                    tree.getXpos(), tree.getYpos());
            if (tree.getId() != null)
            {
              // perhaps bind the tree id to something ?
            }
          }
          else
          {
            // update local tree attributes ?
            // TODO: should check if tp has been manipulated by user - if so its
            // settings shouldn't be modified
            tp.setTitle(tree.getTitle());
            tp.setBounds(new Rectangle(tree.getXpos(), tree.getYpos(), tree
                    .getWidth(), tree.getHeight()));
            tp.av = av; // af.viewport; // TODO: verify 'associate with all
            // views'
            // works still
            tp.treeCanvas.av = av; // af.viewport;
            tp.treeCanvas.ap = ap; // af.alignPanel;

          }
          if (tp == null)
          {
            warn("There was a problem recovering stored Newick tree: \n"
                    + tree.getNewick());
            continue;
          }

          tp.fitToWindow.setState(tree.getFitToWindow());
          tp.fitToWindow_actionPerformed(null);

          if (tree.getFontName() != null)
          {
            tp.setTreeFont(new java.awt.Font(tree.getFontName(), tree
                    .getFontStyle(), tree.getFontSize()));
          }
          else
          {
            tp.setTreeFont(new java.awt.Font(view.getFontName(), view
                    .getFontStyle(), tree.getFontSize()));
          }

          tp.showPlaceholders(tree.getMarkUnlinked());
          tp.showBootstrap(tree.getShowBootstrap());
          tp.showDistances(tree.getShowDistances());

          tp.treeCanvas.threshold = tree.getThreshold();

          if (tree.getCurrentTree())
          {
            af.viewport.setCurrentTree(tp.getTree());
          }
        }

      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }

    // //LOAD STRUCTURES
    if (loadTreesAndStructures)
    {
      // run through all PDB ids on the alignment, and collect mappings between
      // jmol view ids and all sequences referring to it
      Hashtable<String, Object[]> jmolViewIds = new Hashtable();

      for (int i = 0; i < JSEQ.length; i++)
      {
        if (JSEQ[i].getPdbidsCount() > 0)
        {
          Pdbids[] ids = JSEQ[i].getPdbids();
          for (int p = 0; p < ids.length; p++)
          {
            for (int s = 0; s < ids[p].getStructureStateCount(); s++)
            {
              // check to see if we haven't already created this structure view
              String sviewid = (ids[p].getStructureState(s).getViewId() == null) ? null
                      : ids[p].getStructureState(s).getViewId()
                              + uniqueSetSuffix;
              jalview.datamodel.PDBEntry jpdb = new jalview.datamodel.PDBEntry();
              // Originally : ids[p].getFile()
              // : TODO: verify external PDB file recovery still works in normal
              // jalview project load
              jpdb.setFile(loadPDBFile(jprovider, ids[p].getId()));
              jpdb.setId(ids[p].getId());

              int x = ids[p].getStructureState(s).getXpos();
              int y = ids[p].getStructureState(s).getYpos();
              int width = ids[p].getStructureState(s).getWidth();
              int height = ids[p].getStructureState(s).getHeight();

              // Probably don't need to do this anymore...
              // Desktop.desktop.getComponentAt(x, y);
              // TODO: NOW: check that this recovers the PDB file correctly.
              String pdbFile = loadPDBFile(jprovider, ids[p].getId());
              jalview.datamodel.SequenceI seq = (jalview.datamodel.SequenceI) seqRefIds
                      .get(JSEQ[i].getId() + "");
              if (sviewid == null)
              {
                sviewid = "_jalview_pre2_4_" + x + "," + y + "," + width
                        + "," + height;
              }
              if (!jmolViewIds.containsKey(sviewid))
              {
                jmolViewIds.put(sviewid, new Object[]
                { new int[]
                { x, y, width, height }, "",
                    new Hashtable<String, Object[]>(), new boolean[]
                    { false, false, true } });
                // Legacy pre-2.7 conversion JAL-823 :
                // do not assume any view has to be linked for colour by
                // sequence
              }

              // assemble String[] { pdb files }, String[] { id for each
              // file }, orig_fileloc, SequenceI[][] {{ seqs_file 1 }, {
              // seqs_file 2}, boolean[] {
              // linkAlignPanel,superposeWithAlignpanel}} from hash
              Object[] jmoldat = jmolViewIds.get(sviewid);
              ((boolean[]) jmoldat[3])[0] |= ids[p].getStructureState(s)
                      .hasAlignwithAlignPanel() ? ids[p].getStructureState(
                      s).getAlignwithAlignPanel() : false;
              // never colour by linked panel if not specified
              ((boolean[]) jmoldat[3])[1] |= ids[p].getStructureState(s)
                      .hasColourwithAlignPanel() ? ids[p]
                      .getStructureState(s).getColourwithAlignPanel()
                      : false;
              // default for pre-2.7 projects is that Jmol colouring is enabled
              ((boolean[]) jmoldat[3])[2] &= ids[p].getStructureState(s)
                      .hasColourByJmol() ? ids[p].getStructureState(s)
                      .getColourByJmol() : true;

              if (((String) jmoldat[1]).length() < ids[p]
                      .getStructureState(s).getContent().length())
              {
                {
                  jmoldat[1] = ids[p].getStructureState(s).getContent();
                }
              }
              if (ids[p].getFile() != null)
              {
                File mapkey = new File(ids[p].getFile());
                Object[] seqstrmaps = (Object[]) ((Hashtable) jmoldat[2])
                        .get(mapkey);
                if (seqstrmaps == null)
                {
                  ((Hashtable) jmoldat[2]).put(mapkey,
                          seqstrmaps = new Object[]
                          { pdbFile, ids[p].getId(), new Vector(),
                              new Vector() });
                }
                if (!((Vector) seqstrmaps[2]).contains(seq))
                {
                  ((Vector) seqstrmaps[2]).addElement(seq);
                  // ((Vector)seqstrmaps[3]).addElement(n) :
                  // in principle, chains
                  // should be stored here : do we need to
                  // TODO: store and recover seq/pdb_id :
                  // chain mappings
                }
              }
              else
              {
                errorMessage = ("The Jmol views in this project were imported\nfrom an older version of Jalview.\nPlease review the sequence colour associations\nin the Colour by section of the Jmol View menu.\n\nIn the case of problems, see note at\nhttp://issues.jalview.org/browse/JAL-747");
                warn(errorMessage);
              }
            }
          }
        }
      }
      {

        // Instantiate the associated Jmol views
        for (Entry<String, Object[]> entry : jmolViewIds.entrySet())
        {
          String sviewid = entry.getKey();
          Object[] svattrib = entry.getValue();
          int[] geom = (int[]) svattrib[0];
          String state = (String) svattrib[1];
          Hashtable<File, Object[]> oldFiles = (Hashtable<File, Object[]>) svattrib[2];
          final boolean useinJmolsuperpos = ((boolean[]) svattrib[3])[0], usetoColourbyseq = ((boolean[]) svattrib[3])[1], jmolColouring = ((boolean[]) svattrib[3])[2];
          int x = geom[0], y = geom[1], width = geom[2], height = geom[3];
          // collate the pdbfile -> sequence mappings from this view
          Vector<String> pdbfilenames = new Vector<String>();
          Vector<SequenceI[]> seqmaps = new Vector<SequenceI[]>();
          Vector<String> pdbids = new Vector<String>();

          // Search to see if we've already created this Jmol view
          AppJmol comp = null;
          JInternalFrame[] frames = null;
          do
          {
            try
            {
              frames = Desktop.desktop.getAllFrames();
            } catch (ArrayIndexOutOfBoundsException e)
            {
              // occasional No such child exceptions are thrown here...
              frames = null;
              try
              {
                Thread.sleep(10);
              } catch (Exception f)
              {
              }
              ;
            }
          } while (frames == null);
          // search for any Jmol windows already open from other
          // alignment views that exactly match the stored structure state
          for (int f = 0; comp == null && f < frames.length; f++)
          {
            if (frames[f] instanceof AppJmol)
            {
              if (sviewid != null
                      && ((AppJmol) frames[f]).getViewId().equals(sviewid))
              {
                // post jalview 2.4 schema includes structure view id
                comp = (AppJmol) frames[f];
              }
              else if (frames[f].getX() == x && frames[f].getY() == y
                      && frames[f].getHeight() == height
                      && frames[f].getWidth() == width)
              {
                comp = (AppJmol) frames[f];
              }
            }
          }

          if (comp == null)
          {
            // create a new Jmol window.
            // First parse the Jmol state to translate filenames loaded into the
            // view, and record the order in which files are shown in the Jmol
            // view, so we can add the sequence mappings in same order.
            StringBuffer newFileLoc = null;
            int cp = 0, ncp, ecp;
            while ((ncp = state.indexOf("load ", cp)) > -1)
            {
              if (newFileLoc == null)
              {
                newFileLoc = new StringBuffer();
              }
              do
              {
                // look for next filename in load statement
                newFileLoc.append(state.substring(cp,
                        ncp = (state.indexOf("\"", ncp + 1) + 1)));
                String oldfilenam = state.substring(ncp,
                        ecp = state.indexOf("\"", ncp));
                // recover the new mapping data for this old filename
                // have to normalize filename - since Jmol and jalview do
                // filename
                // translation differently.
                Object[] filedat = oldFiles.get(new File(oldfilenam));
                newFileLoc.append(Platform
                        .escapeString((String) filedat[0]));
                pdbfilenames.addElement((String) filedat[0]);
                pdbids.addElement((String) filedat[1]);
                seqmaps.addElement(((Vector<SequenceI>) filedat[2])
                        .toArray(new SequenceI[0]));
                newFileLoc.append("\"");
                cp = ecp + 1; // advance beyond last \" and set cursor so we can
                              // look for next file statement.
              } while ((ncp = state.indexOf("/*file*/", cp)) > -1);
            }
            if (cp > 0)
            {
              // just append rest of state
              newFileLoc.append(state.substring(cp));
            }
            else
            {
              System.err
                      .print("Ignoring incomplete Jmol state for PDB ids: ");
              newFileLoc = new StringBuffer(state);
              newFileLoc.append("; load append ");
              for (File id : oldFiles.keySet())
              {
                // add this and any other pdb files that should be present in
                // the viewer
                Object[] filedat = oldFiles.get(id);
                String nfilename;
                newFileLoc.append(((String) filedat[0]));
                pdbfilenames.addElement((String) filedat[0]);
                pdbids.addElement((String) filedat[1]);
                seqmaps.addElement(((Vector<SequenceI>) filedat[2])
                        .toArray(new SequenceI[0]));
                newFileLoc.append(" \"");
                newFileLoc.append((String) filedat[0]);
                newFileLoc.append("\"");

              }
              newFileLoc.append(";");
            }

            if (newFileLoc != null)
            {
              int histbug = newFileLoc.indexOf("history = ");
              histbug += 10;
              int diff = histbug == -1 ? -1 : newFileLoc.indexOf(";",
                      histbug);
              String val = (diff == -1) ? null : newFileLoc.substring(
                      histbug, diff);
              if (val != null && val.length() >= 4)
              {
                if (val.contains("e"))
                {
                  if (val.trim().equals("true"))
                  {
                    val = "1";
                  }
                  else
                  {
                    val = "0";
                  }
                  newFileLoc.replace(histbug, diff, val);
                }
              }
              // TODO: assemble String[] { pdb files }, String[] { id for each
              // file }, orig_fileloc, SequenceI[][] {{ seqs_file 1 }, {
              // seqs_file 2}} from hash
              final String[] pdbf = pdbfilenames
                      .toArray(new String[pdbfilenames.size()]), id = pdbids
                      .toArray(new String[pdbids.size()]);
              final SequenceI[][] sq = seqmaps
                      .toArray(new SequenceI[seqmaps.size()][]);
              final String fileloc = newFileLoc.toString(), vid = sviewid;
              final AlignFrame alf = af;
              final java.awt.Rectangle rect = new java.awt.Rectangle(x, y,
                      width, height);
              try
              {
                javax.swing.SwingUtilities.invokeAndWait(new Runnable()
                {
                  @Override
                  public void run()
                  {
                    AppJmol sview = null;
                    try
                    {
                      sview = new AppJmol(pdbf, id, sq, alf.alignPanel,
                              useinJmolsuperpos, usetoColourbyseq,
                              jmolColouring, fileloc, rect, vid);
                      addNewStructureViewer(sview);
                    } catch (OutOfMemoryError ex)
                    {
                      new OOMWarning("restoring structure view for PDB id "
                              + id, (OutOfMemoryError) ex.getCause());
                      if (sview != null && sview.isVisible())
                      {
                        sview.closeViewer();
                        sview.setVisible(false);
                        sview.dispose();
                      }
                    }
                  }
                });
              } catch (InvocationTargetException ex)
              {
                warn("Unexpected error when opening Jmol view.", ex);

              } catch (InterruptedException e)
              {
                // e.printStackTrace();
              }
            }

          }
          else
          // if (comp != null)
          {
            // NOTE: if the jalview project is part of a shared session then
            // view synchronization should/could be done here.

            // add mapping for sequences in this view to an already open Jmol
            // instance
            for (File id : oldFiles.keySet())
            {
              // add this and any other pdb files that should be present in the
              // viewer
              Object[] filedat = oldFiles.get(id);
              String pdbFile = (String) filedat[0];
              SequenceI[] seq = ((Vector<SequenceI>) filedat[2])
                      .toArray(new SequenceI[0]);
              comp.jmb.ssm.setMapping(seq, null, pdbFile,
                      jalview.io.AppletFormatAdapter.FILE);
              comp.jmb.addSequenceForStructFile(pdbFile, seq);
            }
            // and add the AlignmentPanel's reference to the Jmol view
            comp.addAlignmentPanel(ap);
            if (useinJmolsuperpos)
            {
              comp.useAlignmentPanelForSuperposition(ap);
            }
            else
            {
              comp.excludeAlignmentPanelForSuperposition(ap);
            }
            if (usetoColourbyseq)
            {
              comp.useAlignmentPanelForColourbyseq(ap, !jmolColouring);
            }
            else
            {
              comp.excludeAlignmentPanelForColourbyseq(ap);
            }
          }
        }
      }
    }
    // and finally return.
    return af;
  }
  Vector<AppJmol> newStructureViewers=null;
  protected void addNewStructureViewer(AppJmol sview)
  {
    if (newStructureViewers!=null)
    {
      sview.jmb.setFinishedLoadingFromArchive(false);
      newStructureViewers.add(sview);
    }
  }
  protected void setLoadingFinishedForNewStructureViewers()
  {
    if (newStructureViewers!=null)
    {
      for (AppJmol sview:newStructureViewers)
      {
        sview.jmb.setFinishedLoadingFromArchive(true);
      }
      newStructureViewers.clear();
      newStructureViewers=null;
    }
  }

  AlignFrame loadViewport(String file, JSeq[] JSEQ, Vector hiddenSeqs,
          Alignment al, JalviewModelSequence jms, Viewport view,
          String uniqueSeqSetId, String viewId,
          ArrayList<JvAnnotRow> autoAlan)
  {
    AlignFrame af = null;
    af = new AlignFrame(al, view.getWidth(), view.getHeight(),
            uniqueSeqSetId, viewId);

    af.setFileName(file, "Jalview");

    for (int i = 0; i < JSEQ.length; i++)
    {
      af.viewport.setSequenceColour(af.viewport.getAlignment()
              .getSequenceAt(i), new java.awt.Color(JSEQ[i].getColour()));
    }

    af.viewport.gatherViewsHere = view.getGatheredViews();

    if (view.getSequenceSetId() != null)
    {
      jalview.gui.AlignViewport av = (jalview.gui.AlignViewport) viewportsAdded
              .get(uniqueSeqSetId);

      af.viewport.setSequenceSetId(uniqueSeqSetId);
      if (av != null)
      {
        // propagate shared settings to this new view
        af.viewport.historyList = av.historyList;
        af.viewport.redoList = av.redoList;
      }
      else
      {
        viewportsAdded.put(uniqueSeqSetId, af.viewport);
      }
      // TODO: check if this method can be called repeatedly without
      // side-effects if alignpanel already registered.
      PaintRefresher.Register(af.alignPanel, uniqueSeqSetId);
    }
    // apply Hidden regions to view.
    if (hiddenSeqs != null)
    {
      for (int s = 0; s < JSEQ.length; s++)
      {
        jalview.datamodel.SequenceGroup hidden = new jalview.datamodel.SequenceGroup();

        for (int r = 0; r < JSEQ[s].getHiddenSequencesCount(); r++)
        {
          hidden.addSequence(
                  al.getSequenceAt(JSEQ[s].getHiddenSequences(r)), false);
        }
        af.viewport.hideRepSequences(al.getSequenceAt(s), hidden);
      }

      jalview.datamodel.SequenceI[] hseqs = new jalview.datamodel.SequenceI[hiddenSeqs
              .size()];

      for (int s = 0; s < hiddenSeqs.size(); s++)
      {
        hseqs[s] = (jalview.datamodel.SequenceI) hiddenSeqs.elementAt(s);
      }

      af.viewport.hideSequence(hseqs);

    }
    // recover view properties and display parameters
    if (view.getViewName() != null)
    {
      af.viewport.viewName = view.getViewName();
      af.setInitialTabVisible();
    }
    af.setBounds(view.getXpos(), view.getYpos(), view.getWidth(),
            view.getHeight());

    af.viewport.setShowAnnotation(view.getShowAnnotation());
    af.viewport.setAbovePIDThreshold(view.getPidSelected());

    af.viewport.setColourText(view.getShowColourText());

    af.viewport.setConservationSelected(view.getConservationSelected());
    af.viewport.setShowJVSuffix(view.getShowFullId());
    af.viewport.rightAlignIds = view.getRightAlignIds();
    af.viewport.setFont(new java.awt.Font(view.getFontName(), view
            .getFontStyle(), view.getFontSize()));
    af.alignPanel.fontChanged();
    af.viewport.setRenderGaps(view.getRenderGaps());
    af.viewport.setWrapAlignment(view.getWrapAlignment());
    af.alignPanel.setWrapAlignment(view.getWrapAlignment());
    af.viewport.setShowAnnotation(view.getShowAnnotation());
    af.alignPanel.setAnnotationVisible(view.getShowAnnotation());

    af.viewport.setShowBoxes(view.getShowBoxes());

    af.viewport.setShowText(view.getShowText());

    af.viewport.textColour = new java.awt.Color(view.getTextCol1());
    af.viewport.textColour2 = new java.awt.Color(view.getTextCol2());
    af.viewport.thresholdTextColour = view.getTextColThreshold();
    af.viewport.setShowUnconserved(view.hasShowUnconserved() ? view
            .isShowUnconserved() : false);
    af.viewport.setStartRes(view.getStartRes());
    af.viewport.setStartSeq(view.getStartSeq());

    ColourSchemeI cs = null;
    // apply colourschemes
    if (view.getBgColour() != null)
    {
      if (view.getBgColour().startsWith("ucs"))
      {
        cs = GetUserColourScheme(jms, view.getBgColour());
      }
      else if (view.getBgColour().startsWith("Annotation"))
      {
        // int find annotation
        if (af.viewport.getAlignment().getAlignmentAnnotation() != null)
        {
          for (int i = 0; i < af.viewport.getAlignment()
                  .getAlignmentAnnotation().length; i++)
          {
            if (af.viewport.getAlignment().getAlignmentAnnotation()[i].label
                    .equals(view.getAnnotationColours().getAnnotation()))
            {
              if (af.viewport.getAlignment().getAlignmentAnnotation()[i]
                      .getThreshold() == null)
              {
                af.viewport.getAlignment().getAlignmentAnnotation()[i]
                        .setThreshold(new jalview.datamodel.GraphLine(view
                                .getAnnotationColours().getThreshold(),
                                "Threshold", java.awt.Color.black)

                        );
              }

              if (view.getAnnotationColours().getColourScheme()
                      .equals("None"))
              {
                cs = new AnnotationColourGradient(af.viewport
                        .getAlignment().getAlignmentAnnotation()[i],
                        new java.awt.Color(view.getAnnotationColours()
                                .getMinColour()), new java.awt.Color(view
                                .getAnnotationColours().getMaxColour()),
                        view.getAnnotationColours().getAboveThreshold());
              }
              else if (view.getAnnotationColours().getColourScheme()
                      .startsWith("ucs"))
              {
                cs = new AnnotationColourGradient(af.viewport
                        .getAlignment().getAlignmentAnnotation()[i],
                        GetUserColourScheme(jms, view
                                .getAnnotationColours().getColourScheme()),
                        view.getAnnotationColours().getAboveThreshold());
              }
              else
              {
                cs = new AnnotationColourGradient(af.viewport
                        .getAlignment().getAlignmentAnnotation()[i],
                        ColourSchemeProperty.getColour(al, view
                                .getAnnotationColours().getColourScheme()),
                        view.getAnnotationColours().getAboveThreshold());
              }

              // Also use these settings for all the groups
              if (al.getGroups() != null)
              {
                for (int g = 0; g < al.getGroups().size(); g++)
                {
                  jalview.datamodel.SequenceGroup sg = al.getGroups()
                          .get(g);

                  if (sg.cs == null)
                  {
                    continue;
                  }

                  /*
                   * if
                   * (view.getAnnotationColours().getColourScheme().equals("None"
                   * )) { sg.cs = new AnnotationColourGradient(
                   * af.viewport.getAlignment().getAlignmentAnnotation()[i], new
                   * java.awt.Color(view.getAnnotationColours().
                   * getMinColour()), new
                   * java.awt.Color(view.getAnnotationColours().
                   * getMaxColour()),
                   * view.getAnnotationColours().getAboveThreshold()); } else
                   */
                  {
                    sg.cs = new AnnotationColourGradient(af.viewport
                            .getAlignment().getAlignmentAnnotation()[i],
                            sg.cs, view.getAnnotationColours()
                                    .getAboveThreshold());
                  }

                }
              }

              break;
            }

          }
        }
      }
      else
      {
        cs = ColourSchemeProperty.getColour(al, view.getBgColour());
      }

      if (cs != null)
      {
        cs.setThreshold(view.getPidThreshold(), true);
        cs.setConsensus(af.viewport.getSequenceConsensusHash());
      }
    }

    af.viewport.setGlobalColourScheme(cs);
    af.viewport.setColourAppliesToAllGroups(false);

    if (view.getConservationSelected() && cs != null)
    {
      cs.setConservationInc(view.getConsThreshold());
    }

    af.changeColour(cs);

    af.viewport.setColourAppliesToAllGroups(true);

    if (view.getShowSequenceFeatures())
    {
      af.viewport.showSequenceFeatures = true;
    }
    if (view.hasCentreColumnLabels())
    {
      af.viewport.setCentreColumnLabels(view.getCentreColumnLabels());
    }
    if (view.hasIgnoreGapsinConsensus())
    {
      af.viewport.setIgnoreGapsConsensus(view.getIgnoreGapsinConsensus(),
              null);
    }
    if (view.hasFollowHighlight())
    {
      af.viewport.followHighlight = view.getFollowHighlight();
    }
    if (view.hasFollowSelection())
    {
      af.viewport.followSelection = view.getFollowSelection();
    }
    if (view.hasShowConsensusHistogram())
    {
      af.viewport.setShowConsensusHistogram(view
              .getShowConsensusHistogram());
    }
    else
    {
      af.viewport.setShowConsensusHistogram(true);
    }
    if (view.hasShowSequenceLogo())
    {
      af.viewport.setShowSequenceLogo(view.getShowSequenceLogo());
    }
    else
    {
      af.viewport.setShowSequenceLogo(false);
    }
    if (view.hasNormaliseSequenceLogo())
    {
      af.viewport.setNormaliseSequenceLogo(view.getNormaliseSequenceLogo());
    }
    if (view.hasShowDbRefTooltip())
    {
      af.viewport.setShowDbRefs(view.getShowDbRefTooltip());
    }
    if (view.hasShowNPfeatureTooltip())
    {
      af.viewport.setShowNpFeats(view.hasShowNPfeatureTooltip());
    }
    if (view.hasShowGroupConsensus())
    {
      af.viewport.setShowGroupConsensus(view.getShowGroupConsensus());
    }
    else
    {
      af.viewport.setShowGroupConsensus(false);
    }
    if (view.hasShowGroupConservation())
    {
      af.viewport.setShowGroupConservation(view.getShowGroupConservation());
    }
    else
    {
      af.viewport.setShowGroupConservation(false);
    }

    // recover featre settings
    if (jms.getFeatureSettings() != null)
    {
      af.viewport.featuresDisplayed = new Hashtable();
      String[] renderOrder = new String[jms.getFeatureSettings()
              .getSettingCount()];
      for (int fs = 0; fs < jms.getFeatureSettings().getSettingCount(); fs++)
      {
        Setting setting = jms.getFeatureSettings().getSetting(fs);
        if (setting.hasMincolour())
        {
          GraduatedColor gc = setting.hasMin() ? new GraduatedColor(
                  new java.awt.Color(setting.getMincolour()),
                  new java.awt.Color(setting.getColour()),
                  setting.getMin(), setting.getMax()) : new GraduatedColor(
                  new java.awt.Color(setting.getMincolour()),
                  new java.awt.Color(setting.getColour()), 0, 1);
          if (setting.hasThreshold())
          {
            gc.setThresh(setting.getThreshold());
            gc.setThreshType(setting.getThreshstate());
          }
          gc.setAutoScaled(true); // default
          if (setting.hasAutoScale())
          {
            gc.setAutoScaled(setting.getAutoScale());
          }
          if (setting.hasColourByLabel())
          {
            gc.setColourByLabel(setting.getColourByLabel());
          }
          // and put in the feature colour table.
          af.alignPanel.seqPanel.seqCanvas.getFeatureRenderer().setColour(
                  setting.getType(), gc);
        }
        else
        {
          af.alignPanel.seqPanel.seqCanvas.getFeatureRenderer().setColour(
                  setting.getType(),
                  new java.awt.Color(setting.getColour()));
        }
        renderOrder[fs] = setting.getType();
        if (setting.hasOrder())
          af.alignPanel.seqPanel.seqCanvas.getFeatureRenderer().setOrder(
                  setting.getType(), setting.getOrder());
        else
          af.alignPanel.seqPanel.seqCanvas.getFeatureRenderer().setOrder(
                  setting.getType(),
                  fs / jms.getFeatureSettings().getSettingCount());
        if (setting.getDisplay())
        {
          af.viewport.featuresDisplayed.put(setting.getType(), new Integer(
                  setting.getColour()));
        }
      }
      af.alignPanel.seqPanel.seqCanvas.getFeatureRenderer().renderOrder = renderOrder;
      Hashtable fgtable;
      af.alignPanel.seqPanel.seqCanvas.getFeatureRenderer().featureGroups = fgtable = new Hashtable();
      for (int gs = 0; gs < jms.getFeatureSettings().getGroupCount(); gs++)
      {
        Group grp = jms.getFeatureSettings().getGroup(gs);
        fgtable.put(grp.getName(), new Boolean(grp.getDisplay()));
      }
    }

    if (view.getHiddenColumnsCount() > 0)
    {
      for (int c = 0; c < view.getHiddenColumnsCount(); c++)
      {
        af.viewport.hideColumns(view.getHiddenColumns(c).getStart(), view
                .getHiddenColumns(c).getEnd() // +1
                );
      }
    }
    if (view.getCalcIdParam() != null)
    {
      for (CalcIdParam calcIdParam : view.getCalcIdParam())
      {
        if (calcIdParam != null)
        {
          if (recoverCalcIdParam(calcIdParam, af.viewport))
          {
          }
          else
          {
            warn("Couldn't recover parameters for "
                    + calcIdParam.getCalcId());
          }
        }
      }
    }
    af.setMenusFromViewport(af.viewport);
    // TODO: we don't need to do this if the viewport is aready visible.
    Desktop.addInternalFrame(af, view.getTitle(), view.getWidth(),
            view.getHeight());
    af.alignPanel.updateAnnotation(false, true); // recompute any autoannotation
    reorderAutoannotation(af, al, autoAlan);
    return af;
  }

  private void reorderAutoannotation(AlignFrame af, Alignment al,
          ArrayList<JvAnnotRow> autoAlan)
  {
    // copy over visualization settings for autocalculated annotation in the
    // view
    if (al.getAlignmentAnnotation() != null)
    {
      /**
       * Kludge for magic autoannotation names (see JAL-811)
       */
      String[] magicNames = new String[]
      { "Consensus", "Quality", "Conservation" };
      JvAnnotRow nullAnnot = new JvAnnotRow(-1, null);
      Hashtable<String, JvAnnotRow> visan = new Hashtable<String, JvAnnotRow>();
      for (String nm : magicNames)
      {
        visan.put(nm, nullAnnot);
      }
      for (JvAnnotRow auan : autoAlan)
      {
        visan.put(auan.template.label
                + (auan.template.getCalcId() == null ? "" : "\t"
                        + auan.template.getCalcId()), auan);
      }
      int hSize = al.getAlignmentAnnotation().length;
      ArrayList<JvAnnotRow> reorder = new ArrayList<JvAnnotRow>();
      // work through any autoCalculated annotation already on the view
      // removing it if it should be placed in a different location on the
      // annotation panel.
      List<String> remains = new ArrayList(visan.keySet());
      for (int h = 0; h < hSize; h++)
      {
        jalview.datamodel.AlignmentAnnotation jalan = al
                .getAlignmentAnnotation()[h];
        if (jalan.autoCalculated)
        {
          String k;
          JvAnnotRow valan = visan.get(k = jalan.label);
          if (jalan.getCalcId() != null)
          {
            valan = visan.get(k = jalan.label + "\t" + jalan.getCalcId());
          }

          if (valan != null)
          {
            // delete the auto calculated row from the alignment
            al.deleteAnnotation(jalan, false);
            remains.remove(k);
            hSize--;
            h--;
            if (valan != nullAnnot)
            {
              if (jalan != valan.template)
              {
                // newly created autoannotation row instance
                // so keep a reference to the visible annotation row
                // and copy over all relevant attributes
                if (valan.template.graphHeight >= 0)

                {
                  jalan.graphHeight = valan.template.graphHeight;
                }
                jalan.visible = valan.template.visible;
              }
              reorder.add(new JvAnnotRow(valan.order, jalan));
            }
          }
        }
      }
      // Add any (possibly stale) autocalculated rows that were not appended to
      // the view during construction
      for (String other : remains)
      {
        JvAnnotRow othera = visan.get(other);
        if (othera != nullAnnot && othera.template.getCalcId() != null
                && othera.template.getCalcId().length() > 0)
        {
          reorder.add(othera);
        }
      }
      // now put the automatic annotation in its correct place
      int s = 0, srt[] = new int[reorder.size()];
      JvAnnotRow[] rws = new JvAnnotRow[reorder.size()];
      for (JvAnnotRow jvar : reorder)
      {
        rws[s] = jvar;
        srt[s++] = jvar.order;
      }
      reorder.clear();
      jalview.util.QuickSort.sort(srt, rws);
      // and re-insert the annotation at its correct position
      for (JvAnnotRow jvar : rws)
      {
        al.addAnnotation(jvar.template, jvar.order);
      }
      af.alignPanel.adjustAnnotationHeight();
    }
  }

  Hashtable skipList = null;

  /**
   * TODO remove this method
   * 
   * @param view
   * @return AlignFrame bound to sequenceSetId from view, if one exists. private
   *         AlignFrame getSkippedFrame(Viewport view) { if (skipList==null) {
   *         throw new Error("Implementation Error. No skipList defined for this
   *         Jalview2XML instance."); } return (AlignFrame)
   *         skipList.get(view.getSequenceSetId()); }
   */

  /**
   * Check if the Jalview view contained in object should be skipped or not.
   * 
   * @param object
   * @return true if view's sequenceSetId is a key in skipList
   */
  private boolean skipViewport(JalviewModel object)
  {
    if (skipList == null)
    {
      return false;
    }
    String id;
    if (skipList.containsKey(id = object.getJalviewModelSequence()
            .getViewport()[0].getSequenceSetId()))
    {
      if (Cache.log != null && Cache.log.isDebugEnabled())
      {
        Cache.log.debug("Skipping seuqence set id " + id);
      }
      return true;
    }
    return false;
  }

  public void AddToSkipList(AlignFrame af)
  {
    if (skipList == null)
    {
      skipList = new Hashtable();
    }
    skipList.put(af.getViewport().getSequenceSetId(), af);
  }

  public void clearSkipList()
  {
    if (skipList != null)
    {
      skipList.clear();
      skipList = null;
    }
  }

  private void recoverDatasetFor(SequenceSet vamsasSet, Alignment al)
  {
    jalview.datamodel.Alignment ds = getDatasetFor(vamsasSet.getDatasetId());
    Vector dseqs = null;
    if (ds == null)
    {
      // create a list of new dataset sequences
      dseqs = new Vector();
    }
    for (int i = 0, iSize = vamsasSet.getSequenceCount(); i < iSize; i++)
    {
      Sequence vamsasSeq = vamsasSet.getSequence(i);
      ensureJalviewDatasetSequence(vamsasSeq, ds, dseqs);
    }
    // create a new dataset
    if (ds == null)
    {
      SequenceI[] dsseqs = new SequenceI[dseqs.size()];
      dseqs.copyInto(dsseqs);
      ds = new jalview.datamodel.Alignment(dsseqs);
      debug("Created new dataset " + vamsasSet.getDatasetId()
              + " for alignment " + System.identityHashCode(al));
      addDatasetRef(vamsasSet.getDatasetId(), ds);
    }
    // set the dataset for the newly imported alignment.
    if (al.getDataset() == null)
    {
      al.setDataset(ds);
    }
  }

  /**
   * 
   * @param vamsasSeq
   *          sequence definition to create/merge dataset sequence for
   * @param ds
   *          dataset alignment
   * @param dseqs
   *          vector to add new dataset sequence to
   */
  private void ensureJalviewDatasetSequence(Sequence vamsasSeq,
          AlignmentI ds, Vector dseqs)
  {
    // JBP TODO: Check this is called for AlCodonFrames to support recovery of
    // xRef Codon Maps
    jalview.datamodel.Sequence sq = (jalview.datamodel.Sequence) seqRefIds
            .get(vamsasSeq.getId());
    jalview.datamodel.SequenceI dsq = null;
    if (sq != null && sq.getDatasetSequence() != null)
    {
      dsq = sq.getDatasetSequence();
    }

    String sqid = vamsasSeq.getDsseqid();
    if (dsq == null)
    {
      // need to create or add a new dataset sequence reference to this sequence
      if (sqid != null)
      {
        dsq = (jalview.datamodel.SequenceI) seqRefIds.get(sqid);
      }
      // check again
      if (dsq == null)
      {
        // make a new dataset sequence
        dsq = sq.createDatasetSequence();
        if (sqid == null)
        {
          // make up a new dataset reference for this sequence
          sqid = seqHash(dsq);
        }
        dsq.setVamsasId(uniqueSetSuffix + sqid);
        seqRefIds.put(sqid, dsq);
        if (ds == null)
        {
          if (dseqs != null)
          {
            dseqs.addElement(dsq);
          }
        }
        else
        {
          ds.addSequence(dsq);
        }
      }
      else
      {
        if (sq != dsq)
        { // make this dataset sequence sq's dataset sequence
          sq.setDatasetSequence(dsq);
        }
      }
    }
    // TODO: refactor this as a merge dataset sequence function
    // now check that sq (the dataset sequence) sequence really is the union of
    // all references to it
    // boolean pre = sq.getStart() < dsq.getStart();
    // boolean post = sq.getEnd() > dsq.getEnd();
    // if (pre || post)
    if (sq != dsq)
    {
      StringBuffer sb = new StringBuffer();
      String newres = jalview.analysis.AlignSeq.extractGaps(
              jalview.util.Comparison.GapChars, sq.getSequenceAsString());
      if (!newres.equalsIgnoreCase(dsq.getSequenceAsString())
              && newres.length() > dsq.getLength())
      {
        // Update with the longer sequence.
        synchronized (dsq)
        {
          /*
           * if (pre) { sb.insert(0, newres .substring(0, dsq.getStart() -
           * sq.getStart())); dsq.setStart(sq.getStart()); } if (post) {
           * sb.append(newres.substring(newres.length() - sq.getEnd() -
           * dsq.getEnd())); dsq.setEnd(sq.getEnd()); }
           */
          dsq.setSequence(sb.toString());
        }
        // TODO: merges will never happen if we 'know' we have the real dataset
        // sequence - this should be detected when id==dssid
        System.err.println("DEBUG Notice:  Merged dataset sequence"); // ("
        // + (pre ? "prepended" : "") + " "
        // + (post ? "appended" : ""));
      }
    }
  }

  java.util.Hashtable datasetIds = null;

  java.util.IdentityHashMap dataset2Ids = null;

  private Alignment getDatasetFor(String datasetId)
  {
    if (datasetIds == null)
    {
      datasetIds = new Hashtable();
      return null;
    }
    if (datasetIds.containsKey(datasetId))
    {
      return (Alignment) datasetIds.get(datasetId);
    }
    return null;
  }

  private void addDatasetRef(String datasetId, Alignment dataset)
  {
    if (datasetIds == null)
    {
      datasetIds = new Hashtable();
    }
    datasetIds.put(datasetId, dataset);
  }

  /**
   * make a new dataset ID for this jalview dataset alignment
   * 
   * @param dataset
   * @return
   */
  private String getDatasetIdRef(jalview.datamodel.Alignment dataset)
  {
    if (dataset.getDataset() != null)
    {
      warn("Serious issue!  Dataset Object passed to getDatasetIdRef is not a Jalview DATASET alignment...");
    }
    String datasetId = makeHashCode(dataset, null);
    if (datasetId == null)
    {
      // make a new datasetId and record it
      if (dataset2Ids == null)
      {
        dataset2Ids = new IdentityHashMap();
      }
      else
      {
        datasetId = (String) dataset2Ids.get(dataset);
      }
      if (datasetId == null)
      {
        datasetId = "ds" + dataset2Ids.size() + 1;
        dataset2Ids.put(dataset, datasetId);
      }
    }
    return datasetId;
  }

  private void addDBRefs(SequenceI datasetSequence, Sequence sequence)
  {
    for (int d = 0; d < sequence.getDBRefCount(); d++)
    {
      DBRef dr = sequence.getDBRef(d);
      jalview.datamodel.DBRefEntry entry = new jalview.datamodel.DBRefEntry(
              sequence.getDBRef(d).getSource(), sequence.getDBRef(d)
                      .getVersion(), sequence.getDBRef(d).getAccessionId());
      if (dr.getMapping() != null)
      {
        entry.setMap(addMapping(dr.getMapping()));
      }
      datasetSequence.addDBRef(entry);
    }
  }

  private jalview.datamodel.Mapping addMapping(Mapping m)
  {
    SequenceI dsto = null;
    // Mapping m = dr.getMapping();
    int fr[] = new int[m.getMapListFromCount() * 2];
    Enumeration f = m.enumerateMapListFrom();
    for (int _i = 0; f.hasMoreElements(); _i += 2)
    {
      MapListFrom mf = (MapListFrom) f.nextElement();
      fr[_i] = mf.getStart();
      fr[_i + 1] = mf.getEnd();
    }
    int fto[] = new int[m.getMapListToCount() * 2];
    f = m.enumerateMapListTo();
    for (int _i = 0; f.hasMoreElements(); _i += 2)
    {
      MapListTo mf = (MapListTo) f.nextElement();
      fto[_i] = mf.getStart();
      fto[_i + 1] = mf.getEnd();
    }
    jalview.datamodel.Mapping jmap = new jalview.datamodel.Mapping(dsto,
            fr, fto, (int) m.getMapFromUnit(), (int) m.getMapToUnit());
    if (m.getMappingChoice() != null)
    {
      MappingChoice mc = m.getMappingChoice();
      if (mc.getDseqFor() != null)
      {
        String dsfor = "" + mc.getDseqFor();
        if (seqRefIds.containsKey(dsfor))
        {
          /**
           * recover from hash
           */
          jmap.setTo((SequenceI) seqRefIds.get(dsfor));
        }
        else
        {
          frefedSequence.add(new Object[]
          { dsfor, jmap });
        }
      }
      else
      {
        /**
         * local sequence definition
         */
        Sequence ms = mc.getSequence();
        jalview.datamodel.Sequence djs = null;
        String sqid = ms.getDsseqid();
        if (sqid != null && sqid.length() > 0)
        {
          /*
           * recover dataset sequence
           */
          djs = (jalview.datamodel.Sequence) seqRefIds.get(sqid);
        }
        else
        {
          System.err
                  .println("Warning - making up dataset sequence id for DbRef sequence map reference");
          sqid = ((Object) ms).toString(); // make up a new hascode for
          // undefined dataset sequence hash
          // (unlikely to happen)
        }

        if (djs == null)
        {
          /**
           * make a new dataset sequence and add it to refIds hash
           */
          djs = new jalview.datamodel.Sequence(ms.getName(),
                  ms.getSequence());
          djs.setStart(jmap.getMap().getToLowest());
          djs.setEnd(jmap.getMap().getToHighest());
          djs.setVamsasId(uniqueSetSuffix + sqid);
          jmap.setTo(djs);
          seqRefIds.put(sqid, djs);

        }
        jalview.bin.Cache.log.debug("about to recurse on addDBRefs.");
        addDBRefs(djs, ms);

      }
    }
    return (jmap);

  }

  public jalview.gui.AlignmentPanel copyAlignPanel(AlignmentPanel ap,
          boolean keepSeqRefs)
  {
    initSeqRefs();
    jalview.schemabinding.version2.JalviewModel jm = SaveState(ap, null,
            null);

    if (!keepSeqRefs)
    {
      clearSeqRefs();
      jm.getJalviewModelSequence().getViewport(0).setSequenceSetId(null);
    }
    else
    {
      uniqueSetSuffix = "";
      jm.getJalviewModelSequence().getViewport(0).setId(null); // we don't
      // overwrite the
      // view we just
      // copied
    }
    if (this.frefedSequence == null)
    {
      frefedSequence = new Vector();
    }

    viewportsAdded = new Hashtable();

    AlignFrame af = LoadFromObject(jm, null, false, null);
    af.alignPanels.clear();
    af.closeMenuItem_actionPerformed(true);

    /*
     * if(ap.av.getAlignment().getAlignmentAnnotation()!=null) { for(int i=0;
     * i<ap.av.getAlignment().getAlignmentAnnotation().length; i++) {
     * if(!ap.av.getAlignment().getAlignmentAnnotation()[i].autoCalculated) {
     * af.alignPanel.av.getAlignment().getAlignmentAnnotation()[i] =
     * ap.av.getAlignment().getAlignmentAnnotation()[i]; } } }
     */

    return af.alignPanel;
  }

  /**
   * flag indicating if hashtables should be cleared on finalization TODO this
   * flag may not be necessary
   */
  private final boolean _cleartables = true;

  private Hashtable jvids2vobj;

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#finalize()
   */
  @Override
  protected void finalize() throws Throwable
  {
    // really make sure we have no buried refs left.
    if (_cleartables)
    {
      clearSeqRefs();
    }
    this.seqRefIds = null;
    this.seqsToIds = null;
    super.finalize();
  }

  private void warn(String msg)
  {
    warn(msg, null);
  }

  private void warn(String msg, Exception e)
  {
    if (Cache.log != null)
    {
      if (e != null)
      {
        Cache.log.warn(msg, e);
      }
      else
      {
        Cache.log.warn(msg);
      }
    }
    else
    {
      System.err.println("Warning: " + msg);
      if (e != null)
      {
        e.printStackTrace();
      }
    }
  }

  private void debug(String string)
  {
    debug(string, null);
  }

  private void debug(String msg, Exception e)
  {
    if (Cache.log != null)
    {
      if (e != null)
      {
        Cache.log.debug(msg, e);
      }
      else
      {
        Cache.log.debug(msg);
      }
    }
    else
    {
      System.err.println("Warning: " + msg);
      if (e != null)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * set the object to ID mapping tables used to write/recover objects and XML
   * ID strings for the jalview project. If external tables are provided then
   * finalize and clearSeqRefs will not clear the tables when the Jalview2XML
   * object goes out of scope. - also populates the datasetIds hashtable with
   * alignment objects containing dataset sequences
   * 
   * @param vobj2jv
   *          Map from ID strings to jalview datamodel
   * @param jv2vobj
   *          Map from jalview datamodel to ID strings
   * 
   * 
   */
  public void setObjectMappingTables(Hashtable vobj2jv,
          IdentityHashMap jv2vobj)
  {
    this.jv2vobj = jv2vobj;
    this.vobj2jv = vobj2jv;
    Iterator ds = jv2vobj.keySet().iterator();
    String id;
    while (ds.hasNext())
    {
      Object jvobj = ds.next();
      id = jv2vobj.get(jvobj).toString();
      if (jvobj instanceof jalview.datamodel.Alignment)
      {
        if (((jalview.datamodel.Alignment) jvobj).getDataset() == null)
        {
          addDatasetRef(id, (jalview.datamodel.Alignment) jvobj);
        }
      }
      else if (jvobj instanceof jalview.datamodel.Sequence)
      {
        // register sequence object so the XML parser can recover it.
        if (seqRefIds == null)
        {
          seqRefIds = new Hashtable();
        }
        if (seqsToIds == null)
        {
          seqsToIds = new IdentityHashMap();
        }
        seqRefIds.put(jv2vobj.get(jvobj).toString(), jvobj);
        seqsToIds.put(jvobj, id);
      }
      else if (jvobj instanceof jalview.datamodel.AlignmentAnnotation)
      {
        if (annotationIds == null)
        {
          annotationIds = new Hashtable();
        }
        String anid;
        annotationIds.put(anid = jv2vobj.get(jvobj).toString(), jvobj);
        jalview.datamodel.AlignmentAnnotation jvann = (jalview.datamodel.AlignmentAnnotation) jvobj;
        if (jvann.annotationId == null)
        {
          jvann.annotationId = anid;
        }
        if (!jvann.annotationId.equals(anid))
        {
          // TODO verify that this is the correct behaviour
          this.warn("Overriding Annotation ID for " + anid
                  + " from different id : " + jvann.annotationId);
          jvann.annotationId = anid;
        }
      }
      else if (jvobj instanceof String)
      {
        if (jvids2vobj == null)
        {
          jvids2vobj = new Hashtable();
          jvids2vobj.put(jvobj, jv2vobj.get(jvobj).toString());
        }
      }
      else
        Cache.log.debug("Ignoring " + jvobj.getClass() + " (ID = " + id);
    }
  }

  /**
   * set the uniqueSetSuffix used to prefix/suffix object IDs for jalview
   * objects created from the project archive. If string is null (default for
   * construction) then suffix will be set automatically.
   * 
   * @param string
   */
  public void setUniqueSetSuffix(String string)
  {
    uniqueSetSuffix = string;

  }

  /**
   * uses skipList2 as the skipList for skipping views on sequence sets
   * associated with keys in the skipList
   * 
   * @param skipList2
   */
  public void setSkipList(Hashtable skipList2)
  {
    skipList = skipList2;
  }

}
