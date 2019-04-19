package com.example.andreafranco.musicmanagementapp.ui.component;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumRecycleViewAdapter extends RecyclerView.Adapter<AlbumRecycleViewAdapter.AlbumViewHolder> {
    private List<AlbumEntity> mAlbumList;
    private Context mContext;
    OnAlbumIterationListener mListener;

    public interface OnAlbumIterationListener {
        void onAlbumSelected(AlbumEntity album, Pair<View, String>... p);
    }

    public AlbumRecycleViewAdapter(List<AlbumEntity> albumList, Context context, OnAlbumIterationListener listener) {
        mAlbumList = albumList;
        mContext = context;
        mListener = listener;
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
                .inflate(R.layout.album_item_view, parent, false);
        return new AlbumViewHolder(v);
    }

    /**
     * View holder class
     */
    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mAlbumPictureImageVIew;
        TextView mAlbumNameTextView;
        TextView mArtistNameTextView;
        AlbumEntity mAlbum;

        public AlbumViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mAlbumPictureImageVIew = (ImageView) view.findViewById(R.id.album_picture_imageview);
            mAlbumNameTextView = (TextView) view.findViewById(R.id.artist_name_textview);
            mArtistNameTextView = (TextView) view.findViewById(R.id.artist_name_textview);
        }

        public void bindTeamVierwHolder(AlbumEntity album) {
            mAlbum = album;
            mAlbumPictureImageVIew.setImageBitmap(BitmapFactory.decodeByteArray(mAlbum.getImage(), 0, mAlbum.getImage().length));
            String albumFormat = mContext.getString(R.string.format_album);
            mAlbumNameTextView.setText(String.format(albumFormat, album.getName()));
            String artistFormat = mContext.getString(R.string.format_artist);
            mArtistNameTextView.setText(String.format(artistFormat, album.getArtistname()));
        }

        @Override
        public void onClick(View v) {
            Pair<View, String> p1 = Pair.create(mAlbumPictureImageVIew, "albumpicture");
            Pair<View, String> p2 = Pair.create(mAlbumNameTextView, "albumname");
            Pair<View, String> p3 = Pair.create(mArtistNameTextView, "artistname");
            mListener.onAlbumSelected(mAlbum, p1, p2, p3);
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