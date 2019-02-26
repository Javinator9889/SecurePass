/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 02/11/2018 - SecurePass.
 */
package javinator9889.securepass.io.database.operations.entry.text.longtext;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.fields.LongText;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.io.database.operations.entry.text.ITextSetOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.LongTextFields;

/**
 * All the common operations for the text classes.
 */
public class LongTextOperations extends CommonOperations implements ITextSetOperations,
        ILongTextGetOperations {
    private static final String TAG = "LongText Operations";
    private static final String TABLE_NAME = Constants.SQL.LONG_TEXT.NAME;
    private static final String ID = LongTextFields.ID;
    private static final String TEXT = LongTextFields.TEXT;
    private static final String DESCRIPTION = LongTextFields.DESCRIPTION;
    private static final String ORDER = LongTextFields.ORDER;
    private static final String ENTRY = LongTextFields.ENTRY;
    private static final String WHERE_ID = ID + "=?";

    /**
     * Available constructor, matching {@link CommonOperations#CommonOperations(DatabaseManager)
     * super} one
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object
     *
     * @see DatabaseManager
     */
    public LongTextOperations(@NonNull DatabaseManager databaseInstance) {
        super(databaseInstance);
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
     * Gets the TABLE NAME for using {@link #scheduleUpdateExecutor(long, ContentValues)} - should
     * be overridden
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
     *
     * @return {@code String} with the text
     */
    @Override
    public String getTextText(long textId) {
        String text = null;
        try (Cursor longTextsCursor = get(TABLE_NAME, whereArgs(ID), WHERE_ID,
                whereArgs(textId), null, null, ID + " ASC")) {
            Map<String, Integer> longTextColumns = constructMapFromCursor(longTextsCursor);
            if (longTextsCursor.moveToNext())
                text = longTextsCursor.getString(longTextColumns.get(TEXT));
        }
        return text;
    }

    /**
     * Obtains the text's description
     *
     * @param textId ID of the text to obtain its data
     *
     * @return {@code String} with the description
     */
    @Override
    public String getTextDescription(long textId) {
        String description = null;
        try (Cursor longTextsCursor = get(TABLE_NAME, whereArgs(DESCRIPTION),
                WHERE_ID, whereArgs(textId), null, null, ID + " ASC")) {
            Map<String, Integer> longTextColumns = constructMapFromCursor(longTextsCursor);
            if (longTextsCursor.moveToNext())
                description = longTextsCursor.getString(longTextColumns.get(DESCRIPTION));
        }
        return description;
    }

    /**
     * Obtains the text's order
     *
     * @param textId ID of the text to obtain its data
     *
     * @return {@code int} with the ordinal order
     */
    @Override
    public int getTextOrder(long textId) {
        int order = -1;
        try (Cursor longTextsCursor = get(TABLE_NAME, whereArgs(ORDER), WHERE_ID,
                whereArgs(textId), null, null, ID + " ASC")) {
            Map<String, Integer> longTextColumns = constructMapFromCursor(longTextsCursor);
            if (longTextsCursor.moveToNext())
                order = longTextsCursor.getInt(longTextColumns.get(ORDER));
        }
        return order;
    }

    /**
     * Obtains the text's entry ID
     *
     * @param textId ID of the text to obtain its data
     *
     * @return {@code long} with the entry ID
     */
    @Override
    public long getTextEntryId(long textId) {
        long entryId = -1;
        try (Cursor longTextsCursor = get(TABLE_NAME, whereArgs(ENTRY), WHERE_ID,
                whereArgs(textId), null, null, ID + " ASC")) {
            Map<String, Integer> longTextColumns = constructMapFromCursor(longTextsCursor);
            if (longTextsCursor.moveToNext())
                entryId = longTextsCursor.getLong(longTextColumns.get(ENTRY));
        }
        return entryId;
    }

    /**
     * Registers a new simple long text
     *
     * @param text        text to store in the DB
     * @param description long text description
     * @param order       ordinal order
     * @param entryId     parent entry ID
     *
     * @return {@code long} with the new long text ID
     */
    @Override
    public long registerNewText(@NonNull String text,
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
        params.put(TEXT, text);
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
        params.put(DESCRIPTION, description);
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
        params.put(ORDER, order);
        scheduleUpdateExecutor(textId, params);
    }

    /**
     * Removes the text by using its ID
     *
     * @param textId ID of the text to remove
     */
    @Override
    public void removeText(long textId) {
        delete(TABLE_NAME, ID, textId);
    }

    /**
     * Obtains all long texts' data and saves it inside a {@link GeneralObjectContainer} of {@link
     * LongText}
     *
     * @return {@code GeneralObjectContainer} of entries
     *
     * @see ObjectContainer
     * @see LongText
     */
    @Override
    public GeneralObjectContainer<LongText> getAllLongTexts() {
        GeneralObjectContainer<LongText> longTexts = new ObjectContainer<>();
        try (Cursor longTextsCursor = getAll(TABLE_NAME, ID + " ASC")) {
            Map<String, Integer> longTextColumns = constructMapFromCursor(longTextsCursor);
            while (longTextsCursor.moveToNext()) {
                long id = longTextsCursor.getLong(longTextColumns.get(ID));
                String text = longTextsCursor.getString(longTextColumns.get(TEXT));
                String description =
                        longTextsCursor.getString(longTextColumns.get(DESCRIPTION));
                int order = longTextsCursor.getInt(longTextColumns.get(ORDER));
                long entryId = longTextsCursor.getLong(longTextColumns.get(ENTRY));
                LongText currentLongText = new LongText(id, entryId, text, description, order);
                longTexts.storeObject(currentLongText);
            }
        }
        return longTexts;
    }

    /**
     * Generates a map with the provided params
     *
     * @param text        text source
     * @param description text description
     * @param order       ordinal order
     * @param entryId     entry ID
     *
     * @return {@code ContentValues} with the params
     *
     * @see ContentValues
     */
    private ContentValues setParams(@NonNull String text,
                                    @NonNull String description,
                                    int order,
                                    long entryId) {
        ContentValues params = new ContentValues(4);
        params.put(TEXT, text);
        params.put(DESCRIPTION, description);
        params.put(ORDER, order);
        params.put(ENTRY, entryId);
        return params;
    }
}
