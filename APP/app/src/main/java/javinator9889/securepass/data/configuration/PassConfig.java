package javinator9889.securepass.data.configuration;

import androidx.annotation.Nullable;

import java.io.Serializable;

import javinator9889.securepass.util.values.Constants.SQL.PASS_CONFIG;
import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class PassConfig implements IConfigFields, Serializable {
    private static final String TABLE_NAME = PASS_CONFIG.NAME;
    private static final DatabaseTables TABLE_TYPE = DatabaseTables.PASS_CONFIG;
    private long id;
    private String description;
    private int sortOrder;
    private long configId;

    public PassConfig(long id, @Nullable String description, int sortOrder, long configId) {
        this.id = id;
        this.description = description;
        this.sortOrder = sortOrder;
        this.configId = configId;
    }

    @Override
    public long getConfigId() {
        return configId;
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
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void setConfigId(long id) {
        this.configId = id;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setSortOrder(int index) {
        this.sortOrder = index;
    }

    @Override
    public DatabaseTables getTableType() {
        return TABLE_TYPE;
    }

    @Override
    public long getFieldId() {
        return id;
    }

    @Override
    public void setFieldId(long id) {
        this.id = id;
    }
}
