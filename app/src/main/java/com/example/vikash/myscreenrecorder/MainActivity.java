package com.example.vikash.myscreenrecorder;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    MediaProjection mediaProjection;
    MediaProjectionManager mediaProjectionManager;
    Surface surface;
    SurfaceView surfaceView;
    VirtualDisplay virtualDisplay;
    int REQ_CODE=100;
    int width;
    int height;
    int dpi;
    MediaRecorder recorder;



    SaveVideo record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("LifeCycle"," onCreate called");

       // surfaceView =(SurfaceView)findViewById(R.id.surface_view);
       // surface=surfaceView.getHolder().getSurface();

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width=displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;
        dpi=displayMetrics.densityDpi;
        mediaProjectionManager=(MediaProjectionManager)getSystemService(MEDIA_PROJECTION_SERVICE);

        Intent intent=mediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(intent,REQ_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaProjection=mediaProjectionManager.getMediaProjection(resultCode,data);

        //resonsible for displaying content on surface
        //virtualDisplay=mediaProjection.createVirtualDisplay("MainActivity",width,height,dpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,surface,null,null);

        final int bitrate = 6000000;
       //recording take place here;
        File file = new File(Environment.getExternalStorageDirectory(),
                "record-" + width + "x" + height + "-" + System.currentTimeMillis() + ".mp4");

        record=new SaveVideo(width,height,bitrate,dpi,mediaProjection,file.getAbsolutePath());
        record.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("LifeCycle"," OnResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("LifeCycle"," OnPause called");
        if(virtualDisplay!=null)
        virtualDisplay.release();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v("LifeCycle"," OnStopped called");
        record.quit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("LifeCycle"," OnDestroy called");
    }


}
