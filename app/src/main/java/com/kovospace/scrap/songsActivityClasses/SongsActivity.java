package com.kovospace.scrap.songsActivityClasses;

import android.content.Context;
import android.os.Bundle;
import com.downloader.PRDownloader;
import com.kovospace.scrap.appBase.Activity;
import com.kovospace.scrap.R;

public class SongsActivity extends Activity {
    public String slug;
    private BandWrapper bandWrapper;
    private Context context;

    @Override
    protected void onNetworkChanged() {
        if (connectionTest.isConnectionChanged()) {
            loadSongs(this.context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_songs);
        slug = getIntent().getStringExtra("slug");
        PRDownloader.initialize(getApplicationContext());
        loadSongs(this);
    }

    protected void onResume() {
        super.onResume();
    }

    private void loadSongs(Context context) {
        if (connectionTest.isConnectionAvailable()) {
            bandWrapper = new BandWrapperNet(SongsActivity.this, context, slug);
        } else {
            bandWrapper = new BandWrapperOffline(SongsActivity.this, context, slug);
        }
    }
}