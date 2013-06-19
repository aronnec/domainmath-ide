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
/**
 * 
 */
package jalview.ws.jws2;

import jalview.bin.Cache;
import jalview.ws.jws2.jabaws2.Jws2Instance;

import java.util.HashSet;
import java.util.Set;

import compbio.data.msa.Category;
import compbio.data.msa.JABAService;
import compbio.ws.client.Jws2Client;
import compbio.ws.client.Services;

/**
 * @author JimP
 * 
 */
public class JabaWsServerQuery implements Runnable
{

  Jws2Discoverer jws2Discoverer = null;

  String jwsservers = null;

  boolean quit = false, running = false;

  /**
   * @return the running
   */
  public boolean isRunning()
  {
    return running;
  }

  /**
   * @param quit
   *          the quit to set
   */
  public void setQuit(boolean quit)
  {
    this.quit = quit;
  }

  public JabaWsServerQuery(Jws2Discoverer jws2Discoverer, String jwsservers)
  {
    this.jws2Discoverer = jws2Discoverer;
    this.jwsservers = jwsservers;
  }

  Services[] JABAWS1SERVERS = new Services[]
  { Services.ClustalWS, Services.MuscleWS, Services.MafftWS,
      Services.ProbconsWS, Services.TcoffeeWS };

  Services[] JABAWS2SERVERS = new Services[]
  { Services.ClustalWS, Services.MuscleWS, Services.MafftWS,
      Services.ProbconsWS, Services.TcoffeeWS, Services.AAConWS,
      Services.DisemblWS, Services.GlobPlotWS, Services.IUPredWS,
      Services.JronnWS };

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run()
  {
    running = true;
    try
    {
      if (Jws2Client.validURL(jwsservers))
      {
        compbio.data.msa.RegistryWS registry = null;
        Set svccategories = null;
        boolean noservices = true;
        // look for services
        boolean jabasws2 = false;
        // If we are dealing with a JABAWS2 service, then just go and ask the
        // JABAWS 2 service registry
        Set<Services> srv_set = new HashSet<Services>();

        Set<Category> categories = Category.getCategories();
        String svc_cat;

        try
        {
          // JBPNote: why is RegistryWS in compbio.data.msa ?
          registry = Jws2Client.connectToRegistry(jwsservers);
          if (registry != null)
          {
            // System.err.println("Test Services Output\n"
            // + registry.testAllServices());
            // TODO: enumerate services and test those that haven't been tested
            // in the last n-days/hours/etc.

            jabasws2 = true;
            srv_set = registry.getSupportedServices();
            svccategories = registry.getServiceCategories();

          }
        } catch (Exception ex)
        {
          System.err.println("Exception whilst trying to get at registry:");
          ex.printStackTrace();
          // if that failed, then we are probably working with a JABAWS1 server.
          // in that case, look for each service endpoint
          System.err.println("JWS2 Discoverer: " + jwsservers
                  + " is a JABAWS1 server. Using hardwired list.");
          for (Services srv : JABAWS1SERVERS)
          {
            srv_set.add(srv);
          }
        }
        for (Category cat : categories)
        {
          for (Services srv : cat.getServices())
          {
            if (quit)
            {
              running = false;
              return;
            }
            if (!srv_set.contains(srv))
            {
              continue;
            }
            JABAService service = null;
            try
            {
              service = Jws2Client.connect(jwsservers, srv);
            } catch (Exception e)
            {
              System.err.println("Jws2 Discoverer: Problem on "
                      + jwsservers + " with service " + srv + ":\n"
                      + e.getMessage());
              if (!(e instanceof javax.xml.ws.WebServiceException))
              {
                e.printStackTrace();
              }
              // For moment, report service as a problem.
              jws2Discoverer.addInvalidServiceUrl(jwsservers);
            }
            ;
            if (service != null)
            {
              noservices = false;
              Jws2Instance svc = null;
              if (registry != null)
              {

                String description = registry.getServiceDescription(srv);

                svc = new Jws2Instance(jwsservers, srv.toString(),
                        cat.name, description, service);
              }
              if (svc == null)
              {
                svc = new Jws2Instance(jwsservers, srv.toString(),
                        cat.name, "JABAWS 1 Alignment Service", service);
              }
              jws2Discoverer.addService(jwsservers, svc);
            }

          }
        }

        if (noservices)
        {
          jws2Discoverer.addUrlwithnoservices(jwsservers);
        }
      }
      else
      {
        jws2Discoverer.addInvalidServiceUrl(jwsservers);
        Cache.log.info("Ignoring invalid Jws2 service url " + jwsservers);
      }
    } catch (Exception e)
    {
      e.printStackTrace();
      Cache.log.warn("Exception when discovering Jws2 services.", e);
      jws2Discoverer.addInvalidServiceUrl(jwsservers);
    } catch (Error e)
    {
      Cache.log.error("Exception when discovering Jws2 services.", e);
      jws2Discoverer.addInvalidServiceUrl(jwsservers);
    }
    running = false;
  }

}
