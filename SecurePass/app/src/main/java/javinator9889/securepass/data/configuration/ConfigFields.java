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
 * Created by Javinator9889 on 23/08/2018 - SecurePass.
 */
package javinator9889.securepass.data.configuration;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Class that contains the parameters for ConfigFields.
 */
public abstract class ConfigFields implements IConfigFields, Serializable {
    private long mId;
    private String mDescription;
    private int mSortOrder;
    private long mConfigId;

    /**
     * Abstract constructor only visible for inheriting classes
     *
     * @param id          config field id
     * @param description optional description
     * @param sortOrder   fields sort order
     * @param configId    configuration id
     * @see ImagesConfig
     * @see PassConfig
     * @see LongTextConfig
     * @see SmallTextConfig
     */
    ConfigFields(long id, @Nullable String description, int sortOrder,
                 long configId) {
        mId = id;
        mDescription = description;
        mSortOrder = sortOrder;
        mConfigId = configId;
    }

    /**
     * Obtains current configuration ID (the same as in the database)
     *
     * @return long corresponding the ID
     */
    @Override
    public long getConfigId() {
        return mConfigId;
    }

    /**
     * Obtains current description (the same as in the database)
     *
     * @return String corresponding the description (may be null)
     */
    @Override
    @Nullable
    public String getDescription() {
        return mDescription;
    }

    /**
     * Obtains current sort order (the same as in the database)
     *
     * @return int corresponding the sort order
     */
    @Override
    public int getSortOrder() {
        return mSortOrder;
    }

    /**
     * Sets current configuration ID (the same as in the database)
     *
     * @param id long which must exists at configurations IDs
     */
    @Override
    public void setConfigId(long id) {
        this.mConfigId = id;
    }

    /**
     * Sets current description (the same as in the database)
     *
     * @param description nullable String which contains the description
     */
    @Override
    public void setDescription(@Nullable String description) {
        this.mDescription = description;
    }

    /**
     * Sets current sort order (the same as in the database)
     *
     * @param index int which correspondents the order inside the fields objects
     */
    @Override
    public void setSortOrder(int index) {
        this.mSortOrder = index;
    }

    /**
     * Gets current field ID (the same as in the database) - cannot be changed
     *
     * @return long corresponding the ID
     */
    @Override
    public long getFieldId() {
        return mId;
    }

    /**
     * Changes field ID if there is any reason for
     *
     * @param id long corresponding the new ID
     */
    @Override
    public void setFieldId(long id) {
        this.mId = id;
    }


    /**
     * Gets the proper table name - names available at
     * {@link javinator9889.securepass.util.values.Constants.SQL SQL names} attributes
     *
     * @return String which is the table name
     * @see javinator9889.securepass.util.values.Constants.SQL
     */
    @Override
    public abstract String getTableName();

    /**
     * Gets the proper database type - types available at
     * {@link DatabaseTables DatabaseTables enum} types
     *
     * @return DatabaseTables object with the correspondent type
     * @see DatabaseTables
     */
    @Override
    public abstract DatabaseTables getTableType();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigFields that = (ConfigFields) o;
        return mId == that.mId &&
                mSortOrder == that.mSortOrder &&
                mConfigId == that.mConfigId &&
                Objects.equals(mDescription, that.mDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mDescription, mSortOrder, mConfigId);
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public String toString() {
        return getTableName() + ":\n" +
                "{ ID: " + getFieldId() + ",\n" +
                " Description: " + getDescription() + ",\n" +
                " Sort order: " + getSortOrder() + ",\n" +
                " Configuration ID: " + getConfigId() + "}\n";
    }
}
