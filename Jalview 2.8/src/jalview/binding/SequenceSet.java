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
 * Class SequenceSet.
 * 
 * @version $Revision$ $Date$
 */
public class SequenceSet implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _gapChar.
   */
  private java.lang.String _gapChar;

  /**
   * Field _aligned.
   */
  private boolean _aligned;

  /**
   * keeps track of state for field: _aligned
   */
  private boolean _has_aligned;

  /**
   * Field _sequenceList.
   */
  private java.util.Vector _sequenceList;

  /**
   * Field _annotationList.
   */
  private java.util.Vector _annotationList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public SequenceSet()
  {
    super();
    this._sequenceList = new java.util.Vector();
    this._annotationList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vAnnotation
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAnnotation(final jalview.binding.Annotation vAnnotation)
          throws java.lang.IndexOutOfBoundsException
  {
    this._annotationList.addElement(vAnnotation);
  }

  /**
   * 
   * 
   * @param index
   * @param vAnnotation
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAnnotation(final int index,
          final jalview.binding.Annotation vAnnotation)
          throws java.lang.IndexOutOfBoundsException
  {
    this._annotationList.add(index, vAnnotation);
  }

  /**
   * 
   * 
   * @param vSequence
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSequence(final jalview.binding.Sequence vSequence)
          throws java.lang.IndexOutOfBoundsException
  {
    this._sequenceList.addElement(vSequence);
  }

  /**
   * 
   * 
   * @param index
   * @param vSequence
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSequence(final int index,
          final jalview.binding.Sequence vSequence)
          throws java.lang.IndexOutOfBoundsException
  {
    this._sequenceList.add(index, vSequence);
  }

  /**
     */
  public void deleteAligned()
  {
    this._has_aligned = false;
  }

  /**
   * Method enumerateAnnotation.
   * 
   * @return an Enumeration over all jalview.binding.Annotation elements
   */
  public java.util.Enumeration enumerateAnnotation()
  {
    return this._annotationList.elements();
  }

  /**
   * Method enumerateSequence.
   * 
   * @return an Enumeration over all jalview.binding.Sequence elements
   */
  public java.util.Enumeration enumerateSequence()
  {
    return this._sequenceList.elements();
  }

  /**
   * Returns the value of field 'aligned'.
   * 
   * @return the value of field 'Aligned'.
   */
  public boolean getAligned()
  {
    return this._aligned;
  }

  /**
   * Method getAnnotation.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.binding.Annotation at the given index
   */
  public jalview.binding.Annotation getAnnotation(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._annotationList.size())
    {
      throw new IndexOutOfBoundsException("getAnnotation: Index value '"
              + index + "' not in range [0.."
              + (this._annotationList.size() - 1) + "]");
    }

    return (jalview.binding.Annotation) _annotationList.get(index);
  }

  /**
   * Method getAnnotation.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.binding.Annotation[] getAnnotation()
  {
    jalview.binding.Annotation[] array = new jalview.binding.Annotation[0];
    return (jalview.binding.Annotation[]) this._annotationList
            .toArray(array);
  }

  /**
   * Method getAnnotationCount.
   * 
   * @return the size of this collection
   */
  public int getAnnotationCount()
  {
    return this._annotationList.size();
  }

  /**
   * Returns the value of field 'gapChar'.
   * 
   * @return the value of field 'GapChar'.
   */
  public java.lang.String getGapChar()
  {
    return this._gapChar;
  }

  /**
   * Method getSequence.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.binding.Sequence at the given index
   */
  public jalview.binding.Sequence getSequence(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceList.size())
    {
      throw new IndexOutOfBoundsException("getSequence: Index value '"
              + index + "' not in range [0.."
              + (this._sequenceList.size() - 1) + "]");
    }

    return (jalview.binding.Sequence) _sequenceList.get(index);
  }

  /**
   * Method getSequence.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.binding.Sequence[] getSequence()
  {
    jalview.binding.Sequence[] array = new jalview.binding.Sequence[0];
    return (jalview.binding.Sequence[]) this._sequenceList.toArray(array);
  }

  /**
   * Method getSequenceCount.
   * 
   * @return the size of this collection
   */
  public int getSequenceCount()
  {
    return this._sequenceList.size();
  }

  /**
   * Method hasAligned.
   * 
   * @return true if at least one Aligned has been added
   */
  public boolean hasAligned()
  {
    return this._has_aligned;
  }

  /**
   * Returns the value of field 'aligned'.
   * 
   * @return the value of field 'Aligned'.
   */
  public boolean isAligned()
  {
    return this._aligned;
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
  public void removeAllAnnotation()
  {
    this._annotationList.clear();
  }

  /**
     */
  public void removeAllSequence()
  {
    this._sequenceList.clear();
  }

  /**
   * Method removeAnnotation.
   * 
   * @param vAnnotation
   * @return true if the object was removed from the collection.
   */
  public boolean removeAnnotation(
          final jalview.binding.Annotation vAnnotation)
  {
    boolean removed = _annotationList.remove(vAnnotation);
    return removed;
  }

  /**
   * Method removeAnnotationAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.binding.Annotation removeAnnotationAt(final int index)
  {
    java.lang.Object obj = this._annotationList.remove(index);
    return (jalview.binding.Annotation) obj;
  }

  /**
   * Method removeSequence.
   * 
   * @param vSequence
   * @return true if the object was removed from the collection.
   */
  public boolean removeSequence(final jalview.binding.Sequence vSequence)
  {
    boolean removed = _sequenceList.remove(vSequence);
    return removed;
  }

  /**
   * Method removeSequenceAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.binding.Sequence removeSequenceAt(final int index)
  {
    java.lang.Object obj = this._sequenceList.remove(index);
    return (jalview.binding.Sequence) obj;
  }

  /**
   * Sets the value of field 'aligned'.
   * 
   * @param aligned
   *          the value of field 'aligned'.
   */
  public void setAligned(final boolean aligned)
  {
    this._aligned = aligned;
    this._has_aligned = true;
  }

  /**
   * 
   * 
   * @param index
   * @param vAnnotation
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setAnnotation(final int index,
          final jalview.binding.Annotation vAnnotation)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._annotationList.size())
    {
      throw new IndexOutOfBoundsException("setAnnotation: Index value '"
              + index + "' not in range [0.."
              + (this._annotationList.size() - 1) + "]");
    }

    this._annotationList.set(index, vAnnotation);
  }

  /**
   * 
   * 
   * @param vAnnotationArray
   */
  public void setAnnotation(
          final jalview.binding.Annotation[] vAnnotationArray)
  {
    // -- copy array
    _annotationList.clear();

    for (int i = 0; i < vAnnotationArray.length; i++)
    {
      this._annotationList.add(vAnnotationArray[i]);
    }
  }

  /**
   * Sets the value of field 'gapChar'.
   * 
   * @param gapChar
   *          the value of field 'gapChar'.
   */
  public void setGapChar(final java.lang.String gapChar)
  {
    this._gapChar = gapChar;
  }

  /**
   * 
   * 
   * @param index
   * @param vSequence
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setSequence(final int index,
          final jalview.binding.Sequence vSequence)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceList.size())
    {
      throw new IndexOutOfBoundsException("setSequence: Index value '"
              + index + "' not in range [0.."
              + (this._sequenceList.size() - 1) + "]");
    }

    this._sequenceList.set(index, vSequence);
  }

  /**
   * 
   * 
   * @param vSequenceArray
   */
  public void setSequence(final jalview.binding.Sequence[] vSequenceArray)
  {
    // -- copy array
    _sequenceList.clear();

    for (int i = 0; i < vSequenceArray.length; i++)
    {
      this._sequenceList.add(vSequenceArray[i]);
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
   * @return the unmarshaled jalview.binding.SequenceSet
   */
  public static jalview.binding.SequenceSet unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.SequenceSet) Unmarshaller.unmarshal(
            jalview.binding.SequenceSet.class, reader);
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
