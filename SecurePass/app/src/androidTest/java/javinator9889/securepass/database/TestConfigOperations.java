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
 * Created by Javinator9889 on 23/01/2019 - SecurePass.
 */
package javinator9889.securepass.database;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.configuration.ConfigurationOperations;
import javinator9889.securepass.io.database.operations.configuration.IConfigurationGetOperations;
import javinator9889.securepass.io.database.operations.configuration.IConfigurationSetOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.IConfigFieldsSetOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.imagesconfig.ImagesConfigOperations;

import static java.lang.Thread.sleep;

public class TestConfigOperations extends DatabaseTests {
    private IConfigurationGetOperations mGetOperations;
    private IConfigurationSetOperations mSetOperations;
    private ConfigurationOperations mOperations;
    protected int mConfigurationSuffix = 1;
    protected DatabaseManager mInstance;
    private static final String TAG = "ConfigurationOperations";

    @Before
    public void preload() {
        mInstance = super.generateInstance("password");
        mOperations = new ConfigurationOperations(mInstance);
        mSetOperations = mOperations;
        mGetOperations = mOperations;
    }

    @Test
    public void registerConfiguration() {
        long id = mSetOperations.registerNewConfiguration("configuration" + mConfigurationSuffix);
        Log.i(TAG, "New configuration: " + id);
        getAll();
        mConfigurationSuffix = ((int) id) + 1;
        Log.i(TAG, "Recently created configuration name: " + getConfigurationNameById(id));
//        IConfigFieldsSetOperations image = new ImagesConfigOperations(mInstance);
//        image.registerNewConfigField("image description", 1, id);
//        Log.i(TAG, ((ImagesConfigOperations) image).getAllConfigFields().toString());
    }

    @Test
    public void changeConfigurationName() throws InterruptedException {
        registerConfiguration();
        long id = mConfigurationSuffix - 1;
        String name = "updatedConfiguration" + id;
        mSetOperations.updateConfigurationName(id, name);
        mSetOperations.apply();
        sleep(1000);
        Log.i(TAG, "Category new name: " + getConfigurationNameById(id));
        getAll();
    }

    @Test
    public void removeConfiguration() {
        registerConfiguration();
        long id = mConfigurationSuffix - 1;
        Log.i(TAG, "Deleting ID: " + id);
        mSetOperations.removeConfiguration(id);
        getAll();
    }

    @After
    public void finish() {
        mOperations.finishConnection();
    }

    private String getConfigurationNameById(long id) {
        return mGetOperations.getConfigurationName(id);
    }

    private void getAll() {
        Log.d(TAG, mGetOperations.getAllConfigurations().toString());
    }
}
