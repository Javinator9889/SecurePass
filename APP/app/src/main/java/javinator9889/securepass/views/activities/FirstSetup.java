package javinator9889.securepass.views.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javinator9889.securepass.R;
import javinator9889.securepass.SecurePass;
import javinator9889.securepass.views.fragments.SlidePage;

import static javinator9889.securepass.util.values.Constants.FIRST_SETUP;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class FirstSetup extends AppIntro {
    private SecurePass applicationInstance;
    private List<SlidePage> fragmentList;
    private String[] titles;
    private String[] descriptions;
    private int[] drawables;
    private int[] backgroundColors;
    private int elementsCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.applicationInstance = SecurePass.getApplicationInstance();
        initParams();
        for (SlidePage aSlidePage : fragmentList) addSlide(aSlidePage);
        showSkipButton(false);
        setFlowAnimation();
    }

    private void initParams() {
        initTitles();
        initDescriptions();
        initDrawables();
        initBackgroundColors();
        elementsCount = titles.length;
    }

    private void initTitles() {
        this.titles = this.applicationInstance.getResources().getStringArray(R.array.titles);
    }

    private void initDescriptions() {
        this.descriptions = this.applicationInstance.getResources()
                .getStringArray(R.array.descriptions);
    }

    private void initDrawables() {
        this.drawables = new int[]{
                R.drawable.speed,
                R.drawable.secure_image,
                R.drawable.data_security,
                R.drawable.user_friendly
        };
    }

    private void initBackgroundColors() {
        this.backgroundColors = new int[]{
                Color.BLUE,
                Color.CYAN,
                Color.GREEN,
                Color.YELLOW
        };
    }

    private void createFragmentList() {
        this.fragmentList = new ArrayList<>();
        for (int i = 0; i < elementsCount; ++i) {
            SlidePage currentSlideFragmentToBeAdded = SlidePage.newInstance(
                    titles[i],
                    descriptions[i],
                    drawables[i],
                    backgroundColors[i],
                    FIRST_SETUP.TITLE_COLOR,
                    FIRST_SETUP.DESCRIPTION_COLOR
                    );
            fragmentList.add(currentSlideFragmentToBeAdded);
        }
    }
}
