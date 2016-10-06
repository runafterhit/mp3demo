package com.example.wuya.demo;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlayerService extends Service {
    public int musicPos = 0;
    private Messenger mmessenger;
    private Handler mHander;
    private MediaPlayer Mplayer = new MediaPlayer();
    private String musicPath = new String();
    public List<MusicInfo> musiclist = new ArrayList<MusicInfo>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mHander = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 1 :
                        break;
                    default:
                        break;
                }
            }
        };
        mmessenger = new Messenger(mHander);
        return mmessenger.getBinder();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        listInit();
        Mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("PlayerService", "PlayerService: ----->play over to next");
                musicPos++;
                if(musicPos > musiclist.size()-1){
                    musicPos = musiclist.size()-1;
                }
                musicPath = musiclist.get(musicPos).getMusicPath();
                getNewSongSrc();
                Play();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //0 int 1 play 2 prev 3 next 4 sel
        int msg = intent.getIntExtra("MSG", 0);
        if(msg == 0) {
            musicPath = musiclist.get(musicPos).getMusicPath();
            InitMediaPlayer();
            Play();
            Log.e("PlayerService", "PlayerService: ----->init");
        }else if(msg == 1){
            Play();
            Log.e("PlayerService", "PlayerService: ----->play");
        }else if(msg == 2){
            musicPos--;
            if(musicPos<0) {
                musicPos = 0;
            }
            musicPath = musiclist.get(musicPos).getMusicPath();
            getNewSongSrc();
            Play();
            Log.e("PlayerService", "PlayerService: ----->next");
        }else if (msg == 3){
            musicPos++;
            if(musicPos > musiclist.size()-1){
                musicPos = musiclist.size()-1;
            }
            musicPath = musiclist.get(musicPos).getMusicPath();
            getNewSongSrc();
            Play();
            Log.e("PlayerService", "PlayerService: ----->pre");
        }else if(msg == 4){
            musicPos = intent.getIntExtra("POS", 0);
            musicPath = musiclist.get(musicPos).getMusicPath();
            getNewSongSrc();
            Play();
            Log.e("PlayerService", "PlayerService: ----->sel");
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

    private void listInit(){
        ContentResolver cr = this.getContentResolver();
        Cursor cur;
        String[] mString = new String[] { MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA };

        cur = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mString, null,null,null);
        if(cur != null){
            cur.moveToFirst();
            int j=1;
            for (int i=0;i<cur.getCount();i++){
                if(cur.getString(0).endsWith(".mp3")) {
                    MusicInfo mInfo = new MusicInfo();
                    String name = cur.getString(0).substring(0, cur.getString(0).lastIndexOf(".mp3"));
                    mInfo.setMusicIndex(j++);
                    mInfo.setMusicName(name);
                    mInfo.setMusicAlubm(cur.getString(1));
                    mInfo.setMusicSinger(cur.getString(2));
                    mInfo.setMusicTime(cur.getInt(3));
                    mInfo.setMusicSize(cur.getInt(4));
                    mInfo.setMusicId(cur.getInt(5));
                    mInfo.setMusicPath(cur.getString(6));
                    musiclist.add(mInfo);
                }
                cur.moveToNext();
            }
        }else{
            Log.e("PlayerService", "PlayerService: ----->cur=null!!");
        }
    }
}
