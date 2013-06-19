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
package jalview.datamodel;

import java.awt.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class SequenceNode extends BinaryNode
{
  /** DOCUMENT ME!! */
  public float dist;

  /** DOCUMENT ME!! */
  public int count;

  /** DOCUMENT ME!! */
  public float height;

  /** DOCUMENT ME!! */
  public float ycount;

  /** DOCUMENT ME!! */
  public Color color = Color.black;

  /** DOCUMENT ME!! */
  public boolean dummy = false;

  private boolean placeholder = false;

  /**
   * Creates a new SequenceNode object.
   */
  public SequenceNode()
  {
    super();
  }

  /**
   * Creates a new SequenceNode object.
   * 
   * @param val
   *          DOCUMENT ME!
   * @param parent
   *          DOCUMENT ME!
   * @param dist
   *          DOCUMENT ME!
   * @param name
   *          DOCUMENT ME!
   */
  public SequenceNode(Object val, SequenceNode parent, float dist,
          String name)
  {
    super(val, parent, name);
    this.dist = dist;
  }

  /**
   * Creates a new SequenceNode object.
   * 
   * @param val
   *          DOCUMENT ME!
   * @param parent
   *          DOCUMENT ME!
   * @param name
   *          DOCUMENT ME!
   * @param dist
   *          DOCUMENT ME!
   * @param bootstrap
   *          DOCUMENT ME!
   * @param dummy
   *          DOCUMENT ME!
   */
  public SequenceNode(Object val, SequenceNode parent, String name,
          float dist, int bootstrap, boolean dummy)
  {
    super(val, parent, name);
    this.dist = dist;
    this.bootstrap = bootstrap;
    this.dummy = dummy;
  }

  /**
   * @param dummy
   *          true if node is created for the representation of polytomous trees
   */
  public boolean isDummy()
  {
    return dummy;
  }

  /*
   * @param placeholder is true if the sequence refered to in the element node
   * is not actually present in the associated alignment
   */
  public boolean isPlaceholder()
  {
    return placeholder;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param newstate
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean setDummy(boolean newstate)
  {
    boolean oldstate = dummy;
    dummy = newstate;

    return oldstate;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param Placeholder
   *          DOCUMENT ME!
   */
  public void setPlaceholder(boolean Placeholder)
  {
    this.placeholder = Placeholder;
  }

  /**
   * ascends the tree but doesn't stop until a non-dummy node is discovered.
   * This will probably break if the tree is a mixture of BinaryNodes and
   * SequenceNodes.
   */
  public SequenceNode AscendTree()
  {
    SequenceNode c = this;

    do
    {
      c = (SequenceNode) c.parent();
    } while ((c != null) && c.dummy);

    return c;
  }

  /**
   * test if this node has a name that might be a label rather than a bootstrap
   * value
   * 
   * @return true if node has a non-numeric label
   */
  public boolean isSequenceLabel()
  {
    if (name != null && name.length() > 0)
    {
      for (int c = 0, s = name.length(); c < s; c++)
      {
        char q = name.charAt(c);
        if ('0' <= q && q <= '9')
          continue;
        return true;
      }
    }
    return false;
  }
}
