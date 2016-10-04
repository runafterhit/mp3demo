package com.example.wuya.demo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

public class PlayerService extends Service {
    private MediaPlayer Mplayer = new MediaPlayer();
    private String musicPath = new String();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        int msg = intent.getIntExtra("MSG", 0);
        if(msg == 0) {
            musicPath = intent.getStringExtra("url");
            InitMediaPlayer();
            Play();
        }else if(msg == 1){
            Play();
        }else if((msg == 2) || (msg == 3) || (msg == 4)){
            musicPath = intent.getStringExtra("url");
            getNewSongSrc();
            Play();
        }

        return  0;
    }

    @Override
    public void onDestroy(){

    }

    public void getNewSongSrc(){
        Mplayer.reset();
        try{
            Mplayer.setDataSource(musicPath);
            Mplayer.prepare();
        } catch (Exception e){
            Log.e("MainActivity", "InitMediaPlayer: source err");
        }

    }
    public void Play(){
        if (!Mplayer.isPlaying()) {
            Mplayer.start();
        } else {
            Mplayer.pause();
        }
    }

    private void InitMediaPlayer(){
            try{Mplayer.setDataSource(musicPath);}
            catch (Exception e){
                Log.e("MainActivity", "InitMediaPlayer: source err");
            }
            try{Mplayer.prepare();}
            catch (Exception e){
                Log.e("MainActivity", "InitMediaPlayer: preer");
            }
    }
}
