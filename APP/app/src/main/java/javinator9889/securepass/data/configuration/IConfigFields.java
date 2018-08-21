package javinator9889.securepass.data.configuration;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public interface IConfigFields {
    long getConfigId();
    String getDescription();
    int getSortOrder();
    int getConfigType();
    void setConfigId(long id);
    void setDescription(String description);
    void setSortOrder(int index);
}
