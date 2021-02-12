#pragma once
#include "../pch.h"
#include <mutex>
class LockObject {
private:
	std::mutex lockObject;
public:
	void lock() {
		lockObject.lock();
	}
	void unLock() {
		lockObject.unlock();
	}
	~LockObject() = default;

};

