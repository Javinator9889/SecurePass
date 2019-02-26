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
 * Created by Javinator9889 on 29/03/2018 - SecurePass.
 */
package javinator9889.securepass.data.secret;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Contains Fields of the {@link SecurityCode} class
 * Created by Javinator9889 on 29/03/2018.
 */
public class Field implements Serializable {
    private long mId;
    private String mCode;
    private boolean mIsCodeUsed;
    private long mSecurityCodeId;

    /**
     * Public available constructor for Field
     *
     * @param id         field ID
     * @param code       field code
     * @param isCodeUsed whether the code has been used or not
     * @param securityCodeId security code parent ID
     * @see SecurityCode
     */
    public Field(long id, @NonNull String code, boolean isCodeUsed, long securityCodeId) {
        this.mId = id;
        this.mCode = code;
        this.mIsCodeUsed = isCodeUsed;
        this.mSecurityCodeId = securityCodeId;
    }

    /**
     * Obtains parent ID
     *
     * @return <code>long</code> with the parent ID
     */
    public long getSecurityCodeID() {
        return mSecurityCodeId;
    }

    /**
     * Obtains field code
     *
     * @return <code>String</code> with the code
     */
    public String getCode() {
        return mCode;
    }

    /**
     * Determine whether the current code has been used
     *
     * @return <code>boolean</code>, 'true' if used, else 'false'
     */
    public boolean isCodeUsed() {
        return mIsCodeUsed;
    }

    /**
     * Gets current field ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Field Code: " + mCode +
                "\nField ID: " + mId +
                "\nField is used: " + mIsCodeUsed +
                "\nField of: " + mSecurityCodeId + "\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return mId == field.mId &&
                mIsCodeUsed == field.mIsCodeUsed &&
                mSecurityCodeId == field.mSecurityCodeId &&
                Objects.equals(mCode, field.mCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mCode, mIsCodeUsed, mSecurityCodeId);
    }
}