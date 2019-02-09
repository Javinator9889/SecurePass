package javinator9889.securepass.data.entry;

import java.io.Serializable;
import java.util.Objects;

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
    private long mParentEntryId;

    /**
     * Public constructor available for generating a new QRCode
     *
     * @param id            QRCode ID
     * @param name          QRCode name
     * @param description   QRCode description
     * @param qrData        QRCode data
     * @param parentEntryId QRCode parent entry ID
     */
    public QRCode(long id,
                  @NonNull String name,
                  @Nullable String description,
                  @NonNull String qrData,
                  long parentEntryId) {
        this.mId = id;
        this.mName = name;
        this.description = description;
        this.mQrData = qrData;
        this.mParentEntryId = parentEntryId;
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
     * @return parent entry ID
     */
    public long getEntryId() {
        return mParentEntryId;
    }

    /**
     * Updates QRCode parent entryId
     *
     * @param entryId new entryId
     */
    public void setEntryId(@NonNull long entryId) {
        this.mParentEntryId = entryId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "QRCode ID: " + mId +
                "\nQRCode name: " + mName +
                "\nQRCode description: " + description +
                "\nQRCode data: " + mQrData +
                "\nQRCode entry ID: " + mParentEntryId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QRCode qrCode = (QRCode) o;
        return mId == qrCode.mId &&
                mParentEntryId == qrCode.mParentEntryId &&
                Objects.equals(mName, qrCode.mName) &&
                Objects.equals(description, qrCode.description) &&
                Objects.equals(mQrData, qrCode.mQrData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mName, description, mQrData, mParentEntryId);
    }
}
