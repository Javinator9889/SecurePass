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
package javinator9889.securepass.views.fragments.firstsetup.slides;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.github.paolorotolo.appintro.AppIntroBaseFragment;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.github.paolorotolo.appintro.R.id;

import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import javinator9889.securepass.R;
import javinator9889.securepass.objects.SlidesTypefacesContainer;

/**
 * TODO
 */
public class SlidePage extends AppIntroBaseFragment implements ISlideBackgroundColorHolder {
    private @FontRes int mTitleTypeface = -1;
    private @FontRes int mDescriptionTypeface = -1;

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

    private static SlidePage newInstance(SliderPage sliderPage,
                                         @Nullable SlidesTypefacesContainer container) {
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
        if (container != null) {
            slide.setTitleTypeface(container.getTitleTypeface());
            slide.setDescriptionTypeface(container.getDescriptionTypeface());
        }

        return slide;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleText = view.findViewById(id.title);
        TextView descriptionText = view.findViewById(id.description);
        if (mTitleTypeface != -1) {
            Typeface titleFont = ResourcesCompat.getFont(view.getContext(), mTitleTypeface);
            titleText.setTypeface(titleFont);
        }
        if (mDescriptionTypeface != -1) {
            Typeface descriptionFont = ResourcesCompat.getFont(view.getContext(),
                    mDescriptionTypeface);
            descriptionText.setTypeface(descriptionFont);
        }
    }

    private void setTitleTypeface(@FontRes int titleTypeface) {
        this.mTitleTypeface = titleTypeface;
    }

    private void setDescriptionTypeface(@FontRes int descriptionTypeface) {
        this.mDescriptionTypeface = descriptionTypeface;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.appintro_fragment_intro;
    }
}
