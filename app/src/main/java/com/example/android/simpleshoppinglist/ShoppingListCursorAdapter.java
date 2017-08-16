package com.example.android.simpleshoppinglist;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.simpleshoppinglist.Data.ShoppingListContract.WarehouseEntry;

/**
 * Created by Utilizador on 16/08/2017.
 */

public class ShoppingListCursorAdapter extends CursorAdapter {
    private int inListStatus;

    /**
     * Constructs a new {@link }.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */

    Context mContext;
    public ShoppingListCursorAdapter(ShoppingListFragment context, Cursor c) {

        super(context, c, 0 /* flags */);

    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.shopping_list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        final Button boughtButton = (Button) view.findViewById(R.id.bought_button);
        TextView nameTextView = (TextView) view.findViewById(R.id.shopping_item_name);
        TextView dateTextView = (TextView) view.findViewById(R.id.shopping_item_last_bought);
        final Button shoppingDeleteButton = (Button) view.findViewById(R.id.shopping_delete_button);

        boughtButton.setTag(cursor.getPosition());
        shoppingDeleteButton.setTag(cursor.getPosition());


        mContext = context;
        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_WAREHOUSE_NAME);
        int dateColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_WAREHOUSE_DATE);


        // Read the pet attributes from the Cursor for the current pet
        final String shoppingListItemName = cursor.getString(nameColumnIndex);
        final String shoppingListItemDate = cursor.getString(dateColumnIndex);


        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(shoppingListItemName);
        dateTextView.setText(shoppingListItemDate);


        boughtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) boughtButton.getTag();
                cursor.moveToPosition(position);
                long id = cursor.getLong(cursor.getColumnIndex(WarehouseEntry._ID));
                Uri currentItemUri = ContentUris.withAppendedId(WarehouseEntry.CONTENT_URI, id);
                inListStatus = 0;

                ContentValues values = new ContentValues();
                values.put(WarehouseEntry.COLUMN_WAREHOUSE_NAME, shoppingListItemName);
                values.put(WarehouseEntry.COLUMN_WAREHOUSE_DATE, shoppingListItemDate);
                values.put(WarehouseEntry.COLUMN_WAREHOUSE_INLIST, inListStatus);
                int rowsAffected = mContext.getContentResolver().update(currentItemUri, values, null, null);
            }
        });

        shoppingDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) shoppingDeleteButton.getTag();
                cursor.moveToPosition(position);
                long id = cursor.getLong(cursor.getColumnIndex(WarehouseEntry._ID));
                Uri currentItemUri = ContentUris.withAppendedId(WarehouseEntry.CONTENT_URI, id);
                deleteItem(currentItemUri);

                //Falta completar com diálogo para apagar de ambas as listas ou só de uma
            }
        });
    }

    private void deleteItem(Uri currentItemUri){

        if (currentItemUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that wc want.
            int rowsDeleted = mContext.getContentResolver().delete(currentItemUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(mContext, mContext.getResources().getString(R.string.detail_delete_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(mContext, mContext.getResources().getString(R.string.detail_delete_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
