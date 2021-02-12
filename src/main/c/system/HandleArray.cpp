#include "HandleArray.h"
template<class T> bv::HandleArray<T>::~HandleArray() {
	delete handles;
	delete objects;
	delete descriptors;
}