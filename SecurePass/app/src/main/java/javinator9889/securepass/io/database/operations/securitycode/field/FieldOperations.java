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
package javinator9889.securepass.io.database.operations.securitycode.field;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.FieldsFields;

/**
 * TODO
 */
public class FieldOperations extends CommonOperations implements IFieldSetOperations,
        IFieldGetOperations {
    private static final String TAG = "Fields Operations";
    private static final String TABLE_NAME = Constants.SQL.FIELD.NAME;
    private static final FieldsFields ID = FieldsFields.ID;
    private static final FieldsFields CODE = FieldsFields.CODE;
    private static final FieldsFields USED = FieldsFields.USED;
    private static final FieldsFields SECURITY_CODE = FieldsFields.SECURITY_CODES;
    private static final String WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Available constructor, matching
     * {@link CommonOperations#CommonOperations(DatabaseManager) super} one
     *
     * @param databaseManager instance of the {@link DatabaseManager} object
     * @see DatabaseManager
     */
    public FieldOperations(@NonNull DatabaseManager databaseManager) {
        super(databaseManager);
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
     * Obtains the field's code by using the provided ID
     *
     * @param fieldId ID of the field where obtaining the code
     * @return {@code String} with the code
     */
    @Override
    public String getFieldCode(long fieldId) {
        String code = null;
        try (Cursor fieldsCursor = get(TABLE_NAME, whereArgs(CODE.getFieldName()), WHERE_ID,
                whereArgs(fieldId), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> fieldsColumns = constructMapFromCursor(fieldsCursor);
            if (fieldsCursor.moveToNext())
                code = fieldsCursor.getString(fieldsColumns.get(CODE.getFieldName()));
        }
        return code;
    }

    /**
     * Obtains if the field's code has been used
     *
     * @param fieldId ID of the field where obtaining the code
     * @return {@code boolean} with the value
     */
    @Override
    public boolean getFieldCodeBeenUsed(long fieldId) {
        boolean isCodeUsed = false;
        try (Cursor fieldsCursor = get(TABLE_NAME, whereArgs(USED.getFieldName()), WHERE_ID,
                whereArgs(fieldId), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> fieldsColums = constructMapFromCursor(fieldsCursor);
            if (fieldsCursor.moveToNext())
                isCodeUsed = fieldsCursor.getInt(fieldsColums.get(USED.getFieldName())) != 0;
        }
        return isCodeUsed;
    }

    /**
     * Obtains all the fields' data and stores it inside a {@link GeneralObjectContainer} of
     * {@link Field}
     *
     * @return {@code GeneralObjectContainer} with the fields
     * @see ObjectContainer
     * @see Field
     */
    @Override
    public GeneralObjectContainer<Field> getAllFields() {
        GeneralObjectContainer<Field> fields = new ObjectContainer<>();
        try (Cursor fieldsCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            Map<String, Integer> fieldColumns = constructMapFromCursor(fieldsCursor);
            while (fieldsCursor.moveToNext()) {
                long id = fieldsCursor.getLong(fieldColumns.get(ID.getFieldName()));
                long securityCodeId =
                        fieldsCursor.getLong(fieldColumns.get(SECURITY_CODE.getFieldName()));
                String code = fieldsCursor.getString(fieldColumns.get(CODE.getFieldName()));
                boolean isCodeUsed =
                        fieldsCursor.getInt(fieldColumns.get(USED.getFieldName())) != 0;
                Field currentField = new Field(id, code, isCodeUsed, securityCodeId);
                fields.storeObject(currentField);
            }
        }
        return fields;
    }

    /**
     * Registers a new simple field
     *
     * @param securityCodeId parent {@link SecurityCode} ID
     * @param code           code contained by field
     * @param isCodeUsed     whether the code has been used or not
     * @return {@code long} with the new field ID
     */
    @Override
    public long registerNewField(long securityCodeId, @NonNull String code, boolean isCodeUsed) {
        ContentValues params = setParams(securityCodeId, code, isCodeUsed);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates the field's code
     *
     * @param fieldId ID where changing the code
     * @param newCode new code
     */
    @Override
    public void updateFieldCode(long fieldId, @NonNull String newCode) {
        ContentValues params = new ContentValues(1);
        params.put(CODE.getFieldName(), newCode);
        scheduleUpdateExecutor(fieldId, params);
    }

    /**
     * Updates the field's code been used
     *
     * @param fieldId    ID where changing the code
     * @param isCodeUsed whether the code has been used or not
     */
    @Override
    public void updateFieldCodeBeenUsed(long fieldId, boolean isCodeUsed) {
        ContentValues params = new ContentValues(1);
        params.put(USED.getFieldName(), isCodeUsed);
        scheduleUpdateExecutor(fieldId, params);
    }

    /**
     * Removes field by using the given ID
     *
     * @param fieldId ID of the field to delete
     */
    @Override
    public void removeField(long fieldId) {
        delete(TABLE_NAME, ID.getFieldName(), fieldId);
    }

    /**
     * Generates a map with the provided params
     *
     * @param securityCodeId security code ID
     * @param code           field's code
     * @param isCodeUsed     whether the code has been used or not
     * @return {@code ContentValues} with the params
     */
    private ContentValues setParams(long securityCodeId,
                                    @NonNull String code,
                                    boolean isCodeUsed) {
        ContentValues params = new ContentValues(3);
        params.put(SECURITY_CODE.getFieldName(), securityCodeId);
        params.put(CODE.getFieldName(), code);
        params.put(USED.getFieldName(), isCodeUsed);
        return params;
    }
}
