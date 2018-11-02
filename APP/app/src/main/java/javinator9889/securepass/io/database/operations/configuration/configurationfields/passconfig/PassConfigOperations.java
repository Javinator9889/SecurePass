package javinator9889.securepass.io.database.operations.configuration.configurationfields.passconfig;

import android.util.Log;

import net.sqlcipher.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.configuration.IConfigFields;
import javinator9889.securepass.data.configuration.PassConfig;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.ConfigFieldsOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.threading.ThreadExceptionListener;
import javinator9889.securepass.util.values.Constants;

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
public class PassConfigOperations extends ConfigFieldsOperations {
    private static final String TAG = "PassConfig Operations";
    private static final String TABLE_NAME = Constants.SQL.PASS_CONFIG.NAME;

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
    public PassConfigOperations(@NonNull DatabaseManager databaseInstance,
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
     * Obtains all config fields' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link IConfigFields}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see ObjectContainer
     * @see javinator9889.securepass.data.configuration.PassConfig
     */
    @Override
    public GeneralObjectContainer<IConfigFields> getAllConfigFields() {
        GeneralObjectContainer<IConfigFields> configFields = new ObjectContainer<>();
        try (Cursor passConfigsCursor = getAll(TABLE_NAME, ORDER_BY)) {
            while (passConfigsCursor.moveToNext()) {
                long id = passConfigsCursor.getLong(ID.getFieldIndex());
                String description = passConfigsCursor.getString(DESCRIPTION.getFieldIndex());
                int order = passConfigsCursor.getInt(ORDER.getFieldIndex());
                long configurationId = passConfigsCursor.getLong(CONFIGURATION.getFieldIndex());
                IConfigFields currentConfigField =
                        new PassConfig(id, description, order, configurationId);
                configFields.storeObject(currentConfigField);
            }
        }
        return configFields;
    }
}
