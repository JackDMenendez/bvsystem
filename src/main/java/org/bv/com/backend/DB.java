package org.bv.com.backend;

import java.util.concurrent.Callable;

public interface DB {
	String read(String queryData, Callable<QueryArgs> asyncCompletionHander);
	String insert(String queryData, Callable<QueryArgs> asyncCompletionHander);
	String update(String queryData, Callable<QueryArgs> asyncCompletionHander);
	String delete(String queryData, Callable<QueryArgs> asyncCompletionHander);
}
