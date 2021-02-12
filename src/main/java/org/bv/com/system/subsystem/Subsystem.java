package org.bv.com.system.subsystem;

public interface Subsystem {
	enum State { stopped, starting, running, inshutdown }
	int getSID();
	void setSID(int sid);
	void start();
	void shutdown();
	String Name();
	String Alias();
	boolean AcceptCommand(String[] command);
	boolean SingletonOnly();
	void setState(State state);
	State getState();

}
