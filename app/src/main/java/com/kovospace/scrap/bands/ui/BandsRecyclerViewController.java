package com.kovospace.scrap.bands.ui;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kovospace.scrap.R;
import com.kovospace.scrap.appBase.ui.RecyclerViewController;
import com.kovospace.scrap.bands.BandsService;
import com.kovospace.scrap.bands.sources.BandEntity;
import com.kovospace.scrap.bands.sources.BandsDTO;
import com.kovospace.scrap.appBase.ui.ToastMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public  class BandsRecyclerViewController
        extends RecyclerViewController
{
  private Context context;
  private BandsService bandsService;

  private Activity activity;
  protected RecyclerView bandsRecyclerView;
  protected RecyclerView.Adapter bandsAdapter;
  protected RecyclerView.LayoutManager bandsLayoutManager;

  private BandsDTO bandsDTO;
  private List<BandEntity> bands;
  private int nextPageToLoad;
  private boolean preventMultipleLoads;

  public BandsRecyclerViewController(Context context, BandsService bandsService) {
    this.context = context;
    this.bandsService = bandsService;
    init();
  }

  @Override
  protected int dataSourceType() {
    return DATA_SOURCE_INTERNET;
  }

  private void init() {
    bands = new ArrayList<>();
    activity = (Activity) context;
    bandsRecyclerView = this.activity.findViewById(R.id.bandsList);
    bandsLayoutManager = new LinearLayoutManager(this.activity);
    bandsRecyclerView.setLayoutManager(bandsLayoutManager);
    bandsRecyclerView.setHasFixedSize(true);
    bandsAdapter = new BandsRecyclerView(this.context, bands);
    bandsRecyclerView.setAdapter(bandsAdapter);
    setScrollListener();
  }

  public void render(BandsDTO bandsDTO, boolean sortMethod) {
    this.bandsDTO = bandsDTO;
    append(this.bands, bandsDTO.getBands(), sortMethod);
    bandsAdapter.notifyDataSetChanged();
    calculateNextPage();
    preventMultipleLoads = false;
    if (dataSourceType() == DATA_SOURCE_INTERNET) {
      removeLoadingDialog();
    }
  }

  private void calculateNextPage() {
    if (bandsDTO.getCurrentPageNum() + 1 <= bandsDTO.getPagesCount()) {
      nextPageToLoad = bandsDTO.getCurrentPageNum() + 1;
    } else {
      nextPageToLoad = 0;
    }
  }

  private void append(List<BandEntity> current, List<BandEntity> neew, boolean sort) {
    List<BandEntity> result = neew
      .stream()
      .filter(band -> !current.contains(band))
      .collect(Collectors.toList());
    if (sort) {
      result.addAll(
        current
          .stream()
          .filter(neew::contains)
          .collect(Collectors.toList())
      );
      bands.clear();
    }
    bands.addAll(result);
  }

  private void setScrollListener() {
    bandsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
          if (nextPageToLoad != 0) {
            if (!preventMultipleLoads) {
              preventMultipleLoads = true;
              bandsService.nextPage(nextPageToLoad);
              if (dataSourceType() == DATA_SOURCE_INTERNET) {
                displayLoadingDialog();
              }
            }
          } else {
            if (bandsDTO.getCurrentPageNum() == bandsDTO.getPagesCount()) {
              new ToastMessage(context, R.string.noMoreBands);
            }
          }
        }
      }
    });
  }

  private void displayLoadingDialog() {
    bands.add(null);
    bandsAdapter.notifyItemInserted(bands.size() - 1);
  }

  private void removeLoadingDialog() {
    int posToRemove = bands.indexOf(null);
    bands.removeIf(Objects::isNull);
    bandsAdapter.notifyItemRemoved(posToRemove);
  }
}
