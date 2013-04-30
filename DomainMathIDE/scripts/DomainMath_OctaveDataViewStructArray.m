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
function DomainMath_OctaveDataViewStructArray(sFileName,variables)
		s=variables;
            [r,c]=size(variables);
                
			pFile =fopen(sFileName,'w');
		 	fprintf(pFile,'%d|%d\n',r,c);
		 	
                        for i =1:r
			for j=1:c
			
				if(ischar(s(i,j)))
			   		fprintf(pFile,"'%s'|",s(i,j));
			   elseif(isstruct(s(i,j)))
					[r1,c1]=size(s(i,j));
					if(r1>=2 || c1>=2)
						fprintf(pFile,"struct-array<%dx%d>|",r1,c1);	
					else
						fprintf(pFile,"struct<%dx%d>|",r1,c1);	
					endif
			   elseif(isscalar(s(i,j)))
			   		if(iscomplex(s(i,j)))
			   			_r2=real(s(i,j));
			   			_c2=imag(s(i,j));
			   		 if(_c1 < 0)
							fprintf(pFile,["%f"," %f","i|"],_r2,_c2);
					  else
							fprintf(pFile,["%f","+","%f","i|"],_r2,_c2);
					  endif
			   		else
			   			fprintf(pFile,"%f|",s(i,j));
			   		endif
			   elseif(iscell(s(i,j)))
					[r3,c3]=size(s(i,j));
			   		fprintf(pFile,"cell<%dx%d>|",r3,c3);
			    
			    elseif(isbool(s(i,j)))
					if(s(i,j))
						fprintf(pFile,"'true'|");
					else
						fprintf(pFile,"'false'|");
					endif
			    elseif(isnull(s(i,j)))
			    		fprintf(pFile,"'null'|");
			    elseif(ismatrix(s(i,j)))
					[r2,c2]=size(s(i,j));
					if(r2==0 && c2==0)
						fprintf(pFile,"[]|");
					else
			   			fprintf(pFile,"matrix<%dx%d>|",r2,c2);
			   		endif
			   
			   elseif(isobject(s(i,j)))
			   		fprintf(pFile,"object|");
			   else
			   		fprintf(pFile,"'%s'|",s(i,j));
			   	endif
			end;
            end;

		fprintf(pFile,'\n');  
		fclose(pFile);	
endfunction