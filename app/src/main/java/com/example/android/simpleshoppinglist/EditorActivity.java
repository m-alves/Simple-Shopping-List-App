package com.example.android.simpleshoppinglist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.simpleshoppinglist.Data.ShoppingListContract.WarehouseEntry;

import java.util.Calendar;

public class EditorActivity extends AppCompatActivity {

    /** Identifier for the item data loader */
    private static final int EXISTING_ITEM_LOADER = 0;

    /** Content URI for the existing item (null if it's a new item) */
    private String mLauncherActivity;

    /** EditText field to enter the item's name */
    private EditText mNameEditText;

    /** EditText field to enter the item's date */
    private EditText mDateEditText;

    /*Save Button*/
    private Button mSaveButton;

    private boolean mValidInput;

    private int inListStatus = 0;

    /** Boolean flag that keeps track of whether the item has been edited (true) or not (false) */
    private boolean mItemHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mLauncherActivity = bundle.getString("Activity") ;



        mNameEditText = (EditText) findViewById(R.id.editor_item_name_edit);

        mSaveButton = (Button) findViewById(R.id.editor_save_button);

        mNameEditText.setOnTouchListener(mTouchListener);

        mSaveButton.setOnTouchListener(mTouchListener);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLauncherActivity.equals("shoppingList")) {
                    inListStatus = 1;
                }
                saveItem();
                finish();
            }
        });

    }

    /**
     * Get user input from editor and save item into database.
     */
    private void saveItem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        if(TextUtils.isEmpty(nameString)){
            Toast.makeText(this, "Please insert valid name", Toast.LENGTH_SHORT).show();
            mValidInput=false;
            return;
        }


        String dateString = getDate();



        // Create a ContentValues object where column names are the keys,
        // and item attributes from the editor are the values.
        ContentValues values = new ContentValues();

        values.put(WarehouseEntry.COLUMN_WAREHOUSE_NAME, nameString);

        values.put(WarehouseEntry.COLUMN_WAREHOUSE_DATE, dateString);

        values.put(WarehouseEntry.COLUMN_WAREHOUSE_INLIST, inListStatus);


        Uri newUri = getContentResolver().insert(WarehouseEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful.
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.detail_insert_item_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            mValidInput=true;
            Toast.makeText(this, getString(R.string.detail_insert_item_successful),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        // If the item hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }
        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private String getDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        StringBuilder sb = new StringBuilder();
        sb.append("Adicionado a ").append(mDay)
                .append("/").append(mMonth).append("/")
                .append(mYear);

        return sb.toString();
    }
}

