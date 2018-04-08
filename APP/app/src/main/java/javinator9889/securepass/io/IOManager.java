package javinator9889.securepass.io;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import javinator9889.securepass.R;
import javinator9889.securepass.util.cipher.DataCipher;
import javinator9889.securepass.util.values.Constants;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Manage inputs-outputs from application
 */

public class IOManager {
    private Context activityContext;
    private File filesDir;
    private InputStream sqlScriptInputFile;

    private IOManager(@NonNull Context activityContext) {
        this.activityContext = activityContext;
        this.filesDir = activityContext.getFilesDir();
        int sqlScript = R.raw.database_script;
        this.sqlScriptInputFile = activityContext.getResources().openRawResource(sqlScript);
    }

    @NonNull
    public static IOManager newInstance(Context activityContext) {
        return new IOManager(activityContext);
    }

    public String loadSQLScript() throws IOException {
        BufferedReader sqlStringsInFile = new BufferedReader(
                new InputStreamReader(sqlScriptInputFile));
        StringBuilder completeFileRead = new StringBuilder();
        String currentLine;
        while ((currentLine = sqlStringsInFile.readLine()) != null)
            completeFileRead.append(currentLine).append("\n");
        return completeFileRead.toString();
    }

    public void storePassword(@NonNull String userPassword) {
        try {
            DataCipher cipher = DataCipher.newInstance(activityContext);
            cipher.createAndroidAsymmetricKey(Constants.CIPHER.MASTER_KEY);
            KeyPair masterKey = cipher.getAndroidKeyStoreAsymmetricKeyPair(
                    Constants.CIPHER.MASTER_KEY);
            String encryptedPass = cipher.encrypt(userPassword, masterKey.getPublic());
            storePasswordInFile(encryptedPass);
        } catch (NoSuchProviderException | NoSuchAlgorithmException |
                InvalidAlgorithmParameterException | KeyStoreException | UnrecoverableKeyException
                | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            Log.e("PASSWORD-S", "Error while recovering-getting passwords. Message: "
                    + e.getMessage() + "\nFull trace: ");
            e.printStackTrace();
        }
    }

    private void storePasswordInFile(@NonNull String base64StringPassword) {
        try {
            File fileCreator = new File(this.filesDir, Constants.IO.PASS_FILENAME);
            fileCreator.createNewFile();
            FileOutputStream outputFile = activityContext.openFileOutput(Constants.IO.PASS_FILENAME,
                    Context.MODE_PRIVATE);
            outputFile.write(base64StringPassword.getBytes());
            outputFile.close();
        } catch (IOException e) {
            Log.e("PASSWORD", "Unable to save password in file. Message: " + e.getMessage()
                    + "\nFull trace: ");
            e.printStackTrace();
        }
    }

    @Nullable
    public String readPassword() {
        try {
            DataCipher cipher = DataCipher.newInstance(activityContext);
            cipher.createAndroidAsymmetricKey(Constants.CIPHER.MASTER_KEY);
            KeyPair masterKey = cipher.getAndroidKeyStoreAsymmetricKeyPair(
                    Constants.CIPHER.MASTER_KEY);
            return cipher.decrypt(readContentsFromPasswordFile(),
                    masterKey.getPrivate());
        } catch (NoSuchProviderException | NoSuchAlgorithmException |
                InvalidAlgorithmParameterException | KeyStoreException | UnrecoverableKeyException
                | BadPaddingException | IllegalBlockSizeException | InvalidKeyException
                | NullPointerException e)
        {
            Log.e("PASSWORD-S", "Error while recovering-getting passwords. Message: "
                    + e.getMessage() + "\nFull trace: ");
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private String readContentsFromPasswordFile() {
        try {
            InputStream inputPasswordFile = activityContext
                    .openFileInput(Constants.IO.PASS_FILENAME);
            BufferedReader passwordReader = new BufferedReader(
                    new InputStreamReader(inputPasswordFile));
            StringBuilder completePassword = new StringBuilder();
            String currentLine;
            while ((currentLine = passwordReader.readLine()) != null)
                completePassword.append(currentLine).append("\n");
            return completePassword.toString();
        } catch (IOException e) {
            Log.e("PASSWORD-READ", "Error while trying to read password from file. " +
                    "Message: " + e.getMessage() + "\nFull trace: ");
            e.printStackTrace();
            return null;
        }
    }
}
