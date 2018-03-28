package com.example.thetardis.opencv_test;

/**
 * Created by Familia on 3/28/2018.
 */

public class Frame {
    public final int width;
    public final int height;
    public final byte[] nv21Data;
    public final Bitmap bitmap;

    public Frame(int width, int height, byte[] nv21Data)
    {
        this.width = width;
        this.height = height;
        this.nv21Data = nv21Data;
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    public void close()
    {
        bitmap.recycle();
    }
}
