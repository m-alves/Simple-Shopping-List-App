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

public class WarehouseCursorAdapter extends CursorAdapter{

    private int inListStatus;

    /**
     * Constructs a new {@linkr}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */

    Context mContext;
    public WarehouseCursorAdapter(Context context, Cursor c) {

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
        return LayoutInflater.from(context).inflate(R.layout.warehouse_list_item, parent, false);
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
        final Button warehouseDeleteButton = (Button) view.findViewById(R.id.warehouse_delete_button);
        TextView nameTextView = (TextView) view.findViewById(R.id.warehouse_item_name);
        TextView dateTextView = (TextView) view.findViewById(R.id.warehouse_item_last_bought);
        final Button addButton = (Button) view.findViewById(R.id.warehouse_add_button);

        warehouseDeleteButton.setTag(cursor.getPosition());
        addButton.setTag(cursor.getPosition());


        mContext = context;
        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_WAREHOUSE_NAME);
        int dateColumnIndex = cursor.getColumnIndex(WarehouseEntry.COLUMN_WAREHOUSE_DATE);


        // Read the pet attributes from the Cursor for the current pet
        final String warehouseItemName = cursor.getString(nameColumnIndex);
        final String warehouseItemDate = cursor.getString(dateColumnIndex);


        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(warehouseItemName);
        dateTextView.setText(warehouseItemDate);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) addButton.getTag();
                cursor.moveToPosition(position);
                long id = cursor.getLong(cursor.getColumnIndex(WarehouseEntry._ID));
                Uri currentItemUri = ContentUris.withAppendedId(WarehouseEntry.CONTENT_URI, id);
                inListStatus = 1;

                ContentValues values = new ContentValues();
                values.put(WarehouseEntry.COLUMN_WAREHOUSE_NAME, warehouseItemName);
                values.put(WarehouseEntry.COLUMN_WAREHOUSE_DATE, warehouseItemDate);
                values.put(WarehouseEntry.COLUMN_WAREHOUSE_INLIST, inListStatus);
                int rowsAffected = mContext.getContentResolver().update(currentItemUri, values, null, null);
            }
        });

        warehouseDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) warehouseDeleteButton.getTag();
                cursor.moveToPosition(position);
                long id = cursor.getLong(cursor.getColumnIndex(WarehouseEntry._ID));
                Uri currentItemUri = ContentUris.withAppendedId(WarehouseEntry.CONTENT_URI, id);
                deleteItem(currentItemUri);
            }
        });
    }

    private void deleteItem(Uri currentItemUri){

        if (currentItemUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
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
