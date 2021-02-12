#pragma once
#include "../pch.h"
namespace bv {

		template<class T> class Element {
		public: 
			T* parent;
			Element<T>* next;
			long timeStamp;
		};
}
