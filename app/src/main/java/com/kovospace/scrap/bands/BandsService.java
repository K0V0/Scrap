package com.kovospace.scrap.bands;

import android.content.Context;
import com.kovospace.scrap.appBase.utils.Connection;
import com.kovospace.scrap.bands.sources.BandsDTO;
import com.kovospace.scrap.bands.sources.BandsSource;
import com.kovospace.scrap.bands.sources.BandsSourceDatabase;
import com.kovospace.scrap.bands.sources.BandsSourceOnline;
import com.kovospace.scrap.bands.ui.BandsRecyclerViewController;
import com.kovospace.scrap.bands.ui.SearchFieldProgress;

public class BandsService
{
  private Context context;
  private BandsSource bandsSource;
  private Connection connection;
  private BandsRecyclerViewController listController;
  private String searchString;
  private boolean oldReplaced;

  public BandsService(Context context) {
    this.context = context;
    this.init();
  }

  protected void init() {
    this.connection = new Connection(context);
    this.listController = new BandsRecyclerViewController(context, this);
    SearchFieldProgress.init(context);
    this.chooseDataSource();
  }

  public void search(String searchString) {
    this.searchString = searchString;
    this.oldReplaced = true;
    SearchFieldProgress.start();
    bandsSource.fetch(searchString);
  }

  public void nextPage(int pageNum) {
    this.oldReplaced = false;
    bandsSource.fetch(searchString, pageNum);
  }

  public void render(BandsDTO bandsDTO) {
    SearchFieldProgress.stop();
    listController.render(bandsDTO, oldReplaced);
  }

  private void chooseDataSource() {
    connection.getConnectionMethod();
    if (connection.isConnectionChanged()) {
      if (connection.isConnectionAvailable()) {
        this.bandsSource = new BandsSourceOnline(context, this);
      } else {
        this.bandsSource = new BandsSourceDatabase(context);
      }
    }
  }
}
