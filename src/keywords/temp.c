#include <jni,h>
#include "MyDemo.h"

JNIEXPORT jint JNICALL Java_NativeDemo_add
    (JNIEnv *env, jobject obj, jint a, jint b) {
    return a + b
}