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
 * Class Pdbids.
 * 
 * @version $Revision$ $Date$
 */
public class Pdbids extends jalview.schemabinding.version2.Pdbentry
        implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _structureStateList.
   */
  private java.util.Vector _structureStateList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Pdbids()
  {
    super();
    this._structureStateList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vStructureState
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addStructureState(
          final jalview.schemabinding.version2.StructureState vStructureState)
          throws java.lang.IndexOutOfBoundsException
  {
    this._structureStateList.addElement(vStructureState);
  }

  /**
   * 
   * 
   * @param index
   * @param vStructureState
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addStructureState(
          final int index,
          final jalview.schemabinding.version2.StructureState vStructureState)
          throws java.lang.IndexOutOfBoundsException
  {
    this._structureStateList.add(index, vStructureState);
  }

  /**
   * Method enumerateStructureState.
   * 
   * @return an Enumeration over all
   *         jalview.schemabinding.version2.StructureState elements
   */
  public java.util.Enumeration enumerateStructureState()
  {
    return this._structureStateList.elements();
  }

  /**
   * Method getStructureState.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.StructureState at
   *         the given index
   */
  public jalview.schemabinding.version2.StructureState getStructureState(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._structureStateList.size())
    {
      throw new IndexOutOfBoundsException(
              "getStructureState: Index value '" + index
                      + "' not in range [0.."
                      + (this._structureStateList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.StructureState) _structureStateList
            .get(index);
  }

  /**
   * Method getStructureState.Returns the contents of the collection in an
   * Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.StructureState[] getStructureState()
  {
    jalview.schemabinding.version2.StructureState[] array = new jalview.schemabinding.version2.StructureState[0];
    return (jalview.schemabinding.version2.StructureState[]) this._structureStateList
            .toArray(array);
  }

  /**
   * Method getStructureStateCount.
   * 
   * @return the size of this collection
   */
  public int getStructureStateCount()
  {
    return this._structureStateList.size();
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
  public void removeAllStructureState()
  {
    this._structureStateList.clear();
  }

  /**
   * Method removeStructureState.
   * 
   * @param vStructureState
   * @return true if the object was removed from the collection.
   */
  public boolean removeStructureState(
          final jalview.schemabinding.version2.StructureState vStructureState)
  {
    boolean removed = _structureStateList.remove(vStructureState);
    return removed;
  }

  /**
   * Method removeStructureStateAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.StructureState removeStructureStateAt(
          final int index)
  {
    java.lang.Object obj = this._structureStateList.remove(index);
    return (jalview.schemabinding.version2.StructureState) obj;
  }

  /**
   * 
   * 
   * @param index
   * @param vStructureState
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setStructureState(
          final int index,
          final jalview.schemabinding.version2.StructureState vStructureState)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._structureStateList.size())
    {
      throw new IndexOutOfBoundsException(
              "setStructureState: Index value '" + index
                      + "' not in range [0.."
                      + (this._structureStateList.size() - 1) + "]");
    }

    this._structureStateList.set(index, vStructureState);
  }

  /**
   * 
   * 
   * @param vStructureStateArray
   */
  public void setStructureState(
          final jalview.schemabinding.version2.StructureState[] vStructureStateArray)
  {
    // -- copy array
    _structureStateList.clear();

    for (int i = 0; i < vStructureStateArray.length; i++)
    {
      this._structureStateList.add(vStructureStateArray[i]);
    }
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
   * @return the unmarshaled jalview.schemabinding.version2.Pdbentry
   */
  public static jalview.schemabinding.version2.Pdbentry unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.Pdbentry) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.Pdbids.class, reader);
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
