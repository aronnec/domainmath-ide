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
package jalview.ws.params;

import java.util.List;

/**
 * A web service parameter set
 * 
 */
public interface WsParamSetI
{
  /**
   * Human readable name for parameter set
   * 
   * @return unique string (given applicable URLs)
   */
  public String getName();

  /**
   * @return notes about this parameter set
   */
  public String getDescription();

  /**
   * the service endpoints for which this parameter set is valid
   * 
   * @return one or more URLs
   */
  public String[] getApplicableUrls();

  /**
   * 
   * @return null, or the file used to store this parameter set.
   */
  public String getSourceFile();

  /**
   * set the filename used to store this parameter set.
   * 
   * @newfile
   */
  public void setSourceFile(String newfile);

  /**
   * is this a preset or a user modifiable parameter set
   * 
   * @return true if set can be modified
   */
  public boolean isModifiable();

  /**
   * 
   * @return arguments in preset
   */
  List<ArgumentI> getArguments();

  /**
   * set the arguments for the preset. Should this preset instance be
   * unmodifiable, an Error should be thrown.
   * 
   * @param args
   */
  public void setArguments(List<ArgumentI> args);
}
