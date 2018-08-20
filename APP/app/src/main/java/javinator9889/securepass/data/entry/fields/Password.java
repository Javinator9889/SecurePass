package javinator9889.securepass.data.entry.fields;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public class Password implements IPassword, Serializable {
    private long id;
    private String password;
    private String fieldDescription;

    public Password(long id, @NonNull String password, @NonNull String fieldDescription) {
        this.id = id;
        this.password = password;
        this.fieldDescription = fieldDescription;
    }

    @Override
    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public long getPasswordID() {
        return id;
    }

    @Override
    public void setPasswordID(long passwordID) {
        this.id = passwordID;
    }

    @Override
    public void setFieldDescription(@NonNull String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    @Override
    public String getFieldDescription() {
        return fieldDescription;
    }

    @Override
    public String toString() {
        return "Password: " + getPassword() + "\nID: " + getPasswordID() + "\nField description: " +
                getFieldDescription();
    }
}
