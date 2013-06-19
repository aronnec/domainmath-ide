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
package jalview.structure;

import java.io.*;
import java.util.*;

import MCview.*;
import jalview.analysis.*;
import jalview.api.AlignmentViewPanel;
import jalview.api.StructureSelectionManagerProvider;
import jalview.datamodel.*;

public class StructureSelectionManager
{
  static IdentityHashMap<StructureSelectionManagerProvider, StructureSelectionManager> instances;

  StructureMapping[] mappings;

  /**
   * debug function - write all mappings to stdout
   */
  public void reportMapping()
  {
    if (mappings == null)
    {
      System.err.println("reportMapping: No PDB/Sequence mappings.");
    }
    else
    {
      System.err.println("reportMapping: There are " + mappings.length
              + " mappings.");
      for (int m = 0; m < mappings.length; m++)
      {
        System.err.println("mapping " + m + " : " + mappings[m].pdbfile);
      }
    }
  }

  Hashtable mappingData = new Hashtable();

  public static StructureSelectionManager getStructureSelectionManager(
          StructureSelectionManagerProvider context)
  {
    if (instances == null)
    {
      instances = new java.util.IdentityHashMap<StructureSelectionManagerProvider, StructureSelectionManager>();
    }
    StructureSelectionManager instance = instances.get(context);
    if (instance == null)
    {
      instances.put(context, instance = new StructureSelectionManager());
    }
    return instance;
  }

  /**
   * flag controlling whether SeqMappings are relayed from received sequence
   * mouse over events to other sequences
   */
  boolean relaySeqMappings = true;

  /**
   * Enable or disable relay of seqMapping events to other sequences. You might
   * want to do this if there are many sequence mappings and the host computer
   * is slow
   * 
   * @param relay
   */
  public void setRelaySeqMappings(boolean relay)
  {
    relaySeqMappings = relay;
  }

  /**
   * get the state of the relay seqMappings flag.
   * 
   * @return true if sequence mouse overs are being relayed to other mapped
   *         sequences
   */
  public boolean isRelaySeqMappingsEnabled()
  {
    return relaySeqMappings;
  }

  Vector listeners = new Vector();

  /**
   * register a listener for alignment sequence mouseover events
   * 
   * @param svl
   */
  public void addStructureViewerListener(Object svl)
  {
    if (!listeners.contains(svl))
    {
      listeners.addElement(svl);
    }
  }

  public String alreadyMappedToFile(String pdbid)
  {
    if (mappings != null)
    {
      for (int i = 0; i < mappings.length; i++)
      {
        if (mappings[i].getPdbId().equals(pdbid))
        {
          return mappings[i].pdbfile;
        }
      }
    }
    return null;
  }

  /**
   * create sequence structure mappings between each sequence and the given
   * pdbFile (retrieved via the given protocol).
   * 
   * @param sequence
   *          - one or more sequences to be mapped to pdbFile
   * @param targetChains
   *          - optional chain specification for mapping each sequence to pdb
   *          (may be nill, individual elements may be nill)
   * @param pdbFile
   *          - structure data resource
   * @param protocol
   *          - how to resolve data from resource
   * @return null or the structure data parsed as a pdb file
   */
  synchronized public MCview.PDBfile setMapping(SequenceI[] sequence,
          String[] targetChains, String pdbFile, String protocol)
  {
    /*
     * There will be better ways of doing this in the future, for now we'll use
     * the tried and tested MCview pdb mapping
     */
    MCview.PDBfile pdb = null;
    try
    {
      pdb = new MCview.PDBfile(pdbFile, protocol);
    } catch (Exception ex)
    {
      ex.printStackTrace();
      return null;
    }

    String targetChain;
    for (int s = 0; s < sequence.length; s++)
    {
      boolean infChain = true;
      if (targetChains != null && targetChains[s] != null)
      {
        infChain = false;
        targetChain = targetChains[s];
      }
      else if (sequence[s].getName().indexOf("|") > -1)
      {
        targetChain = sequence[s].getName().substring(
                sequence[s].getName().lastIndexOf("|") + 1);
        if (targetChain.length() > 1)
        {
          if (targetChain.trim().length() == 0)
          {
            targetChain = " ";
          }
          else
          {
            // not a valid chain identifier
            targetChain = "";
          }
        }
      }
      else
        targetChain = "";

      int max = -10;
      AlignSeq maxAlignseq = null;
      String maxChainId = " ";
      PDBChain maxChain = null;
      boolean first = true;
      for (int i = 0; i < pdb.chains.size(); i++)
      {
        PDBChain chain = ((PDBChain) pdb.chains.elementAt(i));
        if (targetChain.length() > 0 && !targetChain.equals(chain.id)
                && !infChain)
        {
          continue; // don't try to map chains don't match.
        }
        // TODO: correctly determine sequence type for mixed na/peptide
        // structures
        AlignSeq as = new AlignSeq(sequence[s],
                ((PDBChain) pdb.chains.elementAt(i)).sequence,
                ((PDBChain) pdb.chains.elementAt(i)).isNa ? AlignSeq.DNA
                        : AlignSeq.PEP);
        as.calcScoreMatrix();
        as.traceAlignment();

        if (first || as.maxscore > max
                || (as.maxscore == max && chain.id.equals(targetChain)))
        {
          first = false;
          maxChain = chain;
          max = as.maxscore;
          maxAlignseq = as;
          maxChainId = chain.id;
        }
      }
      if (maxChain == null)
      {
        continue;
      }
      final StringBuffer mappingDetails = new StringBuffer();
      mappingDetails.append("\n\nPDB Sequence is :\nSequence = "
              + maxChain.sequence.getSequenceAsString());
      mappingDetails.append("\nNo of residues = "
              + maxChain.residues.size() + "\n\n");
      PrintStream ps = new PrintStream(System.out)
      {
        public void print(String x)
        {
          mappingDetails.append(x);
        }

        public void println()
        {
          mappingDetails.append("\n");
        }
      };

      maxAlignseq.printAlignment(ps);

      mappingDetails.append("\nPDB start/end " + maxAlignseq.seq2start
              + " " + maxAlignseq.seq2end);
      mappingDetails.append("\nSEQ start/end "
              + (maxAlignseq.seq1start + sequence[s].getStart() - 1) + " "
              + (maxAlignseq.seq1end + sequence[s].getEnd() - 1));

      maxChain.makeExactMapping(maxAlignseq, sequence[s]);

      maxChain.transferRESNUMFeatures(sequence[s], null);

      // allocate enough slots to store the mapping from positions in
      // sequence[s] to the associated chain
      int[][] mapping = new int[sequence[s].findPosition(sequence[s]
              .getLength()) + 2][2];
      int resNum = -10000;
      int index = 0;

      do
      {
        Atom tmp = (Atom) maxChain.atoms.elementAt(index);
        if (resNum != tmp.resNumber && tmp.alignmentMapping != -1)
        {
          resNum = tmp.resNumber;
          mapping[tmp.alignmentMapping + 1][0] = tmp.resNumber;
          mapping[tmp.alignmentMapping + 1][1] = tmp.atomIndex;
        }

        index++;
      } while (index < maxChain.atoms.size());

      if (mappings == null)
      {
        mappings = new StructureMapping[1];
      }
      else
      {
        StructureMapping[] tmp = new StructureMapping[mappings.length + 1];
        System.arraycopy(mappings, 0, tmp, 0, mappings.length);
        mappings = tmp;
      }

      if (protocol.equals(jalview.io.AppletFormatAdapter.PASTE))
        pdbFile = "INLINE" + pdb.id;

      mappings[mappings.length - 1] = new StructureMapping(sequence[s],
              pdbFile, pdb.id, maxChainId, mapping,
              mappingDetails.toString());
      maxChain.transferResidueAnnotation(mappings[mappings.length - 1]);
    }
    // ///////

    return pdb;
  }

  public void removeStructureViewerListener(Object svl, String[] pdbfiles)
  {
    listeners.removeElement(svl);
    if (svl instanceof SequenceListener)
    {
      for (int i = 0; i < listeners.size(); i++)
      {
        if (listeners.elementAt(i) instanceof StructureListener)
        {
          ((StructureListener) listeners.elementAt(i))
                  .releaseReferences(svl);
        }
      }
    }

    if (pdbfiles == null)
    {
      return;
    }
    boolean removeMapping = true;
    String[] handlepdbs;
    Vector pdbs = new Vector();
    for (int i = 0; i < pdbfiles.length; pdbs.addElement(pdbfiles[i++]))
      ;
    StructureListener sl;
    for (int i = 0; i < listeners.size(); i++)
    {
      if (listeners.elementAt(i) instanceof StructureListener)
      {
        sl = (StructureListener) listeners.elementAt(i);
        handlepdbs = sl.getPdbFile();
        for (int j = 0; j < handlepdbs.length; j++)
        {
          if (pdbs.contains(handlepdbs[j]))
          {
            pdbs.removeElement(handlepdbs[j]);
          }
        }

      }
    }

    if (pdbs.size() > 0 && mappings != null)
    {
      Vector tmp = new Vector();
      for (int i = 0; i < mappings.length; i++)
      {
        if (!pdbs.contains(mappings[i].pdbfile))
        {
          tmp.addElement(mappings[i]);
        }
      }

      mappings = new StructureMapping[tmp.size()];
      tmp.copyInto(mappings);
    }
  }

  public void mouseOverStructure(int pdbResNum, String chain, String pdbfile)
  {
    if (listeners == null)
    {
      // old or prematurely sent event
      return;
    }
    boolean hasSequenceListeners = handlingVamsasMo || seqmappings != null;
    SearchResults results = null;
    SequenceI lastseq = null;
    int lastipos = -1, indexpos;
    for (int i = 0; i < listeners.size(); i++)
    {
      if (listeners.elementAt(i) instanceof SequenceListener)
      {
        if (results == null)
        {
          results = new SearchResults();
        }
        if (mappings != null)
        {
          for (int j = 0; j < mappings.length; j++)
          {
            if (mappings[j].pdbfile.equals(pdbfile)
                    && mappings[j].pdbchain.equals(chain))
            {
              indexpos = mappings[j].getSeqPos(pdbResNum);
              if (lastipos != indexpos && lastseq != mappings[j].sequence)
              {
                results.addResult(mappings[j].sequence, indexpos, indexpos);
                lastipos = indexpos;
                lastseq = mappings[j].sequence;
                // construct highlighted sequence list
                if (seqmappings != null)
                {

                  Enumeration e = seqmappings.elements();
                  while (e.hasMoreElements())

                  {
                    ((AlignedCodonFrame) e.nextElement()).markMappedRegion(
                            mappings[j].sequence, indexpos, results);
                  }
                }
              }

            }
          }
        }
      }
    }
    if (results != null)
    {
      for (int i = 0; i < listeners.size(); i++)
      {
        Object li = listeners.elementAt(i);
        if (li instanceof SequenceListener)
          ((SequenceListener) li).highlightSequence(results);
      }
    }
  }

  Vector seqmappings = null; // should be a simpler list of mapped seuqence

  /**
   * highlight regions associated with a position (indexpos) in seq
   * 
   * @param seq
   *          the sequeence that the mouse over occured on
   * @param indexpos
   *          the absolute position being mouseovered in seq (0 to seq.length())
   * @param index
   *          the sequence position (if -1, seq.findPosition is called to
   *          resolve the residue number)
   */
  public void mouseOverSequence(SequenceI seq, int indexpos, int index,
          VamsasSource source)
  {
    boolean hasSequenceListeners = handlingVamsasMo || seqmappings != null;
    SearchResults results = null;
    if (index == -1)
      index = seq.findPosition(indexpos);
    StructureListener sl;
    int atomNo = 0;
    for (int i = 0; i < listeners.size(); i++)
    {
      Object listener = listeners.elementAt(i);
      if (listener == source)
      {
        continue;
      }
      if (listener instanceof StructureListener)
      {
        sl = (StructureListener) listener;
        if (mappings == null)
        {
          continue;
        }
        for (int j = 0; j < mappings.length; j++)
        {
          if (mappings[j].sequence == seq
                  || mappings[j].sequence == seq.getDatasetSequence())
          {
            atomNo = mappings[j].getAtomNum(index);

            if (atomNo > 0)
            {
              sl.highlightAtom(atomNo, mappings[j].getPDBResNum(index),
                      mappings[j].pdbchain, mappings[j].pdbfile);
            }
          }
        }
      }
      else
      {
        if (relaySeqMappings && hasSequenceListeners
                && listener instanceof SequenceListener)
        {
          // DEBUG
          // System.err.println("relay Seq " + seq.getDisplayId(false) + " " +
          // index);

          if (results == null)
          {
            results = new SearchResults();
            if (index >= seq.getStart() && index <= seq.getEnd())
            {
              // construct highlighted sequence list

              if (seqmappings != null)
              {
                Enumeration e = seqmappings.elements();
                while (e.hasMoreElements())

                {
                  ((AlignedCodonFrame) e.nextElement()).markMappedRegion(
                          seq, index, results);
                }
              }
              // hasSequenceListeners = results.getSize() > 0;
              if (handlingVamsasMo)
              {
                // maybe have to resolve seq to a dataset seqeunce...
                // add in additional direct sequence and/or dataset sequence
                // highlighting
                results.addResult(seq, index, index);
              }
            }
          }
          if (hasSequenceListeners)
          {
            ((SequenceListener) listener).highlightSequence(results);
          }
        }
        else if (listener instanceof VamsasListener && !handlingVamsasMo)
        {
          // DEBUG
          // System.err.println("Vamsas from Seq " + seq.getDisplayId(false) + "
          // " +
          // index);
          // pass the mouse over and absolute position onto the
          // VamsasListener(s)
          ((VamsasListener) listener).mouseOver(seq, indexpos, source);
        }
        else if (listener instanceof SecondaryStructureListener)
        {
          ((SecondaryStructureListener) listener).mouseOverSequence(seq,
                  indexpos);
        }
      }
    }
  }

  /**
   * true if a mouse over event from an external (ie Vamsas) source is being
   * handled
   */
  boolean handlingVamsasMo = false;

  long lastmsg = 0;

  /**
   * as mouseOverSequence but only route event to SequenceListeners
   * 
   * @param sequenceI
   * @param position
   *          in an alignment sequence
   */
  public void mouseOverVamsasSequence(SequenceI sequenceI, int position,
          VamsasSource source)
  {
    handlingVamsasMo = true;
    long msg = sequenceI.hashCode() * (1 + position);
    if (lastmsg != msg)
    {
      lastmsg = msg;
      mouseOverSequence(sequenceI, position, -1, source);
    }
    handlingVamsasMo = false;
  }

  public Annotation[] colourSequenceFromStructure(SequenceI seq,
          String pdbid)
  {
    return null;
    // THIS WILL NOT BE AVAILABLE IN JALVIEW 2.3,
    // UNTIL THE COLOUR BY ANNOTATION IS REWORKED
    /*
     * Annotation [] annotations = new Annotation[seq.getLength()];
     * 
     * StructureListener sl; int atomNo = 0; for (int i = 0; i <
     * listeners.size(); i++) { if (listeners.elementAt(i) instanceof
     * StructureListener) { sl = (StructureListener) listeners.elementAt(i);
     * 
     * for (int j = 0; j < mappings.length; j++) {
     * 
     * if (mappings[j].sequence == seq && mappings[j].getPdbId().equals(pdbid)
     * && mappings[j].pdbfile.equals(sl.getPdbFile())) {
     * System.out.println(pdbid+" "+mappings[j].getPdbId() +"
     * "+mappings[j].pdbfile);
     * 
     * java.awt.Color col; for(int index=0; index<seq.getLength(); index++) {
     * if(jalview.util.Comparison.isGap(seq.getCharAt(index))) continue;
     * 
     * atomNo = mappings[j].getAtomNum(seq.findPosition(index)); col =
     * java.awt.Color.white; if (atomNo > 0) { col = sl.getColour(atomNo,
     * mappings[j].getPDBResNum(index), mappings[j].pdbchain,
     * mappings[j].pdbfile); }
     * 
     * annotations[index] = new Annotation("X",null,' ',0,col); } return
     * annotations; } } } }
     * 
     * return annotations;
     */
  }

  public void structureSelectionChanged()
  {
  }

  public void sequenceSelectionChanged()
  {
  }

  public void sequenceColoursChanged(Object source)
  {
    StructureListener sl;
    for (int i = 0; i < listeners.size(); i++)
    {
      if (listeners.elementAt(i) instanceof StructureListener)
      {
        sl = (StructureListener) listeners.elementAt(i);
        sl.updateColours(source);
      }
    }
  }

  public StructureMapping[] getMapping(String pdbfile)
  {
    Vector tmp = new Vector();
    if (mappings != null)
    {
      for (int i = 0; i < mappings.length; i++)
      {
        if (mappings[i].pdbfile.equals(pdbfile))
        {
          tmp.addElement(mappings[i]);
        }
      }
    }
    StructureMapping[] ret = new StructureMapping[tmp.size()];
    for (int i = 0; i < tmp.size(); i++)
    {
      ret[i] = (StructureMapping) tmp.elementAt(i);
    }

    return ret;
  }

  public String printMapping(String pdbfile)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < mappings.length; i++)
    {
      if (mappings[i].pdbfile.equals(pdbfile))
      {
        sb.append(mappings[i].mappingDetails);
      }
    }

    return sb.toString();
  }

  private int[] seqmappingrefs = null; // refcount for seqmappings elements

  private synchronized void modifySeqMappingList(boolean add,
          AlignedCodonFrame[] codonFrames)
  {
    if (!add && (seqmappings == null || seqmappings.size() == 0))
      return;
    if (seqmappings == null)
      seqmappings = new Vector();
    if (codonFrames != null && codonFrames.length > 0)
    {
      for (int cf = 0; cf < codonFrames.length; cf++)
      {
        if (seqmappings.contains(codonFrames[cf]))
        {
          if (add)
          {
            seqmappingrefs[seqmappings.indexOf(codonFrames[cf])]++;
          }
          else
          {
            if (--seqmappingrefs[seqmappings.indexOf(codonFrames[cf])] <= 0)
            {
              int pos = seqmappings.indexOf(codonFrames[cf]);
              int[] nr = new int[seqmappingrefs.length - 1];
              if (pos > 0)
              {
                System.arraycopy(seqmappingrefs, 0, nr, 0, pos);
              }
              if (pos < seqmappingrefs.length - 1)
              {
                System.arraycopy(seqmappingrefs, pos + 1, nr, 0,
                        seqmappingrefs.length - pos - 2);
              }
            }
          }
        }
        else
        {
          if (add)
          {
            seqmappings.addElement(codonFrames[cf]);

            int[] nsr = new int[(seqmappingrefs == null) ? 1
                    : seqmappingrefs.length + 1];
            if (seqmappingrefs != null && seqmappingrefs.length > 0)
              System.arraycopy(seqmappingrefs, 0, nsr, 0,
                      seqmappingrefs.length);
            nsr[(seqmappingrefs == null) ? 0 : seqmappingrefs.length] = 1;
            seqmappingrefs = nsr;
          }
        }
      }
    }
  }

  public void removeMappings(AlignedCodonFrame[] codonFrames)
  {
    modifySeqMappingList(false, codonFrames);
  }

  public void addMappings(AlignedCodonFrame[] codonFrames)
  {
    modifySeqMappingList(true, codonFrames);
  }

  Vector<SelectionListener> sel_listeners = new Vector<SelectionListener>();

  public void addSelectionListener(SelectionListener selecter)
  {
    if (!sel_listeners.contains(selecter))
    {
      sel_listeners.addElement(selecter);
    }
  }

  public void removeSelectionListener(SelectionListener toremove)
  {
    if (sel_listeners.contains(toremove))
    {
      sel_listeners.removeElement(toremove);
    }
  }

  public synchronized void sendSelection(
          jalview.datamodel.SequenceGroup selection,
          jalview.datamodel.ColumnSelection colsel, SelectionSource source)
  {
    if (sel_listeners != null && sel_listeners.size() > 0)
    {
      Enumeration listeners = sel_listeners.elements();
      while (listeners.hasMoreElements())
      {
        SelectionListener slis = ((SelectionListener) listeners
                .nextElement());
        if (slis != source)
        {
          slis.selection(selection, colsel, source);
        }
        ;
      }
    }
  }

  Vector<AlignmentViewPanelListener> view_listeners = new Vector<AlignmentViewPanelListener>();

  public synchronized void sendViewPosition(
          jalview.api.AlignmentViewPanel source, int startRes, int endRes,
          int startSeq, int endSeq)
  {

    if (view_listeners != null && view_listeners.size() > 0)
    {
      Enumeration<AlignmentViewPanelListener> listeners = view_listeners
              .elements();
      while (listeners.hasMoreElements())
      {
        AlignmentViewPanelListener slis = listeners.nextElement();
        if (slis != source)
        {
          slis.viewPosition(startRes, endRes, startSeq, endSeq, source);
        }
        ;
      }
    }
  }

  public void finalize() throws Throwable
  {
    if (listeners != null)
    {
      listeners.clear();
      listeners = null;
    }
    if (mappingData != null)
    {
      mappingData.clear();
      mappingData = null;
    }
    if (sel_listeners != null)
    {
      sel_listeners.clear();
      sel_listeners = null;
    }
    if (view_listeners != null)
    {
      view_listeners.clear();
      view_listeners = null;
    }
    mappings = null;
    seqmappingrefs = null;
  }

  /**
   * release all references associated with this manager provider
   * 
   * @param jalviewLite
   */
  public static void release(StructureSelectionManagerProvider jalviewLite)
  {
    // synchronized (instances)
    {
      if (instances == null)
      {
        return;
      }
      StructureSelectionManager mnger = (instances.get(jalviewLite));
      if (mnger != null)
      {
        instances.remove(jalviewLite);
        try
        {
          mnger.finalize();
        } catch (Throwable x)
        {
        }
        ;
      }
    }
  }

}
