package javinator9889.securepass.views.fragments.firstSetup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

import javinator9889.securepass.R;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.PreferencesManager;
import javinator9889.securepass.util.resources.SharedPreferencesManager;

/**
 * Created by Javinator9889 on 08/04/2018.
 */
public class PasswordRegistration extends FragmentActivity implements ISlideBackgroundColorHolder,
        View.OnClickListener {
    private static final String TAG = "PasswordRegistration";
    private EditText firstPasswordEntry;
    private EditText passwordConfirmation;
    private int backgroundColor = Color.LTGRAY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_intro);
        Button confirmButton = findViewById(R.id.confirmButton);
        firstPasswordEntry = findViewById(R.id.firstPasswordEntry);
        passwordConfirmation = findViewById(R.id.passwordConfirmation);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public int getDefaultBackgroundColor() {
        return this.backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmButton:
                if (validate()) {
                    MaterialDialog savingPasswordProgress = new MaterialDialog
                            .Builder(v.getContext())
                            .title(R.string.saving_pass)
                            .content(R.string.saving_pass_more_info)
                            .progress(true, 0)
                            .build();
                    savingPasswordProgress.show();
                    IOManager io = IOManager.newInstance(this);
                    String passwordWithHashApplied = Hashing.sha256()
                            .hashString(this.firstPasswordEntry.getText().toString(),
                                    StandardCharsets.UTF_8).toString();
                    io.storePassword(passwordWithHashApplied);
                    /*Intent googleDriveLauncher = new Intent(this, DriveContent.class);
                    startActivity(googleDriveLauncher);
                    savingPasswordProgress.dismiss();*/
                    //finish();
                    // This will try to create a DB
                    DatabaseManager manager = DatabaseManager
                            .newInstance(this, passwordWithHashApplied);
                    Thread databaseThread = manager.getDatabaseInitializer();
                    try {
                        databaseThread.join();
                        ISharedPreferencesManager preferencesManager = PreferencesManager
                                .getInstance();
                        preferencesManager.databaseInitialized(true);
                        savingPasswordProgress.dismiss();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(v.getContext(), R.string.passwords_does_not_match,
                            Toast.LENGTH_LONG).show();
                break;
            default:
                Log.e(TAG, "Non-contemplated button click");
                break;
        }
    }

    private boolean validate() {
        try {
            String firstPasswordHash = Hashing.sha256()
                    .hashString(this.firstPasswordEntry.getText().toString(),
                            StandardCharsets.UTF_8).toString();
            String passwordConfirmationHash = Hashing.sha256()
                    .hashString(this.passwordConfirmation.getText().toString(),
                            StandardCharsets.UTF_8).toString();
            return firstPasswordHash.equals(passwordConfirmationHash);
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException with texts. Full trace: " + e.getMessage());
            return false;
        }
    }
}
