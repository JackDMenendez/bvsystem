/**
 *
 */
package org.bv.com.system.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bv.com.system.LoggingConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * @author jackd
 *
 */
class LoggingConfigTest {
	private static Logger log;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		new LoggingConfig();
		log = Logger.getLogger(LoggingConfig.LoggerIdentity);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	public String hello(String greetings, String name) {

		log.entering(LoggingConfigTest.class.getName(), "hello", new Object[] { greetings, name });

		// lambdas
		log.finest(() -> "finest: " + LocalDateTime.now());
		log.finer(() -> "finer: " + LocalDateTime.now());
		log.fine(() -> "fine: " + LocalDateTime.now());
		log.info(() -> "info: " + LocalDateTime.now());
		log.warning(() -> "warning: " + LocalDateTime.now());
		log.severe(() -> "severe: " + LocalDateTime.now());

		// exception logging

		// throwing will be logged as FINER
		log.throwing(LoggingConfigTest.class.getName(), "hello", new Exception("test"));

		// exception + message logging with lambda
		log.log(Level.FINEST, new Exception("test"), () -> String.format("arg=%s", name));

		// exception + parameter logging with LogRecord
		final var record = new LogRecord(Level.FINEST, "arg={0}");
		record.setThrown(new Exception("test"));
		record.setLoggerName(log.getName()); // logger name will be null unless this
		record.setParameters(new Object[] { name });
		log.log(record);

		final var rc = greetings + ", " + name;

		// exiting will be logged as FINER
		log.exiting(LoggingConfigTest.class.getName(), "hello", rc);
		return rc;
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
	 * Test method for {@link org.bv.com.system.LoggingConfig#LoggingConfig()}.
	 */
	@DisplayName("Dynamic Logging Config")
	@Test
	@Tag("Fast")
	void testLoggingConfig() {
		assertEquals("Hello, Kyle", hello("Hello", "Kyle"));
	}

}
