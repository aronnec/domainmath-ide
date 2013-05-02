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
function DomainMath_OctaveDataViewCell(sFileName,variables)
		s = variables;
		pFile =fopen(sFileName,'w');
		[r,c] = size(s);
		fprintf(pFile,'%d|%d\n',r,c);
		
		for i =1:r
			for j=1:c
			
				if(ischar(s{i,j}))
					processCharDataFromCell(pFile,s,i,j)
			   elseif(isstruct(s{i,j}))
					processStructDataFromCell(pFile,s,i,j)
			   elseif(isscalar(s{i,j}))
			   		if(iscomplex(s{i,j}))
			   			_r2=real(s{i,j});
			   			_c2=imag(s{i,j});
			   		 if(_c1 < 0)
							fprintf(pFile,["%f"," %f","i|"],_r2,_c2);
					  else
							fprintf(pFile,["%f","+","%f","i|"],_r2,_c2);
					  endif
			   		else
			   			fprintf(pFile,"%f|",s{i,j});
			   		endif
			   elseif(iscell(s{i,j}))
					processCellDataFromAnotherCell(pFile,s,i,j)
			    
			    elseif(isbool(s{i,j}))
					if(s{i,j})
						fprintf(pFile,"'true'|");
					else
						fprintf(pFile,"'false'|");
					endif
			    elseif(isnull(s{i,j}))
			    		fprintf(pFile,"'null'|");
			    elseif(ismatrix(s{i,j}))
					processMatrixDataFromCell(pFile,s,i,j)
			   
			   elseif(isobject(s{i,j}))
			   		fprintf(pFile,"object|");
			   elseif(ischar(s{i,j}))
			   		[row_char,col_char] = size(s{i,j});

			   		if(row_char >= 2)
			   			fprintf(pFile,"char<%dx%d>|",r2,c2);
			   		else
			   			fprintf(pFile,"'%s'|",s{i,j});
			   		endif
			   else
			   		fprintf(pFile,"'%s'|",s{i,j});
			   	endif
			end;
            end;

		fprintf(pFile,'\n');  
		fclose(pFile);	
endfunction

function processCharDataFromCell(pFile,s,i,j)
	char_size = size(s{i,j});
	if(length(char_size) <=2)
		fprintf(pFile,"'%s'|",s{i,j});
	else
		disp_multidim_cell(pFile,s,i,j,char_size,"char")
	endif
endfunction

function processStructDataFromCell(pFile,s,i,j)
	[r1,c1]=size(s{i,j});
	if(r1>=2 || c1>=2)
		fprintf(pFile,"struct-array<%dx%d>|",r1,c1);	
	else
		fprintf(pFile,"struct<%dx%d>|",r1,c1);	
	endif
endfunction

function processCellDataFromAnotherCell(pFile,s,i,j)
	[r3,c3]=size(s{i,j});
	cell_size = size(s{i,j});
	if(length(cell_size) <= 2)
		fprintf(pFile,"cell<%dx%d>|",r3,c3);
	else
		disp_multidim_cell(pFile,s,i,j,cell_size,"cell")
	endif
endfunction

function processMatrixDataFromCell(pFile,s,i,j)
	[r2,c2]=size(s{i,j});
	matrix_size =size(s{i,j});
	if(length(matrix_size <= 2))
		if(r2==0 && c2==0)
			fprintf(pFile,"[]|");
		else
			fprintf(pFile,"matrix<%dx%d>|",r2,c2);
		endif
	else
		disp_multidim_cell(pFile,s,i,j,matrix_size,"matrix")
	endif
endfunction