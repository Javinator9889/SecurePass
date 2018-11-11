package javinator9889.securepass;

import com.github.javinator9889.exporter.FileToBytesExporter;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import androidx.annotation.NonNull;

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
public class Generator {
    private static final String SIGNATURE = "SHA256withRSA";
    /**
     * Do not let anyone use this class
     */
    private Generator() {}

    /**
     * Generates a bytes file from given file.
     *
     * @param args {@code String[]} containing, at 0: "filename"; at 1: output file path.
     * @throws IOException when an error occurs (see {@link FileToBytesExporter#readSource()} &
     *                     {@link FileToBytesExporter#writeObject(File)}).
     */
    public static void main(String[] args) throws Exception {
        /*if (args.length <= 1)
            throw new IllegalArgumentException("At least two arguments must be included " +
                    "(filename & output file)");
        FileToBytesExporter exporter = new FileToBytesExporter(args[0]);
        exporter.readSource(true);
        exporter.writeObject(new File(args[1]));*/
        String ANDROID_KEY_STORE = "AndroidKeyStore";
        String ALGORITHM_RSA = "RSA";
        KeyPair keyPair;
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        generator.initialize(2048, new SecureRandom());
        keyPair = generator.generateKeyPair();
        String data = "hola1234";
        byte[] s = sign(data, keyPair.getPrivate());
        System.out.println(Arrays.toString(s));
        System.out.println(verifySignature(s, keyPair.getPublic()));
        System.out.println(new String(s));

    }

    private static byte[] sign(@NonNull String data, @NonNull PrivateKey privateKeyEntry)
            throws InvalidKeyException, SignatureException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE);
            signature.initSign(privateKeyEntry);
            signature.update(data.getBytes());
            return signature.sign();
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    private static boolean verifySignature(@NonNull byte[] data, @NonNull PublicKey
            privateKeyEntry) throws Exception {
        Signature s = Signature.getInstance(SIGNATURE);
        s.initVerify(privateKeyEntry);
        s.update(data);
        return s.verify(data);
    }
}
