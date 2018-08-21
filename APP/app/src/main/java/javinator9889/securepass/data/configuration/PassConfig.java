package javinator9889.securepass.data.configuration;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class PassConfig implements IConfigFields, Serializable {
    private static final int TYPE = 0;
    private long id;
    private String description;
    private int sortOrder;

    public PassConfig(long id, @Nullable String description, int sortOrder) {
        this.id = id;
        this.description = description;
        this.sortOrder = sortOrder;
    }

    @Override
    public long getConfigId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public int getConfigType() {
        return TYPE;
    }

    @Override
    public void setConfigId(long id) {
        this.id = id;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setSortOrder(int index) {
        this.sortOrder = index;
    }
}
