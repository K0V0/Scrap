package com.kovospace.scrap.eventBus;

public class ReloadBandsList {
    public boolean reload;

    public ReloadBandsList(boolean reload) {
        this.reload = reload;
    }

    public boolean isReload() {
        return reload;
    }
}
