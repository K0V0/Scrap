package com.kovospace.scrap.bands.sources;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kovospace.scrap.bands.BandsService;
import com.kovospace.scrap.helpers.JsonRequest;
import java.lang.reflect.Type;
import org.json.JSONObject;

public  class BandsSourceOnline
        implements BandsSource
{
  private final String QUERY_URL = "http://172.104.155.216:4000/bandzone/bands?q=";
  private final Context context;
  private BandsService bandsService;

  public BandsSourceOnline(Context context, BandsService bandsService) {
    this.context = context;
    this.bandsService = bandsService;
  }

  @Override
  public void fetch(String searchString) {
    this.doJsonRequest(QUERY_URL + searchString);
  }

  @Override
  public void fetch(String searchString, int pageNum) {
    this.doJsonRequest(QUERY_URL + searchString + "&p=" + pageNum);
  }

  private void doJsonRequest(String query) {
    new JsonRequest(this.context, query) {
      @Override
      public void doStuff(JSONObject response) {
        Type bandsListType = new TypeToken<BandsDTO>(){}.getType();
        BandsDTO bandsDTO = new Gson().fromJson(String.valueOf(response), bandsListType);
        bandsService.render(bandsDTO);
      }
    };
  }
}
