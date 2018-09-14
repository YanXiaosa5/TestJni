//
// Created by admin on 2018/9/14.
//

//LOG宏定义
#include <stdio.h>
#include <jni.h>
#include <malloc.h>
#include <string.h>
#include <strings.h>
#include <stdlib.h>
#include <unistd.h>
#include <android/log.h>
#include <sys/inotify.h>
#define _Included_com_baitu_jnitest_UnInstallListen
#define LOG_INFO(tag, msg) __android_log_write(ANDROID_LOG_INFO, tag, msg)
#define LOG_DEBUG(tag, msg) __android_log_write(ANDROID_LOG_DEBUG, tag, msg)
#define LOG_WARN(tag, msg) __android_log_write(ANDROID_LOG_WARN, tag, msg)
#define LOG_ERROR(tag, msg) __android_log_write(ANDROID_LOG_ERROR, tag, msg)

/* 内全局变量begin */
static char c_TAG[] = "onEvent";
static jboolean b_IS_COPY = JNI_TRUE;

jstring Java_com_baitu_jnitest_UnInstallListen_register(JNIEnv* env,
        jobject thiz, jstring path, jstring url, jint version) {
    jstring tag = (*env)->NewStringUTF(env, c_TAG);

    //初始化log
    LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
            (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "init OK"),
                    &b_IS_COPY));

    //fork子进程。以运行轮询任务
    pid_t pid = fork();
    if (pid < 0) {
        //出错log
        LOG_ERROR((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                (*env)->GetStringUTFChars(env,
                        (*env)->NewStringUTF(env, "fork failed !!!"),
                        &b_IS_COPY));
    } else if (pid == 0) {
        //子进程注冊文件夹监听器
        int fileDescriptor = inotify_init();
        if (fileDescriptor < 0) {
            LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                    (*env)->GetStringUTFChars(env,
                            (*env)->NewStringUTF(env,
                                    "inotify_init failed !!!"), &b_IS_COPY));

            exit(1);
        }

        int watchDescriptor;

        watchDescriptor = inotify_add_watch(fileDescriptor,
                (*env)->GetStringUTFChars(env, path, NULL), IN_DELETE);
        if (watchDescriptor < 0) {
            LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                    (*env)->GetStringUTFChars(env,
                            (*env)->NewStringUTF(env,
                                    "inotify_add_watch failed !!!"),
                            &b_IS_COPY));

            exit(1);
        }

        //分配缓存。以便读取event。缓存大小=一个struct inotify_event的大小。这样一次处理一个event
        void *p_buf = malloc(sizeof(struct inotify_event));
        if (p_buf == NULL) {
            LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                    (*env)->GetStringUTFChars(env,
                            (*env)->NewStringUTF(env, "malloc failed !!!"),
                            &b_IS_COPY));

            exit(1);
        }
        //開始监听
        LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                (*env)->GetStringUTFChars(env,
                        (*env)->NewStringUTF(env, "start observer"),
                        &b_IS_COPY));
        //read会堵塞进程
        size_t readBytes = read(fileDescriptor, p_buf,
                sizeof(struct inotify_event));

        //走到这里说明收到文件夹被删除的事件。注销监听器
        free(p_buf);
        inotify_rm_watch(fileDescriptor, IN_DELETE);

        //文件夹不存在log
        LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY),
                (*env)->GetStringUTFChars(env,
                        (*env)->NewStringUTF(env, "uninstalled"), &b_IS_COPY));

        if (version >= 17) {
            //4.2以上的系统因为用户权限管理更严格，须要加上 --user 0
            execlp("am", "am", "start", "--user", "0", "-a",
                    "android.intent.action.VIEW", "-d",
                    (*env)->GetStringUTFChars(env, url, NULL), (char *) NULL);
        } else {
            execlp("am", "am", "start", "-a", "android.intent.action.VIEW",
                    "-d", (*env)->GetStringUTFChars(env, url, NULL),
                    (char *) NULL);
        }
        //扩展：能够运行其它shell命令，am(即activity manager)，能够打开某程序、服务，broadcast intent，等等

    } else {
        //父进程直接退出，使子进程被init进程领养，以避免子进程僵死
        return (*env)->NewStringUTF(env, "父进程直接退出，使子进程被init进程领养，以避免子进程僵死");
    }

    return (*env)->NewStringUTF(env, "Hello from JNI !");
}

