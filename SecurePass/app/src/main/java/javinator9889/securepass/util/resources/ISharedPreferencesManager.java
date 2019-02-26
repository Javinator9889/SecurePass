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
 * Created by Javinator9889 on 03/11/2018 - SecurePass.
 */
package javinator9889.securepass.util.resources;

/**
 * Interface for accessing the PreferencesManager methods.
 */
public interface ISharedPreferencesManager {
    /**
     * Returns whether the application has been executed.
     *
     * @return {@code boolean} 'True' if initialized else 'False'.
     */
    boolean isApplicationInitialized();

    /**
     * Sets the application initialized (or not, for debugging process).
     *
     * @param isInitialized {@code boolean} with the initialization status.
     */
    void applicationInitialized(boolean isInitialized);

    /**
     * Returns whether the database has been initialized or not.
     * @return {@code boolean} 'True' if initialized else 'False'.
     */
    boolean isDatabaseInitialized();

    /**
     * Sets the database initialized (or not, for debugging process).
     * @param isInitialized {@code boolean} whether it is or not initialized.
     */
    void databaseInitialized(boolean isInitialized);

    /**
     * Returns whether the privacy policy has been accepted or not.
     * @return {@code boolean} 'True' if accepted else 'False'.
     */
    boolean isPrivacyAccepted();

    /**
     * Sets the privacy policy accepted (or not, for debugging process).
     * @param isAccepted {@code boolean} whether it is accepted or not.
     */
    void setPrivacyAccepted(boolean isAccepted);

    /**
     * Returns whether the terms of service have been accepted or not.
     * @return {@code boolean} 'True' if accepted else 'False'.
     */
    boolean areTermsOfServiceAccepted();

    /**
     * Sets the terms of service accepted (or not, for debugging process).
     * @param isAccepted {@code boolean} whether they are accepted or not.
     */
    void setTermsOfServiceAccepted(boolean isAccepted);

    /**
     * Returns whether the software license has been accepted or not.
     * @return {@code boolean} 'True' if accepted else 'False'.
     */
    boolean isSoftwareLicenseAccepted();

    /**
     * Sets the software license accepted (or not, for debugging process).
     * @param isAccepted {@code boolean} whether it is accepted or not.
     */
    void setSoftwareLicenseAccepted(boolean isAccepted);

    /**
     * Checks whether the application licenses are all accepted (or someone is missing).
     * @return {@code boolean} 'True' if all accepted, else 'False'.
     */
    boolean isApplicationLicenseAccepted();
}
