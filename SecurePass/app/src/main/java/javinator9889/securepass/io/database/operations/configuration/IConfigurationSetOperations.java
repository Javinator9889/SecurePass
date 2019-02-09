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

import androidx.annotation.NonNull;
import javinator9889.securepass.errors.database.NoJobsEnqueuedError;

/**
 * Interface for accessing the SET operations for the Configuration class.
 */
public interface IConfigurationSetOperations {
    /**
     * Registers a new simple configuration
     *
     * @param configurationName new custom configuration name
     *
     * @return {@code long} with the new configuration ID
     */
    long registerNewConfiguration(@NonNull String configurationName);

    /**
     * Updates the configuration's name by using its ID
     *
     * @param configurationId   ID of the configuration
     * @param configurationName new configuration name
     */
    void updateConfigurationName(long configurationId, @NonNull String configurationName);

    /**
     * Removes the configuration from the DB
     *
     * @param configurationId ID of the configuration to delete
     */
    void removeConfiguration(long configurationId);

    /**
     * Runs the {@link com.github.javinator9889.threading.pools.ThreadsPooling} - only necessary
     * when doing UPDATE operations
     *
     * @throws NoJobsEnqueuedError when there is no job added to the queue
     */
    void apply();
}
