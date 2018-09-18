package javinator9889.securepass.backup.drive;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javinator9889.securepass.R;
import javinator9889.securepass.backup.drive.base.GoogleDriveBase;
import javinator9889.securepass.io.IOManager;

/**
 * Created by Javinator9889 on 03/09/2018.
 */
public class DriveDownloader extends GoogleDriveBase implements IDriveDownloader {
    private static final String TAG = "DriveDownloader";
//    private Context mDriveContext;
//    private DriveResourceClient mResourceClient;
    private MaterialDialog mProgressBar;

    public DriveDownloader(@NonNull Context driveContext, @NonNull Activity mainActivity,
                           @NonNull DriveResourceClient resourceClient) {
        super(driveContext, mainActivity);
//        this.mDriveContext = driveContext;
//        this.mMainActivity = mainActivity;
        super.setDriveResourceClient(resourceClient);
//        this.mResourceClient = resourceClient;
        mProgressBar = new MaterialDialog.Builder(driveContext)
                .title(R.string.retrieving_data)
                .content(R.string.wait)
                .cancelable(false)
                .progress(false, 100)
                .build();
    }



    @Override
    public void retrieveContents(@NonNull DriveFile contents) {
        mProgressBar.show();
        final DriveResourceClient resourceClient = super.getDriveResourceClient();
        final Context driveContext = super.getDriveContext();
        resourceClient.openFile(contents, DriveFile.MODE_READ_ONLY,
                new OpenFileCallback() {
                    @Override
                    public void onProgress(long bytesDownloaded, long bytesExpected) {
                        int progress = (int) ((bytesDownloaded * 100) / bytesExpected);
                        Log.d(TAG, String.format("Download progress: %d percent", progress));
                        mProgressBar.setProgress(progress);
                    }

                    @Override
                    public void onContents(@NonNull DriveContents driveContents) {
                        mProgressBar.setProgress(100);
                        try {
                            try (InputStream obtainedFile = driveContents.getInputStream()) {
                                IOManager io = IOManager.newInstance(driveContext);
                                io.writeDownloadedDatabaseBackup(obtainedFile);
                                mProgressBar.dismiss();
                                completeDownload();
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Exception while copying/retrieving database", e);
                        }
                    }

                    @Override
                    public void onError(@NonNull Exception e) {
                        Log.e(TAG, "Exception while downloading/retrieving contents", e);
                    }
                });
    }

    private void completeDownload() {
        final Context driveContext = super.getDriveContext();
        SQLiteDatabase.loadLibs(driveContext);
        IOManager io = IOManager.newInstance(driveContext);
        String databaseDownloadedBackupPath = io.downloadedDatabaseBackupPath();
        final StringBuilder passwordBuilder = new StringBuilder();
        MaterialDialog passwordInputDialog = new MaterialDialog.Builder(driveContext)
                .title(R.string.put_pass)
                .content(R.string.pass_need)
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(null, null, false,
                        ((dialog, input) -> passwordBuilder.append(input)))
                .onAny(((dialog, which) -> {
                    final String password = Hashing.sha256()
                            .hashString(passwordBuilder.toString(), StandardCharsets.UTF_8)
                            .toString();
                    try {
                        SQLiteDatabase openedDatabase = SQLiteDatabase
                                .openDatabase(databaseDownloadedBackupPath, password, null,
                                        SQLiteDatabase.OPEN_READONLY);
                        openedDatabase.close();
                        File databaseBackup = new File(databaseDownloadedBackupPath);
                        File databasePath = io.getDatabasePath();
                        try {
                            Files.move(databaseBackup, databasePath);
                            showMessage("Backup restore completed");
                        } catch (IOException e) {
                            showMessage("Error while recovering your backup");
                        }
                    } catch (SQLiteException e) {
                        showMessage("Password not correct");
                        completeDownload();
                    }
                }))
                .cancelable(false)
                .build();
        passwordInputDialog.show();
    }
}
