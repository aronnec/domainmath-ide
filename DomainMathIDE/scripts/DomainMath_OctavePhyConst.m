
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

pkg load physicalconstants;
f=physical_constant();
configOb=javaObject('GridFrame',"Physical Constants",true,false,true);

	configOb.addCol('Name');
	configOb.addCol('Description');
	configOb.addCol('Value');
	configOb.addCol('Units');
	configOb.addCol('Uncertainty');

for i=1:length(f)
		s=f(i);
		configOb.addRow(disp(s.name));
		configOb.addRow(disp(s.description));
		configOb.addRow(disp(s.value));
		configOb.addRow(disp(s.units));
		configOb.addRow(disp(s.uncertanity));
end

configOb.showGrid()

clear('f');
clear('configOb');
clear('i');
clear('s');