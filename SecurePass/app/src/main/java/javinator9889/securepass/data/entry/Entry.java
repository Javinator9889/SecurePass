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
 * Created by Javinator9889 on 29/03/2018 - SecurePass.
 */
package javinator9889.securepass.data.entry;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * TODO
 */
public class Entry implements Serializable {
    private long mId;
    private long mCategoryId;
    private long mConfigId;
    private String mName;
    private String mIcon;

    /**
     * Public constructor declaring only the needed entry params
     *
     * @param id              entry ID
     * @param name            entry name
     * @param icon            entry icon
     * @param categoryId      entry parent category ID
     * @param configurationId entry parent configuration ID
     */
    public Entry(long id,
                 @NonNull String name,
                 @NonNull String icon,
                 long categoryId,
                 long configurationId) {
        mId = id;
        mName = name;
        mIcon = icon;
        mCategoryId = categoryId;
        mConfigId = configurationId;
    }

    /**
     * Obtains current entry ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    /**
     * Obtains current entry name
     *
     * @return <code>String</code> with the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Updates entry name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Obtains current entry icon
     *
     * @return <code>String</code> with the icon
     */
    public String getIcon() {
        return mIcon;
    }

    /**
     * Sets a new icon for the entry
     *
     * @param icon new icon
     */
    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    /**
     * Gets current entry category
     *
     * @return {@code long} with the category ID
     */
    public long getCategoryId() {
        return mCategoryId;
    }

    /**
     * Updates current entry categoryId
     *
     * @param categoryId new categoryId
     */
    public void setCategoryId(long categoryId) {
        this.mCategoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Entry ID: " + mId +
                "\nEntry icon: " + mIcon +
                "\nEntry name: " + mName +
                "\nEntry categoryId: " + mCategoryId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return mId == entry.mId &&
                mCategoryId == entry.mCategoryId &&
                mConfigId == entry.mConfigId &&
                Objects.equals(mName, entry.mName) &&
                Objects.equals(mIcon, entry.mIcon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(mId, mCategoryId, mConfigId, mName, mIcon);
    }
}
