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
package jalview.ws.rest.params;

import jalview.datamodel.AlignmentI;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.ws.params.OptionI;
import jalview.ws.params.simple.IntegerParameter;
import jalview.ws.params.simple.Option;
import jalview.ws.rest.AlignmentProcessor;
import jalview.ws.rest.InputType;
import jalview.ws.rest.NoValidInputDataException;
import jalview.ws.rest.RestJob;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * Represents the partitions defined on the alignment as indices e.g. for a
 * partition (A,B,C),(D,E),(F) The indices would be 3,2,1. Note, the alignment
 * must be ordered so groups are contiguous before this input type can be used.
 * 
 * @author JimP
 * 
 */
public class SeqGroupIndexVector extends InputType implements
        AlignmentProcessor
{
  public SeqGroupIndexVector()
  {
    super(new Class[]
    { AlignmentI.class });
  }

  /**
   * separator for list of sequence Indices - default is ','
   */
  public String sep = ",";

  /**
   * min size of each partition
   */
  public int minsize = 1;

  molType type;

  /**
   * prepare the context alignment for this input
   * 
   * @param al
   *          - alignment to be processed
   * @return al or a new alignment with appropriate attributes/order for input
   */
  public AlignmentI prepareAlignment(AlignmentI al)
  {
    jalview.analysis.AlignmentSorter.sortByGroup(al);
    return al;
  }

  @Override
  public ContentBody formatForInput(RestJob rj)
          throws UnsupportedEncodingException, NoValidInputDataException
  {
    StringBuffer idvector = new StringBuffer();
    boolean list = false;
    AlignmentI al = rj.getAlignmentForInput(token, type);
    // assume that alignment is properly ordered so groups form consecutive
    // blocks
    ArrayList<int[]> gl = new ArrayList<int[]>();
    int p = 0,lowest=al.getHeight(), highest=0;
    List<SequenceGroup> sgs;
    synchronized (sgs = al.getGroups())
    {
      for (SequenceGroup sg : sgs)
      {
        if (sg.getSize() < minsize)
        {
          throw new NoValidInputDataException("Group contains less than "
                  + minsize + " sequences.");
        }
        // TODO: refactor to sequenceGroup for efficiency -
        // getAlignmentRowInterval(AlignmentI al)
        int[] se = null;
        for (SequenceI sq : sg.getSequencesInOrder(al))
        {
          p = al.findIndex(sq);
          if (lowest>p)
          {
            lowest=p;
          }
          if (highest<p)
          {
            highest=p;
          }
          if (se == null)
          {
            se = new int[]
            { p, p };
          }
          else
          {
            if (p < se[0])
              se[0] = p;
            if (p > se[1])
              se[1] = p;
          }
        }
        if (se != null)
        {
          gl.add(se);
        }
      }
    }
    // are there any more sequences ungrouped that should be added as a single
    // remaining group ? - these might be at the start or the end
    if (gl.size() > 0)
    {
      if (lowest-1>minsize)
      {
        gl.add(0, new int[]
          { 0, lowest-2});
      }
      if ((al.getHeight()-1-highest)>minsize)
      {
        gl.add(new int[] { highest+1, al.getHeight()-1});
      }
    }
    else
    {
      gl.add(new int[]
      { 0, al.getHeight() - 1 });
    }
    if (min >= 0 && gl.size() < min)
    {
      throw new NoValidInputDataException(
              "Not enough sequence groups for input. Need at least " + min
                      + " groups (including ungrouped regions).");
    }
    if (max > 0 && gl.size() > max)
    {
      throw new NoValidInputDataException(
              "Too many sequence groups for input. Need at most " + max
                      + " groups (including ungrouped regions).");
    }
    int[][] vals = gl.toArray(new int[gl.size()][]);
    int[] srt = new int[gl.size()];
    for (int i = 0; i < vals.length; i++)
      srt[i] = vals[i][0];
    jalview.util.QuickSort.sort(srt, vals);
    list = false;
    int last = vals[0][0] - 1;
    for (int[] range : vals)
    {
      if (range[1] > last)
      {
        if (list)
        {
          idvector.append(sep);
        }
        idvector.append(range[1] - last);
        last = range[1];
        list = true;
      }
    }
    return new StringBody(idvector.toString());
  }

  /**
   * set minimum number of sequences allowed in a partition. Default is 1
   * sequence.
   * 
   * @param i
   *          (number greater than 1)
   */
  public void setMinsize(int i)
  {
    if (minsize >= 1)
    {
      minsize = i;
    }
    else
    {
      minsize = 1;
    }
  }

  @Override
  public List<String> getURLEncodedParameter()
  {
    ArrayList<String> prms = new ArrayList<String>();
    super.addBaseParams(prms);
    prms.add("minsize='" + minsize + "'");
    prms.add("sep='" + sep + "'");
    if (type != null)
    {
      prms.add("type='" + type + "'");
    }
    return prms;
  }

  @Override
  public String getURLtokenPrefix()
  {
    return "PARTITION";
  }

  @Override
  public boolean configureProperty(String tok, String val,
          StringBuffer warnings)
  {

    if (tok.startsWith("sep"))
    {
      sep = val;
      return true;
    }
    if (tok.startsWith("minsize"))
    {
      try
      {
        minsize = Integer.valueOf(val);
        if (minsize >= 0)
          return true;
      } catch (Exception x)
      {

      }
      warnings.append("Invalid minsize value '" + val
              + "'. Must be a positive integer.\n");
    }
    if (tok.startsWith("type"))
    {
      try
      {
        type = molType.valueOf(val);
        return true;
      } catch (Exception x)
      {
        warnings.append("Invalid molecule type '" + val
                + "'. Must be one of (");
        for (molType v : molType.values())
        {
          warnings.append(" " + v);
        }
        warnings.append(")\n");
      }
    }
    return false;
  }

  @Override
  public List<OptionI> getOptions()
  {
    List<OptionI> lst = getBaseOptions();
    lst.add(new Option("sep",
            "Separator character between elements of vector", true, ",",
            sep, Arrays.asList(new String[]
            { " ", ",", ";", "\t", "|" }), null));
    lst.add(new IntegerParameter("minsize",
            "Minimum size of partition allowed by service", true, 1,
            minsize, 1, 0));
    lst.add(createMolTypeOption("type", "Sequence type", false, type,
            molType.MIX));
    return lst;
  }

}
