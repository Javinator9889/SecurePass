package javinator9889.securepass.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.DriveEventService;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;

import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.errors.GoogleAccountNotSignedInException;
import javinator9889.securepass.errors.GoogleDriveNotAvailableException;
import javinator9889.securepass.errors.NoSHA2AlgorithmException;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.util.cipher.FileCipher;
import javinator9889.securepass.util.values.Constants.TIME;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 06/04/2018.
 */
public class GoogleDriveApi implements GoogleApiClient.OnConnectionFailedListener {
    private Context fragmentContext;
    private Activity sourceActivity;
    private GoogleSignInClient signInClient;
    private GoogleSignInAccount alreadySignedInAccount;
    private DriveResourceClient driveResourceClient;
    private IOManager ioManager;

    @SuppressWarnings("deprecation")
    private GoogleDriveApi(@NonNull Context fragmentContext,
                           @NonNull Activity sourceActivity) {
        this.fragmentContext = fragmentContext;
        this.sourceActivity = sourceActivity;
        alreadySignedInAccount = GoogleSignIn.getLastSignedInAccount(fragmentContext);
        this.ioManager = IOManager.newInstance(fragmentContext);
    }

    public static GoogleDriveApi newInstance(@NonNull Context fragmentContext,
                                             @NonNull Activity sourceActivity)
            throws GoogleDriveNotAvailableException {
        if (isGooglePlayServicesAvailable(fragmentContext))
            return new GoogleDriveApi(fragmentContext, sourceActivity);
        else
            throw new GoogleDriveNotAvailableException(DRIVE.GOOGLE_PLAY_NOT_AVAILABLE);
    }

    public boolean isAnyAccountAlreadySignedIn() {
        return alreadySignedInAccount != null;
    }

    public void setAlreadySignedInAccount(@NonNull GoogleSignInAccount alreadySignedInAccount) {
        this.alreadySignedInAccount = alreadySignedInAccount;
    }

    public Map<Intent, Integer> requestSignIn() {
        signInClient = createGoogleSignInClient();
        Map<Intent, Integer> signInIntentWithCode = new HashMap<>();
        signInIntentWithCode.put(signInClient.getSignInIntent(), DRIVE.REQUEST_CODE_SIGN_IN);
        return signInIntentWithCode;
    }

    private GoogleSignInClient createGoogleSignInClient() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Drive.SCOPE_APPFOLDER)
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(fragmentContext, signInOptions);
    }

    public void createNewBackup(@NonNull final ClassContainer dataToBackup) {
        if (!isAnyAccountAlreadySignedIn())
            throw new GoogleAccountNotSignedInException(DRIVE.GOOGLE_ACCOUNT_NOT_SIGNED_IN);
        final Task<DriveFolder> appFolderTask = Drive
                .getDriveResourceClient(fragmentContext, alreadySignedInAccount).getAppFolder();
        final Task<DriveContents> createContentsTask = Drive
                .getDriveResourceClient(fragmentContext, alreadySignedInAccount).createContents();

        Tasks.whenAll(appFolderTask, createContentsTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
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
                                .setMimeType("text/plain")
                                .setStarred(true)
                                .build();
                        return Drive
                                .getDriveResourceClient(fragmentContext, alreadySignedInAccount)
                                .createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener(sourceActivity,
                        new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {
                                //to-do
                            }
                        })
                .addOnFailureListener(sourceActivity,
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //to-do
                            }
                        });
    }

    public ClassContainer getLatestBackup(@NonNull String password,
                                          @Nullable MaterialDialog progressDialog) {
        final DriveResourceClient resources = Drive.getDriveResourceClient(fragmentContext,
                alreadySignedInAccount);
        final Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, "text/plain"))
                .build();
        final Task<DriveFolder> appFolderTask = resources.getAppFolder();
        Tasks.whenAll(appFolderTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                        final DriveFolder parent = appFolderTask.getResult();
                        Task<MetadataBuffer> queryTask = resources.queryChildren(parent, query);
                        queryTask.addOnSuccessListener(sourceActivity,
                                new OnSuccessListener<MetadataBuffer>() {
                                    @Override
                                    public void onSuccess(MetadataBuffer metadata) {

                                    }
                                });
                    }
                });
    }

    private static boolean isGooglePlayServicesAvailable(Context servicesContext) {
        GoogleApiAvailability googleApiAvailabilityForPlayServices = GoogleApiAvailability
                .getInstance();
        int status = googleApiAvailabilityForPlayServices
                .isGooglePlayServicesAvailable(servicesContext);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("DRIVECONN", "Connection failed. Log: "
                + connectionResult.getErrorMessage());
    }
}
