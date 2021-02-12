package org.bv.com.datastructures;

public interface Poolable<T> {
	PooledElement<T> poolELement();
	void reset();
}
