package javinator9889.securepass.data.configuration;

import javinator9889.securepass.util.values.DatabaseTables;

/**
 * Interface for accessing {@link ConfigFields} methods
 * Created by Javinator9889 on 21/08/2018.
 */
public interface IConfigFields {
    /**
     * Obtains current configuration ID (the same as in the database)
     *
     * @return long corresponding the ID
     */
    long getConfigId();

    /**
     * Obtains current description (the same as in the database)
     *
     * @return String corresponding the description (may be null)
     */
    String getDescription();

    /**
     * Obtains current sort order (the same as in the database)
     *
     * @return int corresponding the sort order
     */
    int getSortOrder();

    /**
     * Gets the proper table name - names available at
     * {@link javinator9889.securepass.util.values.Constants.SQL SQL names} attributes
     *
     * @return String which is the table name
     * @see javinator9889.securepass.util.values.Constants.SQL
     */
    String getTableName();

    /**
     * Sets current configuration ID (the same as in the database)
     *
     * @param id long which must exists at configurations IDs
     */
    void setConfigId(long id);

    /**
     * Sets current description (the same as in the database)
     *
     * @param description nullable String which contains the description
     */
    void setDescription(String description);

    /**
     * Sets current sort order (the same as in the database)
     *
     * @param index int which correspondents the order inside the fields objects
     */
    void setSortOrder(int index);

    /**
     * Gets the proper database type - types available at
     * {@link DatabaseTables DatabaseTables enum} types
     *
     * @return DatabaseTables object with the correspondent type
     * @see DatabaseTables
     */
    DatabaseTables getTableType();

    /**
     * Gets current field ID (the same as in the database) - cannot be changed
     *
     * @return long corresponding the ID
     */
    long getFieldId();

    /**
     * Changes field ID if there is any reason for
     *
     * @param id long corresponding the new ID
     */
    void setFieldId(long id);
}
