package javinator9889.securepass.data.container;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;

/**
 * Created by Javinator9889 on 06/04/2018.
 */
public class ClassContainer implements Serializable {
    private List<Category> categories;
    private List<Entry> entries;
    private List<QRCode> qrCodes;
    private List<Field> fields;
    private List<SecurityCode> securityCodes;
    private Map<String, Object> userSharedPreferences;

    public ClassContainer(@NonNull List<Category> categories,
                          @NonNull List<Entry> entries,
                          @NonNull List<QRCode> qrCodes,
                          @NonNull List<Field> fields,
                          @NonNull List<SecurityCode> securityCodes,
                          @NonNull Map<String, Object> userSharedPreferences) {
        this.categories = categories;
        this.entries = entries;
        this.qrCodes = qrCodes;
        this.fields = fields;
        this.securityCodes = securityCodes;
        this.userSharedPreferences = userSharedPreferences;
    }

    public ClassContainer(){}

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public void setQrCodes(List<QRCode> qrCodes) {
        this.qrCodes = qrCodes;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void setSecurityCodes(List<SecurityCode> securityCodes) {
        this.securityCodes = securityCodes;
    }

    public void setUserSharedPreferences(Map<String, Object> userSharedPreferences) {
        this.userSharedPreferences = userSharedPreferences;
    }

    public void storeDataInDB() {}
}
