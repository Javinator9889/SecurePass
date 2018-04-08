package javinator9889.securepass.util.cipher;

import android.support.annotation.NonNull;

import com.google.common.hash.Hashing;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

import javinator9889.securepass.errors.NoSHA2AlgorithmException;
import javinator9889.securepass.util.values.Constants.CIPHER.FILE;
import javinator9889.securepass.util.values.Hash;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class FileCipher {
    private byte[] key;

    private FileCipher(@NonNull byte[] key) {
        this.key = key;
    }

    public static FileCipher newInstance(@NonNull String key) throws NoSHA2AlgorithmException {
        byte[] hashKey = Hashing.sha256().hashString(key, StandardCharsets.UTF_8).asBytes();
        return new FileCipher(hashKey);
    }

    public Map<SealedObject, CipherOutputStream> encrypt(@NonNull Serializable classToEncrypt,
                                                         @NonNull OutputStream outputStream)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IOException
    {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, FILE.TRANSFORMATION);
            Cipher cipher = Cipher.getInstance(FILE.TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
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

    public Object decrypt(@NonNull InputStream inputStream) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, FILE.TRANSFORMATION);
        Cipher cipher = Cipher.getInstance(FILE.TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

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
}
