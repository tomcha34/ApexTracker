package com.android.training.apextracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.training.apextracker.data.ApexContract.GameEntry;

/**
 * Database helper for Apex Tracker app. Manages database creation and version management.
 */
public class ApexDbHelper extends SQLiteOpenHelper {

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "games.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ApexDbHelper}.
     *
     * @param context of the app
     */
    public ApexDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the Games table
        String SQL_CREATE_GAMES_TABLE = "CREATE TABLE " + GameEntry.TABLE_NAME + " ("
                + GameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GameEntry.COLUMN_GAME_NAME + " TEXT NOT NULL, "
                + GameEntry.COLUMN_GAME_PLACE + " TEXT, "
                + GameEntry.COLUMN_GAME_CHARACTER + " INTEGER NOT NULL, "
                + GameEntry.COLUMN_GAME_KILLS + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_GAMES_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}