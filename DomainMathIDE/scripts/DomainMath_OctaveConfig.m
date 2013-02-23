
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




configOb=javaObject('GridFrame',"Octave Configuration",true,false,true);
configOb.addCol("Property");
configOb.addCol("Value");
s = octave_config_info();
a=fieldnames(s);



for i=1:length(a)
	
		configOb.addRow(disp(a{i}));
		configOb.addRow(disp(getfield(s,a{i})));
	
end

configOb.showGrid()
