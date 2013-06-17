
function DomainMath_Multicore(fun_handle,common_dir,parameterCell,octave_path,maMasterEvaluations,resultCell)
    pkg load multicore;
	[in0, out0, pid0] = popen2 (octave_path, "-q");
	input_str = ['startmulticoreslave(''',common_dir,''');'];
	fputs (in0, input_str);
	fputs (in0, sprintf('\n'));

	tic
	resultCell = startmulticoremaster(fun_handle, parameterCell,...
									 common_dir, maxMasterEvaluations);
	toc
	resultCell
	
	fclose(in0)
	fclose(out0)
endfunction