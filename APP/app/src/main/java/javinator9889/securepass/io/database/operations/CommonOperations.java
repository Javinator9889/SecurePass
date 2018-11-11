package javinator9889.securepass.io.database.operations;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.errors.database.ExecutorNonDefinedException;
import javinator9889.securepass.errors.database.NoJobsEnqueuedError;
import javinator9889.securepass.errors.database.OverriddenMethodsNotDefinedError;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.util.threading.ThreadExceptionListener;
import javinator9889.securepass.util.threading.ThreadingExecutor;
import javinator9889.securepass.util.threading.thread.NotifyingThread;
import javinator9889.securepass.util.values.Constants.SQL;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class CommonOperations {
    private static final String TAG = "Database Operations";
    private static final String WHERE_ID = null;
    private static final String TABLE_NAME = null;
    private SQLiteDatabase mDatabase;
    private ThreadingExecutor mExecutor;

    /**
     * Public constructor for creating this class - use this instead of
     * {@link #newInstance(DatabaseManager)} - does not assign any {@code onExceptionListener}.
     * For that, use {@link #CommonOperations(DatabaseManager, ThreadExceptionListener)}.
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object.
     * @see DatabaseManager
     */
    public CommonOperations(@NonNull DatabaseManager databaseInstance) {
        this(databaseInstance, null);
    }

    /**
     * Public constructor for creating this class and providing a {@link ThreadExceptionListener}
     *
     * @param databaseInstance    instance of the {@link DatabaseManager} object
     * @param onExceptionListener nullable class listening on threading errors
     * @see DatabaseManager
     * @see ThreadExceptionListener
     */
    public CommonOperations(@NonNull DatabaseManager databaseInstance,
                            @Nullable ThreadExceptionListener onExceptionListener) {
        try {
            databaseInstance.getDatabaseInitializer().join();
            mDatabase = databaseInstance.getDatabaseInstance();
            mExecutor = onExceptionListener == null ?
                    ThreadingExecutor.Builder().build() :
                    ThreadingExecutor.Builder().addOnThreadExceptionListener(onExceptionListener)
                            .build();
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
     * Gets the tag for {@link android.util.Log} output - should be overridden
     *
     * @return <code>String</code> with the tag name
     */
    public String getTag() {
        return TAG;
    }

    /**
     * Gets the WHERE ID clause for using {@link #scheduleUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the WHERE clause - null if not defined
     */
    @Nullable
    public String getWhereId() {
        return WHERE_ID;
    }

    /**
     * Gets the TABLE NAME for using {@link #scheduleUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the TABLE NAME - null if not defined
     */
    @Nullable
    public String getTableName() {
        return TABLE_NAME;
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
    protected Cursor getAll(@NonNull String tableName, @Nullable String orderBy) {
        return get(tableName, null, null, null, null, null, orderBy);
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

    /**
     * Converts an array of {@code Object} varargs into a {@code String} array
     *
     * @param args amount of elements to store in a {@code String} array
     * @return {@code String[]} containing the args
     */
    protected String[] whereArgs(@NonNull Object... args) {
        return Arrays.copyOf(args, args.length, String[].class);
    }

    /**
     * Schedules an update operation by using the given ID and new values
     *
     * @param id     ID where changing values
     * @param params new values
     * @throws OverriddenMethodsNotDefinedError when {@link #getTableName()} or
     *                                          {@link #getWhereId()} are not overridden
     * @throws ExecutorNonDefinedException      when constructor
     *                                          {@link #CommonOperations(DatabaseManager,
     *                                          ThreadExceptionListener)}
     *                                          has not been used
     * @see ThreadingExecutor#add(NotifyingThread)
     * @see ThreadingExecutor#run()
     */
    protected void scheduleUpdateExecutor(long id, @NonNull ContentValues params) {
        if (getTableName() == null || getWhereId() == null) {
            StringBuilder nonDefinedAttributes = new StringBuilder(3);
            boolean isFirstDefined = false;
            if (getTableName() == null) {
                nonDefinedAttributes.append("getTableName()");
                isFirstDefined = true;
            }
            if (getWhereId() == null)
                if (isFirstDefined)
                    nonDefinedAttributes.append(", ");
            nonDefinedAttributes.append("getWhereId()");
            throw new OverriddenMethodsNotDefinedError("Following methods not overridden: " +
                    nonDefinedAttributes.toString());
        }
        if (mExecutor == null)
            throw new ExecutorNonDefinedException("You must define the \"ThreadingExecutor\" by " +
                    "using \"CommonOperations(DatabaseManager, ThreadExceptionListener)\" " +
                    "constructor");
        mExecutor.add(new NotifyingThread() {
            @Override
            public void doRun() {
                update(getTableName(), params, getWhereId(), whereArgs(id));
            }
        });
    }

    /**
     * Runs the {@link ThreadingExecutor} - only necessary when doing UPDATE operations
     *
     * @throws NoJobsEnqueuedError when there is no job added to the queue
     */
    public void apply() {
        if (mExecutor.isAnyPendingThread())
            mExecutor.run();
        else
            throw new NoJobsEnqueuedError("There is no pending thread waiting to be executed");
    }
}
