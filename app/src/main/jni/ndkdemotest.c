//
// Created by scy on 2019/7/23.
//
#include "com_scy_android_ndkdemo_NDKTools.h"

JNIEXPORT jstring JNICALL Java_com_scy_android_ndkdemo_NDKTools_getStringFromNDK
        (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "这是沈程阳的第一行NDK 代码");
}
