package org.bv.com.datastructures;

import org.bv.com.system.StopWatch;
import org.bv.com.system.TimeStatistics;

public interface GenStackElement<T> {

    StopWatch stopWatch(TimeStatistics accumulator);

	GenStackElement<T> next();

	void next(GenStackElement<T> element);

	boolean inUse();

	void inUse(boolean inUseFlag);

	T data();

	void data(T data);

}