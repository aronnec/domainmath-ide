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

import java.util.Hashtable;

import jalview.analysis.StructureFrequency;
import jalview.api.AlignCalcWorkerI;
import jalview.api.AlignViewportI;
import jalview.api.AlignmentViewPanel;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.Annotation;

public class StrucConsensusThread extends AlignCalcWorker implements
        AlignCalcWorkerI
{
  public StrucConsensusThread(AlignViewportI alignViewport,
          AlignmentViewPanel alignPanel)
  {
    super(alignViewport, alignPanel);
  }

  AlignmentAnnotation strucConsensus;

  Hashtable[] hStrucConsensus;

  @Override
  public void run()
  {
    try
    {
      if (calcMan.isPending(this))
      {
        return;
      }
      calcMan.notifyStart(this);
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
      AlignmentI alignment = alignViewport.getAlignment();

      int aWidth = -1;

      if (alignment == null || (aWidth = alignment.getWidth()) < 0)
      {
        calcMan.workerComplete(this);
        return;
      }
      strucConsensus = alignViewport.getAlignmentStrucConsensusAnnotation();
      hStrucConsensus = alignViewport.getRnaStructureConsensusHash();
      strucConsensus.annotations = null;
      strucConsensus.annotations = new Annotation[aWidth];

      hStrucConsensus = new Hashtable[aWidth];

      AlignmentAnnotation[] aa = alignViewport.getAlignment()
              .getAlignmentAnnotation();
      AlignmentAnnotation rnaStruc = null;
      // select rna struct to use for calculation
      for (int i = 0; i < aa.length; i++)
      {
        if (aa[i].getRNAStruc() != null && aa[i].isValidStruc())
        {
          rnaStruc = aa[i];
          break;
        }
      }
      // check to see if its valid

      if (rnaStruc == null || !rnaStruc.isValidStruc())
      {
        calcMan.workerComplete(this);
        return;
      }

      try
      {
        jalview.analysis.StructureFrequency.calculate(
                alignment.getSequencesArray(), 0, alignment.getWidth(),
                hStrucConsensus, true, rnaStruc);
      } catch (ArrayIndexOutOfBoundsException x)
      {
        calcMan.workerComplete(this);
        return;
      }
      alignViewport.setRnaStructureConsensusHash(hStrucConsensus);
      // TODO AlignmentAnnotation rnaStruc!!!
      updateResultAnnotation(true);
      if (alignViewport.getGlobalColourScheme() != null)
      {
        alignViewport.getGlobalColourScheme().setConsensus(hStrucConsensus);
      }

    } catch (OutOfMemoryError error)
    {
      calcMan.workerCannotRun(this);

      // consensus = null;
      // hconsensus = null;
      ap.raiseOOMWarning("calculating RNA structure consensus", error);
    } finally
    {
      calcMan.workerComplete(this);
      if (ap != null)
      {
        ap.paintAlignment(true);
      }
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
    if (immediate || !calcMan.isWorking(this) && strucConsensus != null
            && hStrucConsensus != null)
    {
      StructureFrequency.completeConsensus(strucConsensus, hStrucConsensus,
              0, hStrucConsensus.length,
              alignViewport.getIgnoreGapsConsensus(),
              alignViewport.isShowSequenceLogo());
    }
  }

}
