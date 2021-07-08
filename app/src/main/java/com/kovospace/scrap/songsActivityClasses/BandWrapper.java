package com.kovospace.scrap.songsActivityClasses;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kovospace.scrap.R;
import com.kovospace.scrap.databases.DbHelper;
import com.kovospace.scrap.bands.sources.BandItem;
import com.kovospace.scrap.objects.Band;
import com.kovospace.scrap.objects.Track;

import java.util.ArrayList;
import java.util.List;

public abstract class BandWrapper {
    protected Activity activity;
    protected Context context;
    protected String extra;

    protected Band band;
    protected List<Track> tracks;
    protected List<BandItem> bandItems;
    protected Mp3File mp3File;
    protected ImageFile imageFile;

    protected RecyclerView tracksRecyclerView;
    protected RecyclerView.Adapter tracksAdapter;
    protected RecyclerView.LayoutManager tracksLayoutManager;
    protected int dataSourceType;
    protected TextView noTracksText;

    public BandWrapper() {}

    public BandWrapper(Activity activity, Context context, String extra) {
        this.activity = activity;
        this.context = context;
        this.extra = extra;
        inits();
        loadStaticUI();
        loadTracksUI();
    }

    private void inits() {
        //this.dataSourceType = setDataSourceType();
        this.tracks = new ArrayList<>();
        this.bandItems = new ArrayList<>();
        this.mp3File = new Mp3File(this.context);
        this.imageFile = new ImageFile(this.context);
    }

    private void loadStaticUI() {
        this.noTracksText = activity.findViewById(R.id.noTracksText);
    }

    private void loadTracksUI() {
        this.tracksRecyclerView = this.activity.findViewById(R.id.songsList);
        this.tracksLayoutManager = new LinearLayoutManager(activity);
        this.tracksRecyclerView.setLayoutManager(tracksLayoutManager);
        this.tracksRecyclerView.setHasFixedSize(true);
        this.tracksAdapter = new TracksAdapter(this.context, bandItems);
        this.tracksRecyclerView.setAdapter(tracksAdapter);
        //this.dataSourceType = setDataSourceType();
    }

    private List<Track> addExtraData(List<Track> trackList) {
        int i = 0;
        for (Track track : trackList) {
            track.setOrder(i);
            track.setBandName(band.getTitle());
            track.setBandSlug(band.getSlug());
            track.setTrackFullLocalPath(mp3File);
            track.hasOfflineCopy();
            track.convertDuration();
            i++;
        }
        return trackList;
    }

    private void noTracksMessage() {
        int trackCount = 0;
        for (BandItem item : bandItems) {
            if (item.getClass() == Track.class) {
                trackCount++;
            }
        }
        if (trackCount == 0) {
            noTracksText.setVisibility(View.VISIBLE);
        }
    }

    public void triggerShow() {
        // runs after data retrieved from wrapper (JSON or local)
        bandItems.clear();
        band.setImageFullLocalPath(imageFile);
        //band.hasOfflineCopy();
        bandItems.add(band);
        bandItems.addAll(addExtraData(tracks));
        DbHelper.rememberBandAndTracksForOffline(bandItems);
        tracksAdapter.notifyItemInserted(bandItems.size() - 1);
        noTracksMessage();
    }

}
