#pragma once
#include "../pch.h"
#include "Element.h"
namespace bv {
	template<class T> class ElementContainer
	{
	public:
		virtual Element<T>* getElement() = 0;
		virtual ~ElementContainer() = 0;
	};
	template<class T> ElementContainer<T>::~ElementContainer() {}
}

