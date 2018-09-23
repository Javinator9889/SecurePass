package javinator9889.securepass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import javinator9889.securepass.backup.drive.DriveUploader;
import javinator9889.securepass.backup.drive.IDriveUploader;
import javinator9889.securepass.views.fragments.DriveContent;

/**
 * Created by Javinator9889 on 21/09/2018.
 */
public class DriveUploadTest {
    @Test
    public void uploadDb() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent driveIntent = new Intent(appContext, DriveContent.class);
        appContext.startActivity(driveIntent);
    }
}
