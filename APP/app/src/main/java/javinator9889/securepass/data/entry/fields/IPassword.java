package javinator9889.securepass.data.entry.fields;

import android.support.annotation.NonNull;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public interface IPassword extends ICommonMethods {
    void setPassword(@NonNull String password);
    String getPassword();
    int getPasswordID();
}
