#pragma once
#include "../pch.h"
#include "ThreadSafeStack.h"
namespace bv {
	template<class T> class Pool
	{
	private:
		ThreadSafeStack<T> pooledElements;
	public:
		T* get() {
			T* item = pooledElements.pop();
			if (item == NULL)
				item = new T();
			return item;
		}
		Pool() {}
		~Pool() {}
	};
}

