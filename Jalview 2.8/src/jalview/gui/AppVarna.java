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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;

import javax.swing.*;

import jalview.bin.Cache;
import jalview.datamodel.*;
import jalview.structure.*;
import jalview.util.ShiftList;
import fr.orsay.lri.varna.VARNAPanel;
import fr.orsay.lri.varna.exceptions.ExceptionFileFormatOrSyntax;
import fr.orsay.lri.varna.exceptions.ExceptionUnmatchedClosingParentheses;
import fr.orsay.lri.varna.interfaces.InterfaceVARNAListener;
import fr.orsay.lri.varna.interfaces.InterfaceVARNASelectionListener;
import fr.orsay.lri.varna.models.BaseList;
import fr.orsay.lri.varna.models.VARNAConfig;
import fr.orsay.lri.varna.models.annotations.HighlightRegionAnnotation;
import fr.orsay.lri.varna.models.rna.ModeleBase;
import fr.orsay.lri.varna.models.rna.RNA;

public class AppVarna extends JInternalFrame implements
        InterfaceVARNAListener, SelectionListener,
        SecondaryStructureListener// implements
                                  // Runnable,SequenceStructureBinding,
                                  // ViewSetProvider
        , InterfaceVARNASelectionListener, VamsasSource

{
  AppVarnaBinding vab;

  VARNAPanel varnaPanel;

  public String name;

  public StructureSelectionManager ssm;

  /*
   * public AppVarna(){ vab = new AppVarnaBinding(); initVarna(); }
   */

  AlignmentPanel ap;

  public AppVarna(String sname, SequenceI seq, String strucseq,
          String struc, String name, AlignmentPanel ap)
  {
    this.ap = ap;
    ArrayList<RNA> rnaList = new ArrayList<RNA>();
    RNA rna1 = new RNA(name);
    try
    {
      rna1.setRNA(strucseq, replaceOddGaps(struc));
    } catch (ExceptionUnmatchedClosingParentheses e2)
    {
      e2.printStackTrace();
    } catch (ExceptionFileFormatOrSyntax e3)
    {
      e3.printStackTrace();
    }
    RNA trim = trimRNA(rna1, "trimmed " + sname);
    rnaList.add(trim);
    rnaList.add(rna1);
    rnas.put(seq, rna1);
    rnas.put(seq, trim);
    rna1.setName(sname + " (with gaps)");

    {
      seqs.put(trim, seq);
      seqs.put(rna1, seq);

      /**
       * if (false || seq.getStart()!=1) { for (RNA rshift:rnaList) { ShiftList
       * shift=offsets.get(rshift); if (shift==null) { offsets.put(rshift,
       * shift=new ShiftList());} shift.addShift(1, seq.getStart()-1);
       * offsetsInv.put(rshift, shift.getInverse()); } }
       **/
    }
    vab = new AppVarnaBinding(rnaList);
    // vab = new AppVarnaBinding(seq,struc);
    // System.out.println("Hallo: "+name);
    this.name = sname + " trimmed to " + name;
    initVarna();
    ssm = ap.getStructureSelectionManager();
    ssm.addStructureViewerListener(this);
    ssm.addSelectionListener(this);
  }

  public void initVarna()
  {
    // vab.setFinishedInit(false);
    varnaPanel = vab.get_varnaPanel();
    setBackground(Color.white);
    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
            vab.getListPanel(), varnaPanel);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(split, BorderLayout.CENTER);
    // getContentPane().add(vab.getTools(), BorderLayout.NORTH);
    varnaPanel.addVARNAListener(this);
    varnaPanel.addSelectionListener(this);
    jalview.gui.Desktop.addInternalFrame(this, "VARNA -" + name,
            getBounds().width, getBounds().height);
    this.pack();
    showPanel(true);
  }

  public String replaceOddGaps(String oldStr)
  {
    String patternStr = "[^([{<>}])]";
    String replacementStr = ".";
    Pattern pattern = Pattern.compile(patternStr);
    Matcher matcher = pattern.matcher(oldStr);
    String newStr = matcher.replaceAll(replacementStr);
    return newStr;
  }

  public RNA trimRNA(RNA rna, String name)
  {
    ShiftList offset = new ShiftList();
    RNA rnaTrim = new RNA(name);
    try
    {
      rnaTrim.setRNA(rna.getSeq(), replaceOddGaps(rna.getStructDBN()));
    } catch (ExceptionUnmatchedClosingParentheses e2)
    {
      e2.printStackTrace();
    } catch (ExceptionFileFormatOrSyntax e3)
    {
      e3.printStackTrace();
    }

    StringBuffer seq = new StringBuffer(rnaTrim.getSeq());
    StringBuffer struc = new StringBuffer(rnaTrim.getStructDBN());
    int ofstart = -1, sleng = rnaTrim.getSeq().length();
    for (int i = 0; i < sleng; i++)
    {
      // TODO: Jalview utility for gap detection java.utils.isGap()
      // TODO: Switch to jalview rna datamodel
      if (jalview.util.Comparison.isGap(seq.charAt(i)))
      {
        if (ofstart == -1)
        {
          ofstart = i;
        }
        if (!rnaTrim.findPair(i).isEmpty())
        {
          int m = rnaTrim.findPair(i).get(1);
          int l = rnaTrim.findPair(i).get(0);

          struc.replace(m, m + 1, "*");
          struc.replace(l, l + 1, "*");
        }
        else
        {
          struc.replace(i, i + 1, "*");
        }
      }
      else
      {
        if (ofstart > -1)
        {
          offset.addShift(offset.shift(ofstart), ofstart - i);
          ofstart = -1;
        }
      }
    }
    // final gap
    if (ofstart > -1)
    {
      offset.addShift(offset.shift(ofstart), ofstart - sleng);
      ofstart = -1;
    }
    String newSeq = rnaTrim.getSeq().replace("-", "");
    rnaTrim.getSeq().replace(".", "");
    String newStruc = struc.toString().replace("*", "");

    try
    {
      rnaTrim.setRNA(newSeq, newStruc);
      registerOffset(rnaTrim, offset);
    } catch (ExceptionUnmatchedClosingParentheses e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExceptionFileFormatOrSyntax e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rnaTrim;
  }

  // needs to be many-many
  Map<RNA, SequenceI> seqs = new Hashtable<RNA, SequenceI>();

  Map<SequenceI, RNA> rnas = new Hashtable<SequenceI, RNA>();

  Map<RNA, ShiftList> offsets = new Hashtable<RNA, ShiftList>();

  Map<RNA, ShiftList> offsetsInv = new Hashtable<RNA, ShiftList>();

  private void registerOffset(RNA rnaTrim, ShiftList offset)
  {
    offsets.put(rnaTrim, offset);
    offsetsInv.put(rnaTrim, offset.getInverse());
  }

  public void showPanel(boolean show)
  {
    this.setVisible(show);
  }

  private boolean _started = false;

  public void run()
  {
    _started = true;

    try
    {
      initVarna();
    } catch (OutOfMemoryError oomerror)
    {
      new OOMWarning("When trying to open the Varna viewer!", oomerror);
    } catch (Exception ex)
    {
      Cache.log.error("Couldn't open Varna viewer!", ex);
    }
  }

  @Override
  public void onUINewStructure(VARNAConfig v, RNA r)
  {

  }

  @Override
  public void onWarningEmitted(String s)
  {
    // TODO Auto-generated method stub

  }

  private class VarnaHighlighter
  {
    private HighlightRegionAnnotation _lastHighlight;

    private RNA _lastRNAhighlighted = null;

    public void highlightRegion(RNA rna, int start, int end)
    {
      clearSelection(null);
      HighlightRegionAnnotation highlight = new HighlightRegionAnnotation(
              rna.getBasesBetween(start, end));
      rna.addHighlightRegion(highlight);
      _lastHighlight = highlight;
      _lastRNAhighlighted = rna;

    }

    public HighlightRegionAnnotation getLastHighlight()
    {
      return _lastHighlight;
    }

    public RNA getLastRNA()
    {
      return _lastRNAhighlighted;
    }

    public void clearSelection(AppVarnaBinding vab)
    {
      if (_lastRNAhighlighted != null)
      {
        _lastRNAhighlighted.removeHighlightRegion(_lastHighlight);
        if (vab != null)
        {
          vab.updateSelectedRNA(_lastRNAhighlighted);
        }
        _lastRNAhighlighted = null;
        _lastHighlight = null;

      }
    }
  }

  VarnaHighlighter mouseOverHighlighter = new VarnaHighlighter(),
          selectionHighlighter = new VarnaHighlighter();

  /**
   * If a mouseOver event from the AlignmentPanel is noticed the currently
   * selected RNA in the VARNA window is highlighted at the specific position.
   * To be able to remove it before the next highlight it is saved in
   * _lastHighlight
   */
  @Override
  public void mouseOverSequence(SequenceI sequence, int index)
  {
    RNA rna = vab.getSelectedRNA();
    if (seqs.get(rna) == sequence)
    {
      ShiftList shift = offsets.get(rna);
      if (shift != null)
      {
        // System.err.print("Orig pos:"+index);
        index = shift.shift(index);
        // System.err.println("\nFinal pos:"+index);
      }
      mouseOverHighlighter.highlightRegion(rna, index, index);
      vab.updateSelectedRNA(rna);
    }
  }

  @Override
  public void onStructureRedrawn()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void selection(SequenceGroup seqsel, ColumnSelection colsel,
          SelectionSource source)
  {
    if (source != ap.av)
    {
      // ignore events from anything but our parent alignpanel
      // TODO - reuse many-one panel-view system in jmol viewer
      return;
    }
    if (seqsel != null && seqsel.getSize() > 0)
    {
      int start = seqsel.getStartRes(), end = seqsel.getEndRes();
      RNA rna = vab.getSelectedRNA();
      ShiftList shift = offsets.get(rna);
      if (shift != null)
      {
        start = shift.shift(start);
        end = shift.shift(end);
      }
      selectionHighlighter.highlightRegion(rna, start, end);
      selectionHighlighter.getLastHighlight().setOutlineColor(
              seqsel.getOutlineColour());
      // TODO - translate column markings to positions on structure if present.
      vab.updateSelectedRNA(rna);
    }
    else
    {
      selectionHighlighter.clearSelection(vab);
    }
  }

  @Override
  public void onHoverChanged(ModeleBase arg0, ModeleBase arg1)
  {
    RNA rna = vab.getSelectedRNA();
    ShiftList shift = offsetsInv.get(rna);
    SequenceI seq = seqs.get(rna);
    if (arg1 != null && seq != null)
    {
      if (shift != null)
      {
        int i = shift.shift(arg1.getIndex());
        // System.err.println("shifted "+(arg1.getIndex())+" to "+i);
        ssm.mouseOverVamsasSequence(seq, i, this);
      }
      else
      {
        ssm.mouseOverVamsasSequence(seq, arg1.getIndex(), this);
      }
    }
  }

  @Override
  public void onSelectionChanged(BaseList arg0, BaseList arg1, BaseList arg2)
  {
    // TODO translate selected regions in VARNA to a selection on the
    // alignpanel.

  }

}
