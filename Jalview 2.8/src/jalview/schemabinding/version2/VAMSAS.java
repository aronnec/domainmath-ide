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
 * Class VAMSAS.
 * 
 * @version $Revision$ $Date$
 */
public class VAMSAS implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _treeList.
   */
  private java.util.Vector _treeList;

  /**
   * Field _sequenceSetList.
   */
  private java.util.Vector _sequenceSetList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public VAMSAS()
  {
    super();
    this._treeList = new java.util.Vector();
    this._sequenceSetList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vSequenceSet
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSequenceSet(
          final jalview.schemabinding.version2.SequenceSet vSequenceSet)
          throws java.lang.IndexOutOfBoundsException
  {
    this._sequenceSetList.addElement(vSequenceSet);
  }

  /**
   * 
   * 
   * @param index
   * @param vSequenceSet
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSequenceSet(final int index,
          final jalview.schemabinding.version2.SequenceSet vSequenceSet)
          throws java.lang.IndexOutOfBoundsException
  {
    this._sequenceSetList.add(index, vSequenceSet);
  }

  /**
   * 
   * 
   * @param vTree
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addTree(final java.lang.String vTree)
          throws java.lang.IndexOutOfBoundsException
  {
    this._treeList.addElement(vTree);
  }

  /**
   * 
   * 
   * @param index
   * @param vTree
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addTree(final int index, final java.lang.String vTree)
          throws java.lang.IndexOutOfBoundsException
  {
    this._treeList.add(index, vTree);
  }

  /**
   * Method enumerateSequenceSet.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.SequenceSet
   *         elements
   */
  public java.util.Enumeration enumerateSequenceSet()
  {
    return this._sequenceSetList.elements();
  }

  /**
   * Method enumerateTree.
   * 
   * @return an Enumeration over all java.lang.String elements
   */
  public java.util.Enumeration enumerateTree()
  {
    return this._treeList.elements();
  }

  /**
   * Method getSequenceSet.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.SequenceSet at the
   *         given index
   */
  public jalview.schemabinding.version2.SequenceSet getSequenceSet(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceSetList.size())
    {
      throw new IndexOutOfBoundsException("getSequenceSet: Index value '"
              + index + "' not in range [0.."
              + (this._sequenceSetList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.SequenceSet) _sequenceSetList
            .get(index);
  }

  /**
   * Method getSequenceSet.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.SequenceSet[] getSequenceSet()
  {
    jalview.schemabinding.version2.SequenceSet[] array = new jalview.schemabinding.version2.SequenceSet[0];
    return (jalview.schemabinding.version2.SequenceSet[]) this._sequenceSetList
            .toArray(array);
  }

  /**
   * Method getSequenceSetCount.
   * 
   * @return the size of this collection
   */
  public int getSequenceSetCount()
  {
    return this._sequenceSetList.size();
  }

  /**
   * Method getTree.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the java.lang.String at the given index
   */
  public java.lang.String getTree(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._treeList.size())
    {
      throw new IndexOutOfBoundsException("getTree: Index value '" + index
              + "' not in range [0.." + (this._treeList.size() - 1) + "]");
    }

    return (java.lang.String) _treeList.get(index);
  }

  /**
   * Method getTree.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public java.lang.String[] getTree()
  {
    java.lang.String[] array = new java.lang.String[0];
    return (java.lang.String[]) this._treeList.toArray(array);
  }

  /**
   * Method getTreeCount.
   * 
   * @return the size of this collection
   */
  public int getTreeCount()
  {
    return this._treeList.size();
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
  public void removeAllSequenceSet()
  {
    this._sequenceSetList.clear();
  }

  /**
     */
  public void removeAllTree()
  {
    this._treeList.clear();
  }

  /**
   * Method removeSequenceSet.
   * 
   * @param vSequenceSet
   * @return true if the object was removed from the collection.
   */
  public boolean removeSequenceSet(
          final jalview.schemabinding.version2.SequenceSet vSequenceSet)
  {
    boolean removed = _sequenceSetList.remove(vSequenceSet);
    return removed;
  }

  /**
   * Method removeSequenceSetAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.SequenceSet removeSequenceSetAt(
          final int index)
  {
    java.lang.Object obj = this._sequenceSetList.remove(index);
    return (jalview.schemabinding.version2.SequenceSet) obj;
  }

  /**
   * Method removeTree.
   * 
   * @param vTree
   * @return true if the object was removed from the collection.
   */
  public boolean removeTree(final java.lang.String vTree)
  {
    boolean removed = _treeList.remove(vTree);
    return removed;
  }

  /**
   * Method removeTreeAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public java.lang.String removeTreeAt(final int index)
  {
    java.lang.Object obj = this._treeList.remove(index);
    return (java.lang.String) obj;
  }

  /**
   * 
   * 
   * @param index
   * @param vSequenceSet
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setSequenceSet(final int index,
          final jalview.schemabinding.version2.SequenceSet vSequenceSet)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceSetList.size())
    {
      throw new IndexOutOfBoundsException("setSequenceSet: Index value '"
              + index + "' not in range [0.."
              + (this._sequenceSetList.size() - 1) + "]");
    }

    this._sequenceSetList.set(index, vSequenceSet);
  }

  /**
   * 
   * 
   * @param vSequenceSetArray
   */
  public void setSequenceSet(
          final jalview.schemabinding.version2.SequenceSet[] vSequenceSetArray)
  {
    // -- copy array
    _sequenceSetList.clear();

    for (int i = 0; i < vSequenceSetArray.length; i++)
    {
      this._sequenceSetList.add(vSequenceSetArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vTree
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setTree(final int index, final java.lang.String vTree)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._treeList.size())
    {
      throw new IndexOutOfBoundsException("setTree: Index value '" + index
              + "' not in range [0.." + (this._treeList.size() - 1) + "]");
    }

    this._treeList.set(index, vTree);
  }

  /**
   * 
   * 
   * @param vTreeArray
   */
  public void setTree(final java.lang.String[] vTreeArray)
  {
    // -- copy array
    _treeList.clear();

    for (int i = 0; i < vTreeArray.length; i++)
    {
      this._treeList.add(vTreeArray[i]);
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
   * @return the unmarshaled jalview.schemabinding.version2.VAMSAS
   */
  public static jalview.schemabinding.version2.VAMSAS unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.VAMSAS) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.VAMSAS.class, reader);
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
