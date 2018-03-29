package javinator9889.securepass.util.values;

import android.os.Build;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Constants of the application that will be used in hole program
 */

public class Constants {
    public static class SQL {
        public static String DB_FILENAME = "SecurePass.db";
        public static String DB_INIT_THREAD_NAME = "DB Initializer";

        // INSERT SQL OPERATIONS
        public static String DB_NEW_ENTRY = "INSERT INTO Entry VALUES(?, ?, ?, ?, ?)";
        public static String DB_NEW_CATEGORY = "INSERT INTO Category VALUE(?)";
        public static String DB_NEW_QR = "INSERT INTO QRCode VALUES (?, ?, ?, ?)";
        public static String DB_NEW_SECURITY_CODE = "INSERT INTO SecurityCodes VALUE (?)";
        public static String DB_NEW_FIELD = "INSERT INTO Fields VALUES (?, ?, ?)";

        //DELETE SQL OPERATIONS
        public static String DB_DELETE_ENTRY = "DELETE FROM Entry WHERE Entry.idEntry = ?";
        public static String DB_DELETE_CATEGORY =
                "DELETE FROM Category WHERE Category.idCategory = ?";
        public static String DB_DELETE_QR_CODE = "DELETE FROM QRCode WHERE QRCode.idQRCode = ?";
        public static String DB_DELETE_FIELD_FROM_SECURITY_CODE = "DELETE FROM Fields WHERE " +
                "Fields.fidSecurityCodes = ?";
        public static String DB_DELETE_SECURITY_CODE = "DELETE FROM SecurityCodes " +
                "WHERE SecurityCodes.idSecurityCodes = ?";
        public static String DB_DELETE_FIELD = "DELETE FROM Fields WHERE Fields.idField = ?";

        // UPDATE SQL OPERATIONS
        public static String DB_UPDATE_FOR_DELETED_CATEGORY = "UPDATE Entry SET " +
                "Entry.cidCategory = 0 WHERE Entry.cidCategory = ?";
        public static String DB_UPDATE_ENTRY = "UPDATE Entry SET Entry.account = ?," +
                " Entry.password = ?, Entry.icon = ?, Entry.description = ?, " +
                "Entry.cidCategory = ? WHERE Entry.idEntry = ?";
        public static String DB_UPDATE_CATEGORY = "UPDATE Category SET Category.name = ? WHERE" +
                " Category.idCategory = ?";
        public static String DB_UPDATE_QR_CODE = "UPDATE QRCode SET QRCode.name = ?," +
                " QRCode.description = ?, QRCode.data = ?, QRCode.fidEntry = ? " +
                "WHERE QRCode.idQRCode = ?";
        public static String DB_UPDATE_SECURITY_CODE = "UPDATE SecurityCodes " +
                "SET SecurityCodes.accountName = ? WHERE SecurityCodes.idSecurityCodes = ?";
        public static String DB_UPDATE_FIELD = "UPDATE Fields SET Fields.code = ?, " +
                "Fields.used = ? WHERE Fields.idField = ?";

        // SELECT SQL OPERATIONS
        public static String DB_SELECT_CATEGORIES = "SELECT * FROM Categories";
        public static String DB_SELECT_ENTRIES = "SELECT * FROM Entries";
        public static String DB_SELECT_QR_CODES = "SELECT * FROM QRCode";
        public static String DB_SELECT_SECURITY_CODES = "SELECT * FROM SecurityCodes";
        public static String DB_SELECT_FIELDS = "SELECT * FROM Fields";
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
