package com.example.andreafranco.musicmanagementapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.andreafranco.musicmanagementapp.BasicApp;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.TrackEntity;
import com.example.andreafranco.musicmanagementapp.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class AlbumViewModel extends AndroidViewModel {

    private final LiveData<AlbumEntity> mObservableAlbum;

    private final String mAlbumName;
    private final DataRepository mRepository;

    public AlbumViewModel(@NonNull Application application, DataRepository repository,
                         final String albumName) {
        super(application);
        mAlbumName = albumName;
        mRepository = repository;
        mObservableAlbum = repository.getAlbumByName(mAlbumName);
    }

    public LiveData<AlbumEntity> getAlbum() {
        return mObservableAlbum;
    }

    public LiveData<AlbumEntity> getAlbumByName(String albumName) {
        return mRepository.getAlbumByName(albumName);
    }

    public LiveData<List<TrackEntity>> getTracks(int albumId) {
        return mRepository.getTracksByALbum(albumId);
    }

    public void deleteAlbum(AlbumEntity album) {
        mRepository.deleteAlbum(album);
    }

    public void insertAlbum(AlbumEntity album, List<TrackEntity> mTrackArrayList) {
        mRepository.insertAlbum(album, mTrackArrayList);
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mAlbumId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, String albumId) {
            mApplication = application;
            mAlbumId = albumId;
            mRepository = ((BasicApp) application).getLocalRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AlbumViewModel(mApplication, mRepository, mAlbumId);
        }
    }
}