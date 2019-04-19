package com.example.andreafranco.musicmanagementapp.ui.component;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class SpaceItemDecoration  extends RecyclerView.ItemDecoration {

    private final int mSpace;

    public SpaceItemDecoration(int mSpace) {
        this.mSpace = mSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

        // Add top margin only for the first items to avoid double space between items
        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager &&
                parent.getChildAdapterPosition(view) <= ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount())
            outRect.top = mSpace;
    }
}
