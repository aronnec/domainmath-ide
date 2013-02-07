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
		s = fieldnames(variables);
		[r,c]=size(s);

		pFile =fopen(sFileName,'w');
		 fprintf(pFile,'%d|%d\n',r,(c+1));
		 
		for _i =1:length(s)
		   k = getfield(variables,s{_i});
		   
		   if(ischar(k))
		   		fprintf(pFile,"%s|'%s'|",s{_i},k);

		   elseif(isstruct(k))
				[r2,c2]=size(k);
		   		fprintf(pFile,"%s|struct<%dx%d>|",s{_i},r2,c2);
		   elseif(iscell(k))
		   		[r3,c3]=size(k);
		   		fprintf(pFile,"%s|cell<%dx%d>|",s{_i},r3,c3);
		   elseif(isbool(k))
		   		if(k)
					fprintf(pFile,"%s|'true'|",s{_i});
				else
					fprintf(pFile,"%s|'false'|",s{_i});
				endif
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
					fprintf(pFile,"[]|");
				else
		   			fprintf(pFile,"%s|matrix<%dx%d>|",s{_i},r4,c4);	
		   		endif
		   		
		   elseif(isvector(k))
				[r5,c5]=size(k);
		   		fprintf(pFile,"%s|vector<%dx%d>|",s{_i},r5,c5);
		   else
		   	fprintf(pFile,"%s|-|",s{_i});
		   endif
	     end;
		fprintf(pFile,'\n');  
		fclose(pFile);
	elseif(ischar(variables))
		pFile =fopen(sFileName,'w');
		fprintf(pFile,'1|1\n');
		fprintf(pFile,"'%s'|",variables);
		fprintf(pFile,'\n'); 
		fclose(pFile);  
	elseif(iscell(variables))
		s = variables;
		pFile =fopen(sFileName,'w');
		fprintf(pFile,'%d|1\n',length(s));
		
		for _i =1:length(s)
		   if(isstruct(s{_i}))
				[r1,c1]=size(s{_i});
		   		fprintf(pFile,"struct<%dx%d>|",r1,c1);	
		   elseif(isscalar(s{_i}))
		   		if(iscomplex(s{_i}))
		   			_r2=real(s{_i});
		   			_c2=imag(s{_i});
		   		 if(_c1 < 0)
						fprintf(pFile,["%f"," %f","i|"],_r2,_c2);
				  else
						fprintf(pFile,["%f","+","%f","i|"],_r2,_c2);
				  endif
		   		else
		   			fprintf(pFile,"%f|",s{_i});
		   		endif
		   elseif(iscell(s{_i}))
				[r3,c3]=size(s{_i});
		   		fprintf(pFile,"cell<%dx%d>|",r3,c3);
		    elseif(ischar(s{_i}))
		   		fprintf(pFile,"'%s'|",s{_i});
		    elseif(isbool(s{_i}))
				if(s{_i})
					fprintf(pFile,"'true'|");
				else
					fprintf(pFile,"'false'|");
				endif
		    elseif(isnull(s{_i}))
		    		fprintf(pFile,"'null'|");
		    elseif(ismatrix(s{_i}))
				[r2,c2]=size(s{_i});
				if(r2==0 && c2==0)
					fprintf(pFile,"[]|");
				else
		   			fprintf(pFile,"matrix<%dx%d>|",r2,c2);
		   		endif
		   elseif(isvector(s{_i}))
				[r3,c3]=size(s{_i});
		   		fprintf(pFile,"vector<%dx%d>|",r3,c3);
		   else
		   		fprintf(pFile,"'%s'|",s{_i});
		   	endif

            end;

		fprintf(pFile,'\n');  
		fclose(pFile);	
	elseif(isbool(variables))
		pFile =fopen(sFileName,'w');
		fprintf(pFile,'1|1\n');
		if(variables)
			fprintf(pFile,"'true'|");
		else
			fprintf(pFile,"'false'|");
		endif
		fprintf(pFile,'\n'); 
		fclose(pFile); 
	else
		pFile =fopen(sFileName,'w');
		fprintf(pFile,'1|1\n');
		fprintf(pFile,"unknown|");

		fprintf(pFile,'\n'); 
		fclose(pFile);  
	endif
    


clear('sFileName');
clear('pFile');
clear('iRow');
clear('iCol');
clear('ii');
clear('jj');
clear('s');
clear('_i');