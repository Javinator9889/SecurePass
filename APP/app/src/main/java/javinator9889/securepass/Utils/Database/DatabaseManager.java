package javinator9889.securepass.Utils.Database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

import javinator9889.securepass.Utils.IO.IOManager;
import javinator9889.securepass.Utils.Values.Constants;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Based on SQLCipher documentation
 */

public class DatabaseManager {
    private Context activityContext;
    private File databaseFile;
    private String userHashedPassword;

    @NonNull
    public static DatabaseManager newInstance(Context activityContext,
                                              @NonNull String userHashedPassword)
    {
        return new DatabaseManager(activityContext, userHashedPassword);
    }

    private DatabaseManager(Context activityContext, @NonNull String userHashedPassword) {
        this.activityContext = activityContext;
        this.userHashedPassword = userHashedPassword;
        initDB();
    }

    private void initDB() {
        Thread databaseInitializer = new Thread(new Runnable() {
            private final Context databaseContext = DatabaseManager.this.activityContext;
            private final String databasePassword = DatabaseManager.this.userHashedPassword;
            @Override
            public void run() {
                SQLiteDatabase.loadLibs(databaseContext);
                File tempDatabaseFile = databaseContext.getDatabasePath(Constants.SQL.DB_FILENAME);
                tempDatabaseFile.mkdirs();
                SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                        tempDatabaseFile,
                        databasePassword,
                        null);
                String databaseScript;
                try {
                    databaseScript = IOManager.newInstance(databaseContext).loadSQLScript();
                    database.execSQL(databaseScript);
                } catch (IOException e) {
                    throw new RuntimeException(e.getCause());
                } finally {
                    database.close();
                    DatabaseManager.this.databaseFile = tempDatabaseFile;
                }
            }
        });
        databaseInitializer.setName(Constants.SQL.DB_INIT_THREAD_NAME);
        databaseInitializer.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        databaseInitializer.run();
    }

    private class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Log.e(t.getName(), "Exception in thread \"" + t.getName() + "\" with " +
                    "exception thrown -> " + e.getMessage() + "\nFull trace: ");
            e.printStackTrace();
        }
    }
}
