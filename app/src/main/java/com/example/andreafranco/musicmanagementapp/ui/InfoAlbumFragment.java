package com.example.andreafranco.musicmanagementapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreafranco.musicmanagementapp.BasicApp;
import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.TrackEntity;
import com.example.andreafranco.musicmanagementapp.ui.component.TrackRecycleViewAdapter;
import com.example.andreafranco.musicmanagementapp.util.HttpUtils;
import com.example.andreafranco.musicmanagementapp.viewmodel.AlbumListViewModel;
import com.example.andreafranco.musicmanagementapp.viewmodel.AlbumViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InfoAlbumFragment extends DialogFragment implements HttpUtils.DataInterface, TrackRecycleViewAdapter.OnTrackIterationListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String STORED = "stored";

    private OnInfoAlbumFragmentInteractionListener mListener;
    private AlbumEntity mAlbum;
    private TextView mAlbumNameTextView;
    private TextView mArtistNameTextView;
    private RecyclerView mTracksRecycleView;
    private ImageButton mActionImageButton;
    private AlbumViewModel mAlbumViewModel;
    private AlbumListViewModel mAlbumListViewModel;
    private ArrayList<TrackEntity> mTrackArrayList;
    private TrackRecycleViewAdapter mTrackRecycleViewAdapter;

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
        mTrackArrayList = new ArrayList<>();
        mAlbumNameTextView = rootView.findViewById(R.id.album_name_textview);
        mArtistNameTextView = rootView.findViewById(R.id.artist_name_textview);
        mTracksRecycleView = rootView.findViewById(R.id.tracks_recycle_view);
        mTracksRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTrackRecycleViewAdapter = new TrackRecycleViewAdapter(mTrackArrayList, getActivity(), this);
        mTracksRecycleView.setAdapter(mTrackRecycleViewAdapter);
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
        if (view.getTag() != null) {
            mAlbumViewModel.deleteAlbum(mAlbum);
        } else {
            mAlbumViewModel.insertAlbum(mAlbum, mTrackArrayList);
        }
        mListener.onInfoAlbumFragmentInteraction();
    }

    private void initValues(View view) {
        mAlbumNameTextView.setText(mAlbum.getName());
        mArtistNameTextView.setText(mAlbum.getArtistname());
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
        HttpUtils.getInstance().fetchTracks(this, mAlbum);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInfoAlbumFragmentInteractionListener) {
            mListener = (OnInfoAlbumFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInfoAlbumFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void responseArtistData(ArrayList<ArtistEntity> artists) {

    }

    @Override
    public void responseAlbumData(ArrayList<AlbumEntity> albums) {

    }

    @Override
    public void responseTrackData(ArrayList<TrackEntity> tracks) {
        if (tracks != null && tracks.size() != 0) {
            mTrackRecycleViewAdapter.clear();
            mTrackRecycleViewAdapter.addAll(tracks);
        }
        mTrackArrayList = tracks;
    }

    @Override
    public void onTrackSelected() {

    }

    public interface OnInfoAlbumFragmentInteractionListener {
        void onInfoAlbumFragmentInteraction();
    }
}
