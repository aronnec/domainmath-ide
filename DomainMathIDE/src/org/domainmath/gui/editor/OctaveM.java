
/*
 * Copyright (C) 2011 Vinu K.N
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.domainmath.gui.editor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class OctaveM {

     public List  getKey(String datafile) {
           String line;
        List data =Collections.synchronizedList(new ArrayList());
        try {
            FileInputStream fin = new FileInputStream(datafile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            try {
             
                    while((line=br.readLine()) != null) {
                        StringTokenizer s2 = new StringTokenizer(line,"\n");
                        while(s2.hasMoreTokens()) {
                            data.add(s2.nextToken());
                        }
                  
                }
                br.close();
            } catch (IOException ex) {
             //   ex.printStackTrace();
            }
            
            
        } catch (FileNotFoundException ex) {
            //ex.printStackTrace();
        }
         return data;
       }
  
}
