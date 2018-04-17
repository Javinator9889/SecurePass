package javinator9889.securepass.views.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroBaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javinator9889.securepass.R;
import javinator9889.securepass.SecurePass;
import javinator9889.securepass.views.fragments.EulaConfirmation;
import javinator9889.securepass.views.fragments.PasswordRegistration;
import javinator9889.securepass.views.fragments.SlidePage;

import static javinator9889.securepass.util.values.Constants.FIRST_SETUP;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class FirstSetup extends AppIntro {
    //private static final String TAG = "FirstSetup";
    private SecurePass applicationInstance;
    private EulaConfirmation eulaConfirmation;
    private List<AppIntroBaseFragment> fragmentList;
    private String[] titles;
    private String[] descriptions;
    private int[] drawables;
    private int[] backgroundColors;
    private int elementsCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.applicationInstance = SecurePass.getApplicationInstance();
        initParams();
        createFragmentList();
        for (AppIntroBaseFragment baseFragment : fragmentList) addSlide(baseFragment);
        /*PasswordRegistration registrationFragment = PasswordRegistration
                .newInstance(R.layout.request_intro);*/
        //eulaConfirmation = EulaConfirmation.newInstance(R.layout.eula_intro);
        //addSlide(registrationFragment);
        //addSlide(eulaConfirmation);
        showSkipButton(false);
        setFlowAnimation();
    }

    private void initParams() {
        initTitles();
        initDescriptions();
        initDrawables();
        initBackgroundColors();
        elementsCount = titles.length - 1;
    }

    private void initTitles() {
        this.titles = this.applicationInstance.getResources().getStringArray(R.array.titles);
    }

    private void initDescriptions() {
        this.descriptions = this.applicationInstance.getResources()
                .getStringArray(R.array.descriptions);
    }

    private void initDrawables() {
        this.drawables = new int[]{
                R.drawable.speed,
                R.drawable.secure_image,
                R.drawable.data_security,
                R.drawable.user_friendly,
                R.drawable.privacy
        };
    }

    private void initBackgroundColors() {
        this.backgroundColors = new int[]{
                Color.BLUE,
                Color.CYAN,
                Color.GREEN,
                Color.YELLOW,
                Color.LTGRAY
        };
    }

    private void createFragmentList() {
        this.fragmentList = new ArrayList<>();
        for (int i = 0; i < elementsCount; ++i) {
            SlidePage currentSlideFragmentToBeAdded = SlidePage.newInstance(
                    titles[i],
                    descriptions[i],
                    drawables[i],
                    backgroundColors[i],
                    FIRST_SETUP.TITLE_COLOR,
                    FIRST_SETUP.DESCRIPTION_COLOR
            );
            fragmentList.add(currentSlideFragmentToBeAdded);
        }
        int elementToAdd = titles.length - 1;
        eulaConfirmation = EulaConfirmation.newInstance(titles[elementToAdd],
                descriptions[elementToAdd],
                drawables[elementToAdd],
                backgroundColors[elementToAdd],
                Color.BLACK,
                Color.BLACK);
        eulaConfirmation.addPackageContext(this);
        fragmentList.add(eulaConfirmation);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        boolean isCheckBoxChecked = eulaConfirmation.getCheckBox().isChecked();
        if (isCheckBoxChecked) {
            Toast.makeText(this, "Correctly received", Toast.LENGTH_LONG).show();
            //to-do | Load a new activity
        } else {
            Toast.makeText(this, R.string.no_checkbox_clicked, Toast.LENGTH_LONG).show();
        }
    }
}
