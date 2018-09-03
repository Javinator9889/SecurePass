package javinator9889.securepass.backup.drive;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.io.Files;

import java.io.File;
import java.io.OutputStream;

import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.util.values.Constants;

/**
 * Created by Javinator9889 on 24/08/2018.
 */
public class DriveUploader implements IDriveUploader {
    private static final String TAG = "DriveUploader";
    private Context mDriveContext;
    private Activity mDriveActivity;
    private DriveResourceClient mResourceClient;
    private IOManager ioManager;

    public DriveUploader(@NonNull Context driveContext, @NonNull Activity mainActivity,
                         @NonNull DriveResourceClient resourceClient) {
        this.mDriveContext = driveContext;
        this.mDriveActivity = mainActivity;
        this.mResourceClient = resourceClient;
        this.ioManager = IOManager.newInstance(driveContext);
    }

    @Override
    public void createFileInAppFolder() {
        final Task<DriveFolder> appFolderTask = mResourceClient.getAppFolder();
        final Task<DriveContents> driveContentTask = mResourceClient.createContents();
        Tasks.whenAll(appFolderTask, driveContentTask)
                .continueWith(task -> {
                    DriveFolder appFolder = appFolderTask.getResult();
                    DriveContents folderContents = driveContentTask.getResult();
                    try (OutputStream streamToGoogleDrive = folderContents.getOutputStream()) {
                        File databaseFile = ioManager.getDatabasePath();
                        Files.copy(databaseFile, streamToGoogleDrive);
                    }
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle(Constants.SQL.DB_FILENAME)
                            .setMimeType(Constants.DRIVE.MIME_TYPE)
                            .setPinned(true)
                            .build();
                    return mResourceClient.createFile(appFolder, changeSet, folderContents);
                })
                .addOnSuccessListener(mDriveActivity, driveFileTask ->
                        Toast.makeText(mDriveContext, "DB file uploaded", Toast.LENGTH_LONG)
                                .show())
                .addOnFailureListener(mDriveActivity, e -> {
                    Log.e(TAG, Constants.DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                    Toast.makeText(mDriveContext, "Error while uploading", Toast.LENGTH_LONG)
                            .show();
                });
    }
}
