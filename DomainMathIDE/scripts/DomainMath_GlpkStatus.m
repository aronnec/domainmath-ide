 
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

function DomainMath_GlpkStatus(status)
	pkg load java;
	if(status == 180)
		helpdlg('Solution is optimal.','Glpk status');
	elseif(status == 181)
		helpdlg('Solution is feasible.','Glpk status');
	elseif(status == 182)
		helpdlg('Solution is infeasible.','Glpk status');
	elseif(status == 183)
		helpdlg('Problem has no feasible solution. ','Glpk status');
	elseif(status == 184)
		helpdlg('Problem has no unbounded solution. ','Glpk status');
	elseif(status == 185)
		helpdlg('Solution status undefined. ','Glpk status');
	elseif(status == 150)
		helpdlg('The interior point method is undefined. ','Glpk status');
	elseif(status == 151)
		helpdlg('The interior point method is optimal. ','Glpk status');	
	elseif(status == 170)
		helpdlg('The status is undefined. ','Glpk status');	
	elseif(status == 170)
		helpdlg('The status is undefined. ','Glpk status');	
	elseif(status == 171)
		helpdlg('The solution is integer optimal. ','Glpk status');	
	elseif(status == 172)
		helpdlg('Solution integer feasible but its optimality has not been proven ','Glpk status');	
	elseif(status == 173)
		helpdlg('No integer feasible solution. ','Glpk status');	
	elseif(status == 204)
		errordlg('Unable to start the search. ','Glpk status');	
	elseif(status == 205)
		errordlg('Objective function lower limit reached. ','Glpk status');	
	elseif(status == 206)
		errordlg('Objective function upper limit reached. ','Glpk status');	
	elseif(status == 207)
		errordlg('Iterations limit exhausted. ','Glpk status');	
	elseif(status == 208)
		errordlg('Time limit exhausted. ','Glpk status');	
	elseif(status == 209)
		errordlg('No feasible solution. ','Glpk status');	
	elseif(status == 210)
		errordlg('Numerical instability. ','Glpk status');	
	elseif(status == 211)
		errordlg('Problems with basis matrix. ','Glpk status');	
	elseif(status == 212)
		errordlg('No convergence (interior). ','Glpk status');	
	elseif(status == 213)
		errordlg('No primal feasible solution (LP presolver). ','Glpk status');	
	elseif(status == 214)
		errordlg('No dual feasible solution (LP presolver).  ','Glpk status');	
	else
		errordlg('An internal error occured. ','Glpk status');	
	endif