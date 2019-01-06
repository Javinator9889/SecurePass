package javinator9889.securepass.data.configuration;

import androidx.annotation.Nullable;

import javinator9889.securepass.util.values.Constants.SQL.LONG_TEXT_CONFIG;
import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Created by Javinator9889 on 21/08/2018.
 */
public class LongTextConfig extends ConfigFields {
    private static final String TABLE_NAME = LONG_TEXT_CONFIG.NAME;
    private static final DatabaseTables TABLE_TYPE =
            DatabaseTables.LONG_TEXT_CONFIG;

    /**
     * Constructor that uses
     * {@link ConfigFields#ConfigFields(long, String, int, long) ConfigFileds constructor} (only
     * visible for this classes) - generates LongTextConfig instance
     *
     * @param id          config field id
     * @param description optional description
     * @param sortOrder   fields sort order
     * @param configId    configuration id
     * @see ConfigFields
     * @see IConfigFields
     */
    public LongTextConfig(long id, @Nullable String description, int sortOrder,
                          long configId) {
        super(id, description, sortOrder, configId);
    }

    /**
     * Gets the proper table name - names available at
     * {@link javinator9889.securepass.util.values.Constants.SQL SQL names} attributes
     *
     * @return String which is the table name
     * @see javinator9889.securepass.util.values.Constants.SQL
     */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Gets the proper database type - types available at
     * {@link DatabaseTables DatabaseTables enum} types
     *
     * @return DatabaseTables object with the correspondent type
     * @see DatabaseTables
     */
    @Override
    public DatabaseTables getTableType() {
        return TABLE_TYPE;
    }
}
