package javinator9889.securepass.data.secret;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class Field implements Serializable {
    private int id;
    private String code;
    private boolean isCodeUsed;
    private SecurityCode fieldOf;

    public Field(int id, @NonNull String code, boolean isCodeUsed, @NonNull SecurityCode fieldOf) {
        this.id = id;
        this.code = code;
        this.isCodeUsed = isCodeUsed;
        this.fieldOf = fieldOf;
    }

    public int getSecurityCodeID() {
        return fieldOf.getId();
    }

    public String getCode() {
        return code;
    }

    public boolean isCodeUsed() {
        return isCodeUsed;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Field code: " + code + "\nField is used: " + isCodeUsed + "\nField field of: " +
                fieldOf.toString();
    }
}
