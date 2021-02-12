package org.bv.com.system.subsystem;

import org.bv.com.system.EventArg;

public class SubsystemEventArg extends EventArg {
	private final Subsystem subsystem_;
	public Subsystem getSubsystem() {return subsystem_;}
	public SubsystemEventArg(Subsystem subsystem) {
		subsystem_ = subsystem;
	}
}
