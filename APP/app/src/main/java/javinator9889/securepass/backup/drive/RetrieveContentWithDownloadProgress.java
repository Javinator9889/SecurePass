package javinator9889.securepass.backup.drive;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.tasks.Task;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javinator9889.securepass.R;
import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.util.cipher.FileCipherOld;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class RetrieveContentWithDownloadProgress implements IDriveDownloadOperations {
    private static final String TAG = "RetrieveWithProgress";

    private Context driveContext;
    private Activity mainActivity;
    private DriveResourceClient resourceClient;
    private MaterialDialog mProgressBar;
    private ExecutorService mExecutorService;
    private byte[] iv;

    public RetrieveContentWithDownloadProgress(@NonNull Context driveContext,
                                               @NonNull Activity mainActivity,
                                               @NonNull DriveResourceClient resourceClient) {
        this.driveContext = driveContext;
        this.mainActivity = mainActivity;
        this.resourceClient = resourceClient;

        mProgressBar = new MaterialDialog.Builder(driveContext)
                .title(R.string.retrieving_data)
                .content(R.string.wait)
                .cancelable(false)
                .progress(false, 100)
                .build();
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void retrieveIvVector(DriveFile file) {
        Task<DriveContents> openFileTask = resourceClient
                .openFile(file, DriveFile.MODE_READ_ONLY);
        openFileTask
                .continueWithTask(task -> {
                    DriveContents contents = task.getResult();
                    try (InputStream stream = contents.getInputStream()) {
                        this.iv = ByteStreams.toByteArray(stream);
                        return resourceClient.discardContents(contents);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, DRIVE.GOOGLE_FILE_NO_SELECTED, e);
                    mainActivity.finish();
                });
    }

    @Override
    public void retrieveContents(DriveFile file) {
        mProgressBar.show();
        resourceClient.openFile(file, DriveFile.MODE_READ_ONLY, new OpenFileCallback() {
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
                try {
                    try (InputStream obtainedFile = driveContents.getInputStream()) {
                        IOManager io = IOManager.newInstance(driveContext);
                        io.writeDownloadedDatabaseBackup(obtainedFile);
                        completeDownload();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "IOException when retrieving contents", e);
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                Log.e(TAG, "Unable to read contents", e);
                mainActivity.finish();
            }
        });
    }

    private void completeDownload() {
        IOManager io = IOManager.newInstance(driveContext);
        InputStream obtainedStream = io.readDownloadedDatabaseBackup();
        final StringBuilder passwordBuilder = new StringBuilder();
        new MaterialDialog.Builder(driveContext)
                .title(R.string.put_pass)
                .content(R.string.pass_need)
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(null, null, false,
                        ((dialog, input) -> passwordBuilder.append(input)))
                .onAny(((dialog, which) -> {
                    String password = Hashing.sha256()
                            .hashString(passwordBuilder.toString(), StandardCharsets.UTF_8)
                            .toString();
                    try {
                        FileCipherOld fileDecrypt = FileCipherOld.newInstance(password,
                                iv);
                        ClassContainer restoredData =
                                (ClassContainer) fileDecrypt.decrypt(obtainedStream);
//                        restoredData.storeDataInDB(); // must implement
                        obtainedStream.close();
                        io.deleteDownloadedDatabaseBackup();
                    } catch (StreamCorruptedException e) {
                        Log.e(TAG, "Password not correct captured");
                        Toast.makeText(driveContext, "Password is not correct",
                                Toast.LENGTH_LONG).show();
                        completeDownload();
                    } catch (Exception e) {
                        Log.e(TAG, "Exception during process of decrypting", e);
                    }
                }))
                .cancelable(false)
                .build().show();
    }

    public void finish() {
        this.mExecutorService.shutdown();
    }
}
