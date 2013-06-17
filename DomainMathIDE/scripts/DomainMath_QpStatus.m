function DomainMath_QpStatus(x0,H,info,x,obj)
    
			ob= javaObject("ResultsFrame","");
         ob.appendText("Initial Guess:\n");
          	ob.appendText(disp(x0));
          	ob.appendText("Quadratic Penality:\n");
          	ob.appendText(disp(H));
			ob.appendText("\nRESULT:\n");
			ob.appendText("Solution Status:");
			status=info.info;
			if(status == 0)
				ob.appendText("The problem is feasible and convex. Global solution found. ");
			elseif(status == 1)
				ob.appendText("The problem is not convex. Local solution found. ");
			elseif(status == 2)
				ob.appendText("The problem is not convex and unbounded. ");
			elseif(status == 3)
				ob.appendText("Maximum number of iterations reached. ");
			elseif(status == 6)
				ob.appendText("The problem is infeasible. ");
			else
				ob.appendText("Internal error. ");
			endif
          	ob.appendText("\nValue of the decision variables at the optimum:\n");
			ob.appendText(disp(x));
			ob.appendText("\nOptimum value of the objective function:\n");
			ob.appendText(disp(obj));
endfunction