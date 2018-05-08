package com.example.thetardis.opencv_test;

/**
 * Created by TheTardis on 4/16/18.
 */

public class NativeGLRenderer {
    static
    {
        System.loadLibrary("opencv_java3"); // comment this when using OpenCV Manager
        System.loadLibrary("JNIrender");
    }
    public static native int initGL();
    public static native void closeGL();
    public static native void drawFrame();
    public static native void changeSize(int width, int height);
}