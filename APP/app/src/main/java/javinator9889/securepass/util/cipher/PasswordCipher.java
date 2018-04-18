package javinator9889.securepass.util.cipher;

import android.content.Context;
import android.support.annotation.NonNull;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Javinator9889 on 18/04/2018.
 */
public interface PasswordCipher {
    String KEY_ALGORITHM = "RSA";
    int KEY_SIZE = 2048;
    String PUBLIC_KEY = "publicKey";
    String PRIVATE_KEY = "privateKey";
    String MODULES = "RSAModules";
    String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    int encodeLen = 110;
    Map<String, Object> generateKeyPair() throws NoSuchAlgorithmException;
    void loadKeysFromKeyStore(@NonNull Context sourceContext);
    void storeKeysInKeyStore(@NonNull Context sourceContext);
}
