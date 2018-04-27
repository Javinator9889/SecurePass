package javinator9889.securepass.backup.drive.base;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;

import javinator9889.securepass.data.container.ClassContainer;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public interface IDriveBase {
    int REQUEST_CODE_SIGN_IN = 0;
    int REQUEST_CODE_OPEN_ITEM = 1;
    Intent FILL_IN_INTENT = null;
    int FLAGS_MASK = 0;
    int FLAGS_VALUES = 0;
    int EXTRA_FLAGS = 0;
    int REQUEST_CODE_CAPTURE_IMAGE = 1;
    int REQUEST_CODE_CREATOR = 2;

    //GoogleSignInClient buildGoogleSignInClient();
    //Task<DriveId> pickClassFile();
    //Task<DriveId> pickIvFile();
    //Task<DriveId> pickItem(@NonNull OpenFileActivityOptions openOptions);
    void signIn();
    void uploadFile(@NonNull ClassContainer dataToBackup);
    void restoreData();
    void setDriveResourceClient(DriveResourceClient resourceClient);
    void setLoggedIn(boolean isLoggedIn);
}
