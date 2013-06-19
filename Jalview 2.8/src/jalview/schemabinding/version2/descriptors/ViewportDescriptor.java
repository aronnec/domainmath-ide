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

import jalview.schemabinding.version2.Viewport;

/**
 * Class ViewportDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class ViewportDescriptor extends
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

  public ViewportDescriptor()
  {
    super();
    _nsURI = "www.jalview.org";
    _xmlName = "Viewport";
    _elementDefinition = true;

    // -- set grouping compositor
    setCompositorAsSequence();
    org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
    org.exolab.castor.mapping.FieldHandler handler = null;
    org.exolab.castor.xml.FieldValidator fieldValidator = null;
    // -- initialize attribute descriptors

    // -- _conservationSelected
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_conservationSelected",
            "conservationSelected",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasConservationSelected())
        {
          return null;
        }
        return (target.getConservationSelected() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteConservationSelected();
            return;
          }
          target.setConservationSelected(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _conservationSelected
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _pidSelected
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_pidSelected", "pidSelected",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasPidSelected())
        {
          return null;
        }
        return (target.getPidSelected() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deletePidSelected();
            return;
          }
          target.setPidSelected(((java.lang.Boolean) value).booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _pidSelected
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _bgColour
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_bgColour", "bgColour",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getBgColour();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.setBgColour((java.lang.String) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _bgColour
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _consThreshold
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_consThreshold", "consThreshold",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasConsThreshold())
        {
          return null;
        }
        return new java.lang.Integer(target.getConsThreshold());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteConsThreshold();
            return;
          }
          target.setConsThreshold(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _consThreshold
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _pidThreshold
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_pidThreshold", "pidThreshold",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasPidThreshold())
        {
          return null;
        }
        return new java.lang.Integer(target.getPidThreshold());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deletePidThreshold();
            return;
          }
          target.setPidThreshold(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _pidThreshold
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _title
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_title", "title",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getTitle();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.setTitle((java.lang.String) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _title
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _showFullId
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showFullId", "showFullId",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowFullId())
        {
          return null;
        }
        return (target.getShowFullId() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowFullId();
            return;
          }
          target.setShowFullId(((java.lang.Boolean) value).booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showFullId
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _rightAlignIds
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_rightAlignIds", "rightAlignIds",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasRightAlignIds())
        {
          return null;
        }
        return (target.getRightAlignIds() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteRightAlignIds();
            return;
          }
          target.setRightAlignIds(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _rightAlignIds
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showText
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showText", "showText",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowText())
        {
          return null;
        }
        return (target.getShowText() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowText();
            return;
          }
          target.setShowText(((java.lang.Boolean) value).booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showText
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showColourText
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showColourText", "showColourText",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowColourText())
        {
          return null;
        }
        return (target.getShowColourText() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowColourText();
            return;
          }
          target.setShowColourText(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showColourText
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showUnconserved
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showUnconserved", "showUnconserved",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowUnconserved())
        {
          return null;
        }
        return (target.getShowUnconserved() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowUnconserved();
            return;
          }
          target.setShowUnconserved(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showUnconserved
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showBoxes
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showBoxes", "showBoxes",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowBoxes())
        {
          return null;
        }
        return (target.getShowBoxes() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowBoxes();
            return;
          }
          target.setShowBoxes(((java.lang.Boolean) value).booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showBoxes
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _wrapAlignment
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_wrapAlignment", "wrapAlignment",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasWrapAlignment())
        {
          return null;
        }
        return (target.getWrapAlignment() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteWrapAlignment();
            return;
          }
          target.setWrapAlignment(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _wrapAlignment
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _renderGaps
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_renderGaps", "renderGaps",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasRenderGaps())
        {
          return null;
        }
        return (target.getRenderGaps() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteRenderGaps();
            return;
          }
          target.setRenderGaps(((java.lang.Boolean) value).booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _renderGaps
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showSequenceFeatures
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showSequenceFeatures",
            "showSequenceFeatures",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowSequenceFeatures())
        {
          return null;
        }
        return (target.getShowSequenceFeatures() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowSequenceFeatures();
            return;
          }
          target.setShowSequenceFeatures(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showSequenceFeatures
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showNPfeatureTooltip
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showNPfeatureTooltip",
            "showNPfeatureTooltip",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowNPfeatureTooltip())
        {
          return null;
        }
        return (target.getShowNPfeatureTooltip() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowNPfeatureTooltip();
            return;
          }
          target.setShowNPfeatureTooltip(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showNPfeatureTooltip
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showDbRefTooltip
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showDbRefTooltip",
            "showDbRefTooltip", org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowDbRefTooltip())
        {
          return null;
        }
        return (target.getShowDbRefTooltip() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowDbRefTooltip();
            return;
          }
          target.setShowDbRefTooltip(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showDbRefTooltip
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _followHighlight
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_followHighlight", "followHighlight",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasFollowHighlight())
        {
          return null;
        }
        return (target.getFollowHighlight() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteFollowHighlight();
            return;
          }
          target.setFollowHighlight(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _followHighlight
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _followSelection
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_followSelection", "followSelection",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasFollowSelection())
        {
          return null;
        }
        return (target.getFollowSelection() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteFollowSelection();
            return;
          }
          target.setFollowSelection(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _followSelection
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showAnnotation
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showAnnotation", "showAnnotation",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowAnnotation())
        {
          return null;
        }
        return (target.getShowAnnotation() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowAnnotation();
            return;
          }
          target.setShowAnnotation(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showAnnotation
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _centreColumnLabels
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_centreColumnLabels",
            "centreColumnLabels", org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasCentreColumnLabels())
        {
          return null;
        }
        return (target.getCentreColumnLabels() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteCentreColumnLabels();
            return;
          }
          target.setCentreColumnLabels(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _centreColumnLabels
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showGroupConservation
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showGroupConservation",
            "showGroupConservation",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowGroupConservation())
        {
          return null;
        }
        return (target.getShowGroupConservation() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowGroupConservation();
            return;
          }
          target.setShowGroupConservation(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showGroupConservation
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showGroupConsensus
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showGroupConsensus",
            "showGroupConsensus", org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowGroupConsensus())
        {
          return null;
        }
        return (target.getShowGroupConsensus() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowGroupConsensus();
            return;
          }
          target.setShowGroupConsensus(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showGroupConsensus
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showConsensusHistogram
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showConsensusHistogram",
            "showConsensusHistogram",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowConsensusHistogram())
        {
          return null;
        }
        return (target.getShowConsensusHistogram() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowConsensusHistogram();
            return;
          }
          target.setShowConsensusHistogram(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showConsensusHistogram
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showSequenceLogo
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showSequenceLogo",
            "showSequenceLogo", org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasShowSequenceLogo())
        {
          return null;
        }
        return (target.getShowSequenceLogo() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowSequenceLogo();
            return;
          }
          target.setShowSequenceLogo(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _showSequenceLogo
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _normaliseSequenceLogo
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_normaliseSequenceLogo",
            "normaliseSequenceLogo",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasNormaliseSequenceLogo())
        {
          return null;
        }
        return (target.getNormaliseSequenceLogo() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteNormaliseSequenceLogo();
            return;
          }
          target.setNormaliseSequenceLogo(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _normaliseSequenceLogo
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _ignoreGapsinConsensus
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_ignoreGapsinConsensus",
            "ignoreGapsinConsensus",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasIgnoreGapsinConsensus())
        {
          return null;
        }
        return (target.getIgnoreGapsinConsensus() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteIgnoreGapsinConsensus();
            return;
          }
          target.setIgnoreGapsinConsensus(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _ignoreGapsinConsensus
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _startRes
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_startRes", "startRes",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasStartRes())
        {
          return null;
        }
        return new java.lang.Integer(target.getStartRes());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteStartRes();
            return;
          }
          target.setStartRes(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _startRes
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _startSeq
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_startSeq", "startSeq",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasStartSeq())
        {
          return null;
        }
        return new java.lang.Integer(target.getStartSeq());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteStartSeq();
            return;
          }
          target.setStartSeq(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _startSeq
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _fontName
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_fontName", "fontName",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getFontName();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.setFontName((java.lang.String) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _fontName
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _fontSize
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_fontSize", "fontSize",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasFontSize())
        {
          return null;
        }
        return new java.lang.Integer(target.getFontSize());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteFontSize();
            return;
          }
          target.setFontSize(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _fontSize
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _fontStyle
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_fontStyle", "fontStyle",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasFontStyle())
        {
          return null;
        }
        return new java.lang.Integer(target.getFontStyle());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteFontStyle();
            return;
          }
          target.setFontStyle(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _fontStyle
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _viewName
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_viewName", "viewName",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getViewName();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.setViewName((java.lang.String) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _viewName
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _sequenceSetId
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_sequenceSetId", "sequenceSetId",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getSequenceSetId();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.setSequenceSetId((java.lang.String) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _sequenceSetId
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _gatheredViews
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_gatheredViews", "gatheredViews",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasGatheredViews())
        {
          return null;
        }
        return (target.getGatheredViews() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteGatheredViews();
            return;
          }
          target.setGatheredViews(((java.lang.Boolean) value)
                  .booleanValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _gatheredViews
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _textCol1
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_textCol1", "textCol1",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasTextCol1())
        {
          return null;
        }
        return new java.lang.Integer(target.getTextCol1());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteTextCol1();
            return;
          }
          target.setTextCol1(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _textCol1
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _textCol2
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_textCol2", "textCol2",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasTextCol2())
        {
          return null;
        }
        return new java.lang.Integer(target.getTextCol2());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteTextCol2();
            return;
          }
          target.setTextCol2(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _textCol2
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _textColThreshold
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_textColThreshold",
            "textColThreshold", org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasTextColThreshold())
        {
          return null;
        }
        return new java.lang.Integer(target.getTextColThreshold());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteTextColThreshold();
            return;
          }
          target.setTextColThreshold(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _textColThreshold
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _id
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_id", "id",
            org.exolab.castor.xml.NodeType.Attribute);
    super.setIdentity(desc);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getId();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.setId((java.lang.String) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return new java.lang.String();
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _id
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IdValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IdValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _width
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_width", "width",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasWidth())
        {
          return null;
        }
        return new java.lang.Integer(target.getWidth());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteWidth();
            return;
          }
          target.setWidth(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _width
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _height
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_height", "height",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasHeight())
        {
          return null;
        }
        return new java.lang.Integer(target.getHeight());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteHeight();
            return;
          }
          target.setHeight(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _height
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _xpos
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_xpos", "xpos",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasXpos())
        {
          return null;
        }
        return new java.lang.Integer(target.getXpos());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteXpos();
            return;
          }
          target.setXpos(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _xpos
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _ypos
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_ypos", "ypos",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        if (!target.hasYpos())
        {
          return null;
        }
        return new java.lang.Integer(target.getYpos());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteYpos();
            return;
          }
          target.setYpos(((java.lang.Integer) value).intValue());
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return null;
      }
    };
    desc.setHandler(handler);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _ypos
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- initialize element descriptors

    // -- _annotationColours
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            jalview.schemabinding.version2.AnnotationColours.class,
            "_annotationColours", "AnnotationColours",
            org.exolab.castor.xml.NodeType.Element);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getAnnotationColours();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.setAnnotationColours((jalview.schemabinding.version2.AnnotationColours) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return new jalview.schemabinding.version2.AnnotationColours();
      }
    };
    desc.setHandler(handler);
    desc.setNameSpaceURI("www.jalview.org");
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _annotationColours
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
    }
    desc.setValidator(fieldValidator);
    // -- _hiddenColumnsList
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            jalview.schemabinding.version2.HiddenColumns.class,
            "_hiddenColumnsList", "hiddenColumns",
            org.exolab.castor.xml.NodeType.Element);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getHiddenColumns();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.addHiddenColumns((jalview.schemabinding.version2.HiddenColumns) value);
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
          Viewport target = (Viewport) object;
          target.removeAllHiddenColumns();
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return new jalview.schemabinding.version2.HiddenColumns();
      }
    };
    desc.setHandler(handler);
    desc.setNameSpaceURI("www.jalview.org");
    desc.setMultivalued(true);
    addFieldDescriptor(desc);

    // -- validation code for: _hiddenColumnsList
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    fieldValidator.setMinOccurs(0);
    { // -- local scope
    }
    desc.setValidator(fieldValidator);
    // -- _calcIdParamList
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            jalview.schemabinding.version2.CalcIdParam.class,
            "_calcIdParamList", "calcIdParam",
            org.exolab.castor.xml.NodeType.Element);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Viewport target = (Viewport) object;
        return target.getCalcIdParam();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Viewport target = (Viewport) object;
          target.addCalcIdParam((jalview.schemabinding.version2.CalcIdParam) value);
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
          Viewport target = (Viewport) object;
          target.removeAllCalcIdParam();
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return new jalview.schemabinding.version2.CalcIdParam();
      }
    };
    desc.setHandler(handler);
    desc.setNameSpaceURI("www.jalview.org");
    desc.setMultivalued(true);
    addFieldDescriptor(desc);

    // -- validation code for: _calcIdParamList
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
    return jalview.schemabinding.version2.Viewport.class;
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
