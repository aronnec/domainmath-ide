
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
 
_version  			= ['Octave Version: ',version()];
_home 			= ['Octave Home: ',OCTAVE_HOME()];
_license 			= ['Octave License: ',license];
_canonical_host_type  	= ['Canonical Host: ',computer()];
_toolkit 			= ['Graphics Toolkit: ',graphics_toolkit()];
_string 			= [_version,10,_home,10,_license,10,_canonical_host_type ,10,_toolkit];

pkg load java;
helpdlg(_string,"Octave");

# clear all variables
clear('_version');
clear('_home');
clear('_license');
clear('_canonical_host_type');
clear('_toolkit');
clear('_string');