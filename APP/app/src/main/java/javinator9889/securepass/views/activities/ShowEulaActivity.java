package javinator9889.securepass.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javinator9889.securepass.R;
import javinator9889.securepass.views.fragments.termsofservice.LicenseFragment;
import javinator9889.securepass.views.fragments.termsofservice.PrivacyFragment;
import javinator9889.securepass.views.fragments.termsofservice.TermsFragment;

/**
 * Created by Javinator9889 on 17/04/2018.
 */
public class ShowEulaActivity extends AppCompatActivity implements SmartTabLayout.OnTabClickListener, ViewPager.OnPageChangeListener {
    private int currentPosition = 0;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_eula);
//        Bundle args = getIntent().getBundleExtra("bundle");
        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add(R.string.privacy_name, PrivacyFragment.class)
                        .add(R.string.tos_name, TermsFragment.class)
                        .add(R.string.eula_name, LicenseFragment.class)
                        .create());
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        SmartTabLayout tabLayout = findViewById(R.id.viewpagertab);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnTabClickListener(this);
        tabLayout.setOnPageChangeListener(this);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onTabClicked(int position) {
        this.currentPosition = position;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        return;
    }

    @Override
    public void onPageSelected(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        return;
    }
}
