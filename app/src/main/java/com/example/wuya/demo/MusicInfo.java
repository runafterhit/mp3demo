package com.example.wuya.demo;

public class MusicInfo{
    private int musicIndex;
    private int musicAlubmId;
    private String musicName;
    private String musicSinger;
    private int musicTime;
    private String musicAlubm;
    private int musicSize;
    private String musicPath;

    public int getMusicIndex(){
        return musicIndex;
    }
    public void setMusicIndex(int index)
    {
        this.musicIndex = index;
    }

    public int getMusicId(){
        return musicAlubmId;
    }
    public void setMusicId(int id)
    {
        this.musicAlubmId = id;
    }

    public String getMusicName(){
        return musicName;
    }
    public void setMusicName(String name)
    {
        this.musicName = name;
    }

    public String getMusicSinger(){
        return musicSinger;
    }
    public void setMusicSinger(String singer)
    {
        this.musicSinger = singer;
    }

    public int getMusicTime(){
        return musicTime;
    }
    public void setMusicTime(int time)
    {
        this.musicTime = time;
    }

    public String  getMusicAlbum(){
        return musicAlubm;
    }
    public void setMusicAlubm(String alubm)
    {
        this.musicAlubm = alubm;
    }

    public int getMusicSize(){
        return musicSize;
    }
    public void setMusicSize(int size)
    {
        this.musicSize = size;
    }

    public String  getMusicPath(){
        return musicPath;
    }
    public void setMusicPath(String path)
    {
        this.musicPath = path;
    }
}
