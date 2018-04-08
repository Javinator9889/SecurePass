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

    @Override
    public boolean isApplicationInitialized() {
        String key = String.valueOf(sharedPreferencesKeys.keySet().toArray()[0]);
        return preferencesInstance.getBoolean(key, (boolean) sharedPreferencesKeys.get(key));
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
