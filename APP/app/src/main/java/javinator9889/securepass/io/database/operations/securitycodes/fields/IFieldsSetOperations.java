package javinator9889.securepass.io.database.operations.securitycodes.fields;

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
public interface IFieldsSetOperations {
    /**
     * Registers a new simple field
     *
     * @param securityCodeId parent {@link javinator9889.securepass.data.secret.SecurityCode} ID
     * @param code           code contained by field
     * @param isCodeUsed     whether the code has been used or not
     * @return {@code long} with the new field ID
     */
    long registerNewField(long securityCodeId, @NonNull String code, boolean isCodeUsed);

    /**
     * Updates the field's code
     *
     * @param fieldId ID where changing the code
     * @param newCode new code
     */
    void updateFieldCode(long fieldId, @NonNull String newCode);

    /**
     * Updates the field's code been used
     *
     * @param fieldId    ID where changing the code
     * @param isCodeUsed whether the code has been used or not
     */
    void updateFieldCodeBeenUsed(long fieldId, boolean isCodeUsed);

    /**
     * Removes field by using the given ID
     *
     * @param fieldId ID of the field to delete
     */
    void removeField(long fieldId);
}
