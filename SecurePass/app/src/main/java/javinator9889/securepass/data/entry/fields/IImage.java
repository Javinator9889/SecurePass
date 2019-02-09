package javinator9889.securepass.data.entry.fields;

import androidx.annotation.NonNull;

/**
 * Interface for accessing {@link Image} methods
 * Created by Javinator9889 on 16/08/2018.
 */
public interface IImage extends ICommonMethods {
    /**
     * Saves the image source
     *
     * @param imageSource non null <code>String</code> containing the Base64 source
     * @see android.util.Base64
     */
    void setImageSource(@NonNull String imageSource);

    /**
     * Obtains the image source
     *
     * @return <code>String</code> containing the Base64 image
     * @see android.util.Base64
     */
    String getImageSource();

    /**
     * Obtains the current image ID
     *
     * @return <code>long</code> corresponding the ID
     */
    long getImageID();

    /**
     * Sets a new image ID for any reason - should not be called
     *
     * @param imageID the new ID
     */
    void setImageID(long imageID);
}
