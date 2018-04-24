package javinator9889.securepass.network.drive.base;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.tasks.Task;

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

    GoogleSignInClient buildGoogleSignInClient();
    Task<DriveId> pickClassFile();
    Task<DriveId> pickIvFile();
    Task<DriveId> pickItem(@NonNull OpenFileActivityOptions openOptions);
}
