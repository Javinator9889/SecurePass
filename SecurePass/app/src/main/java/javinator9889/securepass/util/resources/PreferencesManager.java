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

import android.app.Application;
import android.content.SharedPreferences;

import java.util.Map;

import androidx.annotation.NonNull;
import javinator9889.securepass.SecurePass;

import static javinator9889.securepass.util.values.Constants.SHARED_PREF.APP_FIRST_EXECUTED;
import static javinator9889.securepass.util.values.Constants.SHARED_PREF.DATABASE_INIT;
import static javinator9889.securepass.util.values.Constants.SHARED_PREF.FILENAME;
import static javinator9889.securepass.util.values.Constants.SHARED_PREF.KEYS;
import static javinator9889.securepass.util.values.Constants.SHARED_PREF.MODE;
import static javinator9889.securepass.util.values.Constants.SHARED_PREF.PRIVACY_ACCEPT;
import static javinator9889.securepass.util.values.Constants.SHARED_PREF.SOFTWARE_LICENSE_ACCEPT;
import static javinator9889.securepass.util.values.Constants.SHARED_PREF.TERMS_OF_SERVICE_ACCEPT;

/**
 * Class for managing the user preferences easily - provides a fast access for
 * the application preferences, so the developer do not have to remember all key
 * names.
 */
public class PreferencesManager implements ISharedPreferencesManager {
    private Map<String, String> mKeys;
    private volatile SharedPreferences mSharedPreferences;
    private static PreferencesManager INSTANCE;

    /**
     * Private access for the {@code PreferencesManager} instance when it is not available.
     *
     * @param instance the {@link Application} instance for obtaining the shared preferences.
     */
    private PreferencesManager(@NonNull SecurePass instance) {
        this.mSharedPreferences = instance.getSharedPreferences(FILENAME, MODE);
        mKeys = KEYS();
    }

    /**
     * Instantiate a new {@code PreferencesManager} instance when there is no one available.
     */
    private PreferencesManager() {
        SecurePass appInstance = SecurePass.getApplicationInstance();
        this.mSharedPreferences = appInstance
                .getSharedPreferences(FILENAME, MODE);
        this.mKeys = KEYS();
    }

    /**
     * Static synchronized method for accessing the {@linkplain PreferencesManager} instance.
     * Can be accessed from different threads.
     *
     * @return {@code PreferencesManager} instance.
     */
    public static synchronized PreferencesManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PreferencesManager();
        return INSTANCE;
    }

    /**
     * Method for creating the {@link SharedPreferences} instance that will be used
     * along the entire application. It must be called the first time the application
     * is executed so it will be available the entire app cycle.
     *
     * @param instance {@code Application} instance when it is being created and
     *                 should be called at {@link Application#onCreate()}.
     */
    public static synchronized void instantiate(@NonNull SecurePass instance) {
        if (INSTANCE == null)
            INSTANCE = new PreferencesManager(instance);
    }

    /**
     * Applies in background the recently applied values for the correspondent keys
     * calling {@link SharedPreferences#edit()}.
     *
     * @param keyName the key that will be updated.
     * @param value   the new value for the key.
     * @param clazz   the class of the value.
     */
    private void applyInBackground(@NonNull String keyName, @NonNull Object value,
                                   @NonNull Class<?> clazz) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String key = mKeys.get(keyName);
        switch (clazz.getSimpleName()) {
            case "boolean":
                editor.putBoolean(key, (Boolean) value);
                break;
            default:
                break;
        }
        editor.apply();
    }

    /**
     * Returns whether the application has been executed.
     * @return {@code boolean} 'True' if initialized else 'False'.
     */
    @Override
    public boolean isApplicationInitialized() {
        return mSharedPreferences.getBoolean(mKeys.get(APP_FIRST_EXECUTED), false);
    }

    /**
     * Sets the application initialized (or not, for debugging process).
     * @param isInitialized {@code boolean} with the initialization status.
     */
    @Override
    public void applicationInitialized(boolean isInitialized) {
        applyInBackground(APP_FIRST_EXECUTED, isInitialized, Boolean.TYPE);
    }

    /**
     * Returns whether the database has been initialized or not.
     * @return {@code boolean} 'True' if initialized else 'False'.
     */
    @Override
    public boolean isDatabaseInitialized() {
        return false;
//        return mSharedPreferences.getBoolean(mKeys.get(DATABASE_INIT), false);
    }

    /**
     * Sets the database initialized (or not, for debugging process).
     * @param isInitialized {@code boolean} whether it is or not initialized.
     */
    @Override
    public void databaseInitialized(boolean isInitialized) {
        applyInBackground(DATABASE_INIT, isInitialized, Boolean.TYPE);
    }

    /**
     * Returns whether the privacy policy has been accepted or not.
     * @return {@code boolean} 'True' if accepted else 'False'.
     */
    @Override
    public boolean isPrivacyAccepted() {
        return mSharedPreferences.getBoolean(mKeys.get(PRIVACY_ACCEPT), false);
    }

    /**
     * Sets the privacy policy accepted (or not, for debugging process).
     * @param isAccepted {@code boolean} whether it is accepted or not.
     */
    @Override
    public void setPrivacyAccepted(boolean isAccepted) {
        applyInBackground(PRIVACY_ACCEPT, isAccepted, Boolean.TYPE);
    }

    /**
     * Returns whether the terms of service have been accepted or not.
     * @return {@code boolean} 'True' if accepted else 'False'.
     */
    @Override
    public boolean areTermsOfServiceAccepted() {
        return mSharedPreferences.getBoolean(mKeys.get(TERMS_OF_SERVICE_ACCEPT), false);
    }

    /**
     * Sets the terms of service accepted (or not, for debugging process).
     * @param isAccepted {@code boolean} whether they are accepted or not.
     */
    @Override
    public void setTermsOfServiceAccepted(boolean isAccepted) {
        applyInBackground(TERMS_OF_SERVICE_ACCEPT, isAccepted, Boolean.TYPE);
    }

    /**
     * Returns whether the software license has been accepted or not.
     * @return {@code boolean} 'True' if accepted else 'False'.
     */
    @Override
    public boolean isSoftwareLicenseAccepted() {
        return mSharedPreferences.getBoolean(mKeys.get(SOFTWARE_LICENSE_ACCEPT), false);
    }

    /**
     * Sets the software license accepted (or not, for debugging process).
     * @param isAccepted {@code boolean} whether it is accepted or not.
     */
    @Override
    public void setSoftwareLicenseAccepted(boolean isAccepted) {
        applyInBackground(SOFTWARE_LICENSE_ACCEPT, isAccepted, Boolean.TYPE);
    }

    /**
     * Checks whether the application licenses are all accepted (or someone is missing).
     * @return {@code boolean} 'True' if all accepted, else 'False'.
     */
    @Override
    public boolean isApplicationLicenseAccepted() {
        return isPrivacyAccepted() && areTermsOfServiceAccepted() && isSoftwareLicenseAccepted();
    }
}
