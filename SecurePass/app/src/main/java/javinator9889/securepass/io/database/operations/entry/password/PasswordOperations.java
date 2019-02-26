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
package javinator9889.securepass.io.database.operations.entry.password;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.fields.Password;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.PasswordFields;

/**
 * All the operations for the password class.
 */
public class PasswordOperations extends CommonOperations implements IPasswordSetOperations,
        IPasswordGetOperations {
    private static final String TAG = "Password Operations";
    private static final String TABLE_NAME = Constants.SQL.PASSWORD.NAME;
    private static final String ID = PasswordFields.ID;
    private static final String PASSWORD = PasswordFields.PASSWORD;
    private static final String DESCRIPTION = PasswordFields.DESCRIPTION;
    private static final String ORDER = PasswordFields.ORDER;
    private static final String ENTRY = PasswordFields.ENTRY;
    private static final String WHERE_ID = ID + "=?";

    /**
     * Available constructor, matching
     * {@link CommonOperations#CommonOperations(DatabaseManager) super} one
     *
     * @param databaseInstance    instance of the {@link DatabaseManager} object
     * @see DatabaseManager
     */
    public PasswordOperations(@NonNull DatabaseManager databaseInstance) {
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
     * Obtains the stored password
     *
     * @param passwordId ID of the password in the DB
     * @return {@code String} with the password
     */
    @Override
    public String getPasswordPassword(long passwordId) {
        String password = null;
        try (Cursor passwordCursor = get(TABLE_NAME, whereArgs(PASSWORD), WHERE_ID,
                whereArgs(passwordId), null, null, ID + " ASC")) {
            Map<String, Integer> passwordColumns = constructMapFromCursor(passwordCursor);
            if (passwordCursor.moveToNext())
                password = passwordCursor.getString(passwordColumns.get(PASSWORD));
        }
        return password;
    }

    /**
     * Obtains the password description
     *
     * @param passwordId ID of the password in DB
     * @return {@code String} with the description
     */
    @Override
    public String getPasswordDescription(long passwordId) {
        String description = null;
        try (Cursor passwordCursor = get(TABLE_NAME, whereArgs(DESCRIPTION), WHERE_ID,
                whereArgs(passwordId), null, null, ID + " ASC")) {
            Map<String, Integer> passwordColumns = constructMapFromCursor(passwordCursor);
            if (passwordCursor.moveToNext())
                description = passwordCursor.getString(passwordColumns.get(DESCRIPTION));
        }
        return description;
    }

    /**
     * Obtains the password ordinal order
     *
     * @param passwordId ID of the password in DB
     * @return {@code int} with the order
     */
    @Override
    public int getPasswordOrder(long passwordId) {
        int order = -1;
        try (Cursor passwordCursor = get(TABLE_NAME, whereArgs(ORDER), WHERE_ID,
                whereArgs(passwordId), null, null, ID + " ASC")) {
            Map<String, Integer> passwordColumns = constructMapFromCursor(passwordCursor);
            if (passwordCursor.moveToNext())
                order = passwordCursor.getInt(passwordColumns.get(ORDER));
        }
        return order;
    }

    /**
     * Obtains the password parent entry ID
     *
     * @param passwordId ID of the password in DB
     * @return {@code long} with the entry ID
     */
    @Override
    public long getPasswordEntryId(long passwordId) {
        long entryId = -1;
        try (Cursor passwordCursor = get(TABLE_NAME, whereArgs(ENTRY), WHERE_ID,
                whereArgs(passwordId), null, null, ID + " ASC")) {
            Map<String, Integer> passwordColumns = constructMapFromCursor(passwordCursor);
            if (passwordCursor.moveToNext())
                entryId = passwordCursor.getLong(passwordColumns.get(ENTRY));
        }
        return entryId;
    }

    /**
     * Obtains all entries' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link Password}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see ObjectContainer
     * @see Password
     */
    @Override
    public GeneralObjectContainer<Password> getAllPasswords() {
        GeneralObjectContainer<Password> passwords = new ObjectContainer<>();
        try (Cursor passwordCursor = getAll(TABLE_NAME, ID + " ASC")) {
            Map<String, Integer> passwordColumns = constructMapFromCursor(passwordCursor);
            while (passwordCursor.moveToNext()) {
                long id = passwordCursor.getLong(passwordColumns.get(ID));
                long entryId = passwordCursor.getLong(passwordColumns.get(ENTRY));
                String password =
                        passwordCursor.getString(passwordColumns.get(PASSWORD));
                String description =
                        passwordCursor.getString(passwordColumns.get(DESCRIPTION));
                Password currentPassword = new Password(id, entryId, password, description);
                passwords.storeObject(currentPassword);
            }
        }
        return passwords;
    }

    /**
     * Registers a new simple password entry
     *
     * @param password    password to save
     * @param description current entry description
     * @param order       ordinal order when showing it on UI
     * @param entryId     parent entry ID
     * @return {@code long} with the new password ID
     */
    @Override
    public long registerNewPassword(@NonNull String password,
                                    @NonNull String description,
                                    int order,
                                    long entryId) {
        ContentValues params = setParams(password, description, order, entryId);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates the saved password by using the given ID
     *
     * @param passwordId ID of the entry to change
     * @param password   new password to save
     */
    @Override
    public void updatePassword(long passwordId, @NonNull String password) {
        ContentValues params = new ContentValues(1);
        params.put(PASSWORD, password);
        scheduleUpdateExecutor(passwordId, params);
    }

    /**
     * Updates the password description by using the given ID
     *
     * @param passwordId  ID of the entry to change
     * @param description new description
     */
    @Override
    public void updateDescription(long passwordId, @NonNull String description) {
        ContentValues params = new ContentValues(1);
        params.put(DESCRIPTION, description);
        scheduleUpdateExecutor(passwordId, params);
    }

    /**
     * Updates the password order inside the entry
     *
     * @param passwordId ID of the password to change
     * @param order      new order
     */
    @Override
    public void updateSortOrder(long passwordId, int order) {
        ContentValues params = new ContentValues(1);
        params.put(ORDER, order);
        scheduleUpdateExecutor(passwordId, params);
    }

    /**
     * Removes the hole password by using the given ID
     *
     * @param passwordId password ID to remove
     */
    @Override
    public void removePassword(long passwordId) {
        delete(TABLE_NAME, ID, passwordId);
    }

    /**
     * Generates a map with the provided params
     *
     * @param password    password
     * @param description password description
     * @param order       ordinal order
     * @param entryId     entry ID
     * @return {@code ContentValues} with the params
     * @see ContentValues
     */
    private ContentValues setParams(@NonNull String password,
                                    @NonNull String description,
                                    int order,
                                    long entryId) {
        ContentValues params = new ContentValues(4);
        params.put(PASSWORD, password);
        params.put(DESCRIPTION, description);
        params.put(ORDER, order);
        params.put(ENTRY, entryId);
        return params;
    }
}
