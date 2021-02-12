package org.bv.com.datastructures;
///////////////////////////////////////////////////////////////////////////////
/**
 * Defines an element in a queue that points to the queued data type.
 *
 * A part of the queue implementation that is a container for a data type
 * placed in the queue. QElement<T> can be supplied or the queue can allocate
 * one. The queue will attempt to cast a data data type into a QElement and
 * use it if unused.
 *
 * @author jackd
 *
 * @param <T> the type of data managed by the element
 */
public interface QElement<T> {
	/**
	 * Is this flag supplied by the dataType?
	 *
	 * @return true if the
	 */
	QELFlag flags();
	T getData();
	QElement<T> next();
	QElement<T> previous();
	void next(QElement<T> nextElement);
	void previous(QElement<T> previousElement);
}
