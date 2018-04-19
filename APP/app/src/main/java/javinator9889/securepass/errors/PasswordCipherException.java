package javinator9889.securepass.errors;

import android.support.annotation.NonNull;

import java.security.GeneralSecurityException;

/**
 * Created by Javinator9889 on 19/04/2018.
 */
public class PasswordCipherException extends GeneralSecurityException {
    public PasswordCipherException(@NonNull Throwable e) {
        super(e);
    }
}
