package com.example.andreafranco.musicmanagementapp.ui.component;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreafranco.musicmanagementapp.AppExecutors;
import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.ui.TopAlbumFragment;
import com.example.andreafranco.musicmanagementapp.viewmodel.AlbumListViewModel;
import com.example.andreafranco.musicmanagementapp.viewmodel.AlbumViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TopAlbumRecycleViewAdapter extends RecyclerView.Adapter<TopAlbumRecycleViewAdapter.AlbumViewHolder> {
    private static final String LOG_TAG = TopAlbumRecycleViewAdapter.class.getSimpleName();
    private List<AlbumEntity> mAlbumList;
    private Context mContext;
    private Fragment mParent;
    OnAlbumIterationListener mListener;

    public interface OnAlbumIterationListener {
        void onAlbumSelected(AlbumEntity album, Pair<View, String>... p);
    }

    public TopAlbumRecycleViewAdapter(List<AlbumEntity> albumList, Context context, Fragment parent, OnAlbumIterationListener listener) {
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
            new AppExecutors().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                        Picasso.with(mContext).
                                load(album.getImage()).
                                into(mAlbumPictureImageVIew);
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
            /*switch (v.getId()) {
                case R.id.action_image_button:
                    if (v.getTag() != null && v.getTag().equals(STORED)) {
                        mAlbumViewModel.deleteAlbum(mAlbum);
                    } else {
                        mAlbumViewModel.insertAlbum(mAlbum);
                    }
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }*/
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