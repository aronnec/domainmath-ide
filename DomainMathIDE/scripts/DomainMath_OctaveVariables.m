 
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

function DomainMath_OctaveVariables(sFileName,structWhos)
pFile =fopen(sFileName,'w');


for iStructWhos=1:length(structWhos)
	fprintf(pFile,'%s|',structWhos(iStructWhos).name);
	_size = structWhos(iStructWhos).size;
	for i=1:length(_size)
		fprintf(pFile,"%d",_size(i));
		if(i != length(_size))
			fprintf(pFile,"x");
		endif
	endfor
	fprintf(pFile,'|%d|',structWhos(iStructWhos).bytes);
	fprintf(pFile,'%s\n',structWhos(iStructWhos).class);

        desc="";

	if(structWhos(iStructWhos).global)

                if(structWhos(iStructWhos).sparse)
                    desc = "global-sparse";
                elseif(structWhos(iStructWhos).complex)
                    desc = "global-complex";
                elseif(structWhos(iStructWhos).nesting)
                    desc = "global-nesting";
                else
                    desc = "global";
		endif
	elseif(structWhos(iStructWhos).persistent)
		if(structWhos(iStructWhos).sparse)
                    desc = "persistent-sparse";
                elseif(structWhos(iStructWhos).complex)
                    desc = "persistent-complex";
                elseif(structWhos(iStructWhos).nesting)
                    desc = "persistent-nesting";
                else
                    desc = "persistent";
		endif	
        else
                if(structWhos(iStructWhos).sparse)
                    desc = "local-sparse";
                elseif(structWhos(iStructWhos).complex)
                    desc = "local-complex";
                elseif(structWhos(iStructWhos).nesting)
                    desc = "local-nesting";
                else
                    desc = "local";
		endif
	endif
	fprintf(pFile,'%s\n',desc);
	
end;
fclose(pFile);

# clear all variables
clear('pFile');
clear('structWhos');
clear('iStructWhos');
clear('_size');
clear('desc');
clear('sFileName');
endfunction