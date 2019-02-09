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
 * Class that contains Fields with SecurityCodes
 * Created by Javinator9889 on 29/03/2018.
 */
public class SecurityCode implements Serializable {
    private long mId;
    private String mAccountName;

    /**
     * Public available constructor for SecurityCodes
     *
     * @param id          SecurityCode ID
     * @param accountName SecurityCode name
     */
    public SecurityCode(long id, @NonNull String accountName) {
        this.mId = id;
        this.mAccountName = accountName;
    }

    /**
     * Obtains current ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    /**
     * Obtains current SecurityCode account name
     *
     * @return <code>String</code> with the name
     */
    public String getAccountName() {
        return mAccountName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SecurityCode ID: " + mId +
                "\nSecurityCode account name: " + mAccountName + "\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityCode that = (SecurityCode) o;
        return mId == that.mId &&
                Objects.equals(mAccountName, that.mAccountName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mAccountName);
    }
}
