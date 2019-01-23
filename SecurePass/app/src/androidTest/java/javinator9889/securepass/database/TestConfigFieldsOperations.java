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

import org.junit.Test;

import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.IConfigFieldsGetOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.IConfigFieldsSetOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.imagesconfig.ImagesConfigOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.longtextconfig.LongTextConfigOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.passconfig.PassConfigOperations;
import javinator9889.securepass.io.database.operations.configuration.configurationfields.smalltextconfig.SmallTextConfigOperations;

public class TestConfigFieldsOperations extends TestConfigOperations {
    private static final String TAG = "ConfigFieldsOperations";

    @Test
    public void createFields() {
        super.registerConfiguration();
//        DatabaseManager instance = super.generateInstance("password");
        Log.i(TAG, "Configuration ID: " + (mConfigurationSuffix - 1));
        register((int) (Math.random() * 100), mInstance, mConfigurationSuffix - 1);
        showAll(mInstance);
    }

    private void register(int amount, DatabaseManager instance, long id) {
        IConfigFieldsSetOperations imageSetOperations = new ImagesConfigOperations(instance);
        IConfigFieldsSetOperations passSetOperations = new PassConfigOperations(instance);
        IConfigFieldsSetOperations smallTextOperations = new SmallTextConfigOperations(instance);
        IConfigFieldsSetOperations longTextOperations = new LongTextConfigOperations(instance);

        registerFields(imageSetOperations, amount, id, "image_");
        registerFields(passSetOperations, amount, id, "password_");
        registerFields(smallTextOperations, amount, id, "smalltext_");
        registerFields(longTextOperations, amount, id, "longtext_");
    }

    private void showAll(DatabaseManager instance) {
        IConfigFieldsGetOperations getOperations = new ImagesConfigOperations(instance);
        Log.i(TAG, getOperations.getAllConfigFields().toString());
        getOperations = new PassConfigOperations(instance);
        Log.i(TAG, getOperations.getAllConfigFields().toString());
        getOperations = new SmallTextConfigOperations(instance);
        Log.i(TAG, getOperations.getAllConfigFields().toString());
        getOperations = new LongTextConfigOperations(instance);
        Log.i(TAG, getOperations.getAllConfigFields().toString());
    }

    private void registerFields(IConfigFieldsSetOperations field,
                                int amount,
                                long configId,
                                String prefix) {
        for (int i = 1; i <= amount; i++) {
            field.registerNewConfigField(prefix + i, i, configId);
        }
    }
}
