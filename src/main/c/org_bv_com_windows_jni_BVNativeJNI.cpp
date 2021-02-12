#include "Pch.h"
#include "org_bv_com_windows_jni_BVNativeJNI.h"

#include "BVNative.h"
union tick_data {
	jlong fill[2];
	ULONGLONG dataFromTicks;
};
extern "C" {
	JNIEXPORT jlongArray JNICALL Java_org_bv_com_windows_jni_BVNativeJNI_ticks
	(JNIEnv* env, jobject that) {
		jlongArray result;
		result = env->NewLongArray(2);
		if (result == NULL)
			return NULL; /* out of memory error thrown */
		tick_data data;
		data.dataFromTicks = GetTickCount64();
		env->SetLongArrayRegion(result, 0, 2, data.fill);
		return result;
	}
}


