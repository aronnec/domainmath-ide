 function DomainMath_SqpStatus(x0,info,x,obj)

    ob= javaObject("ResultsFrame","");
    
    ob.appendText("Initial Guess:\n");
    ob.appendText(disp(x0));
    
    ob.appendText("\nRESULT:\n");
    ob.appendText("Solution Status:");
    status=info;
    
    if(status == 101)
		ob.appendText("The algorithm terminated normally. Either all constraints meet the requested tolerance, or the stepsize, delta x, is less than tol * norm (x). ");
    elseif(status == 102)
		ob.appendText("The BFGS update failed.  ");
    elseif(status == 103)
		ob.appendText("The maximum number of iterations was reached. ");
    else
		ob.appendText("Internal error. ");
	endif	

    ob.appendText("\nValue of the decision variables at the optimum:\n");
    ob.appendText(disp(x));
    ob.appendText("\nOptimum value of the objective function:\n");
	ob.appendText(disp(obj));
endfunction