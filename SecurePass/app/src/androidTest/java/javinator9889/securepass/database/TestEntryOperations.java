/*
 * Copyright © 2019 - present | SecurePass by Javinator9889
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
 * Created by Javinator9889 on 25/01/2019 - SecurePass.
 */
package javinator9889.securepass.database;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.entry.EntryOperations;
import javinator9889.securepass.io.database.operations.entry.IEntryGetOperations;
import javinator9889.securepass.io.database.operations.entry.IEntrySetOperations;
import javinator9889.securepass.io.database.operations.entry.image.ImageOperations;
import javinator9889.securepass.io.database.operations.entry.password.PasswordOperations;
import javinator9889.securepass.io.database.operations.entry.text.longtext.LongTextOperations;
import javinator9889.securepass.io.database.operations.entry.text.smalltext.SmallTextOperations;

public class TestEntryOperations extends TestConfigOperations {
    private static final String TAG = "TestEntryOperations";
    private DatabaseManager mInstance;
    private EntryOperations mOperations;
    private IEntryGetOperations mGetOperations;
    private IEntrySetOperations mSetOperations;
    private long mEntryId;

    @Before
    @Override
    public void preload() {
        mInstance = super.generateInstance(PASSWORD);
        mOperations = new EntryOperations(mInstance);
        mGetOperations = mOperations;
        mSetOperations = mOperations;
        super.preload();
    }

    @Test
    public void registerEntry() {
        super.registerConfiguration();
        Log.i(TAG, "Configuration ID: " + (mConfigurationSuffix - 1));
        Log.i(TAG, "Category ID: (default) 0");
        long id = mSetOperations.registerNewEntry(0, (mConfigurationSuffix - 1), "First entry",
                "Entry icon");
        mEntryId = id;
        Log.i(TAG, "Recently created entry ID: " + id);
        getAll();
    }

    @Test
    public void registerEntryFields() {
        registerEntry();
        PasswordOperations password = new PasswordOperations(mInstance);
        LongTextOperations longText = new LongTextOperations(mInstance);
        SmallTextOperations smallText = new SmallTextOperations(mInstance);
        ImageOperations image = new ImageOperations(mInstance);
        int fieldsToCreate = (int) (Math.random() * 100);
        Log.i(TAG, "Creating " + fieldsToCreate + " fields");
        for (int i = 0; i < fieldsToCreate; i++) {
            password.registerNewPassword("password" + i, "Password #" + i, i, mEntryId);
            longText.registerNewText("long text " + i, "Text #" + i, i, mEntryId);
            smallText.registerNewText("small text " + i, "SText #" + i, i, mEntryId);
            image.registerNewImage("image " + i, "Image #" + i, i, mEntryId);
        }
        Log.i(TAG, password.getAllPasswords().toString());
        Log.i(TAG, longText.getAllLongTexts().toString());
        Log.i(TAG, smallText.getAllSmallTexts().toString());
        Log.i(TAG, image.getAllImages().toString());
    }

    @After
    public void finish() {
        mOperations.finishConnection();
    }

    protected long getEntryId() {
        return mEntryId;
    }

    private void getAll() {
        Log.i(TAG, mGetOperations.getAllEntries().toString());
    }
}
