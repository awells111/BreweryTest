package com.android.wellsandwhistles.brewerytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.wellsandwhistles.brewerytest.networking.RetroClient;
import com.android.wellsandwhistles.brewerytest.objects.Beers;
import com.android.wellsandwhistles.brewerytest.objects.Datum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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

        for (Datum s : list) {
            listString += s.toString() + "\t";
        }

        System.out.println(listString);
    }

}
