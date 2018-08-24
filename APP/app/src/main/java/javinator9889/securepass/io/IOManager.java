package javinator9889.securepass.io;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javinator9889.securepass.R;
import javinator9889.securepass.util.cipher.PasswordCipher;
import javinator9889.securepass.util.cipher.PasswordSaver;
import javinator9889.securepass.util.values.Constants;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Manage inputs-outputs from application
 */

public class IOManager {
    private Context activityContext;
    private File filesCache;
    private File databasePath;

    private IOManager(@NonNull Context activityContext) {
        this.activityContext = activityContext;
        this.filesCache = activityContext.getCacheDir();
        this.databasePath = activityContext.getDatabasePath(Constants.SQL.DB_FILENAME);
    }

    @NonNull
    public static IOManager newInstance(Context activityContext) {
        return new IOManager(activityContext);
    }

    public List<String> loadSQLScript() throws IOException {
        List<String> result = new ArrayList<>(5);
        int[] sqlScripts = new int[]{R.raw.create_category, R.raw.create_entry, R.raw.create_qrcode,
                R.raw.create_security_code, R.raw.create_field};
        for (int sqlScript : sqlScripts) {
            InputStream sqlScriptInputFile = this.activityContext.getResources()
                    .openRawResource(sqlScript);
            StringBuilder builder = new StringBuilder();
            BufferedReader sqlStringsInFile = new BufferedReader(
                    new InputStreamReader(sqlScriptInputFile));
            String currentLine;
            while ((currentLine = sqlStringsInFile.readLine()) != null)
                builder.append(currentLine).append("\n");
            result.add(builder.toString());
        }
        return result;
    }

    public void storePassword(@NonNull String userPassword) {
        PasswordCipher passwordSaver = PasswordSaver.instantiate(activityContext);
        passwordSaver.putPassword(userPassword);
    }

    @Nullable
    public String readPassword() {
        PasswordCipher passwordReader = PasswordSaver.instantiate(activityContext);
        return passwordReader.getPassword();
    }

    public void writeDownloadedClass(@NonNull InputStream from) {
        String filename = filesCache.getAbsolutePath() + "/class.bck";
        try {
            OutputStream to = new FileOutputStream(filename);
            IOUtils.copyStream(from, to);
        } catch (IOException e) {
            Log.e("Copy IO", "There was an error while trying to copy the InputStream" +
                    " to an OutputStream. Full trace: ", e);
        }
    }

    public InputStream readDownloadedClass() {
        String filename = filesCache.getAbsolutePath() + "/class.bck";
        try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            Log.e("Read class", "File not found when trying to recover. Full trace: ", e);
            return null;
        }
    }

    public void deleteDownloadedClass() {
        String filename = filesCache.getAbsolutePath() + "/class.bck";

        File fileToDelete = new File(filename);
        if (!fileToDelete.delete())
            Log.e("Delete IO", "There was an error while trying to delete class.bck");
    }

    public File getDatabasePath() {
        return databasePath;
    }
}
