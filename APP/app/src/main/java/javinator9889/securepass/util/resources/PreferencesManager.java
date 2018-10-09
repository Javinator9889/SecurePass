package javinator9889.securepass.util.resources;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Map;

import javinator9889.securepass.SecurePass;

import static javinator9889.securepass.util.values.Constants.SHARED_PREF.*;

/**
 * Created by Javinator9889 on 06/10/2018.
 */
public class PreferencesManager implements ISharedPreferencesManager {
    private Map<String, String> mKeys;
    private volatile SharedPreferences mSharedPreferences;
    private static PreferencesManager INSTANCE;

    public static synchronized PreferencesManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PreferencesManager();
        return INSTANCE;
    }

    public static synchronized void instantiate(@NonNull SecurePass instance) {
        if (INSTANCE == null)
            INSTANCE = new PreferencesManager(instance);
    }

    private PreferencesManager(@NonNull SecurePass instance) {
        this.mSharedPreferences = instance.getSharedPreferences(FILENAME, MODE);
        mKeys = KEYS();
    }

    private PreferencesManager() {
        SecurePass appInstance = SecurePass.getApplicationInstance();
        this.mSharedPreferences = appInstance
                .getSharedPreferences(FILENAME, MODE);
        this.mKeys = KEYS();
    }

    private void applyInBackground(@NonNull String keyName, @NonNull Object value,
                                   @NonNull Class clazz) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String key = mKeys.get(keyName);
        switch (clazz.getSimpleName()) {
            case "boolean":
                editor.putBoolean(key, Boolean.class.cast(value));
                break;
            default:
                break;
        }
        editor.apply();
    }

    @Override
    public boolean isApplicationInitialized() {
        return mSharedPreferences.getBoolean(mKeys.get(APP_FIRST_EXECUTED), false);
    }

    @Override
    public void applicationInitialized(boolean isInitialized) {
        applyInBackground(APP_FIRST_EXECUTED, isInitialized, Boolean.TYPE);
    }

    @Override
    public boolean isDatabaseInitialized() {
        return mSharedPreferences.getBoolean(mKeys.get(DATABASE_INIT), false);
    }

    @Override
    public void databaseInitialized(boolean isInitialized) {
        applyInBackground(DATABASE_INIT, isInitialized, Boolean.TYPE);
    }

    @Override
    public boolean isPrivacyAccepted() {
        return mSharedPreferences.getBoolean(mKeys.get(PRIVACY_ACCEPT), false);
    }

    @Override
    public void setPrivacyAccepted(boolean isAccepted) {
        applyInBackground(PRIVACY_ACCEPT, isAccepted, Boolean.TYPE);
    }

    @Override
    public boolean areTermsOfServiceAccepted() {
        return mSharedPreferences.getBoolean(mKeys.get(TERMS_OF_SERVICE_ACCEPT), false);
    }

    @Override
    public void setTermsOfServiceAccepted(boolean isAccepted) {
        applyInBackground(TERMS_OF_SERVICE_ACCEPT, isAccepted, Boolean.TYPE);
    }

    @Override
    public boolean isSoftwareLicenseAccepted() {
        return mSharedPreferences.getBoolean(mKeys.get(SOFTWARE_LICENSE_ACCEPT), false);
    }

    @Override
    public void setSoftwareLicenseAccepted(boolean isAccepted) {
        applyInBackground(SOFTWARE_LICENSE_ACCEPT, isAccepted, Boolean.TYPE);
    }

    @Override
    public boolean isApplicationLicenseAccepted() {
        return isPrivacyAccepted() && areTermsOfServiceAccepted() && isSoftwareLicenseAccepted();
    }
}
