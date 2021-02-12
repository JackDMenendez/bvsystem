package org.bv.com.datastructures;
import java.util.concurrent.atomic.AtomicReference;

import org.bv.com.system.TimeStatistics;
/**
 * A thread safe stack.
 *
 * This stack implementation assumes that the data
 * has an element in it that points to the next
 * older stack member. This means we can serve the
 * stack with a single atomic compare and swap
 * operation.
 *
 * This is not a collection and cannot be iterated.
 *
 * @author jackd
 *
 * @param <T> The data type.
 */
public class GenStack<T extends Stackable<T>> implements GStack<T> {
    private final String name;
    public String name() { return name; }
    private final TimeStatistics timeStatistics;
	private final AtomicReference<GenStackElement<T>> first =
			new AtomicReference<>(null);

	public GenStack(final String name) {
		this.name = name;
		timeStatistics = new TimeStatistics(name);
	}

	protected void add(final GenStackElement<T> element) {
		assert element != null;
		element.stopWatch(timeStatistics).Start();
		GenStackElement<T> current, found;
		found = first.getPlain(); // OK if null
		current = element;  // so the while check is always true
		while(current != found) {
			current = found;
			element.next(current);
			found = first.compareAndExchange(current, element);
		}
	}

	protected GenStackElement<T> remove() {
		GenStackElement<T> current, found;
		found = first.getPlain(); // OK if null
		current = null;
		while(current!=found) {
			current = found;
			found = first.compareAndExchange(current, current.next());
		}
		found.stopWatch(timeStatistics).Stop();
		return found;
	}

	@Override
	public void push(final T data) {
		assert data != null;
		assert !data.stackElement().inUse();
		this.add(data.stackElement());
		data.stackElement().inUse(true);
	}

	@Override
	public T peek() {
		if (first.getPlain() == null) {
			return null;
		}
		return first.getPlain().data();
	}

	@Override
	public T pop() {
		final var element = remove();
		element.inUse(false);
		return element.data();
	}

	@Override
	public String toString() { return timeStatistics.toString(); }
}
