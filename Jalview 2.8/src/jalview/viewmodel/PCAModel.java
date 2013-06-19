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
package jalview.viewmodel;

import java.util.Vector;

import jalview.analysis.PCA;
import jalview.datamodel.AlignmentView;
import jalview.datamodel.SequenceI;
import jalview.datamodel.SequencePoint;
import jalview.api.RotatableCanvasI;

public class PCAModel
{

  public PCAModel(AlignmentView seqstrings2, SequenceI[] seqs2,
          boolean nucleotide2)
  {
    seqstrings = seqstrings2;
    seqs = seqs2;
    nucleotide = nucleotide2;
  }

  private volatile PCA pca;

  int top;

  AlignmentView seqstrings;

  SequenceI[] seqs;

  /**
   * use the identity matrix for calculating similarity between sequences.
   */
  private boolean nucleotide = false;

  private Vector<SequencePoint> points;

  private boolean jvCalcMode = true;

  public boolean isJvCalcMode()
  {
    return jvCalcMode;
  }

  public void run()
  {

    pca = new PCA(seqstrings.getSequenceStrings(' '), nucleotide);
    pca.setJvCalcMode(jvCalcMode);
    pca.run();

    // Now find the component coordinates
    int ii = 0;

    while ((ii < seqs.length) && (seqs[ii] != null))
    {
      ii++;
    }

    double[][] comps = new double[ii][ii];

    for (int i = 0; i < ii; i++)
    {
      if (pca.getEigenvalue(i) > 1e-4)
      {
        comps[i] = pca.component(i);
      }
    }

    top = pca.getM().rows - 1;

    points = new Vector<SequencePoint>();
    float[][] scores = pca.getComponents(top - 1, top - 2, top - 3, 100);

    for (int i = 0; i < pca.getM().rows; i++)
    {
      SequencePoint sp = new SequencePoint(seqs[i], scores[i]);
      points.addElement(sp);
    }

  }

  public void updateRc(RotatableCanvasI rc)
  {
    rc.setPoints(points, pca.getM().rows);
  }

  public boolean isNucleotide()
  {
    return nucleotide;
  }

  public void setNucleotide(boolean nucleotide)
  {
    this.nucleotide = nucleotide;
  }

  /**
   * 
   * 
   * @return index of principle dimension of PCA
   */
  public int getTop()
  {
    return top;
  }

  /**
   * update the 2d coordinates for the list of points to the given dimensions
   * Principal dimension is getTop(). Next greatest eigenvector is getTop()-1.
   * Note - pca.getComponents starts counting the spectrum from rank-2 to zero,
   * rather than rank-1, so getComponents(dimN ...) == updateRcView(dimN+1 ..)
   * 
   * @param dim1
   * @param dim2
   * @param dim3
   */
  public void updateRcView(int dim1, int dim2, int dim3)
  {
    // note: actual indices for components are dim1-1, etc (patch for JAL-1123)
    float[][] scores = pca.getComponents(dim1 - 1, dim2 - 1, dim3 - 1, 100);

    for (int i = 0; i < pca.getM().rows; i++)
    {
      ((SequencePoint) points.elementAt(i)).coord = scores[i];
    }
  }

  public String getDetails()
  {
    return pca.getDetails();
  }

  public AlignmentView getSeqtrings()
  {
    return seqstrings;
  }

  public String getPointsasCsv(boolean transformed, int xdim, int ydim,
          int zdim)
  {
    StringBuffer csv = new StringBuffer();
    csv.append("\"Sequence\"");
    if (transformed)
    {
      csv.append(",");
      csv.append(xdim);
      csv.append(",");
      csv.append(ydim);
      csv.append(",");
      csv.append(zdim);
    }
    else
    {
      for (int d = 1, dmax = pca.component(1).length; d <= dmax; d++)
      {
        csv.append("," + d);
      }
    }
    csv.append("\n");
    for (int s = 0; s < seqs.length; s++)
    {
      csv.append("\"" + seqs[s].getName() + "\"");
      double fl[];
      if (!transformed)
      {
        // output pca in correct order
        fl = pca.component(s);
        for (int d = fl.length - 1; d >= 0; d--)
        {
          csv.append(",");
          csv.append(fl[d]);
        }
      }
      else
      {
        // output current x,y,z coords for points
        fl = getPointPosition(s);
        for (int d = 0; d < fl.length; d++)
        {
          csv.append(",");
          csv.append(fl[d]);
        }
      }
      csv.append("\n");
    }
    return csv.toString();
  }

  /**
   * 
   * @return x,y,z positions of point s (index into points) under current
   *         transform.
   */
  public double[] getPointPosition(int s)
  {
    double pts[] = new double[3];
    float[] p = points.elementAt(s).coord;
    pts[0] = p[0];
    pts[1] = p[1];
    pts[2] = p[2];
    return pts;
  }

  public void setJvCalcMode(boolean state)
  {
    jvCalcMode = state;
  }

}
