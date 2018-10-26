package javinator9889.securepass.data.secret;

import androidx.annotation.NonNull;

import java.io.Serializable;

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
     * @return <code>String</code> with the name
     */
    public String getAccountName() {
        return mAccountName;
    }

    @Override
    public String toString() {
        return "SecurityCode ID: " + mId +
                "\nSecurityCode account name: " + mAccountName + "\n";
    }
}
