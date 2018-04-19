package javinator9889.securepass.io;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javinator9889.securepass.R;
import javinator9889.securepass.util.cipher.PasswordCipher;
import javinator9889.securepass.util.cipher.PasswordSaver;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Manage inputs-outputs from application
 */

public class IOManager {
    private Context activityContext;
    private InputStream sqlScriptInputFile;

    private IOManager(@NonNull Context activityContext) {
        this.activityContext = activityContext;
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
        PasswordCipher passwordSaver = PasswordSaver.instantiate(activityContext);
        passwordSaver.putPassword(userPassword);
    }

    @Nullable
    public String readPassword() {
        PasswordCipher passwordReader = PasswordSaver.instantiate(activityContext);
        return passwordReader.getPassword();
    }
}
