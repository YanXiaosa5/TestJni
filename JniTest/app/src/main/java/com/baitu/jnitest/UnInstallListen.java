package com.baitu.jnitest;

public class UnInstallListen {

    static {
        System.loadLibrary("uninstall");
    }

    /***
     *
     * @param path 须要监听的文件路径。可用 getApplicationContext().getFilesDir().getPath()
     * @param url 卸载调转http
     * @param version android.os.Build.VERSION.SDK_INT
     * @return
     */
    public static native String register(String path, String url, int version);

}
