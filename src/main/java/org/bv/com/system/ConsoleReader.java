package org.bv.com.system;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bv.com.system.vt100.Frmt;

public abstract class ConsoleReader extends BVServiceRequest implements CReader {

	protected static boolean notStopping = true;
	protected static final Object localLock = new Object();

	public static boolean stopping() {
		return notStopping == false;
	}

	public static boolean running() {
		return notStopping == true;
	}

	public static void stop() {
		notStopping = false;
	}

	protected ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();
	protected static ConsoleReader singleton = null;

	public static CReader getConsoleReader() {
		return singleton;
	}

	public static CReader getConsoleReader(final Console console) {
		if (singleton == null) {
			singleton = new JConsoleReader(console);
		}
		return singleton;
	}

	public static CReader getConsoleReader(final InputStream istream) {
		if (singleton == null) {
			singleton = new SConsoleReader(istream);
		}
		return singleton;
	}

	protected ConsoleReader() {
		setWorkHandler(() -> {
			StringBuilder stringBuilder = null;
			ByteArrayOutputStream byteArrayOutputStream = null;
			do {
				var nextChar = readChar(); // \x1b[?1;0c
				System.out.print(BVLog.byteToHex(nextChar));
				System.out.print("\n");
				System.out.flush();
				if (nextChar == Frmt.ESC) {
					byteArrayOutputStream = new ByteArrayOutputStream();
					byteArrayOutputStream.write(nextChar);
					while (ready()) {
						nextChar = readChar();
						System.out.print(BVLog.byteToHex(nextChar));
						System.out.print("\n");
						System.out.flush();
						if (nextChar != Frmt.NUL) {
							byteArrayOutputStream.write(nextChar);
						}
					}
					EscapedInput(byteArrayOutputStream.toByteArray());
					byteArrayOutputStream = null;
				} else {
					if (stringBuilder == null) {
						stringBuilder = new StringBuilder();
					}
					stringBuilder.append(nextChar);
					if (nextChar == ';') {
						final var command = stringBuilder.toString();
						messages.add(command);
						synchronized (messages) {
							messages.notify();
						}
						stringBuilder = null;
						if (0 == command.toLowerCase().compareTo("quit;")) {
							stop();
							break;
						}
					}
				}
			} while (running());
			return rcOK;
		});
	}

	protected abstract boolean ready();

	protected abstract byte readChar();

	/**
	 * Read more than one character at a time
	 *
	 * @param buffer         buffer to receive input
	 * @param offsetInBuffer where to start putting chars
	 * @param max2Read       max to read
	 * @return number of characters read
	 */
	protected abstract int read(char[] buffer, int offsetInBuffer, int len);

	@Override
	public void EscapedInput(final byte[] data) {

	}

	@Override
	public String read() {
		String msg = null;
		do {
			msg = messages.poll();
			if (msg == null) {
				try {
					synchronized (messages) {
						messages.wait(500);
					}
				} catch (final InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (msg == null && running());
		return msg;
	}
}
