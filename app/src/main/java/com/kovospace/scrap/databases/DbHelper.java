package com.kovospace.scrap.databases;

import android.content.Context;
import com.kovospace.scrap.helpers.Settings;
import com.kovospace.scrap.bands.sources.BandItem;
import com.kovospace.scrap.objects.Band;
import com.kovospace.scrap.objects.Track;

import java.util.List;

public abstract class DbHelper {
    protected static Context context;
    protected static OfflineBandsRoomDatabase offlineBandsRoomDatabase;
    protected static OfflineTracksRoomDatabase offlineTracksRoomDatabase;

    public static void init(Context c) {
        context = c;
        offlineBandsRoomDatabase = OfflineBandsRoomDatabase.getInstance(context);
        offlineTracksRoomDatabase = OfflineTracksRoomDatabase.getInstance(context);
    }

    public static void rememberBandAndTracksForOffline(List<BandItem> bandItem) {
        boolean downloadedAtLeastOne = false;
        Band band = (Band) bandItem.get(0);

        for (int i = 1; i < bandItem.size(); i++) {
            if (((Track) bandItem.get(i)).hasOfflineCopy()) {
                downloadedAtLeastOne = true;
                break;
            }
        }
        if (downloadedAtLeastOne) {
            //BandsDbHelper.insertIfNotExist(band);
            for (int i = 1; i < bandItem.size(); i++) {
                TracksDbHelper.insertBandTrackIfNotExist((Track) bandItem.get(i));
            }
        } else {
            BandsDbHelper.delete(band.getSlug());
            TracksDbHelper.removeBandTracks(band);
            Settings.sendBandDowloadsRemoved(band);
        }
        // ^ do buducnosti rerobit, v niektorom vyuziti
        // tohoto adaptera bude moct byt viac kapiel mozno / nemusi byt pozicia 0
    }

    public static void deleteAllDatabases() {
        offlineBandsRoomDatabase.bandEntityDao().deleteAll();
        offlineTracksRoomDatabase.trackEntityDao().deleteAll();
    }
}
