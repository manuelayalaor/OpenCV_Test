package com.example.thetardis.opencv_test;

/**
 * Created by Familia on 3/28/2018.
 */

public class CameraController
{
    protected FrameListener frameListener;

    public abstract void initialize() throws NoCameraAvailableException;

    public abstract void shutdown();

    public abstract int getPreviewWidth();

    public abstract int getPreviewHeight();

    public void setFrameListener(FrameListener frameListener)
    {
        this.frameListener = frameListener;
    }

}
