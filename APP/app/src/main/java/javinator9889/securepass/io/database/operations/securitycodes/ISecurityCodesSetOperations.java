package javinator9889.securepass.io.database.operations.securitycodes;

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
public interface ISecurityCodesSetOperations {
    /**
     * Registers a new simple security code
     *
     * @param securityCodeName security code name
     * @return {@code long} with the ID
     */
    long registerNewSecurityCode(@NonNull String securityCodeName);

    /**
     * Updates security code name
     *
     * @param securityCodeId security code ID where changing name
     * @param newName        new name
     */
    void updateName(long securityCodeId, @NonNull String newName);

    /**
     * Removes the security code with its fields by using the provided ID
     *
     * @param securityCodeId ID to remove
     */
    void removeSecurityCode(long securityCodeId);

    /**
     * Applies pending changes to the database
     */
    void apply();
}
