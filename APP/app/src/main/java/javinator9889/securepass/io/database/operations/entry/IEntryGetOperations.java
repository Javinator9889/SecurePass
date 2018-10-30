package javinator9889.securepass.io.database.operations.entry;

import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.objects.GeneralObjectContainer;

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
 * Created by Javinator9889 on 28/10/2018 - APP.
 */
public interface IEntryGetOperations {
    /**
     * Obtains the entry name by using the given ID
     *
     * @param entryId entry ID
     * @return {@code String} with the entry name
     */
    String getEntryName(long entryId);

    /**
     * Obtains the entry icon by using the given ID
     *
     * @param entryId entry ID
     * @return {@code String} with the entry icon
     */
    String getEntryIcon(long entryId);

    /**
     * Obtains the entry parent category by using the given ID
     *
     * @param entryId entry ID
     * @return {@code long} with the category ID
     * @see javinator9889.securepass.data.entry.Category
     */
    long getEntryCategory(long entryId);

    /**
     * Obtains the entry configuration by using the given ID
     *
     * @param entryId entry ID
     * @return {@code long} with the configuration ID
     * @see javinator9889.securepass.data.configuration.Configuration
     */
    long getEntryConfiguration(long entryId);

    /**
     * Obtains all entries' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link Entry}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see javinator9889.securepass.objects.ObjectContainer
     * @see Entry
     */
    GeneralObjectContainer<Entry> getAllEntries();
}
