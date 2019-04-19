package com.example.andreafranco.musicmanagementapp.ui.component;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andreafranco.musicmanagementapp.BasicApp;
import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.viewmodel.AlbumViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopAlbumRecycleViewAdapter extends RecyclerView.Adapter<TopAlbumRecycleViewAdapter.AlbumViewHolder> {
    private static final String LOG_TAG = TopAlbumRecycleViewAdapter.class.getSimpleName();
    private List<AlbumEntity> mAlbumList;
    private Context mContext;
    private Fragment mParent;
    OnTopAlbumIterationListener mListener;

    public interface OnTopAlbumIterationListener {
        void onAlbumSelected(AlbumEntity album, Pair<View, String>... p);
    }

    public TopAlbumRecycleViewAdapter(List<AlbumEntity> albumList, Context context, Fragment parent, OnTopAlbumIterationListener listener) {
        mAlbumList = albumList;
        mContext = context;
        mListener = listener;
        mParent = parent;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        AlbumEntity album = mAlbumList.get(position);
        holder.bindTeamVierwHolder(album);
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_album_item_view, parent, false);
        return new AlbumViewHolder(v);
    }

    /**
     * View holder class
     */
    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Object STORED = "stored";
        ImageView mAlbumPictureImageVIew;
        AlbumEntity mAlbum;
        private AlbumViewModel mAlbumViewModel;

        public AlbumViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mAlbumPictureImageVIew = (ImageView) view.findViewById(R.id.album_picture_imageview);
        }

        public void bindTeamVierwHolder(AlbumEntity album) {
            mAlbum = album;
            ((BasicApp) mContext.getApplicationContext()).getExecutor().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    if (album.getImageurl() != null &&
                            !TextUtils.isEmpty(album.getImageurl())) {
                        Picasso.with(mContext).
                                load(album.getImageurl()).
                                into(mAlbumPictureImageVIew);
                    }
                }
            });

            String albumFormat = mContext.getString(R.string.format_album);
            /*mAlbumNameTextView.setText(String.format(albumFormat, album.getName()));
            String artistFormat = mContext.getString(R.string.format_artist);
            mArtistNameTextView.setText(String.format(artistFormat, album.getArtistname()));
            AlbumViewModel.Factory factory = new AlbumViewModel.Factory(((Activity) mContext).getApplication(), album.getName());
            mAlbumViewModel = ViewModelProviders.of(mParent, factory).get(AlbumViewModel.class);
            mAlbumViewModel.getAlbum().observe(mParent, albumEntity -> {
                if (albumEntity != null) {
                    mActionImageButton.setImageDrawable(mContext.getDrawable(android.R.drawable.ic_delete));
                    mActionImageButton.setTag(STORED);
                } else {
                    mActionImageButton.setImageDrawable(mContext.getDrawable(android.R.drawable.ic_menu_save));
                    mActionImageButton.setTag(null);
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            Pair<View, String> p1 = Pair.create(mAlbumPictureImageVIew, "albumpicturedetail");
            mListener.onAlbumSelected(mAlbum, p1);
        }
    }

    /**
     * Clears all items from the data set.
     */
    public void clear() {
        if (mAlbumList != null) {
            mAlbumList.clear();
            notifyDataSetChanged();
        }
    }

    /*
     * Adds all of the items to the data set.
     * @param items The item array to be added.
     */
    public void addAll(List<AlbumEntity> items) {
        if (mAlbumList == null) {
            mAlbumList = new ArrayList<>();
        }
        if (items != null) {
            mAlbumList.addAll(items);
        }
        notifyDataSetChanged();
    }
}