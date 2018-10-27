package javinator9889.securepass.objects;

import androidx.annotation.FontRes;

/**
 * Created by Javinator9889 on 12/10/2018.
 */
public class SlidesTypefacesContainer {
    private @FontRes
    int mTitleTypeface;
    private @FontRes
    int mDescriptionTypeface;

    public SlidesTypefacesContainer(@FontRes int titleTypeface, @FontRes int descriptionTypeface) {
        this.mTitleTypeface = titleTypeface;
        this.mDescriptionTypeface = descriptionTypeface;
    }

    @FontRes
    public int getTitleTypeface() {
        return mTitleTypeface;
    }

    @FontRes
    public int getDescriptionTypeface() {
        return mDescriptionTypeface;
    }
}
