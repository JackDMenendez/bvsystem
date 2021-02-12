package org.bv.com.system;

import java.util.UUID;

public class Delegate<T> implements DelegateDataI{
    private final UUID id_;
    private final DelegateI<T> delegate_;
    @Override
	public UUID getID() {return id_; }
    public Delegate(DelegateI<T> handler) {
    	delegate_ = handler;
    	id_ = UUID.randomUUID();
    }
	public void handler(Object sender, T eventArg) {
		delegate_.handler(sender, eventArg);
	}

}
