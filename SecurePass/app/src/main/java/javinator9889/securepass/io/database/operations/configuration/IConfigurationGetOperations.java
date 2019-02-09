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
package javinator9889.securepass.io.database.operations.configuration;

import javinator9889.securepass.data.configuration.Configuration;
import javinator9889.securepass.data.configuration.IConfiguration;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * TODO
 */
public interface IConfigurationGetOperations {
    /**
     * Obtains the configuration's name by using its ID
     *
     * @param configurationId ID of the configuration
     * @return {@code String} with the configuration name
     */
    String getConfigurationName(long configurationId);

    /**
     * Obtains all configurations' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link javinator9889.securepass.data.configuration.IConfiguration}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see ObjectContainer
     * @see Configuration
     */
    GeneralObjectContainer<IConfiguration> getAllConfigurations();
}
