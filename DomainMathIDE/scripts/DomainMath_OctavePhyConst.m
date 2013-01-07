
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
function DomainMath_OctPhyConst(file_name)
f = physical_constant();

fid= fopen(file_name,'w');
	
for _dmn_index=1:length(f);
	fprintf(fid,'%s|',disp(f(_dmn_index).name));
	fprintf(fid,'%s|',disp(f(_dmn_index).description)); 
	fprintf(fid,'%s|',disp(f(_dmn_index).value)); 
	
	
if (strcmp(f(_dmn_index).units,"")),fprintf(fid,'-|'); else fprintf(fid,'%s|',disp(f(_dmn_index).units)); end;
 fprintf(fid,'%s\n',disp(f(_dmn_index).uncertanity)); 

end;

fclose(fid);
clear('f');
clear('file_name');
clear('fid');
clear('_dmn_index');

