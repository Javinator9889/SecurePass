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
 * Created by Javinator9889 on 29/01/2019 - SecurePass.
 */
package javinator9889.securepass.database;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.securitycode.ISecurityCodeGetOperations;
import javinator9889.securepass.io.database.operations.securitycode.ISecurityCodeSetOperations;
import javinator9889.securepass.io.database.operations.securitycode.SecurityCodeOperations;
import javinator9889.securepass.io.database.operations.securitycode.field.FieldOperations;
import javinator9889.securepass.io.database.operations.securitycode.field.IFieldGetOperations;
import javinator9889.securepass.io.database.operations.securitycode.field.IFieldSetOperations;
import javinator9889.securepass.objects.ObjectContainer;

import static java.lang.Thread.sleep;


public class TestSecurityCodeOperations extends DatabaseTests {
    private static final String TAG = "TestSecurityCodeOperations";
    private DatabaseManager mInstance;
    private SecurityCodeOperations mOperations;
    private FieldOperations mFields;
    private long mId;
    private ISecurityCodeSetOperations mSetOperations;
    private ISecurityCodeGetOperations mGetOperations;
    private IFieldSetOperations mFSetOperations;
    private IFieldGetOperations mFGetOperations;

    @Before
    public void preload() {
        mInstance = super.generateInstance(PASSWORD);
        mOperations = new SecurityCodeOperations(mInstance);
        mSetOperations = mOperations;
        mGetOperations = mOperations;
        mFields = new FieldOperations(mInstance);
        mFSetOperations = mFields;
        mFGetOperations = mFields;
    }

    @Test
    public void registerSecurityCode() {
        mId = mSetOperations.registerNewSecurityCode("testSecurityCode");
        Log.i(TAG, "Recently created SecurityCode ID: " + mId);
        getAllSecurityCodes();
    }

    @Test
    public void registerSecurityCodeFields() throws Exception {
        registerSecurityCode();
        int fieldsToCreate = (int) (Math.random() * 95);
        Log.i(TAG, "Creating " + fieldsToCreate + " fields");
        ObjectContainer<Long> ids = new ObjectContainer<>(fieldsToCreate);
        for (int i = 0; i < fieldsToCreate; i++) {
            long id = mFSetOperations.registerNewField(mId, "field#" + i, false);
            ids.storeObject(id);
        }
        Log.d(TAG, ids.toString());
        getAllFields();
        ids.forEach(id -> {
            mFSetOperations.updateFieldCode(id, "updatedField#" + id);
            mFSetOperations.updateFieldCodeBeenUsed(id, (Math.random() > Math.random()));
        });
        mFSetOperations.apply();
        sleep(1000);
        getAllFields();
        ids.forEach(id -> mFSetOperations.removeField(id));
        getAllFields();
    }

    @After
    public void finish() {
        mOperations.finishConnection();
        mFields.finishConnection();
    }

    private void getAllSecurityCodes() {
        Log.i(TAG, mGetOperations.getAllSecurityCodes().toString());
    }

    private void getAllFields() {
        Log.i(TAG, mFGetOperations.getAllFields().toString());
    }
}
