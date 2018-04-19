package javinator9889.securepass.util.cipher;

import android.support.annotation.NonNull;

/**
 * Created by Javinator9889 on 18/04/2018.
 */
public interface PasswordCipher {
    String KEY = "password";
    String DEF_VALUE = "NONE";
    String FILENAME = "privatePrefs";
    String FOLDER_NAME = "private";
    void putPassword(@NonNull String password);
    String getPassword();
    void changeCurrentExistingPassword(@NonNull String password);
}
