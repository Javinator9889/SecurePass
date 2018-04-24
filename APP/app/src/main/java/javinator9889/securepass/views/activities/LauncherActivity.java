package javinator9889.securepass.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javinator9889.securepass.data.backup.GoogleDriveActivity;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.SharedPreferencesManager;
import javinator9889.securepass.views.fragments.DriveContent;

/**
 * Created by Javinator9889 on 04/04/2018.
 */
public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ISharedPreferencesManager preferencesManager = SharedPreferencesManager.newInstance();
        if (!preferencesManager.isApplicationInitialized()) {
            //Intent firstSetupLauncher = new Intent(this, FirstSetup.class);
            Intent firstSetupLauncher = new Intent(this, DriveContent.class);
            startActivity(firstSetupLauncher);
        } else {
            //to-do
        }
    }
}
