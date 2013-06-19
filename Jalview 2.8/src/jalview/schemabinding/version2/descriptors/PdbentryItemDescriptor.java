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
package jalview.schemabinding.version2.descriptors;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import jalview.schemabinding.version2.PdbentryItem;

/**
 * Class PdbentryItemDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class PdbentryItemDescriptor extends
        org.exolab.castor.xml.util.XMLClassDescriptorImpl
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _elementDefinition.
   */
  private boolean _elementDefinition;

  /**
   * Field _nsPrefix.
   */
  private java.lang.String _nsPrefix;

  /**
   * Field _nsURI.
   */
  private java.lang.String _nsURI;

  /**
   * Field _xmlName.
   */
  private java.lang.String _xmlName;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public PdbentryItemDescriptor()
  {
    super();
    _nsURI = "www.jalview.org";
    _xmlName = "pdbentry";
    _elementDefinition = false;

    // -- set grouping compositor
    setCompositorAsSequence();
    org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
    org.exolab.castor.mapping.FieldHandler handler = null;
    org.exolab.castor.xml.FieldValidator fieldValidator = null;
    // -- initialize attribute descriptors

    // -- initialize element descriptors

    // -- _propertyList
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            jalview.schemabinding.version2.Property.class, "_propertyList",
            "property", org.exolab.castor.xml.NodeType.Element);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        PdbentryItem target = (PdbentryItem) object;
        return target.getProperty();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          PdbentryItem target = (PdbentryItem) object;
          target.addProperty((jalview.schemabinding.version2.Property) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public void resetValue(Object object) throws IllegalStateException,
              IllegalArgumentException
      {
        try
        {
          PdbentryItem target = (PdbentryItem) object;
          target.removeAllProperty();
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return new jalview.schemabinding.version2.Property();
      }
    };
    desc.setHandler(handler);
    desc.setNameSpaceURI("www.jalview.org");
    desc.setMultivalued(true);
    addFieldDescriptor(desc);

    // -- validation code for: _propertyList
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    fieldValidator.setMinOccurs(0);
    { // -- local scope
    }
    desc.setValidator(fieldValidator);
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * Method getAccessMode.
   * 
   * @return the access mode specified for this class.
   */
  public org.exolab.castor.mapping.AccessMode getAccessMode()
  {
    return null;
  }

  /**
   * Method getIdentity.
   * 
   * @return the identity field, null if this class has no identity.
   */
  public org.exolab.castor.mapping.FieldDescriptor getIdentity()
  {
    return super.getIdentity();
  }

  /**
   * Method getJavaClass.
   * 
   * @return the Java class represented by this descriptor.
   */
  public java.lang.Class getJavaClass()
  {
    return jalview.schemabinding.version2.PdbentryItem.class;
  }

  /**
   * Method getNameSpacePrefix.
   * 
   * @return the namespace prefix to use when marshaling as XML.
   */
  public java.lang.String getNameSpacePrefix()
  {
    return _nsPrefix;
  }

  /**
   * Method getNameSpaceURI.
   * 
   * @return the namespace URI used when marshaling and unmarshaling as XML.
   */
  public java.lang.String getNameSpaceURI()
  {
    return _nsURI;
  }

  /**
   * Method getValidator.
   * 
   * @return a specific validator for the class described by this
   *         ClassDescriptor.
   */
  public org.exolab.castor.xml.TypeValidator getValidator()
  {
    return this;
  }

  /**
   * Method getXMLName.
   * 
   * @return the XML Name for the Class being described.
   */
  public java.lang.String getXMLName()
  {
    return _xmlName;
  }

  /**
   * Method isElementDefinition.
   * 
   * @return true if XML schema definition of this Class is that of a global
   *         element or element with anonymous type definition.
   */
  public boolean isElementDefinition()
  {
    return _elementDefinition;
  }

}
