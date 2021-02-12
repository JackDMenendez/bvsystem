#include "pch.h"
#include "BvNative.h"

DLLAPI ULONGLONG ticks()
{
    return GetTickCount64();
}
