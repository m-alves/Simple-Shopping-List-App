package com.example.android.simpleshoppinglist.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.simpleshoppinglist.Data.ShoppingListContract.WarehouseEntry;

/**
 * Created by Utilizador on 16/08/2017.
 */


public class ShoppingListDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = ShoppingListDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "simpleshoppinglist.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public static final String WAREHOUSE_TABLE_SQL = "CREATE TABLE " + WarehouseEntry.TABLE_NAME + " ("
            + WarehouseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + WarehouseEntry.COLUMN_WAREHOUSE_NAME + " TEXT NOT NULL, "
            + WarehouseEntry.COLUMN_WAREHOUSE_DATE + " TEXT NOT NULL, "
            + WarehouseEntry.COLUMN_WAREHOUSE_INLIST + " INTEGER NOT NULL DEFAULT 0);";


    /**
     * Constructs a new instance of.
     *
     * @param context of the app
     */
    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WAREHOUSE_TABLE_SQL);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
