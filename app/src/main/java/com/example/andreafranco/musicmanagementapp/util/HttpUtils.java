package com.example.andreafranco.musicmanagementapp.util;

import android.support.v4.app.Fragment;

import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.TrackEntity;
import com.example.andreafranco.musicmanagementapp.pojo.artist.Artist;
import com.example.andreafranco.musicmanagementapp.pojo.artist.ArtistObject;
import com.example.andreafranco.musicmanagementapp.pojo.artist.Artist_;
import com.example.andreafranco.musicmanagementapp.pojo.artist.Image;
import com.example.andreafranco.musicmanagementapp.pojo.artist.Image_;
import com.example.andreafranco.musicmanagementapp.pojo.artist.Similar;
import com.example.andreafranco.musicmanagementapp.pojo.album.Album;
import com.example.andreafranco.musicmanagementapp.pojo.album.TopAlbumObject;
import com.example.andreafranco.musicmanagementapp.pojo.album.Topalbums;
import com.example.andreafranco.musicmanagementapp.pojo.track.AlbumTrack;
import com.example.andreafranco.musicmanagementapp.pojo.track.Track;
import com.example.andreafranco.musicmanagementapp.pojo.track.TrackObject;
import com.example.andreafranco.musicmanagementapp.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {

    private static final String LOG_TAG = HttpUtils.class.getSimpleName();
    private static final String BASE_URL = "http://ws.audioscrobbler.com";
    private static final String API_KEY = "662a954a65209a1d7763b5c655077174";

    private static Retrofit retrofit;
    private static HttpUtils sInstance;
    private static DataInterface mListener;

    public interface DataInterface {
        void responseArtistData(ArrayList<ArtistEntity> artists);
        void responseAlbumData(ArrayList<AlbumEntity> albums);
        void responseTrackData(ArrayList<TrackEntity> tracks);
    }

    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (sInstance == null) {
            sInstance = new HttpUtils();
        }
        return sInstance;
    }

    private Retrofit getRetrofitClient() {
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Convertor library used to convert response into POJO
                    .build();
        }
        return retrofit;
    }


    public void fetchArtist(Fragment fragment, String artistName) {
        mListener = (DataInterface) fragment;
        Retrofit retrofit = getRetrofitClient();
        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);

        Call<ArtistObject> call = retrofitClient.getArtistByName(
                "artist.getinfo",
                artistName,
                API_KEY,
                "json");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of ArtistEntity POJO class
                 */
                if (response.body() != null) {
                    ArrayList<ArtistEntity> artistData = parseArtistData(response);
                    mListener.responseArtistData(artistData);
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                /*
                Error callback
                */
                Request request = call.request();
            }
        });
    }

    private static ArrayList<ArtistEntity> parseArtistData(Response response){
        Object body = response.body();
        ArrayList<ArtistEntity> artistList = new ArrayList<>();
        Artist mainArtist = ((ArtistObject) body).getArtist();
        setArtistInfo(mainArtist, artistList);

        //Loading of similar artists
        Similar similar = ((ArtistObject) body).getArtist().getSimilar();
        similar.getArtist();
        for (Artist_ artist : similar.getArtist()) {
            setArtistInfo(artist, artistList);
        }
        return artistList;
    }

    private static void setArtistInfo(Object body, ArrayList<ArtistEntity> artistList) {
        String name = "";
        String url = "";
        if (body instanceof Artist) {
            Artist artist = (Artist) body;
            name = artist.getName();
            List<Image> imageList = artist.getImage();
            for (Image image : imageList) {
                if (image.getSize().equals("medium")) {
                    url = image.getText();
                    break;
                }
            }
        } else if (body instanceof Artist_) {
            Artist_ artist = (Artist_) body;
            name = artist.getName();
            List<Image_> imageList = artist.getImage();
            for (Image_ image : imageList) {
                if (image.getSize().equals("medium")) {
                    url = image.getText();
                    break;
                }
            }
        }
        artistList.add(new ArtistEntity(name, url));
    }


    /**
     * Search top albums
     */
    public void fetchTopAlbums(Fragment fragment, String artistName) {
        mListener = (DataInterface) fragment;
        Retrofit retrofit = getRetrofitClient();
        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);

        Call<TopAlbumObject> call = retrofitClient.getTopAlbums(
                "artist.gettopalbums",
                artistName,
                API_KEY,
                "json");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of ArtistEntity POJO class
                 */
                if (response.body() != null) {
                    ArrayList<AlbumEntity> artistData = parseAlbumData(response);
                    mListener.responseAlbumData(artistData);
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                /*
                Error callback
                */
                Request request = call.request();
            }
        });
    }

    private static ArrayList<AlbumEntity> parseAlbumData(Response response){
        Object body = response.body();
        ArrayList<AlbumEntity> artistList = new ArrayList<>();
        Topalbums topalbums = ((TopAlbumObject) body).getTopalbums();
        setAlbumInfo(topalbums, artistList);
        return artistList;
    }

    private static void setAlbumInfo(Object body, ArrayList<AlbumEntity> albumList) {
        String url = "";
        String albumName = "";
        String name = "";
        String tracks = "";

        Topalbums topalbums = (Topalbums) body;
        List<Album> albumsList = topalbums.getAlbum();
        for (Album album : albumsList) {
            albumName = album.getName();
            name = album.getArtist().getName();

            List<Image> imageList = album.getImage();
            for (Image image : imageList) {
                if (image.getSize().equals("large")) {
                    url = image.getText();
                    break;
                }
            }

            albumList.add(new AlbumEntity(albumName, name, url, tracks));
        }
    }

    public void fetchTracks(Fragment fragment, AlbumEntity albumEntity) {
        mListener = (DataInterface) fragment;
        String albumName = albumEntity.getName();
        String name = albumEntity.getArtistname();
        Retrofit retrofit = getRetrofitClient();
        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);

        Call<TrackObject> call = retrofitClient.getTracks(
                "album.getinfo",
                name,
                albumName,
                API_KEY,
                "json");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of ArtistEntity POJO class
                 */
                if (response.body() != null) {
                    ArrayList<TrackEntity> tracksData = parseTracksData(response);
                    mListener.responseTrackData(tracksData);
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                /*
                Error callback
                */
                Request request = call.request();
            }
        });
    }

    private static ArrayList<TrackEntity> parseTracksData(Response response) {
        ArrayList<TrackEntity> trackList = new ArrayList<>();
        TrackObject trackObject = (TrackObject) response.body();
        AlbumTrack album = trackObject.getAlbum();
        List<Track> tracksObject = album.getTracks().getTrack();
        for (Track track : tracksObject) {
            setTracks(trackList, track);
        }
        return trackList;
    }


    private static void setTracks(ArrayList<TrackEntity> trackList, Track track) {
        trackList.add(new TrackEntity(track.getName(), track.getDuration()));
    }
}
