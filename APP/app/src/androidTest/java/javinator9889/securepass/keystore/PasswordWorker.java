package javinator9889.securepass.keystore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.UnrecoverableEntryException;
import java.util.Arrays;

import androidx.test.platform.app.InstrumentationRegistry;
import javinator9889.securepass.util.cipher.keystore.IPasswordCipher;
import javinator9889.securepass.util.cipher.keystore.PasswordCipher;

/**
 * Copyright © 2018 - present | APP by Javinator9889
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 * <p>
 * Created by Javinator9889 on 11/11/2018 - APP.
 */
public class PasswordWorker {
    private IPasswordCipher mCipher;

    @Before
    public void setup() throws KeyStoreException, InvalidAlgorithmParameterException {
        mCipher = new PasswordCipher(InstrumentationRegistry.getInstrumentation()
                .getTargetContext(), "test");
        mCipher.createNewKeys();
    }

    @Test
    public void test() throws UnrecoverableEntryException, InvalidKeyException {
        String password = "passwordToBeEncryptedUsingRSAAndPasswordCipher";
        System.out.println("Password: " + password);
        byte[] cipheredPassword = mCipher.encryptPassword(password);
        System.out.println("Ciphered & signed password: \n\n" + Arrays.toString(cipheredPassword));
        String recoveredPassword = mCipher.decryptPassword(cipheredPassword);
        System.out.println("Recovered password: " + recoveredPassword);
    }

    @After
    public void finalizeTest() {
        mCipher.deleteKey();
    }
}
