package org.bv.com.system.subsystem;

import org.bv.com.system.BVLog;
import org.bv.com.system.Event;

public abstract class SubsystemBase implements Subsystem {

	public static final String START_COMMAND = "start";
	public static final String SHUTDOWN_COMMAND = "shutdown";

	private static Starter mainSubsystem_ = null;

	public static Starter getStarter() {
		return mainSubsystem_;
	}

	protected static void setStarter(Starter mainSubsystem) {
		mainSubsystem_ = mainSubsystem;
	}

	public static Event<SubsystemEventArg> SubsystemStateChange = new Event<>();

	/**
	 * Mostly for testing state machines
	 * 
	 * @param subsystem The Subsystem changing state
	 */
	protected void onSubsystemStateChange(Subsystem subsystem) {
		SubsystemStateChange.invoke(this, new SubsystemEventArg(subsystem));
	}

	private int sid_ = -1;

	@Override
	public int getSID() {
		return sid_;
	}

	@Override
	public void setSID(int sid) {
		sid_ = sid;
	}

	@Override
	public abstract void start();

	@Override
	public abstract void shutdown();

	@Override
	public abstract String Name();

	@Override
	public abstract String Alias();

	@Override
	public abstract boolean AcceptCommand(String[] args);

	@Override
	public boolean SingletonOnly() {
		return true;
	}

	@Override
	public Subsystem.State getState() {
		return state_;
	}

	Subsystem.State state_ = Subsystem.State.stopped;

	public static String getDisplayState(Subsystem.State state) {
		String currentState = "unknown";
		switch (state) {
		case stopped:
			currentState = "stopped";
			break;
		case starting:
			currentState = "starting";
			break;
		case running:
			currentState = "running";
			break;
		case inshutdown:
			currentState = "in shutdown";
		default:
			assert (false);
		}
		return currentState;
	}

	@Override
	public void setState(Subsystem.State newState) {
		Subsystem.State oldState = getState();
		state_ = newState;
		// The log might not be initialized at first
		BVLog.info("Subsystem " + Name() + " change " + getDisplayState(oldState) + " ==> " + getDisplayState(state_));
		// Tell anyone who wants to know
		onSubsystemStateChange(this);
	}
}
