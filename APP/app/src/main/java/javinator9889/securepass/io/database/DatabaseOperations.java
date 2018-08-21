package javinator9889.securepass.io.database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javinator9889.securepass.data.entry.fields.IImage;
import javinator9889.securepass.data.entry.fields.ILongText;
import javinator9889.securepass.data.entry.fields.IPassword;
import javinator9889.securepass.data.entry.fields.ISmallText;
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
//        ContentValues params = setParams(DatabaseTables.CATEGORY, "Global");
        ContentValues params = new ContentValues();
        params.put(SQL.CATEGORY.C_ID, 1);
        params.put(SQL.CATEGORY.C_NAME, "Global");
        return database.insertWithOnConflict(SQL.CATEGORY.NAME, null, params,
                SQLiteDatabase.CONFLICT_IGNORE);
    }

    public long registerNewEntry(@NonNull String entryName, @NonNull String icon,
                                 long entryParentCategoryId,
                                 @Nullable IPassword[] entryPasswords,
                                 @Nullable ISmallText[] entrySmallTexts,
                                 @Nullable ILongText[] entryLongTexts,
                                 @Nullable IImage[] entryImages) {
        ContentValues params = setParams(DatabaseTables.ENTRY, entryName, icon,
                entryParentCategoryId);
        long entryId = database.insert(SQL.ENTRY.NAME, null, params);
        if (entryPasswords != null) {
            for (IPassword password : entryPasswords) {
                ContentValues passwordParams = setParams(DatabaseTables.PASSWORD,
                        password.getPassword(), password.getFieldDescription(), entryId,
                        entryParentCategoryId);
                long passwordId =
                        database.insert(SQL.PASSWORD.NAME, null, passwordParams);
                password.setPasswordID(passwordId);
            }
        }
        if (entrySmallTexts != null) {
            for (ISmallText smallText : entrySmallTexts) {
                ContentValues smallTextParams = setParams(DatabaseTables.SMALL_TEXT,
                        smallText.getText(), smallText.getFieldDescription(), entryId,
                        entryParentCategoryId);
                long smallTextId =
                        database.insert(SQL.SMALL_TEXT.NAME, null, smallTextParams);
                smallText.setSmallTextID(smallTextId);
            }
        }
        if (entryLongTexts != null) {
            for (ILongText longText : entryLongTexts) {
                ContentValues longTextParams = setParams(DatabaseTables.LONG_TEXT,
                        longText.getText(), longText.getFieldDescription(), entryId,
                        entryParentCategoryId);
                long longTextId =
                        database.insert(SQL.LONG_TEXT.NAME, null, longTextParams);
                longText.setLongTextID(longTextId);
            }
        }
        if (entryImages != null) {
            for (IImage image : entryImages) {
                ContentValues imageParams = setParams(DatabaseTables.IMAGE,
                        image.getImageSource(), image.getFieldDescription(), entryId,
                        entryParentCategoryId);
                long imageId =
                        database.insert(SQL.IMAGE.NAME, null, imageParams);
                image.setImageID(imageId);
            }
        }
        return entryId;
    }
    
//    public long registerNewAccount(@NonNull String accountName, @NonNull String accountPassword,
//                                   @NonNull String icon, @Nullable String description,
//                                   long entryParentCategoryId) {
//        ContentValues params = setParams(DatabaseTables.ENTRY, accountName, accountPassword, icon,
//                description, entryParentCategoryId);
//        return database.insert(SQL.ENTRY.NAME, null, params);
//    }

    public long registerNewCategory(@NonNull String name) {
        ContentValues params = setParams(DatabaseTables.CATEGORY, name);
        return database.insert(SQL.CATEGORY.NAME, null, params);
    }

    public long registerQRCode(long sourceEntryId, @NonNull String name,
                               @Nullable String description, @NonNull String qrData) {
        ContentValues params = setParams(DatabaseTables.QR_CODE, name, description, qrData,
                sourceEntryId);
        return database.insert(SQL.QR_CODE.NAME, null, params);
    }

    public long registerNewSecurityCodeSource(@NonNull String securityCodeName) {
        ContentValues params = setParams(DatabaseTables.SECURITY_CODE,
                securityCodeName);
        return database.insert(SQL.SECURITY_CODE.NAME, null, params);
    }

    public long registerNewFieldForSecurityCodeSource(@NonNull String fieldCode,
                                                      boolean isCodeUsed,
                                                      long parentSecurityCodeId) {
        ContentValues params = setParams(DatabaseTables.FIELD,
                fieldCode, isCodeUsed, parentSecurityCodeId);
        return database.insert(SQL.FIELD.NAME, null, params);
    }

    public void deleteAccount(long accountID) {
        String[] selectionArgs = setSelectionArgs(accountID);
        database.delete(SQL.ENTRY.NAME, SQL.DB_DELETE_ENTRY_WHERE_CLAUSE, selectionArgs);
    }

    public void deleteCategory(long categoryID) {
        String[] selectionArgs = setSelectionArgs(categoryID);
        ContentValues entryUpdatedValues = new ContentValues();
        entryUpdatedValues.put(SQL.ENTRY.E_PARENT_CATEGORY, 0);
        database.update(
                SQL.ENTRY.NAME, entryUpdatedValues,
                SQL.DB_UPDATE_FOR_DELETED_CATEGORY_WHERE_CLAUSE,
                selectionArgs);
        database.delete(SQL.CATEGORY.NAME, SQL.DB_DELETE_CATEGORY_WHERE_CLAUSE, selectionArgs);
    }

    public void deleteQRCode(long qrID) {
        String[] selectionArgs = setSelectionArgs(qrID);
        database.delete(SQL.QR_CODE.NAME, SQL.DB_DELETE_QR_CODE_WHERE_CLAUSE, selectionArgs);
    }

    public void deleteSecurityCode(long securityCodeID) {
        String[] selectionArgs = setSelectionArgs(securityCodeID);
        database.delete(SQL.FIELD.NAME,
                SQL.DB_DELETE_FIELD_FROM_SECURITY_CODE_WHERE_CLAUSE, selectionArgs);
        database.delete(SQL.SECURITY_CODE.NAME, SQL.DB_DELETE_SECURITY_CODE_WHERE_CLAUSE,
                selectionArgs);
    }

    public void deleteField(long fieldCodeID) {
        String[] selectionArgs = setSelectionArgs(fieldCodeID);
        database.delete(SQL.FIELD.NAME, SQL.DB_DELETE_FIELD_WHERE_CLAUSE, selectionArgs);
    }

    public void updateInformationForEntry(@NonNull String entryName, @NonNull String icon,
                                          long entryParentCategoryId,
                                          long entryId,
                                          @Nullable IPassword[] entryPasswords,
                                          @Nullable ISmallText[] entrySmallTexts,
                                          @Nullable ILongText[] entryLongTexts,
                                          @Nullable IImage[] entryImages) {
        ContentValues params = setParams(DatabaseTables.ENTRY, entryName, icon,
                entryParentCategoryId);
        String[] selectionArgs = setSelectionArgs(entryId);
        database.update(SQL.ENTRY.NAME, params, SQL.DB_UPDATE_ENTRY_WHERE_CLAUSE,
                selectionArgs);
        if (entryPasswords != null) {
            for (IPassword password : entryPasswords) {
                ContentValues passwordParams = setParams(DatabaseTables.PASSWORD,
                        password.getPassword(), password.getFieldDescription(), entryId,
                        entryParentCategoryId);
                String[] passwordSelectionArgs = setSelectionArgs(password.getPasswordID());
                database.update(SQL.PASSWORD.NAME, passwordParams,
                        SQL.DB_UPDATE_PASSWORD_WHERE_CLAUSE, passwordSelectionArgs);
            }
        }
        if (entrySmallTexts != null) {
            for (ISmallText smallText : entrySmallTexts) {
                ContentValues smallTextParams = setParams(DatabaseTables.SMALL_TEXT,
                        smallText.getText(), smallText.getFieldDescription(), entryId,
                        entryParentCategoryId);
                String[] smallTextSelectionArgs = setSelectionArgs(smallText.getSmallTextID());
                database.update(SQL.SMALL_TEXT.NAME, smallTextParams,
                        SQL.DB_UPDATE_SMALL_TEXT_WHERE_CLAUSE, smallTextSelectionArgs);
            }
        }
        if (entryLongTexts != null) {
            for (ILongText longText : entryLongTexts) {
                ContentValues longTextParams = setParams(DatabaseTables.LONG_TEXT,
                        longText.getText(), longText.getFieldDescription(), entryId,
                        entryParentCategoryId);
                String[] longTextSelectionArgs = setSelectionArgs(longText.getLongTextID());
                database.update(SQL.LONG_TEXT.NAME, longTextParams,
                        SQL.DB_UPDATE_LONG_TEXT_WHERE_CLAUSE, longTextSelectionArgs);
            }
        }
        if (entryImages != null) {
            for (IImage image : entryImages) {
                ContentValues imageParams = setParams(DatabaseTables.IMAGE,
                        image.getImageSource(), image.getFieldDescription(), entryId,
                        entryParentCategoryId);
                String[] imageSelectionArgs = setSelectionArgs(image.getImageID());
                database.update(SQL.IMAGE.NAME, imageParams,
                        SQL.DB_UPDATE_IMAGE_WHERE_CLAUSE, imageSelectionArgs);
            }
        }
    }

//    public void updateInformationForEntry(@NonNull String accountName,
//                                          @NonNull String accountPassword,
//                                          @NonNull String icon,
//                                          @Nullable String description,
//                                          long parentEntryCategoryId,
//                                          long entryId) {
//        ContentValues params = setParams(DatabaseTables.ENTRY,
//                accountName,
//                accountPassword,
//                icon,
//                description,
//                parentEntryCategoryId);
//        String[] selectionArgs = setSelectionArgs(entryId);
//        database.update(SQL.ENTRY.NAME, params, SQL.DB_UPDATE_ENTRY_WHERE_CLAUSE, selectionArgs);
//    }

    public void updateInformationForCategory(@NonNull String categoryName, long categoryId) {
        ContentValues params = setParams(DatabaseTables.CATEGORY, categoryName);
        String[] selectionArgs = setSelectionArgs(categoryId);
        database.update(SQL.CATEGORY.NAME, params, SQL.DB_UPDATE_CATEGORY_WHERE_CLAUSE,
                selectionArgs);
    }

    public void updateInformationForQRCode(long sourceEntryId, @NonNull String name,
                                           @Nullable String description, @NonNull String qrData,
                                           long qrCodeId) {
        ContentValues params = setParams(DatabaseTables.QR_CODE, name,
                description,
                qrData,
                sourceEntryId);
        String[] selectionArgs = setSelectionArgs(qrCodeId);
        database.update(SQL.QR_CODE.NAME, params, SQL.DB_UPDATE_QR_CODE_WHERE_CLAUSE,
                selectionArgs);
    }

    public void updateInformationForSecurityCode(@NonNull String newSecurityCodeName,
                                                 long securityCodeId) {
        ContentValues params = setParams(DatabaseTables.SECURITY_CODE,
                newSecurityCodeName);
        String[] selectionArgs = setSelectionArgs(securityCodeId);
        database.update(SQL.SECURITY_CODE.NAME, params, SQL.DB_UPDATE_SECURITY_CODE_WHERE_CLAUSE,
                selectionArgs);
    }

    public void updateInformationForField(@NonNull String newFieldCode,
                                          boolean isCodeUsed,
                                          long fieldId, long securityCodeId) {
        ContentValues params = setParams(DatabaseTables.FIELD, newFieldCode,
                isCodeUsed, securityCodeId);
        String[] selectionArgs = setSelectionArgs(fieldId);
        database.update(SQL.FIELD.NAME, params, SQL.DB_UPDATE_FIELD_WHERE_CLAUSE, selectionArgs);
    }

    private Map<String, Object> getValuesFromCursor(Cursor sourceData, DatabaseTables type) {
        Map<String, Object> result = new HashMap<>();
        switch (type) {
            case CATEGORY:
                long categoryId = sourceData.getLong(
                        sourceData.getColumnIndexOrThrow(SQL.CATEGORY.C_ID));
                String categoryName = sourceData.getString(
                        sourceData.getColumnIndexOrThrow(SQL.CATEGORY.C_NAME));
                result.put(SQL.CATEGORY.C_ID, categoryId);
                result.put(SQL.CATEGORY.C_NAME, categoryName);
                return result;
            case ENTRY:
                long entryId = sourceData.getLong(
                        sourceData.getColumnIndexOrThrow(SQL.ENTRY.E_ID));
                String entryName = sourceData.getString(
                        sourceData.getColumnIndexOrThrow(SQL.ENTRY.E_NAME)
                );
                String entryIcon = sourceData.getString(
                        sourceData.getColumnIndexOrThrow(SQL.ENTRY.E_ICON)
                );
                String entryParentCategory = sourceData.getString(
                        sourceData
                                .getColumnIndexOrThrow(SQL.ENTRY.E_PARENT_CATEGORY)
                );
                result.put(SQL.ENTRY.E_ID, entryId);
                result.put(SQL.ENTRY.E_NAME, entryName);
                result.put(SQL.ENTRY.E_ICON, entryIcon);
                result.put(SQL.ENTRY.E_PARENT_CATEGORY, entryParentCategory);
                return result;
            case QR_CODE:
                long qrCodeId = sourceData.getLong(
                        sourceData.getColumnIndexOrThrow(SQL.QR_CODE.Q_ID)
                );
                String qrCodeName = sourceData.getString(
                        sourceData.getColumnIndexOrThrow(SQL.QR_CODE.Q_NAME)
                );
                String qrCodeData = sourceData.getString(
                        sourceData.getColumnIndexOrThrow(SQL.QR_CODE.Q_DATA)
                );
                String qrCodeDescription = sourceData.getString(
                        sourceData
                                .getColumnIndexOrThrow(SQL.QR_CODE.Q_DESCRIPTION)
                );
                long qrCodeParentEntryId = sourceData.getLong(
                        sourceData.getColumnIndexOrThrow(SQL.QR_CODE.Q_PARENT_ENTRY)
                );
                result.put(SQL.QR_CODE.Q_ID, qrCodeId);
                result.put(SQL.QR_CODE.Q_NAME, qrCodeName);
                result.put(SQL.QR_CODE.Q_DATA, qrCodeData);
                result.put(SQL.QR_CODE.Q_DESCRIPTION, qrCodeDescription);
                result.put(SQL.QR_CODE.Q_PARENT_ENTRY, qrCodeParentEntryId);
                return result;
            case SECURITY_CODE:
                long securityCodeId = sourceData.getLong(
                        sourceData.getColumnIndexOrThrow(SQL.SECURITY_CODE.S_ID)
                );
                String securityCodeName = sourceData.getString(
                        sourceData.getColumnIndexOrThrow(SQL.SECURITY_CODE.S_ACCOUNT_NAME)
                );
                result.put(SQL.SECURITY_CODE.S_ID, securityCodeId);
                result.put(SQL.SECURITY_CODE.S_ACCOUNT_NAME, securityCodeName);
                return result;
            case FIELD:
                long fieldId = sourceData.getLong(
                        sourceData.getColumnIndexOrThrow(SQL.FIELD.F_ID)
                );
                String fieldCode = sourceData.getString(
                        sourceData.getColumnIndexOrThrow(SQL.FIELD.F_CODE)
                );
                boolean isFieldUsed = sourceData.getInt(
                        sourceData.getColumnIndexOrThrow(SQL.FIELD.F_USED)
                ) == 1;
                long fieldParentSecurityCode = sourceData.getLong(
                        sourceData.getColumnIndexOrThrow(SQL.FIELD.F_PARENT_SECURITY_CODE)
                );
                result.put(SQL.FIELD.F_ID, fieldId);
                result.put(SQL.FIELD.F_CODE, fieldCode);
                result.put(SQL.FIELD.F_USED, isFieldUsed);
                result.put(SQL.FIELD.F_PARENT_SECURITY_CODE, fieldParentSecurityCode);
                return result;
            default:
                return result;
        }
    }

    public List<Map<String, Object>> getAllCategories() {
        String sortOrder = SQL.CATEGORY.C_ID + " DESC";
        Cursor categoriesCursor = database.query(SQL.CATEGORY.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
        List<Map<String, Object>> itemsObtained = new ArrayList<>();
        while (categoriesCursor.moveToNext()) {
            itemsObtained.add(getValuesFromCursor(categoriesCursor, DatabaseTables.CATEGORY));
        }
        categoriesCursor.close();
        return itemsObtained;
//        List<String[]> categoriesList = new ArrayList<>();
//        if (categoriesCursor.moveToFirst()) {
//            do {
//                categoriesList.add(loadDataIntoArray(categoriesCursor));
//            } while (categoriesCursor.moveToNext());
//        }
//        categoriesCursor.close();
//        return categoriesList;
    }

    public List<Map<String, Object>> getAllEntries() {
        String sortOrder = SQL.ENTRY.E_ID + " DESC";
        Cursor entriesCursor = database.query(SQL.ENTRY.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
        List<Map<String, Object>> entriesList = new ArrayList<>();
        while (entriesCursor.moveToNext()) {
            entriesList.add(getValuesFromCursor(entriesCursor, DatabaseTables.ENTRY));
        }
        entriesCursor.close();
        return entriesList;
    }

    public List<Map<String, Object>> getAllQRCodes() {
        String sortOrder = SQL.QR_CODE.Q_ID + " DESC";
        Cursor qrCodesCursor = database.query(SQL.QR_CODE.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
        List<Map<String, Object>> qrCodesList = new ArrayList<>();
        while (qrCodesCursor.moveToNext()) {
            qrCodesList.add(getValuesFromCursor(qrCodesCursor, DatabaseTables.QR_CODE));
        }
        qrCodesCursor.close();
        return qrCodesList;
    }

    public List<Map<String, Object>> getAllSecurityCodes() {
        String sortOrder = SQL.SECURITY_CODE.S_ID + " DESC";
        Cursor securityCodesCursor = database.query(SQL.SECURITY_CODE.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
        List<Map<String, Object>> securityCodesList = new ArrayList<>();
        while (securityCodesCursor.moveToNext()) {
            securityCodesList.add(getValuesFromCursor(securityCodesCursor,
                    DatabaseTables.SECURITY_CODE));
        }
        securityCodesCursor.close();
        return securityCodesList;
    }

    public List<Map<String, Object>> getAllFields() {
        String sortOrder = SQL.FIELD.F_ID + " DESC";
        Cursor fieldsCursor = database.query(SQL.FIELD.NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
        List<Map<String, Object>> fieldsList = new ArrayList<>();
        while (fieldsCursor.moveToNext()) {
            fieldsList.add(getValuesFromCursor(fieldsCursor, DatabaseTables.FIELD));
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
                params.put(SQL.ENTRY.E_NAME, (String) values[0]);
//                params.put(SQL.ENTRY.E_PASSWORD, (String) values[1]);
                params.put(SQL.ENTRY.E_ICON, (String) values[1]);
//                params.put(SQL.ENTRY.E_DESCRIPTION, (String) values[3]);
                params.put(SQL.ENTRY.E_PARENT_CATEGORY, (long) values[2]);
                break;
            case QR_CODE:
                params.put(SQL.QR_CODE.Q_NAME, (String) values[0]);
                params.put(SQL.QR_CODE.Q_DESCRIPTION, (String) values[1]);
                params.put(SQL.QR_CODE.Q_DATA, (String) values[2]);
                params.put(SQL.QR_CODE.Q_PARENT_ENTRY, (long) values[3]);
                break;
            case SECURITY_CODE:
                params.put(SQL.SECURITY_CODE.S_ACCOUNT_NAME, (String) values[0]);
                break;
            case FIELD:
                params.put(SQL.FIELD.F_CODE, (String) values[0]);
                params.put(SQL.FIELD.F_USED, (boolean) values[1]);
                params.put(SQL.FIELD.F_PARENT_SECURITY_CODE, (long) values[2]);
                break;
            case PASSWORD:
                params.put(SQL.PASSWORD.P_PASSWORD, (String) values[0]);
                params.put(SQL.PASSWORD.P_DESCRIPTION, (String) values[1]);
                params.put(SQL.PASSWORD.P_PARENT_ENTRY, (long) values[2]);
                params.put(SQL.PASSWORD.P_PARENT_CATEGORY, (long) values[3]);
                break;
            case IMAGE:
                params.put(SQL.IMAGE.I_SOURCE, (String) values[0]);
                params.put(SQL.IMAGE.I_DESCRIPTION, (String) values[1]);
                params.put(SQL.IMAGE.I_PARENT_ENTRY, (long) values[2]);
                params.put(SQL.IMAGE.I_PARENT_CATEGORY, (long) values[3]);
                break;
            case SMALL_TEXT:
                params.put(SQL.SMALL_TEXT.S_TEXT, (String) values[0]);
                params.put(SQL.SMALL_TEXT.S_DESCRIPTION, (String) values[1]);
                params.put(SQL.SMALL_TEXT.S_PARENT_ENTRY, (long) values[2]);
                params.put(SQL.SMALL_TEXT.S_PARENT_CATEGORY, (long) values[3]);
                break;
            case LONG_TEXT:
                params.put(SQL.LONG_TEXT.L_TEXT, (String) values[0]);
                params.put(SQL.LONG_TEXT.L_DESCRIPTION, (String) values[1]);
                params.put(SQL.LONG_TEXT.L_PARENT_ENTRY, (long) values[2]);
                params.put(SQL.LONG_TEXT.L_PARENT_CATEGORY, (long) values[3]);
                break;
        }
        return params;
    }

    private String[] setSelectionArgs(long id) {
        return new String[]{String.valueOf(id)};
    }
}
