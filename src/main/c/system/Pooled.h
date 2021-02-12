#pragma once
#include "Element.h"
namespace bv {
	template<class T> class Pooled
	{
	public:
		virtual void reset() = 0;
		virtual Element<T>* getPooledElement() = 0;
		virtual ~Pooled()=0;
	};
	
	template<class T> 
	bv::Pooled<T>::~Pooled()
	{
	}

}

