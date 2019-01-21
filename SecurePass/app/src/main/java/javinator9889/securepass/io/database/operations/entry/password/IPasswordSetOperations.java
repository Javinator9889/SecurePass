package javinator9889.securepass.io.database.operations.entry.password;

import androidx.annotation.NonNull;

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
 * Created by Javinator9889 on 01/11/2018 - APP.
 */
public interface IPasswordSetOperations {
    /**
     * Registers a new simple password entry
     *
     * @param password    password to save
     * @param description current entry description
     * @param order       ordinal order when showing it on UI
     * @param entryId     parent entry ID
     * @return {@code long} with the new password ID
     */
    long registerNewPassword(@NonNull String password, @NonNull String description, int order,
                             long entryId);

    /**
     * Updates the saved password by using the given ID
     *
     * @param passwordId ID of the entry to change
     * @param password   new password to save
     */
    void updatePassword(long passwordId, @NonNull String password);

    /**
     * Updates the password description by using the given ID
     *
     * @param passwordId  ID of the entry to change
     * @param description new description
     */
    void updateDescription(long passwordId, @NonNull String description);

    /**
     * Updates the password order inside the entry
     *
     * @param passwordId ID of the password to change
     * @param order      new order
     */
    void updateSortOrder(long passwordId, int order);

    /**
     * Removes the hole password by using the given ID
     *
     * @param passwordId password ID to remove
     */
    void removePassword(long passwordId);

    /**
     * Applies pending changes to the database - only necessary when doing UPDATE operations
     */
    void apply();
}
