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

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.io.*;

import jalview.jbgui.GStructureViewer;
import jalview.api.SequenceStructureBinding;
import jalview.bin.Cache;
import jalview.datamodel.*;
import jalview.gui.ViewSelectionMenu.ViewSetProvider;
import jalview.datamodel.PDBEntry;
import jalview.io.*;
import jalview.schemes.*;
import jalview.util.Platform;

public class AppJmol extends GStructureViewer implements Runnable,
        SequenceStructureBinding, ViewSetProvider

{
  AppJmolBinding jmb;

  JPanel scriptWindow;

  JSplitPane splitPane;

  RenderPanel renderPanel;

  AlignmentPanel ap;

  Vector atomsPicked = new Vector();

  private boolean addingStructures = false;

  /**
   * 
   * @param file
   * @param id
   * @param seq
   * @param ap
   * @param loadStatus
   * @param bounds
   * @deprecated defaults to AppJmol(String[] files, ... , viewid);
   */
  public AppJmol(String file, String id, SequenceI[] seq,
          AlignmentPanel ap, String loadStatus, Rectangle bounds)
  {
    this(file, id, seq, ap, loadStatus, bounds, null);
  }

  /**
   * @deprecated
   */
  public AppJmol(String file, String id, SequenceI[] seq,
          AlignmentPanel ap, String loadStatus, Rectangle bounds,
          String viewid)
  {
    this(new String[]
    { file }, new String[]
    { id }, new SequenceI[][]
    { seq }, ap, true, true, false, loadStatus, bounds, viewid);
  }

  ViewSelectionMenu seqColourBy;

  /**
   * 
   * @param files
   * @param ids
   * @param seqs
   * @param ap
   * @param usetoColour
   *          - add the alignment panel to the list used for colouring these
   *          structures
   * @param useToAlign
   *          - add the alignment panel to the list used for aligning these
   *          structures
   * @param leaveColouringToJmol
   *          - do not update the colours from any other source. Jmol is
   *          handling them
   * @param loadStatus
   * @param bounds
   * @param viewid
   */
  public AppJmol(String[] files, String[] ids, SequenceI[][] seqs,
          AlignmentPanel ap, boolean usetoColour, boolean useToAlign,
          boolean leaveColouringToJmol, String loadStatus,
          Rectangle bounds, String viewid)
  {
    PDBEntry[] pdbentrys = new PDBEntry[files.length];
    for (int i = 0; i < pdbentrys.length; i++)
    {
      PDBEntry pdbentry = new PDBEntry();
      pdbentry.setFile(files[i]);
      pdbentry.setId(ids[i]);
      pdbentrys[i] = pdbentry;
    }
    // / TODO: check if protocol is needed to be set, and if chains are
    // autodiscovered.
    jmb = new AppJmolBinding(this, ap.getStructureSelectionManager(),
            pdbentrys, seqs, null, null);

    jmb.setLoadingFromArchive(true);
    addAlignmentPanel(ap);
    if (useToAlign)
    {
      useAlignmentPanelForSuperposition(ap);
    }
    if (leaveColouringToJmol || !usetoColour)
    {
      jmb.setColourBySequence(false);
      seqColour.setSelected(false);
      jmolColour.setSelected(true);
    }
    if (usetoColour)
    {
      useAlignmentPanelForColourbyseq(ap);
      jmb.setColourBySequence(true);
      seqColour.setSelected(true);
      jmolColour.setSelected(false);
    }
    this.setBounds(bounds);
    initMenus();
    viewId = viewid;
    // jalview.gui.Desktop.addInternalFrame(this, "Loading File",
    // bounds.width,bounds.height);

    this.addInternalFrameListener(new InternalFrameAdapter()
    {
      public void internalFrameClosing(InternalFrameEvent internalFrameEvent)
      {
        closeViewer();
      }
    });
    initJmol(loadStatus); // pdbentry, seq, JBPCHECK!

  }

  private void initMenus()
  {
    seqColour.setSelected(jmb.isColourBySequence());
    jmolColour.setSelected(!jmb.isColourBySequence());
    if (_colourwith == null)
    {
      _colourwith = new Vector<AlignmentPanel>();
    }
    if (_alignwith == null)
    {
      _alignwith = new Vector<AlignmentPanel>();
    }

    seqColourBy = new ViewSelectionMenu("Colour by ..", this, _colourwith,
            new ItemListener()
            {

              @Override
              public void itemStateChanged(ItemEvent e)
              {
                if (!seqColour.isSelected())
                {
                  seqColour.doClick();
                }
                else
                {
                  // update the jmol display now.
                  seqColour_actionPerformed(null);
                }
              }
            });
    viewMenu.add(seqColourBy);
    final ItemListener handler;
    JMenu alpanels = new ViewSelectionMenu("Superpose with ..", this,
            _alignwith, handler = new ItemListener()
            {

              @Override
              public void itemStateChanged(ItemEvent e)
              {
                alignStructs.setEnabled(_alignwith.size() > 0);
                alignStructs.setToolTipText("Align structures using "
                        + _alignwith.size() + " linked alignment views");
              }
            });
    handler.itemStateChanged(null);
    jmolActionMenu.add(alpanels);
    jmolActionMenu.addMenuListener(new MenuListener()
    {

      @Override
      public void menuSelected(MenuEvent e)
      {
        handler.itemStateChanged(null);
      }

      @Override
      public void menuDeselected(MenuEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void menuCanceled(MenuEvent e)
      {
        // TODO Auto-generated method stub

      }
    });
  }

  IProgressIndicator progressBar = null;

  /**
   * add a single PDB structure to a new or existing Jmol view
   * 
   * @param pdbentry
   * @param seq
   * @param chains
   * @param ap
   */
  public AppJmol(PDBEntry pdbentry, SequenceI[] seq, String[] chains,
          final AlignmentPanel ap)
  {
    progressBar = ap.alignFrame;
    // ////////////////////////////////
    // Is the pdb file already loaded?
    String alreadyMapped = ap.getStructureSelectionManager()
            .alreadyMappedToFile(pdbentry.getId());

    if (alreadyMapped != null)
    {
      int option = JOptionPane.showInternalConfirmDialog(Desktop.desktop,
              pdbentry.getId() + " is already displayed."
                      + "\nDo you want to re-use this viewer ?",
              "Map Sequences to Visible Window: " + pdbentry.getId(),
              JOptionPane.YES_NO_OPTION);

      if (option == JOptionPane.YES_OPTION)
      {
        // TODO : Fix multiple seq to one chain issue here.
        ap.getStructureSelectionManager().setMapping(seq, chains,
                alreadyMapped, AppletFormatAdapter.FILE);
        if (ap.seqPanel.seqCanvas.fr != null)
        {
          ap.seqPanel.seqCanvas.fr.featuresAdded();
          ap.paintAlignment(true);
        }

        // Now this AppJmol is mapped to new sequences. We must add them to
        // the exisiting array
        JInternalFrame[] frames = Desktop.instance.getAllFrames();

        for (int i = 0; i < frames.length; i++)
        {
          if (frames[i] instanceof AppJmol)
          {
            final AppJmol topJmol = ((AppJmol) frames[i]);
            // JBPNOTE: this looks like a binding routine, rather than a gui
            // routine
            for (int pe = 0; pe < topJmol.jmb.pdbentry.length; pe++)
            {
              if (topJmol.jmb.pdbentry[pe].getFile().equals(alreadyMapped))
              {
                topJmol.jmb.addSequence(pe, seq);
                topJmol.addAlignmentPanel(ap);
                // add it to the set used for colouring
                topJmol.useAlignmentPanelForColourbyseq(ap);
                topJmol.buildJmolActionMenu();
                ap.getStructureSelectionManager()
                        .sequenceColoursChanged(ap);
                break;
              }
            }
          }
        }

        return;
      }
    }
    // /////////////////////////////////
    // Check if there are other Jmol views involving this alignment
    // and prompt user about adding this molecule to one of them
    Vector existingViews = getJmolsFor(ap);
    if (existingViews.size() > 0)
    {
      Enumeration jm = existingViews.elements();
      while (jm.hasMoreElements())
      {
        AppJmol topJmol = (AppJmol) jm.nextElement();
        // TODO: highlight topJmol in view somehow
        int option = JOptionPane.showInternalConfirmDialog(Desktop.desktop,
                "Do you want to add " + pdbentry.getId()
                        + " to the view called\n'" + topJmol.getTitle()
                        + "'\n", "Align to existing structure view",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
          topJmol.useAlignmentPanelForSuperposition(ap);
          topJmol.addStructure(pdbentry, seq, chains, true, ap.alignFrame);
          return;
        }
      }
    }
    // /////////////////////////////////
    openNewJmol(ap, new PDBEntry[]
    { pdbentry }, new SequenceI[][]
    { seq });
  }

  private void openNewJmol(AlignmentPanel ap, PDBEntry[] pdbentrys,
          SequenceI[][] seqs)
  {
    progressBar = ap.alignFrame;
    jmb = new AppJmolBinding(this, ap.getStructureSelectionManager(),
            pdbentrys, seqs, null, null);
    addAlignmentPanel(ap);
    useAlignmentPanelForColourbyseq(ap);
    if (pdbentrys.length > 1)
    {
      alignAddedStructures = true;
      useAlignmentPanelForSuperposition(ap);
    }
    jmb.setColourBySequence(true);
    setSize(400, 400); // probably should be a configurable/dynamic default here
    initMenus();
    worker = null;
    {
      addingStructures = false;
      worker = new Thread(this);
      worker.start();
    }
    this.addInternalFrameListener(new InternalFrameAdapter()
    {
      public void internalFrameClosing(InternalFrameEvent internalFrameEvent)
      {
        closeViewer();
      }
    });

  }

  /**
   * create a new Jmol containing several structures superimposed using the
   * given alignPanel.
   * 
   * @param ap
   * @param pe
   * @param seqs
   */
  public AppJmol(AlignmentPanel ap, PDBEntry[] pe, SequenceI[][] seqs)
  {
    openNewJmol(ap, pe, seqs);
  }

  /**
   * list of sequenceSet ids associated with the view
   */
  ArrayList<String> _aps = new ArrayList();

  public AlignmentPanel[] getAllAlignmentPanels()
  {
    AlignmentPanel[] t, list = new AlignmentPanel[0];
    for (String setid : _aps)
    {
      AlignmentPanel[] panels = PaintRefresher.getAssociatedPanels(setid);
      if (panels != null)
      {
        t = new AlignmentPanel[list.length + panels.length];
        System.arraycopy(list, 0, t, 0, list.length);
        System.arraycopy(panels, 0, t, list.length, panels.length);
        list = t;
      }
    }

    return list;
  }

  /**
   * list of alignment panels to use for superposition
   */
  Vector<AlignmentPanel> _alignwith = new Vector<AlignmentPanel>();

  /**
   * list of alignment panels that are used for colouring structures by aligned
   * sequences
   */
  Vector<AlignmentPanel> _colourwith = new Vector<AlignmentPanel>();

  /**
   * set the primary alignmentPanel reference and add another alignPanel to the
   * list of ones to use for colouring and aligning
   * 
   * @param nap
   */
  public void addAlignmentPanel(AlignmentPanel nap)
  {
    if (ap == null)
    {
      ap = nap;
    }
    if (!_aps.contains(nap.av.getSequenceSetId()))
    {
      _aps.add(nap.av.getSequenceSetId());
    }
  }

  /**
   * remove any references held to the given alignment panel
   * 
   * @param nap
   */
  public void removeAlignmentPanel(AlignmentPanel nap)
  {
    try
    {
      _alignwith.remove(nap);
      _colourwith.remove(nap);
      if (ap == nap)
      {
        ap = null;
        for (AlignmentPanel aps : getAllAlignmentPanels())
        {
          if (aps != nap)
          {
            ap = aps;
            break;
          }
        }
      }
    } catch (Exception ex)
    {
    }
    if (ap != null)
    {
      buildJmolActionMenu();
    }
  }

  public void useAlignmentPanelForSuperposition(AlignmentPanel nap)
  {
    addAlignmentPanel(nap);
    if (!_alignwith.contains(nap))
    {
      _alignwith.add(nap);
    }
  }

  public void excludeAlignmentPanelForSuperposition(AlignmentPanel nap)
  {
    if (_alignwith.contains(nap))
    {
      _alignwith.remove(nap);
    }
  }

  public void useAlignmentPanelForColourbyseq(AlignmentPanel nap,
          boolean enableColourBySeq)
  {
    useAlignmentPanelForColourbyseq(nap);
    jmb.setColourBySequence(enableColourBySeq);
    seqColour.setSelected(enableColourBySeq);
    jmolColour.setSelected(!enableColourBySeq);
  }

  public void useAlignmentPanelForColourbyseq(AlignmentPanel nap)
  {
    addAlignmentPanel(nap);
    if (!_colourwith.contains(nap))
    {
      _colourwith.add(nap);
    }
  }

  public void excludeAlignmentPanelForColourbyseq(AlignmentPanel nap)
  {
    if (_colourwith.contains(nap))
    {
      _colourwith.remove(nap);
    }
  }

  /**
   * pdb retrieval thread.
   */
  private Thread worker = null;

  /**
   * add a new structure (with associated sequences and chains) to this viewer,
   * retrieving it if necessary first.
   * 
   * @param pdbentry
   * @param seq
   * @param chains
   * @param alignFrame
   * @param align
   *          if true, new structure(s) will be align using associated alignment
   */
  private void addStructure(final PDBEntry pdbentry, final SequenceI[] seq,
          final String[] chains, final boolean b,
          final IProgressIndicator alignFrame)
  {
    if (pdbentry.getFile() == null)
    {
      if (worker != null && worker.isAlive())
      {
        // a retrieval is in progress, wait around and add ourselves to the
        // queue.
        new Thread(new Runnable()
        {
          public void run()
          {
            while (worker != null && worker.isAlive() && _started)
            {
              try
              {
                Thread.sleep(100 + ((int) Math.random() * 100));

              } catch (Exception e)
              {
              }

            }
            // and call ourselves again.
            addStructure(pdbentry, seq, chains, b, alignFrame);
          }
        }).start();
        return;
      }
    }
    // otherwise, start adding the structure.
    jmb.addSequenceAndChain(new PDBEntry[]
    { pdbentry }, new SequenceI[][]
    { seq }, new String[][]
    { chains });
    addingStructures = true;
    _started = false;
    alignAddedStructures = b;
    progressBar = alignFrame; // visual indication happens on caller frame.
    (worker = new Thread(this)).start();
    return;
  }

  private Vector getJmolsFor(AlignmentPanel ap2)
  {
    Vector otherJmols = new Vector();
    // Now this AppJmol is mapped to new sequences. We must add them to
    // the exisiting array
    JInternalFrame[] frames = Desktop.instance.getAllFrames();

    for (int i = 0; i < frames.length; i++)
    {
      if (frames[i] instanceof AppJmol)
      {
        AppJmol topJmol = ((AppJmol) frames[i]);
        if (topJmol.isLinkedWith(ap2))
        {
          otherJmols.addElement(topJmol);
        }
      }
    }
    return otherJmols;
  }

  void initJmol(String command)
  {
    jmb.setFinishedInit(false);
    renderPanel = new RenderPanel();
    // TODO: consider waiting until the structure/view is fully loaded before
    // displaying
    this.getContentPane().add(renderPanel, java.awt.BorderLayout.CENTER);
    jalview.gui.Desktop.addInternalFrame(this, jmb.getViewerTitle(),
            getBounds().width, getBounds().height);
    if (scriptWindow == null)
    {
      BorderLayout bl = new BorderLayout();
      bl.setHgap(0);
      bl.setVgap(0);
      scriptWindow = new JPanel(bl);
      scriptWindow.setVisible(false);
    }
    ;
    jmb.allocateViewer(renderPanel, true, "", null, null, "", scriptWindow,
            null);
    jmb.newJmolPopup(true, "Jmol", true);
    if (command == null)
    {
      command = "";
    }
    jmb.evalStateCommand(command);
    jmb.setFinishedInit(true);
  }

  void setChainMenuItems(Vector chains)
  {
    chainMenu.removeAll();
    if (chains == null)
    {
      return;
    }
    JMenuItem menuItem = new JMenuItem("All");
    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        allChainsSelected = true;
        for (int i = 0; i < chainMenu.getItemCount(); i++)
        {
          if (chainMenu.getItem(i) instanceof JCheckBoxMenuItem)
            ((JCheckBoxMenuItem) chainMenu.getItem(i)).setSelected(true);
        }
        centerViewer();
        allChainsSelected = false;
      }
    });

    chainMenu.add(menuItem);

    for (int c = 0; c < chains.size(); c++)
    {
      menuItem = new JCheckBoxMenuItem(chains.elementAt(c).toString(), true);
      menuItem.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent evt)
        {
          if (!allChainsSelected)
            centerViewer();
        }
      });

      chainMenu.add(menuItem);
    }
  }

  boolean allChainsSelected = false;

  private boolean alignAddedStructures = false;

  void centerViewer()
  {
    Vector toshow = new Vector();
    String lbl;
    int mlength, p, mnum;
    for (int i = 0; i < chainMenu.getItemCount(); i++)
    {
      if (chainMenu.getItem(i) instanceof JCheckBoxMenuItem)
      {
        JCheckBoxMenuItem item = (JCheckBoxMenuItem) chainMenu.getItem(i);
        if (item.isSelected())
        {
          toshow.addElement(item.getText());
        }
      }
    }
    jmb.centerViewer(toshow);
  }

  void closeViewer()
  {
    jmb.closeViewer();
    ap = null;
    _aps.clear();
    _alignwith.clear();
    _colourwith.clear();
    // TODO: check for memory leaks where instance isn't finalised because jmb
    // holds a reference to the window
    jmb = null;
  }

  /**
   * state flag for PDB retrieval thread
   */
  private boolean _started = false;

  public void run()
  {
    _started = true;
    String pdbid = "";
    // todo - record which pdbids were successfuly imported.
    StringBuffer errormsgs = new StringBuffer(), files = new StringBuffer();
    try
    {
      String[] curfiles = jmb.getPdbFile(); // files currently in viewer
      // TODO: replace with reference fetching/transfer code (validate PDBentry
      // as a DBRef?)
      jalview.ws.dbsources.Pdb pdbclient = new jalview.ws.dbsources.Pdb();
      for (int pi = 0; pi < jmb.pdbentry.length; pi++)
      {
        String file = jmb.pdbentry[pi].getFile();
        if (file == null)
        {
          // retrieve the pdb and store it locally
          AlignmentI pdbseq = null;
          pdbid = jmb.pdbentry[pi].getId();
          long hdl = pdbid.hashCode() - System.currentTimeMillis();
          if (progressBar != null)
          {
            progressBar.setProgressBar("Fetching PDB " + pdbid, hdl);
          }
          try
          {
            pdbseq = pdbclient.getSequenceRecords(pdbid = jmb.pdbentry[pi]
                    .getId());
          } catch (OutOfMemoryError oomerror)
          {
            new OOMWarning("Retrieving PDB id " + pdbid, oomerror);
          } catch (Exception ex)
          {
            ex.printStackTrace();
            errormsgs.append("'" + pdbid + "'");
          }
          if (progressBar != null)
          {
            progressBar.setProgressBar("Finished.", hdl);
          }
          if (pdbseq != null)
          {
            // just transfer the file name from the first sequence's first
            // PDBEntry
            file = new File(((PDBEntry) pdbseq.getSequenceAt(0).getPDBId()
                    .elementAt(0)).getFile()).getAbsolutePath();
            jmb.pdbentry[pi].setFile(file);

            files.append(" \"" + Platform.escapeString(file) + "\"");
          }
          else
          {
            errormsgs.append("'" + pdbid + "' ");
          }
        }
        else
        {
          if (curfiles != null && curfiles.length > 0)
          {
            addingStructures = true; // already files loaded.
            for (int c = 0; c < curfiles.length; c++)
            {
              if (curfiles[c].equals(file))
              {
                file = null;
                break;
              }
            }
          }
          if (file != null)
          {
            files.append(" \"" + Platform.escapeString(file) + "\"");
          }
        }
      }
    } catch (OutOfMemoryError oomerror)
    {
      new OOMWarning("Retrieving PDB files: " + pdbid, oomerror);
    } catch (Exception ex)
    {
      ex.printStackTrace();
      errormsgs.append("When retrieving pdbfiles : current was: '" + pdbid
              + "'");
    }
    if (errormsgs.length() > 0)
    {

      JOptionPane.showInternalMessageDialog(Desktop.desktop,
              "The following pdb entries could not be retrieved from the PDB:\n"
                      + errormsgs.toString()
                      + "\nPlease try downloading them manually.",
              "Couldn't load file", JOptionPane.ERROR_MESSAGE);

    }
    long lastnotify = jmb.getLoadNotifiesHandled();
    if (files.length() > 0)
    {
      if (!addingStructures)
      {

        try
        {
          initJmol("load FILES " + files.toString());
        } catch (OutOfMemoryError oomerror)
        {
          new OOMWarning("When trying to open the Jmol viewer!", oomerror);
          Cache.log.debug("File locations are " + files);
        } catch (Exception ex)
        {
          Cache.log.error("Couldn't open Jmol viewer!", ex);
        }
      }
      else
      {
        StringBuffer cmd = new StringBuffer();
        cmd.append("loadingJalviewdata=true\nload APPEND ");
        cmd.append(files.toString());
        cmd.append("\nloadingJalviewdata=null");
        final String command = cmd.toString();
        cmd = null;
        lastnotify = jmb.getLoadNotifiesHandled();

        try
        {
          jmb.evalStateCommand(command);
        } catch (OutOfMemoryError oomerror)
        {
          new OOMWarning(
                  "When trying to add structures to the Jmol viewer!",
                  oomerror);
          Cache.log.debug("File locations are " + files);
        } catch (Exception ex)
        {
          Cache.log.error("Couldn't add files to Jmol viewer!", ex);
        }
      }

      // need to wait around until script has finished
      while (addingStructures ? lastnotify >= jmb.getLoadNotifiesHandled()
              : (jmb.isFinishedInit() && jmb.getPdbFile().length != jmb.pdbentry.length))
      {
        try
        {
          Cache.log.debug("Waiting around for jmb notify.");
          Thread.sleep(35);
        } catch (Exception e)
        {
        }
      }
      // refresh the sequence colours for the new structure(s)
      for (AlignmentPanel ap : _colourwith)
      {
        jmb.updateColours(ap);
      }
      // do superposition if asked to
      if (alignAddedStructures)
      {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            alignStructs_withAllAlignPanels();
            // jmb.superposeStructures(ap.av.getAlignment(), -1, null);
          }
        });
        alignAddedStructures = false;
      }
      addingStructures = false;

    }
    _started = false;
    worker = null;
  }

  public void pdbFile_actionPerformed(ActionEvent actionEvent)
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"));

    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Save PDB File");
    chooser.setToolTipText("Save");

    int value = chooser.showSaveDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      try
      {
        // TODO: cope with multiple PDB files in view
        BufferedReader in = new BufferedReader(new FileReader(
                jmb.getPdbFile()[0]));
        File outFile = chooser.getSelectedFile();

        PrintWriter out = new PrintWriter(new FileOutputStream(outFile));
        String data;
        while ((data = in.readLine()) != null)
        {
          if (!(data.indexOf("<PRE>") > -1 || data.indexOf("</PRE>") > -1))
          {
            out.println(data);
          }
        }
        out.close();
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }

  public void viewMapping_actionPerformed(ActionEvent actionEvent)
  {
    jalview.gui.CutAndPasteTransfer cap = new jalview.gui.CutAndPasteTransfer();
    try
    {
      for (int pdbe = 0; pdbe < jmb.pdbentry.length; pdbe++)
      {
        cap.appendText(jmb.printMapping(jmb.pdbentry[pdbe].getFile()));
        cap.appendText("\n");
      }
    } catch (OutOfMemoryError e)
    {
      new OOMWarning(
              "composing sequence-structure alignments for display in text box.",
              e);
      cap.dispose();
      return;
    }
    jalview.gui.Desktop.addInternalFrame(cap, "PDB - Sequence Mapping",
            550, 600);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void eps_actionPerformed(ActionEvent e)
  {
    makePDBImage(jalview.util.ImageMaker.EPS);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void png_actionPerformed(ActionEvent e)
  {
    makePDBImage(jalview.util.ImageMaker.PNG);
  }

  void makePDBImage(int type)
  {
    int width = getWidth();
    int height = getHeight();

    jalview.util.ImageMaker im;

    if (type == jalview.util.ImageMaker.PNG)
    {
      im = new jalview.util.ImageMaker(this, jalview.util.ImageMaker.PNG,
              "Make PNG image from view", width, height, null, null);
    }
    else
    {
      im = new jalview.util.ImageMaker(this, jalview.util.ImageMaker.EPS,
              "Make EPS file from view", width, height, null,
              this.getTitle());
    }

    if (im.getGraphics() != null)
    {
      Rectangle rect = new Rectangle(width, height);
      jmb.viewer.renderScreenImage(im.getGraphics(), rect.getSize(), rect);
      im.writeImage();
    }
  }

  public void jmolColour_actionPerformed(ActionEvent actionEvent)
  {
    if (jmolColour.isSelected())
    {
      // disable automatic sequence colouring.
      jmb.setColourBySequence(false);
    }
  }

  public void seqColour_actionPerformed(ActionEvent actionEvent)
  {
    jmb.setColourBySequence(seqColour.isSelected());
    if (_colourwith == null)
    {
      _colourwith = new Vector<AlignmentPanel>();
    }
    if (jmb.isColourBySequence())
    {
      if (!jmb.isLoadingFromArchive())
      {
        if (_colourwith.size() == 0 && ap != null)
        {
          // Make the currently displayed alignment panel the associated view
          _colourwith.add(ap.alignFrame.alignPanel);
        }
      }
      // Set the colour using the current view for the associated alignframe
      for (AlignmentPanel ap : _colourwith)
      {
        jmb.colourBySequence(ap.av.showSequenceFeatures, ap);
      }
    }
  }

  public void chainColour_actionPerformed(ActionEvent actionEvent)
  {
    chainColour.setSelected(true);
    jmb.colourByChain();
  }

  public void chargeColour_actionPerformed(ActionEvent actionEvent)
  {
    chargeColour.setSelected(true);
    jmb.colourByCharge();
  }

  public void zappoColour_actionPerformed(ActionEvent actionEvent)
  {
    zappoColour.setSelected(true);
    jmb.setJalviewColourScheme(new ZappoColourScheme());
  }

  public void taylorColour_actionPerformed(ActionEvent actionEvent)
  {
    taylorColour.setSelected(true);
    jmb.setJalviewColourScheme(new TaylorColourScheme());
  }

  public void hydroColour_actionPerformed(ActionEvent actionEvent)
  {
    hydroColour.setSelected(true);
    jmb.setJalviewColourScheme(new HydrophobicColourScheme());
  }

  public void helixColour_actionPerformed(ActionEvent actionEvent)
  {
    helixColour.setSelected(true);
    jmb.setJalviewColourScheme(new HelixColourScheme());
  }

  public void strandColour_actionPerformed(ActionEvent actionEvent)
  {
    strandColour.setSelected(true);
    jmb.setJalviewColourScheme(new StrandColourScheme());
  }

  public void turnColour_actionPerformed(ActionEvent actionEvent)
  {
    turnColour.setSelected(true);
    jmb.setJalviewColourScheme(new TurnColourScheme());
  }

  public void buriedColour_actionPerformed(ActionEvent actionEvent)
  {
    buriedColour.setSelected(true);
    jmb.setJalviewColourScheme(new BuriedColourScheme());
  }

  public void purinePyrimidineColour_actionPerformed(ActionEvent actionEvent)
  {
    setJalviewColourScheme(new PurinePyrimidineColourScheme());
  }

  public void userColour_actionPerformed(ActionEvent actionEvent)
  {
    userColour.setSelected(true);
    new UserDefinedColours(this, null);
  }

  public void backGround_actionPerformed(ActionEvent actionEvent)
  {
    java.awt.Color col = JColorChooser.showDialog(this,
            "Select Background Colour", null);
    if (col != null)
    {
      jmb.setBackgroundColour(col);
    }
  }

  public void jmolHelp_actionPerformed(ActionEvent actionEvent)
  {
    try
    {
      jalview.util.BrowserLauncher
              .openURL("http://jmol.sourceforge.net/docs/JmolUserGuide/");
    } catch (Exception ex)
    {
    }
  }

  public void showConsole(boolean showConsole)
  {

    if (showConsole)
    {
      if (splitPane == null)
      {
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(renderPanel);
        splitPane.setBottomComponent(scriptWindow);
        this.getContentPane().add(splitPane, BorderLayout.CENTER);
        splitPane.setDividerLocation(getHeight() - 200);
        scriptWindow.setVisible(true);
        scriptWindow.validate();
        splitPane.validate();
      }

    }
    else
    {
      if (splitPane != null)
      {
        splitPane.setVisible(false);
      }

      splitPane = null;

      this.getContentPane().add(renderPanel, BorderLayout.CENTER);
    }

    validate();
  }

  class RenderPanel extends JPanel
  {
    final Dimension currentSize = new Dimension();

    final Rectangle rectClip = new Rectangle();

    public void paintComponent(Graphics g)
    {
      getSize(currentSize);
      g.getClipBounds(rectClip);

      if (jmb.fileLoadingError != null)
      {
        g.setColor(Color.black);
        g.fillRect(0, 0, currentSize.width, currentSize.height);
        g.setColor(Color.white);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        g.drawString("Error loading file...", 20, currentSize.height / 2);
        StringBuffer sb = new StringBuffer();
        int lines = 0;
        for (int e = 0; e < jmb.pdbentry.length; e++)
        {
          sb.append(jmb.pdbentry[e].getId());
          if (e < jmb.pdbentry.length - 1)
          {
            sb.append(",");
          }

          if (e == jmb.pdbentry.length - 1 || sb.length() > 20)
          {
            lines++;
            g.drawString(sb.toString(), 20, currentSize.height / 2 - lines
                    * g.getFontMetrics().getHeight());
          }
        }
      }
      else if (jmb == null || jmb.viewer == null || !jmb.isFinishedInit())
      {
        g.setColor(Color.black);
        g.fillRect(0, 0, currentSize.width, currentSize.height);
        g.setColor(Color.white);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        g.drawString("Retrieving PDB data....", 20, currentSize.height / 2);
      }
      else
      {
        jmb.viewer.renderScreenImage(g, currentSize, rectClip);
      }
    }
  }

  String viewId = null;

  public String getViewId()
  {
    if (viewId == null)
    {
      viewId = System.currentTimeMillis() + "." + this.hashCode();
    }
    return viewId;
  }

  public void updateTitleAndMenus()
  {
    if (jmb.fileLoadingError != null && jmb.fileLoadingError.length() > 0)
    {
      repaint();
      return;
    }
    setChainMenuItems(jmb.chainNames);

    this.setTitle(jmb.getViewerTitle());
    if (jmb.getPdbFile().length > 1 && jmb.sequence.length > 1)
    {
      jmolActionMenu.setVisible(true);
    }
    if (!jmb.isLoadingFromArchive())
    {
      seqColour_actionPerformed(null);
    }
  }

  protected void buildJmolActionMenu()
  {
    if (_alignwith == null)
    {
      _alignwith = new Vector<AlignmentPanel>();
    }
    if (_alignwith.size() == 0 && ap != null)
    {
      _alignwith.add(ap);
    }
    ;
    for (Component c : jmolActionMenu.getMenuComponents())
    {
      if (c != alignStructs)
      {
        jmolActionMenu.remove((JMenuItem) c);
      }
    }
    final ItemListener handler;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GStructureViewer#alignStructs_actionPerformed(java.awt.event
   * .ActionEvent)
   */
  @Override
  protected void alignStructs_actionPerformed(ActionEvent actionEvent)
  {
    alignStructs_withAllAlignPanels();
  }

  private void alignStructs_withAllAlignPanels()
  {
    if (ap == null)
    {
      return;
    }
    ;
    if (_alignwith.size() == 0)
    {
      _alignwith.add(ap);
    }
    ;
    try
    {
      AlignmentI[] als = new Alignment[_alignwith.size()];
      ColumnSelection[] alc = new ColumnSelection[_alignwith.size()];
      int[] alm = new int[_alignwith.size()];
      int a = 0;

      for (AlignmentPanel ap : _alignwith)
      {
        als[a] = ap.av.getAlignment();
        alm[a] = -1;
        alc[a++] = ap.av.getColumnSelection();
      }
      jmb.superposeStructures(als, alm, alc);
    } catch (Exception e)
    {
      StringBuffer sp = new StringBuffer();
      for (AlignmentPanel ap : _alignwith)
      {
        sp.append("'" + ap.alignFrame.getTitle() + "' ");
      }
      Cache.log.info("Couldn't align structures with the " + sp.toString()
              + "associated alignment panels.", e);

    }

  }

  public void setJalviewColourScheme(ColourSchemeI ucs)
  {
    jmb.setJalviewColourScheme(ucs);

  }

  /**
   * 
   * @param alignment
   * @return first alignment panel displaying given alignment, or the default
   *         alignment panel
   */
  public AlignmentPanel getAlignmentPanelFor(AlignmentI alignment)
  {
    for (AlignmentPanel ap : getAllAlignmentPanels())
    {
      if (ap.av.getAlignment() == alignment)
      {
        return ap;
      }
    }
    return ap;
  }

  /**
   * 
   * @param ap2
   * @return true if this Jmol instance is linked with the given alignPanel
   */
  public boolean isLinkedWith(AlignmentPanel ap2)
  {
    return _aps.contains(ap2.av.getSequenceSetId());
  }

  public boolean isUsedforaligment(AlignmentPanel ap2)
  {

    return (_alignwith != null) && _alignwith.contains(ap2);
  }

  public boolean isUsedforcolourby(AlignmentPanel ap2)
  {
    return (_colourwith != null) && _colourwith.contains(ap2);
  }

  /**
   * 
   * @return TRUE if the view is NOT being coloured by sequence associations.
   */
  public boolean isColouredByJmol()
  {
    return !jmb.isColourBySequence();
  }

}
