package javinator9889.securepass.errors;

import android.support.annotation.Nullable;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public class NoLayoutSpecifiedException extends RuntimeException {
    public NoLayoutSpecifiedException(@Nullable String message) {
        super(message);
    }
}
