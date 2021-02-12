package org.bv.com.datastructures;

public interface QAnchor<T> {
	QElement<T> first();
	QElement<T> last();
	int size();
}
