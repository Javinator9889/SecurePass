package javinator9889.securepass.data.secret;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Contains Fields of the {@link SecurityCode} class
 * Created by Javinator9889 on 29/03/2018.
 */
public class Field implements Serializable {
    private long mId;
    private String mCode;
    private boolean mIsCodeUsed;
    private long mSecurityCodeId;

    /**
     * Public available constructor for Field
     *
     * @param id         field ID
     * @param code       field code
     * @param isCodeUsed whether the code has been used or not
     * @param securityCodeId security code parent ID
     * @see SecurityCode
     */
    public Field(long id, @NonNull String code, boolean isCodeUsed, long securityCodeId) {
        this.mId = id;
        this.mCode = code;
        this.mIsCodeUsed = isCodeUsed;
        this.mSecurityCodeId = securityCodeId;
    }

    /**
     * Obtains parent ID
     *
     * @return <code>long</code> with the parent ID
     */
    public long getSecurityCodeID() {
        return mSecurityCodeId;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Field mCode: " + mCode +
                "\nField is used: " + mIsCodeUsed +
                "\nField field of: " + mSecurityCodeId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return mId == field.mId &&
                mIsCodeUsed == field.mIsCodeUsed &&
                mSecurityCodeId == field.mSecurityCodeId &&
                Objects.equals(mCode, field.mCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mCode, mIsCodeUsed, mSecurityCodeId);
    }
}
