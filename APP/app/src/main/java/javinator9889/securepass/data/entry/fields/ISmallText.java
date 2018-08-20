package javinator9889.securepass.data.entry.fields;

import android.support.annotation.NonNull;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public interface ISmallText extends ICommonMethods {
    void setText(@NonNull String text);
    String getText();
    long getSmallTextID();
    void setSmallTextID(long smallTextID);
}
