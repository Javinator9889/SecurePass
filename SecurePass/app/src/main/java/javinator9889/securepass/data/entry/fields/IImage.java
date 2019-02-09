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
package javinator9889.securepass.data.entry.fields;

import androidx.annotation.NonNull;

/**
 * Interface for accessing {@link Image} methods
 * Created by Javinator9889 on 16/08/2018.
 */
public interface IImage extends ICommonMethods {
    /**
     * Saves the image source
     *
     * @param imageSource non null <code>String</code> containing the Base64 source
     * @see android.util.Base64
     */
    void setImageSource(@NonNull String imageSource);

    /**
     * Obtains the image source
     *
     * @return <code>String</code> containing the Base64 image
     * @see android.util.Base64
     */
    String getImageSource();

    /**
     * Obtains the current image ID
     *
     * @return <code>long</code> corresponding the ID
     */
    long getImageID();

    /**
     * Sets a new image ID for any reason - should not be called
     *
     * @param imageID the new ID
     */
    void setImageID(long imageID);
}
