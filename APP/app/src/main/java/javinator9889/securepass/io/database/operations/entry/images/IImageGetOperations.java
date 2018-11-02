package javinator9889.securepass.io.database.operations.entry.images;

import javinator9889.securepass.data.entry.fields.Image;
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
 * Created by Javinator9889 on 02/11/2018 - APP.
 */
public interface IImageGetOperations {
    /**
     * Obtains the image source by its ID
     *
     * @param imageId ID of the image to get the source
     * @return {@code String} with the source
     */
    String getImageSource(long imageId);

    /**
     * Obtains the image description by its ID
     *
     * @param imageId ID of the image to get the description
     * @return {@code String} with the description
     */
    String getImageDescription(long imageId);

    /**
     * Obtains the image order by its ID
     *
     * @param imageId ID of the image to get the order
     * @return {@code int} with the ordinal order
     */
    int getImageOrder(long imageId);

    /**
     * Obtains the image parent entry ID
     *
     * @param imageId ID of the image to get the parent entry ID
     * @return {@code long} with the entry ID
     */
    long getImageEntryId(long imageId);

    /**
     * Obtains all images' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link Image}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see javinator9889.securepass.objects.ObjectContainer
     * @see Image
     */
    GeneralObjectContainer<Image> getAllImages();
}
