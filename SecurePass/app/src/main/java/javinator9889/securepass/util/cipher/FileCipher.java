/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 03/11/2018 - SecurePass.
 */
package javinator9889.securepass.util.cipher;

import android.content.Context;
import android.util.Log;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.NonNull;
import javinator9889.securepass.BuildConfig;
import javinator9889.securepass.io.IOManager;

/**
 * Class designed for encrypting - decrypting files for Drive uploads and downloads.
 */
public class FileCipher implements ICipher {
    private byte[] ivVector;
    private IvParameterSpec ivSpec;
    private byte[] password;

    public FileCipher(@NonNull Context activityContext) {
        this.password = Hashing.sha256()
                .hashString(BuildConfig.APPLICATION_ID, StandardCharsets.UTF_8)
                .toString()
                .substring(32, 48)
                .getBytes();
        IOManager io = IOManager.newInstance(activityContext);
        ivVector = new byte[IV_SIZE];
        if (io.isAnyIVVectorStored()) {
            File ivFile = io.getIVVector();
            try {
                ivVector = Files.toByteArray(ivFile);
            } catch (IOException e) {
                Log.e("FileCipher", "Error while recovering IV Vector", e);
            }
        } else {
            SecureRandom randomGenerator = new SecureRandom();
            randomGenerator.nextBytes(ivVector);
            try {
                io.saveIVVector(ivVector);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public void decryptFile(@NonNull InputStream source, @NonNull File destination)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException
    {
        SecretKeySpec keySpec = new SecretKeySpec(password, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedData = new byte[0];
        try (CipherInputStream inputStream =
                     new CipherInputStream(source, cipher)) {
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
