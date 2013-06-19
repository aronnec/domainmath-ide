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
 * Class Annotation.
 * 
 * @version $Revision$ $Date$
 */
public class Annotation implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _graph.
   */
  private boolean _graph;

  /**
   * keeps track of state for field: _graph
   */
  private boolean _has_graph;

  /**
   * Field _graphType.
   */
  private int _graphType;

  /**
   * keeps track of state for field: _graphType
   */
  private boolean _has_graphType;

  /**
   * Field _annotationElementList.
   */
  private java.util.Vector _annotationElementList;

  /**
   * Field _label.
   */
  private java.lang.String _label;

  /**
   * Field _description.
   */
  private java.lang.String _description;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Annotation()
  {
    super();
    this._annotationElementList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vAnnotationElement
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAnnotationElement(
          final jalview.binding.AnnotationElement vAnnotationElement)
          throws java.lang.IndexOutOfBoundsException
  {
    this._annotationElementList.addElement(vAnnotationElement);
  }

  /**
   * 
   * 
   * @param index
   * @param vAnnotationElement
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAnnotationElement(final int index,
          final jalview.binding.AnnotationElement vAnnotationElement)
          throws java.lang.IndexOutOfBoundsException
  {
    this._annotationElementList.add(index, vAnnotationElement);
  }

  /**
     */
  public void deleteGraph()
  {
    this._has_graph = false;
  }

  /**
     */
  public void deleteGraphType()
  {
    this._has_graphType = false;
  }

  /**
   * Method enumerateAnnotationElement.
   * 
   * @return an Enumeration over all jalview.binding.AnnotationElement elements
   */
  public java.util.Enumeration enumerateAnnotationElement()
  {
    return this._annotationElementList.elements();
  }

  /**
   * Method getAnnotationElement.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.binding.AnnotationElement at the given
   *         index
   */
  public jalview.binding.AnnotationElement getAnnotationElement(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._annotationElementList.size())
    {
      throw new IndexOutOfBoundsException(
              "getAnnotationElement: Index value '" + index
                      + "' not in range [0.."
                      + (this._annotationElementList.size() - 1) + "]");
    }

    return (jalview.binding.AnnotationElement) _annotationElementList
            .get(index);
  }

  /**
   * Method getAnnotationElement.Returns the contents of the collection in an
   * Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.binding.AnnotationElement[] getAnnotationElement()
  {
    jalview.binding.AnnotationElement[] array = new jalview.binding.AnnotationElement[0];
    return (jalview.binding.AnnotationElement[]) this._annotationElementList
            .toArray(array);
  }

  /**
   * Method getAnnotationElementCount.
   * 
   * @return the size of this collection
   */
  public int getAnnotationElementCount()
  {
    return this._annotationElementList.size();
  }

  /**
   * Returns the value of field 'description'.
   * 
   * @return the value of field 'Description'.
   */
  public java.lang.String getDescription()
  {
    return this._description;
  }

  /**
   * Returns the value of field 'graph'.
   * 
   * @return the value of field 'Graph'.
   */
  public boolean getGraph()
  {
    return this._graph;
  }

  /**
   * Returns the value of field 'graphType'.
   * 
   * @return the value of field 'GraphType'.
   */
  public int getGraphType()
  {
    return this._graphType;
  }

  /**
   * Returns the value of field 'label'.
   * 
   * @return the value of field 'Label'.
   */
  public java.lang.String getLabel()
  {
    return this._label;
  }

  /**
   * Method hasGraph.
   * 
   * @return true if at least one Graph has been added
   */
  public boolean hasGraph()
  {
    return this._has_graph;
  }

  /**
   * Method hasGraphType.
   * 
   * @return true if at least one GraphType has been added
   */
  public boolean hasGraphType()
  {
    return this._has_graphType;
  }

  /**
   * Returns the value of field 'graph'.
   * 
   * @return the value of field 'Graph'.
   */
  public boolean isGraph()
  {
    return this._graph;
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
  public void removeAllAnnotationElement()
  {
    this._annotationElementList.clear();
  }

  /**
   * Method removeAnnotationElement.
   * 
   * @param vAnnotationElement
   * @return true if the object was removed from the collection.
   */
  public boolean removeAnnotationElement(
          final jalview.binding.AnnotationElement vAnnotationElement)
  {
    boolean removed = _annotationElementList.remove(vAnnotationElement);
    return removed;
  }

  /**
   * Method removeAnnotationElementAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.binding.AnnotationElement removeAnnotationElementAt(
          final int index)
  {
    java.lang.Object obj = this._annotationElementList.remove(index);
    return (jalview.binding.AnnotationElement) obj;
  }

  /**
   * 
   * 
   * @param index
   * @param vAnnotationElement
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setAnnotationElement(final int index,
          final jalview.binding.AnnotationElement vAnnotationElement)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._annotationElementList.size())
    {
      throw new IndexOutOfBoundsException(
              "setAnnotationElement: Index value '" + index
                      + "' not in range [0.."
                      + (this._annotationElementList.size() - 1) + "]");
    }

    this._annotationElementList.set(index, vAnnotationElement);
  }

  /**
   * 
   * 
   * @param vAnnotationElementArray
   */
  public void setAnnotationElement(
          final jalview.binding.AnnotationElement[] vAnnotationElementArray)
  {
    // -- copy array
    _annotationElementList.clear();

    for (int i = 0; i < vAnnotationElementArray.length; i++)
    {
      this._annotationElementList.add(vAnnotationElementArray[i]);
    }
  }

  /**
   * Sets the value of field 'description'.
   * 
   * @param description
   *          the value of field 'description'.
   */
  public void setDescription(final java.lang.String description)
  {
    this._description = description;
  }

  /**
   * Sets the value of field 'graph'.
   * 
   * @param graph
   *          the value of field 'graph'.
   */
  public void setGraph(final boolean graph)
  {
    this._graph = graph;
    this._has_graph = true;
  }

  /**
   * Sets the value of field 'graphType'.
   * 
   * @param graphType
   *          the value of field 'graphType'.
   */
  public void setGraphType(final int graphType)
  {
    this._graphType = graphType;
    this._has_graphType = true;
  }

  /**
   * Sets the value of field 'label'.
   * 
   * @param label
   *          the value of field 'label'.
   */
  public void setLabel(final java.lang.String label)
  {
    this._label = label;
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
   * @return the unmarshaled jalview.binding.Annotation
   */
  public static jalview.binding.Annotation unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.Annotation) Unmarshaller.unmarshal(
            jalview.binding.Annotation.class, reader);
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
