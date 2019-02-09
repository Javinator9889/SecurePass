/*
 * Copyright © 2019 - present | SecurePass by Javinator9889
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
 * Created by Javinator9889 on 02/02/2019 - SecurePass.
 */
package javinator9889.securepass.io.database.operations.entry.qrcode;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.QRCodeFields;

/**
 * All the operations for the QRCode class.
 */
public class QRCodeOperations extends CommonOperations implements
        IQRCodeSetOperations, IQRCodeGetOperations {
    private static final String TAG = "Entry Operations";
    private static final String TABLE_NAME = Constants.SQL.QR_CODE.NAME;
    private static final QRCodeFields NAME = QRCodeFields.NAME;
    private static final QRCodeFields ID = QRCodeFields.ID;
    private static final QRCodeFields DESCRIPTION = QRCodeFields.DESCRIPTION;
    private static final QRCodeFields DATA = QRCodeFields.DATA;
    private static final QRCodeFields ENTRY = QRCodeFields.ENTRY;
    private static final String QRCODE_WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Public constructor for creating this class - use this instead of {@link
     * #newInstance(DatabaseManager)}.
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object.
     * @see DatabaseManager
     */
    public QRCodeOperations(@NonNull DatabaseManager databaseInstance) {
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
        return QRCODE_WHERE_ID;
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
     * Obtains the QRCode name by using the given ID.
     *
     * @param id the QRCode ID.
     * @return {@code String} with the name.
     */
    @Override
    public String getQRCodeName(long id) {
        String name = null;
        try (Cursor qrCodeCursor = get(TABLE_NAME, whereArgs(NAME.getFieldName()),
                QRCODE_WHERE_ID, whereArgs(id), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> qrCodeColumns = constructMapFromCursor(qrCodeCursor);
            if (qrCodeCursor.moveToNext()) {
                name = qrCodeCursor.getString(qrCodeColumns.get(NAME.getFieldName()));
            }
        }
        return name;
    }

    /**
     * Obtains the QRCode description by using the given ID - can be {@code null}.
     *
     * @param id the QRCode ID.
     * @return {@code String} with the description.
     */
    @Nullable
    @Override
    public String getQRCodeDescription(long id) {
        String description = null;
        try (Cursor qrCodeCursor = get(TABLE_NAME, whereArgs(DESCRIPTION.getFieldName()),
                QRCODE_WHERE_ID, whereArgs(id), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> qrCodeColumns = constructMapFromCursor(qrCodeCursor);
            if (qrCodeCursor.moveToNext()) {
                description = qrCodeCursor
                        .getString(qrCodeColumns.get(DESCRIPTION.getFieldName()));
            }
        }
        return description;
    }

    /**
     * Obtains the QRCode data by using the given ID.
     *
     * @param id the QRCode ID.
     * @return {@code String} with the data.
     */
    @Override
    public String getQRCodeData(long id) {
        String qrCodeData = null;
        try (Cursor qrCodeCursor = get(TABLE_NAME, whereArgs(DATA.getFieldName()),
                QRCODE_WHERE_ID, whereArgs(id), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> qrCodeColumns = constructMapFromCursor(qrCodeCursor);
            if (qrCodeCursor.moveToNext()) {
                qrCodeData = qrCodeCursor
                        .getString(qrCodeColumns.get(DATA.getFieldName()));
            }
        }
        return qrCodeData;
    }

    /**
     * Obtains the QRCode entry ID by using the QRCode ID.
     *
     * @param id the QRCode ID.
     * @return {@code long} with the entry ID.
     */
    @Override
    public long getQRCodeEntryID(long id) {
        long entryId = -1;
        try (Cursor qrCodeCursor = get(TABLE_NAME, whereArgs(ENTRY.getFieldName()),
                QRCODE_WHERE_ID, whereArgs(id), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> qrCodeColumns = constructMapFromCursor(qrCodeCursor);
            if (qrCodeCursor.moveToNext()) {
                entryId = qrCodeCursor.getLong(qrCodeColumns.get(ENTRY.getFieldName()));
            }
        }
        return entryId;
    }

    /**
     * Obtains all entries' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link QRCode}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see ObjectContainer
     * @see QRCode
     */
    @Override
    public GeneralObjectContainer<QRCode> getAllQRCodes() {
        GeneralObjectContainer<QRCode> results = new ObjectContainer<>();
        try (Cursor qrCodes = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            Map<String, Integer> qrCodesColumns = constructMapFromCursor(qrCodes);
            while (qrCodes.moveToNext()) {
                long id = qrCodes.getLong(qrCodesColumns.get(ID.getFieldName()));
                String name = qrCodes.getString(qrCodesColumns.get(NAME.getFieldName()));
                String description = qrCodes
                        .getString(qrCodesColumns.get(DESCRIPTION.getFieldName()));
                String data = qrCodes.getString(qrCodesColumns.get(DATA.getFieldName()));
                long entryId = qrCodes.getLong(qrCodesColumns.get(ENTRY.getFieldName()));
                QRCode currentCode = new QRCode(id, name, description, data, entryId);
                results.storeObject(currentCode);
            }
        }
        return results;
    }

    /**
     * Registers a new QRCode on the database.
     *
     * @param entryId     parent entry ID.
     * @param name        the name for the QRCode.
     * @param description the optional description for the QRCode - can be {@code null}.
     * @param qrCodeData  the data of the QR.
     * @return {@code long} with the created row ID.
     */
    @Override
    public long registerNewQRCode(long entryId,
                                  @NonNull String name,
                                  @Nullable String description,
                                  @NonNull String qrCodeData) {
        ContentValues params = setParams(entryId, name, description, qrCodeData);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates the QRCode name by using the given ID.
     *
     * @param id   the QRCode ID.
     * @param name the new name.
     */
    @Override
    public void updateName(long id, @NonNull String name) {
        ContentValues params = new ContentValues(1);
        params.put(NAME.getFieldName(), name);
        scheduleUpdateExecutor(id, params);
    }

    /**
     * Updates the QRCode description by using the given ID.
     *
     * @param id          the QRCode ID.
     * @param description the new description - can be {@code null}.
     */
    @Override
    public void updateDescription(long id, @Nullable String description) {
        ContentValues params = new ContentValues(1);
        params.put(DESCRIPTION.getFieldName(), description);
        scheduleUpdateExecutor(id, params);
    }

    /**
     * Updates the QRCode data by using the given ID.
     *
     * @param id         the QRCode ID.
     * @param qrCodeData the new QRCode data.
     */
    @Override
    public void updateQrCodeData(long id, @NonNull String qrCodeData) {
        ContentValues params = new ContentValues(1);
        params.put(DATA.getFieldName(), qrCodeData);
        scheduleUpdateExecutor(id, params);
    }

    /**
     * Removes the QRCode by using the given ID.
     *
     * @param id the QRCode row to delete.
     */
    @Override
    public void removeQRCode(long id) {
        delete(TABLE_NAME, ID.getFieldName(), id);
    }

    /**
     * Generates a map with the provided params.
     *
     * @param entryId       entry ID.
     * @param name       QRCode name.
     * @param description      QRCode description - can be {@code null}.
     * @param data QRCode data.
     *
     * @return {@code ContentValues} with the params
     *
     * @see ContentValues
     */
    private ContentValues setParams(long entryId,
                                    @NonNull String name,
                                    @Nullable String description,
                                    @NonNull String data) {
        ContentValues params = new ContentValues(4);
        params.put(ENTRY.getFieldName(), entryId);
        params.put(NAME.getFieldName(), name);
        params.put(DESCRIPTION.getFieldName(), description);
        params.put(DATA.getFieldName(), data);
        return params;
    }
}
