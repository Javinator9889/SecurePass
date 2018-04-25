package javinator9889.securepass.backup.drive;

import com.google.android.gms.drive.DriveFile;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public interface IDriveDownloadOperations {
    void retrieveIvVector(DriveFile file);
    void retrieveContents(DriveFile file);
}
