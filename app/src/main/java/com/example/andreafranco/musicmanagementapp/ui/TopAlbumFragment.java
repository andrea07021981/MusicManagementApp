package com.example.andreafranco.musicmanagementapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.loader.AlbumLoader;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.ui.component.SpaceItemDecoration;
import com.example.andreafranco.musicmanagementapp.ui.component.TopAlbumRecycleViewAdapter;

import java.util.ArrayList;


public class TopAlbumFragment extends Fragment implements TopAlbumRecycleViewAdapter.OnAlbumIterationListener, LoaderManager.LoaderCallbacks<ArrayList<AlbumEntity>> {

    private static final String ARG_PARAM1 = "param1";
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
        getLoaderManager().initLoader(1, null, TopAlbumFragment.this);
        return rootView;
    }

    private void initView(View rootView) {
        mAlbumArrayList = new ArrayList<>();
        mTopAlbumRecyclerView = rootView.findViewById(R.id.topalbumlist_recycleview);
        mTopAlbumRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTeamRecycleViewAdapter = new TopAlbumRecycleViewAdapter(mAlbumArrayList, getActivity(), this);
        mTopAlbumRecyclerView.setAdapter(mTeamRecycleViewAdapter);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        mTopAlbumRecyclerView.addItemDecoration(decoration);
        mWaitProgressBar = rootView.findViewById(R.id.wait_progress_bar);
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

    }

    @NonNull
    @Override
    public Loader<ArrayList<AlbumEntity>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AlbumLoader(getContext(), mArtistName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<AlbumEntity>> loader, ArrayList<AlbumEntity> data) {
        if (data != null && data.size() != 0) {
            mTeamRecycleViewAdapter.clear();
            mTeamRecycleViewAdapter.addAll(data);
        }
        mAlbumArrayList = data;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<AlbumEntity>> loader) {
        mTeamRecycleViewAdapter.clear();
        mAlbumArrayList = null;
    }

    public interface OnTopAlbumFragmentInteractionListener {
        void onTopAlbumFragmentInteraction(Uri uri);
    }
}
