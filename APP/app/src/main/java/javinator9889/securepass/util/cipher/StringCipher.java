package javinator9889.securepass.util.cipher;

import android.content.Context;
import android.support.annotation.NonNull;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * Created by Javinator9889 on 18/04/2018.
 */
public class StringCipher implements PasswordCipher {
    private RSAProvider provider;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public StringCipher() {
        provider = new RSAProvider();
    }

    public void generateKeys() {
        try {
            Map<String, Object> keys = generateKeyPair();
            this.publicKey = (RSAPublicKey) keys.get(PUBLIC_KEY);
            this.privateKey = (RSAPrivateKey) keys.get(PRIVATE_KEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> generateKeyPair() throws NoSuchAlgorithmException {
        provider = new RSAProvider();
        return provider.generateKeyPair();
    }

    @Override
    public void loadKeysFromKeyStore(@NonNull Context sourceContext) {

    }

    public String encryptUsingPublicKey(@NonNull String dataToEncrypt) {

    }

    public String decryptUsingPrivateKey(@NonNull String dataToDecrypt) {
        return null;
    }

    @Override
    public void storeKeysInKeyStore(@NonNull Context sourceContext) {

    }
}
