package javinator9889.securepass.database;

import android.util.Log;

import net.sqlcipher.database.SQLiteException;

import org.junit.Test;

import java.util.Random;

import javinator9889.securepass.io.database.operations.CommonOperations;

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
public class BasicOperations extends DatabaseTests {
    private CommonOperations mOperations;
    private String mPassword;
    private String mTag;
//    private BasicOperations mInstance;

    public BasicOperations() {
        super();
        mPassword = String.valueOf(new Random().nextInt(123456789));
        mOperations = generateOperations(mPassword);
        mTag = mOperations.getTag();
        Log.i(mTag, "Password: " + mPassword);
    }

//    @Before
//    public void setup() {
//        mInstance = new BasicOperations();
//    }

    @Test
    public void test() {
        BasicOperations mInstance = new BasicOperations();
        try {
            mInstance.changePassword();
            long id = mInstance.registerDefaultCategory();
            System.out.println("ID: " + id);
        } catch (SQLiteException e) {
            System.err.println("Error occurred");
            e.printStackTrace();
        } finally {
            mInstance.finish();
        }
    }

    public void changePassword() {
        mPassword = String.valueOf(new Random().nextInt(123456789));
        mOperations.changeDatabasePassword(mPassword);
        Log.i(mTag, "New password: " + mPassword);
    }

    public long registerDefaultCategory() {
        return mOperations.registerDefaultCategory();
    }

    public void finish() {
        mOperations.finishConnection();
    }

//    @After
//    public void end() {
//        mInstance.finish();
//    }
}
