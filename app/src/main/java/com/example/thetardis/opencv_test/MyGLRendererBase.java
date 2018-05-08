package com.example.thetardis.opencv_test;

/**
 * Created by TheTardis on 4/16/18.
 */

public abstract class MyGLRendererBase implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    protected final String LOGTAG = "MyGLRendererBase";
    protected SurfaceTexture mSTex;
    protected MyGLSurfaceView mView;
    protected boolean mGLInit = false;
    protected boolean mTexUpdate = false;
    MyGLRendererBase(MyGLSurfaceView view) {
        mView = view;
    }
    protected abstract void openCamera();
    protected abstract void closeCamera();
    protected abstract void setCameraPreviewSize(int width, int height);
    public void onResume() {
        Log.i(LOGTAG, "onResume");
    }
    public void onPause() {
        Log.i(LOGTAG, "onPause");
        mGLInit = false;
        mTexUpdate = false;
        closeCamera();
        if(mSTex != null) {
            mSTex.release();
            mSTex = null;
            NativeGLRenderer.closeGL();
        }
    }
    @Override
    public synchronized void onFrameAvailable(SurfaceTexture surfaceTexture) {
        //Log.i(LOGTAG, "onFrameAvailable");
        mTexUpdate = true;
        mView.requestRender();
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        //Log.i(LOGTAG, "onDrawFrame");
        if (!mGLInit)
            return;
        synchronized (this) {
            if (mTexUpdate) {
                mSTex.updateTexImage();
                mTexUpdate = false;
            }
        }
        NativeGLRenderer.drawFrame();
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int surfaceWidth, int surfaceHeight) {
        Log.i(LOGTAG, "onSurfaceChanged("+surfaceWidth+"x"+surfaceHeight+")");
        NativeGLRenderer.changeSize(surfaceWidth, surfaceHeight);
        setCameraPreviewSize(surfaceWidth, surfaceHeight);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(LOGTAG, "onSurfaceCreated");
        String strGLVersion = GLES20.glGetString(GLES20.GL_VERSION);
        if (strGLVersion != null)
            Log.i(LOGTAG, "OpenGL ES version: " + strGLVersion);
        int hTex = NativeGLRenderer.initGL();
        mSTex = new SurfaceTexture(hTex);
        mSTex.setOnFrameAvailableListener(this);
        openCamera();
        mGLInit = true;
    }
}
