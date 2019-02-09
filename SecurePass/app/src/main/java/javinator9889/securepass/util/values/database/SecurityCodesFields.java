/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 03/11/2018 - SecurePass.
 */
package javinator9889.securepass.util.values.database;

/**
 * TODO
 */
public enum SecurityCodesFields implements FieldsOperations {
    ID("idSecurityCodes", 0),
    NAME("accountName", 1);

    private String mFieldName;
    private int mFieldIndex;

    /**
     * Private constructor that sets the table field name and index order
     *
     * @param fieldName  name for identifying the column in the database
     * @param fieldIndex column index
     */
    SecurityCodesFields(String fieldName, int fieldIndex) {
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
