package javinator9889.securepass.backup.drive.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.tasks.TaskCompletionSource;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public abstract class GoogleDriveBase_old extends Activity {
    private static final String TAG = "DriveBaseActivity";
    private boolean isSignedIn = false;

    private GoogleSignInClient mGoogleSignInClient;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;

    @Override
    protected void onStart() {
        super.onStart();
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //if (isGooglePlayServicesAvailable(getApplicationContext()))
        //signIn();
        /*else
            throw new GoogleDriveNotAvailableException(DRIVE.GOOGLE_PLAY_NOT_AVAILABLE);*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);*/
    }

    private static boolean isGooglePlayServicesAvailable(Context servicesContext) {
        GoogleApiAvailability googleApiAvailabilityForPlayServices = GoogleApiAvailability
                .getInstance();
        int status = googleApiAvailabilityForPlayServices
                .isGooglePlayServicesAvailable(servicesContext);
        return status == ConnectionResult.SUCCESS;
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode != RESULT_OK) {
                    Log.e(TAG, DRIVE.GOOGLE_ACCOUNT_NOT_SIGNED_IN + " | Result code: "
                            + resultCode);
                    finish();
                    return;
                }
                Task<GoogleSignInAccount> getAccountTask =
                        GoogleSignIn.getSignedInAccountFromIntent(data);
                if (getAccountTask.isSuccessful()) {
                    initializeDriveClient(getAccountTask.getResult());
                    isSignedIn = true;
                }
                else {
                    Log.e(TAG, DRIVE.GOOGLE_ACCOUNT_NOT_SIGNED_IN + " | Task unsuccessful");
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
    }*/

    /*protected void newSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, DRIVE.REQUEST_CODE_SIGN_IN);
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
                        .requestEmail()
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

    protected Task<DriveId> pickClassFile() {
        List<Filter> filters = new ArrayList<>(2);
        filters.add(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE));
        filters.add(Filters.eq(SearchableField.TITLE, DRIVE.FILE_TITLE));
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                .setSelectionFilter(Filters.and(filters))
                .build();
        return pickItem(openOptions);
    }

    protected Task<DriveId> pickIvFile() {
        List<Filter> filters = new ArrayList<>(2);
        filters.add(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE));
        filters.add(Filters.eq(SearchableField.TITLE, DRIVE.IV_FILE));
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                .setSelectionFilter(Filters.and(filters))
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
    }*/

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

    protected boolean isSignedIn() {
        return isSignedIn;
    }
}
