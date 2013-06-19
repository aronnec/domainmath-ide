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
package uk.ac.ebi.picr.model;

public class CrossReference_Helper
{
  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
          CrossReference.class, true);

  static
  {
    typeDesc.setXmlType(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "CrossReference"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("accession");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "accession"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("accessionVersion");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "accessionVersion"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("databaseDescription");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "databaseDescription"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("databaseName");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "databaseName"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("dateAdded");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "dateAdded"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "dateTime"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("dateDeleted");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "dateDeleted"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "dateTime"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("deleted");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "deleted"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("gi");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "gi"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("taxonId");
    elemField.setXmlName(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "taxonId"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
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
