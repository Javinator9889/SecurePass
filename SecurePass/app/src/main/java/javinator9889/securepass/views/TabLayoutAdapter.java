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
 * Adapter for displaying a TabLayout with custom options, typefaces and more.
 */
public abstract class TabLayoutAdapter extends AppCompatActivity
        implements SmartTabLayout.OnTabClickListener, ViewPager.OnPageChangeListener {
    private int mCurrentPosition = 0;
    private ViewPager mViewPager;
    private SmartTabLayout mTabLayout;
    private boolean mMustChangeTypeface = false;
    private @FontRes
    int mSelectedTextFontRes = 0;
    private @FontRes
    int mNonSelectedTextFontRes = 0;
    private @IdRes
    int mTabIdRes;
    private @IdRes
    int mViewPagerIdRes;
    private boolean mMustShowBackButtonOnActionBar = false;

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        if (mCurrentPosition > 0) {
            --mCurrentPosition;
            mViewPager.setCurrentItem(mCurrentPosition, true);
        } else
            super.onBackPressed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mCurrentPosition = 0;
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTabClicked(int position) {
        mCurrentPosition = position;
        if (mMustChangeTypeface && mNonSelectedTextFontRes != 0 && mSelectedTextFontRes != 0)
            changeTextTypeface(position, mSelectedTextFontRes, mNonSelectedTextFontRes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageSelected(int position) {
        onTabClicked(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Sets if the typeface must change.
     *
     * @param mustChangeTypeface whether or not it has to change.
     */
    public void setMustChangeTypeface(boolean mustChangeTypeface) {
        mMustChangeTypeface = mustChangeTypeface;
    }

    /**
     * Sets the selected font typeface.
     *
     * @param selectedTextFontRes the typeface.
     */
    public void setSelectedTextFontRes(@FontRes int selectedTextFontRes) {
        mSelectedTextFontRes = selectedTextFontRes;
    }

    /**
     * Sets the non selected font typeface.
     *
     * @param nonSelectedTextFontRes the typeface.
     */
    public void setNonSelectedTextFontRes(@FontRes int nonSelectedTextFontRes) {
        mNonSelectedTextFontRes = nonSelectedTextFontRes;
    }

    /**
     * Sets the tab ID resource.
     *
     * @param tabIdRes the ID resource.
     */
    public void setTabIdRes(@IdRes int tabIdRes) {
        mTabIdRes = tabIdRes;
    }

    /**
     * Sets the view pager ID resource.
     *
     * @param viewPagerIdRes the ID resource.
     */
    public void setViewPagerIdRes(@IdRes int viewPagerIdRes) {
        mViewPagerIdRes = viewPagerIdRes;
    }

    /**
     * Sets whether the back button should be displayed or not.
     *
     * @param mustShowBackButtonOnActionBar whether it must be shown or not.
     */
    public void setMustShowBackButtonOnActionBar(boolean mustShowBackButtonOnActionBar) {
        mMustShowBackButtonOnActionBar = mustShowBackButtonOnActionBar;
    }

    /**
     * Changes the text typeface the first execution - worthless if so many values are displayed
     * (very slow).
     *
     * @param position           the selected position.
     * @param selectedFontRes    the font resource for the selected text.
     * @param nonSelectedFontRes the font resource for the non selected text.
     */
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

    /**
     * Gets the items that conforms a tab.
     *
     * @return {@code FragmentPagerItems} items.
     */
    protected abstract FragmentPagerItems getItems();
}
