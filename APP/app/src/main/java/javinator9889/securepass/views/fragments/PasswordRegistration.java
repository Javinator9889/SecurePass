package javinator9889.securepass.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javinator9889.securepass.R;
import javinator9889.securepass.io.IOManager;

/**
 * Created by Javinator9889 on 08/04/2018.
 */
public class PasswordRegistration extends FragmentActivity implements ISlideBackgroundColorHolder,
        View.OnClickListener {
    private static final String TAG = "PasswordRegistration";
    private EditText firstPasswordEntry;
    private EditText passwordConfirmation;
    private int backgroundColor = Color.LTGRAY;

    /*public static PasswordRegistration newInstance(@LayoutRes int layoutResId) {
        PasswordRegistration passwordInstance = new PasswordRegistration();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        args.putInt(ARG_BG_COLOR, Color.LTGRAY);
        passwordInstance.setArguments(args);

        return passwordInstance;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_intro);
        Button confirmButton = findViewById(R.id.confirmButton);
        firstPasswordEntry = findViewById(R.id.firstPasswordEntry);
        passwordConfirmation = findViewById(R.id.passwordConfirmation);
        confirmButton.setOnClickListener(this);
        /*if (getArguments() != null) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
            backgroundColor = getArguments().getInt(ARG_BG_COLOR);
        }*/
    }

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(container).setBackgroundColor(backgroundColor);
        int layoutResId = R.layout.request_intro;
        return inflater.inflate(layoutResId, container, false);
    }*/

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button confirmButton = view.findViewById(R.id.confirmButton);
        firstPasswordEntry = view.findViewById(R.id.firstPasswordEntry);
        passwordConfirmation = view.findViewById(R.id.passwordConfirmation);
        confirmButton.setOnClickListener(this);
    }*/

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
                    /*Toast.makeText(v.getContext(), "Passwords are the same",
                            Toast.LENGTH_LONG).show();*/
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
                    Log.d("PASSREGISTRATION", "Password hash: " + passwordWithHashApplied);
                    io.storePassword(passwordWithHashApplied);
                    savingPasswordProgress.dismiss();
                    Toast.makeText(v.getContext(), "Password saved", Toast.LENGTH_LONG).show();
                    String hashedPass = io.readPassword();
                    Toast.makeText(v.getContext(), "Pass hash: " + hashedPass, Toast.LENGTH_LONG).show();
                    Log.d("PASSREGISTRATION", "Recovered password hash: " + hashedPass);
                }
                else
                    Toast.makeText(v.getContext(), "Passwords does not match",
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
