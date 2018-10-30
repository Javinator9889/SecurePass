package javinator9889.securepass.io.database.operations.entry;

import android.content.ContentValues;

import net.sqlcipher.Cursor;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.configuration.Configuration;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.threading.ThreadingExecutor;
import javinator9889.securepass.util.threading.thread.NotifyingThread;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.Constants.SQL.ENTRY;

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
    private static final String NAME = ENTRY.E_NAME;
    private static final String ID = ENTRY.E_ID;
    private static final String ICON = ENTRY.E_ICON;
    private static final String CATEGORY = ENTRY.E_PARENT_CATEGORY;
    private static final String CONFIGURATION = ENTRY.E_PARENT_CONFIGURATION;
    private static final String ENTRY_WHERE_ID = Constants.SQL.DB_UPDATE_ENTRY_WHERE_CLAUSE;
    private ThreadingExecutor mExecutor;

    /**
     * Available constructor, matching
     * {@link CommonOperations#CommonOperations(DatabaseManager) super} one
     *
     * @param databaseManager instance of the {@link DatabaseManager} object
     * @see DatabaseManager
     */
    public EntryOperations(@NonNull DatabaseManager databaseManager) {
        super(databaseManager);
        mExecutor = ThreadingExecutor.Builder().build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTag() {
        return TAG;
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
     * Runs an update operation by using the given ID and new values
     *
     * @param entryId ID where changing values
     * @param params  new values
     */
    private void runUpdateExecutor(long entryId, @NonNull ContentValues params) {
        mExecutor.add(new NotifyingThread() {
            @Override
            public void doRun() {
                update(TABLE_NAME, params, ENTRY_WHERE_ID, whereArgs(entryId));
            }
        });
        mExecutor.run();
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
        params.put(NAME, name);
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
        params.put(ICON, icon);
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
        params.put(CATEGORY, categoryId);
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
        params.put(CONFIGURATION, configurationId);
        runUpdateExecutor(entryId, params);
    }

    /**
     * Removes the hole entry by the given ID
     *
     * @param entryId entry ID
     */
    @Override
    public void removeEntry(long entryId) {
        delete(TABLE_NAME, ID, entryId);
    }

    private ContentValues setParams(@NonNull String entryName,
                                    @NonNull String entryIcon,
                                    long categoryId,
                                    long configurationId) {
        ContentValues params = new ContentValues(4);
        params.put(NAME, entryName);
        params.put(ICON, entryIcon);
        params.put(CATEGORY, categoryId);
        params.put(CONFIGURATION, configurationId);
        return params;
    }

    private String[] whereArgs(@NonNull Object... args) {
        return Arrays.copyOf(args, args.length, String[].class);
    }

    /**
     * Obtains the entry name by using the given ID
     *
     * @param entryId entry ID
     * @return {@code String} with the entry name - null if ID does not exists
     */
    @Override
    @Nullable
    public String getEntryName(long entryId) {
        String name = null;
        try (Cursor entryCursor = get(TABLE_NAME, whereArgs(NAME), ENTRY_WHERE_ID, whereArgs
                        (entryId),
                null, null, ID + " ASC")) {
            if (entryCursor.moveToNext())
                name = entryCursor.getString(2);
        }
        return name;
    }

    /**
     * Obtains the entry icon by using the given ID
     *
     * @param entryId entry ID
     * @return {@code String} with the entry icon
     */
    @Override
    public String getEntryIcon(long entryId) {
        return null;
    }

    /**
     * Obtains the entry parent category by using the given ID
     *
     * @param entryId entry ID
     * @return {@code long} with the category ID
     * @see Category
     */
    @Override
    public long getEntryCategory(long entryId) {
        return 0;
    }

    /**
     * Obtains the entry configuration by using the given ID
     *
     * @param entryId entry ID
     * @return {@code long} with the configuration ID
     * @see Configuration
     */
    @Override
    public long getEntryConfiguration(long entryId) {
        return 0;
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
        return null;
    }
}
