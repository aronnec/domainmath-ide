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
 * Class AnnotationElement.
 * 
 * @version $Revision$ $Date$
 */
public class AnnotationElement implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _position.
   */
  private int _position;

  /**
   * keeps track of state for field: _position
   */
  private boolean _has_position;

  /**
   * Field _displayCharacter.
   */
  private java.lang.String _displayCharacter;

  /**
   * Field _description.
   */
  private java.lang.String _description;

  /**
   * Field _secondaryStructure.
   */
  private java.lang.String _secondaryStructure;

  /**
   * Field _value.
   */
  private float _value;

  /**
   * keeps track of state for field: _value
   */
  private boolean _has_value;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public AnnotationElement()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deletePosition()
  {
    this._has_position = false;
  }

  /**
     */
  public void deleteValue()
  {
    this._has_value = false;
  }

  /**
   * Returns the value of field 'description'.
   * 
   * @return the value of field 'Description'.
   */
  public java.lang.String getDescription()
  {
    return this._description;
  }

  /**
   * Returns the value of field 'displayCharacter'.
   * 
   * @return the value of field 'DisplayCharacter'.
   */
  public java.lang.String getDisplayCharacter()
  {
    return this._displayCharacter;
  }

  /**
   * Returns the value of field 'position'.
   * 
   * @return the value of field 'Position'.
   */
  public int getPosition()
  {
    return this._position;
  }

  /**
   * Returns the value of field 'secondaryStructure'.
   * 
   * @return the value of field 'SecondaryStructure'.
   */
  public java.lang.String getSecondaryStructure()
  {
    return this._secondaryStructure;
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
   * Method hasPosition.
   * 
   * @return true if at least one Position has been added
   */
  public boolean hasPosition()
  {
    return this._has_position;
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
   * Sets the value of field 'description'.
   * 
   * @param description
   *          the value of field 'description'.
   */
  public void setDescription(final java.lang.String description)
  {
    this._description = description;
  }

  /**
   * Sets the value of field 'displayCharacter'.
   * 
   * @param displayCharacter
   *          the value of field 'displayCharacter'
   */
  public void setDisplayCharacter(final java.lang.String displayCharacter)
  {
    this._displayCharacter = displayCharacter;
  }

  /**
   * Sets the value of field 'position'.
   * 
   * @param position
   *          the value of field 'position'.
   */
  public void setPosition(final int position)
  {
    this._position = position;
    this._has_position = true;
  }

  /**
   * Sets the value of field 'secondaryStructure'.
   * 
   * @param secondaryStructure
   *          the value of field 'secondaryStructure'.
   */
  public void setSecondaryStructure(
          final java.lang.String secondaryStructure)
  {
    this._secondaryStructure = secondaryStructure;
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
   * @return the unmarshaled jalview.binding.AnnotationElement
   */
  public static jalview.binding.AnnotationElement unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.AnnotationElement) Unmarshaller.unmarshal(
            jalview.binding.AnnotationElement.class, reader);
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
