package javinator9889.securepass.util.cipher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javinator9889.securepass.BuildConfig;
import javinator9889.securepass.io.IOManager;

/**
 * Created by Javinator9889 on 05/09/2018.
 */
public class FileCipher implements ICipher {
    private byte[] ivVector;
    private IvParameterSpec ivSpec;
    private byte[] password;

    public FileCipher(@NonNull Context activityContext) {
        this.password = Hashing.sha256()
                .hashString(BuildConfig.APPLICATION_ID, StandardCharsets.UTF_8)
                .toString()
                .substring(0, 16)
                .getBytes();
        IOManager io = IOManager.newInstance(activityContext);
        if (io.isAnyIVVectorStored()) {
            File ivFile = io.getIVVector();
            ivVector = new byte[IV_SIZE];
            try {
                ivVector = Files.toByteArray(ivFile);
            } catch (IOException e) {
                Log.e("FileCipher", "Error while recovering IV Vector", e);
            }
        } else {
            SecureRandom randomGenerator = new SecureRandom();
            randomGenerator.nextBytes(ivVector);
        }
        ivSpec = new IvParameterSpec(ivVector);
    }

    @Override
    public void encryptFile(@NonNull File source, @NonNull File destination)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException,
            BadPaddingException, IllegalBlockSizeException
    {
        SecretKeySpec keySpec = new SecretKeySpec(password, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedData = cipher.doFinal(Files.toByteArray(source));
        Files.write(encryptedData, destination);
    }

    @Override
    public void decryptFile(@NonNull File source, @NonNull File destination)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException
    {
        SecretKeySpec keySpec = new SecretKeySpec(password, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedData = new byte[0];
        try (CipherInputStream inputStream =
                     new CipherInputStream(new FileInputStream(source), cipher)) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[2048];
            while (inputStream.read(data) != -1)
                buffer.write(data);
            buffer.flush();
            decryptedData = buffer.toByteArray();
        } catch (IOException e) {
            Log.e("DECRYPT", "Error while recovering data", e);
        }
        Files.write(decryptedData, destination);
    }
}
