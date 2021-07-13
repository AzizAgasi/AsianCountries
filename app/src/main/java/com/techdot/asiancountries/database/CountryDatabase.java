package com.techdot.asiancountries.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = CountryEntity.class, exportSchema = false, version = 4)
public abstract class CountryDatabase extends RoomDatabase {

    private static final String DB_NAME = "countries_db";
    private static CountryDatabase instance;

    public static synchronized CountryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CountryDatabase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract CountryDao countryDao();
}
