package javinator9889.securepass.io.database.operations.entry;

import android.content.ContentValues;

import net.sqlcipher.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.configuration.Configuration;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.threading.ThreadExceptionListener;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.Constants.SQL.ENTRY;
import javinator9889.securepass.util.values.database.EntryFields;

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
 * Created by Javinator9889 on 28/10/2018 - APP.
 */
public class EntryOperations extends CommonOperations implements
        IEntrySetOperations, IEntryGetOperations {
    private static final String TAG = "Entry Operations";
    private static final String TABLE_NAME = ENTRY.NAME;
    private static final EntryFields NAME = EntryFields.NAME;
    private static final EntryFields ID = EntryFields.ID;
    private static final EntryFields ICON = EntryFields.ICON;
    private static final EntryFields CATEGORY = EntryFields.CATEGORY;
    private static final EntryFields CONFIGURATION = EntryFields.CONFIGURATION;
    private static final String ENTRY_WHERE_ID = Constants.SQL.DB_UPDATE_ENTRY_WHERE_CLAUSE;

    /**
     * Available constructor, matching
     * {@link CommonOperations#CommonOperations(DatabaseManager, ThreadExceptionListener) super} one
     *
     * @param databaseManager     instance of the {@link DatabaseManager} object
     * @param onExceptionListener class that implements {@link ThreadExceptionListener} interface
     *                            - can be null if no listener is set up
     * @see DatabaseManager
     * @see ThreadExceptionListener
     */
    public EntryOperations(@NonNull DatabaseManager databaseManager,
                           @Nullable ThreadExceptionListener onExceptionListener) {
        super(databaseManager, onExceptionListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTag() {
        return TAG;
    }

    /**
     * Gets the WHERE ID clause for using {@link #runUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the WHERE clause - null if not defined
     */
    @NonNull
    @Override
    public String getWhereId() {
        return ENTRY_WHERE_ID;
    }

    /**
     * Gets the TABLE NAME for using {@link #runUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the TABLE NAME - null if not defined
     */
    @NonNull
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Registers a new simple entry
     *
     * @param parentCategoryId category ID
     * @param configId         configuration ID
     * @param entryName        entry name
     * @param icon             entry icon
     * @return <code>long</code> with the new entry ID
     * @see Category
     * @see Configuration
     */
    @Override
    public long registerNewEntry(long parentCategoryId,
                                 long configId,
                                 @NonNull String entryName,
                                 @NonNull String icon) {
        ContentValues params = setParams(entryName, icon, parentCategoryId, configId);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates entry name
     *
     * @param entryId entry ID
     * @param name    new name
     */
    @Override
    public void updateName(long entryId,
                           @NonNull String name) {
        ContentValues params = new ContentValues(1);
        params.put(NAME.getFieldName(), name);
        runUpdateExecutor(entryId, params);
    }

    /**
     * Updates entry icon
     *
     * @param entryId entry ID
     * @param icon    new icon
     */
    @Override
    public void updateIcon(long entryId,
                           @NonNull String icon) {
        ContentValues params = new ContentValues(1);
        params.put(ICON.getFieldName(), icon);
        runUpdateExecutor(entryId, params);
    }

    /**
     * Updates entry category
     *
     * @param entryId    entry ID
     * @param categoryId new category ID
     */
    @Override
    public void updateCategory(long entryId,
                               long categoryId) {
        ContentValues params = new ContentValues(1);
        params.put(CATEGORY.getFieldName(), categoryId);
        runUpdateExecutor(entryId, params);
    }

    /**
     * Updates entry configuration
     *
     * @param entryId         entry ID
     * @param configurationId new configuration ID
     */
    @Override
    public void updateConfiguration(long entryId,
                                    long configurationId) {
        ContentValues params = new ContentValues(1);
        params.put(CONFIGURATION.getFieldName(), configurationId);
        runUpdateExecutor(entryId, params);
    }

    /**
     * Removes the hole entry by the given ID
     *
     * @param entryId entry ID
     */
    @Override
    public void removeEntry(long entryId) {
        delete(TABLE_NAME, ID.getFieldName(), entryId);
    }

    /**
     * Generates a map with the provided params
     *
     * @param entryName       entry name
     * @param entryIcon       entry icon
     * @param categoryId      category ID
     * @param configurationId configuration ID
     * @return {@code ContentValues} with the params
     * @see ContentValues
     */
    private ContentValues setParams(@NonNull String entryName,
                                    @NonNull String entryIcon,
                                    long categoryId,
                                    long configurationId) {
        ContentValues params = new ContentValues(4);
        params.put(NAME.getFieldName(), entryName);
        params.put(ICON.getFieldName(), entryIcon);
        params.put(CATEGORY.getFieldName(), categoryId);
        params.put(CONFIGURATION.getFieldName(), configurationId);
        return params;
    }

    /**
     * Obtains the entry name by using the given ID
     *
     * @param entryId entry ID
     * @return {@code String} with the entry name - null if ID does not exist
     */
    @Override
    @Nullable
    public String getEntryName(long entryId) {
        String name = null;
        try (Cursor entryCursor = get(TABLE_NAME, whereArgs(NAME.getFieldName()), ENTRY_WHERE_ID,
                whereArgs(entryId), null, null, ID.getFieldName() + " ASC")) {
            if (entryCursor.moveToNext())
                name = entryCursor.getString(NAME.getFieldIndex());
        }
        return name;
    }

    /**
     * Obtains the entry icon by using the given ID
     *
     * @param entryId entry ID
     * @return {@code String} with the entry icon - null if ID does not exist
     */
    @Override
    @Nullable
    public String getEntryIcon(long entryId) {
        String icon = null;
        try (Cursor iconCursor = get(TABLE_NAME, whereArgs(ICON.getFieldName()), ENTRY_WHERE_ID,
                whereArgs(entryId), null, null, ID.getFieldName() + " ASC")) {
            if (iconCursor.moveToNext())
                icon = iconCursor.getString(ICON.getFieldIndex());
        }
        return icon;
    }

    /**
     * Obtains the entry parent category by using the given ID
     *
     * @param entryId entry ID
     * @return {@code long} with the category ID - 0 if ID does not exist
     * @see Category
     */
    @Override
    public long getEntryCategory(long entryId) {
        long id = 0;
        try (Cursor categoryCursor = get(TABLE_NAME, whereArgs(CATEGORY.getFieldName()),
                ENTRY_WHERE_ID, whereArgs(entryId), null, null,
                ID.getFieldName() + " ASC")) {
            if (categoryCursor.moveToNext())
                id = categoryCursor.getLong(CATEGORY.getFieldIndex());
        }
        return id;
    }

    /**
     * Obtains the entry configuration by using the given ID
     *
     * @param entryId entry ID
     * @return {@code long} with the configuration ID - 0 if ID does not exist
     * @see Configuration
     */
    @Override
    public long getEntryConfiguration(long entryId) {
        long id = 0;
        try (Cursor configCursor = get(TABLE_NAME, whereArgs(CONFIGURATION.getFieldName()),
                ENTRY_WHERE_ID, whereArgs(entryId), null, null,
                ID.getFieldName() + " ASC")) {
            if (configCursor.moveToNext())
                id = configCursor.getLong(CONFIGURATION.getFieldIndex());
        }
        return id;
    }

    /**
     * Obtains all entries' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link Entry}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see ObjectContainer
     * @see Entry
     */
    @Override
    public GeneralObjectContainer<Entry> getAllEntries() {
        GeneralObjectContainer<Entry> entries = new ObjectContainer<>();
        try (Cursor entriesCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            while (entriesCursor.moveToNext()) {
                long id = entriesCursor.getLong(ID.getFieldIndex());
                String name = entriesCursor.getString(NAME.getFieldIndex());
                String icon = entriesCursor.getString(ICON.getFieldIndex());
                long categoryId = entriesCursor.getLong(CATEGORY.getFieldIndex());
                long configurationId = entriesCursor.getLong(CONFIGURATION.getFieldIndex());
                Entry currentEntry = new Entry(id, name, icon, categoryId, configurationId);
                entries.storeObject(currentEntry);
            }
        }
        return entries;
    }
}
