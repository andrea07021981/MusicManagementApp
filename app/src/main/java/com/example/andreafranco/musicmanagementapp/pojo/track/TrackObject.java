package com.example.andreafranco.musicmanagementapp.pojo.track;

import com.example.andreafranco.musicmanagementapp.pojo.album.Album;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackObject {
    @SerializedName("album")
    @Expose
    private AlbumTrack album;

    public AlbumTrack getAlbum() {
        return album;
    }

    public void setAlbum(AlbumTrack album) {
        this.album = album;
    }
}
