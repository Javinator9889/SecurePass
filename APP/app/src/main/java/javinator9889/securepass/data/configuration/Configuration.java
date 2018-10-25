package javinator9889.securepass.data.configuration;

import java.io.Serializable;

import androidx.annotation.Nullable;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Class that contains configurations available at class level
 * Created by Javinator9889 on 21/08/2018.
 */
public class Configuration implements IConfiguration, Serializable {
    private long mId;
    private String mConfigurationName;
    private GeneralObjectContainer<IConfigFields> mContainer;

    /**
     * Public available constructor
     *
     * @param id                configuration ID (same as in database)
     * @param configurationName nullable String which has a configuration name
     * @param container         nullable container for storing {@link IConfigFields}
     */
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

    /**
     * Obtains current configuration ID (same as in database)
     *
     * @return long corresponding the current ID
     */
    @Override
    public long getConfigurationId() {
        return mId;
    }

    /**
     * Obtains current configuration name (same as in database) - can be null
     *
     * @return String with the name
     */
    @Override
    @Nullable
    public String getConfigurationName() {
        return mConfigurationName;
    }

    /**
     * Obtains current configuration fields (same as in database)
     *
     * @return {@link GeneralObjectContainer} of {@link IConfigFields}
     * @see ObjectContainer
     */
    @Override
    public GeneralObjectContainer<IConfigFields> getConfigFields() {
        return mContainer;
    }

    /**
     * Changes configuration ID if needed (it should not be changed)
     *
     * @param id long with the new configuration ID
     */
    @Override
    public void setConfigurationId(long id) {
        this.mId = id;
    }

    /**
     * Changes current configuration name (can be null)
     *
     * @param name the new name (or null for no one)
     */
    @Override
    public void setConfigurationName(@Nullable String name) {
        this.mConfigurationName = name;
    }

    /**
     * Adds a new {@link IConfigFields configuration} by using the
     * {@link IConfigFields#getSortOrder() field sort order}
     *
     * @param field new configuration to store
     * @see ConfigFields
     */
    @Override
    public void addConfigurationField(IConfigFields field) {
        int position = field.getSortOrder();
        mContainer.storeObject(field, position);
    }
}
