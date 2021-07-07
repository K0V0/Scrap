package com.kovospace.scrap;

import android.app.Application;
import com.kovospace.scrap.databases.DbHelper;
import com.kovospace.scrap.helpers.Settings;

public class Main extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Settings.init(this);
        DbHelper.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
