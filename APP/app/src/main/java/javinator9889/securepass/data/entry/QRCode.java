package javinator9889.securepass.data.entry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class QRCode {
    private int id;
    private String name;
    private String description;
    private String qrData;
    private int entryID;

    public QRCode(int id, @NonNull String name, @Nullable String description,
                  @NonNull String qrData, int entryID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qrData = qrData;
        this.entryID = entryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrData() {
        return qrData;
    }

    public void setQrData(String qrData) {
        this.qrData = qrData;
    }

    public int getEntryID() {
        return entryID;
    }

    public void setEntryID(int entryID) {
        this.entryID = entryID;
    }
}
