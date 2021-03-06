package com.example.andreafranco.musicmanagementapp.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.andreafranco.musicmanagementapp.model.TrackModel;

@Entity(tableName = "tracks", indices = {@Index("id")})
public class TrackEntity implements TrackModel, Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String duration;

    @NonNull
    private int albumid;

    public static final Creator CREATOR = new Creator() {
        public TrackEntity createFromParcel(Parcel in) {
            return new TrackEntity(in);
        }

        public TrackEntity[] newArray(int size) {
            return new TrackEntity[size];
        }
    };

    public TrackEntity() {

    }

    @Ignore
    public TrackEntity(@NonNull String name, @NonNull String duration) {
        this.name = name;
        this.duration = duration;
    }

    public TrackEntity(int id,
                       @NonNull String name,
                       @NonNull String duration,
                       @NonNull int albumid) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.albumid = albumid;
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
    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(@NonNull int albumid) {
        this.albumid = albumid;
    }

    @Override
    @NonNull
    public String getDuration() {
        return duration;
    }

    public void setDuration(@NonNull String duration) {
        this.duration = duration;
    }

    // Parcelling part
    public TrackEntity(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.duration = in.readString();
        this.albumid = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.duration);
        dest.writeInt(this.albumid);
    }
}
