#pragma once

#ifdef BVNATIVE_EXPORTS
#define DLLAPI __declspec(dllexport)
#else
#define DLLAPI __declspec(dllimport)
#endif

namespace BVNative {
	DLLAPI ULONGLONG ticks();
}
