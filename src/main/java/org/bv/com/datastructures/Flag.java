package org.bv.com.datastructures;

public interface Flag {
	int value();
	boolean isOn(int setting);
	boolean isOff(int setting);
	void setOn(int setting);
	void setOff(int setting);
	void clearAll();
}
