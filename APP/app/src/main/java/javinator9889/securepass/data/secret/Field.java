package javinator9889.securepass.data.secret;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Contains Fields of the {@link SecurityCode} class
 * Created by Javinator9889 on 29/03/2018.
 */
public class Field implements Serializable {
    private long mId;
    private String mCode;
    private boolean mIsCodeUsed;
    private SecurityCode mFieldOf;

    /**
     * Public available constructor for Field
     *
     * @param id         field ID
     * @param code       field code
     * @param isCodeUsed whether the code has been used or not
     * @param fieldOf    {@link SecurityCode} parent
     * @see SecurityCode
     */
    public Field(long id, @NonNull String code, boolean isCodeUsed, @NonNull SecurityCode fieldOf) {
        this.mId = id;
        this.mCode = code;
        this.mIsCodeUsed = isCodeUsed;
        this.mFieldOf = fieldOf;
    }

    /**
     * Obtains parent ID
     *
     * @return <code>long</code> with the parent ID
     */
    public long getSecurityCodeID() {
        return mFieldOf.getId();
    }

    /**
     * Obtains field code
     *
     * @return <code>String</code> with the code
     */
    public String getCode() {
        return mCode;
    }

    /**
     * Determine whether the current code has been used
     *
     * @return <code>boolean</code>, 'true' if used, else 'false'
     */
    public boolean isCodeUsed() {
        return mIsCodeUsed;
    }

    /**
     * Gets current field ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    @Override
    public String toString() {
        return "Field mCode: " + mCode + "\nField is used: " + mIsCodeUsed + "\nField field of: " +
                mFieldOf.toString();
    }
}
