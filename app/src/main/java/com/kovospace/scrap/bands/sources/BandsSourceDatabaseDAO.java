package com.kovospace.scrap.bands.sources;

import androidx.room.*;

import java.util.List;

@Dao
public interface BandsSourceDatabaseDAO {

    @Query("SELECT * FROM bandentity WHERE slug = :slug")
    List<BandEntity> findBySlug(String slug);

    @Query("SELECT * FROM bandentity WHERE slug = :slug LIMIT 1")
    List<BandEntity> findFirstBySlug(String slug);

    @Query("SELECT * FROM bandentity WHERE title LIKE '%' || :title || '%' ORDER BY title")
    List<BandEntity> findByName(String title);

    @Query("SELECT * FROM bandentity WHERE title LIKE '%' || :title || '%' ORDER BY title LIMIT :limit")
    List<BandEntity> findByName(String title, int limit);

    @Query("SELECT * FROM bandentity WHERE title LIKE '%' || :title || '%' ORDER BY title LIMIT :limit OFFSET :offset")
    List<BandEntity> findByName(String title, int limit, int offset);

    @Query("SELECT COUNT(*) FROM bandentity WHERE title LIKE '%' || :title || '%'")
    int getCount(String title);

    @Query("SELECT * FROM bandentity ORDER BY title")
    List<BandEntity> getAll();

    @Insert
    void insert(BandEntity bandEntity);

    @Update
    void update(BandEntity bandEntity);

    @Delete
    void delete(BandEntity bandEntity); // not working, maybe because making "new" entity ?

    @Query("DELETE FROM bandentity WHERE slug = :slug")
    void delete(String slug);

    @Delete
    void delete(BandEntity... bandEntities);

    @Query("DELETE FROM bandentity")
    void deleteAll();
}
