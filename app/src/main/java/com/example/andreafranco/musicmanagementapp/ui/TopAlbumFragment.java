package com.example.andreafranco.musicmanagementapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.TrackEntity;
import com.example.andreafranco.musicmanagementapp.ui.component.SpaceItemDecoration;
import com.example.andreafranco.musicmanagementapp.ui.component.TopAlbumRecycleViewAdapter;
import com.example.andreafranco.musicmanagementapp.util.HttpUtils;

import java.util.ArrayList;


public class TopAlbumFragment extends Fragment implements TopAlbumRecycleViewAdapter.OnTopAlbumIterationListener, HttpUtils.DataInterface {

    private static final String ARG_PARAM1 = "param1";
    public static final String ALBUM_DETAIL = "album_detail";

    private OnTopAlbumFragmentInteractionListener mListener;
    private String mArtistName;
    private RecyclerView mTopAlbumRecyclerView;
    ProgressBar mWaitProgressBar = null;
    private TopAlbumRecycleViewAdapter mTeamRecycleViewAdapter;
    private ArrayList<AlbumEntity> mAlbumArrayList;

    public TopAlbumFragment() {
        // Required empty public constructor
    }

    public static TopAlbumFragment newInstance(String artist) {
        TopAlbumFragment fragment = new TopAlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArtistName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_album, container, false);
        initView(rootView);
        HttpUtils.getInstance().fetchTopAlbums(this, mArtistName);
        return rootView;
    }

    private void initView(View rootView) {
        mAlbumArrayList = new ArrayList<>();
        mTopAlbumRecyclerView = rootView.findViewById(R.id.topalbumlist_recycleview);
        mTopAlbumRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mTeamRecycleViewAdapter = new TopAlbumRecycleViewAdapter(mAlbumArrayList, getActivity(), this, this);
        mTopAlbumRecyclerView.setAdapter(mTeamRecycleViewAdapter);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        mTopAlbumRecyclerView.addItemDecoration(decoration);
        mWaitProgressBar = rootView.findViewById(R.id.wait_progress_bar);
        mWaitProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTopAlbumFragmentInteractionListener) {
            mListener = (OnTopAlbumFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAlbumSelected(AlbumEntity album, Pair<View, String>... p) {
        Intent intent = new Intent(getActivity(), InfoAlbumActivity.class);
        Bundle args = new Bundle();
        args.putParcelable(ALBUM_DETAIL, album);
        intent.putExtras(args);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), p[0]);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void responseArtistData(ArrayList<ArtistEntity> artists) {

    }

    @Override
    public void responseAlbumData(ArrayList<AlbumEntity> albums) {
        if (albums != null && albums.size() != 0) {
            mTeamRecycleViewAdapter.clear();
            mTeamRecycleViewAdapter.addAll(albums);
        }
        mAlbumArrayList = albums;
        mWaitProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void responseTrackData(ArrayList<TrackEntity> tracks) {

    }

    public interface OnTopAlbumFragmentInteractionListener {
        void onTopAlbumFragmentInteraction(Uri uri);
    }
}
