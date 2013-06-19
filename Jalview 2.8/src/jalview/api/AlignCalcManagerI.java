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
package jalview.api;

import jalview.datamodel.AlignmentAnnotation;

import java.util.List;

public interface AlignCalcManagerI
{

  /**
   * tell manager that a worker is initialised and has started to run
   * 
   * @param worker
   */
  void notifyStart(AlignCalcWorkerI worker);

  /**
   * check if a calculation of this type is already active
   * 
   * @param worker
   * @return
   */
  boolean alreadyDoing(AlignCalcWorkerI worker);

  /**
   * tell manager that worker is now processing data
   * 
   * @param worker
   */
  boolean notifyWorking(AlignCalcWorkerI worker);

  /**
   * notify manager that the worker has completed, and results may be ready to
   * collect
   * 
   * @param worker
   */
  void workerComplete(AlignCalcWorkerI worker);

  /**
   * indicate that a worker like this cannot run on the platform and shouldn't
   * be started again
   * 
   * @param worker
   */
  void workerCannotRun(AlignCalcWorkerI worker);

  /**
   * indicate that a worker like this may be run on the platform.
   * 
   * @param worker
   *          of class to be removed from the execution blacklist
   */
  void workerMayRun(AlignCalcWorkerI worker);

  /**
   * launch a new worker
   * 
   * @param worker
   */
  void startWorker(AlignCalcWorkerI worker);

  /**
   * 
   * @param worker
   * @return
   */
  boolean isWorking(AlignCalcWorkerI worker);

  /**
   * if any worker thread is operational, return true!
   * 
   * @return
   */
  boolean isWorking();

  /**
   * register a restartable worker
   * 
   * @param worker
   */
  void registerWorker(AlignCalcWorkerI worker);

  /**
   * restart any registered workers
   */
  void restartWorkers();

  /**
   * 
   * @param alignmentAnnotation
   * @return true if a currently registered and working worker indicates its
   *         involvement with the given alignmentAnnotation
   */
  boolean workingInvolvedWith(AlignmentAnnotation alignmentAnnotation);

  /**
   * kick any known instances of the given worker class to update their
   * annotation
   * 
   * @param workerClass
   */
  void updateAnnotationFor(Class workerClass);

  /**
   * return any registered workers of the given class
   * 
   * @param workerClass
   * @return null or one or more workers of the given class
   */
  List<AlignCalcWorkerI> getRegisteredWorkersOfClass(Class workerClass);

  /**
   * start any workers of the given class
   * 
   * @param workerClass
   * @return false if no workers of given class were registered (note -
   *         blacklisted classes cannot be restarted, so this method will return
   *         true for blacklisted workers)
   */
  boolean startRegisteredWorkersOfClass(Class workerClass);

  /**
   * work out if there is an instance of a worker that is *waiting* to start
   * calculating
   * 
   * @param workingClass
   * @return true if workingClass is already waiting to calculate. false if it
   *         is calculating, or not queued.
   */
  boolean isPending(AlignCalcWorkerI workingClass);

  /**
   * deregister and otherwise remove any registered and working instances of the
   * given worker type
   * 
   * @param typeToRemove
   */
  void removeRegisteredWorkersOfClass(Class typeToRemove);

}
