/*
 * Copyright © 2019 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 23/08/2018 - SecurePass.
 */
package javinator9889.securepass.backup.drive;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import androidx.annotation.NonNull;
import javinator9889.securepass.backup.drive.base.GoogleDriveBase;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.cipher.FileCipher;
import javinator9889.securepass.util.cipher.ICipher;
import javinator9889.securepass.util.values.Constants;

/**
 * Class for uploading files to app folder at Google Drive
 *
 * @see GoogleDriveBase
 * @see IDriveUploader
 * Created by Javinator9889 on 24/08/2018.
 */
public class DriveUploader extends GoogleDriveBase implements IDriveUploader {
    private static final String TAG = "DriveUploader";
    private ICipher mFileCipher;
    private IOManager mIOManager;

    /**
     * Public constructor for starting the upload activity
     *
     * @param driveContext <code>Context</code> when instantiating this class
     * @param mainActivity <code>Activity</code> when instantiating this class
     * @see Activity
     * @see Context
     * @see GoogleDriveBase
     * @see FileCipher
     */
    public DriveUploader(@NonNull Context driveContext, @NonNull Activity mainActivity) {
        super(driveContext, mainActivity);
        super.signIn();
        this.mFileCipher = new FileCipher(driveContext);
        this.mIOManager = IOManager.newInstance(driveContext);
    }

    /**
     * Creates a file inside the app folder at Google Drive
     * <p>
     * First, it <b>ciphers</b> the object (database) to upload. <br />
     * Then, it <i>tries</i> to upload that such file (stored temporally at cache dir) by
     * setting the {@link Constants.SQL} <code>DB_FILENAME</code> and the
     * {@link Constants.DRIVE} <code>MIME_TYPE</code><br />
     *
     * Also it uploads the <b>necessary IV Vector</b> for encrypting/decrypting the file
     * </p>
     *
     * @see DriveResourceClient
     * @see FileCipher#encryptFile(File, File)
     * @see Tasks
     * @see Files#copy(File, OutputStream)
     * @see MetadataChangeSet
     */
    private void createFileInAppFolder() {
        final DriveResourceClient resourceClient = super.getDriveResourceClient();
        final Context driveContext = super.getDriveContext();
        final Activity driveActivity = super.getMainActivity();
        final Task<DriveFolder> appFolderTask = resourceClient.getAppFolder();
        final Task<DriveContents> driveContentTask = resourceClient.createContents();
        final GeneralObjectContainer<File> fileContainer = new ObjectContainer<>(1);
        try {
            fileContainer.storeObject(new File(driveContext.getCacheDir().getAbsolutePath() +
                    "/SecurePass.crypt.db"));
            mFileCipher.encryptFile(mIOManager.getDatabasePath(),
                    fileContainer.getLatestStoredObject());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException |
                IllegalBlockSizeException | IOException e) {
            Log.e(TAG, "Error while encrypting file - working at insecure mode | More info: "
                    + e.getMessage());
            fileContainer.storeObject(mIOManager.getDatabasePath());
        } finally {
            Tasks.whenAll(appFolderTask, driveContentTask)
                    .continueWith(task -> {
                        DriveFolder appFolder = appFolderTask.getResult();
                        DriveContents folderContents = driveContentTask.getResult();
                        try (OutputStream streamToGoogleDrive = folderContents.getOutputStream()) {
                            Files.copy(fileContainer.getLatestStoredObject(), streamToGoogleDrive);
                        }
                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setTitle(Constants.SQL.DB_FILENAME)
                                .setMimeType(Constants.DRIVE.MIME_TYPE)
                                .setPinned(true)
                                .build();
                        return resourceClient.createFile(appFolder, changeSet, folderContents);
                    })
                    .addOnSuccessListener(driveActivity, driveFileTask -> {
                        int uploadIVVectorStatus = uploadIVVector();
                        if (uploadIVVectorStatus == 0)
                            showMessage("DB file uploaded");
                        else
                            showMessage("Error uploading file");
                    })
                    .addOnFailureListener(driveActivity, e -> {
                        Log.e(TAG, Constants.DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                        showMessage("Error while uploading");
                    });
            finalizeUpload(fileContainer.getLatestStoredObject());
        }
    }

    /**
     * After uploading the <b>database</b>, it must destroy the cached version
     *
     * @param sourceFile path for the file to be deleted if possible
     * @see File#equals(Object)
     * @see File#delete()
     */
    private void finalizeUpload(@NonNull File sourceFile) {
        if (!sourceFile.equals(mIOManager.getDatabasePath())) {
            sourceFile.delete();
        }
    }

    /**
     * Uploads the <b>IV Vector</b> to the app folder at Google Drive. It must be uploaded once
     *
     * @return int value which represents the status of the stored object: '0' if everything has
     * gone well, else '-1'
     * @see Tasks
     * @see ObjectContainer#getLatestStoredObject()
     */
    private int uploadIVVector() {
        final DriveResourceClient resourceClient = super.getDriveResourceClient();
        final Activity driveActivity = super.getMainActivity();
        final Task<DriveFolder> appFolderTask = resourceClient.getAppFolder();
        final Task<DriveContents> driveContentTask = resourceClient.createContents();
        final GeneralObjectContainer<Integer> status = new ObjectContainer<>(1);
        Tasks.whenAll(appFolderTask, driveContentTask)
                .continueWith(task -> {
                    DriveFolder appFolder = appFolderTask.getResult();
                    DriveContents folderContents = driveContentTask.getResult();
                    try (OutputStream streamToGoogleDrive = folderContents.getOutputStream()) {
                        Files.copy(mIOManager.getIVVector(), streamToGoogleDrive);
                    }
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle(Constants.DRIVE.IV_FILE)
                            .setMimeType(Constants.DRIVE.IV_MIME_TYPE)
                            .build();
                    return resourceClient.createFile(appFolder, changeSet, folderContents);
                })
                .addOnSuccessListener(driveActivity, driveFileTask -> status.storeObject(0))
                .addOnFailureListener(driveActivity, e -> {
                    Log.e(TAG, Constants.DRIVE.GOOGLE_DRIVE_FILE_NOT_CREATED, e);
                    status.storeObject(-1);
                });
        return status.getLatestStoredObject();
    }

    /**
     * Deletes old files if found at the Google Drive app folder, in order to only store and save
     * the latest one
     *
     * @param resultsAdapter container with all the found objects that can be deleted
     */
    private void deleteOldFiles(DataBufferAdapter<Metadata> resultsAdapter) {
        int resultsFound = resultsAdapter.getCount();
        for (int i = 0; i < resultsFound; ++i) {
            DriveResource fileToDelete = resultsAdapter.getItem(i).getDriveId().asDriveResource();
            getDriveResourceClient().delete(fileToDelete);
        }
    }

    /**
     * General public method which invokes in order the following ones:
     * <ul>
     * <li>
     * {@link #isAbleToSignIn()}
     * </li>
     * <li>
     * {@link #queryFiles(DataBufferAdapter)}
     * </li>
     * <li>
     * {@link #createFileInAppFolder()}
     * </li>
     * <li>
     * {@link #deleteOldFiles(DataBufferAdapter)}
     * </li>
     * </ul>
     *
     * @see DataBufferAdapter
     * @see ResultsAdapter
     * @see GoogleDriveBase
     */
    @Override
    public void uploadDatabase() {
        DataBufferAdapter<Metadata> resultsAdapter = new ResultsAdapter(getDriveContext());
        if (super.isAbleToSignIn()) {
            queryFiles(resultsAdapter);
            createFileInAppFolder();
            deleteOldFiles(resultsAdapter);
        }
    }
}
