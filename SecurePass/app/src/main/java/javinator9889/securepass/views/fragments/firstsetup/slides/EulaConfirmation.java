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

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntroBaseFragment;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.model.SliderPage;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import javinator9889.securepass.R;
import javinator9889.securepass.objects.SlidesTypefacesContainer;
import javinator9889.securepass.views.activities.ShowEulaActivity;

/**
 * Custom {@link AppIntroBaseFragment SlidePage} that shows a button for accepting the EULA.
 */
public class EulaConfirmation extends AppIntroBaseFragment implements Button.OnClickListener,
        ISlideBackgroundColorHolder {
    private Context packageContext;
    private Button gotoEulaButton;
    private @Nullable SlidesTypefacesContainer mContainer;

    /**
     * Generates a new eula confirmation page for an AppIntro instance.
     *
     * @param title         the title.
     * @param description   the description.
     * @param imageDrawable the drawable.
     * @param bgColor       the background color.
     * @param titleColor    the title color.
     * @param descColor     the description color.
     * @param container     the container.
     * @return {@code SlidePage} instance.
     */
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

    /**
     * Generates a new slider page for an AppIntro instance.
     *
     * @param sliderPage the generated options.
     * @param container  the container.
     * @return {@code SlidePage} instance.
     */
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

    /**
     * Sets the container.
     * @param container the container.
     */
    public void setContainer(@Nullable SlidesTypefacesContainer container) {
        this.mContainer = container;
    }

    /**
     * Updates the package context.
     * @param packageContext the context.
     */
    public void addPackageContext(Context packageContext) {
        this.packageContext = packageContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultBackgroundColor() {
        return super.getDefaultBackgroundColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundColor(int backgroundColor) {
        super.setBackgroundColor(backgroundColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getLayoutId() {
        return R.layout.eula_intro;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Gets the EULA button.
     * @return {@code Button}.
     */
    public Button getGotoEulaButton() {
        return gotoEulaButton;
    }

    /**
     * {@inheritDoc}
     */
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
