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
package jalview.ws.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.AlignmentOrder;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.io.packed.JalviewDataset;
import jalview.ws.AWsJob;
import jalview.ws.rest.params.Alignment;
import jalview.ws.rest.params.SeqGroupIndexVector;

public class RestJob extends AWsJob
{

  // TODO: input alignmentview and other data for this job
  RestServiceDescription rsd;

  // boolean submitted;
  boolean gotresponse;

  boolean error;

  boolean waiting;

  boolean gotresult;

  Hashtable squniq;

  /**
   * dataset associated with this input data.
   */
  AlignmentI dsForIO;

  AlignmentOrder inputOrder;

  /**
   * context of input data with respect to an AlignmentView's visible contigs.
   */
  int[] origviscontig;

  private AlignmentI contextAl = null;

  /**
   * create a rest job using data bounded by the given start/end column.
   * 
   * @param addJobPane
   * @param restJobThread
   * @param _input
   * @param viscontigs
   *          visible contigs of an alignment view from which _input was derived
   */
  public RestJob(int jobNum, RestJobThread restJobThread,
          AlignmentI _input, int[] viscontigs)
  {
    rsd = restJobThread.restClient.service;
    jobnum = jobNum;
    if (viscontigs != null)
    {
      origviscontig = new int[viscontigs.length];
      System.arraycopy(viscontigs, 0, origviscontig, 0, viscontigs.length);
    }
    // get sequences for the alignmentI
    // get groups trimmed to alignment columns
    // get any annotation trimmed to start/end columns, too.
    squniq = jalview.analysis.SeqsetUtils.uniquify(_input.getSequencesArray(), true);
    // prepare input
    // form alignment+groups+annotation,preprocess and then record references
    // for formatters
    ArrayList<InputType> alinp = new ArrayList<InputType>();
    int paramsWithData = 0;
    // TODO: JAL-715 - generalise the following validation logic for all parameter types
    // we cheat for moment - since we know a-priori what data is available and
    // what inputs we have implemented so far
    for (Map.Entry<String, InputType> prm : rsd.inputParams.entrySet())
    {
      if (!prm.getValue().isConstant())
      {
        if (prm.getValue() instanceof Alignment)
        {
          alinp.add(prm.getValue());
        }
        else
        {
          if (prm.getValue() instanceof SeqGroupIndexVector
                  && _input.getGroups() != null
                  && _input.getGroups().size() >= -1 + prm.getValue().min)
          {
            // the test above is not rigorous but fixes JAL-1298, since submission will fail if the partition set doesn't contain at least one partition
            alinp.add(prm.getValue());
          }
          else
          {
            statMessage = ("Not enough groups defined on the alignment - need at least " + prm
                    .getValue().min);
          }
        }
      }
      else
      {
        paramsWithData++;
      }
    }
    if ((paramsWithData + alinp.size()) == rsd.inputParams.size())
    {
      inputOrder = new AlignmentOrder(_input);
      if ((dsForIO = _input.getDataset()) == null)
      {
        _input.setDataset(null);
      }
      dsForIO = _input.getDataset();
      if (contextAl == null)
      {
        contextAl = _input;
      }
      setAlignmentForInputs(alinp, _input);
      validInput = true;
    }
    else
    {
      // not enough data, so we bail.
      validInput = false;
    }
  }

  boolean validInput = false;

  @Override
  public boolean hasResults()
  {
    return gotresult && (parsedResults ? validJvresults : true);
  }

  @Override
  public boolean hasValidInput()
  {
    return validInput;
  }

  @Override
  public boolean isRunning()
  {
    return running; // TODO: can we check the response body for status messages
                    // ?
  }

  @Override
  public boolean isQueued()
  {
    return waiting;
  }

  @Override
  public boolean isFinished()
  {
    return resSet != null;
  }

  @Override
  public boolean isFailed()
  {
    // TODO logic for error
    return error;
  }

  @Override
  public boolean isBroken()
  {
    // TODO logic for error
    return error;
  }

  @Override
  public boolean isServerError()
  {
    // TODO logic for error
    return error;
  }

  @Override
  public boolean hasStatus()
  {
    return statMessage != null;
  }

  protected String statMessage = null;

  public HttpResultSet resSet;

  @Override
  public String getStatus()
  {
    return statMessage;
  }

  @Override
  public boolean hasResponse()
  {
    return statMessage != null || resSet != null;
  }

  @Override
  public void clearResponse()
  {
    // only clear the transient server response
    // statMessage=null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.AWsJob#getState()
   */
  @Override
  public String getState()
  {
    // TODO generate state string - prolly should have a default abstract method
    // for this
    return "Job is clueless";
  }

  public String getPostUrl()
  {

    // TODO Auto-generated method stub
    return rsd.postUrl;
  }

  public Set<Map.Entry<String, InputType>> getInputParams()
  {
    return rsd.inputParams.entrySet();
  }

  // return the URL that should be polled for this job
  public String getPollUrl()
  {
    return rsd.getDecoratedResultUrl(jobId);
  }

  /**
   * 
   * @return the context for parsing results from service
   */
  public JalviewDataset newJalviewDataset()
  {
    if (context == null)
    {
      context = new JalviewDataset(dsForIO, null, squniq, null);
      if (contextAl != null)
      {
        // TODO devise way of merging new annotation onto (identical) existing
        // annotation that was used as input
        // delete all input annotation
        if (contextAl.getAlignmentAnnotation() != null)
        {
          for (AlignmentAnnotation alan : contextAl
                  .getAlignmentAnnotation())
          {
            contextAl.deleteAnnotation(alan);
          }
        }
        // TODO devise way of merging new groups onto (identical) existing
        // groups when they were used as input to service
        // delete all existing groups
        if (contextAl.getGroups() != null)
        {
          contextAl.deleteAllGroups();
        }
        context.addAlignment(contextAl);
      }

    }
    return context;
  }

  /**
   * Extract list of sequence IDs for input parameter 'token' with given
   * molecule type
   * 
   * @param token
   * @param type
   * @return
   */
  public SequenceI[] getSequencesForInput(String token,
          InputType.molType type) throws NoValidInputDataException
  {
    Object sgdat = inputData.get(token);
    // can we form an alignment from this data ?
    if (sgdat == null)
    {
      throw new NoValidInputDataException(
              "No Sequence vector data bound to input '" + token
                      + "' for service at " + rsd.postUrl);
    }
    if (sgdat instanceof AlignmentI)
    {
      return ((AlignmentI) sgdat).getSequencesArray();
    }
    if (sgdat instanceof SequenceGroup)
    {
      return ((SequenceGroup) sgdat).getSequencesAsArray(null);
    }
    if (sgdat instanceof Vector)
    {
      if (((Vector) sgdat).size() > 0
              && ((Vector) sgdat).get(0) instanceof SequenceI)
      {
        SequenceI[] sq = new SequenceI[((Vector) sgdat).size()];
        ((Vector) sgdat).copyInto(sq);
        return sq;
      }
    }
    throw new NoValidInputDataException(
            "No Sequence vector data bound to input '" + token
                    + "' for service at " + rsd.postUrl);
  }

  /**
   * binding between input data (AlignmentI, SequenceGroup, NJTree) and input
   * param names.
   */
  private Hashtable<String, Object> inputData = new Hashtable<String, Object>();

  /**
   * is the job fully submitted to server and apparently in progress ?
   */
  public boolean running = false;

  /**
   * 
   * @param itypes
   * @param al
   *          - reference to object to be stored as input. Note - input data may
   *          be modifed by formatter
   */
  public void setAlignmentForInputs(Collection<InputType> itypes,
          AlignmentI al)
  {
    for (InputType itype : itypes)
    {
      if (!rsd.inputParams.values().contains(itype))
      {
        throw new IllegalArgumentException("InputType " + itype.getClass()
                + " is not valid for service at " + rsd.postUrl);
      }
      if (itype instanceof AlignmentProcessor)
      {
        ((AlignmentProcessor) itype).prepareAlignment(al);
      }
      // stash a reference for recall when the alignment data is formatted
      inputData.put(itype.token, al);
    }

  }

  /**
   * 
   * @param token
   * @param type
   * @return alignment object bound to the given token
   * @throws NoValidInputDataException
   */
  public AlignmentI getAlignmentForInput(String token,
          InputType.molType type) throws NoValidInputDataException
  {
    Object al = inputData.get(token);
    // can we form an alignment from this data ?
    if (al == null || !(al instanceof AlignmentI))
    {
      throw new NoValidInputDataException(
              "No alignment data bound to input '" + token
                      + "' for service at " + rsd.postUrl);
    }
    return (AlignmentI) al;
  }

  /**
   * test to see if the job has data of type cl that's needed for the job to run
   * 
   * @param cl
   * @return true or false
   */
  public boolean hasDataOfType(Class cl)
  {
    if (AlignmentI.class.isAssignableFrom(cl))
    {
      return true;
    }
    // TODO: add more source data types

    return false;
  }

  /**
   * context used to parse results from service
   */
  JalviewDataset context = null;

  protected boolean parsedResults = false;

  protected boolean validJvresults = false;

  Object[] jvresultobj = null;

  /**
   * process the results obtained from the server into jalview datamodel objects
   * ready to be merged/added to the users' view. Use hasResults to test if
   * results were added to context.
   */
  public void parseResultSet() throws Exception, Error
  {
    if (!parsedResults)
    {
      parsedResults = true;
      jvresultobj = resSet.parseResultSet();
      validJvresults = true;
    }
  }

  /**
   * 
   * @return true if job has an input alignment and it was annotated when
   *         results were parsed
   */
  public boolean isInputContextModified()
  {
    return contextAl != null && validJvresults
            && context.getAl().get(0).isModified();
  }

  /**
   * 
   * @return true if the ID/metadata for the input sequences were saved and
   *         sequence IDs renamed.
   */
  public boolean isInputUniquified()
  {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Return map between ordering of alignment submitted as input, and ordering
   * of alignment as provided by user
   * 
   * @return int[sequence index in submitted data]==sequence index in input.
   */
  public int[] getOrderMap()
  {
    SequenceI[] contseq = contextAl.getSequencesArray();
    int map[] = new int[contseq.length];
    for (int i = 0; i < contseq.length; i++)
    {
      // TODO: optimise for large N - build a lookup hash for IDs returning
      // order, and then lookup each sequ's original order
      map[i] = inputOrder.getOrder().indexOf(contseq[i]);
    }
    return map;
  }

}
