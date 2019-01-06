package javinator9889.securepass.data.secret;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Class that contains Fields with SecurityCodes
 * Created by Javinator9889 on 29/03/2018.
 */
public class SecurityCode implements Serializable {
    private long mId;
    private String mAccountName;

    /**
     * Public available constructor for SecurityCodes
     *
     * @param id          SecurityCode ID
     * @param accountName SecurityCode name
     */
    public SecurityCode(long id, @NonNull String accountName) {
        this.mId = id;
        this.mAccountName = accountName;
    }

    /**
     * Obtains current ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    /**
     * Obtains current SecurityCode account name
     *
     * @return <code>String</code> with the name
     */
    public String getAccountName() {
        return mAccountName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SecurityCode ID: " + mId +
                "\nSecurityCode account name: " + mAccountName + "\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityCode that = (SecurityCode) o;
        return mId == that.mId &&
                Objects.equals(mAccountName, that.mAccountName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mAccountName);
    }
}
