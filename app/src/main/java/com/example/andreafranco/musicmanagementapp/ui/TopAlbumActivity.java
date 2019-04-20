package com.example.andreafranco.musicmanagementapp.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import static com.example.andreafranco.musicmanagementapp.ui.SearchFragment.ARTIST_SELECTED;

public class TopAlbumActivity extends SingleFragmentActivity implements TopAlbumFragment.OnTopAlbumFragmentInteractionListener{

    private String mArtistSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mArtistSelected = getIntent().getStringExtra(ARTIST_SELECTED);
        }
        super.onCreate(savedInstanceState);
        setupToolbar(true);
    }

    @Override
    protected Fragment createFragment() {
        return TopAlbumFragment.newInstance(mArtistSelected);
    }

    @Override
    protected void setupToolbar(boolean visible) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        getSupportActionBar().setDisplayShowHomeEnabled(visible);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTopAlbumFragmentInteraction() {

    }
}
