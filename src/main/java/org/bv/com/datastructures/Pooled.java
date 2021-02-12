package org.bv.com.datastructures;

public class Pooled<T> implements PooledElement<T> {
    private Pool<T> pool;
    private T data;

	public Pooled(final Pool<T> pool) {
		this.pool = pool;
	}

	@Override
	public void home(final Pool<T> pool) {
		this.pool = pool;
	}

	@Override
	public Pool<T> home() {
		return this.pool;
	}

	@Override
	public GenStackElement<T> stackElement() {
		return null;
	}

	@Override
	public T data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void data(final T data) {
		// TODO Auto-generated method stub

	}

}
