package org.bv.com.system;

import java.io.Console;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.LogRecord;

import org.bv.com.system.diagnostics.Env;

public abstract class ConsoleWriter extends BVServiceRequest implements CWriter {
	protected static Object localLock = new Object();

	public static String STOP_WRITER = "***STOP!***";

	protected static boolean notStopping = true;

	public static boolean stopping() {
		return notStopping == false;
	}

	public static boolean running() {
		return notStopping == true;
	}

	public static void stop() {
		singleton.print(STOP_WRITER);
	}

	private static ConsoleWriter singleton = null;

	public static CWriter getConsoleWriter() {
		return singleton;
	}

	public static CWriter getConsoleWriter(Console console, OutputStream out) {
		if (singleton == null)
			singleton = new JConsoleWriter(console, out);
		return singleton;
	}

	public static CWriter getConsoleWriter(OutputStream out) {
		if (singleton == null)
			singleton = new SConsoleWriter(out);
		return singleton;
	}

	protected abstract void write(String msg);

	protected ConsoleWriter() {
		// Work done on another thread
		setWorkHandler(() -> {
			String msg = null;
			do {
				synchronized (messages) {
					this.messages.wait(1000);
				}
				do {
					msg = messages.poll();
					if (msg != null) {
						if (0 == msg.compareTo(STOP_WRITER)) {
							notStopping = false;
						}
						write(msg);
					}
				} while (msg != null);
			} while (running());
			return rcOK;
		});
	}

	protected ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();

	protected int width = 132;
	protected int height = 50;

	@Override
	public int height() {
		return this.height;
	}

	@Override
	public int width() {
		return this.width;
	}

	@Override
	public void print(String s) {
		messages.add(s);
		synchronized (messages) {
			messages.notify();
		}
	}

	protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	protected String parseLocalTime(Instant instant) {
		LocalTime lt = LocalTime.ofInstant(instant, Env.LocalTimeZone);
		String text = lt.format(dateTimeFormatter);
		return text;
	}

	@Override
	public void Clear() {
	}

	/**
	 * Special formatting for the console
	 */
	@Override
	public void print(LogRecord record) {
		StringBuilder msgSB = new StringBuilder();
		msgSB.append(BVLog.LevelAbreviate(record.getLevel())).append(" ");
		msgSB.append(parseLocalTime(record.getInstant())).append(" ");
		msgSB.append(String.format(record.getMessage(), record.getParameters()));
		msgSB.append(Env.LineSeparator); // Temporary
		print(msgSB.toString());
	}
}
