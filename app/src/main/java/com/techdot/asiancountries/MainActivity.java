package com.techdot.asiancountries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.techdot.asiancountries.adapter.CountryAdapter;
import com.techdot.asiancountries.model.Country;
import com.techdot.asiancountries.model.Language;
import com.techdot.asiancountries.database.AppExecutors;
import com.techdot.asiancountries.database.CountryDatabase;
import com.techdot.asiancountries.database.CountryEntity;
import com.techdot.asiancountries.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView countriesView;
    private CountryAdapter adapter;
    private ProgressBar progressBar;

    private ArrayList<Country> countryList = new ArrayList<>();
    private List<CountryEntity> countries = new ArrayList<>();


    private CountryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = CountryDatabase.getInstance(this);

        getCountries();

        countriesView = findViewById(R.id.countryRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        countriesView.setLayoutManager(manager);
        progressBar = findViewById(R.id.loading);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!db.countryDao().getCountriesList().isEmpty()) {
                    adapter = new CountryAdapter(db.countryDao().getCountriesList());
                    countriesView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getCountries() {
        Call<ArrayList<Country>> call = RetrofitInstance.api.getAsianCountries();
        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                if (response.code() == 200) {
                    ArrayList<Country> countryResponse = response.body();

                    assert countryResponse != null;
                    countryList.addAll(countryResponse);

                    for (int i = 0; i < countryList.size(); i++) {
                        Country currentCountry = countryList.get(i);

                        List<Language> languages = currentCountry.getLanguages();

                        String language = "";
                        for (int j = 0; j < languages.size(); j++) {
                            language += languages.get(j).getName();
                            if (j < languages.size() - 1) language += ", ";
                        }

                        List<String> borders = currentCountry.getBorders();

                        String border = "";
                        for (int k = 0; k < borders.size(); k++) {
                            border += borders.get(k);
                            if (k < borders.size() - 1) border += ", ";
                        }
                        CountryEntity country = new CountryEntity(
                                i,
                                currentCountry.getName(),
                                currentCountry.getFlag(),
                                currentCountry.getCapital(),
                                currentCountry.getRegion(),
                                currentCountry.getSubregion(),
                                currentCountry.getPopulation().toString(),
                                language,
                                border
                        );
                        countries.add(country);
                    }

                    adapter = new CountryAdapter(countries);
                    countriesView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.countryDao().insertCountry(countries);
                        }
                    });

                    Log.v("FLAGS", countryResponse.get(0).getFlag());

                    Log.v("COUNTRY", countryList.get(0).getName());

                } else {
                    Toast.makeText(MainActivity.this, "Failure" + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("RESPONSE_ERROR", "Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                Log.e("Callback failure", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteDatabase) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    db.clearAllTables();
                }
            });
            Toast.makeText(this, "Data Cleared successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}