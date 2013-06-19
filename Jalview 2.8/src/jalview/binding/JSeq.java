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
 * Class JSeq.
 * 
 * @version $Revision$ $Date$
 */
public class JSeq implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _colour.
   */
  private int _colour;

  /**
   * keeps track of state for field: _colour
   */
  private boolean _has_colour;

  /**
   * Field _start.
   */
  private int _start;

  /**
   * keeps track of state for field: _start
   */
  private boolean _has_start;

  /**
   * Field _end.
   */
  private int _end;

  /**
   * keeps track of state for field: _end
   */
  private boolean _has_end;

  /**
   * Field _id.
   */
  private int _id;

  /**
   * keeps track of state for field: _id
   */
  private boolean _has_id;

  /**
   * Field _featuresList.
   */
  private java.util.Vector _featuresList;

  /**
   * Field _pdbidsList.
   */
  private java.util.Vector _pdbidsList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public JSeq()
  {
    super();
    this._featuresList = new java.util.Vector();
    this._pdbidsList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vFeatures
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addFeatures(final jalview.binding.Features vFeatures)
          throws java.lang.IndexOutOfBoundsException
  {
    this._featuresList.addElement(vFeatures);
  }

  /**
   * 
   * 
   * @param index
   * @param vFeatures
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addFeatures(final int index,
          final jalview.binding.Features vFeatures)
          throws java.lang.IndexOutOfBoundsException
  {
    this._featuresList.add(index, vFeatures);
  }

  /**
   * 
   * 
   * @param vPdbids
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addPdbids(final jalview.binding.Pdbids vPdbids)
          throws java.lang.IndexOutOfBoundsException
  {
    this._pdbidsList.addElement(vPdbids);
  }

  /**
   * 
   * 
   * @param index
   * @param vPdbids
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addPdbids(final int index,
          final jalview.binding.Pdbids vPdbids)
          throws java.lang.IndexOutOfBoundsException
  {
    this._pdbidsList.add(index, vPdbids);
  }

  /**
     */
  public void deleteColour()
  {
    this._has_colour = false;
  }

  /**
     */
  public void deleteEnd()
  {
    this._has_end = false;
  }

  /**
     */
  public void deleteId()
  {
    this._has_id = false;
  }

  /**
     */
  public void deleteStart()
  {
    this._has_start = false;
  }

  /**
   * Method enumerateFeatures.
   * 
   * @return an Enumeration over all jalview.binding.Features elements
   */
  public java.util.Enumeration enumerateFeatures()
  {
    return this._featuresList.elements();
  }

  /**
   * Method enumeratePdbids.
   * 
   * @return an Enumeration over all jalview.binding.Pdbids elements
   */
  public java.util.Enumeration enumeratePdbids()
  {
    return this._pdbidsList.elements();
  }

  /**
   * Returns the value of field 'colour'.
   * 
   * @return the value of field 'Colour'.
   */
  public int getColour()
  {
    return this._colour;
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
   * Method getFeatures.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.binding.Features at the given index
   */
  public jalview.binding.Features getFeatures(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._featuresList.size())
    {
      throw new IndexOutOfBoundsException("getFeatures: Index value '"
              + index + "' not in range [0.."
              + (this._featuresList.size() - 1) + "]");
    }

    return (jalview.binding.Features) _featuresList.get(index);
  }

  /**
   * Method getFeatures.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.binding.Features[] getFeatures()
  {
    jalview.binding.Features[] array = new jalview.binding.Features[0];
    return (jalview.binding.Features[]) this._featuresList.toArray(array);
  }

  /**
   * Method getFeaturesCount.
   * 
   * @return the size of this collection
   */
  public int getFeaturesCount()
  {
    return this._featuresList.size();
  }

  /**
   * Returns the value of field 'id'.
   * 
   * @return the value of field 'Id'.
   */
  public int getId()
  {
    return this._id;
  }

  /**
   * Method getPdbids.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.binding.Pdbids at the given index
   */
  public jalview.binding.Pdbids getPdbids(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._pdbidsList.size())
    {
      throw new IndexOutOfBoundsException("getPdbids: Index value '"
              + index + "' not in range [0.."
              + (this._pdbidsList.size() - 1) + "]");
    }

    return (jalview.binding.Pdbids) _pdbidsList.get(index);
  }

  /**
   * Method getPdbids.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.binding.Pdbids[] getPdbids()
  {
    jalview.binding.Pdbids[] array = new jalview.binding.Pdbids[0];
    return (jalview.binding.Pdbids[]) this._pdbidsList.toArray(array);
  }

  /**
   * Method getPdbidsCount.
   * 
   * @return the size of this collection
   */
  public int getPdbidsCount()
  {
    return this._pdbidsList.size();
  }

  /**
   * Returns the value of field 'start'.
   * 
   * @return the value of field 'Start'.
   */
  public int getStart()
  {
    return this._start;
  }

  /**
   * Method hasColour.
   * 
   * @return true if at least one Colour has been added
   */
  public boolean hasColour()
  {
    return this._has_colour;
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
   * Method hasId.
   * 
   * @return true if at least one Id has been added
   */
  public boolean hasId()
  {
    return this._has_id;
  }

  /**
   * Method hasStart.
   * 
   * @return true if at least one Start has been added
   */
  public boolean hasStart()
  {
    return this._has_start;
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
  public void removeAllFeatures()
  {
    this._featuresList.clear();
  }

  /**
     */
  public void removeAllPdbids()
  {
    this._pdbidsList.clear();
  }

  /**
   * Method removeFeatures.
   * 
   * @param vFeatures
   * @return true if the object was removed from the collection.
   */
  public boolean removeFeatures(final jalview.binding.Features vFeatures)
  {
    boolean removed = _featuresList.remove(vFeatures);
    return removed;
  }

  /**
   * Method removeFeaturesAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.binding.Features removeFeaturesAt(final int index)
  {
    java.lang.Object obj = this._featuresList.remove(index);
    return (jalview.binding.Features) obj;
  }

  /**
   * Method removePdbids.
   * 
   * @param vPdbids
   * @return true if the object was removed from the collection.
   */
  public boolean removePdbids(final jalview.binding.Pdbids vPdbids)
  {
    boolean removed = _pdbidsList.remove(vPdbids);
    return removed;
  }

  /**
   * Method removePdbidsAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.binding.Pdbids removePdbidsAt(final int index)
  {
    java.lang.Object obj = this._pdbidsList.remove(index);
    return (jalview.binding.Pdbids) obj;
  }

  /**
   * Sets the value of field 'colour'.
   * 
   * @param colour
   *          the value of field 'colour'.
   */
  public void setColour(final int colour)
  {
    this._colour = colour;
    this._has_colour = true;
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
   * 
   * 
   * @param index
   * @param vFeatures
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setFeatures(final int index,
          final jalview.binding.Features vFeatures)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._featuresList.size())
    {
      throw new IndexOutOfBoundsException("setFeatures: Index value '"
              + index + "' not in range [0.."
              + (this._featuresList.size() - 1) + "]");
    }

    this._featuresList.set(index, vFeatures);
  }

  /**
   * 
   * 
   * @param vFeaturesArray
   */
  public void setFeatures(final jalview.binding.Features[] vFeaturesArray)
  {
    // -- copy array
    _featuresList.clear();

    for (int i = 0; i < vFeaturesArray.length; i++)
    {
      this._featuresList.add(vFeaturesArray[i]);
    }
  }

  /**
   * Sets the value of field 'id'.
   * 
   * @param id
   *          the value of field 'id'.
   */
  public void setId(final int id)
  {
    this._id = id;
    this._has_id = true;
  }

  /**
   * 
   * 
   * @param index
   * @param vPdbids
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setPdbids(final int index,
          final jalview.binding.Pdbids vPdbids)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._pdbidsList.size())
    {
      throw new IndexOutOfBoundsException("setPdbids: Index value '"
              + index + "' not in range [0.."
              + (this._pdbidsList.size() - 1) + "]");
    }

    this._pdbidsList.set(index, vPdbids);
  }

  /**
   * 
   * 
   * @param vPdbidsArray
   */
  public void setPdbids(final jalview.binding.Pdbids[] vPdbidsArray)
  {
    // -- copy array
    _pdbidsList.clear();

    for (int i = 0; i < vPdbidsArray.length; i++)
    {
      this._pdbidsList.add(vPdbidsArray[i]);
    }
  }

  /**
   * Sets the value of field 'start'.
   * 
   * @param start
   *          the value of field 'start'.
   */
  public void setStart(final int start)
  {
    this._start = start;
    this._has_start = true;
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
   * @return the unmarshaled jalview.binding.JSeq
   */
  public static jalview.binding.JSeq unmarshal(final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.JSeq) Unmarshaller.unmarshal(
            jalview.binding.JSeq.class, reader);
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
