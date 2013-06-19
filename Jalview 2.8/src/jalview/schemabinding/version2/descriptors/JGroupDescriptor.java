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

import jalview.schemabinding.version2.JGroup;

/**
 * Class JGroupDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class JGroupDescriptor extends
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

  public JGroupDescriptor()
  {
    super();
    _nsURI = "www.jalview.org";
    _xmlName = "JGroup";
    _elementDefinition = true;

    // -- set grouping compositor
    setCompositorAsSequence();
    org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
    org.exolab.castor.mapping.FieldHandler handler = null;
    org.exolab.castor.xml.FieldValidator fieldValidator = null;
    // -- initialize attribute descriptors

    // -- _start
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_start", "start",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        if (!target.hasStart())
        {
          return null;
        }
        return new java.lang.Integer(target.getStart());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteStart();
            return;
          }
          target.setStart(((java.lang.Integer) value).intValue());
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

    // -- validation code for: _start
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _end
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_end", "end",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        if (!target.hasEnd())
        {
          return null;
        }
        return new java.lang.Integer(target.getEnd());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteEnd();
            return;
          }
          target.setEnd(((java.lang.Integer) value).intValue());
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

    // -- validation code for: _end
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _name
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_name", "name",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        return target.getName();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          target.setName((java.lang.String) value);
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

    // -- validation code for: _name
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _colour
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_colour", "colour",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        return target.getColour();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          target.setColour((java.lang.String) value);
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

    // -- validation code for: _colour
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
    // -- _outlineColour
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_outlineColour", "outlineColour",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        if (!target.hasOutlineColour())
        {
          return null;
        }
        return new java.lang.Integer(target.getOutlineColour());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteOutlineColour();
            return;
          }
          target.setOutlineColour(((java.lang.Integer) value).intValue());
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

    // -- validation code for: _outlineColour
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _displayBoxes
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_displayBoxes", "displayBoxes",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        if (!target.hasDisplayBoxes())
        {
          return null;
        }
        return (target.getDisplayBoxes() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteDisplayBoxes();
            return;
          }
          target.setDisplayBoxes(((java.lang.Boolean) value).booleanValue());
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

    // -- validation code for: _displayBoxes
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _displayText
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_displayText", "displayText",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        if (!target.hasDisplayText())
        {
          return null;
        }
        return (target.getDisplayText() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteDisplayText();
            return;
          }
          target.setDisplayText(((java.lang.Boolean) value).booleanValue());
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

    // -- validation code for: _displayText
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _colourText
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_colourText", "colourText",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        if (!target.hasColourText())
        {
          return null;
        }
        return (target.getColourText() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteColourText();
            return;
          }
          target.setColourText(((java.lang.Boolean) value).booleanValue());
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

    // -- validation code for: _colourText
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
    // -- _showUnconserved
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showUnconserved", "showUnconserved",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
        JGroup target = (JGroup) object;
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
          JGroup target = (JGroup) object;
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
    // -- _id
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_id", "id",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        return target.getId();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          target.setId((java.lang.String) value);
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

    // -- validation code for: _id
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- initialize element descriptors

    // -- _seqList
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_seqList", "seq",
            org.exolab.castor.xml.NodeType.Element);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        JGroup target = (JGroup) object;
        return target.getSeq();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          JGroup target = (JGroup) object;
          target.addSeq((java.lang.String) value);
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
          JGroup target = (JGroup) object;
          target.removeAllSeq();
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
    desc.setNameSpaceURI("www.jalview.org");
    desc.setRequired(true);
    desc.setMultivalued(true);
    addFieldDescriptor(desc);

    // -- validation code for: _seqList
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    fieldValidator.setMinOccurs(1);
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
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
    return jalview.schemabinding.version2.JGroup.class;
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
