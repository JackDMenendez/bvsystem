#include "pch.h"
#include <iostream>
#include "CppUnitTest.h"
#include <chrono>
#include <time.h>
#include "../c/system/WorkUnit.h"
#include "../c/system/StopWatch.h"
using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace SystemTest
{
	TEST_CLASS(SystemTest)
	{
	public:
		TEST_METHOD(TestWorkUnitFactory)
		{	bv::StopWatch stopwatch;
	bv::WorkUnit* workUnit = bv::WorkUnit::get();
	auto elapsed = stopwatch.elapsed();
	std::cout << "Elapsed Ticks: " << elapsed.count() << std::endl;

		}
	};
}
