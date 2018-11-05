package javinator9889.securepass.io;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.github.javinator9889.exporter.FileToBytesExporter;
import com.google.android.gms.common.util.IOUtils;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.R;
import javinator9889.securepass.util.cipher.PasswordCipher;
import javinator9889.securepass.util.cipher.PasswordSaver;
import javinator9889.securepass.util.values.Constants;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Manage inputs-outputs from application
 */

public class IOManager {
    private Context mActivityContext;
    private File mFilesCache;
    private File mDatabasePath;
    private File mDataDir;

    /**
     * Private constructor used by {@link #newInstance(Context)} method
     *
     * @param activityContext <code>Context</code> when instantiated the class
     * @see Context
     * @see File
     */
    private IOManager(@NonNull Context activityContext) {
        this.mActivityContext = activityContext;
        this.mFilesCache = activityContext.getCacheDir();
        this.mDatabasePath = activityContext.getDatabasePath(Constants.SQL.DB_FILENAME);
        this.mDataDir = activityContext.getFilesDir();
    }

    /**
     * Public static available constructor for getting IOManager instances. Uses internally
     * {@link #IOManager(Context)}
     *
     * @param activityContext <code>Context</code> when instantiated the class
     * @return <code>IOManager</code> generated instance
     * @see Context
     */
    @NonNull
    public static IOManager newInstance(@NonNull Context activityContext) {
        return new IOManager(activityContext);
    }

    /**
     * Checks if there is any IV Vector stored (for encryption/decryption) at cache or data dir
     *
     * @return <code>boolean</code> 'true' if there is any vector, else 'false'
     * @see Context#getCacheDir()
     * @see Context#getDataDir()
     */
    public boolean isAnyIVVectorStored() {
        File cacheFile = new File(mFilesCache.getAbsolutePath() + "/iv_vector.dat");
        File dataFile = new File(mDataDir.getAbsolutePath() + "/iv_vector.dat");
        return cacheFile.exists() || dataFile.exists();
    }

    /**
     * Gets IV Vector file description inside the {@link File} object
     *
     * @return <code>File</code> with IV Vector file data
     * @see Context#getCacheDir()
     * @see Context#getDataDir()
     */
    public File getIVVector() {
        File cacheFile = new File(mFilesCache.getAbsolutePath() + "/iv_vector.dat");
        File dataFile = new File(mDataDir.getAbsolutePath() + "/iv_vector.dat");
        return dataFile.exists() ?
                mDataDir.listFiles((dir, name) -> name.toLowerCase().equals("iv_vector.dat"))[0] :
                cacheFile.listFiles(((dir, name) -> name.toLowerCase().equals("iv_vector.dat")))[0];
    }

    /**
     * Stores the IV Vector at the {@link Context#getDataDir() data dir path}
     *
     * @param ivVector the vector (in bytes) which will be stored
     * @throws IOException if it is not possible to write to the file
     * @see Context#getDataDir()
     * @see Files#write(byte[], File)
     */
    public void saveIVVector(byte[] ivVector) throws IOException {
        String filename = mDataDir.getAbsolutePath() + "/iv_vector.dat";
        File outputFile = new File(filename);
        Files.write(ivVector, outputFile);
    }

    /**
     * Loads from {@link R.raw raw resources} all the SQL scripts
     *
     * @return a <code>List</code> of <code>String</code> with the correspondent scripts
     * @throws IOException when there is no file available
     * @see R.raw
     * @deprecated this method is no longer valid - use {@link #obtainSQLScript()} instead
     */
    @Deprecated
    public List<String> loadSQLScript() throws IOException {
        FileToBytesExporter exporter = new FileToBytesExporter();
        List<String> result = new ArrayList<>(5);
        int[] sqlScripts = new int[]{R.raw.create_category, R.raw.create_entry, R.raw.create_qrcode,
                R.raw.create_security_code, R.raw.create_field};
        for (int sqlScript : sqlScripts) {
            InputStream sqlScriptInputFile = this.mActivityContext.getResources()
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

    /**
     * Loads from {@link R.raw raw resources} the binary SQL script, using
     * {@link FileToBytesExporter} for recovering data
     *
     * @return {@code ArrayList} with the different SQL parts.
     * @throws URISyntaxException when the URI is malformed while recovering file from resources.
     * @throws IOException        if something happens while recovering the data from the source
     *                            file.
     * @see FileToBytesExporter#readObject(File)
     */
    public List<String> obtainSQLScript() throws URISyntaxException, IOException {
        String sqlScriptFilenameUri = mActivityContext
                .getResources()
                .getResourceName(R.raw.database_script);
        Uri androidUri = Uri.parse(sqlScriptFilenameUri);
        URI javaUri = new URI(androidUri.toString());
        File sourceFile = new File(javaUri);
        FileToBytesExporter reader = new FileToBytesExporter();
        reader.readObject(sourceFile);
        String obtainedData = reader.getReadData();
        String[] separateScripts = obtainedData.split("(\\r?\\n){2}");
        return new ArrayList<>(Arrays.asList(separateScripts));
    }

    /**
     * Loads from {@link R.raw raw resources} the <b>privacy</b> text saved as Markdown file
     *
     * @return <code>String</code> with the hole text
     * @throws IOException when there is no file available
     */
    public String loadPrivacyTextMD() throws IOException {
        InputStream privacyPolicyInputStream = this.mActivityContext.getResources()
                .openRawResource(R.raw.privacy);
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(privacyPolicyInputStream));
        String currentLine;
        while ((currentLine = reader.readLine()) != null)
            builder.append(currentLine).append("\n");
        return builder.toString();
    }

    /**
     * Loads from {@link R.raw raw resources} the <b>terms and conditions</b> text saved as
     * Markdown
     * file
     *
     * @return <code>String</code> with the hole text
     * @throws IOException when there is no file available
     */
    public String loadTermsConditionsTextMD() throws IOException {
        InputStream termsConditionsStream = this.mActivityContext.getResources()
                .openRawResource(R.raw.terms_conditions);
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(termsConditionsStream));
        String currentLine;
        while ((currentLine = reader.readLine()) != null)
            builder.append(currentLine).append("\n");
        return builder.toString();
    }

    /**
     * Stores a password in {@link com.chamber.java.library.SharedChamber}
     *
     * @param userPassword password to save
     * @deprecated method as the password is no longer saved in a file
     */
    @Deprecated
    public void storePassword(@NonNull String userPassword) {
        PasswordCipher passwordSaver = PasswordSaver.instantiate(mActivityContext);
        passwordSaver.putPassword(userPassword);
    }

    /**
     * Reads password from {@link com.chamber.java.library.SharedChamber}
     *
     * @return <code>String</code> with the password
     * @deprecated method as the password is no longer saved in a file
     */
    @Deprecated
    @Nullable
    public String readPassword() {
        PasswordCipher passwordReader = PasswordSaver.instantiate(mActivityContext);
        return passwordReader.getPassword();
    }

    /**
     * Writes an InputStream with a database backup to an OutputStream
     *
     * @param from source
     * @deprecated everything is now done at
     * {@link javinator9889.securepass.backup.drive.DriveDownloader DriveDownloader} class
     */
    @Deprecated
    public void writeDownloadedDatabaseBackup(@NonNull InputStream from) {
        String filename = mFilesCache.getAbsolutePath() + "/SecurePass.db";
        try {
            OutputStream to = new FileOutputStream(filename);
            IOUtils.copyStream(from, to);
        } catch (IOException e) {
            Log.e("Copy IO", "There was an error while trying to copy the InputStream" +
                    " to an OutputStream. Full trace: ", e);
        }
    }

    /**
     * Reads a downloaded database backup from FilesCache
     *
     * @return <code>InputStream</code> when file is available
     * @deprecated everything is now done at
     * {@link javinator9889.securepass.backup.drive.DriveDownloader DriveDownloader} class
     */
    @Deprecated
    public InputStream readDownloadedDatabaseBackup() {
        String filename = mFilesCache.getAbsolutePath() + "/SecurePass.db";
        try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            Log.e("Read class", "File not found when trying to recover. Full trace: ", e);
            return null;
        }
    }

    /**
     * Obtains the path where databases backups are saved temporally
     *
     * @return <code>String</code> with the path
     */
    public String downloadedDatabaseBackupPath() {
        return mFilesCache.getAbsolutePath() + "/SecurePass.db";
    }

    /**
     * Removes downloaded database backup
     *
     * @deprecated everything is now done at
     * {@link javinator9889.securepass.backup.drive.DriveDownloader DriveDownloader} class
     */
    @Deprecated
    public void deleteDownloadedDatabaseBackup() {
        String filename = mFilesCache.getAbsolutePath() + "/class.bck";

        File fileToDelete = new File(filename);
        if (!fileToDelete.delete())
            Log.e("Delete IO", "There was an error while trying to delete class.bck");
    }

    /**
     * Obtains the path where database is stored
     *
     * @return <code>File</code> with the path
     */
    public File getDatabasePath() {
        return mDatabasePath;
    }
}
