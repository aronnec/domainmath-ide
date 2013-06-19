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
 * Class JalviewModel.
 * 
 * @version $Revision$ $Date$
 */
public class JalviewModel implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _creationDate.
   */
  private java.util.Date _creationDate;

  /**
   * Field _version.
   */
  private java.lang.String _version;

  /**
   * Field _vamsasModel.
   */
  private jalview.binding.VamsasModel _vamsasModel;

  /**
   * Field _jalviewModelSequence.
   */
  private jalview.binding.JalviewModelSequence _jalviewModelSequence;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public JalviewModel()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Returns the value of field 'creationDate'.
   * 
   * @return the value of field 'CreationDate'.
   */
  public java.util.Date getCreationDate()
  {
    return this._creationDate;
  }

  /**
   * Returns the value of field 'jalviewModelSequence'.
   * 
   * @return the value of field 'JalviewModelSequence'.
   */
  public jalview.binding.JalviewModelSequence getJalviewModelSequence()
  {
    return this._jalviewModelSequence;
  }

  /**
   * Returns the value of field 'vamsasModel'.
   * 
   * @return the value of field 'VamsasModel'.
   */
  public jalview.binding.VamsasModel getVamsasModel()
  {
    return this._vamsasModel;
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
   * Sets the value of field 'creationDate'.
   * 
   * @param creationDate
   *          the value of field 'creationDate'.
   */
  public void setCreationDate(final java.util.Date creationDate)
  {
    this._creationDate = creationDate;
  }

  /**
   * Sets the value of field 'jalviewModelSequence'.
   * 
   * @param jalviewModelSequence
   *          the value of field 'jalviewModelSequence'.
   */
  public void setJalviewModelSequence(
          final jalview.binding.JalviewModelSequence jalviewModelSequence)
  {
    this._jalviewModelSequence = jalviewModelSequence;
  }

  /**
   * Sets the value of field 'vamsasModel'.
   * 
   * @param vamsasModel
   *          the value of field 'vamsasModel'.
   */
  public void setVamsasModel(final jalview.binding.VamsasModel vamsasModel)
  {
    this._vamsasModel = vamsasModel;
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
   * @return the unmarshaled jalview.binding.JalviewModel
   */
  public static jalview.binding.JalviewModel unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.JalviewModel) Unmarshaller.unmarshal(
            jalview.binding.JalviewModel.class, reader);
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
