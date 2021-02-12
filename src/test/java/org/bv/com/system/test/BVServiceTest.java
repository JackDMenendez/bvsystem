/**
 *
 */
package org.bv.com.system.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.bv.com.system.BVServiceRequest;
import org.bv.com.system.Method;
import org.bv.com.system.SynchronizerI;
import org.bv.com.system.TimeStatistics;
import org.bv.com.system.VotingSynchronizer;
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
class BVServiceTest {
	public static Logger globalLogger_;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TimeStatistics.ZeroStatisticsMeasure();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		TimeStatistics.ResetAll();
	}

	final int testCode = 5;

	final String testMsg = "Test Message";

	final String TestFailed = "Test Failed";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		TimeStatistics.ResetAll();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		final var stats = TimeStatistics.getStats();
		for (final TimeStatistics stat : stats) {
			System.out.println(stat.toString());
		}
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for
	 * {@link org.bv.com.system.BVServiceRequest#BVService(java.lang.Runnable, java.lang.Runnable)}.
	 */
	@DisplayName("CTOR Defaults Custom Synchronizer and Asynchronous Test")
	@Test
	@Tag("Fast")
	void testBVServiceCustomSynchronizer() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		// --------- Synchronous test ----------------------
		final var synchronizer = new VotingSynchronizer(3);
		final var testInstance1 = new BVServiceRequest(synchronizer);
		final var testInstance2 = new BVServiceRequest(synchronizer);
		// Run the service asynchronously should succeed
		testInstance1.Execute();
		testInstance2.Execute();
		synchronizer.Join();
		Assertions.assertEquals(testInstance1.rcOK, testInstance1.getReturnCode());
		Assertions.assertEquals(testInstance1.DefaultInfoMessage, testInstance1.getErrorMessage());
		Assertions.assertEquals(testInstance2.rcOK, testInstance1.getReturnCode());
		Assertions.assertEquals(testInstance2.DefaultInfoMessage, testInstance1.getErrorMessage());
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for
	 * {@link org.bv.com.system.BVServiceRequest#BVService(java.lang.Runnable, java.lang.Runnable)}.
	 */
	@DisplayName("CTOR Defaults Custom Synchronizer and Asynchronous Test")
	@Test
	@Tag("Fast")
	void testBVServiceCustomWorkerSynchronizer() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		final var counter = new AtomicInteger(0);
		// --------- Synchronous test ----------------------
		final SynchronizerI synchronizer = new VotingSynchronizer(2);
		final var testInstance1 = new BVServiceRequest(() -> counter.incrementAndGet(), synchronizer);
		// Run the service asynchronously should succeed
		testInstance1.Execute();
		synchronizer.Join();
		Assertions.assertEquals(1, testInstance1.getReturnCode());
		Assertions.assertEquals(testInstance1.DefaultUnintialized, testInstance1.getErrorMessage());
		Assertions.assertEquals(1, counter.get());
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for
	 * {@link org.bv.com.system.BVServiceRequest#BVService(java.lang.Runnable, java.lang.Runnable)}.
	 */
	@DisplayName("CTOR Defaults Default Synchronizer and Asynchronous Test")
	@Test
	@Tag("Fast")
	void testBVServiceDefaultCTORASynchronous() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		// --------- Synchronous test ----------------------
		final var testInstance = new BVServiceRequest();
		// Run the service synchronously should succeed
		testInstance.Execute();
		testInstance.Join();
		Assertions.assertEquals(testInstance.rcOK, testInstance.getReturnCode());
		Assertions.assertEquals(testInstance.DefaultInfoMessage, testInstance.getErrorMessage());
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for
	 * {@link org.bv.com.system.BVServiceRequest#BVService(java.lang.Runnable, java.lang.Runnable)}.
	 */
	@DisplayName("CTOR Defaults and Synchronous Test")
	@Test
	@Tag("Fast")
	void testBVServiceDefaultCTORSynchronous() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		// --------- Synchronous test ----------------------
		final var testInstance = new BVServiceRequest();
		// Check the default initialization is uninitialized
		Assertions.assertEquals(testInstance.rcUninitialized, testInstance.getReturnCode());
		Assertions.assertEquals(testInstance.DefaultUnintialized, testInstance.getErrorMessage());
		// Run the service synchronously should succeed
		testInstance.run();
		Assertions.assertEquals(testInstance.rcOK, testInstance.getReturnCode());
		Assertions.assertEquals(testInstance.DefaultInfoMessage, testInstance.getErrorMessage());
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for
	 * {@link org.bv.com.system.BVServiceRequest#BVService(java.lang.Runnable, java.lang.Runnable)}.
	 */
	@DisplayName("Replace default method, error run synchronously")
	@Test
	@Tag("Fast")
	void testBVServiceErrorCTORSynchronous() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		// --------- Synchronous test ----------------------
		final var testInstance = new BVServiceRequest(() -> {
			throw new Exception(TestFailed);
		}, e -> testCode);
		testInstance.run();
		Assertions.assertEquals(testCode, testInstance.getReturnCode());
		Assertions.assertEquals(testInstance.DefaultUnintialized, testInstance.getErrorMessage());
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for
	 * {@link org.bv.com.system.BVServiceRequest#BVService(java.lang.Runnable, java.lang.Runnable)}.
	 */
	@DisplayName("Test error method run synchronously")
	@Test
	@Tag("Fast")
	void testBVServiceErrorSynchronous() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		// --------- Synchronous test ----------------------
		final var testInstance = new BVServiceRequest(() -> {
			throw new Exception(TestFailed);
		});
		// should run the default error handler.
		testInstance.run();
		Assertions.assertEquals(testInstance.rcError, testInstance.getReturnCode());
		Assertions.assertEquals(TestFailed, testInstance.getErrorMessage());
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for
	 * {@link org.bv.com.system.BVServiceRequest#BVService(java.lang.Runnable, java.lang.Runnable)}.
	 */
	@DisplayName("Replace default method run synchronously")
	@Test
	@Tag("Fast")
	void testBVServiceSynchronous() {
		System.out.println("**** TEST **** " + Method.MethodInfo());
		// --------- Synchronous test ----------------------
		final var testInstance = new BVServiceRequest();
		testInstance.setWorkHandler(() -> {
			testInstance.setErrorMessage(testMsg);
			return testCode;
		});
		testInstance.run();
		Assertions.assertEquals(testCode, testInstance.getReturnCode());
		Assertions.assertEquals(testMsg, testInstance.getErrorMessage());
	}
}
