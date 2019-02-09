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

import androidx.annotation.NonNull;
import javinator9889.securepass.errors.database.NoJobsEnqueuedError;

/**
 * Interface for accessing the SET operations for the Fields class.
 */
public interface IConfigFieldsSetOperations {
    /**
     * Registers a new simple configuration field
     *
     * @param description     field description
     * @param order           field order for displaying at UI
     * @param configurationId parent configuration ID
     *
     * @return {@code long} with the new ID
     */
    long registerNewConfigField(@NonNull String description, int order, long configurationId);

    /**
     * Updates the field's description
     *
     * @param id          ID of the field to change
     * @param description new description
     */
    void updateConfigFieldDescription(long id, @NonNull String description);

    /**
     * Updates the field's order
     *
     * @param id    ID of the field to change
     * @param order new order
     */
    void updateConfigurationOrder(long id, int order);

    /**
     * Runs the {@link com.github.javinator9889.threading.pools.ThreadsPooling} - only necessary
     * when doing UPDATE operations
     *
     * @throws NoJobsEnqueuedError when there is no job added to the queue
     */
    void apply();
}
