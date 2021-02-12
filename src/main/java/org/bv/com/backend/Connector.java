package org.bv.com.backend;

public interface Connector {
	enum Protection {
		allowUpdateCollisions,
		noCollisions
	}
    Protection protection();

	String close();
	DB dB();
	Transaction transaction();
}
