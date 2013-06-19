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
package vamsas.objects.simple;

public class Result_Helper
{
  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
          Result.class, true);

  static
  {
    typeDesc.setXmlType(new javax.xml.namespace.QName(
            "simple.objects.vamsas", "Result"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("broken");
    elemField.setXmlName(new javax.xml.namespace.QName("", "broken"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("failed");
    elemField.setXmlName(new javax.xml.namespace.QName("", "failed"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("finished");
    elemField.setXmlName(new javax.xml.namespace.QName("", "finished"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("invalid");
    elemField.setXmlName(new javax.xml.namespace.QName("", "invalid"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("jobFailed");
    elemField.setXmlName(new javax.xml.namespace.QName("", "jobFailed"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("queued");
    elemField.setXmlName(new javax.xml.namespace.QName("", "queued"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("running");
    elemField.setXmlName(new javax.xml.namespace.QName("", "running"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("serverError");
    elemField.setXmlName(new javax.xml.namespace.QName("", "serverError"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("state");
    elemField.setXmlName(new javax.xml.namespace.QName("", "state"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "int"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("status");
    elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://schemas.xmlsoap.org/soap/encoding/", "string"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("suspended");
    elemField.setXmlName(new javax.xml.namespace.QName("", "suspended"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
  }

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
