/**
 *
 */
package org.bv.com.system;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jackd
 *
 */
public class TimeStatistics {
	private static LinkedList<TimeStatistics> Stats_ = new LinkedList<>();

	public static TimeCalibration timeCalibration = new TimeCalibration();
	public static final long NANOS2MS = 1000000;

	public static long ZeroDelta = 0;

	public static TimeStatistics[] getStats() {
		synchronized (Stats_) {
			return Stats_.stream().toArray(a -> new TimeStatistics[a]);
		}
	}

	public static void ResetAll() {
		synchronized (Stats_) {
			for (final TimeStatistics stat : Stats_) {
				stat.Reset();
			}
		}
	}

	public static String toCommaDelimitedColumnTitles() {
		return "Name,Count,Average MSec,longest MSec";
	}

	/**
	 * Zero the overhead of the timer
	 *
	 * Call this before establishing timers.
	 */
	public static void ZeroStatisticsMeasure() {
		ZeroDelta = StopWatch.Zero();
	}

	public final String DoubleFormat = "%.7f";
	private String name_;
	private AtomicLong accumulator_;

	private AtomicInteger count_;

	private AtomicLong longest_;

	protected TimeStatistics() {
		synchronized (Stats_) {
			Stats_.add(this);
		}
	}

	public TimeStatistics(final String name) {
		this();
		name_ = name;
		accumulator_ = new AtomicLong(0);
		count_ = new AtomicInteger(0);
		longest_ = new AtomicLong(0);
	}

	public void Accumulate(final long uncorrectedSample) {
		long expectedLongest;
		long found;
		var sample = uncorrectedSample - ZeroDelta;
		if (sample < 0) {
			sample = 0;
		}

		do {
			expectedLongest = longest_.get();
			found = expectedLongest;

			if (sample <= expectedLongest) {
				count_.incrementAndGet();
				accumulator_.addAndGet(sample);
			} else {

				found = longest_.compareAndExchange(expectedLongest, sample);

				if (found == expectedLongest) {
					// Successfully found a new longest sample
					// Recover the old one and add it to our stats.
					count_.incrementAndGet();
					accumulator_.addAndGet(found);
				}
			}
		} while (found != expectedLongest);
	}

	protected String getAverageMS() {
		return String.format(DoubleFormat, getRawAverageMS());
	}

	protected String getLongestMS() {
		return String.format(DoubleFormat, Double.valueOf(Double.valueOf(accumulator_.get()) /
				NANOS2MS));
	}

	public String getName() {
		return name_;
	}

	public double getRawAverageMS() {

		if (count_.get() == 0) {
			return Double.valueOf(0);
		}

		return Double.valueOf(
				Double.valueOf(accumulator_.get()) / Double.valueOf(count_.get() *
						NANOS2MS));
	}

	public int getRawCount() {
		return count_.get();
	}

	public void Reset() {
		count_.set(0);
		accumulator_.set(0);
	}

	public String toCommaDelimited() {
		return name_ + "," + count_.get() + "," + getAverageMS() + "," + getLongestMS();
	}

	public String toJson() {
		return "\"" + name_ + "\" : { \"count\" : " + count_ + "," + " \"average\" : " + getAverageMS() + ","
				+ " \"longest\" : " + getLongestMS() + " }";
	}

	@Override
	public String toString() {
		return name_ + " count: " + count_.get() + " average: " + getAverageMS() + " ms" + " longest: " + getLongestMS()
				+ " ms";
	}
}
