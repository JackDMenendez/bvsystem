/**
 * 
 */
package org.bv.com.system;


public interface EventI<T> {
	void invoke(Object sender, T eventArg); 
	void Remove(long[] id);
	long[] Add(DelegateI<T> handler);
	long[] Add(Delegate<T> handler);
	boolean Empty();
}
