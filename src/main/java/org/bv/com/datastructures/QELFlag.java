package org.bv.com.datastructures;

public interface QELFlag {

	int IN_USE = FlagInt.f01;
	int PRIVATE = FlagInt.f02;
	int POOLED = FlagInt.f04;

	boolean isInUse();

	boolean isPrivate();

	boolean isPooled();

	void setInUse();

	void setNotInUse();

	void setPrivate();

	void setPooled();

}