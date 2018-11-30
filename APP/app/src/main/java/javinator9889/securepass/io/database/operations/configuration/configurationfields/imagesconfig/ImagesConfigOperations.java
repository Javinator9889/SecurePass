package javinator9889.securepass.io.database.operations.configuration.configurationfields.imagesconfig;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.configuration.IConfigFields;
import javinator9889.securepass.data.configuration.ImagesConfig;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.ConfigFieldsOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;

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
public class ImagesConfigOperations extends ConfigFieldsOperations {
    private static final String TAG = "ImagesConfig Operations";
    private static final String TABLE_NAME = Constants.SQL.IMAGES_CONFIG.NAME;

    /**
     * Available constructor, matching {@link CommonOperations#CommonOperations(DatabaseManager)
     * super} one
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object
     *
     * @see DatabaseManager
     */
    protected ImagesConfigOperations(@NonNull DatabaseManager databaseInstance) {
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
     * Obtains all config fields' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link IConfigFields}
     *
     * @return {@code GeneralObjectContainer} of entries
     *
     * @see ObjectContainer
     * @see javinator9889.securepass.data.configuration.ImagesConfig
     */
    @Override
    public GeneralObjectContainer<IConfigFields> getAllConfigFields() {
        GeneralObjectContainer<IConfigFields> configFields = new ObjectContainer<>();
        try (Cursor imagesConfigCursor = getAll(TABLE_NAME, ORDER_BY)) {
            while (imagesConfigCursor.moveToNext()) {
                long id = imagesConfigCursor.getLong(ID.getFieldIndex());
                String description = imagesConfigCursor.getString(DESCRIPTION.getFieldIndex());
                int sortOrder = imagesConfigCursor.getInt(ORDER.getFieldIndex());
                long configId = imagesConfigCursor.getLong(CONFIGURATION.getFieldIndex());
                IConfigFields currentField = new ImagesConfig(id, description, sortOrder, configId);
                configFields.storeObject(currentField);
            }
        }
        return configFields;
    }
}
