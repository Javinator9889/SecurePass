package javinator9889.securepass.objects;

import androidx.annotation.FontRes;

/**
 * Class for containing <code>Typeface</code> for
 * {@link com.github.paolorotolo.appintro.model.SliderPage}
 * Created by Javinator9889 on 12/10/2018.
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
