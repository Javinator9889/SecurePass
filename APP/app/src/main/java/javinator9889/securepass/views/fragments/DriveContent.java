package javinator9889.securepass.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javinator9889.securepass.R;
import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.network.drive.CreateFileInAppFolder;
import javinator9889.securepass.network.drive.DataClassForTests;
import javinator9889.securepass.network.drive.RetrieveContentWithDownloadProgress;
import javinator9889.securepass.network.drive.base.GoogleDriveBase;

/**
 * Created by Javinator9889 on 21/04/2018.
 */
public class DriveContent extends FragmentActivity implements Button.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "drive-quickstart";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Button signin = findViewById(R.id.signin);
        Button upload = findViewById(R.id.upload);
        Button download = findViewById(R.id.download);
        signin.setOnClickListener(this);
        upload.setOnClickListener(this);
        download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                Intent signIn = new Intent(this, GoogleDriveBase.class);
                startActivity(signIn);
                break;
            case R.id.upload:
                ClassContainer container = DataClassForTests.CONTAINER_TEST_CLASS();
                Intent appFolder = new Intent(this, CreateFileInAppFolder.class);
                appFolder.putExtra("data", container);
                startActivity(appFolder);
                break;
            case R.id.download:
                Intent download = new Intent(this,
                        RetrieveContentWithDownloadProgress.class);
                startActivity(download);
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Error de conexion!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "OnConnectionFailed: " + connectionResult);
    }
}
