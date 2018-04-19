package javinator9889.securepass.util.cipher;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chamber.java.library.SharedChamber;
import com.chamber.java.library.model.ChamberType;

/**
 * Created by Javinator9889 on 19/04/2018.
 */
public class PasswordSaver implements PasswordCipher {
    //private SecurePreferences preferences;
    private SharedChamber sharedChamber;

    private PasswordSaver(@NonNull Context context) {
        //this.context = context;
        /*Context context = SecurePass.getApplicationInstance().getApplicationContext();
        AesCbcWithIntegrity.SecretKeys keys = AesCbcWithIntegrity.generateKeyFromPassword(
                Build.ID,
                AesCbcWithIntegrity.generateSalt(),
                1000);
        preferences = new SecurePreferences(
                context,
                keys,
                FILENAME);*/
        sharedChamber = new SharedChamber.ChamberBuilder(context)
                .setChamberType(ChamberType.KEY_256)
                .enableCrypto(true, true)
                .setFolderName(FOLDER_NAME)
                .buildChamber();
    }

    public static PasswordSaver instantiate(@NonNull Context context) {
        return new PasswordSaver(context);
        /*try {
            return new PasswordSaver();
        } catch (GeneralSecurityException e) {
            throw new PasswordCipherException(e);
        }*/
    }

    @Override
    public void putPassword(@NonNull String password) {
        /*SecurePreferences.Editor editor = preferences.edit();
        Set<String> value = new HashSet<>();
        value.add(password);
        editor.putStringSet(KEY, value);
        //editor.p
        //editor.putString(KEY, password);
        //editor.commit();
        editor.apply();*/
        sharedChamber.put(KEY, password);
    }

    @Override
    public String getPassword() {
        /*Map<String, ?> map = preferences.getAll();
        String value = DEF_VALUE;
        for (String key : map.keySet()) {
            Log.d("KEY", key);
            Log.d("Contains?", String.valueOf(preferences.contains(key)));
            Log.d("Contains?", String.valueOf(preferences.contains(KEY)));
            //Log.d("Value", preferences.getAll().get(KEY));
            Log.d("Value", preferences.getString(KEY, DEF_VALUE));
            value = preferences.getString(key, DEF_VALUE);
        }
        return value;*/
        /*Set<String> result = preferences.getStringSet(KEY, new HashSet<>());
        Iterator<String> iterator = result.iterator();
        String pass = DEF_VALUE;
        while (iterator.hasNext())
            pass = iterator.next();
        return pass;*/
        /*for (String pass : result) {
            return pass;
        }*/
        //return String.valueOf(result.toArray()[0]);
        //return preferences.getString(KEY, DEF_VALUE);
        return sharedChamber.getString(KEY, DEF_VALUE);
    }

    @Override
    public void changeCurrentExistingPassword(@NonNull String password) {
        /*SharedPreferences.Editor editor = preferences.edit();
        editor.remove("");
        editor.apply();
        putPassword(password);*/
        sharedChamber.remove(KEY);
        putPassword(password);
    }
}
