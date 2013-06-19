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
package MCview;

import java.awt.*;

public class Atom
{
  public float x;

  public float y;

  public float z;

  public int number;

  public String name;

  public String resName;

  public int resNumber;

  public char insCode = ' ';

  public String resNumIns = null;

  public int type;

  Color color = Color.lightGray;

  public String chain;

  /**
   * this is a temporary value - designed to store the position in sequence that
   * this atom corresponds to after aligning the chain to a SequenceI object. Do
   * not rely on its value being correct when visualizing sequence colourings on
   * the structure - use the StructureSelectionManager's mapping instead.
   */
  public int alignmentMapping = -1;

  public int atomIndex;

  public float occupancy = 0;

  public float tfactor = 0;

  // need these if we ever want to export Atom data
  // public boolean tfacset=true,occset=true;
  public boolean isSelected = false;

  public Atom(String str)
  {
    atomIndex = Integer.parseInt(str.substring(6, 11).trim());

    name = str.substring(12, 15).trim();

    resName = str.substring(17, 20);

    chain = str.substring(21, 22);

    resNumber = Integer.parseInt(str.substring(22, 26).trim());
    resNumIns = str.substring(22, 27);
    insCode = str.substring(26, 27).charAt(0);
    this.x = (float) (new Float(str.substring(30, 38).trim()).floatValue());
    this.y = (float) (new Float(str.substring(38, 46).trim()).floatValue());
    this.z = (float) (new Float(str.substring(47, 55).trim()).floatValue());
    // optional entries - see JAL-730
    String tm = str.substring(54, 60).trim();
    if (tm.length() > 0)
    {
      occupancy = (float) (new Float(tm)).floatValue();
    }
    else
    {
      occupancy = 1f; // default occupancy
      // see note above: occset=false;
    }
    tm = str.substring(60, 66).trim();
    if (tm.length() > 0)
    {
      tfactor = (float) (new Float(tm).floatValue());
    }
    else
    {
      tfactor = 1f;
      // see note above: tfacset=false;
    }
  }

  public Atom(float x, float y, float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  // public void setColor(Color col) {
  // this.color = col;
  // }
}
