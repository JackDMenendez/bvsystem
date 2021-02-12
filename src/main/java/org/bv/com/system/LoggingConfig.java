/**
 *
 */
package org.bv.com.system;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author jackd
 *
 */
public class LoggingConfig {
	public static String LoggerIdentity = "BVApplication";

	public LoggingConfig() {
		try {

			// Programmatic configuration
			System.setProperty("java.util.logging.SimpleFormatter.format",
					"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n");

			final var consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(Level.FINEST);
			consoleHandler.setFormatter(new SimpleFormatter());

			final var app = Logger.getLogger(LoggerIdentity);
			app.setLevel(Level.FINEST);
			app.addHandler(consoleHandler);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
