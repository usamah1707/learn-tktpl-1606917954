#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jlong JNICALL
Java_id_ac_ui_cs_mobileprogramming_usamahnashirulhaq_helloworld_MainActivity_Increment(
        JNIEnv *env, jobject thiz, jlong x) {
    return ++x;
}

extern "C"
JNIEXPORT jlong  JNICALL
Java_id_ac_ui_cs_mobileprogramming_usamahnashirulhaq_helloworld_MainActivity_Decrement(
        JNIEnv *env, jobject thiz, jlong x) {
    return --x;
}

extern "C"
JNIEXPORT jlong  JNICALL
Java_id_ac_ui_cs_mobileprogramming_usamahnashirulhaq_helloworld_MainActivity_MultiplyBy2(
        JNIEnv *env, jobject thiz, jlong x) {
return x*2;
}
