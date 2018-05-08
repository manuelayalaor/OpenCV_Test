package com.example.thetardis.opencv_test;

/**
 * Created by TheTardis on 4/16/18.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    MyGLRendererBase mRenderer;
    public MyGLSurfaceView(Context context) {
        super(context);
        if(android.os.Build.VERSION.SDK_INT >= 21)
            mRenderer = new Camera2Renderer(this);
        else
            mRenderer = new CameraRenderer(this);
        setEGLContextClientVersion(2);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
    }
    @Override
    public void onResume() {
        super.onResume();
        mRenderer.onResume();
    }
    @Override
    public void onPause() {
        mRenderer.onPause();
        super.onPause();
    }
}