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
 * developed after mapRangeType from
 * http://www.vamsas.ac.uk/schemas/1.0/vamsasTypes
 * 
 * This effectively represents a java.util.MapList object
 * 
 * 
 * @version $Revision$ $Date$
 */
public class MapListType implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * number of dictionary symbol widths involved in each mapped position on this
   * sequence (for example, 3 for a dna sequence exon region that is being
   * mapped to a protein sequence). This is optional, since the unit can be
   * usually be inferred from the dictionary type of each sequence involved in
   * the mapping.
   */
  private long _mapFromUnit;

  /**
   * keeps track of state for field: _mapFromUnit
   */
  private boolean _has_mapFromUnit;

  /**
   * number of dictionary symbol widths involved in each mapped position on this
   * sequence (for example, 3 for a dna sequence exon region that is being
   * mapped to a protein sequence). This is optional, since the unit can be
   * usually be inferred from the dictionary type of each sequence involved in
   * the mapping.
   */
  private long _mapToUnit;

  /**
   * keeps track of state for field: _mapToUnit
   */
  private boolean _has_mapToUnit;

  /**
   * a region from start to end inclusive
   */
  private java.util.Vector _mapListFromList;

  /**
   * a region from start to end inclusive
   */
  private java.util.Vector _mapListToList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public MapListType()
  {
    super();
    this._mapListFromList = new java.util.Vector();
    this._mapListToList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vMapListFrom
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addMapListFrom(
          final jalview.schemabinding.version2.MapListFrom vMapListFrom)
          throws java.lang.IndexOutOfBoundsException
  {
    this._mapListFromList.addElement(vMapListFrom);
  }

  /**
   * 
   * 
   * @param index
   * @param vMapListFrom
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addMapListFrom(final int index,
          final jalview.schemabinding.version2.MapListFrom vMapListFrom)
          throws java.lang.IndexOutOfBoundsException
  {
    this._mapListFromList.add(index, vMapListFrom);
  }

  /**
   * 
   * 
   * @param vMapListTo
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addMapListTo(
          final jalview.schemabinding.version2.MapListTo vMapListTo)
          throws java.lang.IndexOutOfBoundsException
  {
    this._mapListToList.addElement(vMapListTo);
  }

  /**
   * 
   * 
   * @param index
   * @param vMapListTo
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addMapListTo(final int index,
          final jalview.schemabinding.version2.MapListTo vMapListTo)
          throws java.lang.IndexOutOfBoundsException
  {
    this._mapListToList.add(index, vMapListTo);
  }

  /**
     */
  public void deleteMapFromUnit()
  {
    this._has_mapFromUnit = false;
  }

  /**
     */
  public void deleteMapToUnit()
  {
    this._has_mapToUnit = false;
  }

  /**
   * Method enumerateMapListFrom.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.MapListFrom
   *         elements
   */
  public java.util.Enumeration enumerateMapListFrom()
  {
    return this._mapListFromList.elements();
  }

  /**
   * Method enumerateMapListTo.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.MapListTo
   *         elements
   */
  public java.util.Enumeration enumerateMapListTo()
  {
    return this._mapListToList.elements();
  }

  /**
   * Returns the value of field 'mapFromUnit'. The field 'mapFromUnit' has the
   * following description: number of dictionary symbol widths involved in each
   * mapped position on this sequence (for example, 3 for a dna sequence exon
   * region that is being mapped to a protein sequence). This is optional, since
   * the unit can be usually be inferred from the dictionary type of each
   * sequence involved in the mapping.
   * 
   * @return the value of field 'MapFromUnit'.
   */
  public long getMapFromUnit()
  {
    return this._mapFromUnit;
  }

  /**
   * Method getMapListFrom.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.MapListFrom at the
   *         given index
   */
  public jalview.schemabinding.version2.MapListFrom getMapListFrom(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._mapListFromList.size())
    {
      throw new IndexOutOfBoundsException("getMapListFrom: Index value '"
              + index + "' not in range [0.."
              + (this._mapListFromList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.MapListFrom) _mapListFromList
            .get(index);
  }

  /**
   * Method getMapListFrom.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.MapListFrom[] getMapListFrom()
  {
    jalview.schemabinding.version2.MapListFrom[] array = new jalview.schemabinding.version2.MapListFrom[0];
    return (jalview.schemabinding.version2.MapListFrom[]) this._mapListFromList
            .toArray(array);
  }

  /**
   * Method getMapListFromCount.
   * 
   * @return the size of this collection
   */
  public int getMapListFromCount()
  {
    return this._mapListFromList.size();
  }

  /**
   * Method getMapListTo.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.MapListTo at the
   *         given index
   */
  public jalview.schemabinding.version2.MapListTo getMapListTo(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._mapListToList.size())
    {
      throw new IndexOutOfBoundsException("getMapListTo: Index value '"
              + index + "' not in range [0.."
              + (this._mapListToList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.MapListTo) _mapListToList
            .get(index);
  }

  /**
   * Method getMapListTo.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.MapListTo[] getMapListTo()
  {
    jalview.schemabinding.version2.MapListTo[] array = new jalview.schemabinding.version2.MapListTo[0];
    return (jalview.schemabinding.version2.MapListTo[]) this._mapListToList
            .toArray(array);
  }

  /**
   * Method getMapListToCount.
   * 
   * @return the size of this collection
   */
  public int getMapListToCount()
  {
    return this._mapListToList.size();
  }

  /**
   * Returns the value of field 'mapToUnit'. The field 'mapToUnit' has the
   * following description: number of dictionary symbol widths involved in each
   * mapped position on this sequence (for example, 3 for a dna sequence exon
   * region that is being mapped to a protein sequence). This is optional, since
   * the unit can be usually be inferred from the dictionary type of each
   * sequence involved in the mapping.
   * 
   * @return the value of field 'MapToUnit'.
   */
  public long getMapToUnit()
  {
    return this._mapToUnit;
  }

  /**
   * Method hasMapFromUnit.
   * 
   * @return true if at least one MapFromUnit has been added
   */
  public boolean hasMapFromUnit()
  {
    return this._has_mapFromUnit;
  }

  /**
   * Method hasMapToUnit.
   * 
   * @return true if at least one MapToUnit has been added
   */
  public boolean hasMapToUnit()
  {
    return this._has_mapToUnit;
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
  public void removeAllMapListFrom()
  {
    this._mapListFromList.clear();
  }

  /**
     */
  public void removeAllMapListTo()
  {
    this._mapListToList.clear();
  }

  /**
   * Method removeMapListFrom.
   * 
   * @param vMapListFrom
   * @return true if the object was removed from the collection.
   */
  public boolean removeMapListFrom(
          final jalview.schemabinding.version2.MapListFrom vMapListFrom)
  {
    boolean removed = _mapListFromList.remove(vMapListFrom);
    return removed;
  }

  /**
   * Method removeMapListFromAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.MapListFrom removeMapListFromAt(
          final int index)
  {
    java.lang.Object obj = this._mapListFromList.remove(index);
    return (jalview.schemabinding.version2.MapListFrom) obj;
  }

  /**
   * Method removeMapListTo.
   * 
   * @param vMapListTo
   * @return true if the object was removed from the collection.
   */
  public boolean removeMapListTo(
          final jalview.schemabinding.version2.MapListTo vMapListTo)
  {
    boolean removed = _mapListToList.remove(vMapListTo);
    return removed;
  }

  /**
   * Method removeMapListToAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.MapListTo removeMapListToAt(
          final int index)
  {
    java.lang.Object obj = this._mapListToList.remove(index);
    return (jalview.schemabinding.version2.MapListTo) obj;
  }

  /**
   * Sets the value of field 'mapFromUnit'. The field 'mapFromUnit' has the
   * following description: number of dictionary symbol widths involved in each
   * mapped position on this sequence (for example, 3 for a dna sequence exon
   * region that is being mapped to a protein sequence). This is optional, since
   * the unit can be usually be inferred from the dictionary type of each
   * sequence involved in the mapping.
   * 
   * @param mapFromUnit
   *          the value of field 'mapFromUnit'.
   */
  public void setMapFromUnit(final long mapFromUnit)
  {
    this._mapFromUnit = mapFromUnit;
    this._has_mapFromUnit = true;
  }

  /**
   * 
   * 
   * @param index
   * @param vMapListFrom
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setMapListFrom(final int index,
          final jalview.schemabinding.version2.MapListFrom vMapListFrom)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._mapListFromList.size())
    {
      throw new IndexOutOfBoundsException("setMapListFrom: Index value '"
              + index + "' not in range [0.."
              + (this._mapListFromList.size() - 1) + "]");
    }

    this._mapListFromList.set(index, vMapListFrom);
  }

  /**
   * 
   * 
   * @param vMapListFromArray
   */
  public void setMapListFrom(
          final jalview.schemabinding.version2.MapListFrom[] vMapListFromArray)
  {
    // -- copy array
    _mapListFromList.clear();

    for (int i = 0; i < vMapListFromArray.length; i++)
    {
      this._mapListFromList.add(vMapListFromArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vMapListTo
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setMapListTo(final int index,
          final jalview.schemabinding.version2.MapListTo vMapListTo)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._mapListToList.size())
    {
      throw new IndexOutOfBoundsException("setMapListTo: Index value '"
              + index + "' not in range [0.."
              + (this._mapListToList.size() - 1) + "]");
    }

    this._mapListToList.set(index, vMapListTo);
  }

  /**
   * 
   * 
   * @param vMapListToArray
   */
  public void setMapListTo(
          final jalview.schemabinding.version2.MapListTo[] vMapListToArray)
  {
    // -- copy array
    _mapListToList.clear();

    for (int i = 0; i < vMapListToArray.length; i++)
    {
      this._mapListToList.add(vMapListToArray[i]);
    }
  }

  /**
   * Sets the value of field 'mapToUnit'. The field 'mapToUnit' has the
   * following description: number of dictionary symbol widths involved in each
   * mapped position on this sequence (for example, 3 for a dna sequence exon
   * region that is being mapped to a protein sequence). This is optional, since
   * the unit can be usually be inferred from the dictionary type of each
   * sequence involved in the mapping.
   * 
   * @param mapToUnit
   *          the value of field 'mapToUnit'.
   */
  public void setMapToUnit(final long mapToUnit)
  {
    this._mapToUnit = mapToUnit;
    this._has_mapToUnit = true;
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
   * @return the unmarshaled jalview.schemabinding.version2.MapListType
   */
  public static jalview.schemabinding.version2.MapListType unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.MapListType) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.MapListType.class,
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
