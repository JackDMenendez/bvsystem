package org.bv.com.datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
///////////////////////////////////////////////////////////////////////////////
/**
 * A Queue implementation that behaves differently than
 * specified by the interface documentation. Differences will be noted in this
 * documentation.
 *
 * The Java implementation in java.util is not clean.  The specification of
 * the interface assumes a certain implementation. Specifically, the sun.util
 * implementation is a queue that can run out of space. The purpose of this
 * implementation is to make a queue that never runs out of space, and never
 * throws exception.
 *
 * @author jackd
 *
 * @param <T>
 */
public class GenQueue<T extends Object> implements QAnchor<T>, Queue<T> {


	protected class GenIterator<D extends Object> implements Iterator<D> {

		private QElement<D> current;

		public GenIterator(final QElement<D> element) {
			current = element;
		}

		@Override
		public boolean hasNext() {
			return current.next() != null;
		}

		@Override
		public D next() {
			if (current == null || current.next() == null) {
				return null;
			}
			current = current.next();
			return current.getData();
		}

	}

	private QElement<T> first_ = null;
	private QElement<T> last_ = null;
	private int size = 0;

	public GenQueue() {
	}

	@Override
	public QElement<T> first() {
		return null;
	}

	@Override
	public QElement<T> last() {
		return null;
	}

	@Override
	public int size() {
		return this.size;
	}


	@Override
	public boolean isEmpty() {
		return null == first();
	}

	@Override
	public boolean contains(final Object o) {
		if (o == null) {
			return false;
		}
		var element = first();
		while(element != null) {
			if (o == element) {
				return true;
			}
			element = element.next();
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new GenIterator<>(first());
	}

	@Override
	public Object[] toArray() {
		final var a = new Object[size()];
		var index = 0;
		var element = first();
		while(element != null) {
			a[index++] = element.getData();
			element = element.next();
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(final T[] a) {
		var idx = 0;
		var element = first();
		while(idx < a.length && element != null) {
			a[idx++] = (T) element.getData();
			element = element.next();
		}
		return a;
	}

	@Override
	public boolean remove(final Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {

		return false;
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean add(final T e) {
		final var qel = new QueueElement<>(e);
		if (0 == this.size++) {
			this.first_ = this.last_ = qel;
		} else {
			this.last_.next(qel);
			qel.previous(this.last_);
			this.last_ = qel;
		}

		return true;
	}

	@Override
	public boolean offer(final T e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return null;
	}
}
