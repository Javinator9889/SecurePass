package javinator9889.securepass.network.drive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.tasks.Task;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.crypto.NoSuchPaddingException;

import javinator9889.securepass.R;
import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.network.drive.base.GoogleDriveBase;
import javinator9889.securepass.util.cipher.FileCipher;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class RetrieveContentWithDownloadProgress extends GoogleDriveBase
        implements IDriveDownloadOperations {
    private static final String TAG = "RetrieveWithProgress";
    private MaterialDialog mProgressBar;
    private ExecutorService mExecutorService;
    private byte[] iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressBar = new MaterialDialog.Builder(this)
                .title(R.string.retrieving_data)
                .content(R.string.wait)
                .cancelable(false)
                .progress(false, 100)
                .build();
        mExecutorService = Executors.newSingleThreadExecutor();
        launchActivities();
    }

    private void launchActivities() {
        pickIvFile()
                .addOnSuccessListener(this,
                        driveId -> retrieveIvVector(driveId.asDriveFile()))
                .addOnFailureListener(this, e -> {
                    Log.e(TAG, "Fail when recovering IV vector. Message: " + e.getMessage());
                    finish();
                });
        try {
            wait(TimeUnit.SECONDS.toMillis(10));
            pickClassFile()
                    .addOnSuccessListener(this,
                            driveId -> retrieveContents(driveId.asDriveFile()))
                    .addOnFailureListener(this, e -> {
                        Log.e(TAG, DRIVE.GOOGLE_FILE_NO_SELECTED, e);
                        finish();
                    });
        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted while waiting. Message: " + e.getMessage());
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdown();
    }

    @Override
    public void retrieveIvVector(DriveFile file) {
        Task<DriveContents> openFileTask = getDriveResourceClient()
                .openFile(file, DriveFile.MODE_READ_ONLY);
        openFileTask
                .continueWithTask(task -> {
                    DriveContents contents = task.getResult();
                    try (InputStream stream = contents.getInputStream()) {
                        this.iv = ByteStreams.toByteArray(stream);
                        notify();
                        return getDriveResourceClient().discardContents(contents);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, DRIVE.GOOGLE_FILE_NO_SELECTED, e);
                    finish();
                });
    }

    @Override
    public void retrieveContents(DriveFile file) {
        mProgressBar.show();
        OpenFileCallback openCallback = new OpenFileCallback() {
            @Override
            public void onProgress(long bytesDownloaded, long bytesExpected) {
                int progress = (int) (bytesDownloaded * 100 / bytesExpected);
                Log.d(TAG, String.format("Download progress: %d percent", progress));
                mProgressBar.setProgress(progress);
            }

            @Override
            public void onContents(@NonNull DriveContents driveContents) {
                mProgressBar.setProgress(100);
                mProgressBar.dismiss();
                final StringBuilder passwordBuilder = new StringBuilder();
                new MaterialDialog.Builder(
                        RetrieveContentWithDownloadProgress.this)
                        .title(R.string.put_pass)
                        .content(R.string.pass_need)
                        .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        .input(null, null, false,
                                (dialog, input) -> passwordBuilder.append(input))
                        .show();
                String password = passwordBuilder.toString();
                try (InputStream fileInput = driveContents.getInputStream()) {
                    FileCipher decrypt = FileCipher.newInstance(password, iv);
                    ClassContainer restoredData = (ClassContainer) decrypt.decrypt(fileInput);
                    restoredData.storeDataInDB();
                    getDriveResourceClient().discardContents(driveContents);
                } catch (IOException | NoSuchAlgorithmException | InvalidKeyException
                        | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                Log.e(TAG, "Unable to read contents", e);
                finish();
            }
        };
        getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY, openCallback);
    }
}
