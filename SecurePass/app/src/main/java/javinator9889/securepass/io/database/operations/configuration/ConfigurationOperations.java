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
 * Created by Javinator9889 on 16/08/2018 - SecurePass.
 */
package javinator9889.securepass.io.database.operations.configuration;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.configuration.Configuration;
import javinator9889.securepass.data.configuration.IConfiguration;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.ConfigurationFields;

/**
 * All operations for the Configuration class.
 */
public class ConfigurationOperations extends CommonOperations
        implements IConfigurationSetOperations, IConfigurationGetOperations {
    private static final String TAG = "Configuration Operations";
    private static final String TABLE_NAME = Constants.SQL.CONFIGURATION.NAME;
    private static final ConfigurationFields ID = ConfigurationFields.ID;
    private static final ConfigurationFields NAME = ConfigurationFields.NAME;
    private static final String WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Available constructor, matching {@link CommonOperations#CommonOperations(DatabaseManager)
     * super} one
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object
     *
     * @see DatabaseManager
     */
    public ConfigurationOperations(@NonNull DatabaseManager databaseInstance) {
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
     * Obtains the configuration's name by using its ID
     *
     * @param configurationId ID of the configuration
     *
     * @return {@code String} with the configuration name
     */
    @Override
    public String getConfigurationName(long configurationId) {
        String name = null;
        try (Cursor configurationCursor = get(TABLE_NAME, whereArgs(NAME.getFieldName()),
                WHERE_ID, whereArgs(configurationId), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> configurationsColumns = constructMapFromCursor(configurationCursor);
            if (configurationCursor.moveToNext())
                name = configurationCursor.getString(configurationsColumns.get(NAME.getFieldName()));
        }
        return name;
    }

    /**
     * Obtains all configurations' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link IConfiguration}
     *
     * @return {@code GeneralObjectContainer} of entries
     *
     * @see ObjectContainer
     * @see Configuration
     */
    @Override
    public GeneralObjectContainer<IConfiguration> getAllConfigurations() {
        GeneralObjectContainer<IConfiguration> configurations = new ObjectContainer<>();
        try (Cursor configurationsCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            Map<String, Integer> configColumns = constructMapFromCursor(configurationsCursor);
            while (configurationsCursor.moveToNext()) {
                long id = configurationsCursor.getLong(configColumns.get(ID.getFieldName()));
                String name = configurationsCursor.getString(configColumns.get(NAME.getFieldName()));
                IConfiguration currentConfiguration = new Configuration(id, name, null);
                configurations.storeObject(currentConfiguration);
            }
        }
        return configurations;
    }

    /**
     * Registers a new simple configuration
     *
     * @param configurationName new custom configuration name
     *
     * @return {@code long} with the new configuration ID
     */
    @Override
    public long registerNewConfiguration(@NonNull String configurationName) {
        ContentValues params = setParams(configurationName);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates the configuration's name by using its ID
     *
     * @param configurationId   ID of the configuration
     * @param configurationName new configuration name
     */
    @Override
    public void updateConfigurationName(long configurationId, @NonNull String configurationName) {
        ContentValues params = setParams(configurationName);
        scheduleUpdateExecutor(configurationId, params);
    }

    /**
     * Removes the configuration from the DB
     *
     * @param configurationId ID of the configuration to delete
     */
    @Override
    public void removeConfiguration(long configurationId) {
        delete(TABLE_NAME, ID.getFieldName(), configurationId);
    }

    /**
     * Generates a map with the provided params
     *
     * @param name configuration name
     *
     * @return {@code ContentValues} with the params
     *
     * @see ContentValues
     */
    private ContentValues setParams(@NonNull String name) {
        ContentValues params = new ContentValues(1);
        params.put(NAME.getFieldName(), name);
        return params;
    }
}
