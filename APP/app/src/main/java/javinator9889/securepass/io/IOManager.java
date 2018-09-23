package javinator9889.securepass.io;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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
    private File dataDir;

    private IOManager(@NonNull Context activityContext) {
        this.activityContext = activityContext;
        this.filesCache = activityContext.getCacheDir();
        this.databasePath = activityContext.getDatabasePath(Constants.SQL.DB_FILENAME);
        this.dataDir = activityContext.getFilesDir();
    }

    @NonNull
    public static IOManager newInstance(Context activityContext) {
        return new IOManager(activityContext);
    }

    public boolean isAnyIVVectorStored() {
        File cacheFile = new File(filesCache.getAbsolutePath() + "/iv_vector.dat");
        File dataFile = new File(dataDir.getAbsolutePath() + "/iv_vector.dat");
        return cacheFile.exists() || dataFile.exists();
    }

    public File getIVVector() {
        File cacheFile = new File(filesCache.getAbsolutePath() + "/iv_vector.dat");
        File dataFile = new File(dataDir.getAbsolutePath() + "/iv_vector.dat");
        return dataFile.exists() ?
                dataDir.listFiles((dir, name) -> name.toLowerCase().equals("iv_vector.dat"))[0] :
                cacheFile.listFiles(((dir, name) -> name.toLowerCase().equals("iv_vector.dat")))[0];
    }

    public void saveIVVector(byte[] ivVector) throws IOException {
        String filename = dataDir.getAbsolutePath() + "/iv_vector.dat";
        File outputFile = new File(filename);
        Files.write(ivVector, outputFile);
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

    public String loadPrivacyHTML() throws IOException {
        InputStream privacyPolicyInputStream = this.activityContext.getResources()
                .openRawResource(R.raw.privacy_policy);
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(privacyPolicyInputStream));
        String currentLine;
        while ((currentLine = reader.readLine()) != null)
            builder.append(currentLine).append("\n");
        return builder.toString();
    }

    public String loadTermsAndConditionsHTML() throws IOException {
        InputStream termsConditionsStream = this.activityContext.getResources()
                .openRawResource(R.raw.terms_and_conditions);
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(termsConditionsStream));
        String currentLine;
        while ((currentLine = reader.readLine()) != null)
            builder.append(currentLine).append("\n");
        return builder.toString();
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

    public void writeDownloadedDatabaseBackup(@NonNull InputStream from) {
        String filename = filesCache.getAbsolutePath() + "/SecurePass.db";
        try {
            OutputStream to = new FileOutputStream(filename);
            IOUtils.copyStream(from, to);
        } catch (IOException e) {
            Log.e("Copy IO", "There was an error while trying to copy the InputStream" +
                    " to an OutputStream. Full trace: ", e);
        }
    }

    public InputStream readDownloadedDatabaseBackup() {
        String filename = filesCache.getAbsolutePath() + "/SecurePass.db";
        try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            Log.e("Read class", "File not found when trying to recover. Full trace: ", e);
            return null;
        }
    }

    public String downloadedDatabaseBackupPath() {
        return filesCache.getAbsolutePath() + "/SecurePass.db";
    }

    public void deleteDownloadedDatabaseBackup() {
        String filename = filesCache.getAbsolutePath() + "/class.bck";

        File fileToDelete = new File(filename);
        if (!fileToDelete.delete())
            Log.e("Delete IO", "There was an error while trying to delete class.bck");
    }

    public File getDatabasePath() {
        return databasePath;
    }
}
