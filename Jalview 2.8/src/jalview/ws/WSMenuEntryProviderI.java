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
package jalview.ws;

import jalview.gui.AlignFrame;

import javax.swing.JMenu;

public interface WSMenuEntryProviderI
{
  /**
   * Called by the AlignFrame web service menu constructor to enable a service
   * instance to add menu entries. Usually, any actionperformed methods
   * associated with menu items will create new instances of GUI classes to
   * handle the action in a thread-safe manner.
   * 
   * @param wsmenu
   *          the menu to which any menu entries/sub menus are to be attached
   * @param alignFrame
   *          the alignFrame instance that provides input data for the service
   */
  public void attachWSMenuEntry(JMenu wsmenu, final AlignFrame alignFrame);
}
