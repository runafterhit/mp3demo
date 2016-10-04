package com.example.wuya.demo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends BaseAdapter {
    public ContentResolver cr;
    public Cursor cur;
    public List<MusicInfo> musiclist;
    public Context context;

    public MusicAdapter(Context context){
        this.context = context;

        cr = context.getContentResolver();
        musiclist = new ArrayList<MusicInfo>();

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
        }

    }

    @Override
    public int getCount() {
        return musiclist.size();
    }

    @Override
    public Object getItem(int i) {
        return musiclist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mlistLayout = LayoutInflater.from(context);
        View mlistView = mlistLayout.inflate(R.layout.music_list_item_layout, null);
        TextView songName = (TextView) mlistView.findViewById(R.id.songname);
        songName.setText(musiclist.get(i).getMusicName());

        return mlistView;
    }
}
