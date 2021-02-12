package org.bv.com.backend;

import java.util.concurrent.atomic.AtomicInteger;

import org.bv.com.backend.Connector.Protection;
import org.bv.com.system.Event;
import org.bv.com.system.EventI;

public class TransactionController implements Transaction {

	private final AtomicInteger referenceCount = new AtomicInteger(0);

	protected EventI<TransactionController> referencesClear =
			new Event<>();

	public void addReference() {
		referenceCount.incrementAndGet();
	}

	public int removeReference() {

		final var references = referenceCount.decrementAndGet();

		if (references == 0) {
			onReferenceCountIsZero();
		}

		return references;
	}

	protected void onReferenceCountIsZero() {
		referencesClear.invoke(this, this);
	}

	public TransactionController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int id() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String ready() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventI<TransactionArgs> subscribeRollBack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventI<TransactionArgs> subscribeCommit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventI<SystemArgs> subscribeComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connector open(final Protection protection) {
		// TODO Auto-generated method stub
		return null;
	}
}
