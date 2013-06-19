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
 * Class WebServiceParameterSet.
 * 
 * @version $Revision$ $Date$
 */
public class WebServiceParameterSet implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * The short name for the parameter set. This will be shown amongst the other
   * presets for the web service.
   * 
   */
  private java.lang.String _name;

  /**
   * A Jalview Web Service Parameter Set container version number. Version 1
   * created for storing Jaba user presets.
   * 
   */
  private java.lang.String _version;

  /**
   * Short description - as utf8 encoded text. This is usually displayed in the
   * body of an HTML capable tooltip, so HTML tags may be embedded using
   * standard UTF8 encoding.
   * 
   */
  private java.lang.String _description;

  /**
   * URL for which the parameter set is valid. Jalview will use it to match up
   * parameter sets to service instances that can parse the parameter set
   * payload.
   * 
   */
  private java.util.Vector _serviceURLList;

  /**
   * UTF8 encoded string to be processed into a specific web services' parameter
   * set. Note - newlines may be important to the structure of this file.
   * 
   */
  private java.lang.String _parameters;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public WebServiceParameterSet()
  {
    super();
    this._serviceURLList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vServiceURL
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addServiceURL(final java.lang.String vServiceURL)
          throws java.lang.IndexOutOfBoundsException
  {
    this._serviceURLList.addElement(vServiceURL);
  }

  /**
   * 
   * 
   * @param index
   * @param vServiceURL
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addServiceURL(final int index,
          final java.lang.String vServiceURL)
          throws java.lang.IndexOutOfBoundsException
  {
    this._serviceURLList.add(index, vServiceURL);
  }

  /**
   * Method enumerateServiceURL.
   * 
   * @return an Enumeration over all java.lang.String elements
   */
  public java.util.Enumeration enumerateServiceURL()
  {
    return this._serviceURLList.elements();
  }

  /**
   * Returns the value of field 'description'. The field 'description' has the
   * following description: Short description - as utf8 encoded text. This is
   * usually displayed in the body of an HTML capable tooltip, so HTML tags may
   * be embedded using standard UTF8 encoding.
   * 
   * 
   * @return the value of field 'Description'.
   */
  public java.lang.String getDescription()
  {
    return this._description;
  }

  /**
   * Returns the value of field 'name'. The field 'name' has the following
   * description: The short name for the parameter set. This will be shown
   * amongst the other presets for the web service.
   * 
   * 
   * @return the value of field 'Name'.
   */
  public java.lang.String getName()
  {
    return this._name;
  }

  /**
   * Returns the value of field 'parameters'. The field 'parameters' has the
   * following description: UTF8 encoded string to be processed into a specific
   * web services' parameter set. Note - newlines may be important to the
   * structure of this file.
   * 
   * 
   * @return the value of field 'Parameters'.
   */
  public java.lang.String getParameters()
  {
    return this._parameters;
  }

  /**
   * Method getServiceURL.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the java.lang.String at the given index
   */
  public java.lang.String getServiceURL(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._serviceURLList.size())
    {
      throw new IndexOutOfBoundsException("getServiceURL: Index value '"
              + index + "' not in range [0.."
              + (this._serviceURLList.size() - 1) + "]");
    }

    return (java.lang.String) _serviceURLList.get(index);
  }

  /**
   * Method getServiceURL.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public java.lang.String[] getServiceURL()
  {
    java.lang.String[] array = new java.lang.String[0];
    return (java.lang.String[]) this._serviceURLList.toArray(array);
  }

  /**
   * Method getServiceURLCount.
   * 
   * @return the size of this collection
   */
  public int getServiceURLCount()
  {
    return this._serviceURLList.size();
  }

  /**
   * Returns the value of field 'version'. The field 'version' has the following
   * description: A Jalview Web Service Parameter Set container version number.
   * Version 1 created for storing Jaba user presets.
   * 
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
     */
  public void removeAllServiceURL()
  {
    this._serviceURLList.clear();
  }

  /**
   * Method removeServiceURL.
   * 
   * @param vServiceURL
   * @return true if the object was removed from the collection.
   */
  public boolean removeServiceURL(final java.lang.String vServiceURL)
  {
    boolean removed = _serviceURLList.remove(vServiceURL);
    return removed;
  }

  /**
   * Method removeServiceURLAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public java.lang.String removeServiceURLAt(final int index)
  {
    java.lang.Object obj = this._serviceURLList.remove(index);
    return (java.lang.String) obj;
  }

  /**
   * Sets the value of field 'description'. The field 'description' has the
   * following description: Short description - as utf8 encoded text. This is
   * usually displayed in the body of an HTML capable tooltip, so HTML tags may
   * be embedded using standard UTF8 encoding.
   * 
   * 
   * @param description
   *          the value of field 'description'.
   */
  public void setDescription(final java.lang.String description)
  {
    this._description = description;
  }

  /**
   * Sets the value of field 'name'. The field 'name' has the following
   * description: The short name for the parameter set. This will be shown
   * amongst the other presets for the web service.
   * 
   * 
   * @param name
   *          the value of field 'name'.
   */
  public void setName(final java.lang.String name)
  {
    this._name = name;
  }

  /**
   * Sets the value of field 'parameters'. The field 'parameters' has the
   * following description: UTF8 encoded string to be processed into a specific
   * web services' parameter set. Note - newlines may be important to the
   * structure of this file.
   * 
   * 
   * @param parameters
   *          the value of field 'parameters'.
   */
  public void setParameters(final java.lang.String parameters)
  {
    this._parameters = parameters;
  }

  /**
   * 
   * 
   * @param index
   * @param vServiceURL
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setServiceURL(final int index,
          final java.lang.String vServiceURL)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._serviceURLList.size())
    {
      throw new IndexOutOfBoundsException("setServiceURL: Index value '"
              + index + "' not in range [0.."
              + (this._serviceURLList.size() - 1) + "]");
    }

    this._serviceURLList.set(index, vServiceURL);
  }

  /**
   * 
   * 
   * @param vServiceURLArray
   */
  public void setServiceURL(final java.lang.String[] vServiceURLArray)
  {
    // -- copy array
    _serviceURLList.clear();

    for (int i = 0; i < vServiceURLArray.length; i++)
    {
      this._serviceURLList.add(vServiceURLArray[i]);
    }
  }

  /**
   * Sets the value of field 'version'. The field 'version' has the following
   * description: A Jalview Web Service Parameter Set container version number.
   * Version 1 created for storing Jaba user presets.
   * 
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
   * @return the unmarshaled
   *         jalview.schemabinding.version2.WebServiceParameterSet
   */
  public static jalview.schemabinding.version2.WebServiceParameterSet unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.WebServiceParameterSet) Unmarshaller
            .unmarshal(
                    jalview.schemabinding.version2.WebServiceParameterSet.class,
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
