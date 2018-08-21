package javinator9889.securepass.data.configuration;

import javinator9889.securepass.objects.GeneralObjectContainer;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public interface IConfiguration {
    long getConfigurationId();
    String getConfigurationName();
    GeneralObjectContainer<IConfigFields> getConfigFields();
    void setConfigurationId(long id);
    void setConfigurationName(String name);
    void addConfigurationField(IConfigFields field);
}
