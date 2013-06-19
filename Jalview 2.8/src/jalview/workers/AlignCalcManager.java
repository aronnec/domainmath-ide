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
package jalview.workers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jalview.api.AlignCalcManagerI;
import jalview.api.AlignCalcWorkerI;
import jalview.datamodel.AlignmentAnnotation;

public class AlignCalcManager implements AlignCalcManagerI
{
  private volatile List<AlignCalcWorkerI> restartable = Collections
          .synchronizedList(new ArrayList<AlignCalcWorkerI>());

  private volatile List<Class> blackList = Collections
          .synchronizedList(new ArrayList<Class>());

  /**
   * global record of calculations in progress
   */
  private volatile Map<Class, AlignCalcWorkerI> inProgress = Collections
          .synchronizedMap(new Hashtable<Class, AlignCalcWorkerI>());

  /**
   * record of calculations pending or in progress in the current context
   */
  private volatile Map<Class, List<AlignCalcWorkerI>> updating = Collections
          .synchronizedMap(new Hashtable<Class, List<AlignCalcWorkerI>>());

  @Override
  public void notifyStart(AlignCalcWorkerI worker)
  {
    synchronized (updating)
    {
      List<AlignCalcWorkerI> upd = updating.get(worker.getClass());
      if (upd == null)
      {
        updating.put(
                worker.getClass(),
                upd = Collections
                        .synchronizedList(new ArrayList<AlignCalcWorkerI>()));
      }
      synchronized (upd)
      {
        upd.add(worker);
      }
    }
  }

  @Override
  public boolean alreadyDoing(AlignCalcWorkerI worker)
  {
    synchronized (inProgress)
    {
      return inProgress.containsKey(worker.getClass());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.api.AlignCalcManagerI#isPending(jalview.api.AlignCalcWorkerI)
   */
  @Override
  public boolean isPending(AlignCalcWorkerI workingClass)
  {
    List<AlignCalcWorkerI> upd;
    synchronized (updating)
    {
      upd = updating.get(workingClass.getClass());
      if (upd == null)
      {
        return false;
      }
      synchronized (upd)
      {
        if (upd.size() > 1)
        {
          return true;
        }
      }
      return false;
    }
  }

  // TODO make into api method if needed ?
  public int numberLive(AlignCalcWorkerI worker)
  {
    synchronized (updating)
    {
      List<AlignCalcWorkerI> upd = updating.get(worker.getClass());
      if (upd == null)
      {
        return 0;
      }
      ;
      return upd.size();
    }
  }

  @Override
  public boolean notifyWorking(AlignCalcWorkerI worker)
  {
    synchronized (inProgress)
    {
      // TODO: decide if we should throw exceptions here if multiple workers
      // start to work
      if (inProgress.get(worker.getClass()) != null)
      {
        if (false)
        {
          System.err
                  .println("Warning: Multiple workers are running of type "
                          + worker.getClass());
        }
        return false;
      }
      inProgress.put(worker.getClass(), worker);
    }
    return true;
  }

  private final HashSet<AlignCalcWorkerI> canUpdate = new HashSet<AlignCalcWorkerI>();

  @Override
  public void workerComplete(AlignCalcWorkerI worker)
  {
    synchronized (inProgress)
    {
      // System.err.println("Worker "+worker.getClass()+" marked as complete.");
      inProgress.remove(worker.getClass());
      List<AlignCalcWorkerI> upd = updating.get(worker.getClass());
      if (upd != null)
      {
        synchronized (upd)
        {
          upd.remove(worker);
        }
        canUpdate.add(worker);
      }
    }
  }

  @Override
  public void workerCannotRun(AlignCalcWorkerI worker)
  {
    synchronized (blackList)
    {
      blackList.add(worker.getClass());
    }
  }

  public boolean isBlackListed(Class workerType)
  {
    synchronized (blackList)
    {
      return blackList.contains(workerType);
    }
  }

  @Override
  public void startWorker(AlignCalcWorkerI worker)
  {
    // System.err.println("Starting "+worker.getClass());
    // new Exception("").printStackTrace();
    Thread tw = new Thread(worker);
    tw.setName(worker.getClass().toString());
    tw.start();
  }

  @Override
  public boolean isWorking(AlignCalcWorkerI worker)
  {
    synchronized (inProgress)
    {// System.err.println("isWorking : worker "+(worker!=null ?
     // worker.getClass():"null")+ " "+hashCode());
      return worker != null && inProgress.get(worker.getClass()) == worker;
    }
  }

  @Override
  public boolean isWorking()
  {
    synchronized (inProgress)
    {
      // System.err.println("isWorking "+hashCode());
      return inProgress.size() > 0;
    }
  }

  @Override
  public void registerWorker(AlignCalcWorkerI worker)
  {
    synchronized (restartable)
    {
      if (!restartable.contains(worker))
      {
        restartable.add(worker);
      }
      startWorker(worker);
    }
  }

  @Override
  public void restartWorkers()
  {
    synchronized (restartable)
    {
      for (AlignCalcWorkerI worker : restartable)
      {
        startWorker(worker);
      }
    }
  }

  @Override
  public boolean workingInvolvedWith(AlignmentAnnotation alignmentAnnotation)
  {
    synchronized (inProgress)
    {
      for (AlignCalcWorkerI worker : inProgress.values())
      {
        if (worker.involves(alignmentAnnotation))
        {
          return true;
        }
      }
    }
    synchronized (updating)
    {
      for (List<AlignCalcWorkerI> workers : updating.values())
      {
        for (AlignCalcWorkerI worker : workers)
          if (worker.involves(alignmentAnnotation))
          {
            return true;
          }
      }
    }
    return false;
  }

  @Override
  public void updateAnnotationFor(Class workerClass)
  {

    AlignCalcWorkerI[] workers;
    synchronized (canUpdate)
    {
      workers = canUpdate.toArray(new AlignCalcWorkerI[0]);
    }
    for (AlignCalcWorkerI worker : workers)
    {
      if (workerClass.equals(worker.getClass()))
      {
        worker.updateAnnotation();
      }
    }
  }

  @Override
  public List<AlignCalcWorkerI> getRegisteredWorkersOfClass(
          Class workerClass)
  {
    List<AlignCalcWorkerI> workingClass = new ArrayList<AlignCalcWorkerI>();
    AlignCalcWorkerI[] workers;
    synchronized (canUpdate)
    {
      workers = canUpdate.toArray(new AlignCalcWorkerI[0]);
    }
    for (AlignCalcWorkerI worker : workers)
    {
      if (workerClass.equals(worker.getClass()))
      {
        workingClass.add(worker);
      }
    }
    return (workingClass.size() == 0) ? null : workingClass;
  }

  @Override
  public boolean startRegisteredWorkersOfClass(Class workerClass)
  {
    List<AlignCalcWorkerI> workers = getRegisteredWorkersOfClass(workerClass);
    if (workers == null)
    {
      return false;
    }
    for (AlignCalcWorkerI worker : workers)
    {
      if (!isPending(worker))
      {
        startWorker(worker);
      }
      else
      {
        System.err.println("Pending exists for " + workerClass);
      }
    }
    return true;
  }

  @Override
  public void workerMayRun(AlignCalcWorkerI worker)
  {
    synchronized (blackList)
    {
      if (blackList.contains(worker.getClass()))
      {
        blackList.remove(worker.getClass());
      }
    }
  }

  @Override
  public void removeRegisteredWorkersOfClass(Class typeToRemove)
  {
    List<AlignCalcWorkerI> workers = getRegisteredWorkersOfClass(typeToRemove);
    List<AlignCalcWorkerI> removable = new ArrayList<AlignCalcWorkerI>();
    Set<AlignCalcWorkerI> toremovannot = new HashSet<AlignCalcWorkerI>();
    synchronized (restartable)
    {
      for (AlignCalcWorkerI worker : restartable)
      {
        if (typeToRemove.equals(worker.getClass()))
        {
          removable.add(worker);
          toremovannot.add(worker);
        }
      }
      restartable.removeAll(removable);
    }
    synchronized (canUpdate)
    {
      for (AlignCalcWorkerI worker : canUpdate)
      {
        if (typeToRemove.equals(worker.getClass()))
        {
          removable.add(worker);
          toremovannot.add(worker);
        }
      }
      canUpdate.removeAll(removable);
    }
    // TODO: finish testing this extension

    /*
     * synchronized (inProgress) { // need to kill or mark as dead any running
     * threads... (inProgress.get(typeToRemove)); }
     * 
     * if (workers == null) { return; } for (AlignCalcWorkerI worker : workers)
     * {
     * 
     * if (isPending(worker)) { worker.abortAndDestroy(); startWorker(worker); }
     * else { System.err.println("Pending exists for " + workerClass); } }
     */
  }
}
