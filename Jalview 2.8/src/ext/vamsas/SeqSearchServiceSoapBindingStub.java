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

public class SeqSearchServiceSoapBindingStub extends
        org.apache.axis.client.Stub implements ext.vamsas.SeqSearchI
{
  private java.util.Vector cachedSerClasses = new java.util.Vector();

  private java.util.Vector cachedSerQNames = new java.util.Vector();

  private java.util.Vector cachedSerFactories = new java.util.Vector();

  private java.util.Vector cachedDeserFactories = new java.util.Vector();

  static org.apache.axis.description.OperationDesc[] _operations;

  static
  {
    _operations = new org.apache.axis.description.OperationDesc[5];
    _initOperationDesc1();
  }

  private static void _initOperationDesc1()
  {
    org.apache.axis.description.OperationDesc oper;
    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("getDatabase");
    oper.setReturnType(new javax.xml.namespace.QName(
            "http://www.w3.org/2001/XMLSchema", "string"));
    oper.setReturnClass(java.lang.String.class);
    oper.setReturnQName(new javax.xml.namespace.QName("",
            "getDatabaseReturn"));
    oper.setStyle(org.apache.axis.constants.Style.RPC);
    oper.setUse(org.apache.axis.constants.Use.ENCODED);
    _operations[0] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("getResult");
    oper.addParameter(new javax.xml.namespace.QName("", "job_id"),
            new javax.xml.namespace.QName(
                    "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.setReturnType(new javax.xml.namespace.QName(
            "simple.objects.vamsas", "SeqSearchResult"));
    oper.setReturnClass(vamsas.objects.simple.SeqSearchResult.class);
    oper.setReturnQName(new javax.xml.namespace.QName("", "getResultReturn"));
    oper.setStyle(org.apache.axis.constants.Style.RPC);
    oper.setUse(org.apache.axis.constants.Use.ENCODED);
    _operations[1] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("psearch");
    oper.addParameter(new javax.xml.namespace.QName("", "al"),
            new javax.xml.namespace.QName("simple.objects.vamsas",
                    "Alignment"), vamsas.objects.simple.Alignment.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(new javax.xml.namespace.QName("", "database"),
            new javax.xml.namespace.QName(
                    "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.setReturnType(new javax.xml.namespace.QName(
            "simple.objects.vamsas", "WsJobId"));
    oper.setReturnClass(vamsas.objects.simple.WsJobId.class);
    oper.setReturnQName(new javax.xml.namespace.QName("", "psearchReturn"));
    oper.setStyle(org.apache.axis.constants.Style.RPC);
    oper.setUse(org.apache.axis.constants.Use.ENCODED);
    _operations[2] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("search");
    oper.addParameter(new javax.xml.namespace.QName("", "s"),
            new javax.xml.namespace.QName("simple.objects.vamsas",
                    "Sequence"), vamsas.objects.simple.Sequence.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.addParameter(new javax.xml.namespace.QName("", "database"),
            new javax.xml.namespace.QName(
                    "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.setReturnType(new javax.xml.namespace.QName(
            "simple.objects.vamsas", "WsJobId"));
    oper.setReturnClass(vamsas.objects.simple.WsJobId.class);
    oper.setReturnQName(new javax.xml.namespace.QName("", "searchReturn"));
    oper.setStyle(org.apache.axis.constants.Style.RPC);
    oper.setUse(org.apache.axis.constants.Use.ENCODED);
    _operations[3] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("cancel");
    oper.addParameter(new javax.xml.namespace.QName("", "jobId"),
            new javax.xml.namespace.QName(
                    "http://www.w3.org/2001/XMLSchema", "string"),
            java.lang.String.class,
            org.apache.axis.description.ParameterDesc.IN, false, false);
    oper.setReturnType(new javax.xml.namespace.QName(
            "simple.objects.vamsas", "WsJobId"));
    oper.setReturnClass(vamsas.objects.simple.WsJobId.class);
    oper.setReturnQName(new javax.xml.namespace.QName("", "cancelReturn"));
    oper.setStyle(org.apache.axis.constants.Style.RPC);
    oper.setUse(org.apache.axis.constants.Use.ENCODED);
    _operations[4] = oper;

  }

  public SeqSearchServiceSoapBindingStub() throws org.apache.axis.AxisFault
  {
    this(null);
  }

  public SeqSearchServiceSoapBindingStub(java.net.URL endpointURL,
          javax.xml.rpc.Service service) throws org.apache.axis.AxisFault
  {
    this(service);
    super.cachedEndpoint = endpointURL;
  }

  public SeqSearchServiceSoapBindingStub(javax.xml.rpc.Service service)
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
    qName = new javax.xml.namespace.QName("simple.objects.vamsas", "Result");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.Result.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName("simple.objects.vamsas",
            "WsJobId");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.WsJobId.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName("http://simple.objects.vamsas",
            "Object");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.Object.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName("vamsas", "ArrayOf_xsd_string");
    cachedSerQNames.add(qName);
    cls = java.lang.String[].class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(arraysf);
    cachedDeserFactories.add(arraydf);

    qName = new javax.xml.namespace.QName("simple.objects.vamsas",
            "Alignment");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.Alignment.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName("simple.objects.vamsas",
            "Sequence");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.Sequence.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName("vamsas", "ArrayOf_tns1_Sequence");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.Sequence[].class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(arraysf);
    cachedDeserFactories.add(arraydf);

    qName = new javax.xml.namespace.QName("simple.objects.vamsas",
            "SeqSearchResult");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.SeqSearchResult.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName("simple.objects.vamsas",
            "SequenceSet");
    cachedSerQNames.add(qName);
    cls = vamsas.objects.simple.SequenceSet.class;
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
          _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
          _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
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

  public java.lang.String getDatabase() throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[0]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("");
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName("vamsas",
            "getDatabase"));

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
        return (java.lang.String) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(
                _resp, java.lang.String.class);
      }
    }
  }

  public vamsas.objects.simple.SeqSearchResult getResult(
          java.lang.String job_id) throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[1]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("");
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName("vamsas",
            "getResult"));

    setRequestHeaders(_call);
    setAttachments(_call);
    java.lang.Object _resp = _call.invoke(new java.lang.Object[]
    { job_id });

    if (_resp instanceof java.rmi.RemoteException)
    {
      throw (java.rmi.RemoteException) _resp;
    }
    else
    {
      extractAttachments(_call);
      try
      {
        return (vamsas.objects.simple.SeqSearchResult) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (vamsas.objects.simple.SeqSearchResult) org.apache.axis.utils.JavaUtils
                .convert(_resp, vamsas.objects.simple.SeqSearchResult.class);
      }
    }
  }

  public vamsas.objects.simple.WsJobId psearch(
          vamsas.objects.simple.Alignment al, java.lang.String database)
          throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[2]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("");
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName("vamsas",
            "psearch"));

    setRequestHeaders(_call);
    setAttachments(_call);
    java.lang.Object _resp = _call.invoke(new java.lang.Object[]
    { al, database });

    if (_resp instanceof java.rmi.RemoteException)
    {
      throw (java.rmi.RemoteException) _resp;
    }
    else
    {
      extractAttachments(_call);
      try
      {
        return (vamsas.objects.simple.WsJobId) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (vamsas.objects.simple.WsJobId) org.apache.axis.utils.JavaUtils
                .convert(_resp, vamsas.objects.simple.WsJobId.class);
      }
    }
  }

  public vamsas.objects.simple.WsJobId search(
          vamsas.objects.simple.Sequence s, java.lang.String database)
          throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[3]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("");
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName("vamsas", "search"));

    setRequestHeaders(_call);
    setAttachments(_call);
    java.lang.Object _resp = _call.invoke(new java.lang.Object[]
    { s, database });

    if (_resp instanceof java.rmi.RemoteException)
    {
      throw (java.rmi.RemoteException) _resp;
    }
    else
    {
      extractAttachments(_call);
      try
      {
        return (vamsas.objects.simple.WsJobId) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (vamsas.objects.simple.WsJobId) org.apache.axis.utils.JavaUtils
                .convert(_resp, vamsas.objects.simple.WsJobId.class);
      }
    }
  }

  public vamsas.objects.simple.WsJobId cancel(java.lang.String jobId)
          throws java.rmi.RemoteException
  {
    if (super.cachedEndpoint == null)
    {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[4]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("");
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName("vamsas", "cancel"));

    setRequestHeaders(_call);
    setAttachments(_call);
    java.lang.Object _resp = _call.invoke(new java.lang.Object[]
    { jobId });

    if (_resp instanceof java.rmi.RemoteException)
    {
      throw (java.rmi.RemoteException) _resp;
    }
    else
    {
      extractAttachments(_call);
      try
      {
        return (vamsas.objects.simple.WsJobId) _resp;
      } catch (java.lang.Exception _exception)
      {
        return (vamsas.objects.simple.WsJobId) org.apache.axis.utils.JavaUtils
                .convert(_resp, vamsas.objects.simple.WsJobId.class);
      }
    }
  }

}
