/*
 * Copyright © 2018 - present | APP by Javinator9889

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.

 * Created by Javinator9889 on 26/11/2018 - APP.
 */
package javinator9889.securepass.util.cipher.database;

import android.content.ContentValues;
import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.NonNull;
import at.favre.lib.crypto.HKDF;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.util.scrypt.Scrypt;

/**
 * Class for encrypting database files by using AES algorithm with the latest security options.
 */
public class DatabaseCipher {
    private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private Context mContext;

    /**
     * Public constructor - context is needed for accessing program files.
     *
     * @param context application context - cannot be {@code null}.
     */
    public DatabaseCipher(@NonNull Context context) {
        mContext = context;
    }

    /**
     * By reading database file as {@code bytes} (by using {@link IOManager#readDatabaseFileAsBytes()}),
     * encrypts it as follows:
     * <ul>
     * <li>
     * Obtains the saved password from file ({@link IOManager#recoverPassword()}).
     * </li>
     * <li>
     * Recovers the key from the derived password ({@link Scrypt#getKey(String)}).
     * </li>
     * <li>
     * Generates a randomly secure IV vector ({@link #generateSecure(SecureRandom, int)}).
     * </li>
     * <li>
     * Generates both authentication and encryption keys from the obtained key ({@link
     * #generateKey(byte[], String, int)}).
     * </li>
     * <li>
     * Reads the entire database as {@code byte} ({@link IOManager#readDatabaseFileAsBytes()}).
     * </li>
     * <li>
     * Generates both {@linkplain GCMParameterSpec} and {@linkplain Cipher} with the key length (256
     * bits), the IV vector and the {@linkplain #CIPHER_ALGORITHM cipher algorithm}.
     * </li>
     * <li>
     * Encrypts the database and generates a MAC authentication ({@link #generateMac(byte[], byte[],
     * byte[])}).
     * </li>
     * <li>
     * Combines all generated params, cleans the variables and returns the result. If for any reason
     * {@code mac} is {@code null}, returns immediately nothing ({@code null}).
     * </li>
     * </ul>
     *
     * @return {@code byte[]} with the encrypted database. If any error while processing MAC,
     * returns {@code null}.
     *
     * @throws NoSuchAlgorithmException           if the Android Provider does not support the
     *                                            encryption algorithm.
     * @throws NoSuchPaddingException             if the padding is not valid (it should not be
     *                                            thrown as we are not using any padding).
     * @throws BadPaddingException                if the padding is not valid (can be thrown if the
     *                                            algorithm is not valid with the specified padding
     *                                            - {@code null}).
     * @throws IllegalBlockSizeException          if the data to be processed is not a multiple of
     *                                            the block size.
     * @throws InvalidAlgorithmParameterException if the generated arguments are not valid for this
     *                                            algorithm encryption.
     * @throws InvalidKeyException                if the key size is inappropriate.
     */
    public byte[] encryptDatabase() throws NoSuchAlgorithmException, NoSuchPaddingException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        IOManager io = IOManager.newInstance(mContext);
        SecureRandom random = new SecureRandom();
        byte[] hashedPass = io.recoverPassword();
        byte[] key = Scrypt.getKey(new String(hashedPass));
        byte[] iv = generateSecure(random, 12);
        byte[] authKey = generateKey(key, "authKey", 32);
        byte[] encKey = generateKey(key, "encKey", key.length);
        byte[] databaseData = io.readDatabaseFileAsBytes();
        GCMParameterSpec parameterSpec = new GCMParameterSpec(hashedPass.length * 8, iv);
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encKey, "AES"), parameterSpec);
        byte[] cipheredDatabase = cipher.doFinal(databaseData);
        byte[] mac = generateMac(authKey, iv, cipheredDatabase);
        if (mac != null) {
            byte[] fullyEncryptedDatabase = combineAll(iv, mac, cipheredDatabase);
            Arrays.fill(encKey, (byte) 0);
            Arrays.fill(authKey, (byte) 0);
            return fullyEncryptedDatabase;
        } else return null;
    }

    /**
     * By wrapping the source using {@link ByteBuffer}, recovers IV vector, MAC authentication and
     * database encrypted value, returning them as a {@link ContentValues}.
     *
     * @param source the source where IV, MAC and database was stored.
     *
     * @return {@code ContentValues} with the obtained values.
     *
     * @throws IllegalArgumentException if the IV length is not valid ({@code != 16}) or the MAC
     *                                  length is not valid ({@code !=32}).
     */
    public ContentValues getEncryptedDatabase(@NonNull byte[] source) {
        ByteBuffer buffer = ByteBuffer.wrap(source);
        int ivLength = (int) buffer.get();
        if (ivLength != 16)
            throw new IllegalArgumentException("Invalid IV length");
        byte[] iv = new byte[ivLength];
        buffer.get(iv);
        int macLength = (int) buffer.get();
        if (macLength != 32)
            throw new IllegalArgumentException("Invalid MAC length");
        byte[] mac = new byte[macLength];
        buffer.get(mac);
        byte[] cipherText = new byte[buffer.remaining()];
        buffer.get(cipherText);
        ContentValues result = new ContentValues(3);
        result.put("IV", iv);
        result.put("MAC", mac);
        result.put("TEXT", cipherText);
        return result;
    }

    /**
     * By using the {@code ContentValues} with the needed params and the {@code key}, decrypts the
     * database when possible:
     * <ul>
     * <li>
     * Obtains the IV vector, encrypted source and MAC from {@code ContentValues (params)}.
     * </li>
     * <li>
     * Generates both encryption and authentication keys from the provided key ({@link
     * #generateKey(byte[], String, int)}).
     * </li>
     * <li>
     * Generates the MAC for authenticating the database source ({@link #generateMac(byte[], byte[],
     * byte[])}).
     * </li>
     * <li>
     * Tries to decrypt the database by using the provided params.
     * </li>
     * </ul>
     *
     * @param params {@code ContentValues} generated by calling {@linkplain
     *               #getEncryptedDatabase(byte[])}.
     * @param key    the key used for decrypting.
     *
     * @return {@code byte[]} with the decrypted database.
     *
     * @throws NoSuchAlgorithmException           if the Android Provider does not support the
     *                                            encryption algorithm.
     * @throws NoSuchPaddingException             if the padding is not valid (it should not be
     *                                            thrown as we are not using any padding).
     * @throws BadPaddingException                if the padding is not valid (can be thrown if the
     *                                            algorithm is not valid with the specified padding
     *                                            - {@code null}).
     * @throws IllegalBlockSizeException          if the data to be processed is not a multiple of
     *                                            the block size.
     * @throws InvalidAlgorithmParameterException if the generated arguments are not valid for this
     *                                            algorithm encryption.
     * @throws InvalidKeyException                if the key size is inappropriate.
     * @throws SecurityException                  when the MACs are not the same, so the database
     *                                            cannot be authenticated.
     */
    public byte[] decryptDatabase(@NonNull ContentValues params,
                                  @NonNull byte[] key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        byte[] iv = params.getAsByteArray("IV");
        byte[] encryptedSource = params.getAsByteArray("TEXT");
        byte[] mac = params.getAsByteArray("MAC");
        byte[] encKey = generateKey(key, "encKey", key.length);
        byte[] macKey = generateKey(key, "authKey", 32);
        byte[] refMac = generateMac(macKey, iv, encryptedSource);
        if (!MessageDigest.isEqual(refMac, mac))
            throw new SecurityException("Cannot authenticate database");
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encKey, "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(encryptedSource);
    }

    /**
     * Generates a secure {@code byte[]} with the provided params.
     *
     * @param secureRandom source secure seed.
     * @param length       the length used for creating the {@code byte[]} result.
     *
     * @return {@code byte[]} with the new secure.
     */
    private byte[] generateSecure(@NonNull SecureRandom secureRandom, int length) {
        byte[] result = new byte[length];
        secureRandom.nextBytes(result);
        return result;
    }

    /**
     * Derives a provided key into a new one by using HKDF algorithm.
     *
     * @param fromKey   source key.
     * @param info      key information used in derivation.
     * @param keyLength result key length.
     *
     * @return {@code byte[]} with the derived key.
     */
    private byte[] generateKey(@NonNull byte[] fromKey, @NonNull String info, int keyLength) {
        return HKDF
                .fromHmacSha256()
                .expand(fromKey, info.getBytes(StandardCharsets.UTF_8), keyLength);
    }

    /**
     * Generates a MAC from the authentication key and the cipher text.
     *
     * @param authKey    authentication key.
     * @param iv         IV vector.
     * @param cipherText encrypted text.
     *
     * @return {@code byte[]} with the MAC. If any exception occurs, returns {@code null}.
     */
    private byte[] generateMac(@NonNull byte[] authKey,
                               @NonNull byte[] iv,
                               @NonNull byte[] cipherText) {
        try {
            SecretKey macKey = new SecretKeySpec(authKey, HMAC_ALGORITHM);
            Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
            hmac.init(macKey);
            hmac.update(iv);
            hmac.update(cipherText);
            return hmac.doFinal();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * After following the encryption steps, combines everything into one single file by using
     * {@link ByteBuffer}.
     *
     * @param iv         IV vector.
     * @param mac        MAC authentication value.
     * @param cipherText encrypted text.
     *
     * @return {@code byte[]} with the complete combination.
     */
    private byte[] combineAll(@NonNull byte[] iv, @NonNull byte[] mac, @NonNull byte[] cipherText) {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(1 + iv.length + 1 + mac.length + cipherText.length);
        byteBuffer.put((byte) iv.length);
        byteBuffer.put(iv);
        byteBuffer.put((byte) mac.length);
        byteBuffer.put(mac);
        byteBuffer.put(cipherText);
        return byteBuffer.array();
    }
}
