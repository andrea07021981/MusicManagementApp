package com.example.andreafranco.musicmanagementapp.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.andreafranco.musicmanagementapp.model.Album;

@Entity(tableName = "albums", indices = {@Index("id")})

public class AlbumEntity implements Album {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String artistname;

    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @NonNull
    private String tracks;

    @Ignore
    public AlbumEntity(@NonNull String name, @NonNull String artistname, @NonNull byte[] image, @NonNull String tracks) {
        this.name = name;
        this.artistname = artistname;
        this.image = image;
        this.tracks = tracks;
    }

    public AlbumEntity(int id,
                       @NonNull String name,
                       @NonNull String artistname,
                       @NonNull byte[] image,
                       @NonNull String tracks) {
        this.id = id;
        this.name = name;
        this.artistname = artistname;
        this.image = image;
        this.tracks = tracks;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    @NonNull
    public String getTracks() {
        return tracks;
    }

    public void setTracks(@NonNull String tracks) {
        this.tracks = tracks;
    }

    @Override
    @NonNull
    public byte[] getImage() {
        return image;
    }

    public void setImage(@NonNull byte[] image) {
        this.image = image;
    }

    @Override
    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(@NonNull String artistname) {
        this.artistname = artistname;
    }
}
