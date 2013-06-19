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
package jalview.io.packed;

import jalview.datamodel.AlignmentI;
import jalview.io.AppletFormatAdapter;
import jalview.io.FileParse;
import jalview.io.FormatAdapter;
import jalview.io.IdentifyFile;
import jalview.io.packed.DataProvider.JvDataType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ParsePackedSet
{

  /**
   * return results as a series of jalview.datamodel objects suitable for
   * display
   * 
   * @param context
   *          - context which is updated with new data
   * @param files
   *          - source data
   * @return list of data objects added to context
   * @throws Exception
   */
  public Object[] getAlignment(JalviewDataset context,
          Iterable<DataProvider> files) throws Exception
  {
    List<Object> rslt = new ArrayList<Object>();
    if (context == null)
    {
      context = new JalviewDataset();
    }
    boolean deuniquify = false;
    for (DataProvider dta : files)
    {
      Exception exerror = null;
      String errmsg = null;
      FileParse src = dta.getDataSource();
      if (dta.getType().equals(DataProvider.JvDataType.ALIGNMENT))
      {
        String fmt = null;
        try
        {
          fmt = new IdentifyFile().Identify(src, false);
        } catch (Exception ex)
        {
          exerror = ex;
          errmsg = "Couldn't identify alignment format.";
        }

        if (fmt != null)
        {
          if (!FormatAdapter.isValidIOFormat(fmt, false))
          {
            errmsg = fmt;
            exerror = null;
          }
          else
          {
            // parse the alignment
            AlignmentI al = null;
            try
            {
              al = new FormatAdapter().readFromFile(src, fmt);
            } catch (Exception e)
            {
              errmsg = "Failed to parse alignment from result set";
              exerror = e;
            }
            if (al != null)
            {
              // deuniquify and construct/merge additional dataset entries if
              // necessary.
              context.addAlignment(al);
              context.updateSetModified(true);
              rslt.add(al);
              deuniquify = true;
            }
          }
        }
      }
      if (dta.getType().equals(JvDataType.ANNOTATION))
      {
        if (!context.hasAlignments())
        {
          errmsg = "No alignment or sequence dataset to associate annotation with.";
          // could duplicate the dataset reference here as default behaviour for
          // sequence associated annotation ?
        }
        try
        {
          BufferedReader br;
          if (src.getReader() instanceof BufferedReader)
          {
            br = (BufferedReader) src.getReader();
          }
          else
          {
            br = new BufferedReader(src.getReader());
          }
          if (new jalview.io.AnnotationFile().parseAnnotationFrom(
                  context.getLastAlignment(), br))
          {
            context.updateSetModified(true);
          }
          else
          {
            errmsg = "Annotation file contained no data.";
          }

        } catch (Exception e)
        {
          errmsg = ((errmsg == null) ? "" : errmsg)
                  + "Failed to parse the annotation file associated with the alignment.";
          exerror = e;
        }
      }
      if (dta.getType().equals(JvDataType.SEQASSOCATED))
      {
        if (!context.hasSequenceAssoc())
        {
          errmsg = "No sequence to associate data with.";

        }
        errmsg = "parsing of sequence associated data is not implemented";
        exerror = new Exception(errmsg);
      }
      if (dta.getType().equals(JvDataType.FEATURES))
      {
        // check the context has a place to store feature rendering definitions,
        // if not, create one.
        if (context.featureColours == null)
        {
          context.featureColours = new Hashtable();
        }
        try
        {
          jalview.io.FeaturesFile ff = new jalview.io.FeaturesFile(src);
          context.updateSetModified(ff.parse(context.getLastAlignment(),
                  context.featureColours, false, context.relaxedIdMatching));
        } catch (Exception e)
        {
          errmsg = ("Failed to parse the Features file associated with the alignment.");
          exerror = e;
        }
      }
      if (dta.getType().equals(JvDataType.TREE))
      {
        try
        {
          jalview.io.NewickFile nf = new jalview.io.NewickFile(src);
          if (!nf.isValid())
          {
            nf.close();
            nf = null;
          }
          else
          {
            // do association to current alignment.

            context.addTreeFromFile(nf);
            rslt.add(nf);
            context.updateSetModified(true);
          }
        } catch (Exception e)
        {
          errmsg = ("Failed to parse the treeFile associated with the result.");
          exerror = e;
        }

      }
      if (exerror != null)
      {
        if (errmsg != null && errmsg.length() > 0)
        {
          throw new IOException(errmsg, exerror);
        }
        else
        {
          throw new IOException(errmsg, exerror);
        }
      }
      else
      {
        if (errmsg != null && errmsg.length() > 0)
        {
          throw new IOException(errmsg);
        }
      }
    }
    if (deuniquify)
    {
      context.getLastAlignmentSet().deuniquifyAlignment();
    }
    return rslt.toArray();
  }

  /**
   * simple command line test. Arguments should be one or more pairs of
   * <DataProvider.JvDataType> <Filename> arguments. The routine will attempt to
   * read each source in turn, and report what kind of Jalview datamodel objects
   * would be created.
   * 
   * @param args
   */
  public static void main(String args[])
  {
    // make data providers from the set of keys/files
    int i = 0;
    List<DataProvider> dp = new ArrayList<DataProvider>();
    while ((i + 1) < args.length)
    {
      String type = args[i++];
      final String file = args[i++];
      final JvDataType jtype = DataProvider.JvDataType.valueOf(type
              .toUpperCase());
      if (jtype != null)
      {
        final FileParse fp;
        try
        {
          fp = new FileParse(file, AppletFormatAdapter.checkProtocol(file));
        } catch (Exception e)
        {
          System.err.println("Couldn't handle datasource of type " + jtype
                  + " using URI " + file);
          e.printStackTrace();
          return;
        }
        dp.add(new SimpleDataProvider(jtype, fp, null));
      }
      else
      {
        System.out.println("Couldn't parse source type token '"
                + type.toUpperCase() + "'");
      }
    }
    if (i < args.length)
    {
      System.out.print("** WARNING\nIgnoring unused arguments:\n");
      while (i < args.length)
      {
        System.out.print(" " + args[i]);
      }
      System.out.print("\n");

    }
    System.out.println("Now trying to parse set:");
    JalviewDataset context;
    Object[] newdm;
    ParsePackedSet pps;
    try
    {
      newdm = (pps = new ParsePackedSet()).getAlignment(
              context = new JalviewDataset(), dp);
    } catch (Exception e)
    {
      System.out.println("Test failed for these arguments.\n");
      e.printStackTrace(System.out);
      return;
    }
    if (newdm != null)
    {
      for (Object o : newdm)
      {
        System.out.println("Will need to create an " + o.getClass());
      }

      // now test uniquify/deuniquify stuff
      // uniquify alignment and write alignment, annotation, features, and trees
      // to buffers.
      // import with deuniquify info, and compare results to input.

    }
    else
    {
      if (context.getLastAlignmentSet().isModified())
      {
        System.err
                .println("Initial alignment set was modified and any associated views should be updated.");
      }
    }
  }
}
