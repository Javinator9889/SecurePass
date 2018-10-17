package javinator9889.securepass.data.configuration;

import java.io.Serializable;

import androidx.annotation.Nullable;
import javinator9889.securepass.util.values.Constants.SQL.IMAGES_CONFIG;
import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class ImagesConfig extends ConfigFields {
    private static final String TABLE_NAME = IMAGES_CONFIG.NAME;
    private static final DatabaseTables TABLE_TYPE =
            DatabaseTables.IMAGES_CONFIG;

    public ImagesConfig(long id, @Nullable String description, int sortOrder,
                        long configId) {
        super(id, description, sortOrder, configId);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public DatabaseTables getTableType() {
        return TABLE_TYPE;
    }
}
