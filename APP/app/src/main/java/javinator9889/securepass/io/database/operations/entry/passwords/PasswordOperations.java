package javinator9889.securepass.io.database.operations.entry.passwords;

import android.util.Log;

import androidx.annotation.Nullable;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.PasswordFields;

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
 * Created by Javinator9889 on 01/11/2018 - APP.
 */
public class PasswordOperations extends CommonOperations implements IPasswordSetOperations,
        IPasswordGetOperations {
    private static final String TAG = "Password Operations";
    private static final String TABLE_NAME = Constants.SQL.PASSWORD.NAME;
    private static final PasswordFields ID = PasswordFields.ID;
    private static final PasswordFields PASSWORD = PasswordFields.PASSWORD;
    private static final PasswordFields DESCRIPTION = PasswordFields.DESCRIPTION;
    private static final PasswordFields ORDER = PasswordFields.ORDER;
    private static final PasswordFields ENTRY = PasswordFields.ENTRY;
    private static final String WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Gets the tag for {@link Log} output - should be overridden
     *
     * @return <code>String</code> with the tag name
     */
    @Override
    public String getTag() {
        return TAG;
    }

    /**
     * Gets the WHERE ID clause for using {@link #runUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the WHERE clause - null if not defined
     */
    @Nullable
    @Override
    public String getWhereId() {
        return WHERE_ID;
    }

    /**
     * Gets the TABLE NAME for using {@link #runUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the TABLE NAME - null if not defined
     */
    @Nullable
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
