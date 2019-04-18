package com.example.andreafranco.musicmanagementapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.andreafranco.musicmanagementapp.BasicApp;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.repository.DataRepository;

import java.util.List;

public class AlbumListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<AlbumEntity>> mObservableAlbums;
    private final DataRepository mRepository;

    public AlbumListViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        mRepository = repository;
        mObservableAlbums = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableAlbums.setValue(null);

        LiveData<List<AlbumEntity>> albums = mRepository.getAlbums();

        // observe the changes of the Album from the database and forward them
        mObservableAlbums.addSource(albums, mObservableAlbums::setValue);
    }

    /**
     * Expose the LiveData User query so the UI can observe it. Ex user list fragment
     */
    public LiveData<List<AlbumEntity>> getAlbums() {
        return mObservableAlbums;
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
        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int userId, int teamId) {
            mApplication = application;
            mRepository = ((BasicApp) application).getLocalRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AlbumListViewModel(mApplication, mRepository);
        }
    }
}
