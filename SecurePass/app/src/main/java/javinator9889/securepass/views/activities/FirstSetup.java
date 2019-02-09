/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 03/11/2018 - SecurePass.
 */
package javinator9889.securepass.views.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroBaseFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import javinator9889.securepass.R;
import javinator9889.securepass.objects.SlidesTypefacesContainer;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.PreferencesManager;
import javinator9889.securepass.views.fragments.firstsetup.PasswordRegistration;
import javinator9889.securepass.views.fragments.firstsetup.slides.EulaConfirmation;
import javinator9889.securepass.views.fragments.firstsetup.slides.SlidePage;

/**
 * TODO
 */
public class FirstSetup extends AppIntro2 {
    private List<AppIntroBaseFragment> mFragmentList;
    private String[] mTitles;
    private String[] mDescriptions;
    private int[] mDrawables;
    private int[] mBackgroundColors;
    private int[] mFontColors;
    private int mElementsCount = 0;
    private @Nullable String mTitleFont;
    private @Nullable String mDescFont;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initParams();
        createFragmentList();
        mTitleFont = "fonts/raleway_semibold.ttf";
        mDescFont = "fonts/raleway.ttf";
//        Typeface titleFont = ResourcesCompat.getFont(this, R.font.raleway_semibold);
//        Typeface descFont = ResourcesCompat.getFont(this, R.font.raleway);
        for (AppIntroBaseFragment baseFragment : mFragmentList) addSlide(baseFragment);
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
        SlidesTypefacesContainer container = new SlidesTypefacesContainer(R.font.raleway_semibold,
                R.font.raleway);
        for (int i = 0; i < mElementsCount; ++i) {
            SlidePage currentSlideFragmentToBeAdded = SlidePage.newInstance(
                    mTitles[i],
                    mDescriptions[i],
                    mDrawables[i],
                    mBackgroundColors[i],
                    mFontColors[i],
                    mFontColors[i],
                    container
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
                mFontColors[elementToAdd],
                container);
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
