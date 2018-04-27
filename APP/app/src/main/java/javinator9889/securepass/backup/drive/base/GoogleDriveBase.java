package javinator9889.securepass.backup.drive.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.SortableField;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.atomic.AtomicInteger;

import javinator9889.securepass.R;
import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.backup.drive.CreateFileInAppFolder;
import javinator9889.securepass.backup.drive.ResultsAdapter;
import javinator9889.securepass.backup.drive.RetrieveContentWithDownloadProgress;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public class GoogleDriveBase implements IDriveBase {
    private static final String TAG = "drive-quickstart";

    private Context driveContext;
    private Activity mainActivity;
    private DriveResourceClient mDriveResourceClient;
    private DataBufferAdapter<Metadata> resultsAdapter;
    private SignIn mSignInClient;

    public GoogleDriveBase(@NonNull Context driveContext, @NonNull Activity mainActivity) {
        this.driveContext = driveContext;
        this.mainActivity = mainActivity;
        mSignInClient = SignIn.getInstance(mainActivity, driveContext);
        resultsAdapter = new ResultsAdapter(driveContext);
    }

    @Override
    public void signIn() {
        mSignInClient.signIn();
    }

    private boolean isAbleToSignIn() {
        if (!mSignInClient.isSignedIn()) {
            GoogleSignInAccount latestSignedInAccount = GoogleSignIn
                    .getLastSignedInAccount(driveContext);
            if (latestSignedInAccount != null) {
                mDriveResourceClient = Drive
                        .getDriveResourceClient(driveContext, latestSignedInAccount);
                return true;
            }
            else {
                Toast.makeText(driveContext, driveContext.getString(R.string.sign_in_first),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        } else
            return true;
    }

    @Override
    public void uploadFile(@NonNull ClassContainer dataToBackup) {
        if (isAbleToSignIn()) {
            CreateFileInAppFolder createFileTask = new CreateFileInAppFolder(
                    driveContext,
                    mainActivity,
                    mDriveResourceClient);
            queryFilesAndDelete(createFileTask, dataToBackup);
        }
    }

    @Override
    public void restoreData() {
        if (isAbleToSignIn()) {
            queryFiles();
        }
    }

    private void deleteFiles(@NonNull CreateFileInAppFolder createFileTask,
                             @NonNull ClassContainer dataToBackup) {
        int filesFound = resultsAdapter.getCount();
        DriveId actualElement;
        AtomicInteger filesDeleted = new AtomicInteger();
        for (int i = 0; i < filesFound; ++i) {
            Metadata actualValue = resultsAdapter.getItem(i);
            actualElement = actualValue.getDriveId();
            mDriveResourceClient.delete(actualElement.asDriveFile())
                    .addOnCompleteListener(mainActivity,
                            task -> {
                                filesDeleted.addAndGet(1);
                                if (filesDeleted.get() == filesFound)
                                    createFileTask.createFileInAppFolder(dataToBackup);
                            });
        }
        if (filesFound == 0)
            createFileTask.createFileInAppFolder(dataToBackup);
    }

    private void continueWithDownload() {
        RetrieveContentWithDownloadProgress retrieveContentClass =
                new RetrieveContentWithDownloadProgress(
                        driveContext,
                        mainActivity,
                        mDriveResourceClient);
        int filesFound = resultsAdapter.getCount();
        Log.d(TAG, "Files found: " + filesFound);
        DriveId ivId = null;
        DriveId classId = null;
        for (int i = 0; i < filesFound; ++i) {
            Metadata actualValue = resultsAdapter.getItem(i);
            Log.d(TAG, actualValue.getTitle());
            Log.d(TAG, actualValue.getCreatedDate().toString());
            switch (actualValue.getTitle()) {
                case DRIVE.FILE_TITLE:
                    classId = actualValue.getDriveId();
                    break;
                case DRIVE.IV_FILE:
                    ivId = actualValue.getDriveId();
                    break;
                default:
                    classId = null;
                    ivId = null;
                    break;
            }
        }
        try {
            retrieveContentClass.retrieveIvVector(ivId.asDriveFile());
            retrieveContentClass.retrieveContents(classId.asDriveFile());
            retrieveContentClass.finish();
        } catch (NullPointerException e) {
            Log.e(TAG, DRIVE.GOOGLE_FILE_NO_SELECTED, e);
        }
    }

    private void queryFilesAndDelete(@NonNull CreateFileInAppFolder createFileTask,
                                     @NonNull ClassContainer dataToBackup) {
        Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE))
                .build();
        Task<DriveFolder> appFolderTask = mDriveResourceClient.getAppFolder();
        appFolderTask.addOnSuccessListener(mainActivity,
                driveFolder -> {
                    Task<MetadataBuffer> queryTask = mDriveResourceClient
                            .queryChildren(driveFolder, query);
                    queryTask
                            .addOnSuccessListener(mainActivity,
                                    metadata -> {
                                        resultsAdapter.append(metadata);
                                        deleteFiles(createFileTask, dataToBackup);
                                    })
                            .addOnFailureListener(mainActivity,
                                    e -> deleteFiles(createFileTask, dataToBackup));
                });
    }

    private void queryFiles() {
        SortOrder sortOrder = new SortOrder.Builder().addSortAscending(SortableField.CREATED_DATE)
                .build();
        Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE))
                .setSortOrder(sortOrder)
                .build();
        Task<DriveFolder> appFolderTask = mDriveResourceClient.getAppFolder();
        appFolderTask.addOnSuccessListener(mainActivity,
                driveFolder -> {
                    Task<MetadataBuffer> queryTask = mDriveResourceClient
                            .queryChildren(driveFolder, query);
                    queryTask
                            .addOnSuccessListener(mainActivity,
                                    metadata -> {
                                        resultsAdapter.append(metadata);
                                        continueWithDownload();
                                    });
                });
    }

    @Override
    public void setDriveResourceClient(DriveResourceClient mDriveResourceClient) {
        this.mDriveResourceClient = mDriveResourceClient;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        mSignInClient.setSignedIn(isLoggedIn);
    }
}
