/**
 *
 */
package org.bv.com.system;

import java.util.concurrent.ScheduledThreadPoolExecutor;

///////////////////////////////////////////////////////////////////////////////
/**
 * Basic unit of asynchronous work.
 *
 * This class vaguely resembles a scheduleable unit of work on a certain
 * operating system. It is mostly an coincidence of design but the name
 * ServiceRequest points out the resemblance.
 *
 * Starting with Servlet 3.0, JEE supports true asynchronous processing in a
 * Servlet. We will take advantage of that with this design.
 *
 * There are different kinds of asynchronous calls supported by JEE and Java
 * that we will need to take advantage of. This class encapsulates Java and JEE
 * asynchronous patterns.
 *
 * Another recent Java technology as of Java 8 released in 2014. There are a
 * number of lightweight interfaces designed to support use of lambdas as
 * asynchronous functions in our model.
 *
 * @author jackd
 *
 */
public class BVServiceRequest implements ServiceRequestI {

	protected static TimeStatistics taskDispatchStats = new TimeStatistics("Time to Dispatch a Service");
	protected static TimeStatistics taskExecutionStats = new TimeStatistics("Service Task Execution Time");
	public final String DefaultInfoMessage = "ok";
	public final String DefaultUnintialized = "uninitialized";
	public final int rcError = 1;
	public final int rcOK = 0;
	public final int rcUninitialized = -1;
	/**
	 * Specifies the default number of free threads to maintain in the thread pool.
	 */
	public final int MaintainDefaultFreeThreadCount = 10;

	// Sorted by attribute name
	private ErrorI errorHandler_ = null;
	private String errorMessage_;
	private ScheduledThreadPoolExecutor executor_;
	private int returnCode_;
	private WorkI runMe_ = null;
	private SynchronizerI synchronizer_ = null;
	private final StopWatch measureDispatch = new StopWatch(taskDispatchStats);
	private final StopWatch measureExecute = new StopWatch(taskExecutionStats);

	/**
	 * Services that inherit can setup there own lambdas or override run
	 *
	 * The lambdas can call other methods of inheriting classes making almost any
	 * method asynchronous. Change the lambdas for subsequent async calls if needed.
	 */
	public BVServiceRequest() {
		setReturnCode(rcUninitialized);
		setErrorMessage(DefaultUnintialized);
		// Default error handler
		setErrorHandler(e -> {
			setErrorMessage(e.getMessage());
			return rcError;
		});
		// Stubbed worker
		setWorkHandler(() -> {
			setErrorMessage(DefaultInfoMessage);
			return rcOK;
		});
	}

	/**
	 * Allows sharing a synchronizer
	 *
	 * @param synchronizer A shared synchronizer between threads
	 */
	public BVServiceRequest(SynchronizerI synchronizer) {
		this();
		setSynchronizer(synchronizer);
	}

	/**
	 * Allows specification of a worker
	 *
	 * @param runThis
	 */
	public BVServiceRequest(WorkI runThis) {
		this();
		setWorkHandler(runThis);
	}

	/**
	 * Allows replacing the worker and error handler
	 *
	 * @param runMe   the worker
	 * @param catchMe exception handler
	 */
	public BVServiceRequest(WorkI runMe, ErrorI catchMe) {
		setReturnCode(rcUninitialized);
		setErrorMessage(DefaultUnintialized);
		setWorkHandler(runMe);
		setErrorHandler(catchMe);
	}

	/**
	 * Allows replacing the worker and error handler
	 *
	 * @param runMe   the worker
	 * @param catchMe exception handler
	 */
	public BVServiceRequest(WorkI runMe, ErrorI catchMe, SynchronizerI synchronizer) {
		setSynchronizer(synchronizer);
		setReturnCode(rcUninitialized);
		setErrorMessage(DefaultUnintialized);
		setWorkHandler(runMe);
		setErrorHandler(catchMe);
	}

	/**
	 * Allows specification of worker and error handler
	 *
	 * @param runThis      worker
	 * @param synchronizer exception handler
	 */
	public BVServiceRequest(WorkI runThis, SynchronizerI synchronizer) {
		this(runThis);
		setSynchronizer(synchronizer);
	}

	/**
	 * For use through asynchronous loops
	 */
	@Override
	public ReturnCodeI call() {
		run();
		return this;
	}

	protected void Complete() {
		if (getSynchronizer() != null) {
			getSynchronizer().Ready();
		}

	}

	/**
	 * Run a call asynchronously and return when it is done.
	 *
	 * Use this call with an AsyncContext with allows the Servlet to continue
	 * working until this returns otherwise acts like a synchronous coroutine call.
	 */
	@Override
	public void Execute() {
		Start();
	}

	protected ErrorI getCatchMe() {
		return errorHandler_;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage_;
	}

	@Override
	public StopWatch getMeasureDispatch() {
		return measureDispatch;
	}

	@Override
	public int getReturnCode() {
		return returnCode_;
	}

	protected WorkI getRunMe() {
		return runMe_;
	}

	@Override
	public SynchronizerI getSynchronizer() {
		return synchronizer_;
	}

	@Override
	public void Join() {
		Complete();
	}

	/**
	 * Run the processing stored in runMe.
	 */
	@Override
	public void run() {
		if (measureDispatch.getStarted()) {
			measureDispatch.Stop();
		}

		measureExecute.Start();
		try {
			if (getRunMe() != null) {
				setReturnCode(getRunMe().Worker());
			}
		} catch (final Exception e) {
			setReturnCode(getCatchMe().Error(e));
		}
		measureExecute.Stop();
		Complete();
	}

	@Override
	public void setErrorHandler(ErrorI errorHandler) {
		errorHandler_ = errorHandler;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		errorMessage_ = errorMessage;
	}

	@Override
	public void setReturnCode(int returnCode) {
		returnCode_ = returnCode;
	}

	@Override
	public void setSynchronizer(SynchronizerI synchronizer) {
		synchronizer_ = synchronizer;
	}

	@Override
	public void setWorkHandler(WorkI runThis) {
		runMe_ = runThis;
	}

	/**
	 * Starts the runnable method in another thread and does not wait
	 */
	@Override
	public void Start() {
		if (getSynchronizer() == null) {
			setSynchronizer(new VotingSynchronizer(2));
		}

		executor_ = new ScheduledThreadPoolExecutor(MaintainDefaultFreeThreadCount);

		executor_.execute(this);
	}

	@Override
	public void shutdown() {
		if (executor_ != null) {
			executor_.shutdownNow();
		}
	}
}
