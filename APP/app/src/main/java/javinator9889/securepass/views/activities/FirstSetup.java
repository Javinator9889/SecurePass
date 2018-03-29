package javinator9889.securepass.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.List;

import javinator9889.securepass.R;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class FirstSetup extends AppIntro {
    private List<AppIntroFragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private AppIntroFragment pageCreator() {
        // this is for testing
        return AppIntroFragment.newInstance("title", "typeface", "description", "descTypeface", R.drawable.secure_image, 0, 0, 0);
    }
}
