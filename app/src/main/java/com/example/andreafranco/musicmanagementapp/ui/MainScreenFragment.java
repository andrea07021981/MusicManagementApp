package com.example.andreafranco.musicmanagementapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.ui.component.AlbumRecycleViewAdapter;
import com.example.andreafranco.musicmanagementapp.ui.component.SpaceItemDecoration;
import com.example.andreafranco.musicmanagementapp.viewmodel.AlbumListViewModel;

import java.util.ArrayList;

public class MainScreenFragment extends Fragment implements AlbumRecycleViewAdapter.OnAlbumIterationListener {

    private MainScreenFragmentInteractionListener mListener;
    private AlbumListViewModel mAlbumViewModel;
    private RecyclerView mAlbumRecyclerView;

    public MainScreenFragment() {
        // Required empty public constructor
    }

    public static MainScreenFragment newInstance() {
        return new MainScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_main_screen, container, false);

        // initialize views and variables
        initView(rootView);
        AlbumListViewModel.Factory factory = new AlbumListViewModel.Factory(getActivity().getApplication());
        mAlbumViewModel = ViewModelProviders.of(this, factory).get(AlbumListViewModel.class);
        subscribeToModel(mAlbumViewModel);

        return rootView;
    }

    private void initView(View rootView) {
        mAlbumRecyclerView = rootView.findViewById(R.id.albumlist_recycleview);
        mAlbumRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlbumRecycleViewAdapter teamRecycleViewAdapter = new AlbumRecycleViewAdapter(new ArrayList<AlbumEntity>(), getActivity(), this);
        mAlbumRecyclerView.setAdapter(teamRecycleViewAdapter);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        mAlbumRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainScreenFragmentInteractionListener) {
            mListener = (MainScreenFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MainScreenFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void subscribeToModel(final AlbumListViewModel model) {
        model.getAlbums().observe(this, albumEntities -> {
            ((AlbumRecycleViewAdapter) mAlbumRecyclerView.getAdapter()).addAll(albumEntities);
        });
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
        args.putParcelable(TopAlbumFragment.ALBUM_DETAIL, album);
        intent.putExtras(args);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), p[0], p[1], p[2]);
        startActivity(intent, options.toBundle());
    }

    public interface MainScreenFragmentInteractionListener {
        void onFragmentInteraction(AlbumEntity album);
    }
}
