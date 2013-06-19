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
 * Class Pdbentry.
 * 
 * @version $Revision$ $Date$
 */
public class Pdbentry implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _id.
   */
  private java.lang.String _id;

  /**
   * Field _type.
   */
  private java.lang.String _type;

  /**
   * Field _items.
   */
  private java.util.Vector _items;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Pdbentry()
  {
    super();
    this._items = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vPdbentryItem
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addPdbentryItem(
          final jalview.binding.PdbentryItem vPdbentryItem)
          throws java.lang.IndexOutOfBoundsException
  {
    this._items.addElement(vPdbentryItem);
  }

  /**
   * 
   * 
   * @param index
   * @param vPdbentryItem
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addPdbentryItem(final int index,
          final jalview.binding.PdbentryItem vPdbentryItem)
          throws java.lang.IndexOutOfBoundsException
  {
    this._items.add(index, vPdbentryItem);
  }

  /**
   * Method enumeratePdbentryItem.
   * 
   * @return an Enumeration over all jalview.binding.PdbentryItem elements
   */
  public java.util.Enumeration enumeratePdbentryItem()
  {
    return this._items.elements();
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
   * Method getPdbentryItem.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.binding.PdbentryItem at the given index
   */
  public jalview.binding.PdbentryItem getPdbentryItem(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._items.size())
    {
      throw new IndexOutOfBoundsException("getPdbentryItem: Index value '"
              + index + "' not in range [0.." + (this._items.size() - 1)
              + "]");
    }

    return (jalview.binding.PdbentryItem) _items.get(index);
  }

  /**
   * Method getPdbentryItem.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.binding.PdbentryItem[] getPdbentryItem()
  {
    jalview.binding.PdbentryItem[] array = new jalview.binding.PdbentryItem[0];
    return (jalview.binding.PdbentryItem[]) this._items.toArray(array);
  }

  /**
   * Method getPdbentryItemCount.
   * 
   * @return the size of this collection
   */
  public int getPdbentryItemCount()
  {
    return this._items.size();
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
  public void removeAllPdbentryItem()
  {
    this._items.clear();
  }

  /**
   * Method removePdbentryItem.
   * 
   * @param vPdbentryItem
   * @return true if the object was removed from the collection.
   */
  public boolean removePdbentryItem(
          final jalview.binding.PdbentryItem vPdbentryItem)
  {
    boolean removed = _items.remove(vPdbentryItem);
    return removed;
  }

  /**
   * Method removePdbentryItemAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.binding.PdbentryItem removePdbentryItemAt(final int index)
  {
    java.lang.Object obj = this._items.remove(index);
    return (jalview.binding.PdbentryItem) obj;
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
   * 
   * 
   * @param index
   * @param vPdbentryItem
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setPdbentryItem(final int index,
          final jalview.binding.PdbentryItem vPdbentryItem)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._items.size())
    {
      throw new IndexOutOfBoundsException("setPdbentryItem: Index value '"
              + index + "' not in range [0.." + (this._items.size() - 1)
              + "]");
    }

    this._items.set(index, vPdbentryItem);
  }

  /**
   * 
   * 
   * @param vPdbentryItemArray
   */
  public void setPdbentryItem(
          final jalview.binding.PdbentryItem[] vPdbentryItemArray)
  {
    // -- copy array
    _items.clear();

    for (int i = 0; i < vPdbentryItemArray.length; i++)
    {
      this._items.add(vPdbentryItemArray[i]);
    }
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
   * @return the unmarshaled jalview.binding.Pdbentry
   */
  public static jalview.binding.Pdbentry unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.Pdbentry) Unmarshaller.unmarshal(
            jalview.binding.Pdbentry.class, reader);
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
