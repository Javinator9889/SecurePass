package javinator9889.securepass.data.entry.fields;

import androidx.annotation.NonNull;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public interface IImage extends ICommonMethods {
    void setImageSource(@NonNull String imageSource);
    String getImageSource();
    long getImageID();
    void setImageID(long imageID);
}
