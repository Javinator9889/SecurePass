package javinator9889.securepass.io.database.operations.configuration.configurationfields;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.configuration.ConfigFields;
import javinator9889.securepass.data.configuration.IConfigFields;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.database.ConfigFieldsFields;

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
 * Created by Javinator9889 on 02/11/2018 - APP.
 */
public abstract class ConfigFieldsOperations extends CommonOperations
        implements IConfigFieldsSetOperations, IConfigFieldsGetOperations {
    protected static final ConfigFieldsFields ID = ConfigFieldsFields.ID;
    protected static final ConfigFieldsFields DESCRIPTION = ConfigFieldsFields.DESCRIPTION;
    protected static final ConfigFieldsFields ORDER = ConfigFieldsFields.ORDER;
    protected static final ConfigFieldsFields CONFIGURATION = ConfigFieldsFields.CONFIGURATION;
    protected static final String ORDER_BY = ID.getFieldName() + " ASC";
    private static final String WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Available constructor, matching {@link CommonOperations#CommonOperations(DatabaseManager)
     * super} one
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object
     *
     * @see DatabaseManager
     */
    protected ConfigFieldsOperations(@NonNull DatabaseManager databaseInstance) {
        super(databaseInstance);
    }

    /**
     * Obtains the current description for the field
     *
     * @param id ID of the field where obtaining the data
     *
     * @return {@code String} with the name
     */
    @Override
    public String getConfigFieldDescription(long id) {
        String description = null;
        try (Cursor configFieldsCursor = get(getTableName(), whereArgs(DESCRIPTION.getFieldName()),
                getWhereId(), whereArgs(id), null, null, ORDER_BY)) {
            Map<String, Integer> configurationsColumns = constructMapFromCursor(configFieldsCursor);
            if (configFieldsCursor.moveToNext())
                description = configFieldsCursor.getString(configurationsColumns.get(DESCRIPTION.getFieldName()));
        }
        return description;
    }

    /**
     * Obtains the current field's order
     *
     * @param id ID of the field where obtaining the data
     *
     * @return {@code int} with the ordinal order
     */
    @Override
    public int getConfigFieldOrder(long id) {
        int order = -1;
        try (Cursor configFieldsCursor = get(getTableName(), whereArgs(ORDER.getFieldName()),
                getWhereId(), whereArgs(id), null, null, ORDER_BY)) {
            Map<String, Integer> configurationsColumns = constructMapFromCursor(configFieldsCursor);
            if (configFieldsCursor.moveToNext())
                order = configFieldsCursor.getInt(configurationsColumns.get(ORDER.getFieldName()));
        }
        return order;
    }

    /**
     * Obtains the current field's parent ID
     *
     * @param id ID of the field where obtaining the data
     *
     * @return {@code long} with the ID
     */
    @Override
    public long getConfigFieldConfigurationId(long id) {
        long configurationId = -1;
        try (Cursor configFieldsCursor = get(getTableName(), whereArgs(CONFIGURATION.getFieldName()),
                getWhereId(), whereArgs(id), null, null, ORDER_BY)) {
            Map<String, Integer> configurationsColumns = constructMapFromCursor(configFieldsCursor);
            if (configFieldsCursor.moveToNext())
                configurationId = configFieldsCursor.getLong(configurationsColumns.get
                        (CONFIGURATION.getFieldName()));
        }
        return configurationId;
    }


    /**
     * Registers a new simple configuration field
     *
     * @param description     field description
     * @param order           field order for displaying at UI
     * @param configurationId parent configuration ID
     *
     * @return {@code long} with the new ID
     */
    @Override
    public long registerNewConfigField(@NonNull String description,
                                       int order,
                                       long configurationId) {
        ContentValues params = setParams(description, order, configurationId);
        return insertIgnoreOnConflict(getTableName(), params);
    }

    /**
     * Updates the field's description
     *
     * @param id          ID of the field to change
     * @param description new description
     */
    @Override
    public void updateConfigFieldDescription(long id, @NonNull String description) {
        ContentValues params = new ContentValues(1);
        params.put(DESCRIPTION.getFieldName(), description);
        scheduleUpdateExecutor(id, params);
    }

    /**
     * Updates the field's order
     *
     * @param id    ID of the field to change
     * @param order new order
     */
    @Override
    public void updateConfigurationOrder(long id, int order) {
        ContentValues params = new ContentValues(1);
        params.put(ORDER.getFieldName(), order);
        scheduleUpdateExecutor(id, params);
    }

    /**
     * Generates a map with the provided params
     *
     * @param description     config field description
     * @param order           config field order
     * @param configurationId config field parent configuration ID
     *
     * @return {@code ContentValues} with the params
     *
     * @see ContentValues
     */
    protected ContentValues setParams(@NonNull String description,
                                      int order,
                                      long configurationId) {
        ContentValues params = new ContentValues(3);
        params.put(DESCRIPTION.getFieldName(), description);
        params.put(ORDER.getFieldName(), order);
        params.put(CONFIGURATION.getFieldName(), configurationId);
        return params;
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
     * Gets the tag for {@link Log} output - should be overridden
     *
     * @return <code>String</code> with the tag name
     */
    @Override
    public abstract String getTag();

    /**
     * Gets the TABLE NAME for using {@link #scheduleUpdateExecutor(long, ContentValues)} - should
     * be overridden
     *
     * @return {@code String} with the TABLE NAME - null if not defined
     */
    @Nullable
    @Override
    public abstract String getTableName();

    /**
     * Obtains all config fields' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link IConfigFields}
     *
     * @return {@code GeneralObjectContainer} of entries
     *
     * @see ObjectContainer
     * @see ConfigFields
     */
    @Override
    public abstract GeneralObjectContainer<IConfigFields> getAllConfigFields();
}
