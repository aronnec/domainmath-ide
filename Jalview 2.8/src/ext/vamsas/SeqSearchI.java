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
package ext.vamsas;

public interface SeqSearchI extends java.rmi.Remote
{
  public java.lang.String getDatabase() throws java.rmi.RemoteException;

  public vamsas.objects.simple.SeqSearchResult getResult(
          java.lang.String job_id) throws java.rmi.RemoteException;

  public vamsas.objects.simple.WsJobId psearch(
          vamsas.objects.simple.Alignment al, java.lang.String database)
          throws java.rmi.RemoteException;

  public vamsas.objects.simple.WsJobId search(
          vamsas.objects.simple.Sequence s, java.lang.String database)
          throws java.rmi.RemoteException;

  public vamsas.objects.simple.WsJobId cancel(java.lang.String jobId)
          throws java.rmi.RemoteException;
}
