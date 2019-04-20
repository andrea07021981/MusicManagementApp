package com.example.andreafranco.musicmanagementapp.ui.component;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreafranco.musicmanagementapp.BasicApp;
import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.ArtistEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArtistRecycleViewAdapter extends RecyclerView.Adapter<ArtistRecycleViewAdapter.ArtistListHolder>{
    private final Context mContext;
    private List<ArtistEntity> mArtistList;
    OnArtistIterationListener mListener;

    public interface OnArtistIterationListener {
        void onArtistSelected(ArtistEntity mArtist);
    }

    /**
     * View holder class
     * */
    public class ArtistListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageImageVIew;
        TextView mArtistName;
        ArtistEntity mArtist;

        public ArtistListHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageImageVIew = (ImageView) view.findViewById(R.id.artist_picture_imageview);
            mArtistName = (TextView) view.findViewById(R.id.artist_name_textview);
        }

        public void bindImage(ArtistEntity artist) {
            mArtist = artist;
            ((BasicApp) mContext.getApplicationContext()).getExecutor().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    if (artist.getImage() != null &&
                            !TextUtils.isEmpty(artist.getImage())) {
                        Picasso.with(mContext).
                                load(artist.getImage()).
                                into(mImageImageVIew);
                    }
                }
            });
            mArtistName.setText(mArtist.getName());
        }

        @Override
        public void onClick(View v) {
            mListener.onArtistSelected(mArtist);
        }
    }

    public ArtistRecycleViewAdapter(List<ArtistEntity> artistList, Context context, OnArtistIterationListener listener) {
        mArtistList = artistList;
        mListener = listener;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ArtistListHolder holder, int position) {
        ArtistEntity image = mArtistList.get(position);
        holder.bindImage(image);
    }

    @Override
    public int getItemCount() {
        return mArtistList.size();
    }

    @Override
    public ArtistListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_item_view,parent, false);
        return new ArtistListHolder(v);
    }

    /**
     * Clears all items from the data set.
     */
    public void clear(){
        if(mArtistList != null){
            mArtistList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Adds all of the items to the data set.
     * @param items The item array to be added.
     */
    public void addAll(List<ArtistEntity> items){
        if(mArtistList == null){
            mArtistList = new ArrayList<>();
        }
        mArtistList.addAll(items);
        notifyDataSetChanged();
    }
}
