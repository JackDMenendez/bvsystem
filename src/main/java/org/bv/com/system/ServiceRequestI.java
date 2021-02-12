package org.bv.com.system;

import java.util.concurrent.Callable;

public interface ServiceRequestI extends Callable<ReturnCodeI>, AsyncStartI, ReturnCodeI {
	StopWatch getMeasureDispatch();

	void setErrorHandler(ErrorI errorHandler);

	void setWorkHandler(WorkI runThis);

	void shutdown();

	@Override
	void Execute();
}
