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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import androidx.annotation.NonNull;
import javinator9889.securepass.errors.cipher.NoPrivateKeyException;
import javinator9889.securepass.util.cipher.sign.Signature;

/**
 * TODO
 */
public class PasswordCipher implements IPasswordCipher {
    private static final String TAG = "PasswordCipher";
    private String mAlias;
    private Context mContext;
    private KeyStore mAndroidKeyStore;

    /**
     * Public "default" constructor - generates a new instance by using the provided {@link Context}
     * and the {@link #DEFAULT_ALIAS default alias} for KeyStore.
     *
     * @param context non-null context for generating and obtaining keys.
     *
     * @throws KeyStoreException when some error occurs while obtaining an instance from {@link
     *                           KeyStore#getInstance(String)} and {@link #ANDROID_KEY_STORE
     *                           AndroidKeyStore} is not available for any reason.
     */
    public PasswordCipher(@NonNull Context context) throws KeyStoreException {
        this(context, DEFAULT_ALIAS);
    }

    /**
     * Public "default" constructor - generates a new instance by using the provided {@link Context}
     * and the {@link #DEFAULT_ALIAS default alias} for KeyStore.
     *
     * @param context non-null context for generating and obtaining keys.
     * @param alias   non-null {@code String} for generating the alias for saving keys at
     *                AndroidKeyStore.
     *
     * @throws KeyStoreException when some error occurs while obtaining an instance from {@link
     *                           KeyStore#getInstance(String)} and {@link #ANDROID_KEY_STORE
     *                           AndroidKeyStore} is not available for any reason.
     * @apiNote {@link CertificateException}, {@link IOException}, {@link NoSuchAlgorithmException}
     * exceptions are ignored as here the class is only getting and instance of {@link
     * #ANDROID_KEY_STORE AndroidKeyStore}, so those exceptions are never thrown.
     */
    public PasswordCipher(@NonNull Context context, @NonNull String alias) throws
            KeyStoreException {
        mContext = context;
        mAlias = alias;
        mAndroidKeyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        try {
            mAndroidKeyStore.load(null);
        } catch (CertificateException | IOException | NoSuchAlgorithmException ignored) {
        }
    }


    /**
     * By using {@link KeyStore}, generates new keys for the specified {@code keyAlias} if it does
     * not exist on {@code AndroidKeyStore}.
     *
     * @throws InvalidAlgorithmParameterException if there was not possible to generate new keys.
     */
    @SuppressLint("WrongConstant")
    @Override
    public void createNewKeys() throws InvalidAlgorithmParameterException {
        try {
            if (mAndroidKeyStore.containsAlias(mAlias))
                return;
        } catch (KeyStoreException ignored) { // KeyStore is always initialized here
        }
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance(ALGORITHM_RSA, ANDROID_KEY_STORE);
        } catch (NoSuchAlgorithmException | NoSuchProviderException ignored) {
        }
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 5);
        X500Principal subject = new X500Principal("CN=SecurePass, O=Javinator9889");
        BigInteger serialNumber = BigInteger.valueOf(1337);
        AlgorithmParameterSpec spec = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            spec = new KeyGenParameterSpec.Builder(mAlias,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT |
                            KeyProperties.PURPOSE_SIGN |
                            KeyProperties.PURPOSE_VERIFY)
                    .setCertificateNotBefore(start.getTime())
                    .setCertificateNotAfter(end.getTime())
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB, KeyProperties.BLOCK_MODE_CBC)
                    .setCertificateSerialNumber(serialNumber)
                    .setCertificateSubject(subject)
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1,
                            KeyProperties.ENCRYPTION_PADDING_PKCS7,
                            KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1,
                            KeyProperties.SIGNATURE_PADDING_RSA_PSS)
//                    .setIsStrongBoxBacked(true) - need more testings
                    .setKeySize(RSA_KEY_SIZE)
                    .setRandomizedEncryptionRequired(true)
                    .build();
        } else {
            try {
                spec = new KeyPairGeneratorSpec.Builder(mContext)
                        .setAlias(mAlias)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .setSerialNumber(serialNumber)
                        .setKeySize(RSA_KEY_SIZE)
                        .setSubject(subject)
                        .setKeyType(ALGORITHM_RSA)
                        .build();
            } catch (NoSuchAlgorithmException ignored) {
            }
        }
        if (generator != null) {
            generator.initialize(spec);
            generator.generateKeyPair();
        }
    }

    /**
     * Removes the specified key from the {@code AndroidKeyStore} if possible.
     */
    @Override
    public void deleteKey() {
        try {
            mAndroidKeyStore.deleteEntry(mAlias);
        } catch (KeyStoreException e) {
            Log.e(TAG, String.format("Entry \"%s\" cannot be deleted", mAlias), e);
        }
    }

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
    @Override
    public byte[] encryptPassword(@NonNull byte[] passwordToEncrypt) throws InvalidKeyException,
            UnrecoverableEntryException {
        try {
            KeyStore.Entry entry = mAndroidKeyStore.getEntry(mAlias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry))
                throw new NoPrivateKeyException(String.format("There is no private key for alias: " +
                        "\"%s\"", mAlias));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, ((KeyStore.PrivateKeyEntry) entry).getCertificate());
            return cipher.doFinal(passwordToEncrypt);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException |
                IllegalBlockSizeException | KeyStoreException ignored) {
            return null;
        }
    }

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
    @Override
    public String decryptPassword(@NonNull byte[] encryptedPassword) throws InvalidKeyException,
            UnrecoverableEntryException {
        try {
            KeyStore.Entry entry = mAndroidKeyStore.getEntry(mAlias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry))
                throw new NoPrivateKeyException(String.format("There is no private key for alias: " +
                        "\"%s\"", mAlias));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, ((KeyStore.PrivateKeyEntry) entry).getPrivateKey());
            byte[] decryptedValue = cipher.doFinal(encryptedPassword);
            return new String(decryptedValue);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException |
                IllegalBlockSizeException | KeyStoreException ignored) {
            return null;
        }
    }

    /**
     * Signs a {@code String} by using the provided {@link KeyStore.PrivateKeyEntry} {@code private
     * key}.
     *
     * @param data       data to sign - cannot be null
     * @param privateKey entry which contains the private key - cannot be null
     *
     * @return a {@code byte[]} with the signed data if no error occurred. If not, returns {@code
     * null}.
     *
     * @throws InvalidKeyException if the key at the entry is invalid.
     */
    private byte[] sign(@NonNull String data, @NonNull PrivateKey privateKey)
            throws InvalidKeyException, NoSuchProviderException {
        Signature signer = new Signature();
        try {
            return signer.sign(data, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException |
                IllegalBlockSizeException e) {  // those errors should never happen
            System.err.println("EXCEPTION!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Removes the signature of the given {@code data} by using the public key.
     *
     * @param data      data where removing the sign - cannot be null.
     * @param publicKey entry which contains the {@link java.security.KeyStore
     *                  .PrivateKeyEntry#getCertificate() certificate} of the public key - cannot be
     *                  null.
     *
     * @return {@code String} with the original data before signing, null if any exception occurs.
     *
     * @throws InvalidKeyException if such the key stored at the entry or the certificate are not
     *                             valid.
     */
    private String removeSignature(@NonNull byte[] data, @NonNull PublicKey publicKey)
            throws InvalidKeyException, NoSuchProviderException {
        Signature signer = new Signature();
        try {
            return signer.removeSign(data, publicKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException |
                IllegalBlockSizeException e) {  // those errors should never happen
            System.err.println("EXCEPTION!");
            e.printStackTrace();
            return null;
        }
    }
}
