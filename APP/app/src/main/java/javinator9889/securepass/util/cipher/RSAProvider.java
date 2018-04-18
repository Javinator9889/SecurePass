package javinator9889.securepass.util.cipher;

import android.content.Context;
import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import javinator9889.securepass.util.ArrayUtils;

/**
 * Created by Javinator9889 on 18/04/2018.
 */
public class RSAProvider implements PasswordCipher {
    RSAProvider() {}
    @Override
    public Map<String, Object> generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair pair = keyPairGenerator.generateKeyPair();

        RSAPrivateKey privateKey = (RSAPrivateKey) pair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) pair.getPublic();
        BigInteger modules = privateKey.getModulus();

        Map<String, Object> keys = new HashMap<>(3);
        keys.put(PUBLIC_KEY, publicKey);
        keys.put(PRIVATE_KEY, privateKey);
        keys.put(MODULES, modules);
        return keys;
    }

    public String encrypt(@NonNull byte[] dataToEncrypt, @NonNull RSAPublicKey key)
            throws Exception {
        byte[] encode = new byte[]{};
        for (int i = 0; i < dataToEncrypt.length; i += encodeLen) {
            byte[] subarray = ArrayUtils.subarray(dataToEncrypt, i, i + encodeLen);
            byte[] doFinal = encryptByPublicKey(subarray, key);
            encode = ArrayUtils.addAll(encode, doFinal);
        }
        return new String(encode, "UTF-8");
    }

    @Override
    public void loadKeysFromKeyStore(@NonNull Context sourceContext) {

    }

    @Override
    public void storeKeysInKeyStore(@NonNull Context sourceContext) {

    }

    private static byte[] encryptByPublicKey(@NonNull byte[] data, RSAPublicKey key)
            throws Exception {
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }
}
