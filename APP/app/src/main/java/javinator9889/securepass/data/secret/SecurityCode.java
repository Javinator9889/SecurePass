package javinator9889.securepass.data.secret;

import android.support.annotation.NonNull;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class SecurityCode {
    private int id;
    private String accountName;

    public SecurityCode(int id, @NonNull String accountName) {
        this.id = id;
        this.accountName = accountName;
    }

    public int getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }
}
