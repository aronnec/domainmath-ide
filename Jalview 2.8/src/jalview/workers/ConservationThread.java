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

import java.util.ArrayList;
import java.util.List;

import jalview.analysis.Conservation;
import jalview.api.AlignCalcWorkerI;
import jalview.api.AlignmentViewPanel;
import jalview.api.AlignViewportI;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;

public class ConservationThread extends AlignCalcWorker implements
        AlignCalcWorkerI
{

  private int ConsPercGaps = 25; // JBPNote : This should be a configurable
                                 // property!

  public ConservationThread(AlignViewportI alignViewport,
          AlignmentViewPanel alignPanel)
  {
    super(alignViewport, alignPanel);
    ConsPercGaps = alignViewport.getConsPercGaps();
  }

  private Conservation cons;

  AlignmentAnnotation conservation, quality;

  int alWidth;

  @Override
  public void run()
  {
    try
    {
      calcMan.notifyStart(this); // updatingConservation = true;

      while (!calcMan.notifyWorking(this))
      {
        try
        {
          if (ap != null)
          {
            // ap.paintAlignment(false);
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
      List<AlignmentAnnotation> ourAnnot = new ArrayList<AlignmentAnnotation>();
      AlignmentI alignment = alignViewport.getAlignment();
      conservation = alignViewport.getAlignmentConservationAnnotation();
      quality = alignViewport.getAlignmentQualityAnnot();
      ourAnnot.add(conservation);
      ourAnnot.add(quality);
      ourAnnots = ourAnnot;

      // AlignViewport.UPDATING_CONSERVATION = true;

      if (alignment == null || (alWidth = alignment.getWidth()) < 0)
      {
        calcMan.workerComplete(this);
        // .updatingConservation = false;
        // AlignViewport.UPDATING_CONSERVATION = false;

        return;
      }
      try
      {
        cons = Conservation.calculateConservation("All",
                jalview.schemes.ResidueProperties.propHash, 3,
                alignment.getSequences(), 0, alWidth - 1, false,
                ConsPercGaps, quality != null);
      } catch (IndexOutOfBoundsException x)
      {
        // probable race condition. just finish and return without any fuss.
        calcMan.workerComplete(this);
        return;
      }
      updateResultAnnotation(true);
    } catch (OutOfMemoryError error)
    {
      ap.raiseOOMWarning("calculating conservation", error);
      calcMan.workerCannotRun(this);
      // alignViewport.conservation = null;
      // this.alignViewport.quality = null;

    }
    calcMan.workerComplete(this);

    if (ap != null)
    {
      ap.paintAlignment(true);
    }

  }

  private void updateResultAnnotation(boolean b)
  {
    if (b || !calcMan.isWorking(this) && cons != null
            && conservation != null && quality != null)
      cons.completeAnnotations(conservation, quality, 0, alWidth);
  }

  @Override
  public void updateAnnotation()
  {
    updateResultAnnotation(false);

  }
}
