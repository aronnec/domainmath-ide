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
package uk.ac.ebi.www;

public interface WSWUBlast extends java.rmi.Remote
{
  public java.lang.String runWUBlast(uk.ac.ebi.www.InputParams params,
          uk.ac.ebi.www.Data[] content) throws java.rmi.RemoteException;

  public java.lang.String checkStatus(java.lang.String jobid)
          throws java.rmi.RemoteException;

  public byte[] poll(java.lang.String jobid, java.lang.String type)
          throws java.rmi.RemoteException;

  public uk.ac.ebi.www.WSFile[] getResults(java.lang.String jobid)
          throws java.rmi.RemoteException;

  public byte[] test(java.lang.String jobid, java.lang.String type)
          throws java.rmi.RemoteException;

  public byte[] polljob(java.lang.String jobid, java.lang.String outformat)
          throws java.rmi.RemoteException;

  public byte[] doWUBlast(uk.ac.ebi.www.InputParams params, byte[] content)
          throws java.rmi.RemoteException;
}
