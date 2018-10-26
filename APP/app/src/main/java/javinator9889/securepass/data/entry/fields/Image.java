package javinator9889.securepass.data.entry.fields;


import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Stores Image fields
 * Created by Javinator9889 on 16/08/2018.
 */
public class Image implements IImage, Serializable {
    private String mSource;
    private long mId;
    private String mFieldDescription;

    /**
     * Public available constructor for generating a new instance
     * @param id current image ID
     * @param source Base64 image information
     * @param fieldDescription description
     * @see android.util.Base64
     */
    public Image(long id, @NonNull String source, @NonNull String fieldDescription) {
        this.mId = id;
        this.mSource = source;
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Saves the image source
     * @param imageSource non null <code>String</code> containing the Base64 source
     * @see android.util.Base64
     */
    @Override
    public void setImageSource(@NonNull String imageSource) {
        this.mSource = imageSource;
    }

    /**
     * Obtains the image source
     * @return <code>String</code> containing the Base64 image
     * @see android.util.Base64
     */
    @Override
    public String getImageSource() {
        return mSource;
    }

    /**
     * Obtains the current image ID
     * @return <code>long</code> corresponding the ID
     */
    @Override
    public long getImageID() {
        return mId;
    }

    /**
     * Sets a new image ID for any reason - should not be called
     * @param imageID the new ID
     */
    @Override
    public void setImageID(long imageID) {
        this.mId = imageID;
    }

    /**
     * Updates the field description by te given one
     * @param fieldDescription new description
     * @see ICommonMethods
     */
    @Override
    public void setFieldDescription(@NonNull String fieldDescription) {
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Obtains the current field description
     * @return <code>String</code> with the description
     * @see ICommonMethods
     */
    @Override
    public String getFieldDescription() {
        return mFieldDescription;
    }

    @Override
    public String toString() {
        return "Source: " + getImageSource() + "\nmId: " + getImageID() + "\nField description: "
                + getFieldDescription();
    }
}
