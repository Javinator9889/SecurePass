package javinator9889.securepass.util.values;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Constants of the application that will be used in hole program
 */

public class Constants {
    public static final class SQL {
        public static String DB_FILENAME = "SecurePass.db";
        public static String DB_INIT_THREAD_NAME = "DB Initializer";
        public static String DB_DEFAULT_CATEGORY = "INSERT IF NOT EXISTS INTO Category VALUE ?";

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

    public static final class CIPHER {
        public static String KEYSTORE = "AndroidKeyStore";
        public static String ALGORITHM = "RSA";
        public static String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
        public static String MASTER_KEY = "javinator9889.securepass." + Build.DEVICE;

        public static final class FILE {
            public static final String TRANSFORMATION = "AES/GCM/PKCS5Padding";
        }
    }

    public static final class IO {
        public static String PASS_FILENAME = "userPass.dat";
    }

    public static final class FIRST_SETUP {
        public static final int TITLE_COLOR = Color.WHITE;
        public static final int DESCRIPTION_COLOR = Color.WHITE;
    }

    public static final class SHARED_PREF {
        public static final String FILENAME = "userPreferences";
        public static final int MODE = Context.MODE_PRIVATE;
        public static final Object[][] VALUES = new Object[][] {
                {"isApplicationFirstExecuted", false, boolean.class}
        };
    }

    public static final class DRIVE {
        public static final int REQUEST_CODE_SIGN_IN = 0;
        public static final int REQUEST_CODE_OPEN_ITEM = 1;
        public static final int HASHSET_INITIAL_CAPACITY = 2;
        public static final String MIME_TYPE = "text/plain";
        public static final Intent FILL_IN_INTENT = null;
        public static final int FLAGS_MASK = 0;
        public static final int FLAGS_VALUES = 0;
        public static final int EXTRA_FLAGS = 0;
        public static final boolean STARRED = true;
        public static final String GOOGLE_PLAY_NOT_AVAILABLE = "Google Play Services are not " +
                "available on this device";
        public static final String GOOGLE_ACCOUNT_NOT_SIGNED_IN = "Google Account has not signed" +
                " in - impossible to use Google Drive API";
        public static final String GOOGLE_DRIVE_FILE_NOT_CREATED = "Unable to create a file on" +
                " Google Drive";
        public static final String GOOGLE_FILE_NO_SELECTED = "No file selected";
        public static final String FILE_TITLE = "secpass.bak";
    }

    public static final class TIME {
        private static final String PATTERN = "DDMMyyyy_HHmm";
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN, Locale.US);
        public static final String ACTUAL_TIME = dateFormat
                .format(Calendar.getInstance().getTime());
    }

    public static final class HASH {
        public static final String SHA2 = "SHA-256";
    }
}
