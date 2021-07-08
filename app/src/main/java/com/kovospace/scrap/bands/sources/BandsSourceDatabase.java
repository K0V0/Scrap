package com.kovospace.scrap.bands.sources;

import android.content.Context;

public  class BandsSourceDatabase
        implements BandsSource
{
  private final Context context;

  public BandsSourceDatabase(Context context) {
    this.context = context;
  }

  @Override
  public void fetch(String searchString) {

  }

  @Override
  public void fetch(String searchString, int pageNum) {

  }
}
