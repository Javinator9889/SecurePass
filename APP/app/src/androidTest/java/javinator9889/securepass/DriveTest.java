package javinator9889.securepass;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.errors.GoogleDriveNotAvailableException;
import javinator9889.securepass.network.drive.CreateFileInAppFolder;

/**
 * Created by Javinator9889 on 21/04/2018.
 */
public class DriveTest {
    @Test
    public void createFileInAppFolder() throws Exception {
        try {
            Context baseContext = InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(baseContext, CreateFileInAppFolder.class);
            ClassContainer container = DataClassForTests.CONTAINER_TEST_CLASS();
            intent.putExtra("data", container);
            baseContext.startActivity(intent);
        } catch (GoogleDriveNotAvailableException e) {
            Log.e("GMS", "Google Play Services are not available on this device", e);
        }
    }
}
