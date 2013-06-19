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
package jalview.api;

import java.util.Hashtable;
import java.util.Map;

import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.ColumnSelection;
import jalview.datamodel.SequenceCollectionI;
import jalview.datamodel.SequenceI;
import jalview.schemes.ColourSchemeI;

/**
 * @author jimp
 * 
 */
public interface AlignViewportI
{

  int getCharWidth();

  int getEndRes();

  int getCharHeight();

  /**
   * calculate the height for visible annotation, revalidating bounds where
   * necessary ABSTRACT GUI METHOD
   * 
   * @return total height of annotation
   */
  public int calcPanelHeight();

  boolean hasHiddenColumns();

  boolean isValidCharWidth();

  boolean isShowConsensusHistogram();

  boolean isShowSequenceLogo();

  boolean isNormaliseSequenceLogo();

  ColourSchemeI getGlobalColourScheme();

  AlignmentI getAlignment();

  ColumnSelection getColumnSelection();

  Hashtable[] getSequenceConsensusHash();

  Hashtable[] getRnaStructureConsensusHash();

  boolean getIgnoreGapsConsensus();

  boolean getCentreColumnLabels();

  boolean isCalculationInProgress(AlignmentAnnotation alignmentAnnotation);

  AlignmentAnnotation getAlignmentQualityAnnot();

  AlignmentAnnotation getAlignmentConservationAnnotation();

  /**
   * get the container for alignment consensus annotation
   * 
   * @return
   */
  AlignmentAnnotation getAlignmentConsensusAnnotation();

  /**
   * Test to see if viewport is still open and active
   * 
   * @return true indicates that all references to viewport should be dropped
   */
  boolean isClosed();

  /**
   * get the associated calculation thread manager for the view
   * 
   * @return
   */
  AlignCalcManagerI getCalcManager();

  /**
   * get the percentage gaps allowed in a conservation calculation
   * 
   */
  public int getConsPercGaps();

  /**
   * set the consensus result object for the viewport
   * 
   * @param hconsensus
   */
  void setSequenceConsensusHash(Hashtable[] hconsensus);

  /**
   * 
   * @return the alignment annotatino row for the structure consensus
   *         calculation
   */
  AlignmentAnnotation getAlignmentStrucConsensusAnnotation();

  /**
   * set the Rna structure consensus result object for the viewport
   * 
   * @param hStrucConsensus
   */
  void setRnaStructureConsensusHash(Hashtable[] hStrucConsensus);

  /**
   * set global colourscheme
   * 
   * @param rhc
   */
  void setGlobalColourScheme(ColourSchemeI rhc);

  Map<SequenceI, SequenceCollectionI> getHiddenRepSequences();

  void setHiddenRepSequences(
          Map<SequenceI, SequenceCollectionI> hiddenRepSequences);

  /**
   * hides or shows dynamic annotation rows based on groups and group and
   * alignment associated auto-annotation state flags apply the current
   * group/autoannotation settings to the alignment view. Usually you should
   * call the AlignmentViewPanel.adjustAnnotationHeight() method afterwards to
   * ensure the annotation panel bounds are set correctly.
   * 
   * @param applyGlobalSettings
   *          - apply to all autoannotation rows or just the ones associated
   *          with the current visible region
   * @param preserveNewGroupSettings
   *          - don't apply global settings to groups which don't already have
   *          group associated annotation
   */
  void updateGroupAnnotationSettings(boolean applyGlobalSettings,
          boolean preserveNewGroupSettings);

}
