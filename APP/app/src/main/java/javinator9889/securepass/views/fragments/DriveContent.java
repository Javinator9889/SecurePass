package javinator9889.securepass.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.OpenFileActivityOptions;

import javinator9889.securepass.R;
import javinator9889.securepass.backup.drive.DriveDownloader;
import javinator9889.securepass.backup.drive.DriveUploader;
import javinator9889.securepass.backup.drive.IDriveDownloader;
import javinator9889.securepass.backup.drive.IDriveUploader;
import javinator9889.securepass.backup.drive.base.GoogleDriveBase;
import javinator9889.securepass.backup.drive.base.IDriveBase;

import static javinator9889.securepass.backup.drive.base.IDriveBase.REQUEST_CODE_OPEN_ITEM;
import static javinator9889.securepass.backup.drive.base.IDriveBase.REQUEST_CODE_SIGN_IN;

/**
 * Created by Javinator9889 on 21/04/2018.
 */
public class DriveContent extends FragmentActivity implements Button.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "drive-quickstart";
    private IDriveBase googleDrive;
//    private IDriveUploader uploader;
//    private IDriveDownloader downloader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive);
        System.out.println("Created");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("On post-create");
        super.onPostCreate(savedInstanceState);
//        this.googleDrive = new GoogleDriveBase(this, this);
//        uploader = new DriveUploader(this, this);
//        downloader = new DriveDownloader(this, this);
        Button signin = findViewById(R.id.signin);
        Button upload = findViewById(R.id.upload);
        Button download = findViewById(R.id.download);
        signin.setOnClickListener(this);
        upload.setOnClickListener(this);
        download.setOnClickListener(this);
        System.out.println("Finished");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                /*Intent signIn = new Intent(this, GoogleDriveBase.class);
                startActivity(signIn);*/
//                googleDrive.signIn();
                System.out.println("Sign-in");
                googleDrive = new GoogleDriveBase(this, this);
                googleDrive.signIn();
                break;
            case R.id.upload:
//                ClassContainer container = DataClassForTests.CONTAINER_TEST_CLASS();
                /*Intent appFolder = new Intent(this, CreateFileInAppFolder.class);
                appFolder.putExtra("data", container);
                startActivity(appFolder);*/
//                googleDrive.uploadFile(container);
                System.out.println("Upload");
                IDriveUploader uploader = new DriveUploader(this, this);
                uploader.uploadDatabase();
                break;
            case R.id.download:
                /*Intent download = new Intent(this,
                        RetrieveContentWithDownloadProgress.class);
                startActivity(download);*/
//                googleDrive.restoreData();
                System.out.println("Download");
                IDriveDownloader downloader = new DriveDownloader(this, this);
                downloader.restoreData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Activity result");
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    GoogleSignInAccount latestSignedInAccount = GoogleSignIn
                            .getLastSignedInAccount(this);
                    if (latestSignedInAccount != null) {
                        DriveClient mDriveClient = Drive
                                .getDriveClient(this, latestSignedInAccount);
                        DriveResourceClient mDriveResourceClient =
                                Drive.getDriveResourceClient(this, latestSignedInAccount);
                        //googleDrive.setDriveClient(mDriveClient);
                        googleDrive.setDriveResourceClient(mDriveResourceClient);
                        googleDrive.setLoggedIn(true);
                    } /*else
                        startActivityForResult(mGoogleSignInClient.getSignInIntent(),
                                REQUEST_CODE_SIGN_IN);*/
                    Toast.makeText(this, "Successfully signed in", Toast.LENGTH_LONG)
                            .show();
                    //finish();
                }
                break;
            case REQUEST_CODE_OPEN_ITEM: // not necessary
                if (resultCode == RESULT_OK) {
                    DriveId id = data.getParcelableExtra(
                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                    //googleDrive.setResult(id);
                } else
                    /*googleDrive.setException(new GoogleDriveUnableToOpenFileException(
                            "Unable to open file"));*/
                break;
            default:
                Log.e(TAG, "Result for activity no contemplated. RequestCode: " + requestCode +
                        " | ResultCode: " + resultCode + " | Intent data: " + data.toString());
//                finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Error de conexion!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "OnConnectionFailed: " + connectionResult);
    }
}
