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
package ext.vamsas;

public class ServiceHandles implements java.io.Serializable
{
  private ext.vamsas.ServiceHandle[] services;

  public ServiceHandles()
  {
  }

  public ServiceHandles(ext.vamsas.ServiceHandle[] services)
  {
    this.services = services;
  }

  /**
   * Gets the services value for this ServiceHandles.
   * 
   * @return services
   */
  public ext.vamsas.ServiceHandle[] getServices()
  {
    return services;
  }

  /**
   * Sets the services value for this ServiceHandles.
   * 
   * @param services
   */
  public void setServices(ext.vamsas.ServiceHandle[] services)
  {
    this.services = services;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj)
  {
    if (!(obj instanceof ServiceHandles))
    {
      return false;
    }
    ServiceHandles other = (ServiceHandles) obj;
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
    _equals = true && ((this.services == null && other.getServices() == null) || (this.services != null && java.util.Arrays
            .equals(this.services, other.getServices())));
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
    if (getServices() != null)
    {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getServices()); i++)
      {
        java.lang.Object obj = java.lang.reflect.Array
                .get(getServices(), i);
        if (obj != null && !obj.getClass().isArray())
        {
          _hashCode += obj.hashCode();
        }
      }
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
          ServiceHandles.class, true);

  static
  {
    typeDesc.setXmlType(new javax.xml.namespace.QName(
            "registry.objects.vamsas", "ServiceHandles"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("services");
    elemField.setXmlName(new javax.xml.namespace.QName("", "services"));
    elemField.setXmlType(new javax.xml.namespace.QName(
            "registry.objects.vamsas", "ServiceHandle"));
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
