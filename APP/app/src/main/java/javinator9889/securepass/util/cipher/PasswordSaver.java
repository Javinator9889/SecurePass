package javinator9889.securepass.util.cipher;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chamber.java.library.SharedChamber;
import com.chamber.java.library.model.ChamberType;

/**
 * Created by Javinator9889 on 19/04/2018.
 */
public class PasswordSaver implements PasswordCipher {
    private SharedChamber securedPreferences;

    private PasswordSaver(@NonNull Context context) {
        securedPreferences = new SharedChamber.ChamberBuilder(context)
                .setChamberType(ChamberType.KEY_256)
                .enableCrypto(true, true)
                .setFolderName(FOLDER_NAME)
                .buildChamber();
    }

    public static PasswordSaver instantiate(@NonNull Context context) {
        return new PasswordSaver(context);
    }

    @Override
    public void putPassword(@NonNull String password) {
        securedPreferences.put(KEY, password);
    }

    @Override
    public String getPassword() {
        return securedPreferences.getString(KEY, DEF_VALUE);
    }

    @Override
    public void changeCurrentExistingPassword(@NonNull String password) {
        securedPreferences.remove(KEY);
        putPassword(password);
    }
}
