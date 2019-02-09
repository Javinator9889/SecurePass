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
package javinator9889.securepass.io.database.operations.entry;

import androidx.annotation.NonNull;

/**
 * TODO
 */
public interface IEntrySetOperations {
    /**
     * Registers a new simple entry
     *
     * @param parentCategoryId category ID
     * @param configId         configuration ID
     * @param entryName        entry name
     * @param icon             entry icon
     * @return <code>long</code> with the new entry ID
     * @see javinator9889.securepass.data.entry.Category
     * @see javinator9889.securepass.data.configuration.Configuration
     */
    long registerNewEntry(long parentCategoryId, long configId, @NonNull String entryName,
                          @NonNull String icon);

    /**
     * Updates entry name
     *
     * @param entryId entry ID
     * @param name    new name
     */
    void updateName(long entryId, @NonNull String name);

    /**
     * Updates entry icon
     *
     * @param entryId entry ID
     * @param icon    new icon
     */
    void updateIcon(long entryId, @NonNull String icon);

    /**
     * Updates entry category
     *
     * @param entryId    entry ID
     * @param categoryId new category ID
     */
    void updateCategory(long entryId, long categoryId);

    /**
     * Updates entry configuration
     *
     * @param entryId         entry ID
     * @param configurationId new configuration ID
     */
    void updateConfiguration(long entryId, long configurationId);

    /**
     * Removes the hole entry by the given ID
     *
     * @param entryId entry ID
     */
    void removeEntry(long entryId);

    /**
     * Applies pending changes to the database - only necessary when doing UPDATE operations
     */
    void apply();
}
