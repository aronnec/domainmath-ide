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
  private java.lang.String _id;

  /**
   * Field _hidden.
   */
  private boolean _hidden;

  /**
   * keeps track of state for field: _hidden
   */
  private boolean _has_hidden;

  /**
   * Field _featuresList.
   */
  private java.util.Vector _featuresList;

  /**
   * Field _pdbidsList.
   */
  private java.util.Vector _pdbidsList;

  /**
   * Field _hiddenSequencesList.
   */
  private java.util.Vector _hiddenSequencesList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public JSeq()
  {
    super();
    this._featuresList = new java.util.Vector();
    this._pdbidsList = new java.util.Vector();
    this._hiddenSequencesList = new java.util.Vector();
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
  public void addFeatures(
          final jalview.schemabinding.version2.Features vFeatures)
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
          final jalview.schemabinding.version2.Features vFeatures)
          throws java.lang.IndexOutOfBoundsException
  {
    this._featuresList.add(index, vFeatures);
  }

  /**
   * 
   * 
   * @param vHiddenSequences
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addHiddenSequences(final int vHiddenSequences)
          throws java.lang.IndexOutOfBoundsException
  {
    this._hiddenSequencesList.addElement(new java.lang.Integer(
            vHiddenSequences));
  }

  /**
   * 
   * 
   * @param index
   * @param vHiddenSequences
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addHiddenSequences(final int index, final int vHiddenSequences)
          throws java.lang.IndexOutOfBoundsException
  {
    this._hiddenSequencesList.add(index, new java.lang.Integer(
            vHiddenSequences));
  }

  /**
   * 
   * 
   * @param vPdbids
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addPdbids(final jalview.schemabinding.version2.Pdbids vPdbids)
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
          final jalview.schemabinding.version2.Pdbids vPdbids)
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
  public void deleteHidden()
  {
    this._has_hidden = false;
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
   * @return an Enumeration over all jalview.schemabinding.version2.Features
   *         elements
   */
  public java.util.Enumeration enumerateFeatures()
  {
    return this._featuresList.elements();
  }

  /**
   * Method enumerateHiddenSequences.
   * 
   * @return an Enumeration over all int elements
   */
  public java.util.Enumeration enumerateHiddenSequences()
  {
    return this._hiddenSequencesList.elements();
  }

  /**
   * Method enumeratePdbids.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Pdbids
   *         elements
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
   * @return the value of the jalview.schemabinding.version2.Features at the
   *         given index
   */
  public jalview.schemabinding.version2.Features getFeatures(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._featuresList.size())
    {
      throw new IndexOutOfBoundsException("getFeatures: Index value '"
              + index + "' not in range [0.."
              + (this._featuresList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Features) _featuresList
            .get(index);
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
  public jalview.schemabinding.version2.Features[] getFeatures()
  {
    jalview.schemabinding.version2.Features[] array = new jalview.schemabinding.version2.Features[0];
    return (jalview.schemabinding.version2.Features[]) this._featuresList
            .toArray(array);
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
   * Returns the value of field 'hidden'.
   * 
   * @return the value of field 'Hidden'.
   */
  public boolean getHidden()
  {
    return this._hidden;
  }

  /**
   * Method getHiddenSequences.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the int at the given index
   */
  public int getHiddenSequences(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._hiddenSequencesList.size())
    {
      throw new IndexOutOfBoundsException(
              "getHiddenSequences: Index value '" + index
                      + "' not in range [0.."
                      + (this._hiddenSequencesList.size() - 1) + "]");
    }

    return ((java.lang.Integer) _hiddenSequencesList.get(index)).intValue();
  }

  /**
   * Method getHiddenSequences.Returns the contents of the collection in an
   * Array.
   * 
   * @return this collection as an Array
   */
  public int[] getHiddenSequences()
  {
    int size = this._hiddenSequencesList.size();
    int[] array = new int[size];
    java.util.Iterator iter = _hiddenSequencesList.iterator();
    for (int index = 0; index < size; index++)
    {
      array[index] = ((java.lang.Integer) iter.next()).intValue();
    }
    return array;
  }

  /**
   * Method getHiddenSequencesCount.
   * 
   * @return the size of this collection
   */
  public int getHiddenSequencesCount()
  {
    return this._hiddenSequencesList.size();
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
   * Method getPdbids.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.Pdbids at the given
   *         index
   */
  public jalview.schemabinding.version2.Pdbids getPdbids(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._pdbidsList.size())
    {
      throw new IndexOutOfBoundsException("getPdbids: Index value '"
              + index + "' not in range [0.."
              + (this._pdbidsList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Pdbids) _pdbidsList.get(index);
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
  public jalview.schemabinding.version2.Pdbids[] getPdbids()
  {
    jalview.schemabinding.version2.Pdbids[] array = new jalview.schemabinding.version2.Pdbids[0];
    return (jalview.schemabinding.version2.Pdbids[]) this._pdbidsList
            .toArray(array);
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
   * Method hasHidden.
   * 
   * @return true if at least one Hidden has been added
   */
  public boolean hasHidden()
  {
    return this._has_hidden;
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
   * Returns the value of field 'hidden'.
   * 
   * @return the value of field 'Hidden'.
   */
  public boolean isHidden()
  {
    return this._hidden;
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
  public void removeAllHiddenSequences()
  {
    this._hiddenSequencesList.clear();
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
  public boolean removeFeatures(
          final jalview.schemabinding.version2.Features vFeatures)
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
  public jalview.schemabinding.version2.Features removeFeaturesAt(
          final int index)
  {
    java.lang.Object obj = this._featuresList.remove(index);
    return (jalview.schemabinding.version2.Features) obj;
  }

  /**
   * Method removeHiddenSequences.
   * 
   * @param vHiddenSequences
   * @return true if the object was removed from the collection.
   */
  public boolean removeHiddenSequences(final int vHiddenSequences)
  {
    boolean removed = _hiddenSequencesList.remove(new java.lang.Integer(
            vHiddenSequences));
    return removed;
  }

  /**
   * Method removeHiddenSequencesAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public int removeHiddenSequencesAt(final int index)
  {
    java.lang.Object obj = this._hiddenSequencesList.remove(index);
    return ((java.lang.Integer) obj).intValue();
  }

  /**
   * Method removePdbids.
   * 
   * @param vPdbids
   * @return true if the object was removed from the collection.
   */
  public boolean removePdbids(
          final jalview.schemabinding.version2.Pdbids vPdbids)
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
  public jalview.schemabinding.version2.Pdbids removePdbidsAt(
          final int index)
  {
    java.lang.Object obj = this._pdbidsList.remove(index);
    return (jalview.schemabinding.version2.Pdbids) obj;
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
          final jalview.schemabinding.version2.Features vFeatures)
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
  public void setFeatures(
          final jalview.schemabinding.version2.Features[] vFeaturesArray)
  {
    // -- copy array
    _featuresList.clear();

    for (int i = 0; i < vFeaturesArray.length; i++)
    {
      this._featuresList.add(vFeaturesArray[i]);
    }
  }

  /**
   * Sets the value of field 'hidden'.
   * 
   * @param hidden
   *          the value of field 'hidden'.
   */
  public void setHidden(final boolean hidden)
  {
    this._hidden = hidden;
    this._has_hidden = true;
  }

  /**
   * 
   * 
   * @param index
   * @param vHiddenSequences
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setHiddenSequences(final int index, final int vHiddenSequences)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._hiddenSequencesList.size())
    {
      throw new IndexOutOfBoundsException(
              "setHiddenSequences: Index value '" + index
                      + "' not in range [0.."
                      + (this._hiddenSequencesList.size() - 1) + "]");
    }

    this._hiddenSequencesList.set(index, new java.lang.Integer(
            vHiddenSequences));
  }

  /**
   * 
   * 
   * @param vHiddenSequencesArray
   */
  public void setHiddenSequences(final int[] vHiddenSequencesArray)
  {
    // -- copy array
    _hiddenSequencesList.clear();

    for (int i = 0; i < vHiddenSequencesArray.length; i++)
    {
      this._hiddenSequencesList.add(new java.lang.Integer(
              vHiddenSequencesArray[i]));
    }
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
   * @param vPdbids
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setPdbids(final int index,
          final jalview.schemabinding.version2.Pdbids vPdbids)
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
  public void setPdbids(
          final jalview.schemabinding.version2.Pdbids[] vPdbidsArray)
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
   * @return the unmarshaled jalview.schemabinding.version2.JSeq
   */
  public static jalview.schemabinding.version2.JSeq unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.JSeq) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.JSeq.class, reader);
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
