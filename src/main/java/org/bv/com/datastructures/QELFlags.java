package org.bv.com.datastructures;


public class QELFlags extends FlagInt implements QELFlag {
	@Override
	public boolean isInUse() { return isOn(IN_USE); }
	@Override
	public boolean isPrivate() { return isOn(PRIVATE); }
	@Override
	public boolean isPooled() { return isOn(POOLED); }
	@Override
	public void setInUse() { setOn(IN_USE); }
	@Override
	public void setNotInUse() { setOff(IN_USE); }
	@Override
	public void setPrivate() { setOn(PRIVATE); }
	@Override
	public void setPooled() { setOn(POOLED); }
	public QELFlags() {}
	public QELFlags(final int initialValue) { setOn(initialValue); }
}
