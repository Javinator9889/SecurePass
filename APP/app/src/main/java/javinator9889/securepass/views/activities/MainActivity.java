package javinator9889.securepass.views.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.LayoutInflaterCompat;
import javinator9889.securepass.R;
import javinator9889.securepass.views.activities.menus.FloatingActionButtonMenuAdapter;
import uk.co.markormesher.android_fab.FloatingActionButton;
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter;
import uk.co.markormesher.android_fab.SpeedDialMenuItem;

/**
 * Created by Javinator9889 on 11/10/2018.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("On Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        System.out.println("Finished on create");
    }

    @Override
    protected void onStart() {
        System.out.println("On Start");
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
        System.out.println("Created fab");
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        System.out.println("Clicked!");
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
