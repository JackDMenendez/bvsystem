package org.bv.com.system.GUID;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bv.com.system.BVServiceRequest;
import org.bv.com.system.Barrier;
import org.bv.com.system.Delegate;
import org.bv.com.system.Event;
import org.bv.com.system.EventI;
import org.bv.com.system.LockProvider;

/**
 * A reservoir of unused GUIDs for whatever purpose you desire.
 *
 * @author jackd
 * @TODO auto tuning
 */
public class Resevoir {

	public static UUID random() {
		final var ruuid = UUID.randomUUID();
		return ruuid;
	}

	public static UUID get() {
		UUID uuid = null;
		try {
			synchronized(localLock) {
				uuid = resevoir.pop();
			}
			if (uuid == null) {
				return random();
			}
			return uuid;
		} finally {
			if (resevoir.size() <= refillLevel) {
				onRefillNeeded();
			}
		}
	}

	protected static final int refillLevel = 90;

	protected static final int preferredSize = 100;

	protected static final int minimumRefill = 10;

	protected static final AtomicBoolean refillFlag = new AtomicBoolean(false);

	protected static final LockProvider localLock = new Barrier();

	protected static final void onRefillNeeded() {
		refillNeeded.invoke(refillNeeded, resevoir);
	}

	protected static final Stack<UUID> resevoir = new Stack<>();

	protected static final EventI<Stack<UUID>> refillNeeded = new Event<>();

	static {
		refillNeeded.Add(new Delegate<Stack<UUID>>((s,e)->{
			if (refillFlag.get() || resevoir.size() >= refillLevel) {
				return;
			}
			refillFlag.set(true);
			final var sr = new BVServiceRequest(()->{
				while(resevoir.size() < preferredSize) {
					synchronized(localLock) {
						for(var refillIdx = 0; refillIdx<minimumRefill; refillIdx++) {
							resevoir.push(random());
						}
					}
				}
				refillFlag.set(false);
				return 0;
			});
			sr.Execute();
		}));
		onRefillNeeded();
	}

	public static void main(final String[] args) {
		System.out.println(get().toString());

	}
}
