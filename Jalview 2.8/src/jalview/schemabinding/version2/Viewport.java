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
package jalview.schemabinding.version2;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Viewport.
 * 
 * @version $Revision$ $Date$
 */
public class Viewport implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _conservationSelected.
   */
  private boolean _conservationSelected;

  /**
   * keeps track of state for field: _conservationSelected
   */
  private boolean _has_conservationSelected;

  /**
   * Field _pidSelected.
   */
  private boolean _pidSelected;

  /**
   * keeps track of state for field: _pidSelected
   */
  private boolean _has_pidSelected;

  /**
   * Field _bgColour.
   */
  private java.lang.String _bgColour;

  /**
   * Field _consThreshold.
   */
  private int _consThreshold;

  /**
   * keeps track of state for field: _consThreshold
   */
  private boolean _has_consThreshold;

  /**
   * Field _pidThreshold.
   */
  private int _pidThreshold;

  /**
   * keeps track of state for field: _pidThreshold
   */
  private boolean _has_pidThreshold;

  /**
   * Field _title.
   */
  private java.lang.String _title;

  /**
   * Field _showFullId.
   */
  private boolean _showFullId;

  /**
   * keeps track of state for field: _showFullId
   */
  private boolean _has_showFullId;

  /**
   * Field _rightAlignIds.
   */
  private boolean _rightAlignIds;

  /**
   * keeps track of state for field: _rightAlignIds
   */
  private boolean _has_rightAlignIds;

  /**
   * Field _showText.
   */
  private boolean _showText;

  /**
   * keeps track of state for field: _showText
   */
  private boolean _has_showText;

  /**
   * Field _showColourText.
   */
  private boolean _showColourText;

  /**
   * keeps track of state for field: _showColourText
   */
  private boolean _has_showColourText;

  /**
   * Field _showUnconserved.
   */
  private boolean _showUnconserved = false;

  /**
   * keeps track of state for field: _showUnconserved
   */
  private boolean _has_showUnconserved;

  /**
   * Field _showBoxes.
   */
  private boolean _showBoxes;

  /**
   * keeps track of state for field: _showBoxes
   */
  private boolean _has_showBoxes;

  /**
   * Field _wrapAlignment.
   */
  private boolean _wrapAlignment;

  /**
   * keeps track of state for field: _wrapAlignment
   */
  private boolean _has_wrapAlignment;

  /**
   * Field _renderGaps.
   */
  private boolean _renderGaps;

  /**
   * keeps track of state for field: _renderGaps
   */
  private boolean _has_renderGaps;

  /**
   * Field _showSequenceFeatures.
   */
  private boolean _showSequenceFeatures;

  /**
   * keeps track of state for field: _showSequenceFeatures
   */
  private boolean _has_showSequenceFeatures;

  /**
   * Field _showNPfeatureTooltip.
   */
  private boolean _showNPfeatureTooltip;

  /**
   * keeps track of state for field: _showNPfeatureTooltip
   */
  private boolean _has_showNPfeatureTooltip;

  /**
   * Field _showDbRefTooltip.
   */
  private boolean _showDbRefTooltip;

  /**
   * keeps track of state for field: _showDbRefTooltip
   */
  private boolean _has_showDbRefTooltip;

  /**
   * Field _followHighlight.
   */
  private boolean _followHighlight = true;

  /**
   * keeps track of state for field: _followHighlight
   */
  private boolean _has_followHighlight;

  /**
   * Field _followSelection.
   */
  private boolean _followSelection = true;

  /**
   * keeps track of state for field: _followSelection
   */
  private boolean _has_followSelection;

  /**
   * Field _showAnnotation.
   */
  private boolean _showAnnotation;

  /**
   * keeps track of state for field: _showAnnotation
   */
  private boolean _has_showAnnotation;

  /**
   * Field _centreColumnLabels.
   */
  private boolean _centreColumnLabels = false;

  /**
   * keeps track of state for field: _centreColumnLabels
   */
  private boolean _has_centreColumnLabels;

  /**
   * Field _showGroupConservation.
   */
  private boolean _showGroupConservation = false;

  /**
   * keeps track of state for field: _showGroupConservation
   */
  private boolean _has_showGroupConservation;

  /**
   * Field _showGroupConsensus.
   */
  private boolean _showGroupConsensus = false;

  /**
   * keeps track of state for field: _showGroupConsensus
   */
  private boolean _has_showGroupConsensus;

  /**
   * Field _showConsensusHistogram.
   */
  private boolean _showConsensusHistogram = true;

  /**
   * keeps track of state for field: _showConsensusHistogram
   */
  private boolean _has_showConsensusHistogram;

  /**
   * Field _showSequenceLogo.
   */
  private boolean _showSequenceLogo = false;

  /**
   * keeps track of state for field: _showSequenceLogo
   */
  private boolean _has_showSequenceLogo;

  /**
   * Field _normaliseSequenceLogo.
   */
  private boolean _normaliseSequenceLogo = false;

  /**
   * keeps track of state for field: _normaliseSequenceLogo
   */
  private boolean _has_normaliseSequenceLogo;

  /**
   * Field _ignoreGapsinConsensus.
   */
  private boolean _ignoreGapsinConsensus = true;

  /**
   * keeps track of state for field: _ignoreGapsinConsensus
   */
  private boolean _has_ignoreGapsinConsensus;

  /**
   * Field _startRes.
   */
  private int _startRes;

  /**
   * keeps track of state for field: _startRes
   */
  private boolean _has_startRes;

  /**
   * Field _startSeq.
   */
  private int _startSeq;

  /**
   * keeps track of state for field: _startSeq
   */
  private boolean _has_startSeq;

  /**
   * Field _fontName.
   */
  private java.lang.String _fontName;

  /**
   * Field _fontSize.
   */
  private int _fontSize;

  /**
   * keeps track of state for field: _fontSize
   */
  private boolean _has_fontSize;

  /**
   * Field _fontStyle.
   */
  private int _fontStyle;

  /**
   * keeps track of state for field: _fontStyle
   */
  private boolean _has_fontStyle;

  /**
   * Field _viewName.
   */
  private java.lang.String _viewName;

  /**
   * Field _sequenceSetId.
   */
  private java.lang.String _sequenceSetId;

  /**
   * Field _gatheredViews.
   */
  private boolean _gatheredViews;

  /**
   * keeps track of state for field: _gatheredViews
   */
  private boolean _has_gatheredViews;

  /**
   * Field _textCol1.
   */
  private int _textCol1;

  /**
   * keeps track of state for field: _textCol1
   */
  private boolean _has_textCol1;

  /**
   * Field _textCol2.
   */
  private int _textCol2;

  /**
   * keeps track of state for field: _textCol2
   */
  private boolean _has_textCol2;

  /**
   * Field _textColThreshold.
   */
  private int _textColThreshold;

  /**
   * keeps track of state for field: _textColThreshold
   */
  private boolean _has_textColThreshold;

  /**
   * unique id used by jalview to synchronize between stored and instantiated
   * views
   * 
   */
  private java.lang.String _id;

  /**
   * Field _width.
   */
  private int _width;

  /**
   * keeps track of state for field: _width
   */
  private boolean _has_width;

  /**
   * Field _height.
   */
  private int _height;

  /**
   * keeps track of state for field: _height
   */
  private boolean _has_height;

  /**
   * Field _xpos.
   */
  private int _xpos;

  /**
   * keeps track of state for field: _xpos
   */
  private boolean _has_xpos;

  /**
   * Field _ypos.
   */
  private int _ypos;

  /**
   * keeps track of state for field: _ypos
   */
  private boolean _has_ypos;

  /**
   * Field _annotationColours.
   */
  private jalview.schemabinding.version2.AnnotationColours _annotationColours;

  /**
   * Field _hiddenColumnsList.
   */
  private java.util.Vector _hiddenColumnsList;

  /**
   * Field _calcIdParamList.
   */
  private java.util.Vector _calcIdParamList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Viewport()
  {
    super();
    this._hiddenColumnsList = new java.util.Vector();
    this._calcIdParamList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vCalcIdParam
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addCalcIdParam(
          final jalview.schemabinding.version2.CalcIdParam vCalcIdParam)
          throws java.lang.IndexOutOfBoundsException
  {
    this._calcIdParamList.addElement(vCalcIdParam);
  }

  /**
   * 
   * 
   * @param index
   * @param vCalcIdParam
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addCalcIdParam(final int index,
          final jalview.schemabinding.version2.CalcIdParam vCalcIdParam)
          throws java.lang.IndexOutOfBoundsException
  {
    this._calcIdParamList.add(index, vCalcIdParam);
  }

  /**
   * 
   * 
   * @param vHiddenColumns
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addHiddenColumns(
          final jalview.schemabinding.version2.HiddenColumns vHiddenColumns)
          throws java.lang.IndexOutOfBoundsException
  {
    this._hiddenColumnsList.addElement(vHiddenColumns);
  }

  /**
   * 
   * 
   * @param index
   * @param vHiddenColumns
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addHiddenColumns(final int index,
          final jalview.schemabinding.version2.HiddenColumns vHiddenColumns)
          throws java.lang.IndexOutOfBoundsException
  {
    this._hiddenColumnsList.add(index, vHiddenColumns);
  }

  /**
     */
  public void deleteCentreColumnLabels()
  {
    this._has_centreColumnLabels = false;
  }

  /**
     */
  public void deleteConsThreshold()
  {
    this._has_consThreshold = false;
  }

  /**
     */
  public void deleteConservationSelected()
  {
    this._has_conservationSelected = false;
  }

  /**
     */
  public void deleteFollowHighlight()
  {
    this._has_followHighlight = false;
  }

  /**
     */
  public void deleteFollowSelection()
  {
    this._has_followSelection = false;
  }

  /**
     */
  public void deleteFontSize()
  {
    this._has_fontSize = false;
  }

  /**
     */
  public void deleteFontStyle()
  {
    this._has_fontStyle = false;
  }

  /**
     */
  public void deleteGatheredViews()
  {
    this._has_gatheredViews = false;
  }

  /**
     */
  public void deleteHeight()
  {
    this._has_height = false;
  }

  /**
     */
  public void deleteIgnoreGapsinConsensus()
  {
    this._has_ignoreGapsinConsensus = false;
  }

  /**
     */
  public void deleteNormaliseSequenceLogo()
  {
    this._has_normaliseSequenceLogo = false;
  }

  /**
     */
  public void deletePidSelected()
  {
    this._has_pidSelected = false;
  }

  /**
     */
  public void deletePidThreshold()
  {
    this._has_pidThreshold = false;
  }

  /**
     */
  public void deleteRenderGaps()
  {
    this._has_renderGaps = false;
  }

  /**
     */
  public void deleteRightAlignIds()
  {
    this._has_rightAlignIds = false;
  }

  /**
     */
  public void deleteShowAnnotation()
  {
    this._has_showAnnotation = false;
  }

  /**
     */
  public void deleteShowBoxes()
  {
    this._has_showBoxes = false;
  }

  /**
     */
  public void deleteShowColourText()
  {
    this._has_showColourText = false;
  }

  /**
     */
  public void deleteShowConsensusHistogram()
  {
    this._has_showConsensusHistogram = false;
  }

  /**
     */
  public void deleteShowDbRefTooltip()
  {
    this._has_showDbRefTooltip = false;
  }

  /**
     */
  public void deleteShowFullId()
  {
    this._has_showFullId = false;
  }

  /**
     */
  public void deleteShowGroupConsensus()
  {
    this._has_showGroupConsensus = false;
  }

  /**
     */
  public void deleteShowGroupConservation()
  {
    this._has_showGroupConservation = false;
  }

  /**
     */
  public void deleteShowNPfeatureTooltip()
  {
    this._has_showNPfeatureTooltip = false;
  }

  /**
     */
  public void deleteShowSequenceFeatures()
  {
    this._has_showSequenceFeatures = false;
  }

  /**
     */
  public void deleteShowSequenceLogo()
  {
    this._has_showSequenceLogo = false;
  }

  /**
     */
  public void deleteShowText()
  {
    this._has_showText = false;
  }

  /**
     */
  public void deleteShowUnconserved()
  {
    this._has_showUnconserved = false;
  }

  /**
     */
  public void deleteStartRes()
  {
    this._has_startRes = false;
  }

  /**
     */
  public void deleteStartSeq()
  {
    this._has_startSeq = false;
  }

  /**
     */
  public void deleteTextCol1()
  {
    this._has_textCol1 = false;
  }

  /**
     */
  public void deleteTextCol2()
  {
    this._has_textCol2 = false;
  }

  /**
     */
  public void deleteTextColThreshold()
  {
    this._has_textColThreshold = false;
  }

  /**
     */
  public void deleteWidth()
  {
    this._has_width = false;
  }

  /**
     */
  public void deleteWrapAlignment()
  {
    this._has_wrapAlignment = false;
  }

  /**
     */
  public void deleteXpos()
  {
    this._has_xpos = false;
  }

  /**
     */
  public void deleteYpos()
  {
    this._has_ypos = false;
  }

  /**
   * Method enumerateCalcIdParam.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.CalcIdParam
   *         elements
   */
  public java.util.Enumeration enumerateCalcIdParam()
  {
    return this._calcIdParamList.elements();
  }

  /**
   * Method enumerateHiddenColumns.
   * 
   * @return an Enumeration over all
   *         jalview.schemabinding.version2.HiddenColumns elements
   */
  public java.util.Enumeration enumerateHiddenColumns()
  {
    return this._hiddenColumnsList.elements();
  }

  /**
   * Returns the value of field 'annotationColours'.
   * 
   * @return the value of field 'AnnotationColours'.
   */
  public jalview.schemabinding.version2.AnnotationColours getAnnotationColours()
  {
    return this._annotationColours;
  }

  /**
   * Returns the value of field 'bgColour'.
   * 
   * @return the value of field 'BgColour'.
   */
  public java.lang.String getBgColour()
  {
    return this._bgColour;
  }

  /**
   * Method getCalcIdParam.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.CalcIdParam at the
   *         given index
   */
  public jalview.schemabinding.version2.CalcIdParam getCalcIdParam(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._calcIdParamList.size())
    {
      throw new IndexOutOfBoundsException("getCalcIdParam: Index value '"
              + index + "' not in range [0.."
              + (this._calcIdParamList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.CalcIdParam) _calcIdParamList
            .get(index);
  }

  /**
   * Method getCalcIdParam.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.CalcIdParam[] getCalcIdParam()
  {
    jalview.schemabinding.version2.CalcIdParam[] array = new jalview.schemabinding.version2.CalcIdParam[0];
    return (jalview.schemabinding.version2.CalcIdParam[]) this._calcIdParamList
            .toArray(array);
  }

  /**
   * Method getCalcIdParamCount.
   * 
   * @return the size of this collection
   */
  public int getCalcIdParamCount()
  {
    return this._calcIdParamList.size();
  }

  /**
   * Returns the value of field 'centreColumnLabels'.
   * 
   * @return the value of field 'CentreColumnLabels'.
   */
  public boolean getCentreColumnLabels()
  {
    return this._centreColumnLabels;
  }

  /**
   * Returns the value of field 'consThreshold'.
   * 
   * @return the value of field 'ConsThreshold'.
   */
  public int getConsThreshold()
  {
    return this._consThreshold;
  }

  /**
   * Returns the value of field 'conservationSelected'.
   * 
   * @return the value of field 'ConservationSelected'.
   */
  public boolean getConservationSelected()
  {
    return this._conservationSelected;
  }

  /**
   * Returns the value of field 'followHighlight'.
   * 
   * @return the value of field 'FollowHighlight'.
   */
  public boolean getFollowHighlight()
  {
    return this._followHighlight;
  }

  /**
   * Returns the value of field 'followSelection'.
   * 
   * @return the value of field 'FollowSelection'.
   */
  public boolean getFollowSelection()
  {
    return this._followSelection;
  }

  /**
   * Returns the value of field 'fontName'.
   * 
   * @return the value of field 'FontName'.
   */
  public java.lang.String getFontName()
  {
    return this._fontName;
  }

  /**
   * Returns the value of field 'fontSize'.
   * 
   * @return the value of field 'FontSize'.
   */
  public int getFontSize()
  {
    return this._fontSize;
  }

  /**
   * Returns the value of field 'fontStyle'.
   * 
   * @return the value of field 'FontStyle'.
   */
  public int getFontStyle()
  {
    return this._fontStyle;
  }

  /**
   * Returns the value of field 'gatheredViews'.
   * 
   * @return the value of field 'GatheredViews'.
   */
  public boolean getGatheredViews()
  {
    return this._gatheredViews;
  }

  /**
   * Returns the value of field 'height'.
   * 
   * @return the value of field 'Height'.
   */
  public int getHeight()
  {
    return this._height;
  }

  /**
   * Method getHiddenColumns.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.HiddenColumns at
   *         the given index
   */
  public jalview.schemabinding.version2.HiddenColumns getHiddenColumns(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._hiddenColumnsList.size())
    {
      throw new IndexOutOfBoundsException("getHiddenColumns: Index value '"
              + index + "' not in range [0.."
              + (this._hiddenColumnsList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.HiddenColumns) _hiddenColumnsList
            .get(index);
  }

  /**
   * Method getHiddenColumns.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.HiddenColumns[] getHiddenColumns()
  {
    jalview.schemabinding.version2.HiddenColumns[] array = new jalview.schemabinding.version2.HiddenColumns[0];
    return (jalview.schemabinding.version2.HiddenColumns[]) this._hiddenColumnsList
            .toArray(array);
  }

  /**
   * Method getHiddenColumnsCount.
   * 
   * @return the size of this collection
   */
  public int getHiddenColumnsCount()
  {
    return this._hiddenColumnsList.size();
  }

  /**
   * Returns the value of field 'id'. The field 'id' has the following
   * description: unique id used by jalview to synchronize between stored and
   * instantiated views
   * 
   * 
   * @return the value of field 'Id'.
   */
  public java.lang.String getId()
  {
    return this._id;
  }

  /**
   * Returns the value of field 'ignoreGapsinConsensus'.
   * 
   * @return the value of field 'IgnoreGapsinConsensus'.
   */
  public boolean getIgnoreGapsinConsensus()
  {
    return this._ignoreGapsinConsensus;
  }

  /**
   * Returns the value of field 'normaliseSequenceLogo'.
   * 
   * @return the value of field 'NormaliseSequenceLogo'.
   */
  public boolean getNormaliseSequenceLogo()
  {
    return this._normaliseSequenceLogo;
  }

  /**
   * Returns the value of field 'pidSelected'.
   * 
   * @return the value of field 'PidSelected'.
   */
  public boolean getPidSelected()
  {
    return this._pidSelected;
  }

  /**
   * Returns the value of field 'pidThreshold'.
   * 
   * @return the value of field 'PidThreshold'.
   */
  public int getPidThreshold()
  {
    return this._pidThreshold;
  }

  /**
   * Returns the value of field 'renderGaps'.
   * 
   * @return the value of field 'RenderGaps'.
   */
  public boolean getRenderGaps()
  {
    return this._renderGaps;
  }

  /**
   * Returns the value of field 'rightAlignIds'.
   * 
   * @return the value of field 'RightAlignIds'.
   */
  public boolean getRightAlignIds()
  {
    return this._rightAlignIds;
  }

  /**
   * Returns the value of field 'sequenceSetId'.
   * 
   * @return the value of field 'SequenceSetId'.
   */
  public java.lang.String getSequenceSetId()
  {
    return this._sequenceSetId;
  }

  /**
   * Returns the value of field 'showAnnotation'.
   * 
   * @return the value of field 'ShowAnnotation'.
   */
  public boolean getShowAnnotation()
  {
    return this._showAnnotation;
  }

  /**
   * Returns the value of field 'showBoxes'.
   * 
   * @return the value of field 'ShowBoxes'.
   */
  public boolean getShowBoxes()
  {
    return this._showBoxes;
  }

  /**
   * Returns the value of field 'showColourText'.
   * 
   * @return the value of field 'ShowColourText'.
   */
  public boolean getShowColourText()
  {
    return this._showColourText;
  }

  /**
   * Returns the value of field 'showConsensusHistogram'.
   * 
   * @return the value of field 'ShowConsensusHistogram'.
   */
  public boolean getShowConsensusHistogram()
  {
    return this._showConsensusHistogram;
  }

  /**
   * Returns the value of field 'showDbRefTooltip'.
   * 
   * @return the value of field 'ShowDbRefTooltip'.
   */
  public boolean getShowDbRefTooltip()
  {
    return this._showDbRefTooltip;
  }

  /**
   * Returns the value of field 'showFullId'.
   * 
   * @return the value of field 'ShowFullId'.
   */
  public boolean getShowFullId()
  {
    return this._showFullId;
  }

  /**
   * Returns the value of field 'showGroupConsensus'.
   * 
   * @return the value of field 'ShowGroupConsensus'.
   */
  public boolean getShowGroupConsensus()
  {
    return this._showGroupConsensus;
  }

  /**
   * Returns the value of field 'showGroupConservation'.
   * 
   * @return the value of field 'ShowGroupConservation'.
   */
  public boolean getShowGroupConservation()
  {
    return this._showGroupConservation;
  }

  /**
   * Returns the value of field 'showNPfeatureTooltip'.
   * 
   * @return the value of field 'ShowNPfeatureTooltip'.
   */
  public boolean getShowNPfeatureTooltip()
  {
    return this._showNPfeatureTooltip;
  }

  /**
   * Returns the value of field 'showSequenceFeatures'.
   * 
   * @return the value of field 'ShowSequenceFeatures'.
   */
  public boolean getShowSequenceFeatures()
  {
    return this._showSequenceFeatures;
  }

  /**
   * Returns the value of field 'showSequenceLogo'.
   * 
   * @return the value of field 'ShowSequenceLogo'.
   */
  public boolean getShowSequenceLogo()
  {
    return this._showSequenceLogo;
  }

  /**
   * Returns the value of field 'showText'.
   * 
   * @return the value of field 'ShowText'.
   */
  public boolean getShowText()
  {
    return this._showText;
  }

  /**
   * Returns the value of field 'showUnconserved'.
   * 
   * @return the value of field 'ShowUnconserved'.
   */
  public boolean getShowUnconserved()
  {
    return this._showUnconserved;
  }

  /**
   * Returns the value of field 'startRes'.
   * 
   * @return the value of field 'StartRes'.
   */
  public int getStartRes()
  {
    return this._startRes;
  }

  /**
   * Returns the value of field 'startSeq'.
   * 
   * @return the value of field 'StartSeq'.
   */
  public int getStartSeq()
  {
    return this._startSeq;
  }

  /**
   * Returns the value of field 'textCol1'.
   * 
   * @return the value of field 'TextCol1'.
   */
  public int getTextCol1()
  {
    return this._textCol1;
  }

  /**
   * Returns the value of field 'textCol2'.
   * 
   * @return the value of field 'TextCol2'.
   */
  public int getTextCol2()
  {
    return this._textCol2;
  }

  /**
   * Returns the value of field 'textColThreshold'.
   * 
   * @return the value of field 'TextColThreshold'.
   */
  public int getTextColThreshold()
  {
    return this._textColThreshold;
  }

  /**
   * Returns the value of field 'title'.
   * 
   * @return the value of field 'Title'.
   */
  public java.lang.String getTitle()
  {
    return this._title;
  }

  /**
   * Returns the value of field 'viewName'.
   * 
   * @return the value of field 'ViewName'.
   */
  public java.lang.String getViewName()
  {
    return this._viewName;
  }

  /**
   * Returns the value of field 'width'.
   * 
   * @return the value of field 'Width'.
   */
  public int getWidth()
  {
    return this._width;
  }

  /**
   * Returns the value of field 'wrapAlignment'.
   * 
   * @return the value of field 'WrapAlignment'.
   */
  public boolean getWrapAlignment()
  {
    return this._wrapAlignment;
  }

  /**
   * Returns the value of field 'xpos'.
   * 
   * @return the value of field 'Xpos'.
   */
  public int getXpos()
  {
    return this._xpos;
  }

  /**
   * Returns the value of field 'ypos'.
   * 
   * @return the value of field 'Ypos'.
   */
  public int getYpos()
  {
    return this._ypos;
  }

  /**
   * Method hasCentreColumnLabels.
   * 
   * @return true if at least one CentreColumnLabels has been adde
   */
  public boolean hasCentreColumnLabels()
  {
    return this._has_centreColumnLabels;
  }

  /**
   * Method hasConsThreshold.
   * 
   * @return true if at least one ConsThreshold has been added
   */
  public boolean hasConsThreshold()
  {
    return this._has_consThreshold;
  }

  /**
   * Method hasConservationSelected.
   * 
   * @return true if at least one ConservationSelected has been added
   */
  public boolean hasConservationSelected()
  {
    return this._has_conservationSelected;
  }

  /**
   * Method hasFollowHighlight.
   * 
   * @return true if at least one FollowHighlight has been added
   */
  public boolean hasFollowHighlight()
  {
    return this._has_followHighlight;
  }

  /**
   * Method hasFollowSelection.
   * 
   * @return true if at least one FollowSelection has been added
   */
  public boolean hasFollowSelection()
  {
    return this._has_followSelection;
  }

  /**
   * Method hasFontSize.
   * 
   * @return true if at least one FontSize has been added
   */
  public boolean hasFontSize()
  {
    return this._has_fontSize;
  }

  /**
   * Method hasFontStyle.
   * 
   * @return true if at least one FontStyle has been added
   */
  public boolean hasFontStyle()
  {
    return this._has_fontStyle;
  }

  /**
   * Method hasGatheredViews.
   * 
   * @return true if at least one GatheredViews has been added
   */
  public boolean hasGatheredViews()
  {
    return this._has_gatheredViews;
  }

  /**
   * Method hasHeight.
   * 
   * @return true if at least one Height has been added
   */
  public boolean hasHeight()
  {
    return this._has_height;
  }

  /**
   * Method hasIgnoreGapsinConsensus.
   * 
   * @return true if at least one IgnoreGapsinConsensus has been added
   */
  public boolean hasIgnoreGapsinConsensus()
  {
    return this._has_ignoreGapsinConsensus;
  }

  /**
   * Method hasNormaliseSequenceLogo.
   * 
   * @return true if at least one NormaliseSequenceLogo has been added
   */
  public boolean hasNormaliseSequenceLogo()
  {
    return this._has_normaliseSequenceLogo;
  }

  /**
   * Method hasPidSelected.
   * 
   * @return true if at least one PidSelected has been added
   */
  public boolean hasPidSelected()
  {
    return this._has_pidSelected;
  }

  /**
   * Method hasPidThreshold.
   * 
   * @return true if at least one PidThreshold has been added
   */
  public boolean hasPidThreshold()
  {
    return this._has_pidThreshold;
  }

  /**
   * Method hasRenderGaps.
   * 
   * @return true if at least one RenderGaps has been added
   */
  public boolean hasRenderGaps()
  {
    return this._has_renderGaps;
  }

  /**
   * Method hasRightAlignIds.
   * 
   * @return true if at least one RightAlignIds has been added
   */
  public boolean hasRightAlignIds()
  {
    return this._has_rightAlignIds;
  }

  /**
   * Method hasShowAnnotation.
   * 
   * @return true if at least one ShowAnnotation has been added
   */
  public boolean hasShowAnnotation()
  {
    return this._has_showAnnotation;
  }

  /**
   * Method hasShowBoxes.
   * 
   * @return true if at least one ShowBoxes has been added
   */
  public boolean hasShowBoxes()
  {
    return this._has_showBoxes;
  }

  /**
   * Method hasShowColourText.
   * 
   * @return true if at least one ShowColourText has been added
   */
  public boolean hasShowColourText()
  {
    return this._has_showColourText;
  }

  /**
   * Method hasShowConsensusHistogram.
   * 
   * @return true if at least one ShowConsensusHistogram has been added
   */
  public boolean hasShowConsensusHistogram()
  {
    return this._has_showConsensusHistogram;
  }

  /**
   * Method hasShowDbRefTooltip.
   * 
   * @return true if at least one ShowDbRefTooltip has been added
   */
  public boolean hasShowDbRefTooltip()
  {
    return this._has_showDbRefTooltip;
  }

  /**
   * Method hasShowFullId.
   * 
   * @return true if at least one ShowFullId has been added
   */
  public boolean hasShowFullId()
  {
    return this._has_showFullId;
  }

  /**
   * Method hasShowGroupConsensus.
   * 
   * @return true if at least one ShowGroupConsensus has been adde
   */
  public boolean hasShowGroupConsensus()
  {
    return this._has_showGroupConsensus;
  }

  /**
   * Method hasShowGroupConservation.
   * 
   * @return true if at least one ShowGroupConservation has been added
   */
  public boolean hasShowGroupConservation()
  {
    return this._has_showGroupConservation;
  }

  /**
   * Method hasShowNPfeatureTooltip.
   * 
   * @return true if at least one ShowNPfeatureTooltip has been added
   */
  public boolean hasShowNPfeatureTooltip()
  {
    return this._has_showNPfeatureTooltip;
  }

  /**
   * Method hasShowSequenceFeatures.
   * 
   * @return true if at least one ShowSequenceFeatures has been added
   */
  public boolean hasShowSequenceFeatures()
  {
    return this._has_showSequenceFeatures;
  }

  /**
   * Method hasShowSequenceLogo.
   * 
   * @return true if at least one ShowSequenceLogo has been added
   */
  public boolean hasShowSequenceLogo()
  {
    return this._has_showSequenceLogo;
  }

  /**
   * Method hasShowText.
   * 
   * @return true if at least one ShowText has been added
   */
  public boolean hasShowText()
  {
    return this._has_showText;
  }

  /**
   * Method hasShowUnconserved.
   * 
   * @return true if at least one ShowUnconserved has been added
   */
  public boolean hasShowUnconserved()
  {
    return this._has_showUnconserved;
  }

  /**
   * Method hasStartRes.
   * 
   * @return true if at least one StartRes has been added
   */
  public boolean hasStartRes()
  {
    return this._has_startRes;
  }

  /**
   * Method hasStartSeq.
   * 
   * @return true if at least one StartSeq has been added
   */
  public boolean hasStartSeq()
  {
    return this._has_startSeq;
  }

  /**
   * Method hasTextCol1.
   * 
   * @return true if at least one TextCol1 has been added
   */
  public boolean hasTextCol1()
  {
    return this._has_textCol1;
  }

  /**
   * Method hasTextCol2.
   * 
   * @return true if at least one TextCol2 has been added
   */
  public boolean hasTextCol2()
  {
    return this._has_textCol2;
  }

  /**
   * Method hasTextColThreshold.
   * 
   * @return true if at least one TextColThreshold has been added
   */
  public boolean hasTextColThreshold()
  {
    return this._has_textColThreshold;
  }

  /**
   * Method hasWidth.
   * 
   * @return true if at least one Width has been added
   */
  public boolean hasWidth()
  {
    return this._has_width;
  }

  /**
   * Method hasWrapAlignment.
   * 
   * @return true if at least one WrapAlignment has been added
   */
  public boolean hasWrapAlignment()
  {
    return this._has_wrapAlignment;
  }

  /**
   * Method hasXpos.
   * 
   * @return true if at least one Xpos has been added
   */
  public boolean hasXpos()
  {
    return this._has_xpos;
  }

  /**
   * Method hasYpos.
   * 
   * @return true if at least one Ypos has been added
   */
  public boolean hasYpos()
  {
    return this._has_ypos;
  }

  /**
   * Returns the value of field 'centreColumnLabels'.
   * 
   * @return the value of field 'CentreColumnLabels'.
   */
  public boolean isCentreColumnLabels()
  {
    return this._centreColumnLabels;
  }

  /**
   * Returns the value of field 'conservationSelected'.
   * 
   * @return the value of field 'ConservationSelected'.
   */
  public boolean isConservationSelected()
  {
    return this._conservationSelected;
  }

  /**
   * Returns the value of field 'followHighlight'.
   * 
   * @return the value of field 'FollowHighlight'.
   */
  public boolean isFollowHighlight()
  {
    return this._followHighlight;
  }

  /**
   * Returns the value of field 'followSelection'.
   * 
   * @return the value of field 'FollowSelection'.
   */
  public boolean isFollowSelection()
  {
    return this._followSelection;
  }

  /**
   * Returns the value of field 'gatheredViews'.
   * 
   * @return the value of field 'GatheredViews'.
   */
  public boolean isGatheredViews()
  {
    return this._gatheredViews;
  }

  /**
   * Returns the value of field 'ignoreGapsinConsensus'.
   * 
   * @return the value of field 'IgnoreGapsinConsensus'.
   */
  public boolean isIgnoreGapsinConsensus()
  {
    return this._ignoreGapsinConsensus;
  }

  /**
   * Returns the value of field 'normaliseSequenceLogo'.
   * 
   * @return the value of field 'NormaliseSequenceLogo'.
   */
  public boolean isNormaliseSequenceLogo()
  {
    return this._normaliseSequenceLogo;
  }

  /**
   * Returns the value of field 'pidSelected'.
   * 
   * @return the value of field 'PidSelected'.
   */
  public boolean isPidSelected()
  {
    return this._pidSelected;
  }

  /**
   * Returns the value of field 'renderGaps'.
   * 
   * @return the value of field 'RenderGaps'.
   */
  public boolean isRenderGaps()
  {
    return this._renderGaps;
  }

  /**
   * Returns the value of field 'rightAlignIds'.
   * 
   * @return the value of field 'RightAlignIds'.
   */
  public boolean isRightAlignIds()
  {
    return this._rightAlignIds;
  }

  /**
   * Returns the value of field 'showAnnotation'.
   * 
   * @return the value of field 'ShowAnnotation'.
   */
  public boolean isShowAnnotation()
  {
    return this._showAnnotation;
  }

  /**
   * Returns the value of field 'showBoxes'.
   * 
   * @return the value of field 'ShowBoxes'.
   */
  public boolean isShowBoxes()
  {
    return this._showBoxes;
  }

  /**
   * Returns the value of field 'showColourText'.
   * 
   * @return the value of field 'ShowColourText'.
   */
  public boolean isShowColourText()
  {
    return this._showColourText;
  }

  /**
   * Returns the value of field 'showConsensusHistogram'.
   * 
   * @return the value of field 'ShowConsensusHistogram'.
   */
  public boolean isShowConsensusHistogram()
  {
    return this._showConsensusHistogram;
  }

  /**
   * Returns the value of field 'showDbRefTooltip'.
   * 
   * @return the value of field 'ShowDbRefTooltip'.
   */
  public boolean isShowDbRefTooltip()
  {
    return this._showDbRefTooltip;
  }

  /**
   * Returns the value of field 'showFullId'.
   * 
   * @return the value of field 'ShowFullId'.
   */
  public boolean isShowFullId()
  {
    return this._showFullId;
  }

  /**
   * Returns the value of field 'showGroupConsensus'.
   * 
   * @return the value of field 'ShowGroupConsensus'.
   */
  public boolean isShowGroupConsensus()
  {
    return this._showGroupConsensus;
  }

  /**
   * Returns the value of field 'showGroupConservation'.
   * 
   * @return the value of field 'ShowGroupConservation'.
   */
  public boolean isShowGroupConservation()
  {
    return this._showGroupConservation;
  }

  /**
   * Returns the value of field 'showNPfeatureTooltip'.
   * 
   * @return the value of field 'ShowNPfeatureTooltip'.
   */
  public boolean isShowNPfeatureTooltip()
  {
    return this._showNPfeatureTooltip;
  }

  /**
   * Returns the value of field 'showSequenceFeatures'.
   * 
   * @return the value of field 'ShowSequenceFeatures'.
   */
  public boolean isShowSequenceFeatures()
  {
    return this._showSequenceFeatures;
  }

  /**
   * Returns the value of field 'showSequenceLogo'.
   * 
   * @return the value of field 'ShowSequenceLogo'.
   */
  public boolean isShowSequenceLogo()
  {
    return this._showSequenceLogo;
  }

  /**
   * Returns the value of field 'showText'.
   * 
   * @return the value of field 'ShowText'.
   */
  public boolean isShowText()
  {
    return this._showText;
  }

  /**
   * Returns the value of field 'showUnconserved'.
   * 
   * @return the value of field 'ShowUnconserved'.
   */
  public boolean isShowUnconserved()
  {
    return this._showUnconserved;
  }

  /**
   * Method isValid.
   * 
   * @return true if this object is valid according to the schema
   */
  public boolean isValid()
  {
    try
    {
      validate();
    } catch (org.exolab.castor.xml.ValidationException vex)
    {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of field 'wrapAlignment'.
   * 
   * @return the value of field 'WrapAlignment'.
   */
  public boolean isWrapAlignment()
  {
    return this._wrapAlignment;
  }

  /**
   * 
   * 
   * @param out
   * @throws org.exolab.castor.xml.MarshalException
   *           if object is null or if any SAXException is thrown during
   *           marshaling
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   */
  public void marshal(final java.io.Writer out)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    Marshaller.marshal(this, out);
  }

  /**
   * 
   * 
   * @param handler
   * @throws java.io.IOException
   *           if an IOException occurs during marshaling
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   * @throws org.exolab.castor.xml.MarshalException
   *           if object is null or if any SAXException is thrown during
   *           marshaling
   */
  public void marshal(final org.xml.sax.ContentHandler handler)
          throws java.io.IOException,
          org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    Marshaller.marshal(this, handler);
  }

  /**
     */
  public void removeAllCalcIdParam()
  {
    this._calcIdParamList.clear();
  }

  /**
     */
  public void removeAllHiddenColumns()
  {
    this._hiddenColumnsList.clear();
  }

  /**
   * Method removeCalcIdParam.
   * 
   * @param vCalcIdParam
   * @return true if the object was removed from the collection.
   */
  public boolean removeCalcIdParam(
          final jalview.schemabinding.version2.CalcIdParam vCalcIdParam)
  {
    boolean removed = _calcIdParamList.remove(vCalcIdParam);
    return removed;
  }

  /**
   * Method removeCalcIdParamAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.CalcIdParam removeCalcIdParamAt(
          final int index)
  {
    java.lang.Object obj = this._calcIdParamList.remove(index);
    return (jalview.schemabinding.version2.CalcIdParam) obj;
  }

  /**
   * Method removeHiddenColumns.
   * 
   * @param vHiddenColumns
   * @return true if the object was removed from the collection.
   */
  public boolean removeHiddenColumns(
          final jalview.schemabinding.version2.HiddenColumns vHiddenColumns)
  {
    boolean removed = _hiddenColumnsList.remove(vHiddenColumns);
    return removed;
  }

  /**
   * Method removeHiddenColumnsAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.HiddenColumns removeHiddenColumnsAt(
          final int index)
  {
    java.lang.Object obj = this._hiddenColumnsList.remove(index);
    return (jalview.schemabinding.version2.HiddenColumns) obj;
  }

  /**
   * Sets the value of field 'annotationColours'.
   * 
   * @param annotationColours
   *          the value of field 'annotationColours'.
   */
  public void setAnnotationColours(
          final jalview.schemabinding.version2.AnnotationColours annotationColours)
  {
    this._annotationColours = annotationColours;
  }

  /**
   * Sets the value of field 'bgColour'.
   * 
   * @param bgColour
   *          the value of field 'bgColour'.
   */
  public void setBgColour(final java.lang.String bgColour)
  {
    this._bgColour = bgColour;
  }

  /**
   * 
   * 
   * @param index
   * @param vCalcIdParam
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setCalcIdParam(final int index,
          final jalview.schemabinding.version2.CalcIdParam vCalcIdParam)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._calcIdParamList.size())
    {
      throw new IndexOutOfBoundsException("setCalcIdParam: Index value '"
              + index + "' not in range [0.."
              + (this._calcIdParamList.size() - 1) + "]");
    }

    this._calcIdParamList.set(index, vCalcIdParam);
  }

  /**
   * 
   * 
   * @param vCalcIdParamArray
   */
  public void setCalcIdParam(
          final jalview.schemabinding.version2.CalcIdParam[] vCalcIdParamArray)
  {
    // -- copy array
    _calcIdParamList.clear();

    for (int i = 0; i < vCalcIdParamArray.length; i++)
    {
      this._calcIdParamList.add(vCalcIdParamArray[i]);
    }
  }

  /**
   * Sets the value of field 'centreColumnLabels'.
   * 
   * @param centreColumnLabels
   *          the value of field 'centreColumnLabels'.
   */
  public void setCentreColumnLabels(final boolean centreColumnLabels)
  {
    this._centreColumnLabels = centreColumnLabels;
    this._has_centreColumnLabels = true;
  }

  /**
   * Sets the value of field 'consThreshold'.
   * 
   * @param consThreshold
   *          the value of field 'consThreshold'.
   */
  public void setConsThreshold(final int consThreshold)
  {
    this._consThreshold = consThreshold;
    this._has_consThreshold = true;
  }

  /**
   * Sets the value of field 'conservationSelected'.
   * 
   * @param conservationSelected
   *          the value of field 'conservationSelected'.
   */
  public void setConservationSelected(final boolean conservationSelected)
  {
    this._conservationSelected = conservationSelected;
    this._has_conservationSelected = true;
  }

  /**
   * Sets the value of field 'followHighlight'.
   * 
   * @param followHighlight
   *          the value of field 'followHighlight'.
   */
  public void setFollowHighlight(final boolean followHighlight)
  {
    this._followHighlight = followHighlight;
    this._has_followHighlight = true;
  }

  /**
   * Sets the value of field 'followSelection'.
   * 
   * @param followSelection
   *          the value of field 'followSelection'.
   */
  public void setFollowSelection(final boolean followSelection)
  {
    this._followSelection = followSelection;
    this._has_followSelection = true;
  }

  /**
   * Sets the value of field 'fontName'.
   * 
   * @param fontName
   *          the value of field 'fontName'.
   */
  public void setFontName(final java.lang.String fontName)
  {
    this._fontName = fontName;
  }

  /**
   * Sets the value of field 'fontSize'.
   * 
   * @param fontSize
   *          the value of field 'fontSize'.
   */
  public void setFontSize(final int fontSize)
  {
    this._fontSize = fontSize;
    this._has_fontSize = true;
  }

  /**
   * Sets the value of field 'fontStyle'.
   * 
   * @param fontStyle
   *          the value of field 'fontStyle'.
   */
  public void setFontStyle(final int fontStyle)
  {
    this._fontStyle = fontStyle;
    this._has_fontStyle = true;
  }

  /**
   * Sets the value of field 'gatheredViews'.
   * 
   * @param gatheredViews
   *          the value of field 'gatheredViews'.
   */
  public void setGatheredViews(final boolean gatheredViews)
  {
    this._gatheredViews = gatheredViews;
    this._has_gatheredViews = true;
  }

  /**
   * Sets the value of field 'height'.
   * 
   * @param height
   *          the value of field 'height'.
   */
  public void setHeight(final int height)
  {
    this._height = height;
    this._has_height = true;
  }

  /**
   * 
   * 
   * @param index
   * @param vHiddenColumns
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setHiddenColumns(final int index,
          final jalview.schemabinding.version2.HiddenColumns vHiddenColumns)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._hiddenColumnsList.size())
    {
      throw new IndexOutOfBoundsException("setHiddenColumns: Index value '"
              + index + "' not in range [0.."
              + (this._hiddenColumnsList.size() - 1) + "]");
    }

    this._hiddenColumnsList.set(index, vHiddenColumns);
  }

  /**
   * 
   * 
   * @param vHiddenColumnsArray
   */
  public void setHiddenColumns(
          final jalview.schemabinding.version2.HiddenColumns[] vHiddenColumnsArray)
  {
    // -- copy array
    _hiddenColumnsList.clear();

    for (int i = 0; i < vHiddenColumnsArray.length; i++)
    {
      this._hiddenColumnsList.add(vHiddenColumnsArray[i]);
    }
  }

  /**
   * Sets the value of field 'id'. The field 'id' has the following description:
   * unique id used by jalview to synchronize between stored and instantiated
   * views
   * 
   * 
   * @param id
   *          the value of field 'id'.
   */
  public void setId(final java.lang.String id)
  {
    this._id = id;
  }

  /**
   * Sets the value of field 'ignoreGapsinConsensus'.
   * 
   * @param ignoreGapsinConsensus
   *          the value of field 'ignoreGapsinConsensus'.
   */
  public void setIgnoreGapsinConsensus(final boolean ignoreGapsinConsensus)
  {
    this._ignoreGapsinConsensus = ignoreGapsinConsensus;
    this._has_ignoreGapsinConsensus = true;
  }

  /**
   * Sets the value of field 'normaliseSequenceLogo'.
   * 
   * @param normaliseSequenceLogo
   *          the value of field 'normaliseSequenceLogo'.
   */
  public void setNormaliseSequenceLogo(final boolean normaliseSequenceLogo)
  {
    this._normaliseSequenceLogo = normaliseSequenceLogo;
    this._has_normaliseSequenceLogo = true;
  }

  /**
   * Sets the value of field 'pidSelected'.
   * 
   * @param pidSelected
   *          the value of field 'pidSelected'.
   */
  public void setPidSelected(final boolean pidSelected)
  {
    this._pidSelected = pidSelected;
    this._has_pidSelected = true;
  }

  /**
   * Sets the value of field 'pidThreshold'.
   * 
   * @param pidThreshold
   *          the value of field 'pidThreshold'.
   */
  public void setPidThreshold(final int pidThreshold)
  {
    this._pidThreshold = pidThreshold;
    this._has_pidThreshold = true;
  }

  /**
   * Sets the value of field 'renderGaps'.
   * 
   * @param renderGaps
   *          the value of field 'renderGaps'.
   */
  public void setRenderGaps(final boolean renderGaps)
  {
    this._renderGaps = renderGaps;
    this._has_renderGaps = true;
  }

  /**
   * Sets the value of field 'rightAlignIds'.
   * 
   * @param rightAlignIds
   *          the value of field 'rightAlignIds'.
   */
  public void setRightAlignIds(final boolean rightAlignIds)
  {
    this._rightAlignIds = rightAlignIds;
    this._has_rightAlignIds = true;
  }

  /**
   * Sets the value of field 'sequenceSetId'.
   * 
   * @param sequenceSetId
   *          the value of field 'sequenceSetId'.
   */
  public void setSequenceSetId(final java.lang.String sequenceSetId)
  {
    this._sequenceSetId = sequenceSetId;
  }

  /**
   * Sets the value of field 'showAnnotation'.
   * 
   * @param showAnnotation
   *          the value of field 'showAnnotation'.
   */
  public void setShowAnnotation(final boolean showAnnotation)
  {
    this._showAnnotation = showAnnotation;
    this._has_showAnnotation = true;
  }

  /**
   * Sets the value of field 'showBoxes'.
   * 
   * @param showBoxes
   *          the value of field 'showBoxes'.
   */
  public void setShowBoxes(final boolean showBoxes)
  {
    this._showBoxes = showBoxes;
    this._has_showBoxes = true;
  }

  /**
   * Sets the value of field 'showColourText'.
   * 
   * @param showColourText
   *          the value of field 'showColourText'.
   */
  public void setShowColourText(final boolean showColourText)
  {
    this._showColourText = showColourText;
    this._has_showColourText = true;
  }

  /**
   * Sets the value of field 'showConsensusHistogram'.
   * 
   * @param showConsensusHistogram
   *          the value of field 'showConsensusHistogram'.
   */
  public void setShowConsensusHistogram(final boolean showConsensusHistogram)
  {
    this._showConsensusHistogram = showConsensusHistogram;
    this._has_showConsensusHistogram = true;
  }

  /**
   * Sets the value of field 'showDbRefTooltip'.
   * 
   * @param showDbRefTooltip
   *          the value of field 'showDbRefTooltip'
   */
  public void setShowDbRefTooltip(final boolean showDbRefTooltip)
  {
    this._showDbRefTooltip = showDbRefTooltip;
    this._has_showDbRefTooltip = true;
  }

  /**
   * Sets the value of field 'showFullId'.
   * 
   * @param showFullId
   *          the value of field 'showFullId'.
   */
  public void setShowFullId(final boolean showFullId)
  {
    this._showFullId = showFullId;
    this._has_showFullId = true;
  }

  /**
   * Sets the value of field 'showGroupConsensus'.
   * 
   * @param showGroupConsensus
   *          the value of field 'showGroupConsensus'.
   */
  public void setShowGroupConsensus(final boolean showGroupConsensus)
  {
    this._showGroupConsensus = showGroupConsensus;
    this._has_showGroupConsensus = true;
  }

  /**
   * Sets the value of field 'showGroupConservation'.
   * 
   * @param showGroupConservation
   *          the value of field 'showGroupConservation'.
   */
  public void setShowGroupConservation(final boolean showGroupConservation)
  {
    this._showGroupConservation = showGroupConservation;
    this._has_showGroupConservation = true;
  }

  /**
   * Sets the value of field 'showNPfeatureTooltip'.
   * 
   * @param showNPfeatureTooltip
   *          the value of field 'showNPfeatureTooltip'.
   */
  public void setShowNPfeatureTooltip(final boolean showNPfeatureTooltip)
  {
    this._showNPfeatureTooltip = showNPfeatureTooltip;
    this._has_showNPfeatureTooltip = true;
  }

  /**
   * Sets the value of field 'showSequenceFeatures'.
   * 
   * @param showSequenceFeatures
   *          the value of field 'showSequenceFeatures'.
   */
  public void setShowSequenceFeatures(final boolean showSequenceFeatures)
  {
    this._showSequenceFeatures = showSequenceFeatures;
    this._has_showSequenceFeatures = true;
  }

  /**
   * Sets the value of field 'showSequenceLogo'.
   * 
   * @param showSequenceLogo
   *          the value of field 'showSequenceLogo'
   */
  public void setShowSequenceLogo(final boolean showSequenceLogo)
  {
    this._showSequenceLogo = showSequenceLogo;
    this._has_showSequenceLogo = true;
  }

  /**
   * Sets the value of field 'showText'.
   * 
   * @param showText
   *          the value of field 'showText'.
   */
  public void setShowText(final boolean showText)
  {
    this._showText = showText;
    this._has_showText = true;
  }

  /**
   * Sets the value of field 'showUnconserved'.
   * 
   * @param showUnconserved
   *          the value of field 'showUnconserved'.
   */
  public void setShowUnconserved(final boolean showUnconserved)
  {
    this._showUnconserved = showUnconserved;
    this._has_showUnconserved = true;
  }

  /**
   * Sets the value of field 'startRes'.
   * 
   * @param startRes
   *          the value of field 'startRes'.
   */
  public void setStartRes(final int startRes)
  {
    this._startRes = startRes;
    this._has_startRes = true;
  }

  /**
   * Sets the value of field 'startSeq'.
   * 
   * @param startSeq
   *          the value of field 'startSeq'.
   */
  public void setStartSeq(final int startSeq)
  {
    this._startSeq = startSeq;
    this._has_startSeq = true;
  }

  /**
   * Sets the value of field 'textCol1'.
   * 
   * @param textCol1
   *          the value of field 'textCol1'.
   */
  public void setTextCol1(final int textCol1)
  {
    this._textCol1 = textCol1;
    this._has_textCol1 = true;
  }

  /**
   * Sets the value of field 'textCol2'.
   * 
   * @param textCol2
   *          the value of field 'textCol2'.
   */
  public void setTextCol2(final int textCol2)
  {
    this._textCol2 = textCol2;
    this._has_textCol2 = true;
  }

  /**
   * Sets the value of field 'textColThreshold'.
   * 
   * @param textColThreshold
   *          the value of field 'textColThreshold'
   */
  public void setTextColThreshold(final int textColThreshold)
  {
    this._textColThreshold = textColThreshold;
    this._has_textColThreshold = true;
  }

  /**
   * Sets the value of field 'title'.
   * 
   * @param title
   *          the value of field 'title'.
   */
  public void setTitle(final java.lang.String title)
  {
    this._title = title;
  }

  /**
   * Sets the value of field 'viewName'.
   * 
   * @param viewName
   *          the value of field 'viewName'.
   */
  public void setViewName(final java.lang.String viewName)
  {
    this._viewName = viewName;
  }

  /**
   * Sets the value of field 'width'.
   * 
   * @param width
   *          the value of field 'width'.
   */
  public void setWidth(final int width)
  {
    this._width = width;
    this._has_width = true;
  }

  /**
   * Sets the value of field 'wrapAlignment'.
   * 
   * @param wrapAlignment
   *          the value of field 'wrapAlignment'.
   */
  public void setWrapAlignment(final boolean wrapAlignment)
  {
    this._wrapAlignment = wrapAlignment;
    this._has_wrapAlignment = true;
  }

  /**
   * Sets the value of field 'xpos'.
   * 
   * @param xpos
   *          the value of field 'xpos'.
   */
  public void setXpos(final int xpos)
  {
    this._xpos = xpos;
    this._has_xpos = true;
  }

  /**
   * Sets the value of field 'ypos'.
   * 
   * @param ypos
   *          the value of field 'ypos'.
   */
  public void setYpos(final int ypos)
  {
    this._ypos = ypos;
    this._has_ypos = true;
  }

  /**
   * Method unmarshal.
   * 
   * @param reader
   * @throws org.exolab.castor.xml.MarshalException
   *           if object is null or if any SAXException is thrown during
   *           marshaling
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   * @return the unmarshaled jalview.schemabinding.version2.Viewport
   */
  public static jalview.schemabinding.version2.Viewport unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.Viewport) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.Viewport.class,
                    reader);
  }

  /**
   * 
   * 
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   */
  public void validate() throws org.exolab.castor.xml.ValidationException
  {
    org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
    validator.validate(this);
  }

}
