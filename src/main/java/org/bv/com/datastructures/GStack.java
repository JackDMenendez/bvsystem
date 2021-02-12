package org.bv.com.datastructures;

public interface GStack<T extends Stackable<T>> {

	void push(T data);

	T peek();

	T pop();

}