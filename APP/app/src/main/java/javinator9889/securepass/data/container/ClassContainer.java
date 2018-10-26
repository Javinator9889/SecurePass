package javinator9889.securepass.data.container;

import android.content.Context;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javinator9889.securepass.SecurePass;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.DatabaseOperations;

/**
 * Created by Javinator9889 on 06/04/2018.
 * CLASS FOR TESTINGS
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

//    public void storeDataInDB() {
//        Context appContext = SecurePass.getApplicationInstance().getApplicationContext();
//        String password = IOManager.newInstance(appContext).readPassword();
//        DatabaseManager manager = DatabaseManager.newInstance(appContext, password);
//        DatabaseOperations operations = DatabaseOperations.newInstance(manager);
//        for (Category actualCategory : categories)
//            operations.registerNewCategory(actualCategory.getName());
//        for (Entry entry : entries)
//            operations.registerNewAccount(
//                    entry.getAccountName(),
//                    entry.getAccountPassword(),
//                    entry.getIcon(),
//                    entry.getDescription(),
//                    entry.getCategory().getId());
//        for (QRCode qrCode : qrCodes)
//            operations.registerQRCode(
//                    qrCode.getEntry().getId(),
//                    qrCode.getName(),
//                    qrCode.getDescription(),
//                    qrCode.getQrData());
//        for (SecurityCode code : securityCodes)
//            operations.registerNewSecurityCodeSource(code.getAccountName());
//        for (Field field : fields)
//            operations.registerNewFieldForSecurityCodeSource(field.getCode(), field.isCodeUsed(),
//                    field.getSecurityCodeID());
//        operations.finishConnection();
//    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public List<QRCode> getQrCodes() {
        return qrCodes;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<SecurityCode> getSecurityCodes() {
        return securityCodes;
    }

    public Map<String, Object> getUserSharedPreferences() {
        return userSharedPreferences;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Category category : categories)
            builder.append(category.toString());
        for (Entry entry : entries)
            builder.append(entry.toString());
        for (QRCode qrCode : qrCodes)
            builder.append(qrCode.toString());
        for (Field field : fields)
            builder.append(field.toString());
        for (SecurityCode securityCode : securityCodes)
            builder.append(securityCode.toString());
        builder.append(userSharedPreferences.toString());
        return builder.toString();
        //return super.toString();
    }
}
