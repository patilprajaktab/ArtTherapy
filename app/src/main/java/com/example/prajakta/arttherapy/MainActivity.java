package com.example.prajakta.arttherapy;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity implements SensorEventListener {


   // Canvas will be updated only when phone is shaken 3 times //
    private SensorManager mSensorManager;
    private static final int FORCE_SENSOR_THRESHOLD = 50;
    private static final int TIME_SENSOR_THRESHOLD = 350;
    private static final int SUBSEQUENT_SHAKE_TIMEOUT = 750;
    private static final int SHAKE_INTERVAL_DURATION = 2000;
    private static final int SHAKE_ITERATOR = 3;


    private float LastSensorX = -1.0f, LastSensorY = -1.0f, LastSensorZ = -1.0f;
    private long LastSensorTime;
    private int ShakeIterator = 0;
    private long LastSensorShake;
    private long LastSensorForce;
    private float Accelerator;

    private CustomView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customCanvas = (CustomView) findViewById(R.id.custom);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        LastSensorForce = System.currentTimeMillis();
        LastSensorTime = LastSensorForce;
        LastSensorShake = LastSensorForce;
    }


    public void clearCanvas(View v) {

        customCanvas.clearCanvas();

    }


    protected void OnResume() {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long now = System.currentTimeMillis();

        if ((now - LastSensorForce) > SUBSEQUENT_SHAKE_TIMEOUT) {
            ShakeIterator = 0;
        }

        if ((now - LastSensorTime) > TIME_SENSOR_THRESHOLD) {
            long diff = now - LastSensorTime;

            Accelerator = Math.abs(event.values[0] + event.values[1] + event.values[2] - LastSensorX - LastSensorY - LastSensorZ) / diff * 10000;
            if (Accelerator > FORCE_SENSOR_THRESHOLD) {

                if ((++ShakeIterator >= SHAKE_ITERATOR) && ((now - LastSensorShake) > SHAKE_INTERVAL_DURATION)) {
                    Vibrator v = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    clearCanvas(customCanvas);
                    Intent intent = new Intent(this,Eraser.class);
                    startService(intent);


                    LastSensorShake = now;
                    ShakeIterator = 0;
                }
                LastSensorForce = now;
            }
            else
            {


            }

            LastSensorTime = now;
            LastSensorX = event.values[0];
            LastSensorY = event.values[1];
            LastSensorZ = event.values[2];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
