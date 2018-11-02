package javinator9889.securepass.io.database.operations.entry.texts.smalltext;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.fields.SmallText;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.threading.ThreadExceptionListener;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.SmallTextFields;

/**
 * Copyright © 2018 - present | APP by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 02/11/2018 - APP.
 */
public class SmallTextOperations extends CommonOperations implements ISmallTextSetOperations,
        ISmallTextGetOperations {
    private static final String TAG = "SmallText Operations";
    private static final String TABLE_NAME = Constants.SQL.SMALL_TEXT.NAME;
    private static final SmallTextFields ID = SmallTextFields.ID;
    private static final SmallTextFields TEXT = SmallTextFields.TEXT;
    private static final SmallTextFields DESCRIPTION = SmallTextFields.DESCRIPTION;
    private static final SmallTextFields ORDER = SmallTextFields.ORDER;
    private static final SmallTextFields ENTRY = SmallTextFields.ENTRY;
    private static final String WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Available constructor, matching
     * {@link CommonOperations#CommonOperations(DatabaseManager, ThreadExceptionListener) super} one
     *
     * @param databaseInstance    instance of the {@link DatabaseManager} object
     * @param onExceptionListener class that implements {@link ThreadExceptionListener} interface
     *                            - can be null if no listener is set up
     * @see DatabaseManager
     * @see ThreadExceptionListener
     */
    public SmallTextOperations(@NonNull DatabaseManager databaseInstance,
                               @Nullable ThreadExceptionListener onExceptionListener) {
        super(databaseInstance, onExceptionListener);
    }

    /**
     * Gets the tag for {@link Log} output - should be overridden
     *
     * @return <code>String</code> with the tag name
     */
    @Override
    public String getTag() {
        return TAG;
    }

    /**
     * Gets the WHERE ID clause for using {@link #scheduleUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the WHERE clause - null if not defined
     */
    @Nullable
    @Override
    public String getWhereId() {
        return WHERE_ID;
    }

    /**
     * Gets the TABLE NAME for using {@link #scheduleUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the TABLE NAME - null if not defined
     */
    @Nullable
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Obtains the text's field text
     *
     * @param textId ID of the text to obtain its data
     * @return {@code String} with the text
     */
    @Override
    public String getTextText(long textId) {
        String text = null;
        try (Cursor smallTextsCursor = get(TABLE_NAME, whereArgs(TEXT.getFieldName()), WHERE_ID,
                whereArgs(textId), null, null, ID.getFieldName() + " ASC")) {
            if (smallTextsCursor.moveToNext())
                text = smallTextsCursor.getString(TEXT.getFieldIndex());
        }
        return text;
    }

    /**
     * Obtains the text's description
     *
     * @param textId ID of the text to obtain its data
     * @return {@code String} with the description
     */
    @Override
    public String getTextDescription(long textId) {
        String description = null;
        try (Cursor smallTextsCursor = get(TABLE_NAME, whereArgs(DESCRIPTION.getFieldName()), WHERE_ID,
                whereArgs(textId), null, null, ID.getFieldName() + " ASC")) {
            if (smallTextsCursor.moveToNext())
                description = smallTextsCursor.getString(DESCRIPTION.getFieldIndex());
        }
        return description;
    }

    /**
     * Obtains the text's order
     *
     * @param textId ID of the text to obtain its data
     * @return {@code int} with the ordinal order
     */
    @Override
    public int getTextOrder(long textId) {
        int order = -1;
        try (Cursor smallTextsCursor = get(TABLE_NAME, whereArgs(ORDER.getFieldName()), WHERE_ID,
                whereArgs(textId), null, null, ID.getFieldName() + " ASC")) {
            if (smallTextsCursor.moveToNext())
                order = smallTextsCursor.getInt(ORDER.getFieldIndex());
        }
        return order;
    }

    /**
     * Obtains the text's entry ID
     *
     * @param textId ID of the text to obtain its data
     * @return {@code long} with the entry ID
     */
    @Override
    public long getTextEntryId(long textId) {
        long entryId = -1;
        try (Cursor smallTextsCursor = get(TABLE_NAME, whereArgs(ENTRY.getFieldName()), WHERE_ID,
                whereArgs(textId), null, null, ID.getFieldName() + " ASC")) {
            if (smallTextsCursor.moveToNext())
                entryId = smallTextsCursor.getLong(ENTRY.getFieldIndex());
        }
        return entryId;
    }

    /**
     * Registers a new simple small text
     *
     * @param text        text to store in the DB
     * @param description small text description
     * @param order       ordinal order
     * @param entryId     parent entry ID
     * @return {@code long} with the new small text ID
     */
    @Override
    public long registerNewSmallText(@NonNull String text,
                                     @NonNull String description,
                                     int order,
                                     long entryId) {
        ContentValues params = setParams(text, description, order, entryId);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates the current text by the provided one
     *
     * @param textId ID of the text field to change
     * @param text   new text
     */
    @Override
    public void updateTextText(long textId, @NonNull String text) {
        ContentValues params = new ContentValues(1);
        params.put(TEXT.getFieldName(), text);
        scheduleUpdateExecutor(textId, params);
    }

    /**
     * Updates the current text description
     *
     * @param textId      ID of the text to change
     * @param description new description
     */
    @Override
    public void updateTextDescription(long textId, @NonNull String description) {
        ContentValues params = new ContentValues(1);
        params.put(DESCRIPTION.getFieldName(), description);
        scheduleUpdateExecutor(textId, params);
    }

    /**
     * Updates the current text order
     *
     * @param textId ID of the text to change
     * @param order  new order
     */
    @Override
    public void updateTextOrder(long textId, int order) {
        ContentValues params = new ContentValues(1);
        params.put(ORDER.getFieldName(), order);
        scheduleUpdateExecutor(textId, params);
    }

    /**
     * Removes the text by using its ID
     *
     * @param textId ID of the text to remove
     */
    @Override
    public void removeText(long textId) {
        delete(TABLE_NAME, ID.getFieldName(), textId);
    }

    /**
     * Obtains all small texts' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link SmallText}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see ObjectContainer
     * @see SmallText
     */
    @Override
    public GeneralObjectContainer<SmallText> getAllSmallTexts() {
        GeneralObjectContainer<SmallText> smallTexts = new ObjectContainer<>();
        try (Cursor smallTextsCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            while (smallTextsCursor.moveToNext()) {
                long id = smallTextsCursor.getLong(ID.getFieldIndex());
                String text = smallTextsCursor.getString(TEXT.getFieldIndex());
                String description = smallTextsCursor.getString(DESCRIPTION.getFieldIndex());
                int order = smallTextsCursor.getInt(ORDER.getFieldIndex());
                long entryId = smallTextsCursor.getLong(ENTRY.getFieldIndex());
                SmallText currentSmallText = new SmallText(id, entryId, text, description);
                smallTexts.storeObject(currentSmallText);
            }
        }
        return smallTexts;
    }

    /**
     * Generates a map with the provided params
     *
     * @param text        text source
     * @param description text description
     * @param order       ordinal order
     * @param entryId     entry ID
     * @return {@code ContentValues} with the params
     * @see ContentValues
     */
    private ContentValues setParams(@NonNull String text,
                                    @NonNull String description,
                                    int order,
                                    long entryId) {
        ContentValues params = new ContentValues(4);
        params.put(TEXT.getFieldName(), text);
        params.put(DESCRIPTION.getFieldName(), description);
        params.put(ORDER.getFieldName(), order);
        params.put(ENTRY.getFieldName(), entryId);
        return params;
    }
}
