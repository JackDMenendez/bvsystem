/**
 * 
 */
package org.bv.com.system;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bv.com.system.subsystem.Subsystem;
import org.bv.com.system.subsystem.SubsystemBase;

/**
 * @author jackd
 *
 */
public class BVLog extends SubsystemBase implements Subsystem {
	public static String LevelAbreviate(Level level) {
		String abrv;
		if (Level.SEVERE == level)
			return "E";
		if (Level.WARNING == level)
			return "W";
		if (Level.INFO == level)
			return "I";
		if (Level.CONFIG == level)
			return "C";
		if (Level.FINE == level || Level.FINER == level || Level.FINEST == level)
			return "T";
		else
			return " ";

	}

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String byteToHex(byte b) {
		StringBuilder sb = new StringBuilder();
		int v = 0;
		v |= b;
		sb.append(HEX_ARRAY[v >>> 4]);
		sb.append(HEX_ARRAY[v & 0x0F]);
		return sb.toString();
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Subsystem can be addressed by name.
	 */
	public static final String name = "log";
	public static final String alias = "L";
	protected static String DefaultFormatter = "java.util.logging.SimpleFormatter.format";
	protected static String DefaultFileFormat = "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL] %4$-7s [%3$s] (%2$s) %5$s %6$s%n";
	protected static String DefaultConsoleFormat = "[%1$tT.%1$tL] [%4$-7s] %5$s %n";
	protected static String DefaultLoggerDomain = "org.bv.com.system.logging";
	protected static String DefaultFileHandlerPattern = "%h/log/bv-%u-gen-%g.log";
	protected static int MaximumFileSizeBytes = 10000000;
	protected static int MaximumFileGenerations = 10;
	protected static boolean FileAppends = true;
	protected static BVLog logger_ = null;
	protected FileHandler fileHandler_ = null;
	protected static Level DefaultFileLevel = Level.FINEST;
	protected static Level DefaultConsoleLevel = Level.WARNING;
	protected static Logger get;
	protected BVFileFormatter fileFormatter_ = null;
	protected ConsoleHandler consoleHandler_ = null;
	protected BVConsoleFormatter consoleFormatter_ = null;
	protected BVConsoleLogger bVConsoleHandler_ = null;

	private static long traceFlags_ = 0;

	public static void setTraceFlagOn(long flag) {
		traceFlags_ = traceFlags_ | flag;
	}

	public static void setTraceFlagOff(long flag) {
		traceFlags_ = traceFlags_ & ~flag;
	}

	public static void trace(long flags, String msg) {
		if (BVLog.get == null)
			return;
		if (testTraceFlags(flags)) {
			BVLog.get.finest(msg);
		}
	}

	public static boolean testTraceFlags(long flag) {
		return (0 < (traceFlags_ | flag));
	}

	public static void trace(long flags, Supplier<String> msgSupplier) {
		if (BVLog.get == null)
			return;
		if (testTraceFlags(flags)) {
			BVLog.get.finest(msgSupplier);
		}
	}

	public static void info(String msg) {
		if (BVLog.get == null)
			return;
		BVLog.get.info(msg);
	}

	public static void info(Supplier<String> msgSupplier) {
		if (BVLog.get == null)
			return;
		BVLog.get.info(msgSupplier);
	}

	public static void warning(String msg) {
		if (BVLog.get == null)
			return;
		BVLog.get.info(msg);
	}

	public static void warning(Supplier<String> msgSupplier) {
		if (BVLog.get == null)
			return;
		BVLog.get.info(msgSupplier);
	}

	protected BVLog() {
		this.setState(Subsystem.State.starting);
		System.setProperty(BVFileFormatter.DefaultFileFormatter, DefaultFileFormat);
		System.setProperty(BVConsoleFormatter.DefaultConsoleFormatter, DefaultConsoleFormat);

		get = Logger.getLogger(DefaultLoggerDomain);
		get.setLevel(Level.FINEST);

		get.setUseParentHandlers(false);

		try {
			fileHandler_ = new FileHandler(DefaultFileHandlerPattern, MaximumFileSizeBytes, MaximumFileGenerations,
					FileAppends);
			fileFormatter_ = new BVFileFormatter();
			fileHandler_.setFormatter(fileFormatter_);
			fileHandler_.setLevel(DefaultFileLevel);
			get.addHandler(fileHandler_);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		consoleHandler_ = new ConsoleHandler();

		consoleHandler_.setLevel(DefaultConsoleLevel);
		consoleFormatter_ = new BVConsoleFormatter();
		consoleHandler_.setFormatter(consoleFormatter_);

		// get.addHandler(consoleHandler_);
		bVConsoleHandler_ = new BVConsoleLogger();
		bVConsoleHandler_.setLevel(Level.INFO);
		get.addHandler(bVConsoleHandler_);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (!(args[0].equals(BVLog.name) || args[0].equals(BVLog.alias)))
			return;

		if (logger_ == null)
			logger_ = new BVLog();

		if (args.length > 1 && args[1].equals(SubsystemBase.START_COMMAND)) {
			// are we stand alone, maybe for testing?
			if (getStarter() == null) {
				logger_.start();
			} else {
				getStarter().Start(logger_);
			}
		}
	}

	@Override
	public int getSID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSID(int sid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		info("Logging Initialization Complete");
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public String Name() {
		return name;
	}

	@Override
	public String Alias() {
		return alias;
	}

	@Override
	public boolean AcceptCommand(String[] args) {
		main(args);
		return true;
	}

}
