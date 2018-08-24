package javinator9889.securepass.util.values;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Javinator9889 on 26/03/2018.
 * Constants of the application that will be used in hole program
 */

public class Constants {
    public static final class SQL {
        public static String DB_FILENAME = "SecurePass.db";
        public static String DB_INIT_THREAD_NAME = "DB Initializer";
        public static String DB_DEFAULT_CATEGORY = "INSERT IF NOT EXISTS INTO " +
                "Category(name) VALUES (?)";

        // INSERT SQL OPERATIONS
        public static final class CATEGORY {
            public static String NAME = "Category";
            public static String C_ID = "idCategory";
            public static String C_NAME = "name";
        }

        public static final class ENTRY {
            public static final String NAME = "Entry";
            public static final String E_ID = "idEntry";
            public static final String E_NAME = "name";
//            public static final String E_PASSWORD = "password";
            public static final String E_ICON = "icon";
//            public static final String E_DESCRIPTION = "description";
            public static final String E_PARENT_CATEGORY = "cidCategory";
            public static final String E_PARENT_CONFIGURATION = "idConfiguration";F
        }

        public static final class PASSWORD {
            public static final String NAME = "Password";
            public static final String P_ID = "idPassword";
            public static final String P_PASSWORD = "password";
            public static final String P_DESCRIPTION = "field_desc";
            public static final String P_PARENT_ENTRY = "idEntry";
            public static final String P_PARENT_CATEGORY = "cidCategory";
        }

        public static final class SMALL_TEXT {
            public static final String NAME = "SmallText";
            public static final String S_ID = "idSmallText";
            public static final String S_TEXT = "text";
            public static final String S_DESCRIPTION = "field_desc";
            public static final String S_PARENT_ENTRY = "idEntry";
            public static final String S_PARENT_CATEGORY = "cidCategory";
        }

        public static final class LONG_TEXT {
            public static final String NAME = "LongText";
            public static final String L_ID = "idLongText";
            public static final String L_TEXT = "text";
            public static final String L_DESCRIPTION = "field_desc";
            public static final String L_PARENT_ENTRY = "idEntry";
            public static final String L_PARENT_CATEGORY = "cidCategory";
        }

        public static final class IMAGE {
            public static final String NAME = "Image";
            public static final String I_ID = "idImage";
            public static final String I_SOURCE = "source";
            public static final String I_DESCRIPTION = "field_desc";
            public static final String I_PARENT_ENTRY = "idEntry";
            public static final String I_PARENT_CATEGORY = "cidCategory";
        }

        public static final class CONFIGURATION {
            public static final String NAME = "Configuration";
            public static final String C_ID = "idConfiguration";
            public static final String C_NAME = "configName";
        }

        private static class COMMON_CONFIG {
            public static final String F_ID = "idConfig";
            public static final String F_DESCRIPTION = "desc";
            public static final String F_SORT_ORDER = "sortOrder";
            public static final String F_PARENT_CONFIG_ID = "idConfiguration";
        }

        public static final class PASS_CONFIG extends COMMON_CONFIG {
            public static final String NAME = "PassConfig";
        }

        public static final class SMALL_TEXT_CONFIG extends COMMON_CONFIG {
            public static final String NAME = "SmallTextConfig";
        }

        public static final class LONG_TEXT_CONFIG extends COMMON_CONFIG {
            public static final String NAME = "LongTextConfig";
        }

        public static final class IMAGES_CONFIG extends COMMON_CONFIG {
            public static final String NAME = "ImagesConfig";
        }

        public static final class QR_CODE {
            public static final String NAME = "QRCode";
            public static final String Q_ID = "idQRCode";
            public static final String Q_NAME = "name";
            public static final String Q_DESCRIPTION = "description";
            public static final String Q_DATA = "data";
            public static final String Q_PARENT_ENTRY = "fidEntry";
        }

        public static final class SECURITY_CODE {
            public static final String NAME = "SecurityCodes";
            public static final String S_ID = "idSecurityCodes";
            public static final String S_ACCOUNT_NAME = "accountName";
        }

        public static final class FIELD {
            public static final String NAME = "Fields";
            public static final String F_ID = "idField";
            public static final String F_CODE = "code";
            public static final String F_USED = "used";
            public static final String F_PARENT_SECURITY_CODE = "fidSecurityCodes";
        }

        public static String DB_NEW_ENTRY = "INSERT INTO Entry VALUES(?, ?, ?, ?, ?)";
        public static String DB_NEW_CATEGORY = "INSERT INTO Category VALUES (?)";
        public static String DB_NEW_QR = "INSERT INTO QRCode VALUES (?, ?, ?, ?)";
        public static String DB_NEW_SECURITY_CODE = "INSERT INTO SecurityCodes VALUES (?)";
        public static String DB_NEW_FIELD = "INSERT INTO Fields VALUES (?, ?, ?)";

        //DELETE SQL OPERATIONS
        public static String DB_DELETE_ENTRY_WHERE_CLAUSE = "Entry.idEntry = ?";
        public static String DB_DELETE_CATEGORY_WHERE_CLAUSE = "Category.idCategory = ?";
        public static String DB_DELETE_QR_CODE_WHERE_CLAUSE = "QRCode.idQRCode = ?";
        public static String DB_DELETE_FIELD_FROM_SECURITY_CODE_WHERE_CLAUSE =
                "Fields.fidSecurityCodes = ?";
        public static String DB_DELETE_SECURITY_CODE_WHERE_CLAUSE =
                "SecurityCodes.idSecurityCodes = ?";
        public static String DB_DELETE_FIELD_WHERE_CLAUSE = "Fields.idField = ?";

        // UPDATE SQL OPERATIONS
        public static String DB_UPDATE_FOR_DELETED_CATEGORY_WHERE_CLAUSE = "Entry.cidCategory = ?";
        public static String DB_UPDATE_ENTRY_WHERE_CLAUSE = "Entry.idEntry = ?";
        public static String DB_UPDATE_CATEGORY_WHERE_CLAUSE = "Category.idCategory = ?";
        public static String DB_UPDATE_QR_CODE_WHERE_CLAUSE = "QRCode.idQRCode = ?";
        public static String DB_UPDATE_SECURITY_CODE_WHERE_CLAUSE =
                "SecurityCodes.idSecurityCodes = ?";
        public static String DB_UPDATE_FIELD_WHERE_CLAUSE = "Fields.idField = ?";
        public static String DB_UPDATE_PASSWORD_WHERE_CLAUSE  = PASSWORD.NAME + "." + PASSWORD.P_ID
                + " = ?";
        public static String DB_UPDATE_SMALL_TEXT_WHERE_CLAUSE = SMALL_TEXT.NAME + "." +
                SMALL_TEXT.S_ID + " = ?";
        public static String DB_UPDATE_LONG_TEXT_WHERE_CLAUSE = LONG_TEXT.NAME + "." +
                LONG_TEXT.L_ID + " = ?";
        public static String DB_UPDATE_IMAGE_WHERE_CLAUSE = IMAGE.NAME + "." + IMAGE.I_ID + " = ?";

        // SELECT SQL OPERATIONS
        public static String DB_SELECT_CATEGORIES = "SELECT * FROM Categories";
        public static String DB_SELECT_ENTRIES = "SELECT * FROM Entries";
        public static String DB_SELECT_QR_CODES = "SELECT * FROM QRCode";
        public static String DB_SELECT_SECURITY_CODES = "SELECT * FROM SecurityCodes";
        public static String DB_SELECT_FIELDS = "SELECT * FROM Fields";
    }

    public static final class CIPHER {

        public static final class FILE {
            public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
            public static final String ALGORITHM = "AES";
        }
    }

    public static final class IO {
    }

    public static final class FIRST_SETUP {
    }

    public static final class SHARED_PREF {
        public static final String FILENAME = "userPreferences";
        public static final int MODE = Context.MODE_PRIVATE;
        public static final Object[][] VALUES = new Object[][]{
                {"isApplicationFirstExecuted", false, boolean.class},
                {"isDatabaseInitialized", false, boolean.class}
        };
    }

    public static final class DRIVE {
        //public static final int REQUEST_CODE_SIGN_IN = 0;
        //public static final int REQUEST_CODE_OPEN_ITEM = 1;
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
        public static final String IV_FILE = "latestIv.vector";
    }
}
