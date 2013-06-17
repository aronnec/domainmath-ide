function DomainMath_GlpkStatus(c, A, b,sense,xopt, fmin, status)
    ob= javaObject("ResultsFrame","");
      if(sense == 1)
				ob.appendText("Problem type:Minimization\n");
			else
				ob.appendText("Problem type:Maximization\n");
      endif
        
      ob.appendText("Objective Function:\n");
			ob.appendText(disp(c));
			ob.appendText("Constraints Coefficients:\n");
			ob.appendText(disp(A));
			ob.appendText("RHS value for constraints coefficients:\n");
			ob.appendText(disp(b));
			ob.appendText("\nRESULT:\n");
			ob.appendText("Solution Status:");
			if(status == 180)
				ob.appendText("Solution is optimal." );
			elseif(status == 181)
				ob.appendText("Solution is feasible." );
			elseif(status == 182)
				ob.appendText("Solution is infeasible." );
			elseif(status == 183)
				ob.appendText("Problem has no feasible solution. " );
			elseif(status == 184)
				ob.appendText("Problem has no unbounded solution. " );
			elseif(status == 185)
				ob.appendText("Solution status undefined. " );
			elseif(status == 150)
				ob.appendText("The interior point method is undefined. " );
			elseif(status == 151)
				ob.appendText("The interior point method is optimal. " );	
			elseif(status == 170)
				ob.appendText("The status is undefined. " );	
			elseif(status == 170)
				ob.appendText("The status is undefined. " );	
			elseif(status == 171)
				ob.appendText("The solution is integer optimal. " );	
			elseif(status == 172)
				ob.appendText("Solution integer feasible but its optimality has not been proven " );	
			elseif(status == 173)
				ob.appendText("No integer feasible solution. " );	
			elseif(status == 204)
				ob.appendText("Error-Unable to start the search. " );	
			elseif(status == 205)
				ob.appendText("Error-Objective function lower limit reached. " );	
			elseif(status == 206)
				ob.appendText("Error-Objective function upper limit reached. " );	
			elseif(status == 207)
				ob.appendText("Error-Iterations limit exhausted. " );	
			elseif(status == 208)
				ob.appendText("Error-Time limit exhausted. " );	
			elseif(status == 209)
				ob.appendText("Error-No feasible solution. " );	
			elseif(status == 210)
				ob.appendText("Error-Numerical instability. " );	
			elseif(status == 211)
				ob.appendText("Error-Problems with basis matrix. " );	
			elseif(status == 212)
				ob.appendText("Error-No convergence (interior). " );	
			elseif(status == 213)
				ob.appendText("Error-No primal feasible solution (LP presolver). " );	
			elseif(status == 214)
				ob.appendText("Error-No dual feasible solution (LP presolver).  " );	
			else
				ob.appendText("Error-An internal error occured. " );	
      endif
        
      ob.appendText("\nValue of the decision variables at the optimum:\n");
			ob.appendText(disp(xopt));
			ob.appendText("\nOptimum value of the objective function:\n");
			ob.appendText(disp(fmin));
endfunction