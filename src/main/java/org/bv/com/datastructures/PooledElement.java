package org.bv.com.datastructures;

public interface PooledElement<T> {
	void home(Pool<T> pool);
	Pool<T> home();
	GenStackElement<T> stackElement();
	T data();
	void data(T data);
}
