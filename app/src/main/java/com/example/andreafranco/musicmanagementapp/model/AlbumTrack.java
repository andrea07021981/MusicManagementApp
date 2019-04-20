package com.example.andreafranco.musicmanagementapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AlbumTrack implements Parcelable{

    public int id;
    public String albumname;
    public String artistname;
    public String imageurl;
    public String trackname;
    public String trackduration;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AlbumTrack createFromParcel(Parcel in) {
            return new AlbumTrack(in);
        }

        public AlbumTrack[] newArray(int size) {
            return new AlbumTrack[size];
        }
    };

    public AlbumTrack() {

    }

    public AlbumTrack(int id, String albumname, String artistname, String imageurl, String trackname, String trackduration) {
        this.id = id;
        this.albumname = albumname;
        this.artistname = artistname;
        this.imageurl = imageurl;
        this.trackname = trackname;
        this.trackduration = trackduration;
    }

    // Parcelling part
    public AlbumTrack(Parcel in){
        this.id = in.readInt();
        this.albumname = in.readString();
        this.artistname = in.readString();
        this.imageurl = in.readString();
        this.trackname = in.readString();
        this.trackduration = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.albumname);
        dest.writeString(this.artistname);
        dest.writeString(this.imageurl);
        dest.writeString(this.trackname);
        dest.writeString(this.trackduration);
    }
}
