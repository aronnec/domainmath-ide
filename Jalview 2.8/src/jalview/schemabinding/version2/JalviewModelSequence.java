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
 * Class JalviewModelSequence.
 * 
 * @version $Revision$ $Date$
 */
public class JalviewModelSequence implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _JSeqList.
   */
  private java.util.Vector _JSeqList;

  /**
   * Field _JGroupList.
   */
  private java.util.Vector _JGroupList;

  /**
   * Field _viewportList.
   */
  private java.util.Vector _viewportList;

  /**
   * Field _userColoursList.
   */
  private java.util.Vector _userColoursList;

  /**
   * Field _treeList.
   */
  private java.util.Vector _treeList;

  /**
   * Field _featureSettings.
   */
  private jalview.schemabinding.version2.FeatureSettings _featureSettings;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public JalviewModelSequence()
  {
    super();
    this._JSeqList = new java.util.Vector();
    this._JGroupList = new java.util.Vector();
    this._viewportList = new java.util.Vector();
    this._userColoursList = new java.util.Vector();
    this._treeList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vJGroup
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addJGroup(final jalview.schemabinding.version2.JGroup vJGroup)
          throws java.lang.IndexOutOfBoundsException
  {
    this._JGroupList.addElement(vJGroup);
  }

  /**
   * 
   * 
   * @param index
   * @param vJGroup
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addJGroup(final int index,
          final jalview.schemabinding.version2.JGroup vJGroup)
          throws java.lang.IndexOutOfBoundsException
  {
    this._JGroupList.add(index, vJGroup);
  }

  /**
   * 
   * 
   * @param vJSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addJSeq(final jalview.schemabinding.version2.JSeq vJSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    this._JSeqList.addElement(vJSeq);
  }

  /**
   * 
   * 
   * @param index
   * @param vJSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addJSeq(final int index,
          final jalview.schemabinding.version2.JSeq vJSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    this._JSeqList.add(index, vJSeq);
  }

  /**
   * 
   * 
   * @param vTree
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addTree(final jalview.schemabinding.version2.Tree vTree)
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
  public void addTree(final int index,
          final jalview.schemabinding.version2.Tree vTree)
          throws java.lang.IndexOutOfBoundsException
  {
    this._treeList.add(index, vTree);
  }

  /**
   * 
   * 
   * @param vUserColours
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addUserColours(
          final jalview.schemabinding.version2.UserColours vUserColours)
          throws java.lang.IndexOutOfBoundsException
  {
    this._userColoursList.addElement(vUserColours);
  }

  /**
   * 
   * 
   * @param index
   * @param vUserColours
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addUserColours(final int index,
          final jalview.schemabinding.version2.UserColours vUserColours)
          throws java.lang.IndexOutOfBoundsException
  {
    this._userColoursList.add(index, vUserColours);
  }

  /**
   * 
   * 
   * @param vViewport
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addViewport(
          final jalview.schemabinding.version2.Viewport vViewport)
          throws java.lang.IndexOutOfBoundsException
  {
    this._viewportList.addElement(vViewport);
  }

  /**
   * 
   * 
   * @param index
   * @param vViewport
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addViewport(final int index,
          final jalview.schemabinding.version2.Viewport vViewport)
          throws java.lang.IndexOutOfBoundsException
  {
    this._viewportList.add(index, vViewport);
  }

  /**
   * Method enumerateJGroup.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.JGroup
   *         elements
   */
  public java.util.Enumeration enumerateJGroup()
  {
    return this._JGroupList.elements();
  }

  /**
   * Method enumerateJSeq.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.JSeq
   *         elements
   */
  public java.util.Enumeration enumerateJSeq()
  {
    return this._JSeqList.elements();
  }

  /**
   * Method enumerateTree.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Tree
   *         elements
   */
  public java.util.Enumeration enumerateTree()
  {
    return this._treeList.elements();
  }

  /**
   * Method enumerateUserColours.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.UserColours
   *         elements
   */
  public java.util.Enumeration enumerateUserColours()
  {
    return this._userColoursList.elements();
  }

  /**
   * Method enumerateViewport.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Viewport
   *         elements
   */
  public java.util.Enumeration enumerateViewport()
  {
    return this._viewportList.elements();
  }

  /**
   * Returns the value of field 'featureSettings'.
   * 
   * @return the value of field 'FeatureSettings'.
   */
  public jalview.schemabinding.version2.FeatureSettings getFeatureSettings()
  {
    return this._featureSettings;
  }

  /**
   * Method getJGroup.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.JGroup at the given
   *         index
   */
  public jalview.schemabinding.version2.JGroup getJGroup(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._JGroupList.size())
    {
      throw new IndexOutOfBoundsException("getJGroup: Index value '"
              + index + "' not in range [0.."
              + (this._JGroupList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.JGroup) _JGroupList.get(index);
  }

  /**
   * Method getJGroup.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.JGroup[] getJGroup()
  {
    jalview.schemabinding.version2.JGroup[] array = new jalview.schemabinding.version2.JGroup[0];
    return (jalview.schemabinding.version2.JGroup[]) this._JGroupList
            .toArray(array);
  }

  /**
   * Method getJGroupCount.
   * 
   * @return the size of this collection
   */
  public int getJGroupCount()
  {
    return this._JGroupList.size();
  }

  /**
   * Method getJSeq.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.JSeq at the given
   *         index
   */
  public jalview.schemabinding.version2.JSeq getJSeq(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._JSeqList.size())
    {
      throw new IndexOutOfBoundsException("getJSeq: Index value '" + index
              + "' not in range [0.." + (this._JSeqList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.JSeq) _JSeqList.get(index);
  }

  /**
   * Method getJSeq.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.JSeq[] getJSeq()
  {
    jalview.schemabinding.version2.JSeq[] array = new jalview.schemabinding.version2.JSeq[0];
    return (jalview.schemabinding.version2.JSeq[]) this._JSeqList
            .toArray(array);
  }

  /**
   * Method getJSeqCount.
   * 
   * @return the size of this collection
   */
  public int getJSeqCount()
  {
    return this._JSeqList.size();
  }

  /**
   * Method getTree.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.Tree at the given
   *         index
   */
  public jalview.schemabinding.version2.Tree getTree(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._treeList.size())
    {
      throw new IndexOutOfBoundsException("getTree: Index value '" + index
              + "' not in range [0.." + (this._treeList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Tree) _treeList.get(index);
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
  public jalview.schemabinding.version2.Tree[] getTree()
  {
    jalview.schemabinding.version2.Tree[] array = new jalview.schemabinding.version2.Tree[0];
    return (jalview.schemabinding.version2.Tree[]) this._treeList
            .toArray(array);
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
   * Method getUserColours.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.UserColours at the
   *         given index
   */
  public jalview.schemabinding.version2.UserColours getUserColours(
          final int index) throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._userColoursList.size())
    {
      throw new IndexOutOfBoundsException("getUserColours: Index value '"
              + index + "' not in range [0.."
              + (this._userColoursList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.UserColours) _userColoursList
            .get(index);
  }

  /**
   * Method getUserColours.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.UserColours[] getUserColours()
  {
    jalview.schemabinding.version2.UserColours[] array = new jalview.schemabinding.version2.UserColours[0];
    return (jalview.schemabinding.version2.UserColours[]) this._userColoursList
            .toArray(array);
  }

  /**
   * Method getUserColoursCount.
   * 
   * @return the size of this collection
   */
  public int getUserColoursCount()
  {
    return this._userColoursList.size();
  }

  /**
   * Method getViewport.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.Viewport at the
   *         given index
   */
  public jalview.schemabinding.version2.Viewport getViewport(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._viewportList.size())
    {
      throw new IndexOutOfBoundsException("getViewport: Index value '"
              + index + "' not in range [0.."
              + (this._viewportList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Viewport) _viewportList
            .get(index);
  }

  /**
   * Method getViewport.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.Viewport[] getViewport()
  {
    jalview.schemabinding.version2.Viewport[] array = new jalview.schemabinding.version2.Viewport[0];
    return (jalview.schemabinding.version2.Viewport[]) this._viewportList
            .toArray(array);
  }

  /**
   * Method getViewportCount.
   * 
   * @return the size of this collection
   */
  public int getViewportCount()
  {
    return this._viewportList.size();
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
  public void removeAllJGroup()
  {
    this._JGroupList.clear();
  }

  /**
     */
  public void removeAllJSeq()
  {
    this._JSeqList.clear();
  }

  /**
     */
  public void removeAllTree()
  {
    this._treeList.clear();
  }

  /**
     */
  public void removeAllUserColours()
  {
    this._userColoursList.clear();
  }

  /**
     */
  public void removeAllViewport()
  {
    this._viewportList.clear();
  }

  /**
   * Method removeJGroup.
   * 
   * @param vJGroup
   * @return true if the object was removed from the collection.
   */
  public boolean removeJGroup(
          final jalview.schemabinding.version2.JGroup vJGroup)
  {
    boolean removed = _JGroupList.remove(vJGroup);
    return removed;
  }

  /**
   * Method removeJGroupAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.JGroup removeJGroupAt(
          final int index)
  {
    java.lang.Object obj = this._JGroupList.remove(index);
    return (jalview.schemabinding.version2.JGroup) obj;
  }

  /**
   * Method removeJSeq.
   * 
   * @param vJSeq
   * @return true if the object was removed from the collection.
   */
  public boolean removeJSeq(final jalview.schemabinding.version2.JSeq vJSeq)
  {
    boolean removed = _JSeqList.remove(vJSeq);
    return removed;
  }

  /**
   * Method removeJSeqAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.JSeq removeJSeqAt(final int index)
  {
    java.lang.Object obj = this._JSeqList.remove(index);
    return (jalview.schemabinding.version2.JSeq) obj;
  }

  /**
   * Method removeTree.
   * 
   * @param vTree
   * @return true if the object was removed from the collection.
   */
  public boolean removeTree(final jalview.schemabinding.version2.Tree vTree)
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
  public jalview.schemabinding.version2.Tree removeTreeAt(final int index)
  {
    java.lang.Object obj = this._treeList.remove(index);
    return (jalview.schemabinding.version2.Tree) obj;
  }

  /**
   * Method removeUserColours.
   * 
   * @param vUserColours
   * @return true if the object was removed from the collection.
   */
  public boolean removeUserColours(
          final jalview.schemabinding.version2.UserColours vUserColours)
  {
    boolean removed = _userColoursList.remove(vUserColours);
    return removed;
  }

  /**
   * Method removeUserColoursAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.UserColours removeUserColoursAt(
          final int index)
  {
    java.lang.Object obj = this._userColoursList.remove(index);
    return (jalview.schemabinding.version2.UserColours) obj;
  }

  /**
   * Method removeViewport.
   * 
   * @param vViewport
   * @return true if the object was removed from the collection.
   */
  public boolean removeViewport(
          final jalview.schemabinding.version2.Viewport vViewport)
  {
    boolean removed = _viewportList.remove(vViewport);
    return removed;
  }

  /**
   * Method removeViewportAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.Viewport removeViewportAt(
          final int index)
  {
    java.lang.Object obj = this._viewportList.remove(index);
    return (jalview.schemabinding.version2.Viewport) obj;
  }

  /**
   * Sets the value of field 'featureSettings'.
   * 
   * @param featureSettings
   *          the value of field 'featureSettings'.
   */
  public void setFeatureSettings(
          final jalview.schemabinding.version2.FeatureSettings featureSettings)
  {
    this._featureSettings = featureSettings;
  }

  /**
   * 
   * 
   * @param index
   * @param vJGroup
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setJGroup(final int index,
          final jalview.schemabinding.version2.JGroup vJGroup)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._JGroupList.size())
    {
      throw new IndexOutOfBoundsException("setJGroup: Index value '"
              + index + "' not in range [0.."
              + (this._JGroupList.size() - 1) + "]");
    }

    this._JGroupList.set(index, vJGroup);
  }

  /**
   * 
   * 
   * @param vJGroupArray
   */
  public void setJGroup(
          final jalview.schemabinding.version2.JGroup[] vJGroupArray)
  {
    // -- copy array
    _JGroupList.clear();

    for (int i = 0; i < vJGroupArray.length; i++)
    {
      this._JGroupList.add(vJGroupArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vJSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setJSeq(final int index,
          final jalview.schemabinding.version2.JSeq vJSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._JSeqList.size())
    {
      throw new IndexOutOfBoundsException("setJSeq: Index value '" + index
              + "' not in range [0.." + (this._JSeqList.size() - 1) + "]");
    }

    this._JSeqList.set(index, vJSeq);
  }

  /**
   * 
   * 
   * @param vJSeqArray
   */
  public void setJSeq(final jalview.schemabinding.version2.JSeq[] vJSeqArray)
  {
    // -- copy array
    _JSeqList.clear();

    for (int i = 0; i < vJSeqArray.length; i++)
    {
      this._JSeqList.add(vJSeqArray[i]);
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
  public void setTree(final int index,
          final jalview.schemabinding.version2.Tree vTree)
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
  public void setTree(final jalview.schemabinding.version2.Tree[] vTreeArray)
  {
    // -- copy array
    _treeList.clear();

    for (int i = 0; i < vTreeArray.length; i++)
    {
      this._treeList.add(vTreeArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vUserColours
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setUserColours(final int index,
          final jalview.schemabinding.version2.UserColours vUserColours)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._userColoursList.size())
    {
      throw new IndexOutOfBoundsException("setUserColours: Index value '"
              + index + "' not in range [0.."
              + (this._userColoursList.size() - 1) + "]");
    }

    this._userColoursList.set(index, vUserColours);
  }

  /**
   * 
   * 
   * @param vUserColoursArray
   */
  public void setUserColours(
          final jalview.schemabinding.version2.UserColours[] vUserColoursArray)
  {
    // -- copy array
    _userColoursList.clear();

    for (int i = 0; i < vUserColoursArray.length; i++)
    {
      this._userColoursList.add(vUserColoursArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vViewport
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setViewport(final int index,
          final jalview.schemabinding.version2.Viewport vViewport)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._viewportList.size())
    {
      throw new IndexOutOfBoundsException("setViewport: Index value '"
              + index + "' not in range [0.."
              + (this._viewportList.size() - 1) + "]");
    }

    this._viewportList.set(index, vViewport);
  }

  /**
   * 
   * 
   * @param vViewportArray
   */
  public void setViewport(
          final jalview.schemabinding.version2.Viewport[] vViewportArray)
  {
    // -- copy array
    _viewportList.clear();

    for (int i = 0; i < vViewportArray.length; i++)
    {
      this._viewportList.add(vViewportArray[i]);
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
   * @return the unmarshaled jalview.schemabinding.version2.JalviewModelSequence
   */
  public static jalview.schemabinding.version2.JalviewModelSequence unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.JalviewModelSequence) Unmarshaller
            .unmarshal(
                    jalview.schemabinding.version2.JalviewModelSequence.class,
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
