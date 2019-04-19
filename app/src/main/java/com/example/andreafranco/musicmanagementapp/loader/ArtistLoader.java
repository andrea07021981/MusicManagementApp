package com.example.andreafranco.musicmanagementapp.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;
import com.example.andreafranco.musicmanagementapp.util.HttpUtils;

import java.util.ArrayList;

public class ArtistLoader extends AsyncTaskLoader<ArrayList<ArtistEntity>> {

    String mQuery;

    public ArtistLoader(@NonNull Context context, String query) {
        super(context);
        mQuery = query;
    }

    @Nullable
    @Override
    public ArrayList<ArtistEntity> loadInBackground() {
        if (mQuery == null || TextUtils.isEmpty(mQuery)) {
            return null;
        }
        return HttpUtils.fetchArtistListData(mQuery);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
