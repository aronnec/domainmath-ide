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
package jalview.ws.jws2;

import jalview.analysis.AlignSeq;
import jalview.analysis.SeqsetUtils;
import jalview.api.AlignViewportI;
import jalview.api.AlignmentViewPanel;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.Annotation;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.gui.IProgressIndicator;
import jalview.workers.AlignCalcWorker;
import jalview.ws.jws2.dm.JabaWsParamSet;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.WsParamSetI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import compbio.data.msa.SequenceAnnotation;
import compbio.data.sequence.FastaSequence;
import compbio.data.sequence.Score;
import compbio.data.sequence.ScoreManager;
import compbio.metadata.Argument;
import compbio.metadata.ChunkHolder;
import compbio.metadata.JobStatus;
import compbio.metadata.JobSubmissionException;
import compbio.metadata.Option;
import compbio.metadata.ResultNotAvailableException;
import compbio.metadata.WrongParameterException;

public abstract class JabawsAlignCalcWorker extends AlignCalcWorker
{
  Jws2Instance service;

  @SuppressWarnings("unchecked")
  protected SequenceAnnotation aaservice;

  protected ScoreManager scoremanager;

  protected WsParamSetI preset;

  protected List<Argument> arguments;

  public JabawsAlignCalcWorker(AlignViewportI alignViewport,
          AlignmentViewPanel alignPanel)
  {
    super(alignViewport, alignPanel);
  }

  IProgressIndicator guiProgress;

  public JabawsAlignCalcWorker(Jws2Instance service, AlignFrame alignFrame,
          WsParamSetI preset, List<Argument> paramset)
  {
    this(alignFrame.getCurrentView(), alignFrame.alignPanel);
    this.guiProgress = alignFrame;
    this.preset = preset;
    this.arguments = paramset;
    this.service = service;
    aaservice = (SequenceAnnotation) service.service;

  }

  public WsParamSetI getPreset()
  {
    return preset;
  }

  public List<Argument> getArguments()
  {
    return arguments;
  }

  /**
   * reconfigure and restart the AAConClient. This method will spawn a new
   * thread that will wait until any current jobs are finished, modify the
   * parameters and restart the conservation calculation with the new values.
   * 
   * @param newpreset
   * @param newarguments
   */
  public void updateParameters(final WsParamSetI newpreset,
          final List<Argument> newarguments)
  {
    preset = newpreset;
    arguments = newarguments;
    calcMan.startWorker(this);
  }

  public List<Option> getJabaArguments()
  {
    List<Option> newargs = new ArrayList<Option>();
    if (preset != null && preset instanceof JabaWsParamSet)
    {
      newargs.addAll(((JabaWsParamSet) preset).getjabaArguments());
    }
    if (arguments != null && arguments.size() > 0)
    {
      for (Argument rg : arguments)
      {
        if (Option.class.isAssignableFrom(rg.getClass()))
        {
          newargs.add((Option) rg);
        }
      }
    }
    return newargs;
  }

  @Override
  public void run()
  {
    if (aaservice == null)
    {
      return;
    }
    long progressId = -1;

    int serverErrorsLeft = 3;

    String rslt = "JOB NOT DEFINED";
    StringBuffer msg = new StringBuffer();
    try
    {
      if (checkDone())
      {
        return;
      }
      List<compbio.data.sequence.FastaSequence> seqs = getInputSequences(alignViewport
              .getAlignment());

      if (seqs == null)
      {
        calcMan.workerComplete(this);
        return;
      }

      AlignmentAnnotation[] aa = alignViewport.getAlignment()
              .getAlignmentAnnotation();
      if (guiProgress != null)
      {
        guiProgress.setProgressBar("JABA " + getServiceActionText(),
                progressId = System.currentTimeMillis());
      }
      if (preset == null && arguments == null)
      {
        rslt = aaservice.analize(seqs);
      }
      else
      {
        try
        {
          rslt = aaservice.customAnalize(seqs, getJabaArguments());
        } catch (WrongParameterException x)
        {
          throw new JobSubmissionException(
                  "Invalid paremeter set. Check Jalview implementation.", x);

        }
      }
      boolean finished = false;
      long rpos = 0;
      do
      {
        JobStatus status = aaservice.getJobStatus(rslt);
        if (status.equals(JobStatus.FINISHED))
        {
          finished = true;
        }
        if (calcMan.isPending(this) && this instanceof AAConClient)
        {
          finished = true;
          // cancel this job and yield to the new job
          try
          {
            if (aaservice.cancelJob(rslt))
            {
              System.err.println("Cancelled AACon job: " + rslt);
            }
            else
            {
              System.err.println("FAILED TO CANCEL AACon job: " + rslt);
            }

          } catch (Exception x)
          {

          }

          return;
        }
        long cpos;
        ChunkHolder stats = null;
        do
        {
          cpos = rpos;
          boolean retry = false;
          do
          {
            try
            {
              stats = aaservice.pullExecStatistics(rslt, rpos);
            } catch (Exception x)
            {

              if (x.getMessage().contains(
                      "Position in a file could not be negative!"))
              {
                // squash index out of bounds exception- seems to happen for
                // disorder predictors which don't (apparently) produce any
                // progress information and JABA server throws an exception
                // because progress length is -1.
                stats = null;
              }
              else
              {
                if (--serverErrorsLeft > 0)
                {
                  retry = true;
                  try
                  {
                    Thread.sleep(200);
                  } catch (InterruptedException q)
                  {
                  }
                  ;
                }
                else
                {
                  throw x;
                }
              }
            }
          } while (retry);
          if (stats != null)
          {
            System.out.print(stats.getChunk());
            msg.append(stats);
            rpos = stats.getNextPosition();
          }
        } while (stats != null && rpos > cpos);

        if (!finished && status.equals(JobStatus.FAILED))
        {
          try
          {
            Thread.sleep(200);
          } catch (InterruptedException x)
          {
          }
          ;
        }
      } while (!finished);
      if (serverErrorsLeft > 0)
      {
        try
        {
          Thread.sleep(200);
        } catch (InterruptedException x)
        {
        }
        ;
        scoremanager = aaservice.getAnnotation(rslt);
        if (scoremanager != null)
        {
          jalview.bin.Cache.log
                  .debug("Updating result annotation from Job " + rslt
                          + " at " + service.getUri());
          updateResultAnnotation(true);
          ap.adjustAnnotationHeight();
        }
      }
    }

    catch (JobSubmissionException x)
    {

      System.err.println("submission error with " + getServiceActionText()
              + " :");
      x.printStackTrace();
      calcMan.workerCannotRun(this);
    } catch (ResultNotAvailableException x)
    {
      System.err.println("collection error:\nJob ID: " + rslt);
      x.printStackTrace();
      calcMan.workerCannotRun(this);

    } catch (OutOfMemoryError error)
    {
      calcMan.workerCannotRun(this);

      // consensus = null;
      // hconsensus = null;
      ap.raiseOOMWarning(getServiceActionText(), error);
    } catch (Exception x)
    {
      calcMan.workerCannotRun(this);

      // consensus = null;
      // hconsensus = null;
      System.err
              .println("Blacklisting worker due to unexpected exception:");
      x.printStackTrace();
    } finally
    {

      calcMan.workerComplete(this);
      if (ap != null)
      {
        calcMan.workerComplete(this);
        if (guiProgress != null && progressId != -1)
        {
          guiProgress.setProgressBar("", progressId);
        }
        ap.paintAlignment(true);
      }
      if (msg.length() > 0)
      {
        // TODO: stash message somewhere in annotation or alignment view.
        // code below shows result in a text box popup
        /*
         * jalview.gui.CutAndPasteTransfer cap = new
         * jalview.gui.CutAndPasteTransfer(); cap.setText(msg.toString());
         * jalview.gui.Desktop.addInternalFrame(cap,
         * "Job Status for "+getServiceActionText(), 600, 400);
         */
      }
    }

  }

  @Override
  public void updateAnnotation()
  {
    updateResultAnnotation(false);
  }

  public abstract void updateResultAnnotation(boolean immediate);

  public abstract String getServiceActionText();

  boolean submitGaps = true;

  boolean alignedSeqs = true;

  boolean nucleotidesAllowed = false;

  boolean proteinAllowed = false;

  /**
   * record sequences for mapping result back to afterwards
   */
  protected boolean bySequence = false;

  Map<String, SequenceI> seqNames;

  boolean[] gapMap;

  int realw;

  public List<FastaSequence> getInputSequences(AlignmentI alignment)
  {
    if (alignment == null || alignment.getWidth() <= 0
            || alignment.getSequences() == null
            // || (alignedSeqs && !alignment.isAligned() && !submitGaps)
            || alignment.isNucleotide() ? !nucleotidesAllowed
            : !proteinAllowed)
    {
      return null;
    }
    List<compbio.data.sequence.FastaSequence> seqs = new ArrayList<compbio.data.sequence.FastaSequence>();

    int minlen = 10;
    int ln = -1;
    if (bySequence)
    {
      seqNames = new HashMap<String, SequenceI>();
    }
    gapMap = new boolean[0];
    for (SequenceI sq : ((List<SequenceI>) alignment.getSequences()))
    {
      if (sq.getEnd() - sq.getStart() > minlen - 1)
      {
        String newname = SeqsetUtils.unique_name(seqs.size() + 1);
        // make new input sequence with or without gaps
        if (seqNames != null)
        {
          seqNames.put(newname, sq);
        }
        FastaSequence seq;
        if (submitGaps)
        {
          seqs.add(seq = new compbio.data.sequence.FastaSequence(newname,
                  sq.getSequenceAsString()));
          if (gapMap == null || gapMap.length < seq.getSequence().length())
          {
            boolean[] tg = gapMap;
            gapMap = new boolean[seq.getLength()];
            System.arraycopy(tg, 0, gapMap, 0, tg.length);
            for (int p = tg.length; p < gapMap.length; p++)
            {
              gapMap[p] = false; // init as a gap
            }
          }
          for (int apos : sq.gapMap())
          {
            gapMap[apos] = true; // aligned.
          }
        }
        else
        {
          seqs.add(seq = new compbio.data.sequence.FastaSequence(newname,
                  AlignSeq.extractGaps(jalview.util.Comparison.GapChars,
                          sq.getSequenceAsString())));
        }
        if (seq.getSequence().length() > ln)
        {
          ln = seq.getSequence().length();
        }
      }
    }
    if (alignedSeqs && submitGaps)
    {
      realw = 0;
      for (int i = 0; i < gapMap.length; i++)
      {
        if (gapMap[i])
        {
          realw++;
        }
      }
      // try real hard to return something submittable
      // TODO: some of AAcon measures need a minimum of two or three amino
      // acids at each position, and AAcon doesn't gracefully degrade.
      for (int p = 0; p < seqs.size(); p++)
      {
        FastaSequence sq = seqs.get(p);
        int l = sq.getSequence().length();
        // strip gapped columns
        char[] padded = new char[realw], orig = sq.getSequence()
                .toCharArray();
        for (int i = 0, pp = 0; i < realw; pp++)
        {
          if (gapMap[pp])
          {
            if (orig.length > pp)
            {
              padded[i++] = orig[pp];
            }
            else
            {
              padded[i++] = '-';
            }
          }
        }
        seqs.set(p, new compbio.data.sequence.FastaSequence(sq.getId(),
                new String(padded)));
      }
    }
    return seqs;
  }

  /**
   * notify manager that we have started, and wait for a free calculation slot
   * 
   * @return true if slot is obtained and work still valid, false if another
   *         thread has done our work for us.
   */
  boolean checkDone()
  {
    calcMan.notifyStart(this);
    ap.paintAlignment(false);
    while (!calcMan.notifyWorking(this))
    {
      if (calcMan.isWorking(this))
      {
        return true;
      }
      try
      {
        if (ap != null)
        {
          ap.paintAlignment(false);
        }

        Thread.sleep(200);
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    if (alignViewport.isClosed())
    {
      abortAndDestroy();
      return true;
    }
    return false;
  }

  protected void createAnnotationRowsForScores(
          List<AlignmentAnnotation> ourAnnot, String calcId, int alWidth,
          Score scr)
  {
    // simple annotation row
    AlignmentAnnotation annotation = alignViewport.getAlignment()
            .findOrCreateAnnotation(scr.getMethod(), calcId, true, null,
                    null);
    if (alWidth == gapMap.length) // scr.getScores().size())
    {
      constructAnnotationFromScore(annotation, 0, alWidth, scr);
      ourAnnot.add(annotation);
    }
  }

  protected AlignmentAnnotation createAnnotationRowsForScores(
          List<AlignmentAnnotation> ourAnnot, String typeName,
          String calcId, SequenceI dseq, int base, Score scr)
  {
    System.out.println("Creating annotation on dseq:" + dseq.getStart()
            + " base is " + base + " and length=" + dseq.getLength()
            + " == " + scr.getScores().size());
    // AlignmentAnnotation annotation = new AlignmentAnnotation(
    // scr.getMethod(), typeName, new Annotation[]
    // {}, 0, -1, AlignmentAnnotation.LINE_GRAPH);
    // annotation.setCalcId(calcId);
    AlignmentAnnotation annotation = alignViewport.getAlignment()
            .findOrCreateAnnotation(typeName, calcId, false, dseq, null);
    constructAnnotationFromScore(annotation, 0, dseq.getLength(), scr);
    annotation.createSequenceMapping(dseq, base, false);
    annotation.adjustForAlignment();
    dseq.addAlignmentAnnotation(annotation);
    ourAnnot.add(annotation);
    return annotation;
  }

  private void constructAnnotationFromScore(AlignmentAnnotation annotation,
          int base, int alWidth, Score scr)
  {
    Annotation[] elm = new Annotation[alWidth];
    Iterator<Float> vals = scr.getScores().iterator();
    float m = 0f, x = 0f;
    for (int i = 0; vals.hasNext(); i++)
    {
      float val = vals.next().floatValue();
      if (i == 0)
      {
        m = val;
        x = val;
      }
      else
      {
        if (m > val)
        {
          m = val;
        }
        ;
        if (x < val)
        {
          x = val;
        }
      }
      // if we're at a gapped column then skip to next ungapped position
      if (gapMap != null && gapMap.length > 0)
      {
        while (!gapMap[i])
        {
          elm[i++] = new Annotation("", "", ' ', Float.NaN);
        }
      }
      elm[i] = new Annotation("", "" + val, ' ', val);
    }

    annotation.annotations = elm;
    annotation.belowAlignment = true;
    if (x < 0)
    {
      x = 0;
    }
    x += (x - m) * 0.1;
    annotation.graphMax = x;
    annotation.graphMin = m;
    annotation.validateRangeAndDisplay();
  }

  protected void updateOurAnnots(List<AlignmentAnnotation> ourAnnot)
  {
    List<AlignmentAnnotation> our = ourAnnots;
    ourAnnots = ourAnnot;
    AlignmentI alignment = alignViewport.getAlignment();
    if (our != null)
    {
      if (our.size() > 0)
      {
        for (AlignmentAnnotation an : our)
        {
          if (!ourAnnots.contains(an))
          {
            // remove the old annotation
            alignment.deleteAnnotation(an);
          }
        }
      }
      our.clear();

      ap.adjustAnnotationHeight();
    }
  }
}
