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
   * Field _alignmentList.
   */
  private java.util.Vector _alignmentList;

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
    this._alignmentList = new java.util.Vector();
    this._treeList = new java.util.Vector();
    this._sequenceSetList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vAlignment
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlignment(final Alignment vAlignment)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alignmentList.addElement(vAlignment);
  }

  /**
   * 
   * 
   * @param index
   * @param vAlignment
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addAlignment(final int index, final Alignment vAlignment)
          throws java.lang.IndexOutOfBoundsException
  {
    this._alignmentList.add(index, vAlignment);
  }

  /**
   * 
   * 
   * @param vSequenceSet
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSequenceSet(final SequenceSet vSequenceSet)
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
  public void addSequenceSet(final int index, final SequenceSet vSequenceSet)
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
   * Method enumerateAlignment.
   * 
   * @return an Enumeration over all Alignment elements
   */
  public java.util.Enumeration enumerateAlignment()
  {
    return this._alignmentList.elements();
  }

  /**
   * Method enumerateSequenceSet.
   * 
   * @return an Enumeration over all SequenceSet elements
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
   * Method getAlignment.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the Alignment at the given index
   */
  public Alignment getAlignment(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alignmentList.size())
    {
      throw new IndexOutOfBoundsException("getAlignment: Index value '"
              + index + "' not in range [0.."
              + (this._alignmentList.size() - 1) + "]");
    }

    return (Alignment) _alignmentList.get(index);
  }

  /**
   * Method getAlignment.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public Alignment[] getAlignment()
  {
    Alignment[] array = new Alignment[0];
    return (Alignment[]) this._alignmentList.toArray(array);
  }

  /**
   * Method getAlignmentCount.
   * 
   * @return the size of this collection
   */
  public int getAlignmentCount()
  {
    return this._alignmentList.size();
  }

  /**
   * Method getSequenceSet.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the SequenceSet at the given index
   */
  public SequenceSet getSequenceSet(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._sequenceSetList.size())
    {
      throw new IndexOutOfBoundsException("getSequenceSet: Index value '"
              + index + "' not in range [0.."
              + (this._sequenceSetList.size() - 1) + "]");
    }

    return (SequenceSet) _sequenceSetList.get(index);
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
  public SequenceSet[] getSequenceSet()
  {
    SequenceSet[] array = new SequenceSet[0];
    return (SequenceSet[]) this._sequenceSetList.toArray(array);
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
   * Method removeAlignment.
   * 
   * @param vAlignment
   * @return true if the object was removed from the collection.
   */
  public boolean removeAlignment(final Alignment vAlignment)
  {
    boolean removed = _alignmentList.remove(vAlignment);
    return removed;
  }

  /**
   * Method removeAlignmentAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public Alignment removeAlignmentAt(final int index)
  {
    java.lang.Object obj = this._alignmentList.remove(index);
    return (Alignment) obj;
  }

  /**
     */
  public void removeAllAlignment()
  {
    this._alignmentList.clear();
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
  public boolean removeSequenceSet(final SequenceSet vSequenceSet)
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
  public SequenceSet removeSequenceSetAt(final int index)
  {
    java.lang.Object obj = this._sequenceSetList.remove(index);
    return (SequenceSet) obj;
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
   * @param vAlignment
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setAlignment(final int index, final Alignment vAlignment)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._alignmentList.size())
    {
      throw new IndexOutOfBoundsException("setAlignment: Index value '"
              + index + "' not in range [0.."
              + (this._alignmentList.size() - 1) + "]");
    }

    this._alignmentList.set(index, vAlignment);
  }

  /**
   * 
   * 
   * @param vAlignmentArray
   */
  public void setAlignment(final Alignment[] vAlignmentArray)
  {
    // -- copy array
    _alignmentList.clear();

    for (int i = 0; i < vAlignmentArray.length; i++)
    {
      this._alignmentList.add(vAlignmentArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vSequenceSet
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setSequenceSet(final int index, final SequenceSet vSequenceSet)
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
  public void setSequenceSet(final SequenceSet[] vSequenceSetArray)
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
   * @return the unmarshaled jalview.binding.VAMSAS
   */
  public static jalview.binding.VAMSAS unmarshal(final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.binding.VAMSAS) Unmarshaller.unmarshal(
            jalview.binding.VAMSAS.class, reader);
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
