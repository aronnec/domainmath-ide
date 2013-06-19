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
 * Class AlcodonFrame.
 * 
 * @version $Revision$ $Date$
 */
public class AlcodonFrame implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _alcodonList.
   */
  private java.util.Vector _alcodonList;

  /**
   * Field _alcodMapList.
   */
  private java.util.Vector _alcodMapList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public AlcodonFrame()
  {
    super();
    this._alcodonList = new java.util.Vector();
    this._alcodMapList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vAlcodMap
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlcodMap(
          final jalview.schemabinding.version2.AlcodMap vAlcodMap)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alcodMapList.addElement(vAlcodMap);
  }

  /**
   * 
   * 
   * @param index
   * @param vAlcodMap
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlcodMap(final int index,
          final jalview.schemabinding.version2.AlcodMap vAlcodMap)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alcodMapList.add(index, vAlcodMap);
  }

  /**
   * 
   * 
   * @param vAlcodon
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlcodon(
          final jalview.schemabinding.version2.Alcodon vAlcodon)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alcodonList.addElement(vAlcodon);
  }

  /**
   * 
   * 
   * @param index
   * @param vAlcodon
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlcodon(final int index,
          final jalview.schemabinding.version2.Alcodon vAlcodon)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alcodonList.add(index, vAlcodon);
  }

  /**
   * Method enumerateAlcodMap.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.AlcodMap
   *         elements
   */
  public java.util.Enumeration enumerateAlcodMap()
  {
    return this._alcodMapList.elements();
  }

  /**
   * Method enumerateAlcodon.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Alcodon
   *         elements
   */
  public java.util.Enumeration enumerateAlcodon()
  {
    return this._alcodonList.elements();
  }

  /**
   * Method getAlcodMap.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.AlcodMap at the
   *         given index
   */
  public jalview.schemabinding.version2.AlcodMap getAlcodMap(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alcodMapList.size())
    {
      throw new IndexOutOfBoundsException("getAlcodMap: Index value '"
              + index + "' not in range [0.."
              + (this._alcodMapList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.AlcodMap) _alcodMapList
            .get(index);
  }

  /**
   * Method getAlcodMap.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.AlcodMap[] getAlcodMap()
  {
    jalview.schemabinding.version2.AlcodMap[] array = new jalview.schemabinding.version2.AlcodMap[0];
    return (jalview.schemabinding.version2.AlcodMap[]) this._alcodMapList
            .toArray(array);
  }

  /**
   * Method getAlcodMapCount.
   * 
   * @return the size of this collection
   */
  public int getAlcodMapCount()
  {
    return this._alcodMapList.size();
  }

  /**
   * Method getAlcodon.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.Alcodon at the
   *         given index
   */
  public jalview.schemabinding.version2.Alcodon getAlcodon(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alcodonList.size())
    {
      throw new IndexOutOfBoundsException("getAlcodon: Index value '"
              + index + "' not in range [0.."
              + (this._alcodonList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Alcodon) _alcodonList.get(index);
  }

  /**
   * Method getAlcodon.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.Alcodon[] getAlcodon()
  {
    jalview.schemabinding.version2.Alcodon[] array = new jalview.schemabinding.version2.Alcodon[0];
    return (jalview.schemabinding.version2.Alcodon[]) this._alcodonList
            .toArray(array);
  }

  /**
   * Method getAlcodonCount.
   * 
   * @return the size of this collection
   */
  public int getAlcodonCount()
  {
    return this._alcodonList.size();
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
   * Method removeAlcodMap.
   * 
   * @param vAlcodMap
   * @return true if the object was removed from the collection.
   */
  public boolean removeAlcodMap(
          final jalview.schemabinding.version2.AlcodMap vAlcodMap)
  {
    boolean removed = _alcodMapList.remove(vAlcodMap);
    return removed;
  }

  /**
   * Method removeAlcodMapAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.AlcodMap removeAlcodMapAt(
          final int index)
  {
    java.lang.Object obj = this._alcodMapList.remove(index);
    return (jalview.schemabinding.version2.AlcodMap) obj;
  }

  /**
   * Method removeAlcodon.
   * 
   * @param vAlcodon
   * @return true if the object was removed from the collection.
   */
  public boolean removeAlcodon(
          final jalview.schemabinding.version2.Alcodon vAlcodon)
  {
    boolean removed = _alcodonList.remove(vAlcodon);
    return removed;
  }

  /**
   * Method removeAlcodonAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.Alcodon removeAlcodonAt(
          final int index)
  {
    java.lang.Object obj = this._alcodonList.remove(index);
    return (jalview.schemabinding.version2.Alcodon) obj;
  }

  /**
     */
  public void removeAllAlcodMap()
  {
    this._alcodMapList.clear();
  }

  /**
     */
  public void removeAllAlcodon()
  {
    this._alcodonList.clear();
  }

  /**
   * 
   * 
   * @param index
   * @param vAlcodMap
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setAlcodMap(final int index,
          final jalview.schemabinding.version2.AlcodMap vAlcodMap)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alcodMapList.size())
    {
      throw new IndexOutOfBoundsException("setAlcodMap: Index value '"
              + index + "' not in range [0.."
              + (this._alcodMapList.size() - 1) + "]");
    }

    this._alcodMapList.set(index, vAlcodMap);
  }

  /**
   * 
   * 
   * @param vAlcodMapArray
   */
  public void setAlcodMap(
          final jalview.schemabinding.version2.AlcodMap[] vAlcodMapArray)
  {
    // -- copy array
    _alcodMapList.clear();

    for (int i = 0; i < vAlcodMapArray.length; i++)
    {
      this._alcodMapList.add(vAlcodMapArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vAlcodon
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setAlcodon(final int index,
          final jalview.schemabinding.version2.Alcodon vAlcodon)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alcodonList.size())
    {
      throw new IndexOutOfBoundsException("setAlcodon: Index value '"
              + index + "' not in range [0.."
              + (this._alcodonList.size() - 1) + "]");
    }

    this._alcodonList.set(index, vAlcodon);
  }

  /**
   * 
   * 
   * @param vAlcodonArray
   */
  public void setAlcodon(
          final jalview.schemabinding.version2.Alcodon[] vAlcodonArray)
  {
    // -- copy array
    _alcodonList.clear();

    for (int i = 0; i < vAlcodonArray.length; i++)
    {
      this._alcodonList.add(vAlcodonArray[i]);
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
   * @return the unmarshaled jalview.schemabinding.version2.AlcodonFrame
   */
  public static jalview.schemabinding.version2.AlcodonFrame unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.AlcodonFrame) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.AlcodonFrame.class,
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
