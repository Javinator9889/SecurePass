package javinator9889.securepass.network.drive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;

import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.errors.NoArgsSpecifiedException;
import javinator9889.securepass.errors.NoBackupSpecifiedException;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.network.drive.base.GoogleDriveBase;
import javinator9889.securepass.util.cipher.FileCipher;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class CreateFileInAppFolder extends GoogleDriveBase implements IDriveUploadOperations {
    private static final String TAG = "CreateFileInAppFolder";
    private byte[] generatedIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent args = getIntent();
        Bundle obtainedArgs = args.getExtras();
        if (obtainedArgs != null) {
            Serializable classContainer = obtainedArgs.getSerializable("data");
            ClassContainer dataToBackup;
            if (classContainer != null)
                dataToBackup = (ClassContainer) classContainer;
            else
                throw new NoBackupSpecifiedException("Class " + this.getClass().getSimpleName() +
                        " must specify a " + ClassContainer.class.getName() + " class as \"data\"" +
                        " parameter");
            createFileInAppFolder(dataToBackup);
        } else
            throw new NoArgsSpecifiedException("Class " + this.getClass().getSimpleName() +
                    " must specify arguments: \"data\" (Serializable)");

    }

    @Override
    public void createFileInAppFolder(@NonNull final ClassContainer dataToBackup) {
        final Task<DriveFolder> appFolderTask = getDriveResourceClient().getAppFolder();
        final Task<DriveContents> createContentsTask = getDriveResourceClient().createContents();
        final IOManager ioManager = IOManager.newInstance(this);
        Tasks.whenAll(appFolderTask, createContentsTask)
                .continueWith(task -> {
                    DriveFolder parent = appFolderTask.getResult();
                    DriveContents contents = createContentsTask.getResult();
                    Map<SealedObject, CipherOutputStream> encryptedBackup;
                    String password = ioManager.readPassword();
                    try (OutputStream outputStream = contents.getOutputStream()) {
                        if (password != null) {
                            FileCipher cipher = FileCipher.newInstance(password, null);
                            generatedIv = cipher.getIv();
                            encryptedBackup = cipher.encrypt(dataToBackup, outputStream);
                            CipherOutputStream createdCipher =
                                    encryptedBackup.values().iterator().next();
                            SealedObject createdSealedObject =
                                    encryptedBackup.keySet().iterator().next();
                            ObjectOutputStream encryptedClassWriter =
                                    new ObjectOutputStream(createdCipher);
                            encryptedClassWriter.writeObject(createdSealedObject);
                            encryptedClassWriter.close();
                        }
                    }
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle(DRIVE.FILE_TITLE)
                            .setMimeType(DRIVE.MIME_TYPE)
                            .setStarred(DRIVE.STARRED)
                            .build();
                    return getDriveResourceClient().createFile(parent, changeSet, contents);
                })
                .addOnSuccessListener(this,
                        driveFile -> createIvSaveFile())
                .addOnFailureListener(this,
                        e -> {
                            Log.e(TAG, DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                            finish();
                        });
    }

    @Override
    public void createIvSaveFile() {
        final Task<DriveFolder> appFolderTask = getDriveResourceClient().getAppFolder();
        final Task<DriveContents> createContentsTask = getDriveResourceClient().createContents();
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
                    return getDriveResourceClient().createFile(parent, changeSet, contents);
                })
                .addOnFailureListener(this,
                        e -> {
                            Log.e(TAG, DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                            finish();
                        })
                .addOnSuccessListener(this,
                        driveFileTask -> finish());
    }
}
