package javinator9889.securepass.data.entry.fields;

import androidx.annotation.NonNull;

/**
 * Interface with common methods to {@link Image}, {@link Password}, {@link LongText} and
 * {@link SmallText}
 * Created by Javinator9889 on 16/08/2018.
 */
public interface ICommonMethods {
    /**
     * Updates the field description by te given one
     *
     * @param fieldDescription new description
     */
    void setFieldDescription(@NonNull String fieldDescription);

    /**
     * Obtains the current field description
     *
     * @return <code>String</code> with the description
     */
    String getFieldDescription();

    /**
     * Gets the parent entry ID for this image
     *
     * @return {@code long} with the ID
     */
    long getEntryId();

    /**
     * Sets a new image parent entry ID
     *
     * @param id new parent entry ID
     */
    void setEntryId(long id);
}