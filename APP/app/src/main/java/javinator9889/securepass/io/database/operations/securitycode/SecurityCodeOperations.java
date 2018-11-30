package javinator9889.securepass.io.database.operations.securitycode;

import android.content.ContentValues;

import net.sqlcipher.Cursor;

import androidx.annotation.NonNull;
import javinator9889.securepass.data.secret.SecurityCode;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.SecurityCodesFields;

/**
 * Copyright © 2018 - present | APP by Javinator9889
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 * <p>
 * Created by Javinator9889 on 01/11/2018 - APP.
 */
public class SecurityCodeOperations extends CommonOperations implements
        ISecurityCodeSetOperations, ISecurityCodeGetOperations {
    private static final String TAG = "Security Codes Operations";
    private static final String TABLE_NAME = Constants.SQL.SECURITY_CODE.NAME;
    private static final SecurityCodesFields ID = SecurityCodesFields.ID;
    private static final SecurityCodesFields NAME = SecurityCodesFields.NAME;
    private static final String WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Available constructor, matching {@link CommonOperations#CommonOperations(DatabaseManager)
     * super} one
     *
     * @param databaseManager instance of the {@link DatabaseManager} object
     *
     * @see DatabaseManager
     */
    public SecurityCodeOperations(@NonNull DatabaseManager databaseManager) {
        super(databaseManager);
    }

    /**
     * {@inheritDoc}
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
    @NonNull
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
    @NonNull
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Obtains the security code name by using the given ID
     *
     * @param securityCodeId ID where obtaining the name
     *
     * @return {@code String} with the name
     */
    @Override
    public String getSecurityCodeName(long securityCodeId) {
        String name = null;
        try (Cursor securityCodesCursor = get(TABLE_NAME, whereArgs(NAME.getFieldName()),
                WHERE_ID, whereArgs(securityCodeId), null, null, ID.getFieldName() + " ASC")) {
            if (securityCodesCursor.moveToNext())
                name = securityCodesCursor.getString(NAME.getFieldIndex());
        }
        return name;
    }

    /**
     * Obtains all security codes' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link SecurityCode}
     *
     * @return {@code GeneralObjectContainer} of security codes
     *
     * @see ObjectContainer
     * @see SecurityCode
     */
    @Override
    public GeneralObjectContainer<SecurityCode> getAllSecurityCodes() {
        GeneralObjectContainer<SecurityCode> securityCodes = new ObjectContainer<>();
        try (Cursor securityCodesCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            while (securityCodesCursor.moveToNext()) {
                long id = securityCodesCursor.getLong(ID.getFieldIndex());
                String name = securityCodesCursor.getString(NAME.getFieldIndex());
                SecurityCode currentSecurityCode = new SecurityCode(id, name);
                securityCodes.storeObject(currentSecurityCode);
            }
        }
        return securityCodes;
    }

    /**
     * Registers a new simple security code
     *
     * @param securityCodeName security code name
     *
     * @return {@code long} with the ID
     */
    @Override
    public long registerNewSecurityCode(@NonNull String securityCodeName) {
        ContentValues params = setParams(securityCodeName);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates security code name
     *
     * @param securityCodeId security code ID where changing name
     * @param newName        new name
     */
    @Override
    public void updateName(long securityCodeId, @NonNull String newName) {
        ContentValues params = setParams(newName);
        scheduleUpdateExecutor(securityCodeId, params);
    }

    /**
     * Removes the security code with its fields by using the provided ID
     *
     * @param securityCodeId ID to remove
     */
    @Override
    public void removeSecurityCode(long securityCodeId) {
        delete(TABLE_NAME, ID.getFieldName(), securityCodeId);
    }

    /**
     * Generates a map with the provided params
     *
     * @param securityCodeName category name
     *
     * @return {@code ContentValues} with the params
     */
    private ContentValues setParams(@NonNull String securityCodeName) {
        ContentValues params = new ContentValues(1);
        params.put(NAME.getFieldName(), securityCodeName);
        return params;
    }
}