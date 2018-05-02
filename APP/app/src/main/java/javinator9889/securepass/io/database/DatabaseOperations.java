package javinator9889.securepass.io.database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;
import javinator9889.securepass.util.values.Constants.SQL;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class DatabaseOperations {
    private SQLiteDatabase database;

    private DatabaseOperations(DatabaseManager databaseInstance) {
        try {
            databaseInstance.getDatabaseInitializer().join();
            this.database = databaseInstance.getDatabaseInstance();
        } catch (InterruptedException e) {
            Log.e("DATABASE-OP", "Error while trying to join thread \""
                    + SQL.DB_INIT_THREAD_NAME + "\". Interrupted exception. Full trace:");
            e.printStackTrace();
        }
    }

    public static DatabaseOperations newInstance(DatabaseManager databaseManagerInstance) {
        return new DatabaseOperations(databaseManagerInstance);
    }

    public long registerDefaultCategory() {
        ContentValues params = new ContentValues();
        params.put("name", "Global");
        return database.insert("Category", null, params);
    }

    public long registerNewAccount(@NonNull String accountName, @NonNull String accountPassword,
                                   @NonNull String icon, @Nullable String description,
                                   @NonNull Category entryCategory) {
        /*Object[] params = new Object[]{
                accountName,
                accountPassword,
                icon,
                description,
                entryCategory.getId()
        };*/
        ContentValues params = new ContentValues();
        params.put("account", accountName);
        params.put("password", accountPassword);
        params.put("icon", icon);
        params.put("description", description);
        params.put("cidCategory", entryCategory.getId());
        return database.insert("Entry", null, params);
        //database.execSQL(SQL.DB_NEW_ENTRY, params);
    }

    public void registerNewCategory(@NonNull String name) {
        Object[] params = new Object[]{name};
        database.execSQL(SQL.DB_NEW_CATEGORY, params);
    }

    public void registerQRCode(@NonNull Entry sourceEntry, @NonNull String name,
                               @Nullable String description, @NonNull String qrData) {
        Object[] params = new Object[]{
                name,
                description,
                qrData,
                sourceEntry.getId()
        };
        database.execSQL(SQL.DB_NEW_QR, params);
    }

    public void registerNewSecurityCodeSource(@NonNull SecurityCode securityCodeSource) {
        Object[] params = new Object[]{
                securityCodeSource.getAccountName()
        };
        database.execSQL(SQL.DB_NEW_SECURITY_CODE, params);
    }

    public void registerNewFieldForSecurityCodeSource(@NonNull Field newField) {
        Object[] params = new Object[]{
                newField.getCode(),
                newField.isCodeUsed(),
                newField.getSecurityCodeID()
        };
        database.execSQL(SQL.DB_NEW_FIELD, params);
    }

    public void deleteAccount(int accountID) {
        Object[] params = new Object[]{accountID};
        database.execSQL(SQL.DB_DELETE_ENTRY, params);
    }

    public void deleteCategory(int categoryID) {
        Object[] params = new Object[]{categoryID};
        database.execSQL(SQL.DB_UPDATE_FOR_DELETED_CATEGORY, params);
        database.execSQL(SQL.DB_DELETE_CATEGORY, params);
    }

    public void deleteQRCode(int qrID) {
        Object[] params = new Object[]{qrID};
        database.execSQL(SQL.DB_DELETE_QR_CODE, params);
    }

    public void deleteSecurityCode(int securityCodeID) {
        Object[] params = new Object[]{securityCodeID};
        database.execSQL(SQL.DB_DELETE_FIELD_FROM_SECURITY_CODE, params);
        database.execSQL(SQL.DB_DELETE_SECURITY_CODE, params);
    }

    public void deleteField(@NonNull String fieldCode) {
        Object[] params = new Object[]{fieldCode};
        database.execSQL(SQL.DB_DELETE_FIELD, params);
    }

    public void updateInformationForEntry(@NonNull Entry modifiedEntry) {
        Object[] params = new Object[]{
                modifiedEntry.getAccountName(),
                modifiedEntry.getAccountPassword(),
                modifiedEntry.getIcon(),
                modifiedEntry.getDescription(),
                modifiedEntry.getCategory().getId(),
                modifiedEntry.getId()
        };
        database.execSQL(SQL.DB_UPDATE_ENTRY, params);
    }

    public void updateInformationForCategory(@NonNull Category modifiedCategory) {
        Object[] params = new Object[]{
                modifiedCategory.getName(),
                modifiedCategory.getId()
        };
        database.execSQL(SQL.DB_UPDATE_CATEGORY, params);
    }

    public void updateInformationForQRCode(@NonNull QRCode modifiedQRCode) {
        Object[] params = new Object[]{
                modifiedQRCode.getName(),
                modifiedQRCode.getDescription(),
                modifiedQRCode.getQrData(),
                modifiedQRCode.getEntry().getId(),
                modifiedQRCode.getId()
        };
        database.execSQL(SQL.DB_UPDATE_QR_CODE, params);
    }

    public void updateInformationForSecurityCode(@NonNull SecurityCode modifiedSecurityCode) {
        Object[] params = new Object[]{
                modifiedSecurityCode.getAccountName(),
                modifiedSecurityCode.getId()
        };
        database.execSQL(SQL.DB_UPDATE_SECURITY_CODE, params);
    }

    public void updateInformationForField(@NonNull Field modifiedField) {
        Object[] params = new Object[]{
                modifiedField.getCode(),
                modifiedField.isCodeUsed(),
                modifiedField.getId()
        };
        database.execSQL(SQL.DB_UPDATE_FIELD, params);
    }

    private String[] loadDataIntoArray(Cursor sourceData) {
        int numberOfColumns = sourceData.getColumnCount();
        String[] currentData = new String[numberOfColumns];
        for (int i = 0; i < numberOfColumns; ++i) {
            switch (sourceData.getType(i)) {
                case Cursor.FIELD_TYPE_INTEGER:
                    currentData[i] = String.valueOf(sourceData.getInt(i));
                    break;
                default:
                    currentData[i] = sourceData.getString(i);
                    break;
            }
        }
        return currentData;
    }

    public List<String[]> getAllCategories() {
        Cursor categoriesCursor = database.rawQuery(SQL.DB_SELECT_CATEGORIES, null);
        List<String[]> categoriesList = new ArrayList<>();
        if (categoriesCursor.moveToFirst()) {
            do {
                categoriesList.add(loadDataIntoArray(categoriesCursor));
            } while (categoriesCursor.moveToNext());
        }
        categoriesCursor.close();
        return categoriesList;
    }

    public List<String[]> getAllEntries() {
        Cursor entriesCursor = database.rawQuery(SQL.DB_SELECT_ENTRIES, null);
        List<String[]> entriesList = new ArrayList<>();
        if (entriesCursor.moveToFirst()) {
            do {
                entriesList.add(loadDataIntoArray(entriesCursor));
            } while (entriesCursor.moveToNext());
        }
        entriesCursor.close();
        return entriesList;
    }

    public List<String[]> getAllQRCodes() {
        Cursor qrCodesCursor = database.rawQuery(SQL.DB_SELECT_QR_CODES, null);
        List<String[]> qrCodesList = new ArrayList<>();
        if (qrCodesCursor.moveToFirst()) {
            do {
                qrCodesList.add(loadDataIntoArray(qrCodesCursor));
            } while (qrCodesCursor.moveToNext());
        }
        qrCodesCursor.close();
        return qrCodesList;
    }

    public List<String[]> getAllSecurityCodes() {
        Cursor securityCodesCursor = database.rawQuery(SQL.DB_SELECT_SECURITY_CODES, null);
        List<String[]> securityCodesList = new ArrayList<>();
        if (securityCodesCursor.moveToFirst()) {
            do {
                securityCodesList.add(loadDataIntoArray(securityCodesCursor));
            } while (securityCodesCursor.moveToNext());
        }
        securityCodesCursor.close();
        return securityCodesList;
    }

    public List<String[]> getAllFields() {
        Cursor fieldsCursor = database.rawQuery(SQL.DB_SELECT_FIELDS, null);
        List<String[]> fieldsList = new ArrayList<>();
        if (fieldsCursor.moveToFirst()) {
            do {
                fieldsList.add(loadDataIntoArray(fieldsCursor));
            } while (fieldsCursor.moveToNext());
        }
        fieldsCursor.close();
        return fieldsList;
    }

    public void finishConnection() {
        database.close();
    }
}
