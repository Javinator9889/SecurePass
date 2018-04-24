package javinator9889.securepass.network.drive.base;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.List;

import javinator9889.securepass.errors.GoogleDriveUnableToOpenFileException;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public class GoogleDriveBase extends AppCompatActivity implements IDriveBase {
    private static final String TAG = "drive-quickstart";
    private GoogleSignInClient mGoogleSignInClient;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;

    @Override
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
    }

    @Override
    public Task<DriveId> pickClassFile() {
        List<Filter> filters = new ArrayList<>(2);
        filters.add(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE));
        filters.add(Filters.eq(SearchableField.TITLE, DRIVE.FILE_TITLE));
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                        .setSelectionFilter(Filters.and(filters))
                        .build();
        return pickItem(openOptions);
    }

    @Override
    public Task<DriveId> pickIvFile() {
        List<Filter> filters = new ArrayList<>(2);
        filters.add(Filters.eq(SearchableField.MIME_TYPE, DRIVE.MIME_TYPE));
        filters.add(Filters.eq(SearchableField.TITLE, DRIVE.IV_FILE));
        OpenFileActivityOptions openOptions =
                new OpenFileActivityOptions.Builder()
                        .setSelectionFilter(Filters.and(filters))
                        .build();
        return pickItem(openOptions);
    }

    @Override
    public Task<DriveId> pickItem(@NonNull OpenFileActivityOptions openOptions) {
        mOpenItemTaskSource = new TaskCompletionSource<>();
        getDriveClient()
                .newOpenFileActivityIntentSender(openOptions)
                .continueWith((Continuation<IntentSender, Void>) task -> {
                    startIntentSenderForResult(task.getResult(),
                            REQUEST_CODE_OPEN_ITEM,
                            FILL_IN_INTENT,
                            FLAGS_MASK,
                            FLAGS_VALUES,
                            EXTRA_FLAGS);
                    return null;
                });
        return mOpenItemTaskSource.getTask();
    }

    protected DriveResourceClient getDriveResourceClient() {
        return mDriveResourceClient;
    }

    protected DriveClient getDriveClient() {
        return mDriveClient;
    }

    @Override
    public GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    @Override
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
    }
}
