#include <jni.h>
#include <string>
#include <android/log.h>
#include "ObserverChain.h"

//#include "std"

#include <vector>

static JavaVM *jvm = nullptr;


std::vector<ObserverChain *> store_Wlistener_vector;

JNIEnv *store_env;


void txtCallback(JNIEnv *env, const _jstring *message_);

extern "C"
JNIEXPORT void JNICALL
Java_ua_zt_mezon_myjnacallbacktest_MainActivity_nsubscribeListener(JNIEnv *env, jobject instance,
                                                                   jobject listener) {

    env->GetJavaVM(&jvm); //store jvm reference for later call

    store_env = env;

    jweak store_Wlistener = env->NewWeakGlobalRef(listener);
    jclass clazz = env->GetObjectClass(store_Wlistener);

    jmethodID store_method = env->GetMethodID(clazz, "onAcceptMessage", "(Ljava/lang/String;)V");
    jmethodID store_methodVAL = env->GetMethodID(clazz, "onAcceptMessageVal", "(I)V");

    ObserverChain *tmpt = new ObserverChain(store_Wlistener, store_method, store_methodVAL);

    store_Wlistener_vector.push_back(tmpt);


    __android_log_print(ANDROID_LOG_VERBOSE, "GetEnv:", " Subscribe to Listener  OK \n");
    if (nullptr == store_method) return;


}
extern "C"
JNIEXPORT void JNICALL
Java_ua_zt_mezon_myjnacallbacktest_MainActivity_ndismissListener(JNIEnv *env, jobject instance) {
    if (!store_Wlistener_vector.empty()) {
        for (auto &i: store_Wlistener_vector) {
            env->DeleteWeakGlobalRef(i->store_Wlistener);
            i->store_method = NULL;
            i->store_methodVAL = NULL;
        }
        store_Wlistener_vector.clear();
    }


}

void test_string_callback_fom_c(char *val) {
    __android_log_print(ANDROID_LOG_VERBOSE, "GetEnv:", " start Callback  to JNL [%d]  \n", val);
    JNIEnv *g_env;
    if (NULL == jvm) {
        __android_log_print(ANDROID_LOG_ERROR, "GetEnv:", "  No VM  \n");
        return;
    }
    //  double check it's all ok
    JavaVMAttachArgs args;
    args.version = JNI_VERSION_1_6; // set your JNI version
    args.name = NULL; // you might want to give the java thread a name
    args.group = NULL; // you might want to assign the java thread to a ThreadGroup

    int getEnvStat = jvm->GetEnv((void **) &g_env, JNI_VERSION_1_6);

    if (getEnvStat == JNI_EDETACHED) {
        __android_log_print(ANDROID_LOG_ERROR, "GetEnv:", " not attached\n");
        if (jvm->AttachCurrentThread(&g_env, &args) != 0) {
            __android_log_print(ANDROID_LOG_ERROR, "GetEnv:", " Failed to attach\n");
        }
    } else if (getEnvStat == JNI_OK) {
        __android_log_print(ANDROID_LOG_VERBOSE, "GetEnv:", " JNI_OK\n");
    } else if (getEnvStat == JNI_EVERSION) {
        __android_log_print(ANDROID_LOG_ERROR, "GetEnv:", " version not supported\n");
    }

    jstring message = g_env->NewStringUTF(val);//

    txtCallback(g_env, message);

    if (g_env->ExceptionCheck()) {
        g_env->ExceptionDescribe();
    }

    if (getEnvStat == JNI_EDETACHED) {
        jvm->DetachCurrentThread();
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_ua_zt_mezon_myjnacallbacktest_MainActivity_nonNextListener(JNIEnv *env, jobject instance,
                                                                jstring message_) {


    txtCallback(env, message_);

}

void txtCallback(JNIEnv *env, const _jstring *message_) {
    if (!store_Wlistener_vector.empty()) {
        for (auto &i: store_Wlistener_vector) {
            env->CallVoidMethod(i->store_Wlistener,
                                i->store_method, message_);
        }

    }
}