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
package javinator9889.securepass.objects;

import androidx.annotation.FontRes;

/**
 * Class for containing <code>Typeface</code> for
 * {@link com.github.paolorotolo.appintro.model.SliderPage}
 */
public class SlidesTypefacesContainer {
    private @FontRes
    int mTitleTypeface;
    private @FontRes
    int mDescriptionTypeface;

    /**
     * Public available constructor for saving the <code>Typeface</code>s
     *
     * @param titleTypeface       title custom typeface (must be a @FontRes)
     * @param descriptionTypeface description custom typeface (must be a @FontRes)
     * @see FontRes
     */
    public SlidesTypefacesContainer(@FontRes int titleTypeface, @FontRes int descriptionTypeface) {
        this.mTitleTypeface = titleTypeface;
        this.mDescriptionTypeface = descriptionTypeface;
    }

    /**
     * Obtains the stored title <code>Typeface</code>
     *
     * @return <code>int</code> with the @FontRes typeface
     * @see FontRes
     */
    @FontRes
    public int getTitleTypeface() {
        return mTitleTypeface;
    }

    /**
     * Obtains the stored description <code>Typeface</code>
     *
     * @return <code>int</code> with the @FontRes typeface
     * @see FontRes
     */
    @FontRes
    public int getDescriptionTypeface() {
        return mDescriptionTypeface;
    }
}
