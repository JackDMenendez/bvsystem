/**
 *
 */
package org.bv.com.system;

/**
 * Implementing this interface allows a class to support asynchronous calling.
 *
 * @author jackd
 *
 */
public interface AsyncStartI extends Runnable {
	void Execute();

	SynchronizerI getSynchronizer();

	void Join();

	void setSynchronizer(SynchronizerI synchronizer);

	void Start();
}
