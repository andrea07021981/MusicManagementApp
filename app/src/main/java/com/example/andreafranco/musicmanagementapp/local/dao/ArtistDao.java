package com.example.andreafranco.musicmanagementapp.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;

import java.util.List;

@Dao
public interface ArtistDao {

    @Query("SELECT * FROM artists ORDER BY name")
    LiveData<List<ArtistEntity>> getAllArtists();

    @Query("SELECT * FROM artists WHERE id = :id")
    LiveData<ArtistEntity> getArtistById(long id);

    @Insert
    public long insertArtist(ArtistEntity artistEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllArtists(List<ArtistEntity> artistsList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArtist(ArtistEntity artistEntity);

    @Delete
    public int deleteArtist(ArtistEntity artistEntity);
}
