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
package uk.ac.ebi.www.picr.AccessionMappingService;

public class AccessionMapperBindingStub extends org.apache.axis.client.Stub
        implements
        uk.ac.ebi.www.picr.AccessionMappingService.AccessionMapperInterface
{
  private java.util.Vector cachedSerClasses = new java.util.Vector();

  private java.util.Vector cachedSerQNames = new java.util.Vector();

  private java.util.Vector cachedSerFactories = new java.util.Vector();

  private java.util.Vector cachedDeserFactories = new java.util.Vector();

  static org.apache.axis.description.OperationDesc[] _operations;

  static
  {
    _operations = new org.apache.axis.description.OperationDesc[3];
    _initOperationDesc1();
  }

  private static void _initOperationDesc1()
  {
    org.apache.axis.description.OperationDesc oper;
    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("getUPIForSequence");
    oper.addParameter(
            new javax.xml.namespace.QName(
                    "http://www.ebi.ac.uk/picr/AccessionMappingService",
                    "sequence"), new javax.xml.namespace.QName(
                    "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "searchDatabases"), new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String[].class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(
            new javax.xml.namespace.QName(
                    "http://www.ebi.ac.uk/picr/AccessionMappingService",
                    "taxonId"), new javax.xml.namespace.QName(
                    "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "onlyActive"), new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.setReturnType(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "UPEntry"));
    oper.setReturnClass(uk.ac.ebi.picr.model.UPEntry.class);
    oper.setReturnQName(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "getUPIForSequenceReturn"));
    oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
    oper.setUse(org.apache.axis.constants.Use.LITERAL);
    _operations[0] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("getUPIForAccession");
    oper.addParameter(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "accession"), new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "ac_version"), new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "searchDatabases"), new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String[].class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(
            new javax.xml.namespace.QName(
                    "http://www.ebi.ac.uk/picr/AccessionMappingService",
                    "taxonId"), new javax.xml.namespace.QName(
                    "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "onlyActive"), new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.setReturnType(new javax.xml.namespace.QName(
            "http://model.picr.ebi.ac.uk", "UPEntry"));
    oper.setReturnClass(uk.ac.ebi.picr.model.UPEntry[].class);
    oper.setReturnQName(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "getUPIForAccessionReturn"));
    oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
    oper.setUse(org.apache.axis.constants.Use.LITERAL);
    _operations[1] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("getMappedDatabaseNames");
    oper.setReturnType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    oper.setReturnClass(java.lang.String[].class);
    oper.setReturnQName(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "mappedDatabases"));
    oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
    oper.setUse(org.apache.axis.constants.Use.LITERAL);
    _operations[2] = oper;

  }

  public AccessionMapperBindingStub() throws org.apache.axis.AxisFault
  {
    this(null);
  }

  public AccessionMapperBindingStub(java.net.URL endpointURL,
          javax.xml.rpc.Service service) throws org.apache.axis.AxisFault
  {
    this(service);
    super.cachedEndpoint = endpointURL;
  }

  public AccessionMapperBindingStub(javax.xml.rpc.Service service)
          throws org.apache.axis.AxisFault
  {
    if (service == null)
    {
      super.service = new org.apache.axis.client.Service();
    }
    else
    {
      super.service = service;
    }
    java.lang.Class cls;
    javax.xml.namespace.QName qName;
    java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
    java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
    java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
    java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
    java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
    java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
    java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
    java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
    java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
    java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
    qName = new javax.xml.namespace.QName("http://model.picr.ebi.ac.uk",
            "UPEntry");
    cachedSerQNames.add(qName);
    cls = uk.ac.ebi.picr.model.UPEntry.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName("http://model.picr.ebi.ac.uk",
            "CrossReference");
    cachedSerQNames.add(qName);
    cls = uk.ac.ebi.picr.model.CrossReference.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

  }

  protected org.apache.axis.client.Call createCall()
          throws java.rmi.RemoteException
  {
    try
    {
      org.apache.axis.client.Call _call = (org.apache.axis.client.Call) super.service
              .createCall();
      if (super.maintainSessionSet)
      {
        _call.setMaintainSession(super.maintainSession);
      }
      if (super.cachedUsername != null)
      {
        _call.setUsername(super.cachedUsername);
      }
      if (super.cachedPassword != null)
      {
        _call.setPassword(super.cachedPassword);
      }
      if (super.cachedEndpoint != null)
      {
        _call.setTargetEndpointAddress(super.cachedEndpoint);
      }
      if (super.cachedTimeout != null)
      {
        _call.setTimeout(super.cachedTimeout);
      }
      if (super.cachedPortName != null)
      {
        _call.setPortName(super.cachedPortName);
      }
      java.util.Enumeration keys = super.cachedProperties.keys();
      while (keys.hasMoreElements())
      {
        java.lang.String key = (java.lang.String) keys.nextElement();
        _call.setProperty(key, super.cachedProperties.get(key));
      }
      // All the type mapping information is registered
      // when the first call is made.
      // The type mapping information is actually registered in
      // the TypeMappingRegistry of the service, which
      // is the reason why registration is only needed for the first call.
      synchronized (this)
      {
        if (firstCall())
        {
          // must set encoding style before registering serializers
          _call.setEncodingStyle(null);
          for (int i = 0; i < cachedSerFactories.size(); ++i)
          {
            java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
            javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames
                    .get(i);
            java.lang.Class sf = (java.lang.Class) cachedSerFactories
                    .get(i);
            java.lang.Class df = (java.lang.Class) cachedDeserFactories
                    .get(i);
            _call.registerTypeMapping(cls, qName, sf, df, false);
          }
        }
      }
      return _call;
    } catch (java.lang.Throwable _t)
    {
      throw new org.apache.axis.AxisFault(
              "Failure trying to get the Call object", _t);
    }
  }

  public uk.ac.ebi.picr.model.UPEntry getUPIForSequence(
          java.lang.String sequence, java.lang.String[] searchDatabases,
          java.lang.String taxonId, boolean onlyActive)
          throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[0]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("getUPIForSequence");
    _call.setEncodingStyle(null);
    _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
            Boolean.FALSE);
    _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
            Boolean.FALSE);
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "getUPIForSequence"));

    setRequestHeaders(_call);
    setAttachments(_call);
    java.lang.Object _resp = _call
            .invoke(new java.lang.Object[]
            { sequence, searchDatabases, taxonId,
                new java.lang.Boolean(onlyActive) });

    if (_resp instanceof java.rmi.RemoteException)
    {
      throw (java.rmi.RemoteException) _resp;
    }
    else
    {
      extractAttachments(_call);
      try
      {
        return (uk.ac.ebi.picr.model.UPEntry) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (uk.ac.ebi.picr.model.UPEntry) org.apache.axis.utils.JavaUtils
                .convert(_resp, uk.ac.ebi.picr.model.UPEntry.class);
      }
    }
  }

  public uk.ac.ebi.picr.model.UPEntry[] getUPIForAccession(
          java.lang.String accession, java.lang.String ac_version,
          java.lang.String[] searchDatabases, java.lang.String taxonId,
          boolean onlyActive) throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[1]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("getUPIForAccession");
    _call.setEncodingStyle(null);
    _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
            Boolean.FALSE);
    _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
            Boolean.FALSE);
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "getUPIForAccession"));

    setRequestHeaders(_call);
    setAttachments(_call);
    java.lang.Object _resp = _call.invoke(new java.lang.Object[]
    { accession, ac_version, searchDatabases, taxonId,
        new java.lang.Boolean(onlyActive) });

    if (_resp instanceof java.rmi.RemoteException)
    {
      throw (java.rmi.RemoteException) _resp;
    }
    else
    {
      extractAttachments(_call);
      try
      {
        return (uk.ac.ebi.picr.model.UPEntry[]) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (uk.ac.ebi.picr.model.UPEntry[]) org.apache.axis.utils.JavaUtils
                .convert(_resp, uk.ac.ebi.picr.model.UPEntry[].class);
      }
    }
  }

  public java.lang.String[] getMappedDatabaseNames()
          throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[2]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("getMappedDatabaseNames");
    _call.setEncodingStyle(null);
    _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
            Boolean.FALSE);
    _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
            Boolean.FALSE);
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName(
            "http://www.ebi.ac.uk/picr/AccessionMappingService",
            "getMappedDatabaseNames"));

    setRequestHeaders(_call);
    setAttachments(_call);
    java.lang.Object _resp = _call.invoke(new java.lang.Object[]
    {});

    if (_resp instanceof java.rmi.RemoteException)
    {
      throw (java.rmi.RemoteException) _resp;
    }
    else
    {
      extractAttachments(_call);
      try
      {
        return (java.lang.String[]) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (java.lang.String[]) org.apache.axis.utils.JavaUtils
                .convert(_resp, java.lang.String[].class);
      }
    }
  }

}
