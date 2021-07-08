package com.kovospace.scrap.bands.sources;

public interface BandItem {
    int TYPE_TRACK = 1;
    int TYPE_BAND = -1;
    boolean contains(BandItem o);
    String getLocalOrHref();
    /*boolean hasOfflineCopy();
    boolean isAvailableOffline();*/
}
