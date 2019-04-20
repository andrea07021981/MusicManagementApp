package com.example.andreafranco.musicmanagementapp.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import com.example.andreafranco.musicmanagementapp.model.ArtistModel;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "artists", indices = {@Index("id")})
public class ArtistEntity implements ArtistModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @SerializedName("name")
    private String name;

    @NonNull
    @SerializedName("image")
    private String image;

    @Ignore
    public ArtistEntity(@NonNull String name, @NonNull String image) {
        this.name = name;
        this.image = image;
    }

    public ArtistEntity(int id,
                        @NonNull String name,
                        @NonNull String image) {
        this.id = id;
        this.name = name;
        this.image = image;
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
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }
}
