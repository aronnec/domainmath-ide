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
package jalview.workers;

import jalview.analysis.AAFrequency;
import jalview.api.AlignCalcWorkerI;
import jalview.api.AlignViewportI;
import jalview.api.AlignmentViewPanel;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.Annotation;
import jalview.schemes.ColourSchemeI;

import java.util.Hashtable;

public class ConsensusThread extends AlignCalcWorker implements
        AlignCalcWorkerI
{
  public ConsensusThread(AlignViewportI alignViewport,
          AlignmentViewPanel alignPanel)
  {
    super(alignViewport, alignPanel);
  }

  @Override
  public void run()
  {
    if (calcMan.isPending(this))
    {
      return;
    }
    calcMan.notifyStart(this);
    long started = System.currentTimeMillis();
    try
    {
      AlignmentAnnotation consensus = alignViewport
              .getAlignmentConsensusAnnotation();
      if (consensus == null || calcMan.isPending(this))
      {
        calcMan.workerComplete(this);
        return;
      }
      while (!calcMan.notifyWorking(this))
      {
        // System.err.println("Thread (Consensus"+Thread.currentThread().getName()+") Waiting around.");
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
      }
      AlignmentI alignment = alignViewport.getAlignment();

      int aWidth = -1;

      if (alignment == null || (aWidth = alignment.getWidth()) < 0)
      {
        calcMan.workerComplete(this);
        // .updatingConservation = false;
        // AlignViewport.UPDATING_CONSERVATION = false;

        return;
      }
      consensus = alignViewport.getAlignmentConsensusAnnotation();

      consensus.annotations = null;
      consensus.annotations = new Annotation[aWidth];
      Hashtable[] hconsensus = alignViewport.getSequenceConsensusHash();
      hconsensus = new Hashtable[aWidth];
      try
      {
        AAFrequency.calculate(alignment.getSequencesArray(), 0,
                alignment.getWidth(), hconsensus, true);
      } catch (ArrayIndexOutOfBoundsException x)
      {
        // this happens due to a race condition -
        // alignment was edited at same time as calculation was running
        //
        // calcMan.workerCannotRun(this);
        calcMan.workerComplete(this);
        return;
      }
      alignViewport.setSequenceConsensusHash(hconsensus);
      updateResultAnnotation(true);
      ColourSchemeI globalColourScheme = alignViewport
              .getGlobalColourScheme();
      if (globalColourScheme != null)
      {
        globalColourScheme.setConsensus(hconsensus);
      }

    } catch (OutOfMemoryError error)
    {
      calcMan.workerCannotRun(this);

      // consensus = null;
      // hconsensus = null;
      ap.raiseOOMWarning("calculating consensus", error);
    }

    calcMan.workerComplete(this);
    if (ap != null)
    {
      ap.paintAlignment(true);
    }
  }

  /**
   * update the consensus annotation from the sequence profile data using
   * current visualization settings.
   */
  @Override
  public void updateAnnotation()
  {
    updateResultAnnotation(false);
  }

  public void updateResultAnnotation(boolean immediate)
  {
    AlignmentAnnotation consensus = alignViewport
            .getAlignmentConsensusAnnotation();
    Hashtable[] hconsensus = alignViewport.getSequenceConsensusHash();
    if (immediate || !calcMan.isWorking(this) && consensus != null
            && hconsensus != null)
    {
      AAFrequency.completeConsensus(consensus, hconsensus, 0,
              hconsensus.length, alignViewport.getIgnoreGapsConsensus(),
              alignViewport.isShowSequenceLogo());
    }
  }
}
