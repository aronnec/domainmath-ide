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

import uk.ac.vamsas.client.Vobject;

/**
 * Implement the basic logic for synchronising changes to or from the Vamsas
 * Document. This is a more generic and normalised framework than the one
 * implemented in DatastoreItem, but probably more tedious to implement. ..
 * abandoned. Nov 2008
 * 
 * @author JimP
 */
public abstract class LocalDocSyncObject extends DatastoreItem
{
  /**
   * 
   * @return null or the local object that is being worked on.
   */
  public abstract Object getLObject();

  /**
   * 
   * @return null or the document object that is being worked on
   */
  public abstract Vobject getVObject();

  /**
   * endpoint for synchronize when all opreations are finished.
   */
  public abstract void nextObject();

  /**
   * called if the local object can be safely updated from the bound document
   * object. public abstract void updateToDoc();
   */

  /**
   * called if the associated document object can be safely updated with the
   * local changes public abstract void updateToDoc();
   */

  /**
   * @return true if the local object is modified
   */
  public abstract boolean locallyModified();

  /**
   * 
   * @return true if the bound document object is modified
   */
  public abstract boolean documentModified();

  /**
   * 
   * @return true if the document object is locked w.r.t. this object's update.
   */
  public abstract boolean documentObjectLocked();

  /**
   * 
   * @return a new datastore item instance which binds the local object to a new
   *         document object
   */
  public abstract LocalDocSyncObject newDocumentObject(); // could make this

  // constructor(Lobject)

  /**
   * 
   * @return a new datastore item instance which binds the document object to a
   *         new local object.
   */
  public abstract LocalDocSyncObject newLocalObject(); // make this

  // constructor(Vobject)

  /**
   * apply the update/commit logic as defined in the vamsas paper
   * 
   * @param documentIsUpdated
   *          true if a document update event is being handled
   */
  public void synchronize(boolean documentIsUpdated)
  {
    Object Lobject = getLObject();
    Vobject Vobject = getVObject();
    if (Lobject == null)
    {
      // no local binding for document object
      newLocalObject().synchronize(documentIsUpdated);
      return;
    }
    if (Vobject == null)
    {
      // no document binding for local object
      newDocumentObject().synchronize(documentIsUpdated);
    }
    // Check binding is valid
    if (getjv2vObj(Lobject) != Vobject)
    {
      // no local binding for document object
      newLocalObject().synchronize(documentIsUpdated);
      // no document binding for local object
      newDocumentObject().synchronize(documentIsUpdated);
    }
  }
}
