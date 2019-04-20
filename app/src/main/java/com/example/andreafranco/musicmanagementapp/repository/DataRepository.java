package com.example.andreafranco.musicmanagementapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.example.andreafranco.musicmanagementapp.AppExecutors;
import com.example.andreafranco.musicmanagementapp.local.AppDatabase;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.TrackEntity;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    private static DataRepository sInstance;
    private final AppDatabase mDatabase;
    private final AppExecutors mExecutors;
    private MediatorLiveData<List<AlbumEntity>> mObservableAlbums;

    private DataRepository(final AppDatabase appDatabase, AppExecutors appExecutors) {
        mDatabase = appDatabase;
        mExecutors = appExecutors;
        mObservableAlbums = new MediatorLiveData<>();

        mObservableAlbums.addSource(mDatabase.albumDao().getAllalbums(),
                albumEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableAlbums.postValue(albumEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database, AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance =  new DataRepository(database, appExecutors);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of albums from the database and get notified when the data changes.
     */
    public LiveData<List<AlbumEntity>> getAlbums() {
        return mObservableAlbums;
    }

    public LiveData<AlbumEntity> getAlbumByName(final String albumName) {
        return mDatabase.albumDao().getAlbumById(albumName);
    }

    //here I add the tracks on db
    //TODO the next step would be creating a relation between table and do the inserts simultaneously with a @Transaction method
    public void insertAlbum(AlbumEntity albumEntity, ArrayList<TrackEntity> mTrackArrayList) {
        mExecutors.diskIO().execute(()-> {
            long id = mDatabase.albumDao().insertAlbum(albumEntity);
            for (TrackEntity track : mTrackArrayList) {
                track.setAlbumid(Math.toIntExact(id));
            }
            mDatabase.trackDao().insertAllTracks(mTrackArrayList);
        });
    }

    public void insertTracks(List<TrackEntity> tracks) {
        mExecutors.diskIO().execute(()-> {
            mDatabase.trackDao().insertAllTracks(tracks);
        });
    }

    public void updateAlbum(AlbumEntity albumEntity) {
        mExecutors.diskIO().execute(() -> {
            mDatabase.albumDao().updateAlbum(albumEntity);
        });
    }

    public void deleteAlbum(AlbumEntity album) {
        mExecutors.diskIO().execute(() -> {
            int id = mDatabase.albumDao().deleteAlbum(album.getId());
            mDatabase.trackDao().deleteTracks(id);
        });
    }
}
