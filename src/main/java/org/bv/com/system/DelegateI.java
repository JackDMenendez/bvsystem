/**
 * 
 */
package org.bv.com.system;


/**
 * @author jackd
 *
 */
public interface DelegateI<T> {
	void handler(Object sender, T eventArg);
}
