/*
 * Copyright © 2019 - present | SecurePass by Javinator9889
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
 * Created by Javinator9889 on 21/08/2018 - SecurePass.
 */
package javinator9889.securepass.data.configuration;

import androidx.annotation.Nullable;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public interface IConfiguration {
    /**
     * Obtains current configuration ID (same as in database)
     *
     * @return long corresponding the current ID
     */
    long getConfigurationId();

    /**
     * Obtains current configuration name (same as in database) - can be null
     *
     * @return String with the name
     */
    @Nullable
    String getConfigurationName();

    /**
     * Obtains current configuration fields (same as in database)
     *
     * @return {@link GeneralObjectContainer} of {@link IConfigFields}
     * @see ObjectContainer
     */
    GeneralObjectContainer<IConfigFields> getConfigFields();

    /**
     * Changes configuration ID if needed (it should not be changed)
     *
     * @param id long with the new configuration ID
     */
    void setConfigurationId(long id);

    /**
     * Changes current configuration name (can be null)
     *
     * @param name the new name (or null for no one)
     */
    void setConfigurationName(String name);

    /**
     * Adds a new {@link IConfigFields configuration} by using the
     * {@link IConfigFields#getSortOrder() field sort order}
     *
     * @param field new configuration to store
     * @see ConfigFields
     */
    void addConfigurationField(IConfigFields field);
}
