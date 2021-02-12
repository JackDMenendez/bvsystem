package org.bv.com.system;

import java.util.ArrayList;
import java.util.List;

public class Event<T> implements EventI<T> {
    private List<Delegate<T>> handlers = null;
	@Override
	public void invoke(Object sender, T eventArg) {
		
		if (Empty())
			return;
		
		for(int handlerIdx=0;handlerIdx<handlers.size();handlerIdx++) {
			handlers.get(handlerIdx).handler(sender, eventArg);
		}
	}

	@Override
	public void Remove(long[] id) {
		assert(!Empty());
		
		for(int handlerIdx=0;handlerIdx<handlers.size();handlerIdx++) {
			if (id[0] == ((DelegateDataI)(handlers.get(handlerIdx))).getID().getLeastSignificantBits() &&
				id[1] == ((DelegateDataI)(handlers.get(handlerIdx))).getID().getMostSignificantBits()	) {
				handlers.remove(handlerIdx);
				break;
			}
		}
		if (handlers.size() == 0) {
			handlers = null;
		}
	}

	@Override
	public long[] Add(DelegateI<T> handler) {
		if (Empty())
			handlers = new ArrayList<Delegate<T>>();
		var delegate = new Delegate<T>(handler);
		handlers.add(delegate);
		long[] id = { delegate.getID().getLeastSignificantBits(),
				delegate.getID().getMostSignificantBits()};
		return id;
	}

	@Override
	public long[] Add(Delegate<T> delegate) {
		if (Empty())
			handlers = new ArrayList<Delegate<T>>();
		handlers.add(delegate);
		long[] id = { delegate.getID().getLeastSignificantBits(),
				delegate.getID().getMostSignificantBits()};
		return id;		
	}

	@Override
	public boolean Empty() {
		return handlers == null;
	}
    
}
