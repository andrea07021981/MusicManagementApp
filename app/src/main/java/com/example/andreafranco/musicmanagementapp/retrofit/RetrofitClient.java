package com.example.andreafranco.musicmanagementapp.retrofit;

import com.example.andreafranco.musicmanagementapp.pojo.artist.ArtistObject;
import com.example.andreafranco.musicmanagementapp.pojo.album.TopAlbumObject;
import com.example.andreafranco.musicmanagementapp.pojo.track.TrackObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitClient {

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
