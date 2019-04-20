package com.example.andreafranco.musicmanagementapp.ui.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andreafranco.musicmanagementapp.R;
import com.example.andreafranco.musicmanagementapp.local.entity.TrackEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrackRecycleViewAdapter extends RecyclerView.Adapter<TrackRecycleViewAdapter.TrackViewHolder> {
    private List<TrackEntity> mTrackList;
    private Context mContext;
    OnTrackIterationListener mListener;

    public interface OnTrackIterationListener {
        void onTrackSelected();
    }

    public TrackRecycleViewAdapter(List<TrackEntity> trackList, Context context, OnTrackIterationListener listener) {
        mTrackList = trackList;
        mContext = context;
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        TrackEntity track = mTrackList.get(position);
        holder.bindTeamVierwHolder(track);
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item_view, parent, false);
        return new TrackViewHolder(v);
    }

    /**
     * View holder class
     */
    public class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mNumberTextView;
        TextView mTitleTextView;
        TextView mDurationTextView;
        TrackEntity mTrack;

        public TrackViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mNumberTextView = (TextView) view.findViewById(R.id.number_textview);
            mTitleTextView = (TextView) view.findViewById(R.id.title_textview);
            mDurationTextView = (TextView) view.findViewById(R.id.duration_textview);
        }

        public void bindTeamVierwHolder(TrackEntity track) {
            mTrack = track;
            mNumberTextView.setText(String.valueOf(getAdapterPosition()));
            mTitleTextView.setText(track.getName());
            mDurationTextView.setText(convertTime(track.getDuration()));
        }

        private String convertTime(String duration) {
            String artistFormat = mContext.getString(R.string.format_time);
            long timeInMillis = Long.parseLong(duration) * 1000;
            long minutes = timeInMillis / (60 * 1000);
            long seconds = (timeInMillis / 1000) % 60;
            return String.format(artistFormat, minutes, seconds);
        }

        @Override
        public void onClick(View v) {
        }
    }

    /**
     * Clears all items from the data set.
     */
    public void clear() {
        if (mTrackList != null) {
            mTrackList.clear();
            notifyDataSetChanged();
        }
    }

    /*
     * Adds all of the items to the data set.
     * @param items The item array to be added.
     */
    public void addAll(List<TrackEntity> items) {
        if (mTrackList == null) {
            mTrackList = new ArrayList<>();
        }
        if (items != null) {
            mTrackList.addAll(items);
        }
        notifyDataSetChanged();
    }
}