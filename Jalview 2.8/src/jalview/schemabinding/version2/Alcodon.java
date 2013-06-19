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
 * Class Alcodon.
 * 
 * @version $Revision$ $Date$
 */
public class Alcodon implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _pos1.
   */
  private long _pos1;

  /**
   * keeps track of state for field: _pos1
   */
  private boolean _has_pos1;

  /**
   * Field _pos2.
   */
  private long _pos2;

  /**
   * keeps track of state for field: _pos2
   */
  private boolean _has_pos2;

  /**
   * Field _pos3.
   */
  private long _pos3;

  /**
   * keeps track of state for field: _pos3
   */
  private boolean _has_pos3;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Alcodon()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deletePos1()
  {
    this._has_pos1 = false;
  }

  /**
     */
  public void deletePos2()
  {
    this._has_pos2 = false;
  }

  /**
     */
  public void deletePos3()
  {
    this._has_pos3 = false;
  }

  /**
   * Returns the value of field 'pos1'.
   * 
   * @return the value of field 'Pos1'.
   */
  public long getPos1()
  {
    return this._pos1;
  }

  /**
   * Returns the value of field 'pos2'.
   * 
   * @return the value of field 'Pos2'.
   */
  public long getPos2()
  {
    return this._pos2;
  }

  /**
   * Returns the value of field 'pos3'.
   * 
   * @return the value of field 'Pos3'.
   */
  public long getPos3()
  {
    return this._pos3;
  }

  /**
   * Method hasPos1.
   * 
   * @return true if at least one Pos1 has been added
   */
  public boolean hasPos1()
  {
    return this._has_pos1;
  }

  /**
   * Method hasPos2.
   * 
   * @return true if at least one Pos2 has been added
   */
  public boolean hasPos2()
  {
    return this._has_pos2;
  }

  /**
   * Method hasPos3.
   * 
   * @return true if at least one Pos3 has been added
   */
  public boolean hasPos3()
  {
    return this._has_pos3;
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
   * Sets the value of field 'pos1'.
   * 
   * @param pos1
   *          the value of field 'pos1'.
   */
  public void setPos1(final long pos1)
  {
    this._pos1 = pos1;
    this._has_pos1 = true;
  }

  /**
   * Sets the value of field 'pos2'.
   * 
   * @param pos2
   *          the value of field 'pos2'.
   */
  public void setPos2(final long pos2)
  {
    this._pos2 = pos2;
    this._has_pos2 = true;
  }

  /**
   * Sets the value of field 'pos3'.
   * 
   * @param pos3
   *          the value of field 'pos3'.
   */
  public void setPos3(final long pos3)
  {
    this._pos3 = pos3;
    this._has_pos3 = true;
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
   * @return the unmarshaled jalview.schemabinding.version2.Alcodo
   */
  public static jalview.schemabinding.version2.Alcodon unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.Alcodon) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.Alcodon.class, reader);
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
