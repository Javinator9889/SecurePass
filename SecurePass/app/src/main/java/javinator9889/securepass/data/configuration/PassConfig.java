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
 * Created by Javinator9889 on 21/08/2018 - SecurePass.
 */
package javinator9889.securepass.data.configuration;

import androidx.annotation.Nullable;

import javinator9889.securepass.util.values.Constants.SQL.PASS_CONFIG;
import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class PassConfig extends ConfigFields {
    private static final String TABLE_NAME = PASS_CONFIG.NAME;
    private static final DatabaseTables TABLE_TYPE =
            DatabaseTables.PASS_CONFIG;

    /**
     * Constructor that uses
     * {@link ConfigFields#ConfigFields(long, String, int, long) ConfigFileds constructor} (only
     * visible for this classes) - generates PassConfig instance
     *
     * @param id          config field id
     * @param description optional description
     * @param sortOrder   fields sort order
     * @param configId    configuration id
     * @see ConfigFields
     * @see IConfigFields
     */
    public PassConfig(long id, @Nullable String description, int sortOrder,
                      long configId) {
        super(id, description, sortOrder, configId);
    }

    /**
     * Gets the proper table name - names available at
     * {@link javinator9889.securepass.util.values.Constants.SQL SQL names} attributes
     *
     * @return String which is the table name
     * @see javinator9889.securepass.util.values.Constants.SQL
     */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Gets the proper database type - types available at
     * {@link DatabaseTables DatabaseTables enum} types
     *
     * @return DatabaseTables object with the correspondent type
     * @see DatabaseTables
     */
    @Override
    public DatabaseTables getTableType() {
        return TABLE_TYPE;
    }
}
