package javinator9889.securepass.network.drive.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javinator9889.securepass.R;
import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.errors.GoogleDriveUnableToOpenFileException;
import javinator9889.securepass.network.drive.CreateFileInAppFolder;
import javinator9889.securepass.network.drive.ResultsAdapter;
import javinator9889.securepass.network.drive.RetrieveContentWithDownloadProgress;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public class GoogleDriveBase implements IDriveBase {
    private static final String TAG = "drive-quickstart";

    private Context driveContext;
    private Activity mainActivity;
    private GoogleSignInClient mGoogleSignInClient;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;
    private DataBufferAdapter<Metadata> resultsAdapter;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleSignInClient = buildGoogleSignInClient();
        GoogleSignInAccount latestSignedInAccount = GoogleSignIn
                .getLastSignedInAccount(this);
        if (latestSignedInAccount != null) {
            mDriveClient = Drive.getDriveClient(this, latestSignedInAccount);
            mDriveResourceClient = Drive.getDriveResourceClient(this,
                    latestSignedInAccount);
        } else
            startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }*/
    public GoogleDriveBase(@NonNull Context driveContext, @NonNull Activity mainActivity) {
        this.driveContext = driveContext;
        this.mainActivity = mainActivity;
        mGoogleSignInClient = buildGoogleSignInClient();
        resultsAdapter = new ResultsAdapter(driveContext);
    }

    @Override
    public void signIn() {
        mainActivity.startActivityForResult(mGoogleSignInClient.getSignInIntent(),
                REQUEST_CODE_SIGN_IN);
    }

    @Override
    public void uploadFile(@NonNull ClassContainer dataToBackup) {
        if (mDriveResourceClient == null)
            signIn();
        CreateFileInAppFolder createFileTask = new CreateFileInAppFolder(
                driveContext,
                mainActivity,
                mDriveResourceClient);
        createFileTask.createFileInAppFolder(dataToBackup);
    }

    @Override
    public synchronized void restoreData() {
        if (mDriveResourceClient == null)
            signIn();
        MaterialDialog md = new MaterialDialog.Builder(driveContext)
                .progress(true, 0)
                .title(R.string.wait)
                .build();
        md.show();
        /*pickIvFile()
                .addOnSuccessListener(mainActivity,
                        driveId -> retrieveContentClass.retrieveIvVector(driveId.asDriveFile()))
                .addOnFailureListener(mainActivity,
                        e -> {
                            Log.e(TAG, "Fail when recovering IV vector. Message: "
                                    + e.getMessage());
                            mainActivity.finish();
                        });
        try {
//            wait(TimeUnit.SECONDS.toMillis(10));
            pickClassFile()
                    .addOnSuccessListener(mainActivity,
                            driveId -> retrieveContentClass.retrieveContents(driveId.asDriveFile()))
                    .addOnFailureListener(mainActivity,
                            e -> {
                                Log.e(TAG, DRIVE.GOOGLE_FILE_NO_SELECTED, e);
                                mainActivity.finish();
                            });
        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted while waiting. Message: " + e.getMessage());
            mainActivity.finish();
        } finally {
            retrieveContentClass.finish();
        }*/
        /*Tasks.whenAll(pickIvFile(), pickClassFile(), mDriveResourceClient.getAppFolder())
                .continueWith(task -> {
                    DriveId ivId = pickIvFile().getResult();
                    DriveId classId = pickClassFile().getResult();
                    retrieveContentClass.retrieveIvVector(ivId.asDriveFile());
                    retrieveContentClass.retrieveContents(classId.asDriveFile());
                    return null;
                });
        retrieveContentClass.finish();*/

        queryFiles(md);
        /*int filesFound = resultsAdapter.getCount();
        System.out.println(filesFound);
        DriveId ivId = null;
        DriveId classId = null;
        for (int i = 0; i < filesFound; ++i) {
            Metadata actualValue = resultsAdapter.getItem(i);
            Log.d(TAG, actualValue.getTitle());
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
        } catch (NullPointerException e) {
            Log.e(TAG, DRIVE.GOOGLE_FILE_NO_SELECTED, e);
        }*/
    }

    private void continueWithDownload(MaterialDialog md) {
        RetrieveContentWithDownloadProgress retrieveContentClass =
                new RetrieveContentWithDownloadProgress(
                        driveContext,
                        mainActivity,
                        mDriveResourceClient);
        int filesFound = resultsAdapter.getCount();
        System.out.println(filesFound);
        DriveId ivId = null;
        DriveId classId = null;
        for (int i = 0; i < filesFound; ++i) {
            Metadata actualValue = resultsAdapter.getItem(i);
            Log.d(TAG, actualValue.getTitle());
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
        md.dismiss();
        try {
            retrieveContentClass.retrieveIvVector(ivId.asDriveFile());
            retrieveContentClass.retrieveContents(classId.asDriveFile());
        } catch (NullPointerException e) {
            Log.e(TAG, DRIVE.GOOGLE_FILE_NO_SELECTED, e);
        }
    }

    private Task<DriveId> pickClassFile() {
        List<Filter> filters = new ArrayList<>(2);
        filters.add(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE));
        filters.add(Filters.eq(SearchableField.TITLE, DRIVE.FILE_TITLE));
        //filters.add(Filters.eq(SearchableField.MIME_TYPE, DriveFolder.MIME_TYPE));
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                        .setSelectionFilter(Filters.and(filters))
                        .build();
        return pickItem(openOptions);
    }

    private Task<DriveId> pickIvFile() {
        List<Filter> filters = new ArrayList<>(2);
        filters.add(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE));
        filters.add(Filters.eq(SearchableField.TITLE, DRIVE.IV_FILE));
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                        .setSelectionFilter(Filters.and(filters))
                        .build();
        return pickItem(openOptions);
    }

    private Task<DriveId> pickItem(@NonNull OpenFileActivityOptions openOptions) {
        mOpenItemTaskSource = new TaskCompletionSource<>();
        mDriveClient
                .newOpenFileActivityIntentSender(openOptions)
                .continueWith((Continuation<IntentSender, Void>) task -> {
                    mainActivity.startIntentSenderForResult(task.getResult(),
                            REQUEST_CODE_OPEN_ITEM,
                            FILL_IN_INTENT,
                            FLAGS_MASK,
                            FLAGS_VALUES,
                            EXTRA_FLAGS);
                    return null;
                });
        return mOpenItemTaskSource.getTask();
    }

    /*protected Task<DriveId> pickFolder() {
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                        .setSelectionFilter(
                                Filters.eq(SearchableField.MIME_TYPE, DriveFolder.MIME_TYPE))
                        .setActivityTitle(getString(R.string.select_folder))
                        .build();
        return pickItem(openOptions);
    }*/

    private synchronized void queryFiles(MaterialDialog md) {
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
                                        continueWithDownload(md);
                                    });
                });
        /*Tasks.whenAll(appFolderTask)
                .continueWith(task -> {
                    Task<MetadataBuffer> queryTask = mDriveResourceClient
                            .queryChildren(appFolderTask.getResult(), query);
                    Tasks.whenAll(queryTask)
                            .continueWith(newTask -> {
                                resultsAdapter.append(queryTask.getResult());
                                return null;
                            });
                    return null;
                });*/
    }

    /*protected DriveResourceClient getDriveResourceClient() {
        return mDriveResourceClient;
    }*/

    @Override
    public void setResult(DriveId id) {
        mOpenItemTaskSource.setResult(id);
    }

    @Override
    public void setException(Exception e) {
        mOpenItemTaskSource.setException(e);
    }

    /*private DriveClient getDriveClient() {
        return mDriveClient;
    }*/

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE, Drive.SCOPE_APPFOLDER)
                        .build();
        return GoogleSignIn.getClient(driveContext, signInOptions);
    }

    @Override
    public void setDriveResourceClient(DriveResourceClient mDriveResourceClient) {
        this.mDriveResourceClient = mDriveResourceClient;
    }

    @Override
    public void setDriveClient(DriveClient mDriveClient) {
        this.mDriveClient = mDriveClient;
    }

    /*@Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    GoogleSignInAccount latestSignedInAccount = GoogleSignIn
                            .getLastSignedInAccount(this);
                    if (latestSignedInAccount != null) {
                        mDriveClient = Drive.getDriveClient(this, latestSignedInAccount);
                        mDriveResourceClient =
                                Drive.getDriveResourceClient(this, latestSignedInAccount);
                    } else
                        startActivityForResult(mGoogleSignInClient.getSignInIntent(),
                                REQUEST_CODE_SIGN_IN);
                    Toast.makeText(this, "Successfully signed in", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }
                break;
            case REQUEST_CODE_OPEN_ITEM:
                if (resultCode == RESULT_OK) {
                    DriveId id = data.getParcelableExtra(
                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                    mOpenItemTaskSource.setResult(id);
                } else
                    mOpenItemTaskSource.setException(new GoogleDriveUnableToOpenFileException(
                            "Unable to open file"));
                break;
            default:
                Log.e(TAG, "Result for activity no contemplated. RequestCode: " + requestCode +
                        " | ResultCode: " + resultCode + " | Intent data: " + data.toString());
                finish();
        }
    }*/
}
