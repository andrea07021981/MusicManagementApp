package com.example.andreafranco.musicmanagementapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.andreafranco.musicmanagementapp.local.AppDatabase;
import com.example.andreafranco.musicmanagementapp.local.DataGenerator;
import com.example.andreafranco.musicmanagementapp.local.dao.AlbumDao;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    private AlbumDao mAlbumDao;
    private AppDatabase mDb;
    private Context mContext;

    @Before
    public void createDb() {
        mContext = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();
        mAlbumDao = mDb.albumDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        List<AlbumEntity> albums = DataGenerator.generateAlbums(mContext);
        long[] value = mAlbumDao.insertAllalbums(albums);
        LiveData<List<AlbumEntity>> albumsRead = mAlbumDao.getAllalbums();
        assertThat(albumsRead, is(notNullValue()));
    }
}
