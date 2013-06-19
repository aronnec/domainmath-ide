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
 * Class SequenceType.
 * 
 * @version $Revision$ $Date$
 */
public class SequenceType implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _id.
   */
  private java.lang.String _id;

  /**
   * Field _description.
   */
  private java.lang.String _description;

  /**
   * Field _sequence.
   */
  private java.lang.String _sequence;

  /**
   * Field _name.
   */
  private java.lang.String _name;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public SequenceType()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

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
   * Returns the value of field 'id'.
   * 
   * @return the value of field 'Id'.
   */
  public java.lang.String getId()
  {
    return this._id;
  }

  /**
   * Returns the value of field 'name'.
   * 
   * @return the value of field 'Name'.
   */
  public java.lang.String getName()
  {
    return this._name;
  }

  /**
   * Returns the value of field 'sequence'.
   * 
   * @return the value of field 'Sequence'.
   */
  public java.lang.String getSequence()
  {
    return this._sequence;
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
   * Sets the value of field 'id'.
   * 
   * @param id
   *          the value of field 'id'.
   */
  public void setId(final java.lang.String id)
  {
    this._id = id;
  }

  /**
   * Sets the value of field 'name'.
   * 
   * @param name
   *          the value of field 'name'.
   */
  public void setName(final java.lang.String name)
  {
    this._name = name;
  }

  /**
   * Sets the value of field 'sequence'.
   * 
   * @param sequence
   *          the value of field 'sequence'.
   */
  public void setSequence(final java.lang.String sequence)
  {
    this._sequence = sequence;
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
   * @return the unmarshaled jalview.schemabinding.version2.SequenceType
   */
  public static jalview.schemabinding.version2.SequenceType unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.SequenceType) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.SequenceType.class,
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
