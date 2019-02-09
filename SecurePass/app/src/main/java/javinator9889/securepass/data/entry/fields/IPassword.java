package javinator9889.securepass.data.entry.fields;

import androidx.annotation.NonNull;

/**
 * Interface for accessing the {@link Password} methods
 * Created by Javinator9889 on 16/08/2018.
 */
public interface IPassword extends ICommonMethods {
    /**
     * Sets a new password
     *
     * @param password non null String with the password
     */
    void setPassword(@NonNull String password);

    /**
     * Obtains the current stored password
     *
     * @return <code>String</code> with the password
     */
    String getPassword();

    /**
     * Gets the current password ID
     *
     * @return <code>long</code> with the ID
     */
    long getPasswordID();

    /**
     * Sets a new ID - this method should not be called
     *
     * @param passwordID new ID
     */
    void setPasswordID(long passwordID);
}
