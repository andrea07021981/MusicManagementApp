package com.example.andreafranco.musicmanagementapp.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.andreafranco.musicmanagementapp.model.AlbumModel;

@Entity(tableName = "albums", indices = {@Index("id")})

public class AlbumEntity implements AlbumModel, Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String artistname;

    @NonNull
    private String imageurl;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AlbumEntity createFromParcel(Parcel in) {
            return new AlbumEntity(in);
        }

        public AlbumEntity[] newArray(int size) {
            return new AlbumEntity[size];
        }
    };

    public AlbumEntity() {

    }

    @Ignore
    public AlbumEntity(@NonNull String name, @NonNull String artistname, @NonNull String imageurl) {
        this.name = name;
        this.artistname = artistname;
        this.imageurl = imageurl;
    }

    public AlbumEntity(int id,
                       @NonNull String name,
                       @NonNull String artistname,
                       @NonNull String imageurl) {
        this.id = id;
        this.name = name;
        this.artistname = artistname;
        this.imageurl = imageurl;
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
    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(@NonNull String image) {
        this.imageurl = image;
    }

    @Override
    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(@NonNull String artistname) {
        this.artistname = artistname;
    }

    // Parcelling part
    public AlbumEntity(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.artistname = in.readString();
        this.imageurl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.artistname);
        dest.writeString(this.imageurl);
    }
}
