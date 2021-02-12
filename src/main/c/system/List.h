#pragma once
#include "../pch.h"
#include "Element.h"

namespace bv {
	template<class T> class List {
	private:
		Element<T>* first;
	public:
		Element<T>* remove() {
			Element<T>* current = first;
			if (current != NULL)
				first = current->next;
			return current;
		}
		void Add(Element<T>* element) {
			element->next = first;
			first = element;
		}
		List<T>(Element<T>* element) {
			first = element;
		}
	};
}

