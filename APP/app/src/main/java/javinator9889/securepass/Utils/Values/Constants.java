package javinator9889.securepass.Utils.Values;

import android.os.Build;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Constants of the application that will be used in hole program
 */

public class Constants {
    public static class SQL {
        public static String DB_FILENAME = "SecurePass.db";
        public static String DB_INIT_THREAD_NAME = "DB Initializer";
    }

    public static class CIPHER {
        public static String KEYSTORE = "AndroidKeyStore";
        public static String ALGORITHM = "RSA";
        public static String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
        public static String MASTER_KEY = "javinator9889.securepass." + Build.DEVICE;
    }

    public static class IO {
        public static String PASS_FILENAME = "userPass.dat";
    }
}
