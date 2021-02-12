package org.bv.com.datastructures;

public class QueueElement<T> implements QElement<T> {

	private T data = null;
	private final QELFlags flags;

	private QElement<T> next = null;
	private QElement<T> previous= null;

	public QueueElement() {
		flags = new QELFlags();
	}

	public QueueElement(final int qelFlags) {
		this.flags = new QELFlags(qelFlags);
	}

	public QueueElement(final T data) {
		this();
		this.data = data;
	}

	public QueueElement(final T data, final int qelFlags) {
		this.data = data;
		flags = new QELFlags(qelFlags);
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public QElement<T> next() {
		return this.next;
	}

	@Override
	public QElement<T> previous() {
		return this.previous;
	}

	@Override
	public void next(final QElement<T> nextElement) {
		this.next = nextElement;
	}

	@Override
	public void previous(final QElement<T> previousElement) {
		this.previous = previousElement;
	}

	@Override
	public QELFlag flags() {

		return this.flags;
	}

}
