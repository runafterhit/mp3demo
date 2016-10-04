package com.example.wuya.demo;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button ButtonPre;
    private  Button ButtonNext;
    private  Button ButtonPlay;
    private PlayerService playService = new PlayerService();

    public ListView mlistview;
    public MusicAdapter mAdapter;
    public int musicPos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButtonNext = (Button)findViewById(R.id.bnnext);
        ButtonPre = (Button)findViewById(R.id.bnpre);
        ButtonPlay = (Button)findViewById(R.id.bnplay);

        View.OnClickListener ClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent();
                MusicInfo mInfo;
                switch (view.getId()) {
                    case R.id.bnplay:
                        startIntent.putExtra("MSG", 1);//0 int 1 play 2 prev 3 next 4 sel
                        startIntent.setClass(MainActivity.this, PlayerService.class);
                        startService(startIntent);
                        break;
                    case R.id.bnnext:
                        musicPos++;
                        if(musicPos > mAdapter.musiclist.size()-1){
                            musicPos = mAdapter.musiclist.size()-1;
                        }
                        mInfo = mAdapter.musiclist.get(musicPos);
                        startIntent.putExtra("MSG", 2);//0 int 1 play 2 prev 3 next 4 sel
                        startIntent.putExtra("url", mInfo.getMusicPath());
                        startIntent.setClass(MainActivity.this, PlayerService.class);
                        startService(startIntent);
                        break;
                    case R.id.bnpre:
                        musicPos--;
                        if(musicPos<0) {
                            musicPos = 0;
                        }
                        mInfo = mAdapter.musiclist.get(musicPos);
                        startIntent.putExtra("MSG", 3);//0 int 1 play 2 prev 3 next 4 sel
                        startIntent.putExtra("url", mInfo.getMusicPath());
                        startIntent.setClass(MainActivity.this, PlayerService.class);
                        startService(startIntent);
                        break;
                }
            }
        };

        ButtonNext.setOnClickListener(ClickListener);
        ButtonPlay.setOnClickListener(ClickListener);
        ButtonPre.setOnClickListener(ClickListener);

        mlistview = (ListView)findViewById(R.id.listView);
        mAdapter = new MusicAdapter(this);

        mlistview.setAdapter(mAdapter);

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                musicPos = i;
                MusicInfo mInfo = mAdapter.musiclist.get(musicPos);
                Intent startIntent = new Intent();
                startIntent.putExtra("MSG", 4);//0 int 1 play 2 prev 3 next 4 sel
                startIntent.putExtra("url", mInfo.getMusicPath());
                startIntent.setClass(MainActivity.this, PlayerService.class);
                startService(startIntent);

            }
        });

        initService(musicPos);
    }

    public void initService(int pos){
        MusicInfo mInfo = mAdapter.musiclist.get(pos);
        Intent startIntent = new Intent();
        startIntent.putExtra("url", mInfo.getMusicPath());
        startIntent.putExtra("MSG", 0);//0 int 1 play 2 prev 3 next
        startIntent.setClass(MainActivity.this, PlayerService.class);
        startService(startIntent);
    }

}

