package com.example.andreafranco.musicmanagementapp.ui;

import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andreafranco.musicmanagementapp.local.entity.AlbumEntity;
import com.example.andreafranco.musicmanagementapp.ui.InfoAlbumFragment;
import com.example.andreafranco.musicmanagementapp.ui.SingleFragmentActivity;

import static com.example.andreafranco.musicmanagementapp.ui.TopAlbumFragment.ALBUM_DETAIL;

public class InfoAlbumActivity extends SingleFragmentActivity implements InfoAlbumFragment.OnInfoAlbumFragmentInteractionListener {

    private AlbumEntity mAlbumSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAlbumSelected = extras.getParcelable(ALBUM_DETAIL);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return InfoAlbumFragment.newInstance(mAlbumSelected);
    }

    public void onBackPressed() {
        //This allows the previous activity to animate the shared components.
        //Prev activity must have windowContentTransitions = "true"
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void setupToolbar(boolean visible) {

    }

    @Override
    public void onInfoAlbumFragmentInteraction() {
        finish();
    }
}
