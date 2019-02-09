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
 * Interface with common methods to {@link Image}, {@link Password}, {@link LongText} and
 * {@link SmallText}
 * Created by Javinator9889 on 16/08/2018.
 */
public interface ICommonMethods {
    /**
     * Updates the field description by te given one
     *
     * @param fieldDescription new description
     */
    void setFieldDescription(@NonNull String fieldDescription);

    /**
     * Obtains the current field description
     *
     * @return <code>String</code> with the description
     */
    String getFieldDescription();

    /**
     * Gets the parent entry ID for this image
     *
     * @return {@code long} with the ID
     */
    long getEntryId();

    /**
     * Sets a new image parent entry ID
     *
     * @param id new parent entry ID
     */
    void setEntryId(long id);
}
