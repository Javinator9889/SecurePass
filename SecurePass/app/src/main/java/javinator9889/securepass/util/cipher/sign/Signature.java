package javinator9889.securepass.util.cipher.sign;

import android.os.Build;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
public class Signature {
    private String mAlgorithm;
    private String mProvider;

    /**
     * Public default constructor for signing - it sets the {@link #mAlgorithm algorithm} to RSA.
     */
    public Signature() {
        this(null, null);
    }

    /**
     * Constructor for providing a custom signing algorithm to use with {@link Cipher} - if {@code
     * null} or empty string {@code ""}, it sets to RSA.
     *
     * @param signAlgorithm algorithm to do the signature - if {@code null} or empty string {@code
     *                      ""}, it sets to RSA/ECB/PKCS1Padding.
     * @param provider      cipher provider - if {@code null} or empty string {@code ""}, it is set
     *                      to "AndroidOpenSSl" (if lower than Marshmallow) or
     *                      "AndroidKeyStoreBCWorkaround" (if Marshmallow or higher).
     */
    public Signature(@Nullable String signAlgorithm, @Nullable String provider) {
        mAlgorithm = (signAlgorithm != null && !signAlgorithm.equals("")) ?
                signAlgorithm :
                "RSA";
        mProvider = (provider != null && !provider.equals("")) ?
                provider :
                (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ?
                        "AndroidOpenSSL" :
                        "AndroidKeyStoreBCWorkaround");
    }

    /**
     * Signs the data using the provided private key - for signing, an input is ciphered with the
     * private key, so for checking the authenticity of the user only the public key can decrypt the
     * obtained output.
     *
     * @param data       input data to sign.
     * @param privateKey private key used for signing.
     *
     * @return {@code byte[]} with the input signed.
     *
     * @throws NoSuchAlgorithmException  if transformation is null, empty, in an invalid format, or
     *                                   if no Provider supports a CipherSpi implementation for the
     *                                   specified algorithm.
     * @throws InvalidKeyException       InvalidKeyException if the given key is inappropriate for
     *                                   initializing this cipher, or requires algorithm parameters
     *                                   that cannot be determined from the given key, or if the
     *                                   given key has a keysize that exceeds the maximum allowable
     *                                   keysize (as determined from the configured jurisdiction
     *                                   policy files).
     * @throws NoSuchPaddingException    if transformation contains a padding scheme that is not
     *                                   available.
     * @throws BadPaddingException       if this cipher is in decryption mode, and (un)padding has
     *                                   been requested, but the decrypted data is not bounded by
     *                                   the appropriate padding bytes.
     * @throws IllegalBlockSizeException if this cipher is a block cipher, no padding has been
     *                                   requested (only in encryption mode), and the total input
     *                                   length of the data processed by this cipher is not a
     *                                   multiple of block size; or if this encryption algorithm is
     *                                   unable to process the input data provided.
     * @throws NoSuchProviderException   if the specified provider is not registered in the security
     *                                   provider list.
     */
    public byte[] sign(@NonNull String data, @NonNull PrivateKey privateKey) throws
            NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
            BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        Cipher signer = Cipher.getInstance(mAlgorithm);
        signer.init(Cipher.ENCRYPT_MODE, privateKey);
        return signer.doFinal(data.getBytes());
    }

    /**
     * Removes the sign for obtaining the original signed data - as explained at {@link
     * #sign(String, PrivateKey) sign method}, for signing first the input is ciphered using the
     * private key, so for removing that signature the public key is used, as it is the only key
     * that can remove the signature for verifying the authenticity of the user who signed the
     * input.
     *
     * @param data      input data that will get the signature removed.
     * @param publicKey key necessary for removing the signature.
     *
     * @return {@code String} with the original data.
     *
     * @throws NoSuchAlgorithmException  if transformation is null, empty, in an invalid format, or
     *                                   if no Provider supports a CipherSpi implementation for the
     *                                   specified algorithm.
     * @throws InvalidKeyException       InvalidKeyException if the given key is inappropriate for
     *                                   initializing this cipher, or requires algorithm parameters
     *                                   that cannot be determined from the given key, or if the
     *                                   given key has a keysize that exceeds the maximum allowable
     *                                   keysize (as determined from the configured jurisdiction
     *                                   policy files).
     * @throws NoSuchPaddingException    if transformation contains a padding scheme that is not
     *                                   available.
     * @throws BadPaddingException       if this cipher is in decryption mode, and (un)padding has
     *                                   been requested, but the decrypted data is not bounded by
     *                                   the appropriate padding bytes.
     * @throws IllegalBlockSizeException if this cipher is a block cipher, no padding has been
     *                                   requested (only in encryption mode), and the total input
     *                                   length of the data processed by this cipher is not a
     *                                   multiple of block size; or if this encryption algorithm is
     *                                   unable to process the input data provided.
     * @throws NoSuchProviderException   if the specified provider is not registered in the security
     *                                   provider list.
     */
    public String removeSign(@NonNull byte[] data, @NonNull PublicKey publicKey) throws
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        Cipher signer = Cipher.getInstance(mAlgorithm);
        signer.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(signer.doFinal(data), StandardCharsets.UTF_8);
    }
}
