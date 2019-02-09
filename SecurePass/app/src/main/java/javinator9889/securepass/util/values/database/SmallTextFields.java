package javinator9889.securepass.util.values.database;

/**
 * A list of the parameters for the SmallText.
 */
public enum SmallTextFields implements FieldsOperations {
    ID("idSmallText", 0),
    TEXT("text", 1),
    DESCRIPTION("field_desc", 2),
    ORDER("sortOrder", 3),
    ENTRY("idEntry", 4);

    private String mFieldName;
    private int mFieldIndex;

    /**
     * Private constructor that sets the table field name and index order
     *
     * @param fieldName  name for identifying the column in the database
     * @param fieldIndex column index
     */
    SmallTextFields(String fieldName, int fieldIndex) {
        mFieldName = fieldName;
        mFieldIndex = fieldIndex;
    }

    /**
     * Obtains current field name
     *
     * @return {@code String} with the column identifier
     */
    @Override
    public String getFieldName() {
        return mFieldName;
    }

    /**
     * Obtains current field index
     *
     * @return {@code int} with the column index
     */
    @Override
    public int getFieldIndex() {
        return mFieldIndex;
    }
}
