package com.example.andreafranco.musicmanagementapp.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;

import java.util.List;

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM albums ORDER BY name")
    LiveData<List<AlbumEntity>> getAllalbums();

    @Query("SELECT * FROM albums WHERE name = :name")
    LiveData<AlbumEntity> getAlbumById(String name);

    @Insert
    public long insertAlbum(AlbumEntity albumEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllalbums(List<AlbumEntity> albumsList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAlbum(AlbumEntity albumEntity);

    @Delete
    public int deleteAlbum(AlbumEntity albumEntity);
}
