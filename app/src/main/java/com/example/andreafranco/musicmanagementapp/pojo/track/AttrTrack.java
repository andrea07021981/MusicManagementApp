package com.example.andreafranco.musicmanagementapp.pojo.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttrTrack {
    @SerializedName("rank")
    @Expose
    private String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
