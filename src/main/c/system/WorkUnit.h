#include "../pch.h"
#include "Element.h"
#include "ElementContainer.h"
#include "HandleArray.h"
#include "Semaphore.h"
#include "Method.h"
#include "ReturnCode.h"
#include "AsynchResult.h"
#include "Pooled.h"
#include <exception>
#include "ThreadSafeStack.h"
#include "Pool.h"

namespace bv {
	/**
	* An asynchronous unit of work.
	* 
	* Characteristics
	* ---------------
	* Pooled
	* ElementContainer
	*/
	class WorkUnit : ElementContainer<WorkUnit>, Pooled<WorkUnit> {
	private:
		static Pool<WorkUnit> pool;
		Semaphore semaphore;
		Element<WorkUnit> queueElement;
		Handle threadHandle = bv::INVALIDHANDLE;
		method work;
		void setThreadAffinity(Handle thrdH) {
			threadHandle = thrdH;
		}
	protected:
	public:
		WorkUnit() {
			work = NULL;
			queueElement.parent = this;
			queueElement.next = NULL;
		}
		static WorkUnit* get() {
			return pool.get();
		}
		static WorkUnit* get(method someWork) {
		}
	
		template<typename T> AsynchResult<T> getAsynchResult(ReturnCode immediateRC) {

		}
		static WorkUnit* schedule(method someWork, Handle thrdH) {
		}
		virtual ReturnCode execute() throw (std::exception) {
			return bv::OK;
		}
		bool hasThreadAffinity() { return threadHandle != bv::INVALIDHANDLE; }
		virtual Element<WorkUnit>* getElement() { return &queueElement; }
		virtual Element<WorkUnit>* getPooledElement() { return getElement(); }
		virtual void reset() {}
		virtual ~WorkUnit();
	};	
	WorkUnit::~WorkUnit() {}
	Pool<WorkUnit> WorkUnit::pool;
}