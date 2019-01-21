package javinator9889.securepass.util.resources;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import javinator9889.securepass.SecurePass;
import javinator9889.securepass.util.values.Constants.SHARED_PREF;

/**
 * Created by Javinator9889 on 04/04/2018.
 */
public class SharedPreferencesManager implements ISharedPreferencesManager {
    private SharedPreferences preferencesInstance;
    private Map<String, Object> sharedPreferencesKeys;
    private Map<String, Class> keysType;

    private SharedPreferencesManager() {
        SecurePass tempApplicationInstance = SecurePass.getApplicationInstance();
        this.preferencesInstance = tempApplicationInstance.getSharedPreferences(
                SHARED_PREF.FILENAME,
                SHARED_PREF.MODE);
        loadSharedPreferencesKeys();
    }

    public static SharedPreferencesManager newInstance() {
        return new SharedPreferencesManager();
    }

    public void initSharedPreferences() {
        for (String key : sharedPreferencesKeys.keySet()) {
            if (!preferencesInstance.contains(key)) {
                SharedPreferences.Editor newEntry = preferencesInstance.edit();
                switch (keysType.get(key).getSimpleName()) {
                    case "boolean":
                        newEntry.putBoolean(key, (boolean) sharedPreferencesKeys.get(key));
                        break;
                    case "int":
                        newEntry.putInt(key, (int) sharedPreferencesKeys.get(key));
                        break;
                    case "String":
                        newEntry.putString(key, String.valueOf(sharedPreferencesKeys.get(key)));
                        break;
                }
                newEntry.apply();
            }
        }
    }

    private String getKey(int position) {
        return String.valueOf(sharedPreferencesKeys.keySet().toArray()[position]);
    }

    @Override
    public boolean isApplicationInitialized() {
        String key = getKey(0);
        return preferencesInstance.getBoolean(key, (boolean) sharedPreferencesKeys.get(key));
    }

    @Override
    public void applicationInitialized(boolean isInitialized) {
        String key = getKey(0);
        preferencesInstance.edit().putBoolean(key, isInitialized).apply();
    }

    @Override
    public boolean isDatabaseInitialized() {
        String key = getKey(1);
        return preferencesInstance.getBoolean(key, (boolean) sharedPreferencesKeys.get(key));
    }

    @Override
    public void databaseInitialized(boolean isInitialized) {
        String key = getKey(1);
        preferencesInstance.edit().putBoolean(key, isInitialized).apply();
    }

    @Override
    public boolean isPrivacyAccepted() {
        String key = getKey(2);
        return preferencesInstance.getBoolean(key, (boolean) sharedPreferencesKeys.get(key));
    }

    @Override
    public void setPrivacyAccepted(boolean isAccepted) {
        String key = getKey(2);
        preferencesInstance.edit().putBoolean(key, isAccepted).apply();
    }

    @Override
    public boolean areTermsOfServiceAccepted() {
        String key = getKey(3);
        return preferencesInstance.getBoolean(key, (boolean) sharedPreferencesKeys.get(key));
    }

    @Override
    public void setTermsOfServiceAccepted(boolean isAccepted) {
        String key = getKey(3);
        preferencesInstance.edit().putBoolean(key, isAccepted).apply();
    }

    @Override
    public boolean isSoftwareLicenseAccepted() {
        String key = getKey(4);
        return preferencesInstance.getBoolean(key, (boolean) sharedPreferencesKeys.get(key));
    }

    @Override
    public void setSoftwareLicenseAccepted(boolean isAccepted) {
        String key = getKey(4);
        preferencesInstance.edit().putBoolean(key, isAccepted).apply();
    }

    @Override
    public boolean isApplicationLicenseAccepted() {
        return isSoftwareLicenseAccepted() && isPrivacyAccepted() && areTermsOfServiceAccepted();
    }

    private void loadSharedPreferencesKeys() {
        sharedPreferencesKeys = new HashMap<>();
        keysType = new HashMap<>();
        for (int i = 0; i < SHARED_PREF.VALUES.length; ++i) {
            sharedPreferencesKeys.put(
                    String.valueOf(SHARED_PREF.VALUES[i][0]),
                    SHARED_PREF.VALUES[i][1]);
            keysType.put(String.valueOf(SHARED_PREF.VALUES[i][0]),
                    SHARED_PREF.VALUES[i][2].getClass());
        }
    }
}
