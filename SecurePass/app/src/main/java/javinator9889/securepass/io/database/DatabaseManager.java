package javinator9889.securepass.io.database;

import android.content.Context;
import android.util.Log;

import com.github.javinator9889.exporter.FileToBytesExporter;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.PreferencesManager;
import javinator9889.securepass.util.values.Constants;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Based on SQLCipher documentation
 */

public class DatabaseManager {
    private static DatabaseManager INSTANCE = null;
    //    private Context mActivityContext;
    private File mDatabaseFile;
    private String mUserHashedPassword;
    private Thread mDatabaseInitializer;

    /**
     * Generates a new instance and also initializes the database with {@link #initDB(Context)} method
     *
     * @param activityContext    <code>Context</code> when instantiating the class
     * @param userHashedPassword password for encrypting/decrypting the database
     * @see Context
     * @see #initDB(Context)
     */
    private DatabaseManager(Context activityContext, @NonNull String userHashedPassword) {
//        this.mActivityContext = activityContext;
        this.mUserHashedPassword = userHashedPassword;
        initDB(activityContext);
        INSTANCE = this;
    }

    /**
     * Generates a <code>DatabaseManager</code> instance. If the libraries are loaded
     *
     * @param activityContext    <code>Context</code> when instantiating the class
     * @param userHashedPassword password for encrypting/decrypting the database
     * @return <code>DatabaseManager</code> instance
     * @see Context
     */
    @NonNull
    public static DatabaseManager getInstance(Context activityContext,
                                              @NonNull String userHashedPassword) {
        return INSTANCE == null ?
                new DatabaseManager(activityContext, userHashedPassword) :
                INSTANCE;
    }

    /**
     * Initializes de database with the required script on a <b>separate thread</b>.
     * <p>
     * It reads from {@link ISharedPreferencesManager} if the
     * {@link ISharedPreferencesManager#isApplicationInitialized() application is initialized
     * } for creating the correspondent files. <br />
     * If the
     * {@link ISharedPreferencesManager#isDatabaseInitialized() database is initialized} it
     * will not load again the scripts. Else, by using {@link IOManager} it will create the
     * tables inside SQLite
     * </p>
     *
     * @param databaseContext <code>Context</code> when instantiating the class
     * @see SQLiteDatabase
     * @see PreferencesManager
     * @see IOManager#loadSQLScript()
     */
    private void initDB(@NonNull final Context databaseContext) {
        mDatabaseInitializer = new Thread(new Runnable() {
            //            private final Context databaseContext = DatabaseManager.this.mActivityContext;
            private final String databasePassword = DatabaseManager.this.mUserHashedPassword;

            @Override
            public void run() {
                SQLiteDatabase.loadLibs(databaseContext);
                ISharedPreferencesManager preferencesManager = PreferencesManager.getInstance();
                mDatabaseFile = databaseContext
                        .getDatabasePath(Constants.SQL.DB_FILENAME);
                if (!preferencesManager.isApplicationInitialized()) {
                    try {
                        mDatabaseFile.delete();
                        mDatabaseFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                        mDatabaseFile,
                        databasePassword,
                        null);
                List<String> databaseScripts;
                try {
                    if (!preferencesManager.isDatabaseInitialized()) {
                        databaseScripts = IOManager.newInstance(databaseContext).obtainSQLScript();
                        for (String script : databaseScripts) {
                            database.execSQL(script);
                        }
                        DatabaseManager.this.createDefaultCategory();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getCause());
                } finally {
                    database.close();
                }
            }
        });
        mDatabaseInitializer.setName(Constants.SQL.DB_INIT_THREAD_NAME);
        mDatabaseInitializer.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        mDatabaseInitializer.run();
    }

    /**
     * Gets the database instance from SQLite
     *
     * @return <code>SQLiteDatabase</code> instance
     */
    public SQLiteDatabase getDatabaseInstance() {
//        SQLiteDatabase.loadLibs(context);
        return SQLiteDatabase.openOrCreateDatabase(mDatabaseFile, mUserHashedPassword, null);
    }

    /**
     * Gets the thread when running for joining the database initializer with current thread
     *
     * @return <code>Thread</code> with the init process
     * @see Thread
     * @see Thread#join()
     */
    public Thread getDatabaseInitializer() {
        return mDatabaseInitializer;
    }

    /**
     * Finalizes this instance (e.g.: the ongoing database operations are finished) - method should
     * be called whether is going to use a new instance.
     */
    public static void finish() {
        INSTANCE = null;
    }

    /**
     * Creates the default category necessary in order to allow the hole application work
     */
    private void createDefaultCategory() {
        CommonOperations operations = new CommonOperations(this);
        operations.registerDefaultCategory();
        operations.finishConnection();
    }

    /**
     * Class for handling <code>Threading</code> exceptions
     */
    private class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
        /**
         * {@inheritDoc}
         */
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Log.e(t.getName(), "Exception in thread \"" + t.getName() + "\" with " +
                    "exception thrown -> " + e.getMessage() + "\nFull trace: ");
            e.printStackTrace();
        }
    }
}
