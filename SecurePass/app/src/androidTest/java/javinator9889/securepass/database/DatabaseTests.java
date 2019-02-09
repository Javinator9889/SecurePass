package javinator9889.securepass.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.util.values.Constants;

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
 * Created by Javinator9889 on 05/11/2018 - APP.
 */
public abstract class DatabaseTests {
    public static final String PASSWORD = "password";
    private Context mContext;

    DatabaseTests() {
        this(true);
    }

    protected DatabaseTests(boolean removePrevious) {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        if (removePrevious)
            removePreviousDB();
    }

    private void removePreviousDB() {
        mContext.getDatabasePath(Constants.SQL.DB_FILENAME).delete();
    }

    public Context getContext() {
        return mContext;
    }

    public CommonOperations generateOperations(@NonNull String password) {
        DatabaseManager instance = DatabaseManager.getInstance(mContext, password);
        return new CommonOperations(instance);
    }

    public DatabaseManager generateInstance(@NonNull String password) {
        return DatabaseManager.getInstance(mContext, password);
    }
}
