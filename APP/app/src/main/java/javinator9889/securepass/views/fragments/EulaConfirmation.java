package javinator9889.securepass.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntroBaseFragment;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.model.SliderPage;

import java.util.Objects;

import javinator9889.securepass.R;
import javinator9889.securepass.views.activities.ShowEulaActivity;
import ru.noties.markwon.Markwon;

/**
 * Created by Javinator9889 on 13/04/2018.
 */
public class EulaConfirmation extends AppIntroBaseFragment implements Button.OnClickListener,
        ISlideBackgroundColorHolder {
    private Context packageContext;
    private CheckBox eulaAcceptCheckBox;
    private Button gotoEulaButton;

    /*public static EulaConfirmation newInstance(@LayoutRes int layoutResId) {
        EulaConfirmation eulaConfirmation = new EulaConfirmation();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        args.putInt(ARG_BG_COLOR, Color.LTGRAY);
        eulaConfirmation.setArguments(args);

        return eulaConfirmation;
    }*/
    public static EulaConfirmation newInstance(CharSequence title,
                                               CharSequence description,
                                               @DrawableRes int imageDrawable,
                                               @ColorInt int bgColor,
                                               @ColorInt int titleColor,
                                               @ColorInt int descColor) {
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(title);
        sliderPage.setTitleTypeface(null);
        sliderPage.setDescription(description);
        sliderPage.setDescTypeface(null);
        sliderPage.setImageDrawable(imageDrawable);
        sliderPage.setBgColor(bgColor);
        sliderPage.setTitleColor(titleColor);
        sliderPage.setDescColor(descColor);

        return newInstance(sliderPage);
    }

    private static EulaConfirmation newInstance(SliderPage sliderPage) {
        EulaConfirmation slide = new EulaConfirmation();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, sliderPage.getTitleString());
        args.putString(ARG_TITLE_TYPEFACE, sliderPage.getTitleTypeface());
        args.putString(ARG_DESC, sliderPage.getDescriptionString());
        args.putString(ARG_DESC_TYPEFACE, sliderPage.getDescTypeface());
        args.putInt(ARG_DRAWABLE, sliderPage.getImageDrawable());
        args.putInt(ARG_BG_COLOR, sliderPage.getBgColor());
        args.putInt(ARG_TITLE_COLOR, sliderPage.getTitleColor());
        args.putInt(ARG_DESC_COLOR, sliderPage.getDescColor());
        slide.setArguments(args);

        return slide;
    }

    public void addPackageContext(Context packageContext) {
        this.packageContext = packageContext;
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
            backgroundColor = getArguments().getInt(ARG_BG_COLOR);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(container).setBackgroundColor(backgroundColor);
        return inflater.inflate(layoutResId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        eulaAcceptCheckBox = view.findViewById(R.id.eula_accept);
        TextView fullEula = view.findViewById(R.id.eula_content);
        Markwon.setMarkdown(fullEula, getString(R.string.eula_terms));
        super.onViewCreated(view, savedInstanceState);
    }*/

    @Override
    public int getDefaultBackgroundColor() {
        return super.getDefaultBackgroundColor();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        super.setBackgroundColor(backgroundColor);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.eula_intro;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        eulaAcceptCheckBox = view.findViewById(R.id.eula_accept);
        gotoEulaButton = view.findViewById(R.id.goto_full_eula_button);
        gotoEulaButton.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    public CheckBox getCheckBox() {
        return eulaAcceptCheckBox;
    }

    public Button getGotoEulaButton() {
        return gotoEulaButton;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_full_eula_button:
                Intent startEulaActivity = new Intent(packageContext, ShowEulaActivity.class);
                startActivity(startEulaActivity);
                break;
            default:
                Log.e("FIRSTSETUP", "Clicked something non-contemplated. ID: "
                        + v.getId());
                break;
        }
    }
}