package javinator9889.securepass.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javinator9889.securepass.R;
import javinator9889.securepass.views.fragments.LicenseFragment;
import javinator9889.securepass.views.fragments.PrivacyFragment;
import javinator9889.securepass.views.fragments.TermsFragment;
import ru.noties.markwon.Markwon;

/**
 * Created by Javinator9889 on 17/04/2018.
 */
public class ShowEulaActivity extends AppCompatActivity {
    ActionBar supportActionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getIntent().getBundleExtra("bundle");
        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add(R.string.eula_name, LicenseFragment.class, args)
                        .add(R.string.privacy_name, PrivacyFragment.class, args)
                        .add(R.string.tos_name, TermsFragment.class, args)
                        .create());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        SmartTabLayout tabLayout = findViewById(R.id.viewpagertab);
        tabLayout.setViewPager(viewPager);
        setContentView(R.layout.full_eula);
        supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
//        TextView fullEula = findViewById(R.id.eula_contents_full);
//        Markwon.setMarkdown(fullEula, getString(R.string.eula_terms));
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
