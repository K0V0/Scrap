package com.kovospace.scrap.songsActivityClasses;

import android.content.Context;
import com.kovospace.scrap.helpers.FileStorage;

public class Mp3File extends FileStorage {

    public Mp3File(Context context) {
        super(context);
        createDirIfNotExist("music");
        setWorkingDirectory("music");
    }

}
