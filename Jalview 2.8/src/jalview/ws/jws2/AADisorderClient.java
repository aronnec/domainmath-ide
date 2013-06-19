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

import jalview.api.AlignCalcWorkerI;
import jalview.datamodel.AlignmentAnnotation;

import jalview.datamodel.GraphLine;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.schemes.GraduatedColor;
import jalview.schemes.UserColourScheme;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.WsParamSetI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import compbio.data.sequence.Range;
import compbio.data.sequence.Score;
import compbio.data.sequence.ScoreManager.ScoreHolder;
import compbio.metadata.Argument;

public class AADisorderClient extends JabawsAlignCalcWorker implements
        AlignCalcWorkerI
{

  private static final String THRESHOLD = "THRESHOLD";

  String typeName;

  String methodName;

  String groupName;

  AlignFrame af;

  public AADisorderClient(Jws2Instance sh, AlignFrame alignFrame,
          WsParamSetI preset, List<Argument> paramset)
  {
    super(sh, alignFrame, preset, paramset);
    af = alignFrame;
    typeName = sh.action;
    methodName = sh.serviceType;

    submitGaps = false;
    alignedSeqs = false;
    nucleotidesAllowed = false;
    proteinAllowed = true;
    bySequence = true;
  }

  @Override
  public String getServiceActionText()
  {
    return "Submitting amino acid sequences for disorder prediction.";
  }

  private static Map<String, Map<String, String[]>> featureMap;

  private static Map<String, Map<String, Map<String, Object>>> annotMap;

  private static String DONTCOMBINE = "DONTCOMBINE";

  private static String INVISIBLE = "INVISIBLE";
  static
  {
    // TODO: turn this into some kind of configuration file that's a bit easier
    // to edit
    featureMap = new HashMap<String, Map<String, String[]>>();
    Map<String, String[]> fmap;
    featureMap.put(compbio.ws.client.Services.IUPredWS.toString(),
            fmap = new HashMap<String, String[]>());
    fmap.put("Glob", new String[]
    { "Globular Domain", "Predicted globular domain" });
    featureMap.put(compbio.ws.client.Services.JronnWS.toString(),
            fmap = new HashMap<String, String[]>());
    featureMap.put(compbio.ws.client.Services.DisemblWS.toString(),
            fmap = new HashMap<String, String[]>());
    fmap.put("REM465", new String[]
    { "REM465", "Missing density" });
    fmap.put("HOTLOOPS", new String[]
    { "HOTLOOPS", "Flexible loops" });
    fmap.put("COILS", new String[]
    { "COILS", "Random coil" });
    featureMap.put(compbio.ws.client.Services.GlobPlotWS.toString(),
            fmap = new HashMap<String, String[]>());
    fmap.put("GlobDoms", new String[]
    { "Globular Domain", "Predicted globular domain" });
    fmap.put("Disorder", new String[]
    { "Protein Disorder", "Probable unstructured peptide region" });
    Map<String, Map<String, Object>> amap;
    annotMap = new HashMap<String, Map<String, Map<String, Object>>>();
    annotMap.put(compbio.ws.client.Services.GlobPlotWS.toString(),
            amap = new HashMap<String, Map<String, Object>>());
    amap.put("Dydx", new HashMap<String, Object>());
    amap.get("Dydx").put(DONTCOMBINE, DONTCOMBINE);
    amap.get("Dydx").put(THRESHOLD, new double[]
    { 1, 0 });
    amap.put("SmoothedScore", new HashMap<String, Object>());
    amap.get("SmoothedScore").put(INVISIBLE, INVISIBLE);
    amap.put("RawScore", new HashMap<String, Object>());
    amap.get("RawScore").put(INVISIBLE, INVISIBLE);
    annotMap.put(compbio.ws.client.Services.DisemblWS.toString(),
            amap = new HashMap<String, Map<String, Object>>());
    amap.put("COILS", new HashMap<String, Object>());
    amap.put("HOTLOOPS", new HashMap<String, Object>());
    amap.put("REM465", new HashMap<String, Object>());
    amap.get("COILS").put(THRESHOLD, new double[]
    { 1, 0.516 });
    amap.get("HOTLOOPS").put(THRESHOLD, new double[]
    { 1, 0.6 });
    amap.get("REM465").put(THRESHOLD, new double[]
    { 1, 0.1204 });

    annotMap.put(compbio.ws.client.Services.IUPredWS.toString(),
            amap = new HashMap<String, Map<String, Object>>());
    amap.put("Long", new HashMap<String, Object>());
    amap.put("Short", new HashMap<String, Object>());
    amap.get("Long").put(THRESHOLD, new double[]
    { 1, 0.5 });
    amap.get("Short").put(THRESHOLD, new double[]
    { 1, 0.5 });
    annotMap.put(compbio.ws.client.Services.JronnWS.toString(),
            amap = new HashMap<String, Map<String, Object>>());
    amap.put("JRonn", new HashMap<String, Object>());
    amap.get("JRonn").put(THRESHOLD, new double[]
    { 1, 0.5 });
  }

  @Override
  public void updateResultAnnotation(boolean immediate)
  {

    if (immediate || !calcMan.isWorking(this) && scoremanager != null)
    {
      Map<String, String[]> featureTypeMap = featureMap
              .get(service.serviceType);
      Map<String, Map<String, Object>> annotTypeMap = annotMap
              .get(service.serviceType);
      boolean dispFeatures = false;
      Map<String, Object> fc = new Hashtable<String, Object>();
      List<AlignmentAnnotation> ourAnnot = new ArrayList<AlignmentAnnotation>();
      /**
       * grouping for any annotation rows created
       */
      int graphGroup = 1;
      if (alignViewport.getAlignment().getAlignmentAnnotation() != null)
      {
        for (AlignmentAnnotation ala : alignViewport.getAlignment()
                .getAlignmentAnnotation())
        {
          if (ala.graphGroup > graphGroup)
          {
            graphGroup = ala.graphGroup;
          }
        }
      }

      for (String seqId : seqNames.keySet())
      {
        boolean sameGroup = false;
        SequenceI dseq, aseq, seq = seqNames.get(seqId);
        int base = seq.getStart() - 1;
        aseq = seq;
        while ((dseq = seq).getDatasetSequence() != null)
        {
          seq = seq.getDatasetSequence();
        }
        ;
        ScoreHolder scores = scoremanager.getAnnotationForSequence(seqId);
        float last = Float.NaN, val = Float.NaN;
        int lastAnnot = ourAnnot.size();
        for (Score scr : scores.scores)
        {

          if (scr.getRanges() != null && scr.getRanges().size() > 0)
          {
            Iterator<Float> vals = scr.getScores().iterator();
            // make features on sequence
            for (Range rn : scr.getRanges())
            {

              SequenceFeature sf;
              String[] type = featureTypeMap.get(scr.getMethod());
              if (type == null)
              {
                // create a default type for this feature
                type = new String[]
                { typeName + " (" + scr.getMethod() + ")",
                    service.getActionText() };
              }
              if (vals.hasNext())
              {
                sf = new SequenceFeature(type[0], type[1], base + rn.from,
                        base + rn.to, val = vals.next().floatValue(),
                        methodName);
              }
              else
              {
                sf = new SequenceFeature(type[0], type[1], null, base
                        + rn.from, base + rn.to, methodName);
              }
              dseq.addSequenceFeature(sf);
              if (last != val && last != Float.NaN)
              {
                fc.put(sf.getType(), sf);
              }
              last = val;
              dispFeatures = true;
            }
          }
          else
          {
            if (scr.getScores().size() == 0)
            {
              continue;
            }
            AlignmentAnnotation annot = createAnnotationRowsForScores(
                    ourAnnot, service.serviceType + " (" + scr.getMethod()
                            + ")",
                    service.getServiceTypeURI() + "/" + scr.getMethod(),
                    aseq, base + 1, scr);
            annot.graph = AlignmentAnnotation.LINE_GRAPH;
            annot.visible = (annotTypeMap == null
                    || annotTypeMap.get(scr.getMethod()) == null || annotTypeMap
                    .get(scr.getMethod()).get(INVISIBLE) == null);
            double[] thrsh = (annotTypeMap == null || annotTypeMap.get(scr
                    .getMethod()) == null) ? null : (double[]) annotTypeMap
                    .get(scr.getMethod()).get(THRESHOLD);
            if (annotTypeMap == null
                    || annotTypeMap.get(scr.getMethod()) == null
                    || annotTypeMap.get(scr.getMethod()).get(DONTCOMBINE) == null)
            {
              {
                if (!sameGroup)
                {
                  graphGroup++;
                  sameGroup = true;
                }

                annot.graphGroup = graphGroup;
              }
            }

            annot.description = "<html>" + service.getActionText()
                    + " - raw scores";
            if (thrsh != null)
            {
              String threshNote = (thrsh[0] > 0 ? "Above " : "Below ")
                      + thrsh[1] + " indicates disorder";
              annot.threshold = new GraphLine((float) thrsh[1], threshNote,
                      Color.red);
              annot.description += "<br/>" + threshNote;
            }
            annot.description += "</html>";
            Color col = new UserColourScheme(typeName)
                    .createColourFromName(typeName + scr.getMethod());
            for (int p = 0, ps = annot.annotations.length; p < ps; p++)
            {
              if (annot.annotations[p] != null)
              {
                annot.annotations[p].colour = col;
              }
            }
            annot._linecolour = col;
          }
        }
        if (lastAnnot + 1 == ourAnnot.size())
        {
          // remove singleton alignment annotation row
          ourAnnot.get(lastAnnot).graphGroup = -1;
        }
      }
      {
        if (dispFeatures)
        {
          jalview.gui.FeatureRenderer fr = ((jalview.gui.AlignmentPanel) ap)
                  .cloneFeatureRenderer();
          for (String ft : fc.keySet())
          {
            Object gc = fr.getFeatureStyle(ft);
            if (gc instanceof Color)
            {
              // set graduated color as fading to white for minimum, and
              // autoscaling to values on alignment
              GraduatedColor ggc = new GraduatedColor(Color.white,
                      (Color) gc, Float.MIN_VALUE, Float.MAX_VALUE);
              ggc.setAutoScaled(true);
              fr.setColour(ft, ggc);
            }
          }
          // TODO: JAL-1150 - create sequence feature settings API for defining
          // styles and enabling/disabling feature overlay on alignment panel
          ((jalview.gui.AlignmentPanel) ap).updateFeatureRendererFrom(fr);
          if (af.alignPanel == ap)
          {
            // only do this if the alignFrame is currently showing this view.
            af.setShowSeqFeatures(true);
          }
          ap.paintAlignment(true);
        }
        if (ourAnnot.size() > 0)
        {
          // Modify the visible annotation on the alignment viewport with the
          // new alignment annotation rows created.
          updateOurAnnots(ourAnnot);
          ap.adjustAnnotationHeight();
        }
      }
    }
  }

}
