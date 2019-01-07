package javinator9889.securepass.data.entry.fields;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Created by Javinator9889 on 16/08/2018.
 */
public class LongText extends Text implements Serializable {
    /**
     * Public available constructor that uses
     * {@link Text#Text(long, long, String, String, int) super}
     * constructor
     *
     * @param id               long text ID
     * @param parentEntryId    long parent entry ID
     * @param text             long text text
     * @param fieldDescription description
     */
    public LongText(long id,
                    long parentEntryId,
                    @NonNull String text,
                    @NonNull String fieldDescription,
                    int order) {
        super(id, parentEntryId, text, fieldDescription, order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Text: " + getText() +
                "\nID: " + getTextID() +
                "\nField description: " + getFieldDescription() +
                "\nEntry ID: " + getEntryId() +
                "\nSort order: " + getOrder();
    }
}
