#pragma once
#include "LockObject.h"
class Lock
{
private:
	LockObject* lockSource;
public:
	Lock(LockObject& lockObject) {
		lockSource = &lockObject;
		lockObject.lock();
	}
	virtual ~Lock() {
		lockSource->unLock();
	}
};

