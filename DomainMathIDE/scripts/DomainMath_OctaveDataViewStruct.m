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
function DomainMath_OctaveDataViewStruct(sFileName,variables)
	s = fieldnames(variables);
		[r,c]=size(s);
		
		pFile =fopen(sFileName,'w');
		 fprintf(pFile,'%d|%d\n',r,(c+1));
		 
		for _i =1:length(s)
		   k = getfield(variables,s{_i});
		   
		   if(ischar(k))
		   		[row_char,col_char] = size(k);
					
		   		if(row_char >=2)
		   			fprintf(pFile,"%s|char<%dx%d>|",s{_i},row_char,col_char);
		   		else
		   			fprintf(pFile,"%s|'%s'|",s{_i},k);
				endif
				
		   elseif(isstruct(k))
				[r2,c2]=size(k);
				if(r2>=2 || c2>=2)
					fprintf(pFile,"%s|struct-array<%dx%d>|",s{_i},r2,c2);
				else
		   			fprintf(pFile,"%s|struct<%dx%d>|",s{_i},r2,c2);
		   		endif
		   elseif(iscell(k))
		   		[r3,c3]=size(k);
		   		fprintf(pFile,"%s|cell<%dx%d>|",s{_i},r3,c3);
		   elseif(isbool(k))
		   		if(k)
					fprintf(pFile,"%s|'true'|",s{_i});
				else
					fprintf(pFile,"%s|'false'|",s{_i});
				endif
		   elseif(is_function_handle(k))
		   		fprintf(pFile,"%s|%s|",s{_i},func2str(k));
		   elseif(isscalar(k))
		   		if(iscomplex(k))
		   			_r1=real(k);
		   			_c1=imag(k);
		   		 	if(_c1 < 0)
							fprintf(pFile,["%f"," %f","i|"],_r1,_c1);
				    	else
							fprintf(pFile,["%f","+","%f","i|"],_r1,_c1);
				  	endif
			
		   		else
		   			fprintf(pFile,"%s|%f|",s{_i},k);
		   		endif
		   elseif(ismatrix(k))
				[r4,c4]=size(k);
				if(r4==0 && c4==0)
					fprintf(pFile,"%s|[]|",s{_i});
				else
					if(r4<=3 && c4<=3)
						_s1=mat2str(k,[4 2]);
						fprintf(pFile,"%s|%s|",s{_i},_s1);
					else
		   				fprintf(pFile,"%s|matrix<%dx%d>|",s{_i},r4,c4);	
		   			endif
		   		endif

		   elseif(isobject(k))
		   		fprintf(pFile,"%s|object|",s{_i});
		    
		   else
		   	fprintf(pFile,"%s|-|",s{_i});
		   endif
	     end;
		fprintf(pFile,'\n');  
		fclose(pFile);
		
endfunction