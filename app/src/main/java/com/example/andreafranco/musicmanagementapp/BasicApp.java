package com.example.andreafranco.musicmanagementapp;

import android.app.Application;

import com.example.andreafranco.musicmanagementapp.local.AppDatabase;
import com.example.andreafranco.musicmanagementapp.repository.DataRepository;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getLocalRepository() {
        return DataRepository.getInstance(getDatabase(), mAppExecutors);
    }
}
