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

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;

import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.util.cipher.FileCipher;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class CreateFileInAppFolder implements IDriveUploadOperations {
    private static final String TAG = "CreateFileInAppFolder";

    private Context driveContext;
    private Activity mainActivity;
    private DriveResourceClient resourceClient;
    private byte[] generatedIv;

    public CreateFileInAppFolder(@NonNull Context driveContext,
                                 @NonNull Activity mainActivity,
                                 @NonNull DriveResourceClient resourceClient) {
        this.driveContext = driveContext;
        this.mainActivity = mainActivity;
        this.resourceClient = resourceClient;
    }

    @Override
    public void createFileInAppFolder(@NonNull final ClassContainer dataToBackup) {
        final Task<DriveFolder> appFolderTask = resourceClient.getAppFolder();
        final Task<DriveContents> createContentsTask = resourceClient.createContents();
        final IOManager ioManager = IOManager.newInstance(driveContext);
        Tasks.whenAll(appFolderTask, createContentsTask)
                .continueWith(task -> {
                    DriveFolder parent = appFolderTask.getResult();
                    DriveContents contents = createContentsTask.getResult();
                    //Map<SealedObject, CipherOutputStream> encryptedBackup;
                    String password = ioManager.readPassword();
                    Log.d(TAG, "Password: " + password);
                    try (OutputStream outputStream = contents.getOutputStream()) {
                        if (password != null) {
                            FileCipher cipher = FileCipher.newInstance(password, null);
                            generatedIv = cipher.getIv();
                            Log.d(TAG, Arrays.toString(generatedIv));
                            /*encryptedBackup = cipher.encrypt(dataToBackup, outputStream);
                            CipherOutputStream createdCipher =
                                    encryptedBackup.values().iterator().next();
                            SealedObject createdSealedObject =
                                    encryptedBackup.keySet().iterator().next();
                            ObjectOutputStream encryptedClassWriter =
                                    new ObjectOutputStream(createdCipher);
                            encryptedClassWriter.writeObject(createdSealedObject);
                            encryptedClassWriter.close();*/
                            cipher.newEncrypt(dataToBackup, outputStream);
                        }
                    }
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle(DRIVE.FILE_TITLE)
                            .setMimeType(DRIVE.MIME_TYPE)
                            .setStarred(DRIVE.STARRED)
                            .build();
                    return resourceClient.createFile(parent, changeSet, contents);
                })
                .addOnSuccessListener(mainActivity,
                        driveFile -> {
                            createIvSaveFile();
                            Toast.makeText(driveContext, "Class uploaded", Toast.LENGTH_LONG).show();
                        })
                .addOnFailureListener(mainActivity,
                        e -> {
                            Log.e(TAG, DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                            mainActivity.finish();
                        });
    }

    @Override
    public void createIvSaveFile() {
        final Task<DriveFolder> appFolderTask = resourceClient.getAppFolder();
        final Task<DriveContents> createContentsTask = resourceClient.createContents();
        Tasks.whenAll(appFolderTask, createContentsTask)
                .continueWith(task -> {
                    DriveFolder parent = appFolderTask.getResult();
                    DriveContents contents = createContentsTask.getResult();
                    try (OutputStream outputStream = contents.getOutputStream()) {
                        outputStream.write(generatedIv);
                    }
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle(DRIVE.IV_FILE)
                            .setMimeType(DRIVE.MIME_TYPE)
                            .setStarred(DRIVE.STARRED)
                            .build();
                    return resourceClient.createFile(parent, changeSet, contents);
                })
                .addOnFailureListener(mainActivity,
                        e -> {
                            Log.e(TAG, DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                            //mainActivity.finish();
                        })
                .addOnSuccessListener(mainActivity,
                        driveFileTask -> Toast.makeText(driveContext, "Done", Toast.LENGTH_LONG).show()
        );//mainActivity.finish());
    }
}
