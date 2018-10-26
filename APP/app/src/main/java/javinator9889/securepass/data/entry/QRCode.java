package javinator9889.securepass.data.entry;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Class containing QRCodes
 * Created by Javinator9889 on 29/03/2018.
 */
public class QRCode implements Serializable {
    private long mId;
    private String mName;
    private String description;
    private String mQrData;
    private Entry mParentEntry;

    /**
     * Public constructor available for generating a new QRCode
     *
     * @param id          QRCode ID
     * @param name        QRCode name
     * @param description QRCode description
     * @param qrData      QRCode data
     * @param parentEntry QRCode parent entry
     */
    public QRCode(long id,
                  @NonNull String name,
                  @Nullable String description,
                  @NonNull String qrData,
                  @NonNull Entry parentEntry) {
        this.mId = id;
        this.mName = name;
        this.description = description;
        this.mQrData = qrData;
        this.mParentEntry = parentEntry;
    }

    /**
     * Obtains current QRCode ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    /**
     * Updates current QRCode ID - this method should not be used
     *
     * @param id new ID
     */
    public void setId(long id) {
        this.mId = id;
    }

    /**
     * Obtains current QRCode name
     *
     * @return <code>String</code> with the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Updates current QRCode name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Obtains current QRCode description
     *
     * @return <code>String</code> with the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates current QRCode description
     *
     * @param description new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtains QRCode data
     *
     * @return <code>String</code> with data
     */
    public String getQrData() {
        return mQrData;
    }

    /**
     * Updates QRCode data
     *
     * @param qrData new data
     */
    public void setQrData(String qrData) {
        this.mQrData = qrData;
    }

    /**
     * Obtains QRCode parent entry
     *
     * @return parent <code>Entry</code>
     */
    public Entry getEntry() {
        return mParentEntry;
    }

    /**
     * Updates QRCode parent entry
     *
     * @param entry new entry
     */
    public void setEntry(@NonNull Entry entry) {
        this.mParentEntry = entry;
    }

    @Override
    public String toString() {
        return "QRCode ID: " + mId +
                "\nQRCode mName: " + mName +
                "\nQRCode description: " + description +
                "\nQRCode data: " + mQrData +
                "\nQRCode entry: " + mParentEntry.toString();
    }
}
