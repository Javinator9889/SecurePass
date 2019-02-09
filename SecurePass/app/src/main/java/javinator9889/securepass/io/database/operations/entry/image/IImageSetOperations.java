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
package javinator9889.securepass.io.database.operations.entry.image;

import androidx.annotation.NonNull;
import javinator9889.securepass.errors.database.NoJobsEnqueuedError;

/**
 * Interface for accessing the SET operations for the Image class.
 */
public interface IImageSetOperations {
    /**
     * Registers a new simple image
     *
     * @param source      image source
     * @param description image description
     * @param order       ordinal order when showing on UI
     * @param entryId     parent entry ID
     *
     * @return {@code long} with the new image ID
     */
    long registerNewImage(@NonNull String source, @NonNull String description, int order,
                          long entryId);

    /**
     * Updates the image source of the provided ID
     *
     * @param imageId   ID of the image to change source
     * @param newSource new source
     */
    void updateImageSource(long imageId, @NonNull String newSource);

    /**
     * Updates the image description of the provided ID
     *
     * @param imageId        ID of the image to change description
     * @param newDescription new description
     */
    void updateImageDescription(long imageId, @NonNull String newDescription);

    /**
     * Updates the image order of the provided ID
     *
     * @param imageId  ID of the image to change order
     * @param newOrder new ordinal order
     */
    void updateImageOrder(long imageId, int newOrder);

    /**
     * Removes the image by its ID
     *
     * @param imageId ID of the image to delete
     */
    void removeImage(long imageId);

    /**
     * Runs the {@link com.github.javinator9889.threading.pools.ThreadsPooling} - only necessary
     * when doing UPDATE operations
     *
     * @throws NoJobsEnqueuedError when there is no job added to the queue
     */
    void apply();
}
