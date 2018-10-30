package javinator9889.securepass.io.database.operations;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.util.values.Constants.SQL;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class CommonOperations {
    private static final String TAG = "Database Operations";
    private SQLiteDatabase mDatabase;

    /**
     * Public constructor for creating this class - use this instad of
     * {@link #newInstance(DatabaseManager)}
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object
     * @see DatabaseManager
     */
    public CommonOperations(DatabaseManager databaseInstance) {
        try {
            databaseInstance.getDatabaseInitializer().join();
            this.mDatabase = databaseInstance.getDatabaseInstance();
        } catch (InterruptedException e) {
            Log.e(getTag(), "Error while trying to join thread \""
                    + SQL.DB_INIT_THREAD_NAME + "\". Interrupted exception. Full trace:");
            e.printStackTrace();
        }
    }

    /**
     * Public class loader - uses {@link #CommonOperations(DatabaseManager) private constructor}
     *
     * @param databaseManagerInstance instance of the {@link DatabaseManager} object
     * @return <code>CommonOperations</code>
     * @deprecated use {@link #CommonOperations(DatabaseManager)} instead
     */
    @Deprecated
    public static CommonOperations newInstance(DatabaseManager databaseManagerInstance) {
        return new CommonOperations(databaseManagerInstance);
    }

    /**
     * Method for changing the database password
     *
     * @param newDatabasePassword new password
     * @see SQLiteDatabase#changePassword(String)
     */
    public void changeDatabasePassword(@NonNull String newDatabasePassword) {
        this.mDatabase.changePassword(newDatabasePassword);
    }

    /**
     * Registers the default ("Global") category
     *
     * @return <code>long</code> with the category ID (should be '1')
     * @see SQLiteDatabase#insertWithOnConflict(String, String, ContentValues, int)
     */
    public long registerDefaultCategory() {
        ContentValues params = new ContentValues();
        params.put(SQL.CATEGORY.C_ID, 1);
        params.put(SQL.CATEGORY.C_NAME, "Global");
        return mDatabase.insertWithOnConflict(SQL.CATEGORY.NAME, null, params,
                SQLiteDatabase.CONFLICT_IGNORE);
    }

    /**
     * Gets the tag for {@link Log} output - should be overridden
     *
     * @return <code>String</code> with the tag name
     */
    public String getTag() {
        return TAG;
    }

    /**
     * Tries to insert the data at the specified table
     *
     * @param tableName the table to insert the row
     * @param params    map containing the initial values
     * @return the row ID or -1 if any error occurred
     * @see SQLiteDatabase#insert(String, String, ContentValues)
     * @see ContentValues
     */
    protected long insert(@NonNull String tableName, @NonNull ContentValues params) {
        return mDatabase.insert(tableName, null, params);
    }

    /**
     * Tries to insert the data at the specified table ignoring conflicts
     *
     * @param tableName the table to insert the row
     * @param params    map containing the initial values
     * @return the row ID or -1 if any error occurred
     * @see SQLiteDatabase#insertWithOnConflict(String, String, ContentValues, int)
     * @see SQLiteDatabase#CONFLICT_IGNORE
     * @see ContentValues
     */
    protected long insertIgnoreOnConflict(@NonNull String tableName,
                                          @NonNull ContentValues params) {
        return mDatabase.insertWithOnConflict(tableName, null, params, SQLiteDatabase
                .CONFLICT_IGNORE);
    }

    /**
     * Tries to insert the data at the specified table aborting when conflicts occur
     *
     * @param tableName the table to insert the row
     * @param params    map containing the initial values
     * @return the row ID or -1 if any error occurred
     * @see SQLiteDatabase#insertWithOnConflict(String, String, ContentValues, int)
     * @see SQLiteDatabase#CONFLICT_ABORT
     * @see ContentValues
     */
    protected long insertAbortOnConflict(@NonNull String tableName, @NonNull ContentValues params) {
        return mDatabase.insertWithOnConflict(tableName, null, params, SQLiteDatabase
                .CONFLICT_ABORT);
    }

    /**
     * Tries to insert the data at the specified table doing nothing with conflicts
     *
     * @param tableName the table to insert the row
     * @param params    map containing the initial values
     * @return the row ID or -1 if any error ocurred
     * @see SQLiteDatabase#insertWithOnConflict(String, String, ContentValues, int)
     * @see SQLiteDatabase#CONFLICT_NONE
     * @see ContentValues
     */
    protected long insertNoneOnConflit(@NonNull String tableName, @NonNull ContentValues params) {
        return mDatabase.insertWithOnConflict(tableName, null, params, SQLiteDatabase
                .CONFLICT_NONE);
    }

    /**
     * Tries to insert the data at the specified table failing with conflicts
     *
     * @param tableName the table to insert the row
     * @param params    map containing the initial values
     * @return the row ID or -1 if any error occurred
     * @see SQLiteDatabase#insertWithOnConflict(String, String, ContentValues, int)
     * @see SQLiteDatabase#CONFLICT_FAIL
     * @see ContentValues
     */
    protected long insertFailOnConflict(@NonNull String tableName, @NonNull ContentValues params) {
        return mDatabase.insertWithOnConflict(tableName, null, params, SQLiteDatabase
                .CONFLICT_FAIL);
    }

    /**
     * Tries to insert the data at the specified table replacing conflicts
     *
     * @param tableName the table to insert the row
     * @param params    map containing the initial values
     * @return the row ID or -1 if any error occurred
     * @see SQLiteDatabase#insertWithOnConflict(String, String, ContentValues, int)
     * @see SQLiteDatabase#CONFLICT_REPLACE
     * @see ContentValues
     */
    protected long insertReplaceOnConflict(@NonNull String tableName,
                                           @NonNull ContentValues params) {
        return mDatabase.insertWithOnConflict(tableName, null, params, SQLiteDatabase
                .CONFLICT_REPLACE);
    }

    /**
     * Tries to insert the data at the specified table rolling back on conflicts
     *
     * @param tableName the table to insert the row
     * @param params    map containing the initial values
     * @return the row ID or -1 if any error occurred
     * @see SQLiteDatabase#insertWithOnConflict(String, String, ContentValues, int)
     * @see SQLiteDatabase#CONFLICT_ROLLBACK
     * @see ContentValues
     */
    protected long insertRollbackOnConflict(@NonNull String tableName,
                                            @NonNull ContentValues params) {
        return mDatabase.insertWithOnConflict(tableName, null, params, SQLiteDatabase
                .CONFLICT_ROLLBACK);
    }

    /**
     * Updates table information if exists
     *
     * @param tableName   table name to modify
     * @param values      new values to insert
     * @param whereClause SQL where clause
     * @param whereArgs   SQL where args (probably it is an ID)
     * @return the affected rows
     * @throws net.sqlcipher.SQLException If the SQL string is invalid for some reason
     * @throws IllegalStateException      if the database is not open
     */
    protected int update(@NonNull String tableName,
                         @NonNull ContentValues values,
                         @NonNull String whereClause,
                         @NonNull String[] whereArgs) {
        return mDatabase.update(tableName, values, whereClause, whereArgs);
    }

    /**
     * Obtains the specified table information and saves it inside a {@link Cursor}
     *
     * @param tableName    where to obtain the values.
     * @param columnsToGet which columns to get - passing null will return all columns.
     * @param whereClause  where to obtain data - passing null will return all rows.
     * @param args         args for modifying the whereClause statement.
     * @param groupBy      a filter declaring how to group rows - passing null will cause the rows to
     *                     not to be grouped.
     * @param having       a filter declaring which row groups to include in the cursor, if groupBy is
     *                     being used
     * @param orderBy      how to order the rows - passing null will use the default sort order
     * @return a {@link Cursor} object positioned before the first entry
     * @throws net.sqlcipher.SQLException if there is an issue executing the SQL
     * @throws IllegalStateException      if the database is not open
     * @see Cursor
     */
    protected Cursor get(@NonNull String tableName,
                         @Nullable String[] columnsToGet,
                         @Nullable String whereClause,
                         @Nullable String[] args,
                         @Nullable String groupBy,
                         @Nullable String having,
                         @Nullable String orderBy) {
        return mDatabase
                .query(tableName, columnsToGet, whereClause, args, groupBy, having, orderBy);
    }

    /**
     * Gets all fields for the given table name
     *
     * @param tableName where to obtain the values
     * @return a {@link Cursor} object positioned before the first entry
     * @throws net.sqlcipher.SQLException if there is an issue executing the SQL
     * @throws IllegalStateException      if the database is not open
     * @see Cursor
     */
    protected Cursor getAll(@NonNull String tableName) {
        return get(tableName, null, null, null, null, null, null);
    }

    /**
     * Deletes all columns for a given ID of a table
     *
     * @param tableName   table to delete values
     * @param idFieldName table column name which contains the ID
     * @param id          ID of the row to delete
     * @return the number of rows affected
     * @throws net.sqlcipher.SQLException If the SQL string is invalid for some reason
     * @throws IllegalStateException      if the database is not open
     */
    protected int delete(@NonNull String tableName, @NonNull String idFieldName, long id) {
        String whereClause = idFieldName + "=?";
        return mDatabase.delete(tableName, whereClause, setSelectionArgs(id));
    }

    /**
     * Closes database connection
     *
     * @see SQLiteDatabase#close()
     */
    public void finishConnection() {
        mDatabase.close();
    }

    /**
     * Casts {@code long} to a {@code String[]} array
     *
     * @param id ID to cast
     * @return {@code String[]} with the ID value
     */
    private String[] setSelectionArgs(long id) {
        return new String[]{String.valueOf(id)};
    }
}
