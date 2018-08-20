package javinator9889.securepass.data.entry.fields;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public class SmallText implements ISmallText, Serializable {
    private long id;
    private String text;
    private String fieldDescription;

    public SmallText(long id, @NonNull String text, @NonNull String fieldDescription) {
        this.id = id;
        this.text = text;
        this.fieldDescription = fieldDescription;
    }

    @Override
    public void setText(@NonNull String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public long getSmallTextID() {
        return id;
    }

    @Override
    public void setSmallTextID(long smallTextID) {
        this.id = smallTextID;
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
        return "Text: " + getText() + "\nID: " + getSmallTextID() + "\nField description: " +
                getFieldDescription();
    }
}
