package javinator9889.securepass.errors;

import androidx.annotation.Nullable;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public class NoBackupSpecifiedException extends RuntimeException {
    public NoBackupSpecifiedException(@Nullable String message) {
        super(message);
    }
}
