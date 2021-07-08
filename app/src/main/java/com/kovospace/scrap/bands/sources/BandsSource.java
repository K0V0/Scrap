package com.kovospace.scrap.bands.sources;

public interface BandsSource
{
  void fetch(String searchString);
  void fetch(String searchString, int pageNum);
}
