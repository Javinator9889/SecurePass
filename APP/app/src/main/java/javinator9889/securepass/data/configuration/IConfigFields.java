package javinator9889.securepass.data.configuration;

import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public interface IConfigFields {
    long getConfigId();
    String getDescription();
    int getSortOrder();
    String getTableName();
    void setConfigId(long id);
    void setDescription(String description);
    void setSortOrder(int index);
    DatabaseTables getTableType();
    long getFieldId();
    void setFieldId(long id);
}
