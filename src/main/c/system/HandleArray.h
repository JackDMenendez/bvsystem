#pragma once
#include "../pch.h"
#include <exception>
#include "HandleException.h"
#include "Lock.h"
#include "LockObject.h"
#include "SecurityException.h"
#include <assert.h>
// TODO security qualifyer

namespace bv {
	using Handle = int;
	const Handle INVALIDHANDLE = -1;
	using SecurityDescriptor = long;
	template<class T> class HandleArray
	{
	private:
		LockObject local;
		Handle* handles;
		T** objects;
		size_t count;
		Handle firstAvailable;
		SecurityDescriptor* descriptors;
		int inUse = 0;
	protected:
		void initializeArrays() {
			handles = new Handle[count];
			objects = new T * [count];
			descriptors = new SecurityDescriptor[count];
			firstAvailable = 0;
			for (int i = 0; i < count; i++) {
				if (i < count - 1) handles[i] = i + 1;
				else handles[i] = -1;
				objects[i] = NULL;
				descriptors[i] = 0;
			}
		}
	public:
		T get(Handle handle, SecurityDescriptor secd) {
			T object = *(objects + handle);
			if (object == NULL) {
				throw HandleException("Invalid handle lookup");
			}
			if (*(descriptors + handle) != secd) {
				throw SecurityException("Handle Security Violation");
			}
			return object;
		}


		virtual auto open(T* object, SecurityDescriptor secd) noexcept(false) -> Handle {
			int returnedHandle = INVALIDHANDLE;
			{
				Lock varname(local); {

					if (firstAvailable == INVALIDHANDLE) {
						throw new HandleException("Out of Handles during open");
					}
						
					int returnedHandle = firstAvailable;
					firstAvailable = *(handles + firstAvailable);
					*(objects + returnedHandle) = object;
					inUse++;
				}
				assert(returnedHandle != INVALIDHANDLE);
				*(descriptors + returnedHandle) = secd;
			}
			
			return returnedHandle;
		}
		virtual void close(Handle handle, SecurityDescriptor desc) {
			if (*(descriptors + handle) != desc) {
				throw new SecurityException("Invalid Descriptor");
			}
			*(objects + handle) = nullptr; 
			*(descriptors + handle) = 0;
			{
				Lock varname(local); {
					*(handles + handle) = firstAvailable;
					firstAvailable = handle;
					inUse--;
				}
			}
		}
		HandleArray(size_t numberOfHandles) {
			count = numberOfHandles;
			initializeArrays();
		}
		virtual ~HandleArray();
	};
}
