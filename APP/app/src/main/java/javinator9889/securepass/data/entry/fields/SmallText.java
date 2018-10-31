package javinator9889.securepass.data.entry.fields;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public class SmallText extends Text implements Serializable {
    /**
     * Public available constructor that uses {@link Text#Text(long, String, String) super}
     * constructor
     *
     * @param id               long text ID
     * @param text             long text text
     * @param fieldDescription description
     */
    public SmallText(long id,
                     long parentEntryId,
                     @NonNull String text,
                     @NonNull String fieldDescription) {
        super(id, parentEntryId, text, fieldDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Text: " + getText() +
                "\nID: " + getTextID() +
                "\nField description: " + getFieldDescription();
    }
}
