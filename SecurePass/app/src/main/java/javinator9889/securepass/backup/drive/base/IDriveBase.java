/*
 * Copyright © 2019 - present | SecurePass by Javinator9889
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
 * Created by Javinator9889 on 23/08/2018 - SecurePass.
 */
package javinator9889.securepass.backup.drive.base;

import com.google.android.gms.drive.DriveResourceClient;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import javinator9889.securepass.R;

/**
 * Interface for accessing the <code>GoogleDriveBase</code> class
 * Created by Javinator9889 on 24/04/2018.
 */
public interface IDriveBase {
    int REQUEST_CODE_SIGN_IN = 0;

    int REQUEST_CODE_OPEN_ITEM = 1;

    /**
     * Signs-in into Google Drive by using the SignIn class
     *
     * @see SignIn
     */
    void signIn();

    /**
     * Sets the <code>DriveResourceClient</code> for classes that inherit from this
     *
     * @param resourceClient <code>DriveResourceClient</code> when the user has signed-in
     * @see DriveResourceClient
     */
    void setDriveResourceClient(DriveResourceClient resourceClient);

    /**
     * Sets whether the user is or not logged in
     *
     * @param isLoggedIn <code>boolean</code>, 'true' if signed-in, else 'false'
     */
    void setLoggedIn(boolean isLoggedIn);

    /**
     * Method for not repeating code when showing a Toast
     *
     * @param message <code>String</code> which contains the message to be shown
     * @see #showMessage(int)
     * @deprecated use {@link #showMessage(int)} instead
     */
    @Deprecated
    void showMessage(@NonNull String message);

    /**
     * Method for not repeating code when showing a Toast
     *
     * @param message <code>@StringRes int</code> which contains the resource id (R.string
     *                .string_name)
     * @see StringRes
     * @see R.string
     */
    void showMessage(@StringRes int message);
}
