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
 * Class Feature.
 * 
 * @version $Revision$ $Date$
 */
public class Feature implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _begin.
   */
  private int _begin;

  /**
   * keeps track of state for field: _begin
   */
  private boolean _has_begin;

  /**
   * Field _end.
   */
  private int _end;

  /**
   * keeps track of state for field: _end
   */
  private boolean _has_end;

  /**
   * Field _type.
   */
  private java.lang.String _type;

  /**
   * Field _description.
   */
  private java.lang.String _description;

  /**
   * Field _status.
   */
  private java.lang.String _status;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Feature()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deleteBegin()
  {
    this._has_begin = false;
  }

  /**
     */
  public void deleteEnd()
  {
    this._has_end = false;
  }

  /**
   * Returns the value of field 'begin'.
   * 
   * @return the value of field 'Begin'.
   */
  public int getBegin()
  {
    return this._begin;
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
   * Returns the value of field 'end'.
   * 
   * @return the value of field 'End'.
   */
  public int getEnd()
  {
    return this._end;
  }

  /**
   * Returns the value of field 'status'.
   * 
   * @return the value of field 'Status'.
   */
  public java.lang.String getStatus()
  {
    return this._status;
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
   * Method hasBegin.
   * 
   * @return true if at least one Begin has been added
   */
  public boolean hasBegin()
  {
    return this._has_begin;
  }

  /**
   * Method hasEnd.
   * 
   * @return true if at least one End has been added
   */
  public boolean hasEnd()
  {
    return this._has_end;
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
   * Sets the value of field 'begin'.
   * 
   * @param begin
   *          the value of field 'begin'.
   */
  public void setBegin(final int begin)
  {
    this._begin = begin;
    this._has_begin = true;
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
   * Sets the value of field 'end'.
   * 
   * @param end
   *          the value of field 'end'.
   */
  public void setEnd(final int end)
  {
    this._end = end;
    this._has_end = true;
  }

  /**
   * Sets the value of field 'status'.
   * 
   * @param status
   *          the value of field 'status'.
   */
  public void setStatus(final java.lang.String status)
  {
    this._status = status;
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
   * @return the unmarshaled jalview.binding.Feature
   */
  public static jalview.binding.Feature unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.Feature) Unmarshaller.unmarshal(
            jalview.binding.Feature.class, reader);
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
