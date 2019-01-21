package javinator9889.securepass.data.entry.fields;


import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Stores Image fields
 * Created by Javinator9889 on 16/08/2018.
 */
public class Image implements IImage, Serializable {
    private long mId;
    private long mParentEntryId;
    private String mSource;
    private String mFieldDescription;

    /**
     * Public available constructor for generating a new instance
     *
     * @param id               current image ID
     * @param source           Base64 image information
     * @param fieldDescription description
     * @see android.util.Base64
     */
    public Image(long id,
                 long parentEntryId,
                 @NonNull String source,
                 @NonNull String fieldDescription) {
        this.mId = id;
        this.mParentEntryId = parentEntryId;
        this.mSource = source;
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Saves the image source
     *
     * @param imageSource non null <code>String</code> containing the Base64 source
     * @see android.util.Base64
     */
    @Override
    public void setImageSource(@NonNull String imageSource) {
        this.mSource = imageSource;
    }

    /**
     * Obtains the image source
     *
     * @return <code>String</code> containing the Base64 image
     * @see android.util.Base64
     */
    @Override
    public String getImageSource() {
        return mSource;
    }

    /**
     * Obtains the current image ID
     *
     * @return <code>long</code> corresponding the ID
     */
    @Override
    public long getImageID() {
        return mId;
    }

    /**
     * Sets a new image ID for any reason - should not be called
     *
     * @param imageID the new ID
     */
    @Override
    public void setImageID(long imageID) {
        this.mId = imageID;
    }

    /**
     * Gets the parent entry ID for this image
     *
     * @return {@code long} with the ID
     */
    @Override
    public long getEntryId() {
        return mParentEntryId;
    }

    /**
     * Sets a new image parent entry ID
     *
     * @param id new parent entry ID
     */
    @Override
    public void setEntryId(long id) {
        mParentEntryId = id;
    }

    /**
     * Updates the field description by te given one
     *
     * @param fieldDescription new description
     * @see ICommonMethods
     */
    @Override
    public void setFieldDescription(@NonNull String fieldDescription) {
        this.mFieldDescription = fieldDescription;
    }

    /**
     * Obtains the current field description
     *
     * @return <code>String</code> with the description
     * @see ICommonMethods
     */
    @Override
    public String getFieldDescription() {
        return mFieldDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Source: " + getImageSource() +
                "\nId: " + getImageID() +
                "\nEntry ID: " + getEntryId() +
                "\nField description: " + getFieldDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return mId == image.mId &&
                mParentEntryId == image.mParentEntryId &&
                Objects.equals(mSource, image.mSource) &&
                Objects.equals(mFieldDescription, image.mFieldDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mParentEntryId, mSource, mFieldDescription);
    }
}
