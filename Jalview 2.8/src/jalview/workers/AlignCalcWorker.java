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

import java.util.List;

import jalview.api.AlignCalcManagerI;
import jalview.api.AlignCalcWorkerI;
import jalview.api.AlignViewportI;
import jalview.api.AlignmentViewPanel;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;

/**
 * Base class for alignment calculation workers
 * 
 * @author jimp
 * 
 */
public abstract class AlignCalcWorker implements AlignCalcWorkerI
{
  /**
   * manager and data source for calculations
   */
  protected AlignViewportI alignViewport;

  protected AlignCalcManagerI calcMan;

  protected AlignmentViewPanel ap;

  protected List<AlignmentAnnotation> ourAnnots = null;

  public AlignCalcWorker(AlignViewportI alignViewport,
          AlignmentViewPanel alignPanel)
  {
    this.alignViewport = alignViewport;
    calcMan = alignViewport.getCalcManager();
    ap = alignPanel;
  }

  protected void abortAndDestroy()
  {
    if (calcMan != null)
    {
      calcMan.workerComplete(this);
    }
    alignViewport = null;
    calcMan = null;
    ap = null;

  }

  public boolean involves(AlignmentAnnotation i)
  {
    return ourAnnots != null && ourAnnots.contains(i);
  }

  /**
   * permanently remove from the alignment all annotation rows managed by this
   * worker
   */
  @Override
  public void removeOurAnnotation()
  {
    if (ourAnnots != null && alignViewport != null)
    {
      AlignmentI alignment = alignViewport.getAlignment();
      synchronized (ourAnnots)
      {
        for (AlignmentAnnotation aa : ourAnnots)
        {
          alignment.deleteAnnotation(aa, true);
        }
      }
    }
  }
  // TODO: allow GUI to query workers associated with annotation to add items to
  // annotation label panel popup menu

}
