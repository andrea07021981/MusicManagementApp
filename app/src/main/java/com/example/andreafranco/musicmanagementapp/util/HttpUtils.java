package com.example.andreafranco.musicmanagementapp.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.example.andreafranco.musicmanagementapp.local.DataGenerator;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpUtils {

    private static final String LOG_TAG = HttpUtils.class.getSimpleName();
    private static Context mContext;

    /**
     * User the query parameter for creating url and getting the List of artist
     * @param query
     * @return
     */
    public static ArrayList<ArtistEntity> fetchArtistListData(String query) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("ws.audioscrobbler.com")
                .appendPath("2.0")
                .appendQueryParameter("method", "artist.getinfo")
                .appendQueryParameter("artist", query)
                .appendQueryParameter("api_key", "662a954a65209a1d7763b5c655077174")
                .appendQueryParameter("format", "json");

        URL url = createUrl(builder.build().toString());
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error during connection", e);
        }

        ArrayList<ArtistEntity> imageArrayList = extractArtistsFromJson(jsonResponse);;
        return imageArrayList;
    }

    /**
     * User the query parameter for creating url and getting the List of artist
     *
     * @param context
     * @param query
     * @return
     */
    public static ArrayList<AlbumEntity> fetchTopAlbumsListData(Context context, String query) {
        mContext = context;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("ws.audioscrobbler.com")
                .appendPath("2.0")
                .appendQueryParameter("method", "artist.gettopalbums")
                .appendQueryParameter("artist", query)
                .appendQueryParameter("api_key", "662a954a65209a1d7763b5c655077174")
                .appendQueryParameter("format", "json");

        URL url = createUrl(builder.build().toString());
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error during connection", e);
        }

        ArrayList<AlbumEntity> imageArrayList = extractAlbumsFromJson(jsonResponse);;
        return imageArrayList;
    }

    private static URL createUrl(String query) {
        URL url = null;
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error with connection code " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    private static ArrayList<ArtistEntity> extractArtistsFromJson(String jsonResponse) {
        if (jsonResponse == null || TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<ArtistEntity> artistList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject artist = root.getJSONObject("artist");

            getArtist(artistList, artist);

            JSONObject similar = artist.getJSONObject("similar");
            JSONArray similarArtists = similar.getJSONArray("artist");
            for (int i = 0; i < similarArtists.length(); i++) {
                getArtist(artistList, similarArtists.getJSONObject(i));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing json ");
        }
        return artistList;
    }

    private static ArrayList<AlbumEntity> extractAlbumsFromJson(String jsonResponse) {
        if (jsonResponse == null || TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<AlbumEntity> albumList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject topalbums = root.getJSONObject("topalbums");
            JSONArray albumArray = topalbums.getJSONArray("album");

            for (int i = 0; i < albumArray.length(); i++) {
                getAlbum(albumList, albumArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing json ");
        }
        return albumList;
    }

    private static void getArtist(ArrayList<ArtistEntity> artistList, JSONObject artist) throws JSONException {
        JSONArray imagesArray = artist.getJSONArray("image");
        String url = null;
        for (int i = 0; i < imagesArray.length(); i++){
            JSONObject image = imagesArray.getJSONObject(i);
            if (image.getString("size").equals("medium")) {
                url = image.getString("#text");
            }
        }
        Bitmap image = createBitmap(url);
        double size = 0L;
        int width = 0;
        int height = 0;
        if (image != null) {
            size = new Integer(getSizeOf(image)).doubleValue();
            width = image.getWidth();
            height = image.getHeight();
        }
        String name = artist.getString("name");
        artistList.add(new ArtistEntity(name, DataGenerator.convertBitmapToByte(image)));
    }

    private static void getAlbum(ArrayList<AlbumEntity> artistList, JSONObject album) throws JSONException {

        String albumName = album.getString("name");
        String name = album.getJSONObject("artist").getString("name");

        JSONArray imagesArray = album.getJSONArray("image");
        String url = null;
        for (int i = 0; i < imagesArray.length(); i++){
            JSONObject image = imagesArray.getJSONObject(i);
            if (image.getString("size").equals("large")) {
                url = image.getString("#text");
            }
        }
        Bitmap image = createBitmap(url);

        String tracks = searchTracks(name, albumName);

        artistList.add(new AlbumEntity(albumName, name, DataGenerator.convertBitmapToByte(image), tracks));
    }

    private static String searchTracks(String name, String albumName) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("ws.audioscrobbler.com")
                .appendPath("2.0")
                .appendQueryParameter("method", "album.getinfo")
                .appendQueryParameter("artist", name)
                .appendQueryParameter("album", albumName)
                .appendQueryParameter("api_key", "662a954a65209a1d7763b5c655077174")
                .appendQueryParameter("format", "json");

        URL url = createUrl(builder.build().toString());
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error during connection", e);
        }

        return extractTracksFromJson(jsonResponse);
    }

    private static String extractTracksFromJson(String jsonResponse) {
        if (jsonResponse == null || TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject tracks = root.getJSONObject("album").getJSONObject("tracks");
            JSONArray track = tracks.getJSONArray("track");

            for (int i = 0; i < track.length(); i++) {
                JSONObject jsonObject = track.getJSONObject(i);
                stringBuilder.append(jsonObject.getString("name")).append("-");
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing json ");
        }
        return stringBuilder.toString();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected static int getSizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }

    private static Bitmap createBitmap(String url) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return bitmap;
    }
}
