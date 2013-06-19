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
 * Class CalcIdParam.
 * 
 * @version $Revision$ $Date$
 */
public class CalcIdParam extends WebServiceParameterSet implements
        java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * handle for the calculation which uses this parameter set
   */
  private java.lang.String _calcId;

  /**
   * should the calculation be performed immediately after loading in order to
   * refresh results
   */
  private boolean _needsUpdate = false;

  /**
   * keeps track of state for field: _needsUpdate
   */
  private boolean _has_needsUpdate;

  /**
   * should the calculation be automatically performed on edits
   */
  private boolean _autoUpdate;

  /**
   * keeps track of state for field: _autoUpdate
   */
  private boolean _has_autoUpdate;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public CalcIdParam()
  {
    super();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
     */
  public void deleteAutoUpdate()
  {
    this._has_autoUpdate = false;
  }

  /**
     */
  public void deleteNeedsUpdate()
  {
    this._has_needsUpdate = false;
  }

  /**
   * Returns the value of field 'autoUpdate'. The field 'autoUpdate' has the
   * following description: should the calculation be automatically performed on
   * edits
   * 
   * @return the value of field 'AutoUpdate'.
   */
  public boolean getAutoUpdate()
  {
    return this._autoUpdate;
  }

  /**
   * Returns the value of field 'calcId'. The field 'calcId' has the following
   * description: handle for the calculation which uses this parameter set
   * 
   * @return the value of field 'CalcId'.
   */
  public java.lang.String getCalcId()
  {
    return this._calcId;
  }

  /**
   * Returns the value of field 'needsUpdate'. The field 'needsUpdate' has the
   * following description: should the calculation be performed immediately
   * after loading in order to refresh results
   * 
   * @return the value of field 'NeedsUpdate'.
   */
  public boolean getNeedsUpdate()
  {
    return this._needsUpdate;
  }

  /**
   * Method hasAutoUpdate.
   * 
   * @return true if at least one AutoUpdate has been added
   */
  public boolean hasAutoUpdate()
  {
    return this._has_autoUpdate;
  }

  /**
   * Method hasNeedsUpdate.
   * 
   * @return true if at least one NeedsUpdate has been added
   */
  public boolean hasNeedsUpdate()
  {
    return this._has_needsUpdate;
  }

  /**
   * Returns the value of field 'autoUpdate'. The field 'autoUpdate' has the
   * following description: should the calculation be automatically performed on
   * edits
   * 
   * @return the value of field 'AutoUpdate'.
   */
  public boolean isAutoUpdate()
  {
    return this._autoUpdate;
  }

  /**
   * Returns the value of field 'needsUpdate'. The field 'needsUpdate' has the
   * following description: should the calculation be performed immediately
   * after loading in order to refresh results
   * 
   * @return the value of field 'NeedsUpdate'.
   */
  public boolean isNeedsUpdate()
  {
    return this._needsUpdate;
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
   * Sets the value of field 'autoUpdate'. The field 'autoUpdate' has the
   * following description: should the calculation be automatically performed on
   * edits
   * 
   * @param autoUpdate
   *          the value of field 'autoUpdate'.
   */
  public void setAutoUpdate(final boolean autoUpdate)
  {
    this._autoUpdate = autoUpdate;
    this._has_autoUpdate = true;
  }

  /**
   * Sets the value of field 'calcId'. The field 'calcId' has the following
   * description: handle for the calculation which uses this parameter set
   * 
   * @param calcId
   *          the value of field 'calcId'.
   */
  public void setCalcId(final java.lang.String calcId)
  {
    this._calcId = calcId;
  }

  /**
   * Sets the value of field 'needsUpdate'. The field 'needsUpdate' has the
   * following description: should the calculation be performed immediately
   * after loading in order to refresh results
   * 
   * @param needsUpdate
   *          the value of field 'needsUpdate'.
   */
  public void setNeedsUpdate(final boolean needsUpdate)
  {
    this._needsUpdate = needsUpdate;
    this._has_needsUpdate = true;
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
   * @return the unmarshaled jalview.schemabinding.version2.CalcIdParam
   */
  public static jalview.schemabinding.version2.CalcIdParam unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.CalcIdParam) Unmarshaller
            .unmarshal(jalview.schemabinding.version2.CalcIdParam.class,
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
