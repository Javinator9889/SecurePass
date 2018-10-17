package javinator9889.securepass.data.configuration;

import java.io.Serializable;

import androidx.annotation.Nullable;
import javinator9889.securepass.util.values.DatabaseTables;

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
 * Created by Javinator9889 on 17/10/2018 - APP.
 */
public abstract class ConfigFields implements IConfigFields, Serializable {
    private long mId;
    private String mDescription;
    private int mSortOrder;
    private long mConfigId;

    ConfigFields(long id, @Nullable String description, int sortOrder,
                 long configId) {
        mId = id;
        mDescription = description;
        mSortOrder = sortOrder;
        mConfigId = configId;
    }

    @Override
    public long getConfigId() {
        return mConfigId;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public int getSortOrder() {
        return mSortOrder;
    }

    @Override
    public void setConfigId(long id) {
        this.mConfigId = id;
    }

    @Override
    public void setDescription(String description) {
        this.mDescription = description;
    }

    @Override
    public void setSortOrder(int index) {
        this.mSortOrder = index;
    }

    @Override
    public long getFieldId() {
        return mId;
    }

    @Override
    public void setFieldId(long id) {
        this.mId = id;
    }

    @Override
    public abstract String getTableName();

    @Override
    public abstract DatabaseTables getTableType();
}
