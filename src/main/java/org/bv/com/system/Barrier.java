package org.bv.com.system;

public class Barrier implements LockProvider {

	private Thread owner = null;
	//private GenQueue<Thread> waiting = new GenQueue<Thread>();
    public Thread getOwner() {
		return owner;
	}

	/**
	 * For asserting ownership on entry
	 */
	public boolean isOwnedByMe() {
		return owner != null &&
			owner.getId() == Thread.currentThread().getId();
	}
	public Barrier() {
	}

	@Override
	public void get() {
		synchronized (this) {
			if (owner == null) {
				owner = Thread.currentThread();
				return;
			} else if (owner.getId() == Thread.currentThread().getId()) {
				return;
			} else {
				//try {
					//waiting.put(Thread.currentThread());
				//} catch (InterruptedException e) {
				//	e.printStackTrace();
				//}
		    }
		}
		try {
			Thread.currentThread().wait(10000);
			synchronized(this) {
				//waiting.remove(Thread.currentThread());
				if (owner.getId() == Thread.currentThread().getId()) {
					return;
				}
			}
		} catch (final InterruptedException e) {
		    e.printStackTrace();
		}
		get();
	}

	@Override
	public void release() {
		assert owner != null &&
				 owner.getId() == Thread.currentThread().getId();
		synchronized(this) {
			//if (waiting.size() > 0) {
			//	waiting.peek().notify();
			//}
			owner = null;
		}
	}
}
