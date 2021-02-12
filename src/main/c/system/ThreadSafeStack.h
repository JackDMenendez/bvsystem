#pragma once
#include "../pch.h"
#include <atomic>
#include "Element.h"
#include "List.h"
#include "ElementContainer.h"
#include "Element.h"
namespace bv {
	/**
	* A threadsafe stack that can be used for FIFO communications 
	* between threads.
	*/
	template<class T> class ThreadSafeStack
	{
	private:
		std::atomic<Element<T>*> first;
	public:
		Element<T>* peek() {
			return first.load(std::memory_order_relaxed);
		}
		/**
		* push an element onto the stack.
		*/
		void push(ElementContainer<T>* object) {

			Element<T> element = object->getElement();
			
			bool success = false;

			while (!success) {

				element->next = first;

				success = first.compare_exchange_weak(
					element->next,
					element,
					std::memory_order_release,
					std::memory_order_relaxed
				);
			}
		}
		T* pop() {
			Element<T>* current = peek();
			while ( current != NULL) {
				if (first.compare_exchange_weak(
					current,
					current->next,
					std::memory_order_release,
					std::memory_order_relaxed)) {

					break;
				}
				current = peek();
			}

			if (current != NULL) {
				current->next = NULL;
				return current->parent;
			}
			return NULL;
		}
		/**
		* Remove all elements of the stack and return them as a list
		* 
		* The pointer to the first element is swapped off orphaning the
		* list of elements which return simply by casting the address 
		* of the pointer as a pointer to the list. 
		* 
		*/
		List<T>* popAll() {

			Element<T>* current = peek();

			while ( current != NULL) {

				if (first.compare_exchange_weak(
					current,
					NULL,
					std::memory_order_release,
					std::memory_order_relaxed)) {

					break;
				}
				current = peek();
			}

			if (current == NULL) {
				return NULL;
			}

			return new List<T>(current);
		}
	};
}

