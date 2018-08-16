package javinator9889.securepass.data.entry.fields;


import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public class Image implements IImage, Serializable {
    private String source;
    private int id;
    private String fieldDescription;

    public Image(int id, @NonNull String source, @NonNull String fieldDescription) {
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
    public int getImageID() {
        return id;
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
