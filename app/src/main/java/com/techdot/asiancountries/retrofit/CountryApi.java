package com.techdot.asiancountries.retrofit;

import com.techdot.asiancountries.model.Country;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryApi {
    @GET("asia")
    Call<ArrayList<Country>> getAsianCountries();
}
