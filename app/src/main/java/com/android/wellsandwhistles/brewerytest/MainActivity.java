package com.android.wellsandwhistles.brewerytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "f6d7c4f2a8159624e41db89517417bf1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button detailButton = (Button) findViewById(R.id.detail_button);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeerDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
