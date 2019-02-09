package javinator9889.securepass.util.cipher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import androidx.annotation.NonNull;

/**
 * Created by Javinator9889 on 05/09/2018.
 */
public interface ICipher {
    int IV_SIZE = 16;
    String ALGORITHM = "AES";
    String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    void encryptFile(@NonNull File source, @NonNull File destination) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IOException, BadPaddingException, IllegalBlockSizeException;
    void decryptFile(@NonNull InputStream source, @NonNull File destination) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IOException;
}
