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
package jalview.ws.dbsources.das.datamodel;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import org.biodas.jdas.client.ConnectionPropertyProviderI;
import org.biodas.jdas.client.SourcesClient;
import org.biodas.jdas.client.threads.MultipleConnectionPropertyProviderI;
import org.biodas.jdas.dassources.Capabilities;
import org.biodas.jdas.schema.sources.CAPABILITY;
import org.biodas.jdas.schema.sources.SOURCE;
import org.biodas.jdas.schema.sources.SOURCES;
import org.biodas.jdas.schema.sources.VERSION;

import jalview.bin.Cache;
import jalview.ws.dbsources.das.api.DasSourceRegistryI;
import jalview.ws.dbsources.das.api.jalviewSourceI;

/**
 *
 */
public class DasSourceRegistry implements DasSourceRegistryI,
        MultipleConnectionPropertyProviderI
{
  // private org.biodas.jdas.schema.sources.SOURCE[] dasSources = null;
  private List<jalviewSourceI> dasSources = null;

  private Hashtable<String, jalviewSourceI> sourceNames = null;

  private Hashtable<String, jalviewSourceI> localSources = null;

  public static String DEFAULT_REGISTRY = "http://www.dasregistry.org/das/";

  /**
   * true if thread is running and we are talking to DAS registry service
   */
  private boolean loadingDasSources = false;

  public boolean isLoadingDasSources()
  {
    return loadingDasSources;
  }

  public String getDasRegistryURL()
  {
    String registry = jalview.bin.Cache.getDefault("DAS_REGISTRY_URL",
            DEFAULT_REGISTRY);

    if (registry.indexOf("/registry/das1/sources/") > -1)
    {
      jalview.bin.Cache.setProperty(jalview.bin.Cache.DAS_REGISTRY_URL,
              DEFAULT_REGISTRY);
      registry = DEFAULT_REGISTRY;
    }
    if (registry.lastIndexOf("sources.xml") == registry.length() - 11)
    {
      // no trailing sources.xml document for registry in JDAS
      jalview.bin.Cache.setProperty(
              jalview.bin.Cache.DAS_REGISTRY_URL,
              registry = registry.substring(0,
                      registry.lastIndexOf("sources.xml")));
    }
    return registry;
  }

  /**
   * query the default DAS Source Registry for sources. Uses value of jalview
   * property DAS_REGISTRY_URL and the DasSourceBrowser.DEFAULT_REGISTRY if that
   * doesn't exist.
   * 
   * @return list of sources
   */
  private List<jalviewSourceI> getDASSources()
  {

    return getDASSources(getDasRegistryURL(), this);
  }

  /**
   * query the given URL for DasSources.
   * 
   * @param registryURL
   *          return sources from registryURL
   */
  private static List<jalviewSourceI> getDASSources(String registryURL,
          MultipleConnectionPropertyProviderI registry)
  {
    try
    {
      URL url = new URL(registryURL);
      org.biodas.jdas.client.SourcesClientInterface client = new SourcesClient();

      SOURCES sources = client.fetchDataRegistry(registryURL, null, null,
              null, null, null, null);

      List<SOURCE> dassources = sources.getSOURCE();
      ArrayList<jalviewSourceI> dsrc = new ArrayList<jalviewSourceI>();
      HashMap<String, Integer> latests = new HashMap<String, Integer>();
      Integer latest;
      for (SOURCE src : dassources)
      {
        JalviewSource jsrc = new JalviewSource(src, registry, false);
        latest = latests.get(jsrc.getSourceURL());
        if (latest != null)
        {
          if (jsrc.isNewerThan(dsrc.get(latest.intValue())))
          {
            dsrc.set(latest.intValue(), jsrc);
          }
          else
          {
            System.out.println("Debug: Ignored older source "
                    + jsrc.getTitle());
          }
        }
        else
        {
          latests.put(jsrc.getSourceURL(), Integer.valueOf(dsrc.size()));
          dsrc.add(jsrc);
        }
      }
      return dsrc;
    } catch (Exception ex)
    {
      System.err.println("Failed to contact DAS1 registry at "
              + registryURL);
      ex.printStackTrace();
      return new ArrayList<jalviewSourceI>();
    }
  }

  public void run()
  {
    getSources();
  }

  @Override
  public List<jalviewSourceI> getSources()
  {
    if (dasSources == null)
    {
      dasSources = getDASSources();
    }
    return appendLocalSources();
  }

  /**
   * generate Sources from the local das source list
   * 
   */
  private void addLocalDasSources()
  {
    if (localSources == null)
    {
      // get local sources from properties and initialise the local source list
      String local = jalview.bin.Cache.getProperty("DAS_LOCAL_SOURCE");
      if (local != null)
      {
        StringTokenizer st = new StringTokenizer(local, "\t");
        while (st.hasMoreTokens())
        {
          String token = st.nextToken();
          int bar = token.indexOf("|");
          String url = token.substring(bar + 1);
          boolean features = true, sequence = false;
          if (url.startsWith("sequence:"))
          {
            url = url.substring(9);
            // this source also serves sequences as well as features
            sequence = true;
          }
          createLocalSource(url, token.substring(0, bar), sequence,
                  features);
        }
      }
    }
  }

  private List<jalviewSourceI> appendLocalSources()
  {
    List<jalviewSourceI> srclist = new ArrayList<jalviewSourceI>();
    addLocalDasSources();
    sourceNames = new Hashtable<String, jalviewSourceI>();
    if (dasSources != null)
    {
      for (jalviewSourceI src : dasSources)
      {
        sourceNames.put(src.getTitle(), src);
        srclist.add(src);
      }
    }

    if (localSources == null)
    {
      return srclist;
    }
    Enumeration en = localSources.keys();
    while (en.hasMoreElements())
    {
      String key = en.nextElement().toString();
      jalviewSourceI jvsrc = localSources.get(key);
      sourceNames.put(key, jvsrc);
      srclist.add(jvsrc);
    }
    return srclist;
  }

  /*
 * 
 */

  @Override
  public jalviewSourceI createLocalSource(String url, String name,
          boolean sequence, boolean features)
  {
    SOURCE local = _createLocalSource(url, name, sequence, features);

    if (localSources == null)
    {
      localSources = new Hashtable<String, jalviewSourceI>();
    }
    jalviewSourceI src = new JalviewSource(local, this, true);
    localSources.put(local.getTitle(), src);
    return src;
  }

  private SOURCE _createLocalSource(String url, String name,
          boolean sequence, boolean features)
  {
    SOURCE local = new SOURCE();

    local.setUri(url);
    local.setTitle(name);
    local.setVERSION(new ArrayList<VERSION>());
    VERSION v = new VERSION();
    List<CAPABILITY> cp = new ArrayList<CAPABILITY>();
    if (sequence)
    {
      /*
       * Could try and synthesize a coordinate system for the source if needbe
       * COORDINATES coord = new COORDINATES(); coord.setAuthority("NCBI");
       * coord.setSource("Chromosome"); coord.setTaxid("9606");
       * coord.setVersion("35"); version.getCOORDINATES().add(coord);
       */
      CAPABILITY cap = new CAPABILITY();
      cap.setType("das1:" + Capabilities.SEQUENCE.getName());
      cap.setQueryUri(url + "/sequence");
      cp.add(cap);
    }
    if (features)
    {
      CAPABILITY cap = new CAPABILITY();
      cap.setType("das1:" + Capabilities.FEATURES.getName());
      cap.setQueryUri(url + "/features");
      cp.add(cap);
    }

    v.getCAPABILITY().addAll(cp);
    local.getVERSION().add(v);

    return local;
  }

  @Override
  public jalviewSourceI getSource(String nickname)
  {
    return sourceNames.get(nickname);
  }

  @Override
  public boolean removeLocalSource(jalviewSourceI source)
  {
    if (localSources.containsValue(source))
    {
      localSources.remove(source.getTitle());
      sourceNames.remove(source.getTitle());
      dasSources.remove(source);
      jalview.bin.Cache.setProperty("DAS_LOCAL_SOURCE",
              getLocalSourceString());

      return true;
    }
    return false;
  }

  @Override
  public void refreshSources()
  {
    dasSources = null;
    sourceNames = null;
    run();
  }

  @Override
  public List<jalviewSourceI> resolveSourceNicknames(List<String> sources)
  {
    ArrayList<jalviewSourceI> resolved = new ArrayList<jalviewSourceI>();
    if (sourceNames != null)
    {
      for (String src : sources)
      {
        jalviewSourceI dsrc = sourceNames.get(src);
        if (dsrc != null)
        {
          resolved.add(dsrc);
        }
      }
    }
    return resolved;
  }

  @Override
  public String getLocalSourceString()
  {
    if (localSources != null)
    {
      StringBuffer sb = new StringBuffer();
      Enumeration en = localSources.keys();
      while (en.hasMoreElements())
      {
        String token = en.nextElement().toString();
        jalviewSourceI srco = localSources.get(token);
        sb.append(token + "|"
                + (srco.isSequenceSource() ? "sequence:" : "")
                + srco.getUri() + "\t");
      }
      return sb.toString();
    }
    return "";
  }

  private static final Hashtable<URL, String> authStash;
  static
  {
    authStash = new Hashtable<URL, String>();

    try
    {
      // TODO: allow same credentials for https and http
      authStash.put(new URL(
              "http://www.compbio.dundee.ac.uk/geneweb/das/myseq/"),
              "Basic SmltOm1pSg==");
    } catch (MalformedURLException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public MultipleConnectionPropertyProviderI getSessionHandler()
  {
    return this;
  }

  @Override
  public ConnectionPropertyProviderI getConnectionPropertyProviderFor(
          String arg0)
  {

    final ConnectionPropertyProviderI conprov = new ConnectionPropertyProviderI()
    {
      boolean authed = false;

      @Override
      public void setConnectionProperties(HttpURLConnection connection)
      {
        String auth = authStash.get(connection.getURL());
        if (auth != null && auth.length() > 0)
        {
          connection.setRequestProperty("Authorisation", auth);
          authed = true;
        }
        else
        {
          authed = false;
        }
      }

      @Override
      public boolean getResponseProperties(HttpURLConnection connection)
      {
        String auth = authStash.get(connection.getURL());
        if (auth != null && auth.length() == 0)
        {
          // don't attempt to check if we authed or not - user entered empty
          // password
          return false;
        }
        if (!authed)
        {
          if (auth != null)
          {
            // try and pass credentials.
            return true;
          }
          // see if we should try and create a new auth record.
          String ameth = connection.getHeaderField("X-DAS-AuthMethods");
          Cache.log.debug("Could authenticate to " + connection.getURL()
                  + " with : " + ameth);
          // TODO: search auth string and raise login box - return if auth was
          // provided
          return false;
        }
        else
        {
          // check to see if auth was successful
          String asuc = connection
                  .getHeaderField("X-DAS_AuthenticatedUser");
          if (asuc != null && asuc.trim().length() > 0)
          {
            // authentication was successful
            Cache.log.debug("Authenticated successfully to "
                    + connection.getURL().toString());
            return false;
          }
          // it wasn't - so we should tell the user it failed and ask if they
          // want to attempt authentication again.
          authStash.remove(connection.getURL());
          // open a new login/password dialog with cancel button
          // set new authStash content with password and return true
          return true; //
          // User cancelled auth - so put empty string in stash to indicate we
          // don't want to auth with this server.
          // authStash.put(connection.getURL(), "");
          // return false;
        }
      }
    };
    return conprov;
  }

}
