package javinator9889.securepass.data.secret;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class SecurityCode implements Serializable {
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

    @Override
    public String toString() {
        return "SecurityCode ID: " + id + "\nSecurityCode account name: " + accountName + "\n";
    }
}
