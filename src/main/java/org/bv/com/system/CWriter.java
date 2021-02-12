package org.bv.com.system;

import java.util.logging.LogRecord;

public interface CWriter extends ServiceRequestI {
	void print(String s);

	void print(LogRecord record);

	/**
	 * Clear the console screen
	 */
	void Clear();

	int width();

	int height();

}