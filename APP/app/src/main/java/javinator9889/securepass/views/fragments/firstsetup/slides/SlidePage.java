package javinator9889.securepass.views.fragments.firstsetup.slides;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import com.github.paolorotolo.appintro.AppIntroBaseFragment;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.model.SliderPage;

import javinator9889.securepass.R;

/**
 * Created by Javinator9889 on 03/04/2018.
 */
public class SlidePage extends AppIntroBaseFragment implements ISlideBackgroundColorHolder {
    @Override
    public int getDefaultBackgroundColor() {
        return super.getDefaultBackgroundColor();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        super.setBackgroundColor(backgroundColor);
    }

    public static SlidePage newInstance(CharSequence title,
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

    private static SlidePage newInstance(SliderPage sliderPage) {
        SlidePage slide = new SlidePage();
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_intro;
    }
}
