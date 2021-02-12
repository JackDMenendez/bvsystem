package org.bv.com.system;

public class Lock implements AutoCloseable {

	private final LockProvider lockProvider;

	public Lock(final LockProvider provider) {
		lockProvider = provider;
		provider.get();
	}
	@Override
	public void close() throws Exception {
		lockProvider.release();
	}

}
