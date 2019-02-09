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
 * Created by Javinator9889 on 05/02/2019 - SecurePass.
 */
package javinator9889.securepass.database;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.entry.qrcode.IQRCodeGetOperations;
import javinator9889.securepass.io.database.operations.entry.qrcode.IQRCodeSetOperations;
import javinator9889.securepass.io.database.operations.entry.qrcode.QRCodeOperations;
import javinator9889.securepass.objects.ObjectContainer;

import static java.lang.Thread.sleep;

public class TestQRCodeOperations extends TestEntryOperations {
    private static final String TAG = "TestQRCodeOperations";
    private DatabaseManager mInstance;
    private QRCodeOperations mOperations;
    private IQRCodeSetOperations mSetOperations;
    private IQRCodeGetOperations mGetOperations;

    @Before
    public void preload() {
        super.preload();
        mInstance = generateInstance(PASSWORD);
        mOperations = new QRCodeOperations(mInstance);
        mSetOperations = mOperations;
        mGetOperations = mOperations;
    }

    @Test
    public void registerQRCode() {
        long id = mSetOperations.registerNewQRCode(getEntryId(),
                "QRCodeName",
                "QRDescription",
                "QRCodeData");
        Log.i(TAG, "Created ID: " + id);
        getAll();
    }

    @Test
    public void registerQRCodes() throws Exception {
        int qrCodesToCreate = (int) (Math.random() * 95);
        Log.i(TAG, "Creating " + qrCodesToCreate + " QRCodes");
        ObjectContainer<Long> ids = new ObjectContainer<>(qrCodesToCreate);
        for (int i = 0; i < qrCodesToCreate; i++) {
            long id = mSetOperations.registerNewQRCode(getEntryId(),
                    "QRCode#" + i,
                    "QRCode " + i + " description",
                    String.valueOf(Math.random() * 123456789));
            ids.storeObject(id);
        }
        getAll();
        ids.forEach(id -> {
            mSetOperations.updateDescription(id, "NewQRCodeDescription");
            mSetOperations.updateName(id, "NewQRCodeName#" + id);
            mSetOperations.updateQrCodeData(id, String.valueOf(Math.random() * 123987456));
        });
        mSetOperations.apply();
        sleep(1000);
        getAll();
        ids.forEach(id -> mSetOperations.removeQRCode(id));
        getAll();
    }

    @Override
    @After
    public void finish() {
        super.finish();
        mOperations.finishConnection();
    }

    private void getAll() {
        Log.d(TAG, mGetOperations.getAllQRCodes().toString());
    }
}
