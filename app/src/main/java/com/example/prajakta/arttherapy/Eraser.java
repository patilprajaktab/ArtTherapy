package com.example.prajakta.arttherapy;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

/**
 * Created by prajakta on 2/14/15.
 */
public class Eraser extends IntentService {
    private boolean flag = false;
    public Eraser() {
        super("HelloIntentService");
    }

    protected void onHandleIntent(Intent intent) {

        MediaPlayer mp = MediaPlayer.create(Eraser.this, R.raw.eraser);
        mp.start();
        while(mp.isPlaying())
        {


            System.out.println();


        }
        mp.reset();
        mp.release();

    }

}


