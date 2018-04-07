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
import java.util.Map;

import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;

import javinator9889.securepass.R;
import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.errors.NoSHA2AlgorithmException;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.network.drive.base.GoogleDriveBase;
import javinator9889.securepass.util.cipher.FileCipher;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class CreateFileInAppFolder extends GoogleDriveBase {
    private static final String TAG = "CreateFileInAppFolder";
    private ClassContainer dataToBackup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent args = getIntent();
        dataToBackup = (ClassContainer) args.getExtras().getSerializable("data");
    }

    @Override
    protected void onDriveClientReady() {
        createFileInAppFolder(dataToBackup);
    }

    private void createFileInAppFolder(@NonNull final ClassContainer dataToBackup) {
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
                            FileCipher cipher = FileCipher.newInstance(password);
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
                    } catch (NoSHA2AlgorithmException e) {
                        e.printStackTrace();
                    }
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle(DRIVE.FILE_TITLE)
                            .setMimeType(DRIVE.MIME_TYPE)
                            .setStarred(DRIVE.STARRED)
                            .build();
                    return getDriveResourceClient().createFile(parent, changeSet, contents);
                })
                .addOnSuccessListener(this,
                        driveFile -> {
                            showMessage(getString(R.string.data_backed_up));
                            finish();
                        })
                .addOnFailureListener(this,
                        e -> {
                            Log.e(TAG, DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                            showMessage(getString(R.string.backup_error));
                            finish();
                        });
    }
}
