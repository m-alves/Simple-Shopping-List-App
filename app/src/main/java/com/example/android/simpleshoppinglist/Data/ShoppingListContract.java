package com.example.android.simpleshoppinglist.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;



/**
 * Created by Utilizador on 16/08/2017.
 */

public class ShoppingListContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ShoppingListContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.simpleshoppinglist";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */


    public static final String PATH_WAREHOUSE = "warehouse";



    public static final class WarehouseEntry implements BaseColumns {

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_WAREHOUSE);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WAREHOUSE;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WAREHOUSE;

        /** Name of database table for pets */
        public final static String TABLE_NAME = "warehouse";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * The item's name.
         *
         * Type: TEXT
         */
        public final static String COLUMN_WAREHOUSE_NAME ="name";


        /**
         * Date when the item was last bought.
         * Type: TEXT
         */
        public final static String COLUMN_WAREHOUSE_DATE = "date";

        /**
         * Boolean regarding shopping list inclusion.
         * Type: INTEGER
         */
        public final static String COLUMN_WAREHOUSE_INLIST = "inlist";
    }

}

