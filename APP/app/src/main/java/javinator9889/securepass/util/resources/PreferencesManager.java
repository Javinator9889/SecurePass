package javinator9889.securepass.util.resources;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import javinator9889.securepass.SecurePass;
import javinator9889.securepass.util.values.Constants.SHARED_PREF;

import static javinator9889.securepass.util.values.Constants.SHARED_PREF.*;

/**
 * Created by Javinator9889 on 06/10/2018.
 */
public final class PreferencesManager implements ISharedPreferencesManager {
    private Map<String, ?> mValues;
    private HashMap<String, String> mKeys;
    private SharedPreferences mSharedPreferences;
    private static PreferencesManager INSTANCE;

    public static synchronized PreferencesManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PreferencesManager();
        return INSTANCE;
    }

    private PreferencesManager() {
        SecurePass appInstance = SecurePass.getApplicationInstance();
        this.mSharedPreferences = appInstance
                .getSharedPreferences(FILENAME, MODE);
        this.mValues = mSharedPreferences.getAll();
        this.mKeys = KEYS();
    }

    @Override
    public boolean isApplicationInitialized() {
        return Boolean.class.cast(mValues.get(mKeys.get(APP_FIRST_EXECUTED)));
    }

    @Override
    public void applicationInitialized(boolean isInitialized) {

    }

    @Override
    public boolean isDatabaseInitialized() {
        return false;
    }

    @Override
    public void databaseInitialized(boolean isInitialized) {

    }

    @Override
    public boolean isPrivacyAccepted() {
        return false;
    }

    @Override
    public void setPrivacyAccepted(boolean isAccepted) {

    }

    @Override
    public boolean areTermsOfServiceAccepted() {
        return false;
    }

    @Override
    public void setTermsOfServiceAccepted(boolean isAccepted) {

    }

    @Override
    public boolean isSoftwareLicenseAccepted() {
        return false;
    }

    @Override
    public void setSoftwareLicenseAccepted(boolean isAccepted) {

    }

    @Override
    public boolean isApplicationLicenseAccepted() {
        return false;
    }
}
