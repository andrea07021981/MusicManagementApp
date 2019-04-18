package com.example.andreafranco.musicmanagementapp.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import com.example.andreafranco.musicmanagementapp.model.Artist;

@Entity(tableName = "artists", indices = {@Index("id")})
public class ArtistEntity implements Artist {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @Ignore
    public ArtistEntity(@NonNull String name) {
        this.name = name;
    }

    public ArtistEntity(int id,
                      @NonNull String name) {
        this.id = id;
        this.name = name;
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
}
