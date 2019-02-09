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

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import javinator9889.securepass.R;
import javinator9889.securepass.views.TabLayoutAdapter;
import javinator9889.securepass.views.activities.menus.FloatingActionButtonMenuAdapter;
import javinator9889.securepass.views.fragments.EntriesDisplayer;
import uk.co.markormesher.android_fab.FloatingActionButton;

/**
 * TODO
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
        setMustShowBackButtonOnActionBar(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected FragmentPagerItems getItems() {
        // Stress test
//        int amount = new Random().nextInt(10000);
        int amount = 100;
        FragmentPagerItems items = new FragmentPagerItems(this);
        items.add(FragmentPagerItem.of("Title test", EntriesDisplayer.class));
        items.add(FragmentPagerItem.of("Another tab", EntriesDisplayer.class));
        for (int i = 0; i < amount; ++i)
            items.add(FragmentPagerItem.of("Tab #" + i, EntriesDisplayer.class));
        Toast.makeText(this, "Generated: " + amount + " tabs", Toast.LENGTH_LONG).show();
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
                        .colorPrimary, null))
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
