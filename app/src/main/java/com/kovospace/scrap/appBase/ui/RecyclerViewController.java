package com.kovospace.scrap.appBase.ui;

public abstract class RecyclerViewController
{
  protected static final int ITEMS_PER_PAGE = 20;
  protected static final int DATA_SOURCE_INTERNET = 1;
  protected static final int DATA_SOURCE_LOCAL = 2;

  protected abstract int dataSourceType();
}
