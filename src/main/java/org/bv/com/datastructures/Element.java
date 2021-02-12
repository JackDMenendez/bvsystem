package org.bv.com.datastructures;

import org.bv.com.system.StopWatch;
import org.bv.com.system.TimeStatistics;

public class Element<T> implements GenStackElement<T> {

    private boolean inUseFlag = false;
    private T data;

    private GenStackElement<T> next;
    private StopWatch stopWatch = null;
    public Element(final T data) {
    	this();
    	this.data = data;
    }
	public Element() {
		next = null;
	}
	@Override
	public StopWatch stopWatch(final TimeStatistics accumulator) {
		if (this.stopWatch == null) {
			this.stopWatch = new StopWatch(accumulator);
		} else {
			this.stopWatch.Accumulator(accumulator);
		}
		return this.stopWatch;
	}
	@Override
	public GenStackElement<T> next() {return this.next; }
	@Override
	public void next(final GenStackElement<T> element) { this.next = element;}
	@Override
	public T data() { return this.data;}
	@Override
	public void data(final T data) { this.data = data;}
	@Override
	public boolean inUse() {
		return inUseFlag;
	}
	@Override
	public void inUse(final boolean inUseFlag) {
		this.inUseFlag=inUseFlag;
	}
}
