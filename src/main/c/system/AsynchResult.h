#pragma once
#include "../pch.h"
#include "ReturnCode.h"
#include "Semaphore.h"
namespace bv {
	template<typename T> class AsynchResult
	{
	private:
		ReturnCode immediateRC;
		ReturnCode futureRC;
		T* futureValue;
		Semaphore* semaphore;
	public:
		AsynchResult(ReturnCode imRC, Semaphore* semphr) { 
			immediateRC = imRC; 
			semaphore = semphr;
		}
		void setFutureRC(ReturnCode rc) { futureRC = rc; }
		ReturnCode getImmediateRC() { return immediateRC; }
		T* getFutureValue() { return futureValue; }
	};
}

