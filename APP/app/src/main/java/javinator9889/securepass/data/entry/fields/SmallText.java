package javinator9889.securepass.data.entry.fields;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public class SmallText implements ISmallText, Serializable {
    private int id;
    private String text;
    private String fieldDescription;

    public SmallText(int id, @NonNull String text, @NonNull String fieldDescription) {
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
    public int getSmallTextID() {
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
        return "Text: " + getText() + "\nID: " + getSmallTextID() + "\nField description: " +
                getFieldDescription();
    }
}
