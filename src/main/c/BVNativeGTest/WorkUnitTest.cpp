#include "pch.h"
#include <WorkUnit.h>
#include <StopWatch.h>
using bv::WorkUnit;
using bv::StopWatch;
namespace  systest {

	TEST(WorkUnitCTOR, Factory) {
		StopWatch stopWatch;
		WorkUnit* article = WorkUnit::get();
		auto elapsed = stopWatch.elapsed();
		EXPECT_NE(article, (WorkUnit*)NULL);
		EXPECT_GT(elapsed.count(), 0);
	}
}

