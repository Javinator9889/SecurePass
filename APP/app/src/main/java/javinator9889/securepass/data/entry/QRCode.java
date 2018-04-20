package javinator9889.securepass.data.entry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class QRCode implements Serializable {
    private int id;
    private String name;
    private String description;
    private String qrData;
    private Entry parentEntry;

    public QRCode(int id, @NonNull String name, @Nullable String description,
                  @NonNull String qrData, @NonNull Entry parentEntry) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qrData = qrData;
        this.parentEntry = parentEntry;
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

    public Entry getEntry() {
        return parentEntry;
    }

    public void setEntry(@NonNull Entry entry) {
        this.parentEntry = parentEntry;
    }

    @Override
    public String toString() {
        return "QRCode ID: " + id + "\nQRCode name: " + name + "\nQRCode description: " +
                description + "\nQRCode data: " + qrData + "QRCode entry: " +
                parentEntry.toString();
    }
}
