#pragma once
#include "../pch.h"
#include <exception>

namespace bv {
	/**
	* A method that can be scheduled for asynchronous completion.
	*/
	typedef int (*method)(void* context) throw (std::exception);
}
