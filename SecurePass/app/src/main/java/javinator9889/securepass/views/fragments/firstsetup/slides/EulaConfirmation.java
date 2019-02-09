package javinator9889.securepass.views.fragments.firstsetup.slides;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntroBaseFragment;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.model.SliderPage;

import androidx.core.content.res.ResourcesCompat;
import javinator9889.securepass.R;
import javinator9889.securepass.objects.SlidesTypefacesContainer;
import javinator9889.securepass.views.activities.ShowEulaActivity;

/**
 * Created by Javinator9889 on 13/04/2018.
 */
public class EulaConfirmation extends AppIntroBaseFragment implements Button.OnClickListener,
        ISlideBackgroundColorHolder {
    private Context packageContext;
    private Button gotoEulaButton;
    private @Nullable SlidesTypefacesContainer mContainer;

    public static EulaConfirmation newInstance(CharSequence title,
                                               CharSequence description,
                                               @DrawableRes int imageDrawable,
                                               @ColorInt int bgColor,
                                               @ColorInt int titleColor,
                                               @ColorInt int descColor,
                                               @Nullable SlidesTypefacesContainer container) {
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(title);
        sliderPage.setTitleTypeface(null);
        sliderPage.setDescription(description);
        sliderPage.setDescTypeface(null);
        sliderPage.setImageDrawable(imageDrawable);
        sliderPage.setBgColor(bgColor);
        sliderPage.setTitleColor(titleColor);
        sliderPage.setDescColor(descColor);

        return newInstance(sliderPage, container);
    }

    private static EulaConfirmation newInstance(SliderPage sliderPage,
                                                @Nullable SlidesTypefacesContainer container) {
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
        slide.setContainer(container);

        return slide;
    }

    public void setContainer(@Nullable SlidesTypefacesContainer container) {
        this.mContainer = container;
    }

    public void addPackageContext(Context packageContext) {
        this.packageContext = packageContext;
    }

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
        TextView titleText = view.findViewById(R.id.title);
        TextView descriptionText =
                view.findViewById(R.id.description);
        if (mContainer != null) {
            Typeface titleFont = ResourcesCompat.getFont(view.getContext(),
                    mContainer.getTitleTypeface());
            titleText.setTypeface(titleFont);
            Typeface descriptionFont = ResourcesCompat.getFont(view.getContext(),
                    mContainer.getDescriptionTypeface());
            descriptionText.setTypeface(descriptionFont);
        }
        gotoEulaButton = view.findViewById(R.id.goto_full_eula_button);
        gotoEulaButton.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    public Button getGotoEulaButton() {
        return gotoEulaButton;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_full_eula_button:
                Intent startEulaActivity = new Intent(packageContext, ShowEulaActivity.class);
//                startEulaActivity.putExtra("bundle", mExtra);
                startActivity(startEulaActivity);
                break;
            default:
                Log.e("FIRSTSETUP", "Clicked something non-contemplated. ID: "
                        + v.getId());
                break;
        }
    }
}
