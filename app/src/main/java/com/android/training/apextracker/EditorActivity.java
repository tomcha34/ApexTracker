package com.android.training.apextracker;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.training.apextracker.data.ApexContract.GameEntry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

/**
 * Allows user to create a new game or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the game data loader */
    private static final int EXISTING_GAME_LOADER = 0;

    /** Content URI for the existing game (null if it's a new game) */
    private Uri mCurrentGameUri;

    /** EditText field to enter the games name */
    private EditText mNameEditText;

    /** EditText field to enter the games place */
    private EditText mPlaceEditText;

    /** EditText field to enter the games kills */
    private EditText mKillsEditText;

    /** EditText field to enter the games character */
    private Spinner mCharacterSpinner;

    //Character in the game.
    private int mCharacter = GameEntry.CHARACTER_UNKNOWN;

    /** Boolean flag that keeps track of whether the game has been edited (true) or not (false) */
    private boolean mGameHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mGameHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGameHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new game or editing an existing one.
        Intent intent = getIntent();
        mCurrentGameUri = intent.getData();

        // If the intent DOES NOT contain a game content URI, then we know that we are
        // creating a new game.
        if (mCurrentGameUri == null) {
            // This is a new game, so change the app bar to say "Add a game"
            setTitle(getString(R.string.editor_activity_title_new_game));
        } else {
            // Otherwise this is an existing game, so change app bar to say "Edit game"
            setTitle(getString(R.string.editor_activity_title_edit_game));

            // Initialize a loader to read the game data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_GAME_LOADER, null, this
            );
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_game_name);
        mPlaceEditText = (EditText) findViewById(R.id.edit_game_place);
        mKillsEditText = (EditText) findViewById(R.id.edit_game_kills);
        mCharacterSpinner = (Spinner) findViewById(R.id.spinner_character);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mNameEditText.setOnTouchListener(mTouchListener);
        mPlaceEditText.setOnTouchListener(mTouchListener);
        mKillsEditText.setOnTouchListener(mTouchListener);
        mCharacterSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the character of the game.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter characterSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_character_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        characterSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mCharacterSpinner.setAdapter(characterSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mCharacterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.character_bangalore))) {
                        mCharacter = GameEntry.CHARACTER_BAGALORE;
                    } else if (selection.equals(getString(R.string.character_bloodhound))) {
                        mCharacter = GameEntry.CHARACTER_BLOODHOUND;
                    } else if (selection.equals(getString(R.string.character_caustic))) {
                        mCharacter = GameEntry.CHARACTER_CAUSTIC;
                    }else if (selection.equals(getString(R.string.character_crypto))) {
                        mCharacter = GameEntry.CHARACTER_CRYPTO;
                    }else if (selection.equals(getString(R.string.character_gilbraltar))) {
                        mCharacter = GameEntry.CHARACTER_GILBRALTER;
                    }else if (selection.equals(getString(R.string.character_lifeline))) {
                        mCharacter = GameEntry.CHARACTER_LIFELINE;
                    }else if (selection.equals(getString(R.string.character_loba))) {
                        mCharacter = GameEntry.CHARACTER_LOBA;
                    }else if (selection.equals(getString(R.string.character_mirage))) {
                        mCharacter = GameEntry.CHARACTER_MIRAGE;
                    }else if (selection.equals(getString(R.string.character_octane))) {
                        mCharacter = GameEntry.CHARACTER_OCTANE;
                    }else if (selection.equals(getString(R.string.character_pathfinder))) {
                        mCharacter = GameEntry.CHARACTER_PATHFINDER;
                    }else if (selection.equals(getString(R.string.character_rampart))) {
                        mCharacter = GameEntry.CHARACTER_RAMPART;
                    }else if (selection.equals(getString(R.string.character_revenant))) {
                        mCharacter = GameEntry.CHARACTER_REVENANT;
                    }else if (selection.equals(getString(R.string.character_wattson))) {
                        mCharacter = GameEntry.CHARACTER_WATTSON;
                    }else if (selection.equals(getString(R.string.character_wraith))) {
                        mCharacter = GameEntry.CHARACTER_WRAITH;
                    }else {
                        mCharacter = GameEntry.CHARACTER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCharacter = GameEntry.CHARACTER_UNKNOWN;
            }
        });
    }
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the game.
                deleteGame();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the game.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the game in the database.
     */
    private void deleteGame() {
        // Only perform the delete if this is an existing game.
        if (mCurrentGameUri != null) {
            // Call the ContentResolver to delete the game at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentGameUri
            // content URI already identifies the game that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentGameUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_game_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_game_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }


    /**
     * Get user input from editor and save game into database.
     */
    private void saveGame() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String placeString = mPlaceEditText.getText().toString().trim();
        String killsString = mKillsEditText.getText().toString().trim();

        // Check if this is supposed to be a new game
        // and check if all the fields in the editor are blank
        if (mCurrentGameUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(placeString) &&
                TextUtils.isEmpty(killsString) && mCharacter == GameEntry.CHARACTER_UNKNOWN) {
            // Since no fields were modified, we can return early without creating a new game.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and game attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(GameEntry.COLUMN_GAME_NAME, nameString);
        values.put(GameEntry.COLUMN_GAME_PLACE, placeString);
        values.put(GameEntry.COLUMN_GAME_CHARACTER, mCharacter);
        // If the kills is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int kills = 0;
        if (!TextUtils.isEmpty(killsString)) {
            kills = Integer.parseInt(killsString);
        }
        values.put(GameEntry.COLUMN_GAME_KILLS, kills);

        // Determine if this is a new or existing game by checking if mCurrentGameUri is null or not
        if (mCurrentGameUri == null) {
            // This is a NEW game, so insert a new game into the provider,
            // returning the content URI for the new game.
            Uri newUri = getContentResolver().insert(GameEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_game_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_game_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING game, so update the pet with content URI: mCurrentGameUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentGameUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentGameUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_game_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_game_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        if (mCurrentGameUri == null) {
            // This is a new game, so change the app bar to say "Add a game"
            setTitle(getString(R.string.editor_activity_title_new_game));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        } else { //other stuff
        }
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new game, hide the "Delete" menu item.
        if (mCurrentGameUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save game to database
                saveGame();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the game hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mGameHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the game hasn't changed, continue with handling back button press
        if (!mGameHasChanged) {
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all game attributes, define a projection that contains
        // all columns from the game table
        String[] projection = {
                GameEntry._ID,
                GameEntry.COLUMN_GAME_NAME,
                GameEntry.COLUMN_GAME_PLACE,
                GameEntry.COLUMN_GAME_CHARACTER,
                GameEntry.COLUMN_GAME_KILLS };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentGameUri,         // Query the content URI for the current game
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of game attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(GameEntry.COLUMN_GAME_NAME);
            int placeColumnIndex = cursor.getColumnIndex(GameEntry.COLUMN_GAME_PLACE);
            int characterColumnIndex = cursor.getColumnIndex(GameEntry.COLUMN_GAME_CHARACTER);
            int killsColumnIndex = cursor.getColumnIndex(GameEntry.COLUMN_GAME_KILLS);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String place = cursor.getString(placeColumnIndex);
            int character = cursor.getInt(characterColumnIndex);
            int kills = cursor.getInt(killsColumnIndex);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mPlaceEditText.setText(place);
            mKillsEditText.setText(Integer.toString(kills));

            // Character is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Banglore, 2 is Bloodhound etc).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (character) {
                case GameEntry.CHARACTER_BAGALORE:
                    mCharacterSpinner.setSelection(1);
                    break;
                case GameEntry.CHARACTER_BLOODHOUND:
                    mCharacterSpinner.setSelection(2);
                    break;
                case GameEntry.CHARACTER_CAUSTIC:
                    mCharacterSpinner.setSelection(3);
                    break;
                case GameEntry.CHARACTER_CRYPTO:
                    mCharacterSpinner.setSelection(4);
                    break;
                case GameEntry.CHARACTER_GILBRALTER:
                    mCharacterSpinner.setSelection(5);
                    break;
                case GameEntry.CHARACTER_LIFELINE:
                    mCharacterSpinner.setSelection(6);
                    break;
                case GameEntry.CHARACTER_LOBA:
                    mCharacterSpinner.setSelection(7);
                    break;
                case GameEntry.CHARACTER_MIRAGE:
                    mCharacterSpinner.setSelection(8);
                    break;
                case GameEntry.CHARACTER_OCTANE:
                    mCharacterSpinner.setSelection(9);
                    break;
                case GameEntry.CHARACTER_PATHFINDER:
                    mCharacterSpinner.setSelection(10);
                    break;
                case GameEntry.CHARACTER_RAMPART:
                    mCharacterSpinner.setSelection(11);
                    break;
                case GameEntry.CHARACTER_REVENANT:
                    mCharacterSpinner.setSelection(12);
                    break;
                case GameEntry.CHARACTER_WATTSON:
                    mCharacterSpinner.setSelection(13);
                    break;
                case GameEntry.CHARACTER_WRAITH:
                    mCharacterSpinner.setSelection(14);
                    break;
                default:
                    mCharacterSpinner.setSelection(0);
                    break;
            }
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");
        mPlaceEditText.setText("");
        mKillsEditText.setText("");
        mCharacterSpinner.setSelection(0); // Select "Unknown" character

    }



    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the game.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}