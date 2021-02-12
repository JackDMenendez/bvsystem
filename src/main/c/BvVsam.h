#pragma once
#include "pch.h"
#ifdef BVVSAM_EXPORTS
#define DLLAPI __declspec(dllexport)
#else
#define DLLAPI __declspec(dllimport)
#endif

