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
   * reference to set where jalview will gather the dataset sequences for all
   * sequences in the set.
   * 
   */
  private java.lang.String _datasetId;

  /**
   * Field _sequenceList.
   */
  private java.util.Vector _sequenceList;

  /**
   * Field _annotationList.
   */
  private java.util.Vector _annotationList;

  /**
   * Field _sequenceSetPropertiesList.
   */
  private java.util.Vector _sequenceSetPropertiesList;

  /**
   * Field _alcodonFrameList.
   */
  private java.util.Vector _alcodonFrameList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public SequenceSet()
  {
    super();
    this._sequenceList = new java.util.Vector();
    this._annotationList = new java.util.Vector();
    this._sequenceSetPropertiesList = new java.util.Vector();
    this._alcodonFrameList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vAlcodonFrame
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlcodonFrame(
          final jalview.schemabinding.version2.AlcodonFrame vAlcodonFrame)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alcodonFrameList.addElement(vAlcodonFrame);
  }

  /**
   * 
   * 
   * @param index
   * @param vAlcodonFrame
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlcodonFrame(final int index,
          final jalview.schemabinding.version2.AlcodonFrame vAlcodonFrame)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alcodonFrameList.add(index, vAlcodonFrame);
  }

  /**
   * 
   * 
   * @param vAnnotation
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAnnotation(
          final jalview.schemabinding.version2.Annotation vAnnotation)
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
          final jalview.schemabinding.version2.Annotation vAnnotation)
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
  public void addSequence(
          final jalview.schemabinding.version2.Sequence vSequence)
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
          final jalview.schemabinding.version2.Sequence vSequence)
          throws java.lang.IndexOutOfBoundsException
  {
    this._sequenceList.add(index, vSequence);
  }

  /**
   * 
   * 
   * @param vSequenceSetProperties
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSequenceSetProperties(
          final jalview.schemabinding.version2.SequenceSetProperties vSequenceSetProperties)
          throws java.lang.IndexOutOfBoundsException
  {
    this._sequenceSetPropertiesList.addElement(vSequenceSetProperties);
  }

  /**
   * 
   * 
   * @param index
   * @param vSequenceSetProperties
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSequenceSetProperties(
          final int index,
          final jalview.schemabinding.version2.SequenceSetProperties vSequenceSetProperties)
          throws java.lang.IndexOutOfBoundsException
  {
    this._sequenceSetPropertiesList.add(index, vSequenceSetProperties);
  }

  /**
   * Method enumerateAlcodonFrame.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.AlcodonFrame
   *         elements
   */
  public java.util.Enumeration enumerateAlcodonFrame()
  {
    return this._alcodonFrameList.elements();
  }

  /**
   * Method enumerateAnnotation.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Annotation
   *         elements
   */
  public java.util.Enumeration enumerateAnnotation()
  {
    return this._annotationList.elements();
  }

  /**
   * Method enumerateSequence.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Sequence
   *         elements
   */
  public java.util.Enumeration enumerateSequence()
  {
    return this._sequenceList.elements();
  }

  /**
   * Method enumerateSequenceSetProperties.
   * 
   * @return an Enumeration over all
   *         jalview.schemabinding.version2.SequenceSetProperties elements
   */
  public java.util.Enumeration enumerateSequenceSetProperties()
  {
    return this._sequenceSetPropertiesList.elements();
  }

  /**
   * Method getAlcodonFrame.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.AlcodonFrame at the
   *         given inde
   */
  public jalview.schemabinding.version2.AlcodonFrame getAlcodonFrame(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alcodonFrameList.size())
    {
      throw new IndexOutOfBoundsException("getAlcodonFrame: Index value '"
              + index + "' not in range [0.."
              + (this._alcodonFrameList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.AlcodonFrame) _alcodonFrameList
            .get(index);
  }

  /**
   * Method getAlcodonFrame.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.AlcodonFrame[] getAlcodonFrame()
  {
    jalview.schemabinding.version2.AlcodonFrame[] array = new jalview.schemabinding.version2.AlcodonFrame[0];
    return (jalview.schemabinding.version2.AlcodonFrame[]) this._alcodonFrameList
            .toArray(array);
  }

  /**
   * Method getAlcodonFrameCount.
   * 
   * @return the size of this collection
   */
  public int getAlcodonFrameCount()
  {
    return this._alcodonFrameList.size();
  }

  /**
   * Method getAnnotation.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.Annotation at the
   *         given index
   */
  public jalview.schemabinding.version2.Annotation getAnnotation(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._annotationList.size())
    {
      throw new IndexOutOfBoundsException("getAnnotation: Index value '"
              + index + "' not in range [0.."
              + (this._annotationList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Annotation) _annotationList
            .get(index);
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
  public jalview.schemabinding.version2.Annotation[] getAnnotation()
  {
    jalview.schemabinding.version2.Annotation[] array = new jalview.schemabinding.version2.Annotation[0];
    return (jalview.schemabinding.version2.Annotation[]) this._annotationList
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
   * Returns the value of field 'datasetId'. The field 'datasetId' has the
   * following description: reference to set where jalview will gather the
   * dataset sequences for all sequences in the set.
   * 
   * 
   * @return the value of field 'DatasetId'.
   */
  public java.lang.String getDatasetId()
  {
    return this._datasetId;
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
   * @return the value of the jalview.schemabinding.version2.Sequence at the
   *         given index
   */
  public jalview.schemabinding.version2.Sequence getSequence(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceList.size())
    {
      throw new IndexOutOfBoundsException("getSequence: Index value '"
              + index + "' not in range [0.."
              + (this._sequenceList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Sequence) _sequenceList
            .get(index);
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
  public jalview.schemabinding.version2.Sequence[] getSequence()
  {
    jalview.schemabinding.version2.Sequence[] array = new jalview.schemabinding.version2.Sequence[0];
    return (jalview.schemabinding.version2.Sequence[]) this._sequenceList
            .toArray(array);
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
   * Method getSequenceSetProperties.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the
   *         jalview.schemabinding.version2.SequenceSetProperties at the given
   *         index
   */
  public jalview.schemabinding.version2.SequenceSetProperties getSequenceSetProperties(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceSetPropertiesList.size())
    {
      throw new IndexOutOfBoundsException(
              "getSequenceSetProperties: Index value '" + index
                      + "' not in range [0.."
                      + (this._sequenceSetPropertiesList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.SequenceSetProperties) _sequenceSetPropertiesList
            .get(index);
  }

  /**
   * Method getSequenceSetProperties.Returns the contents of the collection in
   * an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.SequenceSetProperties[] getSequenceSetProperties()
  {
    jalview.schemabinding.version2.SequenceSetProperties[] array = new jalview.schemabinding.version2.SequenceSetProperties[0];
    return (jalview.schemabinding.version2.SequenceSetProperties[]) this._sequenceSetPropertiesList
            .toArray(array);
  }

  /**
   * Method getSequenceSetPropertiesCount.
   * 
   * @return the size of this collection
   */
  public int getSequenceSetPropertiesCount()
  {
    return this._sequenceSetPropertiesList.size();
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
   * Method removeAlcodonFrame.
   * 
   * @param vAlcodonFrame
   * @return true if the object was removed from the collection.
   */
  public boolean removeAlcodonFrame(
          final jalview.schemabinding.version2.AlcodonFrame vAlcodonFrame)
  {
    boolean removed = _alcodonFrameList.remove(vAlcodonFrame);
    return removed;
  }

  /**
   * Method removeAlcodonFrameAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.AlcodonFrame removeAlcodonFrameAt(
          final int index)
  {
    java.lang.Object obj = this._alcodonFrameList.remove(index);
    return (jalview.schemabinding.version2.AlcodonFrame) obj;
  }

  /**
     */
  public void removeAllAlcodonFrame()
  {
    this._alcodonFrameList.clear();
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
     */
  public void removeAllSequenceSetProperties()
  {
    this._sequenceSetPropertiesList.clear();
  }

  /**
   * Method removeAnnotation.
   * 
   * @param vAnnotation
   * @return true if the object was removed from the collection.
   */
  public boolean removeAnnotation(
          final jalview.schemabinding.version2.Annotation vAnnotation)
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
  public jalview.schemabinding.version2.Annotation removeAnnotationAt(
          final int index)
  {
    java.lang.Object obj = this._annotationList.remove(index);
    return (jalview.schemabinding.version2.Annotation) obj;
  }

  /**
   * Method removeSequence.
   * 
   * @param vSequence
   * @return true if the object was removed from the collection.
   */
  public boolean removeSequence(
          final jalview.schemabinding.version2.Sequence vSequence)
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
  public jalview.schemabinding.version2.Sequence removeSequenceAt(
          final int index)
  {
    java.lang.Object obj = this._sequenceList.remove(index);
    return (jalview.schemabinding.version2.Sequence) obj;
  }

  /**
   * Method removeSequenceSetProperties.
   * 
   * @param vSequenceSetProperties
   * @return true if the object was removed from the collection.
   */
  public boolean removeSequenceSetProperties(
          final jalview.schemabinding.version2.SequenceSetProperties vSequenceSetProperties)
  {
    boolean removed = _sequenceSetPropertiesList
            .remove(vSequenceSetProperties);
    return removed;
  }

  /**
   * Method removeSequenceSetPropertiesAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.SequenceSetProperties removeSequenceSetPropertiesAt(
          final int index)
  {
    java.lang.Object obj = this._sequenceSetPropertiesList.remove(index);
    return (jalview.schemabinding.version2.SequenceSetProperties) obj;
  }

  /**
   * 
   * 
   * @param index
   * @param vAlcodonFrame
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setAlcodonFrame(final int index,
          final jalview.schemabinding.version2.AlcodonFrame vAlcodonFrame)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alcodonFrameList.size())
    {
      throw new IndexOutOfBoundsException("setAlcodonFrame: Index value '"
              + index + "' not in range [0.."
              + (this._alcodonFrameList.size() - 1) + "]");
    }

    this._alcodonFrameList.set(index, vAlcodonFrame);
  }

  /**
   * 
   * 
   * @param vAlcodonFrameArray
   */
  public void setAlcodonFrame(
          final jalview.schemabinding.version2.AlcodonFrame[] vAlcodonFrameArray)
  {
    // -- copy array
    _alcodonFrameList.clear();

    for (int i = 0; i < vAlcodonFrameArray.length; i++)
    {
      this._alcodonFrameList.add(vAlcodonFrameArray[i]);
    }
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
          final jalview.schemabinding.version2.Annotation vAnnotation)
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
          final jalview.schemabinding.version2.Annotation[] vAnnotationArray)
  {
    // -- copy array
    _annotationList.clear();

    for (int i = 0; i < vAnnotationArray.length; i++)
    {
      this._annotationList.add(vAnnotationArray[i]);
    }
  }

  /**
   * Sets the value of field 'datasetId'. The field 'datasetId' has the
   * following description: reference to set where jalview will gather the
   * dataset sequences for all sequences in the set.
   * 
   * 
   * @param datasetId
   *          the value of field 'datasetId'.
   */
  public void setDatasetId(final java.lang.String datasetId)
  {
    this._datasetId = datasetId;
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
          final jalview.schemabinding.version2.Sequence vSequence)
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
  public void setSequence(
          final jalview.schemabinding.version2.Sequence[] vSequenceArray)
  {
    // -- copy array
    _sequenceList.clear();

    for (int i = 0; i < vSequenceArray.length; i++)
    {
      this._sequenceList.add(vSequenceArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vSequenceSetProperties
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setSequenceSetProperties(
          final int index,
          final jalview.schemabinding.version2.SequenceSetProperties vSequenceSetProperties)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceSetPropertiesList.size())
    {
      throw new IndexOutOfBoundsException(
              "setSequenceSetProperties: Index value '" + index
                      + "' not in range [0.."
                      + (this._sequenceSetPropertiesList.size() - 1) + "]");
    }

    this._sequenceSetPropertiesList.set(index, vSequenceSetProperties);
  }

  /**
   * 
   * 
   * @param vSequenceSetPropertiesArray
   */
  public void setSequenceSetProperties(
          final jalview.schemabinding.version2.SequenceSetProperties[] vSequenceSetPropertiesArray)
  {
    // -- copy array
    _sequenceSetPropertiesList.clear();

    for (int i = 0; i < vSequenceSetPropertiesArray.length; i++)
    {
      this._sequenceSetPropertiesList.add(vSequenceSetPropertiesArray[i]);
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
   * @return the unmarshaled jalview.schemabinding.version2.SequenceSet
   */
  public static jalview.schemabinding.version2.SequenceSet unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.SequenceSet) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.SequenceSet.class,
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
