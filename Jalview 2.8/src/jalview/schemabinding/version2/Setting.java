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
 * Class Setting.
 * 
 * @version $Revision$ $Date$
 */
public class Setting implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _type.
   */
  private java.lang.String _type;

  /**
   * Field _colour.
   */
  private int _colour;

  /**
   * keeps track of state for field: _colour
   */
  private boolean _has_colour;

  /**
   * Field _display.
   */
  private boolean _display;

  /**
   * keeps track of state for field: _display
   */
  private boolean _has_display;

  /**
   * Field _order.
   */
  private float _order;

  /**
   * keeps track of state for field: _order
   */
  private boolean _has_order;

  /**
   * Optional minimum colour for graduated feature colour
   * 
   */
  private int _mincolour;

  /**
   * keeps track of state for field: _mincolour
   */
  private boolean _has_mincolour;

  /**
   * threshold value for graduated feature colour
   * 
   */
  private float _threshold;

  /**
   * keeps track of state for field: _threshold
   */
  private boolean _has_threshold;

  /**
   * threshold type for graduated feature colour
   * 
   */
  private int _threshstate;

  /**
   * keeps track of state for field: _threshstate
   */
  private boolean _has_threshstate;

  /**
   * Field _max.
   */
  private float _max;

  /**
   * keeps track of state for field: _max
   */
  private boolean _has_max;

  /**
   * Field _min.
   */
  private float _min;

  /**
   * keeps track of state for field: _min
   */
  private boolean _has_min;

  /**
   * Field _colourByLabel.
   */
  private boolean _colourByLabel;

  /**
   * keeps track of state for field: _colourByLabel
   */
  private boolean _has_colourByLabel;

  /**
   * Field _autoScale.
   */
  private boolean _autoScale;

  /**
   * keeps track of state for field: _autoScale
   */
  private boolean _has_autoScale;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Setting()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deleteAutoScale()
  {
    this._has_autoScale = false;
  }

  /**
     */
  public void deleteColour()
  {
    this._has_colour = false;
  }

  /**
     */
  public void deleteColourByLabel()
  {
    this._has_colourByLabel = false;
  }

  /**
     */
  public void deleteDisplay()
  {
    this._has_display = false;
  }

  /**
     */
  public void deleteMax()
  {
    this._has_max = false;
  }

  /**
     */
  public void deleteMin()
  {
    this._has_min = false;
  }

  /**
     */
  public void deleteMincolour()
  {
    this._has_mincolour = false;
  }

  /**
     */
  public void deleteOrder()
  {
    this._has_order = false;
  }

  /**
     */
  public void deleteThreshold()
  {
    this._has_threshold = false;
  }

  /**
     */
  public void deleteThreshstate()
  {
    this._has_threshstate = false;
  }

  /**
   * Returns the value of field 'autoScale'.
   * 
   * @return the value of field 'AutoScale'.
   */
  public boolean getAutoScale()
  {
    return this._autoScale;
  }

  /**
   * Returns the value of field 'colour'.
   * 
   * @return the value of field 'Colour'.
   */
  public int getColour()
  {
    return this._colour;
  }

  /**
   * Returns the value of field 'colourByLabel'.
   * 
   * @return the value of field 'ColourByLabel'.
   */
  public boolean getColourByLabel()
  {
    return this._colourByLabel;
  }

  /**
   * Returns the value of field 'display'.
   * 
   * @return the value of field 'Display'.
   */
  public boolean getDisplay()
  {
    return this._display;
  }

  /**
   * Returns the value of field 'max'.
   * 
   * @return the value of field 'Max'.
   */
  public float getMax()
  {
    return this._max;
  }

  /**
   * Returns the value of field 'min'.
   * 
   * @return the value of field 'Min'.
   */
  public float getMin()
  {
    return this._min;
  }

  /**
   * Returns the value of field 'mincolour'. The field 'mincolour' has the
   * following description: Optional minimum colour for graduated feature colour
   * 
   * 
   * @return the value of field 'Mincolour'.
   */
  public int getMincolour()
  {
    return this._mincolour;
  }

  /**
   * Returns the value of field 'order'.
   * 
   * @return the value of field 'Order'.
   */
  public float getOrder()
  {
    return this._order;
  }

  /**
   * Returns the value of field 'threshold'. The field 'threshold' has the
   * following description: threshold value for graduated feature colour
   * 
   * 
   * @return the value of field 'Threshold'.
   */
  public float getThreshold()
  {
    return this._threshold;
  }

  /**
   * Returns the value of field 'threshstate'. The field 'threshstate' has the
   * following description: threshold type for graduated feature colour
   * 
   * 
   * @return the value of field 'Threshstate'.
   */
  public int getThreshstate()
  {
    return this._threshstate;
  }

  /**
   * Returns the value of field 'type'.
   * 
   * @return the value of field 'Type'.
   */
  public java.lang.String getType()
  {
    return this._type;
  }

  /**
   * Method hasAutoScale.
   * 
   * @return true if at least one AutoScale has been added
   */
  public boolean hasAutoScale()
  {
    return this._has_autoScale;
  }

  /**
   * Method hasColour.
   * 
   * @return true if at least one Colour has been added
   */
  public boolean hasColour()
  {
    return this._has_colour;
  }

  /**
   * Method hasColourByLabel.
   * 
   * @return true if at least one ColourByLabel has been added
   */
  public boolean hasColourByLabel()
  {
    return this._has_colourByLabel;
  }

  /**
   * Method hasDisplay.
   * 
   * @return true if at least one Display has been added
   */
  public boolean hasDisplay()
  {
    return this._has_display;
  }

  /**
   * Method hasMax.
   * 
   * @return true if at least one Max has been added
   */
  public boolean hasMax()
  {
    return this._has_max;
  }

  /**
   * Method hasMin.
   * 
   * @return true if at least one Min has been added
   */
  public boolean hasMin()
  {
    return this._has_min;
  }

  /**
   * Method hasMincolour.
   * 
   * @return true if at least one Mincolour has been added
   */
  public boolean hasMincolour()
  {
    return this._has_mincolour;
  }

  /**
   * Method hasOrder.
   * 
   * @return true if at least one Order has been added
   */
  public boolean hasOrder()
  {
    return this._has_order;
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
   * Method hasThreshstate.
   * 
   * @return true if at least one Threshstate has been added
   */
  public boolean hasThreshstate()
  {
    return this._has_threshstate;
  }

  /**
   * Returns the value of field 'autoScale'.
   * 
   * @return the value of field 'AutoScale'.
   */
  public boolean isAutoScale()
  {
    return this._autoScale;
  }

  /**
   * Returns the value of field 'colourByLabel'.
   * 
   * @return the value of field 'ColourByLabel'.
   */
  public boolean isColourByLabel()
  {
    return this._colourByLabel;
  }

  /**
   * Returns the value of field 'display'.
   * 
   * @return the value of field 'Display'.
   */
  public boolean isDisplay()
  {
    return this._display;
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
   * Sets the value of field 'autoScale'.
   * 
   * @param autoScale
   *          the value of field 'autoScale'.
   */
  public void setAutoScale(final boolean autoScale)
  {
    this._autoScale = autoScale;
    this._has_autoScale = true;
  }

  /**
   * Sets the value of field 'colour'.
   * 
   * @param colour
   *          the value of field 'colour'.
   */
  public void setColour(final int colour)
  {
    this._colour = colour;
    this._has_colour = true;
  }

  /**
   * Sets the value of field 'colourByLabel'.
   * 
   * @param colourByLabel
   *          the value of field 'colourByLabel'.
   */
  public void setColourByLabel(final boolean colourByLabel)
  {
    this._colourByLabel = colourByLabel;
    this._has_colourByLabel = true;
  }

  /**
   * Sets the value of field 'display'.
   * 
   * @param display
   *          the value of field 'display'.
   */
  public void setDisplay(final boolean display)
  {
    this._display = display;
    this._has_display = true;
  }

  /**
   * Sets the value of field 'max'.
   * 
   * @param max
   *          the value of field 'max'.
   */
  public void setMax(final float max)
  {
    this._max = max;
    this._has_max = true;
  }

  /**
   * Sets the value of field 'min'.
   * 
   * @param min
   *          the value of field 'min'.
   */
  public void setMin(final float min)
  {
    this._min = min;
    this._has_min = true;
  }

  /**
   * Sets the value of field 'mincolour'. The field 'mincolour' has the
   * following description: Optional minimum colour for graduated feature colour
   * 
   * 
   * @param mincolour
   *          the value of field 'mincolour'.
   */
  public void setMincolour(final int mincolour)
  {
    this._mincolour = mincolour;
    this._has_mincolour = true;
  }

  /**
   * Sets the value of field 'order'.
   * 
   * @param order
   *          the value of field 'order'.
   */
  public void setOrder(final float order)
  {
    this._order = order;
    this._has_order = true;
  }

  /**
   * Sets the value of field 'threshold'. The field 'threshold' has the
   * following description: threshold value for graduated feature colour
   * 
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
   * Sets the value of field 'threshstate'. The field 'threshstate' has the
   * following description: threshold type for graduated feature colour
   * 
   * 
   * @param threshstate
   *          the value of field 'threshstate'.
   */
  public void setThreshstate(final int threshstate)
  {
    this._threshstate = threshstate;
    this._has_threshstate = true;
  }

  /**
   * Sets the value of field 'type'.
   * 
   * @param type
   *          the value of field 'type'.
   */
  public void setType(final java.lang.String type)
  {
    this._type = type;
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
   * @return the unmarshaled jalview.schemabinding.version2.Settin
   */
  public static jalview.schemabinding.version2.Setting unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.Setting) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.Setting.class, reader);
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
