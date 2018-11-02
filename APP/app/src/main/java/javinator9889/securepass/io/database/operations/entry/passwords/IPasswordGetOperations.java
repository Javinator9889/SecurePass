package javinator9889.securepass.io.database.operations.entry.passwords;

import javinator9889.securepass.data.entry.fields.Password;
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
 * Created by Javinator9889 on 01/11/2018 - APP.
 */
public interface IPasswordGetOperations {
    /**
     * Obtains the stored password
     *
     * @param passwordId ID of the password in the DB
     * @return {@code String} with the password
     */
    String getPasswordPassword(long passwordId);

    /**
     * Obtains the password description
     *
     * @param passwordId ID of the password in DB
     * @return {@code String} with the description
     */
    String getPasswordDescription(long passwordId);

    /**
     * Obtains the password ordinal order
     *
     * @param passwordId ID of the password in DB
     * @return {@code int} with the order
     */
    int getPasswordOrder(long passwordId);

    /**
     * Obtains the password parent entry ID
     *
     * @param passwordId ID of the password in DB
     * @return {@code long} with the entry ID
     */
    long getPasswordEntryId(long passwordId);

    /**
     * Obtains all entries' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link Password}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see javinator9889.securepass.objects.ObjectContainer
     * @see Password
     */
    GeneralObjectContainer<Password> getAllPasswords();
}
