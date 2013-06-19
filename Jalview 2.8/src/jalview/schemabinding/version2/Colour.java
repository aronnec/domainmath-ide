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
 * Class Colour.
 * 
 * @version $Revision$ $Date$
 */
public class Colour implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _name.
   */
  private java.lang.String _name;

  /**
   * Field _RGB.
   */
  private java.lang.String _RGB;

  /**
   * Field _minRGB.
   */
  private java.lang.String _minRGB;

  /**
   * loosely specified enumeration: NONE,ABOVE, or BELOW
   */
  private java.lang.String _threshType;

  /**
   * Field _threshold.
   */
  private float _threshold;

  /**
   * keeps track of state for field: _threshold
   */
  private boolean _has_threshold;

  /**
   * Field _max.
   */
  private float _max;

  /**
   * keeps track of state for field: _max
   */
  private boolean _has_max;

  /**
   * Field _min.
   */
  private float _min;

  /**
   * keeps track of state for field: _min
   */
  private boolean _has_min;

  /**
   * Field _colourByLabel.
   */
  private boolean _colourByLabel;

  /**
   * keeps track of state for field: _colourByLabel
   */
  private boolean _has_colourByLabel;

  /**
   * Field _autoScale.
   */
  private boolean _autoScale;

  /**
   * keeps track of state for field: _autoScale
   */
  private boolean _has_autoScale;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Colour()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deleteAutoScale()
  {
    this._has_autoScale = false;
  }

  /**
     */
  public void deleteColourByLabel()
  {
    this._has_colourByLabel = false;
  }

  /**
     */
  public void deleteMax()
  {
    this._has_max = false;
  }

  /**
     */
  public void deleteMin()
  {
    this._has_min = false;
  }

  /**
     */
  public void deleteThreshold()
  {
    this._has_threshold = false;
  }

  /**
   * Returns the value of field 'autoScale'.
   * 
   * @return the value of field 'AutoScale'.
   */
  public boolean getAutoScale()
  {
    return this._autoScale;
  }

  /**
   * Returns the value of field 'colourByLabel'.
   * 
   * @return the value of field 'ColourByLabel'.
   */
  public boolean getColourByLabel()
  {
    return this._colourByLabel;
  }

  /**
   * Returns the value of field 'max'.
   * 
   * @return the value of field 'Max'.
   */
  public float getMax()
  {
    return this._max;
  }

  /**
   * Returns the value of field 'min'.
   * 
   * @return the value of field 'Min'.
   */
  public float getMin()
  {
    return this._min;
  }

  /**
   * Returns the value of field 'minRGB'.
   * 
   * @return the value of field 'MinRGB'.
   */
  public java.lang.String getMinRGB()
  {
    return this._minRGB;
  }

  /**
   * Returns the value of field 'name'.
   * 
   * @return the value of field 'Name'.
   */
  public java.lang.String getName()
  {
    return this._name;
  }

  /**
   * Returns the value of field 'RGB'.
   * 
   * @return the value of field 'RGB'.
   */
  public java.lang.String getRGB()
  {
    return this._RGB;
  }

  /**
   * Returns the value of field 'threshType'. The field 'threshType' has the
   * following description: loosely specified enumeration: NONE,ABOVE, or BELOW
   * 
   * @return the value of field 'ThreshType'.
   */
  public java.lang.String getThreshType()
  {
    return this._threshType;
  }

  /**
   * Returns the value of field 'threshold'.
   * 
   * @return the value of field 'Threshold'.
   */
  public float getThreshold()
  {
    return this._threshold;
  }

  /**
   * Method hasAutoScale.
   * 
   * @return true if at least one AutoScale has been added
   */
  public boolean hasAutoScale()
  {
    return this._has_autoScale;
  }

  /**
   * Method hasColourByLabel.
   * 
   * @return true if at least one ColourByLabel has been added
   */
  public boolean hasColourByLabel()
  {
    return this._has_colourByLabel;
  }

  /**
   * Method hasMax.
   * 
   * @return true if at least one Max has been added
   */
  public boolean hasMax()
  {
    return this._has_max;
  }

  /**
   * Method hasMin.
   * 
   * @return true if at least one Min has been added
   */
  public boolean hasMin()
  {
    return this._has_min;
  }

  /**
   * Method hasThreshold.
   * 
   * @return true if at least one Threshold has been added
   */
  public boolean hasThreshold()
  {
    return this._has_threshold;
  }

  /**
   * Returns the value of field 'autoScale'.
   * 
   * @return the value of field 'AutoScale'.
   */
  public boolean isAutoScale()
  {
    return this._autoScale;
  }

  /**
   * Returns the value of field 'colourByLabel'.
   * 
   * @return the value of field 'ColourByLabel'.
   */
  public boolean isColourByLabel()
  {
    return this._colourByLabel;
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
   * Sets the value of field 'autoScale'.
   * 
   * @param autoScale
   *          the value of field 'autoScale'.
   */
  public void setAutoScale(final boolean autoScale)
  {
    this._autoScale = autoScale;
    this._has_autoScale = true;
  }

  /**
   * Sets the value of field 'colourByLabel'.
   * 
   * @param colourByLabel
   *          the value of field 'colourByLabel'.
   */
  public void setColourByLabel(final boolean colourByLabel)
  {
    this._colourByLabel = colourByLabel;
    this._has_colourByLabel = true;
  }

  /**
   * Sets the value of field 'max'.
   * 
   * @param max
   *          the value of field 'max'.
   */
  public void setMax(final float max)
  {
    this._max = max;
    this._has_max = true;
  }

  /**
   * Sets the value of field 'min'.
   * 
   * @param min
   *          the value of field 'min'.
   */
  public void setMin(final float min)
  {
    this._min = min;
    this._has_min = true;
  }

  /**
   * Sets the value of field 'minRGB'.
   * 
   * @param minRGB
   *          the value of field 'minRGB'.
   */
  public void setMinRGB(final java.lang.String minRGB)
  {
    this._minRGB = minRGB;
  }

  /**
   * Sets the value of field 'name'.
   * 
   * @param name
   *          the value of field 'name'.
   */
  public void setName(final java.lang.String name)
  {
    this._name = name;
  }

  /**
   * Sets the value of field 'RGB'.
   * 
   * @param RGB
   *          the value of field 'RGB'.
   */
  public void setRGB(final java.lang.String RGB)
  {
    this._RGB = RGB;
  }

  /**
   * Sets the value of field 'threshType'. The field 'threshType' has the
   * following description: loosely specified enumeration: NONE,ABOVE, or BELOW
   * 
   * @param threshType
   *          the value of field 'threshType'.
   */
  public void setThreshType(final java.lang.String threshType)
  {
    this._threshType = threshType;
  }

  /**
   * Sets the value of field 'threshold'.
   * 
   * @param threshold
   *          the value of field 'threshold'.
   */
  public void setThreshold(final float threshold)
  {
    this._threshold = threshold;
    this._has_threshold = true;
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
   * @return the unmarshaled jalview.schemabinding.version2.Colour
   */
  public static jalview.schemabinding.version2.Colour unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.Colour) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.Colour.class, reader);
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
