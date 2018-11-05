package javinator9889.securepass;

import android.content.Context;
import android.content.Intent;

import org.junit.Test;

import androidx.test.InstrumentationRegistry;
import javinator9889.securepass.views.fragments.DriveContent;

/**
 * Created by Javinator9889 on 21/09/2018. - deprecated
 * @deprecated
 */
@Deprecated
public class DriveUploadTest {
    @Test
    public void uploadDb() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent driveIntent = new Intent(appContext, DriveContent.class);
        appContext.startActivity(driveIntent);
    }
}
