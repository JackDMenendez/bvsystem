/**
 *
 */
package org.bv.com.system;

/**
 * @author jackd
 *
 */
public class Method {
	public static String MethodInfo() {
		final var stackElement = new Throwable().getStackTrace()[1];
		final var nameofCurrMethod = stackElement.getMethodName();
		final var lineNumber = stackElement.getLineNumber();
		final var fileName = stackElement.getFileName();
		final var className = stackElement.getClassName();
		final var moduleName = stackElement.getModuleName();
		final var moduleVersion = stackElement.getModuleVersion();
		return className + "." + nameofCurrMethod + " " + moduleName + " " + moduleVersion + "\n" + fileName + "("
				+ lineNumber + ")";
	}
}
