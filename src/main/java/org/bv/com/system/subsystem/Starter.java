package org.bv.com.system.subsystem;

public interface Starter extends Subsystem {
	int Start(Subsystem subsystem);
	boolean AcceptCommand(String command);
}
