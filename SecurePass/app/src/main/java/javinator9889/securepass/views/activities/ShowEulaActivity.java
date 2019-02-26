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

import android.os.Bundle;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import javinator9889.securepass.R;
import javinator9889.securepass.views.TabLayoutAdapter;
import javinator9889.securepass.views.fragments.termsofservice.LicenseFragment;
import javinator9889.securepass.views.fragments.termsofservice.PrivacyFragment;
import javinator9889.securepass.views.fragments.termsofservice.TermsFragment;

/**
 * Custom activity that displays the three texts in tabs.
 */
public class ShowEulaActivity extends TabLayoutAdapter {
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.full_eula);
        setTabIdRes(R.id.viewpagertab);
        setViewPagerIdRes(R.id.viewpager);
        setSelectedTextFontRes(R.font.raleway_semibold);
        setNonSelectedTextFontRes(R.font.raleway);
        setMustChangeTypeface(true);
        setMustShowBackButtonOnActionBar(true);
        super.onCreate(savedInstanceState);
    }

    /**
     * Gets the items (fragments) that conforms the tabs.
     *
     * @return {@code FragmentPagerItems} with the tabs' items.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected FragmentPagerItems getItems() {
        FragmentPagerItems items = new FragmentPagerItems(this);
        Class<? extends Fragment>[] availableItems = new Class[]{PrivacyFragment.class,
                TermsFragment.class, LicenseFragment.class};
        @StringRes int[] availableTitles = new int[]{R.string.privacy_name, R.string.tos_name,
                R.string.eula_name};
        final int itemsCount = availableItems.length;
        for (int i = 0; i < itemsCount; ++i)
            items.add(FragmentPagerItem.of(getString(availableTitles[i]), availableItems[i]));
        return items;
    }
}
