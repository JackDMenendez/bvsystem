package org.bv.com.backend;

import java.util.concurrent.atomic.AtomicInteger;

///////////////////////////////////////////////////////////////////////////////
/**
 * Controls transactions state for all existing transactions
 *
 * Transactions are normally but not necessarily used with a
 * database connection.  Obtain the database connection from
 * the transaction.
 *
 * @author jackd
 * @see Transaction
 *
 */
public class TransactionManager {

	/**
	 * Generates next unique transaction id
	 */
	private static AtomicInteger transactionReferenceGenerator =
			new AtomicInteger(0);
	/**
	 * Transaction factory
	 * @return
	 */
	public static TransactionReference getTransaction() {

		return new TransactionReferenceController();
	}

	public TransactionManager() {
	}
}
