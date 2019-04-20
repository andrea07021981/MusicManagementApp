package com.example.andreafranco.musicmanagementapp.retrofit;

import com.example.andreafranco.musicmanagementapp.pojo.ArtistObject;
import com.example.andreafranco.musicmanagementapp.pojo.album.TopAlbumObject;
import com.example.andreafranco.musicmanagementapp.pojo.track.TrackObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitClient {

    //http://ws.audioscrobbler.com/2.0?method=artist.getinfo&artist=cher&api_key=662a954a65209a1d7763b5c655077174&format=json

    @GET("/2.0")
    Call<ArtistObject> getArtistByName(
            @Query("method") String method,
            @Query("artist") String artist,
            @Query("api_key") String apiKey,
            @Query("format") String format);

    @GET("/2.0")
    Call<TopAlbumObject> getTopAlbums(
            @Query("method") String method,
            @Query("artist") String artist,
            @Query("api_key") String apiKey,
            @Query("format") String format);

    @GET("/2.0")
    Call<TrackObject> getTracks(
            @Query("method") String method,
            @Query("artist") String artist,
            @Query("album") String album,
            @Query("api_key") String apiKey,
            @Query("format") String format);

}
