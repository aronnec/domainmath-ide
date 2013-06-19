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

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import jalview.analysis.NJTree;
import jalview.bin.Cache;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.AlignmentView;
import jalview.datamodel.BinaryNode;
import jalview.datamodel.SeqCigar;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceI;
import jalview.datamodel.SequenceNode;
import jalview.gui.AlignViewport;
import jalview.gui.TreePanel;
import jalview.io.NewickFile;
import jalview.io.VamsasAppDatastore;
import uk.ac.vamsas.client.Vobject;
import uk.ac.vamsas.objects.core.AlignmentSequence;
import uk.ac.vamsas.objects.core.Entry;
import uk.ac.vamsas.objects.core.Input;
import uk.ac.vamsas.objects.core.Newick;
import uk.ac.vamsas.objects.core.Param;
import uk.ac.vamsas.objects.core.Provenance;
import uk.ac.vamsas.objects.core.Seg;
import uk.ac.vamsas.objects.core.Treenode;
import uk.ac.vamsas.objects.core.Vref;

public class Tree extends DatastoreItem
{
  AlignmentI jal;

  TreePanel tp;

  uk.ac.vamsas.objects.core.Tree tree;

  uk.ac.vamsas.objects.core.Alignment alignment; // may be null => dataset or

  // other kind of tree
  private NewickFile ntree;

  private String title;

  private AlignmentView inputData = null;

  public static void updateFrom(VamsasAppDatastore datastore,
          jalview.gui.AlignFrame alignFrame,
          uk.ac.vamsas.objects.core.Tree vtree)
  {
    Tree toTree = new Tree(datastore, alignFrame, vtree);
  }

  public Tree(VamsasAppDatastore datastore,
          jalview.gui.AlignFrame alignFrame,
          uk.ac.vamsas.objects.core.Tree vtree)
  {
    super(datastore, vtree, TreePanel.class);
    doJvUpdate();
  }

  private NewickFile getNtree() throws IOException
  {
    return new jalview.io.NewickFile(tree.getNewick(0).getContent());
  }

  public Tree(VamsasAppDatastore datastore, TreePanel tp2, AlignmentI jal2,
          uk.ac.vamsas.objects.core.Alignment alignment2)
  {
    super(datastore, tp2, uk.ac.vamsas.objects.core.Tree.class);

    jal = jal2;
    tp = (TreePanel) jvobj;
    alignment = alignment2;

    tree = (uk.ac.vamsas.objects.core.Tree) vobj;
    doSync();
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.io.vamsas.DatastoreItem#addFromDocument()
   */
  @Override
  public void addFromDocument()
  {
    tree = (uk.ac.vamsas.objects.core.Tree) vobj; // vtree;
    TreePanel tp = (TreePanel) jvobj; // getvObj2jv(tree);
    // make a new tree
    Object[] idata = recoverInputData(tree.getProvenance());
    try
    {
      if (idata != null && idata[0] != null)
      {
        inputData = (AlignmentView) idata[0];
      }
      ntree = getNtree();
      title = tree.getNewick(0).getTitle();
      if (title == null || title.length() == 0)
      {
        title = tree.getTitle(); // hack!!!!
      }
    } catch (Exception e)
    {
      Cache.log.warn("Problems parsing treefile '"
              + tree.getNewick(0).getContent() + "'", e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.io.vamsas.DatastoreItem#conflict()
   */
  @Override
  public void conflict()
  {
    Cache.log
            .info("Update (with conflict) from vamsas document to alignment associated tree not implemented yet.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.io.vamsas.DatastoreItem#update()
   */
  @Override
  public void updateToDoc()
  {
    if (isModifiable(tree.getModifiable()))
    {
      // synchronize(); // update();
      // verify any changes.
      log.info("TODO: Update tree in document from jalview.");
    }
    else
    {
      // handle conflict
      log.info("TODO: Add the locally modified tree in Jalview as a new tree in document, leaving locked tree unchanged.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.io.vamsas.DatastoreItem#updateFromDoc()
   */
  @Override
  public void updateFromDoc()
  {
    // should probably just open a new tree panel in the same place as the old
    // one
    // TODO: Tree.updateFromDoc
    /*
     * TreePanel tp = (TreePanel) jvobj; // getvObj2jv(tree);
     * 
     * // make a new tree Object[] idata =
     * recoverInputData(tree.getProvenance()); try { if (idata != null &&
     * idata[0] != null) { inputData = (AlignmentView) idata[0]; } ntree =
     * getNtree(); title = tree.getNewick(0).getTitle(); if (title == null ||
     * title.length() == 0) { title = tree.getTitle(); // hack!!!! } } catch
     * (Exception e) { Cache.log.warn("Problems parsing treefile '" +
     * tree.getNewick(0).getContent() + "'", e); }
     */
    log.debug("Update the local tree in jalview from the document.");

    if (isModifiable(tree.getModifiable()))
    {
      // synchronize(); // update();
      // verify any changes.
      log.debug("Update tree in document from jalview.");
    }
    else
    {
      // handle conflict
      log.debug("Add modified jalview tree as new tree in document.");
    }
  }

  /**
   * correctly creates provenance for trees calculated on an alignment by
   * jalview.
   * 
   * @param jal
   * @param tp
   * @return
   */
  private Provenance makeTreeProvenance(AlignmentI jal, TreePanel tp)
  {
    Cache.log.debug("Making Tree provenance for " + tp.getTitle());
    Provenance prov = new Provenance();
    prov.addEntry(new Entry());
    prov.getEntry(0).setAction("imported " + tp.getTitle());
    prov.getEntry(0).setUser(provEntry.getUser());
    prov.getEntry(0).setApp(provEntry.getApp());
    prov.getEntry(0).setDate(provEntry.getDate());
    if (tp.getTree().hasOriginalSequenceData())
    {
      Input vInput = new Input();
      // LATER: check to see if tree input data is contained in this alignment -
      // or just correctly resolve the tree's seqData to the correct alignment
      // in
      // the document.
      Vector alsqrefs = getjv2vObjs(findAlignmentSequences(jal,
              tp.getTree().seqData.getSequences()));
      Object[] alsqs = new Object[alsqrefs.size()];
      alsqrefs.copyInto(alsqs);
      vInput.setObjRef(alsqs);
      // now create main provenance data
      prov.getEntry(0).setAction("created " + tp.getTitle());
      prov.getEntry(0).addInput(vInput);
      // jalview's special input parameter for distance matrix calculations
      vInput.setName("jalview:seqdist"); // TODO: settle on appropriate name.
      prov.getEntry(0).addParam(new Param());
      prov.getEntry(0).getParam(0).setName("treeType");
      prov.getEntry(0).getParam(0).setType("utf8");
      prov.getEntry(0).getParam(0).setContent("NJ"); // TODO: type of tree is a
      // general parameter
      int ranges[] = tp.getTree().seqData.getVisibleContigs();
      // VisibleContigs are with respect to alignment coordinates. Still need
      // offsets
      int start = tp.getTree().seqData.getAlignmentOrigin();
      for (int r = 0; r < ranges.length; r += 2)
      {
        Seg visSeg = new Seg();
        visSeg.setStart(1 + start + ranges[r]);
        visSeg.setEnd(start + ranges[r + 1]);
        visSeg.setInclusive(true);
        vInput.addSeg(visSeg);
      }
    }
    Cache.log.debug("Finished Tree provenance for " + tp.getTitle());
    return prov;
  }

  /**
   * look up SeqCigars in an existing alignment.
   * 
   * @param jal
   * @param sequences
   * @return vector of alignment sequences in order of SeqCigar array (but
   *         missing unfound seqcigars)
   */
  private Vector findAlignmentSequences(AlignmentI jal, SeqCigar[] sequences)
  {
    SeqCigar[] tseqs = new SeqCigar[sequences.length];
    System.arraycopy(sequences, 0, tseqs, 0, sequences.length);
    Vector alsq = new Vector();
    List<SequenceI> jalsqs;
    synchronized (jalsqs = jal.getSequences())
    {
      for (SequenceI asq : jalsqs)
      {
        for (int t = 0; t < sequences.length; t++)
        {
          if (tseqs[t] != null
                  && (tseqs[t].getRefSeq() == asq || tseqs[t].getRefSeq() == asq
                          .getDatasetSequence()))
          // && tseqs[t].getStart()>=asq.getStart() &&
          // tseqs[t].getEnd()<=asq.getEnd())
          {
            tseqs[t] = null;
            alsq.add(asq);
          }
        }
      }
    }
    if (alsq.size() < sequences.length)
      Cache.log
              .warn("Not recovered all alignment sequences for given set of input sequence CIGARS");
    return alsq;
  }

  /**
   * 
   * Update jalview newick representation with TreeNode map
   * 
   * @param tp
   *          the treepanel that this tree is bound to.
   */
  public void UpdateSequenceTreeMap(TreePanel tp)
  {
    if (tp == null || tree == null)
      return;
    Vector leaves = new Vector();
    if (tp.getTree() == null)
    {
      Cache.log.warn("Not updating SequenceTreeMap for "
              + tree.getVorbaId());
      return;
    }
    tp.getTree().findLeaves(tp.getTree().getTopNode(), leaves);
    Treenode[] tn = tree.getTreenode(); // todo: select nodes for this
    // particular tree
    int sz = tn.length;
    int i = 0;

    while (i < sz)
    {
      Treenode node = tn[i++];
      BinaryNode mappednode = findNodeSpec(node.getNodespec(), leaves);
      if (mappednode != null && mappednode instanceof SequenceNode)
      {
        SequenceNode leaf = (SequenceNode) mappednode;
        // check if we can make the specified association
        Object jvseq = null;
        int vrf = 0, refv = 0;
        while (jvseq == null && vrf < node.getVrefCount())
        {
          if (refv < node.getVref(vrf).getRefsCount())
          {
            Object noderef = node.getVref(vrf).getRefs(refv++);
            if (noderef instanceof AlignmentSequence)
            {
              // we only make these kind of associations
              jvseq = getvObj2jv((Vobject) noderef);
            }
          }
          else
          {
            refv = 0;
            vrf++;
          }
        }
        if (jvseq instanceof SequenceI)
        {
          leaf.setElement(jvseq);
          leaf.setPlaceholder(false);
        }
        else
        {
          leaf.setPlaceholder(true);
          leaf.setElement(new Sequence(leaf.getName(), "THISISAPLACEHLDER"));
        }
      }
    }
  }

  // / TODO: refactor to vamsas :start
  /**
   * construct treenode mappings for mapped sequences
   * 
   * @param ntree
   * @param newick
   * @return
   */
  public Treenode[] makeTreeNodes(NJTree ntree, Newick newick)
  {
    Vector leaves = new Vector();
    ntree.findLeaves(ntree.getTopNode(), leaves);
    Vector tnv = new Vector();
    Enumeration l = leaves.elements();
    Hashtable nodespecs = new Hashtable();
    while (l.hasMoreElements())
    {
      jalview.datamodel.BinaryNode tnode = (jalview.datamodel.BinaryNode) l
              .nextElement();
      if (tnode instanceof jalview.datamodel.SequenceNode)
      {
        if (!((jalview.datamodel.SequenceNode) tnode).isPlaceholder())
        {
          Object assocseq = ((jalview.datamodel.SequenceNode) tnode)
                  .element();
          if (assocseq instanceof SequenceI)
          {
            Vobject vobj = this.getjv2vObj(assocseq);
            if (vobj != null)
            {
              Treenode node = new Treenode();
              if (newick.isRegisterable())
              {
                this.cdoc.registerObject(newick);
                node.addTreeId(newick);
              }
              node.setNodespec(makeNodeSpec(nodespecs, tnode));
              node.setName(tnode.getName());
              Vref vr = new Vref();
              vr.addRefs(vobj);
              node.addVref(vr);
              tnv.addElement(node);
            }
            else
            {
              System.err.println("WARNING: Unassociated treeNode "
                      + tnode.element().toString()
                      + " "
                      + ((tnode.getName() != null) ? " label "
                              + tnode.getName() : ""));
            }
          }
        }
      }
    }
    if (tnv.size() > 0)
    {
      Treenode[] tn = new Treenode[tnv.size()];
      tnv.copyInto(tn);
      return tn;
    }
    return new Treenode[]
    {};
  }

  private String makeNodeSpec(Hashtable nodespecs,
          jalview.datamodel.BinaryNode tnode)
  {
    String nname = new String(tnode.getName());
    Integer nindx = (Integer) nodespecs.get(nname);
    if (nindx == null)
    {
      nindx = new Integer(1);
    }
    nname = nindx.toString() + " " + nname;
    return nname;
  }

  /**
   * call to match up Treenode specs to NJTree parsed from document object.
   * 
   * @param nodespec
   * @param leaves
   *          as returned from NJTree.findLeaves( .., ..) ..
   * @return
   */
  private jalview.datamodel.BinaryNode findNodeSpec(String nodespec,
          Vector leaves)
  {
    int occurence = -1;
    String nspec = nodespec.substring(nodespec.indexOf(' ') + 1);
    String oval = nodespec.substring(0, nodespec.indexOf(' '));
    try
    {
      occurence = new Integer(oval).intValue();
    } catch (Exception e)
    {
      System.err.println("Invalid nodespec '" + nodespec + "'");
      return null;
    }
    jalview.datamodel.BinaryNode bn = null;

    int nocc = 0;
    Enumeration en = leaves.elements();
    while (en.hasMoreElements() && nocc < occurence)
    {
      bn = (jalview.datamodel.BinaryNode) en.nextElement();
      if (bn instanceof jalview.datamodel.SequenceNode
              && bn.getName().equals(nspec))
      {
        --occurence;
      }
      else
        bn = null;
    }
    return bn;
  }

  // todo: end refactor to vamsas library
  /**
   * add jalview object to vamsas document
   * 
   */
  @Override
  public void addToDocument()
  {
    tree = new uk.ac.vamsas.objects.core.Tree();
    bindjvvobj(tp, tree);
    tree.setTitle(tp.getTitle());
    Newick newick = new Newick();
    newick.setContent(tp.getTree().toString());
    newick.setTitle(tp.getTitle());
    tree.addNewick(newick);
    tree.setProvenance(makeTreeProvenance(jal, tp));
    tree.setTreenode(makeTreeNodes(tp.getTree(), newick));

    alignment.addTree(tree);
  }

  /**
   * note: this function assumes that all sequence and alignment objects
   * referenced in input data has already been associated with jalview objects.
   * 
   * @param tp
   * @param alignFrame
   * @return Object[] { AlignmentView, AlignmentI - reference alignment for
   *         input }
   */
  public Object[] recoverInputData(Provenance tp)
  {
    AlignViewport javport = null;
    jalview.datamodel.AlignmentI jal = null;
    jalview.datamodel.CigarArray view = null;
    for (int pe = 0; pe < tp.getEntryCount(); pe++)
    {
      if (tp.getEntry(pe).getInputCount() > 0)
      {
        if (tp.getEntry(pe).getInputCount() > 1)
        {
          Cache.log
                  .warn("Ignoring additional input spec in provenance entry "
                          + tp.getEntry(pe).toString());
        }
        // LATER: deal sensibly with multiple inputs
        Input vInput = tp.getEntry(pe).getInput(0);
        // is this the whole alignment or a specific set of sequences ?
        if (vInput.getObjRefCount() == 0)
        {
          if (tree.getV_parent() != null
                  && tree.getV_parent() instanceof uk.ac.vamsas.objects.core.Alignment)
          {
            javport = getViewport(tree.getV_parent());
            jal = javport.getAlignment();
            view = javport.getAlignment().getCompactAlignment();
          }
        }
        else
        {
          // Explicit reference - to alignment, sequences or what.
          if (vInput.getObjRefCount() == 1
                  && vInput.getObjRef(0) instanceof uk.ac.vamsas.objects.core.Alignment)
          {
            // recover an AlignmentView for the input data
            javport = getViewport((Vobject) vInput.getObjRef(0));
            jal = javport.getAlignment();
            view = javport.getAlignment().getCompactAlignment();
          }
          else if (vInput.getObjRef(0) instanceof uk.ac.vamsas.objects.core.AlignmentSequence)
          {
            // recover an AlignmentView for the input data
            javport = getViewport(((Vobject) vInput.getObjRef(0))
                    .getV_parent());
            jal = javport.getAlignment();
            jalview.datamodel.SequenceI[] seqs = new jalview.datamodel.SequenceI[vInput
                    .getObjRefCount()];
            for (int i = 0, iSize = vInput.getObjRefCount(); i < iSize; i++)
            {
              SequenceI seq = (SequenceI) getvObj2jv((Vobject) vInput
                      .getObjRef(i));
              seqs[i] = seq;
            }
            view = new jalview.datamodel.Alignment(seqs)
                    .getCompactAlignment();

          }
        }
        int from = 1, to = jal.getWidth();
        int offset = 0; // deleteRange modifies its frame of reference
        for (int r = 0, s = vInput.getSegCount(); r < s; r++)
        {
          Seg visSeg = vInput.getSeg(r);
          int se[] = getSegRange(visSeg, true); // jalview doesn't do
          // bidirection alignments yet.
          if (to < se[1])
          {
            Cache.log.warn("Ignoring invalid segment in InputData spec.");
          }
          else
          {
            if (se[0] > from)
            {
              view.deleteRange(offset + from - 1, offset + se[0] - 2);
              offset -= se[0] - from;
            }
            from = se[1] + 1;
          }
        }
        if (from < to)
        {
          view.deleteRange(offset + from - 1, offset + to - 1); // final
          // deletion -
          // TODO: check
          // off by
          // one for to
        }
        return new Object[]
        { new AlignmentView(view), jal };
      }
    }
    Cache.log
            .debug("Returning null for input data recovery from provenance.");
    return null;
  }

  private AlignViewport getViewport(Vobject v_parent)
  {
    if (v_parent instanceof uk.ac.vamsas.objects.core.Alignment)
    {
      return datastore
              .findViewport((uk.ac.vamsas.objects.core.Alignment) v_parent);
    }
    return null;
  }

  public NewickFile getNewickTree()
  {
    return ntree;
  }

  public String getTitle()
  {
    return title;
  }

  public AlignmentView getInputData()
  {
    return inputData;
  }

  public boolean isValidTree()
  {
    try
    {
      if (ntree == null)
      {
        return false;
      }
      ntree.parse();
      if (ntree.getTree() != null)
      {
        ntree = getNtree();
      }
      return true;
    } catch (Exception e)
    {
      Cache.log.debug("Failed to parse newick tree string", e);
    }
    return false;
  }
}
