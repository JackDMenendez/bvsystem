/**
 * 
 */
package org.bv.com.system;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author jackd
 *
 */
public class BVConsoleLogger extends Handler {

	protected Level level = Level.FINEST;
	protected CWriter consoleWriter = null;

	@Override
	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public Level getLevel() {
		return level;
	}

	public BVConsoleLogger() {
		consoleWriter = ConsoleWriter.getConsoleWriter();
	}

	@Override
	public void publish(LogRecord record) {
		if (consoleWriter == null)
			return;

		if (record.getLevel().intValue() >= getLevel().intValue()) {
			consoleWriter.print(record);
		}
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws SecurityException {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
