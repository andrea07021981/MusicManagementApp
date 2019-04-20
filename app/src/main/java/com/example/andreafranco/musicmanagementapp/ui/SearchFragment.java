package com.example.andreafranco.musicmanagementapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;
import com.example.andreafranco.musicmanagementapp.ui.component.ArtistRecycleViewAdapter;
import com.example.andreafranco.musicmanagementapp.util.HttpUtils;
import com.example.andreafranco.musicmanagementapp.util.NetworkUtils;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements
        ArtistRecycleViewAdapter.OnArtistIterationListener,
        HttpUtils.DataInterface {

    private static final String LOG_TAG = SearchFragment.class.getSimpleName();
    public static final String ARTIST_SELECTED = "artist_selected";

    private OnSearchFragmentInteractionListener mListener;

    EditText mSearchEditText = null;
    ImageButton mSearchImageButton = null;
    ProgressBar mWaitProgressBar = null;
    TextView mNoNetworkTextView = null;
    TextView mEmptyListTextView = null;
    RecyclerView mArtistRecyclerView = null;
    ArrayList<ArtistEntity> mArtistInfoArrayList;
    ArtistRecycleViewAdapter mArtistInfoAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // initialize views and variables
        initView(rootView);
        initValues(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchFragmentInteractionListener) {
            mListener = (OnSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onArtistSelected(ArtistEntity artist) {
        Intent intent = new Intent(getActivity(), TopAlbumActivity.class);
        intent.putExtra(ARTIST_SELECTED, artist.getName());
        startActivity(intent);
    }

    @Override
    public void responseArtistData(ArrayList<ArtistEntity> artists) {
        if (artists != null && artists.size() != 0) {
            mArtistInfoAdapter.clear();
            mArtistInfoAdapter.addAll(artists);
        }
        mWaitProgressBar.setVisibility(View.GONE);
        mArtistInfoArrayList = artists;
    }

    @Override
    public void responseAlbumData(ArrayList<AlbumEntity> albums) {

    }

    public interface OnSearchFragmentInteractionListener {
        void onSearchFragmentInteraction(Uri uri);
    }

    private void initView(final View rootView) {
        mSearchEditText = rootView.findViewById(R.id.search_edit_text);
        mSearchImageButton = rootView.findViewById(R.id.search_image_button);
        mWaitProgressBar = rootView.findViewById(R.id.wait_progress_bar);
        mNoNetworkTextView = rootView.findViewById(R.id.no_network_text_view);
        mEmptyListTextView = rootView.findViewById(R.id.empty_list_text_view);
        mArtistRecyclerView = rootView.findViewById(R.id.artist_recycler_view);
        mArtistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // on Button click
        mSearchImageButton.setOnClickListener(view -> {
            if (NetworkUtils.isNetworkAvailable(getContext())) {
                if (mNoNetworkTextView.isShown()) mNoNetworkTextView.setVisibility(View.GONE);

                mSearchEditText.clearFocus();
                if (!TextUtils.isEmpty(mSearchEditText.getText().toString().trim())) {
                    HttpUtils.fetchArtist(this, mSearchEditText.getText().toString());
                } else {
                    showToast(rootView, getString(R.string.nothing_entered));
                }
            } else {
                mNoNetworkTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initValues(View view) {
        mArtistInfoArrayList = new ArrayList<>();
        mArtistInfoAdapter = new ArtistRecycleViewAdapter(mArtistInfoArrayList, getActivity(),this);
        mArtistRecyclerView.setAdapter(mArtistInfoAdapter);
    }

    private void showToast(View view, String msg) {
        Toast.makeText(view.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
