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
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;
import javinator9889.securepass.util.values.Constants.SQL;
import javinator9889.securepass.util.values.DatabaseTables;

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
        ContentValues params = setParams(DatabaseTables.CATEGORY, "Global");
        return database.insert(SQL.CATEGORY.NAME, null, params);
    }

    public long registerNewAccount(@NonNull String accountName, @NonNull String accountPassword,
                                   @NonNull String icon, @Nullable String description,
                                   @NonNull Category entryCategory) {
        ContentValues params = setParams(DatabaseTables.ENTRY, accountName, accountPassword, icon,
                description, entryCategory.getId());
        return database.insert(SQL.ENTRY.NAME, null, params);
    }

    public long registerNewCategory(@NonNull String name) {
        ContentValues params = setParams(DatabaseTables.CATEGORY, name);
        return database.insert(SQL.CATEGORY.NAME, null, params);
    }

    public long registerQRCode(@NonNull Entry sourceEntry, @NonNull String name,
                               @Nullable String description, @NonNull String qrData) {
        ContentValues params = setParams(DatabaseTables.QR_CODE, name, description, qrData,
                sourceEntry.getId());
        return database.insert(SQL.QR_CODE.NAME, null, params);
    }

    public long registerNewSecurityCodeSource(@NonNull SecurityCode securityCodeSource) {
        ContentValues params = setParams(DatabaseTables.SECURITY_CODE,
                securityCodeSource.getAccountName());
        return database.insert(SQL.SECURITY_CODE.NAME, null, params);
    }

    public long registerNewFieldForSecurityCodeSource(@NonNull Field newField) {
        ContentValues params = setParams(DatabaseTables.FIELD,
                newField.getCode(), newField.isCodeUsed(), newField.getSecurityCodeID());
        return database.insert(SQL.FIELD.NAME, null, params);
    }

    public void deleteAccount(int accountID) {
        String[] selectionArgs = setSelectionArgs(accountID);
        database.delete(SQL.ENTRY.NAME, SQL.DB_DELETE_ENTRY_WHERE_CLAUSE, selectionArgs);
    }

    public void deleteCategory(int categoryID) {
        String[] selectionArgs = setSelectionArgs(categoryID);
        ContentValues entryUpdatedValues = new ContentValues();
        entryUpdatedValues.put(SQL.ENTRY.E_PARENT_CATEGORY, 0);
        database.update(
                SQL.ENTRY.NAME, entryUpdatedValues,
                SQL.DB_UPDATE_FOR_DELETED_CATEGORY_WHERE_CLAUSE,
                selectionArgs);
        database.delete(SQL.CATEGORY.NAME, SQL.DB_DELETE_CATEGORY_WHERE_CLAUSE, selectionArgs);
    }

    public void deleteQRCode(int qrID) {
        String[] selectionArgs = setSelectionArgs(qrID);
        database.delete(SQL.QR_CODE.NAME, SQL.DB_DELETE_QR_CODE_WHERE_CLAUSE, selectionArgs);
    }

    public void deleteSecurityCode(int securityCodeID) {
        String[] selectionArgs = setSelectionArgs(securityCodeID);
        database.delete(SQL.FIELD.NAME,
                SQL.DB_DELETE_FIELD_FROM_SECURITY_CODE_WHERE_CLAUSE, selectionArgs);
        database.delete(SQL.SECURITY_CODE.NAME, SQL.DB_DELETE_CATEGORY_WHERE_CLAUSE, selectionArgs);
    }

    public void deleteField(int fieldCodeID) {
        String[] selectionArgs = setSelectionArgs(fieldCodeID);
        database.delete(SQL.FIELD.NAME, SQL.DB_DELETE_FIELD_WHERE_CLAUSE, selectionArgs);
    }

    public void updateInformationForEntry(@NonNull String accountName,
                                          @NonNull String accountPassword,
                                          @NonNull String icon,
                                          @Nullable String description,
                                          @NonNull Category entryCategory,
                                          int entryId) {
        ContentValues params = setParams(DatabaseTables.ENTRY,
                accountName,
                accountPassword,
                icon,
                description,
                entryCategory.getId());
        String[] selectionArgs = setSelectionArgs(entryId);
        database.update(SQL.ENTRY.NAME, params, SQL.DB_UPDATE_ENTRY_WHERE_CLAUSE, selectionArgs);
    }

    public void updateInformationForCategory(@NonNull String categoryName, int categoryId) {
        ContentValues params = setParams(DatabaseTables.CATEGORY, categoryName);
        String[] selectionArgs = setSelectionArgs(categoryId);
        database.update(SQL.CATEGORY.NAME, params, SQL.DB_UPDATE_CATEGORY_WHERE_CLAUSE,
                selectionArgs);
    }

    public void updateInformationForQRCode(@NonNull Entry sourceEntry, @NonNull String name,
                                           @Nullable String description, @NonNull String qrData,
                                           int qrCodeId) {
        ContentValues params = setParams(DatabaseTables.QR_CODE, name,
                description,
                qrData,
                sourceEntry.getId());
        String[] selectionArgs = setSelectionArgs(qrCodeId);
        database.update(SQL.QR_CODE.NAME, params, SQL.DB_UPDATE_QR_CODE_WHERE_CLAUSE,
                selectionArgs);
    }

    public void updateInformationForSecurityCode(@NonNull SecurityCode modifiedSecurityCode) {
        ContentValues params = setParams(DatabaseTables.SECURITY_CODE,
                modifiedSecurityCode.getAccountName());
        String[] selectionArgs = setSelectionArgs(modifiedSecurityCode.getId());
        database.update(SQL.SECURITY_CODE.NAME, params, SQL.DB_UPDATE_SECURITY_CODE_WHERE_CLAUSE,
                selectionArgs);
    }

    public void updateInformationForField(@NonNull Field modifiedField) {
        ContentValues params = setParams(DatabaseTables.FIELD, modifiedField.getCode(),
                modifiedField.isCodeUsed());
        String[] selectionArgs = setSelectionArgs(modifiedField.getId());
        database.update(SQL.FIELD.NAME, params, SQL.DB_UPDATE_FIELD_WHERE_CLAUSE, selectionArgs);
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
        String sortOrder = SQL.CATEGORY.C_ID + " DESC";
        Cursor categoriesCursor = database.query(SQL.CATEGORY.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
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
        String sortOrder = SQL.ENTRY.E_ID + " DESC";
        Cursor entriesCursor = database.query(SQL.ENTRY.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
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
        String sortOrder = SQL.QR_CODE.Q_ID + " DESC";
        Cursor qrCodesCursor = database.query(SQL.QR_CODE.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
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
        String sortOrder = SQL.SECURITY_CODE.S_ID + " DESC";
        Cursor securityCodesCursor = database.query(SQL.SECURITY_CODE.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
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
        String sortOrder = SQL.FIELD.F_ID + " DESC";
        Cursor fieldsCursor = database.query(SQL.FIELD.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
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

    private ContentValues setParams(DatabaseTables table, Object... values) {
        ContentValues params = new ContentValues();
        switch (table) {
            case CATEGORY:
                params.put(SQL.CATEGORY.C_NAME, (String) values[0]);
                break;
            case ENTRY:
                params.put(SQL.ENTRY.E_ACCOUNT, (String) values[0]);
                params.put(SQL.ENTRY.E_PASSWORD, (String) values[1]);
                params.put(SQL.ENTRY.E_ICON, (String) values[2]);
                params.put(SQL.ENTRY.E_DESCRIPTION, (String) values[3]);
                params.put(SQL.ENTRY.E_PARENT_CATEGORY, (int) values[4]);
                break;
            case QR_CODE:
                params.put(SQL.QR_CODE.NAME, (String) values[0]);
                params.put(SQL.QR_CODE.Q_DESCRIPTION, (String) values[1]);
                params.put(SQL.QR_CODE.Q_DATA, (String) values[3]);
                params.put(SQL.QR_CODE.Q_PARENT_ENTRY, (int) values[4]);
                break;
            case SECURITY_CODE:
                params.put(SQL.SECURITY_CODE.S_ACCOUNT_NAME, (String) values[0]);
                break;
            case FIELD:
                params.put(SQL.FIELD.F_CODE, (String) values[0]);
                params.put(SQL.FIELD.F_USED, (boolean) values[1]);
                params.put(SQL.FIELD.F_PARENT_SECURITY_CODE, (int) values[3]);
                break;
        }
        return params;
    }

    private String[] setSelectionArgs(int id) {
        return new String[]{String.valueOf(id)};
    }
}
