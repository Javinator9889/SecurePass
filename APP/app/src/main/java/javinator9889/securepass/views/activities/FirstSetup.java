package javinator9889.securepass.views.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroBaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javinator9889.securepass.R;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.PreferencesManager;
import javinator9889.securepass.util.resources.SharedPreferencesManager;
import javinator9889.securepass.views.fragments.firstSetup.PasswordRegistration;
import javinator9889.securepass.views.fragments.firstSetup.slides.EulaConfirmation;
import javinator9889.securepass.views.fragments.firstSetup.slides.SlidePage;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class FirstSetup extends AppIntro {
//    private Context mApplicationContext;
    private List<AppIntroBaseFragment> mFragmentList;
    private String[] mTitles;
    private String[] mDescriptions;
    private int[] mDrawables;
    private int[] mBackgroundColors;
    private int[] mFontColors;
    private int mElementsCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        this.mApplicationContext = SecurePass.getApplicationInstance().getApplicationContext();
        initParams();
        createFragmentList();
        for (AppIntroBaseFragment baseFragment : mFragmentList) addSlide(baseFragment);
        /*PasswordRegistration registrationFragment = PasswordRegistration
                .newInstance(R.layout.request_intro);*/
        //eulaConfirmation = EulaConfirmation.newInstance(R.layout.eula_intro);
        //addSlide(registrationFragment);
        //addSlide(eulaConfirmation);
        showSkipButton(false);
        setFadeAnimation();
    }

    private void initParams() {
        initTitles();
        initDescriptions();
        initDrawables();
        initBackgroundColors();
        initFontColors();
        mElementsCount = mTitles.length - 1;
    }

    private void initTitles() {
        this.mTitles = getResources().getStringArray(R.array.titles);
    }

    private void initDescriptions() {
        this.mDescriptions = getResources()
                .getStringArray(R.array.descriptions);
    }

    private void initDrawables() {
        this.mDrawables = new int[]{
                R.drawable.speed,
                R.drawable.secure_image,
                R.drawable.data_security,
                R.drawable.user_friendly,
                R.drawable.privacy
        };
    }

    private void initBackgroundColors() {
        this.mBackgroundColors = new int[]{
                Color.BLUE,
                Color.GRAY,
                Color.GREEN,
                Color.YELLOW,
                Color.LTGRAY
        };
    }

    private void initFontColors() {
        this.mFontColors = new int[] {
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.BLACK,
                Color.BLACK
        };
    }

    private void createFragmentList() {
        this.mFragmentList = new ArrayList<>();
        for (int i = 0; i < mElementsCount; ++i) {
            SlidePage currentSlideFragmentToBeAdded = SlidePage.newInstance(
                    mTitles[i],
                    mDescriptions[i],
                    mDrawables[i],
                    mBackgroundColors[i],
                    mFontColors[i],
                    mFontColors[i]
            );
            mFragmentList.add(currentSlideFragmentToBeAdded);
        }
        int elementToAdd = mTitles.length - 1;
        EulaConfirmation eulaConfirmation = EulaConfirmation.newInstance(
                mTitles[elementToAdd],
                mDescriptions[elementToAdd],
                mDrawables[elementToAdd],
                mBackgroundColors[elementToAdd],
                mFontColors[elementToAdd],
                mFontColors[elementToAdd]);
        eulaConfirmation.addPackageContext(this);
        mFragmentList.add(eulaConfirmation);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        ISharedPreferencesManager sharedPreferences = PreferencesManager.getInstance();
        if (sharedPreferences.isApplicationLicenseAccepted()) {
            Intent startPasswordRegistration = new Intent(
                    this,
                    PasswordRegistration.class);
            startActivity(startPasswordRegistration);
            this.finish();
        } else {
            Toast.makeText(this, R.string.no_checkbox_clicked, Toast.LENGTH_LONG).show();
        }
    }
}
