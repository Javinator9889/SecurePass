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

 * Created by Javinator9889 on 27/11/2018 - APP.
 */
package javinator9889.securepass.util.scrypt;

import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import androidx.annotation.NonNull;

import static com.lambdaworks.codec.Base64.decode;
import static com.lambdaworks.codec.Base64.encode;

/**
 * Scrypt access util class that contains two params: {@code key} and {@code hash}. This class is
 * useful for not doing the same operation twice (for obtaining the key and the hash).
 */
public class Scrypt {
    private byte[] mKey;
    private String mHash;

    /**
     * Default constructor - sets params to {@code null}.
     */
    public Scrypt() {
        super();
    }

    /**
     * Constructor that takes one param - useful for checking passwords
     *
     * @param hash the obtained hash when calling {@link #scrypt(String)} or {@link
     *             SCryptUtil#scrypt(String, int, int, int)} methods.
     */
    public Scrypt(@NonNull String hash) {
        mHash = hash;
    }

    /**
     * Obtains the {@code key} from the stored hashed value of the password.
     *
     * @param hashed hashed password obtained by using {@link #scrypt(String)}.
     *
     * @return {@code byte[]} with the key.
     */
    public static byte[] getKey(@NonNull String hashed) {
        String[] parts = hashed.split("\\$");

        if (parts.length != 5 || !parts[1].equals("s0")) {
            throw new IllegalArgumentException("Invalid hashed value");
        }

        return decode(parts[4].toCharArray());
    }

    /**
     * Obtains the derived key after calling {@link #scrypt(String)} method.
     *
     * @return {@code byte[]} with the key if {@link #scrypt(String)} was called, else {@code null}
     * or the latest value available.
     */
    public byte[] getKey() {
        return mKey;
    }

    /**
     * Calculates the {@code log2} by using bitwise operations.
     *
     * @param n value from which obtain {@code log2}
     *
     * @return {@code int} with the {@code log2}.
     */
    private static int log2(int n) {
        int log = 0;
        if ((n & 0xffff0000) != 0) {
            n >>>= 16;
            log = 16;
        }
        if (n >= 256) {
            n >>>= 8;
            log += 8;
        }
        if (n >= 16) {
            n >>>= 4;
            log += 4;
        }
        if (n >= 4) {
            n >>>= 2;
            log += 2;
        }
        return log + (n >>> 1);
    }

    /**
     * Applies the scrypt to the given password, saving the result to the local variables
     * (recoverable using {@link #getHash()} and {@link #getKey()}.
     * <p>
     * It sets by default a 'N' factor (CPU & memory load factor) of 2^15 (32768 iterations). The
     * 'r' factor is set to 8, which means that about {@code N · 2r · 64 bytes} of memory will be
     * used (2^25 == 32 MiB). Finally, 'p' factor is 1 and key length is of 32 bytes (256 bits).
     *
     * @param password password for applying the scrypt
     *
     * @throws GeneralSecurityException when HMAC_SHA256 is not available or if no Provider supports
     *                                  a SecureRandomSpi implementation for the specified
     *                                  algorithm.
     */
    public void scrypt(@NonNull String password) throws GeneralSecurityException {
        int N = 1 << 15;
        int r = 8;
        int p = 1;
        int keyLength = 32;
        byte[] salt = new byte[16];
        SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);

        mKey = SCrypt.scrypt(password.getBytes(StandardCharsets.UTF_8), salt, N, r, p, keyLength);
        String params = Long.toString(log2(N) << 16L | r << 8 | p, 16);

        mHash = "$s0$" + params + '$' +
                String.valueOf(encode(salt)) + '$' +
                String.valueOf(encode(mKey));
    }

    /**
     * Checks that the given password matches the hashed one - {@code hash} value cannot be {@code
     * null}. Use {@link #setHash(String)} or {@link #Scrypt(String)} for that.
     *
     * @param password the password that will be checked.
     *
     * @return {@code true} whether the passwords are the same or {@code false} if they are not.
     */
    public boolean check(@NonNull String password) {
        if (mHash == null)
            return false;
        else
            return SCryptUtil.check(password, mHash);
    }

    /**
     * Obtains the generated hash after calling {@link #scrypt(String)} method.
     *
     * @return {@code String} with the hash if {@link #scrypt(String)} was called, else {@code null}
     * or the latest value available.
     */
    public String getHash() {
        return mHash;
    }

    /**
     * Updates the hash value for checking passwords.
     *
     * @param hash new hash value.
     */
    public void setHash(@NonNull String hash) {
        mHash = hash;
    }
}
