package javinator9889.securepass.util.values.database;

/**
 * Copyright © 2018 - present | APP by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 30/10/2018 - APP.
 */
public enum LongTextFields implements FieldsOperations {
    ID("idLongText", 0),
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
    LongTextFields(String fieldName, int fieldIndex) {
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
