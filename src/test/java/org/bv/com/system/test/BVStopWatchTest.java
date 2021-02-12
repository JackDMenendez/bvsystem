/**
 *
 */
package org.bv.com.system.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.bv.com.system.Method;
import org.bv.com.system.StopWatch;
import org.bv.com.system.TimeCalibration;
import org.bv.com.system.TimeStatistics;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * @author jackd
 *
 */
class BVStopWatchTest {

	protected static TimeStatistics testArticle_;
	protected static String TestStatisticsName = "Test Accumulator";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	protected static void setUpBeforeClass() throws Exception {
		testArticle_ = new TimeStatistics(TestStatisticsName);
		TimeStatistics.ZeroStatisticsMeasure();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		testArticle_ = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		testArticle_.Reset();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.bv.com.system.StopWatch#StopWatch(org.bv.com.system.TimeStatistics)}.
	 * @throws
	 */
	@DisplayName("StopWatch reset Test")
	@Test
	@Tag("Fast")
	void testStopWatchCTOR() {
		System.out.println("**** Calibration ****");
		try {
			while(TimeCalibration.countOfRunsLeft > 0) {

				System.out.println("Calibration tests left: " +
				    TimeCalibration.countOfRunsLeft + " " +
				    TimeCalibration.CalculatedNanosPerMS +
					" Ticks/MS Error: " + TimeCalibration.error);
				Thread.sleep(5);
			}
			System.out.println("Calibration tests left: " +
				    TimeCalibration.countOfRunsLeft + " " +
				    TimeCalibration.CalculatedNanosPerMS +
					" Ticks/MS Error: " + TimeCalibration.error);
		} catch (final InterruptedException e) {
			fail("Time calibration failed");
		}
		System.out.println("**** TEST **** " + Method.MethodInfo());
		new StopWatch(testArticle_).Start().Stop().Start().Stop();
		final var foundCount = testArticle_.getRawCount();
		final var foundAverageMS = testArticle_.getRawAverageMS();
		Assertions.assertTrue(0 < foundCount);
		System.out.println("Before Reset: " + testArticle_.toString());
		testArticle_.Reset();
		final double expectedAverage = Double.valueOf(0);
		final var foundAverage = testArticle_.getRawAverageMS();
		assertEquals(expectedAverage, foundAverage);
		assertEquals(0, testArticle_.getRawCount());
		System.out.println("After Reset: " + testArticle_.toString());
		System.out.println(testArticle_.toJson());
		System.out.println(TimeStatistics.toCommaDelimitedColumnTitles());
		System.out.println(testArticle_.toCommaDelimited());
	}

	/**
	 * Test method for {@link org.bv.com.system.StopWatch#StopWatch(org.bv.com.system.TimeStatistics)}.
	 */
	@DisplayName("TimeStatistics CTOR Defaults")
	@Test
	@Tag("Fast")
	void testTimeStatisticsCTOR() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		final double expectedAverage = Double.valueOf(0);
		final var foundAverage = testArticle_.getRawAverageMS();
		assertEquals(expectedAverage, foundAverage);
		assertEquals(0, testArticle_.getRawCount());
		assertEquals(TestStatisticsName, testArticle_.getName());
	}

}
