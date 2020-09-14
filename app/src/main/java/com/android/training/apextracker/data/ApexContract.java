package com.android.training.apextracker.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Apex app.
 */
public class ApexContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ApexContract(){}

    // Constant for ApexProvider URI calls.
    public static final String CONTENT_AUTHORITY = "com.android.training.apextracker";

    // Concatenating scheme and base content URI into a constant, and paring URI string into URI.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //This constants stores the path for each of the tables which will be appended to the base content URI .
    public static final String PATH_GAMES = "games";


    /**
     * Inner class that defines constant values for the game database table.
     * Each entry in the table represents a single game.
     */
    public static abstract class GameEntry implements BaseColumns {

        /** The content URI to access the game data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GAMES);

        /** Name of database table for games */
        public static final String TABLE_NAME = "Games";
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of games.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAMES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single game.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAMES;

        /**
         * Unique ID number for the game (only for use in the database table).
         *
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;
        /**
         * Name of the game.
         *
         * Type: TEXT
         */
        public static final String COLUMN_GAME_NAME = "Name";
        /**
         * Place finished in the game.
         *
         * Type: TEXT
         */
        public static final String COLUMN_GAME_PLACE = "Place";
        /**
         * Character of the game.
         *

         *
         * Type: INTEGER
         */
        public static final String COLUMN_GAME_CHARACTER = "Character";
        /**
         * Kills in the game.
         *
         * Type: INTEGER
         */
        public static final String COLUMN_GAME_KILLS = "Kills";

        //Possible values for games character.
        public static final int CHARACTER_UNKNOWN = 0;
        public static final int CHARACTER_BAGALORE = 1;
        public static final int CHARACTER_BLOODHOUND = 2;
        public static final int CHARACTER_CAUSTIC = 3;
        public static final int CHARACTER_CRYPTO = 4;
        public static final int CHARACTER_GILBRALTER = 5;
        public static final int CHARACTER_LIFELINE = 6;
        public static final int CHARACTER_LOBA = 7;
        public static final int CHARACTER_MIRAGE = 8;
        public static final int CHARACTER_OCTANE = 9;
        public static final int CHARACTER_PATHFINDER = 10;
        public static final int CHARACTER_RAMPART = 11;
        public static final int CHARACTER_REVENANT = 12;
        public static final int CHARACTER_WATTSON = 13;
        public static final int CHARACTER_WRAITH = 14;



    }


}


