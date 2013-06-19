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
package jalview.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JalviewChangeSupport implements PropertyChangeListener
{
  public void propertyChange(PropertyChangeEvent evt)
  {
    // Handle change events - most are simply routed to other sources
    changeSupport.firePropertyChange(evt);
  }

  /**
   * change listeners are notified of changes to resources so they can update
   * their state. E.g. - the 'services' property notifies when the available set
   * of web service endpoints have changed.
   */
  private java.beans.PropertyChangeSupport changeSupport = new java.beans.PropertyChangeSupport(
          this);

  /**
   * @param propertyName
   * @param listener
   * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
   *      java.beans.PropertyChangeListener)
   */
  public void addJalviewPropertyChangeListener(String propertyName,
          PropertyChangeListener listener)
  {
    changeSupport.addPropertyChangeListener(propertyName, listener);
  }

  /**
   * @param listener
   * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
   */
  public void addJalviewPropertyChangeListener(
          PropertyChangeListener listener)
  {
    changeSupport.addPropertyChangeListener(listener);
  }

  /*
   * @param propertyName
   * 
   * @param oldValue
   * 
   * @param newValue
   * 
   * @see java.beans.PropertyChangeSupport#firePropertyChange(java.lang.String,
   * java.lang.Object, java.lang.Object) public void firePropertyChange(String
   * propertyName, Object oldValue, Object newValue) {
   * changeSupport.firePropertyChange(propertyName, oldValue, newValue); }
   */

  /**
   * @param propertyName
   * @param listener
   * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
   *      java.beans.PropertyChangeListener)
   */
  public void removeJalviewPropertyChangeListener(String propertyName,
          PropertyChangeListener listener)
  {
    changeSupport.removePropertyChangeListener(propertyName, listener);
  }

}
