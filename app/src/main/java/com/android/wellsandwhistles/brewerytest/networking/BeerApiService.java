package com.android.wellsandwhistles.brewerytest.networking;

import com.android.wellsandwhistles.brewerytest.objects.Beers;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.android.wellsandwhistles.brewerytest.networking.RetroClient.API_KEY;
/**
 * Created by Owner on 6/14/2017.
 */

public interface BeerApiService {

    /*The beers are sorted by name by default. We can add different sort types later if we want.*/
    @GET("v2/beers?key=" + API_KEY + "&format=json")
    Call<Beers> sortNameJSON();
}
