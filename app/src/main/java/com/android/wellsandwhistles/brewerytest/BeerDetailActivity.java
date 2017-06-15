package com.android.wellsandwhistles.brewerytest;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wellsandwhistles.brewerytest.data.BeerContract;
/**
 * Created by Owner on 6/14/2017.
 */

public class BeerDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = BeerDetailActivity.class.getSimpleName();

    private static final int ID_DETAIL_BEER_LOADER = 2;

    public static final String[] DETAIL_DATABASE_BEER = {
            BeerContract.BeerEntry.COLUMN_TITLE,
            BeerContract.BeerEntry.COLUMN_DESCRIPTION,
            BeerContract.BeerEntry.COLUMN_LABEL_ICON,
            BeerContract.BeerEntry.COLUMN_LABEL_MEDIUM,
            BeerContract.BeerEntry.COLUMN_LABEL_LARGE
    };

    public static final int INDEX_DETAIL_BEER_TITLE = 0;
    public static final int INDEX_DETAIL_BEER_DESCRIPTION = 1;
    public static final int INDEX_DETAIL_BEER_LABEL_ICON = 2;
    public static final int INDEX_DETAIL_BEER_LABEL_MEDIUM = 3;
    public static final int INDEX_DETAIL_BEER_LABEL_LARGE = 4;

    private Uri beerUri;

    private TextView titleView;
    private TextView descriptionView;
    private ImageView labelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        beerUri = getIntent().getData();
        titleView = (TextView) findViewById(R.id.beer_title_detail);
        descriptionView = (TextView) findViewById(R.id.beer_description_detail);
        labelView = (ImageView) findViewById(R.id.beer_label_detail);

        getSupportLoaderManager().initLoader(ID_DETAIL_BEER_LOADER, null, BeerDetailActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            /*Calling finish will avoid us calling onCreate again in MainActivity*/
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle loaderArgs) {
        switch (loaderId) {

            case ID_DETAIL_BEER_LOADER:

                return new CursorLoader(this,
                        beerUri,
                        DETAIL_DATABASE_BEER,
                        BeerContract.BeerEntry.COLUMN_TITLE,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        /*Checks if our cursor has valid data (stops our app from crashing after delete)*/
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            Log.e(TAG, "Cursor does not have valid data.");
            return;
        }

        data.moveToFirst();

        String title;
        String description;
        String label;

        title = data.getString(INDEX_DETAIL_BEER_TITLE);

        if (data.getString(INDEX_DETAIL_BEER_DESCRIPTION).equals("")) {
            description = getResources().getString(R.string.no_description_found);
        } else {
            description = data.getString(INDEX_DETAIL_BEER_DESCRIPTION);
        }

        label = data.getString(INDEX_DETAIL_BEER_LABEL_LARGE);

        titleView.setText(title);
        descriptionView.setText(description);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}