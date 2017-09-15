package com.netcetera.reactnative.utils;

public class LogUtils {
    public static void i(String TAG, String message) {
        android.util.Log.i(TAG, message);
    }
    public static void d(String TAG, String message) {
        android.util.Log.d(TAG, message);
    }
    public static void w(String TAG, String message) {
        android.util.Log.w(TAG, message);
    }
    public static void e(String TAG, String message) {
        android.util.Log.e(TAG, message);
    }

}
