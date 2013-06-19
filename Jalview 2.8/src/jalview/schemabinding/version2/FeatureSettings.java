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
 * Class FeatureSettings.
 * 
 * @version $Revision$ $Date$
 */
public class FeatureSettings implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _settingList.
   */
  private java.util.Vector _settingList;

  /**
   * Field _groupList.
   */
  private java.util.Vector _groupList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public FeatureSettings()
  {
    super();
    this._settingList = new java.util.Vector();
    this._groupList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addGroup(final jalview.schemabinding.version2.Group vGroup)
          throws java.lang.IndexOutOfBoundsException
  {
    this._groupList.addElement(vGroup);
  }

  /**
   * 
   * 
   * @param index
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addGroup(final int index,
          final jalview.schemabinding.version2.Group vGroup)
          throws java.lang.IndexOutOfBoundsException
  {
    this._groupList.add(index, vGroup);
  }

  /**
   * 
   * 
   * @param vSetting
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSetting(
          final jalview.schemabinding.version2.Setting vSetting)
          throws java.lang.IndexOutOfBoundsException
  {
    this._settingList.addElement(vSetting);
  }

  /**
   * 
   * 
   * @param index
   * @param vSetting
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSetting(final int index,
          final jalview.schemabinding.version2.Setting vSetting)
          throws java.lang.IndexOutOfBoundsException
  {
    this._settingList.add(index, vSetting);
  }

  /**
   * Method enumerateGroup.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Group
   *         elements
   */
  public java.util.Enumeration enumerateGroup()
  {
    return this._groupList.elements();
  }

  /**
   * Method enumerateSetting.
   * 
   * @return an Enumeration over all jalview.schemabinding.version2.Setting
   *         elements
   */
  public java.util.Enumeration enumerateSetting()
  {
    return this._settingList.elements();
  }

  /**
   * Method getGroup.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.Group at the given
   *         index
   */
  public jalview.schemabinding.version2.Group getGroup(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._groupList.size())
    {
      throw new IndexOutOfBoundsException("getGroup: Index value '" + index
              + "' not in range [0.." + (this._groupList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Group) _groupList.get(index);
  }

  /**
   * Method getGroup.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.Group[] getGroup()
  {
    jalview.schemabinding.version2.Group[] array = new jalview.schemabinding.version2.Group[0];
    return (jalview.schemabinding.version2.Group[]) this._groupList
            .toArray(array);
  }

  /**
   * Method getGroupCount.
   * 
   * @return the size of this collection
   */
  public int getGroupCount()
  {
    return this._groupList.size();
  }

  /**
   * Method getSetting.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the jalview.schemabinding.version2.Setting at the
   *         given index
   */
  public jalview.schemabinding.version2.Setting getSetting(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._settingList.size())
    {
      throw new IndexOutOfBoundsException("getSetting: Index value '"
              + index + "' not in range [0.."
              + (this._settingList.size() - 1) + "]");
    }

    return (jalview.schemabinding.version2.Setting) _settingList.get(index);
  }

  /**
   * Method getSetting.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public jalview.schemabinding.version2.Setting[] getSetting()
  {
    jalview.schemabinding.version2.Setting[] array = new jalview.schemabinding.version2.Setting[0];
    return (jalview.schemabinding.version2.Setting[]) this._settingList
            .toArray(array);
  }

  /**
   * Method getSettingCount.
   * 
   * @return the size of this collection
   */
  public int getSettingCount()
  {
    return this._settingList.size();
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
  public void removeAllGroup()
  {
    this._groupList.clear();
  }

  /**
     */
  public void removeAllSetting()
  {
    this._settingList.clear();
  }

  /**
   * Method removeGroup.
   * 
   * @param vGroup
   * @return true if the object was removed from the collection.
   */
  public boolean removeGroup(
          final jalview.schemabinding.version2.Group vGroup)
  {
    boolean removed = _groupList.remove(vGroup);
    return removed;
  }

  /**
   * Method removeGroupAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.Group removeGroupAt(final int index)
  {
    java.lang.Object obj = this._groupList.remove(index);
    return (jalview.schemabinding.version2.Group) obj;
  }

  /**
   * Method removeSetting.
   * 
   * @param vSetting
   * @return true if the object was removed from the collection.
   */
  public boolean removeSetting(
          final jalview.schemabinding.version2.Setting vSetting)
  {
    boolean removed = _settingList.remove(vSetting);
    return removed;
  }

  /**
   * Method removeSettingAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public jalview.schemabinding.version2.Setting removeSettingAt(
          final int index)
  {
    java.lang.Object obj = this._settingList.remove(index);
    return (jalview.schemabinding.version2.Setting) obj;
  }

  /**
   * 
   * 
   * @param index
   * @param vGroup
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setGroup(final int index,
          final jalview.schemabinding.version2.Group vGroup)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._groupList.size())
    {
      throw new IndexOutOfBoundsException("setGroup: Index value '" + index
              + "' not in range [0.." + (this._groupList.size() - 1) + "]");
    }

    this._groupList.set(index, vGroup);
  }

  /**
   * 
   * 
   * @param vGroupArray
   */
  public void setGroup(
          final jalview.schemabinding.version2.Group[] vGroupArray)
  {
    // -- copy array
    _groupList.clear();

    for (int i = 0; i < vGroupArray.length; i++)
    {
      this._groupList.add(vGroupArray[i]);
    }
  }

  /**
   * 
   * 
   * @param index
   * @param vSetting
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setSetting(final int index,
          final jalview.schemabinding.version2.Setting vSetting)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._settingList.size())
    {
      throw new IndexOutOfBoundsException("setSetting: Index value '"
              + index + "' not in range [0.."
              + (this._settingList.size() - 1) + "]");
    }

    this._settingList.set(index, vSetting);
  }

  /**
   * 
   * 
   * @param vSettingArray
   */
  public void setSetting(
          final jalview.schemabinding.version2.Setting[] vSettingArray)
  {
    // -- copy array
    _settingList.clear();

    for (int i = 0; i < vSettingArray.length; i++)
    {
      this._settingList.add(vSettingArray[i]);
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
   * @return the unmarshaled jalview.schemabinding.version2.FeatureSettings
   */
  public static jalview.schemabinding.version2.FeatureSettings unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.FeatureSettings) Unmarshaller
            .unmarshal(
                    jalview.schemabinding.version2.FeatureSettings.class,
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
