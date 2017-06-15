package com.android.wellsandwhistles.brewerytest.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Owner on 6/14/2017.
 */

public class RetroClient {

    //    https://api.brewerydb.com/v2/beers?key=f6d7c4f2a8159624e41db89517417bf1&format=json
    private static final String API_ROOT_URL = "https://api.brewerydb.com/";
    static final String API_KEY = "f6d7c4f2a8159624e41db89517417bf1";


    private static Retrofit getRetrofitBeerInstance() {
        return new Retrofit.Builder()
                .baseUrl(API_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static BeerApiService getBeerApiService() {
        return getRetrofitBeerInstance().create(BeerApiService.class);
    }
}
