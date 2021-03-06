 #
 # Copyright (C) 2013 Vinu K.N
 # This file is a part of DomainMath IDE
 #
 # This program is free software: you can redistribute it and/or modify
 # it under the terms of the GNU General Public License as published by
 # the Free Software Foundation, either version 3 of the License, or
 # (at your option) any later version.
 #
 # This program is distributed in the hope that it will be useful,
 # but WITHOUT ANY WARRANTY; without even the implied warranty of
 # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 # GNU General Public License for more details.
 #
 # You should have received a copy of the GNU General Public License
 # along with this program.  If not, see <http://www.gnu.org/licenses/>.
 #
function disp_multidim(pFile,s,_i,_size,tag)
			fprintf(pFile,["%s|",tag,"<"],s{_i});
			   		for i=1:length(_size)
						fprintf(pFile,"%d",_size(i));
						if(i != length(_size))
							fprintf(pFile,"x");
						endif
					endfor
		   		    fprintf(pFile,">|");
	     endfunction
