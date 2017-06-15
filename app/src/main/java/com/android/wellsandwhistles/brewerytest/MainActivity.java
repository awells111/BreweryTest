package com.android.wellsandwhistles.brewerytest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.wellsandwhistles.brewerytest.data.BeerAdapter;
import com.android.wellsandwhistles.brewerytest.data.BeerContract;
import com.android.wellsandwhistles.brewerytest.networking.RetroClient;
import com.android.wellsandwhistles.brewerytest.objects.Beers;
import com.android.wellsandwhistles.brewerytest.objects.Datum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry.COLUMN_DESCRIPTION;
import static com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry.COLUMN_LABEL_ICON;
import static com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry.COLUMN_LABEL_LARGE;
import static com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry.COLUMN_LABEL_MEDIUM;
import static com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry.COLUMN_TITLE;
import static com.android.wellsandwhistles.brewerytest.data.BeerContract.BeerEntry.CONTENT_URI;
import static com.android.wellsandwhistles.brewerytest.data.BeerContract.uriWithName;

public class MainActivity extends AppCompatActivity implements
        BeerAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int ID_BEER_LOADER = 1;

    public static final String[] MAIN_DATABASE_BEER = {
            BeerContract.BeerEntry.COLUMN_TITLE,
            BeerContract.BeerEntry.COLUMN_DESCRIPTION,
            BeerContract.BeerEntry.COLUMN_LABEL_ICON,
            BeerContract.BeerEntry.COLUMN_LABEL_MEDIUM,
            BeerContract.BeerEntry.COLUMN_LABEL_LARGE
    };

    public static final int INDEX_BEER_TITLE = 0;
    public static final int INDEX_BEER_DESCRIPTION = 1;
    public static final int INDEX_BEER_LABEL_ICON = 2;
    public static final int INDEX_BEER_LABEL_MEDIUM = 3;
    public static final int INDEX_BEER_LABEL_LARGE = 4;

    private RecyclerView mRecyclerView;
    private BeerAdapter mBeerAdapter;

    Call<Beers> mBeersCall;
    private ArrayList<Datum> datumListFromJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBeerAdapter = new BeerAdapter(null);
        mBeerAdapter.setOnItemClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mBeerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBeersCall = RetroClient.getBeerApiService().sortNameJSON();

        getSupportLoaderManager().initLoader(ID_BEER_LOADER, null, this);

        getContentResolver().delete(CONTENT_URI, null, null);
        makeBeerCall();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {

        switch (loaderId) {

            case ID_BEER_LOADER:

                String sortOrder = BeerContract.BeerEntry._ID + " ASC";

                Log.i(TAG, "LOADER CREATED");
                return new CursorLoader(this,
                        CONTENT_URI,
                        MAIN_DATABASE_BEER,
                        "NAME IS NOT NULL",
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mBeerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mBeerAdapter.swapCursor(null);
    }


    private void makeBeerCall() {
        mBeersCall.enqueue(new Callback<Beers>() {
            @Override
            public void onResponse(Call<Beers> call, Response<Beers> response) {
                datumListFromJSON = response.body().getData();

                getContentResolver().bulkInsert(CONTENT_URI, createBeerContentValues(datumListFromJSON));
            }

            @Override
            public void onFailure(Call<Beers> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failed_to_load,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ContentValues[] createBeerContentValues(ArrayList<Datum> list) {
        ContentValues[] beerContentValues = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, list.get(i).getName());
            values.put(COLUMN_DESCRIPTION, list.get(i).getDescription());
            values.put(COLUMN_LABEL_ICON, list.get(i).getLabels().getIcon());
            values.put(COLUMN_LABEL_MEDIUM, list.get(i).getLabels().getMedium());
            values.put(COLUMN_LABEL_LARGE, list.get(i).getLabels().getLarge());

            beerContentValues[i] = values;
        }

        return beerContentValues;
    }

    @Override
    public void onItemClick(View v, int position) {
        Uri uri = uriWithName(mBeerAdapter.getItemName(position));
        Intent intent = new Intent(MainActivity.this, BeerDetailActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }
}
