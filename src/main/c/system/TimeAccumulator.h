#pragma once
#include "../pch.h"
#include <chrono>
#include <ratio>
#include <string>
#include <atomic>
#include <ratio>
#include <iomanip>
#include <iostream>
#include <cassert>
#include <iostream>
#include <fstream>
using std::ofstream;
using std::ostream;
using std::string;
using std::atomic_llong;
using namespace std;
using std::ios;
namespace bv {
	class TimeAccumulator {
	private:
		string statFile;
		string name;
		atomic_llong accumulate;
		atomic_llong count;
		atomic_long intervalCount;
		int interval;
	protected:
		void writeAndClear() {
			assert(statFile.length() > 0);
			ofstream myfile;
			myfile.open(statFile,ios::out | ios::app);
			myfile << getName() << "," << getCount() << "," << averageMS() << endl;
			cout << getName() << "," << getCount() << "," << averageMS() << endl;
			myfile.close();
			intervalCount.store(0);
		}
	public:
		TimeAccumulator(const char* const title) {
			name = title;
			interval = 0;
		}
		TimeAccumulator(const char* const title,
			const char* const statF,
			int intrvl) : TimeAccumulator(title) {
			interval = intrvl;
			statFile = statF;
			ofstream myfile;
			myfile.open(statFile, ios::out | ios::trunc);
			myfile << "Name,Total,Avg. MS" << endl;
			myfile.close();
		}
		const double NANOS_PER_SECOND = 1000000000.0;
		const double SECONDS_PER_NANO = 1 / NANOS_PER_SECOND;
		void add(long long value) {
			accumulate += value;
			count++;
			if (interval > 0) {
				intervalCount++;
				if (intervalCount >= interval) {
					writeAndClear();
				}
			}
		}
		const char* const getName() const { return name.c_str(); }
		long long getCount() const { return count.load(); }
		double averageMS() const {
			double sum = accumulate.load();
			double cnt = interval > 0 ? interval : count.load();
			return sum / (cnt * NANOS_PER_SECOND/1000.0);
		}
		~TimeAccumulator() {
		}
	};

	ostream& operator<<(ostream& os, const TimeAccumulator& dt)
	{
		os << dt.getName() << " Average: " << std::setprecision(15) << dt.averageMS() << " MS count: " << dt.getCount() << std::endl;
		return os;
	}
}

