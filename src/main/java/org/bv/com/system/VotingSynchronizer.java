/**
 *
 */
package org.bv.com.system;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author jackd
 *
 */
public class VotingSynchronizer implements SynchronizerI {
	private final CyclicBarrier _barrier;

	public VotingSynchronizer(int requiredVotes) {
		_barrier = new CyclicBarrier(requiredVotes);
	}

	@Override
	public int Join() {
		return Ready();
	}

	@Override
	public int Ready() {
		try {
			return _barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			return -1;
		}
	}

	public void Start(BVServiceRequest worker) {
		worker.setSynchronizer(this);
		worker.getMeasureDispatch().Start();
		worker.Start();
	}

}
