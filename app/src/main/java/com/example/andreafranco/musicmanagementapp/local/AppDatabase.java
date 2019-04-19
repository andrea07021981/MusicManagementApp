package com.example.andreafranco.musicmanagementapp.local;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.andreafranco.musicmanagementapp.AppExecutors;
import com.example.andreafranco.musicmanagementapp.BuildConfig;
import com.example.andreafranco.musicmanagementapp.local.dao.AlbumDao;
import com.example.andreafranco.musicmanagementapp.local.dao.ArtistDao;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;

import java.util.List;

@Database(entities = {ArtistEntity.class, AlbumEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();

    @VisibleForTesting
    public static final String DATABASE_NAME = "musicdb";
    private static AppDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = buildDatabase(context, executors);
                sInstance.updateDatabaseCreated(context.getApplicationContext());
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context context, final AppExecutors executors) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(()-> {
                            if (BuildConfig.DEBUG) {
                                // Generate the data for pre-population
                                AppDatabase database = AppDatabase.getInstance(context, executors);
                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();
                            }
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            String currentDBPath=context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
            Log.d(LOG_TAG, currentDBPath);
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public MutableLiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    };

    public abstract ArtistDao artistDao();

    public abstract AlbumDao albumDao();
}
