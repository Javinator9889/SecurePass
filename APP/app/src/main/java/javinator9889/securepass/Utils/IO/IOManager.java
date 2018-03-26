package javinator9889.securepass.Utils.IO;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javinator9889.securepass.R;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Manage inputs-outputs from application
 */

public class IOManager {
    private Context activityContext;
    private File filesDir;
    private File cacheDir;
    private InputStream sqlScriptInputFile;

    private IOManager(Context activityContext) {
        this.activityContext = activityContext;
        this.filesDir = activityContext.getFilesDir();
        this.cacheDir = activityContext.getCacheDir();
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
}
