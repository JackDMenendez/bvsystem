// SystemTestCL.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <StopWatch.h>
#include <TimeAccumulator.h>
#include <filesystem>
#include <string>

using bv::StopWatch;
using bv::TimeAccumulator;
using std::string;
namespace fs = std::filesystem;
string an = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const int lc = 1;  // Name complexit 1-8 larger size increases directories exponentially

int main()
{
	std::cout << "Current path is " << fs::current_path() << std::endl;
	std::cout << "Removing old directories" << std::endl;
	fs::remove_all("A");
	std::cout << "Time Correnction: " << StopWatch::getCorrection() << " nanoseconds" << std::endl;
	TimeAccumulator accumulator("Create Clusters");
	TimeAccumulator accumulateCreateCommand("Create Single Cluster", "create.csv", 100);
	{
		string N1;
		string N2;
		string N3;
		string N4;
		N1.reserve(9);
		N2.reserve(9);
		N3.reserve(9);
		N4.reserve(9);
		string lastGen;
		{
			StopWatch stopwatch(&accumulator);
			for (int N1L1 = 0; N1L1 < an.size(); N1L1++)
				for (int N1L2 = 0; N1L2 < an.size(); N1L2++)
					for (int N1L3 = 0; N1L3 < an.size(); N1L3++)
						for (int N1L4 = 0; N1L4 < an.size(); N1L4++)
							for (int N1L5 = 0; N1L5 < an.size(); N1L5++)
								for (int N1L6 = 0; N1L6 < an.size(); N1L6++)
									for (int N1L7 = 0; N1L7 < an.size(); N1L7++)
										for (int N1L8 = 0; N1L8 < an.size(); N1L8++) {
											N1.clear();
											N1 += an[N1L1];
											if (lc > 1)
												N1 += an[N1L2];
											if (lc > 2)
												N1 += an[N1L3];
											if (lc > 3)
												N1 += an[N1L4];
											if (lc > 4)
												N1 += an[N1L5];
											if (lc > 5)
												N1 += an[N1L6];
											if (lc > 6)
												N1 += an[N1L7];
											if (lc > 7)
												N1 += an[N1L8];
											for (int N2L1 = 0; N2L1 < an.size(); N2L1++)
												for (int N2L2 = 0; N2L2 < an.size(); N2L2++)
													for (int N2L3 = 0; N2L3 < an.size(); N2L3++)
														for (int N2L4 = 0; N2L4 < an.size(); N2L4++)
															for (int N2L5 = 0; N2L5 < an.size(); N2L5++)
																for (int N2L6 = 0; N2L6 < an.size(); N2L6++)
																	for (int N2L7 = 0; N2L7 < an.size(); N2L7++)
																		for (int N2L8 = 0; N2L8 < an.size(); N2L8++) {
																			N2.clear();
																			N2 += an[N2L1];
																			if (lc > 1)
																				N2 += an[N2L2];
																			if (lc > 2)
																				N2 += an[N2L3];
																			if (lc > 3)
																				N2 += an[N2L4];
																			if (lc > 4)
																				N2 += an[N2L5];
																			if (lc > 5)
																				N2 += an[N2L6];
																			if (lc > 6)
																				N2 += an[N2L7];
																			if (lc > 7)
																				N2 += an[N2L8];
																			for (int N3L1 = 0; N3L1 < an.size(); N3L1++)
																				for (int N3L2 = 0; N3L2 < an.size(); N3L2++)
																					for (int N3L3 = 0; N3L3 < an.size(); N3L3++)
																						for (int N3L4 = 0; N3L4 < an.size(); N3L4++)
																							for (int N3L5 = 0; N3L5 < an.size(); N3L5++)
																								for (int N3L6 = 0; N3L6 < an.size(); N3L6++)
																									for (int N3L7 = 0; N3L7 < an.size(); N3L7++)
																										for (int N3L8 = 0; N3L8 < an.size(); N3L8++) {
																											N3.clear();
																											N3 += an[N3L1];
																											if (lc > 1)
																												N3 += an[N3L2];
																											if (lc > 2)
																												N3 += an[N3L3];
																											if (lc > 3)
																												N3 += an[N3L4];
																											if (lc > 4)
																												N3 += an[N3L5];
																											if (lc > 5)
																												N3 += an[N3L6];
																											if (lc > 6)
																												N3 += an[N3L7];
																											if (lc > 7)
																												N3 += an[N3L8];
																											for (int N4L1 = 0; N4L1 < an.size(); N4L1++) {
																												for (int N4L2 = 0; N4L2 < an.size(); N4L2++) {
																													for (int N4L3 = 0; N4L3 < an.size(); N4L3++) {
																														for (int N4L4 = 0; N4L4 < an.size(); N4L4++) {
																															for (int N4L5 = 0; N4L5 < an.size(); N4L5++) {
																																for (int N4L6 = 0; N4L6 < an.size(); N4L6++) {
																																	for (int N4L7 = 0; N4L7 < an.size(); N4L7++) {
																																		for (int N4L8 = 0; N4L8 < an.size(); N4L8++) {
																																			if (N4L8 > lc) break;
																																			N4.clear();
																																			N4 += an[N4L1];
																																			if (lc > 1)
																																				N4 += an[N4L2];
																																			if (lc > 2)
																																				N4 += an[N4L3];
																																			if (lc > 3)
																																				N4 += an[N4L4];
																																			if (lc > 4)
																																				N4 += an[N4L5];
																																			if (lc > 5)
																																				N4 += an[N4L6];
																																			if (lc > 6)
																																				N4 += an[N4L7];
																																			if (lc > 7)
																																				N4 += an[N4L8];
																																			string dirNames = "A/";
																																			dirNames += N1;
																																			dirNames += "/";
																																			dirNames += N2;
																																			dirNames += "/";
																																			dirNames += N3;
																																			dirNames += "/";
																																			dirNames += N4;
																																			if (lastGen != dirNames) {
																																				{
																																					StopWatch createDirs(&accumulateCreateCommand);
																																					fs::create_directories(dirNames);
																																				}
																																				std::cout << "Create Directory: \"" << dirNames << std::endl;
																																				lastGen = dirNames;
																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																												}
																											}
																		}

										}
		}
		std::cout << accumulator << std::endl;
		std::system("tree A");
		std::cout << "Removing Directories" << std::endl;
		fs::remove_all("A");
	}
}
