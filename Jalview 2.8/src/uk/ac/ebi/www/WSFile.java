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
package uk.ac.ebi.www;

public class WSFile implements java.io.Serializable
{
  private java.lang.String type;

  private java.lang.String ext;

  public WSFile()
  {
  }

  public java.lang.String getType()
  {
    return type;
  }

  public void setType(java.lang.String type)
  {
    this.type = type;
  }

  public java.lang.String getExt()
  {
    return ext;
  }

  public void setExt(java.lang.String ext)
  {
    this.ext = ext;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj)
  {
    if (!(obj instanceof WSFile))
    {
      return false;
    }
    WSFile other = (WSFile) obj;
    if (obj == null)
    {
      return false;
    }
    if (this == obj)
    {
      return true;
    }
    if (__equalsCalc != null)
    {
      return (__equalsCalc == obj);
    }
    __equalsCalc = obj;
    boolean _equals;
    _equals = true
            && ((type == null && other.getType() == null) || (type != null && type
                    .equals(other.getType())))
            && ((ext == null && other.getExt() == null) || (ext != null && ext
                    .equals(other.getExt())));
    __equalsCalc = null;
    return _equals;
  }

  private boolean __hashCodeCalc = false;

  public synchronized int hashCode()
  {
    if (__hashCodeCalc)
    {
      return 0;
    }
    __hashCodeCalc = true;
    int _hashCode = 1;
    if (getType() != null)
    {
      _hashCode += getType().hashCode();
    }
    if (getExt() != null)
    {
      _hashCode += getExt().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
          WSFile.class);

  static
  {
    org.apache.axis.description.FieldDesc field = new org.apache.axis.description.ElementDesc();
    field.setFieldName("type");
    field.setXmlName(new javax.xml.namespace.QName("", "type"));
    field.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    typeDesc.addFieldDesc(field);
    field = new org.apache.axis.description.ElementDesc();
    field.setFieldName("ext");
    field.setXmlName(new javax.xml.namespace.QName("", "ext"));
    field.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    typeDesc.addFieldDesc(field);
  };

  /**
   * Return type metadata object
   */
  public static org.apache.axis.description.TypeDesc getTypeDesc()
  {
    return typeDesc;
  }

  /**
   * Get Custom Serializer
   */
  public static org.apache.axis.encoding.Serializer getSerializer(
          java.lang.String mechType, java.lang.Class _javaType,
          javax.xml.namespace.QName _xmlType)
  {
    return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
            _xmlType, typeDesc);
  }

  /**
   * Get Custom Deserializer
   */
  public static org.apache.axis.encoding.Deserializer getDeserializer(
          java.lang.String mechType, java.lang.Class _javaType,
          javax.xml.namespace.QName _xmlType)
  {
    return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
            _xmlType, typeDesc);
  }

}
