/*
package com.example.andreafranco.musicmanagementapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ArtistInfo implements Parcelable {

    private int id;
    private String name;
    private byte[] image;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ArtistInfo createFromParcel(Parcel in) {
            return new ArtistInfo(in);
        }

        public ArtistInfo[] newArray(int size) {
            return new ArtistInfo[size];
        }
    };

    public ArtistInfo() {

    }
    public ArtistInfo(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    // Parcelling part
    public ArtistInfo(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.image = new byte[in.readInt()];
        in.readByteArray(this.image);
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.team = in.readString();
        this.shift = in.readString();
        this.shiftStatus = in.readInt();
        this.userFunction = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeInt(image.length);
        dest.writeByteArray(this.image);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.team);
        dest.writeString(this.shift);
        dest.writeInt(this.shiftStatus);
        dest.writeString(this.userFunction);
    }
}
*/
