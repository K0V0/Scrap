package com.kovospace.scrap.bands.sources;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BandsDTO {
  private int currentPageItemsCount;
  private int pagesCount;
  private int currentPageNum;
  private int totalItemsCount;
  private List<BandEntity> bands;

  @Override
  public String toString() {
    return String.format(
      "page: %s \n page_items_count: %s \n pages_count: %s \n total_items_count: %s \n",
      this.currentPageNum, this.currentPageItemsCount, this.pagesCount, this.totalItemsCount
    ) + this.bands.toString();
  }
}
