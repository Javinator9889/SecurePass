package javinator9889.securepass.data.entry.fields;


import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public class Image implements IImage, Serializable {
    private String source;
    private long id;
    private String fieldDescription;

    public Image(long id, @NonNull String source, @NonNull String fieldDescription) {
        this.id = id;
        this.source = source;
        this.fieldDescription = fieldDescription;
    }
    @Override
    public void setImageSource(@NonNull String imageSource) {
        this.source = imageSource;
    }

    @Override
    public String getImageSource() {
        return source;
    }

    @Override
    public long getImageID() {
        return id;
    }

    @Override
    public void setImageID(long imageID) {
        this.id = imageID;
    }

    @Override
    public void setFieldDescription(@NonNull String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    @Override
    public String getFieldDescription() {
        return fieldDescription;
    }

    @Override
    public String toString() {
        return "Source: " + getImageSource() + "\nid: " + getImageID() + "\nField description: "
                + getFieldDescription();
    }
}
