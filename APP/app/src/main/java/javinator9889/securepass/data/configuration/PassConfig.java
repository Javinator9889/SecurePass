package javinator9889.securepass.data.configuration;

import androidx.annotation.Nullable;

import java.io.Serializable;

import javinator9889.securepass.util.values.Constants.SQL.PASS_CONFIG;
import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class PassConfig extends ConfigFields {
    private static final String TABLE_NAME = PASS_CONFIG.NAME;
    private static final DatabaseTables TABLE_TYPE =
            DatabaseTables.PASS_CONFIG;

    public PassConfig(long id, @Nullable String description, int sortOrder,
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
