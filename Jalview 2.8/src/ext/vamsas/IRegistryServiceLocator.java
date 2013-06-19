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

public class IRegistryServiceLocator extends org.apache.axis.client.Service
        implements ext.vamsas.IRegistryService
{

  public IRegistryServiceLocator()
  {
  }

  public IRegistryServiceLocator(org.apache.axis.EngineConfiguration config)
  {
    super(config);
  }

  // Use to get a proxy class for RegistryService
  private java.lang.String RegistryService_address = "http://webservices.compbio.dundee.ac.uk:8080/jalTestWS/services/RegistryService";

  public java.lang.String getRegistryServiceAddress()
  {
    return RegistryService_address;
  }

  // The WSDD service name defaults to the port name.
  private java.lang.String RegistryServiceWSDDServiceName = "RegistryService";

  public java.lang.String getRegistryServiceWSDDServiceName()
  {
    return RegistryServiceWSDDServiceName;
  }

  public void setRegistryServiceWSDDServiceName(java.lang.String name)
  {
    RegistryServiceWSDDServiceName = name;
  }

  public ext.vamsas.IRegistry getRegistryService()
          throws javax.xml.rpc.ServiceException
  {
    java.net.URL endpoint;
    try
    {
      endpoint = new java.net.URL(RegistryService_address);
    } catch (java.net.MalformedURLException e)
    {
      throw new javax.xml.rpc.ServiceException(e);
    }
    return getRegistryService(endpoint);
  }

  public ext.vamsas.IRegistry getRegistryService(java.net.URL portAddress)
          throws javax.xml.rpc.ServiceException
  {
    try
    {
      ext.vamsas.RegistryServiceSoapBindingStub _stub = new ext.vamsas.RegistryServiceSoapBindingStub(
              portAddress, this);
      _stub.setPortName(getRegistryServiceWSDDServiceName());
      return _stub;
    } catch (org.apache.axis.AxisFault e)
    {
      return null;
    }
  }

  public void setRegistryServiceEndpointAddress(java.lang.String address)
  {
    RegistryService_address = address;
  }

  /**
   * For the given interface, get the stub implementation. If this service has
   * no port for the given interface, then ServiceException is thrown.
   */
  public java.rmi.Remote getPort(Class serviceEndpointInterface)
          throws javax.xml.rpc.ServiceException
  {
    try
    {
      if (ext.vamsas.IRegistry.class
              .isAssignableFrom(serviceEndpointInterface))
      {
        ext.vamsas.RegistryServiceSoapBindingStub _stub = new ext.vamsas.RegistryServiceSoapBindingStub(
                new java.net.URL(RegistryService_address), this);
        _stub.setPortName(getRegistryServiceWSDDServiceName());
        return _stub;
      }
    } catch (java.lang.Throwable t)
    {
      throw new javax.xml.rpc.ServiceException(t);
    }
    throw new javax.xml.rpc.ServiceException(
            "There is no stub implementation for the interface:  "
                    + (serviceEndpointInterface == null ? "null"
                            : serviceEndpointInterface.getName()));
  }

  /**
   * For the given interface, get the stub implementation. If this service has
   * no port for the given interface, then ServiceException is thrown.
   */
  public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
          Class serviceEndpointInterface)
          throws javax.xml.rpc.ServiceException
  {
    if (portName == null)
    {
      return getPort(serviceEndpointInterface);
    }
    java.lang.String inputPortName = portName.getLocalPart();
    if ("RegistryService".equals(inputPortName))
    {
      return getRegistryService();
    }
    else
    {
      java.rmi.Remote _stub = getPort(serviceEndpointInterface);
      ((org.apache.axis.client.Stub) _stub).setPortName(portName);
      return _stub;
    }
  }

  public javax.xml.namespace.QName getServiceName()
  {
    return new javax.xml.namespace.QName("vamsas", "IRegistryService");
  }

  private java.util.HashSet ports = null;

  public java.util.Iterator getPorts()
  {
    if (ports == null)
    {
      ports = new java.util.HashSet();
      ports.add(new javax.xml.namespace.QName("vamsas", "RegistryService"));
    }
    return ports.iterator();
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(java.lang.String portName,
          java.lang.String address) throws javax.xml.rpc.ServiceException
  {
    if ("RegistryService".equals(portName))
    {
      setRegistryServiceEndpointAddress(address);
    }
    else
    { // Unknown Port Name
      throw new javax.xml.rpc.ServiceException(
              " Cannot set Endpoint Address for Unknown Port" + portName);
    }
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(javax.xml.namespace.QName portName,
          java.lang.String address) throws javax.xml.rpc.ServiceException
  {
    setEndpointAddress(portName.getLocalPart(), address);
  }

}
