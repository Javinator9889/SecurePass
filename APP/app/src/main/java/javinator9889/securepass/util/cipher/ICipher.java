package javinator9889.securepass.util.cipher;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Javinator9889 on 05/09/2018.
 */
public interface ICipher {
    static final int IV_SIZE = 16;
    static final String ALGORITHM = "AES";
    static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    void encryptFile(@NonNull File source, @NonNull File destination) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IOException, BadPaddingException, IllegalBlockSizeException;
    void decryptFile(@NonNull File source, @NonNull File destination) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IOException;
}
