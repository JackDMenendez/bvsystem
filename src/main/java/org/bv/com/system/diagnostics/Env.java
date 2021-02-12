package org.bv.com.system.diagnostics;

import java.time.ZoneId;

public class Env {
	private static String keyLineSeparator = "line.separator";
	private static String keyFileSeparator = "file.separator";
	public static final String LineSeparator = System.getProperty(keyLineSeparator);
	public static final String FileSeparator = System.getProperty(keyFileSeparator);
	public static final ZoneId LocalTimeZone = ZoneId.systemDefault();

	public static String DumpOSEnvironment() {
		var sb = new StringBuilder("----- OS Environment -----" + LineSeparator);
		var env = System.getenv();
		for (var key : env.keySet()) {
			sb.append(key + " -> " + env.get(key) + LineSeparator);
		}
		return sb.toString();
	}

	public static String DumpJavaEnvironment() {
		var sb = new StringBuilder("----- Java Environment -----" + LineSeparator);
		var env = System.getProperties();
		for (var key : env.keySet()) {
			sb.append(key + " -> " + env.get(key) + LineSeparator);
		}
		return sb.toString();
	}

}
