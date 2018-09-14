LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_LDLIBS += -llog
LOCAL_MODULE := uninstall
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	C:\fanhua-project-android\JniTest\app\src\main\jni\listen.c \

LOCAL_C_INCLUDES += C:\fanhua-project-android\JniTest\app\src\main\jni
LOCAL_C_INCLUDES += C:\fanhua-project-android\JniTest\app\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
