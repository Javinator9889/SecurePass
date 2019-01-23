package javinator9889.securepass;

import com.github.javinator9889.exporter.FileToBytesExporter;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.lambdaworks.crypto.SCryptUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import androidx.annotation.NonNull;
import javinator9889.securepass.util.scrypt.Scrypt;

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
        exportDB();
        /*if (args.length <= 1)
            throw new IllegalArgumentException("At least two arguments must be included " +
                    "(filename & output file)");
        FileToBytesExporter exporter = new FileToBytesExporter(args[0]);
        exporter.readSource(true);
        exporter.writeObject(new File(args[1]));*/
        /*String ANDROID_KEY_STORE = "AndroidKeyStore";
        String ALGORITHM_RSA = "RSA";
        KeyPair keyPair;
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        generator.initialize(2048, new SecureRandom());
        keyPair = generator.generateKeyPair();
        String data = "hola1234";
        byte[] s = sign(data, keyPair.getPrivate());
        System.out.println(Arrays.toString(s));
        System.out.println(verifySignature(s, keyPair.getPublic()));
        System.out.println(new String(s));*/
        String password = "ThisIsAPassword";
        /*byte[] bytesPass = password.getBytes(StandardCharsets.UTF_8);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        for (int n = 14; n < 22; ++n) {
            long startTime = System.currentTimeMillis();
            SCrypt.scrypt(bytesPass, salt, 1 << n, 8, 1, 32);
            long endTime = System.currentTimeMillis();
            long ms = (endTime - startTime);
            System.out.printf("N = 2^%d\t%d ms\n", n, ms);
        }*/
        /*int N = 1 << 15;
        System.out.println(N);
        Scrypt scryptUtil = new Scrypt();
        scryptUtil.scrypt(password);
        String scryptUtilHash = scryptUtil.getHash();
        byte[] key = scryptUtil.getKey();
//        byte[] key = SCrypt.scrypt(password.getBytes(StandardCharsets.UTF_8), salt, N, 8, 4, 32);
        System.out.println(Arrays.toString(key));
        System.out.println(key.length);
        System.out.println(key.length * 8);
        System.out.println(scryptUtilHash);
        Scrypt checker = new Scrypt(scryptUtilHash);
        System.out.println(checker.check(password));
        for (int i = 0; i < 10; ++i) {
            String scrypt = SCryptUtil.scrypt(password, N, 8, 4);
            System.out.println(scrypt);
            boolean validate = SCryptUtil.check(password, scrypt);
            System.out.println(validate);
//            System.out.println(Arrays.toString(scrypt.getBytes()));
//            System.out.println(scrypt.getBytes().length);
        }
        HashCode hash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8);
        System.out.println(hash.toString());
        System.out.println(Arrays.toString(hash.asBytes()));
        System.out.println(hash.asBytes().length);
        System.out.println(hash.asBytes().length * 8);
        System.out.println(hash.bits());*/
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

    private static void exportDB() throws Exception {
        String path = "D:\\\\\\Nextcloud\\AndroidApps\\SecurePass\\APP\\app\\src\\main\\res" +
                "\\raw\\";
        String source = "database_script_readable.sql";
        String dest = "database_scriptabase_script.osql";
//        FileToBytesExporter exporter = new FileToBytesExporter(source, path);
//        exporter.readSource(true);
//        exporter.writeObject(new File(path + dest));
        String data = FileToBytesExporter.readSource(new File(path, source));
        FileToBytesExporter.writeObject(data, new File(path, dest));
    }
}