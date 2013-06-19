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
 * Class DBRef.
 * 
 * @version $Revision$ $Date$
 */
public class DBRef implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _source.
   */
  private java.lang.String _source;

  /**
   * Field _version.
   */
  private java.lang.String _version;

  /**
   * Field _accessionId.
   */
  private java.lang.String _accessionId;

  /**
   * Field _mapping.
   */
  private jalview.schemabinding.version2.Mapping _mapping;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public DBRef()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Returns the value of field 'accessionId'.
   * 
   * @return the value of field 'AccessionId'.
   */
  public java.lang.String getAccessionId()
  {
    return this._accessionId;
  }

  /**
   * Returns the value of field 'mapping'.
   * 
   * @return the value of field 'Mapping'.
   */
  public jalview.schemabinding.version2.Mapping getMapping()
  {
    return this._mapping;
  }

  /**
   * Returns the value of field 'source'.
   * 
   * @return the value of field 'Source'.
   */
  public java.lang.String getSource()
  {
    return this._source;
  }

  /**
   * Returns the value of field 'version'.
   * 
   * @return the value of field 'Version'.
   */
  public java.lang.String getVersion()
  {
    return this._version;
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
   * Sets the value of field 'accessionId'.
   * 
   * @param accessionId
   *          the value of field 'accessionId'.
   */
  public void setAccessionId(final java.lang.String accessionId)
  {
    this._accessionId = accessionId;
  }

  /**
   * Sets the value of field 'mapping'.
   * 
   * @param mapping
   *          the value of field 'mapping'.
   */
  public void setMapping(
          final jalview.schemabinding.version2.Mapping mapping)
  {
    this._mapping = mapping;
  }

  /**
   * Sets the value of field 'source'.
   * 
   * @param source
   *          the value of field 'source'.
   */
  public void setSource(final java.lang.String source)
  {
    this._source = source;
  }

  /**
   * Sets the value of field 'version'.
   * 
   * @param version
   *          the value of field 'version'.
   */
  public void setVersion(final java.lang.String version)
  {
    this._version = version;
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
   * @return the unmarshaled jalview.schemabinding.version2.DBRef
   */
  public static jalview.schemabinding.version2.DBRef unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.DBRef) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.DBRef.class, reader);
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
