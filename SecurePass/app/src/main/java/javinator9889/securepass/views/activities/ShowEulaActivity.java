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
 * Created by Javinator9889 on 17/04/2018.
 */
public class ShowEulaActivity extends TabLayoutAdapter {
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
