package com.android.wellsandwhistles.brewerytest.data;

import android.net.Uri;
import android.provider.BaseColumns;

import static com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry.CONTENT_URI;
/**
 * Created by Owner on 6/14/2017.
 */

public class BeerContract {

    public static final String CONTENT_AUTHORITY = "com.android.wellsandwhistles.brewerytest.data.BeerProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BEER = "beer";

    public static final class BeerEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_BEER)
                .build();

        public static final String TABLE_NAME = "beer";
        public static final String COLUMN_TITLE = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LABEL = "label";
    }

    public static Uri uriWithId(long id) {
        return CONTENT_URI.buildUpon()
                .appendPath(Long.toString(id))
                .build();
    }
}
