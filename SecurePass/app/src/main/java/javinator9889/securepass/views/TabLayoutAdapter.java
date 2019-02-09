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
package javinator9889.securepass.views;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.FontRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Adapter for displaying a TabLayout.
 */
public abstract class TabLayoutAdapter extends AppCompatActivity
        implements SmartTabLayout.OnTabClickListener, ViewPager.OnPageChangeListener {
    private int mCurrentPosition = 0;
    private ViewPager mViewPager;
    private SmartTabLayout mTabLayout;
    private boolean mMustChangeTypeface = false;
    private @FontRes int mSelectedTextFontRes = 0;
    private @FontRes int mNonSelectedTextFontRes = 0;
    private @IdRes int mTabIdRes;
    private @IdRes int mViewPagerIdRes;
    private boolean mMustShowBackButtonOnActionBar = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabLayout = findViewById(mTabIdRes);
        mViewPager = findViewById(mViewPagerIdRes);
        FragmentPagerItems availableTabs = getItems();
        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),
                availableTabs);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setOnPageChangeListener(this);
        mTabLayout.setOnTabClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(mMustShowBackButtonOnActionBar);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPosition > 0) {
            --mCurrentPosition;
            mViewPager.setCurrentItem(mCurrentPosition, true);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mCurrentPosition = 0;
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabClicked(int position) {
        mCurrentPosition = position;
        if (mMustChangeTypeface && mNonSelectedTextFontRes != 0 && mSelectedTextFontRes != 0)
            changeTextTypeface(position, mSelectedTextFontRes, mNonSelectedTextFontRes);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        onTabClicked(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setMustChangeTypeface(boolean mustChangeTypeface) {
        mMustChangeTypeface = mustChangeTypeface;
    }

    public void setSelectedTextFontRes(@FontRes int selectedTextFontRes) {
        mSelectedTextFontRes = selectedTextFontRes;
    }

    public void setNonSelectedTextFontRes(@FontRes int nonSelectedTextFontRes) {
        mNonSelectedTextFontRes = nonSelectedTextFontRes;
    }

    public void setTabIdRes(@IdRes int tabIdRes) {
        mTabIdRes = tabIdRes;
    }

    public void setViewPagerIdRes(@IdRes int viewPagerIdRes) {
        mViewPagerIdRes = viewPagerIdRes;
    }

    public void setMustShowBackButtonOnActionBar(boolean mustShowBackButtonOnActionBar) {
        mMustShowBackButtonOnActionBar = mustShowBackButtonOnActionBar;
    }

    void changeTextTypeface(int position,
                            @FontRes int selectedFontRes,
                            @FontRes int nonSelectedFontRes) {
        Typeface nonSelectedTextFont = ResourcesCompat.getFont(this, nonSelectedFontRes);
        Typeface selectedTextFont = ResourcesCompat.getFont(this, selectedFontRes);
        LinearLayout availableTabs = LinearLayout.class.cast(mTabLayout.getChildAt(0));
        for (int i = 0; i < availableTabs.getChildCount(); ++i) {
            TextView currentText = TextView.class.cast(availableTabs.getChildAt(i));
            if (i != position)
                currentText.setTypeface(nonSelectedTextFont);
            else
                currentText.setTypeface(selectedTextFont);
        }
    }

    protected abstract FragmentPagerItems getItems();
}
