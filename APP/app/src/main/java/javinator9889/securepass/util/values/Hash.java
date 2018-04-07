package javinator9889.securepass.util.values;

import android.support.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class Hash {
    public static byte[] SHA2(@NonNull String originalString) throws NoSuchAlgorithmException {
        MessageDigest hasher = MessageDigest.getInstance(Constants.HASH.SHA2);
        byte[] encodedHash;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            encodedHash = hasher.digest(originalString.getBytes(StandardCharsets.UTF_8));
        } else {
            encodedHash = hasher.digest(originalString.getBytes());
        }
        return encodedHash;
    }
}
