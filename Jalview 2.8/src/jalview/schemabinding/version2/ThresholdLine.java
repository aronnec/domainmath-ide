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
 * Class ThresholdLine.
 * 
 * @version $Revision$ $Date$
 */
public class ThresholdLine implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _label.
   */
  private java.lang.String _label;

  /**
   * Field _value.
   */
  private float _value;

  /**
   * keeps track of state for field: _value
   */
  private boolean _has_value;

  /**
   * Field _colour.
   */
  private int _colour;

  /**
   * keeps track of state for field: _colour
   */
  private boolean _has_colour;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public ThresholdLine()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deleteColour()
  {
    this._has_colour = false;
  }

  /**
     */
  public void deleteValue()
  {
    this._has_value = false;
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
   * Returns the value of field 'label'.
   * 
   * @return the value of field 'Label'.
   */
  public java.lang.String getLabel()
  {
    return this._label;
  }

  /**
   * Returns the value of field 'value'.
   * 
   * @return the value of field 'Value'.
   */
  public float getValue()
  {
    return this._value;
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
   * Method hasValue.
   * 
   * @return true if at least one Value has been added
   */
  public boolean hasValue()
  {
    return this._has_value;
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
   * Sets the value of field 'label'.
   * 
   * @param label
   *          the value of field 'label'.
   */
  public void setLabel(final java.lang.String label)
  {
    this._label = label;
  }

  /**
   * Sets the value of field 'value'.
   * 
   * @param value
   *          the value of field 'value'.
   */
  public void setValue(final float value)
  {
    this._value = value;
    this._has_value = true;
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
   * @return the unmarshaled jalview.schemabinding.version2.ThresholdLine
   */
  public static jalview.schemabinding.version2.ThresholdLine unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.ThresholdLine) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.ThresholdLine.class,
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
