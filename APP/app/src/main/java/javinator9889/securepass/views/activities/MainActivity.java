package javinator9889.securepass.views.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import javinator9889.securepass.R;
import javinator9889.securepass.views.TabLayoutAdapter;
import javinator9889.securepass.views.activities.menus.FloatingActionButtonMenuAdapter;
import javinator9889.securepass.views.fragments.EntriesDisplayer;
import uk.co.markormesher.android_fab.FloatingActionButton;

/**
 * Created by Javinator9889 on 11/10/2018.
 */
public class MainActivity extends TabLayoutAdapter implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.main_view);
        setTabIdRes(R.id.main_view_tabs);
        setViewPagerIdRes(R.id.main_viewpager);
        setSelectedTextFontRes(R.font.raleway_semibold);
        setNonSelectedTextFontRes(R.font.raleway);
        setMustChangeTypeface(true);
        setMustShowBackButtonOnActionBar(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected FragmentPagerItems getItems() {
        FragmentPagerItems items = new FragmentPagerItems(this);
        items.add(FragmentPagerItem.of("Title test", EntriesDisplayer.class));
        return items;
    }

    @Override
    protected void onStart() {
        FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(this);
        button.setContentCoverEnabled(true);
        FloatingActionButtonMenuAdapter menuAdapter = FloatingActionButtonMenuAdapter
                .Builder(getApplicationContext(), 3)
                .addMenuItem("item 1", MaterialDesignIconic.Icon.gmi_collection_image_o)
                .addMenuItem("item 2", MaterialDesignIconic.Icon.gmi_collection_add)
                .addMenuItem("item 3", MaterialDesignIconic.Icon.gmi_account)
                .setLabelsCustomTypeface(R.font.raleway_semibold)
                .setCustomIconsColor(ResourcesCompat.getColor(getResources(), R.color
                        .colorPrimaryDark, null))
                .setLabelsCustomBackgroundColor(Color.WHITE)
                .withIconRotationEnabled(true)
                .build();
        button.setSpeedDialMenuAdapter(menuAdapter);
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                FloatingActionButton button = v.findViewById(R.id.fab);
                if (button.isSpeedDialMenuOpen())
                    button.closeSpeedDialMenu();
                else
                    button.openSpeedDialMenu();
                break;
            default:
                System.err.println("ID: " + v.getId());
                break;
        }
    }
}
