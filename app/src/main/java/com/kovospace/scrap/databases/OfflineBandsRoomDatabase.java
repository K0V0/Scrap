package com.kovospace.scrap.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.kovospace.scrap.bands.sources.BandEntity;
import com.kovospace.scrap.bands.sources.BandsSourceDatabaseDAO;

@Database(entities = {BandEntity.class}, version = 4)
public abstract class OfflineBandsRoomDatabase extends RoomDatabase {

    public abstract BandsSourceDatabaseDAO bandEntityDao();

    private static OfflineBandsRoomDatabase offlineBandsDB;

    public static OfflineBandsRoomDatabase getInstance(Context context) {
        if (null == offlineBandsDB) {
            offlineBandsDB = buildDatabaseInstance(context);
        }
        return offlineBandsDB;
    }

    private static OfflineBandsRoomDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                OfflineBandsRoomDatabase.class,
                "offlineBands_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public void cleanUp(){
        offlineBandsDB = null;
    }

}
