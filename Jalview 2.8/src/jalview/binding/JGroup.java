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
 * Class JGroup.
 * 
 * @version $Revision$ $Date$
 */
public class JGroup implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

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
   * Field _name.
   */
  private java.lang.String _name;

  /**
   * Field _colour.
   */
  private java.lang.String _colour;

  /**
   * Field _consThreshold.
   */
  private int _consThreshold;

  /**
   * keeps track of state for field: _consThreshold
   */
  private boolean _has_consThreshold;

  /**
   * Field _pidThreshold.
   */
  private int _pidThreshold;

  /**
   * keeps track of state for field: _pidThreshold
   */
  private boolean _has_pidThreshold;

  /**
   * Field _outlineColour.
   */
  private int _outlineColour;

  /**
   * keeps track of state for field: _outlineColour
   */
  private boolean _has_outlineColour;

  /**
   * Field _displayBoxes.
   */
  private boolean _displayBoxes;

  /**
   * keeps track of state for field: _displayBoxes
   */
  private boolean _has_displayBoxes;

  /**
   * Field _displayText.
   */
  private boolean _displayText;

  /**
   * keeps track of state for field: _displayText
   */
  private boolean _has_displayText;

  /**
   * Field _colourText.
   */
  private boolean _colourText;

  /**
   * keeps track of state for field: _colourText
   */
  private boolean _has_colourText;

  /**
   * Field _seqList.
   */
  private java.util.Vector _seqList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public JGroup()
  {
    super();
    this._seqList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSeq(final int vSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    this._seqList.addElement(new java.lang.Integer(vSeq));
  }

  /**
   * 
   * 
   * @param index
   * @param vSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSeq(final int index, final int vSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    this._seqList.add(index, new java.lang.Integer(vSeq));
  }

  /**
     */
  public void deleteColourText()
  {
    this._has_colourText = false;
  }

  /**
     */
  public void deleteConsThreshold()
  {
    this._has_consThreshold = false;
  }

  /**
     */
  public void deleteDisplayBoxes()
  {
    this._has_displayBoxes = false;
  }

  /**
     */
  public void deleteDisplayText()
  {
    this._has_displayText = false;
  }

  /**
     */
  public void deleteEnd()
  {
    this._has_end = false;
  }

  /**
     */
  public void deleteOutlineColour()
  {
    this._has_outlineColour = false;
  }

  /**
     */
  public void deletePidThreshold()
  {
    this._has_pidThreshold = false;
  }

  /**
     */
  public void deleteStart()
  {
    this._has_start = false;
  }

  /**
   * Method enumerateSeq.
   * 
   * @return an Enumeration over all int elements
   */
  public java.util.Enumeration enumerateSeq()
  {
    return this._seqList.elements();
  }

  /**
   * Returns the value of field 'colour'.
   * 
   * @return the value of field 'Colour'.
   */
  public java.lang.String getColour()
  {
    return this._colour;
  }

  /**
   * Returns the value of field 'colourText'.
   * 
   * @return the value of field 'ColourText'.
   */
  public boolean getColourText()
  {
    return this._colourText;
  }

  /**
   * Returns the value of field 'consThreshold'.
   * 
   * @return the value of field 'ConsThreshold'.
   */
  public int getConsThreshold()
  {
    return this._consThreshold;
  }

  /**
   * Returns the value of field 'displayBoxes'.
   * 
   * @return the value of field 'DisplayBoxes'.
   */
  public boolean getDisplayBoxes()
  {
    return this._displayBoxes;
  }

  /**
   * Returns the value of field 'displayText'.
   * 
   * @return the value of field 'DisplayText'.
   */
  public boolean getDisplayText()
  {
    return this._displayText;
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
   * Returns the value of field 'name'.
   * 
   * @return the value of field 'Name'.
   */
  public java.lang.String getName()
  {
    return this._name;
  }

  /**
   * Returns the value of field 'outlineColour'.
   * 
   * @return the value of field 'OutlineColour'.
   */
  public int getOutlineColour()
  {
    return this._outlineColour;
  }

  /**
   * Returns the value of field 'pidThreshold'.
   * 
   * @return the value of field 'PidThreshold'.
   */
  public int getPidThreshold()
  {
    return this._pidThreshold;
  }

  /**
   * Method getSeq.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the int at the given index
   */
  public int getSeq(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._seqList.size())
    {
      throw new IndexOutOfBoundsException("getSeq: Index value '" + index
              + "' not in range [0.." + (this._seqList.size() - 1) + "]");
    }

    return ((java.lang.Integer) _seqList.get(index)).intValue();
  }

  /**
   * Method getSeq.Returns the contents of the collection in an Array.
   * 
   * @return this collection as an Array
   */
  public int[] getSeq()
  {
    int size = this._seqList.size();
    int[] array = new int[size];
    java.util.Iterator iter = _seqList.iterator();
    for (int index = 0; index < size; index++)
    {
      array[index] = ((java.lang.Integer) iter.next()).intValue();
    }
    return array;
  }

  /**
   * Method getSeqCount.
   * 
   * @return the size of this collection
   */
  public int getSeqCount()
  {
    return this._seqList.size();
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
   * Method hasColourText.
   * 
   * @return true if at least one ColourText has been added
   */
  public boolean hasColourText()
  {
    return this._has_colourText;
  }

  /**
   * Method hasConsThreshold.
   * 
   * @return true if at least one ConsThreshold has been added
   */
  public boolean hasConsThreshold()
  {
    return this._has_consThreshold;
  }

  /**
   * Method hasDisplayBoxes.
   * 
   * @return true if at least one DisplayBoxes has been added
   */
  public boolean hasDisplayBoxes()
  {
    return this._has_displayBoxes;
  }

  /**
   * Method hasDisplayText.
   * 
   * @return true if at least one DisplayText has been added
   */
  public boolean hasDisplayText()
  {
    return this._has_displayText;
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
   * Method hasOutlineColour.
   * 
   * @return true if at least one OutlineColour has been added
   */
  public boolean hasOutlineColour()
  {
    return this._has_outlineColour;
  }

  /**
   * Method hasPidThreshold.
   * 
   * @return true if at least one PidThreshold has been added
   */
  public boolean hasPidThreshold()
  {
    return this._has_pidThreshold;
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
   * Returns the value of field 'colourText'.
   * 
   * @return the value of field 'ColourText'.
   */
  public boolean isColourText()
  {
    return this._colourText;
  }

  /**
   * Returns the value of field 'displayBoxes'.
   * 
   * @return the value of field 'DisplayBoxes'.
   */
  public boolean isDisplayBoxes()
  {
    return this._displayBoxes;
  }

  /**
   * Returns the value of field 'displayText'.
   * 
   * @return the value of field 'DisplayText'.
   */
  public boolean isDisplayText()
  {
    return this._displayText;
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
  public void removeAllSeq()
  {
    this._seqList.clear();
  }

  /**
   * Method removeSeq.
   * 
   * @param vSeq
   * @return true if the object was removed from the collection.
   */
  public boolean removeSeq(final int vSeq)
  {
    boolean removed = _seqList.remove(new java.lang.Integer(vSeq));
    return removed;
  }

  /**
   * Method removeSeqAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public int removeSeqAt(final int index)
  {
    java.lang.Object obj = this._seqList.remove(index);
    return ((java.lang.Integer) obj).intValue();
  }

  /**
   * Sets the value of field 'colour'.
   * 
   * @param colour
   *          the value of field 'colour'.
   */
  public void setColour(final java.lang.String colour)
  {
    this._colour = colour;
  }

  /**
   * Sets the value of field 'colourText'.
   * 
   * @param colourText
   *          the value of field 'colourText'.
   */
  public void setColourText(final boolean colourText)
  {
    this._colourText = colourText;
    this._has_colourText = true;
  }

  /**
   * Sets the value of field 'consThreshold'.
   * 
   * @param consThreshold
   *          the value of field 'consThreshold'.
   */
  public void setConsThreshold(final int consThreshold)
  {
    this._consThreshold = consThreshold;
    this._has_consThreshold = true;
  }

  /**
   * Sets the value of field 'displayBoxes'.
   * 
   * @param displayBoxes
   *          the value of field 'displayBoxes'.
   */
  public void setDisplayBoxes(final boolean displayBoxes)
  {
    this._displayBoxes = displayBoxes;
    this._has_displayBoxes = true;
  }

  /**
   * Sets the value of field 'displayText'.
   * 
   * @param displayText
   *          the value of field 'displayText'.
   */
  public void setDisplayText(final boolean displayText)
  {
    this._displayText = displayText;
    this._has_displayText = true;
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
   * Sets the value of field 'name'.
   * 
   * @param name
   *          the value of field 'name'.
   */
  public void setName(final java.lang.String name)
  {
    this._name = name;
  }

  /**
   * Sets the value of field 'outlineColour'.
   * 
   * @param outlineColour
   *          the value of field 'outlineColour'.
   */
  public void setOutlineColour(final int outlineColour)
  {
    this._outlineColour = outlineColour;
    this._has_outlineColour = true;
  }

  /**
   * Sets the value of field 'pidThreshold'.
   * 
   * @param pidThreshold
   *          the value of field 'pidThreshold'.
   */
  public void setPidThreshold(final int pidThreshold)
  {
    this._pidThreshold = pidThreshold;
    this._has_pidThreshold = true;
  }

  /**
   * 
   * 
   * @param index
   * @param vSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setSeq(final int index, final int vSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._seqList.size())
    {
      throw new IndexOutOfBoundsException("setSeq: Index value '" + index
              + "' not in range [0.." + (this._seqList.size() - 1) + "]");
    }

    this._seqList.set(index, new java.lang.Integer(vSeq));
  }

  /**
   * 
   * 
   * @param vSeqArray
   */
  public void setSeq(final int[] vSeqArray)
  {
    // -- copy array
    _seqList.clear();

    for (int i = 0; i < vSeqArray.length; i++)
    {
      this._seqList.add(new java.lang.Integer(vSeqArray[i]));
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
   * @return the unmarshaled jalview.binding.JGroup
   */
  public static jalview.binding.JGroup unmarshal(final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.JGroup) Unmarshaller.unmarshal(
            jalview.binding.JGroup.class, reader);
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
