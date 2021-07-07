package com.kovospace.scrap.bands;

import android.content.Context;

public class BandsService {
  private Context context;

  public BandsService(Context context) {
    this.context = context;
  }

  public void search(String searchString) {
    System.out.println("-- searchString -----------------------");
    System.out.println(searchString);
  }

}
