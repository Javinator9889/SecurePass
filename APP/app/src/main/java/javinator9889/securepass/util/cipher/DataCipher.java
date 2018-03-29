package javinator9889.securepass.util.cipher;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import javinator9889.securepass.util.values.Constants.CIPHER;

/**
 * Created by Javinator9889 on 27/03/2018.
 * Based on: https://proandroiddev.com/secure-data-in-android-encryption-in-android-part-2-991a89e55a23
 */
public class DataCipher {
    private Context cipherContext;
    private Cipher classCipher;
    private KeyStore cipherKeyStore;

    private DataCipher(Context cipherContext) {
        this.cipherContext = cipherContext;
        try {
            this.classCipher = Cipher.getInstance(CIPHER.TRANSFORMATION, CIPHER.KEYSTORE);
            this.cipherKeyStore = createAndroidKeyStore();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException |
                KeyStoreException | CertificateException | IOException e) {
            Log.e("Cipher", "Error while trying to init Cipher@DataCipher. Message: "
            + e.getMessage() + "\nFull trace:");
            e.printStackTrace();
            classCipher = null;
        }
    }

    @NonNull
    public static DataCipher newInstance(Context cipherContext) {
        return new DataCipher(cipherContext);
    }

    private KeyStore createAndroidKeyStore() throws KeyStoreException,
            CertificateException,
            NoSuchAlgorithmException,
            IOException
    {
        KeyStore newKey = KeyStore.getInstance(CIPHER.KEYSTORE);
        newKey.load(null);
        return newKey;
    }

    public KeyPair createAndroidAsymmetricKey(String alias) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyStoreException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(CIPHER.ALGORITHM,
                CIPHER.KEYSTORE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initGeneratorWithKeyGenParameterGenerator(generator, alias);
        } else {
            initGeneratorWithKeyPairGenerator(generator, alias);
        }
        return generator.generateKeyPair();
    }

    public void removeAndroidKeyStoreKey(@NonNull String alias) throws KeyStoreException {
        cipherKeyStore.deleteEntry(alias);
    }

    private void initGeneratorWithKeyPairGenerator(@NonNull KeyPairGenerator generator,
                                                   @NonNull String alias) throws
            InvalidAlgorithmParameterException, KeyStoreException {
        if (!cipherKeyStore.containsAlias(alias)) {
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            endDate.add(Calendar.YEAR, 20);

            KeyPairGeneratorSpec.Builder keyBuilder = new KeyPairGeneratorSpec.Builder(cipherContext)
                    .setAlias(alias)
                    .setSerialNumber(BigInteger.ONE)
                    .setSubject(new X500Principal("CN=" + alias + " CA Certificate"))
                    .setStartDate(startDate.getTime())
                    .setEndDate(endDate.getTime());
            generator.initialize(keyBuilder.build());
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initGeneratorWithKeyGenParameterGenerator(@NonNull KeyPairGenerator generator,
                                                           @NonNull String alias) throws
            InvalidAlgorithmParameterException, KeyStoreException {
        if (!cipherKeyStore.containsAlias(alias)) {
            KeyGenParameterSpec.Builder keyBuilder = new KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);
            generator.initialize(keyBuilder.build());
        }
    }

    public KeyPair getAndroidKeyStoreAsymmetricKeyPair(@NonNull String alias) throws
            UnrecoverableKeyException,
            NoSuchAlgorithmException,
            KeyStoreException
    {
        PrivateKey privateKey = (PrivateKey) cipherKeyStore.getKey(alias, null);
        PublicKey publicKey = cipherKeyStore.getCertificate(alias).getPublicKey();

        return (privateKey != null && publicKey != null) ? new KeyPair(publicKey, privateKey) : null;
    }

    public String encrypt(@NonNull String dataToCipher, @NonNull Key cipherKey) throws
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeyException
    {
        classCipher.init(Cipher.ENCRYPT_MODE, cipherKey);
        byte[] cipherResultInBytes = classCipher.doFinal(dataToCipher.getBytes());
        return Base64.encodeToString(cipherResultInBytes, Base64.DEFAULT);
    }

    public String decrypt(@NonNull String dataToDecrypt, @NonNull Key cipherKey) throws
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException
    {
        classCipher.init(Cipher.DECRYPT_MODE, cipherKey);
        byte[] encryptedData = Base64.decode(dataToDecrypt, Base64.DEFAULT);
        byte[] decodedData = classCipher.doFinal(encryptedData);
        return new String(decodedData);
    }
}
