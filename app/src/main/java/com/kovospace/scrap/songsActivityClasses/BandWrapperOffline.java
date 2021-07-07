package com.kovospace.scrap.songsActivityClasses;

import android.app.Activity;
import android.content.Context;
import com.kovospace.scrap.databases.BandEntity;
import com.kovospace.scrap.databases.BandsDbHelper;
import com.kovospace.scrap.databases.TrackEntity;
import com.kovospace.scrap.databases.TracksDbHelper;
import com.kovospace.scrap.objects.Band;
import com.kovospace.scrap.objects.Track;

import java.util.List;

public class BandWrapperOffline extends BandWrapper {
    private BandEntity bandEntity;
    private List<TrackEntity> trackEntities;

    @Override
    public int setDataSourceType() {
        return DATA_SOURCE_LOCAL;
    }

    public BandWrapperOffline(Activity activity, Context context, String extra) {
        super(activity, context, extra);
        populateData();
        applyData();
        triggerShow();
    }

    private void populateData() {
        bandEntity = BandsDbHelper.findFirstBySlug(extra);
        trackEntities = TracksDbHelper.getBandTracks(extra);
    }

    private void applyData() {
        band = new Band(
                bandEntity.getTitle(),
                bandEntity.getCity(),
                bandEntity.getImage_url(),
                bandEntity.getHref(),
                extra,
                bandEntity.getGenre(),
                "bandzone"
        );
        for (TrackEntity e : trackEntities) {
            tracks.add(new Track(
                    e.getFullTitle(),
                    e.getTitle(),
                    e.getAlbum(),
                    e.getPlays_count(),
                    e.getHref(),
                    e.getHrefHash(),
                    e.getDuration()
            ));
        }
    }
}
