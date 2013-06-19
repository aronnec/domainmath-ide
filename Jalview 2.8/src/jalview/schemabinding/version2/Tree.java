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
 * Class Tree.
 * 
 * @version $Revision$ $Date$
 */
public class Tree implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

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
   * Field _threshold.
   */
  private float _threshold;

  /**
   * keeps track of state for field: _threshold
   */
  private boolean _has_threshold;

  /**
   * Field _showBootstrap.
   */
  private boolean _showBootstrap;

  /**
   * keeps track of state for field: _showBootstrap
   */
  private boolean _has_showBootstrap;

  /**
   * Field _showDistances.
   */
  private boolean _showDistances;

  /**
   * keeps track of state for field: _showDistances
   */
  private boolean _has_showDistances;

  /**
   * Field _markUnlinked.
   */
  private boolean _markUnlinked;

  /**
   * keeps track of state for field: _markUnlinked
   */
  private boolean _has_markUnlinked;

  /**
   * Field _fitToWindow.
   */
  private boolean _fitToWindow;

  /**
   * keeps track of state for field: _fitToWindow
   */
  private boolean _has_fitToWindow;

  /**
   * Field _currentTree.
   */
  private boolean _currentTree;

  /**
   * keeps track of state for field: _currentTree
   */
  private boolean _has_currentTree;

  /**
   * Tree ID added for binding tree visualization settings to vamsas document
   * trees in jalview 2.4.1
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
   * Field _title.
   */
  private java.lang.String _title;

  /**
   * Field _newick.
   */
  private java.lang.String _newick;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Tree()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deleteCurrentTree()
  {
    this._has_currentTree = false;
  }

  /**
     */
  public void deleteFitToWindow()
  {
    this._has_fitToWindow = false;
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
  public void deleteMarkUnlinked()
  {
    this._has_markUnlinked = false;
  }

  /**
     */
  public void deleteShowBootstrap()
  {
    this._has_showBootstrap = false;
  }

  /**
     */
  public void deleteShowDistances()
  {
    this._has_showDistances = false;
  }

  /**
     */
  public void deleteThreshold()
  {
    this._has_threshold = false;
  }

  /**
     */
  public void deleteWidth()
  {
    this._has_width = false;
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
   * Returns the value of field 'currentTree'.
   * 
   * @return the value of field 'CurrentTree'.
   */
  public boolean getCurrentTree()
  {
    return this._currentTree;
  }

  /**
   * Returns the value of field 'fitToWindow'.
   * 
   * @return the value of field 'FitToWindow'.
   */
  public boolean getFitToWindow()
  {
    return this._fitToWindow;
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
   * Returns the value of field 'id'. The field 'id' has the following
   * description: Tree ID added for binding tree visualization settings to
   * vamsas document trees in jalview 2.4.1
   * 
   * 
   * @return the value of field 'Id'.
   */
  public java.lang.String getId()
  {
    return this._id;
  }

  /**
   * Returns the value of field 'markUnlinked'.
   * 
   * @return the value of field 'MarkUnlinked'.
   */
  public boolean getMarkUnlinked()
  {
    return this._markUnlinked;
  }

  /**
   * Returns the value of field 'newick'.
   * 
   * @return the value of field 'Newick'.
   */
  public java.lang.String getNewick()
  {
    return this._newick;
  }

  /**
   * Returns the value of field 'showBootstrap'.
   * 
   * @return the value of field 'ShowBootstrap'.
   */
  public boolean getShowBootstrap()
  {
    return this._showBootstrap;
  }

  /**
   * Returns the value of field 'showDistances'.
   * 
   * @return the value of field 'ShowDistances'.
   */
  public boolean getShowDistances()
  {
    return this._showDistances;
  }

  /**
   * Returns the value of field 'threshold'.
   * 
   * @return the value of field 'Threshold'.
   */
  public float getThreshold()
  {
    return this._threshold;
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
   * Method hasCurrentTree.
   * 
   * @return true if at least one CurrentTree has been added
   */
  public boolean hasCurrentTree()
  {
    return this._has_currentTree;
  }

  /**
   * Method hasFitToWindow.
   * 
   * @return true if at least one FitToWindow has been added
   */
  public boolean hasFitToWindow()
  {
    return this._has_fitToWindow;
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
   * Method hasMarkUnlinked.
   * 
   * @return true if at least one MarkUnlinked has been added
   */
  public boolean hasMarkUnlinked()
  {
    return this._has_markUnlinked;
  }

  /**
   * Method hasShowBootstrap.
   * 
   * @return true if at least one ShowBootstrap has been added
   */
  public boolean hasShowBootstrap()
  {
    return this._has_showBootstrap;
  }

  /**
   * Method hasShowDistances.
   * 
   * @return true if at least one ShowDistances has been added
   */
  public boolean hasShowDistances()
  {
    return this._has_showDistances;
  }

  /**
   * Method hasThreshold.
   * 
   * @return true if at least one Threshold has been added
   */
  public boolean hasThreshold()
  {
    return this._has_threshold;
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
   * Returns the value of field 'currentTree'.
   * 
   * @return the value of field 'CurrentTree'.
   */
  public boolean isCurrentTree()
  {
    return this._currentTree;
  }

  /**
   * Returns the value of field 'fitToWindow'.
   * 
   * @return the value of field 'FitToWindow'.
   */
  public boolean isFitToWindow()
  {
    return this._fitToWindow;
  }

  /**
   * Returns the value of field 'markUnlinked'.
   * 
   * @return the value of field 'MarkUnlinked'.
   */
  public boolean isMarkUnlinked()
  {
    return this._markUnlinked;
  }

  /**
   * Returns the value of field 'showBootstrap'.
   * 
   * @return the value of field 'ShowBootstrap'.
   */
  public boolean isShowBootstrap()
  {
    return this._showBootstrap;
  }

  /**
   * Returns the value of field 'showDistances'.
   * 
   * @return the value of field 'ShowDistances'.
   */
  public boolean isShowDistances()
  {
    return this._showDistances;
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
   * Sets the value of field 'currentTree'.
   * 
   * @param currentTree
   *          the value of field 'currentTree'.
   */
  public void setCurrentTree(final boolean currentTree)
  {
    this._currentTree = currentTree;
    this._has_currentTree = true;
  }

  /**
   * Sets the value of field 'fitToWindow'.
   * 
   * @param fitToWindow
   *          the value of field 'fitToWindow'.
   */
  public void setFitToWindow(final boolean fitToWindow)
  {
    this._fitToWindow = fitToWindow;
    this._has_fitToWindow = true;
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
   * Sets the value of field 'id'. The field 'id' has the following description:
   * Tree ID added for binding tree visualization settings to vamsas document
   * trees in jalview 2.4.1
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
   * Sets the value of field 'markUnlinked'.
   * 
   * @param markUnlinked
   *          the value of field 'markUnlinked'.
   */
  public void setMarkUnlinked(final boolean markUnlinked)
  {
    this._markUnlinked = markUnlinked;
    this._has_markUnlinked = true;
  }

  /**
   * Sets the value of field 'newick'.
   * 
   * @param newick
   *          the value of field 'newick'.
   */
  public void setNewick(final java.lang.String newick)
  {
    this._newick = newick;
  }

  /**
   * Sets the value of field 'showBootstrap'.
   * 
   * @param showBootstrap
   *          the value of field 'showBootstrap'.
   */
  public void setShowBootstrap(final boolean showBootstrap)
  {
    this._showBootstrap = showBootstrap;
    this._has_showBootstrap = true;
  }

  /**
   * Sets the value of field 'showDistances'.
   * 
   * @param showDistances
   *          the value of field 'showDistances'.
   */
  public void setShowDistances(final boolean showDistances)
  {
    this._showDistances = showDistances;
    this._has_showDistances = true;
  }

  /**
   * Sets the value of field 'threshold'.
   * 
   * @param threshold
   *          the value of field 'threshold'.
   */
  public void setThreshold(final float threshold)
  {
    this._threshold = threshold;
    this._has_threshold = true;
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
   * @return the unmarshaled jalview.schemabinding.version2.Tree
   */
  public static jalview.schemabinding.version2.Tree unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.Tree) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.Tree.class, reader);
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
