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

import jalview.datamodel.AlignmentAnnotation;
import jalview.gui.AlignFrame;
import jalview.gui.AlignmentPanel;
import jalview.ws.jws2.dm.AAConSettings;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.WsParamSetI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import compbio.data.sequence.Score;
import compbio.metadata.Argument;

public class AAConClient extends JabawsAlignCalcWorker
{

  public AAConClient(Jws2Instance service, AlignFrame alignFrame,
          WsParamSetI preset, List<Argument> paramset)
  {
    super(service, alignFrame, preset, paramset);
    submitGaps = true;
    alignedSeqs = true;
    nucleotidesAllowed = false;
    proteinAllowed = true;
    gapMap = new boolean[0];
    initViewportParams();
  }

  protected void initViewportParams()
  {
    ((jalview.gui.AlignViewport) alignViewport).setCalcIdSettingsFor(
            getCalcId(),
            new AAConSettings(true, service, this.preset,
                    (arguments != null) ? JabaParamStore
                            .getJwsArgsfromJaba(arguments) : null), true);
  }

  @Override
  public void updateParameters(WsParamSetI newpreset,
          java.util.List<Argument> newarguments)
  {
    super.updateParameters(newpreset, newarguments);
    initViewportParams();
  };

  public String getServiceActionText()
  {
    return "calculating Amino acid consensus using AACon service";
  }

  /**
   * update the consensus annotation from the sequence profile data using
   * current visualization settings.
   */

  public void updateResultAnnotation(boolean immediate)
  {
    if (immediate || !calcMan.isWorking(this) && scoremanager != null)
    {
      Map<String, TreeSet<Score>> scoremap = scoremanager.asMap();
      int alWidth = alignViewport.getAlignment().getWidth();
      ArrayList<AlignmentAnnotation> ourAnnot = new ArrayList<AlignmentAnnotation>();
      for (String score : scoremap.keySet())
      {
        Set<Score> scores = scoremap.get(score);
        for (Score scr : scores)
        {
          if (scr.getRanges() != null && scr.getRanges().size() > 0)
          {
            /**
             * annotation in range annotation = findOrCreate(scr.getMethod(),
             * true, null, null); Annotation[] elm = new Annotation[alWidth];
             * Iterator<Float> vals = scr.getScores().iterator(); for (Range rng
             * : scr.getRanges()) { float val = vals.next().floatValue(); for
             * (int i = rng.from; i <= rng.to; i++) { elm[i] = new
             * Annotation("", "", ' ', val); } } annotation.annotations = elm;
             * annotation.validateRangeAndDisplay();
             */
          }
          else
          {
            createAnnotationRowsForScores(ourAnnot, getCalcId(), alWidth,
                    scr);
          }
        }
      }

      if (ourAnnot.size() > 0)
      {
        updateOurAnnots(ourAnnot);
      }
    }
  }

  public String getCalcId()
  {
    return SequenceAnnotationWSClient.AAConCalcId;
  }

  public static void removeAAConsAnnotation(AlignmentPanel alignPanel)
  {
    for (AlignmentAnnotation aa : alignPanel.getAlignment().findAnnotation(
            SequenceAnnotationWSClient.AAConCalcId))
    {
      alignPanel.getAlignment().deleteAnnotation(aa);
    }
  }
}
