#pragma once
#include "../pch.h"
#include <chrono>
#include <ratio>
#include <string>
#include <atomic>
#include <ratio>
#include "TimeAccumulator.h"
using bv::TimeAccumulator;
using std::chrono::high_resolution_clock;
using std::string;
namespace bv {
	class StopWatch
	{
	
	private:
		high_resolution_clock::time_point begin;
		TimeAccumulator* accumulator;
		static long long correction;
		static long long calculateCorrection() {
			long long accumulator = 0;
			long long i;
			for (i = 0; i < 1000; i++) {
				StopWatch sw;
				accumulator += sw.raw();
			}
			long long imod = accumulator / 1000;
			return imod;
		}
		long long raw() {
			return (high_resolution_clock::now() - begin).count();
		}
	
	public:
		static long long getCorrection() { return correction; }
		StopWatch() {
			accumulator = NULL;
			begin = high_resolution_clock::now();
		}
		StopWatch(TimeAccumulator *accum) {
			accumulator = accum;
			begin = high_resolution_clock::now();
		}
		long long elapsed() {
			auto ended = high_resolution_clock::now();
			auto e = ended - begin;
			return e.count() - correction; // removes timer overhead.
		}
		~StopWatch() {
			if (accumulator != NULL) {
				accumulator->add(elapsed());
			}
		}
	};
	long long StopWatch::correction = StopWatch::calculateCorrection();
}

