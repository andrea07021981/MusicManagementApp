package com.example.andreafranco.musicmanagementapp.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataGenerator {


    //Users
    private static final String[] ALBUM = new String[]{
            "Name1", "Name2", "Name3", "Name4"};

    private static final String[] ARTIST = new String[]{
            "Name1", "Name2", "Name3", "Name4"};

    private static final String[] TRACKS = new String[]{
            "1,2,3,4", "5,6,7,8", "0,0,0,0", "1,1,1,1"};

    public static List<AlbumEntity> generateAlbums(Context context) {
        List<AlbumEntity> users = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < ALBUM.length; i++) {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            Bitmap bitmap = Bitmap.createBitmap(100, 100, conf); // this creates a MUTABLE bitmap
            Canvas canvas = new Canvas(bitmap);
            byte[] imageData = convertBitmapToByte(bitmap);
            AlbumEntity album = new AlbumEntity(
                    id,
                    ALBUM[i],
                    ARTIST[i],
                    imageData,
                    TRACKS[i]);
            users.add(album);
            id++;
        }
        return users;
    }

    public static byte[] convertBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream output= new ByteArrayOutputStream(); bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
        return output.toByteArray();
    }
}
