/**
 * 
 */
package org.bv.com.system.test;

import org.bv.com.system.BVLog;
import org.bv.com.system.subsystem.SubsystemBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author jackd
 *
 */
class BVLogTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.bv.com.system.BVLog#main(java.lang.String[])}.
	 */
	@Test
	void testMain() {
		String[] args = { BVLog.name, SubsystemBase.START_COMMAND };
		BVLog.main(args);
		BVLog.info("Test Complete");

	}

}
