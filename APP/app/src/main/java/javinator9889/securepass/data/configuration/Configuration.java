package javinator9889.securepass.data.configuration;

import android.support.annotation.Nullable;

import java.io.Serializable;

import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class Configuration implements IConfiguration, Serializable {
    private long id;
    private String configurationName;
    private GeneralObjectContainer<IConfigFields> container;

    public Configuration(long id, @Nullable String configurationName,
                         @Nullable GeneralObjectContainer<IConfigFields> container) {
        this.id = id;
        this.configurationName = configurationName;
        this.container = (container == null) ? new ObjectContainer<>() : container;
    }

    @Override
    public long getConfigurationId() {
        return id;
    }

    @Override
    public String getConfigurationName() {
        return configurationName;
    }

    @Override
    public GeneralObjectContainer<IConfigFields> getConfigFields() {
        return container;
    }

    @Override
    public void setConfigurationId(long id) {
        this.id = id;
    }

    @Override
    public void setConfigurationName(String name) {
        this.configurationName = name;
    }

    @Override
    public void addConfigurationField(IConfigFields field) {
        int position = field.getSortOrder();
        container.storeObject(field, position);
    }
}
