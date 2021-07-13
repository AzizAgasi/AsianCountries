package com.techdot.asiancountries.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("Select * from countries ORDER BY name")
    List<CountryEntity> getCountriesList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(List<CountryEntity> countries);

    @Delete
    void deleteCountry(List<CountryEntity> countries);
}
