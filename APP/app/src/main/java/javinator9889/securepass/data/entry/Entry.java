package javinator9889.securepass.data.entry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class Entry implements Serializable {
    private int id;
    private String accountName;
    private String accountPassword;
    private String icon;
    private String description;
    private Category category;

    public Entry(int id, @NonNull String accountName, @NonNull String accountPassword,
                 @NonNull String icon, @Nullable String description, @NonNull Category category) {
        this.id = id;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.icon = icon;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
