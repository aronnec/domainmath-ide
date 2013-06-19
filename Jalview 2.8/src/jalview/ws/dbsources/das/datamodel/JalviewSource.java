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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.biodas.jdas.client.threads.MultipleConnectionPropertyProviderI;
import org.biodas.jdas.dassources.Capabilities;
import org.biodas.jdas.dassources.utils.DasTimeFormat;
import org.biodas.jdas.schema.sources.CAPABILITY;
import org.biodas.jdas.schema.sources.COORDINATES;
import org.biodas.jdas.schema.sources.MAINTAINER;
import org.biodas.jdas.schema.sources.PROP;
import org.biodas.jdas.schema.sources.SOURCE;
import org.biodas.jdas.schema.sources.VERSION;

import jalview.ws.dbsources.das.api.jalviewSourceI;
import jalview.ws.seqfetcher.DbSourceProxy;

public class JalviewSource implements jalviewSourceI
{
  SOURCE source;

  MultipleConnectionPropertyProviderI connprov;

  public JalviewSource(SOURCE local2,
          MultipleConnectionPropertyProviderI connprov, boolean local)
  {
    this.connprov = connprov;
    this.local = local;
    source = local2;
  }

  @Override
  public String getTitle()
  {
    return source.getTitle();
  }

  @Override
  public VERSION getVersion()
  {

    return getVersionFor(source);
  }

  @Override
  public String getDocHref()
  {
    return source.getDocHref();
  }

  @Override
  public String getDescription()
  {
    return source.getDescription();
  }

  @Override
  public String getUri()
  {
    return source.getUri();
  }

  @Override
  public MAINTAINER getMAINTAINER()
  {
    return source.getMAINTAINER();
  }

  @Override
  public String getEmail()
  {
    return (local) ? null : source.getMAINTAINER().getEmail();
  }

  boolean local = false;

  @Override
  public boolean isLocal()
  {
    return local;
  }

  @Override
  public boolean isSequenceSource()
  {
    String seqcap = "das1:" + Capabilities.SEQUENCE.getName();
    for (String cp : getCapabilityList(getVersionFor(source)))
    {
      if (cp.equals(seqcap))
      {
        return true;

      }
    }
    return false;
  }

  @Override
  public boolean isFeatureSource()
  {
    String seqcap = "das1:" + Capabilities.FEATURES.getName();
    for (String cp : getCapabilityList(getVersionFor(source)))
    {
      if (cp.equals(seqcap))
      {
        return true;

      }
    }
    return false;
  }

  private VERSION getVersionFor(SOURCE ds)
  {
    VERSION latest = null;
    for (VERSION v : ds.getVERSION())
    {
      if (latest == null
              || isLaterThan(latest.getCreated(), v.getCreated()))
      {
        // TODO: das 1.6 - should just get the first version - ignore other
        // versions since not specified how to construct URL from version's URI
        // + source URI
        latest = v;
      }
    }
    return latest;
  }

  /**
   * compare date strings. null or unparseable dates are assumed to be oldest
   * 
   * @param ref
   * @param newer
   * @return true iff ref comes before newer
   */
  private boolean isLaterThan(String ref, String newer)
  {
    Date refdate = null, newdate = null;
    if (ref != null && ref.trim().length() > 0)
    {
      try
      {
        refdate = DasTimeFormat.fromDASString(ref.trim());

      } catch (ParseException x)
      {
      }
    }
    if (newer != null && newer.trim().length() > 0)
    {
      try
      {
        newdate = DasTimeFormat.fromDASString(newer);
      } catch (ParseException e)
      {
      }
    }
    if (refdate != null)
    {
      if (newdate != null)
      {
        return refdate.before(newdate);
      }
      return false;
    }
    if (newdate != null)
    {
      return true;
    }
    // assume first instance of source is newest in list. - TODO: check if
    // natural ordering of source versions is newest first or oldest first
    return false;
  }

  public String[] getLabelsFor(VERSION v)
  {
    ArrayList<String> labels = new ArrayList<String>();
    for (PROP p : v.getPROP())
    {
      if (p.getName().equalsIgnoreCase("LABEL"))
      {
        labels.add(p.getValue());
      }
    }
    return labels.toArray(new String[0]);
  }

  private CAPABILITY getCapability(Capabilities capability)
  {
    for (CAPABILITY p : getVersion().getCAPABILITY())
    {
      if (p.getType().equalsIgnoreCase(capability.getName())
              || p.getType().equalsIgnoreCase(
                      "das1:" + capability.getName()))
      {
        return p;
      }
    }
    return null;
  }

  public String[] getCapabilityList(VERSION v)
  {

    ArrayList<String> labels = new ArrayList<String>();
    for (CAPABILITY p : v.getCAPABILITY())
    {
      // TODO: work out what to do with namespace prefix
      // does SEQUENCE == das1:SEQUENCE and das2:SEQUENCE ?
      // for moment, just show all capabilities...
      if (p.getType().startsWith("das1:"))
      {
        labels.add(p.getType());
      }
    }
    return labels.toArray(new String[0]);
  }

  @Override
  public List<DbSourceProxy> getSequenceSourceProxies()
  {
    if (!isSequenceSource())
    {
      return null;
    }
    ArrayList<DbSourceProxy> seqsources = new ArrayList<DbSourceProxy>();
    if (!local)
    {
      VERSION v = getVersion();
      Map<String, COORDINATES> latestc = new Hashtable<String, COORDINATES>();
      for (COORDINATES cs : v.getCOORDINATES())
      {
        COORDINATES ltst = latestc.get(cs.getUri());
        if (ltst == null
                || ltst.getVersion() == null
                || (ltst.getVersion() != null && cs.getVersion() != null && isLaterThan(
                        ltst.getVersion(), cs.getVersion())))
        {
          latestc.put(cs.getUri(), cs);
        }
      }
      for (COORDINATES cs : latestc.values())
      {
        DasSequenceSource ds;
        /*
         * if (css == null || css.length == 0) { // TODO: query das source
         * directly to identify coordinate system... or // have to make up a
         * coordinate system css = new DasCoordinateSystem[] { new
         * DasCoordinateSystem() }; css[0].setName(d1s.getNickname());
         * css[0].setUniqueId(d1s.getNickname()); } for (int c = 0; c <
         * css.length; c++) {
         */
        try
        {
          seqsources.add(ds = new DasSequenceSource(getTitle() + " ("
                  + cs.getAuthority() + " " + cs.getSource()
                  + (cs.getVersion() != null ? " " + cs.getVersion() : "")
                  + ")", cs.getAuthority(), source, v, cs, connprov));
          if (seqsources.size() > 1)
          {
            System.err.println("Added another sequence DB source for "
                    + getTitle() + " (" + ds.getDbName() + ")");
          }
        } catch (Exception e)
        {
          System.err.println("Ignoring sequence coord system " + cs + " ("
                  + cs.getContent() + ") for source " + getTitle()
                  + "- threw exception when constructing fetcher.\n");
          e.printStackTrace();
        }
      }
    }
    else
    {
      try
      {
        seqsources.add(new DasSequenceSource(getTitle(), getTitle(),
                source, getVersion(), null, connprov));
      } catch (Exception e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    if (seqsources.size() > 1)
    {
      // sort by name
      DbSourceProxy[] tsort = seqsources.toArray(new DasSequenceSource[0]);
      String[] nm = new String[tsort.length];
      for (int i = 0; i < nm.length; i++)
      {
        nm[i] = tsort[i].getDbName().toLowerCase();
      }
      jalview.util.QuickSort.sort(nm, tsort);
      seqsources.clear();
      for (DbSourceProxy ssrc : tsort)
      {
        seqsources.add(ssrc);
      }
    }
    return seqsources;
  }

  @Override
  public String getSourceURL()
  {
    try
    {
      // kind of dumb, since
      // org.biodas.jdas.dassources.utils.VersionAdapter.getSourceUriFromQueryUri()
      // does this,
      // but this way, we can access non DAS 1.6 compliant sources (which have
      // to have a URL like <sourcename>/das/ and cause a validation exception)

      for (CAPABILITY cap : getVersion().getCAPABILITY())
      {
        String capname = cap.getType().substring(
                cap.getType().indexOf(":") + 1);
        int p = cap.getQueryUri().lastIndexOf(capname);
        if (p < -1)
        {
          throw new Exception("Invalid das source: " + source.getUri());
        }
        if (cap.getQueryUri().charAt(p) == '/')
        {
          p--;
        }
        return cap.getQueryUri().substring(0, p);
      }
    } catch (Exception x)
    {
      System.err.println("Serious: Couldn't get the URL for source "
              + source.getTitle());
      x.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean isNewerThan(jalviewSourceI other)
  {
    return isLaterThan(getVersion().getCreated(), other.getVersion()
            .getCreated());
  }

  @Override
  public boolean isReferenceSource()
  {
    // TODO check source object for indication that we are the primary for a DAS coordinate system
    return false;
  }
}
