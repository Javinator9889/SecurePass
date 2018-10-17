package javinator9889.securepass.data.configuration;

import androidx.annotation.Nullable;

import java.io.Serializable;

import javinator9889.securepass.util.values.Constants.SQL.SMALL_TEXT_CONFIG;
import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class SmallTextConfig extends ConfigFields {
    private static final String TABLE_NAME = SMALL_TEXT_CONFIG.NAME;
    private static final DatabaseTables TABLE_TYPE = DatabaseTables.SMALL_TEXT;

    public SmallTextConfig(long id, @Nullable String description, int sortOrder,
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
