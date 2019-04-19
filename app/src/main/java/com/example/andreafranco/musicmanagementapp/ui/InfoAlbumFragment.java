package com.example.andreafranco.musicmanagementapp.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.andreafranco.musicmanagementapp.BasicApp;
import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.ui.component.ArtistRecycleViewAdapter;
import com.example.andreafranco.musicmanagementapp.util.NetworkUtils;
import com.example.andreafranco.musicmanagementapp.viewmodel.AlbumViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InfoAlbumFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String STORED = "stored";

    private AlbumEntity mAlbum;
    private TextView mAlbumNameTextView;
    private TextView mArtistNameTextView;
    private ListView mTracksListView;
    private ImageButton mActionImageButton;
    private AlbumViewModel mAlbumViewModel;

    public InfoAlbumFragment() {
        // Required empty public constructor
    }

    /**
     * @param album
     */
    public static InfoAlbumFragment newInstance(AlbumEntity album) {
        InfoAlbumFragment fragment = new InfoAlbumFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbum = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info_album, container, false);
        initView(rootView);
        initValues(rootView);
        return rootView;
    }

    private void initView(final View rootView) {
        mAlbumNameTextView = rootView.findViewById(R.id.album_name_textview);
        mArtistNameTextView = rootView.findViewById(R.id.artist_name_textview);
        mTracksListView = rootView.findViewById(R.id.tracks_list_view);
        mActionImageButton = rootView.findViewById(R.id.action_image_button);
        mActionImageButton.setOnClickListener(this::updateLocalData);
        ImageView albumPictureImageVIew = rootView.findViewById(R.id.album_picture_imageview);
        ((BasicApp) getActivity().getApplicationContext()).getExecutor().mainThread().execute(() -> {
            if (mAlbum.getImageurl() != null &&
                    !TextUtils.isEmpty(mAlbum.getImageurl())) {
                Picasso.with(getActivity()).
                        load(mAlbum.getImageurl()).
                        into(albumPictureImageVIew);
            }
        });
    }

    private void updateLocalData(View view) {
        //TODO da salvare/rimuovere
    }

    private void initValues(View view) {
        mAlbumNameTextView.setText(mAlbum.getName());
        mArtistNameTextView.setText(mAlbum.getArtistname());
        setArrayTracks();
        AlbumViewModel.Factory factory = new AlbumViewModel.Factory(getActivity().getApplication(), mAlbum.getName());
        mAlbumViewModel = ViewModelProviders.of(this, factory).get(AlbumViewModel.class);
        mAlbumViewModel.getAlbum().observe(this, albumEntity -> {
            if (albumEntity != null) {
                mActionImageButton.setImageDrawable(getActivity().getDrawable(android.R.drawable.ic_delete));
                mActionImageButton.setTag(STORED);
            } else {
                mActionImageButton.setImageDrawable(getActivity().getDrawable(android.R.drawable.ic_menu_save));
                mActionImageButton.setTag(null);
            }
        });
    }

    private void setArrayTracks() {
        String tracks = mAlbum.getTracks();
        String[] splitedList = tracks.split("@");
        ArrayList<Map<String,String>> itemDataList = new ArrayList<Map<String,String>>();
        for (int i=0; i<splitedList.length; i++) {
            Map<String,String> listItemMap = new HashMap<String,String>();
            String[] splitedTrackDuration = splitedList[i].split("-");
            listItemMap.put("title", splitedTrackDuration[0]);
            listItemMap.put("description", splitedTrackDuration[1]);
            itemDataList.add(listItemMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),itemDataList,android.R.layout.simple_list_item_2,
                new String[]{"title","description"},new int[]{android.R.id.text1,android.R.id.text2});
        mTracksListView.setAdapter(simpleAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
