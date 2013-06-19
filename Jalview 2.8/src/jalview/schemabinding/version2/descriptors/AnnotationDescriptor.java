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

import jalview.schemabinding.version2.Annotation;

/**
 * Class AnnotationDescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class AnnotationDescriptor extends
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

  public AnnotationDescriptor()
  {
    super();
    _nsURI = "www.vamsas.ac.uk/jalview/version2";
    _xmlName = "Annotation";
    _elementDefinition = true;

    // -- set grouping compositor
    setCompositorAsSequence();
    org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
    org.exolab.castor.mapping.FieldHandler handler = null;
    org.exolab.castor.xml.FieldValidator fieldValidator = null;
    // -- initialize attribute descriptors

    // -- _graph
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_graph", "graph",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasGraph())
        {
          return null;
        }
        return (target.getGraph() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // ignore null values for non optional primitives
          if (value == null)
          {
            return;
          }

          target.setGraph(((java.lang.Boolean) value).booleanValue());
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
    desc.setRequired(true);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _graph
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    fieldValidator.setMinOccurs(1);
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _graphType
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_graphType", "graphType",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasGraphType())
        {
          return null;
        }
        return new java.lang.Integer(target.getGraphType());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteGraphType();
            return;
          }
          target.setGraphType(((java.lang.Integer) value).intValue());
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

    // -- validation code for: _graphType
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _sequenceRef
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_sequenceRef", "sequenceRef",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getSequenceRef();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          target.setSequenceRef((java.lang.String) value);
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

    // -- validation code for: _sequenceRef
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _groupRef
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_groupRef", "groupRef",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getGroupRef();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          target.setGroupRef((java.lang.String) value);
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

    // -- validation code for: _groupRef
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _graphColour
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_graphColour", "graphColour",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasGraphColour())
        {
          return null;
        }
        return new java.lang.Integer(target.getGraphColour());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteGraphColour();
            return;
          }
          target.setGraphColour(((java.lang.Integer) value).intValue());
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

    // -- validation code for: _graphColour
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _graphGroup
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_graphGroup", "graphGroup",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasGraphGroup())
        {
          return null;
        }
        return new java.lang.Integer(target.getGraphGroup());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteGraphGroup();
            return;
          }
          target.setGraphGroup(((java.lang.Integer) value).intValue());
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

    // -- validation code for: _graphGroup
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.IntValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.IntValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-2147483648);
      typeValidator.setMaxInclusive(2147483647);
    }
    desc.setValidator(fieldValidator);
    // -- _graphHeight
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Integer.TYPE, "_graphHeight", "graphHeight",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasGraphHeight())
        {
          return null;
        }
        return new java.lang.Integer(target.getGraphHeight());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteGraphHeight();
            return;
          }
          target.setGraphHeight(((java.lang.Integer) value).intValue());
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

    // -- validation code for: _graphHeight
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
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getId();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
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
    // -- _scoreOnly
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_scoreOnly", "scoreOnly",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasScoreOnly())
        {
          return null;
        }
        return (target.getScoreOnly() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteScoreOnly();
            return;
          }
          target.setScoreOnly(((java.lang.Boolean) value).booleanValue());
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

    // -- validation code for: _scoreOnly
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _score
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Double.TYPE, "_score", "score",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasScore())
        {
          return null;
        }
        return new java.lang.Double(target.getScore());
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteScore();
            return;
          }
          target.setScore(((java.lang.Double) value).doubleValue());
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

    // -- validation code for: _score
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.DoubleValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.DoubleValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setMinInclusive(-1.7976931348623157E308);
      typeValidator.setMaxInclusive(1.7976931348623157E308);
    }
    desc.setValidator(fieldValidator);
    // -- _visible
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_visible", "visible",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasVisible())
        {
          return null;
        }
        return (target.getVisible() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteVisible();
            return;
          }
          target.setVisible(((java.lang.Boolean) value).booleanValue());
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

    // -- validation code for: _visible
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _centreColLabels
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_centreColLabels", "centreColLabels",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasCentreColLabels())
        {
          return null;
        }
        return (target.getCentreColLabels() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteCentreColLabels();
            return;
          }
          target.setCentreColLabels(((java.lang.Boolean) value)
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

    // -- validation code for: _centreColLabels
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _scaleColLabels
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_scaleColLabels", "scaleColLabels",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasScaleColLabels())
        {
          return null;
        }
        return (target.getScaleColLabels() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteScaleColLabels();
            return;
          }
          target.setScaleColLabels(((java.lang.Boolean) value)
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

    // -- validation code for: _scaleColLabels
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _showAllColLabels
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_showAllColLabels",
            "showAllColLabels", org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasShowAllColLabels())
        {
          return null;
        }
        return (target.getShowAllColLabels() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteShowAllColLabels();
            return;
          }
          target.setShowAllColLabels(((java.lang.Boolean) value)
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

    // -- validation code for: _showAllColLabels
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _autoCalculated
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_autoCalculated", "autoCalculated",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasAutoCalculated())
        {
          return null;
        }
        return (target.getAutoCalculated() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteAutoCalculated();
            return;
          }
          target.setAutoCalculated(((java.lang.Boolean) value)
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

    // -- validation code for: _autoCalculated
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _belowAlignment
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.Boolean.TYPE, "_belowAlignment", "belowAlignment",
            org.exolab.castor.xml.NodeType.Attribute);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        if (!target.hasBelowAlignment())
        {
          return null;
        }
        return (target.getBelowAlignment() ? java.lang.Boolean.TRUE
                : java.lang.Boolean.FALSE);
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          // if null, use delete method for optional primitives
          if (value == null)
          {
            target.deleteBelowAlignment();
            return;
          }
          target.setBelowAlignment(((java.lang.Boolean) value)
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

    // -- validation code for: _belowAlignment
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.BooleanValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.BooleanValidator();
      fieldValidator.setValidator(typeValidator);
    }
    desc.setValidator(fieldValidator);
    // -- _calcId
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_calcId", "calcId",
            org.exolab.castor.xml.NodeType.Attribute);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getCalcId();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          target.setCalcId((java.lang.String) value);
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

    // -- validation code for: _calcId
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- initialize element descriptors

    // -- _annotationElementList
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            jalview.schemabinding.version2.AnnotationElement.class,
            "_annotationElementList", "annotationElement",
            org.exolab.castor.xml.NodeType.Element);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getAnnotationElement();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          target.addAnnotationElement((jalview.schemabinding.version2.AnnotationElement) value);
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
          Annotation target = (Annotation) object;
          target.removeAllAnnotationElement();
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return new jalview.schemabinding.version2.AnnotationElement();
      }
    };
    desc.setHandler(handler);
    desc.setNameSpaceURI("www.vamsas.ac.uk/jalview/version2");
    desc.setMultivalued(true);
    addFieldDescriptor(desc);

    // -- validation code for: _annotationElementList
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    fieldValidator.setMinOccurs(0);
    { // -- local scope
    }
    desc.setValidator(fieldValidator);
    // -- _label
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_label", "label",
            org.exolab.castor.xml.NodeType.Element);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getLabel();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          target.setLabel((java.lang.String) value);
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
    desc.setNameSpaceURI("www.vamsas.ac.uk/jalview/version2");
    desc.setRequired(true);
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _label
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    fieldValidator.setMinOccurs(1);
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _description
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            java.lang.String.class, "_description", "description",
            org.exolab.castor.xml.NodeType.Element);
    desc.setImmutable(true);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getDescription();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          target.setDescription((java.lang.String) value);
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
    desc.setNameSpaceURI("www.vamsas.ac.uk/jalview/version2");
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _description
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
    { // -- local scope
      org.exolab.castor.xml.validators.StringValidator typeValidator;
      typeValidator = new org.exolab.castor.xml.validators.StringValidator();
      fieldValidator.setValidator(typeValidator);
      typeValidator.setWhiteSpace("preserve");
    }
    desc.setValidator(fieldValidator);
    // -- _thresholdLine
    desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
            jalview.schemabinding.version2.ThresholdLine.class,
            "_thresholdLine", "thresholdLine",
            org.exolab.castor.xml.NodeType.Element);
    handler = new org.exolab.castor.xml.XMLFieldHandler()
    {
      public java.lang.Object getValue(java.lang.Object object)
              throws IllegalStateException
      {
        Annotation target = (Annotation) object;
        return target.getThresholdLine();
      }

      public void setValue(java.lang.Object object, java.lang.Object value)
              throws IllegalStateException, IllegalArgumentException
      {
        try
        {
          Annotation target = (Annotation) object;
          target.setThresholdLine((jalview.schemabinding.version2.ThresholdLine) value);
        } catch (java.lang.Exception ex)
        {
          throw new IllegalStateException(ex.toString());
        }
      }

      public java.lang.Object newInstance(java.lang.Object parent)
      {
        return new jalview.schemabinding.version2.ThresholdLine();
      }
    };
    desc.setHandler(handler);
    desc.setNameSpaceURI("www.vamsas.ac.uk/jalview/version2");
    desc.setMultivalued(false);
    addFieldDescriptor(desc);

    // -- validation code for: _thresholdLine
    fieldValidator = new org.exolab.castor.xml.FieldValidator();
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
    return jalview.schemabinding.version2.Annotation.class;
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
