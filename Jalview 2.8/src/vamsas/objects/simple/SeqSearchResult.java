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
package vamsas.objects.simple;

public class SeqSearchResult extends vamsas.objects.simple.Result implements
        java.io.Serializable
{
  private vamsas.objects.simple.Alignment alignment;

  private java.lang.String annotation;

  private java.lang.String features;

  private java.lang.String newickTree;

  public SeqSearchResult()
  {
  }

  public SeqSearchResult(vamsas.objects.simple.Alignment alignment,
          java.lang.String annotation, java.lang.String features,
          java.lang.String newickTree)
  {
    this.alignment = alignment;
    this.annotation = annotation;
    this.features = features;
    this.newickTree = newickTree;
  }

  /**
   * Gets the alignment value for this SeqSearchResult.
   * 
   * @return alignment
   */
  public vamsas.objects.simple.Alignment getAlignment()
  {
    return alignment;
  }

  /**
   * Sets the alignment value for this SeqSearchResult.
   * 
   * @param alignment
   */
  public void setAlignment(vamsas.objects.simple.Alignment alignment)
  {
    this.alignment = alignment;
  }

  /**
   * Gets the annotation value for this SeqSearchResult.
   * 
   * @return annotation
   */
  public java.lang.String getAnnotation()
  {
    return annotation;
  }

  /**
   * Sets the annotation value for this SeqSearchResult.
   * 
   * @param annotation
   */
  public void setAnnotation(java.lang.String annotation)
  {
    this.annotation = annotation;
  }

  /**
   * Gets the features value for this SeqSearchResult.
   * 
   * @return features
   */
  public java.lang.String getFeatures()
  {
    return features;
  }

  /**
   * Sets the features value for this SeqSearchResult.
   * 
   * @param features
   */
  public void setFeatures(java.lang.String features)
  {
    this.features = features;
  }

  /**
   * Gets the newickTree value for this SeqSearchResult.
   * 
   * @return newickTree
   */
  public java.lang.String getNewickTree()
  {
    return newickTree;
  }

  /**
   * Sets the newickTree value for this SeqSearchResult.
   * 
   * @param newickTree
   */
  public void setNewickTree(java.lang.String newickTree)
  {
    this.newickTree = newickTree;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj)
  {
    if (!(obj instanceof SeqSearchResult))
      return false;
    SeqSearchResult other = (SeqSearchResult) obj;
    if (obj == null)
      return false;
    if (this == obj)
      return true;
    if (__equalsCalc != null)
    {
      return (__equalsCalc == obj);
    }
    __equalsCalc = obj;
    boolean _equals;
    _equals = super.equals(obj)
            && ((this.alignment == null && other.getAlignment() == null) || (this.alignment != null && this.alignment
                    .equals(other.getAlignment())))
            && ((this.annotation == null && other.getAnnotation() == null) || (this.annotation != null && this.annotation
                    .equals(other.getAnnotation())))
            && ((this.features == null && other.getFeatures() == null) || (this.features != null && this.features
                    .equals(other.getFeatures())))
            && ((this.newickTree == null && other.getNewickTree() == null) || (this.newickTree != null && this.newickTree
                    .equals(other.getNewickTree())));
    __equalsCalc = null;
    return _equals;
  }

  private boolean __hashCodeCalc = false;

  public synchronized int hashCode()
  {
    if (__hashCodeCalc)
    {
      return 0;
    }
    __hashCodeCalc = true;
    int _hashCode = super.hashCode();
    if (getAlignment() != null)
    {
      _hashCode += getAlignment().hashCode();
    }
    if (getAnnotation() != null)
    {
      _hashCode += getAnnotation().hashCode();
    }
    if (getFeatures() != null)
    {
      _hashCode += getFeatures().hashCode();
    }
    if (getNewickTree() != null)
    {
      _hashCode += getNewickTree().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

}
