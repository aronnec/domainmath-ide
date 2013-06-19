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
package jalview.ext.jmol;

import jalview.api.AlignmentViewPanel;
import jalview.api.FeatureRenderer;
import jalview.api.SequenceRenderer;
import jalview.api.SequenceStructureBinding;
import jalview.api.StructureSelectionManagerProvider;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.ColumnSelection;
import jalview.datamodel.PDBEntry;
import jalview.datamodel.SequenceI;
import jalview.io.AppletFormatAdapter;
import jalview.schemes.ColourSchemeI;
import jalview.schemes.ResidueProperties;
import jalview.structure.StructureListener;
import jalview.structure.StructureMapping;
import jalview.structure.StructureSelectionManager;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.net.URL;
import java.security.AccessControlException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolAppConsoleInterface;
import org.jmol.api.JmolSelectionListener;
import org.jmol.api.JmolStatusListener;
import org.jmol.api.JmolViewer;
import org.jmol.constant.EnumCallback;
import org.jmol.popup.JmolPopup;

public abstract class JalviewJmolBinding implements StructureListener,
        JmolStatusListener, SequenceStructureBinding,
        JmolSelectionListener, ComponentListener,
        StructureSelectionManagerProvider

{
  /**
   * set if Jmol state is being restored from some source - instructs binding
   * not to apply default display style when structure set is updated for first
   * time.
   */
  private boolean loadingFromArchive = false;
  
  /**
   * second flag to indicate if the jmol viewer should ignore sequence colouring
   * events from the structure manager because the GUI is still setting up
   */
  private boolean loadingFinished = true;

  /**
   * state flag used to check if the Jmol viewer's paint method can be called
   */
  private boolean finishedInit = false;

  public boolean isFinishedInit()
  {
    return finishedInit;
  }

  public void setFinishedInit(boolean finishedInit)
  {
    this.finishedInit = finishedInit;
  }

  boolean allChainsSelected = false;

  /**
   * when true, try to search the associated datamodel for sequences that are
   * associated with any unknown structures in the Jmol view.
   */
  private boolean associateNewStructs = false;

  Vector atomsPicked = new Vector();

  public Vector chainNames;

  Hashtable chainFile;

  /**
   * array of target chains for seuqences - tied to pdbentry and sequence[]
   */
  protected String[][] chains;

  boolean colourBySequence = true;

  StringBuffer eval = new StringBuffer();

  public String fileLoadingError;

  /**
   * the default or current model displayed if the model cannot be identified
   * from the selection message
   */
  int frameNo = 0;

  protected JmolPopup jmolpopup;

  String lastCommand;

  String lastMessage;

  boolean loadedInline;

  /**
   * current set of model filenames loaded in the Jmol instance
   */
  String[] modelFileNames = null;

  public PDBEntry[] pdbentry;

  /**
   * datasource protocol for access to PDBEntrylatest
   */
  String protocol = null;

  StringBuffer resetLastRes = new StringBuffer();

  /**
   * sequences mapped to each pdbentry
   */
  public SequenceI[][] sequence;

  public StructureSelectionManager ssm;

  public JmolViewer viewer;

  public JalviewJmolBinding(StructureSelectionManager ssm,
          PDBEntry[] pdbentry, SequenceI[][] sequenceIs, String[][] chains,
          String protocol)
  {
    this.ssm = ssm;
    this.sequence = sequenceIs;
    this.chains = chains;
    this.pdbentry = pdbentry;
    this.protocol = protocol;
    if (chains == null)
    {
      this.chains = new String[pdbentry.length][];
    }
    /*
     * viewer = JmolViewer.allocateViewer(renderPanel, new SmarterJmolAdapter(),
     * "jalviewJmol", ap.av.applet .getDocumentBase(),
     * ap.av.applet.getCodeBase(), "", this);
     * 
     * jmolpopup = JmolPopup.newJmolPopup(viewer, true, "Jmol", true);
     */
  }

  public JalviewJmolBinding(StructureSelectionManager ssm,
          JmolViewer viewer2)
  {
    this.ssm = ssm;
    viewer = viewer2;
    viewer.setJmolStatusListener(this);
    viewer.addSelectionListener(this);
  }

  /**
   * construct a title string for the viewer window based on the data jalview
   * knows about
   * 
   * @return
   */
  public String getViewerTitle()
  {
    if (sequence == null || pdbentry == null || sequence.length < 1
            || pdbentry.length < 1 || sequence[0].length < 1)
    {
      return ("Jalview Jmol Window");
    }
    // TODO: give a more informative title when multiple structures are
    // displayed.
    StringBuffer title = new StringBuffer(sequence[0][0].getName() + ":"
            + pdbentry[0].getId());

    if (pdbentry[0].getProperty() != null)
    {
      if (pdbentry[0].getProperty().get("method") != null)
      {
        title.append(" Method: ");
        title.append(pdbentry[0].getProperty().get("method"));
      }
      if (pdbentry[0].getProperty().get("chains") != null)
      {
        title.append(" Chain:");
        title.append(pdbentry[0].getProperty().get("chains"));
      }
    }
    return title.toString();
  }

  /**
   * prepare the view for a given set of models/chains. chainList contains
   * strings of the form 'pdbfilename:Chaincode'
   * 
   * @param chainList
   *          list of chains to make visible
   */
  public void centerViewer(Vector chainList)
  {
    StringBuffer cmd = new StringBuffer();
    String lbl;
    int mlength, p;
    for (int i = 0, iSize = chainList.size(); i < iSize; i++)
    {
      mlength = 0;
      lbl = (String) chainList.elementAt(i);
      do
      {
        p = mlength;
        mlength = lbl.indexOf(":", p);
      } while (p < mlength && mlength < (lbl.length() - 2));
      // TODO: lookup each pdb id and recover proper model number for it.
      cmd.append(":" + lbl.substring(mlength + 1) + " /"
              + (1 + getModelNum((String) chainFile.get(lbl))) + " or ");
    }
    if (cmd.length() > 0)
      cmd.setLength(cmd.length() - 4);
    evalStateCommand("select *;restrict " + cmd + ";cartoon;center " + cmd);
  }

  public void closeViewer()
  {
    viewer.setModeMouse(org.jmol.viewer.JmolConstants.MOUSE_NONE);
    // remove listeners for all structures in viewer
    ssm.removeStructureViewerListener(this, this.getPdbFile());
    // and shut down jmol
    viewer.evalStringQuiet("zap");
    viewer.setJmolStatusListener(null);
    lastCommand = null;
    viewer = null;
    releaseUIResources();
  }

  /**
   * called by JalviewJmolbinding after closeViewer is called - release any
   * resources and references so they can be garbage collected.
   */
  protected abstract void releaseUIResources();

  public void colourByChain()
  {
    colourBySequence = false;
    // TODO: colour by chain should colour each chain distinctly across all
    // visible models
    // TODO: http://issues.jalview.org/browse/JAL-628
    evalStateCommand("select *;color chain");
  }

  public void colourByCharge()
  {
    colourBySequence = false;
    evalStateCommand("select *;color white;select ASP,GLU;color red;"
            + "select LYS,ARG;color blue;select CYS;color yellow");
  }

  /**
   * superpose the structures associated with sequences in the alignment
   * according to their corresponding positions.
   */
  public void superposeStructures(AlignmentI alignment)
  {
    superposeStructures(alignment, -1, null);
  }

  /**
   * superpose the structures associated with sequences in the alignment
   * according to their corresponding positions. ded)
   * 
   * @param refStructure
   *          - select which pdb file to use as reference (default is -1 - the
   *          first structure in the alignment)
   */
  public void superposeStructures(AlignmentI alignment, int refStructure)
  {
    superposeStructures(alignment, refStructure, null);
  }

  /**
   * superpose the structures associated with sequences in the alignment
   * according to their corresponding positions. ded)
   * 
   * @param refStructure
   *          - select which pdb file to use as reference (default is -1 - the
   *          first structure in the alignment)
   * @param hiddenCols
   *          TODO
   */
  public void superposeStructures(AlignmentI alignment, int refStructure,
          ColumnSelection hiddenCols)
  {
    superposeStructures(new AlignmentI[]
    { alignment }, new int[]
    { refStructure }, new ColumnSelection[]
    { hiddenCols });
  }

  public void superposeStructures(AlignmentI[] _alignment,
          int[] _refStructure, ColumnSelection[] _hiddenCols)
  {
    String[] files = getPdbFile();
    StringBuffer selectioncom = new StringBuffer();
    assert (_alignment.length == _refStructure.length && _alignment.length != _hiddenCols.length);
    // union of all aligned positions are collected together.
    for (int a = 0; a < _alignment.length; a++)
    {
      int refStructure = _refStructure[a];
      AlignmentI alignment = _alignment[a];
      ColumnSelection hiddenCols = _hiddenCols[a];
      if (a > 0
              && selectioncom.length() > 0
              && !selectioncom.substring(selectioncom.length() - 1).equals(
                      "|"))
      {
        selectioncom.append("|");
      }
      // process this alignment
      if (refStructure >= files.length)
      {
        System.err.println("Invalid reference structure value "
                + refStructure);
        refStructure = -1;
      }
      if (refStructure < -1)
      {
        refStructure = -1;
      }
      StringBuffer command = new StringBuffer();

      boolean matched[] = new boolean[alignment.getWidth()];
      for (int m = 0; m < matched.length; m++)
      {

        matched[m] = (hiddenCols != null) ? hiddenCols.isVisible(m) : true;
      }

      int commonrpositions[][] = new int[files.length][alignment.getWidth()];
      String isel[] = new String[files.length];
      // reference structure - all others are superposed in it
      String[] targetC = new String[files.length];
      String[] chainNames = new String[files.length];
      for (int pdbfnum = 0; pdbfnum < files.length; pdbfnum++)
      {
        StructureMapping[] mapping = ssm.getMapping(files[pdbfnum]);
        // RACE CONDITION - getMapping only returns Jmol loaded filenames once
        // Jmol callback has completed.
        if (mapping == null || mapping.length < 1)
          continue;

        int lastPos = -1;
        for (int s = 0; s < sequence[pdbfnum].length; s++)
        {
          for (int sp, m = 0; m < mapping.length; m++)
          {
            if (mapping[m].getSequence() == sequence[pdbfnum][s]
                    && (sp = alignment.findIndex(sequence[pdbfnum][s])) > -1)
            {
              if (refStructure == -1)
              {
                refStructure = pdbfnum;
              }
              SequenceI asp = alignment.getSequenceAt(sp);
              for (int r = 0; r < matched.length; r++)
              {
                if (!matched[r])
                {
                  continue;
                }
                matched[r] = false; // assume this is not a good site
                if (r >= asp.getLength())
                {
                  continue;
                }

                if (jalview.util.Comparison.isGap(asp.getCharAt(r)))
                {
                  // no mapping to gaps in sequence
                  continue;
                }
                int t = asp.findPosition(r); // sequence position
                int apos = mapping[m].getAtomNum(t);
                int pos = mapping[m].getPDBResNum(t);

                if (pos < 1 || pos == lastPos)
                {
                  // can't align unmapped sequence
                  continue;
                }
                matched[r] = true; // this is a good ite
                lastPos = pos;
                // just record this residue position
                commonrpositions[pdbfnum][r] = pos;
              }
              // create model selection suffix
              isel[pdbfnum] = "/" + (pdbfnum + 1) + ".1";
              if (mapping[m].getChain() == null
                      || mapping[m].getChain().trim().length() == 0)
              {
                targetC[pdbfnum] = "";
              }
              else
              {
                targetC[pdbfnum] = ":" + mapping[m].getChain();
              }
              chainNames[pdbfnum] = mapping[m].getPdbId()
                      + targetC[pdbfnum];
              // move on to next pdb file
              s = sequence[pdbfnum].length;
              break;
            }
          }
        }
      }
      String[] selcom = new String[files.length];
      int nmatched = 0;
      // generate select statements to select regions to superimpose structures
      {
        for (int pdbfnum = 0; pdbfnum < files.length; pdbfnum++)
        {
          String chainCd = targetC[pdbfnum];
          int lpos = -1;
          boolean run = false;
          StringBuffer molsel = new StringBuffer();
          molsel.append("{");
          for (int r = 0; r < matched.length; r++)
          {
            if (matched[r])
            {
              if (pdbfnum == 0)
              {
                nmatched++;
              }
              if (lpos != commonrpositions[pdbfnum][r] - 1)
              {
                // discontinuity
                if (lpos != -1)
                {
                  molsel.append(lpos);
                  molsel.append(chainCd);
                  // molsel.append("} {");
                  molsel.append("|");
                }
              }
              else
              {
                // continuous run - and lpos >-1
                if (!run)
                {
                  // at the beginning, so add dash
                  molsel.append(lpos);
                  molsel.append("-");
                }
                run = true;
              }
              lpos = commonrpositions[pdbfnum][r];
              // molsel.append(lpos);
            }
          }
          // add final selection phrase
          if (lpos != -1)
          {
            molsel.append(lpos);
            molsel.append(chainCd);
            molsel.append("}");
          }
          selcom[pdbfnum] = molsel.toString();
          selectioncom.append("((");
          selectioncom.append(selcom[pdbfnum].substring(1,
                  selcom[pdbfnum].length() - 1));
          selectioncom.append(" )& ");
          selectioncom.append(pdbfnum + 1);
          selectioncom.append(".1)");
          if (pdbfnum < files.length - 1)
          {
            selectioncom.append("|");
          }
        }
      }
      // TODO: consider bailing if nmatched less than 4 because superposition
      // not
      // well defined.
      // TODO: refactor superposable position search (above) from jmol selection
      // construction (below)
      for (int pdbfnum = 0; pdbfnum < files.length; pdbfnum++)
      {
        if (pdbfnum == refStructure)
        {
          continue;
        }
        command.append("echo ");
        command.append("\"Superposing (");
        command.append(chainNames[pdbfnum]);
        command.append(") against reference (");
        command.append(chainNames[refStructure]);
        command.append(")\";\ncompare ");
        command.append("{");
        command.append(1 + pdbfnum);
        command.append(".1} {");
        command.append(1 + refStructure);
        command.append(".1} SUBSET {*.CA | *.P} ATOMS ");

        // form the matched pair strings
        String sep = "";
        for (int s = 0; s < 2; s++)
        {
          command.append(selcom[(s == 0 ? pdbfnum : refStructure)]);
        }
        command.append(" ROTATE TRANSLATE;\n");
      }
      System.out.println("Select regions:\n" + selectioncom.toString());
      evalStateCommand("select *; cartoons off; backbone; select ("
              + selectioncom.toString() + "); cartoons; ");
      // selcom.append("; ribbons; ");
      System.out.println("Superimpose command(s):\n" + command.toString());

      evalStateCommand(command.toString());
    }
    if (selectioncom.length() > 0)
    {// finally, mark all regions that were superposed.
      if (selectioncom.substring(selectioncom.length() - 1).equals("|"))
      {
        selectioncom.setLength(selectioncom.length() - 1);
      }
      System.out.println("Select regions:\n" + selectioncom.toString());
      evalStateCommand("select *; cartoons off; backbone; select ("
              + selectioncom.toString() + "); cartoons; ");
      // evalStateCommand("select *; backbone; select "+selcom.toString()+"; cartoons; center "+selcom.toString());
    }
  }

  public void evalStateCommand(String command)
  {
    jmolHistory(false);
    if (lastCommand == null || !lastCommand.equals(command))
    {
      viewer.evalStringQuiet(command + "\n");
    }
    jmolHistory(true);
    lastCommand = command;
  }

  /**
   * colour any structures associated with sequences in the given alignment
   * using the getFeatureRenderer() and getSequenceRenderer() renderers but only
   * if colourBySequence is enabled.
   */
  public void colourBySequence(boolean showFeatures,
          jalview.api.AlignmentViewPanel alignmentv)
  {
    if (!colourBySequence || !loadingFinished)
      return;
    if (ssm == null)
    {
      return;
    }
    String[] files = getPdbFile();

    SequenceRenderer sr = getSequenceRenderer(alignmentv);

    FeatureRenderer fr = null;
    if (showFeatures)
    {
      fr = getFeatureRenderer(alignmentv);
    }
    AlignmentI alignment = alignmentv.getAlignment();

    for (jalview.structure.StructureMappingcommandSet cpdbbyseq : JmolCommands
            .getColourBySequenceCommand(ssm, files, sequence, sr, fr,
                    alignment))
      for (String cbyseq : cpdbbyseq.commands)
      {
        evalStateCommand(cbyseq);
      }
  }

  public boolean isColourBySequence()
  {
    return colourBySequence;
  }

  public void setColourBySequence(boolean colourBySequence)
  {
    this.colourBySequence = colourBySequence;
  }

  public void createImage(String file, String type, int quality)
  {
    System.out.println("JMOL CREATE IMAGE");
  }

  public String createImage(String fileName, String type,
          Object textOrBytes, int quality)
  {
    System.out.println("JMOL CREATE IMAGE");
    return null;
  }

  public String eval(String strEval)
  {
    // System.out.println(strEval);
    // "# 'eval' is implemented only for the applet.";
    return null;
  }

  // End StructureListener
  // //////////////////////////

  public float[][] functionXY(String functionName, int x, int y)
  {
    return null;
  }

  public float[][][] functionXYZ(String functionName, int nx, int ny, int nz)
  {
    // TODO Auto-generated method stub
    return null;
  }

  public Color getColour(int atomIndex, int pdbResNum, String chain,
          String pdbfile)
  {
    if (getModelNum(pdbfile) < 0)
      return null;
    // TODO: verify atomIndex is selecting correct model.
    return new Color(viewer.getAtomArgb(atomIndex));
  }

  /**
   * returns the current featureRenderer that should be used to colour the
   * structures
   * 
   * @param alignment
   * 
   * @return
   */
  public abstract FeatureRenderer getFeatureRenderer(
          AlignmentViewPanel alignment);

  /**
   * instruct the Jalview binding to update the pdbentries vector if necessary
   * prior to matching the jmol view's contents to the list of structure files
   * Jalview knows about.
   */
  public abstract void refreshPdbEntries();

  private int getModelNum(String modelFileName)
  {
    String[] mfn = getPdbFile();
    if (mfn == null)
    {
      return -1;
    }
    for (int i = 0; i < mfn.length; i++)
    {
      if (mfn[i].equalsIgnoreCase(modelFileName))
        return i;
    }
    return -1;
  }

  /**
   * map between index of model filename returned from getPdbFile and the first
   * index of models from this file in the viewer. Note - this is not trimmed -
   * use getPdbFile to get number of unique models.
   */
  private int _modelFileNameMap[];

  // ////////////////////////////////
  // /StructureListener
  public synchronized String[] getPdbFile()
  {
    if (viewer == null)
    {
      return new String[0];
    }
    if (modelFileNames == null)
    {

      String mset[] = new String[viewer.getModelCount()];
      _modelFileNameMap = new int[mset.length];
      int j = 1;
      String m = viewer.getModelFileName(0);
      if (m != null)
      {
        try
        {
          mset[0] = new File(m).getAbsolutePath();
        } catch (AccessControlException x)
        {
          // usually not allowed to do this in applet, so keep raw handle
          mset[0] = m;
          // System.err.println("jmolBinding: Using local file string from Jmol: "+m);
        }
      }
      for (int i = 1; i < mset.length; i++)
      {
        m = viewer.getModelFileName(i);
        if (m != null)
        {
          try
          {
            mset[j] = new File(m).getAbsolutePath();
          } catch (AccessControlException x)
          {
            // usually not allowed to do this in applet, so keep raw handle
            mset[j] = m;
            // System.err.println("jmolBinding: Using local file string from Jmol: "+m);
          }
        }
        _modelFileNameMap[j] = i; // record the model index for the filename
        // skip any additional models in the same file (NMR structures)
        if ((mset[j] == null ? mset[j] != mset[j - 1]
                : (mset[j - 1] == null || !mset[j].equals(mset[j - 1]))))
        {
          j++;
        }
      }
      modelFileNames = new String[j];
      System.arraycopy(mset, 0, modelFileNames, 0, j);
    }
    return modelFileNames;
  }

  /**
   * map from string to applet
   */
  public Map getRegistryInfo()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * returns the current sequenceRenderer that should be used to colour the
   * structures
   * 
   * @param alignment
   * 
   * @return
   */
  public abstract SequenceRenderer getSequenceRenderer(
          AlignmentViewPanel alignment);

  // ///////////////////////////////
  // JmolStatusListener

  public void handlePopupMenu(int x, int y)
  {
    jmolpopup.show(x, y);
  }

  // jmol/ssm only
  public void highlightAtom(int atomIndex, int pdbResNum, String chain,
          String pdbfile)
  {
    if (modelFileNames == null)
    {
      return;
    }

    // look up file model number for this pdbfile
    int mdlNum = 0;
    String fn;
    // may need to adjust for URLencoding here - we don't worry about that yet.
    while (mdlNum < modelFileNames.length
            && !pdbfile.equals(modelFileNames[mdlNum]))
    {
      // System.out.println("nomatch:"+pdbfile+"\nmodelfn:"+fn);
      mdlNum++;
    }
    if (mdlNum == modelFileNames.length)
    {
      return;
    }

    jmolHistory(false);
    // if (!pdbfile.equals(pdbentry.getFile()))
    // return;
    if (resetLastRes.length() > 0)
    {
      viewer.evalStringQuiet(resetLastRes.toString());
    }

    eval.setLength(0);
    eval.append("select " + pdbResNum); // +modelNum

    resetLastRes.setLength(0);
    resetLastRes.append("select " + pdbResNum); // +modelNum

    eval.append(":");
    resetLastRes.append(":");
    if (!chain.equals(" "))
    {
      eval.append(chain);
      resetLastRes.append(chain);
    }
    {
      eval.append(" /" + (mdlNum + 1));
      resetLastRes.append("/" + (mdlNum + 1));
    }
    eval.append(";wireframe 100;" + eval.toString() + " and not hetero;");

    resetLastRes.append(";wireframe 0;" + resetLastRes.toString()
            + " and not hetero; spacefill 0;");

    eval.append("spacefill 200;select none");

    viewer.evalStringQuiet(eval.toString());
    jmolHistory(true);

  }

  boolean debug = true;

  private void jmolHistory(boolean enable)
  {
    viewer.evalStringQuiet("History " + ((debug || enable) ? "on" : "off"));
  }

  public void loadInline(String string)
  {
    loadedInline = true;
    // TODO: re JAL-623
    // viewer.loadInline(strModel, isAppend);
    // could do this:
    // construct fake fullPathName and fileName so we can identify the file
    // later.
    // Then, construct pass a reader for the string to Jmol.
    // ((org.jmol.Viewer.Viewer) viewer).loadModelFromFile(fullPathName,
    // fileName, null, reader, false, null, null, 0);
    viewer.openStringInline(string);
  }

  public void mouseOverStructure(int atomIndex, String strInfo)
  {
    int pdbResNum;
    int alocsep = strInfo.indexOf("^");
    int mdlSep = strInfo.indexOf("/");
    int chainSeparator = strInfo.indexOf(":"), chainSeparator1 = -1;

    if (chainSeparator == -1)
    {
      chainSeparator = strInfo.indexOf(".");
      if (mdlSep > -1 && mdlSep < chainSeparator)
      {
        chainSeparator1 = chainSeparator;
        chainSeparator = mdlSep;
      }
    }
    // handle insertion codes
    if (alocsep != -1)
    {
      pdbResNum = Integer.parseInt(strInfo.substring(
              strInfo.indexOf("]") + 1, alocsep));

    }
    else
    {
      pdbResNum = Integer.parseInt(strInfo.substring(
              strInfo.indexOf("]") + 1, chainSeparator));
    }
    String chainId;

    if (strInfo.indexOf(":") > -1)
      chainId = strInfo.substring(strInfo.indexOf(":") + 1,
              strInfo.indexOf("."));
    else
    {
      chainId = " ";
    }

    String pdbfilename = modelFileNames[frameNo]; // default is first or current
    // model
    if (mdlSep > -1)
    {
      if (chainSeparator1 == -1)
      {
        chainSeparator1 = strInfo.indexOf(".", mdlSep);
      }
      String mdlId = (chainSeparator1 > -1) ? strInfo.substring(mdlSep + 1,
              chainSeparator1) : strInfo.substring(mdlSep + 1);
      try
      {
        // recover PDB filename for the model hovered over.
        int _mp = _modelFileNameMap.length - 1, mnumber = new Integer(mdlId)
                .intValue() - 1;
        while (mnumber < _modelFileNameMap[_mp])
        {
          _mp--;
        }
        pdbfilename = modelFileNames[_mp];
        if (pdbfilename == null)
        {
          pdbfilename = new File(viewer.getModelFileName(mnumber))
                  .getAbsolutePath();
        }

      } catch (Exception e)
      {
      }
      ;
    }
    if (lastMessage == null || !lastMessage.equals(strInfo))
      ssm.mouseOverStructure(pdbResNum, chainId, pdbfilename);

    lastMessage = strInfo;
  }

  public void notifyAtomHovered(int atomIndex, String strInfo, String data)
  {
    if (data != null)
    {
      System.err.println("Ignoring additional hover info: " + data
              + " (other info: '" + strInfo + "' pos " + atomIndex + ")");
    }
    mouseOverStructure(atomIndex, strInfo);
  }

  /*
   * { if (history != null && strStatus != null &&
   * !strStatus.equals("Script completed")) { history.append("\n" + strStatus);
   * } }
   */

  public void notifyAtomPicked(int atomIndex, String strInfo, String strData)
  {
    /**
     * this implements the toggle label behaviour copied from the original
     * structure viewer, MCView
     */
    if (strData != null)
    {
      System.err.println("Ignoring additional pick data string " + strData);
    }
    int chainSeparator = strInfo.indexOf(":");
    int p = 0;
    if (chainSeparator == -1)
      chainSeparator = strInfo.indexOf(".");

    String picked = strInfo.substring(strInfo.indexOf("]") + 1,
            chainSeparator);
    String mdlString = "";
    if ((p = strInfo.indexOf(":")) > -1)
      picked += strInfo.substring(p + 1, strInfo.indexOf("."));

    if ((p = strInfo.indexOf("/")) > -1)
    {
      mdlString += strInfo.substring(p, strInfo.indexOf(" #"));
    }
    picked = "((" + picked + ".CA" + mdlString + ")|(" + picked + ".P"
            + mdlString + "))";
    jmolHistory(false);

    if (!atomsPicked.contains(picked))
    {
      viewer.evalStringQuiet("select " + picked + ";label %n %r:%c");
      atomsPicked.addElement(picked);
    }
    else
    {
      viewer.evalString("select " + picked + ";label off");
      atomsPicked.removeElement(picked);
    }
    jmolHistory(true);
    // TODO: in application this happens
    //
    // if (scriptWindow != null)
    // {
    // scriptWindow.sendConsoleMessage(strInfo);
    // scriptWindow.sendConsoleMessage("\n");
    // }

  }

  @Override
  public void notifyCallback(EnumCallback type, Object[] data)
  {
    try
    {
      switch (type)
      {
      case LOADSTRUCT:
        notifyFileLoaded((String) data[1], (String) data[2],
                (String) data[3], (String) data[4],
                ((Integer) data[5]).intValue());

        break;
      case PICK:
        notifyAtomPicked(((Integer) data[2]).intValue(), (String) data[1],
                (String) data[0]);
        // also highlight in alignment
      case HOVER:
        notifyAtomHovered(((Integer) data[2]).intValue(), (String) data[1],
                (String) data[0]);
        break;
      case SCRIPT:
        notifyScriptTermination((String) data[2],
                ((Integer) data[3]).intValue());
        break;
      case ECHO:
        sendConsoleEcho((String) data[1]);
        break;
      case MESSAGE:
        sendConsoleMessage((data == null) ? ((String) null)
                : (String) data[1]);
        break;
      case ERROR:
        // System.err.println("Ignoring error callback.");
        break;
      case SYNC:
      case RESIZE:
        refreshGUI();
        break;
      case MEASURE:

      case CLICK:
      default:
        System.err.println("Unhandled callback " + type + " "
                + data[1].toString());
        break;
      }
    } catch (Exception e)
    {
      System.err.println("Squashed Jmol callback handler error:");
      e.printStackTrace();
    }
  }

  @Override
  public boolean notifyEnabled(EnumCallback callbackPick)
  {
    switch (callbackPick)
    {
    case ECHO:
    case LOADSTRUCT:
    case MEASURE:
    case MESSAGE:
    case PICK:
    case SCRIPT:
    case HOVER:
    case ERROR:
      return true;
    case RESIZE:
    case SYNC:
    case CLICK:
    case ANIMFRAME:
    case MINIMIZATION:
    }
    return false;
  }

  // incremented every time a load notification is successfully handled -
  // lightweight mechanism for other threads to detect when they can start
  // referrring to new structures.
  private long loadNotifiesHandled = 0;

  public long getLoadNotifiesHandled()
  {
    return loadNotifiesHandled;
  }

  public void notifyFileLoaded(String fullPathName, String fileName2,
          String modelName, String errorMsg, int modelParts)
  {
    if (errorMsg != null)
    {
      fileLoadingError = errorMsg;
      refreshGUI();
      return;
    }
    // TODO: deal sensibly with models loaded inLine:
    // modelName will be null, as will fullPathName.

    // the rest of this routine ignores the arguments, and simply interrogates
    // the Jmol view to find out what structures it contains, and adds them to
    // the structure selection manager.
    fileLoadingError = null;
    String[] oldmodels = modelFileNames;
    modelFileNames = null;
    chainNames = new Vector();
    chainFile = new Hashtable();
    boolean notifyLoaded = false;
    String[] modelfilenames = getPdbFile();
    // first check if we've lost any structures
    if (oldmodels != null && oldmodels.length > 0)
    {
      int oldm = 0;
      for (int i = 0; i < oldmodels.length; i++)
      {
        for (int n = 0; n < modelfilenames.length; n++)
        {
          if (modelfilenames[n] == oldmodels[i])
          {
            oldmodels[i] = null;
            break;
          }
        }
        if (oldmodels[i] != null)
        {
          oldm++;
        }
      }
      if (oldm > 0)
      {
        String[] oldmfn = new String[oldm];
        oldm = 0;
        for (int i = 0; i < oldmodels.length; i++)
        {
          if (oldmodels[i] != null)
          {
            oldmfn[oldm++] = oldmodels[i];
          }
        }
        // deregister the Jmol instance for these structures - we'll add
        // ourselves again at the end for the current structure set.
        ssm.removeStructureViewerListener(this, oldmfn);
      }
    }
    refreshPdbEntries();
    for (int modelnum = 0; modelnum < modelfilenames.length; modelnum++)
    {
      String fileName = modelfilenames[modelnum];
      boolean foundEntry = false;
      MCview.PDBfile pdb = null;
      String pdbfile = null, pdbfhash = null;
      // model was probably loaded inline - so check the pdb file hashcode
      if (loadedInline)
      {
        // calculate essential attributes for the pdb data imported inline.
        // prolly need to resolve modelnumber properly - for now just use our
        // 'best guess'
        pdbfile = viewer.getData("" + (1 + _modelFileNameMap[modelnum])
                + ".0", "PDB");
        pdbfhash = "" + pdbfile.hashCode();
      }
      if (pdbentry != null)
      {
        // search pdbentries and sequences to find correct pdbentry for this
        // model
        for (int pe = 0; pe < pdbentry.length; pe++)
        {
          boolean matches = false;
          if (fileName == null)
          {
            if (false)
            // see JAL-623 - need method of matching pasted data up
            {
              pdb = ssm.setMapping(sequence[pe], chains[pe], pdbfile,
                      AppletFormatAdapter.PASTE);
              pdbentry[modelnum].setFile("INLINE" + pdb.id);
              matches = true;
              foundEntry = true;
            }
          }
          else
          {
            File fl;
            if (matches = (fl = new File(pdbentry[pe].getFile()))
                    .equals(new File(fileName)))
            {
              foundEntry = true;
              // TODO: Jmol can in principle retrieve from CLASSLOADER but
              // this
              // needs
              // to be tested. See mantis bug
              // https://mantis.lifesci.dundee.ac.uk/view.php?id=36605
              String protocol = AppletFormatAdapter.URL;
              try
              {
                if (fl.exists())
                {
                  protocol = AppletFormatAdapter.FILE;
                }
              } catch (Exception e)
              {
              } catch (Error e)
              {
              }
              // Explicitly map to the filename used by Jmol ;
              pdb = ssm.setMapping(sequence[pe], chains[pe], fileName,
                      protocol);
              // pdbentry[pe].getFile(), protocol);

            }
          }
          if (matches)
          {
            // add an entry for every chain in the model
            for (int i = 0; i < pdb.chains.size(); i++)
            {
              String chid = new String(pdb.id + ":"
                      + ((MCview.PDBChain) pdb.chains.elementAt(i)).id);
              chainFile.put(chid, fileName);
              chainNames.addElement(chid);
            }
            notifyLoaded = true;
          }
        }
      }
      if (!foundEntry && associateNewStructs)
      {
        // this is a foreign pdb file that jalview doesn't know about - add
        // it to the dataset and try to find a home - either on a matching
        // sequence or as a new sequence.
        String pdbcontent = viewer.getData("/" + (modelnum + 1) + ".1",
                "PDB");
        // parse pdb file into a chain, etc.
        // locate best match for pdb in associated views and add mapping to
        // ssm
        // if properly registered then
        notifyLoaded = true;

      }
    }
    // FILE LOADED OK
    // so finally, update the jmol bits and pieces
    if (jmolpopup != null)
    {
      // potential for deadlock here:
      // jmolpopup.updateComputedMenus();
    }
    if (!isLoadingFromArchive())
    {
      viewer.evalStringQuiet("model 0; select backbone;restrict;cartoon;wireframe off;spacefill off");
    }
    // register ourselves as a listener and notify the gui that it needs to
    // update itself.
    ssm.addStructureViewerListener(this);
    if (notifyLoaded)
    {
      FeatureRenderer fr = getFeatureRenderer(null);
      if (fr != null)
      {
        fr.featuresAdded();
      }
      refreshGUI();
      loadNotifiesHandled++;
    }
    setLoadingFromArchive(false);
  }

  public void notifyNewPickingModeMeasurement(int iatom, String strMeasure)
  {
    notifyAtomPicked(iatom, strMeasure, null);
  }

  public abstract void notifyScriptTermination(String strStatus,
          int msWalltime);

  /**
   * display a message echoed from the jmol viewer
   * 
   * @param strEcho
   */
  public abstract void sendConsoleEcho(String strEcho); /*
                                                         * { showConsole(true);
                                                         * 
                                                         * history.append("\n" +
                                                         * strEcho); }
                                                         */

  // /End JmolStatusListener
  // /////////////////////////////

  /**
   * @param strStatus
   *          status message - usually the response received after a script
   *          executed
   */
  public abstract void sendConsoleMessage(String strStatus);

  public void setCallbackFunction(String callbackType,
          String callbackFunction)
  {
    System.err.println("Ignoring set-callback request to associate "
            + callbackType + " with function " + callbackFunction);

  }

  public void setJalviewColourScheme(ColourSchemeI cs)
  {
    colourBySequence = false;

    if (cs == null)
      return;

    String res;
    int index;
    Color col;
    jmolHistory(false);
    // TODO: Switch between nucleotide or aa selection expressions
    Enumeration en = ResidueProperties.aa3Hash.keys();
    StringBuffer command = new StringBuffer("select *;color white;");
    while (en.hasMoreElements())
    {
      res = en.nextElement().toString();
      index = ((Integer) ResidueProperties.aa3Hash.get(res)).intValue();
      if (index > 20)
        continue;

      col = cs.findColour(ResidueProperties.aa[index].charAt(0));

      command.append("select " + res + ";color[" + col.getRed() + ","
              + col.getGreen() + "," + col.getBlue() + "];");
    }

    evalStateCommand(command.toString());
    jmolHistory(true);
  }

  public void showHelp()
  {
    showUrl("http://jmol.sourceforge.net/docs/JmolUserGuide/", "jmolHelp");
  }

  /**
   * open the URL somehow
   * 
   * @param target
   */
  public abstract void showUrl(String url, String target);

  /**
   * called when the binding thinks the UI needs to be refreshed after a Jmol
   * state change. this could be because structures were loaded, or because an
   * error has occured.
   */
  public abstract void refreshGUI();

  /**
   * called to show or hide the associated console window container.
   * 
   * @param show
   */
  public abstract void showConsole(boolean show);

  /**
   * @param renderPanel
   * @param jmolfileio
   *          - when true will initialise jmol's file IO system (should be false
   *          in applet context)
   * @param htmlName
   * @param documentBase
   * @param codeBase
   * @param commandOptions
   */
  public void allocateViewer(Container renderPanel, boolean jmolfileio,
          String htmlName, URL documentBase, URL codeBase,
          String commandOptions)
  {
    allocateViewer(renderPanel, jmolfileio, htmlName, documentBase,
            codeBase, commandOptions, null, null);
  }

  /**
   * 
   * @param renderPanel
   * @param jmolfileio
   *          - when true will initialise jmol's file IO system (should be false
   *          in applet context)
   * @param htmlName
   * @param documentBase
   * @param codeBase
   * @param commandOptions
   * @param consolePanel
   *          - panel to contain Jmol console
   * @param buttonsToShow
   *          - buttons to show on the console, in ordr
   */
  public void allocateViewer(Container renderPanel, boolean jmolfileio,
          String htmlName, URL documentBase, URL codeBase,
          String commandOptions, final Container consolePanel,
          String buttonsToShow)
  {
    if (commandOptions == null)
    {
      commandOptions = "";
    }
    viewer = JmolViewer.allocateViewer(renderPanel,
            (jmolfileio ? new SmarterJmolAdapter() : null), htmlName
                    + ((Object) this).toString(), documentBase, codeBase,
            commandOptions, this);

    console = createJmolConsole(viewer, consolePanel, buttonsToShow);
    if (consolePanel != null)
    {
      consolePanel.addComponentListener(this);

    }

  }

  protected abstract JmolAppConsoleInterface createJmolConsole(
          JmolViewer viewer2, Container consolePanel, String buttonsToShow);

  protected org.jmol.api.JmolAppConsoleInterface console = null;

  public void componentResized(ComponentEvent e)
  {

  }

  public void componentMoved(ComponentEvent e)
  {

  }

  public void componentShown(ComponentEvent e)
  {
    showConsole(true);
  }

  public void componentHidden(ComponentEvent e)
  {
    showConsole(false);
  }

  public void setLoadingFromArchive(boolean loadingFromArchive)
  {
    this.loadingFromArchive = loadingFromArchive;
  }
  
  /**
   * 
   * @return true if Jmol is still restoring state or loading is still going on (see setFinsihedLoadingFromArchive)
   */
  public boolean isLoadingFromArchive()
  {
    return loadingFromArchive && !loadingFinished;
  }

  /**
   * modify flag which controls if sequence colouring events are honoured by the binding. 
   * Should be true for normal operation
   * @param finishedLoading
   */
  public void setFinishedLoadingFromArchive(boolean finishedLoading)
  {
    loadingFinished = finishedLoading;
  }

  public void setBackgroundColour(java.awt.Color col)
  {
    jmolHistory(false);
    viewer.evalStringQuiet("background [" + col.getRed() + ","
            + col.getGreen() + "," + col.getBlue() + "];");
    jmolHistory(true);
  }

  /**
   * add structures and any known sequence associations
   * 
   * @returns the pdb entries added to the current set.
   */
  public synchronized PDBEntry[] addSequenceAndChain(PDBEntry[] pdbe,
          SequenceI[][] seq, String[][] chns)
  {
    int pe = -1;
    Vector v = new Vector();
    Vector rtn = new Vector();
    for (int i = 0; i < pdbentry.length; i++)
    {
      v.addElement(pdbentry[i]);
    }
    for (int i = 0; i < pdbe.length; i++)
    {
      int r = v.indexOf(pdbe[i]);
      if (r == -1 || r >= pdbentry.length)
      {
        rtn.addElement(new int[]
        { v.size(), i });
        v.addElement(pdbe[i]);
      }
      else
      {
        // just make sure the sequence/chain entries are all up to date
        addSequenceAndChain(r, seq[i], chns[i]);
      }
    }
    pdbe = new PDBEntry[v.size()];
    v.copyInto(pdbe);
    pdbentry = pdbe;
    if (rtn.size() > 0)
    {
      // expand the tied seuqence[] and string[] arrays
      SequenceI[][] sqs = new SequenceI[pdbentry.length][];
      String[][] sch = new String[pdbentry.length][];
      System.arraycopy(sequence, 0, sqs, 0, sequence.length);
      System.arraycopy(chains, 0, sch, 0, this.chains.length);
      sequence = sqs;
      chains = sch;
      pdbe = new PDBEntry[rtn.size()];
      for (int r = 0; r < pdbe.length; r++)
      {
        int[] stri = ((int[]) rtn.elementAt(r));
        // record the pdb file as a new addition
        pdbe[r] = pdbentry[stri[0]];
        // and add the new sequence/chain entries
        addSequenceAndChain(stri[0], seq[stri[1]], chns[stri[1]]);
      }
    }
    else
    {
      pdbe = null;
    }
    return pdbe;
  }

  public void addSequence(int pe, SequenceI[] seq)
  {
    // add sequences to the pe'th pdbentry's seuqence set.
    addSequenceAndChain(pe, seq, null);
  }

  private void addSequenceAndChain(int pe, SequenceI[] seq, String[] tchain)
  {
    if (pe < 0 || pe >= pdbentry.length)
    {
      throw new Error(
              "Implementation error - no corresponding pdbentry (for index "
                      + pe + ") to add sequences mappings to");
    }
    final String nullChain = "TheNullChain";
    Vector s = new Vector();
    Vector c = new Vector();
    if (chains == null)
    {
      chains = new String[pdbentry.length][];
    }
    if (sequence[pe] != null)
    {
      for (int i = 0; i < sequence[pe].length; i++)
      {
        s.addElement(sequence[pe][i]);
        if (chains[pe] != null)
        {
          if (i < chains[pe].length)
          {
            c.addElement(chains[pe][i]);
          }
          else
          {
            c.addElement(nullChain);
          }
        }
        else
        {
          if (tchain != null && tchain.length > 0)
          {
            c.addElement(nullChain);
          }
        }
      }
    }
    for (int i = 0; i < seq.length; i++)
    {
      if (!s.contains(seq[i]))
      {
        s.addElement(seq[i]);
        if (tchain != null && i < tchain.length)
        {
          c.addElement(tchain[i] == null ? nullChain : tchain[i]);
        }
      }
    }
    SequenceI[] tmp = new SequenceI[s.size()];
    s.copyInto(tmp);
    sequence[pe] = tmp;
    if (c.size() > 0)
    {
      String[] tch = new String[c.size()];
      c.copyInto(tch);
      for (int i = 0; i < tch.length; i++)
      {
        if (tch[i] == nullChain)
        {
          tch[i] = null;
        }
      }
      chains[pe] = tch;
    }
    else
    {
      chains[pe] = null;
    }
  }

  /**
   * 
   * @param pdbfile
   * @return text report of alignment between pdbfile and any associated
   *         alignment sequences
   */
  public String printMapping(String pdbfile)
  {
    return ssm.printMapping(pdbfile);
  }

  @Override
  public void resizeInnerPanel(String data)
  {
    // Jalview doesn't honour resize panel requests

  }
}
