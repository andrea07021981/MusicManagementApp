package com.example.andreafranco.musicmanagementapp.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.TrackEntity;

import java.util.List;

@Dao
public interface TrackDao {

    @Query("SELECT * FROM tracks ORDER BY id")
    LiveData<List<TrackEntity>> getAllTracks();

    @Query("SELECT * FROM tracks WHERE id = :id")
    LiveData<TrackEntity> getTrackById(long id);

    @Insert
    public long insertTrack(TrackEntity trackEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllTracks(List<TrackEntity> trackList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArtist(TrackEntity trackEntity);

    @Delete
    public int deleteArtist(TrackEntity artistEntity);
}
