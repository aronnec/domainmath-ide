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
package jalview.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import jalview.bin.Cache;
import jalview.io.JalviewFileChooser;
import jalview.ws.params.ParamDatastoreI;
import jalview.ws.params.ParamManager;
import jalview.ws.params.WsParamSetI;

/**
 * store and retrieve web service parameter sets.
 * 
 * @author JimP
 * 
 */
public class WsParamSetManager implements ParamManager
{
  Hashtable<String, ParamDatastoreI> paramparsers = new Hashtable<String, ParamDatastoreI>();

  @Override
  public WsParamSetI[] getParameterSet(String name, String serviceUrl,
          boolean modifiable, boolean unmodifiable)
  {
    String files = Cache.getProperty("WS_PARAM_FILES");
    if (files == null)
    {
      return null;
    }
    StringTokenizer st = new StringTokenizer(files, "|");
    String pfile = null;
    ArrayList<WsParamSetI> params = new ArrayList<WsParamSetI>();
    while (st.hasMoreTokens())
    {
      pfile = st.nextToken();
      try
      {
        WsParamSetI[] pset = parseParamFile(pfile);
        for (WsParamSetI p : pset)
        {
          boolean add = false;
          if (serviceUrl != null)
          {
            for (String url : p.getApplicableUrls())
            {
              if (url.equals(serviceUrl))
              {
                add = true;
              }
            }
          }
          else
          {
            add = true;
          }
          add &= (modifiable == p.isModifiable() || unmodifiable == !p
                  .isModifiable());
          add &= name == null || p.getName().equals(name);

          if (add)
          {

            params.add(p);
          }

        }
      } catch (IOException e)
      {
        Cache.log
                .info("Failed to parse parameter file "
                        + pfile
                        + " (Check that all JALVIEW_WSPARAMFILES entries are valid!)",
                        e);
      }
    }
    return params.toArray(new WsParamSetI[0]);
  }

  private WsParamSetI[] parseParamFile(String filename) throws IOException
  {
    List<WsParamSetI> psets = new ArrayList<WsParamSetI>();
    InputStreamReader is = new InputStreamReader(
            new java.io.FileInputStream(new File(filename)), "UTF-8");

    jalview.schemabinding.version2.WebServiceParameterSet wspset = new jalview.schemabinding.version2.WebServiceParameterSet();

    org.exolab.castor.xml.Unmarshaller unmar = new org.exolab.castor.xml.Unmarshaller(
            wspset);
    unmar.setWhitespacePreserve(true);
    try
    {
      wspset = (jalview.schemabinding.version2.WebServiceParameterSet) unmar
              .unmarshal(is);
    } catch (Exception ex)
    {
      throw new IOException(ex);
    }
    if (wspset != null && wspset.getParameters().length() > 0)
    {
      for (String url : wspset.getServiceURL())
      {
        ParamDatastoreI parser = paramparsers.get(url);
        if (parser != null)
        {
          WsParamSetI pset = parser.parseServiceParameterFile(
                  wspset.getName(), wspset.getDescription(),
                  wspset.getServiceURL(), wspset.getParameters());
          if (pset != null)
          {
            pset.setSourceFile(filename);
            psets.add(pset);
            break;
          }
        }
      }
    }

    return psets.toArray(new WsParamSetI[0]);
  }

  @Override
  public void storeParameterSet(WsParamSetI parameterSet)
  {
    String filename = parameterSet.getSourceFile();
    File outfile = null;
    try
    {
      if (filename != null && !((outfile = new File(filename)).canWrite()))
      {
        Cache.log.info("Can't write to " + filename
                + " - Prompting for new file to write to.");
        filename = null;
      }
    } catch (Exception e)
    {
      filename = null;
    }

    ParamDatastoreI parser = null;
    for (String urls : parameterSet.getApplicableUrls())
    {
      if (parser == null)
      {
        parser = paramparsers.get(urls);
      }
    }
    if (parser == null)
    {
      throw new Error(
              "Implementation error: Can't find a marshaller for the parameter set");
    }
    if (filename == null)
    {
      JalviewFileChooser chooser = new JalviewFileChooser(
              jalview.bin.Cache.getProperty("LAST_DIRECTORY"), new String[]
              { "wsparams" }, new String[]
              { "Web Service Parameter File" },
              "Web Service Parameter File");
      chooser.setFileView(new jalview.io.JalviewFileView());
      chooser.setDialogTitle("Choose a filename for this parameter file");
      chooser.setToolTipText("Save");
      int value = chooser.showSaveDialog(Desktop.instance);
      if (value == JalviewFileChooser.APPROVE_OPTION)
      {
        outfile = chooser.getSelectedFile();
        jalview.bin.Cache
                .setProperty("LAST_DIRECTORY", outfile.getParent());
        filename = outfile.getAbsolutePath();
        if (!filename.endsWith(".wsparams"))
        {
          filename = filename.concat(".wsparams");
          outfile = new File(filename);
        }
      }
    }
    if (outfile != null)
    {
      String paramFiles = jalview.bin.Cache.getDefault("WS_PARAM_FILES",
              filename);
      if (paramFiles.indexOf(filename) == -1)
      {
        if (paramFiles.length() > 0)
        {
          paramFiles = paramFiles.concat("|");
        }
        paramFiles = paramFiles.concat(filename);
      }
      jalview.bin.Cache.setProperty("WS_PARAM_FILES", paramFiles);

      jalview.schemabinding.version2.WebServiceParameterSet paramxml = new jalview.schemabinding.version2.WebServiceParameterSet();

      paramxml.setName(parameterSet.getName());
      paramxml.setDescription(parameterSet.getDescription());
      paramxml.setServiceURL(parameterSet.getApplicableUrls().clone());
      paramxml.setVersion("1.0");
      try
      {
        paramxml.setParameters(parser
                .generateServiceParameterFile(parameterSet));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(outfile), "UTF-8"));
        paramxml.marshal(out);
        out.close();
        parameterSet.setSourceFile(filename);
      } catch (Exception e)
      {
        Cache.log.error("Couldn't write parameter file to " + outfile, e);
      }
    }
  }

  /*
   * 
   * JalviewFileChooser chooser = new JalviewFileChooser(jalview.bin.Cache
   * .getProperty("LAST_DIRECTORY"), new String[] { "jc" }, new String[] {
   * "Jalview User Colours" }, "Jalview User Colours"); chooser.setFileView(new
   * jalview.io.JalviewFileView());
   * chooser.setDialogTitle("Load colour scheme");
   * chooser.setToolTipText("Load");
   * 
   * int value = chooser.showOpenDialog(this);
   * 
   * if (value == JalviewFileChooser.APPROVE_OPTION) { File choice =
   * chooser.getSelectedFile(); jalview.bin.Cache.setProperty("LAST_DIRECTORY",
   * choice.getParent()); String defaultColours = jalview.bin.Cache.getDefault(
   * "USER_DEFINED_COLOURS", choice.getPath()); if
   * (defaultColours.indexOf(choice.getPath()) == -1) { defaultColours =
   * defaultColours.concat("|") .concat(choice.getPath()); } (non-Javadoc)
   * 
   * @see
   * jalview.ws.params.ParamManager#deleteParameterSet(jalview.ws.params.WsParamSetI
   * )
   */
  @Override
  public void deleteParameterSet(WsParamSetI parameterSet)
  {
    String filename = parameterSet.getSourceFile();
    if (filename == null || filename.trim().length() < 1)
    {
      return;
    }
    String paramFiles = jalview.bin.Cache.getDefault("WS_PARAM_FILES", "");
    if (paramFiles.indexOf(filename) > -1)
    {
      String nparamFiles = new String();
      StringTokenizer st = new StringTokenizer(paramFiles, "|");
      while (st.hasMoreElements())
      {
        String fl = st.nextToken();
        if (!fl.equals(filename))
        {
          nparamFiles = nparamFiles.concat("|").concat(fl);
        }
      }
      jalview.bin.Cache.setProperty("WS_PARAM_FILES", nparamFiles);
    }

    try
    {
      File pfile = new File(filename);
      if (pfile.exists() && pfile.canWrite())
      {
        if (JOptionPane.showConfirmDialog(Desktop.instance,
                "Delete the preset's file, too ?", "Delete User Preset ?",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
          pfile.delete();
        }
      }
    } catch (Exception e)
    {
      Cache.log
              .error("Exception when trying to delete webservice user preset: ",
                      e);
    }
  }

  @Override
  public void registerParser(String hosturl, ParamDatastoreI paramdataStore)
  {
    paramparsers.put(hosturl, paramdataStore);
  }

}
