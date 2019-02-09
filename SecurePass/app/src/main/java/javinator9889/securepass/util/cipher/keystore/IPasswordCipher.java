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
package javinator9889.securepass.util.cipher.keystore;

import android.content.Context;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.UnrecoverableEntryException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * TODO
 */
public interface IPasswordCipher {
    /**
     * Default alias used when no alias is specified at {@link PasswordCipher#PasswordCipher(Context)}.
     */
    String DEFAULT_ALIAS = "SecurePassKeys";

    /**
     * Android KeyStore type for {@link java.security.KeyStore#getInstance(String) KeyStore
     * constructor}.
     */
    String ANDROID_KEY_STORE = "AndroidKeyStore";

    /**
     * Algorithm for generating RSA keys for AndroidKeyStore.
     *
     * @since Android API 18
     */
    @RequiresApi(18)
    String ALGORITHM_RSA = "RSA";

    /**
     * The key size of each RSA key pair.
     */
    int RSA_KEY_SIZE = 4096;

    /**
     * By using {@link java.security.KeyStore}, generates new keys for the specified {@code
     * keyAlias} if it does not exist on {@code AndroidKeyStore}.
     *
     * @throws InvalidAlgorithmParameterException if there was not possible to generate new keys.
     */
    void createNewKeys() throws InvalidAlgorithmParameterException;

    /**
     * Removes the specified key from the {@code AndroidKeyStore} if possible.
     */
    void deleteKey();

    /**
     * By using the keys stored at {@code AndroidKeyStore}, it encrypts the specified {@code
     * password}, returning the encrypted value as {@code byte[]} for directly saving it into a
     * file.
     *
     * @param passwordToEncrypt non-null {@code byte[]} with the password value to encrypt.
     *
     * @return {@code byte[]} with the encrypted password.
     *
     * @throws InvalidKeyException         if the key at the entry is invalid.
     * @throws UnrecoverableEntryException if the specified protParam were insufficient or invalid.
     */
    byte[] encryptPassword(@NonNull byte[] passwordToEncrypt) throws InvalidKeyException,
            UnrecoverableEntryException;

    /**
     * By using the keys stored at {@code AndroidKeyStore}, it decrypts the specified {@code
     * password}, returning the decrypted value as {@code String} for directly using it.
     *
     * @param encryptedPassword non-null {@code byte[]} with the password value to decrypt.
     *
     * @return {@code String} with the decrypted password.
     *
     * @throws InvalidKeyException         if the key at the entry is invalid.
     * @throws UnrecoverableEntryException if the specified protParam were insufficient or invalid.
     */
    String decryptPassword(@NonNull byte[] encryptedPassword) throws InvalidKeyException,
            UnrecoverableEntryException;
}
