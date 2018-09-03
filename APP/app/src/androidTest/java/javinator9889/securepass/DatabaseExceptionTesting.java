package javinator9889.securepass;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.common.hash.Hashing;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javinator9889.securepass.io.database.DatabaseManager;

/**
 * Created by Javinator9889 on 03/09/2018.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseExceptionTesting {
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void wrongPassword() {
        SQLiteDatabase.loadLibs(mContext);
        String databaseTempFilePath = mContext.getCacheDir().getAbsolutePath() + "/test.db";
        File databaseTempFile = new File(databaseTempFilePath);
        try {
            databaseTempFile.delete();
            databaseTempFile.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        String firstPass = Hashing.sha256().hashString("password", StandardCharsets.UTF_8).toString();
        String secondPass = Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString();
//        DatabaseManager manager = DatabaseManager.newInstance(mContext, firstPass);
//        try {
//            manager.getDatabaseInitializer().join();
//            manager.getDatabaseInstance().close();
//        } catch (InterruptedException e) {
//            System.err.println(e.getMessage());
//        }
//        manager = DatabaseManager.newInstance(mContext, secondPass);
//        try {
//            manager.getDatabaseInitializer().join();
//            manager.getDatabaseInstance().close();
//        } catch (InterruptedException e) {
//            System.err.println(e.getMessage());
//        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(databaseTempFilePath, secondPass, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS `Category` (\n" +
                "  `idCategory` INT NOT NULL DEFAULT 0,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  PRIMARY KEY (`idCategory`));");
//        ContentValues values = new ContentValues();
//        values.put("name", "name");
//        long id = db.insert("Category", null, values);
        db.close();
        System.out.println("Database created");
        SQLiteDatabase db1 = SQLiteDatabase.openDatabase(databaseTempFilePath, firstPass, null, SQLiteDatabase.OPEN_READONLY);
//        Cursor query = db1.rawQuery("SELECT * FROM Category WHERE idCategory = ?", new String[]{String.valueOf(id)});
//        Cursor query = db1.rawQuery("SELECT * FROM ? WHERE type=?", new String[]{"dbname.sqlite_master", "'table'"});
        Cursor c = db1.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                System.out.println("Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }
//        db1.changePassword("password");
        db1.close();
        System.err.println("Invalid password used");
    }
}
