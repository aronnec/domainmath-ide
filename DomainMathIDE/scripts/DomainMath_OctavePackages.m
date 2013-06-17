 
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
function DomainMath_OctavePackages(file_name)
	
	f=pkg("list");
 
	fid = fopen(file_name,'w');
	
	for pkg_index=1:length(f)
	if isfield(f{pkg_index},'name') , fprintf(fid,'%s|',disp(f{pkg_index}.name)); else fprintf(fid,'%s|','No name'); end;
	if isfield(f{pkg_index},'version') , fprintf(fid,'%s|',disp(f{pkg_index}.version)); else fprintf(fid,'%s|','No version'); end;
	if isfield(f{pkg_index},'description') , fprintf(fid,'%s|',disp(f{pkg_index}.description)); else fprintf(fid,'%s|','No description'); end;
	
	
	

	d=f{pkg_index}.depends; #get dependencies.
	
	if (length(d) ==1)
	    for k=1:length(d)
				if isfield(d{k},'package'),fprintf(fid,'%s-',d{k}.package); else fprintf(fid,'%s','No package name'); end;
				if isfield(d{k},'version'),fprintf(fid,'%s',d{k}.version);  else fprintf(fid,'%s','No package version '); end;
				if isfield(d{k},'operator'),fprintf(fid,' %s|',d{k}.operator); else fprintf(fid,'%s','No package operator |'); end;
	    end;
	elseif(length(d) > 1)
		 
		for k=1:length(d)
	    if isfield(d{k},'package'),fprintf(fid,'%s-',d{k}.package); else fprintf(fid,'%s',',No package name'); end;
			if isfield(d{k},'version'),fprintf(fid,'%s',d{k}.version);  else fprintf(fid,'%s','No package version '); end;
			if isfield(d{k},'operator'),fprintf(fid,' %s,',d{k}.operator); else fprintf(fid,'%s','No package operator '); end;
			
	    end;
		
    else
		fprintf(fid,' No Dependencies');
    endif		
	
	
	if (isfield(f{pkg_index},'autoload') )
		pkg_autoload   =f{pkg_index}.autoload;
		
	    if (pkg_autoload ==1) ,fprintf(fid,'|%s|','Autoloaded');else fprintf(fid,'|%s|','Not autoloaded');end;
    else
		fprintf(fid,'|%s|','Not autoloaded');
	endif

	
	if (isfield(f{pkg_index},'loaded') )
		
		pkg_load   =f{pkg_index}.loaded;
		
		
		if (pkg_load==1) ,fprintf(fid,'|%s|','Loaded');else fprintf(fid,'|%s|','Not Loaded');end;
	else
		
		fprintf(fid,'|%s|','Not Loaded');		
    endif
  
	if isfield(f{pkg_index},'categories') , fprintf(fid,'%s|',disp(f{pkg_index}.categories)); else fprintf(fid,'%s|','No category'); end;
	if isfield(f{pkg_index},'systemrequirements') , fprintf(fid,'%s|',disp(f{pkg_index}.systemrequirements)); else fprintf(fid,'%s|','No System Requirements'); end;   
	if isfield(f{pkg_index},'dir') , fprintf(fid,'%s|',disp(f{pkg_index}.dir)); else fprintf(fid,'%s|','No directory'); end;
	if isfield(f{pkg_index},'archprefix') , fprintf(fid,'%s|',disp(f{pkg_index}.archprefix)); else fprintf(fid,'%s|','No Arch Prefix '); end;

	if isfield(f{pkg_index},'author') , fprintf(fid,'%s|',disp(f{pkg_index}.author)); else fprintf(fid,'%s|','No author'); end;
	if isfield(f{pkg_index},'url') , fprintf(fid,'%s|',disp(f{pkg_index}.url)); else fprintf(fid,'%s|','No url'); end;  
	if isfield(f{pkg_index},'maintainer') , fprintf(fid,'%s|',disp(f{pkg_index}.maintainer)); else fprintf(fid,'%s|','No maintainer'); end;
	
	if isfield(f{pkg_index},'license') , fprintf(fid,'%s|',disp(f{pkg_index}.license)); else fprintf(fid,'%s|','No license'); end;
	if isfield(f{pkg_index},'date') , fprintf(fid,'%s\n',disp(f{pkg_index}.date)); else fprintf(fid,'%s\n','No date'); end;
end;

fclose(fid);

#clear all variables
clear('f');
clear('file_name');
clear('fid');
clear('file');
clear('d');
clear('k');
clear('pkg_index');
clear('pkg_autoload');
clear('pkg_load');

