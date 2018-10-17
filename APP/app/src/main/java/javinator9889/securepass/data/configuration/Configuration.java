package javinator9889.securepass.data.configuration;

import androidx.annotation.Nullable;

import java.io.Serializable;

import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class Configuration implements IConfiguration, Serializable {
    private long mId;
    private String mConfigurationName;
    private GeneralObjectContainer<IConfigFields> mContainer;

    public Configuration(long id,
                         @Nullable String configurationName,
                         @Nullable GeneralObjectContainer<IConfigFields>
                                 container) {
        this.mId = id;
        this.mConfigurationName = configurationName;
        this.mContainer = (container == null) ?
                new ObjectContainer<>() :
                container;
    }

    @Override
    public long getConfigurationId() {
        return mId;
    }

    @Override
    public String getConfigurationName() {
        return mConfigurationName;
    }

    @Override
    public GeneralObjectContainer<IConfigFields> getConfigFields() {
        return mContainer;
    }

    @Override
    public void setConfigurationId(long id) {
        this.mId = id;
    }

    @Override
    public void setConfigurationName(String name) {
        this.mConfigurationName = name;
    }

    @Override
    public void addConfigurationField(IConfigFields field) {
        int position = field.getSortOrder();
        mContainer.storeObject(field, position);
    }
}
