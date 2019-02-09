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
 * Interface for accessing the {@link Password} methods
 * Created by Javinator9889 on 16/08/2018.
 */
public interface IPassword extends ICommonMethods {
    /**
     * Sets a new password
     *
     * @param password non null String with the password
     */
    void setPassword(@NonNull String password);

    /**
     * Obtains the current stored password
     *
     * @return <code>String</code> with the password
     */
    String getPassword();

    /**
     * Gets the current password ID
     *
     * @return <code>long</code> with the ID
     */
    long getPasswordID();

    /**
     * Sets a new ID - this method should not be called
     *
     * @param passwordID new ID
     */
    void setPasswordID(long passwordID);
}
