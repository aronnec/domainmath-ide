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
package jalview.binding;

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
   * Field _showAnnotation.
   */
  private boolean _showAnnotation;

  /**
   * keeps track of state for field: _showAnnotation
   */
  private boolean _has_showAnnotation;

  /**
   * Field _showConservation.
   */
  private boolean _showConservation;

  /**
   * keeps track of state for field: _showConservation
   */
  private boolean _has_showConservation;

  /**
   * Field _showQuality.
   */
  private boolean _showQuality;

  /**
   * keeps track of state for field: _showQuality
   */
  private boolean _has_showQuality;

  /**
   * Field _showIdentity.
   */
  private boolean _showIdentity;

  /**
   * keeps track of state for field: _showIdentity
   */
  private boolean _has_showIdentity;

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

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Viewport()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

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
  public void deleteHeight()
  {
    this._has_height = false;
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
  public void deleteShowConservation()
  {
    this._has_showConservation = false;
  }

  /**
     */
  public void deleteShowFullId()
  {
    this._has_showFullId = false;
  }

  /**
     */
  public void deleteShowIdentity()
  {
    this._has_showIdentity = false;
  }

  /**
     */
  public void deleteShowQuality()
  {
    this._has_showQuality = false;
  }

  /**
     */
  public void deleteShowSequenceFeatures()
  {
    this._has_showSequenceFeatures = false;
  }

  /**
     */
  public void deleteShowText()
  {
    this._has_showText = false;
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
   * Returns the value of field 'bgColour'.
   * 
   * @return the value of field 'BgColour'.
   */
  public java.lang.String getBgColour()
  {
    return this._bgColour;
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
   * Returns the value of field 'height'.
   * 
   * @return the value of field 'Height'.
   */
  public int getHeight()
  {
    return this._height;
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
   * Returns the value of field 'showConservation'.
   * 
   * @return the value of field 'ShowConservation'.
   */
  public boolean getShowConservation()
  {
    return this._showConservation;
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
   * Returns the value of field 'showIdentity'.
   * 
   * @return the value of field 'ShowIdentity'.
   */
  public boolean getShowIdentity()
  {
    return this._showIdentity;
  }

  /**
   * Returns the value of field 'showQuality'.
   * 
   * @return the value of field 'ShowQuality'.
   */
  public boolean getShowQuality()
  {
    return this._showQuality;
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
   * Returns the value of field 'showText'.
   * 
   * @return the value of field 'ShowText'.
   */
  public boolean getShowText()
  {
    return this._showText;
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
   * Returns the value of field 'title'.
   * 
   * @return the value of field 'Title'.
   */
  public java.lang.String getTitle()
  {
    return this._title;
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
   * Method hasHeight.
   * 
   * @return true if at least one Height has been added
   */
  public boolean hasHeight()
  {
    return this._has_height;
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
   * Method hasShowConservation.
   * 
   * @return true if at least one ShowConservation has been added
   */
  public boolean hasShowConservation()
  {
    return this._has_showConservation;
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
   * Method hasShowIdentity.
   * 
   * @return true if at least one ShowIdentity has been added
   */
  public boolean hasShowIdentity()
  {
    return this._has_showIdentity;
  }

  /**
   * Method hasShowQuality.
   * 
   * @return true if at least one ShowQuality has been added
   */
  public boolean hasShowQuality()
  {
    return this._has_showQuality;
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
   * Method hasShowText.
   * 
   * @return true if at least one ShowText has been added
   */
  public boolean hasShowText()
  {
    return this._has_showText;
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
   * Returns the value of field 'conservationSelected'.
   * 
   * @return the value of field 'ConservationSelected'.
   */
  public boolean isConservationSelected()
  {
    return this._conservationSelected;
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
   * Returns the value of field 'showConservation'.
   * 
   * @return the value of field 'ShowConservation'.
   */
  public boolean isShowConservation()
  {
    return this._showConservation;
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
   * Returns the value of field 'showIdentity'.
   * 
   * @return the value of field 'ShowIdentity'.
   */
  public boolean isShowIdentity()
  {
    return this._showIdentity;
  }

  /**
   * Returns the value of field 'showQuality'.
   * 
   * @return the value of field 'ShowQuality'.
   */
  public boolean isShowQuality()
  {
    return this._showQuality;
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
   * Returns the value of field 'showText'.
   * 
   * @return the value of field 'ShowText'.
   */
  public boolean isShowText()
  {
    return this._showText;
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
   * Sets the value of field 'showConservation'.
   * 
   * @param showConservation
   *          the value of field 'showConservation'
   */
  public void setShowConservation(final boolean showConservation)
  {
    this._showConservation = showConservation;
    this._has_showConservation = true;
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
   * Sets the value of field 'showIdentity'.
   * 
   * @param showIdentity
   *          the value of field 'showIdentity'.
   */
  public void setShowIdentity(final boolean showIdentity)
  {
    this._showIdentity = showIdentity;
    this._has_showIdentity = true;
  }

  /**
   * Sets the value of field 'showQuality'.
   * 
   * @param showQuality
   *          the value of field 'showQuality'.
   */
  public void setShowQuality(final boolean showQuality)
  {
    this._showQuality = showQuality;
    this._has_showQuality = true;
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
   * @return the unmarshaled jalview.binding.Viewport
   */
  public static jalview.binding.Viewport unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.Viewport) Unmarshaller.unmarshal(
            jalview.binding.Viewport.class, reader);
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
