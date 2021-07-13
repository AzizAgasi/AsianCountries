package com.techdot.asiancountries.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class CountryEntity {

    @PrimaryKey()
    private int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "flag")
    private String flag;

    @ColumnInfo(name = "capital")
    private String capital;

    @ColumnInfo(name = "region")
    private String region;

    @ColumnInfo(name = "subregion")
    private String subregion;

    @ColumnInfo(name = "population")
    private String population;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "borders")
    private String borders;

    public CountryEntity(int id, String name, String flag, String capital, String region, String subregion,
                         String population, String language, String borders) {

        this.id = id;
        this.name = name;
        this.flag = flag;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.language = language;
        this.borders = borders;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getPopulation() {
        return population;
    }

    public String getLanguage() {
        return language;
    }

    public String getBorders() {
        return borders;
    }
}
