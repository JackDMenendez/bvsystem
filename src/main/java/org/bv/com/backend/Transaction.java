package org.bv.com.backend;

import org.bv.com.system.EventI;

/**
 * An light transaction layer
 *
 * @author jackd
 *
 */
public interface Transaction {

    int id();
    String ready();
    EventI<TransactionArgs> subscribeRollBack();
    EventI<TransactionArgs> subscribeCommit();
    EventI<SystemArgs> subscribeComplete();
	Connector open(Connector.Protection protection);
}
