package javinator9889.securepass.views.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.HashMap;

import javinator9889.securepass.R;
import javinator9889.securepass.views.fragments.termsofservice.LicenseFragment;
import javinator9889.securepass.views.fragments.termsofservice.PrivacyFragment;
import javinator9889.securepass.views.fragments.termsofservice.TermsFragment;

/**
 * Created by Javinator9889 on 17/04/2018.
 */
public class ShowEulaActivity extends AppCompatActivity
        implements SmartTabLayout.OnTabClickListener, ViewPager.OnPageChangeListener {
    private int currentPosition = 0;
    private ViewPager viewPager;
    private SmartTabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_eula);
        FragmentPagerItem[] availableItems = getItems();
        FragmentPagerItems.Creator itemsCreator = FragmentPagerItems.with(this);
        for (FragmentPagerItem item : availableItems)
            itemsCreator.add(item);
        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),
                itemsCreator.create());
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        tabLayout = findViewById(R.id.viewpagertab);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnTabClickListener(this);
        tabLayout.setOnPageChangeListener(this);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @SuppressWarnings("unchecked")
    private FragmentPagerItem[] getItems() {
        Class<? extends Fragment>[] availableItems =
                new Class[]{PrivacyFragment.class, TermsFragment.class, LicenseFragment.class};
        @StringRes int[] titles =
                new int[]{R.string.privacy_name, R.string.tos_name, R.string.eula_name};
        final int count = availableItems.length;
        FragmentPagerItem[] items = new FragmentPagerItem[count];
        for (int i = 0; i < count; ++i) {
            items[i] = FragmentPagerItem.of(getString(titles[i]), availableItems[i]);
        }
        return items;
    }

    @Override
    public void onBackPressed() {
        if (currentPosition > 0) {
            --currentPosition;
            viewPager.setCurrentItem(currentPosition, true);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            currentPosition = 0;
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeTextTypeface(int position) {
        Typeface nonSelectedText = ResourcesCompat.getFont(this, R.font.raleway);
        Typeface selectedText = ResourcesCompat.getFont(this, R.font.raleway_semibold);
        LinearLayout tabs = (LinearLayout) tabLayout.getChildAt(0);
        for (int i = 0; i < tabs.getChildCount(); ++i) {
            TextView currentText = (TextView) tabs.getChildAt(i);
            if (i != position)
                currentText.setTypeface(nonSelectedText);
            else
                currentText.setTypeface(selectedText);
        }
    }

    @Override
    public void onTabClicked(int position) {
        this.currentPosition = position;
        changeTextTypeface(position);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        return;
    }

    @Override
    public void onPageSelected(int currentPosition) {
        this.currentPosition = currentPosition;
        changeTextTypeface(currentPosition);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        return;
    }
}
