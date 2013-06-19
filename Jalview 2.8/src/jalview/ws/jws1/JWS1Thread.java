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
package jalview.ws.jws1;

import jalview.datamodel.*;
import jalview.gui.*;
import jalview.ws.AWSThread;

/**
 * specific methods for Jalview WS1 web service jobs (will probably disappear)
 * 
 * @author JimP
 * 
 */
public abstract class JWS1Thread extends AWSThread
{

  public JWS1Thread(AlignFrame alFrame, WebserviceInfo wsinfo,
          AlignmentView alview, String wsname, String wsUrl)
  {
    super(alFrame, wsinfo, alview, wsname, wsUrl);
  }

  public JWS1Thread(AlignFrame alframe, WebserviceInfo wsinfo,
          AlignmentView alview, String wsurl)
  {
    super(alframe, wsinfo, alview, wsurl);
  }

}
