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

function DomainMath_OctaveDataView(sFileName,variables)
	
	if(isnumeric (variables))
		pFile =fopen(sFileName,'w');
		[iRow,iCol] = size(variables);
	    fprintf(pFile,'%d|%d\n',iRow,iCol);
		for ii = 1:iRow
			for jj = 1:iCol
				if iscomplex(variables(ii,jj))
				    if(imag(variables(ii,jj)) < 0)
						fprintf(pFile,["%f"," %f","i|"],real(variables(ii,jj)),imag(variables(ii,jj)));
				    else
						fprintf(pFile,["%f","+","%f","i|"],real(variables(ii,jj)),imag(variables(ii,jj)));
					endif	
				else	
					fprintf(pFile,'%f|',variables(ii,jj));
				endif	
		    end;
		   fprintf(pFile,'\n');  

	    end;
	   fclose(pFile);  
        elseif(isstruct(variables))
         [r,c] = size(variables);
         	if(r>=2 || c>=2)
         		DomainMath_OctaveDataViewStructArray(sFileName,variables)
         	else
			DomainMath_OctaveDataViewStruct(sFileName,variables)
		endif
	elseif(ischar(variables))
		DomainMath_OctaveDataViewChar(sFileName,variables) 
	elseif(iscell(variables))
		DomainMath_OctaveDataViewCell(sFileName,variables)
	elseif(isbool(variables))
		DomainMath_OctaveDataViewBool(sFileName,variables) 
	elseif(is_function_handle(variables))
		DomainMath_OctaveDataViewFtnHandle(sFileName,variables) 
	else
		 
	endif
    


clear('sFileName');
clear('pFile');
clear('iRow');
clear('iCol');
clear('ii');
clear('jj');
clear('s');