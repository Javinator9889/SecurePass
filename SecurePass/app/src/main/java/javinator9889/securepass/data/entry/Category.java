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
 * Created by Javinator9889 on 29/08/2018 - SecurePass.
 */
package javinator9889.securepass.data.entry;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class for containing categories
 * Created by Javinator9889 on 29/03/2018.
 */
public class Category implements Serializable {
    private long mId;
    private String mName;

    /**
     * Public available constructor for generating Category
     *
     * @param id   category ID
     * @param name category name
     */
    public Category(long id, String name) {
        this.mId = id;
        this.mName = name;
    }

    /**
     * @deprecated Default constructor - deprecated. Use {@link #Category(long, String)} instead
     */
    @Deprecated
    public Category() {
        this.mId = 0;
        this.mName = "Global";
    }

    /**
     * Obtains current category ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    /**
     * Obtains current category name
     *
     * @return <code>String</code> with the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Updates current category name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Category ID: " + mId + "\n" +
                "Category name: " + mName + "\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return mId == category.mId &&
                Objects.equals(mName, category.mName);
    }

    /**
     * {@inheritDoc}
     *
     * @see Objects#hash(Object...)
     */
    @Override
    public int hashCode() {
        return Objects.hash(mId, mName);
    }
}
