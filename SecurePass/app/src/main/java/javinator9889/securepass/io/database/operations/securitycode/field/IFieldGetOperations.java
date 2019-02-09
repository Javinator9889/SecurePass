package javinator9889.securepass.io.database.operations.securitycode.field;

import javinator9889.securepass.data.secret.Field;
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
public interface IFieldGetOperations {
    /**
     * Obtains the field's code by using the provided ID
     *
     * @param fieldId ID of the field where obtaining the code
     * @return {@code String} with the code
     */
    String getFieldCode(long fieldId);

    /**
     * Obtains if the field's code has been used
     *
     * @param fieldId ID of the field where obtaining the code
     * @return {@code boolean} with the value
     */
    boolean getFieldCodeBeenUsed(long fieldId);

    /**
     * Obtains all the fields' data and stores it inside a {@link GeneralObjectContainer} of
     * {@link Field}
     *
     * @return {@code GeneralObjectContainer} with the fields
     * @see javinator9889.securepass.objects.ObjectContainer
     * @see Field
     */
    GeneralObjectContainer<Field> getAllFields();
}
