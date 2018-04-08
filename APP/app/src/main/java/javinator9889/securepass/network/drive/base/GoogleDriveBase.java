package javinator9889.securepass.network.drive.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.HashSet;
import java.util.Set;

import javinator9889.securepass.errors.GoogleDriveNotAvailableException;
import javinator9889.securepass.errors.GoogleDriveUnableToOpenFileException;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public abstract class GoogleDriveBase extends Activity {
    private static final String TAG = "DriveBaseActivity";

    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;

    @Override
    protected void onStart() {
        super.onStart();
        if (isGooglePlayServicesAvailable(getApplicationContext()))
            signIn();
        else
            throw new GoogleDriveNotAvailableException(DRIVE.GOOGLE_PLAY_NOT_AVAILABLE);
    }

    private static boolean isGooglePlayServicesAvailable(Context servicesContext) {
        GoogleApiAvailability googleApiAvailabilityForPlayServices = GoogleApiAvailability
                .getInstance();
        int status = googleApiAvailabilityForPlayServices
                .isGooglePlayServicesAvailable(servicesContext);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DRIVE.REQUEST_CODE_SIGN_IN:
                if (resultCode != RESULT_OK) {
                    Log.e(TAG, DRIVE.GOOGLE_ACCOUNT_NOT_SIGNED_IN);
                    finish();
                    return;
                }
                Task<GoogleSignInAccount> getAccountTask =
                        GoogleSignIn.getSignedInAccountFromIntent(data);
                if (getAccountTask.isSuccessful())
                    initializeDriveClient(getAccountTask.getResult());
                else {
                    Log.e(TAG, DRIVE.GOOGLE_ACCOUNT_NOT_SIGNED_IN);
                    finish();
                }
                break;
            case DRIVE.REQUEST_CODE_OPEN_ITEM:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = data.getParcelableExtra(
                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                    mOpenItemTaskSource.setResult(driveId);
                } else
                    mOpenItemTaskSource.setException(new GoogleDriveUnableToOpenFileException(
                            "Unable to open file"
                    ));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void signIn() {
        Set<Scope> requiredScopes = new HashSet<>(DRIVE.HASHSET_INITIAL_CAPACITY);
        requiredScopes.add(Drive.SCOPE_FILE);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null && signInAccount.getGrantedScopes().containsAll(requiredScopes))
            initializeDriveClient(signInAccount);
        else {
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(Drive.SCOPE_FILE)
                    .requestScopes(Drive.SCOPE_APPFOLDER)
                    .build();
            GoogleSignInClient googleSignInClient =
                    GoogleSignIn.getClient(this, signInOptions);
            startActivityForResult(googleSignInClient.getSignInIntent(),
                    DRIVE.REQUEST_CODE_SIGN_IN);
        }
    }

    private void initializeDriveClient(GoogleSignInAccount signInAccount) {
        mDriveClient = Drive.getDriveClient(getApplicationContext(), signInAccount);
        mDriveResourceClient = Drive.getDriveResourceClient(getApplicationContext(), signInAccount);
        onDriveClientReady();
    }

    protected Task<DriveId> pickTextFile() {
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                .setSelectionFilter(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE))
                .build();
        return pickItem(openOptions);
    }

    protected Task<DriveId> pickFolder() {
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                .setSelectionFilter(Filters.eq(SearchableField.MIME_TYPE, DriveFolder.MIME_TYPE))
                .build();
        return pickItem(openOptions);
    }

    protected Task<DriveId> pickItem(OpenFileActivityOptions openOptions) {
        mOpenItemTaskSource = new TaskCompletionSource<>();
        getDriveClient()
                .newOpenFileActivityIntentSender(openOptions)
                .continueWith((Continuation<IntentSender, Void>) task -> {
                    startIntentSenderForResult(task.getResult(),
                            DRIVE.REQUEST_CODE_OPEN_ITEM,
                            DRIVE.FILL_IN_INTENT,
                            DRIVE.FLAGS_MASK,
                            DRIVE.FLAGS_VALUES,
                            DRIVE.EXTRA_FLAGS);
                    return null;
                });
        return mOpenItemTaskSource.getTask();
    }

    protected void showMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected abstract void onDriveClientReady();

    protected DriveClient getDriveClient() {
        return mDriveClient;
    }

    protected DriveResourceClient getDriveResourceClient() {
        return mDriveResourceClient;
    }
}
