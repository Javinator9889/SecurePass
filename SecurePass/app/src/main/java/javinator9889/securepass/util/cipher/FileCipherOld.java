package javinator9889.securepass.util.cipher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.common.hash.Hashing;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.util.values.Constants.CIPHER.FILE;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class FileCipherOld {
    private byte[] key;
    //private final byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private final int ivSize = 16;
    private byte[] iv;
    private IvParameterSpec ivspec;
    /*private final IvParameterSpec ivspec = new IvParameterSpec(iv);
    SecureRandom random = new SecureRandom();*/

    private FileCipherOld(@NonNull byte[] key) {
        this.key = key;
        fixKeyLength();
        iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        ivspec = new IvParameterSpec(iv);
    }

    private FileCipherOld(@NonNull byte[] key, @NonNull byte[] iv) {
        this.key = key;
        fixKeyLength();
        this.iv = iv;
        ivspec = new IvParameterSpec(iv);
    }

    public static FileCipherOld newInstance(@NonNull String key, @Nullable byte[] iv) {
        byte[] hashKey = Hashing.sha256().hashString(key, StandardCharsets.UTF_8).asBytes();
        if (iv == null)
            return new FileCipherOld(hashKey);
        else
            return new FileCipherOld(hashKey, iv);
    }

    @NonNull
    public byte[] getIv() {
        return iv;
    }

    public Map<SealedObject, CipherOutputStream> encrypt(@NonNull Serializable classToEncrypt,
                                                         @NonNull OutputStream outputStream)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IOException, InvalidAlgorithmParameterException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, FILE.ALGORITHM);
            Cipher cipher = Cipher.getInstance(FILE.TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
            SealedObject sealedObject = new SealedObject(classToEncrypt, cipher);

            CipherOutputStream outputFile = new CipherOutputStream(outputStream, cipher);
            Map<SealedObject, CipherOutputStream> mapWithEncryptedValues = new HashMap<>();
            mapWithEncryptedValues.put(sealedObject, outputFile);
            return mapWithEncryptedValues;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newEncrypt(@NonNull ClassContainer dataToEncrypt,
                           @NonNull OutputStream outputStream) throws IOException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        try {
            System.out.print("Password in bytes: " + Arrays.toString(key));
            SecretKeySpec keySpec = new SecretKeySpec(key, FILE.ALGORITHM);
            Cipher cipher = Cipher.getInstance(FILE.TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
            SealedObject sealedObject = new SealedObject(dataToEncrypt, cipher);

            CipherOutputStream cipherFile = new CipherOutputStream(outputStream, cipher);
            ObjectOutputStream outputFile = new ObjectOutputStream(cipherFile);
            outputFile.writeObject(sealedObject);
            outputFile.close();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public Object decrypt(@NonNull InputStream inputStream) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException {
        System.out.print("Password in bytes: " + Arrays.toString(key));
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, FILE.ALGORITHM);
        Cipher cipher = Cipher.getInstance(FILE.TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        ObjectInputStream decryptedClass = new ObjectInputStream(cipherInputStream);
        SealedObject sealedObject;
        try {
            sealedObject = (SealedObject) decryptedClass.readObject();
            return sealedObject.getObject(cipher);
        } catch (BadPaddingException | IllegalBlockSizeException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private void fixKeyLength() {
        int actualMaxKeyLength = 0;
        try {
            if ((actualMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
                Class c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
                Constructor con = c.getDeclaredConstructor();
                con.setAccessible(true);
                Object allPermissionCollection = con.newInstance();
                Field f = c.getDeclaredField("all_allowed");
                f.setAccessible(true);
                f.setBoolean(allPermissionCollection, true);

                c = Class.forName("javax.crypto.CryptoPermissions");
                con = c.getDeclaredConstructor();
                con.setAccessible(true);
                Object allPermissions = con.newInstance();
                f = c.getDeclaredField("perms");
                f.setAccessible(true);
                ((Map) f.get(allPermissions)).put("*", allPermissionCollection);

                c = Class.forName("javax.crypto.JceSecurityManager");
                f = c.getDeclaredField("defaultPolicy");
                f.setAccessible(true);
                Field mf = Field.class.getDeclaredField("modifiers");
                mf.setAccessible(true);
                mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, allPermissions);

                actualMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (actualMaxKeyLength < 256)
            throw new RuntimeException("Impossible to change key length");
        /*else
            System.out.println("Changed key length to: " + actualMaxKeyLength);*/
    }
}
