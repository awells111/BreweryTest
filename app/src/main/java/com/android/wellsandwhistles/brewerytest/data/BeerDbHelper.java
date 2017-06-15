package com.android.wellsandwhistles.brewerytest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry;

import static com.android.wellsandwhistles.brewerytest.data.BeerContract.PATH_BEER;
/**
 * Created by Owner on 6/14/2017.
 */

public class BeerDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = PATH_BEER + ".db";

    private static final int DATABASE_VERSION = 1;

    public BeerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_BEER_TABLE =

                "CREATE TABLE " + BeerEntry.TABLE_NAME + " (" +

                        BeerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        BeerEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        BeerEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                        BeerEntry.COLUMN_LABEL + " TEXT NOT NULL, " +

                        " UNIQUE (" + BeerEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_BEER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BeerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
