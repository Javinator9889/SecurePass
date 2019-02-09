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

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Stores Password fields
 * Created by Javinator9889 on 16/08/2018.
 */
public class Password implements IPassword, Serializable {
    private long mId;
    private long mParentEntryId;
    private String mPassword;
    private String mFieldDescription;

    /**
     * Public available constructor fon generating a new instance
     *
     * @param id               password ID
     * @param password         current password - cannot be null
     * @param fieldDescription description for this field - cannot be null
     */
    public Password(long id,
                    long parentEntryId,
                    @NonNull String password,
                    @NonNull String fieldDescription) {
        this.mId = id;
        this.mParentEntryId = parentEntryId;
        this.mPassword = password;
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Sets a new password
     *
     * @param password non null String with the password
     */
    @Override
    public void setPassword(@NonNull String password) {
        this.mPassword = password;
    }

    /**
     * Obtains the current stored password
     *
     * @return <code>String</code> with the password
     */
    @Override
    public String getPassword() {
        return mPassword;
    }

    /**
     * Gets the current password ID
     *
     * @return <code>long</code> with the ID
     */
    @Override
    public long getPasswordID() {
        return mId;
    }

    /**
     * Sets a new ID - this method should not be called
     *
     * @param passwordID new ID
     */
    @Override
    public void setPasswordID(long passwordID) {
        this.mId = passwordID;
    }

    /**
     * Updates the field description by te given one
     *
     * @param fieldDescription new description
     * @see ICommonMethods
     */
    @Override
    public void setFieldDescription(@NonNull String fieldDescription) {
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Obtains the current field description
     *
     * @return <code>String</code> with the description
     * @see ICommonMethods
     */
    @Override
    public String getFieldDescription() {
        return mFieldDescription;
    }

    /**
     * Gets the parent entry ID for this image
     *
     * @return {@code long} with the ID
     */
    @Override
    public long getEntryId() {
        return mParentEntryId;
    }

    /**
     * Sets a new image parent entry ID
     *
     * @param id new parent entry ID
     */
    @Override
    public void setEntryId(long id) {
        mParentEntryId = id;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString() {
        return "Password: " + getPassword() +
                "\nID: " + getPasswordID() +
                "\nField description: " + getFieldDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return mId == password.mId &&
                mParentEntryId == password.mParentEntryId &&
                Objects.equals(mPassword, password.mPassword) &&
                Objects.equals(mFieldDescription, password.mFieldDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mParentEntryId, mPassword, mFieldDescription);
    }
}
