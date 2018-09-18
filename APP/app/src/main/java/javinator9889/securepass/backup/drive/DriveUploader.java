package javinator9889.securepass.backup.drive;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.SortableField;
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

import javinator9889.securepass.backup.drive.base.GoogleDriveBase;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.cipher.FileCipher;
import javinator9889.securepass.util.cipher.ICipher;
import javinator9889.securepass.util.values.Constants;

/**
 * Created by Javinator9889 on 24/08/2018.
 */
public class DriveUploader extends GoogleDriveBase implements IDriveUploader {
    private static final String TAG = "DriveUploader";
    private ICipher mFileCipher;
    private IOManager ioManager;

    public DriveUploader(@NonNull Context driveContext, @NonNull Activity mainActivity,
                         @NonNull DriveResourceClient resourceClient) {
        super(driveContext, mainActivity);
        super.setDriveResourceClient(resourceClient);
        this.mFileCipher = new FileCipher(driveContext);
        this.ioManager = IOManager.newInstance(driveContext);
    }

    @Override
    public void createFileInAppFolder() {
        final DriveResourceClient resourceClient = super.getDriveResourceClient();
        final Context driveContext = super.getDriveContext();
        final Activity driveActivity = super.getMainActivity();
        final Task<DriveFolder> appFolderTask = resourceClient.getAppFolder();
        final Task<DriveContents> driveContentTask = resourceClient.createContents();
        final GeneralObjectContainer<File> fileContainer = new ObjectContainer<>(1);
        try {
            fileContainer.storeObject(new File(driveContext.getCacheDir().getAbsolutePath() +
                    "/SecurePass.crypt.db"));
            mFileCipher.encryptFile(ioManager.getDatabasePath(),
                    fileContainer.getLatestStoredObject());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException |
                IllegalBlockSizeException | IOException e) {
            Log.e(TAG, "Error while encrypting file - working at insecure mode | More info: "
                    + e.getMessage());
            fileContainer.storeObject(ioManager.getDatabasePath());
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

    private void finalizeUpload(@NonNull File sourceFile) {
        if (!sourceFile.equals(ioManager.getDatabasePath())) {
            sourceFile.delete();
        }
    }

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
                        Files.copy(ioManager.getIVVector(), streamToGoogleDrive);
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

    private void queryFiles(DataBufferAdapter<Metadata> resultsAdapter) {
        SortOrder sortOrder = new SortOrder.Builder().addSortAscending(SortableField.CREATED_DATE)
                .build();
        Query query = new Query.Builder()
                .addFilter(Filters.and(Filters.eq(SearchableField.TITLE, Constants.SQL.DB_FILENAME),
                        Filters.eq(SearchableField.MIME_TYPE, Constants.DRIVE.MIME_TYPE)))
                .setSortOrder(sortOrder)
                .build();
        Task<DriveFolder> appFolderTask = getDriveResourceClient().getAppFolder();
        appFolderTask.addOnSuccessListener(getMainActivity(), driveFolder -> {
            Task<MetadataBuffer> queryTask = getDriveResourceClient()
                    .queryChildren(driveFolder, query);
            queryTask.addOnSuccessListener(getMainActivity(), resultsAdapter::append);
        });
    }

    private void deleteOldFiles(DataBufferAdapter<Metadata> resultsAdapter) {
        int resultsFound = resultsAdapter.getCount();
        for (int i = 0; i < resultsFound; ++i) {
            DriveResource fileToDelete = resultsAdapter.getItem(i).getDriveId().asDriveResource();
            getDriveResourceClient().delete(fileToDelete);
        }
    }

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
