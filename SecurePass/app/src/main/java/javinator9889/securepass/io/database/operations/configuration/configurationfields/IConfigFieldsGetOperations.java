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
 * Created by Javinator9889 on 16/08/2018 - SecurePass.
 */
package javinator9889.securepass.io.database.operations.configuration.configurationfields;

import javinator9889.securepass.data.configuration.IConfigFields;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Interface for accessing the GET operations for the ConfigurationFields class.
 */
public interface IConfigFieldsGetOperations {
    /**
     * Obtains the current description for the field
     *
     * @param id ID of the field where obtaining the data
     * @return {@code String} with the name
     */
    String getConfigFieldDescription(long id);

    /**
     * Obtains the current field's order
     *
     * @param id ID of the field where obtaining the data
     * @return {@code int} with the ordinal order
     */
    int getConfigFieldOrder(long id);

    /**
     * Obtains the current field's parent ID
     *
     * @param id ID of the field where obtaining the data
     * @return {@code long} with the ID
     */
    long getConfigFieldConfigurationId(long id);

    /**
     * Obtains all config fields' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link IConfigFields}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see ObjectContainer
     * @see javinator9889.securepass.data.configuration.ConfigFields
     */
    GeneralObjectContainer<IConfigFields> getAllConfigFields();
}
