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
package jalview.io.vamsas;

import java.util.Enumeration;
import java.util.Vector;

import uk.ac.vamsas.objects.core.DataSetAnnotations;
import uk.ac.vamsas.objects.core.Link;
import uk.ac.vamsas.objects.core.Property;
import uk.ac.vamsas.objects.core.Provenance;
import uk.ac.vamsas.objects.core.RangeAnnotation;
import uk.ac.vamsas.objects.core.Score;
import uk.ac.vamsas.objects.core.Seg;
import uk.ac.vamsas.objects.utils.Properties;
import jalview.bin.Cache;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.io.VamsasAppDatastore;
import jalview.util.UrlLink;

/**
 * @author JimP
 * 
 */
public class Sequencefeature extends Rangetype
{

  uk.ac.vamsas.objects.core.DataSet dataset;

  uk.ac.vamsas.objects.core.Sequence sequence;

  private SequenceI dsSeq;

  public Sequencefeature(VamsasAppDatastore vamsasAppDatastore,
          SequenceFeature sequenceFeature,
          uk.ac.vamsas.objects.core.DataSet dataset,
          uk.ac.vamsas.objects.core.Sequence sequence)
  {
    super(vamsasAppDatastore, sequenceFeature, DataSetAnnotations.class);
    this.dataset = dataset;
    this.sequence = sequence;
    doSync();
  }

  public Sequencefeature(VamsasAppDatastore vamsasAppDatastore,
          DataSetAnnotations dseta, SequenceI dsSeq)
  {
    super(vamsasAppDatastore, dseta,
            jalview.datamodel.SequenceFeature.class);
    this.dsSeq = dsSeq;
    doJvUpdate();
  }

  public void addToDocument()
  {
    DataSetAnnotations dsa = (DataSetAnnotations) vobj;
    jalview.datamodel.SequenceFeature feature = (jalview.datamodel.SequenceFeature) jvobj;
    dsa = (DataSetAnnotations) getDSAnnotationFromJalview(
            new DataSetAnnotations(), feature);
    if (dsa.getProvenance() == null)
    {
      dsa.setProvenance(new Provenance());
    }
    addProvenance(dsa.getProvenance(), "created"); // JBPNote - need
    // to update
    dsa.addSeqRef(sequence); // we have just created this annotation
    // - so safe to use this
    bindjvvobj(feature, dsa);
    dataset.addDataSetAnnotations(dsa);
  }

  public void addFromDocument()
  {
    DataSetAnnotations dsa = (DataSetAnnotations) vobj;
    if (dsa.getSeqRefCount() != 1)
    {
      Cache.log
              .warn("Not binding "
                      + dsa.getVorbaId()
                      + " to Sequence Feature - has multiple dataset sequence references.");
      return;
    }
    jalview.datamodel.SequenceFeature sf = (jalview.datamodel.SequenceFeature) jvobj;
    dsSeq.addSequenceFeature(sf = getJalviewSeqFeature(dsa));
    jvobj = sf;
    bindjvvobj(sf, dsa);
  }

  public void conflict()
  {
    log.warn("Untested sequencefeature conflict code");
    DataSetAnnotations dsa = (DataSetAnnotations) vobj;
    jalview.datamodel.SequenceFeature feature = (jalview.datamodel.SequenceFeature) jvobj;
    jalview.datamodel.SequenceFeature sf = getJalviewSeqFeature(dsa);
    replaceJvObjMapping(feature, sf); // switch binding of dsa from old feature
                                      // to newly created feature
    dsSeq.addSequenceFeature(sf); // add new imported feature
    addToDocument(); // and create a new feature in the document
  }

  public void updateToDoc()
  {
    DataSetAnnotations dsa = (DataSetAnnotations) vobj;
    jalview.datamodel.SequenceFeature feature = (jalview.datamodel.SequenceFeature) jvobj;
    if (dsa.getSeqRefCount() != 1)
    {
      replaceJvObjMapping(feature, null);
      Cache.log
              .warn("Binding of annotation to jalview feature has changed. Removing binding and recreating.");
      doSync(); // re-verify bindings.
    }
    else
    {
      // Sync the features from Jalview
      long oldref = dsa.get__last_hash();
      getDSAnnotationFromJalview(dsa, feature);
      if (oldref != dsa.hashCode())
      {
        Cache.log
                .debug("Updated dataset sequence annotation from feature.");
        addProvenance(dsa.getProvenance(), "modified");
      }
    }

  }

  public void updateFromDoc()
  {
    DataSetAnnotations dsa = (DataSetAnnotations) vobj;
    jalview.datamodel.SequenceFeature feature = (jalview.datamodel.SequenceFeature) jvobj;
    if (dsa.getSeqRefCount() != 1)
    {
      // conflicting update from document - we cannot map this feature anymore.
      replaceJvObjMapping(feature, null);
      Cache.log
              .warn("annotation ("
                      + dsa.getVorbaId()
                      + " bound to jalview feature cannot be mapped. Removing binding, deleting feature, and deleting feature.");
      // - consider deleting the feature ?
      dsSeq.deleteFeature(feature);
      // doSync();
    }
    else
    {
      // Sync the features to Jalview - easiest to delete and add the feature
      // again
      jalview.datamodel.SequenceFeature newsf = getJalviewSeqFeature(dsa);
      dsSeq.deleteFeature(feature);
      replaceJvObjMapping(feature, newsf);
      dsSeq.addSequenceFeature(newsf);
      if (feature.otherDetails != null)
      {
        // TODO later: leave this to finalise method ?
        feature.otherDetails.clear();
      }
    }
  }

  /**
   * correctly create/update a RangeAnnotation from a jalview sequence feature
   * TODO: refactor to a method in jalview.io.vamsas.RangeAnnotation class
   * 
   * @param dsa
   *          (typically DataSetAnnotations or AlignmentSequenceAnnotation)
   * @param feature
   *          (the feature to be mapped from)
   * @return
   */
  private RangeAnnotation getDSAnnotationFromJalview(RangeAnnotation dsa,
          jalview.datamodel.SequenceFeature feature)
  {
    dsa.setType(feature.getType());
    Seg vSeg = new Seg();
    vSeg.setStart(feature.getBegin());
    vSeg.setEnd(feature.getEnd());
    vSeg.setInclusive(true);
    if (dsa.getSegCount() > 1)
    {
      Cache.log
              .debug("About to destroy complex annotation in vamsas document mapped to sequence feature ("
                      + dsa.getVorbaId() + ")");
    }
    dsa.setSeg(new Seg[]
    { vSeg });
    dsa.setDescription(feature.getDescription());
    dsa.setStatus(feature.getStatus());
    if (feature.links != null && feature.links.size() > 0)
    {
      for (int i = 0, iSize = feature.links.size(); i < iSize; i++)
      {
        String link = (String) feature.links.elementAt(i);
        UrlLink ulink = new UrlLink(link);
        if (ulink.isValid())
        {
          // We only add static links to the document.
          Link vLink = new Link();
          vLink.setContent(ulink.getLabel());
          vLink.setHref(ulink.getTarget());
          dsa.addLink(vLink);
        }
      }
    }
    dsa.setGroup(feature.getFeatureGroup());
    if (feature.getScore() != Float.NaN)
    {
      Score fscore = new Score();
      dsa.setScore(new Score[]
      { fscore });
      fscore.setContent(feature.getScore());
      fscore.setName(feature.getType());
    }
    if (feature.otherDetails != null)
    {
      Enumeration iter = feature.otherDetails.keys();
      Vector props = dsa.getPropertyAsReference();
      while (iter.hasMoreElements())
      {
        String key = (String) iter.nextElement();
        if (!key.equalsIgnoreCase("score")
                && !key.equalsIgnoreCase("status"))
        {
          Property nprop = new Property();
          nprop.setName(key);
          Object vlu = feature.getValue(key);
          nprop.setContent(feature.getValue(key).toString());
          boolean valid = false;
          if (vlu instanceof String)
          {
            nprop.setType(uk.ac.vamsas.objects.utils.Properties.STRINGTYPE);
            valid = true;
          }
          else if (vlu instanceof Integer)
          {
            valid = true;
            nprop.setType(uk.ac.vamsas.objects.utils.Properties.INTEGERTYPE);
          }
          else if (vlu instanceof Float)
          {
            nprop.setType(uk.ac.vamsas.objects.utils.Properties.FLOATTYPE);
            valid = true;
          }
          if (valid)
          {
            if (props != null)
            {
              uk.ac.vamsas.objects.utils.Properties.addOrReplace(props,
                      nprop);
            }
            else
            {
              dsa.addProperty(nprop);
            }
          }
        }
      }
    }
    return dsa;
  }

  private SequenceFeature getJalviewSeqFeature(RangeAnnotation dseta)
  {
    int[] se = getBounds(dseta);
    SequenceFeature sf = new jalview.datamodel.SequenceFeature(
            dseta.getType(), dseta.getDescription(), dseta.getStatus(),
            se[0], se[1], dseta.getGroup());
    if (dseta.getLinkCount() > 0)
    {
      Link[] links = dseta.getLink();
      for (int i = 0; i < links.length; i++)
      {
        // TODO: use URLLink parsing/validation here.
        sf.addLink(links[i].getContent() + "|" + links[i].getHref());
      }
    }
    if (dseta.getScoreCount() > 0)
    {
      Enumeration scr = dseta.enumerateScore();
      while (scr.hasMoreElements())
      {
        Score score = (Score) scr.nextElement();
        if (score.getName().equals(sf.getType()))
        {
          sf.setScore(score.getContent());
        }
        else
        {
          sf.setValue(score.getName(), "" + score.getContent());
        }
      }
    }
    // other details
    Enumeration props = dseta.enumerateProperty();
    while (props.hasMoreElements())
    {
      Property p = (Property) props.nextElement();
      Object val = null;
      if (Properties.isValid(p))
      {
        if (Properties.isString(p))
        {
          val = p.getContent();
        }
        if (Properties.isBoolean(p))
        {
          try
          {
            val = new Boolean(p.getContent());
          } catch (Exception e)
          {
          }
        }
        if (Properties.isFloat(p))
        {
          try
          {
            val = new Float(p.getContent());

          } catch (Exception e)
          {
          }
        }
        if (Properties.isInteger(p))
        {
          try
          {
            val = new Integer(p.getContent());
          } catch (Exception e)
          {
          }
        }
        if (val != null)
        {
          sf.setValue(p.getName(), val);
        }
      }
    }

    return sf;
  }

}
