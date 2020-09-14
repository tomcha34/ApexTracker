package com.android.training.apextracker;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.training.apextracker.data.ApexContract;
import com.android.training.apextracker.data.ApexContract.GameEntry;

public class ApexCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ApexCursorAdapter}.
     *  @param context The context
     * @param c       The cursor from which to get the data.
     * @param i
     */
    public ApexCursorAdapter(Context context, Cursor c, int i) {
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
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the game data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current game can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        // Find the columns of game attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ApexContract.GameEntry.COLUMN_GAME_NAME);
        int placeColumnIndex = cursor.getColumnIndex(GameEntry.COLUMN_GAME_PLACE);



        // Read the game attributes from the Cursor for the current game
        String gameName = cursor.getString(nameColumnIndex);
        String gamePlace = cursor.getString(placeColumnIndex);

        // If the game place is empty string or null, then use some default text
        // that says "Unknown place", so the TextView isn't blank.
        if (TextUtils.isEmpty(gamePlace)) {
            gamePlace = context.getString(R.string.unknown_place);
        }

        // Update the TextViews with the attributes for the current game
        nameTextView.setText(gameName);
        summaryTextView.setText(gamePlace);
    }
}