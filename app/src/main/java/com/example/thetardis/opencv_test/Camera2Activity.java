package com.example.thetardis.opencv_test;

/**
 * Created by TheTardis on 3/28/18.
 */

import android.app.Activity;
        import android.os.Bundle;

public class Camera2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }
    }

}