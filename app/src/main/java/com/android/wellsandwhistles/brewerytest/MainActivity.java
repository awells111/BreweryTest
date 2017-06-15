package com.android.wellsandwhistles.brewerytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.wellsandwhistles.brewerytest.data.BeerProvider;
import com.android.wellsandwhistles.brewerytest.networking.RetroClient;
import com.android.wellsandwhistles.brewerytest.objects.Beers;
import com.android.wellsandwhistles.brewerytest.objects.Datum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Call<Beers> mBeersCall;
    private ArrayList<Datum> datumListFromJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBeersCall = RetroClient.getBeerApiService().sortNameJSON();
        makeBeerCall();

        //todo delete test button when finished
        Button detailButton = (Button) findViewById(R.id.detail_button);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeerDetailActivity.class);
                startActivity(intent);
            }
        });
        //todo don't forget test button

    }

    private void makeBeerCall() {
        mBeersCall.enqueue(new Callback<Beers>() {
            @Override
            public void onResponse(Call<Beers> call, Response<Beers> response) {
                datumListFromJSON = response.body().getData();
                logDatum(datumListFromJSON);
            }

            @Override
            public void onFailure(Call<Beers> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failed_to_load,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logDatum(ArrayList<Datum> list) {
        String listString = "";


        /*Create a string from our json data*/
        for (Datum s : list) {
            listString += s.toString() + "\t";
        }

        /*If our listString is longer than 4000 characters we will log it in chunks because the Android Monitor
        * has a limited number of characters that it will log.*/
        int chunkSize = 4000;
        if (listString.length() > chunkSize) {
            Log.v(TAG, "listString.length = " + listString.length());
            int chunkCount = listString.length() / chunkSize;
            for (int i = 0; i < chunkCount + 1; i++) {
                int max = chunkSize * (i + 1);
                if (max >= listString.length()) {
                    Log.v(TAG, "chunk " + (i + 1) + " of " + (chunkCount + 1) + ":" + listString.substring(4000 * i));
                } else {
                    Log.v(TAG, "chunk " + (i + 1) + " of " + (chunkCount + 1) + ":" + listString.substring(4000 * i, max));
                }
            }
        } else {
            Log.v(TAG, listString);
        }
    }
}
