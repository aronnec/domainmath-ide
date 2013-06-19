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
 * Class StructureState.
 * 
 * @version $Revision$ $Date$
 */
public class StructureState implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * internal content storage
   */
  private java.lang.String _content = "";

  /**
   * Field _visible.
   */
  private boolean _visible;

  /**
   * keeps track of state for field: _visible
   */
  private boolean _has_visible;

  /**
   * additional identifier which properly disambiguates the structure view from
   * any other view with the same attributes. This is not an ID, because it is
   * possible to have many references to the same physical structure view from
   * different sequences in an alignment. A structureState element citing the
   * same viewId will appear for each instance.
   * 
   */
  private java.lang.String _viewId;

  /**
   * Flag set if the alignment panel containing this JSeq should be included in
   * those used to perform a structure superposition (since Jalview 2.7).
   * 
   */
  private boolean _alignwithAlignPanel = true;

  /**
   * keeps track of state for field: _alignwithAlignPanel
   */
  private boolean _has_alignwithAlignPanel;

  /**
   * Flag set if the alignment panel containing this JSeq should be included in
   * those used to colour its associated sequences in this structureState(since
   * Jalview 2.7).
   * 
   */
  private boolean _colourwithAlignPanel = false;

  /**
   * keeps track of state for field: _colourwithAlignPanel
   */
  private boolean _has_colourwithAlignPanel;

  /**
   * Flag set if the structure display is coloured by the Jmol state, rather
   * than by one or more linked alignment views.
   * 
   */
  private boolean _colourByJmol = true;

  /**
   * keeps track of state for field: _colourByJmol
   */
  private boolean _has_colourByJmol;

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

  // ----------------/
  // - Constructors -/
  // ----------------/

  public StructureState()
  {
    super();
    setContent("");
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deleteAlignwithAlignPanel()
  {
    this._has_alignwithAlignPanel = false;
  }

  /**
     */
  public void deleteColourByJmol()
  {
    this._has_colourByJmol = false;
  }

  /**
     */
  public void deleteColourwithAlignPanel()
  {
    this._has_colourwithAlignPanel = false;
  }

  /**
     */
  public void deleteHeight()
  {
    this._has_height = false;
  }

  /**
     */
  public void deleteVisible()
  {
    this._has_visible = false;
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
   * Returns the value of field 'alignwithAlignPanel'. The field
   * 'alignwithAlignPanel' has the following description: Flag set if the
   * alignment panel containing this JSeq should be included in those used to
   * perform a structure superposition (since Jalview 2.7).
   * 
   * 
   * @return the value of field 'AlignwithAlignPanel'.
   */
  public boolean getAlignwithAlignPanel()
  {
    return this._alignwithAlignPanel;
  }

  /**
   * Returns the value of field 'colourByJmol'. The field 'colourByJmol' has the
   * following description: Flag set if the structure display is coloured by the
   * Jmol state, rather than by one or more linked alignment views.
   * 
   * 
   * @return the value of field 'ColourByJmol'.
   */
  public boolean getColourByJmol()
  {
    return this._colourByJmol;
  }

  /**
   * Returns the value of field 'colourwithAlignPanel'. The field
   * 'colourwithAlignPanel' has the following description: Flag set if the
   * alignment panel containing this JSeq should be included in those used to
   * colour its associated sequences in this structureState(since Jalview 2.7).
   * 
   * 
   * @return the value of field 'ColourwithAlignPanel'.
   */
  public boolean getColourwithAlignPanel()
  {
    return this._colourwithAlignPanel;
  }

  /**
   * Returns the value of field 'content'. The field 'content' has the following
   * description: internal content storage
   * 
   * @return the value of field 'Content'.
   */
  public java.lang.String getContent()
  {
    return this._content;
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
   * Returns the value of field 'viewId'. The field 'viewId' has the following
   * description: additional identifier which properly disambiguates the
   * structure view from any other view with the same attributes. This is not an
   * ID, because it is possible to have many references to the same physical
   * structure view from different sequences in an alignment. A structureState
   * element citing the same viewId will appear for each instance.
   * 
   * 
   * @return the value of field 'ViewId'.
   */
  public java.lang.String getViewId()
  {
    return this._viewId;
  }

  /**
   * Returns the value of field 'visible'.
   * 
   * @return the value of field 'Visible'.
   */
  public boolean getVisible()
  {
    return this._visible;
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
   * Method hasAlignwithAlignPanel.
   * 
   * @return true if at least one AlignwithAlignPanel has been added
   */
  public boolean hasAlignwithAlignPanel()
  {
    return this._has_alignwithAlignPanel;
  }

  /**
   * Method hasColourByJmol.
   * 
   * @return true if at least one ColourByJmol has been added
   */
  public boolean hasColourByJmol()
  {
    return this._has_colourByJmol;
  }

  /**
   * Method hasColourwithAlignPanel.
   * 
   * @return true if at least one ColourwithAlignPanel has been added
   */
  public boolean hasColourwithAlignPanel()
  {
    return this._has_colourwithAlignPanel;
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
   * Method hasVisible.
   * 
   * @return true if at least one Visible has been added
   */
  public boolean hasVisible()
  {
    return this._has_visible;
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
   * Returns the value of field 'alignwithAlignPanel'. The field
   * 'alignwithAlignPanel' has the following description: Flag set if the
   * alignment panel containing this JSeq should be included in those used to
   * perform a structure superposition (since Jalview 2.7).
   * 
   * 
   * @return the value of field 'AlignwithAlignPanel'.
   */
  public boolean isAlignwithAlignPanel()
  {
    return this._alignwithAlignPanel;
  }

  /**
   * Returns the value of field 'colourByJmol'. The field 'colourByJmol' has the
   * following description: Flag set if the structure display is coloured by the
   * Jmol state, rather than by one or more linked alignment views.
   * 
   * 
   * @return the value of field 'ColourByJmol'.
   */
  public boolean isColourByJmol()
  {
    return this._colourByJmol;
  }

  /**
   * Returns the value of field 'colourwithAlignPanel'. The field
   * 'colourwithAlignPanel' has the following description: Flag set if the
   * alignment panel containing this JSeq should be included in those used to
   * colour its associated sequences in this structureState(since Jalview 2.7).
   * 
   * 
   * @return the value of field 'ColourwithAlignPanel'.
   */
  public boolean isColourwithAlignPanel()
  {
    return this._colourwithAlignPanel;
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
   * Returns the value of field 'visible'.
   * 
   * @return the value of field 'Visible'.
   */
  public boolean isVisible()
  {
    return this._visible;
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
   * Sets the value of field 'alignwithAlignPanel'. The field
   * 'alignwithAlignPanel' has the following description: Flag set if the
   * alignment panel containing this JSeq should be included in those used to
   * perform a structure superposition (since Jalview 2.7).
   * 
   * 
   * @param alignwithAlignPanel
   *          the value of field 'alignwithAlignPanel'.
   */
  public void setAlignwithAlignPanel(final boolean alignwithAlignPanel)
  {
    this._alignwithAlignPanel = alignwithAlignPanel;
    this._has_alignwithAlignPanel = true;
  }

  /**
   * Sets the value of field 'colourByJmol'. The field 'colourByJmol' has the
   * following description: Flag set if the structure display is coloured by the
   * Jmol state, rather than by one or more linked alignment views.
   * 
   * 
   * @param colourByJmol
   *          the value of field 'colourByJmol'.
   */
  public void setColourByJmol(final boolean colourByJmol)
  {
    this._colourByJmol = colourByJmol;
    this._has_colourByJmol = true;
  }

  /**
   * Sets the value of field 'colourwithAlignPanel'. The field
   * 'colourwithAlignPanel' has the following description: Flag set if the
   * alignment panel containing this JSeq should be included in those used to
   * colour its associated sequences in this structureState(since Jalview 2.7).
   * 
   * 
   * @param colourwithAlignPanel
   *          the value of field 'colourwithAlignPanel'.
   */
  public void setColourwithAlignPanel(final boolean colourwithAlignPanel)
  {
    this._colourwithAlignPanel = colourwithAlignPanel;
    this._has_colourwithAlignPanel = true;
  }

  /**
   * Sets the value of field 'content'. The field 'content' has the following
   * description: internal content storage
   * 
   * @param content
   *          the value of field 'content'.
   */
  public void setContent(final java.lang.String content)
  {
    this._content = content;
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
   * Sets the value of field 'viewId'. The field 'viewId' has the following
   * description: additional identifier which properly disambiguates the
   * structure view from any other view with the same attributes. This is not an
   * ID, because it is possible to have many references to the same physical
   * structure view from different sequences in an alignment. A structureState
   * element citing the same viewId will appear for each instance.
   * 
   * 
   * @param viewId
   *          the value of field 'viewId'.
   */
  public void setViewId(final java.lang.String viewId)
  {
    this._viewId = viewId;
  }

  /**
   * Sets the value of field 'visible'.
   * 
   * @param visible
   *          the value of field 'visible'.
   */
  public void setVisible(final boolean visible)
  {
    this._visible = visible;
    this._has_visible = true;
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
   * @return the unmarshaled jalview.schemabinding.version2.StructureState
   */
  public static jalview.schemabinding.version2.StructureState unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.StructureState) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.StructureState.class,
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
