package com.example.iat359_petjournal;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicPlayer extends Service {
    private MediaPlayer mp;
    boolean paused = false;
    Thread backgroundThread;

    public MusicPlayer(){

    }
    //we need to override onCreate() and onDestroy()
    //these methods contain the functionality of the service when started and stopped
    @Override
    public void onCreate() {
        super.onCreate();
        paused = false;
        backgroundThread = new Thread(new Runnable() {
            public void run() {
                playMusicFromWeb();
            }
        });
        backgroundThread.start();
    }

    //    ondestroy pause and stop the media player and the background thread is interrupted
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp!=null)
        {
            mp.stop();
            mp.reset();
            mp.release();
        }

        paused = true;
        backgroundThread.interrupt();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //    play the music/beep sound using media player
    public void playMusicFromWeb()
    {
        try {
            mp = MediaPlayer.create(this, R.raw.bgmusic);
            mp.start();
            mp.isLooping();
        }
        catch (Exception e)
        {
            Log.e("Service LogTag", "Player failed", e);
        }
    }
}
