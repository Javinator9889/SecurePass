package javinator9889.securepass.data.entry.fields;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Stores Password fields
 * Created by Javinator9889 on 16/08/2018.
 */
public class Password implements IPassword, Serializable {
    private long mId;
    private String mPassword;
    private String mFieldDescription;

    /**
     * Public available constructor fon generating a new instance
     * @param id password ID
     * @param password current password - cannot be null
     * @param fieldDescription description for this field - cannot be null
     */
    public Password(long id, @NonNull String password, @NonNull String fieldDescription) {
        this.mId = id;
        this.mPassword = password;
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Sets a new password
     * @param password non null String with the password
     */
    @Override
    public void setPassword(@NonNull String password) {
        this.mPassword = password;
    }

    /**
     * Obtains the current stored password
     * @return <code>String</code> with the password
     */
    @Override
    public String getPassword() {
        return mPassword;
    }

    /**
     * Gets the current password ID
     * @return <code>long</code> with the ID
     */
    @Override
    public long getPasswordID() {
        return mId;
    }

    /**
     * Sets a new ID - this method should not be called
     * @param passwordID new ID
     */
    @Override
    public void setPasswordID(long passwordID) {
        this.mId = passwordID;
    }

    /**
     * Updates the field description by te given one
     * @param fieldDescription new description
     * @see ICommonMethods
     */
    @Override
    public void setFieldDescription(@NonNull String fieldDescription) {
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Obtains the current field description
     * @return <code>String</code> with the description
     * @see ICommonMethods
     */
    @Override
    public String getFieldDescription() {
        return mFieldDescription;
    }

    @Override
    public String toString() {
        return "Password: " + getPassword() + "\nID: " + getPasswordID() + "\nField description: " +
                getFieldDescription();
    }
}
