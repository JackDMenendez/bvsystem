/**
 *
 */
package org.bv.com.system;

/**
 * @author jackd
 *
 */
public class StopWatch implements AutoCloseable {

	public static long differenceNow(final long[] start) {
		final var stop = org.bv.com.windows.jni.BVNativeJNI.get().ticks();
		final var elapsed = 0L;
		final var highOrder = stop[0] - start[0];
		if (highOrder > 0) {
			if (highOrder > 1) {
				return -1;  // More than 43 days, can't handle it.
			} else {
			    return 0;
			}
		}
		return 0;
	}
	public static long Zero() {
		return new StopWatch().Start().Check();
	}
    public void Accumulator(final TimeStatistics accumulator) {
    	timeKeeper_ = accumulator;
    }
	private long begin_ = 0;
	private TimeStatistics timeKeeper_;
	private boolean started_ = false;

	protected StopWatch() {
	}

	public StopWatch(final TimeStatistics timeKeeper) {
		timeKeeper_ = timeKeeper;
	}

	/**
	 * Internal use for zeroing timer overhead
	 *
	 * @return the elapsed ticks since start;
	 */
	protected long Check() {
		assert started_;
		return System.nanoTime() - begin_;
	}

	public boolean getStarted() {
		return started_;
	}

	public StopWatch Start() {
		started_ = true;
		begin_ = System.nanoTime();
		return this;
	}

	public StopWatch Stop() {
		timeKeeper_.Accumulate(Check());
		started_ = false;
		return this;
	}

	@Override
	public void close() throws Exception {
		Stop();
	}

}
