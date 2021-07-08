package com.kovospace.scrap.bands.sources;

import androidx.room.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Getter
@Setter
@NoArgsConstructor
@Entity
public  class BandEntity
        implements BandItem
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String title;
    private String slug;
    private String genre;
    private String city;
    private String imageUrl;
    private String imageFullLocalPath;
    private String href;

    @Ignore
    private boolean imageAvailableOffline;

    @Ignore
    private boolean fromDb;

    @Override
    public String toString() {
        return "id=" + id + " slug=" + slug;
    }

    @Override
    public boolean contains(BandItem o) {
        return this.equals(o);
    }

    @Override
    public String getLocalOrHref() {
        if (imageAvailableOffline) {
            return getImageFullLocalPath();
        } else {
            return getImageUrl();
        }
    }
}
